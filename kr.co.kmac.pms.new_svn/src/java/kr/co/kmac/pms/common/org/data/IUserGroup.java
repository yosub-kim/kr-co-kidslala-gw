/*
 * $Id: IUserGroup.java,v 1.1 2009/09/19 11:15:25 cvs3 Exp $
 * created by    : 최인호
 * creation-date : 2005. 10. 24.
 * =========================================================
 * Copyright (c) 2005 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.common.org.data;

import java.util.List;

/**
 * UserGroup 인터페이스. UserGroup은 그룹의 하이어라키가 없고 User만을 가질 수 있는 그룹이다.
 * 
 * @author 최인호
 * @version $Id: IUserGroup.java,v 1.1 2009/09/19 11:15:25 cvs3 Exp $
 */
public interface IUserGroup extends IGroup {
	public List getUserList();

	public void addUser(IUser user);

	public void removeUser(IUser user);
}
