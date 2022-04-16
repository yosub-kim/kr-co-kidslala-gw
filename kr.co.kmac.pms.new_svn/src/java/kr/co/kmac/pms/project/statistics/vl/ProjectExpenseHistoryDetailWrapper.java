package kr.co.kmac.pms.project.statistics.vl;

import java.sql.ResultSet;
import java.sql.SQLException;

import kr.co.kmac.pms.project.statistics.data.ProjectExpenseHistoryDetail;
import net.mlw.vlh.ValueListInfo;
import net.mlw.vlh.adapter.util.ObjectWrapper;

import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.jdbc.support.SQLStateSQLExceptionTranslator;

public class ProjectExpenseHistoryDetailWrapper implements ObjectWrapper {

	private SQLExceptionTranslator exceptionTranslator = new SQLStateSQLExceptionTranslator();

	public Object getWrappedRecord(Object arg0) {
		ResultSet rs = (ResultSet) arg0;
		try {
			ProjectExpenseHistoryDetail projectExpenseResult = new ProjectExpenseHistoryDetail();
			projectExpenseResult.setDRAW_NO(rs.getString("DRAW_NO"));
			projectExpenseResult.setACCT_DATE(rs.getString("ACCT_DATE"));
			projectExpenseResult.setACCT_NBR(rs.getString("ACCT_NBR"));
			projectExpenseResult.setCUST_NAME(rs.getString("CUST_NAME"));
			projectExpenseResult.setREMARK(rs.getString("REMARK"));
			projectExpenseResult.setCREDIT_AMT(rs.getString("CREDIT_AMT"));
			projectExpenseResult.setDEBIT_AMT(rs.getString("DEBIT_AMT"));
			
			return projectExpenseResult;
		} catch (SQLException e) {
			throw exceptionTranslator.translate("ProjectExpenseHistoryDetailWrapper fails", null, e);
		}
	}

	public void setValueListInfo(ValueListInfo arg0) {
	}
}