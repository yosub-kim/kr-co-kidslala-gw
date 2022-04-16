/*
 * $Id: DateTime.java,v 1.2 2010/12/22 02:28:08 cvs Exp $
 * creation-date : 2004. 8. 19.
 * =========================================================
 * Copyright (c) 2004 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.common.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.StringTokenizer;

/**
 * DateTime Class ��뿹
 * 
 * <table>
 * <tr>
 * <td>���ش�</td>
 * <td>DateTime.getYear()</td>
 * </tr>
 * <tr>
 * <td>�̹����� :DateTime.getMonth()</td>
 * </tr>
 * <tr>
 * <td>������ :DateTime.getDay()</td>
 * </tr>
 * <tr>
 * <td>������ :DateTime.getDayOfWeek()</td>
 * </tr>
 * <tr>
 * <td>����ð� :DateTime.getHour()</td>
 * </tr>
 * <tr>
 * <td>����� :DateTime.getMinute()</td>
 * </tr>
 * <tr>
 * <td>���� �� :DateTime.getSecond()</td>
 * </tr>
 * <tr>
 * <td>���� ��¥ :DateTime.getDateString()</td>
 * </tr>
 * <tr>
 * <td>���� ��¥ :DateTime.getDateString(".")</td>
 * </tr>
 * <tr>
 * <td>���� ��¥ :DateTime.getDateString("/")</td>
 * </tr>
 * <tr>
 * <td>�ð���Ʈ�� :DateTime.getTimeString()</td>
 * </tr>
 * <tr>
 * <td>TimeStamp :DateTime.getTimeStampString()</td>
 * </tr>
 * <tr>
 * <td>DateTimeMin :DateTime.getDateTimeMin()</td>
 * </tr>
 * <tr>
 * <td>DateTimeString :DateTime.getDateTimeString()</td>
 * </tr>
 * <tr>
 * <td>�����Ǵ� :DateTime.checkEmbolism(2002)</td>
 * </tr>
 * <tr>
 * <td>�ϼ� ���ϱ� :DateTime.getMonthDate("2002","2")</td>
 * </tr>
 * </table>
 * 
 * @author JooYoul Lee
 * @version $Id: DateTime.java,v 1.2 2010/12/22 02:28:08 cvs Exp $
 */
public class DateTime {
	public static String dateSep = "-";
	public static String timeSep = ":";
	public static String dateSep_1 = ".";

	private static final String[] dayShortName = { "��", "��", "ȭ", "��", "��", "��", "��" };
	private static final String[] dayFullName = { "�Ͽ���", "������", "ȭ����", "������", "�����", "�ݿ���", "�����" };

	/**
	 * 
	 * For example , String time = DateTime.getFormatString("yyyy-MM-dd HH:mm:ss");
	 * 
	 * @param pattern "yyyy, MM, dd, HH, mm, ss and more"
	 * @return formatted string representation of current day and time with your pattern.
	 */
	public static String getNumberByPattern(String pattern) {
		SimpleDateFormat formatter = new SimpleDateFormat(pattern, Locale.KOREA);
		String dateString = formatter.format(new java.util.Date());
		return dateString;
	}

	/**
	 * ���� �⵵ ���ϴ� Method
	 * 
	 * @return
	 */
	public static String getYear() {
		return getNumberByPattern("yyyy");
	}

	/**
	 * ���� ���� ���ϴ� Method
	 * 
	 * @return
	 */
	public static String getMonth() {
		return getNumberByPattern("MM");
	}

	/**
	 * ���� ��¥�� ���ϴ� Method
	 * 
	 * @return
	 */
	public static String getDay() {
		return getNumberByPattern("dd");
	}

	/**
	 * ���� ������ ���ϴ� Method - ª���̸�
	 * 
	 * @return
	 */
	public static String getDayofWeek() {
		Calendar c = Calendar.getInstance();
		return dayShortName[c.get(java.util.Calendar.DAY_OF_WEEK) - 1];
	}

