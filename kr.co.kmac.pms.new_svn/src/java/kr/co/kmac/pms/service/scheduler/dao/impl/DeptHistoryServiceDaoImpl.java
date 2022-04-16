/*
 * $Id: DeptHistoryServiceDaoImpl.java,v 1.4 2012/04/09 08:07:35 cvs Exp $
 * created by    : yhyim
 * creation-date : 2008. 1. 11.
 * =========================================================
 * Copyright (c) 2008 KMAC All rights reserved.
 */

package kr.co.kmac.pms.service.scheduler.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import javax.sql.DataSource;

import kr.co.kmac.pms.common.util.StringUtil;
import kr.co.kmac.pms.service.scheduler.dao.DeptHistoryServiceDao;
import kr.co.kmac.pms.service.scheduler.data.DeptHistoryData;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.object.SqlUpdate;

public class DeptHistoryServiceDaoImpl extends JdbcDaoSupport implements DeptHistoryServiceDao {
	private ExpertInfoToHistory expertInfoToHistory;

	protected void initDao() throws Exception {
		this.expertInfoToHistory = new ExpertInfoToHistory(getDataSource());
	}

	@Override
	public void setWorkingPUMemberInfoToHistory(DeptHistoryData deptHistoryData) {
		deptHistoryData.setYear(StringUtil.getCurr("yyyyMMdd").substring(0, 4));
		deptHistoryData.setMonth(StringUtil.getCurr("yyyyMMdd").substring(4, 6));
		deptHistoryData.setDay(StringUtil.getCurr("yyyyMMdd").substring(6, 8));
		this.expertInfoToHistory.insert(deptHistoryData);
	}

	protected class ExpertInfoToHistory extends SqlUpdate {
		protected ExpertInfoToHistory(DataSource ds) {
			super(ds, " INSERT INTO deptHistory (ssn, name, jobClass, rate, dept, year, month, day, companyPosition, isRunning, userId) "
					+ "           VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, 'Y', ?)");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected int insert(DeptHistoryData deptHistoryData) {
			return this.update(new Object[] { deptHistoryData.getSsn(), deptHistoryData.getName(), deptHistoryData.getJobClass(),
					deptHistoryData.getRate(), deptHistoryData.getDept(), deptHistoryData.getYear(), deptHistoryData.getMonth(),
					deptHistoryData.getDay(), deptHistoryData.getCompanyPosition(), deptHistoryData.getUserId() });
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DeptHistoryData> getWorkingExpertList() {
		StringBuffer query = new StringBuffer();
		query.append(" SELECT ssn, name, jobClass, rate, dept, companyPosition, userId 	");
		query.append("   FROM expertPool                			");
		query.append("  WHERE enable = '1'							");
		query.append("    AND jobClass IN ('A', 'B', 'J', 'C', 'H', 'N')	");
		query.append("  ORDER BY dept, jobClass, companyPosition	");

		return getJdbcTemplate().query(query.toString(), new RowMapper() {
			public Object mapRow(ResultSet rs, int currentRowNum) throws SQLException {
				DeptHistoryData deptHistoryData = new DeptHistoryData();
				deptHistoryData.setSsn(rs.getString("ssn"));
				deptHistoryData.setName(rs.getString("name"));
				deptHistoryData.setJobClass(rs.getString("jobClass"));
				deptHistoryData.setRate(rs.getString("rate"));
				deptHistoryData.setDept(rs.getString("dept"));
				deptHistoryData.setCompanyPosition(rs.getString("companyPosition"));
				deptHistoryData.setUserId(rs.getString("userId"));
				return deptHistoryData;
			}
		});
	}
}