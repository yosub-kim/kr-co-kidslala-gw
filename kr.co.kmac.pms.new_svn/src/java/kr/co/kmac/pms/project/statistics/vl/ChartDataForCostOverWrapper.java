/*
 * $Id: ChartDataForCostOverWrapper.java,v 1.1 2009/09/20 04:08:10 cvs3 Exp $
 * created by    : yhyim
 * creation-date : 2007. 10. 11.
 * =========================================================
 * Copyright (c) 2007 KMAC. All rights reserved.
 */
package kr.co.kmac.pms.project.statistics.vl;

import java.sql.ResultSet;
import java.sql.SQLException;

import kr.co.kmac.pms.project.statistics.data.ChartDataForCostOver;
import net.mlw.vlh.ValueListInfo;
import net.mlw.vlh.adapter.util.ObjectWrapper;

import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.jdbc.support.SQLStateSQLExceptionTranslator;

public class ChartDataForCostOverWrapper implements ObjectWrapper {

	private SQLExceptionTranslator exceptionTranslator = new SQLStateSQLExceptionTranslator();

	public Object getWrappedRecord(Object arg0) {
		ResultSet rs = (ResultSet) arg0;
		try {
			ChartDataForCostOver chartDataForCostOver = new ChartDataForCostOver();
			chartDataForCostOver.setKey_1(rs.getString("key_1"));
			chartDataForCostOver.setData_1(rs.getString("data_1"));
			chartDataForCostOver.setIngTotal(rs.getString("ingTotal"));
			chartDataForCostOver.setPayCostOver(rs.getString("payCostOver"));
			chartDataForCostOver.setEtcCostOver(rs.getString("etcCostOver"));
			
			return chartDataForCostOver;
		} catch (SQLException e) {
			throw exceptionTranslator.translate("ChartDataForCostOverWrapper fails", null, e);
		}
	}

	public void setValueListInfo(ValueListInfo arg0) {
	}
}
