package kr.co.kmac.pms.project.master.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import javax.sql.DataSource;

import kr.co.kmac.pms.project.master.dao.ProjectCsrInfoDao;
import kr.co.kmac.pms.project.master.data.ProjectCsrInfo;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.jdbc.object.SqlUpdate;

public class ProjectCsrInfoDaoImpl extends JdbcDaoSupport implements ProjectCsrInfoDao {
	private ProjectCsrInfoInsertQuery projectCsrInfoInsertQuery;
	private ProjectCsrInfoUpdateQuery projectCsrInfoUpdateQuery;
	private ProjectCsrInfoUpdateQuery2 projectCsrInfoUpdateQuery2;
	private ProjectCsrInfoSelectQuery0 projectCsrInfoSelectQuery0;
	private ProjectCsrInfoSelectQuery1 projectCsrInfoSelectQuery1;
	private ProjectCsrInfoSelectQuery2 projectCsrInfoSelectQuery2;
	private ProjectCsrInfoSelectQuery3 projectCsrInfoSelectQuery3;
	private ProjectCsrInfoDeleteQuery1 projectCsrInfoDeleteQuery1;
	private ProjectCsrInfoDeleteQuery2 projectCsrInfoDeleteQuery2;
	private ProjectCsrInfoDeleteQuery3 projectCsrInfoDeleteQuery3;
	private ProjectThxInfoInsertQuery projectThxInfoInsertQuery;

	private DataSource intranetEduDataSource;

	public DataSource getIntranetEduDataSource() {
		return intranetEduDataSource;
	}

	public void setIntranetEduDataSource(DataSource intranetEduDataSource) {
		this.intranetEduDataSource = intranetEduDataSource;
	}

	@Override
	protected void initDao() throws Exception {
		this.projectThxInfoInsertQuery = new ProjectThxInfoInsertQuery(getDataSource());
		this.projectCsrInfoInsertQuery = new ProjectCsrInfoInsertQuery(getDataSource());
		this.projectCsrInfoUpdateQuery = new ProjectCsrInfoUpdateQuery(getDataSource());
		this.projectCsrInfoUpdateQuery2 = new ProjectCsrInfoUpdateQuery2(getDataSource());
		this.projectCsrInfoDeleteQuery1 = new ProjectCsrInfoDeleteQuery1(getDataSource());
		this.projectCsrInfoDeleteQuery2 = new ProjectCsrInfoDeleteQuery2(getDataSource());
		this.projectCsrInfoDeleteQuery3 = new ProjectCsrInfoDeleteQuery3(getDataSource());
		this.projectCsrInfoSelectQuery0 = new ProjectCsrInfoSelectQuery0(getDataSource());
		this.projectCsrInfoSelectQuery1 = new ProjectCsrInfoSelectQuery1(getDataSource());
		this.projectCsrInfoSelectQuery2 = new ProjectCsrInfoSelectQuery2(getDataSource());
		this.projectCsrInfoSelectQuery3 = new ProjectCsrInfoSelectQuery3(getDataSource());
	}

	protected class ProjectCsrInfoInsertQuery extends SqlUpdate {
		protected ProjectCsrInfoInsertQuery(DataSource ds) {
			super(ds, "INSERT INTO ProjectCsrInfo                                               "
					+ "	(projectCode                                                            "
					+ "	,customerName, customerCode, customerWorkPName, customerWorkPEmail, customerWorkPPhone "
					+ " , isEvaluate, isVoc, isRep)                                                            "
					+ "	VALUES(?,     ?, ?, ?, ?, ?,  ?, ?, ? )                       ");
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

		protected int insert(ProjectCsrInfo projectCsrInfo) throws DataAccessException {
			return this.update(new Object[] { projectCsrInfo.getProjectCode(), projectCsrInfo.getCustomerName(), projectCsrInfo.getCustomerCode(),
					projectCsrInfo.getCustomerWorkPName(), projectCsrInfo.getCustomerWorkPEmail(), projectCsrInfo.getCustomerWorkPPhone(),
					projectCsrInfo.getIsEvaluatel(), projectCsrInfo.getIsVoc(),  projectCsrInfo.getIsRep()});
		}
	}

