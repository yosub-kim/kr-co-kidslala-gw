package kr.co.kmac.pms.project.master.data;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class ProjectMemberMMInfo {

	private String projectCode;
	private String ssn;
	private String[] year;
	private String[] month;
	private String[] mmValue;
	private String[] trainingYN;
	private String[] checkYN;

	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public String[] getYear() {
		return year;
	}

	public void setYear(String[] year) {
		this.year = year;
	}

	public String[] getMonth() {
		return month;
	}

	public void setMonth(String[] month) {
		this.month = month;
	}

	public String[] getMmValue() {
		return mmValue;
	}

	public void setMmValue(String[] mmValue) {
		this.mmValue = mmValue;
	}

	public String[] getTrainingYN() {
		return trainingYN;
	}

	public void setTrainingYN(String[] trainingYN) {
		this.trainingYN = trainingYN;
	}

	public String[] getCheckYN() {
		return checkYN;
	}

	public void setCheckYN(String[] checkYN) {
		this.checkYN = checkYN;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
