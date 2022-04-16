/*
 * $Id: IPrincipal.java,v 1.1 2009/09/19 11:15:25 cvs3 Exp $
 * created by    : 최인호
 * creation-date : 2005. 10. 24.
 * =========================================================
 * Copyright (c) 2005 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.common.org.data;

import java.util.List;

/**
 * Principal 인터페이스. Principal은 Role과 프로파일을 가질 수 있는 Entity이다.
 * @author 최인호
 * @version $Id: IPrincipal.java,v 1.1 2009/09/19 11:15:25 cvs3 Exp $
 */
public interface IPrincipal extends IEntity {
	public List getRoleAssignList();
	public void setRoleAssignList(List roleAssignList);
	public List getRoleList();
	public void setRoleList(List roleList);
	public IRoleAssign getRoleAssign(String roleId);
}
