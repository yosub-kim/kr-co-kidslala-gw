/*
 * $Id: IPrincipalRoleDao.java,v 1.1 2009/09/19 11:15:30 cvs3 Exp $
 * created by    : ����ȣ
 * creation-date : 2005. 10. 26.
 * =========================================================
 * Copyright (c) 2005 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.common.org.dao;

import java.util.List;

import kr.co.kmac.pms.common.org.data.IRoleAssign;

import org.springframework.dao.DataAccessException;

/**
 * Principal Role DAO ���� �������̽�
 * @author ����ȣ
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
	 * PrincipalId�� RoleAssign�� ã�´�.
	 * @param principalId
	 * @return
	 * @throws DataAccessException
	 */
	public List findByPrincipalId(String principalId) throws DataAccessException;
	/**
	 * RoleId�� RoleAssign�� ã�´�.
	 * @param roleId
	 * @return
	 * @throws DataAccessException
	 */
	public List findByRoleId(String roleId) throws DataAccessException;
	/**
	 * roleId�� �Ҵ�� principal�� ã�´�. checkInheritance�� true�̸� ��ӿ� ���� ���ұ��� ã�´�. Group�� disable �Ǿ� ������ ���ܵȴ�.
	 * @param roleId
	 * @param checkInheritance
	 * @return
	 * @throws DataAccessException
	 */
	public List findPrincipalByRoleId(String roleId, boolean checkInheritance) throws DataAccessException;
	/**
	 * principalId�� Role�� ã�´�. checkInheritance�� true�̸� ��ӿ� ���� ���ұ��� ã�´�. Group�� disable �Ǿ� ������ ���ܵȴ�.
	 * @param principalId
	 * @param checkInheritance
	 * @return
	 * @throws DataAccessException
	 */
	public List findRoleByPrincipalId(String principalId, boolean checkInheritance) throws DataAccessException;
	/**
	 * ���̵� <code>principalId</code>�� Principal�� roleId�� ���°��� �˻��Ѵ�. hasRole(principalId, roleId, false)�� ����.
	 * @param principalId
	 * @param roleId
	 * @return
	 * @throws DataAccessException
	 */
	public boolean hasRole(String principalId, String roleId) throws DataAccessException;
	/**
	 * ���̵� <code>principalId</code>�� Principal�� roleId�� ���°��� �˻��Ѵ�. checkInheritance�� true�̸� ��ӿ� ���� ���� ���� ã�´�.
	 * Principal�� disable �Ǿ� ������ ���ܵȴ�.
	 * @param principalId
	 * @param roleId
	 * @param checkInheritance
	 * @return
	 */
	public boolean hasRole(String principalId, String roleId, boolean checkInheritance) throws DataAccessException;
}
