/*
 * $Id: ProjectExpenseDaoImpl.java,v 1.15 2019/04/01 15:37:36 cvs Exp $
 * created by    : CHO DAE HYON
 * creation-date : 2006. 4. 21.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.sanction.projectexpense.dao.impl;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import kr.co.kmac.pms.common.util.StringUtil;
import kr.co.kmac.pms.project.expense.data.ExpenseDetail;
import kr.co.kmac.pms.sanction.projectexpense.dao.ProjectExpenseRestDao;
import kr.co.kmac.pms.sanction.projectexpense.data.ExpenseRealTimeResultDetailForSanction;
import kr.co.kmac.pms.sanction.projectexpense.form.ProjectExpenseForm;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.object.SqlUpdate;

/**
 * TODO 클래스 설명
 * 
 * @author CHO DAE HYON
 * @version $Id: ProjectExpenseDaoImpl.java,v 1.15 2019/04/01 15:37:36 cvs Exp $
 */
public class ProjectExpenseRestDaoImpl extends JdbcDaoSupport implements ProjectExpenseRestDao {
	private ExpenseUpdateQuery11 expenseUpdateQuery11;// BU강사료 팀장 확인 및 기안
	private ExpenseUpdateQuery13 expenseUpdateQuery13;// BU강사료 반려
	private ExpenseUpdateQuery14 expenseUpdateQuery14;// BU강사료 삭제 190424
	private ExpenseInsertQuery31 expenseInsertQuery31;// 회계 강사료 전송(insert)
	private ExpenseUpdateQuery31 expenseUpdateQuery31;// 회계 강사료 전송(update)
	private ExpenseUpdateQuery33 expenseUpdateQuery33;// 강사료 : 회계전송여부 = Y

	// 그룹장 강사료 승인 후 디비에 실 데이터 입력
	private RealTimeExpenseInsertQuery realTimeExpenseInsertQuery;
	private RealTimeExpenseDetailInsertQuery realTimeExpenseDetailInsertQuery;
	private DataSource erpDataSource;// 회계 테이터 베이스 접근

	protected void initDao() throws Exception {
		this.expenseUpdateQuery11 = new ExpenseUpdateQuery11(getDataSource());
		this.expenseUpdateQuery13 = new ExpenseUpdateQuery13(getDataSource());
		this.expenseUpdateQuery14 = new ExpenseUpdateQuery14(getDataSource());
		this.expenseInsertQuery31 = new ExpenseInsertQuery31(getErpDataSource());
		this.expenseUpdateQuery31 = new ExpenseUpdateQuery31(getErpDataSource());
		this.expenseUpdateQuery33 = new ExpenseUpdateQuery33(getDataSource());

		this.realTimeExpenseInsertQuery = new RealTimeExpenseInsertQuery(getDataSource());
		this.realTimeExpenseDetailInsertQuery = new RealTimeExpenseDetailInsertQuery(getDataSource());
	}

	// 집계된 강사료의 순번 부여
	@SuppressWarnings("unchecked")
	@Override
	public List<ExpenseRealTimeResultDetailForSanction> addSeqNumDateInExpenseRestList(List<ExpenseRealTimeResultDetailForSanction> expenseList)
			throws DataAccessException {
		List<ExpenseRealTimeResultDetailForSanction> retVal = new ArrayList<ExpenseRealTimeResultDetailForSanction>();

		for (ExpenseRealTimeResultDetailForSanction data : expenseList) {
			if (data.getSalaryType().equals("KEYIN")) {

				List<Map<String, String>> list = getJdbcTemplate().queryForList(
						" Select seq from ProjectTeachingRestFeeDetail where projectCode=? and year=?  and month=? and chargeSsn=? ",
						new Object[] { data.getSalaryProjectCode(), data.getSalaryYear(), data.getSalaryMonth(),
								StringUtil.replace(data.getSalarySsn(), "-", "") });
				String seqStr = "";
				for (Map<String, String> map : list) {
					seqStr = seqStr + ";" + String.valueOf(map.get("seq"));
				}
				if (seqStr.startsWith(";")) {
					seqStr = seqStr.substring(1);
				}
				data.setSalarySeq(seqStr);
			}
			retVal.add(data);
		}
		return retVal;
	}