	protected class ProjectThxInfoInsertQuery extends SqlUpdate {
		protected ProjectThxInfoInsertQuery(DataSource ds) {
			super(ds, " INSERT INTO ThanksMail (DeptCode,DeptName,projectCode,p_name,  company,customer,email,sendDate)"
					+ " VALUES( ?, dbo.getDeptName(?), ?, ?,     ?, ?, ?, getdate())");
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

		protected int insert(ProjectCsrInfo projectCsrInfo) throws DataAccessException {
			return this.update(new Object[] { projectCsrInfo.getRunningDivCode(), projectCsrInfo.getRunningDivCode(),
					projectCsrInfo.getProjectCode(), projectCsrInfo.getProjectName(),

					projectCsrInfo.getCustomerName(), projectCsrInfo.getCustomerWorkPName(), projectCsrInfo.getCustomerWorkPEmail(), });
		}
	}

	protected class ProjectCsrInfoUpdateQuery extends SqlUpdate {
		protected ProjectCsrInfoUpdateQuery(DataSource ds) {
			super(ds, "UPDATE ProjectCsrInfo SET surveySendDate = getDate() WHERE projectCode=? and customerWorkPEmail=? ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));

			compile();
			//setRequiredRowsAffected(1);
		}

		protected int update(ProjectCsrInfo projectCsrInfo) throws DataAccessException {
			return this.update(new Object[] { projectCsrInfo.getProjectCode(), projectCsrInfo.getCustomerWorkPEmail() });
		}
	}
	protected class ProjectCsrInfoUpdateQuery2 extends SqlUpdate {
		protected ProjectCsrInfoUpdateQuery2(DataSource ds) {
			super(ds, " UPDATE projectCsrInfo	"
					+ " SET customerCode=?, customerName=?, customerWorkPName=?, customerWorkPEmail=?, "
					+ " 	customerWorkPPhone=?, customerContPName=?, customerContPEmail=?, customerContPPhone=? "
					+ " WHERE seq = ?");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));

			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			
			declareParameter(new SqlParameter(Types.INTEGER));
			
			compile();
		}
		
		protected int update(ProjectCsrInfo projectCsrInfo) throws DataAccessException {
			return this.update(new Object[] { projectCsrInfo.getCustomerCode(), projectCsrInfo.getCustomerName(),
					projectCsrInfo.getCustomerWorkPName(), projectCsrInfo.getCustomerWorkPEmail(), projectCsrInfo.getCustomerWorkPPhone(),
					projectCsrInfo.getCustomerContPName(), projectCsrInfo.getCustomerContPEmail(), projectCsrInfo.getCustomerContPPhone(),
					projectCsrInfo.getSeq() });
		}
	}

	protected class ProjectCsrInfoDeleteQuery1 extends SqlUpdate {
		protected ProjectCsrInfoDeleteQuery1(DataSource ds) {
			super(ds, " DELETE FROM ProjectCsrInfo WHERE projectCode=? and (isRep='N' or isRep is null)");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected int delete(String projectCode) throws DataAccessException {
			return this.update(new Object[] { projectCode });
		}
	}

	protected class ProjectCsrInfoDeleteQuery2 extends SqlUpdate {
		protected ProjectCsrInfoDeleteQuery2(DataSource ds) {
			super(ds, " DELETE FROM ProjectCsrInfo WHERE projectCode=? and seq=?");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.INTEGER));
			compile();
			setRequiredRowsAffected(1);
		}

		protected int delete(String projectCode, int seq) throws DataAccessException {
			return this.update(new Object[] { projectCode, seq });
		}
	}

	protected class ProjectCsrInfoDeleteQuery3 extends SqlUpdate {
		protected ProjectCsrInfoDeleteQuery3(DataSource ds) {
			super(ds, " DELETE FROM ProjectCsrInfo WHERE projectCode=? and isRep='Y' ");
			declareParameter(new SqlParameter(Types.INTEGER));
			compile();
			//setRequiredRowsAffected(1);
		}

		protected int delete(String projectCode) throws DataAccessException {
			return this.update(new Object[] { projectCode });
		}
	}

