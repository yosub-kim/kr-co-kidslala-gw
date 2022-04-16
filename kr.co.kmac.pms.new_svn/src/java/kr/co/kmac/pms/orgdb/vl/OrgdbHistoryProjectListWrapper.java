/*
 * $Id: OrgdbHistoryProjectListWrapper.java,v 1.1 2009/09/19 11:15:29 cvs3 Exp $
 * created by    : CHO DAE HYON
 * creation-date : 2006. 3. 10.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.orgdb.vl;

import java.sql.ResultSet;
import java.sql.SQLException;

import kr.co.kmac.pms.orgdb.data.OrgdbHistoryProjectList;
import net.mlw.vlh.ValueListInfo;
import net.mlw.vlh.adapter.util.ObjectWrapper;

import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.jdbc.support.SQLStateSQLExceptionTranslator;

/**
 * @author hyunseong
 * @jobDate &2008. 6. 12.
 * @description :
 */
public class OrgdbHistoryProjectListWrapper implements ObjectWrapper {
	private SQLExceptionTranslator exceptionTranslator = new SQLStateSQLExceptionTranslator();

	public Object getWrappedRecord(Object objectToBeWrapped) {
		ResultSet rs = (ResultSet) objectToBeWrapped;
		try {
			OrgdbHistoryProjectList data = new OrgdbHistoryProjectList();
			data.setRownum(rs.getString("rownum"));
			data.setProjectCode(rs.getString("projectCode"));
			data.setBusinessTypeCode(rs.getString("businessTypeCode"));
			data.setBusinessType(rs.getString("businessType"));
			data.setRunningDivCode(rs.getString("runningDivCode"));
			data.setRunningDiv(rs.getString("runningDiv"));
			data.setProjectName(rs.getString("projectName"));
			data.setProjectState(rs.getString("projectState"));
			data.setProjectStateName(rs.getString("projectStateName"));
			data.setPmSsn(rs.getString("pmSsn"));
			data.setPmName(rs.getString("pmName"));
			return data;
		} catch (SQLException e) {
			throw exceptionTranslator.translate("OrgdbHistoryProjectListWrapper fails", null, e);
		}
	}

	public void setValueListInfo(ValueListInfo info) {
		// TODO Auto-generated method stub

	}

}