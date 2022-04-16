/**
 * 
 */
package kr.co.kmac.pms.system.sanction.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import javax.sql.DataSource;

import kr.co.kmac.pms.system.exception.SystemException;
import kr.co.kmac.pms.system.sanction.dao.SanctionTemplateDao;
import kr.co.kmac.pms.system.sanction.data.SanctionTemplate;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.jdbc.object.SqlUpdate;

/**
 * @author Jiwoong Lee
 * 
 */
public class SanctionTemplateDaoImpl extends JdbcDaoSupport implements SanctionTemplateDao {
	private SanctionTemplateInsertQuery sanctionTemplateInsertQuery;
	private SanctionTemplateUpdateQuery sanctionTemplateUpdateQuery;
	private SanctionTemplateSelectQuery sanctionTemplateSelectQuery;
	private SanctionTemplateSelectQuery1 sanctionTemplateSelectQuery1;
	private SanctionTemplateSelectQuery2 sanctionTemplateSelectQuery2;
	private SanctionTemplateSelectQuery3 sanctionTemplateSelectQuery3;
	private SanctionTemplateSelectQuery4 sanctionTemplateSelectQuery4;
	private SanctionTemplateDeleteQuery sanctionTemplateDeleteQuery;

	@Override
	protected void initDao() throws Exception {
		this.sanctionTemplateInsertQuery = new SanctionTemplateInsertQuery(getDataSource());
		this.sanctionTemplateUpdateQuery = new SanctionTemplateUpdateQuery(getDataSource());
		this.sanctionTemplateSelectQuery = new SanctionTemplateSelectQuery(getDataSource());
		this.sanctionTemplateSelectQuery1 = new SanctionTemplateSelectQuery1(getDataSource());
		this.sanctionTemplateSelectQuery2 = new SanctionTemplateSelectQuery2(getDataSource());
		this.sanctionTemplateSelectQuery3 = new SanctionTemplateSelectQuery3(getDataSource());
		this.sanctionTemplateSelectQuery4 = new SanctionTemplateSelectQuery4(getDataSource());
		this.sanctionTemplateDeleteQuery = new SanctionTemplateDeleteQuery(getDataSource());
	}