	/**
	 * ���� ������ ���ϴ� Method - ���̸�
	 * 
	 * @return
	 */
	public static String getDayofWeekFullName() {
		Calendar c = Calendar.getInstance();
		return dayFullName[c.get(java.util.Calendar.DAY_OF_WEEK) - 1];
	}

	/**
	 * ���� �ð��� �ð��� ���ϴ� Method
	 * 
	 * @return
	 */
	public static int getHour() {
		Calendar c = Calendar.getInstance();
		return c.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * ���� �ð��� ���� ���ϴ� Method
	 * 
	 * @return
	 */
	public static int getMinute() {
		Calendar c = Calendar.getInstance();
		return c.get(Calendar.MINUTE);
	}

	/**
	 * ���� �ð��� �ʸ� ���ϴ� Method
	 * 
	 * @return
	 */
	public static int getSecond() {
		Calendar c = Calendar.getInstance();
		return c.get(Calendar.SECOND);
	}

	/**
	 * YYYY-MM-DD ������ ��Ʈ���� ���ϴ� Method
	 * 
	 * @return
	 */
	public static String getDateString() {
		return getYear() + getMonth() + getDay();
	}

	/**
	 * YYYY-MM-DD ������ ��Ʈ���� pattern�� ���� ���ϴ� Method pattern ���� ���� ��� �ݿ�
	 * 
	 * @param pattern
	 * @return
	 */
	public static String getDateString(String pattern) {
		return getYear() + pattern + getMonth() + pattern + getDay();
	}

	/**
	 * HH:MI:SS ������ ��Ʈ���� ���ϴ� Method
	 * 
	 * @return
	 */
	public static String getTimeString() {
		String hour = getHour() + "";
		String min = getMinute() + "";
		String sec = getSecond() + "";

		if (hour.length() == 1) {
			hour = "0" + hour;
		}
		if (min.length() == 1) {
			min = "0" + min;
		}
		if (sec.length() == 1) {
			sec = "0" + sec;
		}

		return hour + timeSep + min + timeSep + sec;
	}

	/**
	 * YYYY-MM-DD HH:MI:SS ������ ��Ʈ���� ���ϴ� Method
	 * 
	 * @return
	 */
	public static String getTimeStampString() {
		return getDateString("-") + " " + getTimeString();
	}

	/**
	 * YYYYMMDDHHMI ������ ��Ʈ���� ���ϴ� Method
	 * 
	 * @return
	 */
	public static String getDateTimeMin() {
		String month = getMonth() + "";
		String day = getDay() + "";
		String hour = getHour() + "";
		String min = getMinute() + "";

		if (month.length() == 1) {
			month = "0" + month;
		}
		if (day.length() == 1) {
			day = "0" + day;
		}
		if (hour.length() == 1) {
			hour = "0" + hour;
		}
		if (min.length() == 1) {
			min = "0" + min;
		}
		return getYear() + "" + month + "" + day + "" + hour + "" + min;
	}

	/**
	 * ��/��/��/��/��/�� ��Ʈ���� ���ϴ� Method
	 * 
	 * @return
	 */
	public static String getDateTimeString() {
		String month = getMonth() + "";
		String day = getDay() + "";
		String hour = getHour() + "";
		String min = getMinute() + "";
		String sec = getSecond() + "";

		if (month.length() == 1) {
			month = "0" + month;
		}
		if (day.length() == 1) {
			day = "0" + day;
		}
		if (hour.length() == 1) {
			hour = "0" + hour;
		}
		if (min.length() == 1) {
			min = "0" + min;
		}
		return getYear() + "" + month + "" + day + "" + hour + "" + min + sec;
	}

	public static String getParseDateString(String dateTime, String pattern) {
		if (dateTime != null) {
			String year = dateTime.substring(0, 4);
			String month = dateTime.substring(4, 6);
			String day = dateTime.substring(6, 8);

			return year + pattern + month + pattern + day;

		} else {
			return "";

		}
	}

	/**
	 * �־��� �⵵�� �������� �Ǵ��ϴ� Method
	 * 
	 * @param year
	 * @return
	 */
	public static boolean checkEmbolism(int year) {
		int remain = 0;
		int remain_1 = 0;
		int remain_2 = 0;

		remain = year % 4;
		remain_1 = year % 100;
		remain_2 = year % 400;

		if (remain == 0) {
			if (remain_1 == 0) {
				if (remain_2 == 0)
					return true;
				else
					return false;
			} else
				return true;
		}

		return false;
	}

	/**
	 * �־��� ��,���� �ϼ��� ���ϴ� Method
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static int getMonthDate(String year, String month) {
		int[] dateMonth = new int[12];

		dateMonth[0] = 31;
		dateMonth[1] = 28;
		dateMonth[2] = 31;
		dateMonth[3] = 30;
		dateMonth[4] = 31;
		dateMonth[5] = 30;
		dateMonth[6] = 31;
		dateMonth[7] = 31;
		dateMonth[8] = 30;
		dateMonth[9] = 31;
		dateMonth[10] = 30;
		dateMonth[11] = 31;

		if (checkEmbolism(Integer.parseInt(year))) {
			dateMonth[1] = 29;
		}

		int day = dateMonth[Integer.parseInt(month) - 1];

		return day;
	}

	/**
	 * "YYYY-MM-DD" ������ ���ڿ��κ��� �� ���� ���� �ð��� �����Ѵ�.
	 * 
	 * @param formattedDateString "YYYY-MM-DD" ������ ��¥ ���ڿ�
	 * @return �ش� ���� 0�� 0�� 0�� 000�и��ʸ� ��Ÿ���� Date ��ü
	 */
	public static Date getStartDateTimeFromString(String formattedDateString) {
		Calendar cal = Calendar.getInstance();
		StringTokenizer st = new StringTokenizer(formattedDateString, "-");
		cal.set(Calendar.YEAR, Integer.parseInt((String) st.nextElement()));
		cal.set(Calendar.MONTH, Integer.parseInt((String) st.nextElement()) - 1);
		cal.set(Calendar.DATE, Integer.parseInt((String) st.nextElement()));
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		return new Date(cal.getTimeInMillis());
	}

	/**
	 * "YYYY-MM-DD" ������ ���ڿ��κ��� �� ���� ���� �ð��� �����Ѵ�.
	 * 
	 * @param formattedDateString "YYYY-MM-DD" ������ ��¥ ���ڿ�
	 * @return �ش� ���� 23�� 59�� 59�� 999 �и��ʸ� ��Ÿ���� Date ��ü
	 */
	public static Date getEndDateTimeFromString(String formattedDateString) {
		Calendar cal = Calendar.getInstance();
		StringTokenizer st = new StringTokenizer(formattedDateString, "-");
		cal.set(Calendar.YEAR, Integer.parseInt((String) st.nextElement()));
		cal.set(Calendar.MONTH, Integer.parseInt((String) st.nextElement()) - 1);
		cal.set(Calendar.DATE, Integer.parseInt((String) st.nextElement()));
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 999);

		return new Date(cal.getTimeInMillis());
	}

	public static boolean isInDateRange(Date compareDate, Date startDate, Date endDate) {
		boolean isLaterThanStart = true;
		boolean isEarlierThanEnd = true;

		if (compareDate != null) {
			if (startDate != null) {
				if (startDate.compareTo(compareDate) > 0)
					isLaterThanStart = false;
			}
			if (endDate != null) {
				if (endDate.compareTo(compareDate) < 0)
					isEarlierThanEnd = false;
			}

			if (isLaterThanStart && isEarlierThanEnd)
				return true;
			else
				return false;
		} else {
			return false;
		}
	}

	public static String getWeekOfDayString(int i) {
		String weekString = null;
		switch (i) {
		case 1:
			weekString = "SUN";
			break;
		case 2:
			weekString = "MON";
			break;
		case 3:
			weekString = "TUE";
			break;
		case 4:
			weekString = "WED";
			break;
		case 5:
			weekString = "THU";
			break;
		case 6:
			weekString = "FRI";
			break;
		case 7:
			weekString = "SAT";
			break;
		}
		return weekString;
	}
}