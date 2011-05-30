package org.poolc.remote;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringReader;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import org.poolc.xml.XMLElement;
import org.poolc.xml.XMLParser;
import org.poolc.xml.XMLSerializer;

public class RemoteClient implements Runnable, Comparable<RemoteClient> {
	private static final int REQUEST_WAIT_SEC = 3600;
	private static final Logger logger = Logger.getLogger(RemoteClient.class.getName());
	
	private static final String CLEANUP = "$#CLEANUP#$";
	
	public static interface CleanupHandler {
		public void onClientCleanup(RemoteClient client);
	}

	private static Map<Thread, RemoteClient> contextClient = new ConcurrentHashMap<Thread, RemoteClient>();
	
	private static Map<String, Class<?>> primitiveClasssMap = new HashMap<String, Class<?>>();
	static {
		for (Class<?> clazz: new Class<?>[] {
				Boolean.TYPE, Byte.TYPE, Character.TYPE, Short.TYPE,
				Integer.TYPE, Long.TYPE, Float.TYPE, Double.TYPE, Void.TYPE
		}) {
			primitiveClasssMap.put(clazz.getName(), clazz);
		}
	}
	
	private static class NullObject {}

	private ExecutorService threadPool = Executors.newCachedThreadPool();
	
	private Socket socket;
	private InetSocketAddress socketAddress;
	
	private BufferedReader reader;
	private PrintWriter writer;

	private Map<Long, Object> waitLockMap = new ConcurrentHashMap<Long, Object>();
	private Map<Long, Object> returnObjectMap = new ConcurrentHashMap<Long, Object>();
	private Map<String, Object> proxyMap = new ConcurrentHashMap<String, Object>();
	
	private List<CleanupHandler> cleanupHandlers;
	
	public RemoteClient(String ip, int port) throws IOException {
		this(new Socket(ip, port));
	}
	
	RemoteClient(Socket socket) throws IOException {
		super();
		initialize(socket);
	}
	
	private void initialize(Socket socket) throws IOException {
		this.socket = socket;
		this.writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
		this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		
		this.socketAddress = 
			new InetSocketAddress(socket.getInetAddress().getHostAddress(), socket.getLocalPort());
		
		threadPool.execute(this);
		logger.log(Level.FINEST, socket.getInetAddress().toString() + " is connected.");
	}
	
	public Socket getSocket() {
		return socket;
	}
	
	public InetSocketAddress getSocketAddress() {
		return socketAddress;
	}
	
	public String getInetAddressString() {
		return socket.getInetAddress().toString();
	}
	
	public <T> T proxy(Class<T> interfaceClass) {
		return proxy(interfaceClass.getName(), interfaceClass);
	}
	
	public <T> T proxy(final String proxyName, Class<T> interfaceClass) {
		Object proxyObject = proxyMap.get(proxyName);
		if (proxyObject == null) {
			proxyObject = Proxy.newProxyInstance(interfaceClass.getClassLoader(), 
					new Class<?>[] { interfaceClass }, new InvocationHandler() {
				@Override
				public Object invoke(Object proxy, Method method, Object[] args)
						throws Throwable {
					try {
						Method objMethod = Object.class.getMethod(method.getName(), method.getParameterTypes());
						if (objMethod != null) {
							System.err.println(objMethod);
							return objMethod.invoke(proxyName, args);
						}
					} catch (Exception e) {
					}
					
					Class<?>[] paramsClass = method.getParameterTypes();
					String[] paramsClassName = new String[paramsClass.length];
					for (int index = 0; index < paramsClass.length; index++)
						paramsClassName[index] = paramsClass[index].getName();
					return call(proxyName, method.getName(), paramsClassName, args);
				}
			});
			proxyMap.put(proxyName, proxyObject);
		}
		return interfaceClass.cast(proxyObject);
	}

