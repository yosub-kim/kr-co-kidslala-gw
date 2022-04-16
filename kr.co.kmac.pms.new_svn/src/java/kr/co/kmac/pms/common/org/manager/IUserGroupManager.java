/*
 * $Id: IUserGroupManager.java,v 1.1 2009/09/19 11:15:34 cvs3 Exp $
 * created by    : 최인호
 * creation-date : 2005. 10. 24.
 * =========================================================
 * Copyright (c) 2005 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.common.org.manager;

import java.util.List;

/**
 * 사용자 그룹의 사용자 추가/삭제 관리자
 * @author 최인호
 * @version $Id: IUserGroupManager.java,v 1.1 2009/09/19 11:15:34 cvs3 Exp $
 */
public interface IUserGroupManager extends IGroupManager {
	/**
	 * 아이디가 <code>userId</code>인 사용자가 속한 사용자그룹을 찾는다.
	 * @param userId
	 * @return
	 */
	public List findUserGroup(String userId);
}
