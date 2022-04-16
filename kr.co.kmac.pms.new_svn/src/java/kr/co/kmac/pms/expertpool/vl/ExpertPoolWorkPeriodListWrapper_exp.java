package kr.co.kmac.pms.expertpool.vl;

import java.sql.ResultSet;
import java.sql.SQLException;

import kr.co.kmac.pms.expertpool.data.ExpertPool;
import net.mlw.vlh.ValueListInfo;
import net.mlw.vlh.adapter.util.ObjectWrapper;

import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.jdbc.support.SQLStateSQLExceptionTranslator;


public class ExpertPoolWorkPeriodListWrapper_exp implements ObjectWrapper {
	private SQLExceptionTranslator exceptionTranslator = new SQLStateSQLExceptionTranslator();

	public Object getWrappedRecord(Object objectToBeWrapped) {
		ResultSet rs = (ResultSet) objectToBeWrapped;
		ExpertPool expertPool = new ExpertPool();
		try {
			expertPool.setSsn(rs.getString("ssn"));
			expertPool.setName(rs.getString("name"));
			expertPool.setDeptName(rs.getString("deptName"));
			expertPool.setDept(rs.getString("ssnCnt"));
			expertPool.setSsnn(rs.getString("ssnnCnt"));
			expertPool.setProjectName(rs.getString("projectName"));
			expertPool.setRealStartDate(rs.getString("realStartDate"));
			expertPool.setRealEndDate(rs.getString("realEndDate"));
			expertPool.setProjectTypeCode(rs.getString("projectTypeCode"));
			expertPool.setProjectManager(rs.getString("projectManager"));
			expertPool.setDes(rs.getString("des"));
			expertPool.setExpertCnt(rs.getString("expertCnt"));
		} catch (SQLException e) {
			throw exceptionTranslator.translate("ExpertPoolWorkPeriodListWrapper_exp fails", null, e);
		}
		return expertPool;
	}

	public void setValueListInfo(ValueListInfo arg0) {
	}
}