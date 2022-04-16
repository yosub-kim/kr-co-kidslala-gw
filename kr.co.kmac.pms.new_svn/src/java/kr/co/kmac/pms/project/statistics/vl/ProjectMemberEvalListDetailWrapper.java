package kr.co.kmac.pms.project.statistics.vl;

import java.sql.ResultSet;
import java.sql.SQLException;

import kr.co.kmac.pms.project.statistics.data.ProjectMemberEvalListDetail;
import net.mlw.vlh.ValueListInfo;
import net.mlw.vlh.adapter.util.ObjectWrapper;

import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.jdbc.support.SQLStateSQLExceptionTranslator;

public class ProjectMemberEvalListDetailWrapper implements ObjectWrapper {

	private SQLExceptionTranslator exceptionTranslator = new SQLStateSQLExceptionTranslator();

	public Object getWrappedRecord(Object arg0) {
		ResultSet rs = (ResultSet) arg0;
		try {
			ProjectMemberEvalListDetail memberEvaluation = new ProjectMemberEvalListDetail();
			memberEvaluation.setProjectCode(rs.getString("projectCode"));
			memberEvaluation.setProjectName(rs.getString("projectName"));
			memberEvaluation.setRealEndDate(rs.getString("realEndDate"));
			memberEvaluation.setSsn(rs.getString("ssn"));
			memberEvaluation.setName(rs.getString("name"));
			memberEvaluation.setCompanyPosition(rs.getString("companyPosition"));
			memberEvaluation.setBusinessTypeCode(rs.getString("businessTypeCode"));
			memberEvaluation.setBusinessType(rs.getString("businessType"));
			memberEvaluation.setItem(rs.getString("item"));
			memberEvaluation.setTotal(rs.getString("total"));
			memberEvaluation.setWriterSsn(rs.getString("writerSsn"));
			memberEvaluation.setWriterName(rs.getString("writerName"));
			return memberEvaluation;
		} catch (SQLException e) {
			throw exceptionTranslator.translate("ProjectMemberEvalListDetailWrapper fails", null, e);
		}
	}

	public void setValueListInfo(ValueListInfo arg0) {
	}
}
