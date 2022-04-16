/*
 * $Id: ExpertPoolSpecialFieldDaoImpl.java,v 1.11 2014/04/09 05:09:43 cvs Exp $
 * created by : jiwoongLee creation-date : 2006. 1. 17
 * ========================================================= Copyright (c) 2006
 * Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.expertpool.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import javax.sql.DataSource;

import kr.co.kmac.pms.common.org.data.Group;
import kr.co.kmac.pms.expertpool.dao.ExpertPoolSpecialFieldDao;
import kr.co.kmac.pms.expertpool.data.ExpertPoolSpecialField;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.jdbc.object.SqlUpdate;

/**
 * TODO 클래스 설명
 * 
 * @author jiwoongLee
 * @version $Id: ExpertPoolSpecialFieldDaoImpl.java,v 1.11 2014/04/09 05:09:43 cvs Exp $
 */
public class ExpertPoolSpecialFieldDaoImpl extends JdbcDaoSupport implements ExpertPoolSpecialFieldDao {

	private ExpertPoolSpecialFieldInsertQuery expertPoolSpecialFieldInsertQuery;
	private ExpertPoolSpecialFieldFindQuery expertPoolSpecialFieldFindQuery;
	private ExpertPoolSpecialFieldRetrieveQuery expertPoolSpecialFieldRetrieveQuery;
	private ExpertPoolSpecialFieldDeleteQuery expertPoolSpecialFieldDeleteQuery;
	private ExpertPoolSpecialFieldDeleteQueryMulti expertPoolSpecialFieldDeleteQueryMulti;
	private ExpertPoolDeptFindQuery expertPoolDeptFindQuery;;
	private ExpertPoolFunctionFindQuery expertPoolFunctionFindQuery;;

	/*
CREATE TABLE [dbo].[ExpertPoolSpecialField](
	[ssn] [varchar](13) COLLATE Korean_Wansung_CI_AS NOT NULL,
	[specialId] [varchar](30) COLLATE Korean_Wansung_CI_AS NOT NULL,
	[specialName] [varchar](50) COLLATE Korean_Wansung_CI_AS NULL,
	[createdDate] [datetime] NULL,
	[modifiedDate] [datetime] NULL,
 CONSTRAINT [PK_ExpertPoolSpecialField] PRIMARY KEY CLUSTERED 
(
	[ssn] ASC,
	[specialId] ASC
)WITH (IGNORE_DUP_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]

	 */
	protected class ExpertPoolSpecialFieldInsertQuery extends SqlUpdate {
		protected ExpertPoolSpecialFieldInsertQuery(DataSource ds) {
			super(ds, " INSERT INTO ExpertPoolSpecialField(ssn, specialId, specialName, createdDate, modifiedDate) "
					+ "	VALUES(?, ?, ?, getdate(), getdate())                                                ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected int insert(ExpertPoolSpecialField specialFiled) {
			return update(new Object[] { specialFiled.getSsn(), specialFiled.getSpecialId(), specialFiled.getSpecialName() });

		}
	}

	protected class ExpertPoolSpecialFieldDeleteQuery extends SqlUpdate {
		protected ExpertPoolSpecialFieldDeleteQuery(DataSource ds) {
			super(ds, " Delete from ExpertPoolSpecialField where ssn = ?, spclId = ?");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected int delete(String ssn, String specialId) {
			return update(new Object[] { ssn, specialId });
		}
	}

	protected class ExpertPoolSpecialFieldDeleteQueryMulti extends SqlUpdate {
		protected ExpertPoolSpecialFieldDeleteQueryMulti(DataSource ds) {
			super(ds, " Delete from ExpertPoolSpecialField where ssn = ?");
			declareParameter(new SqlParameter(Types.VARCHAR));
		}

		protected int delete(String ssn) {
			return update(new Object[] { ssn });
		}
	}

	protected class ExpertPoolSpecialFieldRetrieveQuery extends MappingSqlQuery {
		protected ExpertPoolSpecialFieldRetrieveQuery(DataSource ds, String query) {
			super(ds, query);
		}

		protected ExpertPoolSpecialFieldRetrieveQuery(DataSource ds) {
			super(ds, " SELECT E.*, C.DATA_2 AS specialBizId, D.DATA_1 AS specialBizName,	" 
					+ " C.DATA_3 AS specialFunctionId, "
					+ " (case when (select aliasName from smGroup where id = c.data_3) is NULL " 
					+ " 	then (select data_1 from cmtabledata where table_name='BUSINESS_FUNCTION_TYPE' and key_1 =c.data_4) " 
					+ "		else (select aliasName from smGroup where id = c.data_3) end) specialFunctionName "
					+ " FROM EXPERTPOOLSPECIALFIELD E INNER JOIN CMTABLEDATA C ON E.SPECIALID = C.KEY_1 "
					+ " INNER JOIN CMTABLEDATA D ON C.DATA_2 = D.KEY_1 "
					+ " WHERE SSN = ? AND D.TABLE_NAME='BUSINESS_TYPE_CODE' ORDER BY C.DATA_3");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			ExpertPoolSpecialField expertPoolSpecialField = new ExpertPoolSpecialField();
			expertPoolSpecialField.setSsn(rs.getString("ssn"));
			expertPoolSpecialField.setSpecialBizId(rs.getString("specialBizId"));
			expertPoolSpecialField.setSpecialBizName(rs.getString("specialBizName"));
			expertPoolSpecialField.setSpecialFunctionId(rs.getString("specialFunctionId"));
			expertPoolSpecialField.setSpecialFunctionName(rs.getString("specialFunctionName"));
			expertPoolSpecialField.setSpecialId(rs.getString("specialId"));
			expertPoolSpecialField.setSpecialName(rs.getString("specialName"));
			return expertPoolSpecialField;
		}
	}

