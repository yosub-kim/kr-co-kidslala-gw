/*
 * $Id: ProjectReportWrapper.java,v 1.2 2010/01/03 12:46:49 cvs1 Exp $
 * created by    : jiwoongLee
 * creation-date : 2006. 2. 27.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.project.preport.vl;

import java.sql.ResultSet;
import java.sql.SQLException;

import kr.co.kmac.pms.project.preport.data.ProjectReportListEntity;
import net.mlw.vlh.ValueListInfo;
import net.mlw.vlh.adapter.util.ObjectWrapper;

import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.jdbc.support.SQLStateSQLExceptionTranslator;

/**
 * TODO Ŭ���� ����
 * @author jiwoongLee
 * @version $Id: ProjectReportWrapper.java,v 1.2 2010/01/03 12:46:49 cvs1 Exp $
 */
public class ProjectReportWrapper implements ObjectWrapper {

	private SQLExceptionTranslator exceptionTranslator = new SQLStateSQLExceptionTranslator();

	public Object getWrappedRecord(Object objectToBeWrapped) {
		ResultSet rs = (ResultSet) objectToBeWrapped;// ����, ����, �Ҵ�����, 
		try {
			ProjectReportListEntity projectReport = new ProjectReportListEntity();
			projectReport.setProjectCode(rs.getString("projectCode"));// ������Ʈ�ڵ�
			projectReport.setTaskFormTypeId(rs.getString("taskFormTypeId"));//
			projectReport.setTaskFormTypeName(rs.getString("name"));// ����
			projectReport.setSeq(rs.getString("seq"));//����
			projectReport.setState(rs.getString("state"));//���� writer(������Ʈ), reviewer(PM), approver(AG)
			projectReport.setAssignDate(rs.getString("assignDate"));// �Ҵ�����
			projectReport.setWriteDate(rs.getString("writeDate"));// �ۼ�����
			projectReport.setWriterSsn(rs.getString("writerSsn"));// �ۼ���(������Ʈ)
			projectReport.setWriterName(rs.getString("writerName"));// �ۼ���(������Ʈ)
			projectReport.setRevieweDate(rs.getString("revieweDate"));// PM Ȯ������
			projectReport.setReviewerSsn(rs.getString("reviewerSsn"));// PM �ֹι�ȣ
			projectReport.setReviewerName(rs.getString("reviewerName"));// PM �ֹι�ȣ
			projectReport.setApproveDate(rs.getString("approveDate"));// AG Ȯ������
			projectReport.setApproverSsn(rs.getString("approverSsn"));// AG �ֹι�ȣ
			projectReport.setApproverName(rs.getString("approverName"));// AG �ֹι�ȣ
			return projectReport;
		} catch (SQLException e) {
			throw exceptionTranslator.translate("ProjectReportWrapper fails", null, e);
		}
	}

	public void setValueListInfo(ValueListInfo arg0) {
	}

}
