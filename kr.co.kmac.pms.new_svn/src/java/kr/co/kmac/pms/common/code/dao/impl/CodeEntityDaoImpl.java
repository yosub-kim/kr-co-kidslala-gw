package kr.co.kmac.pms.common.code.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import kr.co.kmac.pms.common.code.dao.CodeEntityDao;
import kr.co.kmac.pms.common.code.data.Code;
import kr.co.kmac.pms.common.code.data.CodeEntity;
import kr.co.kmac.pms.common.code.exception.CodeException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.jdbc.object.SqlUpdate;

public class CodeEntityDaoImpl extends JdbcDaoSupport implements CodeEntityDao {

	private CodeEntityInsertQuery codeEntityInsertQuery;
	private CodeEntityUpdateQuery codeEntityUpdateQuery;
	private CodeEntitySelectQuery1 codeEntitySelectQuery1;
	private CodeEntitySelectQuery2 codeEntitySelectQuery2;
	private CodeEntitySelectQuery3 codeEntitySelectQuery3;
	private CodeEntitySelectQuery4 codeEntitySelectQuery4;
	private CodeEntitySelectQuery5 codeEntitySelectQuery5;
	private CodeEntitySelectQuery6 codeEntitySelectQuery6;
	private CodeEntitySelectQuery7 codeEntitySelectQuery7;
	private CodeEntityDeleteQuery1 codeEntityDeleteQuery1;
	private CodeEntityDeleteQuery2 codeEntityDeleteQuery2;

	@Override
	protected void initDao() throws Exception {
		this.codeEntityInsertQuery = new CodeEntityInsertQuery(getDataSource());
		this.codeEntityUpdateQuery = new CodeEntityUpdateQuery(getDataSource());
		this.codeEntitySelectQuery1 = new CodeEntitySelectQuery1(getDataSource());
		this.codeEntitySelectQuery2 = new CodeEntitySelectQuery2(getDataSource());
		this.codeEntitySelectQuery3 = new CodeEntitySelectQuery3(getDataSource());
		this.codeEntitySelectQuery4 = new CodeEntitySelectQuery4(getDataSource());
		this.codeEntitySelectQuery5 = new CodeEntitySelectQuery5(getDataSource());
		this.codeEntitySelectQuery6 = new CodeEntitySelectQuery6(getDataSource());
		this.codeEntitySelectQuery7 = new CodeEntitySelectQuery7(getDataSource());
		this.codeEntityDeleteQuery1 = new CodeEntityDeleteQuery1(getDataSource());
		this.codeEntityDeleteQuery2 = new CodeEntityDeleteQuery2(getDataSource());
	}

	protected class CodeEntityInsertQuery extends SqlUpdate {
		protected CodeEntityInsertQuery(DataSource ds) {
			super(ds, " INSERT INTO CMTABLEDATA (TABLE_NAME,KEY_1,KEY_2,KEY_3,DATA_1,DATA_2,DATA_3,DATA_4,DATA_5, "
					+ "							DATA_6,DATA_7,DATA_8,DATA_9,DATA_10,CREATE_USR_ID,CREATE_TIME, SEQ) "
					+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, getdate(), ?) ");
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

		protected int insert(CodeEntity codeEntity) throws DataAccessException {
			return this.update(new Object[] { codeEntity.getTableName(), codeEntity.getKey1(), codeEntity.getKey2(), codeEntity.getKey3(),
					codeEntity.getData1(), codeEntity.getData2(), codeEntity.getData3(), codeEntity.getData4(), codeEntity.getData5(),
					codeEntity.getData6(), codeEntity.getData7(), codeEntity.getData8(), codeEntity.getData9(), codeEntity.getData10(),
					codeEntity.getCreateUserId(), codeEntity.getOrderInfo() });
		}
	}

	protected class CodeEntityDeleteQuery1 extends SqlUpdate {
		protected CodeEntityDeleteQuery1(DataSource ds) {
			super(ds, " DELETE FROM CMTABLEDATA WHERE TABLE_NAME=? AND KEY_1=? ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected int delete(String tableName, String key1) throws DataAccessException {
			return this.update(new Object[] { tableName, key1 });
		}
	}

	protected class CodeEntityDeleteQuery2 extends SqlUpdate {
		protected CodeEntityDeleteQuery2(DataSource ds) {
			super(ds, " DELETE FROM CMTABLEDATA WHERE TABLE_NAME=? ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected int delete(String tableName) throws DataAccessException {
			return this.update(new Object[] { tableName });
		}
	}