	protected class ProjectCsrInfoSelectQuery1 extends MappingSqlQuery {
		protected ProjectCsrInfoSelectQuery1(DataSource ds) {
			super(ds, "	SELECT 	* FROM ProjectCsrInfo where projectCode=? and (isRep='N' or isRep is null)");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected ProjectCsrInfoSelectQuery1(DataSource ds, String query) {
			super(ds, query);
		}

		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			ProjectCsrInfo projectCsrInfo = new ProjectCsrInfo();
			projectCsrInfo.setSeq(rs.getInt("seq"));
			projectCsrInfo.setProjectCode(rs.getString("projectCode"));
			projectCsrInfo.setIsRep(rs.getString("isRep"));
			projectCsrInfo.setCustomerName(rs.getString("customerName"));
			projectCsrInfo.setCustomerCode(rs.getString("customerCode"));
			projectCsrInfo.setCustomerWorkPName(rs.getString("customerWorkPName"));
			projectCsrInfo.setCustomerWorkPEmail(rs.getString("customerWorkPEmail"));
			projectCsrInfo.setCustomerWorkPPhone(rs.getString("customerWorkPPhone"));
			projectCsrInfo.setCustomerContPName(rs.getString("customerContPName"));
			projectCsrInfo.setCustomerContPEmail(rs.getString("customerContPEmail"));
			projectCsrInfo.setCustomerContPPhone(rs.getString("customerContPPhone"));
			// projectCsrInfo.setIsEvaluatel(rs.getString("isEvaluate"));
			return projectCsrInfo;
		}
	}

	protected class ProjectCsrInfoSelectQuery0 extends ProjectCsrInfoSelectQuery1 {
		protected ProjectCsrInfoSelectQuery0(DataSource ds) {
			super(ds, "	SELECT 	* FROM ProjectCsrInfo WHERE projectCode=? ");
			declareParameter(new SqlParameter(Types.INTEGER));
			compile();
		}
	}

	protected class ProjectCsrInfoSelectQuery2 extends ProjectCsrInfoSelectQuery1 {
		protected ProjectCsrInfoSelectQuery2(DataSource ds) {
			//super(ds, "	SELECT 	* FROM ProjectCsrInfo WHERE projectCode=? and seq=? and (isRep='N' or isRep is null)");
			super(ds, "	SELECT 	* FROM ProjectCsrInfo WHERE projectCode=? and seq=? ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.INTEGER));
			compile();
		}
	}

	protected class ProjectCsrInfoSelectQuery3 extends ProjectCsrInfoSelectQuery1 {
		protected ProjectCsrInfoSelectQuery3(DataSource ds) {
			super(ds, "	SELECT 	* FROM ProjectCsrInfo WHERE projectCode=? and isRep='Y' ");
			declareParameter(new SqlParameter(Types.INTEGER));
			compile();
		}
	}

	@Override
	public ProjectCsrInfo getRepCustomer(String projectCode) throws DataAccessException {
		return (ProjectCsrInfo) this.projectCsrInfoSelectQuery3.findObject(new Object[] { projectCode });
	}

	@Override
	public ProjectCsrInfo select(String projectCode, int seq) throws DataAccessException {
		return (ProjectCsrInfo) this.projectCsrInfoSelectQuery2.findObject(new Object[] { projectCode, seq });
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProjectCsrInfo> selectAll(String projectCode) throws DataAccessException {
		return this.projectCsrInfoSelectQuery0.execute(new Object[] { projectCode });
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProjectCsrInfo> select(String projectCode) throws DataAccessException {
		return this.projectCsrInfoSelectQuery1.execute(new Object[] { projectCode });
	}

	@Override
	public void insert(ProjectCsrInfo projectCsrInfo) throws DataAccessException {
		this.projectCsrInfoInsertQuery.insert(projectCsrInfo);
	}

	@Override
	public void insert(List<ProjectCsrInfo> projectCsrInfoList) throws DataAccessException {
		for (ProjectCsrInfo projectCsrInfo : projectCsrInfoList) {
			this.projectCsrInfoInsertQuery.insert(projectCsrInfo);
		}
	}

	@Override
	public void delete(String projectCode) throws DataAccessException {
		this.projectCsrInfoDeleteQuery1.delete(projectCode);
	}

	@Override
	public void delete(String projectCode, int seq) throws DataAccessException {
		this.projectCsrInfoDeleteQuery2.delete(projectCode, seq);
	}

	@Override
	public void deleteRepCustomer(String projectCode) throws DataAccessException {
		this.projectCsrInfoDeleteQuery3.delete(projectCode);
	}

	@Override
	public void update(ProjectCsrInfo projectCsrInfo) throws DataAccessException {
		this.projectCsrInfoUpdateQuery.update(projectCsrInfo);
	}

	@Override
	public int updateObj(ProjectCsrInfo projectCsrInfo) throws DataAccessException {
		return this.projectCsrInfoUpdateQuery2.update(projectCsrInfo);		
	}

	@Override
	public void insertThxInfo(ProjectCsrInfo projectCsrInfo) throws DataAccessException {
		this.projectThxInfoInsertQuery.insert(projectCsrInfo);
	}

}