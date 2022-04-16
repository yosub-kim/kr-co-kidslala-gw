package kr.co.kmac.pms.schedule.data;

import java.util.List;

public class ScheduleDay {
	private String dayId;
	private String date;
	private String day;
	private int dayOfWeek;

	private List<DailyProjectInfo> dailyProjectLst;

	// MM형추가
	private List<DailyProjectInfo> dailyProjectLst_MM;
	private List<DailyProjectInfo> dailyProjectLst_PJT;

	private List<DailyScheduleInfo> dailyScheduleLst;
	private List<DailyScheduleInfo> dailyInternalScheduleLst;
	private List<DailyScheduleInfo> dailyExternalScheduleLst;
	private List<DailyScheduleInfo> dailyDayOffLst;
	private List<DailyScheduleInfo> dailyEducationLst;
	private List<HolidayInfo> holidayLst;
	private List<CustomerPickerInfo> customerPickerLst;
	private List<DailyScheduleInfo> dailyUpdayLst;
	private List<DailyScheduleInfo> dailyScheduleLst_time;

	private int dailyProjectLstCount = 0;
	private int dailyProjectLst_MMCount = 0;
	private int dailyScheduleLstCount = 0;
	private int dailyInternalScheduleLstCount = 0;
	private int dailyExternalScheduleLstCount = 0;
	private int dailyDayOffLstCount = 0;
	private int dailyEducationLstCount = 0;
	private int holidayLstCount = 0;
	private int customerPickerLstCount = 0;
	private int dailyUpdayLstCount = 0;
	private int dailyProjectLst_PJTCount = 0;
	private int dailyScheduleLst_timeCount = 0;

	public List<DailyScheduleInfo> getDailyScheduleLst_time() {
		return dailyScheduleLst_time;
	}

	public void setDailyScheduleLst_time(List<DailyScheduleInfo> dailyScheduleLst_time) {
		this.dailyScheduleLst_time = dailyScheduleLst_time;
	}

	public int getDailyScheduleLst_timeCount() {
		return dailyScheduleLst_timeCount;
	}

	public void setDailyScheduleLst_timeCount(int dailyScheduleLst_timeCount) {
		this.dailyScheduleLst_timeCount = dailyScheduleLst_timeCount;
	}

	public int getDailyProjectLst_PJTCount() {
		return dailyProjectLst_PJTCount;
	}

	public void setDailyProjectLst_PJTCount(int dailyProjectLst_PJTCount) {
		this.dailyProjectLst_PJTCount = dailyProjectLst_PJTCount;
	}

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

