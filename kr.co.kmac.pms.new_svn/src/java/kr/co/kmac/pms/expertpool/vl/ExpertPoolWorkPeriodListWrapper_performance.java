package kr.co.kmac.pms.expertpool.vl;

import java.sql.ResultSet;
import java.sql.SQLException;

import kr.co.kmac.pms.expertpool.data.ExpertPool;
import net.mlw.vlh.ValueListInfo;
import net.mlw.vlh.adapter.util.ObjectWrapper;

import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.jdbc.support.SQLStateSQLExceptionTranslator;


public class ExpertPoolWorkPeriodListWrapper_performance implements ObjectWrapper {
	private SQLExceptionTranslator exceptionTranslator = new SQLStateSQLExceptionTranslator();

	public Object getWrappedRecord(Object objectToBeWrapped) {
		ResultSet rs = (ResultSet) objectToBeWrapped;
		ExpertPool expertPool = new ExpertPool();
		try {
			expertPool.setYear(rs.getString("year"));
			expertPool.setSsn(rs.getString("ssn"));
			expertPool.setName(rs.getString("name"));
			expertPool.setRole(rs.getString("role"));
			expertPool.setEval(rs.getString("eval"));
			expertPool.setGrade(rs.getString("grade"));
			expertPool.setContents(rs.getString("contents"));
			expertPool.setEvalContent(rs.getString("evalContent"));
			expertPool.setSsnCnt(rs.getString("ssnCnt"));
			expertPool.setCompanyPositionName(rs.getString("companyPositionName"));
		} catch (SQLException e) {
			throw exceptionTranslator.translate("ExpertPoolWorkPeriodListWrapper_performance fails", null, e);
		}
		return expertPool;
	}

	public void setValueListInfo(ValueListInfo arg0) {
	}
}