package kr.co.kmac.pms.schedule.data;

public class DailyScheduleInfo {
	private String ssn;
	private String year;
	private String month;
	private String day;
	private int seq;
	private String startHour;
	private String startMin;
	private String endHour;
	private String endMin;
	private String type;
	private String content;
	private String customerName;
	private String relationUser;
	private String place;
	private String secretYN;

	private String googleSyncId;
	private String workType;
	private String cnt;
	private String dept;
	
	private String viewSsn;
	private String scheduleSsn;
	private String scheduleDate;
	private String viewIp;
	private String ssnCnt;
	
	public String getSsnCnt() {
		return ssnCnt;
	}

	public void setSsnCnt(String ssnCnt) {
		this.ssnCnt = ssnCnt;
	}

	public String getViewIp() {
		return viewIp;
	}

	public void setViewIp(String viewIp) {
		this.viewIp = viewIp;
	}

	public String getViewSsn() {
		return viewSsn;
	}

	public void setViewSsn(String viewSsn) {
		this.viewSsn = viewSsn;
	}

	public String getScheduleSsn() {
		return scheduleSsn;
	}

	public void setScheduleSsn(String scheduleSsn) {
		this.scheduleSsn = scheduleSsn;
	}

	public String getScheduleDate() {
		return scheduleDate;
	}

	public void setScheduleDate(String scheduleDate) {
		this.scheduleDate = scheduleDate;
	}
	
	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}
	
	public String getCnt() {
		return cnt;
	}

	public void setCnt(String cnt) {
		this.cnt = cnt;
	}

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

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public String getStartHour() {
		return startHour;
	}

	public void setStartHour(String startHour) {
		this.startHour = startHour;
	}

	public String getStartMin() {
		return startMin;
	}

	public void setStartMin(String startMin) {
		this.startMin = startMin;
	}

	public String getEndHour() {
		return endHour;
	}

	public void setEndHour(String endHour) {
		this.endHour = endHour;
	}

	public String getEndMin() {
		return endMin;
	}

	public void setEndMin(String endMin) {
		this.endMin = endMin;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getSecretYN() {
		return secretYN;
	}

	public void setSecretYN(String secretYN) {
		this.secretYN = secretYN;
	}

	public String getGoogleSyncId() {
		return googleSyncId;
	}

	public void setGoogleSyncId(String googleSyncId) {
		this.googleSyncId = googleSyncId;
	}

	public String getWorkType() {
		return workType;
	}

	public void setWorkType(String workType) {
		this.workType = workType;
	}

}