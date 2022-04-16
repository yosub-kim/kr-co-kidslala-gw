/*
 * $Id: IGroupDao.java,v 1.3 2017/06/01 08:37:51 cvs Exp $
 * created by    : ����ȣ
 * creation-date : 2005. 10. 13.
 * =========================================================
 * Copyright (c) 2005 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.common.org.dao;

import java.util.List;

import kr.co.kmac.pms.common.org.data.IGroup;
import kr.co.kmac.pms.common.org.data.IGroupConstants;

import org.springframework.dao.DataAccessException;

/**
 * �׷� ������ ���� ��ü
 * @author ����ȣ
 * @version $Id: IGroupDao.java,v 1.3 2017/06/01 08:37:51 cvs Exp $
 */
public interface IGroupDao extends IEntityDao, IGroupConstants {
	public boolean isRoot(String groupId) throws DataAccessException;
	public void create(IGroup group) throws DataAccessException;
	public void update(IGroup group) throws DataAccessException;
	public void remove(String groupId, boolean includeAll) throws DataAccessException;
	public IGroup retrieve(String id) throws DataAccessException;
	public IGroup retrieveByPath(String path) throws DataAccessException;
	public boolean containsUser(String parentId, String userId) throws DataAccessException;
	public boolean containsGroup(String parentId, String groupId) throws DataAccessException;

	public int count(int groupType) throws DataAccessException;
	public int count(int groupType, boolean enabled) throws DataAccessException;
	public int countMember(String parentId) throws DataAccessException;
	public int countMember(String parentId, boolean inDepth) throws DataAccessException;
	public int countUserMember(String parentId) throws DataAccessException;
	public int countUserMember(String parentId, boolean inDepth) throws DataAccessException;
	public int countGroupMember(String parentId) throws DataAccessException;
	public int countGroupMember(String parentId, boolean inDepth) throws DataAccessException;
	public int countRootGroup() throws DataAccessException;
	public int countRootGroup(int groupType) throws DataAccessException;
	public int getGroupType(String id) throws DataAccessException;
	
	public void addUserMember(String parentId, String userId) throws DataAccessException;
	/**
	 * parentId�� null�̸� groupId�� �׷��� root�� �����.
	 * @param parentId
	 * @param groupId
	 * @throws DataAccessException
	 */
	public void addGroupMember(String parentId, String groupId) throws DataAccessException;
	public void removeUserMember(String parentId, String userId) throws DataAccessException;
	/**
	 * @param parentId null�� ���� �ȵȴ�.
	 * @param groupId
	 * @throws DataAccessException
	 */
	public void removeGroupMember(String parentId, String groupId) throws DataAccessException;

	public List<Object> find(int groupType) throws DataAccessException;
	public List<Object> find(int groupType, boolean enabled) throws DataAccessException;
	public List<Object> findByName(int groupType, String name) throws DataAccessException;
	public List<Object> findByDepth(int depth);
	public List<Object> findByDepth(int groupType, int depth);
	public List<Object> findByDepth(int groupType, String startWithCode, int depth);
	public List<Object> findByEnabled(int enabled, String startWithCode, int depth);

	public List<Object> findRootGroup() throws DataAccessException;
	public List<Object> findRootGroup(int groupType) throws DataAccessException;
	/**
	 * ���̵� <code>parentId</code>�� �׷��� ��� ����� ã�´�. �ڽ� �׷쿡 ���� ����� �������� �ʴ´�. findMember(parentId, false)�� ����� ����.
	 * @param parentId
	 * @return
	 * @throws DataAccessException
	 */
	public List<Object> findMember(String parentId) throws DataAccessException;
	/**
	 * �θ� �׷��� ��� ����� ã�´�. ���� �׷��� ������� ��� ã�´�.
	 * @param parentId
	 * @param inDepth
	 * @return
	 * @throws DataAccessException
	 */
	public List<Object> findMember(String parentId, boolean inDepth) throws DataAccessException;
	public List<Object> findMember(String parentId, boolean inDepth, boolean isAll) throws DataAccessException;
	/**
	 * ���̵� <code>parentId</code>�� �׷��� ������� ã�´�. �ڽ� �׷쿡 ���� ����ڴ� �������� �ʴ´�. findUserMember(parentId, false)�� ����� ����.
	 * @param parentId
	 * @return
	 * @throws DataAccessException
	 */
	public List<Object> findUserMember(String parentId) throws DataAccessException;
	/**
	 * �θ� �׷��� ����� ����� ã�´�.
	 * @param parentId
	 * @param inDepth true�̸� ��� ���� �׷��� ����ڱ��� ã�´�.
	 * @return
	 * @throws DataAccessException
	 */
	public List<Object> findUserMember(String parentId, boolean inDepth) throws DataAccessException;
	/**
	 * ���̵� <code>parentId</code>�� �׷��� �ڽ� �׷��� ã�´�. �ڽ��� ������ ã�� �ʴ´�. findGroupMember(parentId, false)�� ����� ����.
	 * @param parentId
	 * @return
	 * @throws DataAccessException
	 */
	public List<Object> findGroupMember(String parentId) throws DataAccessException;
	/**
	 * �θ� �׷��� �׷� ����� ã�´�.
	 * @param parentId includeAll�� true�� ��� parent�� �н��� ����� �����Ǿ� �־�� �Ѵ�.
	 * @param inDepth true�̸� ��� ���� �׷��� ã�´�.
	 * @return
	 * @throws DataAccessException
	 */
	public List<Object> findGroupMember(String parentId, boolean inDepth) throws DataAccessException;
	public List<Object> findGroupMember(String parentId, boolean inDepth, boolean isAll) throws DataAccessException;
	/**
	 * user�� ���� �׷��� ã�´�.
	 * @param userId
	 * @return
	 * @throws DataAccessException
	 */
	public List<Object> findGroupOfUser(String userId) throws DataAccessException;
	/**
	 * user�� ���� �׷��� Ÿ�Ժ��� ã�´�.
	 * @param userId
	 * @param groupType
	 * @return
	 * @throws DataAccessException
	 */
	public List<Object> findGroupOfUser(String userId, int groupType) throws DataAccessException;
	/**
	 * Group�� ������ �ʴ� user ����� �����Ѵ�. groupId�� null�̸� � �׷쿡�� ������ �ʴ� user�� �����Ѵ�.
	 * @param groupId
	 * @param includeSelf true�� groupId �ڽ��� �����Ѵ�.
	 * @return
	 * @throws DataAccessException
	 */
	public List<Object> findUserNotInGroup(String groupId, boolean includeSelf) throws DataAccessException;
}
