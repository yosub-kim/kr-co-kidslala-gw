package kr.co.kmac.pms.sanction.projectexpense.manager.impl;

import java.util.Date;
import java.util.List;

import kr.co.kmac.pms.common.util.DateUtil;
import kr.co.kmac.pms.common.util.IdGenerator;
import kr.co.kmac.pms.common.worklist.data.Work;
import kr.co.kmac.pms.common.worklist.data.WorkConstants;
import kr.co.kmac.pms.common.worklist.manager.WorklistManager;
import kr.co.kmac.pms.sanction.exception.SanctionException;
import kr.co.kmac.pms.sanction.general.data.SanctionConstants;
import kr.co.kmac.pms.sanction.projectexpense.dao.ProjectExpenseDao;
import kr.co.kmac.pms.sanction.projectexpense.dao.ProjectExpenseTempForSanctionDao;
import kr.co.kmac.pms.sanction.projectexpense.data.ExpenseRealTimeResultDetailForSanction;
import kr.co.kmac.pms.sanction.projectexpense.form.ProjectExpenseForm;
import kr.co.kmac.pms.sanction.projectexpense.manager.ProjectExpenseManager;

public class ProjectExpenseManagerImpl implements ProjectExpenseManager {

	private ProjectExpenseDao projectExpenseDao;
	private ProjectExpenseTempForSanctionDao projectExpenseTempForSanctionDao;
	private WorklistManager worklistManager;

	@Override
	public List<ExpenseRealTimeResultDetailForSanction> addAssignDateInExpenseList(List<ExpenseRealTimeResultDetailForSanction> expenseList,
			String isSanction) throws SanctionException {
		return this.getProjectExpenseDao().addAssignDateInExpenseList(expenseList, isSanction);
	}

	@Override
	public List<ExpenseRealTimeResultDetailForSanction> addSeqNumInExpenseList(List<ExpenseRealTimeResultDetailForSanction> expenseList)
			throws SanctionException {
		return this.getProjectExpenseDao().addSeqNumDateInExpenseList(expenseList);
	}

	@Override
	public List<ExpenseRealTimeResultDetailForSanction> getProjectExpenseTempDataList(String seq) throws SanctionException {
		return this.getProjectExpenseTempForSanctionDao().select(seq);
	}
	
	@Override
	public List<ExpenseRealTimeResultDetailForSanction> getProjectExpenseTempDataList(String year, String month) throws SanctionException {
		return this.getProjectExpenseTempForSanctionDao().select(year, month);
	}

	@Override
	public void executeARExpenseSanction(ProjectExpenseForm projectExpenseForm) throws SanctionException {
		List<ExpenseRealTimeResultDetailForSanction> expenseRealTimeResultDetailForSanctionList = ExpenseRealTimeResultDetailForSanction
				.valueOf(projectExpenseForm);
		this.getProjectExpenseTempForSanctionDao().delete(projectExpenseForm.getProjectExpenseSeq());
		for (ExpenseRealTimeResultDetailForSanction expenseRealTimeResultDetailForSanction : expenseRealTimeResultDetailForSanctionList) {
			this.getProjectExpenseTempForSanctionDao().insert(expenseRealTimeResultDetailForSanction);
		}

		// 집계된 지도일지의 상태를 결재 진행중으로 변경
		this.getProjectExpenseDao().updateExpensePreport(projectExpenseForm);
		// 집계된 강사료의 상태를 결재 진행중으로 변경
		this.getProjectExpenseDao().updateExpenseTM(projectExpenseForm);// 팀장 확인 ctmCheckYN = Y, approvalYN = Y
	}

	@Override
	public void executeKMExpenseSanction(ProjectExpenseForm projectExpenseForm) throws SanctionException {
		List<ExpenseRealTimeResultDetailForSanction> expenseRealTimeResultDetailForSanctionList = ExpenseRealTimeResultDetailForSanction
				.valueOf(projectExpenseForm);
		this.getProjectExpenseTempForSanctionDao().delete(projectExpenseForm.getProjectExpenseSeq());
		for (ExpenseRealTimeResultDetailForSanction expenseRealTimeResultDetailForSanction : expenseRealTimeResultDetailForSanctionList) {
			this.getProjectExpenseTempForSanctionDao().insert(expenseRealTimeResultDetailForSanction);
		}

		// 집계된 지도일지의 상태를 결재 진행중으로 변경
		this.getProjectExpenseDao().updateExpensePreport(projectExpenseForm);
	}

