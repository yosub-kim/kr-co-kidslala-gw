/*
 * $Id: ExpenseErpScheduleService.java,v 1.4 2011/10/25 13:48:55 cvs Exp $
 * created by    : jiwoongLee
 * creation-date : 2006. 5. 8.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.service.scheduler.batch;

import java.util.List;

import kr.co.kmac.pms.service.scheduler.dao.ExpenseErpDataServiceDao;
import kr.co.kmac.pms.service.scheduler.data.ExpenseErpData;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * TODO 클래스 설명
 * 
 * @author jiwoongLee
 * @version $Id: ExpenseErpScheduleService.java,v 1.4 2011/10/25 13:48:55 cvs Exp $
 */
public class ExpenseErpScheduleService {
	private static final Log log = LogFactory.getLog(ExpenseErpScheduleService.class);
	private ExpenseErpDataServiceDao expenseErpDataServiceDao;

	public void updateCostRelatedField() {

		try {
			List<ExpenseErpData> budgetList = getExpenseErpDataServiceDao().getExpenseDataFromErpFullData();
			getExpenseErpDataServiceDao().setExpenseDataToPms(budgetList);
			log.info("회계 데이터를 갱신하였습니다.");
		
			List<String> list = getExpenseErpDataServiceDao().getExpenseDataFromErpForFlagUpdate();
			this.getExpenseErpDataServiceDao().updateProjectReportExceedStateInit();
			this.getExpenseErpDataServiceDao().updateProjectTeachingFeeExceedStateInit();
			this.getExpenseErpDataServiceDao().updateProjectTeachingRestFeeExceedStateInit();
			
			if (list != null && list.size() > 0) {
				for (String projectCode : list) {
					this.getExpenseErpDataServiceDao().updateProjectReportExceedState(projectCode);
					this.getExpenseErpDataServiceDao().updateProjectTeachingFeeExceedState(projectCode);
					this.getExpenseErpDataServiceDao().updateProjectTeachingRestFeeExceedState(projectCode);
				}
			}
			log.info("인건비 예산초과 데이터를 갱신하였습니다.");
		} catch (Exception e) {
			log.error("Exception occured during execut ErpScheduleService ....");
		}
		log.info("## ExpenseErpScheduleService was executed...");
	}

	/**
	 * @return Returns the expenseErpDataServiceDao.
	 */
	public ExpenseErpDataServiceDao getExpenseErpDataServiceDao() {
		return expenseErpDataServiceDao;
	}

	/**
	 * @param expenseErpDataServiceDao The expenseErpDataServiceDao to set.
	 */
	public void setExpenseErpDataServiceDao(ExpenseErpDataServiceDao expenseErpDataServiceDao) {
		this.expenseErpDataServiceDao = expenseErpDataServiceDao;
	}

}
