package kr.co.kmac.pms.common.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

/**
 * <b>���</b> : <p>��¥ �� �ð��� �ý������κ��� �����ϴ� Ŭ�����Դϴ�. 
 *
 * @author Administrator
 * @since 1.0
 * @see java.util.Date
 */
/**
 * @author Nam
 * 
 */
public class DateUtil {

	public static final int YEAR = 1;
	public static final int MONTH = 2;
	public static final int DATE = 3;
	public static final int MONTHFIRST = 4;
	public static final int MONTHEND = 5;

	/**
	 * <p>
	 * ���� ��¥�� �ð��� yyyyMMdd ���·� ��ȯ �� return.
	 * 
	 * @param null
	 * @return yyyyMMdd
	 * 
	 *         <pre>
	 *  - ��� ��
	 * String date = DateUtil.getYyyymmdd()
	 * </pre>
	 */
	public static String getYyyymmdd(Calendar cal) {
		Locale currentLocale = new Locale("KOREAN", "KOREA");
		String pattern = "yyyyMMdd";
		SimpleDateFormat formatter = new SimpleDateFormat(pattern, currentLocale);
		return formatter.format(cal.getTime());
	}

	/**
	 * <p>
	 * GregorianCalendar ��ü�� ��ȯ��.
	 * 
	 * @param yyyymmdd ��¥ �μ�
	 * @return GregorianCalendar
	 * @see java.util.Calendar
	 * @see java.util.GregorianCalendar <p>
	 * 
	 *      <pre>
	 *  - ��� ��
	 * Calendar cal = DateUtil.getGregorianCalendar(DateUtil.getCurrentYyyymmdd())
	 * </pre>
	 */
	public static GregorianCalendar getGregorianCalendar(String yyyymmdd) {

		int yyyy = Integer.parseInt(yyyymmdd.substring(0, 4));
		int mm = Integer.parseInt(yyyymmdd.substring(4, 6));
		int dd = Integer.parseInt(yyyymmdd.substring(6));

		GregorianCalendar calendar = new GregorianCalendar(yyyy, mm - 1, dd, 0, 0, 0);

		return calendar;

	}

	/**
	 * <p>
	 * ���� ��¥�� �ð��� yyyyMMddhhmmss ���·� ��ȯ �� return.
	 * 
	 * @param null
	 * @return yyyyMMddhhmmss
	 * @see java.util.Date
	 * @see java.util.Locale <p>
	 * 
	 *      <pre>
	 *  - ��� ��
	 * String date = DateUtil.getCurrentDateTime()
	 * </pre>
	 */
	public static String getCurrentDateTime() {
		Date today = new Date();
		Locale currentLocale = new Locale("KOREAN", "KOREA");
		String pattern = "yyyyMMddHHmmss";
		SimpleDateFormat formatter = new SimpleDateFormat(pattern, currentLocale);
		return formatter.format(today);
	}

	/**
	 * <p>
	 * ���� �ð��� hhmmss ���·� ��ȯ �� return.
	 * 
	 * @param null
	 * @return hhmmss
	 * @see java.util.Date
	 * @see java.util.Locale <p>
	 * 
	 *      <pre>
	 *  - ��� ��
	 *   String date = DateUtil.getCurrentDateTime()
	 * </pre>
	 */
	public static String getCurrentTime() {
		Date today = new Date();
		Locale currentLocale = new Locale("KOREAN", "KOREA");
		String pattern = "HHmmss";
		SimpleDateFormat formatter = new SimpleDateFormat(pattern, currentLocale);
		return formatter.format(today);

	}

	/**
	 * <p>
	 * ���� ��¥�� yyyyMMdd ���·� ��ȯ �� return.
	 * 
	 * @param null
	 * @return yyyyMMdd *
	 *         <p>
	 * 
	 *         <pre>
	 *  - ��� ��
	 * String date = DateUtil.getCurrentYyyymmdd()
	 * </pre>
	 */
	public static String getCurrentYyyymmdd() {
		return getCurrentDateTime().substring(0, 8);
	}

