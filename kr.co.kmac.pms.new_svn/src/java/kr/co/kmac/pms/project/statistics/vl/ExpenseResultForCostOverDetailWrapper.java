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
 * TODO Ŭ���� ����
 * @author jiwoongLee
 * @version $Id: ExpenseResultForCostOverDetailWrapper.java,v 1.2 2011/01/17 08:52:55 cvs Exp $
 */
public class ExpenseResultForCostOverDetailWrapper implements ObjectWrapper {

	private SQLExceptionTranslator exceptionTranslator = new SQLStateSQLExceptionTranslator();

	public Object getWrappedRecord(Object objectToBeWrapped) {
		ResultSet rs = (ResultSet) objectToBeWrapped;// ����, ����, �Ҵ�����, 
		try {
			ProjectReportListEntity projectReport = new ProjectReportListEntity();
			projectReport.setWriterName(rs.getString("writerName"));// �ۼ���(������Ʈ)
			projectReport.setAssignDate(rs.getString("assignDate"));// �Ҵ�����
			projectReport.setWriteDate(rs.getString("writeDate"));// �ۼ�����
			projectReport.setRevieweDate(rs.getString("revieweDate"));// PM Ȯ������
			projectReport.setReviewerName(rs.getString("reviewerName"));// PM �ֹι�ȣ
			projectReport.setApproveDate(rs.getString("approveDate"));// AG Ȯ������
			projectReport.setApproverName(rs.getString("approverName"));// AG �ֹι�ȣ
			projectReport.setState(rs.getString("state"));//���� writer(������Ʈ), reviewer(PM), approver(AG)
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
