/*
 * $Id: ProjectExpenseDaoImpl.java,v 1.15 2019/04/01 15:37:36 cvs Exp $
 * created by    : CHO DAE HYON
 * creation-date : 2006. 4. 21.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.sanction.projectexpense.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import kr.co.kmac.pms.common.util.StringUtil;
import kr.co.kmac.pms.project.expense.data.ExpenseDetail;
import kr.co.kmac.pms.sanction.projectexpense.dao.ProjectExpenseDao;
import kr.co.kmac.pms.sanction.projectexpense.data.ExpenseRealTimeResultDetailForSanction;
import kr.co.kmac.pms.sanction.projectexpense.data.PreportState;
import kr.co.kmac.pms.sanction.projectexpense.form.ProjectExpenseForm;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.object.SqlUpdate;

/**
 * TODO Ŭ���� ����
 * 
 * @author CHO DAE HYON
 * @version $Id: ProjectExpenseDaoImpl.java,v 1.15 2019/04/01 15:37:36 cvs Exp $
 */
public class ProjectExpenseDaoImpl extends JdbcDaoSupport implements ProjectExpenseDao {
	private ExpenseUpdateQuery11 expenseUpdateQuery11;// BU����� ���� Ȯ�� �� ���
	private ExpenseUpdateQuery13 expenseUpdateQuery13;// BU����� �ݷ�
	private ExpenseUpdateQuery14 expenseUpdateQuery14;// BU����� ���� 190424
	private ExpenseInsertQuery31 expenseInsertQuery31;// ȸ�� ����� ����(insert)
	private ExpenseUpdateQuery31 expenseUpdateQuery31;// ȸ�� ����� ����(update)
	private ExpenseUpdateQuery33 expenseUpdateQuery33;// ����� : ȸ�����ۿ��� = Y

	// �׷��� ����� ���� �� ��� �� ������ �Է�
	private RealTimeExpenseInsertQuery realTimeExpenseInsertQuery;
	private RealTimeExpenseDetailInsertQuery realTimeExpenseDetailInsertQuery;
	private DataSource erpDataSource;// ȸ�� ������ ���̽� ����

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

