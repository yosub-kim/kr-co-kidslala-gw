/*
 * $Id: UserGroup.java,v 1.1 2009/09/19 11:15:25 cvs3 Exp $
 * created by    : ����ȣ
 * creation-date : 2005. 10. 21.
 * =========================================================
 * Copyright (c) 2005 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.common.org.data;


/**
 * User�� �ڽ����� ���� �׷� ��. �ڽ��� ������ �� rule�� ����� �� �ִ�. rule�� ���� �ڼ��� ���Ǵ� GroupManager�� �����Ѵ�.
 * @author ����ȣ
 * @version $Id: UserGroup.java,v 1.1 2009/09/19 11:15:25 cvs3 Exp $
 */
public class UserGroup extends Group implements IUserGroup {
	/**
	 * generated serialVersionUID
	 */
	private static final long serialVersionUID = 2327423878850281291L;

	public UserGroup(String id, String name) {
		super(id, name);
		setGroupType(GROUP_USER_GROUP);
	}
	public UserGroup(String id) {
		super(id);
		setGroupType(GROUP_USER_GROUP);
	}
	/*
	 * (non-Javadoc)
	 * @see com.miracom.bpms.security.core.IGroup#isHierarchical()
	 */
	public boolean isHierarchical() {
		return false;
	}
}
