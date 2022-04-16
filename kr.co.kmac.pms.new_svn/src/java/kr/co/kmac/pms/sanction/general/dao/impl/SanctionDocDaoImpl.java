/*
 * $Id: SanctionDocDaoImpl.java,v 1.23 2017/12/13 07:38:41 cvs Exp $
 * created by    : jiwoongLee
 * creation-date : 2006. 3. 25.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.sanction.general.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import javax.sql.DataSource;

import kr.co.kmac.pms.project.exception.ProjectException;
import kr.co.kmac.pms.sanction.exception.SanctionException;
import kr.co.kmac.pms.sanction.general.dao.SanctionDocDao;
import kr.co.kmac.pms.sanction.general.data.SanctionDoc;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.jdbc.object.SqlUpdate;

/**
 * 운영, 시작, 일반 품의 DAO
 * 
 * @author jiwoongLee
 * @version $Id: SanctionDocDaoImpl.java,v 1.23 2017/12/13 07:38:41 cvs Exp $
 */
public class SanctionDocDaoImpl extends JdbcDaoSupport implements SanctionDocDao {

	private SanctionDocInsertQuery sanctionDocInsertQuery;
	private SanctionDocUpdateQeury sanctionDocUpdateQeury;
	private SanctionDocRetrieveQuery sanctionDocRetrieveQuery;
	private SanctionDocRetrieveQuery1 sanctionDocRetrieveQuery1;
	private SanctionDocRetrieveQuery2 sanctionDocRetrieveQuery2;
	private SanctionRunningDocRetrieveQuery sanctionRunningDocRetrieveQuery;
	private SanctionDocDeleteQuery sanctionDocDeleteQuery;
	private SanctionDocInsertLogQuery sanctionDocInsertLogQuery;
	private ErpUpdateQuery erpUpdateQuery; 
	private UpdateEduStateQuery updateEduStateQuery;
	private UpdateEduStateDisabledQuery updateEduStateDisabledQuery;
	private UpdateEduStateCancle updateEduStateCancle; 
	private UpdateEduStateAgain updateEduStateAgain;
	private UpdateEduStateResanction updateEduStateResanction;
	private RestoreEduStateResanction restoreEduStateResanction;
	
	private DataSource erpDataSource;// 회계 테이터 베이스 접근
	private DataSource intranetDataSource; // 인트라넷 데이터 베이스 접근
	private DataSource intranetEduDataSource; // 인트라넷 교육 데이터 베이스 접근

	public DataSource getIntranetEduDataSource() {
		return intranetEduDataSource;
	}

	public void setIntranetEduDataSource(DataSource intranetEduDataSource) {
		this.intranetEduDataSource = intranetEduDataSource;
	}

	public DataSource getIntranetDataSource() {
		return intranetDataSource;
	}

	public void setIntranetDataSource(DataSource intranetDataSource) {
		this.intranetDataSource = intranetDataSource;
	}

	public DataSource getErpDataSource() {
		return this.erpDataSource;
	}

	public void setErpDataSource(DataSource erpDataSource) {
		this.erpDataSource = erpDataSource;
	}

	protected void initDao() {
		this.sanctionDocInsertQuery = new SanctionDocInsertQuery(getDataSource());
		this.sanctionDocRetrieveQuery = new SanctionDocRetrieveQuery(getDataSource());
		this.sanctionDocRetrieveQuery1 = new SanctionDocRetrieveQuery1(getDataSource());
		this.sanctionDocRetrieveQuery2 = new SanctionDocRetrieveQuery2(getDataSource());
		this.sanctionRunningDocRetrieveQuery = new SanctionRunningDocRetrieveQuery(getDataSource());
		this.sanctionDocUpdateQeury = new SanctionDocUpdateQeury(getDataSource());
		this.sanctionDocDeleteQuery = new SanctionDocDeleteQuery(getDataSource());
		this.erpUpdateQuery = new ErpUpdateQuery(getErpDataSource());
		this.updateEduStateQuery = new UpdateEduStateQuery(getIntranetEduDataSource());
		this.updateEduStateDisabledQuery = new UpdateEduStateDisabledQuery(getIntranetEduDataSource());
		this.updateEduStateCancle = new UpdateEduStateCancle(getIntranetEduDataSource());
		this.updateEduStateAgain = new UpdateEduStateAgain(getIntranetEduDataSource());
		this.updateEduStateResanction = new UpdateEduStateResanction(getIntranetEduDataSource());
		this.restoreEduStateResanction = new RestoreEduStateResanction(getIntranetEduDataSource());
		this.sanctionDocInsertLogQuery = new SanctionDocInsertLogQuery(getDataSource());
	}

