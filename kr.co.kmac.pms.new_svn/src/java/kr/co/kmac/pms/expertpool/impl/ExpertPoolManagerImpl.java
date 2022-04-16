/*
 * $Id: ExpertPoolManagerImpl.java,v 1.30 2019/03/20 08:12:05 cvs Exp $ created
 * by : Administrator creation-date : 2006. 1. 17
 * ========================================================= Copyright (c) 2006
 * Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.expertpool.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import kr.co.kmac.pms.common.org.data.Group;
import kr.co.kmac.pms.expertpool.dao.ExpertPoolCareerHstDao;
import kr.co.kmac.pms.expertpool.dao.ExpertPoolDao;
import kr.co.kmac.pms.expertpool.dao.ExpertPoolSchoolHstDao;
import kr.co.kmac.pms.expertpool.dao.ExpertPoolSpecialFieldDao;
import kr.co.kmac.pms.expertpool.data.BankAccount;
import kr.co.kmac.pms.expertpool.data.ExpertPool;
import kr.co.kmac.pms.expertpool.data.ExpertPoolCareerHst;
import kr.co.kmac.pms.expertpool.data.ExpertPoolSchoolHst;
import kr.co.kmac.pms.expertpool.data.ExpertPoolSpecialField;
import kr.co.kmac.pms.expertpool.exception.ExpertPoolException;
import kr.co.kmac.pms.expertpool.manager.ExpertPoolManager;

import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.dao.DataAccessException;

/**
 * TODO 클래스 설명
 * 
 * @author Administrator
 * @version $Id: ExpertPoolManagerImpl.java,v 1.30 2019/03/20 08:12:05 cvs Exp $
 */
public class ExpertPoolManagerImpl implements ExpertPoolManager {

	private ExpertPoolCareerHstDao expertPoolCareerHstDao;
	private ExpertPoolDao expertPoolDao;
	private ExpertPoolSchoolHstDao expertPoolSchoolHstDao;
	private ExpertPoolSpecialFieldDao expertPoolSpecialFieldDao;

	public ExpertPoolSpecialFieldDao getExpertPoolSpecialFieldDao() {
		return expertPoolSpecialFieldDao;
	}

	public void setExpertPoolSpecialFieldDao(ExpertPoolSpecialFieldDao expertPoolSpecialFieldDao) {
		this.expertPoolSpecialFieldDao = expertPoolSpecialFieldDao;
	}

	/**
	 * @return Returns the expertPoolSchoolHstDao.
	 */
	public ExpertPoolSchoolHstDao getExpertPoolSchoolHstDao() {
		return this.expertPoolSchoolHstDao;
	}

	/**
	 * @param expertPoolSchoolHstDao The expertPoolSchoolHstDao to set.
	 */
	public void setExpertPoolSchoolHstDao(ExpertPoolSchoolHstDao expertPoolSchoolHstDao) {
		this.expertPoolSchoolHstDao = expertPoolSchoolHstDao;
	}

	/**
	 * @return Returns the expertPoolDao.
	 */
	public ExpertPoolDao getExpertPoolDao() {
		return this.expertPoolDao;
	}

	/**
	 * @param expertPoolDao The expertPoolDao to set.
	 */
	public void setExpertPoolDao(ExpertPoolDao expertPoolDao) {
		this.expertPoolDao = expertPoolDao;
	}

	/**
	 * @return Returns the expertPoolCareerHstDao.
	 */
	public ExpertPoolCareerHstDao getExpertPoolCareerHstDao() {
		return this.expertPoolCareerHstDao;
	}

	/**
	 * @param expertPoolCareerHstDao The expertPoolCareerHstDao to set.
	 */
	public void setExpertPoolCareerHstDao(ExpertPoolCareerHstDao expertPoolCareerHstDao) {
		this.expertPoolCareerHstDao = expertPoolCareerHstDao;
	}

	@Override
	public String getSsn(String jobClass) throws ExpertPoolException {
		// 신규 ssn 생성
		String ssn = this.getExpertPoolDao().getSsn(jobClass);
		// 중복된 ssn이 있는지 확인
		if (getExpertPoolDao().isExist(ssn)) {
			return this.getExpertPoolDao().getSsn2(jobClass);
		} else {
			return ssn;
		}
	}

