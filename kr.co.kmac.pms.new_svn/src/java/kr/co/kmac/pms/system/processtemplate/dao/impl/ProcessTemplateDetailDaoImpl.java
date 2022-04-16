package kr.co.kmac.pms.system.processtemplate.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import javax.sql.DataSource;

import kr.co.kmac.pms.system.processtemplate.dao.ProcessTemplateDetailDao;
import kr.co.kmac.pms.system.processtemplate.data.ProcessTemplateDetail;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.jdbc.object.SqlUpdate;

public class ProcessTemplateDetailDaoImpl extends JdbcDaoSupport implements ProcessTemplateDetailDao {
	private ProcessTemplateDetailSelect1 processTemplateDetailSelect1;
	private ProcessTemplateDetailSelect2 processTemplateDetailSelect2;
	private ProcessTemplateDetailCreate processTemplateDetailCreate;
	private ProcessTemplateDetailModify processTemplateDetailModify;
	private ProcessTemplateDetailRemove1 processTemplateDetailRemove1;
	private ProcessTemplateDetailRemove2 processTemplateDetailRemove2;

	protected class ProcessTemplateDetailSelect1 extends MappingSqlQuery {
		protected ProcessTemplateDetailSelect1(DataSource ds, String query) {
			super(ds, query);
		}

		protected ProcessTemplateDetailSelect1(DataSource ds) {
			super(ds, " select *, (select processTemplateName from processTemplate a                          "
					+ "             where a.processTemplateCode=b.processTemplateCode) as processTemplateName "
					+ " from processTemplateDetail b where processTemplateCode = ?                            ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			ProcessTemplateDetail processTemplateDetail = new ProcessTemplateDetail();
			processTemplateDetail.setProcessTemplateCode(rs.getString("processTemplateCode"));
			processTemplateDetail.setProcessTemplateName(rs.getString("processTemplateName"));
			processTemplateDetail.setWorkSeq(rs.getInt("workSeq"));
			processTemplateDetail.setActivityName(rs.getString("activityName"));
			processTemplateDetail.setLevel(rs.getInt("level"));
			processTemplateDetail.setParentWorkSeq(rs.getInt("parentWorkSeq"));
			processTemplateDetail.setEditable(rs.getBoolean("editable"));
			processTemplateDetail.setNecessary(rs.getBoolean("necessary"));
			return processTemplateDetail;
		}
	}

	protected class ProcessTemplateDetailSelect2 extends ProcessTemplateDetailSelect1 {
		protected ProcessTemplateDetailSelect2(DataSource ds, String query) {
			super(ds, query);
		}

		protected ProcessTemplateDetailSelect2(DataSource ds) {
			super(ds, " select *, (select processTemplateName from processTemplate a "
					+ "             where a.processTemplateCode=b.processTemplateCode) as processTemplateName "
					+ " from processTemplateDetail b where processTemplateCode=? and workSeq=?                ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}
	}

	protected class ProcessTemplateDetailCreate extends SqlUpdate {
		protected ProcessTemplateDetailCreate(DataSource ds) {
			super(ds, " insert into processTemplateDetail ( processTemplateCode, workSeq, activityName, level, parentWorkSeq, editable, necessary )"
					+ " values(?, ?, ?, ?, ?, ?, ? ) ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.INTEGER));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.BOOLEAN));
			declareParameter(new SqlParameter(Types.BOOLEAN));
			compile();
		}

		protected int create(ProcessTemplateDetail processTemplateDetail) throws DataAccessException {
			return this.update(new Object[] { processTemplateDetail.getProcessTemplateCode(), processTemplateDetail.getWorkSeq(),
					processTemplateDetail.getActivityName(), processTemplateDetail.getLevel(), processTemplateDetail.getParentWorkSeq(),
					processTemplateDetail.isEditable(), processTemplateDetail.isNecessary() });
		}
	}

	protected class ProcessTemplateDetailModify extends SqlUpdate {
		protected ProcessTemplateDetailModify(DataSource ds) {
			super(ds, " update processTemplateDetail set activityName=?, level=?, parentWorkSeq=?, editable=?, necessary=? "
					+ " where processTemplateCode=? and workSeq=?                                       ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.BOOLEAN));
			declareParameter(new SqlParameter(Types.BOOLEAN));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.INTEGER));
			compile();
		}

		protected int update(ProcessTemplateDetail processTemplateDetail) throws DataAccessException {
			return this.update(new Object[] { processTemplateDetail.getActivityName(), processTemplateDetail.getLevel(),
					processTemplateDetail.getParentWorkSeq(), processTemplateDetail.isEditable(), processTemplateDetail.isNecessary(),
					processTemplateDetail.getProcessTemplateCode(), processTemplateDetail.getWorkSeq() });
		}
	}

	protected class ProcessTemplateDetailRemove1 extends SqlUpdate {
		protected ProcessTemplateDetailRemove1(DataSource ds) {
			super(ds, " Delete from processTemplateDetail where processTemplateCode=? ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected int delete(String processTemplateCode) {
			return update(new Object[] { processTemplateCode });
		}
	}

	protected class ProcessTemplateDetailRemove2 extends SqlUpdate {
		protected ProcessTemplateDetailRemove2(DataSource ds) {
			super(ds, " Delete from processTemplateDetail where processTemplateCode=? and workSeq=? ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.INTEGER));
			compile();
		}

		protected int delete(String processTemplateCode, String workSeq) {
			return update(new Object[] { processTemplateCode, workSeq });
		}
	}

	protected void initDao() throws Exception {
		processTemplateDetailSelect1 = new ProcessTemplateDetailSelect1(getDataSource());
		processTemplateDetailSelect2 = new ProcessTemplateDetailSelect2(getDataSource());
		processTemplateDetailCreate = new ProcessTemplateDetailCreate(getDataSource());
		processTemplateDetailModify = new ProcessTemplateDetailModify(getDataSource());
		processTemplateDetailRemove1 = new ProcessTemplateDetailRemove1(getDataSource());
		processTemplateDetailRemove2 = new ProcessTemplateDetailRemove2(getDataSource());
	}

	@Override
	public void createProcessTemplateDetail(ProcessTemplateDetail processTemplateDetail) throws DataAccessException {
		this.processTemplateDetailCreate.create(processTemplateDetail);
	}

	@Override
	public void deleteProcessTemplateDetail(String processTemplateCode) throws DataAccessException {
		this.processTemplateDetailRemove1.delete(processTemplateCode);
	}

	@Override
	public void deleteProcessTemplateDetail(String processTemplateCode, String workSeq) throws DataAccessException {
		this.processTemplateDetailRemove2.delete(processTemplateCode, workSeq);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProcessTemplateDetail> getProcessTemplateDetail(String processTemplateCode) throws DataAccessException {
		return this.processTemplateDetailSelect1.execute(new Object[] { processTemplateCode });
	}

	@Override
	public ProcessTemplateDetail getProcessTemplateDetail(String processTemplateCode, String workSeq) throws DataAccessException {
		return (ProcessTemplateDetail) this.processTemplateDetailSelect2.findObject(new Object[] { processTemplateCode, workSeq });
	}

	@Override
	public void updateProcessTemplateDetail(ProcessTemplateDetail processTemplateDetail) throws DataAccessException {
		this.processTemplateDetailModify.update(processTemplateDetail);
	}

}
