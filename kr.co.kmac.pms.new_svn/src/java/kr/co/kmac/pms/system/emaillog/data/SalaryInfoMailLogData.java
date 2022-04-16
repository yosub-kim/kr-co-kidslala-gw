package kr.co.kmac.pms.system.emaillog.data;

import java.util.Date;

public class SalaryInfoMailLogData {
	private String ssn;
	private String year;
	private String month;
	private Date sendDateTime;
	
	public String getSsn() {
		return ssn;
	}
	public void setSsn(String ssn) {
		this.ssn = ssn;
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
	public Date getSendDateTime() {
		return sendDateTime;
	}
	public void setSendDateTime(Date sendDateTime) {
		this.sendDateTime = sendDateTime;
	}
}