package kr.co.kmac.pms.schedule.data;

import java.util.List;

public class UserInfo {
	private String ssn;
	private String userName;
	
	private ScheduleDailyMasterInfo scheduleDailyMasterInfo;

	/**
	 * @deprecated
	 */
	private List<ScheduleDay> scheduleDay;

	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * @deprecated
	 */
	public List<ScheduleDay> getScheduleDay() {
		return scheduleDay;
	}
	/**
	 * @deprecated
	 */
	public void setScheduleDay(List<ScheduleDay> scheduleDay) {
		this.scheduleDay = scheduleDay;
	}

	public ScheduleDailyMasterInfo getScheduleDailyMasterInfo() {
		return scheduleDailyMasterInfo;
	}

	public void setScheduleDailyMasterInfo(
			ScheduleDailyMasterInfo scheduleDailyMasterInfo) {
		this.scheduleDailyMasterInfo = scheduleDailyMasterInfo;
	}	
	
	
}

