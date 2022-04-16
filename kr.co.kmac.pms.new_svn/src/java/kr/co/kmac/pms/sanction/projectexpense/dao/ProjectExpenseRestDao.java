package kr.co.kmac.pms.sanction.projectexpense.dao;

import java.util.List;

import kr.co.kmac.pms.sanction.projectexpense.data.ExpenseRealTimeResultDetailForSanction;
import kr.co.kmac.pms.sanction.projectexpense.form.ProjectExpenseForm;

import org.springframework.dao.DataAccessException;

public interface ProjectExpenseRestDao {

	public List<ExpenseRealTimeResultDetailForSanction> addSeqNumDateInExpenseRestList(List<ExpenseRealTimeResultDetailForSanction> expenseList)
			throws DataAccessException;
	
	public void updateExpenseRestTM(ProjectExpenseForm projectExpenseForm) throws DataAccessException;
	public void updateExpenseRestTMFinish(ProjectExpenseForm projectExpenseForm) throws DataAccessException;
	public void updateExpenseRestTMForReject(ProjectExpenseForm projectExpenseForm) throws DataAccessException;
	public void updateExpenseRestTMForDelete(ProjectExpenseForm projectExpenseForm) throws DataAccessException;

	public void insertBillAndUpdateBillSend(ProjectExpenseForm projectExpenseForm) throws DataAccessException;
	public void insertBillAndUpdateBillSendC(ProjectExpenseForm projectExpenseForm) throws DataAccessException;

}
