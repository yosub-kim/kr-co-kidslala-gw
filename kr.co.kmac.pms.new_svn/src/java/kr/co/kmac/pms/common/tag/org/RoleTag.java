/*
 * $Id: RoleTag.java,v 1.1 2009/09/19 11:15:30 cvs3 Exp $
 * created by    : ������
 * creation-date : 2006. 1. 5.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.common.tag.org;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import kr.co.kmac.pms.common.org.manager.IEntityManager;

import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * ���������� JSP���� �������� ���� �±׶��̺귯�� ���� �ϳ���, ���Ұ� ���õ� ����Ÿ�� ������ �� �ִ�.
 * 
 * ���� ���, ������ ���̵�� ���������� �Ӽ����� �����ϸ�, �ش� ���̵��� ������ �Ӽ� �Ǵ� ���������� ��ȯ�Ѵ�.
 * 
 * Prefix�� security, Tag�� role ��� �����ϸ�,
 * 
 * <org:role id="clerk"/> : ���̵� 'clerk'�� ������ getName()�� �����Ѵ�. (����Ʈ �Ӽ��� 'name' �̴�.)
 * 
 * @author ������
 * @version $Id: RoleTag.java,v 1.1 2009/09/19 11:15:30 cvs3 Exp $
 */
public class RoleTag extends TagSupport {

	private static final long serialVersionUID = 2641533538961720997L;

	private String roleId = null;

	private static IEntityManager entityManager;

	/**
	 * Getter/Setter for the attribute name as defined in the tld file for this tag
	 */
	public void setId(String roleId) {
		// this.roleId = roleId;
		try {
			this.roleId = (String) ExpressionEvaluatorManager.evaluate("id", roleId, java.lang.String.class, this, pageContext);
		} catch (JspException e) {
			e.printStackTrace();
		}
	}

	public String getId() {
		return (roleId);
	}

	/**
	 * doStartTag is called by the JSP container when the tag is encountered
	 */
	public int doStartTag() throws JspException {
		if (roleId == null || roleId.equals(""))
			return SKIP_BODY;

		JspWriter out = pageContext.getOut();
		try {
			out.print(getEntityManager().getRoleName(roleId));
		} catch (Exception e) {
			// e.printStackTrace(); // TODO LOG ó��
			try {
				out.print(roleId);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			// throw new JspException(e);
		}

		return SKIP_BODY;
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
		RoleTag.entityManager = entityManager;
	}
}
