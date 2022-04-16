/*
 * $Id: DownloadLogException.java,v 1.1 2009/09/19 11:15:44 cvs3 Exp $
 * created by    : ¾È¼ºÈ£
 * creation-date : 2006. 5. 30.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.system.downloadlog.exception;

public class DownloadLogException extends RuntimeException {
	private static final long serialVersionUID = 4787595070482490810L;
	
	public DownloadLogException() {
		super();
	}

	public DownloadLogException(String message, Throwable cause) {
		super(message, cause);
	}

	public DownloadLogException(String message) {
		super(message);
	}

	public DownloadLogException(Throwable cause) {
		super(cause);
	}
}
