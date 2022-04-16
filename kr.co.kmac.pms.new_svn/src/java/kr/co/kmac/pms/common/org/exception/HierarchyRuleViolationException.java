/*
 * $Id: HierarchyRuleViolationException.java,v 1.1 2009/09/19 11:15:39 cvs3 Exp $
 * created by    : ����ȣ
 * creation-date : 2005. 10. 28.
 * =========================================================
 * Copyright (c) 2005 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.common.org.exception;

/**
 * �׷쿡 �ڽ��� �߰��ϰų� �����Ҷ� ���� ��Ģ�� ���ݵǸ� �߻��Ѵ�.
 * @author ����ȣ
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