	@Override
	public void updateExpenseRestTMFinish(ProjectExpenseForm projectExpenseForm) throws DataAccessException {
		String projectCode[] = projectExpenseForm.getSalaryProjectCode();
		String year[] = projectExpenseForm.getSalaryYear();
		String month[] = projectExpenseForm.getSalaryMonth();
		String ssn[] = projectExpenseForm.getSalarySsn();
		String seq[] = projectExpenseForm.getSalarySeq();

		for (int i = 0; i < projectCode.length; i++) {// 전체 라인수
			if (projectExpenseForm.getSalaryType()[i].equals("KEYIN")) {
				String seq_[] = seq[i].split(";");
				for (int j = 0; j < seq_.length; j++) {// seq 개수 확인
					if (!seq_[j].equals("")) {// seq 빈값 제외
						getJdbcTemplate().update(
								" UPDATE ProjectTeachingRestFeeDetail SET isSanction = 'FINISHED' " 
							  + " WHERE projectCode = ? and year = ? and month = ? and chargeSsn = ? and seq =? ", 
								new Object[] {projectCode[i], year[i], month[i], ssn[i], Integer.parseInt(seq_[j]) });
					}
				}
			}
		}
		
	}


	// 팀장 강사료 확인 및 기안에 따른 집계된 강사료의 상태를 결재 진행중으로 변경
	@Override
	public void updateExpenseRestTM(ProjectExpenseForm projectExpenseForm) throws DataAccessException {

		String projectCode[] = projectExpenseForm.getSalaryProjectCode();
		String year[] = projectExpenseForm.getSalaryYear();
		String month[] = projectExpenseForm.getSalaryMonth();
		String ssn[] = projectExpenseForm.getSalarySsn();
		String seq[] = projectExpenseForm.getSalarySeq();

		for (int i = 0; i < projectCode.length; i++) {// 전체 라인수
			if (projectExpenseForm.getSalaryType()[i].equals("KEYIN")) {
				String seq_[] = seq[i].split(";");
				for (int j = 0; j < seq_.length; j++) {// seq 개수 확인
					if (!seq_[j].equals("")) {// seq 빈값 제외
						ExpenseDetail expense = new ExpenseDetail();
						expense.setProjectCode(projectCode[i]);
						expense.setYear(year[i]);
						expense.setMonth(month[i]);
						expense.setSsn(StringUtil.replace(ssn[i], "-", ""));
						expense.setSeq(Integer.parseInt(seq_[j]));
						this.expenseUpdateQuery11.update(expense);
					}
				}
			}
		}
	}

	// 팀장 강사료 확인 및 기안에 따른 집계된 강사료의 상태를 초기상태로 변경
	@Override
	public void updateExpenseRestTMForReject(ProjectExpenseForm projectExpenseForm) throws DataAccessException {
		String projectCode[] = projectExpenseForm.getSalaryProjectCode();
		String year[] = projectExpenseForm.getSalaryYear();
		String month[] = projectExpenseForm.getSalaryMonth();
		String ssn[] = projectExpenseForm.getSalarySsn();
		String seq[] = projectExpenseForm.getSalarySeq();

		for (int i = 0; i < projectCode.length; i++) {// 전체 라인수
			if (projectExpenseForm.getSalaryType()[i].equals("KEYIN")) {
				String seq_[] = seq[i].split(";");

				for (int j = 0; j < seq_.length; j++) {// seq 개수 확인
					if (!seq_[j].equals("")) {// seq 빈값 제외
						ExpenseDetail expense = new ExpenseDetail();
						expense.setProjectCode(projectCode[i]);
						expense.setYear(year[i]);
						expense.setMonth(month[i]);
						expense.setSsn(ssn[i]);
						expense.setSeq(Integer.parseInt(seq_[j]));
						this.expenseUpdateQuery13.update(expense);
					}
				}
			}
		}
	}
	

