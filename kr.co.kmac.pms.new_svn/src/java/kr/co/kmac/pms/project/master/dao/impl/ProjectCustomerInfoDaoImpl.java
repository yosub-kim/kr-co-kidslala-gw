package kr.co.kmac.pms.project.master.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import javax.sql.DataSource;

import kr.co.kmac.pms.project.master.dao.ProjectCustomerInfoDao;
import kr.co.kmac.pms.project.master.data.ProjectCustomerInfo;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.jdbc.object.SqlUpdate;

public class ProjectCustomerInfoDaoImpl extends JdbcDaoSupport implements ProjectCustomerInfoDao {
	private ProjectCustomerInfoInsertQuery projectCustomerInfoInsertQuery;
	private ProjectCustomerInfoSelectQuery1 projectCustomerInfoSelectQuery1;
	// private ProjectCustomerInfoSelectQuery2 projectCustomerInfoSelectQuery2;
	private ProjectCustomerInfoDeleteQuery1 projectCustomerInfoDeleteQuery1;
	private ProjectCustomerInfoDeleteQuery2 projectCustomerInfoDeleteQuery2;

	@Override
	protected void initDao() throws Exception {
		this.projectCustomerInfoInsertQuery = new ProjectCustomerInfoInsertQuery(getDataSource());
		this.projectCustomerInfoSelectQuery1 = new ProjectCustomerInfoSelectQuery1(getDataSource());
		// this.projectCustomerInfoSelectQuery2 = new ProjectCustomerInfoSelectQuery2(getDataSource());
		this.projectCustomerInfoDeleteQuery1 = new ProjectCustomerInfoDeleteQuery1(getDataSource());
		this.projectCustomerInfoDeleteQuery2 = new ProjectCustomerInfoDeleteQuery2(getDataSource());
	}

	protected class ProjectCustomerInfoInsertQuery extends SqlUpdate {
		protected ProjectCustomerInfoInsertQuery(DataSource ds) {
			super(ds, " INSERT INTO ProjectCustomerInfo (projectCode, customerCodeIdx, createUserSsn, createDate) "
					+ " VALUES (?, ?, ?, getDAte())                                                   ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
			setRequiredRowsAffected(1);
		}

		protected int insert(ProjectCustomerInfo projectCustomerInfo) throws DataAccessException {
			return this.update(new Object[] { projectCustomerInfo.getProjectCode(), projectCustomerInfo.getCustomerInfoIdx(), projectCustomerInfo.getCreateUserSsn() });
		}
	}

	protected class ProjectCustomerInfoDeleteQuery1 extends SqlUpdate {
		protected ProjectCustomerInfoDeleteQuery1(DataSource ds) {
			super(ds, " DELETE FROM ProjectCustomerInfo WHERE projectCode=? ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected int delete(String projectCode) throws DataAccessException {
			return this.update(new Object[] { projectCode });
		}
	}

	protected class ProjectCustomerInfoDeleteQuery2 extends SqlUpdate {
		protected ProjectCustomerInfoDeleteQuery2(DataSource ds) {
			super(ds, " DELETE FROM ProjectCustomerInfo WHERE projectCode=? and seq=?");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.INTEGER));
			compile();
			setRequiredRowsAffected(1);
		}

		protected int delete(String projectCode, int seq) throws DataAccessException {
			return this.update(new Object[] { projectCode, seq });
		}
	}

	protected class ProjectCustomerInfoSelectQuery1 extends MappingSqlQuery {
		protected ProjectCustomerInfoSelectQuery1(DataSource ds) {
			super(ds, "	select	p.seq, p.projectCode, p.customerCodeIdx,   "
					+ " 		c.customerInfoType, c.subject, c.writer, c.regDate, p.createDate, "
					+ " 		p.createUserSsn, e.name createUserName "
					+ " from ProjectCustomerInfo p inner join customer c " 
					+ " on p.customerCodeIdx = c.idx "
					+ " inner join expertPool e "
					+ " on p.createUserSsn = e.ssn "
					+ " where p.projectCode=? ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected ProjectCustomerInfoSelectQuery1(DataSource ds, String query) {
			super(ds, query);
		}

		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			ProjectCustomerInfo projectCustomerInfo = new ProjectCustomerInfo();
			projectCustomerInfo.setSeq(rs.getInt("seq"));
			projectCustomerInfo.setProjectCode(rs.getString("projectCode"));
			projectCustomerInfo.setCustomerInfoType(rs.getString("customerInfoType"));
			projectCustomerInfo.setCustomerInfoIdx(rs.getString("customerCodeIdx"));
			projectCustomerInfo.setSubject(rs.getString("subject"));
			projectCustomerInfo.setWriter(rs.getString("writer"));
			projectCustomerInfo.setRegDate(rs.getDate("regDate"));
			projectCustomerInfo.setCreateUserName(rs.getString("createUserName"));
			projectCustomerInfo.setCreateDate(rs.getDate("createDate"));
			return projectCustomerInfo;
		}
	}

	// protected class ProjectCustomerInfoSelectQuery2 extends ProjectCustomerInfoSelectQuery1 {
	// protected ProjectCustomerInfoSelectQuery2(DataSource ds) {
	// super(ds, "	SELECT 	* FROM ProjectCustomerInfo WHERE projectCode=? and seq=? ");
	// declareParameter(new SqlParameter(Types.VARCHAR));
	// declareParameter(new SqlParameter(Types.INTEGER));
	// compile();
	// }
	// }

	@Override
	public ProjectCustomerInfo select(String projectCode, int seq) throws DataAccessException {
		return null;// (ProjectCustomerInfo) this.projectCustomerInfoSelectQuery2.findObject(new Object[] { projectCode, String.valueOf(seq) });
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProjectCustomerInfo> select(String projectCode) throws DataAccessException {
		return this.projectCustomerInfoSelectQuery1.execute(new Object[] { projectCode });
	}

	@Override
	public void insert(ProjectCustomerInfo projectCustomerInfo) throws DataAccessException {
		this.projectCustomerInfoInsertQuery.insert(projectCustomerInfo);
	}

	@Override
	public void insert(List<ProjectCustomerInfo> projectCsrInfoList) throws DataAccessException {
		for (ProjectCustomerInfo projectCustomerInfo : projectCsrInfoList) {
			this.insert(projectCustomerInfo);
		}
	}

	@Override
	public void delete(String projectCode) throws DataAccessException {
		this.projectCustomerInfoDeleteQuery1.delete(projectCode);
	}

	@Override
	public void delete(String projectCode, int seq) throws DataAccessException {
		this.projectCustomerInfoDeleteQuery2.delete(projectCode, seq);
	}

}