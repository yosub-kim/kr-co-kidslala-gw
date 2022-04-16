/*
 * $Id: ProjectMonthlyReportWrapper.java,v 1.2 2010/05/12 09:35:58 cvs2 Exp $
 * created by    : ¿”øµ»∆
 * creation-date : 2010. 5. 11.
 * =========================================================
 * Copyright (c) 2010 KMAC All rights reserved.
 */
package kr.co.kmac.pms.project.statistics.vl;

import java.sql.ResultSet;
import java.sql.SQLException;

import kr.co.kmac.pms.project.statistics.data.ProjectMonthlyReportData;
import net.mlw.vlh.ValueListInfo;
import net.mlw.vlh.adapter.util.ObjectWrapper;

import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.jdbc.support.SQLStateSQLExceptionTranslator;

public class ProjectMonthlyReportWrapper implements ObjectWrapper {

	private SQLExceptionTranslator exceptionTranslator = new SQLStateSQLExceptionTranslator();
	/* (non-Javadoc)
	 * @see net.mlw.vlh.adapter.util.ObjectWrapper#getWrappedRecord(java.lang.Object)
	 */
	public Object getWrappedRecord(Object objectToBeWrapped) {
		// TODO Auto-generated method stub
		ResultSet rs = (ResultSet) objectToBeWrapped;
		try {
				ProjectMonthlyReportData projectMonthlyReportData = new ProjectMonthlyReportData();
				
				projectMonthlyReportData.setProjectCode(rs.getString("projectCode"));
				projectMonthlyReportData.setWriterSsn(rs.getString("writerSsn"));
				projectMonthlyReportData.setWriterName(rs.getString("writerName"));
				projectMonthlyReportData.setState(rs.getString("state"));
				projectMonthlyReportData.setProjectName(rs.getString("projectName"));
				projectMonthlyReportData.setBusinessTypeCode(rs.getString("businessTypeCode"));
				projectMonthlyReportData.setBusinessTypeName(rs.getString("businessTypeName"));
				projectMonthlyReportData.setAssignDate(rs.getString("assignDate"));
				projectMonthlyReportData.setAssignee(rs.getString("assignee")); 
			return projectMonthlyReportData;
		}catch (SQLException e) {
			throw exceptionTranslator.translate("ProjectMonthlyReportWrapper fails", null, e);
		}
		
	}

	/* (non-Javadoc)
	 * @see net.mlw.vlh.adapter.util.ObjectWrapper#setValueListInfo(net.mlw.vlh.ValueListInfo)
	 */
	public void setValueListInfo(ValueListInfo valueListInfo) {
		// TODO Auto-generated method stub

	}
}
