package kr.co.kmac.pms.system.processtemplate.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import javax.sql.DataSource;

import kr.co.kmac.pms.system.processtemplate.dao.ProcessTemplateDao;
import kr.co.kmac.pms.system.processtemplate.data.ProcessTemplate;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.jdbc.object.SqlUpdate;

public class ProcessTemplateDaoImpl extends JdbcDaoSupport implements ProcessTemplateDao {
	private ProcessTemplateSelect1 processTemplateSelect1;
	private ProcessTemplateSelect2 processTemplateSelect2;
	private ProcessTemplateSelect3 processTemplateSelect3;
	private ProcessTemplateCreate processTemplateCreate;
	private ProcessTemplateModify processTemplateModify;
	private ProcessTemplateRemove processTemplateRemove;

	protected class ProcessTemplateSelect1 extends MappingSqlQuery {
		protected ProcessTemplateSelect1(DataSource ds, String query) {
			super(ds, query);
		}

		protected ProcessTemplateSelect1(DataSource ds) {
			super(ds, " SELECT * from ProcessTemplate");
			compile();
		}

		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			ProcessTemplate processTemplate = new ProcessTemplate();
			processTemplate.setProcessTemplateCode(rs.getString("processTemplateCode"));
			processTemplate.setProcessTemplateName(rs.getString("processTemplateName"));
			return processTemplate;
		}
	}

	protected class ProcessTemplateSelect2 extends ProcessTemplateSelect1 {
		protected ProcessTemplateSelect2(DataSource ds, String query) {
			super(ds, query);
		}

		protected ProcessTemplateSelect2(DataSource ds) {
			super(ds, " SELECT * from ProcessTemplate WHERE  processTemplateCode = ? ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}
	}

	protected class ProcessTemplateSelect3 extends ProcessTemplateSelect1 {
		protected ProcessTemplateSelect3(DataSource ds, String query) {
			super(ds, query);
		}

		protected ProcessTemplateSelect3(DataSource ds) {
			super(ds, " SELECT * from ProcessTemplate WHERE  processTemplateName like ? ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}
	}

	protected class ProcessTemplateCreate extends SqlUpdate {
		protected ProcessTemplateCreate(DataSource ds) {
			super(ds, " INSERT INTO ProcessTemplate( processTemplateCode, processTemplateName) VALUES(?, ?) ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected int create(ProcessTemplate processTemplate) throws DataAccessException {
			return this.update(new Object[] { processTemplate.getProcessTemplateCode(), processTemplate.getProcessTemplateName() });
		}
	}

	protected class ProcessTemplateModify extends SqlUpdate {
		protected ProcessTemplateModify(DataSource ds) {
			super(ds, " update ProcessTemplate set processTemplateCode=?, processTemplateName=? "
					+ " where processTemplateCode=?                                        ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected int update(ProcessTemplate processTemplate) throws DataAccessException {
			return this.update(new Object[] { processTemplate.getProcessTemplateCode(), processTemplate.getProcessTemplateName(),
					processTemplate.getProcessTemplateCode() });
		}
	}

	protected class ProcessTemplateRemove extends SqlUpdate {
		protected ProcessTemplateRemove(DataSource ds) {
			super(ds, " Delete from ProcessTemplate where processTemplateCode=? ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected int delete(String processTemplateCode) {
			return update(new Object[] { processTemplateCode });
		}
	}

	protected void initDao() throws Exception {
		processTemplateSelect1 = new ProcessTemplateSelect1(getDataSource());
		processTemplateSelect2 = new ProcessTemplateSelect2(getDataSource());
		processTemplateSelect3 = new ProcessTemplateSelect3(getDataSource());
		processTemplateCreate = new ProcessTemplateCreate(getDataSource());
		processTemplateModify = new ProcessTemplateModify(getDataSource());
		processTemplateRemove = new ProcessTemplateRemove(getDataSource());
	}

	@Override
	public void createProcessTemplate(ProcessTemplate processTemplate) throws DataAccessException {
		this.processTemplateCreate.create(processTemplate);
	}

	@Override
	public void deleteProcessTemplate(String processTemplateCode) throws DataAccessException {
		this.processTemplateRemove.delete(processTemplateCode);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProcessTemplate> getProcessTemplateList() throws DataAccessException {
		return this.processTemplateSelect1.execute();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProcessTemplate> getProcessTemplateList(String processTemplateName) throws DataAccessException {
		return this.processTemplateSelect3.execute(new Object[] { "%" + processTemplateName + "%" });
	}

	@Override
	public ProcessTemplate getProcessTemplate(String processTemplateCode) throws DataAccessException {
		return (ProcessTemplate) this.processTemplateSelect2.findObject(new Object[] { processTemplateCode });
	}

	@Override
	public void updateProcessTemplate(ProcessTemplate processTemplate) throws DataAccessException {
		this.processTemplateModify.update(processTemplate);
	}

}
