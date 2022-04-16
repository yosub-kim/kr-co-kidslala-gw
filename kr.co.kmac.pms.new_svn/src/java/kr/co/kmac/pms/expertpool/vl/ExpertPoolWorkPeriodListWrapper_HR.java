package kr.co.kmac.pms.expertpool.vl;

import java.sql.ResultSet;
import java.sql.SQLException;

import kr.co.kmac.pms.expertpool.data.ExpertPool;
import net.mlw.vlh.ValueListInfo;
import net.mlw.vlh.adapter.util.ObjectWrapper;

import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.jdbc.support.SQLStateSQLExceptionTranslator;


public class ExpertPoolWorkPeriodListWrapper_HR implements ObjectWrapper {
	private SQLExceptionTranslator exceptionTranslator = new SQLStateSQLExceptionTranslator();

	public Object getWrappedRecord(Object objectToBeWrapped) {
		ResultSet rs = (ResultSet) objectToBeWrapped;
		ExpertPool expertPool = new ExpertPool();
		try {
			expertPool.setUserName(rs.getString("userName"));
			expertPool.setLabelName(rs.getString("labelName"));
			expertPool.setPosName(rs.getString("posName"));
			expertPool.setGroupcount(rs.getString("groupcount"));	
		} catch (SQLException e) {
			throw exceptionTranslator.translate("ExpertPoolWorkPeriodListWrapper_HR fails", null, e);
		}
		return expertPool;
	}

	public void setValueListInfo(ValueListInfo arg0) {
	}
}