package kr.co.kmac.pms.project.mreport.vl;

import java.sql.ResultSet;
import java.sql.SQLException;

import kr.co.kmac.pms.project.mreport.data.MonthlyReport;
import net.mlw.vlh.ValueListInfo;
import net.mlw.vlh.adapter.util.ObjectWrapper;

import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.jdbc.support.SQLStateSQLExceptionTranslator;

public class MonthlyReportWrapper  implements ObjectWrapper {

	private SQLExceptionTranslator exceptionTranslator = new SQLStateSQLExceptionTranslator();

	public Object getWrappedRecord(Object objectToBeWrapped) {
		ResultSet rs = (ResultSet) objectToBeWrapped;// 상태, 제목, 할당일자, 
		try {
			MonthlyReport monthlyReport = new MonthlyReport();
			monthlyReport.setProjectCode(rs.getString("projectCode"));
			monthlyReport.setAssignDate(rs.getString("assignDate"));
			monthlyReport.setAssignYear(rs.getString("assignYear"));
			monthlyReport.setAssignMonth(rs.getString("assignMonth"));

			monthlyReport.setReportTitle(rs.getString("reportTitle"));
			monthlyReport.setReportContent(rs.getString("reportContent"));
			monthlyReport.setEtcContent(rs.getString("etcContent"));

			monthlyReport.setAttach(rs.getString("attach"));
			monthlyReport.setState(rs.getString("state"));

			monthlyReport.setWriterSsn(rs.getString("writerSsn"));
			monthlyReport.setWriterName(rs.getString("writerName"));
			monthlyReport.setWriteDate(rs.getString("writeDate"));
			monthlyReport.setReviewerSsn(rs.getString("reviewerSsn"));
			monthlyReport.setReviewerName(rs.getString("reviewerName"));
			monthlyReport.setRevieweDate(rs.getString("revieweDate"));
			monthlyReport.setApproverSsn(rs.getString("approverSsn"));
			monthlyReport.setApproverName(rs.getString("approverName"));
			monthlyReport.setApproveDate(rs.getString("approveDate"));
			return monthlyReport;
		} catch (SQLException e) {
			throw exceptionTranslator.translate("WeeklyReportWrapper fails", null, e);
		}
	}

	public void setValueListInfo(ValueListInfo arg0) {
	}

}
