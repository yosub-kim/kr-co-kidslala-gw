/*
 * $Id: IGroupManager.java,v 1.1 2009/09/19 11:15:34 cvs3 Exp $
 * created by    : 최인호
 * creation-date : 2005. 10. 21.
 * =========================================================
 * Copyright (c) 2005 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.common.org.manager;

import java.util.List;

import kr.co.kmac.pms.common.org.data.IGroup;
import kr.co.kmac.pms.common.org.data.IGroupConstants;
import kr.co.kmac.pms.common.org.data.IUser;

/**
 * GroupManager 기본 인터페이스
 * @author 최인호
 * @version $Id: IGroupManager.java,v 1.1 2009/09/19 11:15:34 cvs3 Exp $
 */
public interface IGroupManager extends IGroupConstants {
	public boolean hasUser(String parentId);
	public boolean hasMember(String parentId);
	public boolean containsUser(String parentId, String userId);
	public int countMember(String parentId);
	public int countUser(String parentId);

	/**
	 * 그룹의 멤버를 채운다. 멤버가 그룹일 경우 그 그룹을 채우지는 않는다.
	 * @param group
	 */
	public void populateMember(IGroup group);
	public void populateUserMember(IGroup group);
	/**
	 * 사용자의 그룹을 채운다. 사용자가 속해있는 모든 그룹을 찾아 추가한다.
	 * @param user
	 */
	public void populateGroup(IUser user);
	public void addChild(IGroup parent, IUser child);
	public void removeChild(IGroup parent, IUser child);
	/**
	 * 그룹에 속한 모든 멤버를 삭제한다. 사용자, 그룹을 모두 삭제한다. 실제 entity를 삭제하지는 않고 관계만을 삭제한다.
	 * @param parent
	 */
	public void removeAllMember(IGroup parent);
	/**
	 * parent에 속한 모든 사용자 멤버를 삭제한다. 실제 사용자를 삭제하지는 않고 관계만을 삭제한다.
	 * @param parent
	 */
	public void removeAllUserMember(IGroup parent);
	public List find();
	/**
	 * parent에 속한 사용자를 찾는다.
	 * @param parent
	 * @return
	 */
	public List findUser(String parentId);
	/**
	 * 아이디가 parentId인 그룹에 속하지 않는 사용자 목록을 리턴한다. 하위 그룹까지 모두 포함하여 찾는다.
	 * @param parentId null이면 모든 그룹에 속하지 않는 사용자 목록을 리턴한다.
	 * @param includeSelf true면 parentId 그룹 자신을 포함한다.
	 * @return
	 */
	public List findUserNotInGroup(String parentId, boolean includeSelf);
	/**
	 * 아이디가 <code>userId</code>인 사용자가 속한 그룹을 그룹 타입별로 찾는다.
	 * @param userId
	 * @param groupType GROUP_GENERAL이면 사용자가 속한 모든 그룹을 찾는다. 아니면 각 그룹 타입에 대해 찾는다.
	 * @return
	 */
	public List findGroupOfUser(String userId, int groupType);
}
