/*
 * $Id: SanctionDoc.java,v 1.7 2015/02/17 00:08:49 cvs Exp $
 * created by    : jiwoongLee
 * creation-date : 2006. 3. 25.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.sanction.general.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import kr.co.kmac.pms.common.util.DateUtil;
import kr.co.kmac.pms.sanction.general.form.SanctionForm;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * TODO 클래스 설명
 * 
 * @author jiwoongLee
 * @version $Id: SanctionDoc.java,v 1.7 2015/02/17 00:08:49 cvs Exp $
 */
public class SanctionDoc {

	private String taskId;

	private int seq;
	private String projectCode;
	private String approvalType;

	/**
	 * 기안
	 */
	private String registerSsn;
	private String registerDept;
	private String registerName;
	private Date registerDate;
	private Date createrDate;
	/**
	 * 검토
	 */
	private String teamManagerSsn;
	private String teamManagerDept;
	private String teamManagerName;
	private Date teamManagerDate;
	/**
	 * 확인
	 */
	private String cfoSsn;
	private String cfoDept;
	private String cfoName;
	private Date cfoDate;
	/**
	 * 승인
	 */
	private String cioSsn;
	private String cioDept;
	private String cioName;
	private Date cioDate;
	/**
	 * 대표이사
	 */
	private String ceoSsn;
	private String ceoDept;
	private String ceoName;
	private Date ceoDate;
	/**
	 * 협의1
	 */
	private String assistant1Ssn;
	private String assistant1Dept;
	private String assistant1Name;
	private Date assistant1Date;
	/**
	 * 협의2
	 */
	private String assistant2Ssn;
	private String assistant2Dept;
	private String assistant2Name;
	private Date assistant2Date;
	/**
	 * 협의3
	 */
	private String assistant3Ssn;
	private String assistant3Dept;
	private String assistant3Name;
	private Date assistant3Date;
	/**
	 * 협의4
	 */
	private String assistant4Ssn;
	private String assistant4Dept;
	private String assistant4Name;
	private Date assistant4Date;

	private String subject;
	private String content;
	private String rejectReason = "";
	private String rejectReasonView = "";
	private String isWholeApproval;
	private String state;
	private String reject;

	private String isApproved;

	private String workType;
	
	private String entrustUserSsn;
	private String entrustUserName;
	
	private String eduCourseCode;
	private String projectCodeBp;
	private String func;
	
	private String viewSsn;
	private String viewDate;
	private String sanctionDocSsn;
	private String sanctionDocDate;

	public String getViewSsn() {
		return viewSsn;
	}

	public void setViewSsn(String viewSsn) {
		this.viewSsn = viewSsn;
	}

	public String getViewDate() {
		return viewDate;
	}

	public void setViewDate(String viewDate) {
		this.viewDate = viewDate;
	}

	public String getSanctionDocSsn() {
		return sanctionDocSsn;
	}

	public void setSanctionDocSsn(String sanctionDocSsn) {
		this.sanctionDocSsn = sanctionDocSsn;
	}

	public String getSanctionDocDate() {
		return sanctionDocDate;
	}

	public void setSanctionDocDate(String sanctionDocDate) {
		this.sanctionDocDate = sanctionDocDate;
	}

	public String getFunc() {
		return func;
	}

	public void setFunc(String func) {
		this.func = func;
	}

	public String getProjectCodeBp() {
		return projectCodeBp;
	}

	public void setProjectCodeBp(String projectCodeBp) {
		this.projectCodeBp = projectCodeBp;
	}

	/**
	 * 참조
	 */
	private List<SanctionDocCC> sanctionDocCC;
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
	
	public Date getCreaterDate() {
		return createrDate;
	}
	
	public void setCreaterDate(Date createrDate){
		this.createrDate = createrDate;
	}
	
	public Date getRegisterDate() {
		return registerDate;
	}

