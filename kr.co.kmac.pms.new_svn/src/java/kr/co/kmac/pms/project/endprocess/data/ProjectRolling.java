/*
 * $Id: ProjectRolling.java,v 1.2 2011/05/20 09:32:03 cvs Exp $
 * created by    : CHO DAE HYON
 * creation-date : 2006. 3. 23.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.project.endprocess.data;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * TODO Ŭ���� ����
 * 
 * @author CHO DAE HYON
 * @version $Id: ProjectRolling.java,v 1.2 2011/05/20 09:32:03 cvs Exp $
 */
public class ProjectRolling {

	private String projectCode;// ������Ʈ �ڵ�
	private String customerCode;// ���ڵ�
	private String email;// ������ �̸���
	private String gubun;// ����

	private String answer1;// �׸�1
	private String answer2;// �׸�2
	private String answer3;// �׸�3
	private String answer4;// �׸�4
	private String answer5;// �׸�5
	private String answer6;// �׸�6
	private String answer7;// �׸�7
	private String answer8;// �׸�8
	private String answer9;// �׸�9
	private String answer10;// �׸�10
	private String answer11;// �׸�11
	private String answer12;// �׸�12
	private String answer13;// �׸�13
	private String answer7C;// �׸�7(������)
	private String ssn;// �ֹι�ȣ

	private String dept;// �μ�
	private String duty;// ����
	private String name;// ����

	private String ssnArray[];// �ֹι�ȣ��
	private String answer8Array[];// �׸�8��
	private String answer9Array[];// �׸�9��
	private String answer10Array[];// �׸�10��
	private String answer11Array[];// �׸�11��
	private String answer12Array[];// �׸�12��
	private String answer13Array[];// �׸�13��

	private String comment;// �ڸ�Ʈ

	private String writerSsn;// �ۼ��� �ֹι�ȣ
	private String writeDate;// �ۼ�����

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String[] getAnswer10Array() {
		return this.answer10Array;
	}

	public void setAnswer10Array(String[] answer10Array) {
		this.answer10Array = answer10Array;
	}

	public String getAnswer11() {
		return this.answer11;
	}

	public void setAnswer11(String answer11) {
		this.answer11 = answer11;
	}

	public String[] getAnswer11Array() {
		return this.answer11Array;
	}

	public void setAnswer11Array(String[] answer11Array) {
		this.answer11Array = answer11Array;
	}

	public String getAnswer12() {
		return this.answer12;
	}

	public void setAnswer12(String answer12) {
		this.answer12 = answer12;
	}

	public String[] getAnswer12Array() {
		return this.answer12Array;
	}

	public void setAnswer12Array(String[] answer12Array) {
		this.answer12Array = answer12Array;
	}

	public String getAnswer13() {
		return this.answer13;
	}

	public void setAnswer13(String answer13) {
		this.answer13 = answer13;
	}

	public String[] getAnswer13Array() {
		return this.answer13Array;
	}

	public void setAnswer13Array(String[] answer13Array) {
		this.answer13Array = answer13Array;
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

	public String getAnswer7C() {
		return this.answer7C;
	}

	public void setAnswer7C(String answer7C) {
		this.answer7C = answer7C;
	}

	public String getAnswer8() {
		return this.answer8;
	}

	public void setAnswer8(String answer8) {
		this.answer8 = answer8;
	}

	public String[] getAnswer8Array() {
		return this.answer8Array;
	}

	public void setAnswer8Array(String[] answer8Array) {
		this.answer8Array = answer8Array;
	}

	public String getAnswer9() {
		return this.answer9;
	}

	public void setAnswer9(String answer9) {
		this.answer9 = answer9;
	}

	public String[] getAnswer9Array() {
		return this.answer9Array;
	}

	public void setAnswer9Array(String[] answer9Array) {
		this.answer9Array = answer9Array;
	}

	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
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

	public String getGubun() {
		return this.gubun;
	}

	public void setGubun(String gubun) {
		this.gubun = gubun;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProjectCode() {
		return this.projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public String getSsn() {
		return this.ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public String[] getSsnArray() {
		return this.ssnArray;
	}

	public void setSsnArray(String[] ssnArray) {
		this.ssnArray = ssnArray;
	}

	public String getWriteDate() {
		return this.writeDate;
	}

	public void setWriteDate(String writeDate) {
		this.writeDate = writeDate;
	}

	public String getWriterSsn() {
		return this.writerSsn;
	}

	public void setWriterSsn(String writerSsn) {
		this.writerSsn = writerSsn;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
