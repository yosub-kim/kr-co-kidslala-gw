/*
 * $Id: MemberChangeDao.java,v 1.2 2019/02/09 05:47:24 cvs Exp $
 * created by    : CHO DAE HYON
 * creation-date : 2006. 3. 16.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.sanction.projectchange.dao;

import java.util.List;

import kr.co.kmac.pms.sanction.projectchange.data.AddMemberChange;
import kr.co.kmac.pms.sanction.projectchange.data.AddMemberChangeArray;
import kr.co.kmac.pms.sanction.projectchange.data.RunningMemberChange;
import kr.co.kmac.pms.sanction.projectchange.data.RunningMemberChangeArray;

import org.springframework.dao.DataAccessException;

/**
 * TODO 클래스 설명
 * 
 * @author CHO DAE HYON
 * @version $Id: MemberChangeDao.java,v 1.2 2019/02/09 05:47:24 cvs Exp $
 */
public interface MemberChangeDao {

	public void insertRunningMember(RunningMemberChangeArray runningMemberChange) throws DataAccessException;

	public void insertAddMember(AddMemberChangeArray addMemberChange) throws DataAccessException;

	public void deleteRunningMember(String projectCode, String memberChangeSeq) throws DataAccessException;

	public void deleteAddMember(String projectCode, String memberChangeSeq) throws DataAccessException;

	public List<AddMemberChange> selectAddMemberChange(String projectCode, String memberChangeSeq) throws DataAccessException;

	public List<RunningMemberChange> selectRunningMemberChange(String projectCode, String memberChangeSeq) throws DataAccessException;

	public void finishMemberChangeDao(RunningMemberChangeArray runningMemberChange, AddMemberChangeArray addMemberChange) throws DataAccessException;
	
	public void updateMemberMMPlan(RunningMemberChangeArray runningMemberChangeArray) throws DataAccessException; 
}
