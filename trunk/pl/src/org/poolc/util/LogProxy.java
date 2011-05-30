package org.poolc.util;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.poolc.remote.RemoteClient;

public class LogProxy implements InvocationHandler {
	private Object realImpl;
	private String className;
	private Logger logger;

	private boolean requestLogging = true;
	private boolean endOfRequestLogging = true;

	public LogProxy(Object realImpl, boolean requestLogging, boolean endOfRequestLogging) {
		super();
		this.realImpl = realImpl;
		this.className = realImpl.getClass().getSimpleName();
		this.logger = Logger.getLogger(className);

		this.requestLogging = requestLogging;
		this.endOfRequestLogging = endOfRequestLogging;
	}

	private String getFunctionName(Method method) {
		return className + "#" + method.getName();
	}

	private String toString(Method method, Object[] args) {
		if (method.getParameterTypes().length == 0)
			return "";
		if (args == null)
			return "null";
		if (args.length == 0)
			return "";
		return Arrays.toString(args);
	}

	private String toString(Object value) {
		if (value == null)
			return "null";

		Class<?> clazz = value.getClass();
		if (clazz.isArray()) {
			Object[] objs = (Object[]) value;
			if (objs.length == 0)
				return "[]";
			return Arrays.toString(objs);
		}
		return String.valueOf(value);
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		if (method.getName().equals("toString"))
			return method.invoke(realImpl, args);

		if (requestLogging) {
			if (RemoteClient.getContextClient() != null) {
				logger.log(Level.INFO, "Client[" + RemoteClient.getContextClient().getInetAddressString()
						+ "] requests " + getFunctionName(method) + " (" + toString(method, args) + ")");
			} else {
				logger.log(Level.INFO, "Server responses " + getFunctionName(method) + " (" + toString(method, args) + ")");
			}
		}

		Object returnValue = null;
		try {
			returnValue = method.invoke(realImpl, args);
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Invocation Exception", e);

			/* for nested RemoteException */
			throw e;
		}

		if (endOfRequestLogging) {
			if (RemoteClient.getContextClient() != null) {
				logger.log(Level.INFO, "Server returns (" + toString(returnValue) + ") by request ("
						+ getFunctionName(method) + ")");
			} else {
				logger.log(Level.INFO, "Client returns (" + toString(returnValue) + ") by request ("
						+ getFunctionName(method) + ")");
			}
		}
		return returnValue;
	}

	public static <T> T create(Class<T> interfaceClass, T realImpl, boolean requestLogging, boolean endOfRequestLogging) {
		return interfaceClass.cast(Proxy.newProxyInstance(interfaceClass.getClassLoader(),
				new Class<?>[] { interfaceClass }, new LogProxy(realImpl, requestLogging, endOfRequestLogging)));
	}
}