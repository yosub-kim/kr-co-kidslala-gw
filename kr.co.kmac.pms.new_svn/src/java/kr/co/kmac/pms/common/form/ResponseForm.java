/*
 * $Id: ResponseForm.java,v 1.1 2009/09/19 11:15:36 cvs3 Exp $
 * created by    : ygkim
 * creation-date : 2005. 6. 2.
 * =========================================================
 * Copyright (c) 2005 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.common.form;

import org.apache.struts.action.ActionForm;

/**
 * 수행의 성공, 실패 여부를 반환하는 폼 빈
 * @author ygkim
 * @version $Id: ResponseForm.java,v 1.1 2009/09/19 11:15:36 cvs3 Exp $
 */
public class ResponseForm extends ActionForm {
	/**
	 * generated serialVersionUID
	 */
	private static final long serialVersionUID = 109756771178872916L;

	private boolean success;
	private String message;
	private Throwable throwable;

	public ResponseForm() {

	}

	public ResponseForm(boolean success, String message) {
		this.success = success;
		this.message = message;
	}

	/**
	 * @return Returns the message.
	 */
	public String getMessage() {
		return this.message;
	}

	/**
	 * @param message The message to set.
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return Returns the success.
	 */
	public boolean isSuccess() {
		return this.success;
	}

	/**
	 * @param success The success to set.
	 */
	public void setSuccess(boolean success) {
		this.success = success;
	}

	/**
	 * @return Returns the throwable.
	 */
	public Throwable getThrowable() {
		return this.throwable;
	}

	/**
	 * @param throwable The throwable to set.
	 */
	public void setThrowable(Throwable throwable) {
		this.throwable = throwable;
	}

}
