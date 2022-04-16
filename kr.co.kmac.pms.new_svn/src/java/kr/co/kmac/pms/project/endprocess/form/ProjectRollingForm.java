/*
 * $Id: ProjectRollingForm.java,v 1.1 2009/09/19 11:15:37 cvs3 Exp $
 * created by    : CHO DAE HYON
 * creation-date : 2006. 3. 23.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.project.endprocess.form;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.struts.action.ActionForm;

/**
 * TODO 클래스 설명
 * @author CHO DAE HYON
 * @version $Id: ProjectRollingForm.java,v 1.1 2009/09/19 11:15:37 cvs3 Exp $
 */
/**
 * @struts:form name="projectRollingForm"
 */
public class ProjectRollingForm extends ActionForm {
	static final long serialVersionUID = -7034897190745766939L;

	private String taskId;
	private String projectCode;// 프로젝트 코드
	private String gubun;// 구분

	private String answer1;// 항목1
	private String answer2;// 항목2
	private String answer3;// 항목3
	private String answer4;// 항목4
	private String answer5;// 항목5
	private String answer6;// 항목6
	private String answer7;// 항목7
	private String answer8;// 항목8
	private String answer9;// 항목9
	private String answer10;// 항목10
	private String answer11;// 항목11
	private String answer12;// 항목12
	private String answer13;// 항목13

	private String dept;// 부서
	private String duty;// 직위
	private String name;// 성명

	private String ssn[];// 주민번호들
	private String ssn_;// 주민번호
	private String answer8Array[];// 항목8들
	private String answer9Array[];// 항목9들
	private String answer10Array[];// 항목10들
	private String answer11Array[];// 항목11들
	private String answer12Array[];// 항목12들
	private String answer13Array[];// 항목13들

	private String comment;// 코멘트

	private String writerSsn;// 작성자 주민번호
	private String writeDate;// 작성일자

	/**
	 * @return Returns the answer1.
	 */
	public String getAnswer1() {
		return this.answer1;
	}

	/**
	 * @param answer1 The answer1 to set.
	 */
	public void setAnswer1(String answer1) {
		this.answer1 = answer1;
	}

	/**
	 * @return Returns the answer10.
	 */
	public String getAnswer10() {
		return this.answer10;
	}

	/**
	 * @param answer10 The answer10 to set.
	 */
	public void setAnswer10(String answer10) {
		this.answer10 = answer10;
	}

	/**
	 * @return Returns the answer10Array.
	 */
	public String[] getAnswer10Array() {
		return this.answer10Array;
	}

	/**
	 * @param answer10Array The answer10Array to set.
	 */
	public void setAnswer10Array(String[] answer10Array) {
		this.answer10Array = answer10Array;
	}

	/**
	 * @return Returns the answer11.
	 */
	public String getAnswer11() {
		return this.answer11;
	}

	/**
	 * @param answer11 The answer11 to set.
	 */
	public void setAnswer11(String answer11) {
		this.answer11 = answer11;
	}

	/**
	 * @return Returns the answer11Array.
	 */
	public String[] getAnswer11Array() {
		return this.answer11Array;
	}

	/**
	 * @param answer11Array The answer11Array to set.
	 */
	public void setAnswer11Array(String[] answer11Array) {
		this.answer11Array = answer11Array;
	}

	/**
	 * @return Returns the answer12.
	 */
	public String getAnswer12() {
		return this.answer12;
	}

	/**
	 * @param answer12 The answer12 to set.
	 */
	public void setAnswer12(String answer12) {
		this.answer12 = answer12;
	}

	/**
	 * @return Returns the answer12Array.
	 */
	public String[] getAnswer12Array() {
		return this.answer12Array;
	}

	/**
	 * @param answer12Array The answer12Array to set.
	 */
	public void setAnswer12Array(String[] answer12Array) {
		this.answer12Array = answer12Array;
	}

	/**
	 * @return Returns the answer13.
	 */
	public String getAnswer13() {
		return this.answer13;
	}

	/**
	 * @param answer13 The answer13 to set.
	 */
	public void setAnswer13(String answer13) {
		this.answer13 = answer13;
	}

	/**
	 * @return Returns the answer13Array.
	 */
	public String[] getAnswer13Array() {
		return this.answer13Array;
	}

	/**
	 * @param answer13Array The answer13Array to set.
	 */
	public void setAnswer13Array(String[] answer13Array) {
		this.answer13Array = answer13Array;
	}

	/**
	 * @return Returns the answer2.
	 */
	public String getAnswer2() {
		return this.answer2;
	}

	/**
	 * @param answer2 The answer2 to set.
	 */
	public void setAnswer2(String answer2) {
		this.answer2 = answer2;
	}