	// 팀장 강사료 확인 및 기안에 따른 집계된 강사료 결재 업무 삭제 시 초기화
	@Override
	public void updateExpenseRestTMForDelete(ProjectExpenseForm projectExpenseForm) throws DataAccessException {
		String projectCode[] = projectExpenseForm.getSalaryProjectCode();
		String year[] = projectExpenseForm.getSalaryYear();
		String month[] = projectExpenseForm.getSalaryMonth();
		String ssn[] = projectExpenseForm.getSalarySsn();
		String seq[] = projectExpenseForm.getSalarySeq();

		for (int i = 0; i < projectCode.length; i++) {// 전체 라인수
			if (projectExpenseForm.getSalaryType()[i].equals("KEYIN")) {
				String seq_[] = seq[i].split(";");

				for (int j = 0; j < seq_.length; j++) {// seq 개수 확인
					if (!seq_[j].equals("")) {// seq 빈값 제외
						ExpenseDetail expense = new ExpenseDetail();
						expense.setProjectCode(projectCode[i]);
						expense.setYear(year[i]);
						expense.setMonth(month[i]);
						expense.setSsn(ssn[i]);
						expense.setSeq(Integer.parseInt(seq_[j]));
						this.expenseUpdateQuery14.update(expense);
					}
				}
			}
		}
	}

	/*
	 * 회계 강사료 전송 및 강사료 회계 전송 업데이트
	 */
	public void insertBillAndUpdateBillSendC(ProjectExpenseForm projectExpenseForm) throws DataAccessException {

		String year[] = projectExpenseForm.getSalaryYear();
		String month[] = projectExpenseForm.getSalaryMonth();
		String projectCode[] = projectExpenseForm.getSalaryProjectCode();
		String ssn[] = projectExpenseForm.getSalarySsn();
		String jobClassErp[] = projectExpenseForm.getSalaryJobClassErp();
		String sumAmount[] = projectExpenseForm.getSalaryRealTimeSalaryEachProject();
		String erpSenderSsn = projectExpenseForm.getAssistant1Ssn();
		// String erpSenderSsn = "7905132768412";
		String type[] = projectExpenseForm.getSalaryType();
		String deptType[] = projectExpenseForm.getSalaryDeptType();

		for (int i = 0; i < projectCode.length; i++) {// 해당 년 월에 등록된 강사료가 있는지 확인
			if (type[i].equals("PREPORT")) {
				int count = getJdbcTemplate().queryForInt(" SELECT COUNT(*) AS count FROM projectTeachingFeeM WHERE projectCode=? AND year+month=?",
						new Object[] { projectCode[i], year[i] + month[i] });
				if (count == 0) {
					this.realTimeExpenseInsertQuery.insert(projectCode[i], year[i], month[i]);// 강사료 관리 등록
				}
			}
		}

		for (int i = 0; i < projectCode.length; i++) {// 순번 MAX
			if (type[i].equals("PREPORT")) {
				int seqMax = getJdbcTemplate().queryForInt(
						"	         SELECT CASE WHEN MAX(CONVERT(int, SEQ)) IS NULL THEN '1'                          "
								+ "              WHEN MAX(CONVERT(int, SEQ)) IS NOT NULL THEN MAX(SEQ) +1 END          "
								+ " FROM ProjectTeachingRestFeeDetail WHERE projectCode=? AND chargeSsn=? AND year=? AND month=?	",
						new Object[] { projectCode[i], StringUtil.replace(ssn[i], "-", ""), year[i], month[i] });

				if (!sumAmount[i].equals("")) {
					this.realTimeExpenseDetailInsertQuery.insert(projectCode[i], year[i], month[i], StringUtil.replace(ssn[i], "-", ""),
							String.valueOf(seqMax), sumAmount[i]);// 강사료 관리 상세 등록
				}
				if (!sumAmount[i].equals("0")) {// 소개가 0인것 제외

					ExpenseDetail expense = new ExpenseDetail();
					expense.setProjectCode(projectCode[i]);
					expense.setYear(year[i]);
					expense.setMonth(month[i]);
					expense.setSsn(ssn[i]);//
					expense.setUid((String) getJdbcTemplate().queryForObject(
							"select (substring(SecureDB.DBSEC.DECRYPT_AES(uid), 1, 6)+'-'+substring(SecureDB.DBSEC.DECRYPT_AES(uid), 7, 7)) uid from expertPool where ssn = ?", new Object[] { ssn[i] },
							String.class));
					expense.setDeptType(deptType[i]);
					expense.setJobClassErp(jobClassErp[i]);
					expense.setSeq(seqMax);
					expense.setErpSenderSsn((String) getJdbcTemplate().queryForObject("select SecureDB.DBSEC.DECRYPT_AES(uid) from expertPool where ssn = ?",
							new Object[] { erpSenderSsn }, String.class));

					/*if(jobClassErp[i].equals("001")){
						expense.setTea_amt(sumAmount[i]);
						expense.setTea_amt_seventy(String.valueOf(Integer.valueOf(sumAmount[i]) * 0.7));
						expense.setTea_amt_thirty(String.valueOf(Integer.valueOf(sumAmount[i]) * 0.3));
					}else */if(jobClassErp[i].equals("011")){
						expense.setTea_amt(sumAmount[i]);
						expense.setTea_amt_seventy(String.valueOf(Integer.valueOf(sumAmount[i]) * 0.7));
						expense.setTea_amt_thirty(String.valueOf(Integer.valueOf(sumAmount[i]) * 0.3));
					} else {
						expense.setTea_amt(sumAmount[i]);
						expense.setTea_amt_seventy("0");
						expense.setTea_amt_thirty("0");
					}
					try {
						this.expenseInsertQuery31.insert(expense);// 회계 입력
					} catch (Exception e) {
						e.printStackTrace();
						this.expenseUpdateQuery31.update(expense);// 회계 업데이트
					}
					this.expenseUpdateQuery33.update(expense);
				}
			}
		}
	}