	public Object call(String bindObjectName, String methodName, String[] paramsClassName, Object[] paramsObject)
			throws RemoteException {
		
		RemoteMessage.Call callMessage = new RemoteMessage.Call(bindObjectName, methodName, paramsClassName, paramsObject);
		send(callMessage);

		long messageId = callMessage.getId();
		Object lock = new Object();
		waitLockMap.put(messageId, lock);

		int errorCount = 0;
		synchronized (lock) {
			while (waitLockMap.containsKey(messageId) &&
					!returnObjectMap.containsKey(messageId)) {
				try {
					lock.wait(1000);
				} catch (InterruptedException e) {
				}
				if (++errorCount > REQUEST_WAIT_SEC)
					break;
			}
		}

		waitLockMap.remove(messageId);
		
		Object returnValue = returnObjectMap.remove(messageId);
		if (errorCount > REQUEST_WAIT_SEC && returnValue == null) {
			throw new RemoteException(RemoteException.ERROR_CANNOT_REQUEST_TIME_OUT);
		}
		
		if (returnValue instanceof NullObject)
			returnValue = null;
		
		if (returnValue instanceof Throwable) {
			if (!returnValue.getClass().equals(RemoteException.class))
				returnValue = new RemoteException(RemoteException.ERROR_UNKNOWN_ERROR,
						(Throwable) returnValue);
			throw (RemoteException) returnValue;
		}
		
		return returnValue;
	}
	
	public void send(RemoteMessage message) throws RemoteException {
		XMLElement xml = null;
		try {
			xml = XMLSerializer.serialize(message);
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			return;
		} 
		if (xml == null)
			return;
		
		try {
			if (writer != null) {
				synchronized (writer) {
					writer.println(xml.toString());
					writer.flush();
				}
			}
			
			logger.log(Level.FINEST, xml.toString());
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			cleanup();
			
			throw new RemoteException(RemoteException.ERROR_CANNOT_SEND_MESSAGE);
		}
	}
	
