package kr.co.kmac.pms.project.statistics.vl;

import java.sql.ResultSet;
import java.sql.SQLException;

import kr.co.kmac.pms.project.statistics.data.CustomerSatisfactionReportList;
import net.mlw.vlh.ValueListInfo;
import net.mlw.vlh.adapter.util.ObjectWrapper;

import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.jdbc.support.SQLStateSQLExceptionTranslator;

public class CustomerSatisfactionReportListWrapper implements ObjectWrapper {

	private SQLExceptionTranslator exceptionTranslator = new SQLStateSQLExceptionTranslator();

	public Object getWrappedRecord(Object arg0) {
		ResultSet rs = (ResultSet) arg0;
		try {
			CustomerSatisfactionReportList custSatisResult = new CustomerSatisfactionReportList();
			custSatisResult.setProjectCode(rs.getString("projectCode"));
			custSatisResult.setProjectName(rs.getString("projectName"));
			custSatisResult.setRunningDivCode(rs.getString("runningDivCode"));
			custSatisResult.setRunningDivName(rs.getString("runningDivName"));
			custSatisResult.setBusinessTypeCode(rs.getString("businessTypeCode"));
			custSatisResult.setBusinessTypeName(rs.getString("businessTypeName"));
			custSatisResult.setRatio(rs.getString("ratio"));
			custSatisResult.setSeq(rs.getString("seq"));
			custSatisResult.setCustomerName(rs.getString("customerName"));
			custSatisResult.setReceiveEmail(rs.getString("receiveEmail"));
			custSatisResult.setWriteDate(rs.getString("writeDate"));
			return custSatisResult;
		} catch (SQLException e) {
			throw exceptionTranslator.translate("CustomerSatisfactionReportListWrapper fails", null, e);
		}
	}

	public void setValueListInfo(ValueListInfo arg0) {
	}
}