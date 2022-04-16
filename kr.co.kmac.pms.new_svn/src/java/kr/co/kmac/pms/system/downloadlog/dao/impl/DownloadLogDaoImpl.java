/*
 * $Id: DownloadLogDaoImpl.java,v 1.6 2017/07/20 23:49:07 cvs Exp $
 * created by    : ¾È¼ºÈ£
 * creation-date : 2006. 5. 30.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.system.downloadlog.dao.impl;

import java.sql.Types;

import javax.sql.DataSource;

import kr.co.kmac.pms.system.downloadlog.dao.DownloadLogDao;
import kr.co.kmac.pms.system.downloadlog.data.DownloadLogData;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.object.SqlUpdate;

public class DownloadLogDaoImpl extends JdbcDaoSupport implements DownloadLogDao {
	private DownloadLogCreate downloadLogCreate;

	protected class DownloadLogCreate extends SqlUpdate {
		protected DownloadLogCreate(DataSource ds) {
			super(ds, " INSERT INTO DownloadLog( " + "		fileId, fileName, ssn, downloadTime, ip, uploadUserId)" + " VALUES(?, ?, ?, replace(convert(varchar, getDate(), 120),'-',''), ?, ? ) ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected int insert(DownloadLogData downloadLogData) throws DataAccessException {
			return this.update(new Object[] { downloadLogData.getFileId(), downloadLogData.getFileName(), downloadLogData.getSsn(),
					downloadLogData.getIp(), downloadLogData.getUploadUserId() });
		}
	}

	protected void initDao() throws Exception {
		downloadLogCreate = new DownloadLogCreate(getDataSource());
	}

	public void create(DownloadLogData downloadLogData) throws DataAccessException {
		if (!downloadLogData.getSsn().startsWith("00000011111"))
			this.downloadLogCreate.insert(downloadLogData);
	}

	@Override
	public int getWeeklyCount(String ssn) throws DataAccessException {
		String	query = " SELECT count(ssn) FROM downloadlog WHERE ssn=? ";
		query += " AND downloadTime >= DATEADD(DD, (DATEPART(WEEKDAY,GETDATE())-1)*(-1), GETDATE()) ";
		query += " AND downloadTime < DATEADD(DD, 7-(DATEPART(WEEKDAY,GETDATE()))+1, GETDATE()) ";
		return getJdbcTemplate().queryForInt(query, new Object[] { ssn });
	}

	@Override
	public int getMonthlycount(String ssn, String fromDate, String toDate) throws DataAccessException {
		String query = " SELECT count(fileId) FROM downloadlog WHERE ssn =? ";
		query += "	AND downloadTime >= ?";
		query += "	AND downloadTime < ?";
		return getJdbcTemplate().queryForInt(query, new Object[] { ssn, fromDate, toDate });
	}

}
