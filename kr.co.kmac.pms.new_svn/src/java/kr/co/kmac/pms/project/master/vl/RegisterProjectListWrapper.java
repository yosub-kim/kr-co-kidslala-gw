package kr.co.kmac.pms.project.master.vl;

import java.sql.ResultSet;
import java.sql.SQLException;

import kr.co.kmac.pms.project.master.data.RegisterProject;
import net.mlw.vlh.ValueListInfo;
import net.mlw.vlh.adapter.util.ObjectWrapper;

import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.jdbc.support.SQLStateSQLExceptionTranslator;

public class RegisterProjectListWrapper implements ObjectWrapper {
	private SQLExceptionTranslator exceptionTranslator = new SQLStateSQLExceptionTranslator();

	@Override
	public Object getWrappedRecord(Object objectToBeWrapped) {
		ResultSet rs = (ResultSet) objectToBeWrapped;
		try {
			RegisterProject registerProject = new RegisterProject();
			registerProject.setProjectCode(rs.getString("projectCode"));
			registerProject.setProjectName(rs.getString("projectName"));
			registerProject.setIndustryTypeCode(rs.getString("industryTypeCode"));
			registerProject.setIndustryTypeName(rs.getString("industryTypeName"));
			registerProject.setBusinessTypeCode(rs.getString("businessTypeCode"));
			registerProject.setBusinessTypeName(rs.getString("businessTypeName"));
			registerProject.setProjectState(rs.getString("projectState"));
			registerProject.setCreateDate(rs.getDate("createDate"));
			registerProject.setCreaterSsn(rs.getString("createrSsn"));
			registerProject.setCreaterName(rs.getString("createrName"));
			registerProject.setIsReject(rs.getString("isReject"));
			
			return registerProject;
		} catch (SQLException e) {
			throw exceptionTranslator.translate("ErpProjectListWrapper fails", null, e);
		}
	}

	@Override
	public void setValueListInfo(ValueListInfo info) {
		// TODO Auto-generated method stub
	}
}