	public void insertBillAndUpdateBillSend(ProjectExpenseForm projectExpenseForm) throws DataAccessException {

		String projectCode[] = projectExpenseForm.getSalaryProjectCode();
		String year[] = projectExpenseForm.getSalaryYear();
		String month[] = projectExpenseForm.getSalaryMonth();
		String ssn[] = projectExpenseForm.getSalarySsn();
		String jobClassErp[] = projectExpenseForm.getSalaryJobClassErp();
		String seq[] = projectExpenseForm.getSalarySeq();
		String sumAmount[] = projectExpenseForm.getSalaryRealTimeSalaryEachProject();
		String type[] = projectExpenseForm.getSalaryType();
		String erpSenderSsn = projectExpenseForm.getAssistant1Ssn();

		for (int i = 0; i < projectCode.length; i++) {// 전체 라인수
			if (type[i].equals("KEYIN") && !sumAmount[i].equals("0")) {// 소개가 0인것 제외
				String seq_[] = seq[i].split(";");

				for (int j = 0; j < seq_.length; j++) {// seq 개수 확인
					if (!seq_[j].equals("")) {// seq 빈값 제외
						ExpenseDetail expense = new ExpenseDetail();
						expense.setProjectCode(projectCode[i]);
						expense.setYear(year[i]);
						expense.setMonth(month[i]);
						expense.setSsn(ssn[i]);//
						expense.setUid((String) getJdbcTemplate().queryForObject(
								"select (substring(SecureDB.DBSEC.DECRYPT_AES(uid), 1, 6)+'-'+substring(SecureDB.DBSEC.DECRYPT_AES(uid), 7, 7)) uid from expertPool where ssn = ?", new Object[] { ssn[i] },
								String.class));
						expense.setJobClassErp(jobClassErp[i]);
						expense.setSeq(Integer.parseInt(seq_[j]));
						expense.setErpSenderSsn((String) getJdbcTemplate().queryForObject("select SecureDB.DBSEC.DECRYPT_AES(uid) from expertPool where ssn = ?",
								new Object[] { erpSenderSsn }, String.class));
						String payYN = (String) (getJdbcTemplate().queryForMap(
								"SELECT payYN FROM ProjectTeachingRestFeeDetail WHERE projectCode=? AND year=? AND month=? AND chargeSsn=? AND seq=?",
								new Object[] { projectCode[i], year[i], month[i], StringUtil.replace(ssn[i], "-", ""), seq_[j] }).get("payYN"));

						if (payYN == null || payYN.equals("N")) {
							String tea_amt = String.valueOf(getJdbcTemplate().queryForInt(
									"	SELECT convert(bigint, convert(money, amount)) AS amount FROM 	ProjectTeachingRestFeeDetail "
											+ "  WHERE  projectCode=? AND year=? AND month=? AND chargeSsn=? AND seq=?                          ",
									new Object[] { projectCode[i], year[i], month[i], StringUtil.replace(ssn[i], "-", ""), seq_[j] }));
							tea_amt = tea_amt.equals("") || tea_amt.equals(null) ? "0" : tea_amt;
							expense.setTea_amt(tea_amt);
							try {
								this.expenseInsertQuery31.insert(expense);// 회계 전송
							} catch (Exception e) {
								this.expenseUpdateQuery31.update(expense);// 회계 업데이트
							}
							this.expenseUpdateQuery33.update(expense);
						}

					}
				}
			}
		}
	}

