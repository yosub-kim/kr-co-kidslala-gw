package kr.co.kmac.pms.project.statistics.vl;

import java.sql.ResultSet;
import java.sql.SQLException;

import kr.co.kmac.pms.project.statistics.data.ProjectMemberEvalList;
import net.mlw.vlh.ValueListInfo;
import net.mlw.vlh.adapter.util.ObjectWrapper;

import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.jdbc.support.SQLStateSQLExceptionTranslator;

public class ProjectMemberEvalListWrapper implements ObjectWrapper {

	private SQLExceptionTranslator exceptionTranslator = new SQLStateSQLExceptionTranslator();

	public Object getWrappedRecord(Object arg0) {
		ResultSet rs = (ResultSet) arg0;
		try {
			ProjectMemberEvalList memberEvaluation = new ProjectMemberEvalList();
			try{memberEvaluation.setProjectCode(rs.getString("projectCode"));}catch(Exception e) {}
			try{memberEvaluation.setProjectName(rs.getString("projectName"));}catch(Exception e) {}
			memberEvaluation.setSsnCnt(rs.getString("ssnCnt"));
			memberEvaluation.setPositionCnt(rs.getString("positionCnt"));
			memberEvaluation.setBusinessTypeCnt(rs.getString("businessTypeCnt"));
			memberEvaluation.setSsn(rs.getString("ssn"));
			memberEvaluation.setName(rs.getString("name"));
			memberEvaluation.setCompanyPosition(rs.getString("companyPosition"));
			memberEvaluation.setBusinessTypeCode(rs.getString("businessTypeCode"));
			memberEvaluation.setBusinessType(rs.getString("businessType"));
			memberEvaluation.setItem(rs.getString("item"));
			memberEvaluation.setTotal(rs.getString("total"));
			try{memberEvaluation.setWriteDate(rs.getString("writeDate"));}catch(Exception e) {}
			return memberEvaluation;
		} catch (SQLException e) {
			throw exceptionTranslator.translate("ProjectMemberEvalListWrapper fails", null, e);
		}
	}

	public void setValueListInfo(ValueListInfo arg0) {
	}
}