package kr.co.kmac.pms.project.statistics.vl;

import java.sql.ResultSet;
import java.sql.SQLException;

import kr.co.kmac.pms.project.statistics.data.ProjectMemberAnnualMM;
import net.mlw.vlh.ValueListInfo;
import net.mlw.vlh.adapter.util.ObjectWrapper;

import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.jdbc.support.SQLStateSQLExceptionTranslator;

public class ProjectMemberAnnualMMWrapper implements ObjectWrapper {
	private SQLExceptionTranslator exceptionTranslator = new SQLStateSQLExceptionTranslator();

	public Object getWrappedRecord(Object objectToBeWrapped) {
		ResultSet rs = (ResultSet) objectToBeWrapped;
		try {
			ProjectMemberAnnualMM list = new ProjectMemberAnnualMM();
			list.setDept(rs.getString("dept"));
			list.setDeptName(rs.getString("deptName"));
			list.setName(rs.getString("name"));
			list.setSsn(rs.getString("ssn"));
			list.setProjectTypeCode(rs.getString("projectTypeCode"));
			list.setProjectTypeName(rs.getString("projectTypeName"));
			list.setM01(rs.getString("M01"));
			list.setM02(rs.getString("M02"));
			list.setM03(rs.getString("M03"));
			list.setM04(rs.getString("M04"));
			list.setM05(rs.getString("M05"));
			list.setM06(rs.getString("M06"));
			list.setM07(rs.getString("M07"));
			list.setM08(rs.getString("M08"));
			list.setM09(rs.getString("M09"));
			list.setM10(rs.getString("M10"));
			list.setM11(rs.getString("M11"));
			list.setM12(rs.getString("M12"));
			return list;
		} catch (SQLException e) {
			throw exceptionTranslator.translate("ProjectMemberAnnualMMWrapper fails", null, e);
		}
	}

	public void setValueListInfo(ValueListInfo info) {
		// TODO Auto-generated method stub

	}
}