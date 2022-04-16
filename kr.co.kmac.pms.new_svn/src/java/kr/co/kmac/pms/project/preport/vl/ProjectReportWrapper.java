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
 * TODO 클래스 설명
 * @author jiwoongLee
 * @version $Id: ProjectReportWrapper.java,v 1.2 2010/01/03 12:46:49 cvs1 Exp $
 */
public class ProjectReportWrapper implements ObjectWrapper {

	private SQLExceptionTranslator exceptionTranslator = new SQLStateSQLExceptionTranslator();

	public Object getWrappedRecord(Object objectToBeWrapped) {
		ResultSet rs = (ResultSet) objectToBeWrapped;// 상태, 제목, 할당일자, 
		try {
			ProjectReportListEntity projectReport = new ProjectReportListEntity();
			projectReport.setProjectCode(rs.getString("projectCode"));// 프로젝트코드
			projectReport.setTaskFormTypeId(rs.getString("taskFormTypeId"));//
			projectReport.setTaskFormTypeName(rs.getString("name"));// 제목
			projectReport.setSeq(rs.getString("seq"));//순서
			projectReport.setState(rs.getString("state"));//상태 writer(컨설턴트), reviewer(PM), approver(AG)
			projectReport.setAssignDate(rs.getString("assignDate"));// 할당일자
			projectReport.setWriteDate(rs.getString("writeDate"));// 작성일자
			projectReport.setWriterSsn(rs.getString("writerSsn"));// 작성자(컨설턴트)
			projectReport.setWriterName(rs.getString("writerName"));// 작성자(컨설턴트)
			projectReport.setRevieweDate(rs.getString("revieweDate"));// PM 확인일자
			projectReport.setReviewerSsn(rs.getString("reviewerSsn"));// PM 주민번호
			projectReport.setReviewerName(rs.getString("reviewerName"));// PM 주민번호
			projectReport.setApproveDate(rs.getString("approveDate"));// AG 확인일자
			projectReport.setApproverSsn(rs.getString("approverSsn"));// AG 주민번호
			projectReport.setApproverName(rs.getString("approverName"));// AG 주민번호
			return projectReport;
		} catch (SQLException e) {
			throw exceptionTranslator.translate("ProjectReportWrapper fails", null, e);
		}
	}

	public void setValueListInfo(ValueListInfo arg0) {
	}

}
