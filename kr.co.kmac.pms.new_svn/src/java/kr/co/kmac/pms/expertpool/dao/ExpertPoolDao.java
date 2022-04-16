/*
 * $Id: ExpertPoolDao.java,v 1.26 2018/12/06 08:28:51 cvs Exp $
 * created by    : jiwoongLee
 * creation-date : 2006. 1. 17
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.expertpool.dao;

import java.util.HashMap;
import java.util.List;

import kr.co.kmac.pms.expertpool.data.BankAccount;
import kr.co.kmac.pms.expertpool.data.ExpertPool;
import kr.co.kmac.pms.expertpool.exception.ExpertPoolException;

import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.dao.DataAccessException;

/**
 * TODO Ŭ���� ����
 * 
 * @author jiwoongLee
 * @version $Id: ExpertPoolDao.java,v 1.26 2018/12/06 08:28:51 cvs Exp $
 */
public interface ExpertPoolDao {
	/**
	 * ����� ���� �ĺ���ȣ ���� (20141106 jiwoong)
	 * 
	 * @param jobClass
	 * @return ����� ���� �ĺ���ȣ
	 * @throws DataAccessException
	 */
	public String getSsn(String jobClass) throws DataAccessException;
	
	/**
	 * ����� ���� �ĺ���ȣ ���� (20151124 yhyim)
	 * 
	 * @param jobClass
	 * @return ����� ���� �ĺ���ȣ + 1
	 * @throws DataAccessException
	 */
	public String getSsn2(String jobClass) throws DataAccessException;

	/**
	 * ���ο� �������� Ǯ�� ���
	 * 
	 * @param expertPool
	 * @throws DataAccessException
	 */
	public void create(ExpertPool expertPool) throws DataAccessException;

	/**
	 * ���ο� �������� �μ� �̷°����� ���
	 * 
	 * @param expertPool
	 * @throws DataAccessException Job Date: 2008-01-11 Author: yhyim
	 */
	public void createHistory(ExpertPool expertPool) throws DataAccessException;
	
	/**
	 * �޴� ���� ����� ������
	 * 
	 * @param accessType, expertPool
	 * @throws DataAccessException Job Date: 2018-04-18 Author: yhyim
	 */
	public void createAccountHistory(String accessType, ExpertPool expertPool) throws DataAccessException;
	
	/**
	 * �Էµ� ������ ���� ����
	 * 
	 * @param expertPool
	 * @throws DataAccessException
	 */
	public void store(ExpertPool expertPool) throws DataAccessException;
	
	/**
	 * �Էµ� ������ ���� ����(��й�ȣ �����Ͽ� ����)
	 * 
	 * @param expertPool
	 * @throws DataAccessException
	 */
	public void storeWithPwd(ExpertPool expertPool) throws DataAccessException;
	
	/**
	 * �������� �ý��� ��й�ȣ ����ȭ
	 * 
	 * @param expertPool
	 * @throws DataAccessException
	 */
	public void updateEduSystemAccount(ExpertPool expertPool) throws DataAccessException;

	public void updateRestrictUserState(String ssn, String state, String absence) throws DataAccessException;
	
	public void updateRestrictContents(String ssn, String state, String absence, String restrictContents) throws DataAccessException;

	/**
	 * ������ �μ� �̷� ����
	 * 
	 * @param expertPool
	 * @throws DataAccessException
	 */
	public void storeHistory(ExpertPool expertPool) throws DataAccessException;

	public void storeSanctionLine(ExpertPool expertPool) throws DataAccessException;
	
	/**
	 * ������ ���� ����
	 * 
	 * @param ssn
	 * @throws DataAccessException
	 */
	public void remove(String ssn) throws DataAccessException;

	/**
	 * ������ ��ȸ
	 * 
	 * @param ssn
	 * @return
	 * @throws DataAccessException
	 */
	public ExpertPool retrieve(String ssn) throws DataAccessException;
	public ExpertPool retrieve2(String fileId) throws DataAccessException;
	public ExpertPool retrieve3(String fileId) throws DataAccessException;

