package kr.co.kmac.pms.support.exception;

public class SupportException extends RuntimeException {
	
	static final long serialVersionUID = -7034897190745669115L;
	
	public SupportException() {
		super();
	}
	
	public SupportException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public SupportException(String message) {
		super(message);
	}
	
	public SupportException(Throwable cause) {
		super(cause);
	}
}
