/*
 * $Id: IPrincipalRoleDao.java,v 1.1 2009/09/19 11:15:30 cvs3 Exp $
 * created by    : 최인호
 * creation-date : 2005. 10. 26.
 * =========================================================
 * Copyright (c) 2005 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.common.org.dao;

import java.util.List;

import kr.co.kmac.pms.common.org.data.IRoleAssign;

import org.springframework.dao.DataAccessException;

/**
 * Principal Role DAO 공통 인터페이스
 * @author 최인호
 * @version $Id: IPrincipalRoleDao.java,v 1.1 2009/09/19 11:15:30 cvs3 Exp $
 */
public interface IPrincipalRoleDao extends ICommonDao {
	public boolean principalRoleExists(String principalId, String roleId);
	public void create(IRoleAssign roleAssign) throws DataAccessException;
	public void update(IRoleAssign roleAssign) throws DataAccessException;
	public void remove(IRoleAssign roleAssign) throws DataAccessException;
	public void remove(String principalId, String roleId) throws DataAccessException;
	public void removeAllByPrincipalId(String principalId) throws DataAccessException;
	public void removeAllByRoleId(String roleId) throws DataAccessException;

	public IRoleAssign retrieve(String principalId, String roleId) throws DataAccessException;
	public int count() throws DataAccessException;
	public int countByRoleId(String roleId) throws DataAccessException;

	/**
	 * PrincipalId의 RoleAssign을 찾는다.
	 * @param principalId
	 * @return
	 * @throws DataAccessException
	 */
	public List findByPrincipalId(String principalId) throws DataAccessException;
	/**
	 * RoleId로 RoleAssign을 찾는다.
	 * @param roleId
	 * @return
	 * @throws DataAccessException
	 */
	public List findByRoleId(String roleId) throws DataAccessException;
	/**
	 * roleId가 할당된 principal을 찾는다. checkInheritance가 true이면 상속에 의한 역할까지 찾는다. Group이 disable 되어 있으면 제외된다.
	 * @param roleId
	 * @param checkInheritance
	 * @return
	 * @throws DataAccessException
	 */
	public List findPrincipalByRoleId(String roleId, boolean checkInheritance) throws DataAccessException;
	/**
	 * principalId로 Role을 찾는다. checkInheritance가 true이면 상속에 의한 역할까지 찾는다. Group이 disable 되어 있으면 제외된다.
	 * @param principalId
	 * @param checkInheritance
	 * @return
	 * @throws DataAccessException
	 */
	public List findRoleByPrincipalId(String principalId, boolean checkInheritance) throws DataAccessException;
	/**
	 * 아이디가 <code>principalId</code>인 Principal이 roleId를 갖는가를 검사한다. hasRole(principalId, roleId, false)와 같다.
	 * @param principalId
	 * @param roleId
	 * @return
	 * @throws DataAccessException
	 */
	public boolean hasRole(String principalId, String roleId) throws DataAccessException;
	/**
	 * 아이디가 <code>principalId</code>인 Principal이 roleId를 갖는가를 검사한다. checkInheritance가 true이면 상속에 의한 역할 까지 찾는다.
	 * Principal이 disable 되어 있으면 제외된다.
	 * @param principalId
	 * @param roleId
	 * @param checkInheritance
	 * @return
	 */
	public boolean hasRole(String principalId, String roleId, boolean checkInheritance) throws DataAccessException;
}
