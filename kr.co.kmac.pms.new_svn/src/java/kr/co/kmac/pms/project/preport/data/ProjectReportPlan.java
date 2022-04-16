package kr.co.kmac.pms.project.preport.data;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class ProjectReportPlan {

	private String projectCode;// 프로젝트 코드
	private String year;// 년도
	private String month;// 월
	private String day;// 일
	private String week;// 요일
	private String workSeq;// 업무순서
	private String workName;// 업무순서
	private String outputName;// 업무순서
	private String ssn;// 주민번호
	private String name;// 주민번호
	private String time;// 강의시간
	private String role;
	private String companyPositionName;
	private String position;
	private String rn;
	private String rn2;

	private String[] ssnArray;// 주민번호
	private String[] nameArray;// 주민번호
	private String[] timeArray;// 강의시간

	public String getRn() {
		return rn;
	}

	public void setRn(String rn) {
		this.rn = rn;
	}

	public String getRn2() {
		return rn2;
	}

	public void setRn2(String rn2) {
		this.rn2 = rn2;
	}
	
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getCompanyPositionName() {
		return companyPositionName;
	}

	public void setCompanyPositionName(String companyPositionName) {
		this.companyPositionName = companyPositionName;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
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
	 * @return the year
	 */
	public String getYear() {
		return year;
	}

	/**
	 * @param year the year to set
	 */
	public void setYear(String year) {
		this.year = year;
	}

	/**
	 * @return the month
	 */
	public String getMonth() {
		return month;
	}

	/**
	 * @param month the month to set
	 */
	public void setMonth(String month) {
		this.month = month;
	}

	/**
	 * @return the day
	 */
	public String getDay() {
		return day;
	}

	/**
	 * @param day the day to set
	 */
	public void setDay(String day) {
		this.day = day;
	}

	/**
	 * @return the week
	 */
	public String getWeek() {
		return week;
	}

	/**
	 * @param week the week to set
	 */
	public void setWeek(String week) {
		this.week = week;
	}

	/**
	 * @return the workSeq
	 */
	public String getWorkSeq() {
		return workSeq;
	}

	/**
	 * @param workSeq the workSeq to set
	 */
	public void setWorkSeq(String workSeq) {
		this.workSeq = workSeq;
	}

	/**
	 * @return the workName
	 */
	public String getWorkName() {
		return workName;
	}

	/**
	 * @param workName the workName to set
	 */
	public void setWorkName(String workName) {
		this.workName = workName;
	}

	/**
	 * @return the outputName
	 */
	public String getOutputName() {
		return outputName;
	}

	/**
	 * @param outputName the outputName to set
	 */
	public void setOutputName(String outputName) {
		this.outputName = outputName;
	}

	/**
	 * @return the ssn
	 */
	public String getSsn() {
		return ssn;
	}

	/**
	 * @param ssn the ssn to set
	 */
	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the time
	 */
	public String getTime() {
		return time;
	}

	/**
	 * @param time the time to set
	 */
	public void setTime(String time) {
		this.time = time;
	}

	/**
	 * @return the ssnArray
	 */
	public String[] getSsnArray() {
		return ssnArray;
	}

	/**
	 * @param ssnArray the ssnArray to set
	 */
	public void setSsnArray(String[] ssnArray) {
		this.ssnArray = ssnArray;
	}

	/**
	 * @return the timeArray
	 */
	public String[] getTimeArray() {
		return timeArray;
	}

	/**
	 * @return the nameArray
	 */
	public String[] getNameArray() {
		return nameArray;
	}

	/**
	 * @param nameArray the nameArray to set
	 */
	public void setNameArray(String[] nameArray) {
		this.nameArray = nameArray;
	}

	/**
	 * @param timeArray the timeArray to set
	 */
	public void setTimeArray(String[] timeArray) {
		this.timeArray = timeArray;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