	// /////////////////////////////////
	protected class ExpenseUpdateQuery11 extends SqlUpdate {
		protected ExpenseUpdateQuery11(DataSource ds) {
			super(ds, "	UPDATE 	ProjectTeachingRestFeeDetail  SET 	ctmCheckYN = 'Y', approvalYN = 'Y', isSanction = 'ING' "
					+ "	 WHERE 	projectCode=? AND year=? AND month=? AND chargeSsn=? AND seq=?     ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected int update(ExpenseDetail expense) throws DataAccessException {
			return this.update(new Object[] { expense.getProjectCode(), expense.getYear(), expense.getMonth(), expense.getSsn(), expense.getSeq() });
		}
	}

	protected class ExpenseUpdateQuery13 extends SqlUpdate {
		protected ExpenseUpdateQuery13(DataSource ds) {
			super(ds, "	UPDATE 	ProjectTeachingRestFeeDetail SET ctmCheckYN = 'Y', approvalYN = 'N'    "
					+ "	 WHERE 	projectCode=? AND year=? AND month=? AND chargeSsn=? AND seq=?     ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected int update(ExpenseDetail expense) throws DataAccessException {
			return this.update(new Object[] { expense.getProjectCode(), expense.getYear(), expense.getMonth(), expense.getSsn(), expense.getSeq() });
		}
	}
	

	protected class ExpenseUpdateQuery14 extends SqlUpdate {
		protected ExpenseUpdateQuery14(DataSource ds) {
			super(ds, "	UPDATE 	ProjectTeachingRestFeeDetail SET ctmCheckYN = 'N' , approvalYN = 'N', isSanction = null"
					+ "	 WHERE 	projectCode=? AND year=? AND month=? AND chargeSsn=? AND seq=?     ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected int update(ExpenseDetail expense) throws DataAccessException {
			return this.update(new Object[] { expense.getProjectCode(), expense.getYear(), expense.getMonth(), expense.getSsn(), expense.getSeq() });
		}
	}

