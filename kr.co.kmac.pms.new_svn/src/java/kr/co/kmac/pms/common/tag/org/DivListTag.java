/*
 * $Id: DivListTag.java,v 1.4 2017/06/01 08:37:52 cvs Exp $
 * created by    : 안성호
 * creation-date : 2006. 3. 31.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.common.tag.org;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import kr.co.kmac.pms.common.ajax.JSONWriter;
import kr.co.kmac.pms.common.org.dao.IGroupDao;
import kr.co.kmac.pms.common.org.data.Group;

import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class DivListTag extends TagSupport {
	private static final long serialVersionUID = -7652820146661824773L;
	private String depth = null;
	private String divType = null;
	private String all = null;
	private String isSelectBox = null;
	private String attribute = null;
	private String selectedInfo = null;
	private String enabled = null;

	private IGroupDao groupDao;

	public IGroupDao getGroupDao() {
		WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(pageContext.getServletContext());
		if (groupDao == null) {
			if (wc != null) {
				groupDao = (IGroupDao) wc.getBean("groupDao");
			}
		}
		return groupDao;
	}

	public void setGroupDao(IGroupDao groupDao) {
		this.groupDao = groupDao;
	}

	/**
	 * @return Returns the tableName.
	 */
	public String getDepth() {
		return this.depth;
	}

	/**
	 * @param tableName The tableName to set.
	 */
	public void setDepth(String depth) {
		try {
			this.depth = (String) ExpressionEvaluatorManager.evaluate("depth", depth, java.lang.String.class, this, pageContext);
			if (this.depth == null || this.depth.equals("")) {
				this.depth = "1";
			}
		} catch (JspException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @return Returns the tableName.
	 */
	public String getDivType() {
		return this.divType;
	}
	
	/**
	 * @param tableName The tableName to set.
	 */
	public void setDivType(String divType) {
		try {
			this.divType = (String) ExpressionEvaluatorManager.evaluate("divType", divType, java.lang.String.class, this, pageContext);
		} catch (JspException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @return the enabled
	 */
	public String getEnabled() {
		return this.enabled;
	}

	/**
	 * @param enabled the enabled to set
	 */
	public void setEnabled(String enabled) {
		try {
			this.enabled = (String) ExpressionEvaluatorManager.evaluate("enabled", enabled, java.lang.String.class, this, pageContext);
			if (this.enabled == null || this.enabled.equals("")) {
				this.enabled = "1";
			}
		} catch (JspException e) {
			e.printStackTrace();
		}		
	}

	/**
	 * @return the isSelectBox
	 */
	public String getIsSelectBox() {
		return isSelectBox;
	}

	/**
	 * @param isSelectBox the isSelectBox to set
	 */
	public void setIsSelectBox(String isSelectBox) {
		this.isSelectBox = isSelectBox;
	}

	/**
	 * @return the attribute
	 */
	public String getAttribute() {
		return attribute;
	}

	/**
	 * @param attribute the attribute to set
	 */
	public void setAttribute(String attribute) {
		try {
			this.attribute = (String) ExpressionEvaluatorManager.evaluate("attribute", attribute, java.lang.String.class, this, pageContext);
		} catch (JspException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return the selectedInfo
	 */
	public String getSelectedInfo() {
		return selectedInfo;
	}

	/**
	 * @param selectedInfo the selectedInfo to set
	 */
	public void setSelectedInfo(String selectedInfo) {
		try {
			this.selectedInfo = (String) ExpressionEvaluatorManager.evaluate("selectedInfo", selectedInfo, java.lang.String.class, this, pageContext);
		} catch (JspException e) {
			e.printStackTrace();
		}
	}

	public String getAll() {
		return all;
	}

	public void setAll(String all) {
		try {
			this.all = (String) ExpressionEvaluatorManager.evaluate("all", all, java.lang.String.class, this, pageContext);
		} catch (JspException e) {
			e.printStackTrace();
		}
	}

	/**
	 * doStartTag is called by the JSP container when the tag is encountered
	 */
	public int doStartTag() throws JspException {
		try {
			JspWriter out = pageContext.getOut();
			//List groupList = this.getGroupDao().findByDepth(0, this.divType, Integer.parseInt(this.depth));
			List groupList = this.getGroupDao().findByEnabled(Integer.parseInt(this.enabled), this.divType, Integer.parseInt(this.depth));

			if (groupList != null) {
				if ("Y".equals(this.all)) {
					List allList = new ArrayList();
					Group group = new Group("", "전체");
					group.setDescription("전체");
					allList.add(group);
					allList.addAll(groupList);
					groupList = allList;
				}
				if (isSelectBox != null && isSelectBox.equals("Y")) {
					StringBuffer sb = new StringBuffer();
					for (Object object : groupList) {
						if (selectedInfo.equals(((Group) object).getId())) {
							sb.append("<option value='" + ((Group) object).getId() + "' selected >" + ((Group) object).getDescription() + "</option>");
						} else {
							sb.append("<option value='" + ((Group) object).getId() + "'  >" + ((Group) object).getDescription() + "</option>");
						}
					}
					String html = "<SELECT " + attribute + ">" + sb.toString() + "</SELECT>";
					out.print(html);
				} else {
					JSONWriter writer = new JSONWriter();
					out.print(writer.write(groupList));
				}
			}
		} catch (Exception e) {
			e.printStackTrace(); // TODO LOG 처리
			// throw new JspException(e);
		}

		return SKIP_BODY;
	}
}
