/*
 * $Id: ProjectExpenseManager.java,v 1.3 2015/04/24 01:15:51 cvs Exp $
 * created by    : CHO DAE HYON
 * creation-date : 2006. 4. 21.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.sanction.projectexpense.manager;

import java.util.List;

import kr.co.kmac.pms.sanction.exception.SanctionException;
import kr.co.kmac.pms.sanction.projectexpense.data.ExpenseRealTimeResultDetailForSanction;
import kr.co.kmac.pms.sanction.projectexpense.form.ProjectExpenseForm;

/**
 * TODO 클래스 설명
 * 
 * @author CHO DAE HYON
 * @version $Id: ProjectExpenseManager.java,v 1.3 2015/04/24 01:15:51 cvs Exp $
 */
public interface ProjectExpenseManager {

	public List<ExpenseRealTimeResultDetailForSanction> addAssignDateInExpenseList(List<ExpenseRealTimeResultDetailForSanction> expenseList,
			String isSanction) throws SanctionException;

	public List<ExpenseRealTimeResultDetailForSanction> addSeqNumInExpenseList(List<ExpenseRealTimeResultDetailForSanction> expenseList)
			throws SanctionException;

	public List<ExpenseRealTimeResultDetailForSanction> getProjectExpenseTempDataList(String seq) throws SanctionException;
	
	public List<ExpenseRealTimeResultDetailForSanction> getProjectExpenseTempDataList(String year, String month) throws SanctionException;

	public void deleteARExpenseSanction(ProjectExpenseForm projectExpenseForm) throws SanctionException;

	public void deleteKMExpenseSanction(ProjectExpenseForm projectExpenseForm) throws SanctionException;

	public void rejectARExpenseSanction(ProjectExpenseForm projectExpenseForm) throws SanctionException;

	public void rejectKMExpenseSanction(ProjectExpenseForm projectExpenseForm) throws SanctionException;

	public void executeARExpenseSanction(ProjectExpenseForm projectExpenseForm) throws SanctionException;

	public void executeKMExpenseSanction(ProjectExpenseForm projectExpenseForm) throws SanctionException;

	public void assignFinishTask(ProjectExpenseForm projectExpenseForm) throws SanctionException;

	public void completeARExpenseSanction(ProjectExpenseForm projectExpenseForm) throws SanctionException;

	public void completeKMExpenseSanction(ProjectExpenseForm projectExpenseForm) throws SanctionException;

}
