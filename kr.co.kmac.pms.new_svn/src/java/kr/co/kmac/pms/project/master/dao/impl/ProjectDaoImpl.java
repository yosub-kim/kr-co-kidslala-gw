package kr.co.kmac.pms.project.master.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import kr.co.kmac.pms.project.exception.ProjectException;
import kr.co.kmac.pms.project.master.dao.ProjectDao;
import kr.co.kmac.pms.project.master.data.Project;
import kr.co.kmac.pms.project.master.data.ProjectDelayInfo;
import kr.co.kmac.pms.project.master.data.ProjectSimpleInfo;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.jdbc.object.SqlUpdate;
import org.springframework.jdbc.object.StoredProcedure;

public class ProjectDaoImpl extends JdbcDaoSupport implements ProjectDao {
	private ProjectInsertQuery projectInsertQuery;
	private ProjectUpdateQuery projectUpdateQuery;
	// private ProjectSelectQuery1 projectSelectQuery1;
	private ProjectSelectQuery2 projectSelectQuery2;
	private ProjectDeleteQuery projectDeleteQuery;
	private BudgetSendToErpStoredProcedure budgetSendToErpStoredProcedure;
	private EndProcessTaskList endProcessTaskList;
	private ProjectStateUpdateQuery projectStateUpdateQuery;
	private ProjectEndTaskStateUpdateQuery projectEndTaskStateUpdateQuery;
	private ProjectKdbStateUpdateQuery projectKdbStateUpdateQuery;
	private BudgetStateUpdateQuery budgetStateUpdateQuery;
	private ProjectDelayInfoQuery projectDelayInfoQuery;
	private ProjectTaskDelayInfoQuery projectTaskDelayInfoQuery;
	private ProjectSimpleInfoQuery projectSimpleInfoQuery;
	private ProjectSimpleInfoQuery2 projectSimpleInfoQuery2;
	private ProjectSimpleInfoQuery3 projectSimpleInfoQuery3;
	private ProjectSimpleInfoQuery4 projectSimpleInfoQuery4;
	private ConnectedProjectQuery connectedProjectQuery;
	private AdminOpenUpdateQuery adminOpenUpdateQuery;
	private ProjectBusinessFunctionTypeUpdateQuery projectBusinessFunctionTypeUpdateQuery;

	private DataSource intranetEduDataSource;

	public DataSource getIntranetEduDataSource() {
		return intranetEduDataSource;
	}

	public void setIntranetEduDataSource(DataSource intranetEduDataSource) {
		this.intranetEduDataSource = intranetEduDataSource;
	}

	@Override
	protected void initDao() throws Exception {
		this.projectInsertQuery = new ProjectInsertQuery(getDataSource());
		this.projectUpdateQuery = new ProjectUpdateQuery(getDataSource());
		// this.projectSelectQuery1 = new ProjectSelectQuery1(getDataSource());
		this.projectSelectQuery2 = new ProjectSelectQuery2(getDataSource());
		this.projectDeleteQuery = new ProjectDeleteQuery(getDataSource());
		this.budgetSendToErpStoredProcedure = new BudgetSendToErpStoredProcedure(getDataSource());
		this.endProcessTaskList = new EndProcessTaskList(getDataSource());
		this.projectStateUpdateQuery = new ProjectStateUpdateQuery(getDataSource());
		this.projectEndTaskStateUpdateQuery = new ProjectEndTaskStateUpdateQuery(getDataSource());
		this.projectKdbStateUpdateQuery = new ProjectKdbStateUpdateQuery(getDataSource());
		this.budgetStateUpdateQuery = new BudgetStateUpdateQuery(getDataSource());
		this.projectDelayInfoQuery = new ProjectDelayInfoQuery(getDataSource());
		this.projectTaskDelayInfoQuery = new ProjectTaskDelayInfoQuery(getDataSource());
		this.projectSimpleInfoQuery = new ProjectSimpleInfoQuery(getDataSource());
		this.projectSimpleInfoQuery2 = new ProjectSimpleInfoQuery2(getDataSource());
		this.projectSimpleInfoQuery3 = new ProjectSimpleInfoQuery3(getDataSource());
		this.projectSimpleInfoQuery4 = new ProjectSimpleInfoQuery4(getDataSource());
		this.connectedProjectQuery = new ConnectedProjectQuery(getIntranetEduDataSource());
		this.adminOpenUpdateQuery = new AdminOpenUpdateQuery(getDataSource());
		this.projectBusinessFunctionTypeUpdateQuery = new ProjectBusinessFunctionTypeUpdateQuery(getDataSource());
	}

	protected class ProjectInsertQuery extends SqlUpdate {
		protected ProjectInsertQuery(DataSource ds) {
			super(ds, "INSERT INTO Project                                                           "
					+ "	(entNo, seq                                                                  "
					+ "	,projectCode,processTypeCode,projectName                                     "
					+ "	,industryTypeCode,businessTypeCode,cioTypeCode                               "
					+ "	,projectConditionCode,projectConditionCode2,projectConditionCode3            "
					+ "	,projectTypeCode,projectDetailCode, businessFunctionType, runningDivCode, runningDeptCode, runningPuCode, runningGroupCode            "
					+ "	,preStartDate,preEndDate,realStartDate,realEndDate                           "
					+ "	,projectState,projectStateDetail,attach                                      "
					+ "	,createDate,createrSsn,modifyDate,modifySsn,remark                           "
					+ "	,customerName,customerCode,customerWorkPName,customerWorkPEmail              "
					+ "	,customerWorkPPhone,customerContPName,customerContPEmail,customerContPPhone  "
					+ "	,endGubun,endReason,endRate,groupComment,cfoComment,cboComment,costOver      "
					+ "	,projectContractType,parentProjectCode                                       "
					+ "	,payCostOver,etcCostOver                                                     "
					+ "	,endBackground,endResult,endReview,endSuggestion                             "
					+ "	,isVoc,isEvaluate, projectSubName, lang, isEduConnected, coGRP, realWorkFromDt, realWorkToDt)              "
					+ "	VALUES(?, ?,   ?, ?, ?,   ?, ?, ?,   ?, ?, ?,   ?, ?, ?, ?, ?, ?, ?,   ?, ?, ?, ?,   ?, ?, ?, "
					+ "        ?, ?, ?, ?, ?,   ?, ?, ?, ?,   ?, ?, ?,   ?, ?, ?,   ?, ?, ?, ?, ?,        "
					+ "        ?, ?,   ?, ?,   ?, ?, ?, ?,   ?, ?, ?, ?, ?, ?, ?, ? )                                     ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.INTEGER));

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
			declareParameter(new SqlParameter(Types.VARCHAR));

			declareParameter(new SqlParameter(Types.DATE));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.DATE));
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

