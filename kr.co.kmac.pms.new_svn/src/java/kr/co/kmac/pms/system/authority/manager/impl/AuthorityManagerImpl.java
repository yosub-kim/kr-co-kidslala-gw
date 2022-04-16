/*
 * $Id: AuthorityManagerImpl.java,v 1.1 2009/09/19 11:15:30 cvs3 Exp $
 * created by    : ¾È¼ºÈ£
 * creation-date : 2006. 5. 1.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.system.authority.manager.impl;

import java.util.ArrayList;
import java.util.List;

import kr.co.kmac.pms.system.authority.dao.AuthorityDao;
import kr.co.kmac.pms.system.authority.data.MenuData;
import kr.co.kmac.pms.system.authority.data.NodeData;
import kr.co.kmac.pms.system.authority.data.RoleData;
import kr.co.kmac.pms.system.authority.manager.AuthorityManager;
import kr.co.kmac.pms.system.exception.SystemException;

public class AuthorityManagerImpl implements AuthorityManager {
	private AuthorityDao authorityDao;

	public AuthorityDao getAuthorityDao() {
		return authorityDao;
	}

	public void setAuthorityDao(AuthorityDao authorityDao) {
		this.authorityDao = authorityDao;
	}
	
	public List menuSelect(String menuNum) throws SystemException {
		return authorityDao.menuSelect(menuNum);
	}
	
	public List menuRetrieve(String menuNum, String menuName, String menuPath, String menuType, String menuUseInfo, String menuSort) throws SystemException {
		return authorityDao.menuRetrieve(menuNum, menuName, menuPath, menuType, menuUseInfo, menuSort);
	}


	
	public void menuCreate(MenuData menuData) throws SystemException {
		authorityDao.menuCreate(menuData);
	}

	public void menuStore(MenuData menuData) throws SystemException {
		authorityDao.menuStore(menuData);
	}

	public void menuRemove(String menuNum) throws SystemException {
		authorityDao.menuRemove(menuNum);
	}

	public List roleRetrieve(String roleNum, String roleName, String roleUseInfo) throws SystemException {
		return authorityDao.roleRetrieve(roleNum, roleName, roleUseInfo);
	}

	public void roleCreate(RoleData roleData) throws SystemException {
		authorityDao.roleCreate(roleData);
	}

	public void roleStore(RoleData roleData) throws SystemException {
		authorityDao.roleStore(roleData);
	}

	public void roleRemove(String roleNum) throws SystemException {
		authorityDao.roleRemove(roleNum);
	}

	public List<NodeData[]> roleDetailRetrieve(int depth, String roleNum, String pNodeKey) throws SystemException {
		
		//NodeData[] nodeData = (NodeData[]) authorityDao.roleDetailRetrieve(depth, roleNum, pNodeKey);
		ArrayList nodeData = authorityDao.roleDetailRetrieve(depth, roleNum, pNodeKey);
		for(int i = 0; i < nodeData.size(); i++){
			NodeData nod = new NodeData();
			nod = (NodeData) nodeData.get(i);
			List childNodes = this.roleDetailRetrieve(depth + 1, roleNum, nod.getId());
			if(childNodes.size() > 0){
				nod.setLeaf(false);
				nod.setChildren(childNodes);
				nodeData.set(i,nod);
			}
		}
		
		return nodeData;
	}
	
	public List<NodeData[]> roleDetailRetrieve(int depth, String roleNum, String pNodeKey, String ssn) throws SystemException {
		
		//NodeData[] nodeData = (NodeData[]) authorityDao.roleDetailRetrieve(depth, roleNum, pNodeKey);
		ArrayList nodeData = authorityDao.roleDetailRetrieve(depth, roleNum, pNodeKey, ssn);
		for(int i = 0; i < nodeData.size(); i++){
			NodeData nod = new NodeData();
			nod = (NodeData) nodeData.get(i);
			List childNodes = this.roleDetailRetrieve(depth + 1, roleNum, nod.getId(), ssn);
			if(childNodes.size() > 0){
				nod.setLeaf(false);
				nod.setChildren(childNodes);
				nodeData.set(i,nod);
			}
		}
		
		return nodeData;
	}
	
	public void nodeCreate(String roleNum, String pNodeKey, String menuNum) throws SystemException {
		authorityDao.nodeCreate(roleNum, pNodeKey, menuNum);
	}
	public void nodeDelete(String roleNum, String nodeKey) throws SystemException {
		ArrayList nodeData = authorityDao.roleDetailRetrieve(0, roleNum, nodeKey);
		for(int i = 0; i < nodeData.size(); i++){
			NodeData nod = new NodeData();
			nod = (NodeData) nodeData.get(i);
			List childNodes = authorityDao.roleDetailRetrieve(0, roleNum, nod.getId());
			if(childNodes.size() > 0){
				this.nodeDelete(roleNum, nod.getId());
			}
			authorityDao.nodeDelete(roleNum, nod.getId());
		}
		authorityDao.nodeDelete(roleNum, nodeKey);
	}
	
	public void nodeDeleteAll(String roleNum) throws SystemException {
		authorityDao.nodeDeleteAll(roleNum);
	}
	public void nodeCopy(String newRoleNum, String orgRoleNum) throws SystemException {
		authorityDao.nodesCopy(newRoleNum, orgRoleNum);
	}
}