	/**
	 * <p>
	 * �ַ� ���ڸ� ���ϴ� �޼ҵ�.
	 * 
	 * @param yyyymm ���
	 * @param week ���° ��
	 * @param pattern ���ϵǴ� ��¥���� (ex:yyyyMMdd)
	 * @return ����� ��¥
	 * @see java.util.Calendar <p>
	 * 
	 *      <pre>
	 *  - ��� ��
	 * String date = DateUtil.getWeekToDay(&quot;200801&quot; , 1, &quot;yyyyMMdd&quot;)
	 * </pre>
	 */
	public static String getWeekToDay(String yyyymm, int week, String pattern) {

		Calendar cal = Calendar.getInstance(Locale.FRANCE);

		int new_yy = Integer.parseInt(yyyymm.substring(0, 4));
		int new_mm = Integer.parseInt(yyyymm.substring(4, 6));
		int new_dd = 1;

		cal.set(new_yy, new_mm - 1, new_dd);

		// �ӽ� �ڵ�
		if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
			week = week - 1;
		}

		cal.add(Calendar.DATE, (week - 1) * 7 + (cal.getFirstDayOfWeek() - cal.get(Calendar.DAY_OF_WEEK)));

		SimpleDateFormat formatter = new SimpleDateFormat(pattern, Locale.FRANCE);

