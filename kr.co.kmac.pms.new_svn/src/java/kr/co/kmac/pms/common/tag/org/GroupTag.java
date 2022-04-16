/*
 * $Id: GroupTag.java,v 1.1 2009/09/19 11:15:30 cvs3 Exp $
 * created by    : 남상혁
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
 * 조직정보를 JSP에서 가져오기 위한 태그라이브러리 중의 하나로, 그룹과 관련된 데이타를 가져올 수 있다.
 * 
 * 예를 들어, 그룹의 아이디와 프로파일을 속성으로 제공하면, 해당 아이디의 그룹의 속성 또는 프로파일을 반환한다.
 * 
 * Prefix를 security, Tag를 group 라고 가정하면,
 * 
 * <org:group id="C01"/> : 아이디가 C01인 그룹의 getName()을 리턴한다. (디폴트 속성이 'name' 이다.)
 * 
 * @author 남상혁
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
			e.printStackTrace(); // TODO LOG 처리
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
