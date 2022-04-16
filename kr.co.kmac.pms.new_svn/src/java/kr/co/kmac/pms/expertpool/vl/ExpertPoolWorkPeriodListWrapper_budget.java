package kr.co.kmac.pms.expertpool.vl;

import java.sql.ResultSet;
import java.sql.SQLException;

import kr.co.kmac.pms.expertpool.data.ExpertPool;
import net.mlw.vlh.ValueListInfo;
import net.mlw.vlh.adapter.util.ObjectWrapper;

import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.jdbc.support.SQLStateSQLExceptionTranslator;


public class ExpertPoolWorkPeriodListWrapper_budget implements ObjectWrapper {
	private SQLExceptionTranslator exceptionTranslator = new SQLStateSQLExceptionTranslator();

	public Object getWrappedRecord(Object objectToBeWrapped) {
		ResultSet rs = (ResultSet) objectToBeWrapped;
		ExpertPool expertPool = new ExpertPool();
		try {
			expertPool.setProjectCode(rs.getString("projectCode"));
			expertPool.setProjectName(rs.getString("projectName"));
			expertPool.setRegisterDate(rs.getString("registerDate"));
			expertPool.setSum(rs.getString("sum"));
			expertPool.setDetailSum(rs.getString("detailSum"));
			expertPool.setDetailBen(rs.getString("detailBen"));
			expertPool.setRunningDeptCode(rs.getString("runningDeptCode"));
			expertPool.setRealStartDate(rs.getString("realStartDate"));
			expertPool.setRealEndDate(rs.getString("realEndDate"));
			expertPool.setAliasName(rs.getString("aliasName"));
			expertPool.setCheckYN(rs.getString("checkYN"));
		} catch (SQLException e) {
			throw exceptionTranslator.translate("ExpertPoolWorkPeriodListWrapper_budget fails", null, e);
		}
		return expertPool;
	}

	public void setValueListInfo(ValueListInfo arg0) {
	}
}