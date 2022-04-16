package kr.co.kmac.pms.system.processtemplate.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import javax.sql.DataSource;

import kr.co.kmac.pms.system.processtemplate.dao.ProcessTemplateAttachDao;
import kr.co.kmac.pms.system.processtemplate.data.ProcessTemplateAttach;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.jdbc.object.SqlUpdate;

public class ProcessTemplateAttachDaoImpl extends JdbcDaoSupport implements ProcessTemplateAttachDao {
	private ProcessTemplateAttachSelect1 processTemplateAttachSelect1;
	private ProcessTemplateAttachSelect2 processTemplateAttachSelect2;
	private ProcessTemplateAttachCreate processTemplateAttachCreate;
	private ProcessTemplateAttachModify processTemplateAttachModify;
	private ProcessTemplateAttachRemove1 processTemplateAttachRemove1;
	private ProcessTemplateAttachRemove2 processTemplateAttachRemove2;

	protected class ProcessTemplateAttachSelect1 extends MappingSqlQuery {
		protected ProcessTemplateAttachSelect1(DataSource ds, String query) {
			super(ds, query);
		}

		protected ProcessTemplateAttachSelect1(DataSource ds) {
			super(ds, " select * from processTemplateAttach  where processTemplateCode = ? and workSeq = ? ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.INTEGER));
			compile();
		}

		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			ProcessTemplateAttach processTemplateAttach = new ProcessTemplateAttach();
			processTemplateAttach.setProcessTemplateCode(rs.getString("processTemplateCode"));
			processTemplateAttach.setWorkSeq(rs.getInt("workSeq"));
			processTemplateAttach.setAttachSeq(rs.getInt("attachSeq"));
			processTemplateAttach.setOutputName(rs.getString("outputName"));
			processTemplateAttach.setOutputType(rs.getString("outputType"));
			processTemplateAttach.setBizType(rs.getString("bizType"));
			processTemplateAttach.setNecessary(rs.getBoolean("necessary"));
			return processTemplateAttach;
		}
	}

	protected class ProcessTemplateAttachSelect2 extends ProcessTemplateAttachSelect1 {
		protected ProcessTemplateAttachSelect2(DataSource ds, String query) {
			super(ds, query);
		}

		protected ProcessTemplateAttachSelect2(DataSource ds) {
			super(ds, " select * from processTemplateAttach  where processTemplateCode = ? and workSeq = ? and attachSeq = ?");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.INTEGER));
			declareParameter(new SqlParameter(Types.INTEGER));
			compile();
		}
	}

	protected class ProcessTemplateAttachCreate extends SqlUpdate {
		protected ProcessTemplateAttachCreate(DataSource ds) {
			super(ds,
					" INSERT INTO processTemplateAttach ( processTemplateCode, workSeq, attachSeq, outputName, outputType, bizType, necessary )"
							+ " VALUES(?, ?, ?, ?, ?, ?, ? ) ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.INTEGER));
			declareParameter(new SqlParameter(Types.INTEGER));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.BOOLEAN));
			compile();
		}

		protected int create(ProcessTemplateAttach processTemplateAttach) throws DataAccessException {
			return this.update(new Object[] { processTemplateAttach.getProcessTemplateCode(), processTemplateAttach.getWorkSeq(),
					processTemplateAttach.getAttachSeq(), processTemplateAttach.getOutputName(), processTemplateAttach.getOutputType(),
					processTemplateAttach.getBizType(), processTemplateAttach.isNecessary() });
		}
	}

	protected class ProcessTemplateAttachModify extends SqlUpdate {
		protected ProcessTemplateAttachModify(DataSource ds) {
			super(ds, " update processTemplateAttach set outputName=?, outputType=?, bizType=?, necessary=? "
					+ " where processTemplateCode=? and workSeq=? and attachSeq=?                                 ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.BOOLEAN));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.INTEGER));
			declareParameter(new SqlParameter(Types.INTEGER));
			compile();
		}

		protected int update(ProcessTemplateAttach processTemplateAttach) throws DataAccessException {
			return this.update(new Object[] { processTemplateAttach.getOutputName(), processTemplateAttach.getOutputType(),
					processTemplateAttach.getBizType(), processTemplateAttach.isNecessary(), processTemplateAttach.getProcessTemplateCode(),
					processTemplateAttach.getWorkSeq(), processTemplateAttach.getAttachSeq() });
		}
	}

	protected class ProcessTemplateAttachRemove1 extends SqlUpdate {
		protected ProcessTemplateAttachRemove1(DataSource ds) {
			super(ds, " Delete from processTemplateAttach where processTemplateCode=? and workSeq=? ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.INTEGER));
			compile();
		}

		protected int delete(String processTemplateCode, String workSeq) {
			return update(new Object[] { processTemplateCode, workSeq });
		}
	}

	protected class ProcessTemplateAttachRemove2 extends SqlUpdate {
		protected ProcessTemplateAttachRemove2(DataSource ds) {
			super(ds, " Delete from processTemplateAttach where processTemplateCode=? and workSeq=? and attachSeq = ?");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.INTEGER));
			declareParameter(new SqlParameter(Types.INTEGER));
			compile();
		}

		protected int delete(String processTemplateCode, String workSeq, String attachSeq) {
			return update(new Object[] { processTemplateCode, workSeq, attachSeq });
		}
	}

	protected void initDao() throws Exception {
		processTemplateAttachSelect1 = new ProcessTemplateAttachSelect1(getDataSource());
		processTemplateAttachSelect2 = new ProcessTemplateAttachSelect2(getDataSource());
		processTemplateAttachCreate = new ProcessTemplateAttachCreate(getDataSource());
		processTemplateAttachModify = new ProcessTemplateAttachModify(getDataSource());
		processTemplateAttachRemove1 = new ProcessTemplateAttachRemove1(getDataSource());
		processTemplateAttachRemove2 = new ProcessTemplateAttachRemove2(getDataSource());
	}

	@Override
	public void createProcessTemplateAttach(ProcessTemplateAttach processTemplateAttach) throws DataAccessException {
		this.processTemplateAttachCreate.create(processTemplateAttach);
	}

	@Override
	public void deleteProcessTemplateAttach(String processTemplateCode, String workSeq) throws DataAccessException {
		this.processTemplateAttachRemove1.delete(processTemplateCode, workSeq);
	}

	@Override
	public void deleteProcessTemplateAttach(String processTemplateCode, String workSeq, String attachSeq) throws DataAccessException {
		this.processTemplateAttachRemove2.delete(processTemplateCode, workSeq, attachSeq);
	}

	@Override
	public ProcessTemplateAttach getProcessTemplateAttach(String processTemplateCode, String workSeq, String attachSeq) throws DataAccessException {
		return (ProcessTemplateAttach) this.processTemplateAttachSelect2.findObject(new Object[] { processTemplateCode, workSeq, attachSeq });
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProcessTemplateAttach> getProcessTemplateAttach(String processTemplateCode, String workSeq) throws DataAccessException {
		return this.processTemplateAttachSelect1.execute(new Object[] { processTemplateCode, workSeq });
	}

	@Override
	public void updateProcessTemplateAttach(ProcessTemplateAttach processTemplateAttach) throws DataAccessException {
		this.processTemplateAttachModify.update(processTemplateAttach);
	}

}
