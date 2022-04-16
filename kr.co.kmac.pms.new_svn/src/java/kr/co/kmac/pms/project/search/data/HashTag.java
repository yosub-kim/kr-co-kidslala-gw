/*
 * $Id: Project.java,v 1.9 2018/05/28 06:24:22 cvs Exp $
 * created by    : jiwoongLee
 * creation-date : 2006. 3. 9.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.project.search.data;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 프로젝트 정보를 가지고 있는 model Class
 * 
 * @author jiwoongLee
 * @version $Id: Project.java,v 1.9 2018/05/28 06:24:22 cvs Exp $
 */
public class HashTag {

	private String uuid;
	private String projectCode;
	private String hashTag;
	private String isShow;//SHOW, HIDE
	private String tagType;// ByUSER, BySYSTEM
	private Date createDate;
	private String createrSsn;

	private String bbsId;
	private String seq;
	private String recCnt;
	
	private String expertSsn;
	
	public String getExpertSsn() {
		return expertSsn;
	}

	public void setExpertSsn(String expertSsn) {
		this.expertSsn = expertSsn;
	}

	public String getRecCnt() {
		return recCnt;
	}

	public void setRecCnt(String recCnt) {
		this.recCnt = recCnt;
	}

	public String getBbsId() {
		return bbsId;
	}

	public void setBbsId(String bbsId) {
		this.bbsId = bbsId;
	}

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public String getHashTag() {
		return hashTag;
	}

	public void setHashTag(String hashTag) {
		this.hashTag = hashTag;
	}

	public String getIsShow() {
		return isShow;
	}

	public void setIsShow(String isShow) {
		this.isShow = isShow;
	}

	public String getTagType() {
		return tagType;
	}

	public void setTagType(String tagType) {
		this.tagType = tagType;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCreaterSsn() {
		return createrSsn;
	}

	public void setCreaterSsn(String createrSsn) {
		this.createrSsn = createrSsn;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}
}