	protected class ExpertPoolSpecialFieldFindQuery extends MappingSqlQuery {
		protected ExpertPoolSpecialFieldFindQuery(DataSource ds, String query) {
			super(ds, query);
		}

		protected ExpertPoolSpecialFieldFindQuery(DataSource ds) {
			super(ds, " select c.key_1 as specialId, c.data_1 as specialName, c.data_4 as specialFunctionId, "
					+ " (select data_1 from cmtabledata where table_name='BUSINESS_FUNCTION_TYPE' and key_1 = c.data_4) "
					+ " specialFunctionName from cmTableData c"
					+ " where c.table_name = 'EXPERT_SPECIALISM' and c.data_4 = ?       ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			ExpertPoolSpecialField expertPoolSpecialField = new ExpertPoolSpecialField();
			expertPoolSpecialField.setSpecialId(rs.getString("specialId"));
			expertPoolSpecialField.setSpecialName(rs.getString("specialName"));
			expertPoolSpecialField.setSpecialFunctionName(rs.getString("specialFunctionId"));
			expertPoolSpecialField.setSpecialFunctionName(rs.getString("specialFunctionName"));
			return expertPoolSpecialField;
		}
	}

	protected class ExpertPoolDeptFindQuery extends MappingSqlQuery {
		protected ExpertPoolDeptFindQuery(DataSource ds, String query) {
			super(ds, query);
		}

		protected ExpertPoolDeptFindQuery(DataSource ds) {
			super(ds, " SELECT id, description as name FROM smGroup WHERE enabled='1' and depth = '1' and seq <= '26' ORDER BY seq ");
			compile();
		}

		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new Group(rs.getString("id"), rs.getString("name"));
		}
	}
	
	protected class ExpertPoolFunctionFindQuery extends MappingSqlQuery {
		protected ExpertPoolFunctionFindQuery(DataSource ds, String query) {
			super(ds, query);
		}
		
		protected ExpertPoolFunctionFindQuery(DataSource ds) {
			super(ds, " SELECT KEY_1 AS id, DATA_1 AS name FROM CMTABLEDATA WHERE TABLE_NAME='BUSINESS_FUNCTION_TYPE' ORDER BY KEY_1 ");
			compile();
		}
		
		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new Group(rs.getString("id"), rs.getString("name"));
		}
	}

	protected void initDao() throws Exception {
		this.expertPoolSpecialFieldInsertQuery = new ExpertPoolSpecialFieldInsertQuery(getDataSource());
		this.expertPoolSpecialFieldRetrieveQuery = new ExpertPoolSpecialFieldRetrieveQuery(getDataSource());
		this.expertPoolSpecialFieldFindQuery = new ExpertPoolSpecialFieldFindQuery(getDataSource());
		this.expertPoolSpecialFieldDeleteQuery = new ExpertPoolSpecialFieldDeleteQuery(getDataSource());
		this.expertPoolSpecialFieldDeleteQueryMulti = new ExpertPoolSpecialFieldDeleteQueryMulti(getDataSource());
		this.expertPoolDeptFindQuery = new ExpertPoolDeptFindQuery(getDataSource());
		this.expertPoolFunctionFindQuery = new ExpertPoolFunctionFindQuery(getDataSource());
	}

	@Override
	public void create(ExpertPoolSpecialField expertPoolSpecialField) throws DataAccessException {
		this.expertPoolSpecialFieldInsertQuery.insert(expertPoolSpecialField);
	}

	@Override
	public boolean isExist(String ssn, String specialId) throws DataAccessException {
		Object object = this.expertPoolSpecialFieldRetrieveQuery.findObject(new Object[] { ssn, specialId });
		return object != null;
	}

	@Override
	public void remove(String ssn, String specialId) throws DataAccessException {
		this.expertPoolSpecialFieldDeleteQuery.delete(ssn, specialId);
	}

	@Override
	public void remove(String ssn) throws DataAccessException {
		this.expertPoolSpecialFieldDeleteQueryMulti.delete(ssn);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ExpertPoolSpecialField> getSpecialFieldList(String ssn) throws DataAccessException {
		return this.expertPoolSpecialFieldRetrieveQuery.execute(new Object[] { ssn });
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ExpertPoolSpecialField> findSpecialField(String deptId) throws DataAccessException {
		return this.expertPoolSpecialFieldFindQuery.execute(new Object[] { deptId });
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Group> getDeptLIst() throws DataAccessException {
		return this.expertPoolDeptFindQuery.execute();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Group> getExpertPoolFunctionLIst() throws DataAccessException {
		return this.expertPoolFunctionFindQuery.execute();
	}
}
