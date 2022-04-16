package kr.co.kmac.pms.schedule.data;

public class DateInfo {
	private String year;
	private String month;
	private String day;
	private String dayOfTheWeek;
	private String dayOfTheWeekKor;
	private boolean isHoliday;
	private boolean isToday;
	private String holidayName;

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

	public String getDayOfTheWeek() {
		return dayOfTheWeek;
	}

	public void setDayOfTheWeek(String dayOfTheWeek) {
		this.dayOfTheWeek = dayOfTheWeek;
	}

	public String getDayOfTheWeekKor() {
		return dayOfTheWeekKor;
	}

	public void setDayOfTheWeekKor(String dayOfTheWeekKor) {
		this.dayOfTheWeekKor = dayOfTheWeekKor;
	}

	public boolean isHoliday() {
		return isHoliday;
	}

	public void setHoliday(boolean isHoliday) {
		this.isHoliday = isHoliday;
	}

	public boolean isToday() {
		return isToday;
	}

	public void setToday(boolean isToday) {
		this.isToday = isToday;
	}

	public String getHolidayName() {
		return holidayName;
	}

	public void setHolidayName(String holidayName) {
		this.holidayName = holidayName;
	}

}