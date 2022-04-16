package kr.co.kmac.pms.project.statistics.vl;

import java.sql.ResultSet;
import java.sql.SQLException;

import kr.co.kmac.pms.project.master.data.ProjectReportDetail;
import net.mlw.vlh.ValueListInfo;
import net.mlw.vlh.adapter.util.ObjectWrapper;

import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.jdbc.support.SQLStateSQLExceptionTranslator;

public class ExpertPoolWorkPeriodListWrapper_repodetail implements ObjectWrapper {

	private SQLExceptionTranslator exceptionTranslator = new SQLStateSQLExceptionTranslator();

	public Object getWrappedRecord(Object objectToBeWrapped) {
		ResultSet rs = (ResultSet) objectToBeWrapped;
		try {
			ProjectReportDetail data = new ProjectReportDetail();
			data.setProjectcode(rs.getString("projectcode"));
			data.setName(rs.getString("name"));
			data.setSsn(rs.getString("ssn"));
			data.setYear(rs.getString("year"));
			data.setMonth(rs.getString("month"));
			data.setAllcount(rs.getString("allcount"));
			data.setRealcost(rs.getString("realcost"));
			data.setApprover(rs.getString("approver"));
			data.setComplete(rs.getString("complete"));
			data.setReject(rs.getString("reject"));
			data.setReviewer(rs.getString("reviewer"));
			data.setWrite(rs.getString("write"));
			data.setApprover_cnt(rs.getString("approver_cnt"));
			data.setComplete_cnt(rs.getString("complete_cnt"));
			data.setReject_cnt(rs.getString("reject_cnt"));
			data.setReviewer_cnt(rs.getString("reviewer_cnt"));
			data.setWrite_cnt(rs.getString("write_cnt"));
			data.setAllcount1(rs.getString("allcount1"));
			data.setAllcount2(rs.getString("allcount2"));
			data.setAllmoney(rs.getString("allmoney"));
			data.setAllmoney1(rs.getString("allmoney1"));
			data.setResRate(rs.getString("resrate"));
			return data;
		} catch (SQLException e) {
			throw exceptionTranslator.translate("ExpertPoolWorkPeriodListWrapper_repodetail fails", null, e);
		}
	}

	public void setValueListInfo(ValueListInfo arg0) {

	}

}
