/*
 * $Id: EmergencyConnectionWapper.java,v 1.2 2011/09/06 11:35:13 cvs Exp $
 * created by    : ¾È¼ºÈ£
 * creation-date : 2006. 7. 7.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.emergencyConnection.vl;

import java.sql.ResultSet;
import java.sql.SQLException;

import kr.co.kmac.pms.emergencyConnection.data.EmergencyConnectionData;
import net.mlw.vlh.ValueListInfo;
import net.mlw.vlh.adapter.util.ObjectWrapper;

import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.jdbc.support.SQLStateSQLExceptionTranslator;

public class EmergencyConnectionWapper implements ObjectWrapper {
	private SQLExceptionTranslator exceptionTranslator = new SQLStateSQLExceptionTranslator();
	
	public Object getWrappedRecord(Object arg0) {
		ResultSet rs = (ResultSet) arg0;
		try {
			EmergencyConnectionData data = new EmergencyConnectionData();
			data.setSsn(rs.getString("ssn"));
			data.setDept(rs.getString("dept"));
			data.setCompanyPosition(rs.getString("companyPosition"));
			data.setName(rs.getString("name"));
			data.setEmail(rs.getString("email"));
			data.setCompanyTelNo(rs.getString("companyTelNo"));
			data.setMobileNo(rs.getString("mobileNo"));
			data.setAddress1(rs.getString("address1"));
			data.setCompanyPositionId(rs.getString("companyPositionId"));
			data.setDeptId(rs.getString("deptId"));
			data.setRowCnt(rs.getString("rowCnt"));
			
			return data;
		} catch (SQLException e) {
			throw exceptionTranslator.translate("EmergencyConnectionData fails", null, e);
		}
	}

	public void setValueListInfo(ValueListInfo arg0) {
	}

}
