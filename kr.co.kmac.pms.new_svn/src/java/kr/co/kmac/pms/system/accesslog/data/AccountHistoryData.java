package kr.co.kmac.pms.system.accesslog.data;

public class AccountHistoryData {
	String seq;
	String type;
	String userId;
	String ssn;
	String name;
	String jobClassName;
	String deptName;
	String companyPositionName;
	String menuRoleName;
	String date;
	String etc;
	
	public String getSeq() {
		return seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
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
	public String getJobClassName() {
		return jobClassName;
	}
	public void setJobClassName(String jobClassName) {
		this.jobClassName = jobClassName;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getCompanyPositionName() {
		return companyPositionName;
	}
	public void setCompanyPositionName(String companyPositionName) {
		this.companyPositionName = companyPositionName;
	}
	public String getMenuRoleName() {
		return menuRoleName;
	}
	public void setMenuRoleName(String menuRoleName) {
		this.menuRoleName = menuRoleName;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getEtc() {
		return etc;
	}
	public void setEtc(String etc) {
		this.etc = etc;
	} 
}