	@Override
	public void assignFinishTask(ProjectExpenseForm projectExpenseForm) throws SanctionException {
		try{
		Work assigneeWork = this.getWorklistManager().getWorkTemplate("SFEE8809e0a4c4436010a4ead57e4001f", "PMS",
				projectExpenseForm.getAssistant1Ssn(), "8888888", projectExpenseForm.getApprovalType(), String.valueOf(projectExpenseForm.getSeq()));
		assigneeWork.setId(IdGenerator.generate(IdGenerator.PREFIX_WORK));
		assigneeWork.setCreateDate(new Date());
		assigneeWork.setDraftDate(DateUtil.getDateTime("yyyyMMdd", projectExpenseForm.getRegisterDate()));
		assigneeWork.setDraftUserId(projectExpenseForm.getRegisterSsn());
		assigneeWork.setDraftUserDept(projectExpenseForm.getRegisterDept());
		assigneeWork.setLevel(SanctionConstants.SANCTION_STATE_REVIEW);
		assigneeWork.setState(WorkConstants.WORK_STATE_READY);
		assigneeWork.setTitle(projectExpenseForm.getSubject());

		this.getWorklistManager().assignWork(assigneeWork);
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void completeARExpenseSanction(ProjectExpenseForm projectExpenseForm) throws SanctionException {
		this.getProjectExpenseDao().updateProjectReportHoldingStateC(projectExpenseForm);// 프로젝트 인건비 초과로 인해서 홀딩된 지도일지에 플레그 설정
		this.getProjectExpenseDao().insertBillAndUpdateBillSendC(projectExpenseForm);// 회계 강사료 전송 및 강사료 회계 전송 업데이트
		this.getProjectExpenseDao().updateExpensePreportFinish(projectExpenseForm);// 지도일지에 해당 강사료 집행이 마무리 되었다고 상태값 변경
		this.getProjectExpenseDao().updateExpenseTMFinish(projectExpenseForm);//강사료 키인 테이블에 강사료 집행이 마무리 되었다고 상태값 변경
		this.getProjectExpenseDao().insertBillAndUpdateBillSend(projectExpenseForm);// 회계 강사료 전송 및 강사료 회계 전송 업데이트
	}

	@Override
	public void completeKMExpenseSanction(ProjectExpenseForm projectExpenseForm) throws SanctionException {
		this.getProjectExpenseDao().updateProjectReportHoldingStateC(projectExpenseForm);// 프로젝트 인건비 초과로 인해서 홀딩된 지도일지에 플레그 설정
		this.getProjectExpenseDao().insertBillAndUpdateBillSendC(projectExpenseForm);// 회계 강사료 전송 및 강사료 회계 전송 업데이트
		this.getProjectExpenseDao().updateExpensePreportFinish(projectExpenseForm);// 지도일지에 해당 강사료 집행이 마무리 되었다고 상태값 변경
	}

	@Override
	public void deleteARExpenseSanction(ProjectExpenseForm projectExpenseForm) throws SanctionException {
		this.getProjectExpenseTempForSanctionDao().delete(String.valueOf(projectExpenseForm.getProjectExpenseSeq()));
		this.getProjectExpenseDao().updateExpensePreportForDelete(projectExpenseForm);
		this.getProjectExpenseDao().updateExpenseTMForDelete(projectExpenseForm);
	}

	@Override
	public void deleteKMExpenseSanction(ProjectExpenseForm projectExpenseForm) throws SanctionException {
		this.getProjectExpenseTempForSanctionDao().delete(String.valueOf(projectExpenseForm.getProjectExpenseSeq()));
		this.getProjectExpenseDao().updateExpensePreportForRollBack(projectExpenseForm);
	}

	@Override
	public void rejectARExpenseSanction(ProjectExpenseForm projectExpenseForm) throws SanctionException {
		this.getProjectExpenseDao().updateExpensePreportForRollBack(projectExpenseForm);
		this.getProjectExpenseDao().updateExpenseTMForRollBack(projectExpenseForm);
	}

	@Override
	public void rejectKMExpenseSanction(ProjectExpenseForm projectExpenseForm) throws SanctionException {
		this.getProjectExpenseDao().updateExpensePreportForRollBack(projectExpenseForm);
	}

	// ///////////////////////////////////////////
	public ProjectExpenseDao getProjectExpenseDao() {

		return projectExpenseDao;
	}

	public void setProjectExpenseDao(ProjectExpenseDao projectExpenseDao) {

		this.projectExpenseDao = projectExpenseDao;
	}

	public ProjectExpenseTempForSanctionDao getProjectExpenseTempForSanctionDao() {

		return projectExpenseTempForSanctionDao;
	}

	public void setProjectExpenseTempForSanctionDao(ProjectExpenseTempForSanctionDao projectExpenseTempForSanctionDao) {
		this.projectExpenseTempForSanctionDao = projectExpenseTempForSanctionDao;
	}

	public WorklistManager getWorklistManager() {
		return worklistManager;
	}

	public void setWorklistManager(WorklistManager worklistManager) {
		this.worklistManager = worklistManager;
	}

}
