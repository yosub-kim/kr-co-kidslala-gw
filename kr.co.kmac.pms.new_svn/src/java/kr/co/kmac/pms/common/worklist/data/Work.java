/*
 * $Id: Work.java,v 1.3 2010/08/05 00:38:33 cvs2 Exp $
 * created by    : jiwoong
 * creation-date : 2008. 9. 17.
 * =========================================================
 * Copyright (c) 2008 Maninsoft, Inc. All rights reserved.
 */
package kr.co.kmac.pms.common.worklist.data;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 결재 업무의 업무 단위
 * 
 * @author jiwoong
 * @version $Id: Work.java,v 1.3 2010/08/05 00:38:33 cvs2 Exp $
 */
public class Work {

	/**
	 * work primary key
	 */
	private String id;
	/**
	 * 업무명
	 */
	private String title;
	/**
	 * 업무 담당자 아이디
	 */
	private String assigneeId;
	/**
	 * 업무 할당자
	 */
	private String assignerId;
	/**
	 * work 작업 상태 (ready, complete, reject, terminate)
	 */
	private String state;
	/**
	 * 업무 종류
	 */
	private String type;
	/**
	 * 현재 단계
	 */
	private String level;
	/**
	 * work와 연결 된 업무의 아이디
	 */
	private String refWorkId1;
	private String refWorkId2;
	private String refWorkId3;
	/**
	 * 문서 내용
	 */
	private String document;
	/**
	 * 업무 최초 생성자 부서
	 */
	private String draftUserDept;
	private String draftUserDeptName;
	/**
	 * 업무 최초 생성자
	 */
	private String draftUserId;
	private String draftUserName;
	/**
	 * 업무 최소 생성일
	 */
	private Date draftDate;
	/**
	 * work 생성일
	 */
	private Date createDate;
	/**
	 * work 접수일
	 */
	private Date openDate;
	/**
	 * work 실행일
	 */
	private Date executeDate;
	/**
	 * 위임여부
	 */
	private String isEntrust;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAssigneeId() {
		return assigneeId;
	}

	public void setAssigneeId(String assigneeId) {
		this.assigneeId = assigneeId;
	}

	public String getAssignerId() {
		return assignerId;
	}

	public void setAssignerId(String assignerId) {
		this.assignerId = assignerId;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getRefWorkId1() {
		return refWorkId1;
	}

	public void setRefWorkId1(String refWorkId1) {
		this.refWorkId1 = refWorkId1;
	}

	public String getRefWorkId2() {
		return refWorkId2;
	}

	public void setRefWorkId2(String refWorkId2) {
		this.refWorkId2 = refWorkId2;
	}

	public String getRefWorkId3() {
		return refWorkId3;
	}

	public void setRefWorkId3(String refWorkId3) {
		this.refWorkId3 = refWorkId3;
	}

	public String getDocument() {
		return document;
	}

	public void setDocument(String document) {
		this.document = document;
	}

	public String getDraftUserDept() {
		return draftUserDept;
	}

	public void setDraftUserDept(String draftUserDept) {
		this.draftUserDept = draftUserDept;
	}

	public String getDraftUserId() {
		return draftUserId;
	}

	public void setDraftUserId(String draftUserId) {
		this.draftUserId = draftUserId;
	}

	public Date getDraftDate() {
		return draftDate;
	}

	public void setDraftDate(Date draftDate) {
		this.draftDate = draftDate;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getOpenDate() {
		return openDate;
	}

	public void setOpenDate(Date openDate) {
		this.openDate = openDate;
	}

	public Date getExecuteDate() {
		return executeDate;
	}

	public void setExecuteDate(Date executeDate) {
		this.executeDate = executeDate;
	}

	public String getDraftUserDeptName() {
		return draftUserDeptName;
	}

	public void setDraftUserDeptName(String draftUserDeptName) {
		this.draftUserDeptName = draftUserDeptName;
	}

	public String getDraftUserName() {
		return draftUserName;
	}

	public void setDraftUserName(String draftUserName) {
		this.draftUserName = draftUserName;
	}

	public String getIsEntrust() {
		return isEntrust;
	}

	public void setIsEntrust(String isEntrust) {
		this.isEntrust = isEntrust;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}