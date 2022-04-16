/*
 * $Id: UserDaoImpl.java,v 1.3 2018/08/16 12:45:14 cvs Exp $
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

import kr.co.kmac.pms.common.org.dao.IUserDao;
import kr.co.kmac.pms.common.org.data.IUser;
import kr.co.kmac.pms.common.org.data.User;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.jdbc.object.SqlUpdate;
import org.springframework.orm.ObjectRetrievalFailureException;

/**
 * UserDao 구현
 * 
 * @author 최인호
 * @version $Id: UserDaoImpl.java,v 1.3 2018/08/16 12:45:14 cvs Exp $
 */
public class UserDaoImpl extends JdbcDaoSupport implements IUserDao {
	private InsertQuery insertQuery;
	private UpdateQuery updateQuery;
	private RetrieveQuery retrieveQuery;
	private RetrieveQueryForLigin retrieveQueryForLigin;

	private FindQuery findQuery;
	private FindByStateQuery findByStateQuery;
	private FindByNameQuery findByNameQuery;
	private FindByEmailQuery findByEmailQuery;
	private FindByPositionQuery findByPositionQuery;
	private FindByRoleQuery findByRoleQuery;
	private FindBySsnQuery findBySsnQuery;

	/**
	 * @deprecated
	 */
	protected class InsertQuery extends SqlUpdate {
		protected InsertQuery(DataSource ds) {
			super(ds, "insert into SMUser (id, password, enabled, name, email, pos, ssn, description) values (?,SecureDB.DBSEC.ENCRYPT_PWD(?),?,?,?,?,?,?)");
			declareParameter(new SqlParameter(Types.VARCHAR)); // id
			declareParameter(new SqlParameter(Types.VARCHAR)); // password
			declareParameter(new SqlParameter(Types.BIT)); // enabled
			declareParameter(new SqlParameter(Types.VARCHAR)); // name
			declareParameter(new SqlParameter(Types.VARCHAR)); // email
			declareParameter(new SqlParameter(Types.VARCHAR)); // pos
			declareParameter(new SqlParameter(Types.VARCHAR)); // ssn
			declareParameter(new SqlParameter(Types.VARCHAR)); // description
			compile();
			setRequiredRowsAffected(1);
		}

		protected void insert(IUser user) {
			super.update(new Object[] { user.getId(), user.getPassword(), Boolean.valueOf(user.isEnabled()), user.getName(), user.getEmail(),
					user.getPosition(), user.getSsn(), user.getDescription() });
		}
	}

	/**
	 * @deprecated
	 */
	protected class UpdateQuery extends SqlUpdate {
		protected UpdateQuery(DataSource ds) {
			super(ds, "update SMUser set password=SecureDB.DBSEC.ENCRYPT_PWD(?),enabled=?,name=?,email=?,pos=?,ssn=?,description=? where id=?");
			declareParameter(new SqlParameter(Types.VARCHAR)); // password
			declareParameter(new SqlParameter(Types.BIT)); // enabled
			declareParameter(new SqlParameter(Types.VARCHAR)); // name
			declareParameter(new SqlParameter(Types.VARCHAR)); // email
			declareParameter(new SqlParameter(Types.VARCHAR)); // pos
			declareParameter(new SqlParameter(Types.VARCHAR)); // ssn
			declareParameter(new SqlParameter(Types.VARCHAR)); // description
			declareParameter(new SqlParameter(Types.VARCHAR)); // id
			compile();
			setRequiredRowsAffected(1);
		}

		protected int update(IUser user) {
			return this.update(new Object[] { user.getPassword(), Boolean.valueOf(user.isEnabled()), user.getName(), user.getEmail(),
					user.getPosition(), user.getSsn(), user.getDescription(), user.getId() });
		}
	}

	protected class RetrieveQuery extends MappingSqlQuery {
		protected RetrieveQuery(DataSource ds, String query) {
			super(ds, query);
		}

		protected RetrieveQuery(DataSource ds) {
			super(ds, "select * from dbo.SMUser where id=?");
			declareParameter(new SqlParameter(Types.VARCHAR)); // id
			compile();
		}

		protected Object mapRow(ResultSet rs, int rownum) throws SQLException {
			IUser user = new User(rs.getString("id"), rs.getString("name"));
			user.setPassword(rs.getString("password"));
			user.setEnabled(rs.getBoolean("enabled"));
			user.setEmail(rs.getString("email"));
			user.setPosition(rs.getString("pos"));
			user.setSsn(rs.getString("ssn"));
			user.setDescription(rs.getString("description"));
			user.setOrderSeq(rs.getString("seq"));
			return user;
		}
	}

