/*
 * $Id: ExpertPoolScheduleServiceDaoImpl.java,v 1.6 2019/05/20 15:35:15 cvs Exp $
 * created by    : yhyim
 * creation-date : 2019. 5. 20.
 * =========================================================
 * Copyright (c) 2019 KMAC, Inc. All rights reserved.
 */
package kr.co.kmac.pms.service.scheduler.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import javax.sql.DataSource;

import kr.co.kmac.pms.expertpool.data.ExpertPool;
import kr.co.kmac.pms.service.scheduler.dao.ExpertPoolScheduleServiceDao;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.object.SqlUpdate;


/**
 * TODO 클래스 설명
 * 
 * @author yhyim
 * @version $Id: ExpertPoolScheduleServiceDaoImpl.java,v 1.6 2019/05/20 15:35:15 cvs Exp $
 */
public class ExpertPoolScheduleServiceDaoImpl extends JdbcDaoSupport implements ExpertPoolScheduleServiceDao {

	private StringBuffer query;
	private ExpertPoolNotWorkingUpdateQuery expertPoolNotWorkingUpdateQuery;
	
	/**
	 * 미투입 산업계강사, 대학교수 신분 전환 쿼리 (산업계강사 : D, 대학교수 : E)
	 */
	private StringBuffer query_D;
	private ExpertPoolNotWorkingUpdateQuery_D expertPoolNotWorkingUpdateQuery_D;
	
	private StringBuffer query_E;
	private ExpertPoolNotWorkingUpdateQuery_E expertPoolNotWorkingUpdateQuery_E;
	
	protected void initDao() throws Exception {
		this.expertPoolNotWorkingUpdateQuery = new ExpertPoolNotWorkingUpdateQuery(getDataSource());
		this.expertPoolNotWorkingUpdateQuery_D = new ExpertPoolNotWorkingUpdateQuery_D(getDataSource());
		this.expertPoolNotWorkingUpdateQuery_E = new ExpertPoolNotWorkingUpdateQuery_E(getDataSource());
	}
	
	protected class ExpertPoolNotWorkingUpdateQuery extends SqlUpdate {
		protected ExpertPoolNotWorkingUpdateQuery(DataSource ds) {
			super(ds, " UPDATE ExpertPool								"
					+ "	SET jobclass=?, company=?, dept=?, deptName=?,	" 
					+ "		companyPosition=?, companyPositionName=?,	"
					+ "		role=?, extRole=?, rate=?,					"
					+ "		modifiedDate=getDate(), modifierId='PMS'	"
					+ " WHERE ssn = ?  									");
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

		protected int update(ExpertPool expertPool) throws DataAccessException {
			return this.update(new Object[] { expertPool.getJobClass(), expertPool.getCompany(), expertPool.getDept(), expertPool.getDeptName(),
					expertPool.getCompanyPosition(), expertPool.getCompanyPositionName(),
					expertPool.getRole(), expertPool.getExtRole(), expertPool.getRate(),
					expertPool.getSsn() });
		}
	}
	
	protected class ExpertPoolNotWorkingUpdateQuery_D extends SqlUpdate {
		protected ExpertPoolNotWorkingUpdateQuery_D(DataSource ds) {
			super(ds, " UPDATE ExpertPool								"
					+ "	SET jobclass=?, company=?, dept=?, deptName=?,	" 
					+ "		companyPosition=?, companyPositionName=?,	"
					+ "		role=?, extRole=?, rate=?,					"
					+ "		modifiedDate=getDate(), modifierId='PMS'	"
					+ " WHERE ssn = ?  									");
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

		protected int update_D(ExpertPool expertPool) throws DataAccessException {
			return this.update(new Object[] { expertPool.getJobClass(), expertPool.getCompany(), expertPool.getDept(), expertPool.getDeptName(),
					expertPool.getCompanyPosition(), expertPool.getCompanyPositionName(),
					expertPool.getRole(), expertPool.getExtRole(), expertPool.getRate(),
					expertPool.getSsn() });
		}
	}
	