		return formatter.format(cal.getTime());

	}

	/**
	 * <p>
	 * ������ �÷��׿� ���� ���� , �� , ���ڸ� �����Ѵ�.
	 * 
	 * @param field ���� �ʵ�
	 * @param amount ���� ��
	 * @param date ���� ��� ��¥
	 * @return ����� ��¥
	 * @see java.util.Calendar <p>
	 * 
	 *      <pre>
	 *  - ��� ��
	 * String date = DateUtil.getOpDate(java.util.Calendar.DATE , 1, &quot;20080101&quot;)
	 * </pre>
	 */
	public static String getOpDate(int field, int amount, String date) {

		GregorianCalendar calDate = getGregorianCalendar(date);

		if (field == Calendar.YEAR) {
			calDate.add(GregorianCalendar.YEAR, amount);
		} else if (field == Calendar.MONTH) {
			calDate.add(GregorianCalendar.MONTH, amount);
		} else {
			calDate.add(GregorianCalendar.DATE, amount);
		}

		return getYyyymmdd(calDate);

	}

	/**
	 * <p>
	 * �Էµ� ���ڸ� ���� �ָ� ���Ͽ� return�Ѵ�
	 * 
	 * @param yyyymmdd �⵵��
	 * @param addDay �߰���
	 * @return ����� ��
	 * @see java.util.Calendar <p>
	 * 
	 *      <pre>
	 *  - ��� ��
	 * int date = DateUtil.getWeek(DateUtil.getCurrentYyyymmdd() , 0)
	 * </pre>
	 */
	public static int getWeek(String yyyymmdd, int addDay) {
		Calendar cal = Calendar.getInstance(Locale.FRANCE);
		int new_yy = Integer.parseInt(yyyymmdd.substring(0, 4));
		int new_mm = Integer.parseInt(yyyymmdd.substring(4, 6));
		int new_dd = Integer.parseInt(yyyymmdd.substring(6, 8));

		cal.set(new_yy, new_mm - 1, new_dd);
		cal.add(Calendar.DATE, addDay);

		int week = cal.get(Calendar.DAY_OF_WEEK);
		return week;
	}

	/**
	 * <p>
	 * �Էµ� ����� ������ �ϼ��� return �Ѵ�.
	 * 
	 * @param year
	 * @param month
	 * @return ������ �ϼ�
	 * @see java.util.Calendar <p>
	 * 
	 *      <pre>
	 *  - ��� ��
	 * int date = DateUtil.getLastDayOfMon(2008 , 1)
	 * </pre>
	 */
	public static int getLastDayOfMon(int year, int month) {

		Calendar cal = Calendar.getInstance();
		cal.set(year, month, 1);
		return cal.getActualMaximum(Calendar.DAY_OF_MONTH);

	}// :

	/**
	 * <p>
	 * �Էµ� ����� ������ �ϼ��� return�Ѵ�
	 * 
	 * @param year
	 * @param month
	 * @return ������ �ϼ�
	 *         <p>
	 * 
	 *         <pre>
	 *  - ��� ��
	 * int date = DateUtil.getLastDayOfMon(&quot;2008&quot;)
	 * </pre>
	 */
	public static int getLastDayOfMon(String yyyymm) {

		Calendar cal = Calendar.getInstance();
		int yyyy = Integer.parseInt(yyyymm.substring(0, 4));
		int mm = Integer.parseInt(yyyymm.substring(4)) - 1;

		cal.set(yyyy, mm, 1);
		return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	/**
	 * <p>
	 * �Էµ� ���ڰ� �ùٸ��� Ȯ���մϴ�.
	 * 
	 * @param yyyymmdd
	 * @return boolean
	 *         <p>
	 * 
	 *         <pre>
	 *  - ��� ��
	 * boolean b = DateUtil.isCorrect(&quot;20080101&quot;)
	 * </pre>
	 */
	public static boolean isCorrect(String yyyymmdd) {
		boolean flag = false;
		if (yyyymmdd.length() < 8)
			return false;
		try {
			int yyyy = Integer.parseInt(yyyymmdd.substring(0, 4));
			int mm = Integer.parseInt(yyyymmdd.substring(4, 6));
			int dd = Integer.parseInt(yyyymmdd.substring(6));
			flag = DateUtil.isCorrect(yyyy, mm, dd);
		} catch (Exception ex) {
			return false;
		}
		return flag;
	}// :

	/**
	 * <p>
	 * �Էµ� ���ڰ� �ùٸ� �������� Ȯ���մϴ�.
	 * 
	 * @param yyyy
	 * @param mm
	 * @param dd
	 * @return boolean
	 *         <p>
	 * 
	 *         <pre>
	 *  - ��� ��
	 * boolean b = DateUtil.isCorrect(2008,1,1)
	 * </pre>
	 */
	public static boolean isCorrect(int yyyy, int mm, int dd) {
		if (yyyy < 0 || mm < 0 || dd < 0)
			return false;
		if (mm > 12 || dd > 31)
			return false;

		String year = "" + yyyy;
		String month = "00" + mm;
		String year_str = year + month.substring(month.length() - 2);
		int endday = DateUtil.getLastDayOfMon(year_str);

		if (dd > endday)
			return false;

		return true;

	}// :

	/**
	 * <p>
	 * ���� ���ڸ� �Էµ� type�� ��¥�� ��ȯ�մϴ�.
	 * 
	 * @param type
	 * @return String
	 * @see java.text.DateFormat <p>
	 * 
	 *      <pre>
	 *  - ��� ��
	 * String date = DateUtil.getThisDay(&quot;yyyymmddhhmmss&quot;)
	 * </pre>
	 */
	public static String getThisDay(String type) {
		Date date = new Date();
		SimpleDateFormat sdf = null;

		try {
			if (type.toLowerCase().equals("yyyy-mm-dd")) {
				sdf = new SimpleDateFormat("yyyy-MM-dd");
				return sdf.format(date);
			}
			if (type.toLowerCase().equals("yyyymmdd")) {
				sdf = new SimpleDateFormat("yyyyMMdd");
				return sdf.format(date);
			}
			if (type.toLowerCase().equals("yyyymmddhh")) {
				sdf = new SimpleDateFormat("yyyyMMddHH");
				return sdf.format(date);
			}
			if (type.toLowerCase().equals("yyyymmddhhmm")) {
				sdf = new SimpleDateFormat("yyyyMMddHHmm");
				return sdf.format(date);
			}
			if (type.toLowerCase().equals("yyyymmddhhmmss")) {
				sdf = new SimpleDateFormat("yyyyMMddHHmmss");
				return sdf.format(date);
			}
			if (type.toLowerCase().equals("yyyymmddhhmmssms")) {
				sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
				return sdf.format(date);
			} else {
				sdf = new SimpleDateFormat(type);
				return sdf.format(date);
			}
		} catch (Exception e) {
			return "[ ERROR ]: parameter must be 'YYYYMMDD', 'YYYYMMDDHH', 'YYYYMMDDHHSS'or 'YYYYMMDDHHSSMS'";
		}
	}

	public static String getThisDay(Date date, String type) {
		SimpleDateFormat sdf = null;

		try {
			if (type.toLowerCase().equals("yyyy-mm-dd")) {
				sdf = new SimpleDateFormat("yyyy-MM-dd");
				return sdf.format(date);
			}
			if (type.toLowerCase().equals("yyyymmdd")) {
				sdf = new SimpleDateFormat("yyyyMMdd");
				return sdf.format(date);
			}
			if (type.toLowerCase().equals("yyyymmddhh")) {
				sdf = new SimpleDateFormat("yyyyMMddHH");
				return sdf.format(date);
			}
			if (type.toLowerCase().equals("yyyymmddhhmm")) {
				sdf = new SimpleDateFormat("yyyyMMddHHmm");
				return sdf.format(date);
			}
			if (type.toLowerCase().equals("yyyymmddhhmmss")) {
				sdf = new SimpleDateFormat("yyyyMMddHHmmss");
				return sdf.format(date);
			}
			if (type.toLowerCase().equals("yyyymmddhhmmssms")) {
				sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
				return sdf.format(date);
			} else {
				sdf = new SimpleDateFormat(type);
				return sdf.format(date);
			}
		} catch (Exception e) {
			return "[ ERROR ]: parameter must be 'YYYYMMDD', 'YYYYMMDDHH', 'YYYYMMDDHHSS'or 'YYYYMMDDHHSSMS'";
		}
	}

	/**
	 * <p>
	 * �Էµ� ���ڸ� '9999�� 99�� 99��' ���·� ��ȯ�Ͽ� ��ȯ�Ѵ�.
	 * 
	 * @param yyyymmdd
	 * @return String
	 *         <p>
	 * 
	 *         <pre>
	 *  - ��� ��
	 * String date = DateUtil.changeDateFormat(&quot;20080101&quot;)
	 * </pre>
	 */
	public static String changeDateFormat(String yyyymmdd) {
		String rtnDate = null;

		String yyyy = yyyymmdd.substring(0, 4);
		String mm = yyyymmdd.substring(4, 6);
		String dd = yyyymmdd.substring(6, 8);
		rtnDate = yyyy + " �� " + mm + " �� " + dd + " ��";

		return rtnDate;

	}

	/**
	 * <p>
	 * �� ��¥���� ��¥���� ��ȯ(������ ������)
	 * 
	 * @param startDate ���� ��¥
	 * @param endDate �� ��¥
	 * @return ����
	 * @see java.util.GregorianCalendar <p>
	 * 
	 *      <pre>
	 *  - ��� ��
	 * long date = DateUtil.getDifferDays('20080101','20080202')
	 * </pre>
	 */
	public static long getDifferDays(String startDate, String endDate) {

		GregorianCalendar StartDate = getGregorianCalendar(startDate);
		GregorianCalendar EndDate = getGregorianCalendar(endDate);
		long difer = (EndDate.getTime().getTime() - StartDate.getTime().getTime()) / 86400000;
		return difer;

	}

	public static long getDifferDays(Calendar startDate, Calendar endDate) {
		if ((endDate.getTime().getTime() - startDate.getTime().getTime()) < 0)
			return -1;
		long difer = ((endDate.getTime().getTime() - startDate.getTime().getTime()) / 86400000);
		return difer;
	}

	/**
	 * <p>
	 * ������ ������ ���Ѵ�.
	 * 
	 * @param
	 * @return ����
	 * @see java.util.Calendar <p>
	 * 
	 *      <pre>
	 *  - ��� ��
	 * int day = DateUtil.getDayOfWeek()
	 *  SUNDAY    = 1
	 *  MONDAY    = 2
	 *  TUESDAY   = 3
	 *  WEDNESDAY = 4
	 *  THURSDAY  = 5
	 *  FRIDAY    = 6
	 * </pre>
	 */
	public static int getDayOfWeek() {
		Calendar rightNow = Calendar.getInstance();
		int day_of_week = rightNow.get(Calendar.DAY_OF_WEEK);
		return day_of_week;
	}

	/**
	 * <p>
	 * �����ְ� ���� ��ü�� ��°�ֿ� �ش�Ǵ��� ����Ѵ�.
	 * 
	 * @param
	 * @return ����
	 * @see java.util.Calendar <p>
	 * 
	 *      <pre>
	 *  - ��� ��
	 * int day = DateUtil.getWeekOfYear()
	 * </pre>
	 */
	public static int getWeekOfYear() {
		Locale LOCALE_COUNTRY = Locale.KOREA;
		Calendar rightNow = Calendar.getInstance(LOCALE_COUNTRY);
		int week_of_year = rightNow.get(Calendar.WEEK_OF_YEAR);
		return week_of_year;
	}

	/**
	 * <p>
	 * �����ְ� ������� ��°�ֿ� �ش�Ǵ��� ����Ѵ�.
	 * 
	 * @param
	 * @return ����
	 * @see java.util.Calendar <p>
	 * 
	 *      <pre>
	 *  - ��� ��
	 * int day = DateUtil.getWeekOfMonth()
	 * </pre>
	 */
	public static int getWeekOfMonth() {
		Locale LOCALE_COUNTRY = Locale.KOREA;
		Calendar rightNow = Calendar.getInstance(LOCALE_COUNTRY);
		int week_of_month = rightNow.get(Calendar.WEEK_OF_MONTH);
		return week_of_month;
	}

	/**
	 * <p>
	 * �ش� p_date��¥�� Calendar ��ü�� ��ȯ��.
	 * 
	 * @param p_date
	 * @return Calendar
	 * @see java.util.Calendar <p>
	 * 
	 *      <pre>
	 *  - ��� ��
	 * Calendar cal = DateUtil.getCalendarInstance(DateUtil.getCurrentYyyymmdd())
	 * </pre>
	 */
	public static Calendar getCalendarInstance(String p_date) {
		// Locale LOCALE_COUNTRY = Locale.KOREA;
		Locale LOCALE_COUNTRY = Locale.FRANCE;
		Calendar retCal = Calendar.getInstance(LOCALE_COUNTRY);

		if (p_date != null && p_date.length() == 8) {
			int year = Integer.parseInt(p_date.substring(0, 4));
			int month = Integer.parseInt(p_date.substring(4, 6)) - 1;
			int date = Integer.parseInt(p_date.substring(6));

			retCal.set(year, month, date);
		}
		return retCal;
	}

	/**
	 * yyyy-MM-dd hh:mm:ss ������ ��Ʈ���� DAte��ü�� ����
	 * 
	 * @param dateString
	 * @return
	 */
	public static String getDateTimeByPattern(String patternString) {
		try {
			return new java.text.SimpleDateFormat(patternString).format(new Date());
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public static Date getDateTime(String dateString) {
		Date date = null;

		try {
			if (dateString != null && dateString.length() > 0) {
				date = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(dateString);
			}
			return date;

		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public static Date getDateTime(String pattern, String dateString) {
		Date date = null;

		try {
			if (dateString != null && dateString.length() > 0) {
				date = new java.text.SimpleDateFormat(pattern).parse(dateString);
			}
			return date;

		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public static List<String> getEveryMonth(String fromDate, String toDate) {
		List<String> list = new ArrayList<String>();
		Calendar fromCalendar = DateUtil.getCalendarInstance(fromDate);
		Calendar toCalendar = DateUtil.getCalendarInstance(toDate);
		while (fromCalendar.getTimeInMillis() < toCalendar.getTimeInMillis()) {
			fromCalendar.add(Calendar.MONTH, 1);
			list.add(new SimpleDateFormat("yyyy-MM-dd").format(fromCalendar.getTime()));
		}
		return list;
	}

	public static List<String> getEveryOhterMonth(String fromDate, String toDate) {
		List<String> list = new ArrayList<String>();
		Calendar fromCalendar = DateUtil.getCalendarInstance(fromDate);
		Calendar toCalendar = DateUtil.getCalendarInstance(toDate);
		while (fromCalendar.getTimeInMillis() < toCalendar.getTimeInMillis()) {
			fromCalendar.add(Calendar.MONTH, 2);
			list.add(new SimpleDateFormat("yyyy-MM-dd").format(fromCalendar.getTime()));
		}
		return list;
	}
}
