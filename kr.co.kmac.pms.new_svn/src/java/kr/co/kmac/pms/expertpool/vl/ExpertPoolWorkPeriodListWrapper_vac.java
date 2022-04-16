package kr.co.kmac.pms.expertpool.vl;

import java.sql.ResultSet;
import java.sql.SQLException;

import kr.co.kmac.pms.expertpool.data.ExpertPool;
import net.mlw.vlh.ValueListInfo;
import net.mlw.vlh.adapter.util.ObjectWrapper;

import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.jdbc.support.SQLStateSQLExceptionTranslator;


public class ExpertPoolWorkPeriodListWrapper_vac implements ObjectWrapper {
	private SQLExceptionTranslator exceptionTranslator = new SQLStateSQLExceptionTranslator();

	public Object getWrappedRecord(Object objectToBeWrapped) {
		ResultSet rs = (ResultSet) objectToBeWrapped;
		ExpertPool expertPool = new ExpertPool();
		try {
			expertPool.setDeptName(rs.getString("deptName"));
			expertPool.setName(rs.getString("name"));
			expertPool.setSsn(rs.getString("ssn"));
			expertPool.setAcctBeginDate(rs.getString("acctBeginDate"));
			expertPool.setAcctExpireDate(rs.getString("acctExpireDate"));
			expertPool.setDept(rs.getString("deptCnt"));
			expertPool.setCompanyPositionName(rs.getString("companyPositionName"));
			expertPool.setDeptCnt(rs.getString("dept"));
			expertPool.setRestday_1(rs.getString("restday_1"));
			expertPool.setUseday(rs.getString("useday"));
			expertPool.setRestday(rs.getString("restday"));
		} catch (SQLException e) {
			throw exceptionTranslator.translate("ExpertPoolWorkPeriodListWrapper_vac fails", null, e);
		}
		return expertPool;
	}

	public void setValueListInfo(ValueListInfo arg0) {
	}
}