package kr.co.kmac.pms.support.issue.vl;

import java.sql.ResultSet;
import java.sql.SQLException;

import kr.co.kmac.pms.support.issue.data.IssueList;

import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.jdbc.support.SQLStateSQLExceptionTranslator;

import net.mlw.vlh.ValueListInfo;
import net.mlw.vlh.adapter.util.ObjectWrapper;

public class SearchIssueListWrapper implements ObjectWrapper {
	private SQLExceptionTranslator exceptionTranslator = new SQLStateSQLExceptionTranslator();

	public Object getWrappedRecord(Object objectToBeWrapped) {

		ResultSet rs = (ResultSet) objectToBeWrapped;
		try {
			IssueList list = new IssueList();
			list.setNo(rs.getString("no"));
			list.setYear(rs.getString("year"));
			list.setSeqno(rs.getString("seqno"));
			list.setSabun(rs.getString("sabun"));
			list.setWtime(rs.getString("wtime"));
			list.setCddpt(rs.getString("cddpt"));
			list.setDept_name(rs.getString("dept_name"));
			list.setCount_t(rs.getString("count_t"));
			list.setGubun(rs.getString("gubun"));
			list.setReceive(rs.getString("receive"));
			list.setContent(rs.getString("content"));
			list.setGaksa(rs.getString("gaksa"));
			return list;
		} catch (SQLException e) {
			throw exceptionTranslator.translate("SearchIssueListWrapper", null, e);
		}
	}

	@Override
	public void setValueListInfo(ValueListInfo info) {
		// TODO Auto-generated method stub

	}

}
