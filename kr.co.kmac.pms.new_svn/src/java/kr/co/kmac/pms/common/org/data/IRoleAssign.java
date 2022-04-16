/*
 * $Id: IRoleAssign.java,v 1.1 2009/09/19 11:15:25 cvs3 Exp $
 * created by    : 최인호
 * creation-date : 2005. 10. 24.
 * =========================================================
 * Copyright (c) 2005 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.common.org.data;

import java.io.Serializable;
import java.util.Date;

/**
 * 역할 할당 모델 인터페이스. 역할 할당은 역할 아이디, Principal 아이디, 할당자, 할당일 정보를 갖는다.
 * @author 최인호
 * @version $Id: IRoleAssign.java,v 1.1 2009/09/19 11:15:25 cvs3 Exp $
 */
public interface IRoleAssign extends Serializable {
	public String getRoleId();
	public void setRoleId(String roleId);
	public String getRoleName();
	public void setRoleName(String roleName);
	public String getPrincipalId();
	public void setPrincipalId(String principalId);
	public String getPrincipalName();
	public void setPrincipalName(String principalName);
	public Date getAssignDate();
	public void setAssignDate(Date assignDate);
	public String getAssignerId();
	public void setAssignerId(String assignerId);
	public String getAssignerName();
	public void setAssignerName(String assignerNamae);
	/**
	 * 역할 할당의 상속 깊이. principal이 구조화된 경우에만 의미를 갖는다.
	 * @return
	 */
	public int getInheritance();
	/**
	 * 역할 할당의 상속 깊이를 설정한다. principal이 구조화된 경우에만 의미를 갖는다.
	 * @param inheritance
	 */
	public void setInheritance(int inheritance);
}
