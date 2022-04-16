package kr.co.kmac.pms.schedule.data;

public class DailyProjectInfo {
	private String projectCode;
	private String year;
	private String month;
	private String day;
	private String chargeSsn;
	private String time;
	private String projectName;
	private String customerName;
	private String relationUser;
	
	
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
	public String getChargeSsn() {
		return chargeSsn;
	}
	public void setChargeSsn(String chargeSsn) {
		this.chargeSsn = chargeSsn;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}	
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getRelationUser() {
		return relationUser;
	}
	public void setRelationUser(String relationUser) {
		this.relationUser = relationUser;
	}
}