/*
 * $Id: UserRoleDaoImpl.java,v 1.1 2009/09/19 11:15:31 cvs3 Exp $
 * created by    : 최인호
 * creation-date : 2005. 10. 13.
 * =========================================================
 * Copyright (c) 2005 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.common.org.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.sql.DataSource;

import kr.co.kmac.pms.common.org.dao.IUserRoleDao;
import kr.co.kmac.pms.common.org.data.IRole;
import kr.co.kmac.pms.common.org.data.IRoleAssign;
import kr.co.kmac.pms.common.org.data.IUser;
import kr.co.kmac.pms.common.org.data.Role;
import kr.co.kmac.pms.common.org.data.RoleAssign;
import kr.co.kmac.pms.common.org.data.User;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.jdbc.object.SqlUpdate;
import org.springframework.orm.ObjectRetrievalFailureException;

/**
 * UserRoleDao 구현
 * @author 최인호
 * @version $Id: UserRoleDaoImpl.java,v 1.1 2009/09/19 11:15:31 cvs3 Exp $
 */
public class UserRoleDaoImpl extends JdbcDaoSupport implements IUserRoleDao {
	private InsertQuery insertQuery;
	private UpdateQuery updateQuery;
	private RemoveQuery removeQuery;
	private RetrieveQuery retrieveQuery;

	private String countInheritedRole = "select count (distinct SMGroupRole.roleId) from SMGroupRole, SMGroupUser, SMGroup "
			+ "where SMGroupUser.userId = ? and SMGroupRole.roleId = ? "
			+ "and ( "
			+ "(SMGroupUser.groupId = SMGroupRole.groupId and SMGroupRole.inheritance > -1) "
			+ "or "
			+ "( "
			+ "(select SMGroup.path from SMGroup where SMGroup.id = SMGroupUser.groupId) like SMGroup.path + '%' "
			+ "and SMGroupUser.groupId <> SMGroupRole.groupId and SMGroupRole.inheritance > 0 "
			+ ") "
			+ ") "
			+ "and SMGroupRole.groupId = SMGroup.id and SMGroup.enabled = 1";

	private FindQuery findQuery;
	private FindByUserIdQuery findByUserIdQuery;
	private FindByRoleIdQuery findByRoleIdQuery;
	private FindRoleByUserIdQuery findRoleByUserIdQuery;
	private FindInheritedRoleByUserIdQuery findInheritedRoleByUserIdQuery;
	private FindUserByRoleIdQuery findUserByRoleIdQuery;
	private FindInheritedUserByRoleIdQuery findInheritedUserByRoleIdQuery;

