package kr.co.kmac.pms.expertpool.vl;

import java.sql.ResultSet;
import java.sql.SQLException;

import kr.co.kmac.pms.expertpool.data.ExpertPool;
import net.mlw.vlh.ValueListInfo;
import net.mlw.vlh.adapter.util.ObjectWrapper;

import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.jdbc.support.SQLStateSQLExceptionTranslator;


public class ExpertPoolWorkPeriodListWrapper_schedule implements ObjectWrapper {
	private SQLExceptionTranslator exceptionTranslator = new SQLStateSQLExceptionTranslator();

	public Object getWrappedRecord(Object objectToBeWrapped) {
		ResultSet rs = (ResultSet) objectToBeWrapped;
		ExpertPool expertPool = new ExpertPool();
		try {
			expertPool.setSsn(rs.getString("ssn"));
			expertPool.setGroupName(rs.getString("groupName"));
			expertPool.setName(rs.getString("name"));
			expertPool.setPosName(rs.getString("posName"));
			expertPool.setYear(rs.getString("year"));
			expertPool.setMonth(rs.getString("month"));
			expertPool.setDay(rs.getString("day"));
			expertPool.setStartHour(rs.getString("startHour"));
			expertPool.setStartMin(rs.getString("startMin"));
			expertPool.setEndHour(rs.getString("endHour"));
			expertPool.setEndMin(rs.getString("endMin"));
			expertPool.setContent(rs.getString("content"));
			expertPool.setGroupcount(rs.getString("groupcount"));
			expertPool.setUsercount(rs.getString("usercount"));
			expertPool.setPlace(rs.getString("place"));
			expertPool.setFilterdate(rs.getString("filterdate"));
			expertPool.setDateCnt(rs.getString("datecnt"));
			expertPool.setWorkType(rs.getString("worktype"));
			expertPool.setRelationUser(rs.getString("relationuser"));
			expertPool.setEmplNo(rs.getString("emplNo"));
			expertPool.setLabelName(rs.getString("labelName"));
			expertPool.setCustomerName(rs.getString("customerName"));
		} catch (SQLException e) {
			throw exceptionTranslator.translate("ExpertPoolWorkPeriodListWrapper_schedule fails", null, e);
		}
		return expertPool;
	}

	public void setValueListInfo(ValueListInfo arg0) {
	}
}