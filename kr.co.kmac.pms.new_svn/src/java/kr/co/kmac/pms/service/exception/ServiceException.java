/*
 * $Id: ServiceException.java,v 1.1 2009/09/19 11:15:43 cvs3 Exp $
 * creation-date : 2006. 3. 7.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.service.exception;

/**
 * TODO Provide description for "ProjectException"
 * @author halberd
 * @version $Id: ServiceException.java,v 1.1 2009/09/19 11:15:43 cvs3 Exp $
 */
public class ServiceException extends RuntimeException {
	static final long serialVersionUID = -7034897190745766939L;
	
	public ServiceException() {
		super();
	}

	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public ServiceException(String message) {
		super(message);
	}

	public ServiceException(Throwable cause) {
		super(cause);
	}
}