	protected class CodeEntityUpdateQuery extends SqlUpdate {
		protected CodeEntityUpdateQuery(DataSource ds) {
			super(ds, " UPDATE CMTABLEDATA SET	TABLE_NAME=?,KEY_1=?,KEY_2=?,KEY_3=?,DATA_1=?,DATA_2=?,DATA_3=?,DATA_4=?,DATA_5=?, "
					+ "							DATA_6=?,DATA_7=?,DATA_8=?,DATA_9=?,DATA_10=?,UPDATE_USR_ID=?,UPDATE_TIME=getdate(), SEQ=?   "
					+ " WHERE TABLE_NAME=? AND KEY_1=? AND KEY_2=? AND KEY_3=? ");
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
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected int update(String tableName, String key1, String key2, String key3, CodeEntity codeEntity) throws DataAccessException {
			return this.update(new Object[] { codeEntity.getTableName(), codeEntity.getKey1(), codeEntity.getKey2(), codeEntity.getKey3(),
					codeEntity.getData1(), codeEntity.getData2(), codeEntity.getData3(), codeEntity.getData4(), codeEntity.getData5(),
					codeEntity.getData6(), codeEntity.getData7(), codeEntity.getData8(), codeEntity.getData9(), codeEntity.getData10(),
					codeEntity.getUpdateUserId(), codeEntity.getOrderInfo(), tableName, key1, key2, key3  });
		}
	}

	protected class CodeEntitySelectQuery1 extends MappingSqlQuery {
		protected CodeEntitySelectQuery1(DataSource ds, String query) {
			super(ds, query);
		}

		protected CodeEntitySelectQuery1(DataSource ds) {
			super(ds, "SELECT * FROM CMTABLEDATA  WITH(NOLOCK) WHERE TABLE_NAME = ? ORDER BY SEQ ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected Object mapRow(ResultSet rs, int rownum) throws SQLException {
			CodeEntity codeEntity = new CodeEntity();
			codeEntity.setTableName(rs.getString("TABLE_NAME"));
			codeEntity.setKey1(rs.getString("KEY_1"));
			codeEntity.setKey2(rs.getString("KEY_2"));
			codeEntity.setKey3(rs.getString("KEY_3"));
			codeEntity.setData1(rs.getString("DATA_1"));
			codeEntity.setData2(rs.getString("DATA_2"));
			codeEntity.setData3(rs.getString("DATA_3"));
			codeEntity.setData4(rs.getString("DATA_4"));
			codeEntity.setData5(rs.getString("DATA_5"));
			codeEntity.setData6(rs.getString("DATA_6"));
			codeEntity.setData7(rs.getString("DATA_7"));
			codeEntity.setData8(rs.getString("DATA_8"));
			codeEntity.setData9(rs.getString("DATA_9"));
			codeEntity.setData10(rs.getString("DATA_10"));
			// codeEntity.setCreateUserId(rs.getString(""));
			// codeEntity.setCreateDate(rs.getDate(""));
			// codeEntity.setUpdateUserId(rs.getString(""));
			// codeEntity.setUpdatedDate(rs.getDate(""));
			codeEntity.setOrderInfo(rs.getString("SEQ"));
			return codeEntity;
		}
	}