	public void create(ExpertPool expertPool) throws ExpertPoolException {
		getExpertPoolDao().create(expertPool);
		for (int i = 0; i < expertPool.getExpertPoolCareerHst().size(); i++) {
			ExpertPoolCareerHst t1 = (ExpertPoolCareerHst) expertPool.getExpertPoolCareerHst().get(i);
			t1.setSsn(expertPool.getSsn());
			getExpertPoolCareerHstDao().create(t1);
		}
		for (int i = 0; i < expertPool.getExpertPoolSchoolHst().size(); i++) {
			ExpertPoolSchoolHst t2 = (ExpertPoolSchoolHst) expertPool.getExpertPoolSchoolHst().get(i);
			t2.setSsn(expertPool.getSsn());
			getExpertPoolSchoolHstDao().create(t2);
		}
		for (int i = 0; i < expertPool.getExpertPoolSpecialFields().size(); i++) {
			ExpertPoolSpecialField t3 = (ExpertPoolSpecialField) expertPool.getExpertPoolSpecialFields().get(i);
			t3.setSsn(expertPool.getSsn());
			getExpertPoolSpecialFieldDao().create(t3);
		}
	}

	public void createHistory(ExpertPool expertPool) throws ExpertPoolException {
		getExpertPoolDao().createHistory(expertPool);
	}

	@Override
	public void createSanctionLine(ExpertPool expertPool) throws ExpertPoolException {
		getExpertPoolDao().storeSanctionLine(expertPool);
	}
	
	public void createAccountHistory(String accessType, ExpertPool expertPool) throws ExpertPoolException {
		getExpertPoolDao().createAccountHistory(accessType, expertPool);
	}

	@Override
	public void updateRestrictUserState(String ssn, String state) throws ExpertPoolException {
		String absence = "N";
		if (state.equals("Y"))
			absence = "Y"; // 투입 제한을 해지 할 경우 자동으로 투입 제한 체크 값을 초기화 함
		getExpertPoolDao().updateRestrictUserState(ssn, state, absence);
	}
	
	@Override
	public void updateRestrictContents(String ssn, String state, String restirctContents) throws ExpertPoolException {
		String absence = "N";
		if (state.equals("Y"))
			absence = "Y"; // 투입 제한을 해지 할 경우 자동으로 투입 제한 체크 값을 초기화 함
		getExpertPoolDao().updateRestrictContents(ssn, state, absence, restirctContents);
	}

	@Override
	public void updateReadyUserState(String ssn, String state) throws ExpertPoolException {
		String absence = "N";
		getExpertPoolDao().updateRestrictUserState(ssn, state, absence);
	}

	@Override
	public void updateMyinfo(ExpertPool expertPool, String withPassword) throws ExpertPoolException {
		if (withPassword.equals("Y")) {
			getExpertPoolDao().storeWithPwd(expertPool);
		} else {
			getExpertPoolDao().store(expertPool);
		}
	}
	
	@Override
	public void updateEduSystemAccount(ExpertPool expertPool) throws ExpertPoolException {
		getExpertPoolDao().updateEduSystemAccount(expertPool);
	}

	@Override
	public void store(ExpertPool expertPool) throws ExpertPoolException {
		getExpertPoolDao().store(expertPool);

		// 학력과 경력 삭제하고 다시 저장하는것으로 변경.
		getExpertPoolCareerHstDao().remove(expertPool.getSsn());
		getExpertPoolSchoolHstDao().remove(expertPool.getSsn());
		getExpertPoolSpecialFieldDao().remove(expertPool.getSsn());

		for (int i = 0; i < expertPool.getExpertPoolCareerHst().size(); i++) {
			getExpertPoolCareerHstDao().create((ExpertPoolCareerHst) expertPool.getExpertPoolCareerHst().get(i));
		}
		for (int i = 0; i < expertPool.getExpertPoolSchoolHst().size(); i++) {
			getExpertPoolSchoolHstDao().create((ExpertPoolSchoolHst) expertPool.getExpertPoolSchoolHst().get(i));
		}
		for (int i = 0; i < expertPool.getExpertPoolSpecialFields().size(); i++) {
			getExpertPoolSpecialFieldDao().create((ExpertPoolSpecialField) expertPool.getExpertPoolSpecialFields().get(i));
		}
	}

	public void storeHistory(ExpertPool expertPool) throws ExpertPoolException {
		getExpertPoolDao().storeHistory(expertPool);
	}

	public void storeSanctionLine(ExpertPool expertPool) throws ExpertPoolException {
		getExpertPoolDao().storeSanctionLine(expertPool);
	}
	
	public ExpertPool retrieve(String ssn) throws ExpertPoolException {
		ExpertPool expertPool = getExpertPoolDao().retrieve(ssn);
		return expertPool;
	}
	
	public ExpertPool retrieve2(String fileId) throws ExpertPoolException {
		ExpertPool expertPool = getExpertPoolDao().retrieve2(fileId);
		return expertPool;
	}
	
