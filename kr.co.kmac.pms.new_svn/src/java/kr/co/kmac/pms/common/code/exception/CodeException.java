/*
 * $Id: CodeException.java,v 1.1 2009/09/19 11:15:44 cvs3 Exp $
 * created by    : jiwoongLee
 * creation-date : 2005. 10. 27.
 * =========================================================
 * Copyright (c) 2005 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.common.code.exception;

/**
 * 공콩 코드 관련 예외
 * @author jiwoongLee
 * @version $Id: CodeException.java,v 1.1 2009/09/19 11:15:44 cvs3 Exp $
 */
public class CodeException extends RuntimeException {

	static final long serialVersionUID = -7034897190745766939L;

	public CodeException() {
		super();
	}

	public CodeException(String message, Throwable cause) {
		super(message, cause);
	}

	public CodeException(String message) {
		super(message);
	}

	public CodeException(Throwable cause) {
		super(cause);
	}
}
