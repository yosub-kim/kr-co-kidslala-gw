/*
 * $Id: ProjectMonthlyReportListWrapper2.java,v 1.1 2011/09/25 05:09:13 cvs Exp $
 * created by    : ¿”øµ»∆
 * creation-date : 2011. 9. 23.
 * =========================================================
 * Copyright (c) 2011 KMAC. All rights reserved.
 */
package kr.co.kmac.pms.project.statistics.vl;

import java.sql.ResultSet;
import java.sql.SQLException;

import kr.co.kmac.pms.project.statistics.data.ProjectMonthlyReportDataForList;
import net.mlw.vlh.ValueListInfo;
import net.mlw.vlh.adapter.util.ObjectWrapper;

import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.jdbc.support.SQLStateSQLExceptionTranslator;

public class ProjectMonthlyReportListWrapper2 implements ObjectWrapper {

	private SQLExceptionTranslator exceptionTranslator = new SQLStateSQLExceptionTranslator();
	/* (non-Javadoc)
	 * @see net.mlw.vlh.adapter.util.ObjectWrapper#getWrappedRecord(java.lang.Object)
	 */
	public Object getWrappedRecord(Object objectToBeWrapped) {
		// TODO Auto-generated method stub
		ResultSet rs = (ResultSet) objectToBeWrapped;
		try {
				ProjectMonthlyReportDataForList projectMonthlyReportDataForList = new ProjectMonthlyReportDataForList();
				
				projectMonthlyReportDataForList.setWriter(rs.getString("projectCode"));
				projectMonthlyReportDataForList.setName(rs.getString("projectName"));
				projectMonthlyReportDataForList.setDeptCode(rs.getString("jobClass"));
				projectMonthlyReportDataForList.setDept(rs.getString("jobClassName")); 
				projectMonthlyReportDataForList.setBizTypeCode(rs.getString("businessTypeCode"));
				projectMonthlyReportDataForList.setBizType(rs.getString("businessTypeName"));
				projectMonthlyReportDataForList.setIsWrite(rs.getString("isWrite"));
				projectMonthlyReportDataForList.setIsNotWrite(rs.getString("isNotWrite"));
				projectMonthlyReportDataForList.setIsNotReview(rs.getString("isNotReview"));
				projectMonthlyReportDataForList.setIsNotApprove(rs.getString("isNotApprove"));
				projectMonthlyReportDataForList.setConfirm(rs.getString("confirm"));
				//projectMonthlyReportDataForList.setSeq(rs.getString("seq"));
			return projectMonthlyReportDataForList;
		}catch (SQLException e) {
			throw exceptionTranslator.translate("ProjectMonthlyReportListWrapper fails", null, e);
		}
		
	}

	/* (non-Javadoc)
	 * @see net.mlw.vlh.adapter.util.ObjectWrapper#setValueListInfo(net.mlw.vlh.ValueListInfo)
	 */
	public void setValueListInfo(ValueListInfo valueListInfo) {
		// TODO Auto-generated method stub

	}
}
