package kr.co.kmac.pms.project.dmlist.vl;

import java.sql.ResultSet;
import java.sql.SQLException;

import kr.co.kmac.pms.project.dmlist.data.ProjectDmSearch;
import net.mlw.vlh.ValueListInfo;
import net.mlw.vlh.adapter.util.ObjectWrapper;

import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.jdbc.support.SQLStateSQLExceptionTranslator;

public class ProjectDmSearchWrapper implements ObjectWrapper {
	private SQLExceptionTranslator exceptionTranslator = new SQLStateSQLExceptionTranslator();

	public Object getWrappedRecord(Object objectToBeWrapped) {
		ResultSet rs = (ResultSet) objectToBeWrapped;
		try {
			ProjectDmSearch DmSearchlist = new ProjectDmSearch();
			DmSearchlist.setIdx(rs.getInt("idx"));
			DmSearchlist.setP_company(rs.getString("p_company"));
			DmSearchlist.setP_name(rs.getString("p_name"));
			DmSearchlist.setP_part(rs.getString("p_part"));
			DmSearchlist.setP_tel1(rs.getString("p_tel1"));
			DmSearchlist.setP_email(rs.getString("p_email"));
			DmSearchlist.setWriter(rs.getString("writer"));
			
			return DmSearchlist;
		} catch (SQLException e) {
			throw exceptionTranslator.translate("ProjectDmSearchWrapper fails", null, e);
		}
	}

	public void setValueListInfo(ValueListInfo info) {
		// TODO Auto-generated method stub

	}
}