	protected class SanctionTemplateInsertQuery extends SqlUpdate {
		protected SanctionTemplateInsertQuery(DataSource ds) {
			super(ds,
					" INSERT INTO SanctionTemplate (approvalType, approvalName, hasRefMember, refMemberSsns, refMemberNames, hasAssistMember, assistMemberCnt, "
							+ "                               hasCeo, CeoName, CeoSsn,CeoDept,       hasTeamManager, hasCfo, hasCio,      "
							+ "                               hasSptTeamMng, sptTeamMngName, sptTeamMngSsn, sptTeamMngDept,     hasWholeApprove, "
							+ "								  hasAttach, templateText, useYn, createUser, createdDate, sanctionType, seq, useMobile)    "
							+ " VALUES (?, ?, ?, ?, ?, ?, ?,  ?, ?, ?, ?,   ?, ?, ?,   ?, ?, ?, ?,    ?,   ?, ?, ?, ?, getdate(), ?, ?, ? )");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.BOOLEAN));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.BOOLEAN));
			declareParameter(new SqlParameter(Types.INTEGER));

			declareParameter(new SqlParameter(Types.BOOLEAN));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));

			declareParameter(new SqlParameter(Types.BOOLEAN));
			declareParameter(new SqlParameter(Types.BOOLEAN));
			declareParameter(new SqlParameter(Types.BOOLEAN));

			declareParameter(new SqlParameter(Types.BOOLEAN));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));

			declareParameter(new SqlParameter(Types.BOOLEAN));

			declareParameter(new SqlParameter(Types.BOOLEAN));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.BOOLEAN));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.BOOLEAN));
			compile();
			setRequiredRowsAffected(1);
		}

		protected int insert(SanctionTemplate sanctionTemplate) throws DataAccessException {
			return this.update(new Object[] { sanctionTemplate.getApprovalType(), sanctionTemplate.getApprovalName(),
					sanctionTemplate.isHasRefMember(), sanctionTemplate.getRefMemberSsns(), sanctionTemplate.getRefMemberNames(),
					sanctionTemplate.isHasAssistMember(), sanctionTemplate.getAssistMemberCnt(), sanctionTemplate.isHasCeo(),
					sanctionTemplate.getCeoName(), sanctionTemplate.getCeoSsn(), sanctionTemplate.getCeoDept(), sanctionTemplate.isHasTeamManager(),
					sanctionTemplate.isHasCfo(), sanctionTemplate.isHasCio(), sanctionTemplate.isHasSptTeamMng(),
					sanctionTemplate.getSptTeamMngName(), sanctionTemplate.getSptTeamMngSsn(), sanctionTemplate.getSptTeamMngDept(),
					sanctionTemplate.isHasWholeApprove(), sanctionTemplate.isHasAttach(), sanctionTemplate.getTemplateText(),
					sanctionTemplate.isUseYn(), sanctionTemplate.getCreateUser(), sanctionTemplate.getSanctionType(), sanctionTemplate.getSeq(),
					sanctionTemplate.isUseMobile() });
		}
	}

	protected class SanctionTemplateUpdateQuery extends SqlUpdate {
		protected SanctionTemplateUpdateQuery(DataSource ds) {
			super(
					ds,
					" UPDATE SanctionTemplate SET	approvalName=?, hasRefMember=?, refMemberSsns=?, refMemberNames=?, hasAssistMember=?, assistMemberCnt=?,      "
							+ "                     hasCeo=?, CeoName=?, CeoSsn=?, CeoDept=?,                          "
							+ "                     hasTeamManager=?, hasCfo=?, hasCio=?,                              "
							+ "                     hasSptTeamMng=?, sptTeamMngName=?, sptTeamMngSsn=?, sptTeamMngDept=?, "
							+ "					    hasWholeApprove=?, hasSptTeamMngWholeApprove=?, hasAttach=?, templateText=?, useYn=?, updateUser=?, sanctionType=?, useMobile=?, updatedDate=getdate()  "
							+ " WHERE  approvalType=?                                                                                  ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.BOOLEAN));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.BOOLEAN));
			declareParameter(new SqlParameter(Types.INTEGER));

			declareParameter(new SqlParameter(Types.BOOLEAN));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));

			declareParameter(new SqlParameter(Types.BOOLEAN));
			declareParameter(new SqlParameter(Types.BOOLEAN));
			declareParameter(new SqlParameter(Types.BOOLEAN));

			declareParameter(new SqlParameter(Types.BOOLEAN));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));

			declareParameter(new SqlParameter(Types.BOOLEAN));
			declareParameter(new SqlParameter(Types.BOOLEAN));
			declareParameter(new SqlParameter(Types.BOOLEAN));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.BOOLEAN));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.BOOLEAN));

			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
			setRequiredRowsAffected(1);
		}

		protected int update(SanctionTemplate sanctionTemplate) throws DataAccessException {
			return this.update(new Object[] { sanctionTemplate.getApprovalName(), sanctionTemplate.isHasRefMember(),
					sanctionTemplate.getRefMemberSsns(), sanctionTemplate.getRefMemberNames(), sanctionTemplate.isHasAssistMember(),
					sanctionTemplate.getAssistMemberCnt(), sanctionTemplate.isHasCeo(), sanctionTemplate.getCeoName(), sanctionTemplate.getCeoSsn(),
					sanctionTemplate.getCeoDept(), sanctionTemplate.isHasTeamManager(), sanctionTemplate.isHasCfo(), sanctionTemplate.isHasCio(),
					sanctionTemplate.isHasSptTeamMng(), sanctionTemplate.getSptTeamMngName(), sanctionTemplate.getSptTeamMngSsn(),
					sanctionTemplate.getSptTeamMngDept(), sanctionTemplate.isHasWholeApprove(),  sanctionTemplate.isHasSptTeamMngWholeApprove(), sanctionTemplate.isHasAttach(),
					sanctionTemplate.getTemplateText(), sanctionTemplate.isUseYn(), sanctionTemplate.getUpdateUser(),
					sanctionTemplate.getSanctionType(), sanctionTemplate.isUseMobile(), sanctionTemplate.getApprovalType() });
		}
	}

	protected class SanctionTemplateDeleteQuery extends SqlUpdate {
		protected SanctionTemplateDeleteQuery(DataSource ds) {
			super(ds, " DELETE FROM SanctionTemplate WHERE approvalType=? ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
			setRequiredRowsAffected(1);
		}

		protected int delete(String approvalType) throws DataAccessException {
			return this.update(new Object[] { approvalType });
		}
	}

	protected class SanctionTemplateSelectQuery extends MappingSqlQuery {
		protected SanctionTemplateSelectQuery(DataSource ds) {
			super(ds, "	SELECT 	* FROM SanctionTemplate ORDER BY seq ");
			compile();
		}

		protected SanctionTemplateSelectQuery(DataSource ds, String query) {
			super(ds, query);
		}

		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			SanctionTemplate sanctionTemplate = new SanctionTemplate();
			sanctionTemplate.setApprovalType(rs.getString("approvalType"));
			sanctionTemplate.setApprovalName(rs.getString("approvalName"));
			sanctionTemplate.setHasRefMember(rs.getBoolean("hasRefMember"));
			sanctionTemplate.setRefMemberSsns(rs.getString("refMemberSsns"));
			sanctionTemplate.setRefMemberNames(rs.getString("refMemberNames"));
			sanctionTemplate.setHasAssistMember(rs.getBoolean("hasAssistMember"));
			sanctionTemplate.setAssistMemberCnt(rs.getInt("assistMemberCnt"));
			sanctionTemplate.setHasSptTeamMng(rs.getBoolean("hasSptTeamMng"));
			sanctionTemplate.setSptTeamMngName(rs.getString("sptTeamMngName"));
			sanctionTemplate.setSptTeamMngSsn(rs.getString("sptTeamMngSsn"));
			sanctionTemplate.setSptTeamMngDept(rs.getString("sptTeamMngDept"));
			sanctionTemplate.setHasCeo(rs.getBoolean("hasCeo"));
			sanctionTemplate.setCeoName(rs.getString("CeoName"));
			sanctionTemplate.setCeoSsn(rs.getString("CeoSsn"));
			sanctionTemplate.setCeoDept(rs.getString("CeoDept"));
			sanctionTemplate.setHasTeamManager(rs.getBoolean("hasTeamManager"));
			sanctionTemplate.setHasCfo(rs.getBoolean("hasCfo"));
			sanctionTemplate.setHasCio(rs.getBoolean("hasCio"));
			sanctionTemplate.setHasWholeApprove(rs.getBoolean("hasWholeApprove"));
			sanctionTemplate.setHasSptTeamMngWholeApprove(rs.getBoolean("hasSptTeamMngWholeApprove"));
			sanctionTemplate.setHasAttach(rs.getBoolean("hasAttach"));
			sanctionTemplate.setTemplateText(rs.getString("templateText"));
			sanctionTemplate.setUseYn(rs.getBoolean("useYn"));
			sanctionTemplate.setSanctionType(rs.getString("sanctionType"));
			sanctionTemplate.setCreateUser(rs.getString("createUser"));
			sanctionTemplate.setCreatedDate(rs.getDate("createdDate"));
			sanctionTemplate.setUpdateUser(rs.getString("updateUser"));
			sanctionTemplate.setUpdatedDate(rs.getDate("updatedDate"));
			sanctionTemplate.setUseMobile(rs.getBoolean("useMobile"));
			return sanctionTemplate;
		}
	}

	protected class SanctionTemplateSelectQuery1 extends SanctionTemplateSelectQuery {
		protected SanctionTemplateSelectQuery1(DataSource ds) {
			super(ds, "	SELECT 	* FROM SanctionTemplate WHERE approvalType=?");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}
	}

	protected class SanctionTemplateSelectQuery2 extends SanctionTemplateSelectQuery {
		protected SanctionTemplateSelectQuery2(DataSource ds) {
			super(ds, "	SELECT 	* FROM SanctionTemplate WHERE useYn=?");
			declareParameter(new SqlParameter(Types.BOOLEAN));
			compile();
		}
	}

	protected class SanctionTemplateSelectQuery3 extends SanctionTemplateSelectQuery {
		protected SanctionTemplateSelectQuery3(DataSource ds) {
			super(ds, "	SELECT 	* FROM SanctionTemplate WHERE useYn=? and sanctionType=? ORDER BY seq");
			declareParameter(new SqlParameter(Types.BOOLEAN));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}
	}

	protected class SanctionTemplateSelectQuery4 extends SanctionTemplateSelectQuery {
		protected SanctionTemplateSelectQuery4(DataSource ds) {
			super(ds, "	SELECT 	* FROM SanctionTemplate WHERE useYn=? and useMobile=? ORDER BY seq");
			declareParameter(new SqlParameter(Types.BOOLEAN));
			declareParameter(new SqlParameter(Types.BOOLEAN));
			compile();
		}
	}

	@Override
	public void createSanctionTemplate(SanctionTemplate sanctionTemplate) throws DataAccessException {
		int maxSeq = getJdbcTemplate().queryForInt("SELECT ISNULL(MAX(seq),0)+1 FROM SanctionTemplate  WITH(NOLOCK) ");
		sanctionTemplate.setSeq(Integer.toString(maxSeq));
		this.sanctionTemplateInsertQuery.insert(sanctionTemplate);
	}

	@Override
	public void deleteSanctionTemplate(String approvalType) throws DataAccessException {
		this.sanctionTemplateDeleteQuery.delete(approvalType);
	}

	@Override
	public SanctionTemplate getSanctionTemplate(String approvalType) throws DataAccessException {
		return (SanctionTemplate) this.sanctionTemplateSelectQuery1.findObject(new Object[] { approvalType });
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SanctionTemplate> getSanctionTemplate() throws DataAccessException {
		return this.sanctionTemplateSelectQuery.execute();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SanctionTemplate> getSanctionTemplate(boolean useYn) throws DataAccessException {
		return this.sanctionTemplateSelectQuery2.execute(new Object[] { useYn });
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SanctionTemplate> getSanctionTemplate(boolean useYn, boolean useMobile) throws SystemException {
		return this.sanctionTemplateSelectQuery4.execute(new Object[] { useYn, useMobile });
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SanctionTemplate> getSanctionTemplate(boolean useYn, String sanctionType) throws DataAccessException {
		return this.sanctionTemplateSelectQuery3.execute(new Object[] { useYn, sanctionType });
	}

	@Override
	public void updateSanctionTemplate(SanctionTemplate sanctionTemplate) throws DataAccessException {
		this.sanctionTemplateUpdateQuery.update(sanctionTemplate);
	}

}
