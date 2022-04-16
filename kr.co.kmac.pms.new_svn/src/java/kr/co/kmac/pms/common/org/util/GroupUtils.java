/*
 * $Id: GroupUtils.java,v 1.1 2009/09/19 11:15:40 cvs3 Exp $
 * created by    : 최인호
 * creation-date : 2005. 10. 24.
 * =========================================================
 * Copyright (c) 2005 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.common.org.util;

import java.util.ArrayList;

import kr.co.kmac.pms.common.org.data.Group;
import kr.co.kmac.pms.common.org.data.IGroup;
import kr.co.kmac.pms.common.org.data.IGroupConstants;
import kr.co.kmac.pms.common.org.data.IOrgUnit;
import kr.co.kmac.pms.common.org.data.IUserGroup;
import kr.co.kmac.pms.common.org.data.OrgUnit;
import kr.co.kmac.pms.common.org.data.UserGroup;

/**
 * 그룹 모델 유틸리티 클래스
 * @author 최인호
 * @version $Id: GroupUtils.java,v 1.1 2009/09/19 11:15:40 cvs3 Exp $
 */
public class GroupUtils implements IGroupConstants {
	public static boolean isGeneral(int groupType) {
		assert groupType < TYPE_MAX_VALUE : "GroupType must be less than TYPE_MAX_VALUE=" + TYPE_MAX_VALUE;
		return groupType >= 0;
	}
	public static boolean isUserGroup(int groupType) {
		return groupType >= GROUP_USER_GROUP && groupType < GROUP_USER_GROUP + LEVEL1_STEP;
	}
	public static boolean isOrgUnit(int groupType) {
		return groupType >= GROUP_ORG_UNIT && groupType < GROUP_ORG_UNIT + LEVEL1_STEP;
	}
	public static boolean isOrgCompany(int groupType) {
		return groupType >= GROUP_ORG_COMPANY && groupType < GROUP_ORG_COMPANY + LEVEL2_STEP;
	}
	public static boolean isOrgDepartment(int groupType) {
		return groupType >= GROUP_ORG_DEPARTMENT && groupType < GROUP_ORG_DEPARTMENT + LEVEL2_STEP;
	}
	public static boolean isOrgBranch(int groupType) {
		return groupType >= GROUP_ORG_BRANCH && groupType < GROUP_ORG_BRANCH + LEVEL2_STEP;
	}
	public static boolean isOrgCooperation(int groupType) {
		return groupType >= GROUP_ORG_COORPERATION && groupType < GROUP_ORG_COORPERATION + LEVEL2_STEP;
	}
	public static boolean isOrgTemporary(int groupType) {
		return groupType >= GROUP_ORG_TEMPORARY && groupType < GROUP_ORG_TEMPORARY + LEVEL2_STEP;
	}
	
