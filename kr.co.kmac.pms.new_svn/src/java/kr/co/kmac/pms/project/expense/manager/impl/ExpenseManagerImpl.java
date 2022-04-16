package kr.co.kmac.pms.project.expense.manager.impl;

import java.util.List;

import kr.co.kmac.pms.project.exception.ProjectException;
import kr.co.kmac.pms.project.expense.dao.ExpenseDao;
import kr.co.kmac.pms.project.expense.dao.ExpenseDetailDao;
import kr.co.kmac.pms.project.expense.dao.ExpenseDetailRestDao;
import kr.co.kmac.pms.project.expense.dao.ExpenseHistoryDao;
import kr.co.kmac.pms.project.expense.dao.ExpenseRestDao;
import kr.co.kmac.pms.project.expense.data.Expense;
import kr.co.kmac.pms.project.expense.data.ExpenseDetail;
import kr.co.kmac.pms.project.expense.data.ExpenseDetailAdded;
import kr.co.kmac.pms.project.expense.form.ExpenseForm;
import kr.co.kmac.pms.project.expense.manager.ExpenseManager;
import kr.co.kmac.pms.project.master.dao.ProjectMemberDao;
import kr.co.kmac.pms.project.master.data.ProjectMember;
import kr.co.kmac.pms.project.statistics.data.ProjectMemberMonthlyMM;

public class ExpenseManagerImpl implements ExpenseManager {

	private ExpenseDao expenseDao;
	private ExpenseRestDao expenseRestDao;
	private ExpenseDetailDao expenseDetailDao;
	private ExpenseDetailRestDao expenseDetailRestDao;
	private ExpenseHistoryDao expenseHistoryDao;
	private ProjectMemberDao projectMemberDao;

	@Override
	public List<ProjectMember> getExpenseProjectMember(String projectCode) throws ProjectException {
		return this.getProjectMemberDao().selectForExpense(projectCode);
	}
	
	@Override
	public List<ProjectMember> getExpenseProjectMember2(String projectCode) throws ProjectException {
		return this.getProjectMemberDao().selectForExpense2(projectCode);
	}
	
	@Override
	public List<ProjectMember> getExpenseProjectMemberPU(String projectCode) throws ProjectException {
		return this.getProjectMemberDao().selectForExpensePU(projectCode);
	}
	
	@Override
	public List<ProjectMember> getScheduleDetailListPU(String projectCode, String year, String month) throws ProjectException {
		return this.getProjectMemberDao().selectSchedule(projectCode, year, month);
	}
	
	@Override
	public List<ProjectMember> getScheduleReportDetail(String projectCode, String year, String month) throws ProjectException {
		return this.getProjectMemberDao().selectScheduleReport(projectCode, year, month);
	}
	
	@Override
	public List<ProjectMember> getMonthlyReportDetail(String projectCode, String year, String month) throws ProjectException {
		return this.getProjectMemberDao().selectMonthlyReport(projectCode, year, month);
	}
	
	@Override
	public List<ProjectMember> getExpenseProjectMemberAll(String projectCode) throws ProjectException {
		return this.getProjectMemberDao().selectForExpenseAll(projectCode);
	}

	@Override
	public List<ExpenseDetail> getExpenseDetailList(String projectCode, String year, String month) throws ProjectException {
		return this.getExpenseDetailDao().select(projectCode, year, month);
	}
	
	@Override
	public List<ExpenseDetail> getExpenseDetailList2(String projectCode, String year, String month) throws ProjectException {
		return this.getExpenseDetailDao().select2(projectCode, year, month);
	}
		
	@Override
	public List<ExpenseDetailAdded> getExpenseDetailListPU(String projectCode, String year, String month) throws ProjectException {
		return this.getExpenseDetailDao().selectPU(projectCode, year, month);
	}
	
	@Override
	public List<ExpenseDetail> getExpenseAllDetailList(String projectCode, String year, String month) throws ProjectException {
		return this.getExpenseDetailDao().selectAllExpense(projectCode, year, month);
	}

	// 프로젝트 성과급 지급 내역(100%) 
	@Override
	public List<List<String>> getProjectExpenseHistory(String projectCode) throws ProjectException {
		return this.getExpenseHistoryDao().select(projectCode);
	}
	
	// 프로젝트 성과급 지급 내역(80%) 
	@Override
	public List<List<String>> getProjectExpenseHistory_mmplan(String projectCode) throws ProjectException {
		return this.getExpenseHistoryDao().select_mmplan(projectCode);
	}
	
	// 프로젝트 성과급 지급 내역(20%) 
	@Override
	public List<List<String>> getProjectExpenseHistory_rest(String projectCode) throws ProjectException {
		return this.getExpenseHistoryDao().select_rest(projectCode);
	}
	

