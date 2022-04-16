/*
 * $Id: UserOrgTag.java,v 1.1 2009/09/19 11:15:30 cvs3 Exp $
 * created by    : 남상혁
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
 * 사용자 아이디를 가지고 부서정보 또는 회사정보를 가져오는 태그
 * 
 * 주의) 이 태그는 근본적인 솔루션은 아닌 듯.
 * 
 * 1. BPMS의 조직정보 구조는 한 사용자가 여러 부서에 소속될 수 있는 구조이지만, 여기서는 하나의 조직 그룹만 속한다는 전제가 적용된다.
 * 
 * 2. 부서정보 또는 회사이름을 가져올 것이다.
 * 
 * 사용예)
 * 
 * Prefix를 security, Tag를 user-org 라고 가정하면,
 * 
 * <security:user-org userId="B04622" groupType="2100"/> : 아이디가 'B04622'인 사용자의 회사이름을 리턴한다.
 * 
 * <security:user-org userId="B04622" groupType="2200"/> : 아이디가 'B04622'인 사용자의 부서이름을 리턴한다.
 * 
 * @author 남상혁
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
			e.printStackTrace(); // TODO LOG 처리
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