	public ExpertPool retrieve3(String fileId) throws ExpertPoolException {
		ExpertPool expertPool = getExpertPoolDao().retrieve3(fileId);
		return expertPool;
	}

	// Job Date: 2009-12-24 Author: yhyim Description: impl new virtual function
	public ExpertPool retrieve(String dept, String companyPosition) throws ExpertPoolException {
		ExpertPool expertPool = getExpertPoolDao().retrieve(dept, companyPosition);
		return expertPool;
	}

	public ExpertPool retrieveUserId(String userId) throws ExpertPoolException {
		ExpertPool expertPool = getExpertPoolDao().retrieveUserId(userId);
		return expertPool;
	}

	public ExpertPool retrieveOfficer(String deptCode) throws ExpertPoolException {
		ExpertPool expertPool = getExpertPoolDao().retrieveOfficer(deptCode);
		return expertPool;
	}
	
	public ExpertPool retrieveOfficer2(String deptCode) throws ExpertPoolException {
		ExpertPool expertPool = getExpertPoolDao().retrieveOfficer2(deptCode);
		return expertPool;
	}
	
	@Override
	public BankAccount retrieveBankAccount(ExpertPool expertPool) throws ExpertPoolException {
		BankAccount bankAccount = null;
		try {
			if (expertPool.getJobClass().equals("A")) {
				bankAccount = getExpertPoolDao().retrieveBankAccountKMAC(expertPool.getEmplNo());
			} else {
				String uid = expertPool.getUid().substring(0, 6) + "-" + expertPool.getUid().substring(6, 13);
				bankAccount = getExpertPoolDao().retrieveBankAccountNonKMAC(uid);
			}
		} catch(Exception e) {
		}
		
		return bankAccount;
	}

	public List findAll() throws ExpertPoolException {
		return getExpertPoolDao().findAll();
	}

	@Override
	public List<ExpertPool> findAsName(String name) throws ExpertPoolException {
		return getExpertPoolDao().findAsName(name);
	}

	@Override
	public List<ExpertPool> findAsUid(String uid) throws ExpertPoolException {
		return getExpertPoolDao().findAsUid(uid);
	}

	public List findGroup() throws ExpertPoolException {
		return getExpertPoolDao().findGroup();
	}

	public List findBUGroup() throws ExpertPoolException {
		return getExpertPoolDao().findBUGroup();
	}

	public List findPUGroup() throws ExpertPoolException {
		return getExpertPoolDao().findPUGroup();
	}

	public List findMenuRole() throws ExpertPoolException {
		return getExpertPoolDao().findMenuRole();
	}

	public void addUserMember(String parentId, String userId) throws ExpertPoolException {
		getExpertPoolDao().addUserMember(parentId, userId);
	}

	public void removeUserMember(String userId) throws ExpertPoolException {
		getExpertPoolDao().removeUserMember(userId);
	}

	public List findRole() throws ExpertPoolException {
		return getExpertPoolDao().findRole();
	}

	public List findBURole() throws ExpertPoolException {
		return getExpertPoolDao().findBURole();
	}

	public List findPURole() throws ExpertPoolException {
		return getExpertPoolDao().findPURole();
	}

	public void addUserRole(String userId, String roleId) throws ExpertPoolException {
		getExpertPoolDao().addUserRole(userId, roleId);
	}

	public void removeUserRole(String userId) throws ExpertPoolException {
		getExpertPoolDao().removeUserRole(userId);
	}

	public boolean isExist(String ssn) throws ExpertPoolException {
		return getExpertPoolDao().isExist(ssn);
	}

	public boolean isExistUserId(String userId) throws ExpertPoolException {
		return getExpertPoolDao().isExistUserId(userId);
	}

	public boolean isAbsence(String ssn) throws ExpertPoolException {
		return getExpertPoolDao().isAbsence(ssn);
	}

	/**
	 * 전문가 등록시 같은 주민번호가 있는지 확인
	 */
	public int checkSsn(String ssn) throws ExpertPoolException {
		return getExpertPoolDao().checkSsn(ssn);
	}

	/**
	 * UserId 등록시 같은 UserId가 있는지 확인
	 */
	public int checkUserId(String userId) throws ExpertPoolException {
		return getExpertPoolDao().checkUserId(userId);
	}

	public String getEmail(String ssn) throws ExpertPoolException {
		return getExpertPoolDao().getEmail(ssn);
	}

	public String getName(String ssn) throws ExpertPoolException {
		return getExpertPoolDao().getName(ssn);
	}
	
	public String getMenuRole(String ssn) throws ExpertPoolException {
		return getExpertPoolDao().getMenuRole(ssn);
	}

