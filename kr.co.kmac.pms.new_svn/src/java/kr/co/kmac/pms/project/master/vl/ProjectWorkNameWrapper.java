package kr.co.kmac.pms.project.master.vl;

import java.sql.ResultSet;
import java.sql.SQLException;

import kr.co.kmac.pms.project.master.data.ProjectWorkName;
import net.mlw.vlh.ValueListInfo;
import net.mlw.vlh.adapter.util.ObjectWrapper;

import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.jdbc.support.SQLStateSQLExceptionTranslator;

public class ProjectWorkNameWrapper implements ObjectWrapper {

	private SQLExceptionTranslator exceptionTranslator = new SQLStateSQLExceptionTranslator();

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.mlw.vlh.adapter.util.ObjectWrapper#getWrappedRecord(java.lang.Object)
	 */
	public Object getWrappedRecord(Object objectToBeWrapped) {
		ResultSet rs = (ResultSet) objectToBeWrapped;
		try {
			ProjectWorkName data = new ProjectWorkName();
			data.setWorkSeq(rs.getString("workSeq"));// 업무순서
			data.setWorkName(rs.getString("workName"));// 업무명
			data.setOutputName(rs.getString("outputName"));// 산출물명
			data.setStartDate(rs.getString("startDate"));// 시작일
			data.setEndDate(rs.getString("endDate"));// 종료일
			return data;
		} catch (SQLException e) {
			throw exceptionTranslator.translate("ProjectWorkNameWrapper fails", null, e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.mlw.vlh.adapter.util.ObjectWrapper#setValueListInfo(net.mlw.vlh.ValueListInfo)
	 */
	public void setValueListInfo(ValueListInfo arg0) {
		// TODO Auto-generated method stub

	}

}
