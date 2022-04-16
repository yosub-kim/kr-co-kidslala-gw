/*
 * $Id: OrgUnitManagerImpl.java,v 1.2 2010/07/07 16:27:42 cvs2 Exp $
 * created by    : 최인호
 * creation-date : 2005. 11. 4.
 * =========================================================
 * Copyright (c) 2005 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.common.org.manager.impl;

import java.util.Iterator;
import java.util.List;

import kr.co.kmac.pms.common.org.data.IGroup;
import kr.co.kmac.pms.common.org.data.IOrgUnit;
import kr.co.kmac.pms.common.org.data.OrgUnit;
import kr.co.kmac.pms.common.org.manager.IOrgUnitManager;
import kr.co.kmac.pms.common.org.util.GroupUtils;

import org.springframework.util.Assert;

/**
 * IOrgUnitManager 구현
 * @author 최인호
 * @version $Id: OrgUnitManagerImpl.java,v 1.2 2010/07/07 16:27:42 cvs2 Exp $
 */
public class OrgUnitManagerImpl extends AbstractGroupManagerImpl implements IOrgUnitManager {

	/*
	 * (non-Javadoc)
	 * @see com.miracom.bpms.security.core.IOrgUnitManager#addChild(com.miracom.bpms.security.core.IOrgUnit,
	 *      com.miracom.bpms.security.core.IOrgUnit)
	 */
	public void addChild(IOrgUnit parent, IOrgUnit child) {
		this.groupDao.addGroupMember(parent.getId(), child.getId());
		parent.addMember(child);
	}

	/*
	 * (non-Javadoc)
	 * @see com.miracom.bpms.security.core.IOrgUnitManager#containsOrgUnit(java.lang.String, java.lang.String)
	 */
	public boolean containsOrgUnit(String parentId, String orgUnitId) {
		return this.groupDao.containsGroup(parentId, orgUnitId);
	}

	/*
	 * (non-Javadoc)
	 * @see com.miracom.bpms.security.core.IOrgUnitManager#countMember(java.lang.String, boolean)
	 */
	public int countMember(String parentId, boolean inDepth) {
		return this.groupDao.countMember(parentId, inDepth);
	}

	/*
	 * (non-Javadoc)
	 * @see com.miracom.bpms.security.core.IOrgUnitManager#countOrgUnit(java.lang.String, boolean)
	 */
	public int countOrgUnit(String parentId, boolean inDepth) {
		return this.groupDao.countGroupMember(parentId, inDepth);
	}

	/*
	 * (non-Javadoc)
	 * @see com.miracom.bpms.security.core.IOrgUnitManager#countUser(java.lang.String, boolean)
	 */
	public int countUser(String parentId, boolean inDepth) {
		return this.groupDao.countUserMember(parentId, inDepth);
	}

	/*
	 * (non-Javadoc)
	 * @see com.miracom.bpms.security.core.IOrgUnitManager#findMember(java.lang.String, boolean)
	 */
	public List findMember(String parentId, boolean inDepth) {
		return this.groupDao.findMember(parentId, inDepth);
	}
	
	/* (non-Javadoc)
	 * @see kr.co.kmac.pms.common.org.manager.IOrgUnitManager#findMember(java.lang.String, boolean, boolean)
	 */
	public List findMember(String parentId, boolean inDepth, boolean isAll) {
		return this.groupDao.findMember(parentId, inDepth, isAll);
	}

	/*
	 * (non-Javadoc)
	 * @see com.miracom.bpms.security.core.IOrgUnitManager#findOrgUnit(java.lang.String, boolean)
	 */
	public List findOrgUnit(String parentId, boolean inDepth) {
		return this.groupDao.findGroupMember(parentId, inDepth);
	}

	/*
	 * (non-Javadoc)
	 * @see com.miracom.bpms.security.core.IOrgUnitManager#findOrgUnit(java.lang.String)
	 */
	public List findOrgUnit(String userId) {
		return this.groupDao.findGroupOfUser(userId, GROUP_ORG_UNIT);
	}

	/*
	 * (non-Javadoc)
	 * @see com.miracom.bpms.security.core.IOrgUnitManager#findRoot()
	 */
	public List findRoot() {
		return this.groupDao.findRootGroup(GROUP_ORG_UNIT);
	}

	/*
	 * (non-Javadoc)
	 * @see com.miracom.bpms.security.core.IOrgUnitManager#findRootByType(int)
	 */
	public List findRootByType(int orgUnitType) {
		if (!GroupUtils.isOrgUnit(orgUnitType)) {
			throw new IllegalArgumentException("Type (" + orgUnitType + ") is not a organization unit type.");
		}
		return this.groupDao.findRootGroup(orgUnitType);
	}