	// Job Date: 2009-12-24 Author: yhyim Description: Adding new virtual function
	/**
	 * ������ ��ȸ
	 * 
	 * @param dept, companyPosition
	 * @return
	 * @throws DataAccessException
	 */
	public ExpertPool retrieve(String dept, String companyPosition) throws DataAccessException;

	/**
	 * ������ ��ȸ
	 * 
	 * @param userId
	 * @return
	 * @throws DataAccessException
	 */
	public ExpertPool retrieveUserId(String userId) throws DataAccessException;

	/**
	 * CCO, CBO, COO ��ȸ
	 * 
	 * @param deptCode
	 * @return
	 * @throws DataAccessException
	 */
	public ExpertPool retrieveOfficer(String deptCode) throws DataAccessException;
	
	public ExpertPool retrieveOfficer2(String deptCode) throws DataAccessException;
	
	public BankAccount retrieveBankAccountKMAC(String emplNo) throws DataAccessException;
	
	public BankAccount retrieveBankAccountNonKMAC(String uid) throws DataAccessException;
	
	/**
	 * ��ü ������ ����Ʈ ��ȸ
	 * 
	 * @return
	 * @throws DataAccessException
	 */
	public List findAll() throws DataAccessException;

	public List<ExpertPool> findAsName(String name) throws DataAccessException;

	public List<ExpertPool> findAsUid(String uid) throws DataAccessException;

	/**
	 * ������ �μ��Է�
	 * 
	 * @return
	 * @throws DataAccessException
	 */
	public void addUserMember(String parentId, String userId) throws DataAccessException;

	/**
	 * ������ �μ�����
	 * 
	 * @return
	 * @throws DataAccessException
	 */
	public void removeUserMember(String userId) throws DataAccessException;

	public boolean isExist(String ssn) throws DataAccessException;

	public boolean isExistUserId(String userId) throws DataAccessException;

	public boolean isAbsence(String ssn) throws DataAccessException;

	public List findGroup() throws DataAccessException;

	public List findRole() throws DataAccessException;

	/**
	 * BU ���� ��������
	 * 
	 * @return
	 * @throws DataAccessException
	 */
	public List findBUGroup() throws DataAccessException;

	/**
	 * PU ���� ��������
	 * 
	 * @return
	 * @throws DataAccessException
	 */
	public List findPUGroup() throws DataAccessException;

	/**
	 * BU Role ���� ��������
	 * 
	 * @return
	 * @throws DataAccessException
	 */
	public List findBURole() throws DataAccessException;

	/**
	 * PU Role ���� ��������
	 * 
	 * @return
	 * @throws DataAccessException
	 */
	public List findPURole() throws DataAccessException;

	/**
	 * �޴� ���� ���� ��������
	 * 
	 * @return
	 * @throws DataAccessException
	 */
	public List findMenuRole() throws DataAccessException;

	/**
	 * ������ �����Է�
	 * 
	 * @return
	 * @throws DataAccessException
	 */
	public void addUserRole(String userId, String roleId) throws DataAccessException;

	/**
	 * ������ ��������
	 * 
	 * @return
	 * @throws DataAccessException
	 */
	public void removeUserRole(String userId) throws DataAccessException;

	/**
	 * ������ ��Ͻ� ���� �ֹι�ȣ�� �ִ��� Ȯ�� TODO �޼ҵ� ����
	 * 
	 * @param ssn
	 * @return
	 * @throws DataAccessException
	 */
	public int checkSsn(String ssn) throws DataAccessException;

	/**
	 * UserId ��Ͻ� ���� UserId�� �ִ��� Ȯ�� TODO �޼ҵ� ����
	 * 
	 * @param userId
	 * @return
	 * @throws DataAccessException
	 */
	public int checkUserId(String userId) throws DataAccessException;

	public String getEmail(String ssn) throws DataAccessException;

