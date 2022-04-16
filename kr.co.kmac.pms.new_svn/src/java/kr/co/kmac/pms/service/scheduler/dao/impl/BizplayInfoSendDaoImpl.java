package kr.co.kmac.pms.service.scheduler.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import javax.sql.DataSource;

import kr.co.kmac.pms.service.scheduler.dao.BizplayInfoSendDao;
import kr.co.kmac.pms.service.scheduler.data.bizplay.dvsn.BizplayDeptInfo;
import kr.co.kmac.pms.service.scheduler.data.bizplay.empl.BizplayExpertPoolInfo;
import kr.co.kmac.pms.service.scheduler.data.bizplay.pjt.BizplayProjectInfo;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.object.SqlUpdate;

public class BizplayInfoSendDaoImpl extends JdbcDaoSupport implements BizplayInfoSendDao {
	private static final String PROJECT_INFO_QUERY = "		select * from project_Bizplay  		where 1=1 		";
	private static final String EXPERTPOOL_INFO_QUERY = "	select * from ExpertPool_Bizplay	where 1=1 		";
	private static final String DEPT_INFO_QUERY = "			select * from smgroup_Bizplay 		where 1=1		";
	private InsertSendHsitoryQuery insertSendHsitoryQuery;
	private RowMapper projectRowMapper = new RowMapper() {
		public Object mapRow(ResultSet rs, int currentRowNum) throws SQLException {
			BizplayProjectInfo bizplayProjectInfo = new BizplayProjectInfo();
			bizplayProjectInfo.setProjectCode(rs.getString("projectCode"));
			bizplayProjectInfo.setProjectName(rs.getString("projectName"));
			bizplayProjectInfo.setProjectState(rs.getString("projectState"));
			bizplayProjectInfo.setCompanyCode(rs.getString("companyCode"));
			bizplayProjectInfo.setBsnnNo(rs.getString("bsnn_No"));
			return bizplayProjectInfo;
		}
	};
	private RowMapper expertPoolRowMapper = new RowMapper() {
		public Object mapRow(ResultSet rs, int currentRowNum) throws SQLException {
			BizplayExpertPoolInfo bizplayExpertPoolInfo = new BizplayExpertPoolInfo();
			bizplayExpertPoolInfo.setName(rs.getString("name"));
			bizplayExpertPoolInfo.setEnable(rs.getString("enable"));
			bizplayExpertPoolInfo.setMobileNo(rs.getString("mobileNo"));
			bizplayExpertPoolInfo.setEmail(rs.getString("email"));
			bizplayExpertPoolInfo.setEmplNo(rs.getString("emplNo"));
			bizplayExpertPoolInfo.setDept(rs.getString("dept"));
			bizplayExpertPoolInfo.setCompnayCode(rs.getString("companyCode"));
			bizplayExpertPoolInfo.setBsnnNo(rs.getString("bsnn_no"));
			return bizplayExpertPoolInfo;
		}
	};
	private RowMapper deptRowMapper = new RowMapper() {
		public Object mapRow(ResultSet rs, int currentRowNum) throws SQLException {
			BizplayDeptInfo deptInfo = new BizplayDeptInfo();
			deptInfo.setBsnnNo(rs.getString("bsnn_No"));
			deptInfo.setDept(rs.getString("dept"));
			deptInfo.setDeptName(rs.getString("deptName"));
			deptInfo.setParentId(rs.getString("parentid"));
			deptInfo.setSeq(rs.getString("seq"));
			deptInfo.setEnabled(rs.getString("enabled"));
			deptInfo.setCompanyCode(rs.getString("companyCode"));
			return deptInfo;
		}
	};

	protected class InsertSendHsitoryQuery extends SqlUpdate {
		protected InsertSendHsitoryQuery(DataSource ds) {
			super(ds, "insert into BizplayInfoSendHistory values (getDate(), ?, ?, ?, ?)");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
			setRequiredRowsAffected(1);
		}

		protected int insert(String endPoint, String sendTxt, String ResultTxt, String exceptionStr) {
			return super.update(new Object[] { endPoint, sendTxt, ResultTxt, exceptionStr });
		}
	}

	protected void initDao() throws Exception {
		this.insertSendHsitoryQuery = new InsertSendHsitoryQuery(getDataSource());
	}

	@Override
	public boolean insertHistory(String endPoint, String sendTxt, String ResultTxt, String exceptionStr) throws DataAccessException {
		return this.insertSendHsitoryQuery.insert(endPoint, sendTxt, ResultTxt, exceptionStr) > 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BizplayProjectInfo> projectInfoList() throws DataAccessException {
		StringBuffer query = new StringBuffer();
		query.append(PROJECT_INFO_QUERY);
		query.append(" and CONVERT(CHAR(8), modifyDate, 112)  >=  CONVERT(CHAR(8), getdate()-3, 112)	");
		query.append(" order by projectCode	");

		return getJdbcTemplate().query(query.toString(), projectRowMapper);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BizplayProjectInfo> projectInfoList(String CompanyCode) throws DataAccessException {
		StringBuffer query = new StringBuffer();
		query.append(PROJECT_INFO_QUERY);
		query.append(" and CONVERT(CHAR(8), modifyDate, 112)  >=  CONVERT(CHAR(8), getdate()-3, 112)	");
		if (CompanyCode != null && !"".equals(CompanyCode)) {
			query.append(" and companyCode = '" + CompanyCode + "'	");
		}
		query.append(" order by projectCode	");

		return getJdbcTemplate().query(query.toString(), projectRowMapper);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BizplayProjectInfo> projectInfoListAll(String CompanyCode) throws DataAccessException {
		StringBuffer query = new StringBuffer();
		query.append(PROJECT_INFO_QUERY);
		query.append(" and   CONVERT(CHAR(8), modifyDate, 112)  >= CONVERT(CHAR(8), getdate()-365, 112)	");
		if (CompanyCode != null && !"".equals(CompanyCode)) {
			query.append(" and companyCode = '" + CompanyCode + "'	");
		}
		query.append(" order by projectCode	");
		
		return getJdbcTemplate().query(query.toString(), projectRowMapper);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BizplayExpertPoolInfo> expertPoolList() throws DataAccessException {
		StringBuffer query = new StringBuffer();
		query.append(EXPERTPOOL_INFO_QUERY);
		query.append(" order by emplNo	");

		return getJdbcTemplate().query(query.toString(), expertPoolRowMapper);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BizplayExpertPoolInfo> expertPoolList(String CompanyCode) throws DataAccessException {
		StringBuffer query = new StringBuffer();
		query.append(EXPERTPOOL_INFO_QUERY);
		if (CompanyCode != null && !"".equals(CompanyCode)) {
			query.append(" and companyCode = '" + CompanyCode + "' and emplno is not null	");
		}
		query.append(" order by emplNo	");

		return getJdbcTemplate().query(query.toString(), expertPoolRowMapper);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BizplayDeptInfo> deptList() throws DataAccessException {
		StringBuffer query = new StringBuffer();
		query.append(DEPT_INFO_QUERY);
		query.append(" order by dept	");

		return getJdbcTemplate().query(query.toString(), deptRowMapper);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BizplayDeptInfo> deptList(String CompanyCode) throws DataAccessException {
		StringBuffer query = new StringBuffer();
		query.append(DEPT_INFO_QUERY);
		if (CompanyCode != null && !"".equals(CompanyCode)) {
			query.append(" and companyCode = '" + CompanyCode + "'	");
		}
		query.append(" order by dept	");

		return getJdbcTemplate().query(query.toString(), deptRowMapper);
	}

}
