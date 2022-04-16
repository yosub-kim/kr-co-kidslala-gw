/*
 * $Id: CostOverService.java,v 1.1 2009/09/19 11:15:37 cvs3 Exp $
 * created by    : 안성호
 * creation-date : 2006. 7. 11.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.service.scheduler.batch;

import java.util.List;

import kr.co.kmac.pms.service.exception.ServiceException;
import kr.co.kmac.pms.service.scheduler.data.CostOverData;
import kr.co.kmac.pms.service.scheduler.manager.CostOverManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CostOverService {
	private static final Log logger = LogFactory.getLog(CostOverService.class);
	private CostOverManager costOverManager;

	public CostOverManager getCostOverManager() {
		return costOverManager;
	}

	public void setCostOverManager(CostOverManager costOverManager) {
		this.costOverManager = costOverManager;
	}

	public void costOverWrite() {
		try {
			// get list of cost over project from Accounting System Database
			List<CostOverData> list = this.getCostOverManager().costOverList();
			List<CostOverData> payList = this.getCostOverManager().payCostOverList();
			List<CostOverData> etcList = this.getCostOverManager().etcCostOverList();

			// reset the information of cost over in Project master table
			this.getCostOverManager().resetUpdateProjectCostOver();

			// update the information of cost over in Project master table
			if (list != null && list.size() > 0) {
				for (CostOverData costOverData : list) {
					this.getCostOverManager().updateProjectCostOver(costOverData.getProjId());
				}
			}
			// Job Date: 2007-10-16 Author: yhyim Description: 인건비 초과 여부 처리
			if (payList != null && payList.size() > 0) {
				for (CostOverData costOverData : payList) {
					this.getCostOverManager().updateProjectPayCostOver(costOverData.getProjId());
				}
			}
			// Job Date: 2007-10-16 Author: yhyim Description: 제경비 초과 여부 처리
			if (etcList != null && etcList.size() > 0) {
				for (CostOverData costOverData : etcList) {
					this.getCostOverManager().updateProjectEtcCostOver(costOverData.getProjId());
				}
			}
			logger.info("##  CostOverService was executed... ");
		} catch (ServiceException e) {
			logger.info("##  CostOverService was failed... ");
			logger.error(e.getMessage(), e);
		}
	}
}
