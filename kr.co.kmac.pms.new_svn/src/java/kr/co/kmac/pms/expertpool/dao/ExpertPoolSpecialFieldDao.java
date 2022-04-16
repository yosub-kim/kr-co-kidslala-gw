/*
 * $Id: ExpertPoolSpecialFieldDao.java,v 1.3 2014/04/09 05:09:43 cvs Exp $
 * created by : jiwoongLee creation-date : 2006. 1. 17
 * ========================================================= Copyright (c) 2006
 * Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.expertpool.dao;

import java.util.List;

import kr.co.kmac.pms.common.org.data.Group;
import kr.co.kmac.pms.expertpool.data.ExpertPoolSpecialField;

import org.springframework.dao.DataAccessException;

/**
 * 전문분야 Dao Interface
 * @author jiwoongLee
 * @version $Id: ExpertPoolSpecialFieldDao.java,v 1.3 2014/04/09 05:09:43 cvs Exp $
 */
public interface ExpertPoolSpecialFieldDao {

	public void create(ExpertPoolSpecialField expertPoolSpecialField) throws DataAccessException;

	public void remove(String ssn, String specialId) throws DataAccessException;

	public void remove(String ssn) throws DataAccessException;

	public List<ExpertPoolSpecialField> findSpecialField(String deptId) throws DataAccessException;

	public List<ExpertPoolSpecialField> getSpecialFieldList(String ssn) throws DataAccessException;

	public boolean isExist(String ssn, String specialId) throws DataAccessException;

	public List<Group> getDeptLIst() throws DataAccessException;

	public List<Group> getExpertPoolFunctionLIst() throws DataAccessException;
}
