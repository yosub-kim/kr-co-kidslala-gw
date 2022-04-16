/*
 * $Id: ChartDataWrapper.java,v 1.2 2010/01/21 11:21:13 cvs1 Exp $
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

public class ChartDataWrapper implements ObjectWrapper {

	private SQLExceptionTranslator exceptionTranslator = new SQLStateSQLExceptionTranslator();

	public Object getWrappedRecord(Object arg0) {
		ResultSet rs = (ResultSet) arg0;
		try {
			ChartData chartData = new ChartData();
			chartData.setKey_1(rs.getString("key_1"));
			chartData.setData_1(rs.getString("data_1"));
			chartData.setKey_1(rs.getString("key_1"));
			chartData.setData_1(rs.getString("data_1"));
			chartData.setIngTotal(rs.getString("ingTotal"));
			chartData.setScheduleDelayCnt(rs.getString("scheDelayCnt"));
			chartData.setCostOver(rs.getString("costOver"));
			// Job Date: 2008-06-26	Author: yhyim	Description: add below 
			if (rs.getString("reSanction") == null || rs.getString("reSanction").equals(""))
				chartData.setReSanction("0");
			else
				chartData.setReSanction(rs.getString("reSanction"));
			chartData.setEvalTotal(rs.getString("evalTotal"));
			chartData.setEvalDelayCnt(rs.getString("evalDelayCnt"));
			chartData.setReviewTotal(rs.getString("reviewTotal"));
			chartData.setReviewDelayCnt(rs.getString("reviewDelayCnt"));
			chartData.setDoneTotal(rs.getString("doneTotal"));
			chartData.setTotal(rs.getString("total"));
			return chartData;
		} catch (SQLException e) {
			throw exceptionTranslator.translate("ChartDataWrapper fails", null, e);
		}
	}

	public void setValueListInfo(ValueListInfo arg0) {
	}
}
