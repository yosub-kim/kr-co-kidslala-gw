package kr.co.kmac.pms.project.master.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import kr.co.kmac.pms.project.master.dao.ErpProjectDao;
import kr.co.kmac.pms.project.master.data.Project;
import kr.co.kmac.pms.project.master.data.ProjectBudget;
import kr.co.kmac.pms.project.master.data.ProjectMember;
import kr.co.kmac.pms.project.master.data.ProjectRelatedEntNo;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.jdbc.object.SqlUpdate;

public class ErpProjectDaoImpl extends JdbcDaoSupport implements ErpProjectDao {
	private ErpProjectSelectQuery1 erpProjectSelectQuery1;
	private ErpProjectMemberSelectQuery erpProjectMemberSelectQuery;
	private RecentNewEntNoSelectQuery recentNewEntNoSelectQuery;
	private RecentOldEntNoSelectQuery recentOldEntNoSelectQuery;
	private MemberChangeEntNoSelectQuery memberChangeEntNoSelectQuery;
	private EntNoForUpdateSelectQuery entNoForUpdateSelectQuery;
	private EntNoForSanctionInsertQuery entNoForSanctionInsertQuery;
	private EntNoForSanctionSelectQuery entNoForSanctionSelectQuery;
	private EntNoForSanctionDeleteQuery entNoForSanctionDeleteQuery;
	private EntNoForMemberChangeSanctionInsertQuery entNoForMemberChangeSanctionInsertQuery;
	private EntNoForMemberChangeSanctionSelectQuery entNoForMemberChangeSanctionSelectQuery;
	private EntNoForMemberChangeSanctionDeleteQuery entNoForMemberChangeSanctionDeleteQuery;
	private ProjectBudgetSelectQuery projectBudgetSelectQuery;
	
	@Override
	protected void initDao() throws Exception {
		this.erpProjectSelectQuery1 = new ErpProjectSelectQuery1(getDataSource());
		this.erpProjectMemberSelectQuery = new ErpProjectMemberSelectQuery(getDataSource());
		this.recentNewEntNoSelectQuery = new RecentNewEntNoSelectQuery(getDataSource());
		this.recentOldEntNoSelectQuery = new RecentOldEntNoSelectQuery(getDataSource());
		this.memberChangeEntNoSelectQuery = new MemberChangeEntNoSelectQuery(getDataSource());
		this.entNoForUpdateSelectQuery = new EntNoForUpdateSelectQuery(getDataSource());
		this.entNoForSanctionInsertQuery = new EntNoForSanctionInsertQuery(getDataSource());
		this.entNoForSanctionSelectQuery = new EntNoForSanctionSelectQuery(getDataSource());
		this.entNoForSanctionDeleteQuery = new EntNoForSanctionDeleteQuery(getDataSource());
		this.entNoForMemberChangeSanctionInsertQuery = new EntNoForMemberChangeSanctionInsertQuery(getDataSource());
		this.entNoForMemberChangeSanctionSelectQuery = new EntNoForMemberChangeSanctionSelectQuery(getDataSource());
		this.entNoForMemberChangeSanctionDeleteQuery = new EntNoForMemberChangeSanctionDeleteQuery(getDataSource());
		this.projectBudgetSelectQuery = new ProjectBudgetSelectQuery(getDataSource());
	}

	protected class EntNoForSanctionInsertQuery extends SqlUpdate {
		public EntNoForSanctionInsertQuery(DataSource ds) {
			super(ds, "INSERT INTO ProjectBudgetChangeHistory (projectCode, seq, oldEntNo, newEntNo) values (?, ?, ?, ?)");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected int insert(ProjectRelatedEntNo projectRelatedEntNo) throws DataAccessException {
			return this.update(new Object[] { 
					projectRelatedEntNo.getProjectCode(), 
					projectRelatedEntNo.getSeq(), 
					projectRelatedEntNo.getOldEntNo(),
					projectRelatedEntNo.getNewEntNo() });
		}
	}
	
	protected class EntNoForMemberChangeSanctionInsertQuery extends SqlUpdate {
		public EntNoForMemberChangeSanctionInsertQuery(DataSource ds) {
			super(ds, "INSERT INTO ProjectMemberChangeHistory (projectCode, seq, newEntNo) values (?, ?, ?)");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected int insert(ProjectRelatedEntNo projectRelatedEntNo) throws DataAccessException {
			return this.update(new Object[] { 
					projectRelatedEntNo.getProjectCode(), 
					projectRelatedEntNo.getSeq(), 
					projectRelatedEntNo.getNewEntNo() });
		}
	}
	
