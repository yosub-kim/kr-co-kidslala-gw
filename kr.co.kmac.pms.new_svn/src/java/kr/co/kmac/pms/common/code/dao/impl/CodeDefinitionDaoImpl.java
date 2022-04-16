package kr.co.kmac.pms.common.code.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import javax.sql.DataSource;

import kr.co.kmac.pms.common.code.dao.CodeDefinitionDao;
import kr.co.kmac.pms.common.code.data.CodeDefinition;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.jdbc.object.SqlUpdate;

public class CodeDefinitionDaoImpl extends JdbcDaoSupport implements CodeDefinitionDao {
	private CodeDefinitionInsertQuery codeDefinitionInsertQuery;
	private CodeDefinitionUpdateQuery codeDefinitionUpdateQuery;
	private CodeDefinitionSelectQuery codeDefinitionSelectQuery;
	private CodeDefinitionSelectQuery2 codeDefinitionSelectQuery2;
	private CodeDefinitionSelectQuery1 codeDefinitionSelectQuery1;
	private CodeDefinitionDeleteQuery codeDefinitionDeleteQuery;

	@Override
	protected void initDao() throws Exception {
		this.codeDefinitionInsertQuery = new CodeDefinitionInsertQuery(getDataSource());
		this.codeDefinitionUpdateQuery = new CodeDefinitionUpdateQuery(getDataSource());
		this.codeDefinitionSelectQuery = new CodeDefinitionSelectQuery(getDataSource());
		this.codeDefinitionSelectQuery1 = new CodeDefinitionSelectQuery1(getDataSource());
		this.codeDefinitionSelectQuery2 = new CodeDefinitionSelectQuery2(getDataSource());
		this.codeDefinitionDeleteQuery = new CodeDefinitionDeleteQuery(getDataSource());
	}

	protected class CodeDefinitionInsertQuery extends SqlUpdate {
		protected CodeDefinitionInsertQuery(DataSource ds) {
			super(ds, " INSERT INTO CMTABLEDEF (TABLE_NAME, TABLE_DESC, KEY_1_LABEL, KEY_2_LABEL, KEY_3_LABEL, DATA_1_LABEL, "
					+ "							DATA_2_LABEL, DATA_3_LABEL, DATA_4_LABEL, DATA_5_LABEL, DATA_6_LABEL, DATA_7_LABEL, "
					+ "							DATA_8_LABEL, DATA_9_LABEL, DATA_10_LABEL, CREATE_USR_ID, CREATE_TIME)       "
					+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, getdate())");
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
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected int insert(CodeDefinition codeDefinition) throws DataAccessException {
			return this.update(new Object[] { codeDefinition.getTableName(), codeDefinition.getTableDesc(), codeDefinition.getKey1Label(),
					codeDefinition.getKey2Label(), codeDefinition.getKey3Label(), codeDefinition.getData1Label(), codeDefinition.getData2Label(),
					codeDefinition.getData3Label(), codeDefinition.getData4Label(), codeDefinition.getData5Label(), codeDefinition.getData6Label(),
					codeDefinition.getData7Label(), codeDefinition.getData8Label(), codeDefinition.getData9Label(), codeDefinition.getData10Label(),
					codeDefinition.getCreateUserId() });
		}
	}

	protected class CodeDefinitionUpdateQuery extends SqlUpdate {
		protected CodeDefinitionUpdateQuery(DataSource ds) {
			super(ds, " UPDATE CMTABLEDEF SET	TABLE_DESC=?, KEY_1_LABEL=?, KEY_2_LABEL=?, KEY_3_LABEL=?, DATA_1_LABEL=?, "
					+ "							DATA_2_LABEL=?, DATA_3_LABEL=?, DATA_4_LABEL=?, DATA_5_LABEL=?, DATA_6_LABEL=?, "
					+ "							DATA_7_LABEL=?, DATA_8_LABEL=?, DATA_9_LABEL=?, DATA_10_LABEL=?, UPDATE_USR_ID=?, UPDATE_TIME=getdate()  "
					+ " WHERE  TABLE_NAME=? ");
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
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected int update(CodeDefinition codeDefinition) throws DataAccessException {
			return this.update(new Object[] { codeDefinition.getTableDesc(), codeDefinition.getKey1Label(), codeDefinition.getKey2Label(),
					codeDefinition.getKey3Label(), codeDefinition.getData1Label(), codeDefinition.getData2Label(), codeDefinition.getData3Label(),
					codeDefinition.getData4Label(), codeDefinition.getData5Label(), codeDefinition.getData6Label(), codeDefinition.getData7Label(),
					codeDefinition.getData8Label(), codeDefinition.getData9Label(), codeDefinition.getData10Label(),
					codeDefinition.getUpdatedUserId(), codeDefinition.getTableName() });
		}
	}

	protected class CodeDefinitionDeleteQuery extends SqlUpdate {
		protected CodeDefinitionDeleteQuery(DataSource ds) {
			super(ds, " DELETE FROM CMTABLEDEF WHERE  TABLE_NAME=? ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected int delete(String deleteKey) throws DataAccessException {
			return this.update(new Object[] { deleteKey });
		}
	}

	protected class CodeDefinitionSelectQuery extends MappingSqlQuery {
		protected CodeDefinitionSelectQuery(DataSource ds) {
			super(ds, "	SELECT 	* FROM CMTABLEDEF  WITH(NOLOCK) ");
			compile();
		}