	protected class InsertQuery extends SqlUpdate {
		protected InsertQuery(DataSource ds) {
			super(ds, "insert into SMUserRole values (?,?,?,?)");
			declareParameter(new SqlParameter(Types.VARCHAR)); // userId
			declareParameter(new SqlParameter(Types.VARCHAR)); // roleId
			declareParameter(new SqlParameter(Types.VARCHAR)); // assignerId
			declareParameter(new SqlParameter(Types.DATE)); // assignDate
			compile();
			setRequiredRowsAffected(1);
		}
		protected void insert(IRoleAssign roleAssign) {
			super.update(new Object[]{roleAssign.getPrincipalId(),
					roleAssign.getRoleId(),
					roleAssign.getAssignerId(),
					roleAssign.getAssignDate()});
		}
	}
	protected class UpdateQuery extends SqlUpdate {
		protected UpdateQuery(DataSource ds) {
			super(ds, "update SMUserRole set assignerId=?, assignDate=? where userId=? and roleId=?");
			declareParameter(new SqlParameter(Types.VARCHAR)); // assignerId
			declareParameter(new SqlParameter(Types.DATE)); // assignDate
			declareParameter(new SqlParameter(Types.VARCHAR)); // userId
			declareParameter(new SqlParameter(Types.VARCHAR)); // roleId
			compile();
			setRequiredRowsAffected(1);
		}
		protected int update(IRoleAssign roleAssign) {
			return super.update(new Object[]{roleAssign.getAssignerId(),
					roleAssign.getAssignDate(),
					roleAssign.getPrincipalId(),
					roleAssign.getRoleId()});
		}
	}
	protected class RemoveQuery extends SqlUpdate {
		protected RemoveQuery(DataSource ds) {
			super(ds, "delete from SMUserRole where userId=? and roleId=?");
			declareParameter(new SqlParameter(Types.VARCHAR)); // userId
			declareParameter(new SqlParameter(Types.VARCHAR)); // roleId
			compile();
			setRequiredRowsAffected(1);
		}
		protected int remove(IRoleAssign roleAssign) {
			return super.update(new Object[]{roleAssign.getPrincipalId(), roleAssign.getRoleId()});
		}
	}
	protected class RetrieveQuery extends MappingSqlQuery {
		protected RetrieveQuery(DataSource ds, String query) {
			super(ds, query);
		}
		protected RetrieveQuery(DataSource ds) {
			super(ds, "select SMUserRole.*, SMUser.name, SMRole.name, "
					+ "(select name from SMUser where SMUser.id=SMUSerRole.assignerId) "
					+ "from SMUserRole, SMUser, SMRole "
					+ "where SMUser.id = SMUserRole.userId and SMRole.id = SMUserRole.roleId "
					+ "and SMUserRole.userId=? and SMUserRole.roleId=?");
			declareParameter(new SqlParameter(Types.VARCHAR)); // userId
			declareParameter(new SqlParameter(Types.VARCHAR)); // roleId
			compile();
		}
		protected Object mapRow(ResultSet rs, int rownum) throws SQLException {
			IRoleAssign roleAssign = RoleAssign.createUserRoleAssign(rs.getString(1), // SMUserRole.userId
					rs.getString(2) // SMUserRole.roleId
					);
			roleAssign.setAssignerId(rs.getString(3)); // SMUserRole.assignerId
			roleAssign.setAssignDate(rs.getDate(4)); // SMUserRole.assignDate
			roleAssign.setPrincipalName(rs.getString(5)); // SMUser.name
			roleAssign.setRoleName(rs.getString(6)); // SMRole.name
			roleAssign.setAssignerName(rs.getString(7)); // SMUser.name (assignerId's)
			return roleAssign;
		}
	}
	protected class FindQuery extends RetrieveQuery {
		protected FindQuery(DataSource ds) {
			super(ds, "select SMUserRole.*, SMUser.name, SMRole.name, "
					+ "(select name from SMUser where SMUser.id=SMUSerRole.assignerId) "
					+ "from SMUserRole, SMUser, SMRole "
					+ "where SMUser.id = SMUserRole.userId and SMRole.id = SMUserRole.roleId "
					+ "order by SMUserRole.userId");
			compile();
		}
	}
	protected class FindByUserIdQuery extends RetrieveQuery {
		protected FindByUserIdQuery(DataSource ds) {
			super(ds, "select SMUserRole.*, SMUser.name, SMRole.name, "
					+ "(select name from SMUser where SMUser.id=SMUSerRole.assignerId) "
					+ "from SMUserRole, SMUser, SMRole "
					+ "where SMUser.id = SMUserRole.userId and SMRole.id = SMUserRole.roleId and SMUserRole.userId=? "
					+ "order by SMUserRole.roleId");
			declareParameter(new SqlParameter(Types.VARCHAR)); // userId
			compile();
		}
	}
	protected class FindByRoleIdQuery extends RetrieveQuery {
		protected FindByRoleIdQuery(DataSource ds) {
			super(ds, "select SMUserRole.*, SMUser.name, SMRole.name, "
					+ "(select name from SMUser where SMUser.id=SMUSerRole.assignerId) "
					+ "from SMUserRole, SMUser, SMRole "
					+ "where SMUser.id = SMUserRole.userId and SMRole.id = SMUserRole.roleId and SMUserRole.roleId=? "
					+ "order by SMUserRole.userId");
			declareParameter(new SqlParameter(Types.VARCHAR)); // roleId
			compile();
		}
	}
	protected class FindUserByRoleIdQuery extends MappingSqlQuery {
		protected FindUserByRoleIdQuery(DataSource ds, String query) {
			super(ds, query);
		}
		protected FindUserByRoleIdQuery(DataSource ds) {
			super(ds, "select SMUser.* from SMUser, SMUserRole "
					+ "where SMUserRole.userId = SMUser.id and SMUserRole.roleId=? and SMUser.enabled=1");
			declareParameter(new SqlParameter(Types.VARCHAR)); // roleId
			compile();
		}
		protected Object mapRow(ResultSet rs, int rownum) throws SQLException {
			IUser user = new User(rs.getString("id"), rs.getString("name"));
			user.setPassword(rs.getString("password"));
			user.setEnabled(rs.getBoolean("enabled"));
			user.setEmail(rs.getString("email"));
			return user;
		}
	}
	protected class FindInheritedUserByRoleIdQuery extends FindUserByRoleIdQuery {
		protected FindInheritedUserByRoleIdQuery(DataSource ds) {
			super(ds, "select distinct SMUser.* from SMGroupRole, SMGroupUser, SMGroup, SMUser "
					+ "where SMGroupRole.roleId = ? " // roleId
					+ "and ( " //
					+ "(SMGroupUser.groupId = SMGroupRole.groupId and SMGroupRole.inheritance > -1) " //
					+ "or " //
					+ "( " //
					+ "(select SMGroup.path from SMGroup where SMGroup.id = SMGroupUser.groupId) "
					+ "like SMGroup.path + '%' " //
					+ "and SMGroupUser.groupId <> SMGroupRole.groupId and SMGroupRole.inheritance > 0 " + ") " //
					+ ") " //
					+ "and SMGroupRole.groupId = SMGroup.id and SMGroup.enabled = 1 "
					+ "and SMGroupUser.userId = SMUser.id");
			declareParameter(new SqlParameter(Types.VARCHAR)); // roleId
			compile();
		}
	}
	protected class FindRoleByUserIdQuery extends MappingSqlQuery {
		protected FindRoleByUserIdQuery(DataSource ds, String query) {
			super(ds, query);
		}
		protected FindRoleByUserIdQuery(DataSource ds) {
			super(ds, "select SMRole.* from SMRole, SMUserRole "
					+ "where SMUserRole.roleId = SMRole.id and SMUserRole.userId=?");
			declareParameter(new SqlParameter(Types.VARCHAR)); // userId
			compile();
		}
		protected Object mapRow(ResultSet rs, int rownum) throws SQLException {
			IRole role = new Role(rs.getString("id"), rs.getString("name"));
			role.setEnabled(rs.getBoolean("enabled"));
			role.setDescription(rs.getString("description"));
			return role;
		}
	}
	protected class FindInheritedRoleByUserIdQuery extends FindRoleByUserIdQuery {
		protected FindInheritedRoleByUserIdQuery(DataSource ds) {
			super(ds, "select distinct SMRole.* from SMGroupRole, SMGroupUser, SMGroup, SMRole "
					+ "where SMGroupUser.userId = ? " // userId
					+ "and ( " //
					+ "(SMGroupUser.groupId = SMGroupRole.groupId and SMGroupRole.inheritance > -1) " //
					+ "or " //
					+ "( " //
					+ "(select SMGroup.path from SMGroup where SMGroup.id = SMGroupUser.groupId) "
					+ "like SMGroup.path + '%' " //
					+ "and SMGroupUser.groupId <> SMGroupRole.groupId and SMGroupRole.inheritance > 0 " //
					+ ") " //
					+ ") " //
					+ "and SMGroupRole.groupId = SMGroup.id and SMGroup.enabled = 1 "
					+ "and SMGroupRole.roleId = SMRole.id");
			declareParameter(new SqlParameter(Types.VARCHAR)); // userId
			compile();
		}
	}
	/*
	 * (non-Javadoc)
	 * @see org.springframework.dao.support.DaoSupport#initDao()
	 */
	protected void initDao() throws Exception {
		this.insertQuery = new InsertQuery(getDataSource());
		this.updateQuery = new UpdateQuery(getDataSource());
		this.removeQuery = new RemoveQuery(getDataSource());
		this.retrieveQuery = new RetrieveQuery(getDataSource());

		this.findQuery = new FindQuery(getDataSource());
		this.findByUserIdQuery = new FindByUserIdQuery(getDataSource());
		this.findByRoleIdQuery = new FindByRoleIdQuery(getDataSource());
		this.findUserByRoleIdQuery = new FindUserByRoleIdQuery(getDataSource());
		this.findInheritedUserByRoleIdQuery = new FindInheritedUserByRoleIdQuery(getDataSource());
		this.findRoleByUserIdQuery = new FindRoleByUserIdQuery(getDataSource());
		this.findInheritedRoleByUserIdQuery = new FindInheritedRoleByUserIdQuery(getDataSource());
	}

