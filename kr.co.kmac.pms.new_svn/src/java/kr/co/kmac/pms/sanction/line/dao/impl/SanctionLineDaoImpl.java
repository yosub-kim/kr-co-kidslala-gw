package kr.co.kmac.pms.sanction.line.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import javax.sql.DataSource;

import kr.co.kmac.pms.sanction.line.dao.SanctionLineDao;
import kr.co.kmac.pms.sanction.line.data.SanctionLine;
import kr.co.kmac.pms.sanction.line.form.SanctionLineForm;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.jdbc.object.SqlUpdate;

public class SanctionLineDaoImpl extends JdbcDaoSupport implements SanctionLineDao {
	private SanctionLineInsertQuery sanctionLineInsertQuery;
	private SanctionLineUpdateQeury sanctionLineUpdateQeury;
	private SanctionLineRetrieveQuery sanctionLineRetrieveQuery;
	private SanctionLineDeleteQuery sanctionLineDeleteQuery;

	protected void initDao() {
		this.sanctionLineInsertQuery = new SanctionLineInsertQuery(getDataSource());
		this.sanctionLineRetrieveQuery = new SanctionLineRetrieveQuery(getDataSource());
		this.sanctionLineUpdateQeury = new SanctionLineUpdateQeury(getDataSource());
		this.sanctionLineDeleteQuery = new SanctionLineDeleteQuery(getDataSource());
	}

	protected class SanctionLineInsertQuery extends SqlUpdate {
		protected SanctionLineInsertQuery(DataSource ds) {
			super(ds, " INSERT INTO SanctionLine(id, registerSsn, 	registerDept, registerName,                "
					+ "                         	teamManagerSsn, teamManagerDept, teamManagerName,          "
					+ "                         	cfoSsn, cfoDept, cfoName,                                  "
					+ "                         	cioSsn, cioDept, cioName,                                  "
					+ "                         	ceoSsn, ceoDept, ceoName,                                  "
					+ "                         	assistant1Ssn, assistant1Dept, assistant1Name,             "
					+ "                         	assistant2Ssn, assistant2Dept, assistant2Name, modifyDate) "
					+ " VALUES(?, ?, ?, ?,   ?, ?, ?,   ?, ?, ?,   ?, ?, ?,   ?, ?, ?,   ?, ?, ?,   ?, ?, ?, getDate())");
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
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));

