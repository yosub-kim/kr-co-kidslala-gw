/*
 * $Id: SanctionException.java,v 1.1 2009/09/19 11:15:39 cvs3 Exp $
 * created by    : jiwoongLee
 * creation-date : 2005. 10. 27.
 * =========================================================
 * Copyright (c) 2005 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.sanction.exception;

/**
 * 결제 관련 예외
 * 
 * @author jiwoongLee
 * @version $Id: SanctionException.java,v 1.1 2009/09/19 11:15:39 cvs3 Exp $
 */
public class SanctionException extends RuntimeException {

	static final long serialVersionUID = -7034897190745766939L;

	public SanctionException() {
		super();
	}

	public SanctionException(String message, Throwable cause) {
		super(message, cause);
	}

	public SanctionException(String message) {
		super(message);
	}

	public SanctionException(Throwable cause) {
		super(cause);
	}
}
