/*
 * $Id: IUser.java,v 1.1 2009/09/19 11:15:25 cvs3 Exp $
 * created by    : ����ȣ
 * creation-date : 2005. 10. 24.
 * =========================================================
 * Copyright (c) 2005 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.common.org.data;

import java.util.List;


/**
 * User �� �������̽�. User ���� ��ȣ, �̸���, User�� ���� Group�� ���� ������ ���´�.
 * @author ����ȣ
 * @version $Id: IUser.java,v 1.1 2009/09/19 11:15:25 cvs3 Exp $
 */
public interface IUser extends IPrincipal {
	public String getPassword();
	public void setPassword(String password);
	public String getEmail();
	public void setEmail(String email);
	public String getPosition();
	public void setPosition(String position);
	public String getSsn();
	public void setSsn(String ssn);
	public List getGroupList();
	/**
	 * groupType �� ���ϴ� ��� �׷��� �����Ѵ�.
	 * @param groupType
	 * @return
	 */
	public List getGroupList(int groupType);
	public boolean belongsToGroup(IGroup group);
}
