package kr.co.kmac.pms.project.expense.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import javax.sql.DataSource;

import kr.co.kmac.pms.project.expense.dao.ExpenseDetailDao;
import kr.co.kmac.pms.project.expense.data.ExpenseDetail;
import kr.co.kmac.pms.project.expense.data.ExpenseDetailAdded;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.jdbc.object.SqlUpdate;

public class ExpenseDetailDaoImpl extends JdbcDaoSupport implements ExpenseDetailDao {
	private ExpenseDetailInsertQuery expenseDetailInsertQuery;
	private ExpenseDetailUpdateQuery expenseDetailUpdateQuery;
	private ExpenseDetailDeleteQuery1 expenseDetailDeleteQuery1;
	private ExpenseDetailDeleteQuery2 expenseDetailDeleteQuery2;
	private ExpenseDetailDeleteQuery3 expenseDetailDeleteQuery3;
	private ExpenseDetailDeleteQuery4 expenseDetailDeleteQuery4;
	private ExpenseDetailDeleteQuery5 expenseDetailDeleteQuery5;
	private ExpenseDetailSelectQuery1 expenseDetailSelectQuery1;
	private ExpenseDetailSelectQuery2 expenseDetailSelectQuery2;
	private ExpenseDetailSelectQuery3 expenseDetailSelectQuery3;
	private ExpenseDetailSelectQuery4 expenseDetailSelectQuery4;
	private ExpenseDetailSelectQuery5 expenseDetailSelectQuery5;
	private ExpenseDetailSelectQuery6 expenseDetailSelectQuery6;
	private ExpenseDetailSelectQuery7 expenseDetailSelectQuery7;

	@Override
	protected void initDao() throws Exception {
		this.expenseDetailInsertQuery = new ExpenseDetailInsertQuery(getDataSource());
		this.expenseDetailUpdateQuery = new ExpenseDetailUpdateQuery(getDataSource());
		this.expenseDetailDeleteQuery1 = new ExpenseDetailDeleteQuery1(getDataSource());
		this.expenseDetailDeleteQuery2 = new ExpenseDetailDeleteQuery2(getDataSource());
		this.expenseDetailDeleteQuery3 = new ExpenseDetailDeleteQuery3(getDataSource());
		this.expenseDetailDeleteQuery4 = new ExpenseDetailDeleteQuery4(getDataSource());
		this.expenseDetailDeleteQuery5 = new ExpenseDetailDeleteQuery5(getDataSource());
		this.expenseDetailSelectQuery1 = new ExpenseDetailSelectQuery1(getDataSource());
		this.expenseDetailSelectQuery2 = new ExpenseDetailSelectQuery2(getDataSource());
		this.expenseDetailSelectQuery3 = new ExpenseDetailSelectQuery3(getDataSource());
		this.expenseDetailSelectQuery4 = new ExpenseDetailSelectQuery4(getDataSource());
		this.expenseDetailSelectQuery5 = new ExpenseDetailSelectQuery5(getDataSource());
		this.expenseDetailSelectQuery6 = new ExpenseDetailSelectQuery6(getDataSource());
		this.expenseDetailSelectQuery7 = new ExpenseDetailSelectQuery7(getDataSource());
	}

