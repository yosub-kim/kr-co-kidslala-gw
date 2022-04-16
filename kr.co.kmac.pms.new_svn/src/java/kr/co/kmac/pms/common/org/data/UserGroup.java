/*
 * $Id: UserGroup.java,v 1.1 2009/09/19 11:15:25 cvs3 Exp $
 * created by    : 최인호
 * creation-date : 2005. 10. 21.
 * =========================================================
 * Copyright (c) 2005 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.common.org.data;


/**
 * User를 자식으로 갖는 그룹 모델. 자식을 설정할 때 rule을 사용할 수 있다. rule에 대한 자세한 정의는 GroupManager를 참고한다.
 * @author 최인호
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
