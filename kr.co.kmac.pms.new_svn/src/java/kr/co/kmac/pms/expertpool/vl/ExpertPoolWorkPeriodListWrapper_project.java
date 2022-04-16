package kr.co.kmac.pms.expertpool.vl;

import java.sql.ResultSet;
import java.sql.SQLException;

import kr.co.kmac.pms.expertpool.data.ExpertPool;
import net.mlw.vlh.ValueListInfo;
import net.mlw.vlh.adapter.util.ObjectWrapper;

import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.jdbc.support.SQLStateSQLExceptionTranslator;


public class ExpertPoolWorkPeriodListWrapper_project implements ObjectWrapper {
	private SQLExceptionTranslator exceptionTranslator = new SQLStateSQLExceptionTranslator();

	public Object getWrappedRecord(Object objectToBeWrapped) {
		ResultSet rs = (ResultSet) objectToBeWrapped;
		ExpertPool expertPool = new ExpertPool();
		try {
			expertPool.setSsn(rs.getString("ssn"));
			expertPool.setName(rs.getString("name"));
			expertPool.setProjectCode(rs.getString("projectCode"));
			expertPool.setProjectName(rs.getString("projectName"));
			expertPool.setRole(rs.getString("role"));
			expertPool.setResultDate(rs.getString("resultDate"));
			expertPool.setDept(rs.getString("dept"));
			expertPool.setEntno(rs.getString("entno"));
			expertPool.setSsnCnt(rs.getString("ssnCnt"));
			expertPool.setStateChk(rs.getString("stateChk"));
			expertPool.setProgressCnt(rs.getString("progressCnt"));
			expertPool.setGubunChk(rs.getString("gubunChk"));
			expertPool.setMonthlyAmount(rs.getString("monthlyAmount"));
			expertPool.setPrj_Amount(rs.getString("prj_Amount"));
		} catch (SQLException e) {
			throw exceptionTranslator.translate("ExpertPoolWorkPeriodListWrapper_project fails", null, e);
		}
		return expertPool;
	}

	public void setValueListInfo(ValueListInfo arg0) {
	}
}