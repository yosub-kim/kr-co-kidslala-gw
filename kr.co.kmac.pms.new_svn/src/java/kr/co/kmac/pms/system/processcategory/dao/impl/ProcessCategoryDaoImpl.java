package kr.co.kmac.pms.system.processcategory.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import javax.sql.DataSource;

import kr.co.kmac.pms.common.util.StringUtil;
import kr.co.kmac.pms.system.processcategory.dao.ProcessCategoryDao;
import kr.co.kmac.pms.system.processcategory.data.ProcessCategory;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.jdbc.object.SqlUpdate;

public class ProcessCategoryDaoImpl extends JdbcDaoSupport implements ProcessCategoryDao {
	private static final String SELECT_QUERY = "                                                                                                                  "
			+ " SELECT  processCategoryCode, processCategoryName,                 "
			+ " 		businessTypeCode, (SELECT data_1 from cmTableData where key_1=m.businessTypeCode and table_name='BUSINESS_TYPE_CODE') as businessTypeName,"
			+ " 		runningDivCode, (select name from SmGroup where id=M.runningDivCode) as runningDivName, "
			+ "			processTemplateCode, processTemplateName, "
			+ "			businessFunctionType, dbo.getCmTableDataValue(businessFunctionType, 'BUSINESS_FUNCTION_TYPE') businessFunctionName  "
			+ " FROM   (                                         "
			+ "		SELECT	key_1 as processCategoryCode , data_1 as processCategoryName ,  data_2 as businessTypeCode,  "
			+ "				data_3 as runningDivCode, data_4 as projectType, data_5 as processTemplateCode,	"
			+ "				(select processTemplateName from processTemplate where processTemplateCode=data_5) processTemplateName, 	"
			+ "				data_8 as businessFunctionType										 	"
			+ " 	FROM 	cmtableData WHERE	table_name='PROCESS_PRODUCT_CODE' ) M 				";

	private ProcessCategorySelect1 processCategorySelect1;
	private ProcessCategorySelect2 processCategorySelect2;
	private ProcessCategorySelect3 processCategorySelect3;
	private ProcessCategoryCreate processCategoryCreate;
	private ProcessCategoryModify processCategoryModify;
	private ProcessCategoryRemove processCategoryRemove;

	protected class ProcessCategorySelect1 extends MappingSqlQuery {
		protected ProcessCategorySelect1(DataSource ds, String query) {
			super(ds, query);
		}

		protected ProcessCategorySelect1(DataSource ds) {
			super(ds, SELECT_QUERY 
					+ " WHERE  M.processCategoryName LIKE ? " 
					+ " AND M.businessTypeCode LIKE ? " 
					+ " AND M.runningDivCode LIKE ? "
					+ " AND M.businessFunctionType LIKE ? "
					+ " AND M.processTemplateCode LIKE ? ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			ProcessCategory processCategory = new ProcessCategory();
			processCategory.setprocessCategoryCode(rs.getString("processCategoryCode"));
			processCategory.setProcessCategoryName(rs.getString("processCategoryName"));
			processCategory.setBusinessTypeCode(rs.getString("businessTypeCode"));
			processCategory.setBusinessTypeName(rs.getString("businessTypeName"));
			processCategory.setRunningDivCode(rs.getString("runningDivCode"));
			processCategory.setRunningDivName(rs.getString("runningDivName"));
			processCategory.setProcessTemplateCode(rs.getString("processTemplateCode"));
			processCategory.setProcessTemplateName(rs.getString("processTemplateName"));
			processCategory.setBusinessFunctionType(rs.getString("businessFunctionType"));
			processCategory.setBusinessFunctionName(rs.getString("businessFunctionName"));
			return processCategory;
		}
	}

	protected class ProcessCategorySelect2 extends ProcessCategorySelect1 {
		protected ProcessCategorySelect2(DataSource ds, String query) {
			super(ds, query);
		}

