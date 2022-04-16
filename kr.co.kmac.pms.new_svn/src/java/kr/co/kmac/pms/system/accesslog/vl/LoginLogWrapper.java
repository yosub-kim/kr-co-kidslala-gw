/*
 * $Id: LoginLogWrapper.java,v 1.1 2010/12/06 09:08:53 cvs1 Exp $
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

public class LoginLogWrapper implements ObjectWrapper {

	private SQLExceptionTranslator exceptionTranslator = new SQLStateSQLExceptionTranslator();

	public Object getWrappedRecord(Object objectToBeWrapped) {
		// TODO Auto-generated method stub
		ResultSet rs = (ResultSet) objectToBeWrapped;
		try {
			LoginLogData loginLogData = new LoginLogData();
			loginLogData.setJobClass(rs.getString("jobClass"));
			loginLogData.setDeptName(rs.getString("deptName"));
			loginLogData.setCompanyPositionName(rs.getString("companyPositionName"));
			loginLogData.setSsn(rs.getString("ssn"));
			loginLogData.setName(rs.getString("name"));
			loginLogData.setCnt(rs.getString("cnt"));
			
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
