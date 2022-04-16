/*
 * $Id: CodeException.java,v 1.1 2009/09/19 11:15:44 cvs3 Exp $
 * created by    : jiwoongLee
 * creation-date : 2005. 10. 27.
 * =========================================================
 * Copyright (c) 2005 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.common.menuMy.exception;

/**
 * 공콩 코드 관련 예외
 * @author jiwoongLee
 * @version $Id: CodeException.java,v 1.1 2009/09/19 11:15:44 cvs3 Exp $
 */
public class MenuMyException extends RuntimeException {

	static final long serialVersionUID = -7034897190745766939L;

	public MenuMyException() {
		super();
	}

	public MenuMyException(String message, Throwable cause) {
		super(message, cause);
	}

	public MenuMyException(String message) {
		super(message);
	}

	public MenuMyException(Throwable cause) {
		super(cause);
	}
}
