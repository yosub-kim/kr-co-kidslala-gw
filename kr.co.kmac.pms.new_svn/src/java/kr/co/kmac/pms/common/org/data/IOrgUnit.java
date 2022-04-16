/*
 * $Id: IOrgUnit.java,v 1.1 2009/09/19 11:15:25 cvs3 Exp $
 * created by    : 최인호
 * creation-date : 2005. 10. 24.
 * =========================================================
 * Copyright (c) 2005 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.common.org.data;

import java.util.List;

import kr.co.kmac.pms.common.org.exception.HierarchyRuleViolationException;

/**
 * 부서 모델 인터페이스.
 * @author 최인호
 * @version $Id: IOrgUnit.java,v 1.1 2009/09/19 11:15:25 cvs3 Exp $
 */
public interface IOrgUnit extends IUserGroup {
	public List getSubOrgUnitList();
	public void addSubOrgUnit(IOrgUnit child) throws HierarchyRuleViolationException;
	public void removeSubOrgUnit(IOrgUnit child) throws HierarchyRuleViolationException;
}
