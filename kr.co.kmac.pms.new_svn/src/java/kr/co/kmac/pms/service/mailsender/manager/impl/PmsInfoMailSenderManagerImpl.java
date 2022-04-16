package kr.co.kmac.pms.service.mailsender.manager.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;

import kr.co.kmac.pms.service.mailsender.manager.PmsInfoMailSenderManager;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.object.MappingSqlQuery;

public class PmsInfoMailSenderManagerImpl extends JdbcDaoSupport implements PmsInfoMailSenderManager  {
	private CCMemberSelectQuery1 ccMemberSelectQuery1; 
	private BCCMemberSelectQuery1 bccMemberSelectQuery1;
	private MobileNoSelectQuery1 mobileNoSelectQuery1;
	
	protected void initDao() throws Exception {
		this.ccMemberSelectQuery1 = new CCMemberSelectQuery1(getDataSource());
		this.bccMemberSelectQuery1 = new BCCMemberSelectQuery1(getDataSource());
		this.mobileNoSelectQuery1 = new MobileNoSelectQuery1(getDataSource());
	}
	
	protected class CCMemberSelectQuery1 extends MappingSqlQuery {
		protected CCMemberSelectQuery1(DataSource ds) {
			super(ds, " SELECT email, name FROM expertPool WHERE ssn in ( "
					+ "		SELECT ssn "
					+ "		FROM projectMember WHERE projectCode = ? "
					+ "		and role='AR' and trainingYN='Y' "
					+ "	) "
					+ " UNION "
					+ " SELECT email, name FROM expertPool WHERE dept = ( "
					+ " 	SELECT runningDivCode "
					+ "		FROM project WHERE projectCode=? "
					+ " ) and enable='1' "
					+ " UNION "
					+ " SELECT email, name FROM expertPool WHERE dept = ( "
					+ "		SELECT key_2 "
					+ "		FROM cmtabledata "
					+ "		WHERE table_name='DEPT_BREAK_DOWN' "
					+ "		and data_1 = ( "
					+ "			SELECT runningDivCode "
					+ "			FROM project WHERE projectCode=? "
					+ "		) "
					+ " ) and enable='1' ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}
		
		protected CCMemberSelectQuery1(DataSource ds, String query) {
			super(ds, query);
		}
		
		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			String emailAddress = rs.getString("email");
			return emailAddress;
		}
	}
	
	protected class BCCMemberSelectQuery1 extends MappingSqlQuery {
		protected BCCMemberSelectQuery1(DataSource ds) {
			super(ds, " SELECT email "
					+ " FROM expertPool "
					+ " WHERE ssn in ("
					+ "		SELECT data_1 " 
					+ "		FROM cmtabledata "
					+ "		WHERE table_name='SYSTEM_ADMIN'"
					+ " )");
			compile();
		}
		
		protected BCCMemberSelectQuery1(DataSource ds, String query) {
			super(ds, query);
		}
		
		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			String emailAddress = rs.getString("email");
			return emailAddress;
		}
	}
	
	protected class MobileNoSelectQuery1 extends MappingSqlQuery {
		protected MobileNoSelectQuery1(DataSource ds) {
			super(ds, "");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}
		
		protected MobileNoSelectQuery1(DataSource ds, String query) {
			super(ds, query);
		}
		
		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			String emailAddress = rs.getString("email");
			return emailAddress;
		}
	}
	
	public List<String> selectCCAddress(String projectCode) throws DataAccessException {
		return this.ccMemberSelectQuery1.execute(new Object[] { projectCode, projectCode, projectCode });
	}
	
	public List<String> selectBCCAddress() throws DataAccessException {
		return this.bccMemberSelectQuery1.execute(new Object[] {});
	}

	@Override
	public List<String> selectMobilePhoneNo(String[] projectCode) throws DataAccessException {	
		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(getJdbcTemplate());
		MapSqlParameterSource param = new MapSqlParameterSource();
		param.addValue("projectCode", Arrays.asList(projectCode));
		List mobileNoList = template.query(
				  " SELECT (SELECT mobileNo FROM ExpertPool WHERE ssn = M.ssn) mobileNo "
				+ " FROM ProjectMember M"
				+ " WHERE M.role = 'PM' AND M.trainingYN = 'Y' "
				+ " AND M.projectCode in (:projectCode) GROUP BY ssn ",
				param,
		    new RowMapper() {
				@Override
				public Object mapRow(ResultSet rs, int arg1) throws SQLException {
					String emailAddress = rs.getString("mobileNo");
					return emailAddress;
				}
			});		
		return mobileNoList;
	}
	
	
}