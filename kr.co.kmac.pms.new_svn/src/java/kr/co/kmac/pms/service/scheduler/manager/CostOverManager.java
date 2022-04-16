/*
 * $Id: CostOverManager.java,v 1.1 2009/09/19 11:15:43 cvs3 Exp $
 * created by    : ¾È¼ºÈ£
 * creation-date : 2006. 7. 11.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.service.scheduler.manager;

import java.util.List;

import kr.co.kmac.pms.service.exception.ServiceException;
import kr.co.kmac.pms.service.scheduler.data.CostOverData;

public interface CostOverManager {

	public List<CostOverData> costOverList(String start_date, String end_date) throws ServiceException;

	public List<CostOverData> costOverList() throws ServiceException;

	public void updateProjectCostOver(String projectCode) throws ServiceException;

	// Job Date: 2007-07-26 Author: yhyim Description: New Method for reset project cost over
	public void resetUpdateProjectCostOver() throws ServiceException;

	// Job Date: 2007-10-15 Author: yhyim Description:
	public List<CostOverData> payCostOverList(String start_date, String end_date) throws ServiceException;

	public List<CostOverData> payCostOverList() throws ServiceException;

	public void updateProjectPayCostOver(String projectCode) throws ServiceException;

	// Job Date: 2007-10-15 Author: yhyim Description:
	public List<CostOverData> etcCostOverList(String start_date, String end_date) throws ServiceException;

	public List<CostOverData> etcCostOverList() throws ServiceException;

	public void updateProjectEtcCostOver(String projectCode) throws ServiceException;

}
