/*
 * $Id: IGroupDao.java,v 1.3 2017/06/01 08:37:51 cvs Exp $
 * created by    : 최인호
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
 * 그룹 데이터 접근 객체
 * @author 최인호
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
	 * parentId가 null이면 groupId인 그룹을 root로 만든다.
	 * @param parentId
	 * @param groupId
	 * @throws DataAccessException
	 */
	public void addGroupMember(String parentId, String groupId) throws DataAccessException;
	public void removeUserMember(String parentId, String userId) throws DataAccessException;
	/**
	 * @param parentId null이 오면 안된다.
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
	 * 아이디가 <code>parentId</code>인 그룹의 모든 멤버를 찾는다. 자식 그룹에 속한 멤버는 포함하지 않는다. findMember(parentId, false)와 결과가 같다.
	 * @param parentId
	 * @return
	 * @throws DataAccessException
	 */
	public List<Object> findMember(String parentId) throws DataAccessException;
	/**
	 * 부모 그룹의 모든 멤버를 찾는다. 하위 그룹의 멤버까지 모두 찾는다.
	 * @param parentId
	 * @param inDepth
	 * @return
	 * @throws DataAccessException
	 */
	public List<Object> findMember(String parentId, boolean inDepth) throws DataAccessException;
	public List<Object> findMember(String parentId, boolean inDepth, boolean isAll) throws DataAccessException;
	/**
	 * 아이디가 <code>parentId</code>인 그룹의 사용자을 찾는다. 자식 그룹에 속한 사용자는 포함하지 않는다. findUserMember(parentId, false)와 결과가 같다.
	 * @param parentId
	 * @return
	 * @throws DataAccessException
	 */
	public List<Object> findUserMember(String parentId) throws DataAccessException;
	/**
	 * 부모 그룹의 사용자 멤버를 찾는다.
	 * @param parentId
	 * @param inDepth true이면 모든 하위 그룹의 사용자까지 찾는다.
	 * @return
	 * @throws DataAccessException
	 */
	public List<Object> findUserMember(String parentId, boolean inDepth) throws DataAccessException;
	/**
	 * 아이디가 <code>parentId</code>인 그룹의 자식 그룹을 찾는다. 자식의 하위는 찾지 않는다. findGroupMember(parentId, false)와 결과가 같다.
	 * @param parentId
	 * @return
	 * @throws DataAccessException
	 */
	public List<Object> findGroupMember(String parentId) throws DataAccessException;
	/**
	 * 부모 그룹의 그룹 멤버를 찾는다.
	 * @param parentId includeAll이 true인 경우 parent의 패스가 제대로 설정되어 있어야 한다.
	 * @param inDepth true이면 모든 하위 그룹을 찾는다.
	 * @return
	 * @throws DataAccessException
	 */
	public List<Object> findGroupMember(String parentId, boolean inDepth) throws DataAccessException;
	public List<Object> findGroupMember(String parentId, boolean inDepth, boolean isAll) throws DataAccessException;
	/**
	 * user가 속한 그룹을 찾는다.
	 * @param userId
	 * @return
	 * @throws DataAccessException
	 */
	public List<Object> findGroupOfUser(String userId) throws DataAccessException;
	/**
	 * user가 속한 그룹을 타입별로 찾는다.
	 * @param userId
	 * @param groupType
	 * @return
	 * @throws DataAccessException
	 */
	public List<Object> findGroupOfUser(String userId, int groupType) throws DataAccessException;
	/**
	 * Group에 속하지 않는 user 목록을 리턴한다. groupId가 null이면 어떤 그룹에도 속하지 않는 user를 리턴한다.
	 * @param groupId
	 * @param includeSelf true면 groupId 자신을 포함한다.
	 * @return
	 * @throws DataAccessException
	 */
	public List<Object> findUserNotInGroup(String groupId, boolean includeSelf) throws DataAccessException;
}
