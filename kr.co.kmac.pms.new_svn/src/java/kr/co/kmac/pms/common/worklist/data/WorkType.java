/*
 * $Id: WorkType.java,v 1.2 2013/01/18 08:56:34 cvs Exp $
 * created by    : jiwoong
 * creation-date : 2008. 9. 17.
 * =========================================================
 * Copyright (c) 2008 Maninsoft, Inc. All rights reserved.
 */
package kr.co.kmac.pms.common.worklist.data;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 결재 업무의 업무 단위
 * 
 * @author jiwoong
 * @version $Id: WorkType.java,v 1.2 2013/01/18 08:56:34 cvs Exp $
 */
public class WorkType {

	/**
	 * work primary key
	 */
	private String id;
	/**
	 * 업무명
	 */
	private String name;
	private String formUrl;
	private String description;
	private String useMobile;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return the formUrl
	 */
	public String getFormUrl() {
		return formUrl;
	}

	/**
	 * @param formUrl the formUrl to set
	 */
	public void setFormUrl(String formUrl) {
		this.formUrl = formUrl;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the useMobile
	 */
	public String getUseMobile() {
		return useMobile;
	}

	/**
	 * @param useMobile the useMobile to set
	 */
	public void setUseMobile(String useMobile) {
		this.useMobile = useMobile;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}