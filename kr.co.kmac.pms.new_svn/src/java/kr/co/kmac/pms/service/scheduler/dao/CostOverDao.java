/*
 * $Id: CostOverDao.java,v 1.1 2009/09/19 11:15:40 cvs3 Exp $
 * created by    : ¾È¼ºÈ£
 * creation-date : 2006. 7. 11.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.service.scheduler.dao;

import java.util.List;

import kr.co.kmac.pms.service.scheduler.data.CostOverData;

import org.springframework.dao.DataAccessException;

public interface CostOverDao {
	public List<CostOverData> costOverList(String start_date, String end_date) throws DataAccessException;

	public void updateProjectCostOver(String projectCode) throws DataAccessException;

	// Job Date: 2007-07-25 Author: yhyim Description: New method for reset Project cost over
	public void resetUpdateProjectCostOver() throws DataAccessException;

	// Job Date: 2007-10-15 Author: yhyim
	public List<CostOverData> payCostOverList(String start_date, String end_date) throws DataAccessException;

	public void updateProjectPayCostOver(String projectCode) throws DataAccessException;

	// Job Date: 2007-10-15 Author: yhyim
	public List<CostOverData> etcCostOverList(String start_date, String end_date) throws DataAccessException;

	public void updateProjectEtcCostOver(String projectCode) throws DataAccessException;
}
