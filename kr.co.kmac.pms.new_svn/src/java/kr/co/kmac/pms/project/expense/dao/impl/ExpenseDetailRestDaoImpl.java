package kr.co.kmac.pms.project.expense.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import javax.sql.DataSource;

import kr.co.kmac.pms.project.expense.dao.ExpenseDetailRestDao;
import kr.co.kmac.pms.project.expense.data.ExpenseDetail;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.jdbc.object.SqlUpdate;

public class ExpenseDetailRestDaoImpl extends JdbcDaoSupport implements ExpenseDetailRestDao {
	private ExpenseDetailInsertQuery expenseDetailInsertQuery;
	private ExpenseDetailUpdateQuery1 expenseDetailUpdateQuery1;
	private ExpenseDetailUpdateQuery2 expenseDetailUpdateQuery2;
	private ExpenseDetailDeleteQuery1 expenseDetailDeleteQuery1;
	private ExpenseDetailDeleteQuery2 expenseDetailDeleteQuery2;
	private ExpenseDetailDeleteQuery3 expenseDetailDeleteQuery3;
	private ExpenseDetailDeleteQuery4 expenseDetailDeleteQuery4;
	private ExpenseDetailDeleteQuery5 expenseDetailDeleteQuery5;
	private ExpenseDetailDeleteQuery6 expenseDetailDeleteQuery6;
	private ExpenseDetailSelectQuery1 expenseDetailSelectQuery1;
	private ExpenseDetailSelectQuery2 expenseDetailSelectQuery2;
	private ExpenseDetailSelectQuery3 expenseDetailSelectQuery3;
	private ExpenseDetailSelectQuery4 expenseDetailSelectQuery4;
	private ExpenseDetailSelectQuery5 expenseDetailSelectQuery5;

	@Override
	protected void initDao() throws Exception {
		this.expenseDetailInsertQuery = new ExpenseDetailInsertQuery(getDataSource());
		this.expenseDetailUpdateQuery1 = new ExpenseDetailUpdateQuery1(getDataSource());
		this.expenseDetailUpdateQuery2 = new ExpenseDetailUpdateQuery2(getDataSource());
		this.expenseDetailDeleteQuery1 = new ExpenseDetailDeleteQuery1(getDataSource());
		this.expenseDetailDeleteQuery2 = new ExpenseDetailDeleteQuery2(getDataSource());
		this.expenseDetailDeleteQuery3 = new ExpenseDetailDeleteQuery3(getDataSource());
		this.expenseDetailDeleteQuery4 = new ExpenseDetailDeleteQuery4(getDataSource());
		this.expenseDetailDeleteQuery5 = new ExpenseDetailDeleteQuery5(getDataSource());
		this.expenseDetailDeleteQuery6 = new ExpenseDetailDeleteQuery6(getDataSource());
		this.expenseDetailSelectQuery1 = new ExpenseDetailSelectQuery1(getDataSource());
		this.expenseDetailSelectQuery2 = new ExpenseDetailSelectQuery2(getDataSource());
		this.expenseDetailSelectQuery3 = new ExpenseDetailSelectQuery3(getDataSource());
		this.expenseDetailSelectQuery4 = new ExpenseDetailSelectQuery4(getDataSource());
		this.expenseDetailSelectQuery5 = new ExpenseDetailSelectQuery5(getDataSource());
	}

