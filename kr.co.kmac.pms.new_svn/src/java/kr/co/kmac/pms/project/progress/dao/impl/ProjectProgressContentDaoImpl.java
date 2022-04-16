package kr.co.kmac.pms.project.progress.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import javax.sql.DataSource;

import kr.co.kmac.pms.project.progress.dao.ProjectProgressContentDao;
import kr.co.kmac.pms.project.progress.data.ProjectProgressContent;
import kr.co.kmac.pms.project.progress.form.ProjectProgressContentForm;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.jdbc.object.SqlUpdate;

public class ProjectProgressContentDaoImpl extends JdbcDaoSupport implements ProjectProgressContentDao {
	private ProjectProgressSelectQuery projectProgressSelectQuery;
	private ProjectProgressUpdateQuery projectProgressUpdateQuery;
	private ProjectProgressDeleteQuery projectProgressDeleteQuery;
	private ProjectProgressInsertQuery projectProgressInsertQuery;

	@Override
	protected void initDao() throws Exception {
		this.projectProgressSelectQuery = new ProjectProgressSelectQuery(getDataSource());
		this.projectProgressUpdateQuery = new ProjectProgressUpdateQuery(getDataSource());
		this.projectProgressDeleteQuery = new ProjectProgressDeleteQuery(getDataSource());
		this.projectProgressInsertQuery = new ProjectProgressInsertQuery(getDataSource());
	}

	protected class ProjectProgressInsertQuery extends SqlUpdate {
		protected ProjectProgressInsertQuery(DataSource ds) {
			super(ds, " INSERT INTO ProjectProgressContents( contentId, projectCode, taskName, "
					+ "                                      title, content, creatorId, createDate) "
					+ " VALUES(?,  ?,  ?,   ?,  ?,  ?,   getDate())");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));

			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));

			compile();
		}

		protected int insert(ProjectProgressContent content) throws DataAccessException {
			return this.update(new Object[] { content.getContentId(), content.getProjectCode(), content.getTaskName(), content.getTitle(),
					content.getContent(), content.getCreatorId() });
		}
	}

	protected class ProjectProgressDeleteQuery extends SqlUpdate {
		protected ProjectProgressDeleteQuery(DataSource ds) {
			super(ds, "Delete from ProjectProgressContents where projectCode = ? and contentId = ?");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));

			compile();
		}

		protected int delete(String projectCode, String contentId) throws DataAccessException {
			return this.update(new Object[] { projectCode, contentId });
		}
	}

	protected class ProjectProgressUpdateQuery extends SqlUpdate {
		protected ProjectProgressUpdateQuery(DataSource ds) {
			super(ds, " UPDATE ProjectProgressContents SET taskName=?, title=?,          "
					+ "                                    content=?, creatorId=? , createDate=getDate()"
					+ " WHERE projectCode = ?  and  contentId = ?                                                             ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));

			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));

			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));

			compile();
		}

		protected int update(ProjectProgressContent content) throws DataAccessException {
			return this.update(new Object[] { content.getTaskName(), content.getTitle(), content.getContent(), content.getCreatorId(),
					content.getProjectCode(), content.getContentId() });
		}
	}

	protected class ProjectProgressSelectQuery extends MappingSqlQuery {

		protected ProjectProgressSelectQuery(DataSource ds) {
			super(ds, "SELECT * FROM ProjectProgressContents where projectCode=? and contentId = ?");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected ProjectProgressSelectQuery(DataSource ds, String query) {
			super(ds, query);
		}

		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			ProjectProgressContent content = new ProjectProgressContentForm();
			content.setContentId(rs.getString("contentId"));
			content.setProjectCode(rs.getString("ProjectCode"));
			content.setTaskName(rs.getString("TaskName"));
			content.setTitle(rs.getString("title"));
			content.setContent(rs.getString("content"));
			content.setCreatorId(rs.getString("creatorId"));
			content.setCreateDate(rs.getDate("createDate"));

			return content;
		}

	}

	@Override
	public void delete(String projectCode, String contentId) throws DataAccessException {
		this.projectProgressDeleteQuery.delete(projectCode, contentId);
	}

	@Override
	public void insert(ProjectProgressContent projectProgressContent) throws DataAccessException {
		this.projectProgressInsertQuery.insert(projectProgressContent);
	}

	@Override
	public ProjectProgressContent select(String projectCode, String contentId) throws DataAccessException {
		return (ProjectProgressContent) this.projectProgressSelectQuery.findObject(new Object[] { projectCode, contentId });
	}

	@Override
	public void update(ProjectProgressContent projectProgressContent) throws DataAccessException {
		this.projectProgressUpdateQuery.update(projectProgressContent);
	}

}