		protected int insert(Project project) throws DataAccessException {
			return this.update(new Object[] { 
					project.getEntNo(), project.getSeq(), 
					
					project.getProjectCode(), project.getProcessTypeCode(), project.getProjectName(), 
					
					project.getIndustryTypeCode(), project.getBusinessTypeCode(), project.getCioTypeCode(), 
					
					project.getProjectConditionCode(),project.getProjectConditionCode2(), project.getProjectConditionCode3(), 
					
					project.getProjectTypeCode(),project.getProjectDetailCode(), project.getBusinessFunctionType(), 
					project.getRunningDivCode(), project.getRunningDeptCode(), project.getRunningPuCode(),project.getRunningGroupCode(), 
					
					project.getPreStartDate(), project.getPreEndDate(), project.getRealStartDate(),
					project.getRealEndDate(), project.getProjectState(), project.getProjectScale(), project.getFunc(),
					project.getCreateDate(), project.getCreaterSsn(), project.getModifyDate(), project.getModifySsn(), project.getRemark(),
					project.getCustomerName(), project.getCustomerCode(), project.getCustomerWorkPName(), project.getCustomerWorkPEmail(),
					project.getCustomerWorkPPhone(), project.getCustomerContPName(), project.getCustomerContPEmail(),
					project.getCustomerContPPhone(), project.getEndGubun(),
					project.getEndReason(), project.getEndRate(), project.getGroupComment(), project.getCfoComment(), project.getCboComment(),
					project.getCostOver(), project.getProjectContractType(), project.getBp_projId(), project.getPayCostOver(),
					project.getEtcCostOver(), project.getEndBackground(), project.getEndResult(), project.getEndReview(), project.getEndSuggestion(),
					project.getIsVoc(), project.getIsEvaluate(), project.getProjectSubName(), project.getLang(), project.getIsEduConnected(), project.getCoGRP(),
					project.getRealWorkFromDt(), project.getRealWorkToDt()});
		}
	}

	protected class ProjectUpdateQuery extends SqlUpdate {
		protected ProjectUpdateQuery(DataSource ds) {
			super(ds, "UPDATE Project                                                          "
					+ "   SET entNo=?, processTypeCode=?,projectName=?,                               "
					+ "       industryTypeCode=?,businessTypeCode=?,cioTypeCode=?,                    "
					+ "       projectConditionCode=?,projectConditionCode2=?,projectConditionCode3=?, "
					+ "       projectTypeCode=?,projectDetailCode=?, businessFunctionType=?,          "
					+ "       runningDivCode=?,runningDeptCode=?, runningPuCode=?, runningGroupCode=?, "
					+ "       preStartDate=?,preEndDate=?,realStartDate=?,realEndDate=?,              "
					+ "       projectState=?,projectStateDetail=?,attach=?,                           "
					+ "       createDate=?,createrSsn=?,modifyDate=?,modifySsn=?,remark=?,            "
					+ "       customerName=?,customerCode=?,                                          "
					+ "       customerWorkPName=?,customerWorkPEmail=?,customerWorkPPhone=?,          "
					+ "       customerContPName=?,customerContPEmail=?,customerContPPhone=?,          "
					+ "       endGubun=?,endReason=?,endRate=?,                                       "
					+ "       groupComment=?,cfoComment=?,cboComment=?,                               "
					+ "       costOver=?,                                                             "
					+ "       projectContractType=?,parentProjectCode=?,                              "
					+ "       payCostOver=?,etcCostOver=?,                                            "
					+ "       endBackground=?,endResult=?,endReview=?,endSuggestion=?,                "
					+ "       isVoc=?,isEvaluate=?, projectSubName=?, lang=?, isEduConnected=?        "
					+ " WHERE projectCode=?                                                           ");
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
			declareParameter(new SqlParameter(Types.VARCHAR));

			declareParameter(new SqlParameter(Types.DATE));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.DATE));
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

