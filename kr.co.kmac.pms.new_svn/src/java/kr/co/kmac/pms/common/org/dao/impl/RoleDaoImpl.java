/*
 * $Id: RoleDaoImpl.java,v 1.1 2009/09/19 11:15:31 cvs3 Exp $
 * created by    : 최인호
 * creation-date : 2005. 10. 13.
 * =========================================================
 * Copyright (c) 2005 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.common.org.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import javax.sql.DataSource;

import kr.co.kmac.pms.common.org.dao.IRoleDao;
import kr.co.kmac.pms.common.org.data.IRole;
import kr.co.kmac.pms.common.org.data.IUser;
import kr.co.kmac.pms.common.org.data.Role;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.jdbc.object.SqlUpdate;
import org.springframework.orm.ObjectRetrievalFailureException;

/**
 * RoleDao 구현
 * 
 * @author 최인호
 * @version $Id: RoleDaoImpl.java,v 1.1 2009/09/19 11:15:31 cvs3 Exp $
 */
public class RoleDaoImpl extends JdbcDaoSupport implements IRoleDao {
	private InsertQuery insertQuery;
	private UpdateQuery updateQuery;
	private RetrieveQuery retrieveQuery;

	private FindQuery findQuery;
	private FindByStateQuery findByStateQuery;
	private FindByNameQuery findByNameQuery;

	protected class InsertQuery extends SqlUpdate {
		protected InsertQuery(DataSource ds) {
			super(ds, "insert into SMRole values (?,?,?,?,?)");
			declareParameter(new SqlParameter(Types.VARCHAR)); // id
			declareParameter(new SqlParameter(Types.VARCHAR)); // name
			declareParameter(new SqlParameter(Types.BIT)); // enabled
			declareParameter(new SqlParameter(Types.VARCHAR)); // description
			declareParameter(new SqlParameter(Types.VARCHAR)); // seq
			compile();
			setRequiredRowsAffected(1);
		}

		protected void insert(IRole role) {
			super.update(new Object[] { role.getId(), role.getName(), Boolean.valueOf(role.isEnabled()), role.getDescription(), role.getOrderSeq() });
		}
	}

	protected class UpdateQuery extends SqlUpdate {
		protected UpdateQuery(DataSource ds) {
			super(ds, "update SMRole set name=?,enabled=?,description=?, seq=? where id=?");
			declareParameter(new SqlParameter(Types.VARCHAR)); // name
			declareParameter(new SqlParameter(Types.BIT)); // enabled
			declareParameter(new SqlParameter(Types.VARCHAR)); // description
			declareParameter(new SqlParameter(Types.VARCHAR)); // seq
			declareParameter(new SqlParameter(Types.VARCHAR)); // id
			compile();
			setRequiredRowsAffected(1);
		}

		protected int update(IRole role) {
			return this.update(new Object[] { role.getName(), Boolean.valueOf(role.isEnabled()), role.getDescription(), role.getOrderSeq(),
					role.getId() });
		}
	}

	protected class RetrieveQuery extends MappingSqlQuery {
		protected RetrieveQuery(DataSource ds, String query) {
			super(ds, query);
		}

		protected RetrieveQuery(DataSource ds) {
			super(ds, "select * from SMRole where id=? order by seq");
			declareParameter(new SqlParameter(Types.VARCHAR)); // id
			compile();
		}

		protected Object mapRow(ResultSet rs, int rownum) throws SQLException {
			IRole role = new Role(rs.getString("id"), rs.getString("name"));
			role.setEnabled(rs.getBoolean("enabled"));
			role.setDescription(rs.getString("description"));
			role.setOrderSeq(rs.getString("seq"));
			return role;
		}
	}

	protected class FindQuery extends RetrieveQuery {
		protected FindQuery(DataSource ds) {
			super(ds, "select * from SMRole order by seq");
			compile();
		}
	}

	protected class FindByStateQuery extends RetrieveQuery {
		protected FindByStateQuery(DataSource ds) {
			super(ds, "select * from SMRole where enabled=? order by seq");
			declareParameter(new SqlParameter(Types.BIT));
			compile();
		}
	}

