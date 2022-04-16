/*
 * $Id: ChartDataGadgetForMemberWrapper.java,v 1.1 2009/09/20 10:10:12 cvs3 Exp $
 * created by    : ¾È¼ºÈ£
 * creation-date : 2006. 4. 26.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.project.statistics.vl;

import java.sql.ResultSet;
import java.sql.SQLException;

import kr.co.kmac.pms.project.statistics.data.ChartData;
import net.mlw.vlh.ValueListInfo;
import net.mlw.vlh.adapter.util.ObjectWrapper;

import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.jdbc.support.SQLStateSQLExceptionTranslator;

public class ChartDataGadgetForMemberWrapper implements ObjectWrapper {

	private SQLExceptionTranslator exceptionTranslator = new SQLStateSQLExceptionTranslator();

	public Object getWrappedRecord(Object arg0) {
		ResultSet rs = (ResultSet) arg0;
		try {
			ChartData chartData = new ChartData();
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
			throw exceptionTranslator.translate("ChartDataWrapper fails", null, e);
		}
	}

	public void setValueListInfo(ValueListInfo arg0) {
	}
}
