/*
 * $Id: GroupTag.java,v 1.1 2009/09/19 11:15:30 cvs3 Exp $
 * created by    : ������
 * creation-date : 2006. 1. 5.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.common.tag.org;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import kr.co.kmac.pms.common.org.manager.IEntityManager;

import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * ���������� JSP���� �������� ���� �±׶��̺귯�� ���� �ϳ���, �׷�� ���õ� ����Ÿ�� ������ �� �ִ�.
 * 
 * ���� ���, �׷��� ���̵�� ���������� �Ӽ����� �����ϸ�, �ش� ���̵��� �׷��� �Ӽ� �Ǵ� ���������� ��ȯ�Ѵ�.
 * 
 * Prefix�� security, Tag�� group ��� �����ϸ�,
 * 
 * <org:group id="C01"/> : ���̵� C01�� �׷��� getName()�� �����Ѵ�. (����Ʈ �Ӽ��� 'name' �̴�.)
 * 
 * @author ������
 * @version $Id: GroupTag.java,v 1.1 2009/09/19 11:15:30 cvs3 Exp $
 */
public class GroupTag extends TagSupport {

	private static final long serialVersionUID = 8567890157891127900L;

	private String groupId = null;

	private static IEntityManager entityManager;

	/**
	 * Getter/Setter for the attribute name as defined in the tld file for this tag
	 */
	public void setId(String groupId) {
		// this.groupId = groupId;
		try {
			this.groupId = (String) ExpressionEvaluatorManager.evaluate("id", groupId, java.lang.String.class, this, pageContext);
		} catch (JspException e) {
			e.printStackTrace();
		}
	}

	public String getId() {
		return (groupId);
	}

	/**
	 * doStartTag is called by the JSP container when the tag is encountered
	 */
	public int doStartTag() throws JspException {
		if (groupId == null || groupId.equals(""))
			return SKIP_BODY;

		if (!getEntityManager().groupExists(groupId))
			return SKIP_BODY;

		try {
			JspWriter out = pageContext.getOut();
			out.print(getEntityManager().getGroupName(groupId));
		} catch (Exception e) {
			e.printStackTrace(); // TODO LOG ó��
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
		GroupTag.entityManager = entityManager;
	}
}
