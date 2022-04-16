/*
 * $Id: SanctionForm.java,v 1.4 2015/02/17 00:08:49 cvs Exp $
 * created by    : jiwoongLee
 * creation-date : 2006. 3. 25.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.sanction.general.form;

import kr.co.kmac.pms.attach.form.AttachForm;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * TODO ≈¨∑°Ω∫ º≥∏Ì
 * 
 * @author jiwoongLee
 * @version $Id: SanctionForm.java,v 1.4 2015/02/17 00:08:49 cvs Exp $
 */
/**
 * @struts:form name="generalSanctionAction"
 */
public class SanctionForm extends AttachForm {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -51566578360009983L;

	private String taskId;

	private int seq;
	private String projectCode;
	private String approvalType;
	/**
	 * ±‚æ»
	 */
	private String registerSsn;
	private String registerName;
	private String registerDept;
	private String registerDate;
	/**
	 * ∞À≈‰
	 */
	private String teamManagerSsn;
	private String teamManagerName;
	private String teamManagerDept;
	private String teamManagerDate;
	/**
	 * »Æ¿Œ
	 */
	private String cfoSsn;
	private String cfoDept;
	private String cfoName;
	private String cfoDate;
	/**
	 * Ω¬¿Œ
	 */
	private String cioSsn;
	private String cioDept;
	private String cioName;
	private String cioDate;
	/**
	 * ¥Î«•¿ÃªÁ
	 */
	private String ceoSsn;
	private String ceoName;
	private String ceoDept;
	private String ceoDate;
	/**
	 * «˘¿«1
	 */
	private String assistant1Ssn;
	private String assistant1Name;
	private String assistant1Dept;
	private String assistant1Date;
	/**
	 * «˘¿«2
	 */
	private String assistant2Ssn;
	private String assistant2Name;
	private String assistant2Dept;
	private String assistant2Date;
	/**
	 * «˘¿«3
	 */
	private String assistant3Ssn;
	private String assistant3Name;
	private String assistant3Dept;
	private String assistant3Date;
	/**
	 * «˘¿«4
	 */
	private String assistant4Ssn;
	private String assistant4Name;
	private String assistant4Dept;
	private String assistant4Date;

	private String subject;
	private String content;
	private String rejectReason;
	private String rejectReasonView;
	private String isWholeApproval;
	private String state;
	private String reject;

	private String refMemberSsn;
	private String refMemberName;

	private String entrustUserSsn;
	private String entrustUserName;

	private String isApproved;
	
	private String eduCourseCode;
	
	/**
	 * @return the taskId
	 */
	public String getTaskId() {
		return taskId;
	}

	/**
	 * @param taskId the taskId to set
	 */
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	/**
	 * @return the seq
	 */
	public int getSeq() {
		return seq;
	}

	/**
	 * @param seq the seq to set
	 */
	public void setSeq(int seq) {
		this.seq = seq;
	}

	/**
	 * @return the projectCode
	 */
	public String getProjectCode() {
		return projectCode;
	}

	/**
	 * @param projectCode the projectCode to set
	 */
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	/**
	 * @return the approvalType
	 */
	public String getApprovalType() {
		return approvalType;
	}

	/**
	 * @param approvalType the approvalType to set
	 */
	public void setApprovalType(String approvalType) {
		this.approvalType = approvalType;
	}

	/**
	 * @return the registerSsn
	 */
	public String getRegisterSsn() {
		return registerSsn;
	}

	/**
	 * @param registerSsn the registerSsn to set
	 */
	public void setRegisterSsn(String registerSsn) {
		this.registerSsn = registerSsn;
	}

	/**
	 * @return the registerName
	 */
	public String getRegisterName() {
		return registerName;
	}

	/**
	 * @param registerName the registerName to set
	 */
	public void setRegisterName(String registerName) {
		this.registerName = registerName;
	}

	/**
	 * @return the registerDate
	 */
	public String getRegisterDate() {
		return registerDate;
	}