	protected class ExpertPoolNotWorkingUpdateQuery_E extends SqlUpdate {
		protected ExpertPoolNotWorkingUpdateQuery_E(DataSource ds) {
			super(ds, " UPDATE ExpertPool								"
					+ "	SET jobclass=?, company=?, dept=?, deptName=?,	" 
					+ "		companyPosition=?, companyPositionName=?,	"
					+ "		role=?, extRole=?, rate=?,					"
					+ "		modifiedDate=getDate(), modifierId='PMS'	"
					+ " WHERE ssn = ?  									");
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

		protected int update_E(ExpertPool expertPool) throws DataAccessException {
			return this.update(new Object[] { expertPool.getJobClass(), expertPool.getCompany(), expertPool.getDept(), expertPool.getDeptName(),
					expertPool.getCompanyPosition(), expertPool.getCompanyPositionName(),
					expertPool.getRole(), expertPool.getExtRole(), expertPool.getRate(),
					expertPool.getSsn() });
		}
	}


	public StringBuffer getQuery() {
		this.query  = new StringBuffer();                                                                                                                         
		this.query.append("	 SELECT *																			 ");
		this.query.append("	  	FROM ExpertPool																	 ");	 
		this.query.append("	  	WHERE jobClass = 'C'															 ");	 
		this.query.append("	  	AND ssn NOT IN (																 ");
		this.query.append("	 	SELECT ssn FROM (																 ");
		this.query.append("	 		SELECT e.ssn, ROW_NUMBER() OVER (PARTITION BY m.ssn ORDER BY m.ssn) AS count ");
		this.query.append("	 		FROM project p, projectmember m, expertpool e								 ");
		this.query.append("	 		WHERE 1=1																	 ");
		this.query.append("	 		AND p.projectcode = m.projectcode											 ");
		this.query.append("	 		AND m.ssn = e.ssn															 ");
		this.query.append("	 		AND p.projectstate < '6'													 ");
		this.query.append("	 		AND e.jobclass='C'															 ");
		this.query.append("	 	)ex																				 ");
		this.query.append("	 	WHERE ex.count = '1'															 ");
		this.query.append("	 )																					 ");
		System.out.println(query.toString());
		return query;
	}
	
	public StringBuffer getQuery_D() {
		this.query_D  = new StringBuffer();                                                                                                                         
		this.query_D.append("	 SELECT *																			 ");
		this.query_D.append("	  	FROM ExpertPool																	 ");	 
		this.query_D.append("	  	WHERE jobClass = 'D'															 ");	 
		this.query_D.append("	  	AND ssn NOT IN (																 ");
		this.query_D.append("	 	SELECT ssn FROM (																 ");
		this.query_D.append("	 		SELECT e.ssn, ROW_NUMBER() OVER (PARTITION BY m.ssn ORDER BY m.ssn) AS count ");
		this.query_D.append("	 		FROM project p, projectmember m, expertpool e								 ");
		this.query_D.append("	 		WHERE 1=1																	 ");
		this.query_D.append("	 		AND p.projectcode = m.projectcode											 ");
		this.query_D.append("	 		AND m.ssn = e.ssn															 ");
		this.query_D.append("	 		AND p.projectstate < '6'													 ");
		this.query_D.append("	 		AND e.jobclass='D'															 ");
		this.query_D.append("	 	)ex																				 ");
		this.query_D.append("	 	WHERE ex.count = '1'															 ");
		this.query_D.append("	 )																					 ");
		System.out.println(query_D.toString());
		return query_D;
	}
	
	public StringBuffer getQuery_E() {
		this.query_E  = new StringBuffer();                                                                                                                         
		this.query_E.append("	 SELECT *																			 ");
		this.query_E.append("	  	FROM ExpertPool																	 ");	 
		this.query_E.append("	  	WHERE jobClass = 'E'															 ");	 
		this.query_E.append("	  	AND ssn NOT IN (																 ");
		this.query_E.append("	 	SELECT ssn FROM (																 ");
		this.query_E.append("	 		SELECT e.ssn, ROW_NUMBER() OVER (PARTITION BY m.ssn ORDER BY m.ssn) AS count ");
		this.query_E.append("	 		FROM project p, projectmember m, expertpool e								 ");
		this.query_E.append("	 		WHERE 1=1																	 ");
		this.query_E.append("	 		AND p.projectcode = m.projectcode											 ");
		this.query_E.append("	 		AND m.ssn = e.ssn															 ");
		this.query_E.append("	 		AND p.projectstate < '6'													 ");
		this.query_E.append("	 		AND e.jobclass='E'															 ");
		this.query_E.append("	 	)ex																				 ");
		this.query_E.append("	 	WHERE ex.count = '1'															 ");
		this.query_E.append("	 )																					 ");
		System.out.println(query_E.toString());
		return query_E;
	}
	
