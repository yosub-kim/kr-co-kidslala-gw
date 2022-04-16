/*
 * $Id: AuthorityManager.java,v 1.1 2009/09/19 11:15:41 cvs3 Exp $
 * created by    : ¾È¼ºÈ£
 * creation-date : 2006. 5. 1.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.system.authority.manager;

import java.util.List;

import kr.co.kmac.pms.system.authority.data.MenuData;
import kr.co.kmac.pms.system.authority.data.RoleData;
import kr.co.kmac.pms.system.exception.SystemException;

public interface AuthorityManager {
	public List menuSelect(String menuNum) throws SystemException;
	public List menuRetrieve(String menuNum, String menuName, String menuPath, String menuType, String menuUseInfo, String menuSort) throws SystemException;
	public void menuCreate(MenuData menuData) throws SystemException;
	public void menuStore(MenuData menuData) throws SystemException;
	public void menuRemove(String menuNum) throws SystemException;
	
	
	public List roleRetrieve(String roleNum, String roleName, String roleUseInfo) throws SystemException;
	public void roleCreate(RoleData roleData) throws SystemException;
	public void roleStore(RoleData roleData) throws SystemException;
	public void roleRemove(String roleNum) throws SystemException;
	
	public List roleDetailRetrieve(int depth, String roleNum, String pNodeKey) throws SystemException;
	public List roleDetailRetrieve(int depth, String roleNum, String pNodeKey, String ssn) throws SystemException;
	
	public void nodeCreate(String roleNum, String pNodeKey, String menuNum) throws SystemException;
	public void nodeDelete(String roleNum, String nodeKey) throws SystemException;
	
	public void nodeDeleteAll(String roleNum) throws SystemException;
	public void nodeCopy(String newRoleNum, String orgRoleNum) throws SystemException;
}