	public int getDayOfWeek() {
		return dayOfWeek;
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

	public void setDayOfWeek(int dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}

	public List<DailyProjectInfo> getDailyProjectLst() {
		return dailyProjectLst;
	}

	public void setDailyProjectLst(List<DailyProjectInfo> dailyProjectLst) {
		this.dailyProjectLst = dailyProjectLst;
	}

	// 일정관리에서 MM 투입일정 보이도록 변경
	public List<DailyProjectInfo> getDailyProjectLst_MM() {
		return dailyProjectLst_MM;
	}

	public void setDailyProjectLst_MM(List<DailyProjectInfo> dailyProjectLst_MM) {
		this.dailyProjectLst_MM = dailyProjectLst_MM;
	}

	// 일정관리에서 pjt게시판 보이도록 변경
	public List<DailyProjectInfo> getDailyProjectLst_PJT() {
		return dailyProjectLst_PJT;
	}

	public void setDailyProjectLst_PJT(List<DailyProjectInfo> dailyProjectLst_PJT) {
		this.dailyProjectLst_PJT = dailyProjectLst_PJT;
	}

	public List<DailyScheduleInfo> getDailyScheduleLst() {
		return dailyScheduleLst;
	}

	public void setDailyScheduleLst(List<DailyScheduleInfo> dailyScheduleLst) {
		this.dailyScheduleLst = dailyScheduleLst;
	}

	public List<DailyScheduleInfo> getDailyInternalScheduleLst() {
		return dailyInternalScheduleLst;
	}

	public void setDailyInternalScheduleLst(List<DailyScheduleInfo> dailyInternalScheduleLst) {
		this.dailyInternalScheduleLst = dailyInternalScheduleLst;
	}

	public List<DailyScheduleInfo> getDailyExternalScheduleLst() {
		return dailyExternalScheduleLst;
	}

	public void setDailyExternalScheduleLst(List<DailyScheduleInfo> dailyExternalScheduleLst) {
		this.dailyExternalScheduleLst = dailyExternalScheduleLst;
	}

	public List<HolidayInfo> getHolidayLst() {
		return holidayLst;
	}

	public void setHolidayLst(List<HolidayInfo> holidayLst) {
		this.holidayLst = holidayLst;
	}

	public int getDailyProjectLstCount() {
		return dailyProjectLstCount;
	}

	public void setDailyProjectLstCount(int dailyProjectLstCount) {
		this.dailyProjectLstCount = dailyProjectLstCount;
	}

	// MM형 지도일지 카운트 추가
	public int getDailyProjectLst_MMCount() {
		return dailyProjectLst_MMCount;
	}

	public void setDailyProjectLst_MMCount(int dailyProjectLst_MMCount) {
		this.dailyProjectLst_MMCount = dailyProjectLst_MMCount;
	}

	public int getDailyScheduleLstCount() {
		return this.dailyScheduleLstCount;
	}

	public void setDailyScheduleLstCount(int dailyScheduleLstCount) {
		this.dailyScheduleLstCount = dailyScheduleLstCount;
	}

	public int getDailyInternalScheduleLstCount() {
		return dailyInternalScheduleLstCount;
	}

	public void setDailyInternalScheduleLstCount(int dailyInternalScheduleLstCount) {
		this.dailyInternalScheduleLstCount = dailyInternalScheduleLstCount;
	}

	public int getDailyExternalScheduleLstCount() {
		return dailyExternalScheduleLstCount;
	}

	public void setDailyExternalScheduleLstCount(int dailyExternalScheduleLstCount) {
		this.dailyExternalScheduleLstCount = dailyExternalScheduleLstCount;
	}

	public int getHolidayLstCount() {
		return holidayLstCount;
	}

	public void setHolidayLstCount(int holidayLstCount) {
		this.holidayLstCount = holidayLstCount;
	}

	public List<DailyScheduleInfo> getDailyDayOffLst() {
		return dailyDayOffLst;
	}

	public void setDailyDayOffLst(List<DailyScheduleInfo> dailyDayOffLst) {
		this.dailyDayOffLst = dailyDayOffLst;
	}

	public List<DailyScheduleInfo> getDailyEducationLst() {
		return dailyEducationLst;
	}

	public void setDailyEducationLst(List<DailyScheduleInfo> dailyEducationLst) {
		this.dailyEducationLst = dailyEducationLst;
	}

	public int getDailyEducationLstCount() {
		return dailyEducationLstCount;
	}

	public void setDailyEducationLstCount(int dailyEducationLstCount) {
		this.dailyEducationLstCount = dailyEducationLstCount;
	}

	public List<CustomerPickerInfo> getCustomerPickerLst() {
		return customerPickerLst;
	}

	public void setCustomerPickerLst(List<CustomerPickerInfo> customerPickerLst) {
		this.customerPickerLst = customerPickerLst;
	}

	public int getDailyDayOffLstCount() {
		return dailyDayOffLstCount;
	}

	public void setDailyDayOffLstCount(int dailyDayOffLstCount) {
		this.dailyDayOffLstCount = dailyDayOffLstCount;
	}

	public List<DailyScheduleInfo> getDailyUpdayLst() {
		return dailyUpdayLst;
	}

	public void setDailyUpdayLst(List<DailyScheduleInfo> dailyUpdayLst) {
		this.dailyUpdayLst = dailyUpdayLst;
	}

	public int getDailyUpdayLstCount() {
		return dailyUpdayLstCount;
	}

	public void setDailyUpdayLstCount(int dailyUpdayLstCount) {
		this.dailyUpdayLstCount = dailyUpdayLstCount;
	}

	public int getCustomerPickerLstCount() {
		return customerPickerLstCount;
	}

	public void setCustomerPickerLstCount(int customerPickerLstCount) {
		this.customerPickerLstCount = customerPickerLstCount;
	}
}