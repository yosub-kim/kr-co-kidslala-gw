package kr.co.kmac.pms.moudb.vl;

import java.sql.ResultSet;
import java.sql.SQLException;

import kr.co.kmac.pms.moudb.data.MoudbList;

import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.jdbc.support.SQLStateSQLExceptionTranslator;

import net.mlw.vlh.ValueListInfo;
import net.mlw.vlh.adapter.util.ObjectWrapper;

public class SearchMoudbListWrapper implements ObjectWrapper {
	private SQLExceptionTranslator exceptionTranslator = new SQLStateSQLExceptionTranslator();
	
	public Object getWrappedRecord(Object objectToBeWrapped) {
		
		ResultSet rs = (ResultSet) objectToBeWrapped;
		try {
			MoudbList list = new MoudbList();
			list.setIdx(rs.getString("idx"));
			list.setCom_name(rs.getString("com_name"));
			list.setCom_expert(rs.getString("com_expert"));
			list.setCom_cooperation(rs.getString("com_cooperation"));
			list.setCom_tel(rs.getString("com_tel"));
			list.setWriter(rs.getString("writer"));
			list.setRegdate(rs.getString("regdate"));
			list.setCountry(rs.getString("country"));
			
			return list;
		} catch (SQLException e) {
			throw exceptionTranslator.translate("SearchMoudbListWrapper fails", null, e);
		}
	}
	
	public void setValueListInfo(ValueListInfo valueListInfo) {
		
	}
	

}
