/*
 * $Id: IUser.java,v 1.1 2009/09/19 11:15:25 cvs3 Exp $
 * created by    : 최인호
 * creation-date : 2005. 10. 24.
 * =========================================================
 * Copyright (c) 2005 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.common.org.data;

import java.util.List;


/**
 * User 모델 인터페이스. User 모델은 암호, 이메일, User가 속한 Group에 대한 정보를 갖는다.
 * @author 최인호
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
	 * groupType 에 속하는 모든 그룹을 리턴한다.
	 * @param groupType
	 * @return
	 */
	public List getGroupList(int groupType);
	public boolean belongsToGroup(IGroup group);
}
