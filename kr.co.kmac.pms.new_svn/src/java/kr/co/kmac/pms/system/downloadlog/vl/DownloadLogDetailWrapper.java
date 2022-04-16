/*
 * $Id: DownloadLogDetailWrapper.java,v 1.1 2009/09/19 11:15:32 cvs3 Exp $
 * creation-date : 2006. 2. 21.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.system.downloadlog.vl;

import java.sql.ResultSet;
import java.sql.SQLException;

import kr.co.kmac.pms.system.downloadlog.data.DownloadLogData;
import net.mlw.vlh.ValueListInfo;
import net.mlw.vlh.adapter.util.ObjectWrapper;

import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.jdbc.support.SQLStateSQLExceptionTranslator;

/**
 * TODO Provide description for "DownloadLogDetailWrapper"
 * @author halberd
 * @version $Id: DownloadLogDetailWrapper.java,v 1.1 2009/09/19 11:15:32 cvs3 Exp $
 */
public class DownloadLogDetailWrapper implements ObjectWrapper {

	private SQLExceptionTranslator exceptionTranslator = new SQLStateSQLExceptionTranslator();
	/*
	 * (non-Javadoc)
	 * @see net.mlw.vlh.adapter.util.ObjectWrapper#getWrappedRecord(java.lang.Object)
	 */
	public Object getWrappedRecord(Object objectToBeWrapped) {
		// TODO Auto-generated method stub
		ResultSet rs = (ResultSet) objectToBeWrapped;
		try {
			DownloadLogData downloadLogData = new DownloadLogData();
			downloadLogData.setDownloadTime(rs.getString("downloadTime"));
			downloadLogData.setFileId(rs.getString("fileId"));
			downloadLogData.setFileName(rs.getString("fileName"));
			downloadLogData.setIp(rs.getString("ip"));
			downloadLogData.setSsn(rs.getString("ssn"));
			downloadLogData.setUploadUserId(rs.getString("uploadUserId"));
			downloadLogData.setUploadUserName(rs.getString("uploadUserName"));
			return downloadLogData;
		} catch (SQLException e) {
			throw exceptionTranslator.translate("DownloadLogDetailWrapper fails", null, e);
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
