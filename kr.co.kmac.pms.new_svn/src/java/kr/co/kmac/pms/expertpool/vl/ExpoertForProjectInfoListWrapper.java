/*
 * $Id: ExpoertForProjectInfoListWrapper.java,v 1.1 2009/09/19 11:15:34 cvs3 Exp $
 * created by    : ¾È¼ºÈ£
 * creation-date : 2006. 6. 1.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.expertpool.vl;

import java.sql.ResultSet;
import java.sql.SQLException;

import kr.co.kmac.pms.expertpool.data.ExpoertForProjectInfoListData;

import net.mlw.vlh.ValueListInfo;
import net.mlw.vlh.adapter.util.ObjectWrapper;

import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.jdbc.support.SQLStateSQLExceptionTranslator;

public class ExpoertForProjectInfoListWrapper implements ObjectWrapper {
	private SQLExceptionTranslator exceptionTranslator = new SQLStateSQLExceptionTranslator();

	public Object getWrappedRecord(Object objectToBeWrapped) {
		ResultSet rs = (ResultSet) objectToBeWrapped;
		ExpoertForProjectInfoListData data = new ExpoertForProjectInfoListData();
		try {
			data.setProjectCode(rs.getString("projectCode"));
			data.setProjectName(rs.getString("projectName"));
			data.setCustomerName(rs.getString("customerName"));
			data.setBussinessTypeName(rs.getString("bussinessTypeName"));
			data.setRunningDivName(rs.getString("runningDivName"));
			data.setRealStartDate(rs.getString("realStartDate"));
			data.setRealEndDate(rs.getString("realEndDate"));
		} catch (SQLException e) {
			throw exceptionTranslator.translate("ExpoertForProjectInfoListWrapper fails", null, e);
		}
		return data;
	}

	public void setValueListInfo(ValueListInfo arg0) {

	}

}
