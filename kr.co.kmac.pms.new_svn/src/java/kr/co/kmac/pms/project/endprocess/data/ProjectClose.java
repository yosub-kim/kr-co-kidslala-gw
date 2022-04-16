/*
 * $Id: ProjectClose.java,v 1.1 2009/09/19 11:15:26 cvs3 Exp $
 * created by    : CHO DAE HYON
 * creation-date : 2006. 4. 13.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.project.endprocess.data;

/**
 * TODO 클래스 설명
 * @author CHO DAE HYON
 * @version $Id: ProjectClose.java,v 1.1 2009/09/19 11:15:26 cvs3 Exp $
 */
public class ProjectClose {
	
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
	private String estimateC;// 평가C

	private String dept;// 부서
	private String duty;// 직위
	private String name;// 성명

	private String comment;//의견
	
	private String projectName;//프로젝트명
	
	private String total;//합계
	
	private String customerName;//고객사
	
	private String sum;//평점합계
	
	private String ssn;// 주민번호

	private String endGubun;// 종료구분
	private String endReason;// 종료사유
	private String endRate;// 종료평가
	
	private String subject;// 교육과목
	
	public String getSubject() {
		return this.subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getEndGubun() {
		return this.endGubun;
	}

	public void setEndGubun(String endGubun) {
		this.endGubun = endGubun;
	}

	public String getEndRate() {
		return this.endRate;
	}

	public void setEndRate(String endRate) {
		this.endRate = endRate;
	}

	public String getEndReason() {
		return this.endReason;
	}

	public void setEndReason(String endReason) {
		this.endReason = endReason;
	}

	public String getSsn() {
		return this.ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public String getAnswer1() {
		return this.answer1;
	}

	public void setAnswer1(String answer1) {
		this.answer1 = answer1;
	}

	public String getAnswer10() {
		return this.answer10;
	}

	public void setAnswer10(String answer10) {
		this.answer10 = answer10;
	}

	public String getAnswer11() {
		return this.answer11;
	}

	public void setAnswer11(String answer11) {
		this.answer11 = answer11;
	}

	public String getAnswer12() {
		return this.answer12;
	}

	public void setAnswer12(String answer12) {
		this.answer12 = answer12;
	}

	public String getAnswer13() {
		return this.answer13;
	}

	public void setAnswer13(String answer13) {
		this.answer13 = answer13;
	}

	public String getAnswer2() {
		return this.answer2;
	}

	public void setAnswer2(String answer2) {
		this.answer2 = answer2;
	}

	public String getAnswer3() {
		return this.answer3;
	}

	public void setAnswer3(String answer3) {
		this.answer3 = answer3;
	}

	public String getAnswer4() {
		return this.answer4;
	}

	public void setAnswer4(String answer4) {
		this.answer4 = answer4;
	}

	public String getAnswer5() {
		return this.answer5;
	}

	public void setAnswer5(String answer5) {
		this.answer5 = answer5;
	}

	public String getAnswer6() {
		return this.answer6;
	}

	public void setAnswer6(String answer6) {
		this.answer6 = answer6;
	}

	public String getAnswer7() {
		return this.answer7;
	}

	public void setAnswer7(String answer7) {
		this.answer7 = answer7;
	}

	public String getAnswer8() {
		return this.answer8;
	}

	public void setAnswer8(String answer8) {
		this.answer8 = answer8;
	}

	public String getAnswer9() {
		return this.answer9;
	}

	public void setAnswer9(String answer9) {
		this.answer9 = answer9;
	}

	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getCustomerName() {
		return this.customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getSum() {
		return this.sum;
	}

	public void setSum(String sum) {
		this.sum = sum;
	}

	public String getDept() {
		return this.dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getDuty() {
		return this.duty;
	}

	public void setDuty(String duty) {
		this.duty = duty;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	/**
	 * @return Returns the estimateC.
	 */
	public String getEstimateC() {
		return estimateC;
	}
	/**
	 * @param estimateC The estimateC to set.
	 */
	public void setEstimateC(String estimateC) {
		this.estimateC = estimateC;
	}
}