	/**
	 * @param registerDate the registerDate to set
	 */
	public void setRegisterDate(Date registerDate) {
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
	public Date getTeamManagerDate() {
		return teamManagerDate;
	}

	/**
	 * @param teamManagerDate the teamManagerDate to set
	 */
	public void setTeamManagerDate(Date teamManagerDate) {
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
	public Date getCfoDate() {
		return cfoDate;
	}

	/**
	 * @param cfoDate the cfoDate to set
	 */
	public void setCfoDate(Date cfoDate) {
		this.cfoDate = cfoDate;
	}

	/**
	 * @return the ceoSsn
	 */
	public String getCeoSsn() {
		return ceoSsn;
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
	public Date getCeoDate() {
		return ceoDate;
	}

	/**
	 * @param ceoDate the ceoDate to set
	 */
	public void setCeoDate(Date ceoDate) {
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
	public Date getAssistant1Date() {
		return assistant1Date;
	}

	/**
	 * @param assistant1Date the assistant1Date to set
	 */
	public void setAssistant1Date(Date assistant1Date) {
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
	public Date getAssistant2Date() {
		return assistant2Date;
	}

	/**
	 * @param assistant2Date the assistant2Date to set
	 */
	public void setAssistant2Date(Date assistant2Date) {
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
	public Date getAssistant3Date() {
		return assistant3Date;
	}

	/**
	 * @param assistant3Date the assistant3Date to set
	 */
	public void setAssistant3Date(Date assistant3Date) {
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
	public Date getAssistant4Date() {
		return assistant4Date;
	}

	/**
	 * @param assistant4Date the assistant4Date to set
	 */
	public void setAssistant4Date(Date assistant4Date) {
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
		return isWholeApproval == null ? "" : this.isWholeApproval;
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
	 * @return the sanctionDocCC
	 */
	public List<SanctionDocCC> getSanctionDocCC() {
		return sanctionDocCC;
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

	public String getWorkType() {
		return workType;
	}

	public void setWorkType(String workType) {
		this.workType = workType;
	}

	public String getSanctionDocCCName() {
		String name = "";
		if (getSanctionDocCC() != null) {
			for (SanctionDocCC sanctionDocCC : getSanctionDocCC()) {
				name = name + ", " + sanctionDocCC.getRefMemberName();
			}
			if (name.startsWith(",")) {
				name = name.substring(1).trim();
			}
		}
		return name;
	}

	public String getSanctionDocCCSsn() {
		String ssn = "";
		if (getSanctionDocCC() != null) {
			for (SanctionDocCC sanctionDocCC : getSanctionDocCC()) {
				ssn = ssn + ", " + sanctionDocCC.getRefMemberSsn();
			}
			if (ssn.startsWith(",")) {
				ssn = ssn.substring(1).trim();
			}
		}
		return ssn;
	}

	/**
	 * @param sanctionDocCC the sanctionDocCC to set
	 */
	public void setSanctionDocCC(List<SanctionDocCC> sanctionDocCC) {
		this.sanctionDocCC = sanctionDocCC;
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

	public Date getCioDate() {
		return cioDate;
	}

	public void setCioDate(Date cioDate) {
		this.cioDate = cioDate;
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

	public static SanctionDoc valueOf(SanctionForm sanctionForm) {
		SanctionDoc doc = new SanctionDoc();
		doc.setTaskId(sanctionForm.getTaskId());
		doc.setSeq(sanctionForm.getSeq());
		doc.setProjectCode(sanctionForm.getProjectCode());
		doc.setApprovalType(sanctionForm.getApprovalType());
		doc.setRegisterSsn(sanctionForm.getRegisterSsn());
		doc.setRegisterName(sanctionForm.getRegisterName());
		doc.setRegisterDept(sanctionForm.getRegisterDept());
		doc.setRegisterDate(DateUtil.getDateTime(sanctionForm.getRegisterDate()));
		doc.setCreaterDate(DateUtil.getDateTime(sanctionForm.getRegisterDate()));
		doc.setTeamManagerSsn(sanctionForm.getTeamManagerSsn());
		doc.setTeamManagerName(sanctionForm.getTeamManagerName());
		doc.setTeamManagerDept(sanctionForm.getTeamManagerDept());
		doc.setTeamManagerDate(DateUtil.getDateTime(sanctionForm.getTeamManagerDate()));
		doc.setCfoSsn(sanctionForm.getCfoSsn());
		doc.setCfoName(sanctionForm.getCfoName());
		doc.setCfoDept(sanctionForm.getCfoDept());
		doc.setCfoDate(DateUtil.getDateTime(sanctionForm.getCfoDate()));
		doc.setCioSsn(sanctionForm.getCioSsn());
		doc.setCioName(sanctionForm.getCioName());
		doc.setCioDept(sanctionForm.getCioDept());
		doc.setCioDate(DateUtil.getDateTime(sanctionForm.getCioDate()));
		doc.setCeoSsn(sanctionForm.getCeoSsn());
		doc.setCeoName(sanctionForm.getCeoName());
		doc.setCeoDept(sanctionForm.getCeoDept());
		doc.setCeoDate(DateUtil.getDateTime(sanctionForm.getCeoDate()));
		doc.setAssistant1Ssn(sanctionForm.getAssistant1Ssn());
		doc.setAssistant1Name(sanctionForm.getAssistant1Name());
		doc.setAssistant1Dept(sanctionForm.getAssistant1Dept());
		doc.setAssistant1Date(DateUtil.getDateTime(sanctionForm.getAssistant1Date()));
		doc.setAssistant2Ssn(sanctionForm.getAssistant2Ssn());
		doc.setAssistant2Name(sanctionForm.getAssistant2Name());
		doc.setAssistant2Dept(sanctionForm.getAssistant2Dept());
		doc.setAssistant2Date(DateUtil.getDateTime(sanctionForm.getAssistant2Date()));
		doc.setAssistant3Ssn(sanctionForm.getAssistant3Ssn());
		doc.setAssistant3Name(sanctionForm.getAssistant3Name());
		doc.setAssistant3Dept(sanctionForm.getAssistant3Dept());
		doc.setAssistant3Date(DateUtil.getDateTime(sanctionForm.getAssistant3Date()));
		doc.setAssistant4Ssn(sanctionForm.getAssistant4Ssn());
		doc.setAssistant4Name(sanctionForm.getAssistant4Name());
		doc.setAssistant4Dept(sanctionForm.getAssistant4Dept());
		doc.setAssistant4Date(DateUtil.getDateTime(sanctionForm.getAssistant4Date()));
		doc.setSubject(sanctionForm.getSubject());
		doc.setContent(sanctionForm.getContent());
		doc.setRejectReason(sanctionForm.getRejectReason());
		doc.setRejectReasonView(sanctionForm.getRejectReasonView());
		doc.setIsWholeApproval(sanctionForm.getIsWholeApproval());
		doc.setState(sanctionForm.getState());
		doc.setReject(sanctionForm.getReject());
		doc.setIsApproved(sanctionForm.getIsApproved());
		doc.setEduCourseCode(sanctionForm.getEduCourseCode());
		
		if (sanctionForm.getRefMemberSsn() != null && sanctionForm.getRefMemberSsn().length() > 0) {
			String[] refSsn = sanctionForm.getRefMemberSsn().split(",");
			String[] refName = sanctionForm.getRefMemberName().split(",");
			if (refSsn != null) {
				List<SanctionDocCC> list = new ArrayList<SanctionDocCC>();
				for (int i = 0; i < refSsn.length; i++) {
					SanctionDocCC docCC = new SanctionDocCC();
					docCC.setSeq(sanctionForm.getSeq());
					docCC.setRefMemberSsn(refSsn[i].trim());
					docCC.setRefMemberName(refName[i].trim());
					list.add(docCC);
				}
				doc.setSanctionDocCC(list);
			}
		}

		doc.setEntrustUserName(sanctionForm.getEntrustUserName());
		doc.setEntrustUserSsn(sanctionForm.getEntrustUserSsn());
		
		return doc;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
