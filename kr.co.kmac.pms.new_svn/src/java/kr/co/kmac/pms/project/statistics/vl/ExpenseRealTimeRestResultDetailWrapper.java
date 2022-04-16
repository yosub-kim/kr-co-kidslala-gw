/*
 * $Id: ExpenseRealTimeResultDetailWrapper.java,v 1.5 2011/11/03 05:06:05 cvs Exp $
 * created by    : ¾È¼ºÈ£
 * creation-date : 2006. 4. 26.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.project.statistics.vl;

import java.sql.ResultSet;
import java.sql.SQLException;

import kr.co.kmac.pms.project.statistics.data.ExpenseRealTimeResultDetail;
import net.mlw.vlh.ValueListInfo;
import net.mlw.vlh.adapter.util.ObjectWrapper;

import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.jdbc.support.SQLStateSQLExceptionTranslator;

public class ExpenseRealTimeRestResultDetailWrapper implements ObjectWrapper {

	private SQLExceptionTranslator exceptionTranslator = new SQLStateSQLExceptionTranslator();

	public Object getWrappedRecord(Object arg0) {
		ResultSet rs = (ResultSet) arg0;
		try {
			ExpenseRealTimeResultDetail expenseRealTimeResultDetail = new ExpenseRealTimeResultDetail();
			expenseRealTimeResultDetail.setProjectCode(rs.getString("projectCode"));
			expenseRealTimeResultDetail.setProjectName(rs.getString("projectName"));
			expenseRealTimeResultDetail.setYear(rs.getString("year"));
			expenseRealTimeResultDetail.setMonth(rs.getString("month"));
			expenseRealTimeResultDetail.setName(rs.getString("name"));
			expenseRealTimeResultDetail.setSsn(rs.getString("ssn"));
			expenseRealTimeResultDetail.setCost(rs.getString("cost"));
			expenseRealTimeResultDetail.setSsnCnt(rs.getString("ssnCnt"));
			expenseRealTimeResultDetail.setProjectCnt(rs.getString("projectCnt"));
			expenseRealTimeResultDetail.setIsSanction(rs.getString("isSanction"));
			expenseRealTimeResultDetail.setSumCost(rs.getString("sumcost"));
			return expenseRealTimeResultDetail;
		} catch (SQLException e) {
			throw exceptionTranslator.translate("ExpenseRealTimeRestResultDetailWrapper fails", null, e);
		}
	}

	public void setValueListInfo(ValueListInfo arg0) {
	}
}
