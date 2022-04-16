package kr.co.kmac.pms.expertpool.vl;

import java.sql.ResultSet;
import java.sql.SQLException;

import kr.co.kmac.pms.expertpool.data.ExpertPool;
import net.mlw.vlh.ValueListInfo;
import net.mlw.vlh.adapter.util.ObjectWrapper;

import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.jdbc.support.SQLStateSQLExceptionTranslator;


public class ExpertPoolWorkPeriodListWrapper_customer2 implements ObjectWrapper {
	private SQLExceptionTranslator exceptionTranslator = new SQLStateSQLExceptionTranslator();

	public Object getWrappedRecord(Object objectToBeWrapped) {
		ResultSet rs = (ResultSet) objectToBeWrapped;
		ExpertPool expertPool = new ExpertPool();
		try {
			expertPool.setUserName(rs.getString("userName"));
			expertPool.setYear(rs.getString("year"));
			expertPool.setMonth(rs.getString("month"));
			expertPool.setDay(rs.getString("day"));
			expertPool.setStartHour(rs.getString("startHour"));
			expertPool.setStartMin(rs.getString("startMin"));
			expertPool.setEndHour(rs.getString("endHour"));
			expertPool.setEndMin(rs.getString("endMin"));
			expertPool.setContent(rs.getString("content"));
			expertPool.setCustomerName(rs.getString("customerName"));
			expertPool.setRelationUser(rs.getString("relationUser"));
			expertPool.setPlace(rs.getString("place"));
			expertPool.setGoogleSyncId(rs.getString("googleSyncId"));
			expertPool.setWorkType(rs.getString("workType"));
			expertPool.setType(rs.getString("Type"));
			
		} catch (SQLException e) {
			throw exceptionTranslator.translate("ExpertPoolWorkPeriodListWrapper_customer2 fails", null, e);
		}
		return expertPool;
	}

	public void setValueListInfo(ValueListInfo arg0) {
	}
}