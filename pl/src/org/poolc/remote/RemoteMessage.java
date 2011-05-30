package org.poolc.remote;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.poolc.util.Base64;
import org.poolc.util.Base64DecodingException;

public abstract class RemoteMessage implements Serializable {

	private static final long serialVersionUID = 930394899042442149L;

	protected long id;

	public RemoteMessage() {
		super();
		this.id = System.currentTimeMillis();
	}

	public long getId() {
		return id;
	}

	@Override
	public String toString() {
		return "[RemoteMessage id=" + id + "]";
	}

	public static class Call extends RemoteMessage {

		private static final long serialVersionUID = -3597516251330888926L;

		private String bindObjectName;

		private String methodName;
		private String[] paramsClassName;
		private Object[] paramsObject;
		
		Call() {
		}

		public Call(String bindObjectName, String methodName,
				String[] paramsClassName, Object[] paramsObject) {
			super();
			this.bindObjectName = bindObjectName;
			this.methodName = methodName;
			this.paramsClassName = paramsClassName;
			this.paramsObject = paramsObject;
		}

		public String getBindObjectName() {
			return bindObjectName;
		}

		public void setBindObjectName(String bindObjectName) {
			this.bindObjectName = bindObjectName;
		}

		public String getMethodName() {
			return methodName;
		}

		public void setMethodName(String methodName) {
			this.methodName = methodName;
		}

		public String[] getParamsClass() {
			return paramsClassName;
		}

		public void setParamsClass(String[] paramsClassName) {
			this.paramsClassName = paramsClassName;
		}

		public Object[] getParamsObject() {
			return paramsObject;
		}

		public void setParamsObject(Object[] paramsObject) {
			this.paramsObject = paramsObject;
		}

		@Override
		public String toString() {
			if (paramsClassName.length == 0)
				return "[RemoteMessage.Call id=" + id + ", bindObjectName="
						+ bindObjectName + ", methodName=" + methodName + "]";
			return "[RemoteMessage.Call id=" + id + ", bindObjectName="
					+ bindObjectName + ", methodName=" + methodName
					+ ", paramsClass=[" + paramsClassName[0].toString()
					+ "...], paramsObject=[" + paramsObject[0].toString()
					+ "...]";
		}

	}

	public static class Return extends RemoteMessage {

		private static final long serialVersionUID = 6412402662178686481L;

		protected long returnId;
		protected Object object;
		
		Return() {
		}
		
		protected Return(long returnId) {
			super();
			this.returnId = returnId;
		}

		public Return(long returnId, Object object) {
			super();
			this.returnId = returnId;
			this.object = object;
		}

		public long getReturnId() {
			return returnId;
		}

		public void setReturnId(long returnId) {
			this.returnId = returnId;
		}

		public Object getObject() {
			return object;
		}

		public void setObject(Object object) {
			this.object = object;
		}

		@Override
		public String toString() {
			return "[RemoteMessage.Return id=" + id + ", returnId=" + returnId
					+ ", object=" + object + "]";
		}
	}

	public static class Exception extends RemoteMessage.Return {

		private static final long serialVersionUID = -2644180337380272511L;

		private static final int MESSAGE = 0;
		private static final int THROWABLE = 1;
		private int type;
		
		Exception() {
		}

		public Exception(long returnId, Throwable e) {
			super(returnId);
			try {
				this.object = encodeThrowable(e);
				this.type = THROWABLE;
			} catch (java.lang.Exception ex) {
				this.object = e.getMessage();
				this.type = MESSAGE;
			}
		}

		public String getExceptionMessage() {
			if (type == THROWABLE)
				return null;
			
			return (String) this.object;
		}
		
		public boolean isMessageType() {
			return type == MESSAGE;
		}
		
		public Throwable getThrowable() {
			if (type == MESSAGE)
				return new java.lang.Exception((String) this.object);
			
			try {
				return decodeThrowable((String) this.object);
			} catch (java.lang.Exception e) {
				return e;
			}
		}

		@Override
		public String toString() {
			return "[RemoteMessage.Exception id=" + id + ", returnId="
					+ returnId + ", exception=" + getExceptionMessage().toString()
					+ "]";
		}
	}
	
	static String encodeThrowable(Throwable e) throws IOException {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
		objectOutputStream.writeObject(e);
		objectOutputStream.flush();
		objectOutputStream.close();
		
		byte[] objectBytes = byteArrayOutputStream.toByteArray();
		byteArrayOutputStream.close();
		
		return Base64.encode(objectBytes);
	}
	
	static Throwable decodeThrowable(String message) throws IOException, Base64DecodingException, ClassNotFoundException {
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(Base64.decode(message));
		ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
		Throwable e = (Throwable) objectInputStream.readObject();
		objectInputStream.close();
		byteArrayInputStream.close();
		
		return e;
	}
}
