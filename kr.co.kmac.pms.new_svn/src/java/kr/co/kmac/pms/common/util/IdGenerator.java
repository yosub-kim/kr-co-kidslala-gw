/*
 * $Id: IdGenerator.java,v 1.1 2009/09/19 11:15:32 cvs3 Exp $
 * created by    : ���ȣ
 * creation-date : 2008. 09. 09
 * =========================================================
 * Copyright (c) 2008 Maninsoft, Inc. All rights reserved.
 */
package kr.co.kmac.pms.common.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * ���̵� ������
 * 
 * @author ���ȣ
 * @version $Id: IdGenerator.java,v 1.1 2009/09/19 11:15:32 cvs3 Exp $
 */
public class IdGenerator {
	private static long counter = 0L;
	/**
	 * <code>PREFIX_ROLE</code> ��
	 */
	public static final String PREFIX_ROLE = "ROLE";
	/**
	 * <code>PREFIX_MENU</code> �޴�
	 */
	public static final String PREFIX_MENU = "MENU";
	/**
	 * <code>PREFIX_WORL</code> ��������Ʈ
	 */
	public static final String PREFIX_WORK = "WORK";
	/**
	 * <code>PREFIX_WORL</code> ���� ���� ���̵�
	 */
	public static final String PREFIX_TASK = "TASK";
	/**
	 * <code>PREFIX_WORL</code> ���μ��� ī�װ�
	 */
	public static final String PREFIX_CATEGORY = "PRODUCT";

	// �Ʒ��� ��� �߰�

	/**
	 * ���̵� ��´�.
	 * 
	 * @param prefix ���ξ�
	 * @return
	 */
	public static String generate(String prefix) {
		String id = null;
		synchronized (IdGenerator.class) {
			id = Long.toHexString(System.currentTimeMillis() + counter++).toUpperCase();
		}
		if (prefix != null)
			id = prefix + id;
		return id;
	}

	/**
	 * �ʿ��� ������ ���̵� ��´�.
	 * 
	 * @param n �ʿ��� ���̵� ����
	 * @param prefix ���ξ�
	 * @return
	 */
	public static List<String> generateForMulti(int n, String prefix) {
		Calendar currentDate = Calendar.getInstance();
		List<String> ids = new ArrayList<String>();
		for (int i = 0; i < n; i++) {
			currentDate.set(Calendar.SECOND, i);
			String id = null;
			synchronized (IdGenerator.class) {
				id = Long.toHexString(System.currentTimeMillis() + counter++).toUpperCase();
			}
			if (prefix != null)
				id = prefix + id;
			ids.add(id);
		}
		return ids;
	}
}
