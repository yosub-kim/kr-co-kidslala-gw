package kr.co.kmac.pms.project.statistics.data;

import java.util.Date;

public class SalaryMailingList {

	private String type;
	private String year;
	private String month;
	private String projectCode;
	private String projectName;
	private String jobclass;
	private String email;
	private String projectCount;
	private String jobClassDesc;
	private String name;
	private String ssn;
	private String uid;
	private String company;
	private long realTimeSalaryEachProject;
	private long totalRealTimeSalary;
	private String emailSeq;
	private Date emailSendDate;
	private String emailSendYN;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
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
	public String getProjectCode() {
		return projectCode;
	}
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getJobclass() {
		return jobclass;
	}
	public void setJobclass(String jobclass) {
		this.jobclass = jobclass;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getProjectCount() {
		return projectCount;
	}
	public void setProjectCount(String projectCount) {
		this.projectCount = projectCount;
	}
	public String getJobClassDesc() {
		return jobClassDesc;
	}
	public void setJobClassDesc(String jobClassDesc) {
		this.jobClassDesc = jobClassDesc;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSsn() {
		return ssn;
	}
	public void setSsn(String ssn) {
		this.ssn = ssn;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public long getRealTimeSalaryEachProject() {
		return realTimeSalaryEachProject;
	}
	public void setRealTimeSalaryEachProject(long realTimeSalaryEachProject) {
		this.realTimeSalaryEachProject = realTimeSalaryEachProject;
	}
	public long getTotalRealTimeSalary() {
		return totalRealTimeSalary;
	}
	public void setTotalRealTimeSalary(long totalRealTimeSalary) {
		this.totalRealTimeSalary = totalRealTimeSalary;
	}
	public String getEmailSeq() {
		return emailSeq;
	}
	public void setEmailSeq(String emailSeq) {
		this.emailSeq = emailSeq;
	}
	public Date getEmailSendDate() {
		return emailSendDate;
	}
	public void setEmailSendDate(Date emailSendDate) {
		this.emailSendDate = emailSendDate;
	}
	public String getEmailSendYN() {
		return emailSendYN;
	}
	public void setEmailSendYN(String emailSendYN) {
		this.emailSendYN = emailSendYN;
	}
}