	/*
	 * (non-Javadoc)
	 * @see com.miracom.bpms.security.core.dao.ICommonDao#count()
	 */
	public int count() throws DataAccessException {
		return getJdbcTemplate().queryForInt("select count(*) from SMUserRole");
	}

	/*
	 * (non-Javadoc)
	 * @see com.miracom.bpms.security.core.dao.IUserRoleDao#countByUserId(java.lang.String)
	 */
	public int countByUserId(String userId) throws DataAccessException {
		assert userId != null : "UserId must not be null";
		return getJdbcTemplate().queryForInt("select count(*) from SMUserRole where userId=?",
				new Object[]{userId},
				new int[]{Types.VARCHAR});
	}

	/*
	 * (non-Javadoc)
	 * @see com.miracom.bpms.security.core.dao.IPrincipalRoleDao#countByRoleId(java.lang.String)
	 */
	public int countByRoleId(String roleId) throws DataAccessException {
		assert roleId != null : "RoleId must not be null";
		return getJdbcTemplate().queryForInt("select count(*) from SMUserRole where roleId=?",
				new Object[]{roleId},
				new int[]{Types.VARCHAR});
	}

	/*
	 * (non-Javadoc)
	 * @see com.miracom.bpms.security.core.dao.IPrincipalRoleDao#create(com.miracom.bpms.security.core.IRoleAssign)
	 */
	public void create(IRoleAssign roleAssign) throws DataAccessException {
		assert roleAssign != null : "RoleAssign must not be null";
		this.insertQuery.insert(roleAssign);
	}

