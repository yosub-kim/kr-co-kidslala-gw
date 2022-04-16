/*
 * $Id: ExpenseRealTimeResultDetailForSanctionWrapper.java,v 1.10 2019/04/01 16:11:33 cvs Exp $
 * created by    : ¾È¼ºÈ£
 * creation-date : 2006. 4. 26.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.sanction.projectexpense.vl;

import java.sql.ResultSet;
import java.sql.SQLException;

import kr.co.kmac.pms.sanction.projectexpense.data.ExpenseRealTimeResultDetailForSanction;
import net.mlw.vlh.ValueListInfo;
import net.mlw.vlh.adapter.util.ObjectWrapper;

import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.jdbc.support.SQLStateSQLExceptionTranslator;

public class ExpenseRealTimeResultDetailForSanctionWrapper implements ObjectWrapper {

	private SQLExceptionTranslator exceptionTranslator = new SQLStateSQLExceptionTranslator();

	public Object getWrappedRecord(Object arg0) {
		ResultSet rs = (ResultSet) arg0;
		try {
			ExpenseRealTimeResultDetailForSanction expenseRealTimeResultDetailForSanction = new ExpenseRealTimeResultDetailForSanction();
			expenseRealTimeResultDetailForSanction.setSalaryJobClass(rs.getString("jobClass"));
			expenseRealTimeResultDetailForSanction.setSalaryType(rs.getString("type"));
			expenseRealTimeResultDetailForSanction.setSalaryJobClassDesc(rs.getString("jobClassDesc"));
			expenseRealTimeResultDetailForSanction.setSalaryJobClassErp(rs.getString("jobClassErp"));
			expenseRealTimeResultDetailForSanction.setSalaryDeptType(rs.getString("salaryDeptType"));
			expenseRealTimeResultDetailForSanction.setSalaryYear(rs.getString("year"));
			expenseRealTimeResultDetailForSanction.setSalaryMonth(rs.getString("month"));
			expenseRealTimeResultDetailForSanction.setSalaryName(rs.getString("name"));
			expenseRealTimeResultDetailForSanction.setSalarySsn(rs.getString("ssn"));
			expenseRealTimeResultDetailForSanction.setSalaryDept(rs.getString("dept"));
			expenseRealTimeResultDetailForSanction.setSalaryTotalRealTimeSalary(rs.getLong("totalRealTimeSalary"));
			expenseRealTimeResultDetailForSanction.setSalaryProjectCode(rs.getString("projectCode"));
			expenseRealTimeResultDetailForSanction.setSalaryProjectName(rs.getString("projectName"));
			expenseRealTimeResultDetailForSanction.setSalaryPreportCount(rs.getString("PreportCount"));
			expenseRealTimeResultDetailForSanction.setSalaryRealTimeSalaryEachProject(rs.getLong("RealTimeSalaryEachProject"));
			expenseRealTimeResultDetailForSanction.setSalaryCost(rs.getDouble("cost"));
			expenseRealTimeResultDetailForSanction.setSalaryResRate(rs.getString("resRate"));
			expenseRealTimeResultDetailForSanction.setSalaryExecuted(0);
			try {
				expenseRealTimeResultDetailForSanction.setSalaryIsExceed(rs.getString("isExceed"));
				expenseRealTimeResultDetailForSanction.setSalaryIsMMExceed(rs.getString("isMMExceed"));
				expenseRealTimeResultDetailForSanction.setSalarySsnCnt(rs.getString("ssnCnt"));
			} catch (SQLException se) {
			}
			try {
				expenseRealTimeResultDetailForSanction.setSalaryIsHolding(rs.getString("isHolding"));
			} catch (SQLException se) {
			}
			try {
				expenseRealTimeResultDetailForSanction.setSalaryCumulativeRealTimeSalary(rs.getLong("cumulativeRealTimeSalaryEachProject"));
				expenseRealTimeResultDetailForSanction.setSalaryBudgetEachProject(rs.getLong("totalSalaryBudget"));
			} catch (SQLException se) {
			}
			return expenseRealTimeResultDetailForSanction;
		} catch (SQLException e) {
			throw exceptionTranslator.translate("ExpenseRealTimeResultDetailWrapper fails", null, e);
		}
	}

	public void setValueListInfo(ValueListInfo arg0) {
	}
}
