/*
 * $Id: AttachException.java,v 1.1 2009/09/19 11:15:36 cvs3 Exp $
 * creation-date : 2006. 3. 7.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.attach.exception;

/**
 * TODO Provide description for "AttachException"
 * @author halberd
 * @version $Id: AttachException.java,v 1.1 2009/09/19 11:15:36 cvs3 Exp $
 */
public class AttachException extends RuntimeException {
	static final long serialVersionUID = -7034897190745766939L;
	
	public AttachException() {
		super();
	}

	public AttachException(String message, Throwable cause) {
		super(message, cause);
	}

	public AttachException(String message) {
		super(message);
	}

	public AttachException(Throwable cause) {
		super(cause);
	}
}

