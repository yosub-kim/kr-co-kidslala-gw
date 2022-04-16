package kr.co.kmac.pms.expertpool.vl;

import java.sql.ResultSet;
import java.sql.SQLException;

import kr.co.kmac.pms.expertpool.data.ExpertPool;
import net.mlw.vlh.ValueListInfo;
import net.mlw.vlh.adapter.util.ObjectWrapper;

import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.jdbc.support.SQLStateSQLExceptionTranslator;


public class ExpertPoolWorkPeriodListWrapper_awardingDetail implements ObjectWrapper {
	private SQLExceptionTranslator exceptionTranslator = new SQLStateSQLExceptionTranslator();

	public Object getWrappedRecord(Object objectToBeWrapped) {
		ResultSet rs = (ResultSet) objectToBeWrapped;
		ExpertPool expertPool = new ExpertPool();
		try {
			expertPool.setYear(rs.getString("year"));
			expertPool.setMonth(rs.getString("month"));
			expertPool.setType(rs.getString("type"));
			expertPool.setDeptName(rs.getString("deptName"));
			expertPool.setPmName(rs.getString("pmName"));
			expertPool.setPjtName(rs.getString("pjtName"));
			expertPool.setAmount(rs.getString("amount"));
			expertPool.setContents(rs.getString("contents"));
			expertPool.setRef(rs.getString("ref"));
			expertPool.setMonthCnt(rs.getString("monthCnt"));
			expertPool.setTypeCnt(rs.getString("typeCnt"));
		} catch (SQLException e) {
			throw exceptionTranslator.translate("ExpertPoolWorkPeriodListWrapper_awardingDetail fails", null, e);
		}
		return expertPool;
	}

	public void setValueListInfo(ValueListInfo arg0) {
	}
}