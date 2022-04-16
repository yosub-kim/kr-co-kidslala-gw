/*
 * $Id: AuthorityDaoImpl.java,v 1.4 2018/11/12 14:41:21 cvs Exp $
 * created by    : ¾È¼ºÈ£
 * creation-date : 2006. 5. 1.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.system.authority.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import kr.co.kmac.pms.system.authority.dao.AuthorityDao;
import kr.co.kmac.pms.system.authority.data.MenuData;
import kr.co.kmac.pms.system.authority.data.NodeData;
import kr.co.kmac.pms.system.authority.data.RoleData;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.jdbc.object.SqlUpdate;


public class AuthorityDaoImpl extends JdbcDaoSupport implements AuthorityDao {
	private MenuSelect menuSelect;
	private MenuRetrieve menuRetrieve;
	private MenuCreate menuCreate;
	private MenuStore menuStore;
	private MenuRemove menuRemove;
	private RoleRetrieve roleRetrieve;
	private RoleCreate roleCreate;
	private RoleStore roleStore;
	private RoleRemove roleRemove;
	private RoleDetailRetrieve roleDetailRetrieve;
	private RoleDetailRetrieve2 roleDetailRetrieve2;
	private NodeCreate nodeCreate;
	private NodeDelete nodeDelete;
	private RoleDetailCopy nodeCopy;
	private RoleDetailDelete nodeDeleteAll;

	protected class MenuRetrieve extends MappingSqlQuery {
		protected MenuRetrieve(DataSource ds, String query) {
			super(ds, query);
		}
		protected MenuRetrieve(DataSource ds) {
			super(ds, 
					" 	select 	menuNum, menuName, menuPath, menuType, menuUseInfo, menuSort	" +
					"	  from 	Menu 				" +
					"	 where 	menuNum like ? 		" +
					"	   and 	menuName like ? 	" +
					"	   and 	menuPath like ? 	" +
					"	   and	menuType like ? 	" +
					"	   and 	menuUseInfo like ?	" +
					"	   and 	menuSort like ?		" +
					" ORDER BY	menuSort ASC		"
			);
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}
		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			MenuData menuData = new MenuData();
			menuData.setMenuNum(rs.getString("menuNum"));
			menuData.setMenuName(rs.getString("menuName"));
			menuData.setMenuPath(rs.getString("menuPath"));
			menuData.setMenuType(rs.getString("menuType"));
			menuData.setMenuUseInfo(rs.getString("menuUseInfo"));
			menuData.setMenuSort(rs.getString("menuSort"));
			return menuData;
		}
	}
	
	protected class MenuSelect extends MappingSqlQuery {
		protected MenuSelect(DataSource ds, String query) {
			super(ds, query);
			
		}
		protected MenuSelect(DataSource ds) {
			super(ds, 
					" 	select 	menuNum, menuName, menuPath, menuType, menuUseInfo, menuSort	" +
					"	from 	Menu 				" +
					"	where 	menuNum = ? 		"
			);
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}
		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			MenuData menuData = new MenuData();
			menuData.setMenuNum(rs.getString("menuNum"));
			menuData.setMenuName(rs.getString("menuName"));
			menuData.setMenuPath(rs.getString("menuPath"));
			menuData.setMenuType(rs.getString("menuType"));
			menuData.setMenuUseInfo(rs.getString("menuUseInfo"));
			menuData.setMenuSort(rs.getString("menuSort"));
			return menuData;
		}
	}
	
	protected class MenuCreate extends SqlUpdate {
		protected MenuCreate(DataSource ds) {
			super(ds, 
					" 	INSERT 			" +
					"	  INTO 	Menu	" +
					"			( 		" +
					"				menuNum, menuName, menuPath, menuType, menuUseInfo, menuSort	" +
					"			)		" +
					" 	VALUES	(		" +
					"				?, ?, ?, ?, ?, ? 	" +
					"			) 		"
					
			);
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}
		protected int create(MenuData menuData) throws DataAccessException {
			return this.update(new Object[]{
					menuData.getMenuNum(), menuData.getMenuName(), menuData.getMenuPath(), 
					menuData.getMenuType(), menuData.getMenuUseInfo(), menuData.getMenuSort()
			});
		}
	}
	
	protected class MenuStore extends SqlUpdate {
		protected MenuStore(DataSource ds) {
			super(ds, 
					" 	update 	Menu 		" +
					"	   set 	menuName=?, menuPath=?, menuType=?, menuUseInfo=?, menuSort=? " +
					" 	 where 	menuNum=? 	" 
			);
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}
		protected int store(MenuData menuData) throws DataAccessException {
			return this.update(new Object[]{
					menuData.getMenuName(), menuData.getMenuPath(), menuData.getMenuType(), 
					menuData.getMenuUseInfo(), menuData.getMenuSort(), menuData.getMenuNum()
			});
		}
	}
	
	protected class MenuRemove extends SqlUpdate {
		protected MenuRemove(DataSource ds) {
			super(ds, " Delete from Menu where menuNum=? ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}
		protected int remove(String documentNumber) {
			return update(new Object[]{documentNumber});
		}
	}
	
	protected class RoleRetrieve extends MappingSqlQuery {
		protected RoleRetrieve(DataSource ds, String query) {
			super(ds, query);
		}
		protected RoleRetrieve(DataSource ds) {
			super(ds, " select * from role where roleNum like ? and roleName like ? and roleUseInfo like ? "
					+ " order by roleSeq ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}
		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			RoleData roleData = new RoleData();
			roleData.setRoleNum(rs.getString("roleNum"));
			roleData.setRoleName(rs.getString("roleName"));
			roleData.setRoleSeq (rs.getInt("roleSeq"));
			roleData.setRoleUseInfo(rs.getString("roleUseInfo"));
			return roleData;
		}
	}
	
	protected class RoleCreate extends SqlUpdate {
		protected RoleCreate(DataSource ds) {
			super(ds, " INSERT INTO role( "
					+ "		roleNum, roleName, roleUseInfo, roleSeq)"
					+ " VALUES(?, ?, ?, ? ) ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.INTEGER));
			compile();
		}
		protected int create(RoleData roleData) throws DataAccessException {
			return this.update(new Object[]{roleData.getRoleNum(), roleData.getRoleName(), roleData.getRoleUseInfo(), roleData.getRoleSeq()});
		}
	}
	
	protected class RoleStore extends SqlUpdate {
		protected RoleStore(DataSource ds) {
			super(ds, " update Role set "
					+ "		roleName=?, roleUseInfo=? , roleSeq = ?"
					+ " where roleNum=? " );
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.INTEGER));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}
		protected int store(RoleData roleData) throws DataAccessException {
			return this.update(new Object[]{roleData.getRoleName(), roleData.getRoleUseInfo(), roleData.getRoleSeq(), roleData.getRoleNum()});
		}
	}
	
	protected class RoleRemove extends SqlUpdate {
		protected RoleRemove(DataSource ds) {
			super(ds, " Delete from Role where roleNum=? ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}
		protected int remove(String documentNumber) {
			return update(new Object[]{documentNumber});
		}
	}

	protected class RoleDetailRetrieve extends MappingSqlQuery {
		
		protected RoleDetailRetrieve(DataSource ds, String query) {
			super(ds, query);
			
		}
		protected RoleDetailRetrieve(DataSource ds) {
			super(ds, 
					" SELECT A.roleNum " +
					"   , A.nodeKey    " +
					"   , A.menuNum    " +
					"   , B.menuName   " +
					"   , B.menuPath   " +
					"   , B.menuType   " +
					"   , A.ordSeq     " +
					"   , A.pNodeKey   " +
					"   , ?  AS depth  " +
					" FROM RoleDetail AS A INNER JOIN         " +
					"      Menu AS B ON A.menuNum = B.menuNum " +
					" WHERE A.roleNum = ? AND A.pNodeKey = ?  " +
					" ORDER BY CAST(A.OrdSeq AS int) ASC "
			);
			declareParameter(new SqlParameter(Types.INTEGER));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			
			compile();
		}
		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			NodeData nodeData = new NodeData();

			nodeData.setText(rs.getString("menuName"));
			nodeData.setId(rs.getString("nodeKey"));
			nodeData.setMenuId(rs.getString("menuNum"));
			nodeData.setDepth(rs.getInt("depth"));
			nodeData.setUrl(rs.getString("menuPath"));
			
			nodeData.setChecked(false);
			nodeData.setLeaf(true);
			nodeData.setMenuType(rs.getInt("menuType"));
	

			return nodeData;
		}
	}
	
	protected class RoleDetailRetrieve2 extends MappingSqlQuery {
		
		protected RoleDetailRetrieve2(DataSource ds, String query) {
			super(ds, query);
			
		}
		protected RoleDetailRetrieve2(DataSource ds) {
			super(ds, 
					" SELECT A.roleNum " +
							"   , A.nodeKey    " +
							"   , A.menuNum    " +
							"   , B.menuName   " +
							"   , B.menuPath   " +
							"   , B.menuType   " +
							"   , A.ordSeq     " +
							"   , A.pNodeKey   " +
							"   , IIF(C.menuNum is null, 0, 1)    as menuMy    " +
							"   , ?  AS depth  " +
							" FROM RoleDetail AS A INNER JOIN         " +
							"      Menu AS B ON A.menuNum = B.menuNum LEFT OUTER JOIN" +
							"      (select * from MenuMy where creatorSsn = ?) AS C ON B.menuNum = C.menuNum " +
							" WHERE A.roleNum = ? AND A.pNodeKey = ?  " +
							" ORDER BY CAST(A.OrdSeq AS int) ASC "
					);
			declareParameter(new SqlParameter(Types.INTEGER));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			
			compile();
		}
		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			NodeData nodeData = new NodeData();
			
			nodeData.setText(rs.getString("menuName"));
			nodeData.setId(rs.getString("nodeKey"));
			nodeData.setMenuId(rs.getString("menuNum"));
			nodeData.setDepth(rs.getInt("depth"));
			nodeData.setUrl(rs.getString("menuPath"));
			nodeData.setMenuMy(rs.getString("menuMy").equals("1") ? true : false);
			nodeData.setChecked(false);
			nodeData.setLeaf(true);
			nodeData.setMenuType(rs.getInt("menuType"));
			
			
			return nodeData;
		}
	}
	
	protected class NodeCreate extends SqlUpdate {
		protected NodeCreate(DataSource ds) {
			super(ds, 	  " INSERT INTO RoleDetail "
						+ " ("
						+ "		roleNum "
						+ "		, nodeKey " 
						+ "		, menuNum "
						+ "		, ordSeq "
						+ "		, pNodeKey " 
						+ " )VALUES( "
						+ "		 ? "
						+ "		, dbo.getNewNodeKey( ? ) "
						+ "		, ? "
						+ "		, dbo.getNewOrdSeq( ? ) "
						+ "		, ? "
						+ " )"
				    );
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}
		protected int create(String roleNum, String pNodeKey, String menuNum) throws DataAccessException {
			return this.update(new Object[]{ roleNum, roleNum, menuNum, pNodeKey, pNodeKey });
		}
	}
	
	protected class NodeDelete extends SqlUpdate {
		protected NodeDelete(DataSource ds) {
			super(ds, 	  " DELETE FROM RoleDetail "
						+ " WHERE roleNum = ? AND nodeKey = ? "
				    );
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			
			compile();
		}
		protected int delete(String roleNum, String nodeKey) throws DataAccessException {
			return this.update(new Object[]{ roleNum, nodeKey });
		}
	}

	protected class RoleDetailDelete extends SqlUpdate {
		protected RoleDetailDelete(DataSource ds) {
			super(ds, " DELETE FROM RoleDetail "
				     + "   	WHERE roleNum = ? ");	
			
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}
		protected int roleDetailDelete(String roleNum) {
			return update(new Object[]{ roleNum });
		}
	}
	
	protected class RoleDetailCopy extends SqlUpdate {
		protected RoleDetailCopy(DataSource ds) {
			super(ds, " INSERT INTO RoleDetail "
				     + " 	SELECT ?, nodeKey,menuNum,ordSeq,pNodeKey "
				     + " 	FROM RoleDetail "
				     + " 	WHERE roleNum = ? ");
			
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}
		protected int roleDetailCopy(String newRoleNum, String orgRoleNum) {
			return update(new Object[]{ newRoleNum, orgRoleNum });
		}
	}
	
	protected void initDao() throws Exception {
		menuRetrieve= new MenuRetrieve(getDataSource());
		menuSelect = new MenuSelect(getDataSource());
		menuCreate= new MenuCreate(getDataSource());
		menuStore= new MenuStore(getDataSource());
		menuRemove= new MenuRemove(getDataSource());
		roleRetrieve= new RoleRetrieve(getDataSource());
		roleCreate= new RoleCreate(getDataSource());
		roleStore= new RoleStore(getDataSource());
		roleRemove= new RoleRemove(getDataSource());
		
		roleDetailRetrieve = new RoleDetailRetrieve(getDataSource());
		roleDetailRetrieve2 = new RoleDetailRetrieve2(getDataSource());
		nodeCreate = new NodeCreate(getDataSource());
		nodeDelete = new NodeDelete(getDataSource());
		nodeCopy   = new RoleDetailCopy(getDataSource());
		nodeDeleteAll = new RoleDetailDelete(getDataSource());
	}
	
	public void menuCreate(MenuData menuData) throws DataAccessException {
		menuCreate.create(menuData);
	}

	public void menuStore(MenuData menuData) throws DataAccessException {
		menuStore.store(menuData);
	}

	public void menuRemove(String menuNum) throws DataAccessException {
		menuRemove.remove(menuNum);
	}
	
	public List menuSelect(String menuNum) throws DataAccessException {
		return menuSelect.execute(menuNum);
	}
	
	public List menuRetrieve(String menuNum, String menuName, String menuPath, String menuType, String menuUseInfo, String menuSort) throws DataAccessException {
		if(menuNum==null || "".equals(menuNum) || "*".equals(menuNum)) menuNum="%%"; else menuNum = "%" + menuNum + "%";
		if(menuName==null || "".equals(menuName) || "*".equals(menuName)) menuName="%%";  else menuName = "%" + menuName + "%";
		if(menuPath==null || "".equals(menuPath) || "*".equals(menuPath)) menuPath="%%"; else menuPath = "%" + menuPath + "%";
		if(menuType==null || "".equals(menuType) || "*".equals(menuType)) menuType="%%";
		if(menuUseInfo==null || "".equals(menuUseInfo) || "*".equals(menuUseInfo)) menuUseInfo="%%";
		if(menuSort==null || "".equals(menuSort) || "*".equals(menuSort)) menuSort="%%"; else menuSort = "%" + menuSort + "%";
		return menuRetrieve.execute(new Object[]{menuNum, menuName, menuPath, menuType, menuUseInfo, menuSort});
		//return menuRetrieve.execute();
	}

	public void roleCreate(RoleData roleData) throws DataAccessException {
		roleCreate.create(roleData);
	}

	public void roleStore(RoleData roleData) throws DataAccessException {
		roleStore.store(roleData);
	}

	public void roleRemove(String roleNum) throws DataAccessException {
		roleRemove.remove(roleNum);
	}
	
	public List roleRetrieve(String roleNum, String roleName, String roleUseInfo) throws DataAccessException {
		if(roleNum==null || "".equals(roleNum) || "*".equals(roleNum)) roleNum="%%";
		if(roleName==null || "".equals(roleName) || "*".equals(roleName)) roleName="%%";
		if(roleUseInfo==null || "".equals(roleUseInfo) || "*".equals(roleUseInfo)) roleUseInfo="%%";
		return roleRetrieve.execute(new Object[]{roleNum, roleName, roleUseInfo});
	}
	
	public  ArrayList roleDetailRetrieve(int depth, String roleNum, String pNodeKey) throws DataAccessException {
		return (ArrayList) roleDetailRetrieve.execute(new Object[] { depth, roleNum, pNodeKey });	
	}

	public  ArrayList roleDetailRetrieve(int depth, String roleNum, String pNodeKey, String ssn) throws DataAccessException {
		return (ArrayList) roleDetailRetrieve2.execute(new Object[] { depth, ssn, roleNum, pNodeKey});	
	}
	
	public void nodeCreate(String roleNum, String pNodeKey, String menuNum ) throws DataAccessException {
		nodeCreate.create(roleNum, pNodeKey, menuNum);
	}
	
	public void nodeDelete(String roleNum, String nodeKey) throws DataAccessException {
		nodeDelete.delete(roleNum, nodeKey);
	}

	public void nodesCopy(String newRoleNum, String orgRoleNum) throws DataAccessException {
		nodeCopy.roleDetailCopy(newRoleNum, orgRoleNum);
	}
	
	public void nodeDeleteAll(String roleNum) throws DataAccessException {
		nodeDeleteAll.roleDetailDelete(roleNum);
	}
}