	@Override
	public void insertProjectExpense(ExpenseForm expenseForm) throws ProjectException {
		if (this.getExpenseDao().select(expenseForm.getProjectCode(), expenseForm.getYear(), expenseForm.getMonth()) == null) {
			Expense expense = new Expense();
			expense.setProjectCode(expenseForm.getProjectCode());
			expense.setYear(expenseForm.getYear());
			expense.setMonth(expenseForm.getMonth());
			this.getExpenseDao().insert(expense);
		}
		for (int i = 0; i < expenseForm.getSeq().length; i++) {
			if (expenseForm.getSeq()[i] != -1 ) {
				this.getExpenseDetailDao().delete(expenseForm.getProjectCode(), expenseForm.getYear(), expenseForm.getMonth(),
						expenseForm.getSsn()[i], expenseForm.getSeq()[i]);
			}
			ExpenseDetail expenseDetail = new ExpenseDetail();
			expenseDetail.setProjectCode(expenseForm.getProjectCode());
			expenseDetail.setSsn(expenseForm.getSsn()[i]);
			expenseDetail.setYear(expenseForm.getYear());
			expenseDetail.setMonth(expenseForm.getMonth());
			expenseDetail.setSeq(this.getExpenseDetailDao().getExpenseSeqMaxNum(expenseForm.getProjectCode(), expenseForm.getYear(),
					expenseForm.getMonth(), expenseForm.getSsn()[i]));
			expenseDetail.setAmount(expenseForm.getAmount()[i]);
			this.getExpenseDetailDao().insert(expenseDetail);
			
			/*MM value 주석, 해당 테이블: ProjectMemberMMPlan*/
			/*ProjectMemberMonthlyMM projectMemberMonthlyMM = new ProjectMemberMonthlyMM();
			projectMemberMonthlyMM.setProjectCode(expenseForm.getProjectCode());
			projectMemberMonthlyMM.setYear(expenseForm.getYear());
			projectMemberMonthlyMM.setMonth(expenseForm.getMonth());
			projectMemberMonthlyMM.setSsn(expenseForm.getSsn()[i]);
			projectMemberMonthlyMM.setMmValue((expenseForm.getAmount()[i]).replace(",", ""));
			
			if(((expenseForm.getAmount()[i]).replace(",", "")).equals("")){
				projectMemberMonthlyMM.setMmValue("0");
			}
			if(!projectMemberMonthlyMM.getMmValue().equals("0")){
				if(this.getProjectMemberDao().selectMMinfoByProject(projectMemberMonthlyMM) == null){
					this.getProjectMemberDao().insertMMInfoByProject(projectMemberMonthlyMM);		
				}else{
					this.getExpenseDetailRestDao().deleteMMPlan(expenseForm.getProjectCode(), expenseForm.getYear(), expenseForm.getMonth(), expenseForm.getSsn()[i]);
					this.getProjectMemberDao().insertMMInfoByProject(projectMemberMonthlyMM);		
				}
			}*/	
		}
	}	
	
