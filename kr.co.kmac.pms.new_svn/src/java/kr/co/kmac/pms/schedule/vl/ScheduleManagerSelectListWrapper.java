/**
 * 
 */
/**
 * @author yhyim
 *
 */
package kr.co.kmac.pms.schedule.vl;


import java.sql.ResultSet;
import java.sql.SQLException;

import kr.co.kmac.pms.schedule.data.ScheduleManagerData;
import net.mlw.vlh.ValueListInfo;
import net.mlw.vlh.adapter.util.ObjectWrapper;

import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.jdbc.support.SQLStateSQLExceptionTranslator;

public class ScheduleManagerSelectListWrapper implements ObjectWrapper {
	private SQLExceptionTranslator exceptionTranslator = new SQLStateSQLExceptionTranslator();

	public Object getWrappedRecord(Object objectToBeWrapped) {
		ResultSet rs = (ResultSet) objectToBeWrapped;
		try {
			ScheduleManagerData data = new ScheduleManagerData();
			data.setSsn(rs.getString("ssn"));
			data.setName(rs.getString("name"));
			data.setDeptName(rs.getString("deptName"));
			data.setPosition(rs.getString("position"));
			data.setCnt1(rs.getString("draft4Cnt"));
			data.setCnt2(rs.getString("dayOffCnt"));
			data.setCnt3(rs.getString("eduCnt"));
			return data;
		} catch (SQLException e) {
			throw exceptionTranslator.translate("ScheduleManagerData fails", null, e);
		}
	}

	public void setValueListInfo(ValueListInfo info) {
		// TODO Auto-generated method stub

	}
}