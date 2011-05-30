package org.poolc.remote;

public class RemoteException extends RuntimeException {

	private static final long serialVersionUID = -2614173983852467973L;

	public static final int ERROR_UNKNOWN_ERROR = 1000;
	public static final int ERROR_NOT_CONNECTED = 1001;
	public static final int ERROR_CONNECTION_IS_LOST = 1002;
	public static final int ERROR_CANNOT_SEND_MESSAGE = 1003;
	public static final int ERROR_SERVER_EXCEPTION = 1011;
	public static final int ERROR_CANNOT_REQUEST_TIME_OUT = 1020;
	public static final int ERROR_CANNOT_INVOCATION_REMOTE_METHOD = 1022;
	public static final int ERROR_CALL_OBJECT_NOT_BINDED = 1031;
	public static final int ERROR_NESTED_EXCEPTION = 1040;

	private int errorCode;

	public RemoteException(int errorCode) {
		super(resolveErrorCode(errorCode));
		this.errorCode = errorCode;
	}
	
	public RemoteException(int errorCode, Throwable nestedException) {
		super(resolveErrorCode(errorCode), nestedException);
		this.errorCode = errorCode;
	}

	public RemoteException(String message) {
		super(message);
		this.errorCode = ERROR_NESTED_EXCEPTION;
	}
	
	public RemoteException(Throwable nestedException) {
		super(nestedException);
		this.errorCode = ERROR_NESTED_EXCEPTION;
	}
	
	public int getErrorCode() {
		return errorCode;
	}
	
	public String getErrorMessage() {
		return resolveErrorCode(errorCode);
	}
	
	public static String resolveErrorCode(int errorCode) {
		switch (errorCode) {
		case ERROR_NOT_CONNECTED:
			return "Socket is not connected.";
		case ERROR_CONNECTION_IS_LOST:
			return "Connection is lost.";
		case ERROR_CANNOT_SEND_MESSAGE:
			return "Cannot send a call message to provider.";
		case ERROR_SERVER_EXCEPTION:
			return "Exception occurs in provider-side.";
		case ERROR_CANNOT_REQUEST_TIME_OUT: 
			return "Request time out.";
		case ERROR_CANNOT_INVOCATION_REMOTE_METHOD:
			return "Cannot invoke a remote method.";
		case ERROR_CALL_OBJECT_NOT_BINDED:
			return "Call object is not binded.";
		}
		return "Unknown error occurred.";
	}
}
