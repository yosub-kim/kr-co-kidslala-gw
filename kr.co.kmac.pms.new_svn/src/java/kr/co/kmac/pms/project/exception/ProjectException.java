/*
 * $Id: ProjectException.java,v 1.1 2009/09/19 11:15:41 cvs3 Exp $
 * creation-date : 2006. 3. 7.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.project.exception;

/**
 * TODO Provide description for "ProjectException"
 * @author halberd
 * @version $Id: ProjectException.java,v 1.1 2009/09/19 11:15:41 cvs3 Exp $
 */
public class ProjectException extends RuntimeException {
	static final long serialVersionUID = -7034897190745766939L;
	
	public ProjectException() {
		super();
	}

	public ProjectException(String message, Throwable cause) {
		super(message, cause);
	}

	public ProjectException(String message) {
		super(message);
	}

	public ProjectException(Throwable cause) {
		super(cause);
	}
}