	// ����� ������� ���� �ο�
	@SuppressWarnings("unchecked")
	@Override
	public List<ExpenseRealTimeResultDetailForSanction> addSeqNumDateInExpenseList(List<ExpenseRealTimeResultDetailForSanction> expenseList)
			throws DataAccessException {
		List<ExpenseRealTimeResultDetailForSanction> retVal = new ArrayList<ExpenseRealTimeResultDetailForSanction>();

		for (ExpenseRealTimeResultDetailForSanction data : expenseList) {
			if (data.getSalaryType().equals("KEYIN")) {

				List<Map<String, String>> list = getJdbcTemplate().queryForList(
						" Select seq from projectTeachingFeeMDetail where projectCode=? and year=?  and month=? and chargeSsn=? ",
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

	// ����� ������� ������ �ο�
	@SuppressWarnings("unchecked")
	@Override
	public List<ExpenseRealTimeResultDetailForSanction> addAssignDateInExpenseList(List<ExpenseRealTimeResultDetailForSanction> expenseList,
			String isSanction) throws DataAccessException {
		List<ExpenseRealTimeResultDetailForSanction> retVal = new ArrayList<ExpenseRealTimeResultDetailForSanction>();

		for (ExpenseRealTimeResultDetailForSanction data : expenseList) {
			if (data.getSalaryType().equals("PREPORT")) {

				String query = "  select    assignDate from projectReportContent                                                 "
						+ "         where 	payYn = 'Y' and	state='complete' and projectCode=? and assignDate like ? and writerSsn =?";
				if (isSanction.equals("init")) {
					query = query + "  and	isSanction is null";
				} else if (isSanction.equals("refresh")) {
					query = query + "  and	(isSanction = 'ING' or isSanction is null)";
				}

				List<Map<String, String>> list = getJdbcTemplate().queryForList(
						query,
						new Object[] { data.getSalaryProjectCode(), data.getSalaryYear() + data.getSalaryMonth() + "%",
								StringUtil.replace(data.getSalarySsn(), "-", "") });
				String assignDateStr = "";
				for (Map<String, String> map : list) {
					assignDateStr = assignDateStr + ";" + map.get("assignDate");

				}
				if (assignDateStr.startsWith(";")) {
					assignDateStr = assignDateStr.substring(1);
				}
				data.setSalarySeq(assignDateStr);
			}
			retVal.add(data);
		}

		return retVal;
	}

	// �׷��� ����� Ȯ�� �� ��ȿ� ���� ����� ���������� ���¸� ���� ���������� ����
	@Override
	public void updateExpensePreport(ProjectExpenseForm projectExpenseForm) throws DataAccessException {
		String projectCode[] = projectExpenseForm.getSalaryProjectCode();
		String ssn[] = projectExpenseForm.getSalarySsn();
		String seq[] = projectExpenseForm.getSalarySeq();
		for (int i = 0; i < projectCode.length; i++) {
			if (projectExpenseForm.getSalaryType()[i].equals("PREPORT")) {
				String seq_[] = seq[i].split(";");
				for (int j = 0; j < seq_.length; j++) {
					getJdbcTemplate()
							.update("update ProjectReportContent set groupCheck = 'Y', isSanction = 'ING'   where projectCode=? and writerSsn=? and assignDate=?",
									new Object[] { projectCode[i], StringUtil.replace(ssn[i], "-", ""), seq_[j] });
				}
			}
		}
	}

	// �׷��� ����� ǰ�� �ݷ� �� ����� ���������� ���¸� �ʱ���·� ����
	@Override
	public void updateExpensePreportForRollBack(ProjectExpenseForm projectExpenseForm) throws DataAccessException {
		String projectCode[] = projectExpenseForm.getSalaryProjectCode();
		String ssn[] = projectExpenseForm.getSalarySsn();
		String seq[] = projectExpenseForm.getSalarySeq();
		for (int i = 0; i < projectCode.length; i++) {
			if (projectExpenseForm.getSalaryType()[i].equals("PREPORT")) {
				String seq_[] = seq[i].split(";");
				for (int j = 0; j < seq_.length; j++) {
					if (seq_[j] != null && !seq_[j].equals("")) {
						getJdbcTemplate().update(
								"update ProjectReportContent set groupCheck = 'N' where projectCode=? and writerSsn=? and assignDate =? ",
								new Object[] { projectCode[i], StringUtil.replace(ssn[i], "-", ""), seq_[j] });
					}
				}
			}
		}

	}
	
	// �׷��� ����� �ݷ� ���� ���� �� ����� ���������� ���¸� �ʱ���·� ����
	@Override
	public void updateExpensePreportForDelete(ProjectExpenseForm projectExpenseForm) throws DataAccessException {
		String projectCode[] = projectExpenseForm.getSalaryProjectCode();
		String ssn[] = projectExpenseForm.getSalarySsn();
		String seq[] = projectExpenseForm.getSalarySeq();
		for (int i = 0; i < projectCode.length; i++) {
			if (projectExpenseForm.getSalaryType()[i].equals("PREPORT")) {
				String seq_[] = seq[i].split(";");
				for (int j = 0; j < seq_.length; j++) {
					if (seq_[j] != null && !seq_[j].equals("")) {
						getJdbcTemplate().update(
								"update ProjectReportContent set groupCheck = 'N', isSanction = null where projectCode=? and writerSsn=? and assignDate =? ",
								new Object[] { projectCode[i], StringUtil.replace(ssn[i], "-", ""), seq_[j] });
					}
				}
			}
		}

	}	

	// �׷��� ����� ǰ�� ���� �� �Ϸ� ���·� ����
	public void updateExpensePreportFinish(ProjectExpenseForm projectExpenseForm) throws DataAccessException {
		String projectCode[] = projectExpenseForm.getSalaryProjectCode();
		String ssn[] = projectExpenseForm.getSalarySsn();
		String seq[] = projectExpenseForm.getSalarySeq();
		for (int i = 0; i < projectCode.length; i++) {
			if (projectExpenseForm.getSalaryType()[i].equals("PREPORT")) {
				String seq_[] = seq[i].split(";");
				for (int j = 0; j < seq_.length; j++) {
					getJdbcTemplate().update(
							" update ProjectReportContent set isSanction = 'FINISHED' " + " where projectCode=? and writerSsn=? and assignDate =? ",
							new Object[] { projectCode[i], StringUtil.replace(ssn[i], "-", ""), seq_[j] });
				}
			}
		}
	}

	@Override
	public void updateExpenseTMFinish(ProjectExpenseForm projectExpenseForm) throws DataAccessException {
		String projectCode[] = projectExpenseForm.getSalaryProjectCode();
		String year[] = projectExpenseForm.getSalaryYear();
		String month[] = projectExpenseForm.getSalaryMonth();
		String ssn[] = projectExpenseForm.getSalarySsn();
		String seq[] = projectExpenseForm.getSalarySeq();

		for (int i = 0; i < projectCode.length; i++) {// ��ü ���μ�
			if (projectExpenseForm.getSalaryType()[i].equals("KEYIN")) {
				String seq_[] = seq[i].split(";");
				for (int j = 0; j < seq_.length; j++) {// seq ���� Ȯ��
					if (!seq_[j].equals("")) {// seq �� ����
						getJdbcTemplate().update(
								" UPDATE ProjectTeachingFeeMDetail SET isSanction = 'FINISHED' " 
							  + " WHERE projectCode = ? and year = ? and month = ? and chargeSsn = ? and seq =? ", 
								new Object[] {projectCode[i], year[i], month[i], ssn[i], Integer.parseInt(seq_[j]) });
					}
				}
			}
		}
		
	}

	// �׷��� ����� ǰ�� ���� �� �ΰǺ� �ʰ��� ���ؼ� Ȧ���� ���������� �÷��� ����
	@SuppressWarnings("unchecked")
	public void updateProjectReportHoldingStateC(ProjectExpenseForm projectExpenseForm) throws DataAccessException {
		StringBuffer retriveQuery = new StringBuffer();
		retriveQuery.append(" select	projectReportContent.projectCode, projectMember.ssn, projectReportContent.assignDate         ");
		retriveQuery.append("  from 	projectMember projectMember, expertPool expertPool, projectReportContent projectReportContent");
		retriveQuery.append(" where		projectMember.ssn = projectReportContent.writerSsn              ");
		retriveQuery.append("   and		projectMember.projectCode = projectReportContent.projectCode");
		retriveQuery.append("   and		projectMember.ssn = expertpool.ssn             ");
		retriveQuery.append("   and		projectMember.role <> 'AG'                     ");
		retriveQuery.append("   and		projectReportContent.payYn = 'Y'               ");
		retriveQuery.append("   and		projectReportContent.state = 'complete'        ");
		retriveQuery.append("   and		(approveDate <> '' and approveDate is not null)");
		retriveQuery.append("   and		assignDate like ? and expertpool.dept=?        ");

		List<PreportState> plist = getJdbcTemplate().query(retriveQuery.toString(),
				new Object[] { projectExpenseForm.getAssignDate() + "%", projectExpenseForm.getDeptCode() }, new RowMapper() {
					public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
						PreportState state = new PreportState();
						state.setProjectCode(rs.getString("projectCode"));
						state.setSsn(rs.getString("ssn"));
						state.setAssignDate(rs.getString("assignDate"));
						return state;
					}
				});
		if (plist != null && plist.size() > 0) {
			for (PreportState preportState : plist) {
				getJdbcTemplate().update("update projectReportContent set isHolding='Y' where projectCode=?  and writerSsn=? and assignDate=?",
						new Object[] { preportState.getProjectCode(), preportState.getSsn(), preportState.getAssignDate() });
			}
		}
	}

	// ���� ����� Ȯ�� �� ��ȿ� ���� ����� ������� ���¸� ���� ���������� ����
	@Override
	public void updateExpenseTM(ProjectExpenseForm projectExpenseForm) throws DataAccessException {

		String projectCode[] = projectExpenseForm.getSalaryProjectCode();
		String year[] = projectExpenseForm.getSalaryYear();
		String month[] = projectExpenseForm.getSalaryMonth();
		String ssn[] = projectExpenseForm.getSalarySsn();
		String seq[] = projectExpenseForm.getSalarySeq();

		for (int i = 0; i < projectCode.length; i++) {// ��ü ���μ�
			if (projectExpenseForm.getSalaryType()[i].equals("KEYIN")) {
				String seq_[] = seq[i].split(";");
				for (int j = 0; j < seq_.length; j++) {// seq ���� Ȯ��
					if (!seq_[j].equals("")) {// seq �� ����
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

	// ���� ����� Ȯ�� �� ��ȿ� ���� ����� ������� ���¸� �ʱ���·� ����
	@Override
	public void updateExpenseTMForRollBack(ProjectExpenseForm projectExpenseForm) throws DataAccessException {
		String projectCode[] = projectExpenseForm.getSalaryProjectCode();
		String year[] = projectExpenseForm.getSalaryYear();
		String month[] = projectExpenseForm.getSalaryMonth();
		String ssn[] = projectExpenseForm.getSalarySsn();
		String seq[] = projectExpenseForm.getSalarySeq();

		for (int i = 0; i < projectCode.length; i++) {// ��ü ���μ�
			if (projectExpenseForm.getSalaryType()[i].equals("KEYIN")) {
				String seq_[] = seq[i].split(";");

				for (int j = 0; j < seq_.length; j++) {// seq ���� Ȯ��
					if (!seq_[j].equals("")) {// seq �� ����
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
	

	// ���� ����� Ȯ�� �� ��ȿ� ���� ����� ������� ���¸� �ʱ���·� ����
	@Override
	public void updateExpenseTMForDelete(ProjectExpenseForm projectExpenseForm) throws DataAccessException {
		String projectCode[] = projectExpenseForm.getSalaryProjectCode();
		String year[] = projectExpenseForm.getSalaryYear();
		String month[] = projectExpenseForm.getSalaryMonth();
		String ssn[] = projectExpenseForm.getSalarySsn();
		String seq[] = projectExpenseForm.getSalarySeq();

		for (int i = 0; i < projectCode.length; i++) {// ��ü ���μ�
			if (projectExpenseForm.getSalaryType()[i].equals("KEYIN")) {
				String seq_[] = seq[i].split(";");

				for (int j = 0; j < seq_.length; j++) {// seq ���� Ȯ��
					if (!seq_[j].equals("")) {// seq �� ����
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
	 * ȸ�� ����� ���� �� ����� ȸ�� ���� ������Ʈ
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

		for (int i = 0; i < projectCode.length; i++) {// �ش� �� ���� ��ϵ� ����ᰡ �ִ��� Ȯ��
			if (type[i].equals("PREPORT")) {
				int count = getJdbcTemplate().queryForInt(" SELECT COUNT(*) AS count FROM projectTeachingFeeM WHERE projectCode=? AND year+month=?",
						new Object[] { projectCode[i], year[i] + month[i] });
				if (count == 0) {
					this.realTimeExpenseInsertQuery.insert(projectCode[i], year[i], month[i]);// ����� ���� ���
				}
			}
		}

		for (int i = 0; i < projectCode.length; i++) {// ���� MAX
			if (type[i].equals("PREPORT")) {
				int seqMax = getJdbcTemplate().queryForInt(
						"	         SELECT CASE WHEN MAX(CONVERT(int, SEQ)) IS NULL THEN '1'                          "
								+ "              WHEN MAX(CONVERT(int, SEQ)) IS NOT NULL THEN MAX(SEQ) +1 END          "
								+ " FROM projectTeachingFeeMDetail WHERE projectCode=? AND chargeSsn=? AND year=? AND month=?	",
						new Object[] { projectCode[i], StringUtil.replace(ssn[i], "-", ""), year[i], month[i] });

				if (!sumAmount[i].equals("")) {
					this.realTimeExpenseDetailInsertQuery.insert(projectCode[i], year[i], month[i], StringUtil.replace(ssn[i], "-", ""),
							String.valueOf(seqMax), sumAmount[i]);// ����� ���� �� ���
				}
				if (!sumAmount[i].equals("0")) {// �Ұ��� 0�ΰ� ����

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
					}else */
					if(jobClassErp[i].equals("011")){
						expense.setTea_amt(sumAmount[i]);
						expense.setTea_amt_seventy(String.valueOf(Integer.valueOf(sumAmount[i]) * 0.7));
						expense.setTea_amt_thirty(String.valueOf(Integer.valueOf(sumAmount[i]) * 0.3));
					} else {
						expense.setTea_amt(sumAmount[i]);
						expense.setTea_amt_seventy(String.valueOf(Integer.valueOf(0)));
						expense.setTea_amt_thirty(String.valueOf(Integer.valueOf(0)));
					}

					try {
						this.expenseInsertQuery31.insert(expense);// ȸ�� �Է�
					} catch (Exception e) {
						e.printStackTrace();
						this.expenseUpdateQuery31.update(expense);// ȸ�� ������Ʈ
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

		for (int i = 0; i < projectCode.length; i++) {// ��ü ���μ�
			if (type[i].equals("KEYIN") && !sumAmount[i].equals("0")) {// �Ұ��� 0�ΰ� ����
				String seq_[] = seq[i].split(";");

				for (int j = 0; j < seq_.length; j++) {// seq ���� Ȯ��
					if (!seq_[j].equals("")) {// seq �� ����
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
								"SELECT payYN FROM projectTeachingFeeMDetail WHERE projectCode=? AND year=? AND month=? AND chargeSsn=? AND seq=?",
								new Object[] { projectCode[i], year[i], month[i], StringUtil.replace(ssn[i], "-", ""), seq_[j] }).get("payYN"));

						if (payYN == null || payYN.equals("N")) {
							String tea_amt = String.valueOf(getJdbcTemplate().queryForInt(
									"	SELECT convert(bigint, convert(money, amount)) AS amount FROM 	projectTeachingFeeMDetail "
											+ "  WHERE  projectCode=? AND year=? AND month=? AND chargeSsn=? AND seq=?                          ",
									new Object[] { projectCode[i], year[i], month[i], StringUtil.replace(ssn[i], "-", ""), seq_[j] }));
							tea_amt = tea_amt.equals("") || tea_amt.equals(null) ? "0" : tea_amt;
							expense.setTea_amt(tea_amt);
							
							/*if(jobClassErp[i].equals("001")){
								System.out.println("������ : 001");
								expense.setTea_amt(sumAmount[i]);
								expense.setTea_amt_seventy(String.valueOf(Integer.valueOf(sumAmount[i]) * 0.7));
								expense.setTea_amt_thirty(String.valueOf(Integer.valueOf(sumAmount[i]) * 0.3));
							}else */
							
							if(jobClassErp[i].equals("011")){
								System.out.println("������ : 011");
								expense.setTea_amt(sumAmount[i]);
								expense.setTea_amt_seventy(String.valueOf(Integer.valueOf(sumAmount[i]) * 0.7));
								expense.setTea_amt_thirty(String.valueOf(Integer.valueOf(sumAmount[i]) * 0.3));
							} else {
								System.out.println("������ : not");
								expense.setTea_amt(sumAmount[i]);
								expense.setTea_amt_seventy(String.valueOf(Integer.valueOf(0)));
								expense.setTea_amt_thirty(String.valueOf(Integer.valueOf(0)));
							}
							
							try {
								this.expenseInsertQuery31.insert(expense);// ȸ�� ����
							} catch (Exception e) {
								this.expenseUpdateQuery31.update(expense);// ȸ�� ������Ʈ
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
			super(ds, "	UPDATE 	projectTeachingFeeMDetail  SET 	ctmCheckYN = 'Y', approvalYN = 'Y', isSanction = 'ING' "
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
			super(ds, "	UPDATE 	projectTeachingFeeMDetail SET ctmCheckYN = 'Y', approvalYN = 'N'    "
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
			super(ds, "	UPDATE 	ProjectTeachingFeeMDetail SET ctmCheckYN = 'N', approvalYN = 'N', isSanction = null"
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
			super(ds, "	INSERT INTO A_TEASLIP_PMS (	COMP_CODE, 										" 
					+ "                             PROJID, YYYYMM, JOB_TYPE, JUMIN, 				"
					+ "                             SEQ, TEA_AMT, ADD_AMT, ETC_AMT, 				"
					+ "								INSERT_SNO, BONBU_GU,							"		
					+ "                             APPR_DATE, WRT_DATE )							"
					+ "	VALUES	( 'C',        ?, ?, ?, ?,      ?, ?, ?, ?, ?, ?,                    "
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
					expense.getUid(),// ���� �ֹι�ȣ ����

					expense.getSeq(), expense.getTea_amt(), expense.getTea_amt_seventy() , expense.getTea_amt_thirty(),
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
					expense.getUid(),// ���� �ֹι�ȣ ����
					expense.getSeq() });
		}
	}

	protected class ExpenseUpdateQuery33 extends SqlUpdate {
		protected ExpenseUpdateQuery33(DataSource ds) {
			super(ds, "	UPDATE 	projectTeachingFeeMDetail SET billSendYN = 'Y' WHERE projectCode=? AND year=? AND month=? AND chargeSsn=? AND seq=? ");
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
	 * ����� ���� ���
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
	 * ����� ���� �� ���
	 */
	protected class RealTimeExpenseDetailInsertQuery extends SqlUpdate {
		protected RealTimeExpenseDetailInsertQuery(DataSource ds) {
			super(ds, "	INSERT INTO projectTeachingFeeMDetail ( projectCode, year, month, chargeSsn, seq, amount, "
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
