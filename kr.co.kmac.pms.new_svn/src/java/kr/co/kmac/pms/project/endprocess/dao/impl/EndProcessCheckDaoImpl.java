/*
 * $Id: EndProcessCheckDaoImpl.java,v 1.7 2018/01/29 02:25:48 cvs Exp $
 * created by    : jiwoongLee
 * creation-date : 2006. 3. 30.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.project.endprocess.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import kr.co.kmac.pms.project.endprocess.dao.EndProcessCheckDao;
import kr.co.kmac.pms.project.endprocess.data.EndProcessCheck;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.jdbc.object.SqlUpdate;

/**
 * TODO 클래스 설명
 * 
 * @author jiwoongLee
 * @version $Id: EndProcessCheckDaoImpl.java,v 1.7 2018/01/29 02:25:48 cvs Exp $
 */
public class EndProcessCheckDaoImpl extends JdbcDaoSupport implements EndProcessCheckDao {
	private EndProcessCheckInsertQuery endProcessCheckInsertQuery;
	private EndProcessCheckUpdateQuery endProcessCheckUpdateQuery;
	private EndProcessCheckRetrieveQuery endProcessCheckRetrieveQuery;
	private EndProcessCheckFindQuery endProcessCheckFindQuery;

	protected void initDao() throws Exception {
		this.endProcessCheckInsertQuery = new EndProcessCheckInsertQuery(getDataSource());
		this.endProcessCheckRetrieveQuery = new EndProcessCheckRetrieveQuery(getDataSource());
		this.endProcessCheckUpdateQuery = new EndProcessCheckUpdateQuery(getDataSource());
		this.endProcessCheckFindQuery = new EndProcessCheckFindQuery(getDataSource());
	}

	protected class EndProcessCheckInsertQuery extends SqlUpdate {
		protected EndProcessCheckInsertQuery(DataSource ds) {
			super(ds, " INSERT INTO EndProcessCheck(projectCode, taskId, isExecuted)"
					+ "  VALUES(?, ?, ?)                                            ");

			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected int insert(EndProcessCheck endProcessCheck) {
			return this.update(new Object[] { endProcessCheck.getProjectCode(), endProcessCheck.getTaskId(), endProcessCheck.getIsExecuted() });
		}
	}

	protected class EndProcessCheckUpdateQuery extends SqlUpdate {
		protected EndProcessCheckUpdateQuery(DataSource ds) {
			super(ds, " UPDATE EndProcessCheck SET isExecuted=? WHERE projectCode=? AND taskId=?             ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected int store(EndProcessCheck endProcessCheck) {
			return this.update(new Object[] { endProcessCheck.getIsExecuted(), endProcessCheck.getProjectCode(), endProcessCheck.getTaskId() });
		}
	}

	protected class EndProcessCheckRetrieveQuery extends MappingSqlQuery {
		protected EndProcessCheckRetrieveQuery(DataSource ds, String query) {
			super(ds, query);
		}

		protected EndProcessCheckRetrieveQuery(DataSource ds) {
			super(ds, "Select * from EndProcessCheck where projectCode=? and taskId=? ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			EndProcessCheck endProcessCheck = new EndProcessCheck();
			endProcessCheck.setProjectCode(rs.getString("projectCode"));
			endProcessCheck.setTaskId(rs.getString("taskId"));
			endProcessCheck.setIsExecuted(rs.getString("isExecuted"));
			return endProcessCheck;
		}
	}

	protected class EndProcessCheckFindQuery extends EndProcessCheckRetrieveQuery {
		protected EndProcessCheckFindQuery(DataSource ds, String query) {
			super(ds, query);
		}

		protected EndProcessCheckFindQuery(DataSource ds) {
			super(ds, "Select * from EndProcessCheck where projectCode=?  ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}
	}

	@Override
	public void create(EndProcessCheck endProcessCheck) throws DataAccessException {
		this.endProcessCheckInsertQuery.insert(endProcessCheck);
	}

	@Override
	public void create(EndProcessCheck[] endProcessCheck) throws DataAccessException {
		for (int i = 0; i < endProcessCheck.length; i++) {
			this.endProcessCheckInsertQuery.insert(endProcessCheck[i]);
		}
	}

	@Override
	public void create(String projectCode, String taskId, String isExecuted) throws DataAccessException {
		EndProcessCheck endProcessCheck = new EndProcessCheck();
		endProcessCheck.setProjectCode(projectCode);
		endProcessCheck.setTaskId(taskId);
		endProcessCheck.setIsExecuted(isExecuted);
		this.create(endProcessCheck);
	}

	@Override
	public void store(EndProcessCheck endProcessCheck) throws DataAccessException {
		this.endProcessCheckUpdateQuery.store(endProcessCheck);
	}

	@Override
	public boolean isExist(String projectCode, String taskId) throws DataAccessException {
		return this.endProcessCheckRetrieveQuery.findObject(new Object[] { projectCode, taskId }) != null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean isFinished(String projectCode) throws DataAccessException {
		List<EndProcessCheck> projectEndResult = this.endProcessCheckFindQuery.execute(new Object[] { projectCode });
		for (int i = 0; i < projectEndResult.size(); i++) {
			if (((EndProcessCheck) projectEndResult.get(i)).getIsExecuted().equals("N")) {
				return false;
			}
		}

		return true;
	}
	

	@Override
	public String getCooSsn(String runningDivCode) throws DataAccessException {
		try {
			//return (String) (getJdbcTemplate().queryForMap("Select ssn from expertpool where dept = '" + runningDivCode + "' and enable= '1' and jobClass = 'A'")
			//		.get("ssn"));
			return (String) (getJdbcTemplate().queryForMap("select data_2 as ssn from cmTableData where table_name='RUNNING_DIV_CODE' and key_1= '" + runningDivCode + "'")
					.get("ssn"));
		} catch (Exception e) {
			return "";
		}
	}

	@Override
	public String getKMSsn() throws DataAccessException {
		try {
			return (String) (getJdbcTemplate().queryForMap("Select KEY_1 as ssn from cmTableData where Table_name = 'PROJECT_REVIEW_VERIFICATION' ")
					.get("ssn"));
		} catch (Exception e) {
			return "";
		}
	}

}