	protected class ExpenseInsertQuery31 extends SqlUpdate {
		protected ExpenseInsertQuery31(DataSource ds) {
			super(ds, "	INSERT INTO A_TEASLIP_PMS (	COMP_CODE, " 
					+ "                             PROJID, YYYYMM, JOB_TYPE, JUMIN, "
					+ "                             SEQ, TEA_AMT, ADD_AMT, ETC_AMT, INSERT_SNO, BONBU_GU,"
					+ "                             APPR_DATE, WRT_DATE )"
					+ "	VALUES	( 'C',        ?, ?, ?, ?,      ?, ?, ?, ?, ?, ?,                   "
					+ "           TO_CHAR(SYSDATE, 'YYYYMMDD'), TO_CHAR(SYSDATE, 'YYYYMMDD'))");
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

		protected int insert(ExpenseDetail expense) throws DataAccessException {
			return this.update(new Object[] { expense.getProjectCode(), expense.getYear() + expense.getMonth(),
					expense.getJobClassErp(),
					expense.getUid(),// 실제 주민번호 전송

					expense.getSeq(), expense.getTea_amt(), expense.getTea_amt_seventy(), expense.getTea_amt_thirty(),
					expense.getErpSenderSsn().substring(0, 6) + "-" + expense.getErpSenderSsn().substring(6, 13), expense.getDeptType() });
		}
	}

	protected class ExpenseUpdateQuery31 extends SqlUpdate {
		protected ExpenseUpdateQuery31(DataSource ds) {
			super(ds, "	UPDATE 	A_TEASLIP_PMS SET TEA_AMT=?, ADD_AMT=?, ETC_AMT=?  		 "
					+ "  WHERE PROJID=? AND YYYYMM=? AND JUMIN=? AND SEQ=?               ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));

			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected int update(ExpenseDetail expense) throws DataAccessException {
			return this.update(new Object[] { expense.getTea_amt(), expense.getTea_amt_seventy() , expense.getTea_amt_thirty(),
					expense.getProjectCode(), expense.getYear() + expense.getMonth(),
					expense.getUid(),// 실제 주민번호 전송
					expense.getSeq() });
		}
	}


	protected class ExpenseUpdateQuery33 extends SqlUpdate {
		protected ExpenseUpdateQuery33(DataSource ds) {
			super(ds, "	UPDATE 	ProjectTeachingRestFeeDetail SET billSendYN = 'Y' WHERE projectCode=? AND year=? AND month=? AND chargeSsn=? AND seq=? ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected int update(ExpenseDetail expense) throws DataAccessException {
			return this.update(new Object[] { expense.getProjectCode(), expense.getYear(), expense.getMonth(), expense.getSsn(), expense.getSeq() });
		}
	}

	/**
	 * 강사료 실적 등록
	 */
	protected class RealTimeExpenseInsertQuery extends SqlUpdate {
		protected RealTimeExpenseInsertQuery(DataSource ds) {
			super(ds, "	INSERT	INTO projectTeachingFeeM ( projectCode, year, month ) VALUES ( ?, ?, ? )");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected int insert(String projectCode, String year, String month) throws DataAccessException {
			return update(new Object[] { projectCode, year, month });
		}
	}

	/**
	 * 강사료 실적 상세 등록
	 */
	protected class RealTimeExpenseDetailInsertQuery extends SqlUpdate {
		protected RealTimeExpenseDetailInsertQuery(DataSource ds) {
			super(ds, "	INSERT INTO ProjectTeachingRestFeeDetail ( projectCode, year, month, chargeSsn, seq, amount, "
					+ "		  tmCheckYN, ctmCheckYN, approvalYN, billSendYN, payYN )                              "
					+ "	VALUES ( ?, ?, ?, ?, ?, ?,     'N', 'Y', 'Y', 'N', 'N')");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected int insert(String projectCode, String year, String month, String ssn, String seqMax, String amount) throws DataAccessException {
			return update(new Object[] { projectCode, year, month, ssn, seqMax, amount });
		}
	}

	public DataSource getErpDataSource() {
		return this.erpDataSource;
	}

	public void setErpDataSource(DataSource erpDataSource) {
		this.erpDataSource = erpDataSource;
	}
}
