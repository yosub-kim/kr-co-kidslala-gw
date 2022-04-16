/*
 * $Id: IRoleAssign.java,v 1.1 2009/09/19 11:15:25 cvs3 Exp $
 * created by    : ����ȣ
 * creation-date : 2005. 10. 24.
 * =========================================================
 * Copyright (c) 2005 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.common.org.data;

import java.io.Serializable;
import java.util.Date;

/**
 * ���� �Ҵ� �� �������̽�. ���� �Ҵ��� ���� ���̵�, Principal ���̵�, �Ҵ���, �Ҵ��� ������ ���´�.
 * @author ����ȣ
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
	 * ���� �Ҵ��� ��� ����. principal�� ����ȭ�� ��쿡�� �ǹ̸� ���´�.
	 * @return
	 */
	public int getInheritance();
	/**
	 * ���� �Ҵ��� ��� ���̸� �����Ѵ�. principal�� ����ȭ�� ��쿡�� �ǹ̸� ���´�.
	 * @param inheritance
	 */
	public void setInheritance(int inheritance);
}
