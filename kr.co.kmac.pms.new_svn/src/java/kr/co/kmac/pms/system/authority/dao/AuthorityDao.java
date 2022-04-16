/*
 * $Id: AuthorityDao.java,v 1.1 2009/09/19 11:15:40 cvs3 Exp $
 * created by    : ¾È¼ºÈ£
 * creation-date : 2006. 5. 1.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.system.authority.dao;

import java.util.ArrayList;
import java.util.List;

import kr.co.kmac.pms.system.authority.data.MenuData;
import kr.co.kmac.pms.system.authority.data.RoleData;

import org.springframework.dao.DataAccessException;

public interface AuthorityDao {
	public List menuRetrieve(String menuNum, String menuName, String menuPath, String menuType, String menuUseInfo, String menuSort) throws DataAccessException;
	public List menuSelect(String menuNum) throws DataAccessException;
	public void menuCreate(MenuData menuData) throws DataAccessException;
	public void menuStore(MenuData menuData) throws DataAccessException;
	public void menuRemove(String menuNum) throws DataAccessException;
	
	public List roleRetrieve(String roleNum, String roleName, String roleUseInfo) throws DataAccessException;
	public void roleCreate(RoleData roleData) throws DataAccessException;
	public void roleStore(RoleData roleData) throws DataAccessException;
	public void roleRemove(String roleNum) throws DataAccessException;
	
	public ArrayList roleDetailRetrieve(int depth, String roleNum, String pNodeKey) throws DataAccessException;
	public ArrayList roleDetailRetrieve(int depth, String roleNum, String pNodeKey, String ssn) throws DataAccessException;
	
	public void nodeCreate(String roleNum, String pNodeKey, String menuNum) throws DataAccessException;
	public void nodeDelete(String roleNum, String nodeKey) throws DataAccessException;
	public void nodesCopy(String newRoleNum, String orgRoleNum) throws DataAccessException;
	public void nodeDeleteAll(String roleNum) throws DataAccessException;
}
