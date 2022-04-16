/*
 * $Id: CostOverManagerImpl.java,v 1.1 2009/09/19 11:15:26 cvs3 Exp $
 * created by    : ¾È¼ºÈ£
 * creation-date : 2006. 7. 11.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.service.scheduler.manager.impl;

import java.util.List;

import kr.co.kmac.pms.common.util.DateTime;
import kr.co.kmac.pms.common.util.StringUtil;
import kr.co.kmac.pms.service.exception.ServiceException;
import kr.co.kmac.pms.service.scheduler.dao.CostOverDao;
import kr.co.kmac.pms.service.scheduler.data.CostOverData;
import kr.co.kmac.pms.service.scheduler.manager.CostOverManager;

public class CostOverManagerImpl implements CostOverManager {

	private CostOverDao costOverDao;

	public CostOverDao getCostOverDao() {
		return costOverDao;
	}

	public void setCostOverDao(CostOverDao costOverDao) {
		this.costOverDao = costOverDao;
	}

	@Override
	public List<CostOverData> costOverList(String start_date, String end_date) throws ServiceException {
		return costOverDao.costOverList(start_date, end_date);
	}

	@Override
	public List<CostOverData> costOverList() throws ServiceException {
		String year = DateTime.getYear();
		String month = DateTime.getMonth();
		String day = DateTime.getDay();

		String startYear = (Integer.parseInt(year) - 1) + "";
		if ("2".equals(month) && "29".equals(day)) {
			if (StringUtil.isYunYear(Integer.parseInt(year) - 1)) {
				day = "28";
			}
		}
		return costOverDao.costOverList(startYear + month + day, DateTime.getDateString());
	}

	@Override
	public void updateProjectCostOver(String projectCode) throws ServiceException {
		costOverDao.updateProjectCostOver(projectCode);
	}

	// Job Date: 2007-07-27 Author: yhyim Description: New method for reset Project cost over
	@Override
	public void resetUpdateProjectCostOver() throws ServiceException {
		costOverDao.resetUpdateProjectCostOver();
	}

	public List<CostOverData> etcCostOverList(String start_date, String end_date) throws ServiceException {
		return costOverDao.etcCostOverList(start_date, end_date);
	}

	@Override
	public List<CostOverData> etcCostOverList() throws ServiceException {
		String year = DateTime.getYear();
		String month = DateTime.getMonth();
		String day = DateTime.getDay();

		String startYear = (Integer.parseInt(year) - 1) + "";
		if ("2".equals(month) && "29".equals(day)) {
			if (StringUtil.isYunYear(Integer.parseInt(year) - 1)) {
				day = "28";
			}
		}
		return costOverDao.etcCostOverList(startYear + month + day, DateTime.getDateString());
	}

	public void updateProjectEtcCostOver(String projectCode) throws ServiceException {
		costOverDao.updateProjectEtcCostOver(projectCode);
	}

	public List<CostOverData> payCostOverList(String start_date, String end_date) throws ServiceException {
		return costOverDao.payCostOverList(start_date, end_date);
	}

	public List<CostOverData> payCostOverList() throws ServiceException {
		String year = DateTime.getYear();
		String month = DateTime.getMonth();
		String day = DateTime.getDay();

		String startYear = (Integer.parseInt(year) - 1) + "";
		if ("2".equals(month) && "29".equals(day)) {
			if (StringUtil.isYunYear(Integer.parseInt(year) - 1)) {
				day = "28";
			}
		}
		return costOverDao.payCostOverList(startYear + month + day, DateTime.getDateString());
	}

	public void updateProjectPayCostOver(String projectCode) throws ServiceException {
		costOverDao.updateProjectPayCostOver(projectCode);
	}

}