	@Override
	public void run() {
		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				if (line.equals(CLEANUP)) {
					try {
						writer.close();
					} catch (Exception e) {
					}
					writer = null;
					break;
				}
				
				XMLElement element = null;
				try {
					logger.log(Level.FINEST, line);
					element = XMLParser.parse(new InputSource(new StringReader(line)));
				} catch (SAXException e) {
					logger.log(Level.WARNING, e.getMessage(), e);
					continue;
					
				} catch (IOException e) {
					throw e;
				}
				
				RemoteMessage message = (RemoteMessage) XMLSerializer.deserialize(element);
				if (message instanceof RemoteMessage.Call) {
					threadPool.execute(new RequestProcessor((RemoteMessage.Call) message));
					
				} else if (message instanceof RemoteMessage.Return) {
					threadPool.execute(new ResponseProcessor((RemoteMessage.Return) message));
				}
			}
		} catch (IOException e) {
		} finally {
			cleanup();
		}
	}
	
	public void disconnect() {
		if (writer != null) {
			try {
				writer.println(CLEANUP);
				writer.flush();
			} catch (Exception e) {
			}
		}
	}
	
	void cleanup() {
		if (reader != null) {
			try {
				reader.close();
			} catch (IOException e) {
			}
			reader = null;
		}
		if (writer != null) {
			try {
				writer.close();
			} catch (Exception e) {
			}
			writer = null;
		}
		if (socket != null) {
			try {
				socket.close();
			} catch (IOException e) {
			}
			socket = null;
		}
		threadPool.shutdown();
		
		for (Object waitLock: waitLockMap.values()) {
			synchronized (waitLock) {
				waitLock.notifyAll();
			}
		}
		
		waitLockMap.clear();
		returnObjectMap.clear();
		proxyMap.clear();
		
		if (cleanupHandlers != null) {
			Iterator<CleanupHandler> iterator = cleanupHandlers.iterator();
			while (iterator.hasNext()) {
				CleanupHandler handler = iterator.next();
				if (handler != null) {
					try {
						handler.onClientCleanup(this);
					} catch (Exception e) {
					}
				}
			}
			cleanupHandlers.clear();
		}
	}
	
	public void addCleanupHandler(CleanupHandler cleanupHandler) {
		if (this.cleanupHandlers == null)
			this.cleanupHandlers = new CopyOnWriteArrayList<CleanupHandler>();
		
		this.cleanupHandlers.add(cleanupHandler);
	}
	
	public void removeCleanupHandler(CleanupHandler cleanupHandler) {
		if (this.cleanupHandlers != null) {
			this.cleanupHandlers.remove(cleanupHandler);
		}
	}
	
	@Override
	public String toString() {
		return "[RemoteClient " + getSocketAddress() + "]";
	}
	
	private class RequestProcessor implements Runnable {
		private RemoteMessage.Call callMessage;

		public RequestProcessor(RemoteMessage.Call callMessage) {
			super();
			this.callMessage = callMessage;
		}
		
		@Override
		public void run() {
			Object bindObject = RemoteRegistry.find(callMessage.getBindObjectName());
			if (bindObject == null) {
				System.err.println(callMessage.getBindObjectName());
				System.err.println(RemoteRegistry.bindObjectMap.toString());
				throw new RemoteException(RemoteException.ERROR_CALL_OBJECT_NOT_BINDED);
			}

			RemoteMessage.Return returnMessage = null;
			
			try {
				String[] paramsClassName = callMessage.getParamsClass();
				Class<?>[] paramsClass = new Class<?>[paramsClassName.length];
				for (int index = 0; index < paramsClass.length; index++) {
					if (primitiveClasssMap.containsKey(paramsClassName[index]))
						paramsClass[index] = primitiveClasssMap.get(paramsClassName[index]);
					else
						paramsClass[index] = Class.forName(paramsClassName[index]);
				}

				Class<?> clazz = bindObject.getClass();
				Method method = clazz.getMethod(callMessage.getMethodName(), paramsClass);
				method.setAccessible(true);
				
				contextClient.put(Thread.currentThread(), RemoteClient.this);
				Object returnValue = method.invoke(bindObject, callMessage.getParamsObject());
				contextClient.remove(Thread.currentThread());
				
				returnMessage = new RemoteMessage.Return(callMessage.getId(), returnValue);
				
			} catch (Exception e) {
				logger.log(Level.SEVERE, e.getMessage(), e);
				
				returnMessage = new RemoteMessage.Exception(callMessage.getId(), e);
			}
			send(returnMessage);
		}
	}
	
	public static RemoteClient getContextClient() {
		return contextClient.get(Thread.currentThread());
	}
	
	private class ResponseProcessor implements Runnable {
		private RemoteMessage.Return returnMessage;

		public ResponseProcessor(RemoteMessage.Return returnMessage) {
			super();
			this.returnMessage = returnMessage;
		}
		
		@Override
		public void run() {
			Object returnObject = returnMessage.getObject();
			long requestId = returnMessage.getReturnId();
			
			if (returnMessage instanceof RemoteMessage.Exception) {
				RemoteMessage.Exception exceptionMessage = (RemoteMessage.Exception) returnMessage;
				
				if (exceptionMessage.isMessageType())
					returnObject = new RemoteException(exceptionMessage.getExceptionMessage());
				else {
					Throwable throwable = exceptionMessage.getThrowable();
					returnObject = new RemoteException(throwable);
				}
			}
			
			if (returnObject == null) {
				returnObject = new NullObject();
			}
			
			returnObjectMap.put(requestId, returnObject);
			
			Object lock = waitLockMap.get(requestId);
			if (lock != null) {
				synchronized (lock) {
					lock.notifyAll();
				}
			}
		}
	}

	@Override
	public int compareTo(RemoteClient o) {
		return hashCode() - o.hashCode();
	}
}