	protected class EntNoForSanctionDeleteQuery extends SqlUpdate {
		public EntNoForSanctionDeleteQuery(DataSource ds) {
			super(ds, "DELETE FROM ProjectBudgetChangeHistory WHERE seq=? ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}
		
		protected int delete(String seq) throws DataAccessException {
			return this.update(new Object[] { seq });
		}
	}
	
	protected class EntNoForMemberChangeSanctionDeleteQuery extends SqlUpdate {
		public EntNoForMemberChangeSanctionDeleteQuery(DataSource ds) {
			super(ds, "DELETE FROM ProjectMemberChangeHistory WHERE seq=? ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}
		
		protected int delete(String seq) throws DataAccessException {
			return this.update(new Object[] { seq });
		}
	}
	
	protected class EntNoForSanctionSelectQuery extends MappingSqlQuery {
		protected EntNoForSanctionSelectQuery(DataSource ds) {
			super(ds, " SELECT * from ProjectBudgetChangeHistory " + "  WHERE seq = ? ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		@Override
		protected Object mapRow(ResultSet rs, int arg1) throws SQLException {
			ProjectRelatedEntNo projectRelatedEntNo = new ProjectRelatedEntNo();
			projectRelatedEntNo.setSeq(rs.getString("seq"));
			projectRelatedEntNo.setProjectCode(rs.getString("projectCode"));
			projectRelatedEntNo.setOldEntNo(rs.getString("oldEntNo"));
			projectRelatedEntNo.setNewEntNo(rs.getString("newEntNo"));
			return projectRelatedEntNo;
		}
	}
	
	protected class EntNoForMemberChangeSanctionSelectQuery extends MappingSqlQuery {
		protected EntNoForMemberChangeSanctionSelectQuery(DataSource ds) {
			super(ds, " SELECT * from ProjectMemberChangeHistory " + "  WHERE seq = ? ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		@Override
		protected Object mapRow(ResultSet rs, int arg1) throws SQLException {
			ProjectRelatedEntNo projectRelatedEntNo = new ProjectRelatedEntNo();
			projectRelatedEntNo.setSeq(rs.getString("seq"));
			projectRelatedEntNo.setProjectCode(rs.getString("projectCode"));
			projectRelatedEntNo.setNewEntNo(rs.getString("newEntNo"));
			return projectRelatedEntNo;
		}
	}
	
	@Override
	public ProjectRelatedEntNo getProjectRelatedEntNo(String seq) throws DataAccessException {
		return (ProjectRelatedEntNo) this.entNoForSanctionSelectQuery.findObject(new Object[]{seq});
	}

	@Override
	public void insertProjectRelatedEntNo(ProjectRelatedEntNo projectRelatedEntNo) throws DataAccessException {
		this.entNoForSanctionInsertQuery.insert(projectRelatedEntNo);
	}
	
	@Override
	public ProjectRelatedEntNo getProjectRelatedEntNoForMemberChange(String seq) throws DataAccessException {
		return (ProjectRelatedEntNo) this.entNoForMemberChangeSanctionSelectQuery.findObject(new Object[]{seq});
	}

	@Override
	public void insertProjectRelatedEntNoForMemberChange(ProjectRelatedEntNo projectRelatedEntNo) throws DataAccessException {
		this.entNoForMemberChangeSanctionInsertQuery.insert(projectRelatedEntNo);
	}

	@Override
	public void deleteProjectRelatedEntNoForMemberChange(String seq) throws DataAccessException {
		this.entNoForMemberChangeSanctionDeleteQuery.delete(seq);
	}

	protected class RecentNewEntNoSelectQuery extends MappingSqlQuery {
		protected RecentNewEntNoSelectQuery (DataSource ds) {
			super(ds, " SELECT TOP 1 PROJID, ENTNO FROM DWPM.DBO.DW_PROJECTMST WHERE ACPTTYPE='0' AND PROJID = ? AND ISNULL(PROMTYPENAME,'') <> '02' ORDER BY INPUTDATE DESC");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		@Override
		protected Object mapRow(ResultSet rs, int arg1) throws SQLException {
			return rs.getString("ENTNO");
		}
	}
	
	protected class RecentOldEntNoSelectQuery extends MappingSqlQuery {
		protected RecentOldEntNoSelectQuery (DataSource ds) {
			super(ds, " SELECT TOP 1 PROJID, ENTNO FROM DWPM.DBO.DW_PROJECTMST WHERE ACPTTYPE<>'0' AND PROJID = ? AND ISNULL(PROMTYPENAME,'') <> '02' ORDER BY INPUTDATE DESC");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}
		
		@Override
		protected Object mapRow(ResultSet rs, int arg1) throws SQLException {
			return rs.getString("ENTNO");
		}
	}
	
	protected class MemberChangeEntNoSelectQuery extends MappingSqlQuery {
		protected MemberChangeEntNoSelectQuery (DataSource ds) {
			super(ds, " SELECT TOP 1 PROJID, ENTNO FROM DWPM.DBO.DW_PROJECTMST WHERE ACPTTYPE = '0' AND PROJID = ? AND ISNULL(PROMTYPENAME,'') = '02' ORDER BY INPUTDATE DESC");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}
		
		@Override
		protected Object mapRow(ResultSet rs, int arg1) throws SQLException {
			return rs.getString("ENTNO");
		}
	}
	
	protected class EntNoForUpdateSelectQuery extends MappingSqlQuery {
		protected EntNoForUpdateSelectQuery(DataSource ds) {
			super(ds, " SELECT top 1 PROJID, ENTNO FROM DWPM.DBO.DW_PROJECTMST "
					+ "  WHERE PROJID = ? ORDER BY INPUTDATE DESC");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}
		
		@Override
		protected Object mapRow(ResultSet rs, int arg1) throws SQLException {
			return rs.getString("ENTNO");
		}
	}

	@Override
	public List<String> selectEntNo(String projectCode) throws DataAccessException {
		List<String> res = new ArrayList<String>();
		try {
			res.add((String)this.recentNewEntNoSelectQuery.findObject(new Object[] { projectCode }));
		} catch (Exception e) {
			res.add("");
		}
		try {
			res.add((String)this.recentOldEntNoSelectQuery.findObject(new Object[] { projectCode }));
		} catch (Exception e) {
			res.add("");
		}
		return res;
	}

	public String selectEntNoForUpdate(String projectCode) throws DataAccessException {
		return (String) this.entNoForUpdateSelectQuery.findObject(new Object[] { projectCode });
	}

	@Override
	public String selectMemberChangeEntNo(String projectCode) throws DataAccessException {
		return (String) this.memberChangeEntNoSelectQuery.findObject(new Object[] { projectCode });
	}
	
	@Override
	public List<ProjectMember> selectAllProjectMember(String entNo) throws DataAccessException {
		return this.erpProjectMemberSelectQuery.execute(new Object[] { entNo });
	}

	protected class ErpProjectMemberSelectQuery extends MappingSqlQuery {
		protected ErpProjectMemberSelectQuery(DataSource ds) {
			super(
					ds, " SELECT EMPNO, ISNULL(PJTRNK,'M') LANK, STDAMT,							" 
					  + "		 RATE, SUM(WORKMD) WORKMD,											"
					  + " 		 (SELECT PROJID FROM DWPM.DBO.DW_PROJECTMST WHERE ENTNO = MM.ENTNO) PROJID	"		
					  + " FROM DWPM.DBO.DW_PROJECTWORKMAN_MM MM												"
					  + " WHERE ENTNO=?																"
					  + " GROUP BY ENTNO, EMPNO, PJTRNK, STDAMT, RATE								"

			);
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected ErpProjectMemberSelectQuery(DataSource ds, String query) {
			super(ds, query);
		}

		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			ProjectMember projectMember = new ProjectMember();
			projectMember.setProjectCode(rs.getString("PROJID"));
			projectMember.setSsn(rs.getString("EMPNO"));
			projectMember.setRole(rs.getString("LANK"));
			projectMember.setCost(new DecimalFormat("###.#####").format(rs.getDouble("STDAMT")));
			projectMember.setTeachingDay(rs.getString("WORKMD"));
			projectMember.setResRate(rs.getString("RATE"));
			return projectMember;
		}
	}
	
	protected class ErpProjectSelectQuery1 extends MappingSqlQuery {
		protected ErpProjectSelectQuery1(DataSource ds) {
			super(ds, "	SELECT	ENTNO                                                                       "
					+ "         ,dbo.getConvertBusinessTypeCode_ERP(PJTTYPE)	as BIZTYPE	/*프로젝트 구분*/	"
					+ "			,OPTYPE 				/* 프로젝트 유형 */	"	
					+ "			,PJTTYPENAME		/*--프로젝트 구분명*/											"
					+ "			,BUCODE				/*부서 코드*/												"
					+ "			,BUCODENAME			/*부서명*/                                               	"
					+ "			,isNull(GTEAMCODE,'') AS PUCODE		/*Co-work 코드*/                     		"
					+ "			,isNull(GTEAMNAME,'') AS PUCODENAME	/*Co-work 부서명*/                   		"
					+ "			,dbo.getConvertIndustryCode_ERP(INDSTYPE) as INDSTYPE	/*--산업구분 코드*/		"
					+ "			,INDSTYPENAME		/*--산업구분	*/											"
					+ "			,PROJID				/*--프로젝트코드*/											"
					+ "			,CIOCODE			/*--CIO코드*/												"
					+ "			,CIONAME			/*--CIO명*/												"
					+ "			,PJTNAME			/*--프로젝트명*/											"
					+ "			,PROMTYPE			/*--계약구분*/												"
					+ "			,PROMTYPENAME		/*--계약구분명*/											"
					+ "			,CUSTOM				/*--거래처*/												"
					+ "			,CUSTOMNAME			/*--거래처명*/												"
					+ "			,WORKFROMDT			/*--수행기간 From*/											"
					+ "			,WORKTODT			/*--수행기간 to*/											"
					+ "			,ACPTTYPE																	"
					+ "			,ACPTDATE																	"
					+ "			,ACPTEMPNO																	"
					+ "			,CO_GRP				/*계열사 구분*/												"
					+ "			,INPUTEMP			/*입력 사번*/												"
					+ "			,INPUTDATE			/*입력일*/													"
					+ "			,UPDEMPNO			/*수정 사번*/												"
					+ "			,PROJECTSCALE		/*PJT SCALE */											"
					+ "			,UPDDATE			/*수정일*/													"
					+ "			,SALESAMT			/*금액*/													"
					+ "			,FUNC				/*ByPass 여부*/											"
					+ "			,BP_PROJID				/*ByPass 프로젝트코드*/									"
					+ "			,BP_ENTNO				/*ByPass ENTNO*/									"
					+ "			,REALWORKFROMDT				/*REALWORKFROMDT*/									"
					+ "			,REALWORKTODT				/*REALWORKTODT*/									"
					+ "FROM		DWPM.DBO.DW_PROJECTMST														"
					+ "WHERE	1=1																			"
					+ "			AND PRE_ENTNO IS NULL														"
					+ "			AND PROJID=?																");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected ErpProjectSelectQuery1(DataSource ds, String query) {
			super(ds, query);
		}

		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			Project project = new Project();
			project.setEntNo(rs.getString("ENTNO"));
			project.setProjectCode(rs.getString("PROJID"));
			project.setProjectName(rs.getString("PJTNAME"));
			project.setIndustryTypeCode(rs.getString("INDSTYPE"));
			project.setBusinessTypeCode(rs.getString("BIZTYPE"));
			project.setProjectTypeCode(rs.getString("OPTYPE"));
			project.setCioTypeCode(rs.getString("CIOCODE"));
			project.setRunningDivCode(rs.getString("BUCODE").substring(0, 3) + "0");
			project.setRunningDeptCode(rs.getString("BUCODE"));
			if (!rs.getString("PUCODE").equals("")) {				
				project.setRunningPuCode(rs.getString("PUCODE").substring(0, 3) + "0");
				project.setRunningGroupCode(rs.getString("PUCODE"));
			} else {
				project.setRunningPuCode("");
				project.setRunningGroupCode("");
			}
			project.setPreStartDate(rs.getString("WORKFROMDT"));
			project.setPreEndDate(rs.getString("WORKTODT"));
			project.setRealStartDate(rs.getString("WORKFROMDT"));
			project.setRealEndDate(rs.getString("WORKTODT"));
			project.setCustomerCode(rs.getString("CUSTOM"));
			project.setCustomerName(rs.getString("CUSTOMNAME"));
			project.setCoGRP(rs.getString("CO_GRP"));
			project.setProjectContractType(rs.getString("PROMTYPE"));
			project.setProjectScale(rs.getString("PROJECTSCALE"));
			if(rs.getString("SALESAMT") == null){
				project.setSalesAmt("0");
			}else{
				project.setSalesAmt(rs.getString("SALESAMT"));
			}
			project.setFunc(rs.getString("FUNC"));
			project.setBp_projId(rs.getString("BP_PROJID"));
			project.setBp_entNo(rs.getString("BP_ENTNO"));
			try{
			project.setRealWorkFromDt(rs.getString("REALWORKFROMDT"));
			project.setRealWorkToDt(rs.getString("REALWORKTODT"));
			}catch(Exception e){
				e.getMessage();
			}
			return project;
		}
	}	
	
	protected class ProjectBudgetSelectQuery extends MappingSqlQuery {
		protected ProjectBudgetSelectQuery(DataSource ds) {
			super(ds, " select top 1  dp.entno, dp.projid, dp.INPUTDATE, dt1.amt as amt1, dt2.amt as amt2, dt3.amt as amt3, dt4.amt as amt4, dt5.amt as amt5 "
					+ " from dwpm.dbo.dw_projectmst dp "
					+ " left join "
					+ " ( "
					+ " 	select entno, AMT "
					+ " 	from dwpm.dbo.dw_projectdtl "
					+ " 	where entno = (select top 1 entno from dwpm.dbo.dw_projectmst where projid=? order by inputdate asc) "
					+ " 	and acct_code = '4001009' "
					+ " )dt1 "
					+ " on dp.entno = dt1.entno "
					+ " left join "
					+ " ( "
					+ " 	select entno, AMT "
					+ " 	from dwpm.dbo.dw_projectdtl "
					+ " 	where entno = (select top 1 entno from dwpm.dbo.dw_projectmst where projid=? order by inputdate asc) "
					+ " 	and acct_code = '5009999' "
					+ " )dt2 "
					+ " on dp.entno = dt2.entno "
					+ " left join "
					+ " ( "
					+ " 	select entno, AMT "
					+ " 	from dwpm.dbo.dw_projectdtl "
					+ " 	where entno = (select top 1 entno from dwpm.dbo.dw_projectmst where projid=? order by inputdate asc) "
					+ " 	and acct_code = '5030009' "
					+ " )dt3 "
					+ " on dp.entno = dt3.entno "
					+ " left join "
					+ " ( "
					+ " 	select entno, AMT "
					+ " 	from dwpm.dbo.dw_projectdtl "
					+ " 	where entno = (select top 1 entno from dwpm.dbo.dw_projectmst where projid=? order by inputdate asc) "
					+ " 	and acct_code = '5999999' "
					+ " )dt4 "
					+ " on dp.entno = dt4.entno "
					+ " left join "
					+ " ( "
					+ " 	select entno, AMT "
					+ " 	from dwpm.dbo.dw_projectdtl "
					+ " 	where entno = (select top 1 entno from dwpm.dbo.dw_projectmst where projid=? order by inputdate asc) "
					+ " 	and acct_code = '6000001' "
					+ " )dt5 "
					+ " on dp.entno = dt5.entno "
					+ " where dp.projid=? " 
					+ " order by inputDate asc ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		@Override
		protected Object mapRow(ResultSet rs, int arg1) throws SQLException {
			ProjectBudget projectBudget = new ProjectBudget();
			projectBudget.setEntno(rs.getString("entno"));
			projectBudget.setProjid(rs.getString("projid"));
			projectBudget.setInputDate(rs.getString("inputDate"));
			projectBudget.setAmt1(rs.getString("amt1"));
			projectBudget.setAmt2(rs.getString("amt2"));
			projectBudget.setAmt3(rs.getString("amt3"));
			projectBudget.setAmt4(rs.getString("amt4"));
			projectBudget.setAmt5(rs.getString("amt5"));
			
			return projectBudget;
		}
	}

	@Override
	public Project select(String projectCode) throws DataAccessException {
		return (Project) this.erpProjectSelectQuery1.findObject(new Object[] { projectCode });
	}
	
	@Override
	public ProjectBudget selectBudget(String projectCode) throws DataAccessException {
		return (ProjectBudget) this.projectBudgetSelectQuery.findObject(new Object[] {projectCode,projectCode,projectCode,projectCode,projectCode,projectCode});
	}

	@Override
	public void deleteProjectRelatedEntNo(String seq) throws DataAccessException {
		this.entNoForSanctionDeleteQuery.delete(seq);
	}
}
