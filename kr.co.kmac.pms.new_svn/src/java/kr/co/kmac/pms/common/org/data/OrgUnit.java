/*
 * $Id: OrgUnit.java,v 1.1 2009/09/19 11:15:25 cvs3 Exp $
 * created by    : 최인호
 * creation-date : 2005. 10. 21.
 * =========================================================
 * Copyright (c) 2005 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.common.org.data;

import java.util.List;

import kr.co.kmac.pms.common.org.exception.HierarchyRuleViolationException;
import kr.co.kmac.pms.common.org.util.GroupUtils;

/**
 * 부서 모델.
 * @author 최인호
 * @version $Id: OrgUnit.java,v 1.1 2009/09/19 11:15:25 cvs3 Exp $
 */
public class OrgUnit extends AbstractHierarchicalGroup implements IOrgUnit {
	/**
	 * generated serialVersionUID
	 */
	private static final long serialVersionUID = -3602161159596882903L;

	public OrgUnit(String id, String name) {
		super(id, name);
		setGroupType(GROUP_ORG_UNIT);
	}

	public OrgUnit(String id) {
		super(id);
		setGroupType(GROUP_ORG_UNIT);
	}

	/*
	 * (non-Javadoc)
	 * @see com.miracom.bpms.security.core.IOrgUnit#addSubOrgUnit(com.miracom.bpms.security.core.IOrgUnit)
	 */
	public void addSubOrgUnit(IOrgUnit orgUnit) {
		addSubGroup(orgUnit);
	}
	/*
	 * (non-Javadoc)
	 * @see com.miracom.bpms.security.core.IOrgUnit#removeSubOrgUnit(com.miracom.bpms.security.core.IOrgUnit)
	 */
	public void removeSubOrgUnit(IOrgUnit orgUnit) {
		removeSubGroup(orgUnit);
	}

	/*
	 * (non-Javadoc)
	 * @see com.miracom.bpms.security.core.IOrgUnit#getSubOrgUnitList()
	 */
	public List getSubOrgUnitList() {
		return getSubGroupList();
	}

	/*
	 * (non-Javadoc)
	 * @see com.miracom.bpms.security.core.IOrgUnit#setSubOrgUnitList(java.util.List)
	 */
	public void setSubOrgUnitList(List subOrgUnitList) {
		setSubGroupList(subOrgUnitList);
	}

	/*
	 * (non-Javadoc)
	 * @see com.miracom.bpms.security.core.AbstractHierarchicalGroup#checkSubGroupRule(com.miracom.bpms.security.core.IGroup)
	 */
	protected void checkSubGroupRule(IGroup subGroup) throws HierarchyRuleViolationException {
		super.checkSubGroupRule(subGroup);
		// OrgUnit의 서브 그룹은 항상 OrgUnit이어야 한다.
		if (!GroupUtils.isOrgUnit(subGroup.getGroupType())) {
			throw new HierarchyRuleViolationException(subGroup
					+ " is not OrgUnit. Any sub-group of OrgUnit must be OrgUnit.");
		}
		// Company 타입은 항상 루트여야 한다.
		if (GroupUtils.isOrgCompany(subGroup.getGroupType())) {
			throw new HierarchyRuleViolationException(subGroup
					+ " is Company. Company must not be sub-group of any group.");
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.miracom.bpms.security.core.Group#checkParentRule(com.miracom.bpms.security.core.IGroup)
	 */
	protected void checkParentRule(IGroup parent) throws HierarchyRuleViolationException {
		super.checkParentRule(parent);
		if (GroupUtils.isOrgCompany(this.getGroupType()) && parent != null) {
			throw new HierarchyRuleViolationException(this + " is Company. Company must not have parent.");
		}
		if (parent != null && !GroupUtils.isOrgUnit(parent.getGroupType())) {
			throw new HierarchyRuleViolationException(parent + " is not OrgUnit. Parent of OrgUnit must be OrgUnit.");
		}
	}
}
