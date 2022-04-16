/*
 * $Id: ExpenseResultForCostOverDetailEtcWrapper.java,v 1.1 2011/10/25 13:49:38 cvs Exp $
 * created by    : yhyim
 * creation-date : 2011. 10. 25.
 * =========================================================
 * Copyright (c) 2011 KMAC All rights reserved.
 */
package kr.co.kmac.pms.project.statistics.vl;

import java.sql.ResultSet;
import java.sql.SQLException;

import kr.co.kmac.pms.common.util.StringUtil;
import kr.co.kmac.pms.project.statistics.data.ExpenseRealTimeResultDetailEtc;
import net.mlw.vlh.ValueListInfo;
import net.mlw.vlh.adapter.util.ObjectWrapper;

import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.jdbc.support.SQLStateSQLExceptionTranslator;

/**
 * TODO 클래스 설명
 * @author yhyim
 * @version $Id: ExpenseResultForCostOverDetailEtcWrapper.java,v 1.1 2011/10/25 13:49:38 cvs Exp $
 */
public class ExpenseResultForCostOverDetailEtcWrapper implements ObjectWrapper {

	private SQLExceptionTranslator exceptionTranslator = new SQLStateSQLExceptionTranslator();

	public Object getWrappedRecord(Object objectToBeWrapped) {
		ResultSet rs = (ResultSet) objectToBeWrapped;
		try {
			ExpenseRealTimeResultDetailEtc expenseRealTimeResultDetailEtc = new ExpenseRealTimeResultDetailEtc();
			expenseRealTimeResultDetailEtc.setJobClass(rs.getString("jobClass"));
			expenseRealTimeResultDetailEtc.setName(rs.getString("name"));
			expenseRealTimeResultDetailEtc.setTotalAmount(StringUtil.longt2Money(rs.getLong("totalAmount"))); 
			expenseRealTimeResultDetailEtc.setSeq(rs.getString("seq"));
			expenseRealTimeResultDetailEtc.setAmount(StringUtil.longt2Money(rs.getLong("amount"))); 
			expenseRealTimeResultDetailEtc.setApprovalYN(rs.getString("approvalYN"));
			expenseRealTimeResultDetailEtc.setGrandTotalAmount(StringUtil.longt2Money(rs.getLong("grandTotalAmount"))); 
			return expenseRealTimeResultDetailEtc;
		} catch (SQLException e) {
			throw exceptionTranslator.translate("ExpenseRealTimeResultDetailEtc fails", null, e);
		}
	}

	public void setValueListInfo(ValueListInfo arg0) {
	}

}
