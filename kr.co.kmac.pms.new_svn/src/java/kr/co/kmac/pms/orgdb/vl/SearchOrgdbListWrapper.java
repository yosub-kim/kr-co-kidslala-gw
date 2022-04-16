/*
 * $Id: SearchOrgdbListWrapper.java,v 1.1 2009/09/19 11:15:29 cvs3 Exp $
 * creation-date : 2006. 3. 10.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.orgdb.vl;

import java.sql.ResultSet;
import java.sql.SQLException;

import kr.co.kmac.pms.orgdb.data.OrgdbList;
import net.mlw.vlh.ValueListInfo;
import net.mlw.vlh.adapter.util.ObjectWrapper;

import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.jdbc.support.SQLStateSQLExceptionTranslator;

/**
 * TODO Provide description for "ProjectDataSelectListWrapper"
 * @author halberd
 * @version $Id: SearchOrgdbListWrapper.java,v 1.1 2009/09/19 11:15:29 cvs3 Exp $
 */
public class SearchOrgdbListWrapper implements ObjectWrapper {
	private SQLExceptionTranslator exceptionTranslator = new SQLStateSQLExceptionTranslator();
	/*
	 * (non-Javadoc)
	 * @see net.mlw.vlh.adapter.util.ObjectWrapper#getWrappedRecord(java.lang.Object)
	 */
	public Object getWrappedRecord(Object objectToBeWrapped) {
		// TODO Auto-generated method stub
		ResultSet rs = (ResultSet) objectToBeWrapped;
		try {
			OrgdbList list = new OrgdbList();
			list.setOrgCode(rs.getString("orgCode"));
			list.setOrgName(rs.getString("orgName"));
			list.setRelWithkmac(rs.getString("relWithKmac"));
			list.setSpecialField(rs.getString("specialField"));
			list.setTelNo(rs.getString("telNo"));
			list.setWriter(rs.getString("writer"));
			list.setHomePage(rs.getString("homePage"));
			list.setCheckYN(rs.getString("checkYN"));

			return list;
		} catch (SQLException e) {
			throw exceptionTranslator.translate("SearchOrgdbListWrapper fails", null, e);
		}

	}

	/*
	 * (non-Javadoc)
	 * @see net.mlw.vlh.adapter.util.ObjectWrapper#setValueListInfo(net.mlw.vlh.ValueListInfo)
	 */
	public void setValueListInfo(ValueListInfo valueListInfo) {
		// TODO Auto-generated method stub

	}

}
