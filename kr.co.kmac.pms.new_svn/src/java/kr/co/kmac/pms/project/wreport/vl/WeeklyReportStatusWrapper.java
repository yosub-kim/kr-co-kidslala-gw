package kr.co.kmac.pms.project.wreport.vl;

import java.sql.ResultSet;
import java.sql.SQLException;

import kr.co.kmac.pms.project.wreport.data.WeeklyReport;
import net.mlw.vlh.ValueListInfo;
import net.mlw.vlh.adapter.util.ObjectWrapper;

import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.jdbc.support.SQLStateSQLExceptionTranslator;

public class WeeklyReportStatusWrapper  implements ObjectWrapper {

	private SQLExceptionTranslator exceptionTranslator = new SQLStateSQLExceptionTranslator();

	public Object getWrappedRecord(Object objectToBeWrapped) {
		ResultSet rs = (ResultSet) objectToBeWrapped;// 상태, 제목, 할당일자, 
		try {
			WeeklyReport weeklyReport = new WeeklyReport();
			weeklyReport.setProjectCode(rs.getString("projectCode"));
			weeklyReport.setProjectName(rs.getString("projectName"));
			weeklyReport.setProjectState(rs.getString("projectState"));
			weeklyReport.setProjectStateStr(rs.getString("projectStateStr"));
			

			weeklyReport.setWriterName(rs.getString("writerName"));

			weeklyReport.setWriterCnt(rs.getString("writerCnt"));
			weeklyReport.setReviewerCnt(rs.getString("reviewerCnt"));
			weeklyReport.setApproverCnt(rs.getString("approverCnt"));
			weeklyReport.setCompleteCnt(rs.getString("completeCnt"));
			
			return weeklyReport;
		} catch (SQLException e) {
			throw exceptionTranslator.translate("WeeklyReportStatusWrapper fails", null, e);
		}
	}

	public void setValueListInfo(ValueListInfo arg0) {
	}

}
