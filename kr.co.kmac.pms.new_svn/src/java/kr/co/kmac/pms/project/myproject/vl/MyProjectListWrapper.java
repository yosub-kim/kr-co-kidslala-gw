package kr.co.kmac.pms.project.myproject.vl;

import java.sql.ResultSet;
import java.sql.SQLException;

import kr.co.kmac.pms.common.util.DateUtil;
import kr.co.kmac.pms.project.myproject.data.MyProject;
import net.mlw.vlh.ValueListInfo;
import net.mlw.vlh.adapter.util.ObjectWrapper;

import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.jdbc.support.SQLStateSQLExceptionTranslator;

public class MyProjectListWrapper implements ObjectWrapper {
	private SQLExceptionTranslator exceptionTranslator = new SQLStateSQLExceptionTranslator();

	@Override
	public Object getWrappedRecord(Object objectToBeWrapped) {
		ResultSet rs = (ResultSet) objectToBeWrapped;
		try {
			MyProject myProject = new MyProject();
			myProject.setDelayState(rs.getString("delayState"));
			myProject.setDelayForecast(rs.getString("delayForecast"));
			myProject.setProjectState(rs.getString("projectState"));
			myProject.setProjectCode(rs.getString("projectCode"));
			myProject.setBusinessTypeCodeName(rs.getString("businessTypeCodeName"));
			myProject.setProjectName(rs.getString("projectName"));
			myProject.setProjectTypeCode(rs.getString("projectTypeCode"));
			myProject.setBusinessTypeCode(rs.getString("businessTypeCode"));
			myProject.setProcessTypeCode(rs.getString("processTypeCode"));
			myProject.setRealEndDate(DateUtil.getDateTime("yyyyMMdd", rs.getString("realEndDate")));
			myProject.setRole(rs.getString("role"));
			myProject.setRoleName(rs.getString("roleName"));
			myProject.setCostOver(rs.getString("costOver"));

			myProject.setBoardArticleCount(rs.getString("boardArticleCount"));
			myProject.setBoardArticleCountQM(rs.getString("boardArticleCountQM"));
			myProject.setRunningDivCode(rs.getString("runningDivCode"));
			myProject.setIsVoc(rs.getString("isVoc"));
			myProject.setAdminOpen(rs.getString("adminOpen"));
			myProject.setAttach(rs.getString("attach"));
			myProject.setParentProjectCode(rs.getString("parentProjectCode"));

			return myProject;
		} catch (SQLException e) {
			throw exceptionTranslator.translate("MyProjectListWrapper fails", null, e);
		}
	}

	@Override
	public void setValueListInfo(ValueListInfo info) {
		// TODO Auto-generated method stub

	}

}
