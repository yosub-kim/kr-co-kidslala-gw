
package kr.co.kmac.pms.project.manpower.data;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class ProjectManpower {

	private String projectCode;	// ������Ʈ �ڵ�
	private String year;		// �⵵
	private String month;		// ��
	private String day;			// ��
	private String week;		// ����
	private String workSeq;		// ��������
	private String workName;	// ��������
	private String ssn;			// �ĺ���
	private String name;		// �̸�
	private String role;
	private String position;
	private String companyPositionName;
	private String rn;
	private String rn2;
	private String projectName;

	private String[] ssnArray;	// ���� �η� �ĺ���
	private String[] nameArray;	// ���� �η� �̸�

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getRn2() {
		return rn2;
	}

	public void setRn2(String rn2) {
		this.rn2 = rn2;
	}

	public String getRn() {
		return rn;
	}

	public void setRn(String rn) {
		this.rn = rn;
	}

	public String getCompanyPositionName() {
		return companyPositionName;
	}

	public void setCompanyPositionName(String companyPositionName) {
		this.companyPositionName = companyPositionName;
	}
	
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getWeek() {
		return week;
	}

	public void setWeek(String week) {
		this.week = week;
	}

	public String getWorkSeq() {
		return workSeq;
	}

	public void setWorkSeq(String workSeq) {
		this.workSeq = workSeq;
	}

	public String getWorkName() {
		return workName;
	}

	public void setWorkName(String workName) {
		this.workName = workName;
	}

	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String[] getSsnArray() {
		return ssnArray;
	}

	public void setSsnArray(String[] ssnArray) {
		this.ssnArray = ssnArray;
	}

	public String[] getNameArray() {
		return nameArray;
	}

	public void setNameArray(String[] nameArray) {
		this.nameArray = nameArray;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
