/*
 * $Id: IEntityManager.java,v 1.1 2009/09/19 11:15:34 cvs3 Exp $
 * created by    : ����ȣ
 * creation-date : 2005. 10. 11.
 * =========================================================
 * Copyright (c) 2005 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.common.org.manager;

import java.io.Reader;
import java.io.Writer;
import java.util.List;

import kr.co.kmac.pms.common.org.data.IEntity;
import kr.co.kmac.pms.common.org.data.IGroup;
import kr.co.kmac.pms.common.org.data.IGroupConstants;
import kr.co.kmac.pms.common.org.data.IRole;
import kr.co.kmac.pms.common.org.data.IUser;

/**
 * Entity Manager�� security service�� �ʿ��� entity�� ����, ����, ��ȸ, �˻� �� entity ������ ����Ѵ�.
 * @author ����ȣ
 * @version $Id: IEntityManager.java,v 1.1 2009/09/19 11:15:34 cvs3 Exp $
 */
public interface IEntityManager extends IGroupConstants {
	public boolean userExists(String id);
	public boolean groupExists(String id);
	public boolean roleExists(String id);

	public boolean isUserEnabled(String id);
	public boolean isGroupEnabled(String id);
	public boolean isRoleEnabled(String id);
	public int getGroupType(String id);

	public int countUser();
	public int countUser(boolean enabled);
	public int countRole();
	public int countRole(boolean enabled);
	public int countGroup();
	public int countGroup(boolean enabled);
	public int countGroup(int groupType);
	public int countGroup(int groupType, boolean enabled);

	public String getUserName(String id);
	public String getGroupName(String id);
	public String getRoleName(String id);

	public void store(IEntity entity);
	public void removeUser(String id);
	public void removeGroup(String id);
	public void removeRole(String id);

	/**
	 * ������� �н����带 �����Ѵ�. �н����尡 ���ڵ��Ǿ�� �ϴ� ��� store(IUser user)���ٴ� �� �޼ҵ带 �̿��Ѵ�. 
	 * @param id
	 * @param password
	 */
	public void storeUserPassword(String id, String password);

	public void clearAll();
	public void clearUser();
	public void clearGroup();
	public void clearRole();

	public IGroup retrieveGroup(String id);
	public IUser retrieveUser(String id);
	public IRole retrieveRole(String id);

	public List findGroup();
	public List findGroup(int groupType);
	public List findUser();
	public List findRole();
	public List findGroup(boolean enabled);
	public List findGroup(int groupType, boolean enabled);
	public List findUser(boolean enabled);
	public List findRole(boolean enabled);

	public List findUserByName(String name);
	public List findUserByEmail(String email);
	public List findUserByPosition(String position);
	public List findUserBySsn(String ssn);
	public List findGroupByName(String name);
	public List findGroupByName(int groupType, String name);
	public List findRoleByName(String name);

}
