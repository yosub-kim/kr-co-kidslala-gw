package kr.co.kmac.pms.companySchedule.data;

import java.util.List;

import kr.co.kmac.pms.schedule.data.HolidayInfo;

public class ScheduleDay {
	private String dayId;
	private String date;
	private String day;
	private int dayOfWeek;

	private List<CompanyScheduleInfoData> dailyScheduleLst;
	private List<HolidayInfo> holidayLst;

	public String getDayId() {
		return dayId;
	}

	public void setDayId(String dayId) {
		this.dayId = dayId;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public List<CompanyScheduleInfoData> getDailyScheduleLst() {
		return dailyScheduleLst;
	}

	public void setDailyScheduleLst(List<CompanyScheduleInfoData> dailyScheduleLst) {
		this.dailyScheduleLst = dailyScheduleLst;
	}

	public List<HolidayInfo> getHolidayLst() {
		return holidayLst;
	}

	public void setHolidayLst(List<HolidayInfo> holidayInfo) {
		this.holidayLst = holidayInfo;
	}

	public int getDayOfWeek() {
		return dayOfWeek;
	}

	public void setDayOfWeek(int dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}

	public String getDayOfWeekStr() {
		if (this.dayOfWeek >= 0) {
			switch (this.dayOfWeek) {
			case 0:
				return "sun";
			case 1:
				return "mon";
			case 2:
				return "tue";
			case 3:
				return "wed";
			case 4:
				return "thu";
			case 5:
				return "fri";
			case 6:
				return "sat";
			default:
				return "sun";
			}
		} else {
			return "sun";
		}
	}
}