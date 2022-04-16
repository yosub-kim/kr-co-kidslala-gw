/*
 * $Id: SanctiontPresentStateWrapper.java,v 1.4 2013/01/18 08:56:34 cvs Exp $
 * created by    : ¾È¼ºÈ£
 * creation-date : 2006. 4. 26.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.sanction.statistics.vl;

import java.sql.ResultSet;
import java.sql.SQLException;

import kr.co.kmac.pms.sanction.statistics.data.SanctiontPresentState;
import net.mlw.vlh.ValueListInfo;
import net.mlw.vlh.adapter.util.ObjectWrapper;

import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.jdbc.support.SQLStateSQLExceptionTranslator;

public class SanctiontPresentStateWrapper implements ObjectWrapper {

	private SQLExceptionTranslator exceptionTranslator = new SQLStateSQLExceptionTranslator();

	public Object getWrappedRecord(Object arg0) {
		ResultSet rs = (ResultSet) arg0;
		try {
			SanctiontPresentState preportAllSanctiontPresentState = new SanctiontPresentState();
			preportAllSanctiontPresentState.setSeq(rs.getString("seq"));
			preportAllSanctiontPresentState.setApprovalType(rs.getString("approvalType"));
			preportAllSanctiontPresentState.setApprovalTypeName(rs.getString("approvalName"));
			preportAllSanctiontPresentState.setProjectCode(rs.getString("projectCode"));
			preportAllSanctiontPresentState.setProjectName(rs.getString("projectName"));
			//preportAllSanctiontPresentState.setDivCode(rs.getString("divCode"));
			//preportAllSanctiontPresentState.setDivName(rs.getString("divName"));
			preportAllSanctiontPresentState.setRegisterDept(rs.getString("registerDept"));
			preportAllSanctiontPresentState.setRegisterDeptName(rs.getString("registerDeptName"));
			preportAllSanctiontPresentState.setRegisterSsn(rs.getString("registerSsn"));
			preportAllSanctiontPresentState.setRegisterName(rs.getString("registerName"));
			preportAllSanctiontPresentState.setRegisterDate(rs.getString("registerDate"));
			preportAllSanctiontPresentState.setState(rs.getString("state"));
			preportAllSanctiontPresentState.setPresent(rs.getString("present"));
			preportAllSanctiontPresentState.setPresentName(rs.getString("presentName"));
			preportAllSanctiontPresentState.setWorkType(rs.getString("workType"));
			preportAllSanctiontPresentState.setWorkTypeUrl(rs.getString("workTypeUrl"));
			preportAllSanctiontPresentState.setUseMobile(rs.getString("useMobile"));
			preportAllSanctiontPresentState.setName(rs.getString("registerName"));
			return preportAllSanctiontPresentState;
		} catch (SQLException e) {
			throw exceptionTranslator.translate("SanctiontPresentStateWrapper fails", null, e);
		}
	}

	public void setValueListInfo(ValueListInfo arg0) {
	}
}
