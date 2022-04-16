/*
 * $Id: ExpenseRealTimeResultWrapper.java,v 1.5 2018/01/29 02:25:47 cvs Exp $
 * created by    : ¾È¼ºÈ£
 * creation-date : 2006. 4. 26.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.project.statistics.vl;

import java.sql.ResultSet;
import java.sql.SQLException;

import kr.co.kmac.pms.project.statistics.data.ExpenseRealTimeResult;
import net.mlw.vlh.ValueListInfo;
import net.mlw.vlh.adapter.util.ObjectWrapper;

import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.jdbc.support.SQLStateSQLExceptionTranslator;

public class ExpenseRealTimeResultDetailWrapper3 implements ObjectWrapper {

	private SQLExceptionTranslator exceptionTranslator = new SQLStateSQLExceptionTranslator();

	public Object getWrappedRecord(Object arg0) {
		ResultSet rs = (ResultSet) arg0;
		try {
			ExpenseRealTimeResult expenseRealTimeResult = new ExpenseRealTimeResult();
			expenseRealTimeResult.setDeptName(rs.getString("deptName"));
			expenseRealTimeResult.setDept(rs.getString("dept"));
			expenseRealTimeResult.setName(rs.getString("name"));
			expenseRealTimeResult.setCompanyPositionName(rs.getString("companyPositionName"));
			expenseRealTimeResult.setRealTimeSalary(rs.getLong("RealTimeSalary"));
			expenseRealTimeResult.setUid(rs.getString("uid"));
			expenseRealTimeResult.setLabelName(rs.getString("labelName"));
			return expenseRealTimeResult;
		} catch (SQLException e) {
			throw exceptionTranslator.translate("ExpenseRealTimeResultWrapper3 fails", null, e);
		}
	}

	public void setValueListInfo(ValueListInfo arg0) {
	}
}