	/**
	 * @param registerDate the registerDate to set
	 */
	public void setRegisterDate(String registerDate) {
		this.registerDate = registerDate;
	}

	/**
	 * @return the teamManagerSsn
	 */
	public String getTeamManagerSsn() {
		return teamManagerSsn;
	}

	/**
	 * @param teamManagerSsn the teamManagerSsn to set
	 */
	public void setTeamManagerSsn(String teamManagerSsn) {
		this.teamManagerSsn = teamManagerSsn;
	}

	/**
	 * @return the teamManagerName
	 */
	public String getTeamManagerName() {
		return teamManagerName;
	}

	/**
	 * @param teamManagerName the teamManagerName to set
	 */
	public void setTeamManagerName(String teamManagerName) {
		this.teamManagerName = teamManagerName;
	}

	/**
	 * @return the teamManagerDate
	 */
	public String getTeamManagerDate() {
		return teamManagerDate;
	}

	/**
	 * @param teamManagerDate the teamManagerDate to set
	 */
	public void setTeamManagerDate(String teamManagerDate) {
		this.teamManagerDate = teamManagerDate;
	}

	/**
	 * @return the cfoSsn
	 */
	public String getCfoSsn() {
		return cfoSsn;
	}

	/**
	 * @param cfoSsn the cfoSsn to set
	 */
	public void setCfoSsn(String cfoSsn) {
		this.cfoSsn = cfoSsn;
	}

	/**
	 * @return the cfoName
	 */
	public String getCfoName() {
		return cfoName;
	}

	/**
	 * @param cfoName the cfoName to set
	 */
	public void setCfoName(String cfoName) {
		this.cfoName = cfoName;
	}

	/**
	 * @return the cfoDate
	 */
	public String getCfoDate() {
		return cfoDate;
	}

	/**
	 * @param cfoDate the cfoDate to set
	 */
	public void setCfoDate(String cfoDate) {
		this.cfoDate = cfoDate;
	}

	/**
	 * @return the ceoSsn
	 */
	public String getCeoSsn() {
		return ceoSsn;
	}

	public String getCioSsn() {
		return cioSsn;
	}

	public void setCioSsn(String cioSsn) {
		this.cioSsn = cioSsn;
	}

	public String getCioDept() {
		return cioDept;
	}

	public void setCioDept(String cioDept) {
		this.cioDept = cioDept;
	}

	public String getCioName() {
		return cioName;
	}

	public void setCioName(String cioName) {
		this.cioName = cioName;
	}

	public String getCioDate() {
		return cioDate;
	}

	public void setCioDate(String cioDate) {
		this.cioDate = cioDate;
	}

	/**
	 * @param ceoSsn the ceoSsn to set
	 */
	public void setCeoSsn(String ceoSsn) {
		this.ceoSsn = ceoSsn;
	}

	/**
	 * @return the ceoName
	 */
	public String getCeoName() {
		return ceoName;
	}

	/**
	 * @param ceoName the ceoName to set
	 */
	public void setCeoName(String ceoName) {
		this.ceoName = ceoName;
	}

	/**
	 * @return the ceoDate
	 */
	public String getCeoDate() {
		return ceoDate;
	}

	/**
	 * @param ceoDate the ceoDate to set
	 */
	public void setCeoDate(String ceoDate) {
		this.ceoDate = ceoDate;
	}

	/**
	 * @return the assistant1Ssn
	 */
	public String getAssistant1Ssn() {
		return assistant1Ssn;
	}

	/**
	 * @param assistant1Ssn the assistant1Ssn to set
	 */
	public void setAssistant1Ssn(String assistant1Ssn) {
		this.assistant1Ssn = assistant1Ssn;
	}

	/**
	 * @return the assistant1Name
	 */
	public String getAssistant1Name() {
		return assistant1Name;
	}

	/**
	 * @param assistant1Name the assistant1Name to set
	 */
	public void setAssistant1Name(String assistant1Name) {
		this.assistant1Name = assistant1Name;
	}

