package kr.co.kmac.pms.common.menuMy.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import javax.sql.DataSource;

import kr.co.kmac.pms.common.menuMy.dao.MenuMyDao;
import kr.co.kmac.pms.common.menuMy.data.MenuMy;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.jdbc.object.SqlUpdate;

public class MenuMyDaoImpl extends JdbcDaoSupport implements MenuMyDao {

	private MenuMyInsertQuery menuMyInsertQuery;
	private MenuMyUpdateQuery menuMyUpdateQuery;
	private MenuMyDeleteQuery menuMyDeleteQuery;
	private MenuMySelectQuery menuMySelectQuery;

	@Override
	protected void initDao() throws Exception {
		this.menuMyInsertQuery = new MenuMyInsertQuery(getDataSource());
		this.menuMyUpdateQuery = new MenuMyUpdateQuery(getDataSource());
		this.menuMyDeleteQuery = new MenuMyDeleteQuery(getDataSource());
		this.menuMySelectQuery = new MenuMySelectQuery(getDataSource());
	}

	protected class MenuMyInsertQuery extends SqlUpdate {
		protected MenuMyInsertQuery(DataSource ds) {
			super(ds, " INSERT INTO menuMy (menuNum, creatorSsn, orderSeq, createTime) VALUES (?, ?, ?, getdate())	");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.INTEGER));
			compile();
		}

		protected int insert(MenuMy menuMy) throws DataAccessException {
			return this.update(new Object[] { menuMy.getMenuNum(), menuMy.getCreatorSsn(), menuMy.getOrderSeq() });
		}
	}

	protected class MenuMyUpdateQuery extends SqlUpdate {
		protected MenuMyUpdateQuery(DataSource ds) {
			super(ds, " UPDATE 	menuMy	SET	orderSeq=?		WHERE	menuNum=? and creatorSsn=?	");
			declareParameter(new SqlParameter(Types.INTEGER));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected int update(MenuMy menuMy) throws DataAccessException {
			return this.update(new Object[] { menuMy.getOrderSeq(), menuMy.getMenuNum(), menuMy.getCreatorSsn() });
		}
	}

	protected class MenuMyDeleteQuery extends SqlUpdate {
		protected MenuMyDeleteQuery(DataSource ds) {
			super(ds, " DELETE FROM menuMy WHERE  menuNum=? and creatorSsn=? 	");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected int delete(String menuNum, String creatorSsn) throws DataAccessException {
			return this.update(new Object[] { menuNum, creatorSsn });
		}
	}

	protected class MenuMySelectQuery extends MappingSqlQuery {
		protected MenuMySelectQuery(DataSource ds, String query) {
			super(ds, query);
		}

		protected MenuMySelectQuery(DataSource ds) {
			super(ds, "		SELECT	a.*, b.menuName, b.menuPath FROM menuMy a WITH(NOLOCK)	 "
					+ "	inner Join	MENU b WITH(NOLOCK)										 "
					+ "			on	a.menuNum = b.menuNum									 "
					+ "		 WHERE	a.creatorSsn=?											 "
					+ "	  ORDER BY	a.orderSeq												 ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected Object mapRow(ResultSet rs, int rownum) throws SQLException {
			MenuMy menuMy = new MenuMy();
			menuMy.setMenuNum(rs.getString("menuNum"));
			menuMy.setMenuName(rs.getString("menuName"));
			menuMy.setMenuPath(rs.getString("menuPath"));
			menuMy.setCreatorSsn(rs.getString("creatorSsn"));
			menuMy.setOrderSeq(rs.getInt("orderSeq"));
			menuMy.setCreateTime(rs.getDate("createTime"));
			return menuMy;
		}
	}

	@Override
	public void createMenuMy(MenuMy menuMy) throws DataAccessException {
		this.menuMyInsertQuery.insert(menuMy);

	}

	@Override
	public int updateMenuMy(MenuMy menuMy) throws DataAccessException {
		return this.menuMyUpdateQuery.update(menuMy);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MenuMy> findMenuMyList(String creatorSsn) throws DataAccessException {
		return this.menuMySelectQuery.execute(new Object[] { creatorSsn });
	}

	@Override
	public int deleteMenuMy(String menuNum, String creatorSsn) throws DataAccessException {
		return this.menuMyDeleteQuery.delete(menuNum, creatorSsn);

	}

}
