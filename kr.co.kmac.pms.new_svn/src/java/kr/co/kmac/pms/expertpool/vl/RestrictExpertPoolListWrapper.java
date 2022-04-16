package kr.co.kmac.pms.expertpool.vl;

import java.sql.ResultSet;
import java.sql.SQLException;

import kr.co.kmac.pms.expertpool.data.ExpertPool;

import net.mlw.vlh.ValueListInfo;
import net.mlw.vlh.adapter.util.ObjectWrapper;

import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.jdbc.support.SQLStateSQLExceptionTranslator;

public class RestrictExpertPoolListWrapper implements ObjectWrapper {
	private SQLExceptionTranslator exceptionTranslator = new SQLStateSQLExceptionTranslator();
	
	public Object getWrappedRecord(Object objectToBeWrapped) {
		ResultSet rs = (ResultSet) objectToBeWrapped;
		ExpertPool expertPool = new ExpertPool();
		try {
			expertPool.setRank(rs.getString("rank"));
			expertPool.setName(rs.getString("name"));
			expertPool.setUserId(rs.getString("birth"));
			expertPool.setSsn(rs.getString("ssn"));
			expertPool.setJobClass(rs.getString("jobClassName"));
			expertPool.setCompany(rs.getString("company"));
			expertPool.setDeptName(rs.getString("deptName"));
			expertPool.setCompanyPositionName(rs.getString("companyPositionName"));
			expertPool.setMobileNo(rs.getString("mobileNo"));
			expertPool.setRestrictContents(rs.getString("restrictContents"));
		} catch (SQLException e) {
			throw exceptionTranslator.translate("RestrictExpertPoolListWrapper fails", null, e);
		}
		return expertPool;
	}

	public void setValueListInfo(ValueListInfo arg0) {
		// do nothing
	}	
}