	/**
	 * @return the assistant1Date
	 */
	public String getAssistant1Date() {
		return assistant1Date;
	}

	/**
	 * @param assistant1Date the assistant1Date to set
	 */
	public void setAssistant1Date(String assistant1Date) {
		this.assistant1Date = assistant1Date;
	}

	/**
	 * @return the assistant2Ssn
	 */
	public String getAssistant2Ssn() {
		return assistant2Ssn;
	}

	/**
	 * @param assistant2Ssn the assistant2Ssn to set
	 */
	public void setAssistant2Ssn(String assistant2Ssn) {
		this.assistant2Ssn = assistant2Ssn;
	}

	/**
	 * @return the assistant2Name
	 */
	public String getAssistant2Name() {
		return assistant2Name;
	}

	/**
	 * @param assistant2Name the assistant2Name to set
	 */
	public void setAssistant2Name(String assistant2Name) {
		this.assistant2Name = assistant2Name;
	}

	/**
	 * @return the assistant2Date
	 */
	public String getAssistant2Date() {
		return assistant2Date;
	}

	/**
	 * @param assistant2Date the assistant2Date to set
	 */
	public void setAssistant2Date(String assistant2Date) {
		this.assistant2Date = assistant2Date;
	}

	/**
	 * @return the assistant3Ssn
	 */
	public String getAssistant3Ssn() {
		return assistant3Ssn;
	}

	/**
	 * @param assistant3Ssn the assistant3Ssn to set
	 */
	public void setAssistant3Ssn(String assistant3Ssn) {
		this.assistant3Ssn = assistant3Ssn;
	}

	/**
	 * @return the assistant3Name
	 */
	public String getAssistant3Name() {
		return assistant3Name;
	}

	/**
	 * @param assistant3Name the assistant3Name to set
	 */
	public void setAssistant3Name(String assistant3Name) {
		this.assistant3Name = assistant3Name;
	}

	/**
	 * @return the assistant3Date
	 */
	public String getAssistant3Date() {
		return assistant3Date;
	}

	/**
	 * @param assistant3Date the assistant3Date to set
	 */
	public void setAssistant3Date(String assistant3Date) {
		this.assistant3Date = assistant3Date;
	}

	/**
	 * @return the assistant4Ssn
	 */
	public String getAssistant4Ssn() {
		return assistant4Ssn;
	}

	/**
	 * @param assistant4Ssn the assistant4Ssn to set
	 */
	public void setAssistant4Ssn(String assistant4Ssn) {
		this.assistant4Ssn = assistant4Ssn;
	}

	/**
	 * @return the assistant4Name
	 */
	public String getAssistant4Name() {
		return assistant4Name;
	}

	/**
	 * @param assistant4Name the assistant4Name to set
	 */
	public void setAssistant4Name(String assistant4Name) {
		this.assistant4Name = assistant4Name;
	}

	/**
	 * @return the assistant4Date
	 */
	public String getAssistant4Date() {
		return assistant4Date;
	}

	/**
	 * @param assistant4Date the assistant4Date to set
	 */
	public void setAssistant4Date(String assistant4Date) {
		this.assistant4Date = assistant4Date;
	}

	/**
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * @param subject the subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return the rejectReason
	 */
	public String getRejectReason() {
		return rejectReason;
	}

