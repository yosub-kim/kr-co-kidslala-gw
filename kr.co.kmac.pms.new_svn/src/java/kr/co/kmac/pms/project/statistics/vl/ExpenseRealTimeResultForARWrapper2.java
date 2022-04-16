/*
 * $Id: ExpenseRealTimeResultForARWrapper.java,v 1.3 2011/10/31 22:31:37 cvs Exp $
 * created by    : ¿”øµ»∆
 * creation-date : 2010. 2. 25.
 * =========================================================
 * Copyright (c) 2010 KMAC All rights reserved.
 */
package kr.co.kmac.pms.project.statistics.vl;

import java.sql.ResultSet;
import java.sql.SQLException;

import kr.co.kmac.pms.project.statistics.data.ExpenseRealTimeResult;
import net.mlw.vlh.ValueListInfo;
import net.mlw.vlh.adapter.util.ObjectWrapper;

import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.jdbc.support.SQLStateSQLExceptionTranslator;

public class ExpenseRealTimeResultForARWrapper2 implements ObjectWrapper {

	private SQLExceptionTranslator exceptionTranslator = new SQLStateSQLExceptionTranslator();

	public Object getWrappedRecord(Object arg0) {
		ResultSet rs = (ResultSet) arg0;
		try {
			ExpenseRealTimeResult expenseRealTimeResult = new ExpenseRealTimeResult();
			expenseRealTimeResult.setProjectCode(rs.getString("projectCode"));
			expenseRealTimeResult.setProjectName(rs.getString("projectName"));
			expenseRealTimeResult.setSsn(rs.getString("ssn"));
			expenseRealTimeResult.setName(rs.getString("name"));
			expenseRealTimeResult.setDeptName(rs.getString("deptName"));
			expenseRealTimeResult.setRole(rs.getString("role"));
			expenseRealTimeResult.setPm(rs.getString("pm"));
			expenseRealTimeResult.setRealTimeSalary(rs.getLong("realTimeSalary"));
			expenseRealTimeResult.setRealTimeSalary2(rs.getLong("realTimeSalary2"));
			expenseRealTimeResult.setInvolvedPrjCnt(rs.getString("involvedPrjCnt"));
			expenseRealTimeResult.setSalarySum2(rs.getLong("salarySum2"));
			expenseRealTimeResult.setRate(rs.getString("rate"));
			expenseRealTimeResult.setDeptName(rs.getString("deptName"));
			expenseRealTimeResult.setRole(rs.getString("role"));
			expenseRealTimeResult.setIsExceed(rs.getString("isExceed"));
			expenseRealTimeResult.setSalarySum2(rs.getLong("salarySum2"));
			expenseRealTimeResult.setIsSanction(rs.getString("isSanction"));
			expenseRealTimeResult.setIsSanction2(rs.getString("isSanction2"));
			return expenseRealTimeResult;
		} catch (SQLException e) {
			throw exceptionTranslator.translate("ExpenseRealTimeResultForARWrapper fails", null, e);
		}
	}

	public void setValueListInfo(ValueListInfo arg0) {
	}
}
