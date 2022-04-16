package kr.co.kmac.pms.expertpool.vl;

import java.sql.ResultSet;
import java.sql.SQLException;

import kr.co.kmac.pms.expertpool.data.ExpertPool;
import net.mlw.vlh.ValueListInfo;
import net.mlw.vlh.adapter.util.ObjectWrapper;

import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.jdbc.support.SQLStateSQLExceptionTranslator;


public class ExpertPoolWorkPeriodListWrapper_awarding implements ObjectWrapper {
	private SQLExceptionTranslator exceptionTranslator = new SQLStateSQLExceptionTranslator();

	public Object getWrappedRecord(Object objectToBeWrapped) {
		ResultSet rs = (ResultSet) objectToBeWrapped;
		ExpertPool expertPool = new ExpertPool();
		try {
			expertPool.setYear(rs.getString("year"));
			expertPool.setEvalContent(rs.getString("evalContent"));
			expertPool.setContents(rs.getString("contents"));
		} catch (SQLException e) {
			throw exceptionTranslator.translate("ExpertPoolWorkPeriodListWrapper_awarding fails", null, e);
		}
		return expertPool;
	}

	public void setValueListInfo(ValueListInfo arg0) {
	}
}