	/**
	 * TODO 메소드 설명
	 * @param groupType
	 * @return
	 */
	public static int getLevel(int groupType) {
		assert groupType < TYPE_MAX_VALUE : "GroupType must be less than TYPE_MAX_VALUE=" + TYPE_MAX_VALUE;
		if ((groupType % LEVEL1_STEP) == 0) {
			return 1;
		} else if ((groupType % LEVEL2_STEP) == 0) {
			return 2;
		} else if ((groupType % LEVEL3_STEP) == 0) {
			return 3;
		} else {
			return 4;
		}
	}
	/**
	 * TODO 메소드 설명
	 * @param level
	 * @return
	 */
	public static int getLevelStep(int groupType) {
		assert groupType < TYPE_MAX_VALUE : "GroupType must be less than TYPE_MAX_VALUE=" + TYPE_MAX_VALUE;
		if ((groupType % LEVEL1_STEP) == 0) {
			return LEVEL1_STEP;
		} else if ((groupType % LEVEL2_STEP) == 0) {
			return LEVEL2_STEP;
		} else if ((groupType % LEVEL3_STEP) == 0) {
			return LEVEL3_STEP;
		} else {
			return LEVEL4_STEP;
		}
	}
	/**
	 * 부모와 자식 정보를 이용하여 자식의 패스를 생성한다.
	 * @param parent
	 * @return
	 */
	public static String generatePath(IGroup parent, IGroup child) {
		String path = PATH_SEPARATOR;
		if (parent != null) {
			path = parent.getPath() + PATH_SEPARATOR;
		}
		path += child.getId();
		return path;
	}
	/**
	 * groupType에 따라 그룹 인스턴스를 생성한다.
	 * @param id
	 * @param groupType
	 * @return
	 */
	public static IGroup createGroup(String id, String name) {
		return new Group(id, name);
	}
	/**
	 * groupType에 따라 그룹 인스턴스를 생성한다.
	 * @param id
	 * @param name
	 * @param groupType
	 * @return
	 */
	public static IGroup createGroup(String id, String name, int groupType) {
		Group group = null;
		if (isUserGroup(groupType)) {
			group = new UserGroup(id, name);
		} else if (isOrgUnit(groupType)) {
			group = new OrgUnit(id, name);
		} else if (isGeneral(groupType)) {
			group = new Group(id, name);
		} else {
			throw new IllegalArgumentException("GroupType(=" + groupType + ") is not supported.");
		}
		return group;
	}
	/**
	 * 사용자그룹을 생성한다.
	 * @param id
	 * @param name
	 * @return
	 */
	public static IUserGroup createUserGroup(String id, String name) {
		return new UserGroup(id, name);
	}
	/**
	 * 조직 유닛을 생성한다.
	 * @param id
	 * @param name
	 * @return
	 */
	public static IOrgUnit createOrgUnit(String id, String name) {
		return new OrgUnit(id, name);
	}
	/**
	 * 조직 유닛을 생성한다.
	 * @param id
	 * @param name
	 * @param orgUnitType isOrgUnit(orgUnitType)이 true이어야 한다.
	 * @return
	 */
	public static IOrgUnit createOrgUnit(String id, String name, int orgUnitType) {
		if (!isOrgUnit(orgUnitType)) {
			throw new IllegalArgumentException("GroupType(=" + orgUnitType + ") must be orgUnit type.");
		}
		OrgUnit orgUnit = new OrgUnit(id, name);
		return orgUnit;
	}

	/**
	 * 그룹을 복제한다. deep이 false인 경우 그룹의 멤버, 프로파일, 역할은 복사하지 않는다.
	 * @param source
	 * @return
	 */
	public static IGroup clone(IGroup source, boolean deep) {
		assert source != null : "Source should not be null";

		Group clone = (Group) createGroup(source.getId(), source.getName(), source.getGroupType());
		clone.setParent(source.getParent());
		clone.setParentId(source.getParentId());
		clone.setMemberRule(source.getMemberRule());
		clone.setPath(source.getPath());
		clone.setDepth(source.getDepth());
		clone.setEnabled(source.isEnabled());
		clone.setDescription(source.getDescription());

		if (deep) {
			if (source.getMemberList() != null) {
				clone.setMemberList(new ArrayList(source.getMemberList()));
			}
			if (source.getRoleAssignList() != null) {
				clone.setRoleAssignList(new ArrayList(source.getRoleAssignList()));
			}
		}

		return clone;
	}
	/**
	 * 부모 정보를 이용하여 child의 path, depth 정보를 설정한다.
	 * @param parent
	 * @param child
	 */
	public static void setParent(IGroup parent, IGroup child) {
		assert child != null : "Child should not be null.";
		((Group) child).setParent(parent);
	}
	/**
	 * 그룹을 루트 그룹으로 만든다.
	 * @param clone
	 */
	public static void makeRoot(IGroup group) {
		assert group != null : "Group should not be null.";
		setParent(null, group);
	}
	/**
	 * @return
	 */
	public static int[] getGroupTypes() {
		return GROUP_TYPES;
	}
}
