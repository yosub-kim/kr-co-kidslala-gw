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
public interface ProjectExpenseRestManager {

//	public List<ExpenseRealTimeResultDetailForSanction> addAssignDateInExpenseRestList(List<ExpenseRealTimeResultDetailForSanction> expenseList,
//			String isSanction) throws SanctionException;

	public List<ExpenseRealTimeResultDetailForSanction> addSeqNumInExpenseRestList(List<ExpenseRealTimeResultDetailForSanction> expenseList)
			throws SanctionException;

	public List<ExpenseRealTimeResultDetailForSanction> getProjectExpenseRestTempDataList(String seq) throws SanctionException;
	
	public List<ExpenseRealTimeResultDetailForSanction> getProjectExpenseRestTempDataList(String year, String month) throws SanctionException;

	public void deleteARExpenseRestSanction(ProjectExpenseForm projectExpenseForm) throws SanctionException;

	public void deleteKMExpenseRestSanction(ProjectExpenseForm projectExpenseForm) throws SanctionException;

	public void rejectARExpenseRestSanction(ProjectExpenseForm projectExpenseForm) throws SanctionException;

	public void executeARExpenseRestSanction(ProjectExpenseForm projectExpenseForm) throws SanctionException;

	public void executeKMExpenseRestSanction(ProjectExpenseForm projectExpenseForm) throws SanctionException;

	public void assignFinishTask(ProjectExpenseForm projectExpenseForm) throws SanctionException;

	public void completeARExpenseRestSanction(ProjectExpenseForm projectExpenseForm) throws SanctionException;

	public void completeKMExpenseRestSanction(ProjectExpenseForm projectExpenseForm) throws SanctionException;

}