	/*
	 * (non-Javadoc)
	 * @see com.miracom.bpms.security.core.IOrgUnitManager#findUser(com.miracom.bpms.security.core.IOrgUnit, boolean)
	 */
	public List findUser(String parentId, boolean inDepth) {
		return this.groupDao.findUserMember(parentId, inDepth);
	}

	/*
	 * (non-Javadoc)
	 * @see com.miracom.bpms.security.core.IOrgUnitManager#hasOrgUnit(java.lang.String)
	 */
	public boolean hasOrgUnit(String parentId) {
		return this.groupDao.countGroupMember(parentId) > 0;
	}

	/* (non-Javadoc)
	 * @see com.miracom.bpms.security.core.IOrgUnitManager#move(java.lang.String, java.lang.String)
	 */
	public void move(String srcId, String destId) {
		this.groupDao.addGroupMember(destId, srcId);
	}

	/* (non-Javadoc)
	 * @see com.miracom.bpms.security.core.impl.AbstractGroupManagerImpl#populateMember(com.miracom.bpms.security.core.IGroup)
	 */
	public void populateMember(IGroup group) {
		super.populateMember(group); // populate user
		if (group instanceof OrgUnit) {
			populateOrgUnitMember((IOrgUnit) group);
		}
	}

	/* (non-Javadoc)
	 * @see com.miracom.bpms.security.core.IOrgUnitManager#getRoot(com.miracom.bpms.security.core.IOrgUnit)
	 */
	public IOrgUnit getRoot(IOrgUnit orgUnit) {
		IGroup group = this.groupDao.retrieve(orgUnit.getId());
		if (group.getDepth() > 0) {
			String path = group.getPath();
			path = path.substring(1);
			String rootId = path.substring(0, path.indexOf(PATH_SEPARATOR));
			IGroup root = this.groupDao.retrieve(rootId);
			if (!GroupUtils.isOrgUnit(root.getGroupType())) {
				throw new IllegalStateException("root of " + orgUnit.getId() + " is not an orgUnit.");
			}
			return (IOrgUnit) root;
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.miracom.bpms.security.core.IOrgUnitManager#populateOrgUnitMember(com.miracom.bpms.security.core.IOrgUnit)
	 */
	public void populateOrgUnitMember(IOrgUnit orgUnit) {
		assert orgUnit != null : "OrgUnit must not be null.";
		((OrgUnit) orgUnit).setSubGroupList(this.groupDao.findGroupMember(orgUnit.getId()));
	}

	/*
	 * (non-Javadoc)
	 * @see com.miracom.bpms.security.core.IOrgUnitManager#removeChild(com.miracom.bpms.security.core.IOrgUnit,
	 *      com.miracom.bpms.security.core.IOrgUnit)
	 */
	public void removeChild(IOrgUnit parent, IOrgUnit child) {
		this.groupDao.removeGroupMember(parent.getId(), child.getId());
		parent.removeMember(child);
	}

	/* (non-Javadoc)
	 * @see com.miracom.bpms.security.core.impl.AbstractGroupManagerImpl#removeAllMember(com.miracom.bpms.security.core.IGroup)
	 */
	public void removeAllMember(IGroup parent) {
		super.removeAllMember(parent);
		if (parent instanceof IOrgUnit) {
			removeAllGroupMember((IOrgUnit) parent);
		}
	}

	/* (non-Javadoc)
	 * @see com.miracom.bpms.security.core.IOrgUnitManager#removeAllGroupMember(com.miracom.bpms.security.core.IOrgUnit)
	 */
	public void removeAllGroupMember(IOrgUnit parent) {
		List groupList = this.groupDao.findGroupMember(parent.getId());
		for (Iterator i = groupList.iterator(); i.hasNext();) {
			this.groupDao.removeGroupMember(parent.getId(), ((IGroup) i.next()).getId());
		}
		((OrgUnit) parent).setSubOrgUnitList(null);
	}
	/* (non-Javadoc)
	 * @see com.miracom.bpms.security.core.IOrgUnitManager#retrieveParent(java.lang.String)
	 */
	public IOrgUnit retrieveParent(String orgUnitId) {
		Assert.notNull(orgUnitId);

		IGroup group = this.groupDao.retrieve(orgUnitId);
		if (group.getParentId() != null) {
			IGroup parent = this.groupDao.retrieve(group.getParentId());
			Assert.isTrue(GroupUtils.isOrgUnit(parent.getGroupType()), "Parent is not a organizational-unit");
			return (IOrgUnit) parent;
		}

		return null;
	}

}
