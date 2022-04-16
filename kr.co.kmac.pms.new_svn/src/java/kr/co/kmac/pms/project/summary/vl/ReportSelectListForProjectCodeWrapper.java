/*
 * $Id: ReportSelectListForProjectCodeWrapper.java,v 1.1 2009/09/19 11:15:27 cvs3 Exp $
 * creation-date : 2006. 3. 27.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.project.summary.vl;

import java.sql.ResultSet;
import java.sql.SQLException;

import kr.co.kmac.pms.project.summary.data.ProjectSummaryData;
import net.mlw.vlh.ValueListInfo;
import net.mlw.vlh.adapter.util.ObjectWrapper;

import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.jdbc.support.SQLStateSQLExceptionTranslator;

/**
 * TODO Provide description for "AttachSelectListWrapper"
 * @author halberd
 * @version $Id: ReportSelectListForProjectCodeWrapper.java,v 1.1 2009/09/19 11:15:27 cvs3 Exp $
 */
public class ReportSelectListForProjectCodeWrapper implements ObjectWrapper {
	private SQLExceptionTranslator exceptionTranslator = new SQLStateSQLExceptionTranslator();
	/* (non-Javadoc)
	 * @see net.mlw.vlh.adapter.util.ObjectWrapper#getWrappedRecord(java.lang.Object)
	 */
	public Object getWrappedRecord(Object objectToBeWrapped) {
		// TODO Auto-generated method stub
		ResultSet rs = (ResultSet) objectToBeWrapped;
		try {
			ProjectSummaryData projectSummaryData = new ProjectSummaryData();			
			projectSummaryData.setProjectCode(rs.getString("projectCode"));
			projectSummaryData.setProjectName(rs.getString("projectName"));
			projectSummaryData.setReportTitle(rs.getString("reportTitle"));
			projectSummaryData.setWriterName(rs.getString("writerName"));
			projectSummaryData.setWriteDate(rs.getString("writeDate"));
			projectSummaryData.setPmName(rs.getString("pmName"));
			projectSummaryData.setPlName(rs.getString("plName"));
			return projectSummaryData;
		} catch (SQLException e) {
			throw exceptionTranslator.translate("ReportSelectListForProjectCodeWrapper fails",
					null,
					e);
		}
	}

	/* (non-Javadoc)
	 * @see net.mlw.vlh.adapter.util.ObjectWrapper#setValueListInfo(net.mlw.vlh.ValueListInfo)
	 */
	public void setValueListInfo(ValueListInfo info) {
		// TODO Auto-generated method stub

	}

}
