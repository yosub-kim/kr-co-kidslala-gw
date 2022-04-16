/*
 * $Id: DownloadLogWrapper.java,v 1.2 2012/07/16 11:43:41 cvs Exp $
 * creation-date : 2006. 2. 21.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.system.downloadlog.vl;

import java.sql.ResultSet;
import java.sql.SQLException;

import kr.co.kmac.pms.system.downloadlog.data.DownloadData;
import net.mlw.vlh.ValueListInfo;
import net.mlw.vlh.adapter.util.ObjectWrapper;

import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.jdbc.support.SQLStateSQLExceptionTranslator;

/**
 * TODO Provide description for "DownloadLogWrapper"
 * @author halberd
 * @version $Id: DownloadLogWrapper.java,v 1.2 2012/07/16 11:43:41 cvs Exp $
 */
public class DownloadLogWrapper implements ObjectWrapper {

	private SQLExceptionTranslator exceptionTranslator = new SQLStateSQLExceptionTranslator();
	/*
	 * (non-Javadoc)
	 * @see net.mlw.vlh.adapter.util.ObjectWrapper#getWrappedRecord(java.lang.Object)
	 */
	public Object getWrappedRecord(Object objectToBeWrapped) {
		// TODO Auto-generated method stub
		ResultSet rs = (ResultSet) objectToBeWrapped;
		try {
			DownloadData downloadData = new DownloadData();
			downloadData.setSsn(rs.getString("ssn"));
			downloadData.setName(rs.getString("name"));
			downloadData.setCompany(rs.getString("company"));
			downloadData.setCnt(rs.getString("cnt"));
			downloadData.setDate(rs.getString("date"));
			downloadData.setDept(rs.getString("dept"));
			downloadData.setPosition(rs.getString("position"));
			downloadData.setJobclass(rs.getString("jobclass"));
			downloadData.setIsDailyDownloadLimit(rs.getString("isDailyDownloadLimit"));
			return downloadData;
		} catch (SQLException e) {
			throw exceptionTranslator.translate("DownloadLogWrapper fails", null, e);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see net.mlw.vlh.adapter.util.ObjectWrapper#setValueListInfo(net.mlw.vlh.ValueListInfo)
	 */
	public void setValueListInfo(ValueListInfo info) {
		// TODO Auto-generated method stub

	}

}
