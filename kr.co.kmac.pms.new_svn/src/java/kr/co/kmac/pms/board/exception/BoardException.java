/*
 * $Id: BoardException.java,v 1.1 2009/09/19 11:15:38 cvs3 Exp $
 * created by    : ¾È¼ºÈ£
 * creation-date : 2006. 5. 18.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.board.exception;

public class BoardException extends RuntimeException {
	private static final long serialVersionUID = -7161517095134738198L;
	
	public BoardException() {
		super();
	}

	public BoardException(String message, Throwable cause) {
		super(message, cause);
	}

	public BoardException(String message) {
		super(message);
	}

	public BoardException(Throwable cause) {
		super(cause);
	}

}
