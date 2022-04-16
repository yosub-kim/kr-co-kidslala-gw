/*
 * $Id: ScheduleException.java,v 1.1 2009/09/19 11:15:38 cvs3 Exp $
 * created by    : ¾È¼ºÈ£
 * creation-date : 2006. 4. 11.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.schedule.exception;

public class ScheduleException extends RuntimeException {
	private static final long serialVersionUID = 993792191366305183L;

	public ScheduleException() {
		super();
	}

	public ScheduleException(String message, Throwable cause) {
		super(message, cause);
	}

	public ScheduleException(String message) {
		super(message);
	}

	public ScheduleException(Throwable cause) {
		super(cause);
	}
}
