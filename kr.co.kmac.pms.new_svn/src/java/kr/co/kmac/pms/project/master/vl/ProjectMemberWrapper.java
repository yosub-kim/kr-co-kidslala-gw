package kr.co.kmac.pms.project.master.vl;

import java.sql.ResultSet;
import java.sql.SQLException;

import kr.co.kmac.pms.project.master.data.ProjectMember;
import net.mlw.vlh.ValueListInfo;
import net.mlw.vlh.adapter.util.ObjectWrapper;

import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.jdbc.support.SQLStateSQLExceptionTranslator;

public class ProjectMemberWrapper implements ObjectWrapper {

	private SQLExceptionTranslator exceptionTranslator = new SQLStateSQLExceptionTranslator();

	public Object getWrappedRecord(Object objectToBeWrapped) {
		ResultSet rs = (ResultSet) objectToBeWrapped;
		try {
			ProjectMember data = new ProjectMember();
			data.setName(rs.getString("name"));
			data.setSsn(rs.getString("ssn"));
			data.setRole(rs.getString("role"));
			data.setTrainingYn(rs.getString("trainingYN"));
			data.setCost(rs.getString("cost"));
			// try{data.setRate(rs.getString("rate"));}catch (Exception e) {}
			// try{data.setResRate(rs.getString("resRate"));}catch (Exception e) {}
			// try{data.setCompanyPosition(rs.getString("companyPosition"));}catch (Exception e) {}
			// try{data.setBasicSalary(rs.getString("basicSalary"));}catch (Exception e) {}
			return data;
		} catch (SQLException e) {
			throw exceptionTranslator.translate("ProjectMemberWrapper fails", null, e);
		}
	}

	public void setValueListInfo(ValueListInfo arg0) {

	}

}
