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
			data.setWorkSeq(rs.getString("workSeq"));// ��������
			data.setWorkName(rs.getString("workName"));// ������
			data.setOutputName(rs.getString("outputName"));// ���⹰��
			data.setStartDate(rs.getString("startDate"));// ������
			data.setEndDate(rs.getString("endDate"));// ������
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
