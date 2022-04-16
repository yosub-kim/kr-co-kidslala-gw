/*
 * $Id: LoginLogWrapper.java,v 1.1 2010/12/06 09:08:53 cvs1 Exp $
 * creation-date : 2010. 12. 6.
 * =========================================================
 * Copyright (c) 2010 KMAC. All rights reserved.
 */
package kr.co.kmac.pms.system.bizplaylog.vl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import net.mlw.vlh.ValueListInfo;
import net.mlw.vlh.adapter.util.ObjectWrapper;

import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.jdbc.support.SQLStateSQLExceptionTranslator;

public class BizplayLogListWrapper implements ObjectWrapper {

	private SQLExceptionTranslator exceptionTranslator = new SQLStateSQLExceptionTranslator();

	public Object getWrappedRecord(Object objectToBeWrapped) {
		// TODO Auto-generated method stub
		ResultSet rs = (ResultSet) objectToBeWrapped;
		try {
			HashMap<String, String> m = new HashMap<String, String>();
			m.put("createdTime", rs.getString("createdTime"));
			m.put("endPoint", rs.getString("endPoint"));
			m.put("sendTxt", rs.getString("sendTxt"));
			m.put("resultTxt", rs.getString("resultTxt"));
			m.put("exceptionStr", rs.getString("exceptionStr"));

			return m;

		} catch (SQLException e) {
			throw exceptionTranslator.translate("BizplayLogListWrapper fails", null, e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.mlw.vlh.adapter.util.ObjectWrapper#setValueListInfo(net.mlw.vlh.ValueListInfo)
	 */
	public void setValueListInfo(ValueListInfo info) {
		// TODO Auto-generated method stub

	}

}
