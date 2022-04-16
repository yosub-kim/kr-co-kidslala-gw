/*
 * $Id: OrgdbHistoryExpertListWrapper.java,v 1.1 2009/09/19 11:15:29 cvs3 Exp $
 * created by    : CHO DAE HYON
 * creation-date : 2006. 3. 10.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.orgdb.vl;

import java.sql.ResultSet;
import java.sql.SQLException;

import kr.co.kmac.pms.orgdb.data.OrgdbHistoryExpertList;
import net.mlw.vlh.ValueListInfo;
import net.mlw.vlh.adapter.util.ObjectWrapper;

import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.jdbc.support.SQLStateSQLExceptionTranslator;

/**
 * @author hyunseong
 * @jobDate &2008. 6. 12.
 * @description :
 */
public class OrgdbHistoryExpertListWrapper implements ObjectWrapper {
	private SQLExceptionTranslator exceptionTranslator = new SQLStateSQLExceptionTranslator();

	public Object getWrappedRecord(Object objectToBeWrapped) {
		ResultSet rs = (ResultSet) objectToBeWrapped;
		try {
			OrgdbHistoryExpertList data = new OrgdbHistoryExpertList();
			data.setRownum(rs.getString("rownum"));
			data.setSsn(rs.getString("ssn"));
			data.setName(rs.getString("name"));
			data.setCompanyId(rs.getString("companyId"));
			data.setCompany(rs.getString("company"));
			data.setCreaterId(rs.getString("createrId"));
			data.setCreaterName(rs.getString("createrName"));
			data.setCreatedDate(rs.getString("createdDate"));

			return data;
		} catch (SQLException e) {
			throw exceptionTranslator.translate("OrgdbHistoryExpertListWrapper fails", null, e);
		}
	}

	public void setValueListInfo(ValueListInfo info) {
		// TODO Auto-generated method stub

	}

}