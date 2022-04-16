/*
 * $Id: GroupDaoImpl.java,v 1.7 2017/06/01 08:37:51 cvs Exp $
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

import kr.co.kmac.pms.common.org.dao.IGroupDao;
import kr.co.kmac.pms.common.org.data.Group;
import kr.co.kmac.pms.common.org.data.IGroup;
import kr.co.kmac.pms.common.org.data.IUser;
import kr.co.kmac.pms.common.org.data.User;
import kr.co.kmac.pms.common.org.util.GroupUtils;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.jdbc.object.SqlUpdate;
import org.springframework.orm.ObjectRetrievalFailureException;

/**
 * GroupDao 구현
 * 
 * @author 최인호
 * @version $Id: GroupDaoImpl.java,v 1.7 2017/06/01 08:37:51 cvs Exp $
 */
public class GroupDaoImpl extends JdbcDaoSupport implements IGroupDao {
	private AddUserMemberQuery addUserMemberQuery;
	private RemoveUserMemberQuery removeUserMemberQuery;

	private InsertQuery insertQuery;
	private UpdateQuery updateQuery;
	private RetrieveQuery retrieveQuery;
	private RetrieveByPathQuery retrieveByPathQuery;

	private FindQuery findQuery;
	private FindByGroupTypeQuery findByGroupTypeQuery;
	private FindByDepthQuery findByDepthQuery;
	private FindByGroupTypeAndDepthQuery findByGroupTypeAndDepthQuery;
	private FindByGroupTypeAndDepthQuery2 findByGroupTypeAndDepthQuery2;
	private FindByEnabledAndDepthQuery findByEnabledAndDepthQuery;
	private FindByNameQuery findByNameQuery;
	private FindByGroupTypeAndNameQuery findByGroupTypeAndNameQuery;
	private FindByStateQuery findByStateQuery;
	private FindByGroupTypeAndStateQuery findByGroupTypeAndStateQuery;
	private FindGroupMemberQuery findGroupMemberQuery;
	private FindGroupAllMemberQuery findGroupAllMemberQuery;	
	private FindGroupMemberInDepthQuery findGroupMemberInDepthQuery;
	private FindUserMemberQuery findUserMemberQuery;
	private FindUserMemberInDepthQuery findUserMemberInDepthQuery;
	private FindRootGroupQuery findRootGroupQuery;
	private FindRootGroupByGroupTypeQuery findRootGroupByGroupTypeQuery;
	private FindGroupOfUserQuery findGroupOfUserQuery;
	private FindGroupOfUserByTypeQuery findGroupOfUserByTypeQuery;
	private FindUserNotInGroupInDepthQuery findUserNotInGroupInDepthQuery;

	protected class AddUserMemberQuery extends SqlUpdate {
		protected AddUserMemberQuery(DataSource ds) {
			super(ds, "insert into SMGroupUser values (?,?)");
			declareParameter(new SqlParameter(Types.VARCHAR)); // groupId
			declareParameter(new SqlParameter(Types.VARCHAR)); // userId
			compile();
			setRequiredRowsAffected(1);
		}

		protected void add(String parentId, String userId) {
			super.update(new Object[] { parentId, userId });
		}
	}

	protected class RemoveUserMemberQuery extends SqlUpdate {
		protected RemoveUserMemberQuery(DataSource ds) {
			super(ds, "delete from SMGroupUser where groupId=? and userId=?");
			declareParameter(new SqlParameter(Types.VARCHAR)); // groupId
			declareParameter(new SqlParameter(Types.VARCHAR)); // userId
			compile();
			//setRequiredRowsAffected(1);
		}

		protected void remove(IGroup parent, IUser child) {
			super.update(new Object[] { parent.getId(), child.getId() });
		}
	}

	protected class InsertQuery extends SqlUpdate {
		protected InsertQuery(DataSource ds) {
			super(ds, "insert into SMGroup values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			declareParameter(new SqlParameter(Types.VARCHAR)); // id
			declareParameter(new SqlParameter(Types.VARCHAR)); // name
			declareParameter(new SqlParameter(Types.INTEGER)); // groupType
			declareParameter(new SqlParameter(Types.BIT)); // enabled
			declareParameter(new SqlParameter(Types.VARCHAR)); // memberRule
			declareParameter(new SqlParameter(Types.VARCHAR)); // description
			declareParameter(new SqlParameter(Types.VARCHAR)); // parentId
			declareParameter(new SqlParameter(Types.VARCHAR)); // path
			declareParameter(new SqlParameter(Types.INTEGER)); // depth
			declareParameter(new SqlParameter(Types.VARCHAR)); // seq
			compile();
			setRequiredRowsAffected(1);
		}

		protected void insert(IGroup group) {
			super.update(new Object[] { group.getId(), group.getName(), new Integer(group.getGroupType()), Boolean.valueOf(group.isEnabled()),
					group.getMemberRule(), group.getDescription(), group.getParentId(), group.getPath(), new Integer(group.getDepth()),
					group.getOrderSeq() });
		}
	}

