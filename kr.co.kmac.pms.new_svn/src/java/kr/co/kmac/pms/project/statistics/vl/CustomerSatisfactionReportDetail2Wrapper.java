package kr.co.kmac.pms.project.statistics.vl;

import java.sql.ResultSet;
import java.sql.SQLException;

import kr.co.kmac.pms.project.statistics.data.CustomerSatisfactionReportDetail;
import net.mlw.vlh.ValueListInfo;
import net.mlw.vlh.adapter.util.ObjectWrapper;

import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.jdbc.support.SQLStateSQLExceptionTranslator;

public class CustomerSatisfactionReportDetail2Wrapper implements ObjectWrapper {

	private SQLExceptionTranslator exceptionTranslator = new SQLStateSQLExceptionTranslator();

	public Object getWrappedRecord(Object arg0) {
		ResultSet rs = (ResultSet) arg0;
		try {
			CustomerSatisfactionReportDetail CustSatisResult = new CustomerSatisfactionReportDetail();
			CustSatisResult.setBusinessTypeName(rs.getString("businessTypeName"));
			CustSatisResult.setRunningDivName(rs.getString("runningDivName"));
			CustSatisResult.setProjectName(rs.getString("projectName"));
			CustSatisResult.setCustomerName(rs.getString("customerName"));
			CustSatisResult.setJikwee(rs.getString("jikwee"));
			CustSatisResult.setCustomerWorkPName(rs.getString("customerWorkPName"));
			CustSatisResult.setCs1(rs.getString("cs1"));
			CustSatisResult.setCs2(rs.getString("cs2"));
			CustSatisResult.setCs3(rs.getString("cs3"));
			CustSatisResult.setCs4(rs.getString("cs4"));
			CustSatisResult.setCs5(rs.getString("cs5"));
			CustSatisResult.setCs6(rs.getString("cs6"));
			CustSatisResult.setCs7(rs.getString("cs7"));
			CustSatisResult.setCs8(rs.getString("cs8"));
			CustSatisResult.setCs9(rs.getString("cs9"));
			CustSatisResult.setCs10(rs.getString("cs10"));
			CustSatisResult.setCssn(rs.getString("cssn"));
			CustSatisResult.setCname(rs.getString("cname"));
			CustSatisResult.setRc8(rs.getString("rc8"));
			CustSatisResult.setRc9(rs.getString("rc9"));
			CustSatisResult.setRc10(rs.getString("rc10"));
			CustSatisResult.setRc12(rs.getString("rc12"));
			CustSatisResult.setRc13(rs.getString("rc13"));
			CustSatisResult.setOpinion(rs.getString("opinion"));
			CustSatisResult.setComment(rs.getString("comment"));			
			
			return CustSatisResult;
		} catch (SQLException e) {
			throw exceptionTranslator.translate("CustomerSatisfactionReportDetail2Wrapper fails", null, e);
		}
	}

	public void setValueListInfo(ValueListInfo arg0) {
	}
}