		protected int update(Project project) throws DataAccessException {
			return this.update(new Object[] { project.getEntNo(), project.getProcessTypeCode(), project.getProjectName(),
					project.getIndustryTypeCode(), project.getBusinessTypeCode(), project.getCioTypeCode(), 
					project.getProjectConditionCode(), project.getProjectConditionCode2(), project.getProjectConditionCode3(), 
					project.getProjectTypeCode(), project.getProjectDetailCode(), project.getBusinessFunctionType(),					
					project.getRunningDivCode(), project.getRunningDeptCode(), project.getRunningPuCode(),project.getRunningGroupCode(),
					project.getPreStartDate(), project.getPreEndDate(), project.getRealStartDate(),
					project.getRealEndDate(), project.getProjectState(), project.getProjectStateDetail(), project.getAttach(),
					project.getCreateDate(), project.getCreaterSsn(), project.getModifyDate(), project.getModifySsn(), project.getRemark(),
					project.getCustomerName(), project.getCustomerCode(), project.getCustomerWorkPName(), project.getCustomerWorkPEmail(),
					project.getCustomerWorkPPhone(), project.getCustomerContPName(), project.getCustomerContPEmail(),
					project.getCustomerContPPhone(), project.getEndGubun(),
					project.getEndReason(), project.getEndRate(), project.getGroupComment(), project.getCfoComment(), project.getCboComment(),
					project.getCostOver(), project.getProjectContractType(), project.getParentProjectCode(), project.getPayCostOver(),
					project.getEtcCostOver(), project.getEndBackground(), project.getEndResult(), project.getEndReview(), project.getEndSuggestion(),
					project.getIsVoc(), project.getIsEvaluate(),project.getProjectSubName(), project.getLang(), project.getIsEduConnected(), project.getProjectCode() });
		}
	}

	protected class ProjectDeleteQuery extends SqlUpdate {
		protected ProjectDeleteQuery(DataSource ds) {
			super(ds, " DELETE FROM Project WHERE projectCode=? ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
			setRequiredRowsAffected(1);
		}

		protected int delete(String projectCode) throws DataAccessException {
			return this.update(new Object[] { projectCode });
		}
	}

	protected class ProjectSelectQuery1 extends MappingSqlQuery {
		protected ProjectSelectQuery1(DataSource ds) {
			super(ds, "	SELECT 	* FROM Project ");
			compile();
		}

		protected ProjectSelectQuery1(DataSource ds, String query) {
			super(ds, query);
		}

		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			Project project = new Project();
			project.setSeq(rs.getInt("seq"));
			project.setEntNo(rs.getString("entNo"));
			project.setProjectCode(rs.getString("projectCode"));
			project.setProcessTypeCode(rs.getString("processTypeCode"));
			project.setProjectName(rs.getString("projectName"));
			project.setIndustryTypeCode(rs.getString("industryTypeCode"));
			project.setBusinessTypeCode(rs.getString("businessTypeCode"));
			project.setCioTypeCode(rs.getString("cioTypeCode"));
			project.setProjectConditionCode(rs.getString("projectConditionCode"));
			project.setProjectConditionCode2(rs.getString("projectConditionCode2"));
			project.setProjectConditionCode3(rs.getString("projectConditionCode3"));
			project.setProjectTypeCode(rs.getString("projectTypeCode"));
			project.setProjectDetailCode(rs.getString("projectDetailCode"));
			project.setBusinessFunctionType(rs.getString("businessFunctionType"));
			project.setRunningDivCode(rs.getString("runningDivCode"));
			project.setRunningDeptCode(rs.getString("runningDeptCode"));
			project.setRunningPuCode(rs.getString("runningPuCode"));
			project.setRunningGroupCode(rs.getString("runningGroupCode"));
			project.setPreStartDate(rs.getString("preStartDate"));
			project.setPreEndDate(rs.getString("preEndDate"));
			project.setRealStartDate(rs.getString("realStartDate"));
			project.setRealEndDate(rs.getString("realEndDate"));
			project.setProjectState(rs.getString("projectState"));
			project.setProjectStateDetail(rs.getString("projectStateDetail"));
			project.setAttach(rs.getString("attach"));
			project.setCreateDate(rs.getDate("createDate"));
			project.setCreaterSsn(rs.getString("createrSsn"));
			project.setModifyDate(rs.getDate("modifyDate"));
			project.setModifySsn(rs.getString("modifySsn"));
			project.setRemark(rs.getString("remark"));
			project.setCustomerName(rs.getString("customerName"));
			project.setCustomerCode(rs.getString("customerCode"));
			project.setCustomerWorkPName(rs.getString("customerWorkPName"));
			project.setCustomerWorkPEmail(rs.getString("customerWorkPEmail"));
			project.setCustomerWorkPPhone(rs.getString("customerWorkPPhone"));
			project.setCustomerContPName(rs.getString("customerContPName"));
			project.setCustomerContPEmail(rs.getString("customerContPEmail"));
			project.setCustomerContPPhone(rs.getString("customerContPPhone"));
			project.setEndGubun(rs.getString("endGubun"));
			project.setEndReason(rs.getString("endReason"));
			project.setEndRate(rs.getString("endRate"));
			project.setGroupComment(rs.getString("groupComment"));
			project.setCfoComment(rs.getString("cfoComment"));
			project.setCboComment(rs.getString("cboComment"));
			project.setCostOver(rs.getString("costOver"));
			project.setProjectContractType(rs.getString("projectContractType"));
			project.setParentProjectCode(rs.getString("parentProjectCode"));
			project.setPayCostOver(rs.getString("payCostOver"));
			project.setEtcCostOver(rs.getString("etcCostOver"));
			project.setEndBackground(rs.getString("endBackground"));
			project.setEndResult(rs.getString("endResult"));
			project.setEndReview(rs.getString("endReview"));
			project.setEndSuggestion(rs.getString("endSuggestion"));
			project.setIsVoc(rs.getString("isVoc"));
			project.setIsEvaluate(rs.getString("isEvaluate"));
			project.setAdminOpen(rs.getString("adminOpen"));
			project.setProjectSubName(rs.getString("projectSubName"));
			project.setLang(rs.getString("lang"));
			project.setIsEduConnected(rs.getString("isEduConnected"));
			project.setCoGRP(rs.getString("coGRP"));
			project.setRealWorkFromDt(rs.getString("realWorkFromDt"));
			project.setRealWorkToDt(rs.getString("realWorkToDt"));
			return project;
		}
	}

	protected class ProjectSelectQuery2 extends ProjectSelectQuery1 {
		protected ProjectSelectQuery2(DataSource ds) {
			super(ds, "	SELECT 	* FROM Project WHERE projectCode=?");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}
	}

	protected class EndProcessTaskList extends MappingSqlQuery {
		protected EndProcessTaskList(DataSource ds) {
			super(ds, "	Select taskId from EndProcessCheck where projectCode = ? ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected EndProcessTaskList(DataSource ds, String query) {
			super(ds, query);
		}

		@Override
		protected Object mapRow(ResultSet rs, int num) throws SQLException {
			return rs.getString("taskId");
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getEndProcessTaskList(String projectCode) throws DataAccessException {
		return this.endProcessTaskList.execute(new Object[] { projectCode });
	}

	protected class ProjectStateUpdateQuery extends SqlUpdate {
		protected ProjectStateUpdateQuery(DataSource ds) {
			super(ds, " Update project set projectState = ?, modifyDate = getDate() where projectCode = ? ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
			setRequiredRowsAffected(1);
		}

		protected int updateState(String projectCode, String state) throws DataAccessException {
			return this.update(new Object[] { state, projectCode });
		}
	}

	@Override
	public void updateProjectState(String projectCode, String state) throws DataAccessException {
		this.projectStateUpdateQuery.updateState(projectCode, state);
	}

	protected class ProjectEndTaskStateUpdateQuery extends SqlUpdate {
		protected ProjectEndTaskStateUpdateQuery(DataSource ds) {
			super(ds, " Update project set endTaskState = ? where projectCode = ? ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
			setRequiredRowsAffected(1);
		}

		protected int updateState(String projectCode, String state) throws DataAccessException {
			return this.update(new Object[] { state, projectCode });
		}
	}
	
	protected class ProjectBusinessFunctionTypeUpdateQuery extends SqlUpdate {
		protected ProjectBusinessFunctionTypeUpdateQuery(DataSource ds) {
			super(ds, " Update project set businessFunctionType = ? where projectCode = ? ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
			setRequiredRowsAffected(1);
		}
		
		protected int updateState(String projectCode, String functionType) throws DataAccessException {
			return this.update(new Object[] { functionType, projectCode });
		}
	}
	
	protected class ProjectKdbStateUpdateQuery extends SqlUpdate {
		protected ProjectKdbStateUpdateQuery(DataSource ds) {
			super(ds, " Update project set remark = ? where projectCode = ? ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
			setRequiredRowsAffected(1);
		}
		
		protected int updateState(String projectCode, String state) throws DataAccessException {
			return this.update(new Object[] { state, projectCode });
		}
	}

	@Override
	public void updateProjectEndTaskState(String projectCode, String state) throws DataAccessException {
		this.projectEndTaskStateUpdateQuery.updateState(projectCode, state);
	}
	
	@Override
	public void updateBusinessFunctionType(String projectCode, String functionType) throws DataAccessException {
		this.projectBusinessFunctionTypeUpdateQuery.updateState(projectCode, functionType);
	}
	
	@Override
	public void updateKdbState(String projectCode, String state) throws DataAccessException {
		this.projectKdbStateUpdateQuery.updateState(projectCode, state);
	}

	@Override
	public void delete(String projectCode) throws DataAccessException {
		this.projectDeleteQuery.delete(projectCode);
	} 

	@Override
	public void insert(Project project) throws DataAccessException {
		this.projectInsertQuery.insert(project);
	}

	@Override
	public Project select(String projectCode) throws DataAccessException {
		return (Project) this.projectSelectQuery2.findObject(new Object[] { projectCode });
	}

	@Override
	public void update(Project project) throws DataAccessException {
		this.projectUpdateQuery.update(project);
	}

	protected class ProjectSimpleInfoQuery extends MappingSqlQuery {
		protected ProjectSimpleInfoQuery(DataSource ds) {
			super(ds, "		Select	a.projectCode, a.projectName, a.projectSubName, a.lang,  a.processTypeCode,       "
					+ "						a.industryTypeCode, a.businessTypeCode, a.projectTypeCode, a.cioTypeCode, "
					+ "						a.projectDetailCode, a.runningDeptCode,                                   "
					+ "						(select ssn from projectMember where projectCode = ? and role = 'AA' AND trainingYN = 'Y') as puSsn,                          		"
					+ "						dbo.getExpertPoolName((select ssn from projectMember where projectCode = ? and role = 'AA' AND trainingYN = 'Y')) as puName,  		"
					+ "						(select ssn from projectMember where projectCode = ? and role = 'AR' AND trainingYN = 'Y') as arSsn,                          		"
					+ "						dbo.getExpertPoolName((select ssn from projectMember where projectCode = ? and role = 'AR' AND trainingYN = 'Y')) as arName,  		"
					+ "						(select ssn from projectMember where projectCode = ? and role = 'AG' AND trainingYN = 'Y') as advisiorSsn,                    		"
					+ "						dbo.getExpertPoolName((select ssn from projectMember where projectCode = ? and role = 'AG' AND trainingYN = 'Y')) as advisiorName,  "
					+ "						(select ssn from projectMember where projectCode = ? and role = 'PM' AND trainingYN = 'Y') as pmSsn,                          		"
					+ "						dbo.getExpertPoolName((select ssn from projectMember where projectCode = ? and role = 'PM' AND trainingYN = 'Y')) as pmName, 		"
					+ "						(select ssn from projectMember where projectCode = ? and role = 'PL' AND trainingYN = 'Y') as plSsn,                          		"
					+ "						dbo.getExpertPoolName((select ssn from projectMember where projectCode = ? and role = 'PL' AND trainingYN = 'Y')) as plName,  		"
					+ "						a.preStartDate, a.preEndDate, a.projectState,                             "
					+ "               		a.runningDivCode, a.projectStateDetail,                                   "
					+ "						(select dept from expertpool where ssn = 				                  " // Job Date: 2007-07-05
					+ "							(select ssn from projectMember where projectCode = ?  " // Author: yhyim Description
					+ "								and role = 'AG' AND trainingYN = 'Y') ) as advisorDeptCode,		          " // Description: get dept code of advisior
					+ "						customerName,		"// 고객사 정보 추가 (20090418)
					+ "						customerCode,		"// 고객사 정보 추가 (20090418)
					+ "						customerWorkPName,		"// 고객사 정보 추가 (20090418)
					+ "						customerWorkPEmail,		"// 고객사 정보 추가 (20090418)
					+ "						coGRP		"// 기업코드  정보 추가 (20180628)
					+ "				From	project a"// ,
					+ "			    Where   a.projectCode =  ?                                                      ");
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

		protected ProjectSimpleInfoQuery(DataSource ds, String query) {
			super(ds, query);
		}

		@Override
		protected Object mapRow(ResultSet rs, int num) throws SQLException {
			ProjectSimpleInfo projectSimpleInfo = new ProjectSimpleInfo();
			projectSimpleInfo.setProjectCode(rs.getString("projectCode"));
			projectSimpleInfo.setProjectName(rs.getString("projectName"));
			projectSimpleInfo.setProjectSubName(rs.getString("projectSubName"));
			projectSimpleInfo.setIndustryTypeCode(rs.getString("industryTypeCode"));
			projectSimpleInfo.setBusinessTypeCode(rs.getString("businessTypeCode"));
			projectSimpleInfo.setCioTypeCode(rs.getString("cioTypeCode"));
			projectSimpleInfo.setProjectTypeCode(rs.getString("projectTypeCode"));
			projectSimpleInfo.setProjectDetailCode(rs.getString("projectDetailCode"));
			projectSimpleInfo.setRunningDivCode(rs.getString("runningDivCode"));
			projectSimpleInfo.setRunningDetpCode(rs.getString("runningDeptCode"));
			projectSimpleInfo.setPuSsn(rs.getString("puSsn"));
			projectSimpleInfo.setPuName(rs.getString("puName"));
			projectSimpleInfo.setArSsn(rs.getString("arSsn"));
			projectSimpleInfo.setArName(rs.getString("arName"));
			projectSimpleInfo.setAdvisorSsn(rs.getString("advisiorSsn"));
			projectSimpleInfo.setAdvisorName(rs.getString("advisiorName"));
			projectSimpleInfo.setPmSsn(rs.getString("pmSsn"));
			projectSimpleInfo.setPmName(rs.getString("pmName"));
			projectSimpleInfo.setPlSsn(rs.getString("plSsn"));
			projectSimpleInfo.setPlName(rs.getString("plName"));
			projectSimpleInfo.setStartDate(rs.getString("prestartDate"));
			projectSimpleInfo.setEndDate(rs.getString("preendDate"));
			projectSimpleInfo.setProjectState(rs.getString("projectState"));
			projectSimpleInfo.setProjectStateDetail(rs.getString("projectStateDetail"));
			projectSimpleInfo.setProcessTypeCode(rs.getString("processTypeCode"));
			projectSimpleInfo.setAdvisorDeptCode(rs.getString("advisorDeptCode")); // Job Date: 2007-07-05 Author: yhyim
			projectSimpleInfo.setLang(rs.getString("lang")); 

			projectSimpleInfo.setCustomerCompanyName(rs.getString("customerName"));// 고객사 정보 추가 (20090418)
			projectSimpleInfo.setCustomerCompanyCode(rs.getString("customerCode"));// 고객사 정보 추가 (20090418)
			projectSimpleInfo.setCustomerWorkerName(rs.getString("customerWorkPName"));// 고객사 정보 추가 (20090418)
			projectSimpleInfo.setCustomerEmail(rs.getString("customerWorkPEmail"));// 고객사 정보 추가 (20090418)
			projectSimpleInfo.setcoGRP(rs.getString("coGRP"));// 고객사 정보 추가 (20090418)
			return projectSimpleInfo;
		}
	}
	
	protected class ProjectSimpleInfoQuery2 extends MappingSqlQuery {
		protected ProjectSimpleInfoQuery2(DataSource ds) {
			super(ds, "		Select	a.projectCode, a.projectName, a.projectSubName, a.lang,  a.processTypeCode,       "
					+ "						a.industryTypeCode, a.businessTypeCode, a.projectTypeCode, a.cioTypeCode, "
					+ "						a.projectDetailCode, a.runningDeptCode,                                   "
					+ "						(select ssn from projectMember where projectCode = ? and role = 'AA' AND trainingYN = 'Y') as puSsn,                          		"
					+ "						dbo.getExpertPoolName((select ssn from projectMember where projectCode = ? and role = 'AA' AND trainingYN = 'Y')) as puName,  		"
					+ "						(select ssn from projectMember where projectCode = ? and role = 'AR' AND trainingYN = 'Y') as arSsn,                          		"
					+ "						dbo.getExpertPoolName((select ssn from projectMember where projectCode = ? and role = 'AR' AND trainingYN = 'Y')) as arName,  		"
					+ "						(select ssn from projectMember where projectCode = ? and role = 'AG' AND trainingYN = 'Y') as advisiorSsn,                    		"
					+ "						dbo.getExpertPoolName((select ssn from projectMember where projectCode = ? and role = 'AG' AND trainingYN = 'Y')) as advisiorName,  "
					+ "						(select ssn from projectMember where projectCode = ? and role = 'PM' AND trainingYN = 'Y') as pmSsn,                          		"
					+ "						dbo.getExpertPoolName((select ssn from projectMember where projectCode = ? and role = 'PM' AND trainingYN = 'Y')) as pmName, 		"
					+ "						(select ssn from projectMember where projectCode = ? and role = 'PL' AND trainingYN = 'Y') as plSsn,                          		"
					+ "						dbo.getExpertPoolName((select ssn from projectMember where projectCode = ? and role = 'PL' AND trainingYN = 'Y')) as plName,  		"
					+ "						a.preStartDate, a.preEndDate, a.projectState,                             "
					+ "               		a.runningDivCode, a.projectStateDetail,                                   "
					+ "						(select dept from expertpool where ssn = 				                  " // Job Date: 2007-07-05
					+ "							(select ssn from projectMember where projectCode = ?  " // Author: yhyim Description
					+ "								and role = 'AG' AND trainingYN = 'Y') ) as advisorDeptCode,		          " // Description: get dept code of advisior
					+ "						customerName,		"// 고객사 정보 추가 (20090418)
					+ "						customerCode,		"// 고객사 정보 추가 (20090418)
					+ "						customerWorkPName,		"// 고객사 정보 추가 (20090418)
					+ "						customerWorkPEmail,		"// 고객사 정보 추가 (20090418)
					+ "						coGRP,		"// 기업코드  정보 추가 (20180628)
					+ "				pm.ssn as mbSsn, dbo.getexpertpoolname(pm.ssn) as mbName, COUNT(a.projectcode) OVER (PARTITION BY a.projectcode) as mbCnt, e.jobClass, pm.role as mbRole "
					+ " 				From	project a 											"
					+ "				left join														"
					+ "				(												"
					+ "					select ssn, projectCode, role												"
					+ "					from projectmember												"
					+ "					where projectcode= ?											"
					+ "					and role not in ('PM','QM','AR')												"
					+ "				)pm 												"
					+ "				on a.projectcode = pm.projectcode												"
					+ "				left join												"
					+ "				(												"
					+ "					select ssn, jobClass												"
					+ "					from expertpool												"
					+ "					where enable='1'												"
					+ "					and jobClass in ('A','B','C','J')												"
					+ "				)e												"
					+ "				on pm.ssn = e.ssn		 													"
					+ "			    Where   a.projectCode =  ? and jobClass is not null												");
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
		
		protected ProjectSimpleInfoQuery2(DataSource ds, String query) {
			super(ds, query);
		}

		@Override
		protected Object mapRow(ResultSet rs, int num) throws SQLException {
			ProjectSimpleInfo projectSimpleInfo = new ProjectSimpleInfo();
			projectSimpleInfo.setProjectCode(rs.getString("projectCode"));
			projectSimpleInfo.setProjectName(rs.getString("projectName"));
			projectSimpleInfo.setProjectSubName(rs.getString("projectSubName"));
			projectSimpleInfo.setIndustryTypeCode(rs.getString("industryTypeCode"));
			projectSimpleInfo.setBusinessTypeCode(rs.getString("businessTypeCode"));
			projectSimpleInfo.setCioTypeCode(rs.getString("cioTypeCode"));
			projectSimpleInfo.setProjectTypeCode(rs.getString("projectTypeCode"));
			projectSimpleInfo.setProjectDetailCode(rs.getString("projectDetailCode"));
			projectSimpleInfo.setRunningDivCode(rs.getString("runningDivCode"));
			projectSimpleInfo.setRunningDetpCode(rs.getString("runningDeptCode"));
			projectSimpleInfo.setPuSsn(rs.getString("puSsn"));
			projectSimpleInfo.setPuName(rs.getString("puName"));
			projectSimpleInfo.setArSsn(rs.getString("arSsn"));
			projectSimpleInfo.setArName(rs.getString("arName"));
			projectSimpleInfo.setAdvisorSsn(rs.getString("advisiorSsn"));
			projectSimpleInfo.setAdvisorName(rs.getString("advisiorName"));
			projectSimpleInfo.setPmSsn(rs.getString("pmSsn"));
			projectSimpleInfo.setPmName(rs.getString("pmName"));
			projectSimpleInfo.setPlSsn(rs.getString("plSsn"));
			projectSimpleInfo.setPlName(rs.getString("plName"));
			projectSimpleInfo.setStartDate(rs.getString("prestartDate"));
			projectSimpleInfo.setEndDate(rs.getString("preendDate"));
			projectSimpleInfo.setProjectState(rs.getString("projectState"));
			projectSimpleInfo.setProjectStateDetail(rs.getString("projectStateDetail"));
			projectSimpleInfo.setProcessTypeCode(rs.getString("processTypeCode"));
			projectSimpleInfo.setAdvisorDeptCode(rs.getString("advisorDeptCode")); // Job Date: 2007-07-05 Author: yhyim
			projectSimpleInfo.setLang(rs.getString("lang")); 

			projectSimpleInfo.setCustomerCompanyName(rs.getString("customerName"));// 고객사 정보 추가 (20090418)
			projectSimpleInfo.setCustomerCompanyCode(rs.getString("customerCode"));// 고객사 정보 추가 (20090418)
			projectSimpleInfo.setCustomerWorkerName(rs.getString("customerWorkPName"));// 고객사 정보 추가 (20090418)
			projectSimpleInfo.setCustomerEmail(rs.getString("customerWorkPEmail"));// 고객사 정보 추가 (20090418)
			projectSimpleInfo.setcoGRP(rs.getString("coGRP"));// 고객사 정보 추가 (20090418)
			projectSimpleInfo.setMbCnt(rs.getString("mbCnt"));
			projectSimpleInfo.setMbName(rs.getString("mbName"));
			projectSimpleInfo.setMbSsn(rs.getString("mbSsn"));
			projectSimpleInfo.setMbRole(rs.getString("mbRole"));
			return projectSimpleInfo;
		}
	}
	
	protected class ProjectSimpleInfoQuery3 extends ProjectSimpleInfoQuery2 {
		protected ProjectSimpleInfoQuery3(DataSource ds) {
			super(ds, "		Select	a.projectCode, a.projectName, a.projectSubName, a.lang,  a.processTypeCode,       "
					+ "						a.industryTypeCode, a.businessTypeCode, a.projectTypeCode, a.cioTypeCode, "
					+ "						a.projectDetailCode, a.runningDeptCode,                                   "
					+ "						(select ssn from projectMember where projectCode = ? and role = 'AA' AND trainingYN = 'Y') as puSsn,                          		"
					+ "						dbo.getExpertPoolName((select ssn from projectMember where projectCode = ? and role = 'AA' AND trainingYN = 'Y')) as puName,  		"
					+ "						(select ssn from projectMember where projectCode = ? and role = 'AR' AND trainingYN = 'Y') as arSsn,                          		"
					+ "						dbo.getExpertPoolName((select ssn from projectMember where projectCode = ? and role = 'AR' AND trainingYN = 'Y')) as arName,  		"
					+ "						(select ssn from projectMember where projectCode = ? and role = 'AG' AND trainingYN = 'Y') as advisiorSsn,                    		"
					+ "						dbo.getExpertPoolName((select ssn from projectMember where projectCode = ? and role = 'AG' AND trainingYN = 'Y')) as advisiorName,  "
					+ "						(select ssn from projectMember where projectCode = ? and role = 'PM' AND trainingYN = 'Y') as pmSsn,                          		"
					+ "						dbo.getExpertPoolName((select ssn from projectMember where projectCode = ? and role = 'PM' AND trainingYN = 'Y')) as pmName, 		"
					+ "						(select ssn from projectMember where projectCode = ? and role = 'PL' AND trainingYN = 'Y') as plSsn,                          		"
					+ "						dbo.getExpertPoolName((select ssn from projectMember where projectCode = ? and role = 'PL' AND trainingYN = 'Y')) as plName,  		"
					+ "						a.preStartDate, a.preEndDate, a.projectState,                             "
					+ "               		a.runningDivCode, a.projectStateDetail,                                   "
					+ "						(select dept from expertpool where ssn = 				                  " // Job Date: 2007-07-05
					+ "							(select ssn from projectMember where projectCode = ?  " // Author: yhyim Description
					+ "								and role = 'AG' AND trainingYN = 'Y') ) as advisorDeptCode,		          " // Description: get dept code of advisior
					+ "						customerName,		"// 고객사 정보 추가 (20090418)
					+ "						customerCode,		"// 고객사 정보 추가 (20090418)
					+ "						customerWorkPName,		"// 고객사 정보 추가 (20090418)
					+ "						customerWorkPEmail,		"// 고객사 정보 추가 (20090418)
					+ "						coGRP,		"// 기업코드  정보 추가 (20180628)
					+ "				pm.ssn as mbSsn, dbo.getexpertpoolname(pm.ssn) as mbName, COUNT(a.projectcode) OVER (PARTITION BY a.projectcode) as mbCnt, e.jobClass, pm.role as mbRole  	 "
					+ " 				From	project a 											"
					+ "				left join														"
					+ "				(												"
					+ "					select ssn, projectCode, role												"
					+ "					from projectmember												"
					+ "					where projectcode= ?											"
					+ "					and role not in ('QM', 'AR', 'PL')						"
					+ "				)pm 												"
					+ "				on a.projectcode = pm.projectcode												"
					+ "				left join												"
					+ "				(												"
					+ "					select ssn, jobClass												"
					+ "					from expertpool												"
					+ "					where enable='1'												"
					+ "					and jobClass in ('A','B','C','J')												"
					+ "				)e												"
					+ "				on pm.ssn = e.ssn		 													"
					+ "			    Where   a.projectCode =  ? and jobClass is not null												");
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
	}
	
	protected class ProjectSimpleInfoQuery4 extends ProjectSimpleInfoQuery2 {
		protected ProjectSimpleInfoQuery4(DataSource ds) {
		super(ds, "		Select	a.projectCode, a.projectName, a.projectSubName, a.lang,  a.processTypeCode,       "
				+ "						a.industryTypeCode, a.businessTypeCode, a.projectTypeCode, a.cioTypeCode, "
				+ "						a.projectDetailCode, a.runningDeptCode,                                   "
				+ "						(select ssn from projectMember where projectCode = ? and role = 'AA' AND trainingYN = 'Y') as puSsn,                          		"
				+ "						dbo.getExpertPoolName((select ssn from projectMember where projectCode = ? and role = 'AA' AND trainingYN = 'Y')) as puName,  		"
				+ "						(select ssn from projectMember where projectCode = ? and role = 'AR' AND trainingYN = 'Y') as arSsn,                          		"
				+ "						dbo.getExpertPoolName((select ssn from projectMember where projectCode = ? and role = 'AR' AND trainingYN = 'Y')) as arName,  		"
				+ "						(select ssn from projectMember where projectCode = ? and role = 'AG' AND trainingYN = 'Y') as advisiorSsn,                    		"
				+ "						dbo.getExpertPoolName((select ssn from projectMember where projectCode = ? and role = 'AG' AND trainingYN = 'Y')) as advisiorName,  "
				+ "						(select ssn from projectMember where projectCode = ? and role = 'PM' AND trainingYN = 'Y') as pmSsn,                          		"
				+ "						dbo.getExpertPoolName((select ssn from projectMember where projectCode = ? and role = 'PM' AND trainingYN = 'Y')) as pmName, 		"
				+ "						(select ssn from projectMember where projectCode = ? and role = 'PL' AND trainingYN = 'Y') as plSsn,                          		"
				+ "						dbo.getExpertPoolName((select ssn from projectMember where projectCode = ? and role = 'PL' AND trainingYN = 'Y')) as plName,  		"
				+ "						a.preStartDate, a.preEndDate, a.projectState,                             "
				+ "               		a.runningDivCode, a.projectStateDetail,                                   "
				+ "						(select dept from expertpool where ssn = 				                  " // Job Date: 2007-07-05
				+ "							(select ssn from projectMember where projectCode = ?  " // Author: yhyim Description
				+ "								and role = 'AG' AND trainingYN = 'Y') ) as advisorDeptCode,		          " // Description: get dept code of advisior
				+ "						customerName,		"// 고객사 정보 추가 (20090418)
				+ "						customerCode,		"// 고객사 정보 추가 (20090418)
				+ "						customerWorkPName,		"// 고객사 정보 추가 (20090418)
				+ "						customerWorkPEmail,		"// 고객사 정보 추가 (20090418)
				+ "						coGRP,		"// 기업코드  정보 추가 (20180628)
				+ "				pm.ssn as mbSsn, dbo.getexpertpoolname(pm.ssn) as mbName, COUNT(a.projectcode) OVER (PARTITION BY a.projectcode) as mbCnt, e.jobClass, pm.role as mbRole  	 "
				+ " 				From	project a 											"
				+ "				left join														"
				+ "				(												"
				+ "					select ssn, projectCode, role												"
				+ "					from projectmember												"
				+ "					where projectcode= ?											"
				+ "					and role not in ('PM', 'QM', 'AR')						"
				+ "				)pm 												"
				+ "				on a.projectcode = pm.projectcode												"
				+ "				left join												"
				+ "				(												"
				+ "					select ssn, jobClass												"
				+ "					from expertpool												"
				+ "					where enable='1'												"
				+ "					and jobClass in ('A','B','C','J')												"
				+ "				)e												"
				+ "				on pm.ssn = e.ssn		 													"
				+ "			    Where   a.projectCode =  ? and jobClass is not null												");
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
}

	@SuppressWarnings("unchecked")
	@Override
	public ProjectSimpleInfo getProjectSimpleInfo(String projectCode) throws DataAccessException {
		List<ProjectSimpleInfo> list = this.projectSimpleInfoQuery.execute(new Object[] { projectCode, projectCode, projectCode, projectCode,
				projectCode, projectCode, projectCode, projectCode, projectCode, projectCode, projectCode, projectCode });
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ProjectSimpleInfo> getProjectSimpleInfo2(String projectCode) throws DataAccessException {
		return this.projectSimpleInfoQuery2.execute(new Object[] { projectCode, projectCode, projectCode, projectCode,
				projectCode, projectCode, projectCode, projectCode, projectCode, projectCode, projectCode, projectCode, projectCode });
	}
	
	@SuppressWarnings("unckecked")
	@Override
	public List<ProjectSimpleInfo> getProjectSimpleInfo3(String projectCode) throws DataAccessException {
		return this.projectSimpleInfoQuery3.execute(new Object[] { projectCode, projectCode, projectCode, projectCode,
				projectCode, projectCode, projectCode, projectCode, projectCode, projectCode, projectCode, projectCode, projectCode });
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ProjectSimpleInfo> getProjectSimpleInfo4(String projectCode) throws DataAccessException {
		return this.projectSimpleInfoQuery4.execute(new Object[] { projectCode, projectCode, projectCode, projectCode,
				projectCode, projectCode, projectCode, projectCode, projectCode, projectCode, projectCode, projectCode, projectCode });
	}

	private class BudgetSendToErpStoredProcedure extends StoredProcedure {
		protected BudgetSendToErpStoredProcedure(DataSource ds) {
			super(ds, "DWPM.dbo.sp_DWPM30300_U");
			declareParameter(new SqlParameter("entno", Types.VARCHAR));
			compile();
		}

		public Map execute(String entNo) throws DataAccessException {
			Map<String, String> inputs = new HashMap<String, String>();
			inputs.put("entno", entNo);
			return super.execute(inputs);
		}
	}

	protected class BudgetStateUpdateQuery extends SqlUpdate {
		protected BudgetStateUpdateQuery(DataSource ds) {
			super(ds, " UPDATE DWPM.DBO.DW_PROJECTMST SET ACPTTYPE = '1' WHERE ENTNO = ? ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
			setRequiredRowsAffected(1);
		}

		protected int updateState(String entNo) throws DataAccessException {
			return this.update(new Object[] { entNo });
		}
	}

	@Override
	public void updateBudgetState(String entNo) throws DataAccessException {
		this.budgetStateUpdateQuery.updateState(entNo);
	}

	@Override
	public void sendBudgetState(String entNo) throws DataAccessException {
		this.budgetSendToErpStoredProcedure.execute(entNo);
	}

	protected class ProjectDelayInfoQuery extends MappingSqlQuery {
		protected ProjectDelayInfoQuery(DataSource ds) {
			super(ds, " select p.projectCode, p.projectName, p.realEndDate, t.name, w.title, w.level, w.assigneeId, "
					+ "		(select e.name from expertpool e where e.ssn=w.assigneeId) assigneeName, "
					+ "     CONVERT(VARCHAR(10), w.createDate, 120) createDate, CONVERT(VARCHAR(10), w.executeDate, 120) executeDate"
					+ " from work w	inner join workType t on w.type = t.id inner join project p on w.refWorkId1 = p.projectCode"
					+ " where refWorkId1 = ? and state = 'WORK_STATE_READY'"
					+ " and type in ('4028809e0a43b9c4010a43be04e00002','4028809e0a43b9c4010a43c573480003','4028809e0a43b9c4010a43c573480004','4028809e0a43b9c4010a43c573480007','4028809e0a43b9c4010a43c573480008','4028809e0a43b9c4010a43c573480010',"
					+ "				'4028809e0a43b9c4010a43c573480005','4028809e0a43b9c4010a43c573480006','4028809e0a43b9c4010a43c573580009',"
					+ "				'4028809e0a43b9c4010a43c573580011','4028809e0a43b9c4010a43c573580012','4028809e0a43b9c4010a43c573580013',"
					+ "				'4028809e0a43b9c4010a43c573580014','4028809e0a43b9c4010a43d61140000a','4028809e0a43b9c4010a43d6114f000b',"
					+ "				'4028809e0a43b9c4010a43d6114f000c','4028809e0a43b9c4010a43d61140000d','4028809e0a43b9c4010a43d6114f000e') ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected ProjectDelayInfoQuery(DataSource ds, String query) {
			super(ds, query);
		}

		@Override
		protected Object mapRow(ResultSet rs, int num) throws SQLException {
			ProjectDelayInfo info = new ProjectDelayInfo();
			info.setProjectCode(rs.getString("projectCode"));
			info.setProjectName(rs.getString("projectName"));
			info.setProjectEndDate(rs.getString("realEndDate"));
			info.setTaskTypeName(rs.getString("name"));
			info.setTaskLevel(rs.getString("Level"));
			info.setTaskName(rs.getString("title"));
			info.setAssigneeSsn(rs.getString("assigneeId"));
			info.setAssigneeName(rs.getString("assigneeName"));
			info.setAssignDate(rs.getString("createDate"));
			info.setExecuteDate(rs.getString("executeDate"));
			return info;
		}
	}

	protected class ProjectTaskDelayInfoQuery extends MappingSqlQuery {
		protected ProjectTaskDelayInfoQuery(DataSource ds) {
			super(ds, " SELECT * FROM ( "
					+ " 		SELECT	projectCode, workName, startDate, endDate,  " 
					+ "					(case when (convert(varchar, getDate(), 112) >= endDate )"
					+ "								then datediff(day, convert(datetime, endDate), getDate()) "
					+ "						  when (convert(varchar, getDate()+ 7, 112) >= endDate )"
					+ "								then datediff(day, getDate(), convert(datetime, endDate)) "
					+ "					 else 'ING' end) duration"
					+ " 		FROM	projectScheduleM WITH(NOLOCK) WHERE	projectCode = ? "
					+ " 		AND	workSeq = ( SELECT min(workSeq) FROM	projectScheduleM WITH(NOLOCK)"
					+ "							WHERE	projectCode = ? AND	(realEndDate = ''  OR realEndDate is null ))"
					+ " 		UNION	"
					+ " 		SELECT	top 1 m.projectCode, '평가버튼 Click 요망' workName, p.realStartDate as startDate, p.realEndDate as endDate,	"
					+ " 				DATEDIFF(day, p.realEndDate, getDate()) duration	"
					+ " 		FROM	projectScheduleM m WITH(NOLOCK), project p WITH(NOLOCK) WHERE	m.projectCode = ?	" 
					+ " 		AND	m.projectCode = p.projectCode	"
					+ " 		AND	( SELECT min(workSeq) FROM	projectScheduleM WITH(NOLOCK)	"
					+ " 				WHERE	projectCode = ? AND	(realEndDate = ''  OR realEndDate is null )) is NULL	"
					+ " ) RES ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected ProjectTaskDelayInfoQuery(DataSource ds, String query) {
			super(ds, query);
		}

		@Override
		protected Object mapRow(ResultSet rs, int num) throws SQLException {
			ProjectDelayInfo info = new ProjectDelayInfo();
			info.setProjectCode(rs.getString("projectCode"));
			info.setTaskName(rs.getString("workName"));
			info.setStartDate(rs.getString("startDate"));
			info.setEndDate(rs.getString("endDate"));
			info.setDuration(rs.getString("duration"));
			return info;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void updateAdminOpenState(String projectCode, String adminOpenYn) throws DataAccessException {
		this.adminOpenUpdateQuery.updateState(projectCode, adminOpenYn);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProjectDelayInfo> getProjectDelayInfo(String projectCode, String delayType) throws DataAccessException {
		return this.projectDelayInfoQuery.execute(new Object[] { projectCode });
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProjectDelayInfo> getProjectTaskDelayInfo(String projectCode, String delayType) throws DataAccessException {
		return this.projectTaskDelayInfoQuery.execute(new Object[] { projectCode, projectCode, projectCode, projectCode });
	}
	
	protected class AdminOpenUpdateQuery extends SqlUpdate {
		protected AdminOpenUpdateQuery(DataSource ds) {
			super(ds, " Update project set adminOpen = ? where projectCode = ? ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
			setRequiredRowsAffected(1);
		}

		protected int updateState(String projectCode, String state) throws DataAccessException {
			return this.update(new Object[] { state, projectCode });
		}
	}

	protected class ConnectedProjectQuery extends MappingSqlQuery {
		protected ConnectedProjectQuery(DataSource ds) {
			super(ds, "	select count(c_Projectcode) res from newKmac.dbo.course where c_Projectcode=? ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected ConnectedProjectQuery(DataSource ds, String query) {
			super(ds, query);
		}

		@Override
		protected Object mapRow(ResultSet rs, int num) throws SQLException {
			return rs.getString("res");
		}
	}

	@Override
	public boolean isConnectedProject(String projectCode) throws ProjectException {
		Object res = this.connectedProjectQuery.findObject(new Object[] { projectCode });
		int resCnt = Integer.parseInt((String) res);
		return resCnt > 0;
	}

}
