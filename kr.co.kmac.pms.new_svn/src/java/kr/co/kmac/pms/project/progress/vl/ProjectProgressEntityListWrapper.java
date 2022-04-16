package kr.co.kmac.pms.project.progress.vl;

import java.sql.ResultSet;
import java.sql.SQLException;

import kr.co.kmac.pms.common.util.DateUtil;
import kr.co.kmac.pms.project.progress.data.ProjectProgressEntity;
import net.mlw.vlh.ValueListInfo;
import net.mlw.vlh.adapter.util.ObjectWrapper;

import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.jdbc.support.SQLStateSQLExceptionTranslator;

public class ProjectProgressEntityListWrapper implements ObjectWrapper {
	private SQLExceptionTranslator exceptionTranslator = new SQLStateSQLExceptionTranslator();

	@Override
	public Object getWrappedRecord(Object objectToBeWrapped) {
		ResultSet rs = (ResultSet) objectToBeWrapped;
		try {
			ProjectProgressEntity entity = new ProjectProgressEntity();
			entity.setProjectCode(rs.getString("projectCode"));
			entity.setWorkName(rs.getString("workName"));
			entity.setWorkSeq(rs.getString("workSeq"));
			entity.setLevel(rs.getInt("level"));
			entity.setParentWorkSeq(rs.getInt("parentWorkSeq"));
			entity.setStartDate(rs.getString("startDate") != null && !rs.getString("startDate").equals("") ?   DateUtil.getThisDay(DateUtil.getDateTime("yyyyMMdd", rs.getString("startDate")), "yyyy-mm-dd") : "");
			entity.setEndDate(rs.getString("endDate") != null && !rs.getString("endDate").equals("") ?   DateUtil.getThisDay(DateUtil.getDateTime("yyyyMMdd", rs.getString("endDate")), "yyyy-mm-dd") : "");
			entity.setRealEndDate(rs.getString("realEndDate") != null && !rs.getString("realEndDate").equals("") ?   DateUtil.getThisDay(DateUtil.getDateTime("yyyyMMdd", rs.getString("realEndDate")), "yyyy-mm-dd") : "");
			entity.setOrderSeq(rs.getInt("orderSeq"));
			entity.setContentId(rs.getString("contentId"));
			entity.setWriteDate(rs.getString("writeDate"));
			entity.setProjectTypeCode(rs.getString("projectTypeCode"));
			return entity;
		} catch (SQLException e) {
			throw exceptionTranslator.translate("ProjectProgressEntityListWrapper fails", null, e);
		}
	}

	@Override
	public void setValueListInfo(ValueListInfo info) {
		// TODO Auto-generated method stub
	}

}