	/**
	 * @param rejectReason the rejectReason to set
	 */
	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}

	/**
	 * @return the rejectReasonView
	 */
	public String getRejectReasonView() {
		return rejectReasonView;
	}

	/**
	 * @param rejectReasonView the rejectReasonView to set
	 */
	public void setRejectReasonView(String rejectReasonView) {
		this.rejectReasonView = rejectReasonView;
	}

	/**
	 * @return the isWholeApproval
	 */
	public String getIsWholeApproval() {
		return isWholeApproval;
	}

	/**
	 * @param isWholeApproval the isWholeApproval to set
	 */
	public void setIsWholeApproval(String isWholeApproval) {
		this.isWholeApproval = isWholeApproval;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @return the reject
	 */
	public String getReject() {
		return reject;
	}

	/**
	 * @param reject the reject to set
	 */
	public void setReject(String reject) {
		this.reject = reject;
	}

	/**
	 * @return the refMemberSsn
	 */
	public String getRefMemberSsn() {
		return refMemberSsn;
	}

	/**
	 * @param refMemberSsn the refMemberSsn to set
	 */
	public void setRefMemberSsn(String refMemberSsn) {
		this.refMemberSsn = refMemberSsn;
	}

	/**
	 * @return the refMemberName
	 */
	public String getRefMemberName() {
		return refMemberName;
	}

	/**
	 * @param refMemberName the refMemberName to set
	 */
	public void setRefMemberName(String refMemberName) {
		this.refMemberName = refMemberName;
	}

	/**
	 * @return the registerDept
	 */
	public String getRegisterDept() {
		return registerDept;
	}

	/**
	 * @param registerDept the registerDept to set
	 */
	public void setRegisterDept(String registerDept) {
		this.registerDept = registerDept;
	}

	/**
	 * @return the teamManagerDept
	 */
	public String getTeamManagerDept() {
		return teamManagerDept;
	}

	/**
	 * @param teamManagerDept the teamManagerDept to set
	 */
	public void setTeamManagerDept(String teamManagerDept) {
		this.teamManagerDept = teamManagerDept;
	}

	/**
	 * @return the cfoDept
	 */
	public String getCfoDept() {
		return cfoDept;
	}

	/**
	 * @param cfoDept the cfoDept to set
	 */
	public void setCfoDept(String cfoDept) {
		this.cfoDept = cfoDept;
	}

	/**
	 * @return the ceoDept
	 */
	public String getCeoDept() {
		return ceoDept;
	}

	/**
	 * @param ceoDept the ceoDept to set
	 */
	public void setCeoDept(String ceoDept) {
		this.ceoDept = ceoDept;
	}

	/**
	 * @return the assistant1Dept
	 */
	public String getAssistant1Dept() {
		return assistant1Dept;
	}

	/**
	 * @param assistant1Dept the assistant1Dept to set
	 */
	public void setAssistant1Dept(String assistant1Dept) {
		this.assistant1Dept = assistant1Dept;
	}

	/**
	 * @return the assistant2Dept
	 */
	public String getAssistant2Dept() {
		return assistant2Dept;
	}

	/**
	 * @param assistant2Dept the assistant2Dept to set
	 */
	public void setAssistant2Dept(String assistant2Dept) {
		this.assistant2Dept = assistant2Dept;
	}

	/**
	 * @return the assistant3Dept
	 */
	public String getAssistant3Dept() {
		return assistant3Dept;
	}

	/**
	 * @param assistant3Dept the assistant3Dept to set
	 */
	public void setAssistant3Dept(String assistant3Dept) {
		this.assistant3Dept = assistant3Dept;
	}

	/**
	 * @return the assistant4Dept
	 */
	public String getAssistant4Dept() {
		return assistant4Dept;
	}

	/**
	 * @param assistant4Dept the assistant4Dept to set
	 */
	public void setAssistant4Dept(String assistant4Dept) {
		this.assistant4Dept = assistant4Dept;
	}

	public String getIsApproved() {
		return isApproved;
	}

	public void setIsApproved(String isApproved) {
		this.isApproved = isApproved;
	}

	public String getEntrustUserSsn() {
		return entrustUserSsn;
	}

	public void setEntrustUserSsn(String entrustUserSsn) {
		this.entrustUserSsn = entrustUserSsn;
	}

	public String getEntrustUserName() {
		return entrustUserName;
	}

	public void setEntrustUserName(String entrustUserName) {
		this.entrustUserName = entrustUserName;
	}

	public String getEduCourseCode() {
		return eduCourseCode;
	}

	public void setEduCourseCode(String eduCourseCode) {
		this.eduCourseCode = eduCourseCode;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