	protected class UpdateQuery extends SqlUpdate {
		protected UpdateQuery(DataSource ds) {
			super(ds, "update SMGroup set "
					+ "name=?, groupType=?, enabled=?, memberRule=?, description=?, parentId=?, path=?, depth=?, seq=? where id=?");
			declareParameter(new SqlParameter(Types.VARCHAR)); // name
			declareParameter(new SqlParameter(Types.INTEGER)); // groupType
			declareParameter(new SqlParameter(Types.BIT)); // enabled
			declareParameter(new SqlParameter(Types.VARCHAR)); // memberRule
			declareParameter(new SqlParameter(Types.VARCHAR)); // description
			declareParameter(new SqlParameter(Types.VARCHAR)); // parentId
			declareParameter(new SqlParameter(Types.VARCHAR)); // path
			declareParameter(new SqlParameter(Types.INTEGER)); // depth
			declareParameter(new SqlParameter(Types.VARCHAR)); // seq
			declareParameter(new SqlParameter(Types.VARCHAR)); // id
			compile();
			setRequiredRowsAffected(1);
		}

		protected void update(IGroup group) {
			super.update(new Object[] { group.getName(), new Integer(group.getGroupType()), Boolean.valueOf(group.isEnabled()),
					group.getMemberRule(), group.getDescription(), group.getParentId(), group.getPath(), new Integer(group.getDepth()),
					group.getOrderSeq(), group.getId() });
		}
	}

	protected class RetrieveQuery extends MappingSqlQuery {
		protected RetrieveQuery(DataSource ds, String query) {
			super(ds, query);
		}

		protected RetrieveQuery(DataSource ds) {
			super(ds, "select * from SMGroup  WITH(NOLOCK) where id=?");
			declareParameter(new SqlParameter(Types.VARCHAR)); // id
			compile();
		}

		protected Object mapRow(ResultSet rs, int rownum) throws SQLException {
			Group group = (Group) createGroup(rs.getString("id"), rs.getString("name"), rs.getInt("groupType"));
			group.setEnabled(rs.getBoolean("enabled"));
			group.setMemberRule(rs.getString("memberRule"));
			group.setDescription(rs.getString("description"));
			group.setParentId(rs.getString("parentId"));
			group.setPath(rs.getString("path"));
			group.setDepth(rs.getInt("depth"));
			group.setOrderSeq(rs.getString("seq"));
			return group;
		}
	}

	protected class RetrieveByPathQuery extends RetrieveQuery {
		protected RetrieveByPathQuery(DataSource ds) {
			super(ds, "select * from SMGroup  WITH(NOLOCK) where path=? and enabled=1");
			declareParameter(new SqlParameter(Types.VARCHAR)); // path
			compile();
		}
	}

	protected class FindQuery extends RetrieveQuery {
		protected FindQuery(DataSource ds) {
			super(ds, "select * from SMGroup  WITH(NOLOCK) where enabled=1 order by seq ");
			compile();
		}
	}

	protected class FindByGroupTypeQuery extends RetrieveQuery {
		protected FindByGroupTypeQuery(DataSource ds) {
			super(ds, "select * from SMGroup  WITH(NOLOCK) where groupType >= ? and groupType < ? and enabled=1 order by seq");
			declareParameter(new SqlParameter(Types.INTEGER)); // groupType min
			declareParameter(new SqlParameter(Types.INTEGER)); // groupType max
			compile();
		}
	}

	protected class FindByDepthQuery extends RetrieveQuery {
		protected FindByDepthQuery(DataSource ds) {
			super(ds, "select * from SMGroup  WITH(NOLOCK) where depth=? and enabled=1 order by seq");
			declareParameter(new SqlParameter(Types.INTEGER)); // depth
			compile();
		}
	}

	protected class FindByGroupTypeAndDepthQuery extends RetrieveQuery {
		protected FindByGroupTypeAndDepthQuery(DataSource ds) {
			super(ds, "select * from SMGroup  WITH(NOLOCK) where groupType >= ? and groupType < ? and depth=? and enabled=1 order by seq");
			declareParameter(new SqlParameter(Types.INTEGER)); // groupType min
			declareParameter(new SqlParameter(Types.INTEGER)); // groupType max
			declareParameter(new SqlParameter(Types.INTEGER)); // depth
			compile();
		}
	}

	protected class FindByGroupTypeAndDepthQuery2 extends RetrieveQuery {
		protected FindByGroupTypeAndDepthQuery2(DataSource ds) {
			super(ds, "select * from SMGroup WITH(NOLOCK)  where memberRule like ? and depth=? and enabled='1' order by seq");
			declareParameter(new SqlParameter(Types.VARCHAR)); // 
			declareParameter(new SqlParameter(Types.INTEGER)); // depth
			compile();
		}
	}
	
	protected class FindByEnabledAndDepthQuery extends RetrieveQuery {
		protected FindByEnabledAndDepthQuery(DataSource ds) {
			super(ds, "select * from SMGroup WITH(NOLOCK)  where enabled=? and memberRule like ? and depth=? order by seq");
			declareParameter(new SqlParameter(Types.INTEGER)); // enabled
			declareParameter(new SqlParameter(Types.VARCHAR)); // 
			declareParameter(new SqlParameter(Types.INTEGER)); // depth
			compile();
		}
	}

	protected class FindByNameQuery extends RetrieveQuery {
		protected FindByNameQuery(DataSource ds) {
			super(ds, "select * from SMGroup  WITH(NOLOCK) where name like ? and enabled=1 order by seq");
			declareParameter(new SqlParameter(Types.VARCHAR)); // name
			compile();
		}
	}

