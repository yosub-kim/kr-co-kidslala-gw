package kr.co.kmac.pms.support.issue.exception;
import org.apache.commons.net.nntp.Threadable;

public class IssueException extends RuntimeException {
	
	static final long serialVersionUID = -7034897190745766939L;
	
	public IssueException() {
		super();
	}
	
	public IssueException (String message, Throwable cause) {
		super(message, cause);
	}
	
	public IssueException (String message) {
		super(message);
	}
	
	public IssueException (Throwable cause) {
		super(cause);
	}

}
