package kr.co.kmac.pms.project.statistics.vl;

import java.sql.ResultSet;
import java.sql.SQLException;

import kr.co.kmac.pms.project.statistics.data.ProjectMemberEvalListDetailView;
import net.mlw.vlh.ValueListInfo;
import net.mlw.vlh.adapter.util.ObjectWrapper;

import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.jdbc.support.SQLStateSQLExceptionTranslator;

public class ProjectMemberEvalListDetailViewWrapper implements ObjectWrapper {

	private SQLExceptionTranslator exceptionTranslator = new SQLStateSQLExceptionTranslator();

	public Object getWrappedRecord(Object arg0) {
		ResultSet rs = (ResultSet) arg0;
		try {
			ProjectMemberEvalListDetailView evalDetailView = new ProjectMemberEvalListDetailView();
			evalDetailView.setSsn(rs.getString("ssn"));
			evalDetailView.setProjectCode(rs.getString("projectCode"));
			evalDetailView.setProjectName(rs.getString("projectName"));
			evalDetailView.setAnswer1(rs.getString("answer1"));
			evalDetailView.setAnswer2(rs.getString("answer2"));
			evalDetailView.setAnswer3(rs.getString("answer3"));
			evalDetailView.setAnswer4(rs.getString("answer4"));
			evalDetailView.setAnswer5(rs.getString("answer5"));
			evalDetailView.setAnswer6(rs.getString("answer6"));
			evalDetailView.setAnswer7(rs.getString("answer7"));
			evalDetailView.setAnswer8(rs.getString("answer8"));
			evalDetailView.setAnswer9(rs.getString("answer9"));
			evalDetailView.setAnswer10(rs.getString("answer10"));
			evalDetailView.setAnswer11(rs.getString("answer11"));
			evalDetailView.setAnswer12(rs.getString("answer12"));
			evalDetailView.setAnswer13(rs.getString("answer13"));
			evalDetailView.setWriteDate(rs.getString("writeDate"));
			/*evalDetailView.setEvalChk(rs.getString("evalChk"));*/
			return evalDetailView;
		} catch (SQLException e) {
			throw exceptionTranslator.translate("ProjectMemberEvalListDetailViewWrapper fails", null, e);
		}
	}

	public void setValueListInfo(ValueListInfo arg0) {
	}
}
