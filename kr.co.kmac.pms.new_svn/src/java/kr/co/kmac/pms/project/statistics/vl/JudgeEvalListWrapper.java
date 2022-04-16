package kr.co.kmac.pms.project.statistics.vl;

import java.sql.ResultSet;
import java.sql.SQLException;

import kr.co.kmac.pms.project.statistics.data.JudgeEvalList;
import net.mlw.vlh.ValueListInfo;
import net.mlw.vlh.adapter.util.ObjectWrapper;

import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.jdbc.support.SQLStateSQLExceptionTranslator;

public class JudgeEvalListWrapper implements ObjectWrapper {
	private SQLExceptionTranslator exceptionTranslator = new SQLStateSQLExceptionTranslator();

	public Object getWrappedRecord(Object objectToBeWrapped) {
		ResultSet rs = (ResultSet) objectToBeWrapped;
		try {
			JudgeEvalList judgeEvalList = new JudgeEvalList();
			judgeEvalList.setProjectCode(rs.getString("ProjectCode"));
			judgeEvalList.setPromotionType(rs.getString("PromotionType"));
			judgeEvalList.setPromotionName(rs.getString("PromotionName"));
			judgeEvalList.setYear(rs.getString("Year"));
			judgeEvalList.setJudgeSsn(rs.getString("JudgeSsn"));
			judgeEvalList.setJudgeName(rs.getString("JudgeName"));
			judgeEvalList.setJudgeCompany(rs.getString("JudgeCompany"));
			judgeEvalList.setCompanyId(rs.getString("CompanyId"));
			judgeEvalList.setCompanyName(rs.getString("CompanyName"));
			judgeEvalList.setJudgeRate(rs.getString("JudgeRate"));
			return judgeEvalList;
		} catch (SQLException e) {
			throw exceptionTranslator.translate("JudgeEvalListWrapper fails", null, e);
		}
	}

	public void setValueListInfo(ValueListInfo info) {
		// TODO Auto-generated method stub

	}
}