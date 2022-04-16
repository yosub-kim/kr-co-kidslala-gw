/*
 * $Id: IGroupManager.java,v 1.1 2009/09/19 11:15:34 cvs3 Exp $
 * created by    : ����ȣ
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
 * GroupManager �⺻ �������̽�
 * @author ����ȣ
 * @version $Id: IGroupManager.java,v 1.1 2009/09/19 11:15:34 cvs3 Exp $
 */
public interface IGroupManager extends IGroupConstants {
	public boolean hasUser(String parentId);
	public boolean hasMember(String parentId);
	public boolean containsUser(String parentId, String userId);
	public int countMember(String parentId);
	public int countUser(String parentId);

	/**
	 * �׷��� ����� ä���. ����� �׷��� ��� �� �׷��� ä������ �ʴ´�.
	 * @param group
	 */
	public void populateMember(IGroup group);
	public void populateUserMember(IGroup group);
	/**
	 * ������� �׷��� ä���. ����ڰ� �����ִ� ��� �׷��� ã�� �߰��Ѵ�.
	 * @param user
	 */
	public void populateGroup(IUser user);
	public void addChild(IGroup parent, IUser child);
	public void removeChild(IGroup parent, IUser child);
	/**
	 * �׷쿡 ���� ��� ����� �����Ѵ�. �����, �׷��� ��� �����Ѵ�. ���� entity�� ���������� �ʰ� ���踸�� �����Ѵ�.
	 * @param parent
	 */
	public void removeAllMember(IGroup parent);
	/**
	 * parent�� ���� ��� ����� ����� �����Ѵ�. ���� ����ڸ� ���������� �ʰ� ���踸�� �����Ѵ�.
	 * @param parent
	 */
	public void removeAllUserMember(IGroup parent);
	public List find();
	/**
	 * parent�� ���� ����ڸ� ã�´�.
	 * @param parent
	 * @return
	 */
	public List findUser(String parentId);
	/**
	 * ���̵� parentId�� �׷쿡 ������ �ʴ� ����� ����� �����Ѵ�. ���� �׷���� ��� �����Ͽ� ã�´�.
	 * @param parentId null�̸� ��� �׷쿡 ������ �ʴ� ����� ����� �����Ѵ�.
	 * @param includeSelf true�� parentId �׷� �ڽ��� �����Ѵ�.
	 * @return
	 */
	public List findUserNotInGroup(String parentId, boolean includeSelf);
	/**
	 * ���̵� <code>userId</code>�� ����ڰ� ���� �׷��� �׷� Ÿ�Ժ��� ã�´�.
	 * @param userId
	 * @param groupType GROUP_GENERAL�̸� ����ڰ� ���� ��� �׷��� ã�´�. �ƴϸ� �� �׷� Ÿ�Կ� ���� ã�´�.
	 * @return
	 */
	public List findGroupOfUser(String userId, int groupType);
}