	protected class ExpenseDetailInsertQuery extends SqlUpdate {
		protected ExpenseDetailInsertQuery(DataSource ds) {
			super(ds, " INSERT INTO ProjectTeachingRestFeeDetail                         "
					+ "	 	  (projectCode, year, month, chargeSsn, seq, amount,         "
					+ "       tmCheckYN, ctmCheckYN, approvalYN, billSendYN, payYN )     "
					+ " VALUES (?, ?, ?, ?, ?, ?,   'N', 'N', 'N', 'N', ?)               ");
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
					expenseDetail.getSsn(), expenseDetail.getSeq(), expenseDetail.getAmount().replace(",", ""), expenseDetail.getPayYn() });
		}
	}

	protected class ExpenseDetailUpdateQuery1 extends SqlUpdate {
		protected ExpenseDetailUpdateQuery1(DataSource ds) {
			super(ds, " UPDATE ProjectTeachingRestFeeDetail                                             "
					+ " SET   amount=?, tmCheckYN=?, ctmCheckYN=?, approvalYN=?, billSendYN=?, payYN=?	"
					+ " WHERE projectCode=? and year=? and month=? and chargeSsn=? and seq=?          	");
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
			declareParameter(new SqlParameter(Types.INTEGER));
			compile();
			setRequiredRowsAffected(1);
		}

		protected int update(ExpenseDetail expenseDetail) throws DataAccessException {
			return this.update(new Object[] { expenseDetail.getAmount(), expenseDetail.getTmCheckYn(), expenseDetail.getCtmCheckYn(),
					expenseDetail.getApprovalYn(), expenseDetail.getBillSendYn(), expenseDetail.getPayYn(), expenseDetail.getProjectCode(),
					expenseDetail.getYear(), expenseDetail.getMonth(), expenseDetail.getSsn(), expenseDetail.getSeq() });
		}
	}

	protected class ExpenseDetailUpdateQuery2 extends SqlUpdate {
		protected ExpenseDetailUpdateQuery2(DataSource ds) {
			super(ds, " UPDATE ProjectTeachingRestFeeDetail                             "
					+ "    SET amount=?													"
					+ "  WHERE projectCode=? and year=? and month=? and chargeSsn=?   	");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
			setRequiredRowsAffected(1);
		}

		protected int update(ExpenseDetail expenseDetail) throws DataAccessException {
			return this.update(new Object[] { expenseDetail.getAmount(), expenseDetail.getProjectCode(),
					expenseDetail.getYear(), expenseDetail.getMonth(), expenseDetail.getSsn() });
		}
	}	
	
	protected class ExpenseDetailDeleteQuery1 extends SqlUpdate {
		protected ExpenseDetailDeleteQuery1(DataSource ds) {
			super(ds, " DELETE FROM ProjectTeachingRestFeeDetail WHERE projectCode=? ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected int delete(String projectCode) throws DataAccessException {
			return this.update(new Object[] { projectCode });
		}
	}

	protected class ExpenseDetailDeleteQuery2 extends SqlUpdate {
		protected ExpenseDetailDeleteQuery2(DataSource ds) {
			super(ds, " DELETE FROM ProjectTeachingRestFeeDetail WHERE projectCode=? and year=? and month=?");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected int delete(String projectCode, String year, String month) throws DataAccessException {
			return this.update(new Object[] { projectCode, year, month });
		}
	}

	protected class ExpenseDetailDeleteQuery3 extends SqlUpdate {
		protected ExpenseDetailDeleteQuery3(DataSource ds) {
			super(ds, " DELETE FROM ProjectTeachingRestFeeDetail WHERE projectCode=? and year=? and month=? and chargeSsn=?");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected int delete(String projectCode, String year, String month, String ssn) throws DataAccessException {
			return this.update(new Object[] { projectCode, year, month, ssn });
		}
	}

	protected class ExpenseDetailDeleteQuery4 extends SqlUpdate {
		protected ExpenseDetailDeleteQuery4(DataSource ds) {
			super(ds, " DELETE FROM ProjectTeachingRestFeeDetail WHERE projectCode=? and year=? and month=? and chargeSsn=? and seq=?");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.INTEGER));
			compile();
		}

		protected int delete(String projectCode, String year, String month, String ssn, int seq) throws DataAccessException {
			return this.update(new Object[] { projectCode, year, month, ssn, seq });
		}
	}

	protected class ExpenseDetailDeleteQuery5 extends SqlUpdate {
		protected ExpenseDetailDeleteQuery5(DataSource ds) {
			super(ds, " DELETE FROM ProjectTeachingRestFeeTemp WHERE projectCode=? and year=? and month=? and ssn=?");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected int delete(String projectCode, String year, String month, String ssn) throws DataAccessException {
			return this.update(new Object[] { projectCode, year, month, ssn });
		}
	}
	
	protected class ExpenseDetailDeleteQuery6 extends SqlUpdate {
		protected ExpenseDetailDeleteQuery6(DataSource ds) {
			super(ds, " DELETE FROM ProjectMemberMMPlan WHERE projectCode=? and year=? and month=? and ssn=?");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected int delete(String projectCode, String year, String month, String ssn) throws DataAccessException {
			return this.update(new Object[] { projectCode, year, month, ssn });
		}
	}
	
	protected class ExpenseDetailSelectQuery1 extends MappingSqlQuery {
		protected ExpenseDetailSelectQuery1(DataSource ds) {
			super(ds, "	SELECT 	p.*, e.name FROM ProjectTeachingRestFeeDetail p, expertPool e  where p.chargeSsn = e.ssn and p.projectCode=? ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected ExpenseDetailSelectQuery1(DataSource ds, String query) {
			super(ds, query);
		}

		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			ExpenseDetail expenseDetail = new ExpenseDetail();
			expenseDetail.setProjectCode(rs.getString("projectCode"));
			expenseDetail.setYear(rs.getString("year"));
			expenseDetail.setMonth(rs.getString("month"));
			expenseDetail.setSsn(rs.getString("chargeSsn"));
			expenseDetail.setName(rs.getString("name"));
			expenseDetail.setSeq(rs.getInt("seq"));
			expenseDetail.setAmount(rs.getString("amount"));
			expenseDetail.setTmCheckYn(rs.getString("tmCheckYn"));
			expenseDetail.setCtmCheckYn(rs.getString("ctmCheckYn"));
			expenseDetail.setApprovalYn(rs.getString("approvalYn"));
			expenseDetail.setBillSendYn(rs.getString("billSendYn"));
			expenseDetail.setPayYn(rs.getString("payYn"));
			return expenseDetail;
		}
	}

	protected class ExpenseDetailSelectQuery2 extends ExpenseDetailSelectQuery1 {
		protected ExpenseDetailSelectQuery2(DataSource ds) {
			super(ds, "	SELECT 	p.*, e.name  FROM ProjectTeachingRestFeeDetail p, expertPool e "
					+ " WHERE p.payYN<>'N' and p.chargeSsn = e.ssn and p.projectCode=? and p.year=? and p.month=?");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}
	}

	protected class ExpenseDetailSelectQuery3 extends ExpenseDetailSelectQuery1 {
		protected ExpenseDetailSelectQuery3(DataSource ds) {
			super(ds, "	SELECT 	*, e.name  FROM ProjectTeachingRestFeeDetail p, expertPool e  "
					+ " where p.chargeSsn = e.ssn and p.projectCode=? and p.year=? and p.month=? and p.chargeSsn=?");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}
	}

	protected class ExpenseDetailSelectQuery4 extends ExpenseDetailSelectQuery1 {
		protected ExpenseDetailSelectQuery4(DataSource ds) {
			super(ds, " select	a2.projectCode, year, month, a2.ssn as chargeSsn, a2.name, isNull(seq, '-1') as seq,   "
					+ "         amount, tmCheckYn, ctmCheckYn, approvalYn, billSendYn, payYn  ,role    "
					+ " from   (                                                                       "
					+ "         SELECT 	 p.*, e.name                                                   "
					+ "           FROM ProjectTeachingRestFeeDetail p, expertPool e                       "
					+ "          where p.chargeSsn = e.ssn                                             "
					+ "            and p.year= ?  and p.month=?                                        "
					+ " ) a1 right outer join                                                          "
					+ "(                                                                               "
					+ "SELECT	A.projectCode, B.name, B.ssn, C.data_1 AS role , B.jobClass            "
					+ "	FROM 	ProjectMember A, ExpertPool B, CmTableData C             	           "
					+ "	 WHERE 1=1                                                                     "
					+ " AND		B.jobClass not in ('A','C','J')                       			   	   "
					+ "	AND 	A.ssn = B.ssn                                            	           "
					+ "	AND 	C.table_name = 'position_kind'                           	           "
					+ "	AND 	A.role = C.key_1                                         	           "
					+ "	AND 	( A.role = 'MB' OR A.role = 'PL' )                                     "
					+ ") a2                                                                            "			
					+ "on a1.projectCode= a2.projectCode                                               "
					+ "and a1.chargeSsn = a2.ssn                                                       "
					+ "where a2.projectCode = ?                                                        "
					+ "ORDER BY	role  ASC, ssn ASC                                                    ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}
	}
	
	protected class ExpenseDetailSelectQuery5 extends ExpenseDetailSelectQuery1 {
		protected ExpenseDetailSelectQuery5(DataSource ds) {
			super(ds, " select	a2.projectCode, year, month, a2.ssn as chargeSsn, a2.name, isNull(seq, '-1') as seq,   "
					+ "         amount, tmCheckYn, ctmCheckYn, approvalYn, billSendYn, payYn  ,role    "
					+ " from   (                                                                       "
					+ "         SELECT 	 p.*, e.name                                                   "
					+ "           FROM ProjectTeachingRestFeeDetail p, expertPool e                       "
					+ "          where p.chargeSsn = e.ssn                                             "
					+ "            and p.year= ?  and p.month=?                                        "
					+ " ) a1 right outer join                                                          "
					+ "(                                                                               "
					+ "SELECT	A.projectCode, B.name, B.ssn, C.data_1 AS role , B.jobClass            "
					+ "	FROM 	ProjectMember A, ExpertPool B, CmTableData C             	           "
					+ "	 WHERE 1=1                                                                     "
					+ "	AND 	A.ssn = B.ssn                                            	           "
					+ "	AND 	C.table_name = 'position_kind'                           	           "
					+ "	AND 	A.role = C.key_1                                         	           "
					+ "	AND 	( A.role = 'MB' OR A.role = 'PL' )                                     "
					+ ") a2                                                                            "			
					+ "on a1.projectCode= a2.projectCode                                               "
					+ "and a1.chargeSsn = a2.ssn                                                       "
					+ "where a2.projectCode = ?                                                        "
					+ "ORDER BY	role  ASC, ssn ASC                                                    ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}
	}
	
	

	@Override
	public void delete(String projectCode) throws DataAccessException {
		this.expenseDetailDeleteQuery1.delete(projectCode);
	}

	@Override
	public void delete(String projectCode, String year, String month) throws DataAccessException {
		this.expenseDetailDeleteQuery2.delete(projectCode, year, month);
	}

	@Override
	public void delete(String projectCode, String year, String month, String ssn) throws DataAccessException {
		this.expenseDetailDeleteQuery3.delete(projectCode, year, month, ssn);
	}

	@Override
	public void delete(String projectCode, String year, String month, String ssn, int seq) throws DataAccessException {
		this.expenseDetailDeleteQuery4.delete(projectCode, year, month, ssn, seq);
	}
	
	@Override
	public void deleteRestSalary(String projectCode, String year, String month, String ssn) throws DataAccessException {
		this.expenseDetailDeleteQuery5.delete(projectCode, year, month, ssn);
	}
	
	@Override
	public void deleteMMPlan(String projectCode, String year, String month, String ssn) throws DataAccessException {
		this.expenseDetailDeleteQuery6.delete(projectCode, year, month, ssn);
	}

	@Override
	public void insert(ExpenseDetail expenseDetail) throws DataAccessException {
		this.expenseDetailInsertQuery.insert(expenseDetail);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ExpenseDetail> select(String projectCode, String year, String month) throws DataAccessException {
		List<ExpenseDetail> expenseDetailList = this.expenseDetailSelectQuery2.execute(new Object[] { projectCode, year, month });
		if (expenseDetailList.size() > 0)
			return expenseDetailList;
		else
			return this.expenseDetailSelectQuery4.execute(new Object[] { year, month, projectCode });
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ExpenseDetail> selectAllExpense(String projectCode, String year, String month) throws DataAccessException {
		List<ExpenseDetail> expenseDetailList = this.expenseDetailSelectQuery2.execute(new Object[] { projectCode, year, month });
		if (expenseDetailList.size() > 0)
			return expenseDetailList;
		else
			return this.expenseDetailSelectQuery5.execute(new Object[] { year, month, projectCode });
	}
	
	@Override
	public boolean isExpenseValid(ExpenseDetail expenseDetail) throws DataAccessException {
		return getJdbcTemplate().queryForInt(
				"SELECT count(chargeSsn) FROM ProjectTeachingRestFeeDetail WHERE projectCode = '" + expenseDetail.getProjectCode() 
					+ "' AND year = '" + expenseDetail.getYear() 
					+ "' AND month = '" + expenseDetail.getMonth() 
					+ "' AND chargeSsn = '" + expenseDetail.getSsn() + "'") > 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ExpenseDetail> select(String projectCode, String year, String month, String ssn) throws DataAccessException {
		return this.expenseDetailSelectQuery3.execute(new Object[] { projectCode, year, month, ssn });
	}
	
	@Override
	public void update(ExpenseDetail expenseDetail) throws DataAccessException {
		this.expenseDetailUpdateQuery1.update(expenseDetail);
	}
	
	@Override
	public void updateAmount(ExpenseDetail expenseDetail) throws DataAccessException {
		this.expenseDetailUpdateQuery2.update(expenseDetail);		
	}

	@Override
	public int getExpenseSeqMaxNum(String projectCode, String year, String month, String ssn) throws DataAccessException {
		String query = "SELECT (isNull(max(seq), 0)+1) as maxVal FROM ProjectTeachingRestFeeDetail where projectCode=? and year=? and month=? and chargeSsn=? ";
		return getJdbcTemplate().queryForInt(query, new Object[] { projectCode, year, month, ssn });
	}
}
