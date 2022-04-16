/*
 * $Id: ProjectEndingForm.java,v 1.6 2014/04/09 05:06:00 cvs Exp $
 * created by    : CHO DAE HYON
 * creation-date : 2006. 3. 28.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.project.endprocess.form;

import kr.co.kmac.pms.attach.form.AttachForm;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * TODO 클래스 설명
 * @author CHO DAE HYON
 * @version $Id: ProjectEndingForm.java,v 1.6 2014/04/09 05:06:00 cvs Exp $
 */
/**
 * @struts:form name="projectEndingForm"
 */
public class ProjectEndingForm extends AttachForm {

	static final long serialVersionUID = -7034897190745766939L;

	private String workId;
	private String projectCode;// 프로젝트 코드
	private String writerSsn;// 작성자 주민번호
	private String writeDate;// 작성일자

	private String ssn_;// 컨설턴트 주민번호
	private String answer1;// 문항1
	private String answer2;// 문항2
	private String answer3;// 문항3
	private String answer4;// 문항4
	private String answer5;// 문항5
	private String answer6;// 문항6
	private String answer7;// 문항7
	private String answer8;// 문항8
	private String answer9;// 문항9
	private String answer10;// 문항10
	private String comment;// 의견
	private String subject;// 교육과목

	private String ssn[];// 컨설턴트 주민번호들
	private String answer1Array[];// 문항1들
	private String answer2Array[];// 문항2들
	private String answer3Array[];// 문항3들
	private String answer4Array[];// 문항4들
	private String answer5Array[];// 문항5들
	private String answer6Array[];// 문항6들
	private String answer7Array[];// 문항7들
	private String answer8Array[];// 문항8들
	private String answer9Array[];// 문항9들
	private String answer10Array[];// 문항10들
	private String commentArray[];// 의견들
	private String subjectArray[];// 교육과목

	private String endGubun;// 종료구분
	private String endBackground;
	private String endResult;
	private String endReview;
	private String endReason;
	private String endSuggestion;

	private String endRate;// 종료평가
	private String groupComment;// 그룹장 의견
	private String cfoComment;// cfo 의견
	private String cboComment;// cbo 의견

	private String approveYn;
	private String flag;
	private String endTaskState;
	
	private String bizFunction;
	private String kdbYn;
	
	private String entrustUserSsn;
	private String entrustUserName;
	
	private String memberChk_;
	private String memberChk[];
	private String evalChk;

	public String getEvalChk() {
		return evalChk;
	}

	public void setEvalChk(String evalChk) {
		this.evalChk = evalChk;
	}

	public String getMemberChk_() {
		return memberChk_;
	}

	public void setMemberChk_(String memberChk_) {
		this.memberChk_ = memberChk_;
	}

	public String[] getMemberChk() {
		return memberChk;
	}

	public void setMemberChk(String[] memberChk) {
		this.memberChk = memberChk;
	}

	/**
	 * @return the groupComment
	 */
	public String getGroupComment() {
		return groupComment;
	}

	/**
	 * @param groupComment the groupComment to set
	 */
	public void setGroupComment(String groupComment) {
		this.groupComment = groupComment;
	}

	public String getWorkId() {
		return workId;
	}