	public void setQuery(StringBuffer query) {
		this.query = query;
	}
	
	public void setQuery_D(StringBuffer query_D){
		this.query_D = query_D;
	}
	
	public void setQuery_E(StringBuffer query_E){
		this.query_E = query_E;
	}
	
	
	private static RowMapper expertPoolScheduleDataRowMapper = new RowMapper() {
		public Object mapRow(ResultSet rs, int currentRowNum) throws SQLException {
			ExpertPool expertPool = new ExpertPool();
			expertPool.setSsn(rs.getString("ssn"));
			expertPool.setName(rs.getString("name"));
			expertPool.setUserId(rs.getString("userId"));
			expertPool.setJobClass("F");
			expertPool.setCompany("(대기)엑스퍼트");
			expertPool.setDept("");
			expertPool.setDeptName("(대기)엑스퍼트");
			expertPool.setCompanyPosition("");
			expertPool.setCompanyPositionName("");
			expertPool.setRole("");
			expertPool.setExtRole("");
			expertPool.setRate("0");
						
			return expertPool;
		}
	};
	
	private static RowMapper expertPoolScheduleDataRowMapper_D = new RowMapper() {
		public Object mapRow(ResultSet rs, int currentRowNum) throws SQLException {
			ExpertPool expertPool = new ExpertPool();
			expertPool.setSsn(rs.getString("ssn"));
			expertPool.setName(rs.getString("name"));
			expertPool.setUserId(rs.getString("userId"));
			expertPool.setJobClass("L");
			expertPool.setCompany(rs.getString("company"));
			expertPool.setDept("");
			expertPool.setDeptName(rs.getString("deptName"));
			expertPool.setCompanyPosition("");
			expertPool.setCompanyPositionName(rs.getString("companyPositionName"));
			expertPool.setRole("");
			expertPool.setExtRole("");
			expertPool.setRate("0");
						
			return expertPool;
		}
	};
	
	private static RowMapper expertPoolScheduleDataRowMapper_E = new RowMapper() {
		public Object mapRow(ResultSet rs, int currentRowNum) throws SQLException {
			ExpertPool expertPool = new ExpertPool();
			expertPool.setSsn(rs.getString("ssn"));
			expertPool.setName(rs.getString("name"));
			expertPool.setUserId(rs.getString("userId"));
			expertPool.setJobClass("M");
			expertPool.setCompany(rs.getString("company"));
			expertPool.setDept("");
			expertPool.setDeptName(rs.getString("deptName"));
			expertPool.setCompanyPosition("");
			expertPool.setCompanyPositionName(rs.getString("companyPositionName"));
			expertPool.setRole("");
			expertPool.setExtRole("");
			expertPool.setRate("0");
						
			return expertPool;
		}
	};
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ExpertPool> getNotWorkingExpertList() throws DataAccessException {
		return getJdbcTemplate().query(this.getQuery().toString(), expertPoolScheduleDataRowMapper);
	}

	@Override
	public void update(ExpertPool expertPool) throws DataAccessException {
		try {
			this.expertPoolNotWorkingUpdateQuery.update(expertPool);
		} catch (Exception e) {}
	}
	
	// 산업계 강사 - (대기)산업계 강사
	@SuppressWarnings("unchecked")
	@Override
	public List<ExpertPool> getNotWorkingExpertList_D() throws DataAccessException {
		return getJdbcTemplate().query(this.getQuery_D().toString(), expertPoolScheduleDataRowMapper_D);
	}

	@Override
	public void update_D(ExpertPool expertPool) throws DataAccessException {
		try {
			this.expertPoolNotWorkingUpdateQuery_D.update_D(expertPool);
		} catch (Exception e) {}
	}
	
	// 대학 교수 - (대기)대학 교수
	@SuppressWarnings("unchecked")
	@Override
	public List<ExpertPool> getNotWorkingExpertList_E() throws DataAccessException {
		return getJdbcTemplate().query(this.getQuery_E().toString(), expertPoolScheduleDataRowMapper_E);
	}

	@Override
	public void update_E(ExpertPool expertPool) throws DataAccessException {
		try {
			this.expertPoolNotWorkingUpdateQuery_E.update_E(expertPool);
		} catch (Exception e) {}
	}
}