	/*
	 * (non-Javadoc)
	 * @see com.miracom.bpms.security.core.dao.ICommonDao#find()
	 */
	public List find() throws DataAccessException {
		return this.findQuery.execute();
	}

	/*
	 * (non-Javadoc)
	 * @see com.miracom.bpms.security.core.dao.IPrincipalRoleDao#findByPrincipalId(java.lang.String)
	 */
	public List findByPrincipalId(String principalId) throws DataAccessException {
		return findByUserId(principalId);
	}

	/*
	 * (non-Javadoc)
	 * @see com.miracom.bpms.security.core.dao.IUserRoleDao#findByUserId(java.lang.String)
	 */
	public List findByUserId(String userId) throws DataAccessException {
		assert userId != null : "UserId must not be null";
		return this.findByUserIdQuery.execute(userId);
	}

	/*
	 * (non-Javadoc)
	 * @see com.miracom.bpms.security.core.dao.IPrincipalRoleDao#findByRoleId(java.lang.String)
	 */
	public List findByRoleId(String roleId) throws DataAccessException {
		assert roleId != null : "RoleId must not be null";
		return this.findByRoleIdQuery.execute(roleId);
	}

	/*
	 * (non-Javadoc)
	 * @see com.miracom.bpms.security.core.dao.IUserRoleDao#findRoleByUserId(java.lang.String, boolean)
	 */
	public List findRoleByUserId(String userId, boolean checkInheritance) throws DataAccessException {
		assert userId != null : "UserId must not be null.";

		if (!isEnable(userId)) {
			if (logger.isDebugEnabled()) {
				logger.debug("User[id=" + userId + "] is disabled.");
			}
			return Collections.EMPTY_LIST;
		}

		List roleList = findRoleByUserIdQuery.execute(userId);
		if (!checkInheritance) {
			return roleList;
		}
		List inheritedRoleList = findInheritedRoleByUserIdQuery.execute(userId);
		for (Iterator i = inheritedRoleList.iterator(); i.hasNext();) {
			Object role = i.next();
			if (!roleList.contains(role)) {
				roleList.add(role);
			}
		}
		return roleList;
	}

