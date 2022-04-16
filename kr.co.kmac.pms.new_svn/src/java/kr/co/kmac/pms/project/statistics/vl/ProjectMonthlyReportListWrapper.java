/*
 * $Id: ProjectMonthlyReportListWrapper.java,v 1.2 2010/05/12 09:35:58 cvs2 Exp $
 * created by    : ¾È¼ºÈ£
 * creation-date : 2006. 5. 18.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.project.statistics.vl;

import java.sql.ResultSet;
import java.sql.SQLException;

import kr.co.kmac.pms.project.statistics.data.ProjectMonthlyReportDataForList;
import net.mlw.vlh.ValueListInfo;
import net.mlw.vlh.adapter.util.ObjectWrapper;

import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.jdbc.support.SQLStateSQLExceptionTranslator;

public class ProjectMonthlyReportListWrapper implements ObjectWrapper {

	private SQLExceptionTranslator exceptionTranslator = new SQLStateSQLExceptionTranslator();

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.mlw.vlh.adapter.util.ObjectWrapper#getWrappedRecord(java.lang.Object)
	 */
	public Object getWrappedRecord(Object objectToBeWrapped) {
		// TODO Auto-generated method stub
		ResultSet rs = (ResultSet) objectToBeWrapped;
		try {
			ProjectMonthlyReportDataForList projectMonthlyReportDataForList = new ProjectMonthlyReportDataForList();

			projectMonthlyReportDataForList.setWriter(rs.getString("writer"));
			projectMonthlyReportDataForList.setName(rs.getString("name"));
			projectMonthlyReportDataForList.setDeptCode(rs.getString("deptCode"));
			projectMonthlyReportDataForList.setDept(rs.getString("dept"));
			projectMonthlyReportDataForList.setBizTypeCode(rs.getString("bizTypeCode"));
			projectMonthlyReportDataForList.setBizType(rs.getString("bizType"));
			projectMonthlyReportDataForList.setIsWrite(rs.getString("isWrite"));
			projectMonthlyReportDataForList.setIsNotWrite(rs.getString("isNotWrite"));
			projectMonthlyReportDataForList.setIsNotReview(rs.getString("isNotReview"));
			projectMonthlyReportDataForList.setIsNotApprove(rs.getString("isNotApprove"));
			projectMonthlyReportDataForList.setConfirm(rs.getString("confirm"));
			projectMonthlyReportDataForList.setSeq(rs.getString("seq"));
			projectMonthlyReportDataForList.setTotal(rs.getString("total"));
			projectMonthlyReportDataForList.setProjectName(rs.getString("projectName"));

			return projectMonthlyReportDataForList;
		} catch (SQLException e) {
			throw exceptionTranslator.translate("ProjectMonthlyReportListWrapper fails", null, e);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.mlw.vlh.adapter.util.ObjectWrapper#setValueListInfo(net.mlw.vlh.ValueListInfo)
	 */
	public void setValueListInfo(ValueListInfo valueListInfo) {
		// TODO Auto-generated method stub

	}
}
