package kr.co.kmac.pms.expertpool.vl;

import java.sql.ResultSet;
import java.sql.SQLException;

import kr.co.kmac.pms.expertpool.data.ExpertPool;
import net.mlw.vlh.ValueListInfo;
import net.mlw.vlh.adapter.util.ObjectWrapper;

import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.jdbc.support.SQLStateSQLExceptionTranslator;


public class ExpertPoolWorkPeriodListWrapper_workDay2 implements ObjectWrapper {
	private SQLExceptionTranslator exceptionTranslator = new SQLStateSQLExceptionTranslator();

	public Object getWrappedRecord(Object objectToBeWrapped) {
		ResultSet rs = (ResultSet) objectToBeWrapped;
		ExpertPool expertPool = new ExpertPool();
		try {
			expertPool.setUserName(rs.getString("userName"));
			expertPool.setSsn(rs.getString("ssn"));
			expertPool.setPosName(rs.getString("posName"));
			expertPool.setLabelName(rs.getString("labelName"));
			expertPool.setWorkOn(rs.getString("workOn"));
			expertPool.setWorkOff(rs.getString("workOff"));
			expertPool.setGroupcount(rs.getString("groupcount"));
			expertPool.setType(rs.getString("type"));
			expertPool.setResultDate(rs.getString("resultDate"));
		} catch (SQLException e) {
			throw exceptionTranslator.translate("ExpertPoolWorkPeriodListWrapper_workDay2 fails", null, e);
		}
		return expertPool;
	}

	public void setValueListInfo(ValueListInfo arg0) {
	}
}