		protected ProcessCategorySelect2(DataSource ds) {
			super(ds, SELECT_QUERY + " WHERE  M.processCategoryCode = ? ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}
	}

	protected class ProcessCategorySelect3 extends ProcessCategorySelect1 {
		protected ProcessCategorySelect3(DataSource ds, String query) {
			super(ds, query);
		}

		protected ProcessCategorySelect3(DataSource ds) {
			super(ds, SELECT_QUERY + " left join ( select * From smgroup where enabled='1' )s on M.runningDivCode = s.id WHERE s.enabled='1' and  M.processCategoryName LIKE ? " + " ORDER BY runningDivCode, businessTypeCode");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}
	}

	protected class ProcessCategoryCreate extends SqlUpdate {
		protected ProcessCategoryCreate(DataSource ds) {
			super(ds, " INSERT INTO cmtableData( TABLE_NAME, KEY_1, DATA_1, DATA_2, DATA_3, DATA_4, DATA_5, DATA_8, KEY_2, KEY_3)"
					+ " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ? ) ");
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

		protected int create(ProcessCategory processCategory) throws DataAccessException {
			return this.update(new Object[] { 
					processCategory.getTableName(), 
					processCategory.getprocessCategoryCode(),
					processCategory.getProcessCategoryName(), 
					processCategory.getBusinessTypeCode(), 
					processCategory.getRunningDivCode(), 
					"PMM",
					processCategory.getProcessTemplateCode(),
					StringUtil.isNull(processCategory.getBusinessFunctionType(), ""),
					"$EMPTY", "$EMPTY" });
		}
	}

	protected class ProcessCategoryModify extends SqlUpdate {
		protected ProcessCategoryModify(DataSource ds) {
			super(ds, " update cmtableData set DATA_1=?, DATA_2=?, DATA_3=?, DATA_5=?, DATA_8=? "
					+ " where KEY_1=? and TABLE_NAME=?                                ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected int update(ProcessCategory processCategory) throws DataAccessException {
			return this.update(new Object[] { processCategory.getProcessCategoryName(), processCategory.getBusinessTypeCode(),
					processCategory.getRunningDivCode(), processCategory.getProcessTemplateCode(),
					StringUtil.isNull(processCategory.getBusinessFunctionType(), ""),
					processCategory.getprocessCategoryCode(),
					processCategory.getTableName() });
		}
	}

	protected class ProcessCategoryRemove extends SqlUpdate {
		protected ProcessCategoryRemove(DataSource ds) {
			super(ds, " Delete from cmtableData where KEY_1=? and TABLE_NAME=? ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected int delete(String id, String tableName) {
			return update(new Object[] { id, tableName });
		}
	}

	protected void initDao() throws Exception {
		processCategorySelect1 = new ProcessCategorySelect1(getDataSource());
		processCategorySelect2 = new ProcessCategorySelect2(getDataSource());
		processCategorySelect3 = new ProcessCategorySelect3(getDataSource());
		processCategoryCreate = new ProcessCategoryCreate(getDataSource());
		processCategoryModify = new ProcessCategoryModify(getDataSource());
		processCategoryRemove = new ProcessCategoryRemove(getDataSource());
	}

	@Override
	public void createProcessCatogory(ProcessCategory processCategory) throws DataAccessException {
		this.processCategoryCreate.create(processCategory);
	}

	@Override
	public void deleteProcessCatogory(String id) throws DataAccessException {
		this.processCategoryRemove.delete(id, "PROCESS_PRODUCT_CODE");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProcessCategory> getProcessCatogoryList(String processCategoryName, String businessTypeCode, String runningDivCode,
			String businessFunctionType, String processCode)
			throws DataAccessException {
		if (processCategoryName == null || "".equals(processCategoryName) || "*".equals(processCategoryName))
			processCategoryName = "%%";
		if (businessTypeCode == null || "".equals(businessTypeCode) || "*".equals(businessTypeCode))
			businessTypeCode = "%%";
		if (runningDivCode == null || "".equals(runningDivCode) || "*".equals(runningDivCode))
			runningDivCode = "%%";
		if (businessFunctionType == null || "".equals(businessFunctionType) || "*".equals(businessFunctionType))
			businessFunctionType = "%%";
		if (processCode == null || "".equals(processCode) || "*".equals(processCode))
			processCode = "%%";
		return processCategorySelect1.execute(new Object[] { "%" + processCategoryName + "%", businessTypeCode, runningDivCode, businessFunctionType, processCode });
	}

	@Override
	public ProcessCategory getProcessCatogory(String id) throws DataAccessException {
		return (ProcessCategory) this.processCategorySelect2.findObject(new Object[] { id });
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProcessCategory> getProcessCatogoryList(String processCategoryName) throws DataAccessException {
		return this.processCategorySelect3.execute(new Object[] { "%" + processCategoryName + "%" });
	}

	@Override
	public void updateProcessCatogory(ProcessCategory processCategory) throws DataAccessException {
		this.processCategoryModify.update(processCategory);
	}

}