	protected class SanctionDocInsertQuery extends SqlUpdate {
		protected SanctionDocInsertQuery(DataSource ds) {
			super(ds, " INSERT INTO SanctionDoc(seq, projectCode, approvalType,                         "
					+ "                         registerSsn, 	registerDept, registerName, registerDate,             "
					+ "                         teamManagerSsn, teamManagerDept, teamManagerName, teamManagerDate,       "
					+ "                         cfoSsn, cfoDept, cfoName, cfoDate,                               "
					+ "                         cioSsn, cioDept, cioName, cioDate,                               "
					+ "                         ceoSsn, ceoDept, ceoName, ceoDate,                               "
					+ "                         assistant1Ssn, assistant1Dept, assistant1Name, assistant1Date,          "
					+ "                         assistant2Ssn, assistant2Dept, assistant2Name, assistant2Date,          "
					+ "                         assistant3Ssn, assistant3Dept, assistant3Name, assistant3Date,          "
					+ "                         assistant4Ssn, assistant4Dept, assistant4Name, assistant4Date,          "
					+ "                         subject, content, rejectReason, isWholeApproval, state, reject, workType, eduCourseCode, createrDate) "
					+ " VALUES(?, ?, ?,   ?, ?, ?, ?,  ?, ?, ?, ?,  ?, ?, ?, ?,   ?, ?, ?, ?,   ?, ?, ?, ?,   "
					+ "        ?, ?, ?, ?,   ?, ?, ?, ?,   ?, ?, ?, ?,  ?, ?, ?, ?,   ?, ?, ?,   ?, ?, 'N', ?, ?, ?)");
			declareParameter(new SqlParameter(Types.INTEGER));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));

			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.DATE));

			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.DATE));

			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.DATE));

			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.DATE));

			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.DATE));

			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.DATE));

			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.DATE));

			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.DATE));

			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.DATE));

			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.DATE));
			
			compile();
			setRequiredRowsAffected(1);
		}

		protected int insert(SanctionDoc sanctionDoc) {
			return this.update(new Object[] { sanctionDoc.getSeq(), sanctionDoc.getProjectCode(), sanctionDoc.getApprovalType(),
					sanctionDoc.getRegisterSsn(), sanctionDoc.getRegisterDept(), sanctionDoc.getRegisterName(), sanctionDoc.getRegisterDate(),
					sanctionDoc.getTeamManagerSsn(), sanctionDoc.getTeamManagerDept(), sanctionDoc.getTeamManagerName(),
					sanctionDoc.getTeamManagerDate(), sanctionDoc.getCfoSsn(), sanctionDoc.getCfoDept(), sanctionDoc.getCfoName(),
					sanctionDoc.getCfoDate(), sanctionDoc.getCioSsn(), sanctionDoc.getCioDept(), sanctionDoc.getCioName(), sanctionDoc.getCioDate(),
					sanctionDoc.getCeoSsn(), sanctionDoc.getCeoDept(), sanctionDoc.getCeoName(), sanctionDoc.getCeoDate(),
					sanctionDoc.getAssistant1Ssn(), sanctionDoc.getAssistant1Dept(), sanctionDoc.getAssistant1Name(),
					sanctionDoc.getAssistant1Date(), sanctionDoc.getAssistant2Ssn(), sanctionDoc.getAssistant2Dept(),
					sanctionDoc.getAssistant2Name(), sanctionDoc.getAssistant2Date(), sanctionDoc.getAssistant3Ssn(),
					sanctionDoc.getAssistant3Dept(), sanctionDoc.getAssistant3Name(), sanctionDoc.getAssistant3Date(),
					sanctionDoc.getFunc(), sanctionDoc.getProjectCodeBp(), sanctionDoc.getAssistant4Name(),
					sanctionDoc.getAssistant4Date(), sanctionDoc.getSubject(), sanctionDoc.getContent(), sanctionDoc.getRejectReason(),
					sanctionDoc.getIsWholeApproval(), sanctionDoc.getState(), sanctionDoc.getWorkType(), sanctionDoc.getEduCourseCode(), sanctionDoc.getRegisterDate()});
		}
	}
	
	protected class SanctionDocInsertLogQuery extends SqlUpdate {
		protected SanctionDocInsertLogQuery(DataSource ds) {
			super(ds, " INSERT INTO SanctionDocLog(viewSsn, viewDate, sanctionDocSsn,   "
					+ "                         sanctionDocDate, seq) "
					+ " VALUES(?, getDate(), ?,   ?, ? )");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));

			declareParameter(new SqlParameter(Types.DATE));
			declareParameter(new SqlParameter(Types.INTEGER));
			
			
			compile();
			setRequiredRowsAffected(1);
		}

		protected int insert(SanctionDoc sanctionDoc) {
			return this.update(new Object[] { sanctionDoc.getViewSsn(), sanctionDoc.getSanctionDocSsn(), sanctionDoc.getSanctionDocDate(), sanctionDoc.getSeq()});
		}
	}
	
	

	protected class ErpUpdateQuery extends SqlUpdate {
		protected ErpUpdateQuery(DataSource ds) {
			super(ds, " UPDATE A_PROJM SET STATE = 'Y', CeoDate = TO_CHAR( SYSDATE, 'YYYYMMDD') WHERE PROJID = ? ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected int updateErp(String projectCode) {
			return this.update(new Object[] { projectCode });
		}
	}
	
	protected class UpdateEduStateQuery extends SqlUpdate {
		protected UpdateEduStateQuery(DataSource ds) {
			super(ds, " Update newKmac.dbo.Course set  c_CeoConfirm='Y' where c_ProjectCode= ? ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}
		
		protected int updateEduState(String projectCode) {
			return this.update(new Object[] { projectCode });
		}
	}
	
	protected class UpdateEduStateDisabledQuery extends SqlUpdate {
		protected UpdateEduStateDisabledQuery(DataSource ds) {
			super(ds, " update newKmac.dbo.coursecheck						"
					+ " set chkval = 'N', chkdate = GETDATE()	"
					+ " where oc_id in (						"
					+ " 	select oc_ID from newKmac.dbo.OpenCourse where c_ID  in (	"
					+ "				select c_ID from newKmac.dbo.Course where c_ProjectCode = ?	"
					+ " 	)	"
					+ " )		");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}
		
		protected int updateEduStateDisabled(String projectCode) {
			return this.update(new Object[] { projectCode });
		}
	}
	
	protected class UpdateEduStateCancle extends SqlUpdate {
		protected UpdateEduStateCancle(DataSource ds) {
			super(ds, " Update newKmac.dbo.OpenCourse set oc_PSanction='D', oc_FlagView='Y', oc_FlagClose='N', oc_CancelYN = 'Y' where oc_ID = ? ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}
		
		protected int updateEdu(String oc_code) {
			return this.update(new Object[] { oc_code });
		}
	}
	
	protected class UpdateEduStateAgain extends SqlUpdate {
		protected UpdateEduStateAgain(DataSource ds) {
			super(ds, " Update newKmac.dbo.OpenCourse set oc_PSanction='Y' where oc_ID = ? ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}
		
		protected int updateEdu(String oc_code) {
			return this.update(new Object[] { oc_code });
		}
	}
	
	protected class UpdateEduStateResanction extends SqlUpdate {
		protected UpdateEduStateResanction(DataSource ds) {
			super(ds, " Update newKmac.dbo.OpenCourse set oc_PSanction='I' where oc_ID = ? ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}
		
		protected int updateEdu(String oc_code) {
			return this.update(new Object[] { oc_code });
		}
	}
	
	protected class RestoreEduStateResanction extends SqlUpdate {
		protected RestoreEduStateResanction(DataSource ds) {
			super(ds, " Update newKmac.dbo.OpenCourse set oc_PSanction='W' where oc_ID = ? ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}
		
		protected int updateEdu(String oc_code) {
			return this.update(new Object[] { oc_code });
		}
	}

	protected class SanctionDocUpdateQeury extends SqlUpdate {
		protected SanctionDocUpdateQeury(DataSource ds) {
			super(ds, " UPDATE   SanctionDoc SET registerSsn = ?, registerDept = ?, registerName = ?, registerDate = ?,          "
					+ "                          teamManagerSsn = ?, teamManagerDept = ?, teamManagerName = ?, teamManagerDate = ?, "
					+ "                          cfoSsn = ?, cfoDept = ?, cfoName = ?, cfoDate = ?,                         "
					+ "                          cioSsn = ?, cioDept = ?, cioName = ?, cioDate = ?,                         "
					+ "                          ceoSsn = ?, ceoDept = ?, ceoName = ?, ceoDate = ?,                         "
					+ "                          assistant1Ssn = ?, assistant1Dept = ?, assistant1Name = ?, assistant1Date = ?,    "
					+ "                          assistant2Ssn = ?, assistant2Dept = ?, assistant2Name = ?, assistant2Date = ?,    "
					+ "                          assistant3Ssn = ?, assistant3Dept = ?, assistant3Name = ?, assistant3Date = ?,    "
					+ "                          assistant4Ssn = ?, assistant4Dept = ?, assistant4Name = ?, assistant4Date = ?,    "
					+ "                          subject = ?, content = ?, rejectReason = ?, isWholeApproval = ? , state = ?, reject = ?, createrDate=?"
					+ " WHERE  seq = ? AND projectCode = ? AND approvalType = ?");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.DATE));

			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.DATE));

			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.DATE));

			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.DATE));

			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.DATE));

			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.DATE));

			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.DATE));

			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.DATE));

			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.DATE));

			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.DATE));

			declareParameter(new SqlParameter(Types.INTEGER));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected int update(SanctionDoc sanctionDoc) {
			return this.update(new Object[] { sanctionDoc.getRegisterSsn(), sanctionDoc.getRegisterDept(), sanctionDoc.getRegisterName(),
					sanctionDoc.getRegisterDate(), sanctionDoc.getTeamManagerSsn(), sanctionDoc.getTeamManagerDept(),
					sanctionDoc.getTeamManagerName(), sanctionDoc.getTeamManagerDate(), sanctionDoc.getCfoSsn(), sanctionDoc.getCfoDept(),
					sanctionDoc.getCfoName(), sanctionDoc.getCfoDate(), sanctionDoc.getCioSsn(), sanctionDoc.getCioDept(), sanctionDoc.getCioName(),
					sanctionDoc.getCioDate(), sanctionDoc.getCeoSsn(), sanctionDoc.getCeoDept(), sanctionDoc.getCeoName(), sanctionDoc.getCeoDate(),
					sanctionDoc.getAssistant1Ssn(), sanctionDoc.getAssistant1Dept(), sanctionDoc.getAssistant1Name(),
					sanctionDoc.getAssistant1Date(), sanctionDoc.getAssistant2Ssn(), sanctionDoc.getAssistant2Dept(),
					sanctionDoc.getAssistant2Name(), sanctionDoc.getAssistant2Date(), sanctionDoc.getAssistant3Ssn(),
					sanctionDoc.getAssistant3Dept(), sanctionDoc.getAssistant3Name(), sanctionDoc.getAssistant3Date(),
					sanctionDoc.getFunc(), sanctionDoc.getProjectCodeBp(), sanctionDoc.getAssistant4Name(),
					sanctionDoc.getAssistant4Date(), sanctionDoc.getSubject(), sanctionDoc.getContent(), sanctionDoc.getRejectReasonView(),
					sanctionDoc.getIsWholeApproval(), sanctionDoc.getState(), sanctionDoc.getReject(), sanctionDoc.getRegisterDate(), sanctionDoc.getSeq(),
					sanctionDoc.getProjectCode(), sanctionDoc.getApprovalType(), });
		}
	}

	protected class SanctionDocRetrieveQuery extends MappingSqlQuery {
		protected SanctionDocRetrieveQuery(DataSource ds, String query) {
			super(ds, query);
		}

		protected SanctionDocRetrieveQuery(DataSource ds) {
			super(ds, " SELECT * FROM SanctionDoc WHERE seq = ? AND projectCode = ? AND approvalType = ?");
			declareParameter(new SqlParameter(Types.INTEGER));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			SanctionDoc sanctionDoc = new SanctionDoc();
			sanctionDoc.setSeq(rs.getInt("seq"));
			sanctionDoc.setProjectCode(rs.getString("projectCode"));
			sanctionDoc.setApprovalType(rs.getString("approvalType"));
			sanctionDoc.setRegisterSsn(rs.getString("registerSsn"));
			sanctionDoc.setRegisterDept(rs.getString("registerDept"));
			sanctionDoc.setRegisterName(rs.getString("registerName"));
			sanctionDoc.setRegisterDate(rs.getDate("registerDate"));
			sanctionDoc.setTeamManagerSsn(rs.getString("teamManagerSsn"));
			sanctionDoc.setTeamManagerDept(rs.getString("teamManagerDept"));
			sanctionDoc.setTeamManagerName(rs.getString("teamManagerName"));
			sanctionDoc.setTeamManagerDate(rs.getDate("teamManagerDate"));
			sanctionDoc.setCfoSsn(rs.getString("cfoSsn"));
			sanctionDoc.setCfoDept(rs.getString("cfoDept"));
			sanctionDoc.setCfoName(rs.getString("cfoName"));
			sanctionDoc.setCfoDate(rs.getDate("cfoDate"));
			sanctionDoc.setCioSsn(rs.getString("cioSsn"));
			sanctionDoc.setCioDept(rs.getString("cioDept"));
			sanctionDoc.setCioName(rs.getString("cioName"));
			sanctionDoc.setCioDate(rs.getDate("cioDate"));
			sanctionDoc.setCeoSsn(rs.getString("ceoSsn"));
			sanctionDoc.setCeoDept(rs.getString("ceoDept"));
			sanctionDoc.setCeoName(rs.getString("ceoName"));
			sanctionDoc.setCeoDate(rs.getDate("ceoDate"));
			sanctionDoc.setAssistant1Ssn(rs.getString("assistant1Ssn"));
			sanctionDoc.setAssistant1Dept(rs.getString("assistant1Dept"));
			sanctionDoc.setAssistant1Name(rs.getString("assistant1Name"));
			sanctionDoc.setAssistant1Date(rs.getDate("assistant1Date"));
			sanctionDoc.setAssistant2Ssn(rs.getString("assistant2Ssn"));
			sanctionDoc.setAssistant2Dept(rs.getString("assistant2Dept"));
			sanctionDoc.setAssistant2Name(rs.getString("assistant2Name"));
			sanctionDoc.setAssistant2Date(rs.getDate("assistant2Date"));
			sanctionDoc.setAssistant3Ssn(rs.getString("assistant3Ssn"));
			sanctionDoc.setAssistant3Dept(rs.getString("assistant3Dept"));
			sanctionDoc.setAssistant3Name(rs.getString("assistant3Name"));
			sanctionDoc.setAssistant3Date(rs.getDate("assistant3Date"));
			sanctionDoc.setAssistant4Ssn(rs.getString("assistant4Ssn"));
			sanctionDoc.setAssistant4Dept(rs.getString("assistant4Dept"));
			sanctionDoc.setAssistant4Name(rs.getString("assistant4Name"));
			sanctionDoc.setAssistant4Date(rs.getDate("assistant4Date"));
			sanctionDoc.setSubject(rs.getString("subject"));
			sanctionDoc.setContent(rs.getString("content"));
			sanctionDoc.setRejectReason(rs.getString("rejectReason"));
			sanctionDoc.setIsWholeApproval(rs.getString("isWholeApproval"));
			sanctionDoc.setState(rs.getString("state"));
			sanctionDoc.setWorkType(rs.getString("workType"));
			sanctionDoc.setEduCourseCode(rs.getString("eduCourseCode"));
			sanctionDoc.setCreaterDate(rs.getDate("createrDate"));
			return sanctionDoc;
		}

	}

	protected class SanctionRunningDocRetrieveQuery extends SanctionDocRetrieveQuery{
		protected SanctionRunningDocRetrieveQuery(DataSource ds, String query) {
			super(ds, query);
		}
		protected SanctionRunningDocRetrieveQuery(DataSource ds) {
			super(ds, " SELECT * FROM SanctionDoc WHERE state <> 'SANCTION_STATE_COMPLETE' "
					+ "	   and projectCode = ? AND approvalType = ?");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

	}
	protected class SanctionDocRetrieveQuery1 extends SanctionDocRetrieveQuery {
		protected SanctionDocRetrieveQuery1(DataSource ds, String query) {
			super(ds, query);
		}

		protected SanctionDocRetrieveQuery1(DataSource ds) {
			super(ds, " SELECT * FROM SanctionDoc WHERE seq = ? AND approvalType = ?");
			declareParameter(new SqlParameter(Types.INTEGER));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}
	}

	protected class SanctionDocRetrieveQuery2 extends SanctionDocRetrieveQuery {
		protected SanctionDocRetrieveQuery2(DataSource ds, String query) {
			super(ds, query);
		}
		
		protected SanctionDocRetrieveQuery2(DataSource ds) {
			super(ds, " SELECT * FROM SanctionDoc WHERE projectCode = ? AND (approvalType = 'A' or approvalType = 'PA') ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}
	}

	protected class SanctionDocDeleteQuery extends SqlUpdate {
		protected SanctionDocDeleteQuery(DataSource ds) {
			super(ds, " DELETE FROM SanctionDoc WHERE  seq = ? AND projectCode = ? AND approvalType = ?");
			declareParameter(new SqlParameter(Types.INTEGER));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected int delete(String seq, String projectCode, String approvaltype) {
			return this.update(new Object[] { seq, projectCode, approvaltype });
		}
	}

	@Override
	public boolean updateErpState(String projectCode) throws SanctionException {
		return this.erpUpdateQuery.updateErp(projectCode) == 1;
	}

	@Override
	synchronized public SanctionDoc insert(SanctionDoc sanctionDoc) throws SanctionException {
		int maxSeq = getJdbcTemplate().queryForInt("SELECT ISNULL(MAX(seq),0)+1 FROM SanctionDoc");
		sanctionDoc.setSeq(maxSeq);
		this.sanctionDocInsertQuery.insert(sanctionDoc);
		return sanctionDoc;
	}

	@Override
	public void delete(String seq, String projectCode, String approvalType) throws SanctionException {
		this.sanctionDocDeleteQuery.delete(seq, projectCode, approvalType);
	}
	
	@Override
	public void insertLog(SanctionDoc sanctionDoc){
		this.sanctionDocInsertLogQuery.insert(sanctionDoc);
	}

	@Override
	public boolean isExist(String seq, String projectCode, String approvalType) throws SanctionException {
		return this.sanctionDocRetrieveQuery.findObject(new Object[] { seq, projectCode, approvalType }) != null;
	}

	@Override
	public SanctionDoc select(String seq, String projectCode, String approvalType) throws SanctionException {
		if (projectCode != null && projectCode.equals("8888888")) {
			return (SanctionDoc) this.sanctionDocRetrieveQuery1.findObject(new Object[] { seq, approvalType });
		}
		return (SanctionDoc) this.sanctionDocRetrieveQuery.findObject(new Object[] { seq, projectCode, approvalType });
	}
	
	@Override
	public SanctionDoc select(String projectCode) throws SanctionException {
		return (SanctionDoc) this.sanctionDocRetrieveQuery2.findObject(new Object[] { projectCode });
	}

	@Override
	public void update(SanctionDoc sanctionDoc) throws SanctionException {
		this.sanctionDocUpdateQeury.update(sanctionDoc);
	}

	@Override
	public void updateEduState(String projectCode) throws DataAccessException {
		if (!projectCode.equals("8888888")) {
			this.updateEduStateQuery.updateEduState(projectCode);
		}
	}
	
	@Override
	public void updateEduStateDisabled(String projectCode) throws DataAccessException {
		if (!projectCode.equals("8888888")) {
			this.updateEduStateDisabledQuery.updateEduStateDisabled(projectCode);
		}
	}

	@Override
	public void updateEduStateAgain(String oc_code) throws ProjectException {
		if (!oc_code.equals("8888888")) {
			this.updateEduStateAgain.updateEdu(oc_code);
		}
	}

	public void updateEduStateCancle(String oc_code) throws ProjectException {
		if (!oc_code.equals("8888888")) {
			this.updateEduStateCancle.updateEdu(oc_code);
		}
	}
	
	@Override
	public void updateEduStateResanction(String oc_code) throws ProjectException {
		if (!oc_code.equals("8888888")) {
			this.updateEduStateResanction.updateEdu(oc_code);
		}
	}
	
	@Override
	public void restoreEduStateResanction(String oc_code) throws ProjectException {
		if (!oc_code.equals("8888888")) {
			this.restoreEduStateResanction.updateEdu(oc_code);
		}
	}

	@Override
	public boolean isIngSanctionExist(String projectCode, String approvalType) throws DataAccessException {
		return this.sanctionRunningDocRetrieveQuery.findObject(new Object[] { projectCode, approvalType }) != null;
	}
	
	
}
