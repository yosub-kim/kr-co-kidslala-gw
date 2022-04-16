/*
 * $Id: CustomerException.java,v 1.1 2009/09/19 11:15:27 cvs3 Exp $
 * creation-date : 2006. 3. 7.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.customer.exception;

/**
 * TODO Provide description for "CustomerException"
 * @author halberd
 * @version $Id: CustomerException.java,v 1.1 2009/09/19 11:15:27 cvs3 Exp $
 */
public class CustomerException extends RuntimeException {
	static final long serialVersionUID = -7034897190745766939L;
	
	public CustomerException() {
		super();
	}

	public CustomerException(String message, Throwable cause) {
		super(message, cause);
	}

	public CustomerException(String message) {
		super(message);
	}

	public CustomerException(Throwable cause) {
		super(cause);
	}
}

