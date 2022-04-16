package kr.co.kmac.pms.project.wreport.vl;

import java.sql.ResultSet;
import java.sql.SQLException;

import kr.co.kmac.pms.project.wreport.data.WeeklyReport;
import net.mlw.vlh.ValueListInfo;
import net.mlw.vlh.adapter.util.ObjectWrapper;

import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.jdbc.support.SQLStateSQLExceptionTranslator;

public class WeeklyReportWrapper  implements ObjectWrapper {

	private SQLExceptionTranslator exceptionTranslator = new SQLStateSQLExceptionTranslator();

	public Object getWrappedRecord(Object objectToBeWrapped) {
		ResultSet rs = (ResultSet) objectToBeWrapped;// 상태, 제목, 할당일자, 
		try {
			WeeklyReport weeklyReport = new WeeklyReport();
			weeklyReport.setProjectCode(rs.getString("projectCode"));
			weeklyReport.setAssignDate(rs.getString("assignDate"));
			weeklyReport.setAssignYear(rs.getString("assignYear"));
			weeklyReport.setAssignMonth(rs.getString("assignMonth"));
			weeklyReport.setAssignWeekOfMonth(rs.getString("assignWeekOfMonth"));
			weeklyReport.setWeekOfProject(rs.getString("weekOfProject"));

			weeklyReport.setRealProgress(rs.getString("realProgress"));
			weeklyReport.setPlanProgress(rs.getString("planProgress"));
			weeklyReport.setProgressRatio(rs.getString("progressRatio"));

			weeklyReport.setThisWeekFromDate(rs.getString("thisWeekFromDate"));
			weeklyReport.setThisWeekToDate(rs.getString("thisWeekToDate"));

			weeklyReport.setNextWeekFromDate(rs.getString("nextWeekFromDate"));
			weeklyReport.setNextWeekToDate(rs.getString("nextWeekToDate"));


			weeklyReport.setAttach(rs.getString("attach"));
			weeklyReport.setState(rs.getString("state"));

			weeklyReport.setWriterSsn(rs.getString("writerSsn"));
			weeklyReport.setWriterName(rs.getString("writerName"));
			weeklyReport.setWriteDate(rs.getString("writeDate"));
			weeklyReport.setReviewerSsn(rs.getString("reviewerSsn"));
			weeklyReport.setReviewerName(rs.getString("reviewerName"));
			weeklyReport.setRevieweDate(rs.getString("revieweDate"));
			weeklyReport.setApproverSsn(rs.getString("approverSsn"));
			weeklyReport.setApproverName(rs.getString("approverName"));
			weeklyReport.setApproveDate(rs.getString("approveDate"));
			return weeklyReport;
		} catch (SQLException e) {
			throw exceptionTranslator.translate("WeeklyReportWrapper fails", null, e);
		}
	}

	public void setValueListInfo(ValueListInfo arg0) {
	}

}
