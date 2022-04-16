/*
 * $Id: IUserGroupManager.java,v 1.1 2009/09/19 11:15:34 cvs3 Exp $
 * created by    : ����ȣ
 * creation-date : 2005. 10. 24.
 * =========================================================
 * Copyright (c) 2005 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.common.org.manager;

import java.util.List;

/**
 * ����� �׷��� ����� �߰�/���� ������
 * @author ����ȣ
 * @version $Id: IUserGroupManager.java,v 1.1 2009/09/19 11:15:34 cvs3 Exp $
 */
public interface IUserGroupManager extends IGroupManager {
	/**
	 * ���̵� <code>userId</code>�� ����ڰ� ���� ����ڱ׷��� ã�´�.
	 * @param userId
	 * @return
	 */
	public List findUserGroup(String userId);
}