		protected CodeDefinitionSelectQuery(DataSource ds, String query) {
			super(ds, query);
		}

		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			CodeDefinition codeDefinition = new CodeDefinition();
			codeDefinition.setTableName(rs.getString("TABLE_NAME"));
			codeDefinition.setTableDesc(rs.getString("TABLE_DESC"));
			codeDefinition.setKey1Label(rs.getString("KEY_1_LABEL"));
			codeDefinition.setKey2Label(rs.getString("KEY_2_LABEL"));
			codeDefinition.setKey3Label(rs.getString("KEY_3_LABEL"));
			codeDefinition.setData1Label(rs.getString("DATA_1_LABEL"));
			codeDefinition.setData2Label(rs.getString("DATA_2_LABEL"));
			codeDefinition.setData3Label(rs.getString("DATA_3_LABEL"));
			codeDefinition.setData4Label(rs.getString("DATA_4_LABEL"));
			codeDefinition.setData5Label(rs.getString("DATA_5_LABEL"));
			codeDefinition.setData6Label(rs.getString("DATA_6_LABEL"));
			codeDefinition.setData7Label(rs.getString("DATA_7_LABEL"));
			codeDefinition.setData8Label(rs.getString("DATA_8_LABEL"));
			codeDefinition.setData9Label(rs.getString("DATA_9_LABEL"));
			codeDefinition.setData10Label(rs.getString("DATA_10_LABEL"));
			codeDefinition.setCreateUserId(rs.getString("CREATE_USR_ID"));
			codeDefinition.setCreateDate(rs.getDate("CREATE_TIME"));
			codeDefinition.setUpdatedUserId(rs.getString("UPDATE_USR_ID"));
			codeDefinition.setUpdatedDate(rs.getDate("UPDATE_TIME"));
			return codeDefinition;
		}
	}
	
	protected class CodeDefinitionSelectQuery2 extends MappingSqlQuery {
		protected CodeDefinitionSelectQuery2(DataSource ds) {
			super(ds, "	SELECT 	* FROM CMTABLEDEF  WITH(NOLOCK) WHERE TABLE_NAME = 'SALARY_CAN_DATE' ");
			compile();
		}

		protected CodeDefinitionSelectQuery2(DataSource ds, String query) {
			super(ds, query);
		}

		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			CodeDefinition codeDefinition = new CodeDefinition();
			codeDefinition.setTableName(rs.getString("TABLE_NAME"));
			codeDefinition.setTableDesc(rs.getString("TABLE_DESC"));
			codeDefinition.setKey1Label(rs.getString("KEY_1_LABEL"));
			codeDefinition.setKey2Label(rs.getString("KEY_2_LABEL"));
			codeDefinition.setKey3Label(rs.getString("KEY_3_LABEL"));
			codeDefinition.setData1Label(rs.getString("DATA_1_LABEL"));
			codeDefinition.setData2Label(rs.getString("DATA_2_LABEL"));
			codeDefinition.setData3Label(rs.getString("DATA_3_LABEL"));
			codeDefinition.setData4Label(rs.getString("DATA_4_LABEL"));
			codeDefinition.setData5Label(rs.getString("DATA_5_LABEL"));
			codeDefinition.setData6Label(rs.getString("DATA_6_LABEL"));
			codeDefinition.setData7Label(rs.getString("DATA_7_LABEL"));
			codeDefinition.setData8Label(rs.getString("DATA_8_LABEL"));
			codeDefinition.setData9Label(rs.getString("DATA_9_LABEL"));
			codeDefinition.setData10Label(rs.getString("DATA_10_LABEL"));
			codeDefinition.setCreateUserId(rs.getString("CREATE_USR_ID"));
			codeDefinition.setCreateDate(rs.getDate("CREATE_TIME"));
			codeDefinition.setUpdatedUserId(rs.getString("UPDATE_USR_ID"));
			codeDefinition.setUpdatedDate(rs.getDate("UPDATE_TIME"));
			return codeDefinition;
		}
	}
	
	protected class CodeDefinitionSelectQuery1 extends CodeDefinitionSelectQuery {
		protected CodeDefinitionSelectQuery1(DataSource ds) {
			super(ds, "	SELECT 	* FROM CMTABLEDEF  WITH(NOLOCK) WHERE TABLE_NAME = ?");
			declareParameter(new SqlParameter(Types.VARCHAR)); // roleId
			compile();
		}
	}
	

	@Override
	public void createCodeDefinition(CodeDefinition codeDefinition) throws DataAccessException {
		this.codeDefinitionInsertQuery.insert(codeDefinition);
	}

	@Override
	public void deleteCodeDefinition(String deleteKey) throws DataAccessException {
		this.codeDefinitionDeleteQuery.delete(deleteKey);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CodeDefinition> getCodeDefinition() throws DataAccessException {
		return this.codeDefinitionSelectQuery.execute();
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public List<CodeDefinition> getCodeDefinition_eun() throws DataAccessException {
		return this.codeDefinitionSelectQuery2.execute();
	}

	@Override
	public CodeDefinition getCodeDefinition(String searchKey) throws DataAccessException {
		return (CodeDefinition) this.codeDefinitionSelectQuery1.findObject(new Object[] { searchKey });
	}

	@Override
	public void updateCodeDefinition(CodeDefinition codeDefinition) throws DataAccessException {
		this.codeDefinitionUpdateQuery.update(codeDefinition);
	}

}