	@Override
	public void insertProjectExpensePU(ExpenseForm expenseForm) throws ProjectException {
		if (this.getExpenseDao().select(expenseForm.getProjectCode(), expenseForm.getYear(), expenseForm.getMonth()) == null) {
			Expense expense = new Expense();
			expense.setProjectCode(expenseForm.getProjectCode());
			expense.setYear(expenseForm.getYear());
			expense.setMonth(expenseForm.getMonth());
			this.getExpenseDao().insert(expense);
		}
		for (int i = 0; i < expenseForm.getPuSeq().length; i++) {
				//System.out.println(expenseForm.getPuSsn()[i] + " : " + expenseForm.getPuMMRate()[i]);
				if (!expenseForm.getPuMMRate()[i].equals("") && expenseForm.getPuMMRate()[i] != null) {
					// 100% 금액 초기화, 해당 테이블: ProjectTeachingFeeMDetail
					this.getExpenseDetailDao().delete(expenseForm.getProjectCode(), expenseForm.getYear(), expenseForm.getMonth(),
							expenseForm.getPuSsn()[i], expenseForm.getPuSeq()[i]);
					// 20% 금액 초기화, 해당 테이블: ProjectTeachingRestFeeTemp
					/*try { this.getExpenseRestDao().deleteTemp(expenseForm.getProjectCode(), expenseForm.getYear(), expenseForm.getMonth(), expenseForm.getPuSsn()[i]);
					} catch(Exception e) { // do nothing }*/
				}
				ExpenseDetail expenseDetail = new ExpenseDetail();
				expenseDetail.setProjectCode(expenseForm.getProjectCode());
				expenseDetail.setSsn(expenseForm.getPuSsn()[i]);
				expenseDetail.setYear(expenseForm.getYear());
				expenseDetail.setMonth(expenseForm.getMonth());
				expenseDetail.setSeq(this.getExpenseDetailDao().getExpenseSeqMaxNum(expenseForm.getProjectCode(), expenseForm.getYear(),
						expenseForm.getMonth(), expenseForm.getPuSsn()[i]));
				
				// 수행 인력의 기준금액을 참조하기 위한 프로젝트 수행 인력 정보 생성
				List<ProjectMember> projectMembers = this.getProjectMemberDao().select(expenseForm.getProjectCode());
				
				String amount = "";
				for (ProjectMember projectMember : projectMembers) {
					if(projectMember.getSsn().equals(expenseForm.getPuSsn()[i])) {
					// 성과급 100%
					try{
						amount = String.valueOf(expenseForm.getPuMMRate()[i]);
						// 20% 주석
						/*restAmount = String.valueOf(expenseForm.getPuMMRate()[i]);
						 *amount = Float.parseFloat(projectMember.getResRate()) * Float.parseFloat(projectMember.getCost()) * Float.parseFloat(expenseForm.getPuMMRate()[i]) * 0.8f;
						  restAmount = Float.parseFloat(projectMember.getResRate()) * Float.parseFloat(projectMember.getCost()) * Float.parseFloat(expenseForm.getPuMMRate()[i]) * 0.2f;*/
						break;
						}catch(Exception e){ e.getMessage(); }
					}
				}
				expenseDetail.setAmount(String.valueOf(amount));
				this.getExpenseDetailDao().insert(expenseDetail);
				
				// 20% 주석
				/*expenseDetail.setAmount(String.valueOf(restAmount));
				this.getExpenseRestDao().insertTemp(expenseDetail);*/
				
				/*MM value 주석, 해당 테이블: ProjectMemberMMPlan*/
				/*ProjectMemberMonthlyMM projectMemberMonthlyMM = new ProjectMemberMonthlyMM();
				projectMemberMonthlyMM.setProjectCode(expenseForm.getProjectCode());
				projectMemberMonthlyMM.setYear(expenseForm.getYear());
				projectMemberMonthlyMM.setMonth(expenseForm.getMonth());
				projectMemberMonthlyMM.setSsn(expenseForm.getPuSsn()[i]);
				projectMemberMonthlyMM.setMmValue((expenseForm.getPuMMRate()[i]).replace(",", ""));
				if (this.getProjectMemberDao().selectMMinfoByProject(projectMemberMonthlyMM) == null) {
					this.getProjectMemberDao().insertMMInfoByProject(projectMemberMonthlyMM);				
				} else {
					this.getProjectMemberDao().updateMMInfoByProject(projectMemberMonthlyMM);
				}*/
			}
	}
	
	public void insertProjectExpensePU2(ExpenseForm expenseForm) throws ProjectException {
		// 인센티브 처리를 위한 기준 테이블
		if (this.getExpenseRestDao().select(expenseForm.getProjectCode(), expenseForm.getYear(), expenseForm.getMonth()) == null) {
			Expense expense = new Expense();
			expense.setProjectCode(expenseForm.getProjectCode());
			expense.setYear(expenseForm.getYear());
			expense.setMonth(expenseForm.getMonth());
			this.getExpenseRestDao().insert(expense);
		}
		// 인센티브 금액 처리
		for (int i = 0; i < expenseForm.getPuSeq().length; i++) {
			try{
				if (expenseForm.getPuSeq()[i] != -1) {
						// 기존 인센티브 금액 삭제
						this.getExpenseRestDao().deleteTemp(expenseForm.getProjectCode(), expenseForm.getYear(), expenseForm.getMonth(),
								expenseForm.getPuSsn()[i], expenseForm.getPuSeq()[i]);
				}
			}catch(Exception e){e.getMessage();}
			ExpenseDetail expenseDetail = new ExpenseDetail();
			expenseDetail.setProjectCode(expenseForm.getProjectCode());
			expenseDetail.setSsn(expenseForm.getPuSsn()[i]);
			expenseDetail.setYear(expenseForm.getYear());
			expenseDetail.setMonth(expenseForm.getMonth());
			expenseDetail.setSeq(this.getExpenseDetailDao().getExpenseSeqMaxNum2(expenseForm.getProjectCode(), expenseForm.getYear(),
					expenseForm.getMonth(), expenseForm.getPuSsn()[i]));
				
			// 수행 인력의 기준금액을 참조하기 위한 프로젝트 수행 인력 정보 생성
			List<ProjectMember> projectMembers = this.getProjectMemberDao().select(expenseForm.getProjectCode());
			
			String restAmount = "";
			for (ProjectMember projectMember : projectMembers) {
				if(projectMember.getSsn().equals(expenseForm.getPuSsn()[i])) {
					restAmount = String.valueOf(expenseForm.getRestAmount()[i]);
					break;
				}
			}
			
			// 인센티브 저장
			expenseDetail.setAmount(String.valueOf(restAmount));
			this.getExpenseRestDao().insertReal(expenseDetail);
			
			/*MM value 없음*/
		}
	}
	