	/*
	 * (non-Javadoc)
	 * @see com.miracom.bpms.security.core.dao.IUserRoleDao#findUserByRoleId(java.lang.String, boolean)
	 */
	public List findUserByRoleId(String roleId, boolean checkInheritance) throws DataAccessException {
		assert roleId != null : "RoleId must not be null.";

		List userList = findUserByRoleIdQuery.execute(roleId);
		if (!checkInheritance) {
			return userList;
		}
		List inheritedUserList = findInheritedUserByRoleIdQuery.execute(roleId);
		for (Iterator i = inheritedUserList.iterator(); i.hasNext();) {
			Object user = i.next();
			if (!userList.contains(user)) {
				userList.add(user);
			}
		}
		return userList;
	}
	/*
	 * (non-Javadoc)
	 * @see com.miracom.bpms.security.core.dao.IPrincipalRoleDao#findPrincipalByRoleId(java.lang.String, boolean)
	 */
	public List findPrincipalByRoleId(String roleId, boolean checkInheritance) throws DataAccessException {
		return findUserByRoleId(roleId, checkInheritance);
	}

	/*
	 * (non-Javadoc)
	 * @see com.miracom.bpms.security.core.dao.IPrincipalRoleDao#findRoleByPrincipalId(java.lang.String, boolean)
	 */
	public List findRoleByPrincipalId(String principalId, boolean checkInheritance) throws DataAccessException {
		return findRoleByUserId(principalId, checkInheritance);
	}

	/*
	 * (non-Javadoc)
	 * @see com.miracom.bpms.security.core.dao.IPrincipalRoleDao#hasRole(java.lang.String, java.lang.String)
	 */
	public boolean hasRole(String principalId, String roleId) throws DataAccessException {
		return hasRole(principalId, roleId, false);
	}

	/*
	 * (non-Javadoc)
	 * @see com.miracom.bpms.security.core.dao.IPrincipalRoleDao#hasRole(java.lang.String, java.lang.String, boolean)
	 */
	public boolean hasRole(String principalId, String roleId, boolean checkInheritance) {
		assert principalId != null && roleId != null : "PrincipalId and RoleId must not be null.";

		if (!isEnable(principalId)) {
			if (logger.isDebugEnabled()) {
				logger.debug("User[id=" + principalId + "] is disabled.");
			}
			return false;
		}

		if (!checkInheritance) {
			return principalRoleExists(principalId, roleId);
		} else {
			if (principalRoleExists(principalId, roleId)) {
				return true;
			}
			if (getJdbcTemplate().queryForInt(countInheritedRole,
					new Object[]{principalId, roleId},
					new int[]{Types.VARCHAR, Types.VARCHAR}) > 0) {
				return true;
			}
			return false;
		}
	}

