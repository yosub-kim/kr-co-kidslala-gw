package kr.co.kmac.pms.project.statistics.vl;

import java.sql.ResultSet;
import java.sql.SQLException;

import kr.co.kmac.pms.project.statistics.data.ProjectMemberWholeMM;
import net.mlw.vlh.ValueListInfo;
import net.mlw.vlh.adapter.util.ObjectWrapper;

import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.jdbc.support.SQLStateSQLExceptionTranslator;

public class ProjectMemberWholeMMWrapper implements ObjectWrapper {
	private SQLExceptionTranslator exceptionTranslator = new SQLStateSQLExceptionTranslator();

	public Object getWrappedRecord(Object objectToBeWrapped) {
		ResultSet rs = (ResultSet) objectToBeWrapped;
		try {
			ProjectMemberWholeMM list = new ProjectMemberWholeMM();
			list.setName(rs.getString("name"));
			list.setProjectCode(rs.getString("projectCode"));
			list.setProjectName(rs.getString("projectName"));
			list.setBusinessTypeName(rs.getString("businessTypeName"));
			list.setSanctionDate(rs.getString("sanctionDate"));
			list.setRealStartDate(rs.getString("realStartDate"));
			list.setRealEndDate(rs.getString("realEndDate"));
			list.setRunningDeptName(rs.getString("runningDeptName"));
			list.setTotalMM(rs.getString("totalMM"));
			return list;
		} catch (SQLException e) {
			throw exceptionTranslator.translate("ProjectMemberWholeMMWrapper fails", null, e);
		}
	}

	public void setValueListInfo(ValueListInfo info) {
		// TODO Auto-generated method stub

	}
}