	/**
	 * @return Returns the answer3.
	 */
	public String getAnswer3() {
		return this.answer3;
	}

	/**
	 * @param answer3 The answer3 to set.
	 */
	public void setAnswer3(String answer3) {
		this.answer3 = answer3;
	}

	/**
	 * @return Returns the answer4.
	 */
	public String getAnswer4() {
		return this.answer4;
	}

	/**
	 * @param answer4 The answer4 to set.
	 */
	public void setAnswer4(String answer4) {
		this.answer4 = answer4;
	}

	/**
	 * @return Returns the answer5.
	 */
	public String getAnswer5() {
		return this.answer5;
	}

	/**
	 * @param answer5 The answer5 to set.
	 */
	public void setAnswer5(String answer5) {
		this.answer5 = answer5;
	}

	/**
	 * @return Returns the answer6.
	 */
	public String getAnswer6() {
		return this.answer6;
	}

	/**
	 * @param answer6 The answer6 to set.
	 */
	public void setAnswer6(String answer6) {
		this.answer6 = answer6;
	}

	/**
	 * @return Returns the answer7.
	 */
	public String getAnswer7() {
		return this.answer7;
	}

	/**
	 * @param answer7 The answer7 to set.
	 */
	public void setAnswer7(String answer7) {
		this.answer7 = answer7;
	}

	/**
	 * @return Returns the answer8.
	 */
	public String getAnswer8() {
		return this.answer8;
	}

	/**
	 * @param answer8 The answer8 to set.
	 */
	public void setAnswer8(String answer8) {
		this.answer8 = answer8;
	}

	/**
	 * @return Returns the answer8Array.
	 */
	public String[] getAnswer8Array() {
		return this.answer8Array;
	}

	/**
	 * @param answer8Array The answer8Array to set.
	 */
	public void setAnswer8Array(String[] answer8Array) {
		this.answer8Array = answer8Array;
	}

	/**
	 * @return Returns the answer9.
	 */
	public String getAnswer9() {
		return this.answer9;
	}

	/**
	 * @param answer9 The answer9 to set.
	 */
	public void setAnswer9(String answer9) {
		this.answer9 = answer9;
	}

	/**
	 * @return Returns the answer9Array.
	 */
	public String[] getAnswer9Array() {
		return this.answer9Array;
	}

	/**
	 * @param answer9Array The answer9Array to set.
	 */
	public void setAnswer9Array(String[] answer9Array) {
		this.answer9Array = answer9Array;
	}

	/**
	 * @return Returns the comment.
	 */
	public String getComment() {
		return this.comment;
	}

	/**
	 * @param comment The comment to set.
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * @return Returns the dept.
	 */
	public String getDept() {
		return this.dept;
	}

	/**
	 * @param dept The dept to set.
	 */
	public void setDept(String dept) {
		this.dept = dept;
	}

	/**
	 * @return Returns the duty.
	 */
	public String getDuty() {
		return this.duty;
	}

	/**
	 * @param duty The duty to set.
	 */
	public void setDuty(String duty) {
		this.duty = duty;
	}

	/**
	 * @return Returns the gubun.
	 */
	public String getGubun() {
		return this.gubun;
	}

	/**
	 * @param gubun The gubun to set.
	 */
	public void setGubun(String gubun) {
		this.gubun = gubun;
	}

	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @param name The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return Returns the projectCode.
	 */
	public String getProjectCode() {
		return this.projectCode;
	}

	/**
	 * @param projectCode The projectCode to set.
	 */
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	/**
	 * @return Returns the ssn.
	 */
	public String[] getSsn() {
		return this.ssn;
	}

	/**
	 * @param ssn The ssn to set.
	 */
	public void setSsn(String[] ssn) {
		this.ssn = ssn;
	}

	/**
	 * @return Returns the ssn_.
	 */
	public String getSsn_() {
		return this.ssn_;
	}

	/**
	 * @param ssn_ The ssn_ to set.
	 */
	public void setSsn_(String ssn_) {
		this.ssn_ = ssn_;
	}

	/**
	 * @return Returns the taskId.
	 */
	public String getTaskId() {
		return this.taskId;
	}

	/**
	 * @param taskId The taskId to set.
	 */
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	/**
	 * @return Returns the writeDate.
	 */
	public String getWriteDate() {
		return this.writeDate;
	}

	/**
	 * @param writeDate The writeDate to set.
	 */
	public void setWriteDate(String writeDate) {
		this.writeDate = writeDate;
	}

	/**
	 * @return Returns the writerSsn.
	 */
	public String getWriterSsn() {
		return this.writerSsn;
	}

	/**
	 * @param writerSsn The writerSsn to set.
	 */
	public void setWriterSsn(String writerSsn) {
		this.writerSsn = writerSsn;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
