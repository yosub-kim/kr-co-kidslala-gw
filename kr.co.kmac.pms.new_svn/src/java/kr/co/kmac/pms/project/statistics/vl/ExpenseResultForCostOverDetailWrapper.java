/*
 * $Id: ExpenseResultForCostOverDetailWrapper.java,v 1.2 2011/01/17 08:52:55 cvs Exp $
 * created by    : jiwoongLee
 * creation-date : 2006. 2. 27.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.project.statistics.vl;

import java.sql.ResultSet;
import java.sql.SQLException;

import kr.co.kmac.pms.common.util.StringUtil;
import kr.co.kmac.pms.project.preport.data.ProjectReportListEntity;
import net.mlw.vlh.ValueListInfo;
import net.mlw.vlh.adapter.util.ObjectWrapper;

import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.jdbc.support.SQLStateSQLExceptionTranslator;

/**
 * TODO 클래스 설명
 * @author jiwoongLee
 * @version $Id: ExpenseResultForCostOverDetailWrapper.java,v 1.2 2011/01/17 08:52:55 cvs Exp $
 */
public class ExpenseResultForCostOverDetailWrapper implements ObjectWrapper {

	private SQLExceptionTranslator exceptionTranslator = new SQLStateSQLExceptionTranslator();

	public Object getWrappedRecord(Object objectToBeWrapped) {
		ResultSet rs = (ResultSet) objectToBeWrapped;// 상태, 제목, 할당일자, 
		try {
			ProjectReportListEntity projectReport = new ProjectReportListEntity();
			projectReport.setWriterName(rs.getString("writerName"));// 작성자(컨설턴트)
			projectReport.setAssignDate(rs.getString("assignDate"));// 할당일자
			projectReport.setWriteDate(rs.getString("writeDate"));// 작성일자
			projectReport.setRevieweDate(rs.getString("revieweDate"));// PM 확인일자
			projectReport.setReviewerName(rs.getString("reviewerName"));// PM 주민번호
			projectReport.setApproveDate(rs.getString("approveDate"));// AG 확인일자
			projectReport.setApproverName(rs.getString("approverName"));// AG 주민번호
			projectReport.setState(rs.getString("state"));//상태 writer(컨설턴트), reviewer(PM), approver(AG)
			projectReport.setJobClass(rs.getString("jobClass"));// 
			projectReport.setPayAmount(rs.getString("payAmount"));// 
			projectReport.setRealtimeSalary(StringUtil.longt2Money(rs.getLong("realtimeSalary")));// 
			projectReport.setTotalRealTimeSalary(StringUtil.longt2Money(rs.getLong("totalRealTimeSalary")));// 
			projectReport.setGrandTotalRealTimeSalary(StringUtil.longt2Money(rs.getLong("grandTotalRealTimeSalary")));// 
			return projectReport;
		} catch (SQLException e) {
			throw exceptionTranslator.translate("ProjectReportWrapper fails", null, e);
		}
	}

	public void setValueListInfo(ValueListInfo arg0) {
	}

}
