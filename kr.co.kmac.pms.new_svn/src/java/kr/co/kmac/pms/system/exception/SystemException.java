/*
 * $Id: SystemException.java,v 1.1 2009/09/19 11:15:43 cvs3 Exp $
 * created by    : jiwoongLee
 * creation-date : 2005. 10. 27.
 * =========================================================
 * Copyright (c) 2005 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.system.exception;

/**
 * 결제 관련 예외
 * 
 * @author jiwoongLee
 * @version $Id: SystemException.java,v 1.1 2009/09/19 11:15:43 cvs3 Exp $
 */
public class SystemException extends RuntimeException {

	static final long serialVersionUID = -7034897190745766939L;

	public SystemException() {
		super();
	}

	public SystemException(String message, Throwable cause) {
		super(message, cause);
	}

	public SystemException(String message) {
		super(message);
	}

	public SystemException(Throwable cause) {
		super(cause);
	}
}
