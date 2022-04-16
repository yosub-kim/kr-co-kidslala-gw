/*
 * $Id: HierarchyRuleViolationException.java,v 1.1 2009/09/19 11:15:39 cvs3 Exp $
 * created by    : 최인호
 * creation-date : 2005. 10. 28.
 * =========================================================
 * Copyright (c) 2005 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.common.org.exception;

/**
 * 그룹에 자식을 추가하거나 삭제할때 구조 규칙에 위반되면 발생한다.
 * @author 최인호
 * @version $Id: HierarchyRuleViolationException.java,v 1.1 2009/09/19 11:15:39 cvs3 Exp $
 */
public class HierarchyRuleViolationException extends RuntimeException {
	/**
	 * generated serialVersionUID
	 */
	private static final long serialVersionUID = 38531064981276863L;

	public HierarchyRuleViolationException() {
		super();
	}

	public HierarchyRuleViolationException(String message, Throwable cause) {
		super(message, cause);
	}

	public HierarchyRuleViolationException(String message) {
		super(message);
	}

	public HierarchyRuleViolationException(Throwable cause) {
		super(cause);
	}

}