	protected class FindByGroupTypeAndNameQuery extends RetrieveQuery {
		protected FindByGroupTypeAndNameQuery(DataSource ds) {
			super(ds, "select * from SMGroup where groupType >= ? and groupType < ? and name like ? and enabled=1 order by seq");
			declareParameter(new SqlParameter(Types.INTEGER)); // groupType min
			declareParameter(new SqlParameter(Types.INTEGER)); // groupType max
			declareParameter(new SqlParameter(Types.VARCHAR)); // name
			compile();
		}
	}

	protected class FindByStateQuery extends RetrieveQuery {
		protected FindByStateQuery(DataSource ds) {
			super(ds, "select * from SMGroup where enabled=? order by seq");
			declareParameter(new SqlParameter(Types.BIT)); // enabled
			compile();
		}
	}

	protected class FindByGroupTypeAndStateQuery extends RetrieveQuery {
		protected FindByGroupTypeAndStateQuery(DataSource ds) {
			super(ds, "select * from SMGroup where groupType >= ? and groupType < ? and enabled=? order by seq");
			declareParameter(new SqlParameter(Types.INTEGER)); // groupType min
			declareParameter(new SqlParameter(Types.INTEGER)); // groupType max
			declareParameter(new SqlParameter(Types.BIT)); // enabled
			compile();
		}
	}

	protected class FindGroupMemberQuery extends RetrieveQuery {
		protected FindGroupMemberQuery(DataSource ds) {
			super(ds, "select * from SMGroup where parentId=? and enabled=1 order by seq");
			declareParameter(new SqlParameter(Types.VARCHAR)); // parentId
			compile();
		}
	}
	
	protected class FindGroupAllMemberQuery extends RetrieveQuery {
		protected FindGroupAllMemberQuery(DataSource ds) {
			super(ds, "select * from SMGroup where parentId=? order by seq");
			declareParameter(new SqlParameter(Types.VARCHAR)); // parentId
			compile();
		}
	}

	protected class FindGroupMemberInDepthQuery extends RetrieveQuery {
		protected FindGroupMemberInDepthQuery(DataSource ds) {
			super(ds, "select * from SMGroup where path like ? and enabled=1 order by seq");
			declareParameter(new SqlParameter(Types.VARCHAR)); // path
			compile();
		}
	}

	protected class FindUserMemberQuery extends MappingSqlQuery {
		public FindUserMemberQuery(DataSource ds, String sql) {
			super(ds, sql);
		}

		protected FindUserMemberQuery(DataSource ds) {
			super(ds, "select SMUser.* from SMUser, SMGroupUser "
					+ "where SMUser.id=SMGroupUser.userId and SMGroupUser.groupId=? and SMUser.enabled=1 order by SMUser.seq");
			declareParameter(new SqlParameter(Types.VARCHAR)); // groupId
			compile();
		}

		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			IUser user = new User(rs.getString("id"), rs.getString("name"));
			user.setPassword(rs.getString("password"));
			user.setEnabled(rs.getBoolean("enabled"));
			user.setEmail(rs.getString("email"));
			user.setOrderSeq(rs.getString("seq"));
			return user;
		}
	}

	protected class FindUserMemberInDepthQuery extends FindUserMemberQuery {
		protected FindUserMemberInDepthQuery(DataSource ds) {
			super(ds, " select SMUser.* from SMUser, SMGroup, SMGroupUser                "
					+ " where SMUser.id = SMGroupUser.userId                             "
					+ " and SMGroupUser.groupId = SMGroup.id                             "
					+ " and SMGroup.path like ? order by SMUser.seq                      ");
			declareParameter(new SqlParameter(Types.VARCHAR)); // path
			compile();
		}
	}

	protected class FindRootGroupQuery extends RetrieveQuery {
		protected FindRootGroupQuery(DataSource ds) {
			super(ds, "select * from SMGroup where parentId is null and enabled=1 order by seq");
			compile();
		}
	}

	protected class FindRootGroupByGroupTypeQuery extends RetrieveQuery {
		protected FindRootGroupByGroupTypeQuery(DataSource ds) {
			super(ds, "select * from SMGroup where parentId is null and groupType >= ? and groupType < ?  and enabled=1 order by seq");
			declareParameter(new SqlParameter(Types.INTEGER)); // groupType min
			declareParameter(new SqlParameter(Types.INTEGER)); // groupType max
			compile();
		}
	}

	protected class FindGroupOfUserQuery extends RetrieveQuery {
		protected FindGroupOfUserQuery(DataSource ds) {
			super(ds, "select SMGroup.* from SMGroup, SMGroupUser "
					+ "where SMGroup.id = SMGroupUser.groupId and SMGroupUser.userId=? and SMGroup.enabled=1 order by SMGroup.seq");
			declareParameter(new SqlParameter(Types.VARCHAR)); // userId
		}
	}

	protected class FindGroupOfUserByTypeQuery extends RetrieveQuery {
		protected FindGroupOfUserByTypeQuery(DataSource ds) {
			super(ds, "select SMGroup.* from SMGroup, SMGroupUser where SMGroup.id = SMGroupUser.groupId and SMGroupUser.userId=? "
					+ "and SMGroup.groupType >= ? and SMGroup.groupType < ?  order by SMGroup.seq");
			declareParameter(new SqlParameter(Types.VARCHAR)); // userId
			declareParameter(new SqlParameter(Types.INTEGER)); // groupType min
			declareParameter(new SqlParameter(Types.INTEGER)); // groupType max
		}
	}