	protected class RetrieveQueryForLigin extends RetrieveQuery {
		protected RetrieveQueryForLigin(DataSource ds) {
			super(ds, "select * from dbo.SMUser where loginId=?");
			declareParameter(new SqlParameter(Types.VARCHAR)); // id
			compile();
		}
		protected Object mapRow(ResultSet rs, int rownum) throws SQLException {
			User user = new User(rs.getString("id"), rs.getString("name"));
			user.setPassword(rs.getString("password"));
			user.setEnabled(rs.getBoolean("enabled"));
			user.setEmail(rs.getString("email"));
			user.setPosition(rs.getString("pos"));
			user.setSsn(rs.getString("ssn"));
			user.setLoginId(rs.getString("loginId"));
			user.setDescription(rs.getString("description"));
			user.setOrderSeq(rs.getString("seq"));
			user.setAccountNonLocked(rs.getBoolean("accountNonLocked"));
			return user;
		}
	}

	protected class FindQuery extends RetrieveQuery {
		protected FindQuery(DataSource ds) {
			super(ds, "select * from SMUser order by id");
			compile();
		}
	}

	protected class FindByStateQuery extends RetrieveQuery {
		protected FindByStateQuery(DataSource ds) {
			super(ds, "select * from SMUser where enabled=?");
			declareParameter(new SqlParameter(Types.BIT));
			compile();
		}
	}

	protected class FindByNameQuery extends RetrieveQuery {
		protected FindByNameQuery(DataSource ds) {
			super(ds, "select * from SMUser where name like ? order by id");
			declareParameter(new SqlParameter(Types.VARCHAR)); // name
			compile();
		}
	}

	protected class FindByEmailQuery extends RetrieveQuery {
		protected FindByEmailQuery(DataSource ds) {
			super(ds, "select * from SMUser where email like ? order by id");
			declareParameter(new SqlParameter(Types.VARCHAR)); // email
			compile();
		}
	}

	protected class FindByPositionQuery extends RetrieveQuery {
		protected FindByPositionQuery(DataSource ds) {
			super(ds, "select * from SMUser where pos like ? order by id");
			declareParameter(new SqlParameter(Types.VARCHAR)); // pos
			compile();
		}
	}

	protected class FindBySsnQuery extends RetrieveQuery {
		protected FindBySsnQuery(DataSource ds) {
			super(ds, "select * from SMUser where ssn like ? order by id");
			declareParameter(new SqlParameter(Types.VARCHAR)); // ssn
			compile();
		}
	}

