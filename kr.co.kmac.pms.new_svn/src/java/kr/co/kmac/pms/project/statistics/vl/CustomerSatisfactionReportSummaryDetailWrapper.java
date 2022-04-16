package kr.co.kmac.pms.project.statistics.vl;

import java.sql.ResultSet;
import java.sql.SQLException;

import kr.co.kmac.pms.project.statistics.data.CustomerSatisfactionReportSummaryDetail;
import net.mlw.vlh.ValueListInfo;
import net.mlw.vlh.adapter.util.ObjectWrapper;

import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.jdbc.support.SQLStateSQLExceptionTranslator;

public class CustomerSatisfactionReportSummaryDetailWrapper implements ObjectWrapper {

	private SQLExceptionTranslator exceptionTranslator = new SQLStateSQLExceptionTranslator();

	public Object getWrappedRecord(Object arg0) {
		ResultSet rs = (ResultSet) arg0;
		try {
			CustomerSatisfactionReportSummaryDetail custSatisResult = new CustomerSatisfactionReportSummaryDetail();
			custSatisResult.setBusinessTypeCode(rs.getString("businessTypeCode"));
			custSatisResult.setBusinessTypeName(rs.getString("businessTypeName"));
			custSatisResult.setRunningDivCode(rs.getString("runningDivCode"));
			custSatisResult.setRunningDivName(rs.getString("runningDivName"));
			custSatisResult.setProjectName(rs.getString("projectName"));
			custSatisResult.setProjectCode(rs.getString("projectCode"));
			custSatisResult.setPjEndDate(rs.getString("pjEndDate"));
			custSatisResult.setSurveyDate(rs.getString("surveyDate"));
			custSatisResult.setScore(rs.getString("score"));
			//custSatisResult.setSeq(rs.getString("seq"));
			custSatisResult.setCustomerName(rs.getString("customerName"));
			return custSatisResult;
		} catch (SQLException e) {
			throw exceptionTranslator.translate("CustomerSatisfactionReportSummaryDetailWrapper fails", null, e);
		}
	}

	public void setValueListInfo(ValueListInfo arg0) {
	}
}