	public void setWorkId(String workId) {
		this.workId = workId;
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
	 * @return the writerSsn
	 */
	public String getWriterSsn() {
		return writerSsn;
	}

	/**
	 * @param writerSsn the writerSsn to set
	 */
	public void setWriterSsn(String writerSsn) {
		this.writerSsn = writerSsn;
	}

	/**
	 * @return the writeDate
	 */
	public String getWriteDate() {
		return writeDate;
	}

	/**
	 * @param writeDate the writeDate to set
	 */
	public void setWriteDate(String writeDate) {
		this.writeDate = writeDate;
	}

	/**
	 * @return the ssn_
	 */
	public String getSsn_() {
		return ssn_;
	}

	/**
	 * @param ssn_ the ssn_ to set
	 */
	public void setSsn_(String ssn_) {
		this.ssn_ = ssn_;
	}

	/**
	 * @return the answer1
	 */
	public String getAnswer1() {
		return answer1;
	}

	/**
	 * @param answer1 the answer1 to set
	 */
	public void setAnswer1(String answer1) {
		this.answer1 = answer1;
	}

	/**
	 * @return the answer2
	 */
	public String getAnswer2() {
		return answer2;
	}

	/**
	 * @param answer2 the answer2 to set
	 */
	public void setAnswer2(String answer2) {
		this.answer2 = answer2;
	}

	/**
	 * @return the answer3
	 */
	public String getAnswer3() {
		return answer3;
	}

	/**
	 * @param answer3 the answer3 to set
	 */
	public void setAnswer3(String answer3) {
		this.answer3 = answer3;
	}

	/**
	 * @return the answer4
	 */
	public String getAnswer4() {
		return answer4;
	}

	/**
	 * @param answer4 the answer4 to set
	 */
	public void setAnswer4(String answer4) {
		this.answer4 = answer4;
	}

	/**
	 * @return the answer5
	 */
	public String getAnswer5() {
		return answer5;
	}

	/**
	 * @param answer5 the answer5 to set
	 */
	public void setAnswer5(String answer5) {
		this.answer5 = answer5;
	}

	/**
	 * @return the answer6
	 */
	public String getAnswer6() {
		return answer6;
	}

	/**
	 * @param answer6 the answer6 to set
	 */
	public void setAnswer6(String answer6) {
		this.answer6 = answer6;
	}

	/**
	 * @return the answer7
	 */
	public String getAnswer7() {
		return answer7;
	}

	/**
	 * @param answer7 the answer7 to set
	 */
	public void setAnswer7(String answer7) {
		this.answer7 = answer7;
	}

	/**
	 * @return the answer8
	 */
	public String getAnswer8() {
		return answer8;
	}

	/**
	 * @param answer8 the answer8 to set
	 */
	public void setAnswer8(String answer8) {
		this.answer8 = answer8;
	}

	/**
	 * @return the answer9
	 */
	public String getAnswer9() {
		return answer9;
	}

	/**
	 * @param answer9 the answer9 to set
	 */
	public void setAnswer9(String answer9) {
		this.answer9 = answer9;
	}

	/**
	 * @return the answer10
	 */
	public String getAnswer10() {
		return answer10;
	}

	/**
	 * @param answer10 the answer10 to set
	 */
	public void setAnswer10(String answer10) {
		this.answer10 = answer10;
	}

	/**
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * @param comment the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
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
	 * @return the ssn
	 */
	public String[] getSsn() {
		return ssn;
	}

	/**
	 * @param ssn the ssn to set
	 */
	public void setSsn(String[] ssn) {
		this.ssn = ssn;
	}

	/**
	 * @return the answer1Array
	 */
	public String[] getAnswer1Array() {
		return answer1Array;
	}

	/**
	 * @param answer1Array the answer1Array to set
	 */
	public void setAnswer1Array(String[] answer1Array) {
		this.answer1Array = answer1Array;
	}

	/**
	 * @return the answer2Array
	 */
	public String[] getAnswer2Array() {
		return answer2Array;
	}

	/**
	 * @param answer2Array the answer2Array to set
	 */
	public void setAnswer2Array(String[] answer2Array) {
		this.answer2Array = answer2Array;
	}

	/**
	 * @return the answer3Array
	 */
	public String[] getAnswer3Array() {
		return answer3Array;
	}

	/**
	 * @param answer3Array the answer3Array to set
	 */
	public void setAnswer3Array(String[] answer3Array) {
		this.answer3Array = answer3Array;
	}

	/**
	 * @return the answer4Array
	 */
	public String[] getAnswer4Array() {
		return answer4Array;
	}

	/**
	 * @param answer4Array the answer4Array to set
	 */
	public void setAnswer4Array(String[] answer4Array) {
		this.answer4Array = answer4Array;
	}

	/**
	 * @return the answer5Array
	 */
	public String[] getAnswer5Array() {
		return answer5Array;
	}

	/**
	 * @param answer5Array the answer5Array to set
	 */
	public void setAnswer5Array(String[] answer5Array) {
		this.answer5Array = answer5Array;
	}

	/**
	 * @return the answer6Array
	 */
	public String[] getAnswer6Array() {
		return answer6Array;
	}

	/**
	 * @param answer6Array the answer6Array to set
	 */
	public void setAnswer6Array(String[] answer6Array) {
		this.answer6Array = answer6Array;
	}

	/**
	 * @return the answer7Array
	 */
	public String[] getAnswer7Array() {
		return answer7Array;
	}

	/**
	 * @param answer7Array the answer7Array to set
	 */
	public void setAnswer7Array(String[] answer7Array) {
		this.answer7Array = answer7Array;
	}

	/**
	 * @return the answer8Array
	 */
	public String[] getAnswer8Array() {
		return answer8Array;
	}

	/**
	 * @param answer8Array the answer8Array to set
	 */
	public void setAnswer8Array(String[] answer8Array) {
		this.answer8Array = answer8Array;
	}

	/**
	 * @return the answer9Array
	 */
	public String[] getAnswer9Array() {
		return answer9Array;
	}

	/**
	 * @param answer9Array the answer9Array to set
	 */
	public void setAnswer9Array(String[] answer9Array) {
		this.answer9Array = answer9Array;
	}

	/**
	 * @return the answer10Array
	 */
	public String[] getAnswer10Array() {
		return answer10Array;
	}

	/**
	 * @param answer10Array the answer10Array to set
	 */
	public void setAnswer10Array(String[] answer10Array) {
		this.answer10Array = answer10Array;
	}

	/**
	 * @return the commentArray
	 */
	public String[] getCommentArray() {
		return commentArray;
	}

	/**
	 * @param commentArray the commentArray to set
	 */
	public void setCommentArray(String[] commentArray) {
		this.commentArray = commentArray;
	}

	/**
	 * @return the subjectArray
	 */
	public String[] getSubjectArray() {
		return subjectArray;
	}

	/**
	 * @param subjectArray the subjectArray to set
	 */
	public void setSubjectArray(String[] subjectArray) {
		this.subjectArray = subjectArray;
	}

	/**
	 * @return the endGubun
	 */
	public String getEndGubun() {
		return endGubun;
	}

	/**
	 * @param endGubun the endGubun to set
	 */
	public void setEndGubun(String endGubun) {
		this.endGubun = endGubun;
	}

	/**
	 * @return the endBackground
	 */
	public String getEndBackground() {
		return endBackground;
	}

	/**
	 * @param endBackground the endBackground to set
	 */
	public void setEndBackground(String endBackground) {
		this.endBackground = endBackground;
	}

	/**
	 * @return the endResult
	 */
	public String getEndResult() {
		return endResult;
	}

	/**
	 * @param endResult the endResult to set
	 */
	public void setEndResult(String endResult) {
		this.endResult = endResult;
	}

	/**
	 * @return the endReview
	 */
	public String getEndReview() {
		return endReview;
	}

	/**
	 * @param endReview the endReview to set
	 */
	public void setEndReview(String endReview) {
		this.endReview = endReview;
	}

	/**
	 * @return the endReason
	 */
	public String getEndReason() {
		return endReason;
	}

	/**
	 * @param endReason the endReason to set
	 */
	public void setEndReason(String endReason) {
		this.endReason = endReason;
	}

	/**
	 * @return the endSuggestion
	 */
	public String getEndSuggestion() {
		return endSuggestion;
	}

	/**
	 * @param endSuggestion the endSuggestion to set
	 */
	public void setEndSuggestion(String endSuggestion) {
		this.endSuggestion = endSuggestion;
	}

	/**
	 * @return the endRate
	 */
	public String getEndRate() {
		return endRate;
	}

	/**
	 * @param endRate the endRate to set
	 */
	public void setEndRate(String endRate) {
		this.endRate = endRate;
	}

	/**
	 * @return the cfoComment
	 */
	public String getCfoComment() {
		return cfoComment;
	}

	/**
	 * @param cfoComment the cfoComment to set
	 */
	public void setCfoComment(String cfoComment) {
		this.cfoComment = cfoComment;
	}

	/**
	 * @return the cboComment
	 */
	public String getCboComment() {
		return cboComment;
	}

	/**
	 * @param cboComment the cboComment to set
	 */
	public void setCboComment(String cboComment) {
		this.cboComment = cboComment;
	}

	/**
	 * @return the approveYn
	 */
	public String getApproveYn() {
		return approveYn;
	}

	/**
	 * @param approveYn the approveYn to set
	 */
	public void setApproveYn(String approveYn) {
		this.approveYn = approveYn;
	}

	/**
	 * @return the flag
	 */
	public String getFlag() {
		return flag;
	}

	/**
	 * @param flag the flag to set
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getEndTaskState() {
		return endTaskState;
	}

	public void setEndTaskState(String endTaskState) {
		this.endTaskState = endTaskState;
	}	

	public String getBizFunction() {
		return bizFunction;
	}

	public void setBizFunction(String bizFunction) {
		this.bizFunction = bizFunction;
	}

	/**
	 * @return the kdbYn
	 */
	public String getKdbYn() {
		return kdbYn;
	}

	/**
	 * @param kdbYn the kdbYn to set
	 */
	public void setKdbYn(String kdbYn) {
		this.kdbYn = kdbYn;
	}

	/**
	 * @return the entrustUserSsn
	 */
	public String getEntrustUserSsn() {
		return entrustUserSsn;
	}

	/**
	 * @param entrustUserSsn the entrustUserSsn to set
	 */
	public void setEntrustUserSsn(String entrustUserSsn) {
		this.entrustUserSsn = entrustUserSsn;
	}

	/**
	 * @return the entrustUserName
	 */
	public String getEntrustUserName() {
		return entrustUserName;
	}

	/**
	 * @param entrustUserName the entrustUserName to set
	 */
	public void setEntrustUserName(String entrustUserName) {
		this.entrustUserName = entrustUserName;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}