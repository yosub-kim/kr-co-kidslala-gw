package kr.co.kmac.pms.project.statistics.vl;

import java.sql.ResultSet;
import java.sql.SQLException;

import kr.co.kmac.pms.project.statistics.data.ProjectMemberMonthlyMMAdded;
import net.mlw.vlh.ValueListInfo;
import net.mlw.vlh.adapter.util.ObjectWrapper;

import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.jdbc.support.SQLStateSQLExceptionTranslator;

public class ProjectMemberMonthlyMMWrapper implements ObjectWrapper {
	private SQLExceptionTranslator exceptionTranslator = new SQLStateSQLExceptionTranslator();

	public Object getWrappedRecord(Object objectToBeWrapped) {
		ResultSet rs = (ResultSet) objectToBeWrapped;
		try {
			ProjectMemberMonthlyMMAdded list = new ProjectMemberMonthlyMMAdded();
			list.setProjectName(rs.getString("projectName"));
			list.setBusinessTypeCode(rs.getString("businessTypeCode"));
			list.setProjectTypeCode(rs.getString("projectTypeCode"));
			list.setProjectCode(rs.getString("projectCode"));
			list.setSsn(rs.getString("ssn"));
			list.setName(rs.getString("name"));
			list.setYear(rs.getString("year"));
			list.setMonth(rs.getString("month"));
			list.setMmValue(rs.getString("mmValue"));
			list.setTotalMMValue(rs.getString("totalMMValue"));
			list.setMmState(rs.getString("mmState"));
			list.setPmSsn(rs.getString("pmSsn"));

			return list;
		} catch (SQLException e) {
			throw exceptionTranslator.translate("ProjectMemberMonthlyMMWrapper fails", null, e);
		}
	}

	public void setValueListInfo(ValueListInfo info) {
		// TODO Auto-generated method stub

	}
}