	protected class FindByNameQuery extends RetrieveQuery {
		protected FindByNameQuery(DataSource ds) {
			super(ds, "select * from SMRole where name like ? order by seq");
			declareParameter(new SqlParameter(Types.VARCHAR)); // name
			compile();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.dao.support.DaoSupport#initDao()
	 */
	protected void initDao() throws Exception {
		this.insertQuery = new InsertQuery(getDataSource());
		this.updateQuery = new UpdateQuery(getDataSource());
		this.retrieveQuery = new RetrieveQuery(getDataSource());

		this.findQuery = new FindQuery(getDataSource());
		this.findByNameQuery = new FindByNameQuery(getDataSource());
		this.findByStateQuery = new FindByStateQuery(getDataSource());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.miracom.bpms.security.core.dao.ICommonDao#count()
	 */
	public int count() throws DataAccessException {
		return getJdbcTemplate().queryForInt("select count(*) from SMRole");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.miracom.bpms.security.core.dao.IEntityDao#countByState(boolean)
	 */
	public int count(boolean enabled) throws DataAccessException {
		return getJdbcTemplate().queryForInt("select count(*) from SMRole where enabled=?", new Object[] { Boolean.valueOf(enabled) },
				new int[] { Types.BIT });
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.miracom.bpms.security.core.dao.IRoleDao#create(com.miracom.bpms.security.core.IRole)
	 */
	public void create(IRole role) throws DataAccessException {
		assert role != null : "Role must not be null";
		this.insertQuery.insert(role);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.miracom.bpms.security.core.dao.ICommonDao#find()
	 */
	public List find() throws DataAccessException {
		return this.findQuery.execute();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.miracom.bpms.security.core.dao.IEntityDao#findByState(boolean)
	 */
	public List find(boolean enabled) throws DataAccessException {
		return this.findByStateQuery.execute(new Object[] { Boolean.valueOf(enabled) });
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.miracom.bpms.security.core.dao.IEntityDao#findByName(java.lang.String)
	 */
	public List findByName(String name) throws DataAccessException {
		return this.findByNameQuery.execute(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.miracom.bpms.security.core.dao.IEntityDao#isEnabled(java.lang.String)
	 */
	public boolean isEnabled(String id) throws DataAccessException {
		Boolean enabled = (Boolean) getJdbcTemplate().queryForObject("select enabled from SMRole where id=?", Boolean.class);
		return enabled != null ? enabled.booleanValue() : false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.miracom.bpms.security.core.dao.IEntityDao#isExist(java.lang.String)
	 */
	public boolean entityExists(String id) throws DataAccessException {
		assert id != null : "Id must not be null";
		return getJdbcTemplate().queryForInt("select count(*) from SMRole where id=?", new Object[] { id }, new int[] { Types.VARCHAR }) == 1;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.miracom.bpms.security.core.dao.IEntityDao#remove(java.lang.String)
	 */
	public void remove(String id) throws DataAccessException {
		assert id != null : "Id must not be null";
		String[] sql = { "delete from SMGroupRole where roleId='" + id + "'", // 그룹-역할 관계 삭제
				"delete from SMUserRole where roleId='" + id + "'", // 사용자-역할 관계 삭제
				"delete from SMRole where id='" + id + "'" // 그룹 삭제
		};
		int[] result = getJdbcTemplate().batchUpdate(sql);
		if (result[sql.length - 1] != 1) {
			throw new ObjectRetrievalFailureException(IUser.class, id);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.miracom.bpms.security.core.dao.ICommonDao#removeAll()
	 */
	public void removeAll() throws DataAccessException {
		String[] sql = { "delete from SMGroupRole", // 그룹-역할 관계 삭제
				"delete from SMUserRole", // 사용자-역할 관계 삭제
				"delete from SMRole" // 그룹 삭제
		};
		getJdbcTemplate().batchUpdate(sql);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.miracom.bpms.security.core.dao.IRoleDao#retrieve(java.lang.String)
	 */
	public IRole retrieve(String id) throws DataAccessException {
		assert id != null : "Id must not be null";
		IRole role = (IRole) this.retrieveQuery.findObject(id);
		if (role == null) {
			throw new ObjectRetrievalFailureException(IRole.class, id);
		}
		return role;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.miracom.bpms.security.core.dao.IEntityDao#retrieveName(java.lang.String)
	 */
	public String retrieveName(String id) throws DataAccessException {
		assert id != null : "Id must not be null";
		String roleName = (String) getJdbcTemplate().queryForObject("select name from SMRole where id=?", new Object[] { id },
				new int[] { Types.VARCHAR }, String.class);
		if (roleName == null || roleName.equals("")) {
			roleName = id;
		}
		return roleName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.miracom.bpms.security.core.dao.IRoleDao#update(com.miracom.bpms.security.core.IRole)
	 */
	public void update(IRole role) throws DataAccessException {
		assert role != null : "Role must not be null";
		this.updateQuery.update(role);
	}
}