	/**
	 * ������ �̸� ��ȯ
	 * 
	 * @param ssn
	 * @return
	 * @throws DataAccessException Job Date: 2008-02-04 Author: yhyim
	 */
	public String getName(String ssn) throws DataAccessException;
	
	/**
	 * ������ menu role ��ȯ
	 * 
	 * @param ssn
	 * @return
	 * @throws DataAccessException Job Date: 2016-06-26 Author: yhyim
	 */
	public String getMenuRole(String ssn) throws DataAccessException;

	/**
	 * ������Ʈ ���� �η� ����Ʈ(BU��, ����, PM, PU��, PL, ���)
	 * 
	 * @param projectCode
	 * @return member List
	 * @throws ExpertPoolException Job Date: 2007-12-18 Author: yhyim
	 */
	public List<ListOrderedMap> getProjectInvolvedMembers(String projectCode);

	/**
	 * �ش� Role�� �������� ����� ����Ʈ
	 * 
	 * @param roleNum, extRoleNum
	 * @return member List
	 */
	public List findByRole(String roleNum, String extRoleNum) throws DataAccessException;

	public ExpertPool getDivManager(String divCode) throws DataAccessException;

	public List<ExpertPool> getExpertsBySearch(String indField, String keyWordCon, String keyWord) throws DataAccessException;

	public List<ExpertPool> getExpertPoolList(HashMap<String, String> param) throws DataAccessException;

	public List<ExpertPool> getExpertPoolExtList(HashMap<String, String> param) throws DataAccessException;

	public List<ExpertPool> getExpertPoolExtList2(HashMap<String, String> param) throws DataAccessException;

	public List<ExpertPool> getProjectExpertPoolList(HashMap<String, String> param) throws DataAccessException;

	public int getExpertPoolCnt(HashMap<String, String> param) throws DataAccessException;

	public int getExpertPoolExtCnt(HashMap<String, String> param) throws DataAccessException;
	
	public int getExpertPoolExtCnt2(HashMap<String, String> param) throws DataAccessException;

	public int getProjectExpertPoolCnt(HashMap<String, String> param) throws DataAccessException;

	public void storeDailyDownloadLimitInfo(String ssn) throws DataAccessException;
	
	public void storeHomeTaxValue(String value1, String value2, String value3, String value4, String value5, String value6) throws DataAccessException;

	public boolean isDailyDownloadLimitUser(String ssn) throws DataAccessException;

	public void updateDailyDownloadLimitState(String ssn, String isLimit) throws DataAccessException;
	
	public void insertBudgetCheck(String projectCode) throws DataAccessException;

	public void initDownloadUnlimitState() throws DataAccessException;
	
	public int getLoginAttepmtCnt(String ssn) throws DataAccessException;
	
	public int increaseLoginAttepmtCnt(String ssn) throws DataAccessException;
	
	public void accountLocked(String ssn) throws DataAccessException;
	
	public void accountReset(String ssn) throws DataAccessException;
	
	public void passwordReset(String ssn, String password) throws DataAccessException;

	public String getEncPassword(String password) throws DataAccessException;
	
	public void workTime (String ssn, String workType, String ip) throws DataAccessException;
	
	public String selectWorkDayOn(String ssn, String workIp) throws DataAccessException;
	
	public String selectWorkDayOff(String ssn, String workIp) throws DataAccessException;
	
	public String selectWorkDayCnt(String ssn, String workIp) throws DataAccessException;
	
	public String selectExpertPoolUidCheck(String uid) throws DataAccessException;
	
	public String selectDeptChk(String dept) throws DataAccessException;
	
	public String selectProjectChk(String fileId) throws DataAccessException;
	
	public String selectProjectDeptChk(String fileId) throws DataAccessException;
	
	public String selectConsultantDeptChk(String ssn) throws DataAccessException;
	
	public String selectFileProjectCode(String fileId) throws DataAccessException;
	
	public String selectFileProjectChk(String ssn, String projectCode) throws DataAccessException;
	
	public String selectPastFileProjectChk(String fileId, String ssn) throws DataAccessException;
}

