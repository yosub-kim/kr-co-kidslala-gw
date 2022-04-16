package kr.co.kmac.pms.project.statistics.vl;

import java.sql.ResultSet;
import java.sql.SQLException;

import kr.co.kmac.pms.project.statistics.data.CustomerSatisfactionReportDetail;
import net.mlw.vlh.ValueListInfo;
import net.mlw.vlh.adapter.util.ObjectWrapper;

import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.jdbc.support.SQLStateSQLExceptionTranslator;

public class CustomerSatisfactionReportDetailWrapper implements ObjectWrapper {

	private SQLExceptionTranslator exceptionTranslator = new SQLStateSQLExceptionTranslator();

	public Object getWrappedRecord(Object arg0) {
		ResultSet rs = (ResultSet) arg0;
		try {
			CustomerSatisfactionReportDetail custSatisResult = new CustomerSatisfactionReportDetail();
			custSatisResult.setBusinessTypeName(rs.getString("businessTypeName"));
			custSatisResult.setRunningDivName(rs.getString("runningDivName"));
			custSatisResult.setProcessTypeCode(rs.getString("processTypeCode"));
			custSatisResult.setProjectName(rs.getString("projectName"));
			custSatisResult.setCustomerName(rs.getString("customerName"));
			custSatisResult.setJikwee(rs.getString("jikwee"));
			custSatisResult.setCustomerWorkPName(rs.getString("customerWorkPName"));
			try {custSatisResult.setCs1(rs.getString("cs1"));} catch (Exception e) {}
			try {custSatisResult.setCs2(rs.getString("cs2"));} catch (Exception e) {}
			try {custSatisResult.setCs3(rs.getString("cs3"));} catch (Exception e) {}
			try {custSatisResult.setCs4(rs.getString("cs4"));} catch (Exception e) {}
			try {custSatisResult.setCs5(rs.getString("cs5"));} catch (Exception e) {}
			try {custSatisResult.setCs6(rs.getString("cs6"));} catch (Exception e) {}
			try {custSatisResult.setCs7(rs.getString("cs7"));} catch (Exception e) {}
			try {custSatisResult.setCs8(rs.getString("cs8"));} catch (Exception e) {}
			try {custSatisResult.setCs9(rs.getString("cs9"));} catch (Exception e) {}
			try {custSatisResult.setCs10(rs.getString("cs10"));} catch (Exception e) {}
			try {custSatisResult.setCssn(rs.getString("cssn"));} catch (Exception e) {}
			try {custSatisResult.setCname(rs.getString("cname"));} catch (Exception e) {}
			try {custSatisResult.setRc8(rs.getString("rc8"));} catch (Exception e) {}
			try {custSatisResult.setRc9(rs.getString("rc9"));} catch (Exception e) {}
			try {custSatisResult.setRc10(rs.getString("rc10"));} catch (Exception e) {}
			try {custSatisResult.setRc12(rs.getString("rc12"));} catch (Exception e) {}
			try {custSatisResult.setRc13(rs.getString("rc13"));} catch (Exception e) {}
			try {custSatisResult.setOpinion(rs.getString("opinion"));} catch (Exception e) {}
			try {custSatisResult.setComment(rs.getString("comment"));} catch (Exception e) {}
			
			return custSatisResult;
		} catch (SQLException e) {
			throw exceptionTranslator.translate("CustomerSatisfactionReportDetailWrapper fails", null, e);
		}
	}

	public void setValueListInfo(ValueListInfo arg0) {
	}
}