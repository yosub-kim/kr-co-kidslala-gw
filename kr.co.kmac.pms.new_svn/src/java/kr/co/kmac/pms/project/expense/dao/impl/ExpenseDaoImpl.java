package kr.co.kmac.pms.project.expense.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import javax.sql.DataSource;

import kr.co.kmac.pms.project.expense.dao.ExpenseDao;
import kr.co.kmac.pms.project.expense.data.Expense;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.jdbc.object.SqlUpdate;

public class ExpenseDaoImpl extends JdbcDaoSupport implements ExpenseDao {

	private ExpenseInsertQuery expenseInsertQuery;
	private ExpenseDeleteQuery1 expenseDeleteQuery1;
	private ExpenseDeleteQuery2 expenseDeleteQuery2;
	private ExpenseSelectQuery1 expenseSelectQuery1;
	private ExpenseSelectQuery2 expenseSelectQuery2;

	@Override
	protected void initDao() throws Exception {
		this.expenseInsertQuery = new ExpenseInsertQuery(getDataSource());
		this.expenseDeleteQuery1 = new ExpenseDeleteQuery1(getDataSource());
		this.expenseDeleteQuery2 = new ExpenseDeleteQuery2(getDataSource());
		this.expenseSelectQuery1 = new ExpenseSelectQuery1(getDataSource());
		this.expenseSelectQuery2 = new ExpenseSelectQuery2(getDataSource());
	}

	protected class ExpenseInsertQuery extends SqlUpdate {
		protected ExpenseInsertQuery(DataSource ds) {
			super(ds, "INSERT INTO ProjectTeachingFeeM (projectCode, year, month)values (?, ?, ?)");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));

			compile();
			setRequiredRowsAffected(1);
		}

		protected int insert(Expense expense) throws DataAccessException {
			return this.update(new Object[] { expense.getProjectCode(), expense.getYear(), expense.getMonth() });
		}
	}

	protected class ExpenseDeleteQuery1 extends SqlUpdate {
		protected ExpenseDeleteQuery1(DataSource ds) {
			super(ds, " DELETE FROM ProjectTeachingFeeM WHERE projectCode=? ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected int delete(String projectCode) throws DataAccessException {
			return this.update(new Object[] { projectCode });
		}
	}

	protected class ExpenseDeleteQuery2 extends SqlUpdate {
		protected ExpenseDeleteQuery2(DataSource ds) {
			super(ds, " DELETE FROM ProjectTeachingFeeM WHERE projectCode=? and year=? and month=?");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
			setRequiredRowsAffected(1);
		}

		protected int delete(String projectCode, String year, String month) throws DataAccessException {
			return this.update(new Object[] { projectCode, year, month });
		}
	}

	protected class ExpenseSelectQuery1 extends MappingSqlQuery {
		protected ExpenseSelectQuery1(DataSource ds) {
			super(ds, "	SELECT 	* FROM ProjectTeachingFeeM where projectCode=? ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected ExpenseSelectQuery1(DataSource ds, String query) {
			super(ds, query);
		}

		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			Expense expense = new Expense();
			expense.setProjectCode(rs.getString("projectCode"));
			expense.setYear(rs.getString("year"));
			expense.setMonth(rs.getString("month"));
			return expense;
		}
	}

	protected class ExpenseSelectQuery2 extends ExpenseSelectQuery1 {
		protected ExpenseSelectQuery2(DataSource ds) {
			super(ds, "	SELECT 	* FROM ProjectTeachingFeeM WHERE projectCode=? and year=? and month=?");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}
	}

	@Override
	public void delete(String projectCode) throws DataAccessException {
		this.expenseDeleteQuery1.delete(projectCode);
	}

	@Override
	public void delete(String projectCode, String year, String month) throws DataAccessException {
		this.expenseDeleteQuery2.delete(projectCode, year, month);
	}

	@Override
	public void insert(Expense expense) throws DataAccessException {
		this.expenseInsertQuery.insert(expense);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Expense> select(String projectCode) throws DataAccessException {
		return this.expenseSelectQuery1.execute(new Object[] { projectCode });
	}

	@Override
	public Expense select(String projectCode, String year, String month) throws DataAccessException {
		return (Expense) this.expenseSelectQuery2.findObject(new Object[] { projectCode, year, month });
	}

}
