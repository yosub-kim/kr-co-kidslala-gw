/*
 * $Id: CompanyScheduleException.java,v 1.1 2009/09/19 11:15:40 cvs3 Exp $
 * created by    : ¾È¼ºÈ£
 * creation-date : 2006. 6. 2.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.companySchedule.exception;

public class CompanyScheduleException extends RuntimeException {
	private static final long serialVersionUID = 8438166431150067058L;

	public CompanyScheduleException() {
		super();
	}

	public CompanyScheduleException(String message, Throwable cause) {
		super(message, cause);
	}

	public CompanyScheduleException(String message) {
		super(message);
	}

	public CompanyScheduleException(Throwable cause) {
		super(cause);
	}
}
