/*
 * $Id: LoginLogDetailWrapper.java,v 1.2 2013/01/16 15:54:44 cvs Exp $
 * creation-date : 2010. 12. 6.
 * =========================================================
 * Copyright (c) 2010 KMAC. All rights reserved.
 */
package kr.co.kmac.pms.system.accesslog.vl;

import java.sql.ResultSet;
import java.sql.SQLException;

import kr.co.kmac.pms.system.accesslog.data.LoginLogData;
import net.mlw.vlh.ValueListInfo;
import net.mlw.vlh.adapter.util.ObjectWrapper;

import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.jdbc.support.SQLStateSQLExceptionTranslator;

public class LoginLogDetailWrapper implements ObjectWrapper {

	private SQLExceptionTranslator exceptionTranslator = new SQLStateSQLExceptionTranslator();

	public Object getWrappedRecord(Object objectToBeWrapped) {
		// TODO Auto-generated method stub
		ResultSet rs = (ResultSet) objectToBeWrapped;
		try {
			LoginLogData loginLogData = new LoginLogData();
			loginLogData.setCnt(rs.getString("seq"));
			loginLogData.setLoginDate(rs.getString("loginDate"));
			loginLogData.setIp(rs.getString("ip"));
			loginLogData.setName(rs.getString("name"));
			loginLogData.setDeviceType(rs.getString("deviceType"));
			
			return loginLogData;
			
		} catch (SQLException e) {
			throw exceptionTranslator.translate("LoginLogWrapper fails", null, e);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see net.mlw.vlh.adapter.util.ObjectWrapper#setValueListInfo(net.mlw.vlh.ValueListInfo)
	 */
	public void setValueListInfo(ValueListInfo info) {
		// TODO Auto-generated method stub

	}

}
