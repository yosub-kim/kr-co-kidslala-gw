/*
 * $Id: ExpenseContributionCostRealTimeResultDetailWrapper.java,v 1.1 2009/09/19 11:15:29 cvs3 Exp $
 * created by    : ¿”øµ»∆
 * creation-date : 2009. 5. 7.
 * =========================================================
 * Copyright (c) KMAC All rights reserved.
 */
package kr.co.kmac.pms.project.statistics.vl;

import java.sql.ResultSet;
import java.sql.SQLException;

import kr.co.kmac.pms.project.statistics.data.ExpenseContributionCostRealTimeResult;
import net.mlw.vlh.ValueListInfo;
import net.mlw.vlh.adapter.util.ObjectWrapper;

import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.jdbc.support.SQLStateSQLExceptionTranslator;

public class ExpenseContributionCostRealTimeResultDetailWrapper implements ObjectWrapper {

	private SQLExceptionTranslator exceptionTranslator = new SQLStateSQLExceptionTranslator();

	public Object getWrappedRecord(Object arg0) {
		ResultSet rs = (ResultSet) arg0;
		try {
			ExpenseContributionCostRealTimeResult expenseContributionCostRealTimeResult = new ExpenseContributionCostRealTimeResult();
			expenseContributionCostRealTimeResult.setDept(rs.getString("dept"));
			expenseContributionCostRealTimeResult.setProjectName(rs.getString("projectName"));
			expenseContributionCostRealTimeResult.setName(rs.getString("Name"));
			expenseContributionCostRealTimeResult.setSsn(rs.getString("ssn"));
			expenseContributionCostRealTimeResult.setRealTimeSalary(rs.getString("realTimeSalary"));
			expenseContributionCostRealTimeResult.setRealWorkCount(rs.getString("realWorkCount"));
			expenseContributionCostRealTimeResult.setTotalContributionCost(rs.getString("totalContributionCost"));			
			return expenseContributionCostRealTimeResult;
		} catch (SQLException e) {
			throw exceptionTranslator.translate("ExpenseContributionCostRealTimeResultDetailWrapper fails", null, e);
		}
	}

	public void setValueListInfo(ValueListInfo arg0) {
	}
}