	protected class CodeEntitySelectQuery2 extends CodeEntitySelectQuery1 {
		protected CodeEntitySelectQuery2(DataSource ds) {
			super(ds, "SELECT * FROM CMTABLEDATA WITH(NOLOCK)  WHERE TABLE_NAME = ? AND KEY_1=? ORDER BY SEQ ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}
	}

	protected class CodeEntitySelectQuery3 extends CodeEntitySelectQuery1 {
		protected CodeEntitySelectQuery3(DataSource ds) {
			super(ds, "SELECT * FROM CMTABLEDATA WITH(NOLOCK)  WHERE KEY_1=? ORDER BY SEQ ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}
	}

	/**********************************
	 * 우편번호 검색을 하기 위해서 추가 작성자 : 남민호 작성일 : 2009.06.22
	 **********************************/
	protected class CodeEntitySelectQuery4 extends CodeEntitySelectQuery1 {
		protected CodeEntitySelectQuery4(DataSource ds) {
			super(ds, "SELECT * FROM CMTABLEDATA  WITH(NOLOCK) WHERE TABLE_NAME = ? AND DATA_3 LIKE ? ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}
	}

	protected class CodeEntitySelectQuery5 extends CodeEntitySelectQuery1 {
		protected CodeEntitySelectQuery5(DataSource ds) {
			super(ds, "SELECT * FROM CMTABLEDATA  WITH(NOLOCK) WHERE TABLE_NAME = ? AND KEY_1=? AND KEY_2=? ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}
	}

	protected class CodeEntitySelectQuery6 extends CodeEntitySelectQuery1 {
		protected CodeEntitySelectQuery6(DataSource ds) {
			super(ds, "SELECT * FROM CMTABLEDATA  WITH(NOLOCK) WHERE TABLE_NAME = ? AND KEY_1=? AND KEY_2=? AND KEY_3=? ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}
	}
	
	protected class CodeEntitySelectQuery7 extends CodeEntitySelectQuery1 {
		protected CodeEntitySelectQuery7(DataSource ds) {
			super(ds, "SELECT * FROM CMTABLEDATA  WITH(NOLOCK) WHERE TABLE_NAME = ? AND DATA_2=? ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}
	}

	@Override
	public void createCodeEntity(CodeEntity codeEntity) throws DataAccessException {
		this.codeEntityInsertQuery.insert(codeEntity);
	}

	@Override
	public void deleteCodeEntity(String tableName, String key1) throws DataAccessException {
		this.codeEntityDeleteQuery1.delete(tableName, key1);
	}

	@Override
	public void deleteCodeEntity(String tableName) throws DataAccessException {
		this.codeEntityDeleteQuery2.delete(tableName);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CodeEntity> findCodeEntityList(String tableName) throws DataAccessException {
		return this.codeEntitySelectQuery1.execute(new Object[] { tableName });
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CodeEntity> findCodeEntityList2(String tableName, String data3) throws DataAccessException {
		if (data3.equals("PMS"))
			return this.codeEntitySelectQuery7.execute(new Object[] { tableName, data3 });
		else
			return this.codeEntitySelectQuery4.execute(new Object[] { tableName, data3 });
	}

	@SuppressWarnings("unchecked")
	public List findRelationWithKmac(String companyName) throws DataAccessException {
		String sQuery = "";

		sQuery += "SELECT   A.*, dbo.getCommonCodeToData('RELATION_WITH_KMAC_CODE', B.kmacrelation) AS RELNAME ";
		sQuery += "FROM      OrgdbMain A  WITH(NOLOCK) LEFT OUTER JOIN OrgdbSpecialField B  WITH(NOLOCK) ";
		sQuery += "		ON A.OrgCODE = B.OrgCODE ";
		sQuery += "WHERE (A.name LIKE '%" + companyName + "%')";

		return (List) getJdbcTemplate().queryForList(sQuery);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Code> findCodeEntitySimpleList(String tableName) throws CodeException {
		List<CodeEntity> list = this.codeEntitySelectQuery1.execute(new Object[] { tableName });
		List<Code> newList = new ArrayList<Code>();
		for (CodeEntity codeEntity : list) {
			newList.add(Code.valueOf(codeEntity));
		}
		return newList;
	}

	@Override
	public CodeEntity getCodeEntity(String tableName, String key1) throws DataAccessException {
		return (CodeEntity) this.codeEntitySelectQuery2.findObject(new Object[] { tableName, key1 });
	}
	
	@Override
	public CodeEntity getCodeEntity(String tableName, String key1, String key2) throws DataAccessException {
		return (CodeEntity) this.codeEntitySelectQuery5.findObject(new Object[] { tableName, key1, key2 });
	}
	
	@Override
	public CodeEntity getCodeEntity(String tableName, String key1, String key2, String key3) throws DataAccessException {
		return (CodeEntity) this.codeEntitySelectQuery6.findObject(new Object[] { tableName, key1, key2, key3 });
	}

	@Override
	public void updateCodeEntity(String tableName, String key1, String key2, String key3, CodeEntity codeEntity) throws DataAccessException {
		this.codeEntityUpdateQuery.update(tableName, key1, key2, key3, codeEntity);
	}

	@Override
	public CodeEntity getCodeEntity(String key1) throws DataAccessException {
		return (CodeEntity) this.codeEntitySelectQuery3.findObject(new Object[] { key1 });
	}

}