	protected class FindUserNotInGroupInDepthQuery extends FindUserMemberQuery {
		protected FindUserNotInGroupInDepthQuery(DataSource ds) {
			super(ds, "select SMUser.* from SMUser where SMUser.id not in (select distinct SMGroupUser.userId from SMGroupUser, SMGroup "
					+ "where SMGroupUser.groupId = SMGroup.id and SMGroup.path like ? order by SMUser.seq)");
			declareParameter(new SqlParameter(Types.VARCHAR)); // path
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

		this.addUserMemberQuery = new AddUserMemberQuery(getDataSource());
		this.removeUserMemberQuery = new RemoveUserMemberQuery(getDataSource());

		this.findQuery = new FindQuery(getDataSource());
		this.findByGroupTypeQuery = new FindByGroupTypeQuery(getDataSource());
		this.findByDepthQuery = new FindByDepthQuery(getDataSource());
		this.findByGroupTypeAndDepthQuery = new FindByGroupTypeAndDepthQuery(getDataSource());
		this.findByGroupTypeAndDepthQuery2 = new FindByGroupTypeAndDepthQuery2(getDataSource());
		this.findByEnabledAndDepthQuery = new FindByEnabledAndDepthQuery(getDataSource());
		this.findByNameQuery = new FindByNameQuery(getDataSource());
		this.findByGroupTypeAndNameQuery = new FindByGroupTypeAndNameQuery(getDataSource());
		this.findByStateQuery = new FindByStateQuery(getDataSource());
		this.findByGroupTypeAndStateQuery = new FindByGroupTypeAndStateQuery(getDataSource());

		this.findGroupMemberQuery = new FindGroupMemberQuery(getDataSource());
		this.findGroupAllMemberQuery = new FindGroupAllMemberQuery(getDataSource());
		this.findGroupMemberInDepthQuery = new FindGroupMemberInDepthQuery(getDataSource());
		this.findUserMemberQuery = new FindUserMemberQuery(getDataSource());
		this.findUserMemberInDepthQuery = new FindUserMemberInDepthQuery(getDataSource());

		this.findRootGroupQuery = new FindRootGroupQuery(getDataSource());
		this.findRootGroupByGroupTypeQuery = new FindRootGroupByGroupTypeQuery(getDataSource());

		this.findGroupOfUserQuery = new FindGroupOfUserQuery(getDataSource());
		this.findGroupOfUserByTypeQuery = new FindGroupOfUserByTypeQuery(getDataSource());

		this.findUserNotInGroupInDepthQuery = new FindUserNotInGroupInDepthQuery(getDataSource());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.miracom.bpms.security.core.dao.IGroupDao#addGroupMember(com.miracom.bpms.security.core.IGroup, com.miracom.bpms.security.core.IGroup)
	 */
	public void addGroupMember(String parentId, String groupId) throws DataAccessException {
		assert (groupId != null) : "GroupId must not be null";

		IGroup group = retrieve(groupId);
		if (parentId == null) {
			GroupUtils.makeRoot(group);
		} else {
			IGroup parent = retrieve(parentId);
			GroupUtils.setParent(parent, group);
		}
		update(group);
		// 하위 그룹의 패스와 깊이를 변경한다.
		changeChildPath(group);
	}

	/**
	 * 하위 그룹의 패스와 깊이를 변경한다.
	 * 
	 * @param group
	 */
	protected void changeChildPath(IGroup group) {
		List groupMember = findGroupMember(group.getId(), false);
		if (groupMember != null && groupMember.size() > 0) {
			for (Iterator i = groupMember.iterator(); i.hasNext();) {
				IGroup child = (IGroup) i.next();
				GroupUtils.setParent(group, child);
				update(child);
				changeChildPath(child);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.miracom.bpms.security.core.dao.IGroupDao#addUserMember(com.miracom.bpms.security.core.IGroup, com.miracom.bpms.security.core.IUser)
	 */
	public void addUserMember(String parentId, String userId) throws DataAccessException {
		assert (parentId != null && userId != null) : "Parent and Child must not be null";

		this.addUserMemberQuery.add(parentId, userId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.miracom.bpms.security.core.dao.IGroupDao#containsGroup(java.lang.String, java.lang.String)
	 */
	public boolean containsGroup(String parentId, String groupId) {
		assert parentId != null && groupId != null : "ParentId and GroupId must not be null.";
		String result = (String) getJdbcTemplate().queryForObject("select parentId from SMGroup where id=?", new Object[] { groupId },
				new int[] { Types.VARCHAR }, String.class);
		return result != null && result.equals(parentId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.miracom.bpms.security.core.dao.IGroupDao#containsUser(java.lang.String, java.lang.String)
	 */
	public boolean containsUser(String parentId, String userId) {
		return getJdbcTemplate().queryForInt("select count(*) from SMGroupUser where groupId=? and userId=?", new Object[] { parentId, userId },
				new int[] { Types.VARCHAR, Types.VARCHAR }) == 1;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.miracom.bpms.security.core.dao.IEntityDao#count()
	 */
	public int count() throws DataAccessException {
		return count(GROUP_GENERAL);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.miracom.bpms.security.core.dao.IGroupDao#count(int)
	 */
	public int count(int groupType) throws DataAccessException {
		if (groupType == GROUP_GENERAL) {
			return getJdbcTemplate().queryForInt("select count(*) from SMGroup");
		} else {
			return getJdbcTemplate().queryForInt("select count(*) from SMGroup where groupType >= ? and groupType < ?",
					new Object[] { new Integer(groupType), new Integer(groupType + GroupUtils.getLevelStep(groupType)) },
					new int[] { Types.INTEGER, Types.INTEGER });
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.miracom.bpms.security.core.dao.IEntityDao#countByState(boolean)
	 */
	public int count(boolean enabled) throws DataAccessException {
		return count(GROUP_GENERAL, enabled);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.miracom.bpms.security.core.dao.IGroupDao#countByState(int, boolean)
	 */
	public int count(int groupType, boolean enabled) throws DataAccessException {
		if (groupType == GROUP_GENERAL) {
			return getJdbcTemplate().queryForInt("select count(*) from SMGroup where enabled=?", new Object[] { Boolean.valueOf(enabled) },
					new int[] { Types.BIT });
		} else {
			return getJdbcTemplate().queryForInt("select count(*) from SMGroup where groupType >= ? and groupType < ? and enabled=?",
					new Object[] { new Integer(groupType), new Integer(groupType + GroupUtils.getLevelStep(groupType)), Boolean.valueOf(enabled) },
					new int[] { Types.INTEGER, Types.INTEGER, Types.BIT });
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.miracom.bpms.security.core.dao.IGroupDao#countGroupMember(java.lang.String)
	 */
	public int countGroupMember(String parentId) throws DataAccessException {
		return countGroupMember(parentId, false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.miracom.bpms.security.core.dao.IGroupDao#countGroupMember(com.miracom.bpms.security.core.IGroup, boolean)
	 */
	public int countGroupMember(String parentId, boolean inDepth) throws DataAccessException {
		assert parentId != null : "Parent must not be null";

		if (!inDepth) {
			return getJdbcTemplate().queryForInt("select count(*) from SMGroup where parentId=?", new Object[] { parentId },
					new int[] { Types.VARCHAR });
		} else {
			String path = (String) getJdbcTemplate().queryForObject("select path from SMGroup where id=?", new Object[] { parentId },
					new int[] { Types.VARCHAR }, String.class);
			assert path != null;
			return getJdbcTemplate().queryForInt("select count(*) from SMGroup where path like ?", new Object[] { getPathArg(path, false) },
					new int[] { Types.VARCHAR });
		}
	}

	protected String getPathArg(String path, boolean includeSelf) {
		if (includeSelf) {
			return path + "%";
		} else {
			return path + PATH_SEPARATOR + "%";
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.miracom.bpms.security.core.dao.IGroupDao#countMember(com.miracom.bpms.security.core.IGroup, boolean)
	 */
	public int countMember(String parentId, boolean inDepth) throws DataAccessException {
		assert parentId != null : "Parent must not be null";

		return countGroupMember(parentId, inDepth) + countUserMember(parentId, inDepth);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.miracom.bpms.security.core.dao.IGroupDao#countMember(java.lang.String)
	 */
	public int countMember(String parentId) throws DataAccessException {
		return countMember(parentId, false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.miracom.bpms.security.core.dao.IGroupDao#countUserMember(com.miracom.bpms.security.core.IGroup, boolean)
	 */
	public int countUserMember(String parentId, boolean inDepth) throws DataAccessException {
		assert parentId != null : "Parent must not be null";

		if (!inDepth) {
			return getJdbcTemplate().queryForInt("select count(*) from SMGroupUser where groupId=?", new Object[] { parentId },
					new int[] { Types.VARCHAR });
		} else {
			String path = (String) getJdbcTemplate().queryForObject("select path from SMGroup where id=?", new Object[] { parentId },
					new int[] { Types.VARCHAR }, String.class);
			assert path != null;
			return getJdbcTemplate().queryForInt(
					"select count(SMGroupUser.userId) from SMGroup, SMGroupUser " + "where SMGroupUser.groupId = SMGroup.id and SMGroup.path like ?",
					new Object[] { getPathArg(path, true) }, new int[] { Types.VARCHAR });
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.miracom.bpms.security.core.dao.IGroupDao#countUserMember(java.lang.String)
	 */
	public int countUserMember(String parentId) throws DataAccessException {
		return countUserMember(parentId, false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.miracom.bpms.security.core.dao.IGroupDao#countRootGroup()
	 */
	public int countRootGroup() throws DataAccessException {
		return countRootGroup(GROUP_GENERAL);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.miracom.bpms.security.core.dao.IGroupDao#countRootGroup(int)
	 */
	public int countRootGroup(int groupType) throws DataAccessException {
		if (groupType == GROUP_GENERAL) {
			return getJdbcTemplate().queryForInt("select count(*) from SMGroup where parentId is null");
		} else {
			return getJdbcTemplate().queryForInt("select count(*) from SMGroup where parentId is null and groupType >= ? and groupType < ?",
					new Object[] { new Integer(groupType), new Integer(groupType + GroupUtils.getLevelStep(groupType)) },
					new int[] { Types.INTEGER, Types.INTEGER });
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.miracom.bpms.security.core.dao.IGroupDao#create(com.miracom.bpms.security.core.IGroup)
	 */
	public void create(IGroup group) throws DataAccessException {
		assert group != null : "Group must not be null";

		this.insertQuery.insert(group);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.miracom.bpms.security.core.dao.IEntityDao#find()
	 */
	public List find() throws DataAccessException {
		return find(GROUP_GENERAL);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.miracom.bpms.security.core.dao.IGroupDao#find(int)
	 */
	public List find(int groupType) throws DataAccessException {
		// if (groupType == GROUP_GENERAL) {
		return this.findQuery.execute();
		// } else {
		// return this.findByGroupTypeQuery.execute(new Object[] { new Integer(groupType),
		// new Integer(groupType + GroupUtils.getLevelStep(groupType)) });
		// }
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.miracom.bpms.security.core.dao.IGroupDao#findByDepth(int)
	 */
	public List findByDepth(int depth) {
		return findByDepth(GROUP_GENERAL);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.miracom.bpms.security.core.dao.IGroupDao#findByDepth(int, int)
	 */
	public List findByDepth(int groupType, int depth) {
		if (groupType == GROUP_GENERAL) {
			return this.findByDepthQuery.execute(depth);
		} else {
			return this.findByGroupTypeAndDepthQuery.execute(new Object[] { new Integer(groupType),
					new Integer(groupType + GroupUtils.getLevelStep(groupType)), new Integer(depth) });
		}
	}
	public List findByDepth(int groupType, String jobClass, int depth) {
		if (jobClass.equals("")) jobClass = "%";
		return this.findByGroupTypeAndDepthQuery2.execute(new Object[] { jobClass, new Integer(depth) });
	}
	public List findByEnabled(int enabled, String jobClass, int depth) {
		if (jobClass.equals("")) jobClass = "%";
		return this.findByEnabledAndDepthQuery.execute(new Object[] {new Integer(enabled), jobClass, new Integer(depth) });
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.miracom.bpms.security.core.dao.IEntityDao#findByName(java.lang.String)
	 */
	public List findByName(String name) throws DataAccessException {
		return findByName(GROUP_GENERAL, name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.miracom.bpms.security.core.dao.IGroupDao#findByName(int, java.lang.String)
	 */
	public List findByName(int groupType, String name) throws DataAccessException {
		if (groupType == GROUP_GENERAL) {
			return this.findByNameQuery.execute(name);
		} else {
			return this.findByGroupTypeAndNameQuery.execute(new Object[] { new Integer(groupType),
					new Integer(groupType + GroupUtils.getLevelStep(groupType)), name });
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.miracom.bpms.security.core.dao.IEntityDao#findByState(boolean)
	 */
	public List find(boolean enabled) throws DataAccessException {
		return find(GROUP_GENERAL, enabled);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.miracom.bpms.security.core.dao.IGroupDao#findByState(int, boolean)
	 */
	public List find(int groupType, boolean enabled) throws DataAccessException {
		if (groupType == GROUP_GENERAL) {
			return this.findByStateQuery.execute(new Object[] { Boolean.valueOf(enabled) });
		} else {
			return this.findByGroupTypeAndStateQuery.execute(new Object[] { new Integer(groupType),
					new Integer(groupType + GroupUtils.getLevelStep(groupType)), Boolean.valueOf(enabled) });
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.miracom.bpms.security.core.dao.IGroupDao#findGroupOfUser(java.lang.String)
	 */
	public List findGroupOfUser(String userId) throws DataAccessException {
		return findGroupOfUser(userId, GROUP_GENERAL);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.miracom.bpms.security.core.dao.IGroupDao#findGroupOfUser(java.lang.String, int)
	 */
	public List findGroupOfUser(String userId, int groupType) throws DataAccessException {
		assert userId != null : "UserId muast not be null";
		if (groupType == GROUP_GENERAL) {
			return this.findGroupOfUserQuery.execute(userId);
		} else {
			return this.findGroupOfUserByTypeQuery.execute(new Object[] { userId, new Integer(groupType),
					new Integer(groupType + GroupUtils.getLevelStep(groupType)) });
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.miracom.bpms.security.core.dao.IGroupDao#findGroupMember(com.miracom.bpms.security.core.IGroup, boolean)
	 */
	public List findGroupMember(String parentId, boolean inDepth) throws DataAccessException {
		assert parentId != null : "Parent must not be null";

		if (!inDepth) {
			return this.findGroupMemberQuery.execute(parentId);
		} else {
			try {
				String path = (String) getJdbcTemplate().queryForObject("select path from SMGroup where id=?", new Object[] { parentId },
						new int[] { Types.VARCHAR }, String.class);
				return this.findGroupMemberInDepthQuery.execute(getPathArg(path, false));
			} catch (IncorrectResultSizeDataAccessException e) {
				return Collections.EMPTY_LIST;
			}
		}
	}

	/* (non-Javadoc)
	 * @see kr.co.kmac.pms.common.org.dao.IGroupDao#findGroupMember(java.lang.String, boolean, boolean)
	 */
	@Override
	public List<Object> findGroupMember(String parentId, boolean inDepth, boolean isAll) throws DataAccessException {
		assert parentId != null : "Parent must not be null";

		if (!inDepth) {
			if(isAll){
				return this.findGroupAllMemberQuery.execute(parentId);
			} else {
				return this.findGroupMemberQuery.execute(parentId);
			}
		} else {
			try {
				String path = (String) getJdbcTemplate().queryForObject("select path from SMGroup where id=?", new Object[] { parentId },
						new int[] { Types.VARCHAR }, String.class);
				return this.findGroupMemberInDepthQuery.execute(getPathArg(path, false));
			} catch (IncorrectResultSizeDataAccessException e) {
				return Collections.EMPTY_LIST;
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.miracom.bpms.security.core.dao.IGroupDao#findGroupMember(java.lang.String)
	 */
	public List findGroupMember(String parentId) throws DataAccessException {
		return findGroupMember(parentId, false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.miracom.bpms.security.core.dao.IGroupDao#findMember(com.miracom.bpms.security.core.IGroup, boolean)
	 */
	public List findMember(String parentId, boolean inDepth) throws DataAccessException {
		assert parentId != null : "Parent must not be null";

		List memberList = findUserMember(parentId, inDepth);
		List groupList = findGroupMember(parentId, inDepth);
		if (memberList != null) {
			if (groupList != null) {
				memberList.addAll(groupList);
			}
		} else {
			if (groupList != null) {
				memberList = groupList;
			}
		}
		return memberList;
	}
	
	/* (non-Javadoc)
	 * @see kr.co.kmac.pms.common.org.dao.IGroupDao#findMember(java.lang.String, boolean, boolean)
	 */
	public List<Object> findMember(String parentId, boolean inDepth, boolean isAll) throws DataAccessException {
		assert parentId != null : "Parent must not be null";

		List memberList = findUserMember(parentId, inDepth);
		List groupList = findGroupMember(parentId, inDepth, isAll);

		if (memberList != null) {
			if (groupList != null) {
				memberList.addAll(groupList);
			}
		} else {
			if (groupList != null) {
				memberList = groupList;
			}
		}
		return memberList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.miracom.bpms.security.core.dao.IGroupDao#findMember(java.lang.String)
	 */
	public List findMember(String parentId) throws DataAccessException {
		return findMember(parentId, false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.miracom.bpms.security.core.dao.IGroupDao#findRootGroup()
	 */
	public List findRootGroup() throws DataAccessException {
		return findRootGroup(GROUP_GENERAL);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.miracom.bpms.security.core.dao.IGroupDao#findRootGroup(int)
	 */
	public List findRootGroup(int groupType) throws DataAccessException {
		if (groupType == GROUP_GENERAL) {
			return this.findRootGroupQuery.execute();
		} else {
			return this.findRootGroupByGroupTypeQuery.execute(new Object[] { new Integer(groupType),
					new Integer(groupType + GroupUtils.getLevelStep(groupType)) });
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.miracom.bpms.security.core.dao.IGroupDao#findUserMember(com.miracom.bpms.security.core.IGroup, boolean)
	 */
	public List findUserMember(String parentId, boolean inDepth) throws DataAccessException {
		assert parentId != null : "Parent must not be null";

		if (!inDepth) {
			return this.findUserMemberQuery.execute(parentId);
		} else {
			String path = (String) getJdbcTemplate().queryForObject("select path from SMGroup where id=?", new Object[] { parentId },
					new int[] { Types.VARCHAR }, String.class);
			return this.findUserMemberInDepthQuery.execute(new Object[] { getPathArg(path, true) });
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.miracom.bpms.security.core.dao.IGroupDao#findUserMember(java.lang.String)
	 */
	public List findUserMember(String parentId) throws DataAccessException {
		return findUserMember(parentId, false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.miracom.bpms.security.core.dao.IGroupDao#findUserNotInGroup(java.lang.String, boolean)
	 */
	public List findUserNotInGroup(String groupId, boolean includeSelf) throws DataAccessException {
		IGroup group = retrieve(groupId);
		String path = null;
		if (groupId == null) {
			path = "/%";
		} else {
			path = getPathArg(group.getPath(), includeSelf);
		}
		return this.findUserNotInGroupInDepthQuery.execute(new Object[] { path });
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.miracom.bpms.security.core.dao.IEntityDao#isExist(java.lang.String)
	 */
	public boolean entityExists(String id) throws DataAccessException {
		assert id != null : "Id must not be null";

		return getJdbcTemplate().queryForInt("select count(*) from SMGroup where id=?", new Object[] { id }, new int[] { Types.VARCHAR }) == 1;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.miracom.bpms.security.core.dao.IEntityDao#isEnabled(java.lang.String)
	 */
	public boolean isEnabled(String id) throws DataAccessException {
		assert id != null : "Id must not be null.";
		Boolean enabled = (Boolean) getJdbcTemplate().queryForObject("select enabled from SMGroup where id=?", new Object[] { id }, new RowMapper() {
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				return Boolean.valueOf(rs.getBoolean(1));
			}
		});
		return enabled != null ? enabled.booleanValue() : false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.miracom.bpms.security.core.dao.IGroupDao#isRooe(java.lang.String)
	 */
	public boolean isRoot(String groupId) throws DataAccessException {
		String parentId = (String) getJdbcTemplate().queryForObject("select parentId from SMGroup where id=?", new Object[] { groupId },
				new int[] { Types.VARCHAR }, String.class);
		return parentId == null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.miracom.bpms.security.core.dao.IGroupDao#getGroupType(java.lang.String)
	 */
	public int getGroupType(String id) throws DataAccessException {
		return getJdbcTemplate().queryForInt("select groupType from SMGroup where id=?", new Object[] { id }, new int[] { Types.VARCHAR });
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.miracom.bpms.security.core.dao.IEntityDao#remove(java.lang.String)
	 */
	public void remove(String id) throws DataAccessException {
		remove(id, true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.miracom.bpms.security.core.dao.IGroupDao#remove(java.lang.String, boolean)
	 */
	public void remove(String groupId, boolean includeAll) throws DataAccessException {
		assert groupId != null : "Id must not be null";

		if (!entityExists(groupId)) {
			throw new ObjectRetrievalFailureException(IGroup.class, groupId);
		}
		if (!includeAll) {
			// 삭제될 그룹에 속한 그룹을 루트그룹으로 만든다.
			List list = findGroupMember(groupId, true);
			for (Iterator i = list.iterator(); i.hasNext();) {
				IGroup group = (IGroup) i.next();
				GroupUtils.makeRoot(group);
				update(group);
			}

			String[] sql = { "delete from SMGroupUser where groupId='" + groupId + "'", // 삭제될 그룹에 속한 사용자 삭제
					"delete from SMGroupRole where groupId='" + groupId + "'", // 삭제될 그룹이 갖는 역할 삭제
					"delete from SMGroup where id='" + groupId + "'" // 그룹 삭제
			};
			int[] result = getJdbcTemplate().batchUpdate(sql);
			if (result[sql.length - 1] != 1) {
				throw new ObjectRetrievalFailureException(IGroup.class, groupId);
			}
		} else {
			String path = (String) getJdbcTemplate().queryForObject("select path from SMGroup where id=?", new Object[] { groupId },
					new int[] { Types.VARCHAR }, String.class);
			String pathArg = getPathArg(path, true);
			// 모든 하위그룹을 포함한다.
			String[] sql = { "delete from SMGroupUser where groupId in (select id from SMGroup where path like '" + pathArg + "')", // 삭제될 그룹에 속한 사용자 삭제
					"delete from SMGroupRole where groupId in (select id from SMGroup where path like '" + pathArg + "')", // 삭제될 그룹이 갖는 역할 삭제
					"delete from SMGroup where path like '" + pathArg + "'" // 그룹 삭제
			};
			getJdbcTemplate().batchUpdate(sql);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.miracom.bpms.security.core.dao.ICommonDao#removeAll()
	 */
	public void removeAll() throws DataAccessException {
		String[] sql = { "delete from SMGroupUser", // 그룹-사용자 관계 삭제
				"delete from SMGroup" // 그룹 삭제
		};
		getJdbcTemplate().batchUpdate(sql);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.miracom.bpms.security.core.dao.IGroupDao#removeGroupMember(com.miracom.bpms.security.core.IGroup, com.miracom.bpms.security.core.IGroup)
	 */
	public void removeGroupMember(String parentId, String groupId) throws DataAccessException {
		assert parentId != null && groupId != null : "ParentId and GroupId must not be null";

		IGroup group = retrieve(groupId);
		GroupUtils.makeRoot(group);
		this.updateQuery.update(group);
		// 하위 그룹의 패스와 깊이를 변경한다.
		changeChildPath(group);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.miracom.bpms.security.core.dao.IGroupDao#removeUserMember(com.miracom.bpms.security.core.IGroup, com.miracom.bpms.security.core.IUser)
	 */
	public void removeUserMember(String parentId, String userId) throws DataAccessException {
		assert parentId != null && userId != null : "Parent and Child must not be null";

		this.removeUserMemberQuery.update(new Object[] { parentId, userId });
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.miracom.bpms.security.core.dao.IGroupDao#retrieve(java.lang.String)
	 */
	public IGroup retrieve(String id) throws DataAccessException {
		assert id != null : "Id must not be null.";

		IGroup group = (IGroup) this.retrieveQuery.findObject(id);
		if (group == null) {
			throw new ObjectRetrievalFailureException(IGroup.class, id);
		}
		return group;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.miracom.bpms.security.core.dao.IGroupDao#retrieveByPath(java.lang.String)
	 */
	public IGroup retrieveByPath(String path) throws DataAccessException {
		assert path != null : "Path must not be null";
		return (IGroup) this.retrieveByPathQuery.findObject(path);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.miracom.bpms.security.core.dao.IEntityDao#retrieveName(java.lang.String)
	 */
	public String retrieveName(String id) throws DataAccessException {
		assert id != null : "Id must not be null";
		return (String) getJdbcTemplate().queryForObject("select name from SMGroup where id=?", new Object[] { id }, new int[] { Types.VARCHAR },
				String.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.miracom.bpms.security.core.dao.IGroupDao#update(com.miracom.bpms.security.core.IGroup)
	 */
	public void update(IGroup group) throws DataAccessException {
		assert group != null : "Group must not be null.";
		this.updateQuery.update(group);
	}

	protected IGroup createGroup(String id, String name, int groupType) {
		return GroupUtils.createGroup(id, name, groupType);
	}

}
