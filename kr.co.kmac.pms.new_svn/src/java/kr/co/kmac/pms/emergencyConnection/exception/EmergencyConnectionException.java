/*
 * $Id: EmergencyConnectionException.java,v 1.1 2009/09/19 11:15:42 cvs3 Exp $
 * created by    : ¾È¼ºÈ£
 * creation-date : 2006. 7. 7.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.emergencyConnection.exception;

public class EmergencyConnectionException extends RuntimeException {
	private static final long serialVersionUID = -9131685279405389370L;
	public EmergencyConnectionException() {
		super();
	}
	public EmergencyConnectionException(String message, Throwable cause) {
		super(message, cause);
	}
	public EmergencyConnectionException(String message) {
		super(message);
	}
	public EmergencyConnectionException(Throwable cause) {
		super(cause);
	}
 
}
 