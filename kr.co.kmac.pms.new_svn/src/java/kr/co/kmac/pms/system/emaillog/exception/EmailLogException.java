/**
 * @author yhyim
 * @create date: 2018-11-11
 */
package kr.co.kmac.pms.system.emaillog.exception;

public class EmailLogException extends RuntimeException {
	private static final long serialVersionUID = 4787595070482999910L;
	
	public EmailLogException() {
		super();
	}

	public EmailLogException(String message, Throwable cause) {
		super(message, cause);
	}

	public EmailLogException(String message) {
		super(message);
	}

	public EmailLogException(Throwable cause) {
		super(cause);
	}
}