	protected class FindByRoleQuery extends RetrieveQuery {
		protected FindByRoleQuery(DataSource ds) {
			super(ds, "select SMUser.* from SMUser, SMUserRole " + "where SMUser.id=SMUserRole.userId and SMUserRole.roleId=?");
			declareParameter(new SqlParameter(Types.VARCHAR)); // roleId
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
		this.retrieveQueryForLigin = new RetrieveQueryForLigin(getDataSource());

		this.findQuery = new FindQuery(getDataSource());
		this.findByNameQuery = new FindByNameQuery(getDataSource());
		this.findByEmailQuery = new FindByEmailQuery(getDataSource());
		this.findByPositionQuery = new FindByPositionQuery(getDataSource());
		this.findBySsnQuery = new FindBySsnQuery(getDataSource());
		this.findByStateQuery = new FindByStateQuery(getDataSource());
		this.findByRoleQuery = new FindByRoleQuery(getDataSource());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.miracom.bpms.security.core.dao.ICommonDao#count()
	 */
	public int count() throws DataAccessException {
		return getJdbcTemplate().queryForInt("select count(*) from SMUser");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.miracom.bpms.security.core.dao.IEntityDao#countByState(boolean)
	 */
	public int count(boolean enabled) throws DataAccessException {
		return getJdbcTemplate().queryForInt("select count(*) from SMUser where enabled=?", new Object[] { Boolean.valueOf(enabled) },
				new int[] { Types.BIT });
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.miracom.bpms.security.core.dao.IUserDao#countGroup(java.lang.String)
	 */
	public int countGroup(String userId) throws DataAccessException {
		assert userId != null : "UserId must not be null";
		return getJdbcTemplate().queryForInt("select count(*) from SMGroupUser where userId=?", new Object[] { userId }, new int[] { Types.VARCHAR });
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.miracom.bpms.security.core.dao.IUserDao#create(com.miracom.bpms.security.core.IUser)
	 */
	public void create(IUser user) throws DataAccessException {
		assert user != null : "User must not be null";
		this.insertQuery.insert(user);
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
	 * @see com.miracom.bpms.security.core.dao.IUserDao#findByEmail(java.lang.String)
	 */
	public List findByEmail(String email) throws DataAccessException {
		return this.findByEmailQuery.execute(email);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.miracom.bpms.security.core.dao.IUserDao#findByPosition(java.lang.String)
	 */
	public List findByPosition(String pos) throws DataAccessException {
		return this.findByPositionQuery.execute(pos);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.miracom.bpms.security.core.dao.IUserDao#findBySsn(java.lang.String)
	 */
	public List findBySsn(String ssn) throws DataAccessException {
		return this.findBySsnQuery.execute(ssn);
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
	 * @see com.miracom.bpms.security.core.dao.IPrincipalDao#findByRole(com.miracom.bpms.security.core.IRole)
	 */
	public List findByRoleId(String roleId) throws DataAccessException {
		assert roleId != null : "Role must not be null";

		return this.findByRoleQuery.execute(roleId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.miracom.bpms.security.core.dao.IEntityDao#isEnabled(java.lang.String)
	 */
	public boolean isEnabled(String id) throws DataAccessException {
		assert id != null : "Id must not be null.";
		Boolean enabled = (Boolean) getJdbcTemplate().queryForObject("select enabled from SMUser where id='" + id + "'", Boolean.class);
		return enabled != null ? enabled.booleanValue() : false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.miracom.bpms.security.core.dao.IEntityDao#isExist(java.lang.String)
	 */
	public boolean entityExists(String id) throws DataAccessException {
		assert id != null : "Id must not be null";
		return getJdbcTemplate().queryForInt("select count(*) from SMUser where id=?", new Object[] { id }, new int[] { Types.VARCHAR }) == 1;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.miracom.bpms.security.core.dao.IEntityDao#remove(java.lang.String)
	 */
	public void remove(String id) throws DataAccessException {
		assert id != null : "Id must not be null";
		String[] sql = { "delete from SMGroupUser where userId='" + id + "'", // 사용자의 그룹 관계 삭제
				"delete from SMUserRole where userId='" + id + "'", // 사용자의 역할 삭제
				"delete from SMUserProfile where userId='" + id + "'", // 사용자의 프로파일 삭제
				"delete from SMUser where id='" + id + "'" // 그룹 삭제
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
		String[] sql = { "delete from SMGroupUser", // 사용자의 그룹 관계 삭제
				"delete from SMUserRole", // 사용자의 역할 삭제
				"delete from SMUserProfile", // 사용자의 프로파일 삭제
				"delete from SMUser" // 그룹 삭제
		};
		getJdbcTemplate().batchUpdate(sql);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.miracom.bpms.security.core.dao.IUserDao#retrieve(java.lang.String)
	 */
	public IUser retrieve(String id) throws DataAccessException {
		assert id != null : "Id must not be null";
		IUser user = (IUser) this.retrieveQuery.findObject(id);
		if (user == null) {
			throw new ObjectRetrievalFailureException(IUser.class, id);
		}
		return user;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.miracom.bpms.security.core.dao.IEntityDao#retrieveName(java.lang.String)
	 */
	public String retrieveName(String id) throws DataAccessException {
		assert id != null : "Id must not be null";
		return (String) getJdbcTemplate().queryForObject("select name from SMUser where id=?", new Object[] { id }, new int[] { Types.VARCHAR },
				String.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see kr.co.kmac.pms.common.org.dao.IUserDao#retrieveForLogin(java.lang.String)
	 */
	@Override
	public IUser retrieveForLogin(String loginId) throws DataAccessException {
		assert loginId != null : "Id must not be null";
		IUser user = (IUser) this.retrieveQueryForLigin.findObject(loginId);
		if (user == null) {
			throw new ObjectRetrievalFailureException(IUser.class, loginId);
		}
		return user;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.miracom.bpms.security.core.dao.IUserDao#update(com.miracom.bpms.security.core.IUser)
	 */
	public void update(IUser user) throws DataAccessException {
		assert user != null : "User must not be null";
		this.updateQuery.update(user);
	}

}
