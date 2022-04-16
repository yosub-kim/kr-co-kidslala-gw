package kr.co.kmac.pms.moudb.exception;

public class MoudbException extends RuntimeException {
	
	static final long serialVersionUID = -7034897190745766939L;
	
	public MoudbException() {
		super();
	}
	
	public MoudbException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public MoudbException (String message) {
		super(message);
	}
	
	public MoudbException (Throwable cause) {
		super(cause);
	}

}
