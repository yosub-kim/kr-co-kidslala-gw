package kr.co.kmac.pms.common.worklist.exception;

/**
 * CommonException
 * @author HAN
 * @version $Id: WorklistException.java,v 1.1 2009/09/19 11:15:45 cvs3 Exp $
 */
public class WorklistException extends RuntimeException{
	
	private static final long serialVersionUID = 4053521058256564355L;
	
	public WorklistException(){
		super();
	}
	public WorklistException(String message, Throwable cause) {
		super(message, cause);
	}
	public WorklistException(String message) {
		super(message);
	}
	public WorklistException(Throwable cause) {
		super(cause);
	}
}
