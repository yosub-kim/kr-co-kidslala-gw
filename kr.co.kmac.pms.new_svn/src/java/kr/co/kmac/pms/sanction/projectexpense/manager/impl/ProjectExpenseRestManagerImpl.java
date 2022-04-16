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
import kr.co.kmac.pms.sanction.projectexpense.dao.ProjectExpenseRestDao;
import kr.co.kmac.pms.sanction.projectexpense.dao.ProjectExpenseTempForSanctionDao;
import kr.co.kmac.pms.sanction.projectexpense.data.ExpenseRealTimeResultDetailForSanction;
import kr.co.kmac.pms.sanction.projectexpense.form.ProjectExpenseForm;
import kr.co.kmac.pms.sanction.projectexpense.manager.ProjectExpenseRestManager;

public class ProjectExpenseRestManagerImpl implements ProjectExpenseRestManager {

	private ProjectExpenseRestDao projectExpenseRestDao;
	private ProjectExpenseTempForSanctionDao projectExpenseTempForSanctionDao;
	private WorklistManager worklistManager;

//	@Override
//	public List<ExpenseRealTimeResultDetailForSanction> addAssignDateInExpenseRestList(List<ExpenseRealTimeResultDetailForSanction> expenseList,
//			String isSanction) throws SanctionException {
//		return this.getProjectExpenseRestDao().addAssignDateInExpenseRestList(expenseList, isSanction);
//	}

	@Override
	public List<ExpenseRealTimeResultDetailForSanction> addSeqNumInExpenseRestList(List<ExpenseRealTimeResultDetailForSanction> expenseList)
			throws SanctionException {
		return this.getProjectExpenseRestDao().addSeqNumDateInExpenseRestList(expenseList);
	}

	@Override
	public List<ExpenseRealTimeResultDetailForSanction> getProjectExpenseRestTempDataList(String seq) throws SanctionException {
		return this.getProjectExpenseTempForSanctionDao().select(seq);
	}
	
	@Override
	public List<ExpenseRealTimeResultDetailForSanction> getProjectExpenseRestTempDataList(String year, String month) throws SanctionException {
		return this.getProjectExpenseTempForSanctionDao().select(year, month);
	}

	@Override
	public void executeARExpenseRestSanction(ProjectExpenseForm projectExpenseForm) throws SanctionException {
		List<ExpenseRealTimeResultDetailForSanction> expenseRealTimeResultDetailForSanctionList = ExpenseRealTimeResultDetailForSanction
				.valueOf(projectExpenseForm);
		this.getProjectExpenseTempForSanctionDao().delete(projectExpenseForm.getProjectExpenseSeq());
		for (ExpenseRealTimeResultDetailForSanction expenseRealTimeResultDetailForSanction : expenseRealTimeResultDetailForSanctionList) {
			this.getProjectExpenseTempForSanctionDao().insert(expenseRealTimeResultDetailForSanction);
		}

//		// 집계된 지도일지의 상태를 결재 진행중으로 변경
//		this.getProjectExpenseRestDao().updateExpenseRestPreport(projectExpenseForm);
		// 집계된 강사료의 상태를 결재 진행중으로 변경
		this.getProjectExpenseRestDao().updateExpenseRestTM(projectExpenseForm);// 팀장 확인 ctmCheckYN = Y, approvalYN = Y
	}

	@Override
	public void executeKMExpenseRestSanction(ProjectExpenseForm projectExpenseForm) throws SanctionException {
		List<ExpenseRealTimeResultDetailForSanction> expenseRealTimeResultDetailForSanctionList = ExpenseRealTimeResultDetailForSanction
				.valueOf(projectExpenseForm);
		this.getProjectExpenseTempForSanctionDao().delete(projectExpenseForm.getProjectExpenseSeq());
		for (ExpenseRealTimeResultDetailForSanction expenseRealTimeResultDetailForSanction : expenseRealTimeResultDetailForSanctionList) {
			this.getProjectExpenseTempForSanctionDao().insert(expenseRealTimeResultDetailForSanction);
		}

		// 집계된 지도일지의 상태를 결재 진행중으로 변경
//		this.getProjectExpenseRestDao().updateExpenseRestPreport(projectExpenseForm);
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
	public void completeARExpenseRestSanction(ProjectExpenseForm projectExpenseForm) throws SanctionException {
		this.getProjectExpenseRestDao().updateExpenseRestTMFinish(projectExpenseForm);//강사료 키인 테이블에 강사료 집행이 마무리 되었다고 상태값 변경
		this.getProjectExpenseRestDao().insertBillAndUpdateBillSend(projectExpenseForm);// 회계 강사료 전송 및 강사료 회계 전송 업데이트
	}

	@Override
	public void completeKMExpenseRestSanction(ProjectExpenseForm projectExpenseForm) throws SanctionException {
		this.getProjectExpenseRestDao().insertBillAndUpdateBillSendC(projectExpenseForm);// 회계 강사료 전송 및 강사료 회계 전송 업데이트
	}

	@Override
	public void deleteARExpenseRestSanction(ProjectExpenseForm projectExpenseForm) throws SanctionException {
		this.getProjectExpenseTempForSanctionDao().delete(String.valueOf(projectExpenseForm.getProjectExpenseSeq()));
		this.getProjectExpenseRestDao().updateExpenseRestTMForDelete(projectExpenseForm);
	}

	@Override
	public void deleteKMExpenseRestSanction(ProjectExpenseForm projectExpenseForm) throws SanctionException {
		this.getProjectExpenseTempForSanctionDao().delete(String.valueOf(projectExpenseForm.getProjectExpenseSeq()));
	}

	@Override
	public void rejectARExpenseRestSanction(ProjectExpenseForm projectExpenseForm) throws SanctionException {
		this.getProjectExpenseRestDao().updateExpenseRestTMForReject(projectExpenseForm);
	}


	// ///////////////////////////////////////////

	public ProjectExpenseTempForSanctionDao getProjectExpenseTempForSanctionDao() {

		return projectExpenseTempForSanctionDao;
	}

	public ProjectExpenseRestDao getProjectExpenseRestDao() {
		return projectExpenseRestDao;
	}

	public void setProjectExpenseRestDao(ProjectExpenseRestDao projectExpenseRestDao) {
		this.projectExpenseRestDao = projectExpenseRestDao;
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
