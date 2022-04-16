package kr.co.kmac.pms.expertpool.vl;

import java.sql.ResultSet;
import java.sql.SQLException;

import kr.co.kmac.pms.expertpool.data.ExpertPool;
import net.mlw.vlh.ValueListInfo;
import net.mlw.vlh.adapter.util.ObjectWrapper;

import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.jdbc.support.SQLStateSQLExceptionTranslator;

public class ExpertPoolListForManagerWrapper implements ObjectWrapper {
	private SQLExceptionTranslator exceptionTranslator = new SQLStateSQLExceptionTranslator();
	
	public Object getWrappedRecord(Object objectToBeWrapped) {
		ResultSet rs = (ResultSet) objectToBeWrapped;
		ExpertPool expertPool = new ExpertPool();
		try {
			expertPool.setRank(rs.getString("rank"));
			expertPool.setName(rs.getString("name"));
			expertPool.setUserId(rs.getString("userid"));
			expertPool.setUid(rs.getString("birth"));
			expertPool.setSsn(rs.getString("ssn"));
			expertPool.setJobClass(rs.getString("jobClassName"));
			expertPool.setCompany(rs.getString("company"));
			expertPool.setDeptName(rs.getString("deptName"));
			expertPool.setCompanyPositionName(rs.getString("companyPositionName"));
			expertPool.setMobileNo(rs.getString("mobileNo"));
			expertPool.setCreatedDate(rs.getDate("createdDate"));
			expertPool.setModifiedDate(rs.getDate("modifiedDate"));
			expertPool.setCreaterId(rs.getString("createrId"));
			expertPool.setAddress1(rs.getString("address1"));
			expertPool.setEmail(rs.getString("email"));
		} catch (SQLException e) {
			throw exceptionTranslator.translate("ExpertPoolListForManagerWrapper fails", null, e);
		}
		return expertPool;
	}

	public void setValueListInfo(ValueListInfo arg0) {
		// do nothing
	}	
}