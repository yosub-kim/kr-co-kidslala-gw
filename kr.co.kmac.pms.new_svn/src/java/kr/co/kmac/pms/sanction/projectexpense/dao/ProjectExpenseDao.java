package kr.co.kmac.pms.sanction.projectexpense.dao;

import java.util.List;

import kr.co.kmac.pms.sanction.projectexpense.data.ExpenseRealTimeResultDetailForSanction;
import kr.co.kmac.pms.sanction.projectexpense.form.ProjectExpenseForm;

import org.springframework.dao.DataAccessException;

public interface ProjectExpenseDao {

	public List<ExpenseRealTimeResultDetailForSanction> addSeqNumDateInExpenseList(List<ExpenseRealTimeResultDetailForSanction> expenseList)
			throws DataAccessException;
	public List<ExpenseRealTimeResultDetailForSanction> addAssignDateInExpenseList(List<ExpenseRealTimeResultDetailForSanction> expenseList,
			String isSanction) throws DataAccessException;

	public void updateExpensePreport(ProjectExpenseForm projectExpenseForm) throws DataAccessException;
	public void updateExpensePreportForRollBack(ProjectExpenseForm projectExpenseForm) throws DataAccessException;
	public void updateExpensePreportForDelete(ProjectExpenseForm projectExpenseForm) throws DataAccessException;
	public void updateExpensePreportFinish(ProjectExpenseForm projectExpenseForm) throws DataAccessException;
	public void updateProjectReportHoldingStateC(ProjectExpenseForm projectExpenseForm) throws DataAccessException;

	public void updateExpenseTM(ProjectExpenseForm projectExpenseForm) throws DataAccessException;
	public void updateExpenseTMFinish(ProjectExpenseForm projectExpenseForm) throws DataAccessException;
	public void updateExpenseTMForRollBack(ProjectExpenseForm projectExpenseForm) throws DataAccessException;
	public void updateExpenseTMForDelete(ProjectExpenseForm projectExpenseForm) throws DataAccessException;
	
	public void insertBillAndUpdateBillSend(ProjectExpenseForm projectExpenseForm) throws DataAccessException;
	public void insertBillAndUpdateBillSendC(ProjectExpenseForm projectExpenseForm) throws DataAccessException;

}
