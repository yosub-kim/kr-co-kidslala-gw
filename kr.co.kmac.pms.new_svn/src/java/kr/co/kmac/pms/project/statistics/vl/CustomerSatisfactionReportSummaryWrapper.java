package kr.co.kmac.pms.project.statistics.vl;

import java.sql.ResultSet;
import java.sql.SQLException;

import kr.co.kmac.pms.project.statistics.data.CustomerSatisfactionReportSummary;
import net.mlw.vlh.ValueListInfo;
import net.mlw.vlh.adapter.util.ObjectWrapper;

import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.jdbc.support.SQLStateSQLExceptionTranslator;

public class CustomerSatisfactionReportSummaryWrapper implements ObjectWrapper {

	private SQLExceptionTranslator exceptionTranslator = new SQLStateSQLExceptionTranslator();

	public Object getWrappedRecord(Object arg0) {
		ResultSet rs = (ResultSet) arg0;
		try {
			CustomerSatisfactionReportSummary custSatisResult = new CustomerSatisfactionReportSummary();
			custSatisResult.setRunningDivCode(rs.getString("runningDivCode"));
			custSatisResult.setRunningDivName(rs.getString("runningDivName"));
			custSatisResult.setBusinessTypeCode(rs.getString("businessTypeCode"));
			custSatisResult.setBusinessTypeName(rs.getString("businessTypeName"));
			custSatisResult.setHigh(rs.getString("high"));
			custSatisResult.setMedium(rs.getString("medium"));
			custSatisResult.setLow(rs.getString("low"));
			custSatisResult.setDelayCnt(rs.getString("delayCnt"));
			return custSatisResult;
		} catch (SQLException e) {
			throw exceptionTranslator.translate("CustomerSatisfactionReportSummaryWrapper fails", null, e);
		}
	}

	public void setValueListInfo(ValueListInfo arg0) {
	}
}