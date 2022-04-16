package kr.co.kmac.pms.project.expense.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import javax.sql.DataSource;

import kr.co.kmac.pms.project.expense.dao.ExpenseRestDao;
import kr.co.kmac.pms.project.expense.data.Expense;
import kr.co.kmac.pms.project.expense.data.ExpenseDetail;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.jdbc.object.SqlUpdate;

public class ExpenseRestDaoImpl extends JdbcDaoSupport implements ExpenseRestDao {

	private ExpenseInsertQuery expenseInsertQuery;
	private ExpenseTempInsertQuery expenseTempInsertQuery;
	private ExpenseRealInsertQuery expenseRealInsertQuery;
	private ExpenseDeleteQuery1 expenseDeleteQuery1;
	private ExpenseDeleteQuery2 expenseDeleteQuery2;
	private ExpenseTempDeleteQuery expenseTempDeleteQuery;
	private ExpenseSelectQuery1 expenseSelectQuery1;
	private ExpenseSelectQuery2 expenseSelectQuery2;
	private ExpenseTempSelectQuery expenseTempSelectQuery;

	@Override
	protected void initDao() throws Exception {
		this.expenseInsertQuery = new ExpenseInsertQuery(getDataSource());
		this.expenseTempInsertQuery = new ExpenseTempInsertQuery(getDataSource());
		this.expenseRealInsertQuery = new ExpenseRealInsertQuery(getDataSource());
		this.expenseDeleteQuery1 = new ExpenseDeleteQuery1(getDataSource());
		this.expenseDeleteQuery2 = new ExpenseDeleteQuery2(getDataSource());
		this.expenseTempDeleteQuery= new ExpenseTempDeleteQuery(getDataSource());
		this.expenseSelectQuery1 = new ExpenseSelectQuery1(getDataSource());
		this.expenseSelectQuery2 = new ExpenseSelectQuery2(getDataSource());
		this.expenseTempSelectQuery = new ExpenseTempSelectQuery(getDataSource());
	}

	protected class ExpenseInsertQuery extends SqlUpdate {
		protected ExpenseInsertQuery(DataSource ds) {
			super(ds, "INSERT INTO ProjectTeachingRestFee (projectCode, year, month)values (?, ?, ?)");
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
	
	protected class ExpenseTempInsertQuery extends SqlUpdate {
		protected ExpenseTempInsertQuery(DataSource ds) {
			super(ds, "INSERT INTO ProjectTeachingRestFeeTemp (projectCode, year, month, ssn, amount) values (?, ?, ?, ?, ?)");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));

			compile();
			setRequiredRowsAffected(1);
		}

		protected int insert(ExpenseDetail expenseDetail) throws DataAccessException {
			return this.update(new Object[] { expenseDetail.getProjectCode(), expenseDetail.getYear(), expenseDetail.getMonth(),
					expenseDetail.getSsn(), expenseDetail.getAmount()	});
		}
	}	
	