			compile();
			setRequiredRowsAffected(1);
		}

		protected int insert(SanctionLine sanctionLine) {
			return this.update(new Object[] { sanctionLine.getId(), sanctionLine.getRegisterSsn(), sanctionLine.getRegisterDept(),
					sanctionLine.getRegisterName(), sanctionLine.getTeamManagerSsn(), sanctionLine.getTeamManagerDept(),
					sanctionLine.getTeamManagerName(), 
					sanctionLine.getCfoSsn(), sanctionLine.getCfoDept(), sanctionLine.getCfoName(),
					sanctionLine.getCioSsn(), sanctionLine.getCioDept(), sanctionLine.getCioName(),
					sanctionLine.getCeoSsn(), sanctionLine.getCeoDept(), sanctionLine.getCeoName(), sanctionLine.getAssistant1Ssn(),
					sanctionLine.getAssistant1Dept(), sanctionLine.getAssistant1Name(), sanctionLine.getAssistant2Ssn(),
					sanctionLine.getAssistant2Dept(), sanctionLine.getAssistant2Name() });
		}
	}

	protected class SanctionLineUpdateQeury extends SqlUpdate {
		protected SanctionLineUpdateQeury(DataSource ds) {
			super(ds, " UPDATE   SanctionLine SET registerSsn=?, registerDept=?, registerName=?,         "
					+ "                          teamManagerSsn=?, teamManagerDept=?, teamManagerName=?, "
					+ "                          cfoSsn=?, cfoDept=?, cfoName=?,                         "
					+ "                          cioSsn=?, cioDept=?, cioName=?,                         "
					+ "                          ceoSsn=?, ceoDept=?, ceoName=?,                         "
					+ "                          assistant1Ssn=?, assistant1Dept=?, assistant1Name=?,    "
					+ "                          assistant2Ssn=?, assistant2Dept=?, assistant2Name=?, modifyDate=getDate()     "
					+ " WHERE  id=?                           ");
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
			declareParameter(new SqlParameter(Types.VARCHAR));

			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected int update(SanctionLine sanctionLine) {
			return this.update(new Object[] { sanctionLine.getRegisterSsn(), sanctionLine.getRegisterDept(), sanctionLine.getRegisterName(),
					sanctionLine.getTeamManagerSsn(), sanctionLine.getTeamManagerDept(), sanctionLine.getTeamManagerName(), 
					sanctionLine.getCfoSsn(), sanctionLine.getCfoDept(), sanctionLine.getCfoName(), 
					sanctionLine.getCioSsn(), sanctionLine.getCioDept(), sanctionLine.getCioName(), 
					sanctionLine.getCeoSsn(), sanctionLine.getCeoDept(), sanctionLine.getCeoName(), 
					sanctionLine.getAssistant1Ssn(), sanctionLine.getAssistant1Dept(), sanctionLine.getAssistant1Name(),
					sanctionLine.getAssistant2Ssn(), sanctionLine.getAssistant2Dept(), sanctionLine.getAssistant2Name(), sanctionLine.getId() });
		}
	}

	protected class SanctionLineDeleteQuery extends SqlUpdate {
		protected SanctionLineDeleteQuery(DataSource ds) {
			super(ds, " DELETE FROM SanctionLine WHERE  id=?                           ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected int delete(String id) {
			return this.update(new Object[] { id });
		}
	}

	protected class SanctionLineRetrieveQuery extends MappingSqlQuery {
		protected SanctionLineRetrieveQuery(DataSource ds, String query) {
			super(ds, query);
		}

		protected SanctionLineRetrieveQuery(DataSource ds) {
			super(ds, " SELECT * FROM SanctionLine WHERE id=? ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			SanctionLine sanctionLine = new SanctionLineForm();
			sanctionLine.setId(rs.getString("Id"));
			sanctionLine.setRegisterSsn(rs.getString("registerSsn"));
			sanctionLine.setRegisterDept(rs.getString("registerDept"));
			sanctionLine.setRegisterName(rs.getString("registerName"));
			sanctionLine.setTeamManagerSsn(rs.getString("teamManagerSsn"));
			sanctionLine.setTeamManagerDept(rs.getString("teamManagerDept"));
			sanctionLine.setTeamManagerName(rs.getString("teamManagerName"));
			sanctionLine.setCfoSsn(rs.getString("cfoSsn"));
			sanctionLine.setCfoDept(rs.getString("cfoDept"));
			sanctionLine.setCfoName(rs.getString("cfoName"));
			sanctionLine.setCioSsn(rs.getString("cioSsn"));
			sanctionLine.setCioDept(rs.getString("cioDept"));
			sanctionLine.setCioName(rs.getString("cioName"));
			sanctionLine.setCeoSsn(rs.getString("ceoSsn"));
			sanctionLine.setCeoDept(rs.getString("ceoDept"));
			sanctionLine.setCeoName(rs.getString("ceoName"));
			sanctionLine.setAssistant1Ssn(rs.getString("assistant1Ssn"));
			sanctionLine.setAssistant1Dept(rs.getString("assistant1Dept"));
			sanctionLine.setAssistant1Name(rs.getString("assistant1Name"));
			sanctionLine.setAssistant2Ssn(rs.getString("assistant2Ssn"));
			sanctionLine.setAssistant2Dept(rs.getString("assistant2Dept"));
			sanctionLine.setAssistant2Name(rs.getString("assistant2Name"));
			return sanctionLine;
		}

	}

	@Override
	public void delete(String id) throws DataAccessException {
		this.sanctionLineDeleteQuery.delete(id);
	}

	@Override
	public void insert(SanctionLine sanctionLine) throws DataAccessException {
		this.sanctionLineInsertQuery.insert(sanctionLine);
	}

	@Override
	public SanctionLine select(String id) throws DataAccessException {
		return (SanctionLine) sanctionLineRetrieveQuery.findObject(new Object[] { id });
	}

	@Override
	public void update(SanctionLine sanctionLine) throws DataAccessException {
		this.sanctionLineUpdateQeury.update(sanctionLine);
	}

}
