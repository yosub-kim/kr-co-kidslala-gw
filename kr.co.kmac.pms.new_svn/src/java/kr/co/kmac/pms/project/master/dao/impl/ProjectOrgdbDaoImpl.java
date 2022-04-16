package kr.co.kmac.pms.project.master.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import javax.sql.DataSource;

import kr.co.kmac.pms.project.master.dao.ProjectOrgdbDao;
import kr.co.kmac.pms.project.master.data.ProjectOrgdb;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.object.BatchSqlUpdate;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.jdbc.object.SqlUpdate;

/**
 * @author hyunseong
 * @jobDate &2008. 6. 10.
 * @description : PMS 프로젝트 기본정보에 협력기관 등록을 위한 DaoImpl
 */
public class ProjectOrgdbDaoImpl extends JdbcDaoSupport implements ProjectOrgdbDao {
	private InsertProjectOrgdb insertProjectOrgdb;
	private DeleteProjectOrgdb deleteProjectOrgdb;
	private SeleteProjectOrgdb selectProjectOrgdb;

	protected void initDao() throws Exception {
		this.insertProjectOrgdb = new InsertProjectOrgdb(getDataSource());
		this.deleteProjectOrgdb = new DeleteProjectOrgdb(getDataSource());
		this.selectProjectOrgdb = new SeleteProjectOrgdb(getDataSource());
	}
	
	@SuppressWarnings("unchecked")
	public List<ProjectOrgdb> getProjectOrgdb(String projectCode) throws DataAccessException {
		return this.selectProjectOrgdb.execute(new Object[]{projectCode});
	}
	protected class SeleteProjectOrgdb extends MappingSqlQuery {
		protected SeleteProjectOrgdb(DataSource ds) {
			super(ds, "	SELECT	p.projectCode, p.idx, p.orgCode," 
					+ "					(case when p.orgCode is null or p.orgCode = '' then p.orgName else o.name end) orgName" 
					+ "		FROM ProjectOrgdbRel p" 
					+ "		FULL OUTER JOIN OrgdbMain o" 
					+ "		ON p.orgCode = o.orgCode" 
					+ "		WHERE projectCode = ? ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}
		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			ProjectOrgdb projectOrgdb = new ProjectOrgdb();
			projectOrgdb.setIdx(rs.getString("idx"));
			projectOrgdb.setProjectCode(rs.getString("projectCode"));
			projectOrgdb.setOrgCode(rs.getString("orgCode"));
			projectOrgdb.setOrgName(rs.getString("orgName"));
			return projectOrgdb;
		}
	}
	
	public void setProjectOrgdb(String projectCode, String[] orgCode, String[] orgName) {
		this.insertProjectOrgdb.insert(projectCode, orgCode, orgName);
	}
	protected class InsertProjectOrgdb extends BatchSqlUpdate {
		protected InsertProjectOrgdb(DataSource ds) {
			super(ds, " INSERT INTO ProjectOrgdbRel (idx, projectCode, orgCode, orgName) "
					+ "                      VALUES (?, ?, ?, ?)");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}
		protected void insert(String projectCode, String[] orgCode, String[] orgName) {
			for (int i = 0; i < orgCode.length; i++) {
				this.update(new Object[] {
						String.valueOf(i), projectCode, orgCode[i].trim(), orgName[i].trim()
				});
			}
			this.flush();
		}
	}
	
	public void deleteProjectOrgdb(String projectCode) {
		this.deleteProjectOrgdb.delete(projectCode);
	}
	protected class DeleteProjectOrgdb extends SqlUpdate {
		protected DeleteProjectOrgdb(DataSource ds) {
			super(ds, " DELETE FROM ProjectOrgdbRel WHERE projectCode = ? ");
			declareParameter(new SqlParameter(Types.VARCHAR)); 
			compile();
		}
		protected int delete(String projectCode) {
			return this.update(new Object[]{projectCode});
		}
	}
}