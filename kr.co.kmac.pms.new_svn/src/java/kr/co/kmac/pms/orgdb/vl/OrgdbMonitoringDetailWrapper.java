/*
 * $Id: OrgdbMonitoringDetailWrapper.java,v 1.1 2009/09/19 11:15:29 cvs3 Exp $
 * created by    : CHO DAE HYON
 * creation-date : 2006. 3. 10.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.orgdb.vl;

import java.sql.ResultSet;
import java.sql.SQLException;

import kr.co.kmac.pms.orgdb.data.OrgdbMonitoringDetailData;
import net.mlw.vlh.ValueListInfo;
import net.mlw.vlh.adapter.util.ObjectWrapper;

import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.jdbc.support.SQLStateSQLExceptionTranslator;

/**
 * @author hyunseong
 * @jobDate &2008. 6. 10.
 * @description :
 */
public class OrgdbMonitoringDetailWrapper implements ObjectWrapper {
	private SQLExceptionTranslator exceptionTranslator = new SQLStateSQLExceptionTranslator();

	public Object getWrappedRecord(Object objectToBeWrapped) {
		ResultSet rs = (ResultSet) objectToBeWrapped;
		try {
			OrgdbMonitoringDetailData data = new OrgdbMonitoringDetailData();
			data.setKey1(rs.getString("key1"));
			data.setKey2(rs.getString("key2"));
			data.setWriterSsn(rs.getString("writerSsn"));
			data.setWriterName(rs.getString("writerName"));
			data.setTitle(rs.getString("title"));
			data.setOrgCode(rs.getString("orgCode"));
			data.setOrgName(rs.getString("orgName"));
			return data;
		} catch (SQLException e) {
			throw exceptionTranslator.translate("OrgdbMonitoringDetailWrapper fails", null, e);
		}
	}

	public void setValueListInfo(ValueListInfo info) {
		// TODO Auto-generated method stub

	}

}