	public List<ListOrderedMap> getProjectInvolvedMembers(String projectCode) throws ExpertPoolException {
		return getExpertPoolDao().getProjectInvolvedMembers(projectCode);
	}

	public List getExpertListByRole(String roleNum, String extRoleNum) throws ExpertPoolException {
		return getExpertPoolDao().findByRole(roleNum, extRoleNum);
	}

	public List getExpertsBySearch(String indField, String keyWordCon, String keyWord) throws ExpertPoolException {
		return getExpertPoolDao().getExpertsBySearch(indField, keyWordCon, keyWord);
	}

	@Override
	public void createExpertPoolSpecialField(String ssn, List<ExpertPoolSpecialField> expertPoolSpecialFieldList) throws DataAccessException {
		expertPoolSpecialFieldDao.remove(ssn);
		for (ExpertPoolSpecialField expertPoolSpecialField : expertPoolSpecialFieldList) {
			try {
				expertPoolSpecialFieldDao.create(expertPoolSpecialField);
			} catch (Exception e) {
			}
		}
	}

	@Override
	public void removeExpertPoolSpecialField(String ssn, String specialId) throws DataAccessException {
		if (specialId != null && specialId.length() > 0) {
			expertPoolSpecialFieldDao.remove(ssn, specialId);
		} else {
			expertPoolSpecialFieldDao.remove(ssn);
		}
	}

	@Override
	public List<ExpertPoolSpecialField> retrieveExpertPoolSpecialField(String ssn) throws DataAccessException {
		return expertPoolSpecialFieldDao.getSpecialFieldList(ssn);
	}

	@Override
	public List<ExpertPoolSpecialField> getExpertPoolSpecialField(String deptId) throws DataAccessException {
		return expertPoolSpecialFieldDao.findSpecialField(deptId);
	}

	@Override
	public List<Group> getDeptLIst() throws ExpertPoolException {
		return expertPoolSpecialFieldDao.getDeptLIst();
	}

	@Override
	public List<Group> getExpertPoolFunctionLIst() throws ExpertPoolException {
		return expertPoolSpecialFieldDao.getExpertPoolFunctionLIst();
	}

	@Override
	public List<ExpertPool> getExpertPoolList(HashMap<String, String> param) throws ExpertPoolException {
		return this.getExpertPoolDao().getExpertPoolList(param);
	}

	@Override
	public List<ExpertPool> getExpertPoolExtList(HashMap<String, String> param) throws ExpertPoolException {
		return this.getExpertPoolDao().getExpertPoolExtList(param);
	}
	
	@Override
	public List<ExpertPool> getExpertPoolExtList2(HashMap<String, String> param) throws ExpertPoolException {
		return this.getExpertPoolDao().getExpertPoolExtList2(param);
	}

	@Override
	public List<ExpertPool> getProjectExpertPoolList(HashMap<String, String> param) throws ExpertPoolException {
		return this.getExpertPoolDao().getProjectExpertPoolList(param);
	}

	@Override
	public int getExpertPoolCnt(HashMap<String, String> param) throws ExpertPoolException {
		return this.getExpertPoolDao().getExpertPoolCnt(param);
	}

	@Override
	public int getExpertPoolExtCnt(HashMap<String, String> param) throws ExpertPoolException {
		return this.getExpertPoolDao().getExpertPoolExtCnt(param);
	}
	
	@Override
	public int getExpertPoolExtCnt2(HashMap<String, String> param) throws ExpertPoolException {
		return this.getExpertPoolDao().getExpertPoolExtCnt2(param);
	}

	@Override
	public int getProjectExpertPoolCnt(HashMap<String, String> param) throws ExpertPoolException {
		return this.getExpertPoolDao().getProjectExpertPoolCnt(param);
	}

	@Override
	public void storeDailyDownloadLimitInfo(String ssn) throws ExpertPoolException {
		getExpertPoolDao().storeDailyDownloadLimitInfo(ssn);
	}
	
	@Override
	public void storeHomeTaxValue(String value1, String value2, String value3, String value4, String value5, String value6) throws ExpertPoolException {
		getExpertPoolDao().storeHomeTaxValue(value1, value2, value3, value4, value5, value6);
	}
	
	@Override
	public void workTime(String ssn, String workType, String ip) throws ExpertPoolException {
		getExpertPoolDao().workTime(ssn, workType, ip);
	}

	@Override
	public boolean isDailyDownloadLimitUser(String ssn) throws ExpertPoolException {
		return this.getExpertPoolDao().isDailyDownloadLimitUser(ssn);
	}