	protected class ExpenseRealInsertQuery extends SqlUpdate {
		protected ExpenseRealInsertQuery(DataSource ds) {
			super(ds, " INSERT INTO ProjectTeachingRestFeeDetail (                             "
					+ "	 	  projectCode, year, month, chargeSsn, seq, amount,             "
					+ "       tmCheckYN, ctmCheckYN, approvalYN, billSendYN, payYN )        "
					+ " VALUES (?, ?, ?, ?, ?, ?,   'N', 'N', 'N', 'N', ?)                	");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.INTEGER));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));

			compile();
			setRequiredRowsAffected(1);
		}

		protected int insert(ExpenseDetail expenseDetail) throws DataAccessException {
			return this.update(new Object[] { expenseDetail.getProjectCode(), expenseDetail.getYear(), expenseDetail.getMonth(),
					expenseDetail.getSsn(), expenseDetail.getSeq(), expenseDetail.getAmount().replace(",", "").replace(" ", ""), expenseDetail.getPayYn() });
		}
	}	

	protected class ExpenseDeleteQuery1 extends SqlUpdate {
		protected ExpenseDeleteQuery1(DataSource ds) {
			super(ds, " DELETE FROM ProjectTeachingRestFee WHERE projectCode=? ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected int delete(String projectCode) throws DataAccessException {
			return this.update(new Object[] { projectCode });
		}
	}

	protected class ExpenseDeleteQuery2 extends SqlUpdate {
		protected ExpenseDeleteQuery2(DataSource ds) {
			super(ds, " DELETE FROM ProjectTeachingRestFee WHERE projectCode=? and year=? and month=?");
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
	
	protected class ExpenseTempDeleteQuery extends SqlUpdate {
		protected ExpenseTempDeleteQuery(DataSource ds) {
			super(ds, " DELETE FROM ProjectTeachingRestFeeDetail WHERE projectCode=? and year=? and month=? and chargeSsn=? and seq=?");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.INTEGER));
			compile();
			setRequiredRowsAffected(1);
		}

		protected int delete(ExpenseDetail expenseDetail) throws DataAccessException {
			return this.update(new Object[] { expenseDetail.getProjectCode(), expenseDetail.getYear(), 
					expenseDetail.getMonth(), expenseDetail.getSsn(), expenseDetail.getSeq() });
		}
	}

	protected class ExpenseSelectQuery1 extends MappingSqlQuery {
		protected ExpenseSelectQuery1(DataSource ds) {
			super(ds, "	SELECT 	* FROM ProjectTeachingRestFee where projectCode=? ");
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
			super(ds, "	SELECT 	* FROM ProjectTeachingRestFee WHERE projectCode=? and year=? and month=?");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}
	}
	
	protected class ExpenseTempSelectQuery extends MappingSqlQuery {
		protected ExpenseTempSelectQuery(DataSource ds) {
			super(ds, "	SELECT 	* FROM ProjectTeachingRestFeeTemp WHERE projectCode=? AND year=? AND month=? AND ssn=? ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected ExpenseTempSelectQuery(DataSource ds, String query) {
			super(ds, query);
		}

		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			ExpenseDetail expenseDetail = new ExpenseDetail();
			expenseDetail.setProjectCode(rs.getString("projectCode"));
			expenseDetail.setYear(rs.getString("year"));
			expenseDetail.setMonth(rs.getString("month"));
			expenseDetail.setSsn(rs.getString("ssn"));
			expenseDetail.setAmount(rs.getString("amount"));
			return expenseDetail;
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
	public void deleteTemp(ExpenseDetail expenseDetail) throws DataAccessException {
		this.expenseTempDeleteQuery.delete(expenseDetail);
		
	}
	
	@Override
	public void deleteTemp(String projectCode, String year, String month, String ssn, int seq) throws DataAccessException {
		ExpenseDetail expenseDetail = new ExpenseDetail();
		expenseDetail.setProjectCode(projectCode);
		expenseDetail.setYear(year);
		expenseDetail.setMonth(month);
		expenseDetail.setSsn(ssn);
		expenseDetail.setSeq(seq);
		this.expenseTempDeleteQuery.delete(expenseDetail);
	}

	@Override
	public void insert(Expense expense) throws DataAccessException {
		this.expenseInsertQuery.insert(expense);
	}
	
	@Override
	public void insertTemp(ExpenseDetail expenseDetail) throws DataAccessException {
		this.expenseTempInsertQuery.insert(expenseDetail);
		
	}

	@Override
	public void insertReal(ExpenseDetail expenseDetail) throws DataAccessException {
		this.expenseRealInsertQuery.insert(expenseDetail);
		
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

	@Override
	public ExpenseDetail selectTemp(ExpenseDetail expenseDetail) throws DataAccessException {
		return (ExpenseDetail) this.expenseTempSelectQuery.findObject(new Object[] { 
				expenseDetail.getProjectCode(), expenseDetail.getYear(), expenseDetail.getMonth(), expenseDetail.getSsn() });
	}
}
