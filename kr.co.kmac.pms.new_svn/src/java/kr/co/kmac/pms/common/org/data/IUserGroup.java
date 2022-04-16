/*
 * $Id: IUserGroup.java,v 1.1 2009/09/19 11:15:25 cvs3 Exp $
 * created by    : ����ȣ
 * creation-date : 2005. 10. 24.
 * =========================================================
 * Copyright (c) 2005 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.common.org.data;

import java.util.List;

/**
 * UserGroup �������̽�. UserGroup�� �׷��� ���̾��Ű�� ���� User���� ���� �� �ִ� �׷��̴�.
 * 
 * @author ����ȣ
 * @version $Id: IUserGroup.java,v 1.1 2009/09/19 11:15:25 cvs3 Exp $
 */
public interface IUserGroup extends IGroup {
	public List getUserList();

	public void addUser(IUser user);

	public void removeUser(IUser user);
}
