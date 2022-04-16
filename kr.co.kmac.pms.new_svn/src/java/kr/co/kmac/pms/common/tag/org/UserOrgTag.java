/*
 * $Id: UserOrgTag.java,v 1.1 2009/09/19 11:15:30 cvs3 Exp $
 * created by    : ������
 * creation-date : 2006. 1. 5.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.common.tag.org;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import kr.co.kmac.pms.common.org.data.IGroup;
import kr.co.kmac.pms.common.org.data.IGroupConstants;
import kr.co.kmac.pms.common.org.manager.IEntityManager;
import kr.co.kmac.pms.common.org.manager.IOrgUnitManager;
import kr.co.kmac.pms.common.util.SessionUtils;

import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * ����� ���̵� ������ �μ����� �Ǵ� ȸ�������� �������� �±�
 * 
 * ����) �� �±״� �ٺ����� �ַ���� �ƴ� ��.
 * 
 * 1. BPMS�� �������� ������ �� ����ڰ� ���� �μ��� �Ҽӵ� �� �ִ� ����������, ���⼭�� �ϳ��� ���� �׷츸 ���Ѵٴ� ������ ����ȴ�.
 * 
 * 2. �μ����� �Ǵ� ȸ���̸��� ������ ���̴�.
 * 
 * ��뿹)
 * 
 * Prefix�� security, Tag�� user-org ��� �����ϸ�,
 * 
 * <security:user-org userId="B04622" groupType="2100"/> : ���̵� 'B04622'�� ������� ȸ���̸��� �����Ѵ�.
 * 
 * <security:user-org userId="B04622" groupType="2200"/> : ���̵� 'B04622'�� ������� �μ��̸��� �����Ѵ�.
 * 
 * @author ������
 * @version $Id: UserOrgTag.java,v 1.1 2009/09/19 11:15:30 cvs3 Exp $
 */
public class UserOrgTag extends TagSupport {

	private static final long serialVersionUID = -1728734306659523205L;

	private String userId = null;
	private String group = null;

	private static IOrgUnitManager orgUnitManager;
	private static IEntityManager entityManager;

	/**
	 * Getter/Setter for the attribute name as defined in the tld file for this tag
	 */
	public void setId(String userId) {
		// this.userId = userId;
		try {
			this.userId = (String) ExpressionEvaluatorManager.evaluate("id", userId, java.lang.String.class, this, pageContext);
		} catch (JspException e) {
			e.printStackTrace();
		}
	}

	public String getId() {
		return (userId);
	}

	/**
	 * @return Returns the group.
	 */
	public String getGroup() {
		return group;
	}

	/**
	 * @param group The group to set.
	 */
	public void setGroup(String group) {
		this.group = group;
	}

	/**
	 * doStartTag is called by the JSP container when the tag is encountered
	 */
	public int doStartTag() throws JspException {

		if (userId == null || userId.startsWith("loginedUser"))
			userId = SessionUtils.getUsername((HttpServletRequest) pageContext.getRequest());

		try {
			JspWriter out = pageContext.getOut();

			String value = null;

			if (group == null) {
				List orgUnits = getOrgUnitManager().findGroupOfUser(userId, IGroupConstants.GROUP_ORG_UNIT);
				if (orgUnits != null && orgUnits.size() > 0) {
					IGroup orgUnit = (IGroup) orgUnits.get(0);
					value = orgUnit.getId();
				}
			} else {
				String tempId = null;
				if (group.equals("dept") || group.equals("div")) {
					List orgUnits1 = getOrgUnitManager().findGroupOfUser(userId, IGroupConstants.GROUP_ORG_UNIT);
					if (orgUnits1 != null && orgUnits1.size() > 0) {
						IGroup orgUnit1 = (IGroup) orgUnits1.get(0);
						value = orgUnit1.getName();
						tempId = orgUnit1.getId();
					}
					if (group.equals("div")) {
						try {
							IGroup orgUnit2 = getEntityManager().retrieveGroup(tempId.substring(0, 3) + "0");
							if (orgUnit2 != null) {
								value = orgUnit2.getName().trim();
							}
						} catch (Exception e) {

						}
					}
				}
			}
			if (value != null) {
				out.print(value);
			}
		} catch (Exception e) {
			e.printStackTrace(); // TODO LOG ó��
			// throw new JspException(e);
		}

		return SKIP_BODY;
	}

	/**
	 * @return Returns the orgUnitManager.
	 */
	public IOrgUnitManager getOrgUnitManager() {
		if (orgUnitManager == null) {
			WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(pageContext.getServletContext());
			if (wac != null) {
				orgUnitManager = (IOrgUnitManager) wac.getBean("orgUnitManager");
			}
		}
		return orgUnitManager;
	}

	/**
	 * @param orgUnitManager The orgUnitManager to set.
	 */
	public void setOrgUnitManager(IOrgUnitManager orgUnitManager) {
		UserOrgTag.orgUnitManager = orgUnitManager;
	}

	/**
	 * @return Returns the entityManager.
	 */
	public IEntityManager getEntityManager() {
		if (entityManager == null) {
			WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(pageContext.getServletContext());
			if (wac != null) {
				entityManager = (IEntityManager) wac.getBean("entityManager");
			}
		}
		return entityManager;
	}

	/**
	 * @param entityManager The entityManager to set.
	 */
	public void setEntityManager(IEntityManager entityManager) {
		UserOrgTag.entityManager = entityManager;
	}
}
