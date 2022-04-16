package kr.co.kmac.pms.common.worklist.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import javax.sql.DataSource;

import kr.co.kmac.pms.common.worklist.dao.WorkTypeDao;
import kr.co.kmac.pms.common.worklist.data.WorkType;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.jdbc.object.SqlUpdate;

public class WorkTypeDaoImpl extends JdbcDaoSupport implements WorkTypeDao {
	private WorkTypeInsertQuery workTypeInsertQuery;
	private WorkTypeUpdateQuery workTypeUpdateQuery;
	private WorkTypeSelectQuery workTypeSelectQuery;
	private WorkTypeDeleteQuery workTypeDeleteQuery;

	@Override
	protected void initDao() throws Exception {
		this.workTypeInsertQuery = new WorkTypeInsertQuery(getDataSource());
		this.workTypeUpdateQuery = new WorkTypeUpdateQuery(getDataSource());
		this.workTypeSelectQuery = new WorkTypeSelectQuery(getDataSource());
		this.workTypeDeleteQuery = new WorkTypeDeleteQuery(getDataSource());
	}

	protected class WorkTypeInsertQuery extends SqlUpdate {
		protected WorkTypeInsertQuery(DataSource ds) {
			super(ds, " INSERT INTO WorkType (id, name, formUrl, description) VALUES (?, ?, ?, ?)");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
			setRequiredRowsAffected(1);
		}

		protected int insert(WorkType workType) throws DataAccessException {
			return this.update(new Object[] { workType.getId(), workType.getName(), workType.getFormUrl(), workType.getDescription() });
		}
	}

	protected class WorkTypeUpdateQuery extends SqlUpdate {
		protected WorkTypeUpdateQuery(DataSource ds) {
			super(ds, " UPDATE WorkType SET name=?, formUrl=?, description=? WHERE id=? ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
			setRequiredRowsAffected(1);
		}

		protected int update(WorkType workType) throws DataAccessException {
			return this.update(new Object[] { workType.getName(), workType.getFormUrl(), workType.getDescription(), workType.getId() });
		}
	}

	protected class WorkTypeDeleteQuery extends SqlUpdate {
		protected WorkTypeDeleteQuery(DataSource ds) {
			super(ds, " DELETE FROM WorkType WHERE  id=? ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
			setRequiredRowsAffected(1);
		}

		protected int delete(String id) throws DataAccessException {
			return this.update(new Object[] { id });
		}
	}

	protected class WorkTypeSelectQuery extends MappingSqlQuery {
		protected WorkTypeSelectQuery(DataSource ds) {
			super(ds, "	SELECT 	* FROM WorkType WHERE id=? ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected WorkTypeSelectQuery(DataSource ds, String query) {
			super(ds, query);
		}

		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			WorkType workType = new WorkType();
			workType.setId(rs.getString("id"));
			workType.setName(rs.getString("name"));
			workType.setFormUrl(rs.getString("formUrl"));
			workType.setDescription(rs.getString("description"));
			workType.setUseMobile(rs.getString("useMobile"));
			return workType;
		}
	}

	@Override
	public void createWorkType(WorkType workType) throws DataAccessException {
		this.workTypeInsertQuery.insert(workType);
	}

	@Override
	public void deleteWorkType(String id) throws DataAccessException {
		this.workTypeDeleteQuery.delete(id);
	}

	@Override
	public WorkType getWorkType(String id) throws DataAccessException {
		return (WorkType) this.workTypeSelectQuery.findObject(new Object[] { id });
	}

	@Override
	public void updateWorkType(WorkType workType) throws DataAccessException {
		this.workTypeUpdateQuery.update(workType);
	}

}