	@Override
	public void deleteProjectExpense(String projectCode, String year, String month, String ssn, int seq) {
		this.getExpenseDetailDao().delete(projectCode, year, month, ssn, seq);
	}
	
	@Override
	public void deleteProjectExpense2(String projectCode, String year, String month, String ssn, int seq) {
		this.getExpenseDetailDao().delete2(projectCode, year, month, ssn, seq);
	}
	
	@Override
	public void deleteProjectSalary(String projectCode, String year, String month, String ssn){
		this.getExpenseDetailDao().delete(projectCode, year, month, ssn);
	}
	
	@Override
	public void createProjectRestExpense(String projectCode, String year, String month, String ssn, String restAmount) {
		// 20% 누적 금액 입력을 위한 금액 기준 테이블 처리 
		if (this.getExpenseRestDao().select(projectCode, year, month) == null) {
			Expense expense = new Expense();
			expense.setProjectCode(projectCode);
			expense.setYear(year);
			expense.setMonth(month);
			this.getExpenseRestDao().insert(expense);
		}
		
		ExpenseDetail expenseDetail = new ExpenseDetail();
		expenseDetail.setProjectCode(projectCode);
		expenseDetail.setYear(year);
		expenseDetail.setMonth(month);
		expenseDetail.setSsn(ssn);
		expenseDetail.setAmount(restAmount);
		
		// 20% 누적 금액 처리(저장)
		if (this.getExpenseDetailRestDao().isExpenseValid(expenseDetail)) {
			this.getExpenseDetailRestDao().updateAmount(expenseDetail);
		} else {
			this.getExpenseDetailRestDao().insert(expenseDetail);
		}
	}
	
	@Override
	public void deleteProjectRestExpense(String projectCode, String year, String month, String ssn) {
		this.getExpenseDetailRestDao().delete(projectCode, year, month, ssn);
	}
	
	@Override
	public void deleteProjectRestSalary(String projectCode, String year, String month, String ssn) {
		this.getExpenseDetailRestDao().deleteRestSalary(projectCode, year, month, ssn);
	}
	
	@Override
	public void deleteProjectMMPlan(String projectCode, String year, String month, String ssn) {
		this.getExpenseDetailRestDao().deleteMMPlan(projectCode, year, month, ssn);
	}

	/**
	 * @return the expenseDao
	 */
	public ExpenseDao getExpenseDao() {
		return expenseDao;
	}

	/**
	 * @param expenseDao the expenseDao to set
	 */
	public void setExpenseDao(ExpenseDao expenseDao) {
		this.expenseDao = expenseDao;
	}
	
	/**
	 * @return the expenseRestDao
	 */
	public ExpenseRestDao getExpenseRestDao() {
		return expenseRestDao;
	}

	/**
	 * @param expenseRestDao the expenseRestDao to set
	 */
	public void setExpenseRestDao(ExpenseRestDao expenseRestDao) {
		this.expenseRestDao = expenseRestDao;
	}	

	/**
	 * @return the expenseDetailDao
	 */
	public ExpenseDetailDao getExpenseDetailDao() {
		return expenseDetailDao;
	}

	/**
	 * @param expenseDetailDao the expenseDetailDao to set
	 */
	public void setExpenseDetailDao(ExpenseDetailDao expenseDetailDao) {
		this.expenseDetailDao = expenseDetailDao;
	}
	
	/**
	 * @return the expenseDetailRestDao
	 */
	public ExpenseDetailRestDao getExpenseDetailRestDao() {
		return expenseDetailRestDao;
	}

	/**
	 * @param expenseDetailRestDao the expenseDetailRestDao to set
	 */
	public void setExpenseDetailRestDao(ExpenseDetailRestDao expenseDetailRestDao) {
		this.expenseDetailRestDao = expenseDetailRestDao;
	}	

	/**
	 * @return the projectMemberDao
	 */
	public ProjectMemberDao getProjectMemberDao() {
		return projectMemberDao;
	}

	/**
	 * @param projectMemberDao the projectMemberDao to set
	 */
	public void setProjectMemberDao(ProjectMemberDao projectMemberDao) {
		this.projectMemberDao = projectMemberDao;
	}

	/**
	 * @return the expenseHistoryDao
	 */
	public ExpenseHistoryDao getExpenseHistoryDao() {
		return expenseHistoryDao;
	}

	/**
	 * @param expenseHistoryDao the expenseHistoryDao to set
	 */
	public void setExpenseHistoryDao(ExpenseHistoryDao expenseHistoryDao) {
		this.expenseHistoryDao = expenseHistoryDao;
	}

}