	protected class ExpenseDetailInsertQuery extends SqlUpdate {
		protected ExpenseDetailInsertQuery(DataSource ds) {
			super(ds, " INSERT INTO ProjectTeachingFeeMDetail (                             "
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

	protected class ExpenseDetailUpdateQuery extends SqlUpdate {
		protected ExpenseDetailUpdateQuery(DataSource ds) {
			super(ds, " UPDATE ProjectTeachingFeeMDetail                                               	"
					+ " set   amount=?, tmCheckYN=?, ctmCheckYN=?, approvalYN=?, billSendYN=?, payYN=?	"
					+ " where projectCode=? and year=? and month=? and chargeSsn=? and seq=?           	");
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
	
	protected class ExpenseDetailDeleteQuery1 extends SqlUpdate {
		protected ExpenseDetailDeleteQuery1(DataSource ds) {
			super(ds, " DELETE FROM ProjectTeachingFeeMDetail WHERE projectCode=? ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected int delete(String projectCode) throws DataAccessException {
			return this.update(new Object[] { projectCode });
		}
	}

	protected class ExpenseDetailDeleteQuery2 extends SqlUpdate {
		protected ExpenseDetailDeleteQuery2(DataSource ds) {
			super(ds, " DELETE FROM ProjectTeachingFeeMDetail WHERE projectCode=? and year=? and month=?");
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
			super(ds, " DELETE FROM ProjectTeachingFeeMDetail WHERE projectCode=? and year=? and month=? and chargeSsn=?");
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
			super(ds, " DELETE FROM ProjectTeachingFeeMDetail WHERE projectCode=? and year=? and month=? and chargeSsn=? and seq=?");
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
			super(ds, " DELETE FROM ProjectTeachingRestFeeDetail WHERE projectCode=? and year=? and month=? and chargeSsn=? and seq=?");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.INTEGER));
			compile();
		}

		protected int delete2(String projectCode, String year, String month, String ssn, int seq) throws DataAccessException {
			return this.update(new Object[] { projectCode, year, month, ssn, seq });
		}
	}

	protected class ExpenseDetailSelectQuery1 extends MappingSqlQuery {
		protected ExpenseDetailSelectQuery1(DataSource ds) {
			super(ds, "	SELECT 	p.*, e.name FROM ProjectTeachingFeeMDetail p, expertPool e  where p.chargeSsn = e.ssn and p.projectCode=? ");
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
			expenseDetail.setJob(rs.getString("job"));
			expenseDetail.setCnt(rs.getString("cnt"));
			expenseDetail.setMoney(rs.getString("money"));
			expenseDetail.setRealmoney(rs.getString("realmoney"));
			expenseDetail.setProjectTypeCode(rs.getString("projecttypecode"));
			expenseDetail.setJobClass(rs.getString("jobclass"));
			return expenseDetail;
		}
	}

	protected class ExpenseDetailSelectQuery2 extends ExpenseDetailSelectQuery1 {
		protected ExpenseDetailSelectQuery2(DataSource ds) {
			super(ds, "	SELECT 	p.*, e.name  FROM ProjectTeachingFeeMDetail p, expertPool e "
					//+ " where p.chargeSsn = e.ssn and p.projectCode=? and p.year=? and p.month=?");
					// Job Date: 2010-02-02 Author: yhyim	Description: 키인하는 강사료 문제로 인해 아래와 같은 미봉책 처리 함
					+ " where p.payYN<>'N' and p.chargeSsn = e.ssn and p.projectCode=? and p.year=? and p.month=?");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}
	}

	protected class ExpenseDetailSelectQuery3 extends ExpenseDetailSelectQuery1 {
		protected ExpenseDetailSelectQuery3(DataSource ds) {
			super(ds, "	SELECT 	*, e.name  FROM ProjectTeachingFeeMDetail p, expertPool e  "
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
			super(ds, " SELECT	a2.projectCode, a1.year, a1.month, a2.ssn as chargeSsn, a2.name, isNull(a1.seq, '-1') as seq,  "  
					   + "      a1.amount, a1.tmCheckYn, a1.ctmCheckYn, a1.approvalYn, a1.billSendYn, a1.payYn  ,a2.role,  "
					   + "	  (CASE WHEN a2.jobclass = 'A' THEN '상근(1)' WHEN a2.jobclass = 'B' THEN '상근(2)' WHEN a2.jobclass = 'H' THEN 'AA' WHEN a2.jobclass = 'N' THEN 'RA'    "
					   + " WHEN a2.jobclass = 'J' THEN '상임' WHEN a2.jobclass = 'C' THEN '엑스퍼트' WHEN a2.jobclass = 'D' THEN '산업계강사'  "
					   + " WHEN a2.jobclass = 'E' THEN '대학교수' WHEN a2.jobclass = 'I' THEN 'KMAC Alumni' WHEN a2.jobclass in ('F','L','M') THEN '(대기)인력' ELSE '기타' END ) as job, a2.cost as cnt,"
					   + " isnull(money, 0) as money, isnull(a2.cost - money, 0) as realmoney, a2.projecttypecode, a2.jobclass"
					   + " from   (          "                                                              
					   + "       SELECT p.*, e.name   "                                                 
					   + "        FROM ProjectTeachingFeeMDetail p, expertPool e      "                  
					   + "       where p.chargeSsn = e.ssn                       "                       
					   + "          and p.year= ?  and p.month= ?           "                            
					   + " ) a1 right outer join                                                "           
					   + " (                                                                    "            
					   + " SELECT	A.projectCode, B.name, B.ssn, C.data_1 AS role , B.jobClass, A.cost, D.projecttypecode      "       
					   + " 	FROM 	ProjectMember A, ExpertPool B, CmTableData C , Project D            	     "       
					   + " 	 WHERE 1=1                                                        "              
					   + "	AND 	A.ssn = B.ssn                                            "	      
					   + "  AND		A.projectCode = D.projectCode    "
					   + "	AND 	C.table_name = 'position_kind'                          " 	            
					   + " 	AND 	A.role = C.key_1                                        " 	            
					   + " 	AND 	( A.role = 'MB' OR A.role = 'PL' OR A.role = 'QM' )                     "                 
					   + " ) a2                                                          "                   			
					   + " on a1.projectCode= a2.projectCode                       "                         
					   + " and a1.chargeSsn = a2.ssn   "
					   + " left join"
					   + " ("
					   + "	SELECT projectCode, chargessn, sum(convert(float, amount)) "
					   + "	as money fROM ProjectTeachingFeeMDetail where projectCode = ? "
					   + "	and  billSendYn='Y'  group by projectCode, chargessn"
					   + " )a3"
					   + " on a2.ssn = a3.chargeSsn  "
					   + " where a2.projectCode = ?                                 "                       
					   + " ORDER BY a2.jobclass asc, a2.name asc, role  ASC, ssn ASC   ");
			declareParameter(new SqlParameter(Types.VARCHAR));
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
					+ "           FROM ProjectTeachingFeeMDetail p, expertPool e                       "
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
	
	protected class ExpenseDetailSelectQuery7 extends ExpenseDetailSelectQuery1 {
		protected ExpenseDetailSelectQuery7(DataSource ds) {
			super(ds, " select	a2.projectCode, year, month, a2.ssn as chargeSsn, a2.name, isNull(seq, '-1') as seq,   "
					+ "         amount, tmCheckYn, ctmCheckYn, approvalYn, billSendYn, payYn  ,role, "
					+ " '' as job, '' as cnt, '' as money, '' as realmoney, '' as projecttypecode, '' as jobclass    "
					+ " from   (                                                                       "
					+ "         SELECT 	 p.*, e.name                                                   "
					+ "           FROM ProjectTeachingFeeMDetail p, expertPool e                       "
					+ "          where p.chargeSsn = e.ssn                                             "
					+ "            and p.year= ?  and p.month=?                                        "
					+ " ) a1 right outer join                                                          "
					+ "(                                                                               "
					+ "SELECT	A.projectCode, B.name, B.ssn, C.data_1 AS role , B.jobClass            "
					+ "	FROM 	ProjectMember A, ExpertPool B, CmTableData C             	           "
					+ "	 WHERE 1=1                                                                     "
					+ " AND		B.jobClass not in ('A','C','J','F','B')                       			   "
					+ "	AND 	A.ssn = B.ssn                                            	           "
					+ "	AND 	C.table_name = 'position_kind'                           	           "
					+ "	AND 	A.role = C.key_1                                         	           "
					+ "	AND 	( A.role = 'MB' OR A.role = 'PL' )                                     "
					+ ") a2                                                                            "			
					+ "on a1.projectCode= a2.projectCode                                               "
					+ "and a1.chargeSsn = a2.ssn                                                       "
					+ "where a2.projectCode = ?                                                        "
					+ "ORDER BY	name ASC, role  ASC, ssn ASC                                                    ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}
	}
	
	protected class ExpenseDetailSelectQuery6 extends MappingSqlQuery {
		protected ExpenseDetailSelectQuery6(DataSource ds) {
			  super(ds, " SELECT	MST.jobClassName, MST.projectCode, MST.ssn, MST.name,									"
					  + "			D.year, D.month, isNull(D.seq, '1') as seq,   											"
					  + "			isnull(replace(convert( MONEY, round(D.amount,0)), '.00', '' ), 0) AS amount,						"
					  + "			isnull(D.mmRate, 0) as mmRate, D.ctmCheckYN, D.tmCheckYn, D.approvalYN, D.billSendYn, D.payYn,				"
					  + "			ISNULL(AMOUNT.TOTALMM,0) totalMM, ISNULL(replace(convert( MONEY, round(MST.cost,0)), '.00', '' ),0) totalBudget, 				"
					  + "			ISNULL(AMOUNT.WORKINGMM,0) monthlyMM, 													"
					  + "			replace(convert( MONEY, ISNULL(AMOUNT.MONTHLYAMT,0)), '.00', '' )  monthlyBudget,		"
					  + "			replace(convert( MONEY, ISNULL(AMOUNT.TOTALAMT,0)*0.2), '.00', '' ) as restBudget,		"
					  + "			replace(convert( MONEY, round(ISNULL(REST.restAmount,0),0)), '.00', '' ) as restAmount,	"
					  + "			ISNULL(REST_STATE.ctmCheckYN,'') as restCtmCheckYN,										"
					  + "			ISNULL(REST_STATE.approvalYN,'') as restApprovalYN,										"	
					  + "			PM.resRate																				"
					  + "	FROM (																							"
					  + "		SELECT	A.projectCode, B.name, B.ssn, C.data_1 AS jobClassName, C.data_2, A.cost			"
					  + "		FROM 	ProjectMember A, ExpertPool B, CmTableData C             	           				"
					  + "		WHERE	1=1																					"
					  + "		AND		A.trainingYN = 'Y'																	"
/*					  + "		AND		B.jobClass in ('A','C','J','B')														"*/
					  + "		AND 	A.ssn = B.ssn                                            	           				"
					  + "		AND 	C.table_name = 'EMP_WORKING_TYPE'                           	           			"
					  + "		AND 	B.jobClass = C.key_1                                         	           			"
					  + "		AND 	( A.role = 'MB' OR A.role = 'PL' OR A.role = 'QM' )   												"
					  + "	) MST																							"
					  + "	LEFT OUTER JOIN																					"
					  + "	(																								"
					  + "		SELECT	MST.PROJID, MST.EMPNO, MM.TOTALMM, MM.TOTALAMT,										"
					  + "				ISNULL(AMT.WORKING,0) WORKINGMM, ISNULL(MM.TOTALAMT,0) MONTHLYAMT					"
					  + "		FROM (																						"
					  + "				SELECT MST.ENTNO, PROJID, EMP.EMPNO													"
					  + "				FROM DWPM.DBO.DW_PROJECTMST MST, DWPM.DBO.DW_PROJECTWORKMAN EMP						"
					  + "				WHERE MST.ENTNO = EMP.ENTNO															"
					  + "				AND MST.ENTNO IN																	"
					  + "					(SELECT TOP 1 ENTNO FROM DWPM.DBO.DW_PROJECTMST 								"
					  + "					 WHERE ACPTTYPE = '1' AND PROJID=? 												"
					  + "					 ORDER BY INPUTDATE DESC)														"
					  + "				GROUP BY MST.ENTNO, MST.PROJID, EMP.EMPNO											"
					  + "		) MST INNER JOIN																			"
					  + "		(																							"
					  + "			SELECT	ENTNO, EMPNO, RATE,																"
					  + "					SUM(WORKMD) AS TOTALMM,															"
					  + "					SUM(AMT) AS TOTALAMT  															"
					  + "			FROM  																					"
					  + "			(  																						"
					  + "				SELECT	A.ENTNO,A.EMPNO,   															"
					  + "				A.RATE,A.WORKMD,A.STDAMT AS AMT  													"
					  + "				FROM DWPM.DBO.DW_PROJECTWORKMAN_MM A												"
					  + "				WHERE LEFT(YYYYMM, 4) = ? AND RIGHT(YYYYMM, 2) = ?									"
					  + "			)B  																					"
					  + "			GROUP BY ENTNO, EMPNO, RATE																"
					  + "		) MM																						"
					  + "		ON MST.ENTNO = MM.ENTNO	AND MST.EMPNO = MM.EMPNO											"
					  + "		LEFT OUTER JOIN																				"
					  + "		(																							"
					  + "			SELECT	projectCode, ssn, RATE,															"
					  + "					SUM(WORKMD) AS WORKING															"
					  + "			FROM  																					"
					  + "			(  																						"
					  + "				SELECT	p.projectCode, p.ssn, m.resRate as RATE, p.mmValue as WORKMD, m.cost,		" 
					  + "						convert(float,p.mmValue) * 0.8 as AMT										"
					  + "				FROM ProjectMemberMMPlan p, projectMember m, project pjt							"
					  + "				WHERE p.projectCode = m.projectCode													"
					  + "				AND p.ssn = m.ssn																	"
					  + "				AND p.projectCode = pjt.projectCode													"
					  + "				AND m.projectCode = pjt.projectCode													"
					  + "				AND m.role in ('PL','MB','QM')															"
					  + "				AND m.trainingYN = 'Y'																"
					  + "				AND p.year = ?																		"
					  + "				AND p.month = ?																		"
					  + "				AND pjt.projectTypeCode = 'MM'														"
					  + "			)B  																					"
					  + "			GROUP BY projectCode, ssn, RATE															"
					  + "		) AMT																						"
					  + "		ON MST.PROJID = AMT.projectCode AND MST.EMPNO = AMT.ssn										"
					  + "	) AMOUNT																						"
					  + "	ON MST.projectCode = AMOUNT.PROJID																"
					  + "	AND MST.ssn = AMOUNT.EMPNO																		"
					  + "	LEFT OUTER JOIN																					"
					  + "	(																								"
					  + "		SELECT *, (select mmvalue from ProjectMemberMMPlan  										"
					  + "					where checkYN = 'Y' and projectCode = m.projectCode and ssn = m.chargeSsn		"
					  + "					and year = m.year and month = m.month) mmRate									"
					  + "		FROM ProjectTeachingFeeMDetail	m															"
					  + "		WHERE year = ? AND month = ?																"
					  + "	) D																								"
					  + "	ON MST.projectCode = D.projectCode																"
					  + "	AND MST.ssn = D.chargeSsn																		"
					  + "	LEFT OUTER JOIN																					"
					  + "	(																								"
					  + "		SELECT projectCode, chargessn as ssn, sum(convert(float,amount)) restAmount								"
					  + "		FROM ProjectTeachingRestFeeDetail"
					  + "		WHERE year = ? AND month = ?																	"
					  + "		GROUP BY projectCode, chargessn																	"
					  + "	) REST																							"
					  + "	ON REST.projectCode = MST.projectCode and REST.ssn = MST.ssn									"
					  + "	LEFT OUTER JOIN (																				"
					  + "		SELECT projectCode, chargeSsn, ctmCheckYN, approvalYN										"
					  + "		FROM ProjectTeachingRestFeeDetail															"
					  + "		WHERE year = ? AND month = ?																"
					  + "	) REST_STATE																					"
					  + "	ON REST_STATE.projectCode = MST.projectCode AND REST_STATE.chargeSsn = MST.ssn					"
					  + "	LEFT OUTER JOIN ( 																				"
					  + " 	SELECT projectcode, ssn, resRate																"
					  +	"	FROM projectmember 																				"
					  + "	WHERE role in ('PL','MB','QM')	 																"
					  + "	) PM 																							"
					  + "	ON MST.projectcode = PM.projectcode and MST.ssn = PM.ssn										"
					  + "	WHERE MST.projectCode = ?																		"
					  + "	ORDER BY MST.data_2 ASC, MST.name ASC, MST.ssn DESC												");
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
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}
		
		protected ExpenseDetailSelectQuery6(DataSource ds, String query) {
			super(ds, query);
		}
		
		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			ExpenseDetailAdded expenseDetailAdded = new ExpenseDetailAdded();
			expenseDetailAdded.setJobClassName(rs.getString("jobClassName"));
			expenseDetailAdded.setProjectCode(rs.getString("projectCode"));
			expenseDetailAdded.setSsn(rs.getString("ssn"));
			expenseDetailAdded.setName(rs.getString("name"));
			expenseDetailAdded.setYear(rs.getString("year"));
			expenseDetailAdded.setMonth(rs.getString("month"));
			expenseDetailAdded.setSeq(rs.getInt("seq"));
			expenseDetailAdded.setAmount(rs.getString("amount"));
			expenseDetailAdded.setMmRate(rs.getString("mmRate"));
			expenseDetailAdded.setTmCheckYn(rs.getString("tmCheckYn"));
			expenseDetailAdded.setCtmCheckYn(rs.getString("ctmCheckYn"));
			expenseDetailAdded.setApprovalYn(rs.getString("approvalYn"));
			expenseDetailAdded.setBillSendYn(rs.getString("billSendYn"));
			expenseDetailAdded.setPayYn(rs.getString("payYn"));
			expenseDetailAdded.setTotalMM(rs.getString("totalMM"));
			expenseDetailAdded.setTotalBudget(rs.getString("totalBudget"));
			expenseDetailAdded.setMonthlyMM(rs.getString("monthlyMM"));
			expenseDetailAdded.setMonthlyBudget(rs.getString("monthlyBudget"));
			expenseDetailAdded.setRestBudget(rs.getString("restBudget"));
			expenseDetailAdded.setRestAmount(rs.getString("restAmount"));
			expenseDetailAdded.setRestCtmCheckYN(rs.getString("restCtmCheckYN"));
			expenseDetailAdded.setRestApprovalYN(rs.getString("restApprovalYN"));
			expenseDetailAdded.setResRate(rs.getString("resRate"));
			return expenseDetailAdded;
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
	public void delete2(String projectCode, String year, String month, String ssn, int seq) throws DataAccessException {
		this.expenseDetailDeleteQuery5.delete2(projectCode, year, month, ssn, seq);

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
			return this.expenseDetailSelectQuery4.execute(new Object[] { year, month, projectCode, projectCode });
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ExpenseDetail> select2(String projectCode, String year, String month) throws DataAccessException {
		List<ExpenseDetail> expenseDetailList = this.expenseDetailSelectQuery2.execute(new Object[] { projectCode, year, month });
		if (expenseDetailList.size() > 0)
			return expenseDetailList;
		else
			return this.expenseDetailSelectQuery7.execute(new Object[] {year, month, projectCode });
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ExpenseDetailAdded> selectPU(String projectCode, String year, String month) throws DataAccessException {
		return this.expenseDetailSelectQuery6.execute(new Object[] { projectCode, year, month, year, month, year, month, year, month, year, month, projectCode });
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

	@SuppressWarnings("unchecked")
	@Override
	public List<ExpenseDetail> select(String projectCode, String year, String month, String ssn) throws DataAccessException {
		return this.expenseDetailSelectQuery3.execute(new Object[] { projectCode, year, month, ssn });
	}

	@Override
	public void update(ExpenseDetail expenseDetail) throws DataAccessException {
		this.expenseDetailUpdateQuery.update(expenseDetail);
	}
	
	@Override
	public int getExpenseSeqMaxNum(String projectCode, String year, String month, String ssn) throws DataAccessException {
		String query = "SELECT (isNull(max(seq), 0)+1) as maxVal FROM ProjectTeachingFeeMDetail where projectCode=? and year=? and month=? and chargeSsn=? ";
		return getJdbcTemplate().queryForInt(query, new Object[] { projectCode, year, month, ssn });
	}
	
	@Override
	public int getExpenseSeqMaxNum2(String projectCode, String year, String month, String ssn) throws DataAccessException {
		String query = "SELECT (isNull(max(seq), 0)+1) as maxVal FROM ProjectTeachingRestFeeDetail where projectCode=? and year=? and month=? and chargeSsn=? ";
		return getJdbcTemplate().queryForInt(query, new Object[] { projectCode, year, month, ssn });
	}

}
