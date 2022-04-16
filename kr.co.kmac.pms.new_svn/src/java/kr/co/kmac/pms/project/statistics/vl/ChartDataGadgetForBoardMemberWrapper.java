package kr.co.kmac.pms.project.statistics.vl;

import java.sql.ResultSet;
import java.sql.SQLException;

import kr.co.kmac.pms.project.statistics.data.ChartData;
import net.mlw.vlh.ValueListInfo;
import net.mlw.vlh.adapter.util.ObjectWrapper;

import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.jdbc.support.SQLStateSQLExceptionTranslator;

public class ChartDataGadgetForBoardMemberWrapper implements ObjectWrapper {

	private SQLExceptionTranslator exceptionTranslator = new SQLStateSQLExceptionTranslator();

	public Object getWrappedRecord(Object arg0) {
		ResultSet rs = (ResultSet) arg0;
		try {
			ChartData chartData = new ChartData();
			chartData.setKey_1(rs.getString("key_1"));
			chartData.setData_1(rs.getString("data_1"));

			if (rs.getString("scheduleDelayCnt") == null || rs.getString("scheduleDelayCnt").equals(""))
				chartData.setScheduleDelayCnt("0");
			else
				chartData.setScheduleDelayCnt(rs.getString("scheduleDelayCnt"));

			if (rs.getString("evalDelayCnt") == null || rs.getString("evalDelayCnt").equals(""))
				chartData.setEvalDelayCnt("0");
			else
				chartData.setEvalDelayCnt(rs.getString("evalDelayCnt"));

			if (rs.getString("reviewDelayCnt") == null || rs.getString("reviewDelayCnt").equals(""))
				chartData.setReviewDelayCnt("0");
			else
				chartData.setReviewDelayCnt(rs.getString("reviewDelayCnt"));

			return chartData;
		} catch (SQLException e) {
			throw exceptionTranslator.translate("ChartDataGadgetForBoardMemberWrapper fails", null, e);
		}
	}

	public void setValueListInfo(ValueListInfo arg0) {
	}
}
