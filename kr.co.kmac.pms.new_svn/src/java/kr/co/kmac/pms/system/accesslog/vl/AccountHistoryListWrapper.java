package kr.co.kmac.pms.system.accesslog.vl;

import java.sql.ResultSet;
import java.sql.SQLException;

import kr.co.kmac.pms.system.accesslog.data.AccountHistoryData;
import net.mlw.vlh.ValueListInfo;
import net.mlw.vlh.adapter.util.ObjectWrapper;

import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.jdbc.support.SQLStateSQLExceptionTranslator;

public class AccountHistoryListWrapper implements ObjectWrapper {

	private SQLExceptionTranslator exceptionTranslator = new SQLStateSQLExceptionTranslator();

	public Object getWrappedRecord(Object objectToBeWrapped) {
		// TODO Auto-generated method stub
		ResultSet rs = (ResultSet) objectToBeWrapped;
		try {
			AccountHistoryData accountHistoryData = new AccountHistoryData();
			accountHistoryData.setSeq(rs.getString("seq"));
			accountHistoryData.setType(rs.getString("type"));
			accountHistoryData.setUserId(rs.getString("userId"));
			accountHistoryData.setSsn(rs.getString("ssn"));
			accountHistoryData.setName(rs.getString("name"));
			accountHistoryData.setJobClassName(rs.getString("jobClassName"));
			accountHistoryData.setDeptName(rs.getString("deptName"));
			accountHistoryData.setCompanyPositionName(rs.getString("companyPositionName"));
			accountHistoryData.setMenuRoleName(rs.getString("menuRoleName"));
			accountHistoryData.setDate(rs.getString("date"));
			accountHistoryData.setEtc(rs.getString("etc"));

			return accountHistoryData;
			
		} catch (SQLException e) {
			throw exceptionTranslator.translate("AcctHistoryListWrapper fails", null, e);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see net.mlw.vlh.adapter.util.ObjectWrapper#setValueListInfo(net.mlw.vlh.ValueListInfo)
	 */
	public void setValueListInfo(ValueListInfo info) {
		// TODO Auto-generated method stub

	}

}
