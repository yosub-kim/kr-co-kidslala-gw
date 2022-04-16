/*
 * $Id: ScheduleChangeDao.java,v 1.1 2009/09/19 11:15:42 cvs3 Exp $
 * created by    : CHO DAE HYON
 * creation-date : 2006. 2. 27.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.sanction.projectchange.dao;

import kr.co.kmac.pms.sanction.projectchange.data.ScheduleChange;

import org.springframework.dao.DataAccessException;

/**
 * TODO 클래스 설명
 * 
 * @author CHO DAE HYON
 * @version $Id: ScheduleChangeDao.java,v 1.1 2009/09/19 11:15:42 cvs3 Exp $
 */
public interface ScheduleChangeDao {

	public void insertScheduleChange(ScheduleChange scheduleChange) throws DataAccessException;

	public void updateScheduleChange(ScheduleChange scheduleChange) throws DataAccessException;

	public void deleteScheduleChange(String projectCode, String scheduleChangeSeq) throws DataAccessException;

	public ScheduleChange selectScheduleChange(String projectCode, String scheduleChangeSeq) throws DataAccessException;

	public void finishScheduleChange(ScheduleChange scheduleChange) throws DataAccessException;

}
