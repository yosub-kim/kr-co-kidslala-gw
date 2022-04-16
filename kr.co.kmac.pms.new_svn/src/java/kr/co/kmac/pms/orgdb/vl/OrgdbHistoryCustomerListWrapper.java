/*
 * $Id: OrgdbHistoryCustomerListWrapper.java,v 1.1 2009/09/19 11:15:29 cvs3 Exp $
 * created by    : CHO DAE HYON
 * creation-date : 2006. 3. 10.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.orgdb.vl;

import java.sql.ResultSet;
import java.sql.SQLException;

import kr.co.kmac.pms.orgdb.data.OrgdbHistoryCustomerList;
import net.mlw.vlh.ValueListInfo;
import net.mlw.vlh.adapter.util.ObjectWrapper;

import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.jdbc.support.SQLStateSQLExceptionTranslator;

/**
 * @author hyunseong
 * @jobDate &2008. 6. 12.
 * @description :
 */
public class OrgdbHistoryCustomerListWrapper implements ObjectWrapper {
	private SQLExceptionTranslator exceptionTranslator = new SQLStateSQLExceptionTranslator();

	public Object getWrappedRecord(Object objectToBeWrapped) {
		ResultSet rs = (ResultSet) objectToBeWrapped;
		try {
			OrgdbHistoryCustomerList data = new OrgdbHistoryCustomerList();
			data.setRownum(rs.getString("rownum"));
			data.setIdx(rs.getString("idx"));
			data.setSubject(rs.getString("subject"));
			data.setRunningDiv(rs.getString("runningDiv"));
			data.setBusinessType(rs.getString("businessType"));
			data.setCustomerCode(rs.getString("customerCode"));
			data.setCustomerName(rs.getString("customerName"));
			data.setWriterName(rs.getString("writerName"));
			data.setWriteDate(rs.getString("writeDate"));
			return data;
		} catch (SQLException e) {
			throw exceptionTranslator.translate("OrgdbHistoryCustomerListWrapper fails", null, e);
		}
	}

	public void setValueListInfo(ValueListInfo info) {
		// TODO Auto-generated method stub

	}

}