	@Override
	public void updateDailyDownloadLimitState(String ssn, String isLimit) throws ExpertPoolException {
		// TODO Auto-generated method stub
		getExpertPoolDao().updateDailyDownloadLimitState(ssn, isLimit);
	}
	
	@Override
	public void insertBudgetCheck(String projectCode) throws ExpertPoolException {
		// TODO Auto-generated method stub
		getExpertPoolDao().insertBudgetCheck(projectCode);
	}

	/* (non-Javadoc)
	 * @see kr.co.kmac.pms.expertpool.manager.ExpertPoolManager#getLoginAttepmtCnt(java.lang.String)
	 */
	@Override
	public int getLoginAttepmtCnt(String ssn) throws ExpertPoolException {
		// TODO Auto-generated method stub
		return this.getExpertPoolDao().getLoginAttepmtCnt(ssn);
	}

	/* (non-Javadoc)
	 * @see kr.co.kmac.pms.expertpool.manager.ExpertPoolManager#increaseLoginAttepmtCnt(java.lang.String)
	 */
	@Override
	public int increaseLoginAttepmtCnt(String ssn) throws ExpertPoolException {
		// TODO Auto-generated method stub
		return this.getExpertPoolDao().increaseLoginAttepmtCnt(ssn);
	}

	/* (non-Javadoc)
	 * @see kr.co.kmac.pms.expertpool.manager.ExpertPoolManager#accountLocked(java.lang.String)
	 */
	@Override
	public void accountLocked(String ssn) throws ExpertPoolException {
		// TODO Auto-generated method stub
		this.getExpertPoolDao().accountLocked(ssn);		
	}

	/* (non-Javadoc)
	 * @see kr.co.kmac.pms.expertpool.manager.ExpertPoolManager#accountReset(java.lang.String)
	 */
	@Override
	public void accountReset(String ssn) throws ExpertPoolException {
		this.getExpertPoolDao().accountReset(ssn);				
	}

	/* (non-Javadoc)
	 * @see kr.co.kmac.pms.expertpool.manager.ExpertPoolManager#resetPassword(java.lang.String)
	 */
	@Override
	public void passwordReset(String ssn, String password) throws ExpertPoolException {
		this.getExpertPoolDao().passwordReset(ssn, password);		
		
	}

	/* (non-Javadoc)
	 * @see kr.co.kmac.pms.expertpool.manager.ExpertPoolManager#getEncPassword(java.lang.String)
	 */
	@Override
	public String getEncPassword(String password) throws ExpertPoolException {
		return this.getExpertPoolDao().getEncPassword(password);
	}	
	
	@Override
	public String selectWorkDayOn(String ssn, String date) throws ExpertPoolException {
		return this.getExpertPoolDao().selectWorkDayOn(ssn, date);
	}
	
	@Override
	public String selectWorkDayOff(String ssn, String date) throws ExpertPoolException {
		return this.getExpertPoolDao().selectWorkDayOff(ssn, date);
	}
	
	@Override
	public String selectWorkDayCnt(String ssn, String date) throws ExpertPoolException {
		return this.getExpertPoolDao().selectWorkDayCnt(ssn, date);
	}
	
	@Override
	public String selectExpertPoolUidCheck(String uid) throws ExpertPoolException {
		return this.getExpertPoolDao().selectExpertPoolUidCheck(uid);
	}
	
	@Override
	public String selectDeptChk(String dept) throws ExpertPoolException {
		return this.getExpertPoolDao().selectDeptChk(dept);
	}
	
	@Override
	public String selectProjectChk(String fileId) throws ExpertPoolException {
		return this.getExpertPoolDao().selectProjectChk(fileId);
	}
	
	@Override
	public String selectProjectDeptChk(String fileId) throws ExpertPoolException {
		return this.getExpertPoolDao().selectProjectDeptChk(fileId);
	}
	
	@Override
	public String selectConsultantDeptChk(String ssn) throws ExpertPoolException {
		return this.getExpertPoolDao().selectConsultantDeptChk(ssn);
	}
	
	@Override
	public String selectFileProjectCode(String fileId) throws ExpertPoolException {
		return this.getExpertPoolDao().selectFileProjectCode(fileId);
	}
	
	@Override
	public String selectFileProjectChk(String ssn, String projectCode) throws ExpertPoolException{
		return this.getExpertPoolDao().selectFileProjectChk(ssn, projectCode);
	}
	
	@Override
	public String selectPastFileProjectChk(String fileId, String ssn) throws ExpertPoolException{
		return this.getExpertPoolDao().selectPastFileProjectChk(fileId, ssn);
	}
}