/*
 * $Id: ExpertPoolException.java,v 1.1 2009/09/19 11:15:30 cvs3 Exp $
 * created by    : jiwoongLee
 * creation-date : 2005. 10. 27.
 * =========================================================
 * Copyright (c) 2005 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.expertpool.exception;

/**
 * 전문가 풀 관련 예외
 * @author jiwoongLee
 * @version $Id: ExpertPoolException.java,v 1.1 2009/09/19 11:15:30 cvs3 Exp $
 */
public class ExpertPoolException extends RuntimeException {

	static final long serialVersionUID = -7034897190745766939L;
	
	public ExpertPoolException() {
		super();
	}

	public ExpertPoolException(String message, Throwable cause) {
		super(message, cause);
	}

	public ExpertPoolException(String message) {
		super(message);
	}

	public ExpertPoolException(Throwable cause) {
		super(cause);
	}
}
