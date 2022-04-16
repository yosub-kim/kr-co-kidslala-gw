package kr.co.kmac.pms.common.util;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
	private static String tmpRan = "";
	private static String strMiliSecNow = null;
	private static int seq = 10;
	private static java.util.Random rnd = new java.util.Random();
	private static String docMangeNo;

	/*******************************************************************************************************************
	 * �ѱ����� �ƴ��� �˻� �ϴ� �Լ�.
	 * 
	 * @param uni20 ���� ���ڿ�
	 * @return �ѱ��� ��� true
	 ******************************************************************************************************************/
	public static boolean checkHan(String uni20) {
		boolean result = false;

		if (uni20 == null)
			return result;

		int len = uni20.length();
		char c;
		for (int i = 0; i < len; i++) {
			c = uni20.charAt(i);
			if (c < 0xac00 || 0xd7a3 < c) {

			} else {
				result = true;
				break;
			}
		}
		return result;
	}

	/*******************************************************************************************************************
	 * ���ڿ� ġȯ �Լ�.
	 * 
	 * <pre>
	 * String source = &quot;abcdefghabcdefgh&quot;;
	 * String subject = &quot;cd&quot;;
	 * String object = &quot;1234&quot;;
	 * String rst = strUtil.replace(source, subject, object);
	 * //rst �� &quot;ab1234efghab1234efgh&quot; �� �ȴ�.
	 * </pre>
	 * 
	 * @param source ���� ���ڿ�
	 * @param subject �ٲ� ���ڿ�
	 * @param object �ٲ� ���ڿ�
	 * @return �ٲ� ���ڿ�
	 ******************************************************************************************************************/
	public static String replace(String source, String subject, String object) {
		if (source == null) {
			return source;
		}
		StringBuffer rtnStr = new StringBuffer();
		String preStr = "";
		String nextStr = source;
		while (source.indexOf(subject) >= 0) {
			preStr = source.substring(0, source.indexOf(subject));
			nextStr = source.substring(source.indexOf(subject) + subject.length(), source.length());
			source = nextStr;
			rtnStr.append(preStr).append(object);
		}
		rtnStr.append(nextStr);
		return rtnStr.toString();
	}

	public static String replaceOnce(String source, String subject, String object) {
		if (source == null) {
			return source;
		}
		StringBuffer rtnStr = new StringBuffer();
		String preStr = "";
		String nextStr = source;
		if (source.indexOf(subject) >= 0) {
			preStr = source.substring(0, source.indexOf(subject));
			nextStr = source.substring(source.indexOf(subject) + subject.length(), source.length());
			rtnStr.append(preStr).append(object).append(nextStr);
			return rtnStr.toString();
		} else {
			return source;
		}
	}

	// �ٲٰ��� �ϴ� ���ڿ��� ���������� ���� �� ���
	public static String replaceChar(String source, String subject, String object) {
		if (source == null) {
			return source;
		}
		StringBuffer rtnStr = new StringBuffer();
		String preStr = "";
		String nextStr = source;
		char chA;

		for (int i = 0; i < subject.length(); i++) {
			chA = subject.charAt(i);

			if (source.indexOf(chA) >= 0) {
				preStr = source.substring(0, source.indexOf(chA));
				nextStr = source.substring(source.indexOf(chA) + 1, source.length());
				source = rtnStr.append(preStr).append(object).append(nextStr).toString();
			}
		}
		return source;
	}

	/*******************************************************************************************************************
	 * ���ڿ��� CharSet�� unicode(8859_1) �� �ٲٴ� �Լ�.
	 * 
	 * <pre>
	 * String source = &quot;�����ٶ󸶹ٻ��&quot;;
	 * String rst = strUtil.str2uni(src);
	 * </pre>
	 * 
	 * @param source ���� ���ڿ�
	 * @return �����ڵ�� ��ȯ�� ���ڿ�
	 ******************************************************************************************************************/
	public static String toUnicode(String source) {
		String ret = null;
		if (source == null)
			return null;
		try {
			ret = new String(source.getBytes(), "8859_1");
		} catch (UnsupportedEncodingException e) {
			ret = null;
		}
		return ret;
	}

	public static String toUnicode2(String source) {
		String ret = null;
		if (source == null)
			return null;
		try {
			ret = new String(source.getBytes("KSC5601"), "8859_1");
		} catch (UnsupportedEncodingException e) {
			ret = null;
		}
		return ret;
	}

	/*******************************************************************************************************************
	 * ���ڿ��� CharSet�� EUC-KR(KSC5601) �� �ٲٴ� �Լ�.
	 * 
	 * <pre>
	 * String source = &quot;�����ٶ󸶹ٻ��&quot;;
	 * String rst = strUtil.str2uni(src);
	 * </pre>
	 * 
	 * @param source ���� ���ڿ�
	 * @return �����ڵ�� ��ȯ�� ���ڿ�
	 ******************************************************************************************************************/
	public static String toEuckr(String source) {
		String ret = null;
		if (source == null)
			return null;
		try {
			ret = new String(source.getBytes(), "KSC5601");
		} catch (UnsupportedEncodingException e) {
			ret = null;
		}
		return ret;
	}

	public static String toEuckr2(String source) {
		String ret = null;
		if (source == null)
			return null;
		try {
			ret = new String(source.getBytes("8859_1"), "KSC5601");
		} catch (UnsupportedEncodingException e) {
			ret = null;
		}
		return ret;
	}

	/*******************************************************************************************************************
	 * ���ڿ��� ���ϴ� ������ CharSet���� �ٲٴ� �Լ�.
	 * 
	 * <pre>
	 * String source = &quot;�����ٶ󸶹ٻ��&quot;;
	 * String rst = strUtil.Str2charset(src, &quot;ksc5601&quot;);
	 * </pre>
	 * 
	 * @param source ���� ���ڿ�
	 * @param charset CharSet
	 * @return ���� charset���� ��ȯ�� ���ڿ�
	 ******************************************************************************************************************/
	public static String toCharset(String source, String charset) {
		String ret = null;
		if (source == null)
			return null;
		try {
			ret = new String(source.getBytes("8859_1"), charset);
		} catch (UnsupportedEncodingException e) {
			ret = null;
		}
		return ret;
	}

	/*******************************************************************************************************************
	 * DB�� �ֱ� ���� single quotation �Է��� ó���ϴ� �޼���
	 * 
	 * @param source ���� ���ڿ�
	 * @return single quotation ó���� ���ڿ�
	 ******************************************************************************************************************/
	public static String procQuotation(String source) {
		String ret = null;
		if (source == null)
			return null;
		ret = replace(source, "'", "''");
		return ret;
	}

	/*******************************************************************************************************************
	 * single quotation �Է��� ó���ϰ� �յڷ� single quotation�� �߰��ϴ� �޼���
	 * 
	 * @param source ���� ���ڿ�
	 * @return single quotation ó���� ���ڿ�
	 ******************************************************************************************************************/
	public static String autoQuotation(String source) {
		return "'" + procQuotation(source) + "'";
	}

	/*******************************************************************************************************************
	 * ���ڿ��� null���� Ȯ���ϰ� null�� ��� ������ ���ڿ��� �ٲٴ� �Լ�.
	 * 
	 * <pre>
	 * String id1 = strUtil.isNull(request.getParameter(&quot;id1&quot;),&quot;&quot;);
	 * ���� ��û �Ķ���� id1�� ���� ���� null�̸� &quot;&quot; �� �ٲ۴�.
	 * </pre>
	 * 
	 * @param source ���� ���ڿ�
	 * @param value null�ϰ�� �ٲ� ���ڿ�
	 * @return ���ڿ�
	 ******************************************************************************************************************/
	public static String isNull(String source, String value) {
		String retVal;
		if (source == null || source.length() < 1) {
			retVal = value;
		} else {
			retVal = source;
		}
		// retVal = replace(retVal, "'", "`"); // add by khko 20030908 '�� `�� �ٲ۴�
		return retVal;
	}

	/*******************************************************************************************************************
	 * ���ڿ��� null���� Ȯ���ϰ� null�� ��� ������ ���ڿ��� �ٲٴ� �Լ�. ',' ���ڰ� �ִ°�� �����Ѵ�.
	 * 
	 * <pre>
	 * String id1 = strUtil.isNull(request.getParameter(&quot;id1&quot;),&quot;&quot;);
	 * ���� ��û �Ķ���� id1�� ���� ���� null�̸� &quot;&quot; �� �ٲ۴�.
	 * </pre>
	 * 
	 * @param source ���� ���ڿ�
	 * @param value null�ϰ�� �ٲ� ���ڿ�
	 * @return ���ڿ�
	 ******************************************************************************************************************/
	// add by khko 20030916 ','�� �����Ѵ�
	public static String isNull3(String source, String value) {
		String retVal;
		if (source == null || source.length() < 1) {
			retVal = value;
		} else {
			retVal = source;
		}
		retVal = replace(retVal, ",", "");
		return retVal;
	}

	public static String isNull(String[] source, String value) {
		String retVal;
		if (source == null) {
			retVal = value;
		} else {
			retVal = source[0];
		}
		return retVal;
	}

	/*******************************************************************************************************************
	 * ���ڿ��� int������ ��ȯ�ϰ�, null���� Ȯ���Ͽ� null�� ��� ������ int�� �ٲٴ� �Լ�.
	 * 
	 * <pre>
	 * int id1 = strUtil.isNull(request.getParameter(&quot;id1&quot;),0);
	 * ���� ��û �Ķ���� id1�� ���� ���� null�̸� 0���� �ٲ۴�.
	 * null�� �ƴϸ� int������ ��ȯ�ϰ�, ��ȯ �Ұ����ϸ� ������ 0���� �ǵ�����.
	 * </pre>
	 * 
	 * @param source ���� ���ڿ�
	 * @param val null�ϰ�� �ٲ� ���ڿ�
	 * @return ����
	 ******************************************************************************************************************/
	public static int isNull(String source, int value) {
		int retVal;
		try {
			if (source == null) {
				retVal = value;
			} else {
				retVal = Integer.parseInt(source);
			}
		} catch (Exception e) {
			retVal = value;
		}
		return retVal;
	}

	public static String isNull2(String source, String value) {
		String str;
		char ch;
		String Result = "";
		int i;
		if (source == null) {
			str = value;
		} else {
			str = source;
		}
		try {
			for (i = 0; i < str.length(); i++) {
				ch = str.charAt(i);
				if (ch == '"') {
					Result += "\"";
				} else if (ch == '\'') {
					Result += "\"";
				} else {
					Result += ch;
				}
			}
			return Result;
		} catch (Exception e) {
			e.printStackTrace();
			return Result;
		}
	}

	public static boolean isNotNull(String value) {
		if (value == null)
			return false;
		if (value.equals(""))
			return false;
		if (value.equals("emptyValue"))
			return false;
		return true;
	}

	/*******************************************************************************************************************
	 * ���ڿ��� ������ �и��ڿ� ���� �迭�� �����ϴ� �Լ�.
	 * 
	 * <pre>
	 * String[] rst = strUtil.split(&quot;2002-01-20&quot;,&quot;-&quot;);
	 * ���
	 * rst.length = 3
	 * rst[0] = 2002, rst[1] = 01, rst[2] = 20
	 * 
	 * String[] rst = strUtil.split(&quot;20020120&quot;,&quot;-&quot;);
	 * ���
	 * rst.length = 1
	 * rst[0] = 20020120
	 * 
	 * 
	 * </pre>
	 * 
	 * @param source ���� ���ڿ�
	 * @param separator �и���
	 * @return �и��ڷ� �������� ���ڿ��迭
	 ******************************************************************************************************************/
	public static String[] split(String source, String separator) throws NullPointerException {
		String[] rtnVal = null;
		int cnt = 1;

		int index = source.indexOf(separator);
		int index0 = 0;
		while (index >= 0) {
			cnt++;
			index = source.indexOf(separator, index + 1);
		}
		rtnVal = new String[cnt];
		cnt = 0;
		index = source.indexOf(separator);
		while (index >= 0) {
			rtnVal[cnt] = source.substring(index0, index);
			index0 = index + 1;
			index = source.indexOf(separator, index + 1);
			cnt++;
		}
		rtnVal[cnt] = source.substring(index0);
		return rtnVal;
	}

	/*******************************************************************************************************************
	 * ���ڿ��� ������ �и��ڿ� ���� ������ ������ �迭�� �����ϴ� �Լ�.
	 * 
	 * <pre>
	 * String[] rst = strUtil.split(&quot;2002-01-20&quot;,&quot;-&quot;,4);
	 * ���
	 * rst.length = 4
	 * rst[0] = &quot;2002&quot;, rst[1] = &quot;01&quot;, rst[2] = &quot;20&quot;, rst[3] = &quot;&quot;
	 * 
	 * String[] rst = strUtil.split(&quot;20020120&quot;,&quot;-&quot;,4);
	 * ���
	 * rst.length = 4
	 * rst[0] = &quot;20020120&quot;, rst[1] = &quot;&quot;, rst[2] = &quot;&quot;, rst[3] = &quot;&quot;
	 * 
	 * 
	 * </pre>
	 * 
	 * @param source ���� ���ڿ�
	 * @param separator �и���
	 * @param arraylength �迭 ����
	 * @return �и��ڷ� �������� ���ڿ��迭
	 ******************************************************************************************************************/
	public static String[] split(String source, String separator, int arraylength) throws NullPointerException {
		String[] rtnVal = new String[arraylength];

		int cnt = 0;
		int index0 = 0;
		int index = source.indexOf(separator);
		while (index >= 0 && cnt < (arraylength - 1)) {
			rtnVal[cnt] = source.substring(index0, index);
			index0 = index + 1;
			index = source.indexOf(separator, index + 1);
			cnt++;
		}
		rtnVal[cnt] = source.substring(index0);
		if (cnt < (arraylength - 1)) {
			for (int i = cnt + 1; i < arraylength; i++) {
				rtnVal[i] = "";
			}
		}
		return rtnVal;
	}

	/*******************************************************************************************************************
	 * ���ڿ��迭�� ������ �и��ڿ� ���� ���� ���ڿ��� �����ϴ� �Լ�.
	 * 
	 * <pre>
	 * test[0] = &quot;2000&quot;, test[1] = &quot;01', test[2] = '02'
	 * String rst = strUtil.patch(request.getParameterValues(&quot;test&quot;),&quot;-&quot;);
	 * ���
	 * rst = 2002-01-02
	 * </pre>
	 * 
	 * @param source ���� ���ڿ� �迭
	 * @param separator �и���
	 * @return �и��� ��ģ ���ڿ�
	 ******************************************************************************************************************/
	public static String patch(String[] source, String separator) throws NullPointerException {
		return patch(source, separator, false);
	}

	/*******************************************************************************************************************
	 * ���ڿ��迭�� ������ �и��ڿ� ���� ���� ���ڿ��� �����ϴ� �Լ�.
	 * 
	 * <pre>
	 * test[0] = &quot;2000&quot;, test[1] = &quot;&quot;, test[2] = &quot;02&quot;, test[3] = &quot;00&quot;
	 * String rst = strUtil.patch(request.getParameterValues(&quot;test&quot;),&quot;-&quot;,true);
	 * ���
	 * isblankinsert �� ture �� rst = &quot;2002-02-00&quot;
	 * isblankinsert �� false �� rst = &quot;2002--02-00&quot;
	 * </pre>
	 * 
	 * @param source ���� ���ڿ� �迭
	 * @param separator �и���
	 * @param isblankinsert �󰪿��� �и��ڸ� ǥ���� �� ����
	 * @return �и��� ��ģ ���ڿ�
	 ******************************************************************************************************************/
	public static String patch(String[] source, String separator, boolean isblankinsert) throws NullPointerException {
		StringBuffer rtnVal = new StringBuffer();
		int cnt = 0;
		if (source != null) {
			for (int i = 0; i < source.length; i++) {
				if (!isblankinsert) {
					if (cnt != 0)
						rtnVal.append(separator);
					rtnVal.append(isNull(source[i], ""));
					cnt++;
				} else {
					if (isNull(source[i], "").length() > 0) {
						if (cnt != 0)
							rtnVal.append(separator);
						rtnVal.append(source[i]);
						cnt++;
					}
				}
			}
		}
		return rtnVal.toString();
	}

	/*******************************************************************************************************************
	 * ���ڿ��� ������ ���̸� �ʰ������� �����ѱ��̿��ٰ� �ش� ���ڿ��� �ٿ��ִ°�
	 * 
	 * <pre>
	 * test[0] = &quot;2000&quot;, test[1] = &quot;&quot;, test[2] = &quot;02&quot;, test[3] = &quot;00&quot;
	 * String rst = strUtil.cutString(request.getParameterValues(&quot;aaaaaaabbbbbbb&quot;,&quot;..&quot;,5);
	 * ���
	 * aaaaa..
	 * </pre>
	 * 
	 * @param source ���� ���ڿ� �迭
	 * @param output ���ҹ��ڿ�
	 * @param slength ��������
	 * @return �������̷� �߶� ���Һ��ڿ� ��ģ ���ڿ�
	 ******************************************************************************************************************/

	public static String cutString(String source, String output, int slength) {
		String rtnVal = null;
		if (source != null) {
			if (source.length() > slength) {
				rtnVal = source.substring(0, slength) + output;
			} else
				rtnVal = source;
		}
		return rtnVal;
	}

	/**
	 * �Է¹��� ���� ��ο� �ִ� ������ String���� ��ȯ��Ų��.
	 * 
	 * @param xString ����������
	 * @return String ����String
	 */
	public String getNormalString(String xString) {
		String xmlString = "";
		RandomAccessFile chkin = null;
		try {
			chkin = new RandomAccessFile(new File(xString), "r");

			int currentbyte = (int) chkin.length();
			byte[] buf = new byte[currentbyte];
			chkin.read(buf, 0, currentbyte);
			xmlString = new String(buf, 0, currentbyte); // xml ���� ����
		} catch (Exception ex) {
			//  
		} finally {
			try {
				if (chkin != null)
					chkin.close();
			} catch (IOException ioe) {
			}
		}
		return xmlString;
	}

	// �ĸ� �ִ� ���� ���ڷ�
	public static int money2Int(String money) {
		NumberFormat nf = NumberFormat.getInstance();
		try {
			return nf.parse(money).intValue();
		} catch (Exception e) {
			System.err.print(e);
		}

		return 0;
	}

	// ���ڸ� �ĸ� �ִ� ������
	public static String int2Money(int money) {
		NumberFormat nf = NumberFormat.getInstance();
		try {
			return nf.format(money);
		} catch (Exception e) {
			System.err.print(e);
		}

		return null;
	}

	// �ĸ� �ִ� ���� ���ڷ�
	public static long money2Long(String money) {
		NumberFormat nf = NumberFormat.getInstance();
		try {
			return nf.parse(money).intValue();
		} catch (Exception e) {
			System.err.print(e);
		}

		return 0;
	}

	// ���ڸ� �ĸ� �ִ� ������
	public static String longt2Money(long money) {
		NumberFormat nf = NumberFormat.getInstance();
		try {
			return nf.format(money);
		} catch (Exception e) {
			System.err.print(e);
		}

		return null;
	}

	// �ĸ� �ִ� ���� ���ڷ�
	public static int money2Double(String money) {
		NumberFormat nf = NumberFormat.getInstance();
		try {
			return nf.parse(money).intValue();
		} catch (Exception e) {
			System.err.print(e);
		}

		return 0;
	}

	// ���ڸ� �ĸ� �ִ� ������
	public static String double2Money(double money) {
		String temp = money + "";
		NumberFormat nf = NumberFormat.getInstance();
		try {
			String[] num = split(temp, ".");
			//return nf.format(Integer.parseInt(num[0]));
			return nf.format(Double.parseDouble(num[0]));
		} catch (Exception e) {
			System.err.print(e);
			return "0";
		}
	}

	synchronized public static String getCurr(String format) {
		Calendar calendar = Calendar.getInstance();
		Date date = calendar.getTime();
		String result = (new SimpleDateFormat(format).format(date)).toString();
		return result;
	}

	/**
	 * �ش� ��/���� �ش��ϴ� �ϼ��� ��ȯ�Ѵ�.
	 * 
	 * @return int
	 */
	public static int getMonthlyDayCount(int Year, int Month) {
		int Max = 0;
		if (Month == 4 || Month == 6 || Month == 9 || Month == 11) {
			Max = 30;
		} else if (Month == 2) {
			Max = 28;
			if (isYunYear(Year))
				Max = 29;
		} else {
			Max = 31;
		}
		return Max;
	}

	/**
	 * ���޿��θ� Ȯ���Ѵ�.
	 * 
	 * @return boolean
	 */
	public static boolean isYunYear(int Year) {
		boolean ck = false;
		if (Year % 4 == 0) {
			if (Year % 100 == 0) {
				if (Year % 400 == 0)
					ck = true;
			} else {
				ck = true;
			}
		}
		return ck;
	}

	public static String barAdd(String value, int num) {
		String result = "";
		try {
			result = value.substring(0, num) + "-" + value.substring(num, value.length());
		} catch (Exception e) {
			// e.printStackTrace();
		}
		return result;
	}

	public static String barAdd(String value, String nums) {
		String result_st = "";
		StringBuffer result = new StringBuffer();
		StringTokenizer tok = new StringTokenizer(nums, ",");
		try {
			int num[] = new int[tok.countTokens() + 1];
			num[0] = 0;
			int i = 1;
			while (tok.hasMoreElements()) {
				num[i] = Integer.parseInt(tok.nextToken());
				i++;
			}

			for (int k = 0; k < (num.length); k++) {

				if ((k + 1) < (num.length)) {
					result.append(value.substring(num[k], num[k + 1]));
					result.append("-");
				} else {
					result.append(value.substring(num[k], value.length()));
				}
			}

			result_st = result.toString();
		} catch (Exception e) {
			// e.printStackTrace();
			result_st = value;
		}

		return result_st;
	}

	public static String getDateTime() {
		long currentTimes = System.currentTimeMillis();
		String ymd = new SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date(currentTimes));

		return ymd;
	}

	public static String getDateTime1() {
		long currentTimes = System.currentTimeMillis();
		String ymd = new SimpleDateFormat("yyyyMM").format(new java.util.Date(currentTimes));

		return ymd;
	}

	public List<String> getMonthList(int stardMonth, int endMonth) {
		List<String> res = new ArrayList<String>();
		for (;; stardMonth++) {
			//System.out.println(stardMonth);
			res.add(String.valueOf(stardMonth));
			if (String.valueOf(stardMonth).endsWith("12")) {
				stardMonth = stardMonth - 12;
				stardMonth = stardMonth + 100;
			}

			if (stardMonth == endMonth)
				break;
		}
		return res;
	}

	/**
	 * String ��ü�� �о Ư����ȣ�� �ڵ�� �ٲپ �����ϱ� ���� Method<br>
	 * 
	 * @param orgstr ��ȯ�ϰ��� �ϴ� ��ü String<br>
	 * @return �ڵ�� ��ȯ�� String��<br>
	 */
	public static String convertStingToCustomCode(String orgstr) {
		if (orgstr == null)
			return "";

		int len = orgstr.length();
		String rplStr = "";
		String currStr = "";
		String replaceStr = "";
		int i = 0;
		for (i = 0; i < len; i++) {
			currStr = orgstr.substring(i, i + 1);
			if (currStr.equals("'")) {
				rplStr = "##11";
			} else if (currStr.equals("~")) {
				rplStr = "##12";
			} else if (currStr.equals("!")) {
				rplStr = "##13";
			} else if (currStr.equals("@")) {
				rplStr = "##14";
			} else if (currStr.equals("#")) {
				rplStr = "##15";
			} else if (currStr.equals("$")) {
				rplStr = "##16";
			} else if (currStr.equals("%")) {
				rplStr = "##17";
			} else if (currStr.equals("^")) {
				rplStr = "##18";
			} else if (currStr.equals("&")) {
				rplStr = "##19";
			} else if (currStr.equals("(")) {
				rplStr = "##20";
			} else if (currStr.equals(")")) {
				rplStr = "##21";
			} else if (currStr.equals("_")) {
				rplStr = "##22";
			} else if (currStr.equals("+")) {
				rplStr = "##23";
			} else if (currStr.equals("=")) {
				rplStr = "##24";
			} else if (currStr.equals("[")) {
				rplStr = "##25";
			} else if (currStr.equals("]")) {
				rplStr = "##26";
			} else if (currStr.equals("{")) {
				rplStr = "##27";
			} else if (currStr.equals("}")) {
				rplStr = "##28";
			} else if (currStr.equals(";")) {
				rplStr = "##29";
			} else if (currStr.equals(",")) {
				rplStr = "##30";
			} else if (currStr.equals("`")) {
				rplStr = "##31";
			} else {
				rplStr = currStr;
			}
			replaceStr += rplStr;
		}

		return replaceStr;
	}

	/**
	 * Ư����ȣ�� �ٲ� ��ü String�� �ٽ� Ư�����ڷ� ��ȯ�ϴ� Method<br>
	 * 
	 * @param orgstr �ڵ�� ��ȯ�� String<br>
	 * @return Ư�����ڷ� �ٽ� ��ȯ�� String<br>
	 */
	public static String convertCustomCodeToSting(String orgstr) {
		if (orgstr == null)
			return "";

		int len = orgstr.length();
		String rplStr = "";
		String currChrs = "";
		String currStr = "";
		String replaceStr = "";
		int j = 0, i = 0;
		boolean flag = true;

		for (i = 0; i < len; i++) {
			currChrs = orgstr.substring(i, i + 1);
			flag = true;

			j = i;
			if (len - j >= 4) {
				currStr = orgstr.substring(j, j + 4);
			} else {
				currStr = orgstr.substring(j, j + 1);
			}
			if (currStr.equals("##11")) {
				rplStr = "'";
			} else if (currStr.equals("##12")) {
				rplStr = "~";
			} else if (currStr.equals("##13")) {
				rplStr = "!";
			} else if (currStr.equals("##14")) {
				rplStr = "@";
			} else if (currStr.equals("##15")) {
				rplStr = "#";
			} else if (currStr.equals("##16")) {
				rplStr = "$";
			} else if (currStr.equals("##17")) {
				rplStr = "%";
			} else if (currStr.equals("##18")) {
				rplStr = "^";
			} else if (currStr.equals("##19")) {
				rplStr = "&";
			} else if (currStr.equals("##20")) {
				rplStr = "(";
			} else if (currStr.equals("##21")) {
				rplStr = ")";
			} else if (currStr.equals("##22")) {
				rplStr = "_";
			} else if (currStr.equals("##23")) {
				rplStr = "+";
			} else if (currStr.equals("##24")) {
				rplStr = "=";
			} else if (currStr.equals("##25")) {
				rplStr = "[";
			} else if (currStr.equals("##26")) {
				rplStr = "]";
			} else if (currStr.equals("##27")) {
				rplStr = "{";
			} else if (currStr.equals("##28")) {
				rplStr = "}";
			} else if (currStr.equals("##29")) {
				rplStr = ";";
			} else if (currStr.equals("##30")) {
				rplStr = ",";
			} else if (currStr.equals("##31")) {
				rplStr = "`";
			} else {
				rplStr = currChrs;
				flag = false;
			}

			replaceStr += rplStr;
			if (flag == true) {
				i = i + 3;
			}
		}
		return replaceStr;
	}

	public static String delHtmlTag(String param) {
		Pattern p = Pattern.compile("\\<(\\/?)(\\w+)*([^<>]*)>");
		Matcher m = p.matcher(param);
		param = m.replaceAll("").trim();
		// logger.debug("��ȯ��: " + param);
		return param;
	}
}