	protected boolean isEnable(String userId) {
		assert userId != null : "Id must not be null.";
		Boolean enabled = (Boolean) getJdbcTemplate().queryForObject("select enabled from SMUser where id=?",
				new Object[]{userId},
				new RowMapper() {
					public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
						return Boolean.valueOf(rs.getBoolean(1));
					}
				});
		return enabled != null ? enabled.booleanValue() : false;
	}

	/*
	 * (non-Javadoc)
	 * @see com.miracom.bpms.security.core.dao.IPrincipalRoleDao#isExist(java.lang.String, java.lang.String)
	 */
	public boolean principalRoleExists(String principalId, String roleId) throws DataAccessException {
		assert principalId != null && roleId != null : "UserId and RoleId must not be null";
		return getJdbcTemplate().queryForInt("select count(*) from SMUserRole where userId=? and roleId=?",
				new Object[]{principalId, roleId},
				new int[]{Types.VARCHAR, Types.VARCHAR}) == 1;
	}

	/*
	 * (non-Javadoc)
	 * @see com.miracom.bpms.security.core.dao.IPrincipalRoleDao#remove(com.miracom.bpms.security.core.IRoleAssign)
	 */
	public void remove(IRoleAssign roleAssign) throws DataAccessException {
		assert roleAssign != null : "RoleAssign must not be null";
		this.removeQuery.remove(roleAssign);
	}

	/*
	 * (non-Javadoc)
	 * @see com.miracom.bpms.security.core.dao.IPrincipalRoleDao#remove(java.lang.String, java.lang.String)
	 */
	public void remove(String principalId, String roleId) throws DataAccessException {
		assert principalId != null && roleId != null : "PrinciaplId and RoleId must not be null";
		this.removeQuery.update(new Object[]{principalId, roleId});
	}

	/*
	 * (non-Javadoc)
	 * @see com.miracom.bpms.security.core.dao.ICommonDao#removeAll()
	 */
	public void removeAll() throws DataAccessException {
		getJdbcTemplate().update("delete from SMUserRole");
	}

	/* (non-Javadoc)
	 * @see com.miracom.bpms.security.core.dao.IPrincipalRoleDao#removeAllByPrincipalId(java.lang.String)
	 */
	public void removeAllByPrincipalId(String principalId) throws DataAccessException {
		getJdbcTemplate().update("delete from SMUserRole where userId=?", new Object[]{principalId});
	}

	/* (non-Javadoc)
	 * @see com.miracom.bpms.security.core.dao.IPrincipalRoleDao#removeAllByRoleId(java.lang.String)
	 */
	public void removeAllByRoleId(String roleId) throws DataAccessException {
		getJdbcTemplate().update("delete from SMUserRole where roleId=?", new Object[]{roleId});
	}

	/*
	 * (non-Javadoc)
	 * @see com.miracom.bpms.security.core.dao.IPrincipalRoleDao#retrieve(java.lang.String, java.lang.String)
	 */
	public IRoleAssign retrieve(String userId, String roleId) throws DataAccessException {
		assert userId != null && roleId != null : "UserId and RoleId must not be null";

		IRoleAssign roleAssign = (IRoleAssign) this.retrieveQuery.findObject(new Object[]{userId, roleId});
		if (roleAssign == null) {
			throw new ObjectRetrievalFailureException(Class.class, userId + ":" + roleId);
		}
		return roleAssign;
	}

	/*
	 * (non-Javadoc)
	 * @see com.miracom.bpms.security.core.dao.IPrincipalRoleDao#update(com.miracom.bpms.security.core.IRoleAssign)
	 */
	public void update(IRoleAssign roleAssign) throws DataAccessException {
		assert roleAssign != null : "RoleAssign must not be null";
		this.updateQuery.update(roleAssign);
	}
}
