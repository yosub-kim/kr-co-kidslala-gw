/*
 * $Id: ExpertPoolManager.java,v 1.26 2019/03/20 08:12:04 cvs Exp $ created by :
 * jiwoongLee creation-date : 2006. 1. 17
 * ========================================================= Copyright (c) 2006
 * Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.expertpool.manager;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import kr.co.kmac.pms.common.org.data.Group;
import kr.co.kmac.pms.expertpool.data.BankAccount;
import kr.co.kmac.pms.expertpool.data.ExpertPool;
import kr.co.kmac.pms.expertpool.data.ExpertPoolSpecialField;
import kr.co.kmac.pms.expertpool.exception.ExpertPoolException;

import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.dao.DataAccessException;

/**
 * ������ Ǯ�� �����ϱ� ���� �������̽�
 * 
 * @author jiwoongLee
 * @version $Id: ExpertPoolManager.java,v 1.26 2019/03/20 08:12:04 cvs Exp $
 */
public interface ExpertPoolManager {

	/**
	 * ����� ���� �ĺ���ȣ ���� (20141106 jiwoong)
	 * 
	 * @param jobClass
	 * @return ����� ���� �ĺ���ȣ
	 * @throws ExpertPoolException
	 */
	public String getSsn(String jobClass) throws ExpertPoolException;

	/**
	 * ���ο� �������� Ǯ�� ���
	 * 
	 * @param expertPool
	 * @throws ExpertPoolException
	 */
	public void create(ExpertPool expertPool) throws ExpertPoolException;

	/**
	 * ���ο� �������� �̷°����� ���
	 * 
	 * @param expertPool
	 * @throws ExpertPoolException Job Date: 2008-01-11 Ahthor: yhyim Description: �μ� �̵� �̷°���
	 */
	public void createHistory(ExpertPool expertPool) throws ExpertPoolException;

	/**
	 * ���ο� �������� ������ο� ����ڷ� �߰�
	 * 
	 * @param expertPool
	 * @throws ExpertPoolException Job Date: 2010-07-08 Ahthor: yhyim Description: adding user to sanctionLine
	 */
	public void createSanctionLine(ExpertPool expertPool) throws ExpertPoolException;

	/**
	 * �޴� ���� ���� ��� ����
	 * 
	 * @param expertPool
	 * @throws ExpertPoolException Job Date: 2018-04-18 Ahthor: yhyim Description: create menu access authority history to AccountHistory
	 */
	public void createAccountHistory(String accessType, ExpertPool expertPool) throws ExpertPoolException;	
	
	/**
	 * �Էµ� ������ ���� ����
	 * 
	 * @param expertPool
	 * @throws ExpertPoolException
	 */
	public void store(ExpertPool expertPool) throws ExpertPoolException;

	/**
	 * ���� ���� ������ ���� ����
	 * 
	 * @param ssn
	 * @throws ExpertPoolException
	 */
	public void updateRestrictUserState(String ssn, String state) throws ExpertPoolException;
	
	/**
	 * ���� ���� ������ ����
	 * 
	 * @param ssn
	 * @throws ExpertPoolException
	 */
	public void updateRestrictContents(String ssn, String state, String restrictContents) throws ExpertPoolException;

	/**
	 * ���� �غ� ������ ���� ����
	 * 
	 * @param ssn
	 * @throws ExpertPoolException
	 */
	public void updateReadyUserState(String ssn, String state) throws ExpertPoolException;

	public void updateMyinfo(ExpertPool expertPool, String withPassword) throws ExpertPoolException;

	public void updateEduSystemAccount(ExpertPool expertPool) throws ExpertPoolException;
	
	/**
	 * ������ �μ� �̷� ����
	 * 
	 * @param expertPool
	 * @throws ExpertPoolException
	 */
	public void storeHistory(ExpertPool expertPool) throws ExpertPoolException;

	/**
	 * ������ ������� ���� ����
	 * 
	 * @param expertPool
	 * @throws ExpertPoolException
	 */
	public void storeSanctionLine(ExpertPool expertPool) throws ExpertPoolException;
	
	/**
	 * ������ ���� ��ȸ
	 * 
	 * @param regNo
	 * @return
	 * @throws ExpertPoolException
	 */
	public ExpertPool retrieve(String regNo) throws ExpertPoolException;
	
	public ExpertPool retrieve2(String fileId) throws ExpertPoolException;
	
	public ExpertPool retrieve3(String fileId) throws ExpertPoolException;
	
	// Job Date: 2009-12-24 Author: yhyim Description: Adding retrieve project
	// responsibility person
	public ExpertPool retrieve(String dept, String companyPosition) throws ExpertPoolException;

	public ExpertPool retrieveUserId(String userId) throws ExpertPoolException;
	
	// Job Date: 2018-01-03 Author: yhyim Description: Adding retrieve CCO, CBO, COO
	// responsibility person
	public ExpertPool retrieveOfficer(String deptCode) throws ExpertPoolException;
	
	public ExpertPool retrieveOfficer2(String deptCode) throws ExpertPoolException;
	
	public BankAccount retrieveBankAccount(ExpertPool expertPool)throws ExpertPoolException;

	public String getEmail(String ssn) throws ExpertPoolException;

	// Job Date: 2008-02-04 Author: yhyim Description: adding method get Name
	public String getName(String ssn) throws ExpertPoolException;

	// Job Date: 2016-06-26 Author: yhyim Description: adding method get menu role
	public String getMenuRole(String ssn) throws ExpertPoolException;
	

	/**
	 * ������ ���� ��ü ����Ʈ ��ȸ
	 * 
	 * @return
	 * @throws ExpertPoolException
	 */
	public List findAll() throws ExpertPoolException;

	public List<ExpertPool> findAsName(String name) throws ExpertPoolException;

	public List<ExpertPool> findAsUid(String uid) throws ExpertPoolException;

	public List findMenuRole() throws ExpertPoolException;

	/**
	 * �ش� ������ �ִ��� ��ȸ
	 * 
	 * @param regNo
	 * @return
	 * @throws ExpertPoolException
	 */
	public boolean isExist(String regNo) throws ExpertPoolException;

	public boolean isExistUserId(String userId) throws ExpertPoolException;

	/**
	 * �����Ȳ�� ��ȯ
	 * 
	 * @param regNo
	 * @return
	 * @throws ExpertPoolException
	 */
	public boolean isAbsence(String ssn) throws ExpertPoolException;

	/**
	 * �μ� ���� ��ü��������
	 * 
	 * @return
	 * @throws ExpertPoolException
	 */
	public List findGroup() throws ExpertPoolException;

	/**
	 * BU ���� ��������
	 * 
	 * @return
	 * @throws ExpertPoolException
	 * @author yhyim
	 */
	public List findBUGroup() throws ExpertPoolException;

	/**
	 * PU ���� ��������
	 * 
	 * @return
	 * @throws ExpertPoolException
	 * @author yhyim
	 */
	public List findPUGroup() throws ExpertPoolException;

	/**
	 * ������ �μ��Է�
	 * 
	 * @return
	 * @throws ExpertPoolException
	 */
	public void addUserMember(String parentId, String userId) throws ExpertPoolException;

	/**
	 * ������ �μ�����
	 * 
	 * @return
	 * @throws ExpertPoolException
	 */
	public void removeUserMember(String userId) throws ExpertPoolException;

	/**
	 * ���� ���� ��ü��������
	 * 
	 * @return
	 * @throws ExpertPoolException
	 */
	public List findRole() throws ExpertPoolException;

	/**
	 * BU ���� ���� ��������
	 * 
	 * @return
	 * @throws ExpertPoolException
	 * @author yhyim
	 */
	public List findBURole() throws ExpertPoolException;

	/**
	 * PU ���� ���� ��������
	 * 
	 * @return
	 * @throws ExpertPoolException
	 * @author yhyim
	 */
	public List findPURole() throws ExpertPoolException;

	/**
	 * ������ �����Է�
	 * 
	 * @return
	 * @throws ExpertPoolException
	 */
	public void addUserRole(String userId, String roleId) throws ExpertPoolException;

	/**
	 * ������ ��������
	 * 
	 * @return
	 * @throws ExpertPoolException
	 */
	public void removeUserRole(String userId) throws ExpertPoolException;

	/**
	 * ������ ��Ͻ� ���� �ֹι�ȣ�� �ִ��� Ȯ�� TODO �޼ҵ� ����
	 * 
	 * @param ssn
	 * @return
	 * @throws ExpertPoolException
	 */
	public int checkSsn(String ssn) throws ExpertPoolException;

	/**
	 * UserId ��Ͻ� ���� UserId�� �ִ��� Ȯ�� TODO �޼ҵ� ����
	 * 
	 * @param ssn
	 * @return
	 * @throws ExpertPoolException
	 */
	public int checkUserId(String userId) throws ExpertPoolException;

	/**
	 * ������Ʈ ���� �η� ����Ʈ(BU��, ����, PM, PU��, PL, ���)
	 * 
	 * @param projectCode
	 * @return member List
	 * @throws ExpertPoolException Job Date: 2007-12-18 Author: yhyim
	 */
	public List<ListOrderedMap> getProjectInvolvedMembers(String projectCode) throws ExpertPoolException;

	public List getExpertListByRole(String roleNum, String extRoleNum) throws ExpertPoolException;

	public List getExpertsBySearch(String indField, String keyWordCon, String keyWord) throws ExpertPoolException;

	public void createExpertPoolSpecialField(String ssn, List<ExpertPoolSpecialField> expertPoolSpecialFieldList) throws DataAccessException;

	public void removeExpertPoolSpecialField(String ssn, String specialId) throws ExpertPoolException;

	public List<ExpertPoolSpecialField> retrieveExpertPoolSpecialField(String ssn) throws ExpertPoolException;

	public List<ExpertPoolSpecialField> getExpertPoolSpecialField(String deptId) throws ExpertPoolException;

	public List<Group> getDeptLIst() throws ExpertPoolException;

	public List<Group> getExpertPoolFunctionLIst() throws ExpertPoolException;

	public List<ExpertPool> getExpertPoolList(HashMap<String, String> param) throws ExpertPoolException;

	public List<ExpertPool> getExpertPoolExtList(HashMap<String, String> param) throws ExpertPoolException;
	
	public List<ExpertPool> getExpertPoolExtList2(HashMap<String, String> param) throws ExpertPoolException;
	
	public List<ExpertPool> getProjectExpertPoolList(HashMap<String, String> param) throws ExpertPoolException;

	public int getExpertPoolCnt(HashMap<String, String> param) throws ExpertPoolException;

	public int getExpertPoolExtCnt(HashMap<String, String> param) throws ExpertPoolException;

	public int getExpertPoolExtCnt2(HashMap<String, String> param) throws ExpertPoolException;

	public int getProjectExpertPoolCnt(HashMap<String, String> param) throws ExpertPoolException;

	public void storeDailyDownloadLimitInfo(String ssn) throws ExpertPoolException;
	
	public void storeHomeTaxValue(String value1, String value2, String value3, String value4, String value5, String value6) throws ExpertPoolException;

	public boolean isDailyDownloadLimitUser(String ssn) throws ExpertPoolException;

	public void updateDailyDownloadLimitState(String ssn, String isLimit) throws ExpertPoolException;
	
	public void insertBudgetCheck(String projectCode) throws ExpertPoolException;
	
	public int getLoginAttepmtCnt(String ssn) throws ExpertPoolException;
	
	public int increaseLoginAttepmtCnt(String ssn) throws ExpertPoolException;
	
	public void accountLocked(String ssn) throws ExpertPoolException;
	
	public void accountReset(String ssn) throws ExpertPoolException;

	public void passwordReset(String ssn, String password) throws ExpertPoolException;

	public String getEncPassword(String password) throws ExpertPoolException;
	
	public void workTime(String ssn, String workType, String ip) throws ExpertPoolException;
	
	public String selectWorkDayOn(String ssn, String date) throws ExpertPoolException;

	public String selectWorkDayOff(String ssn, String date) throws ExpertPoolException;
	
	public String selectWorkDayCnt(String ssn, String date) throws ExpertPoolException;
	
	public String selectExpertPoolUidCheck(String uid) throws ExpertPoolException;
	
	public String selectDeptChk(String dept) throws ExpertPoolException;
	
	public String selectProjectChk(String fileId) throws ExpertPoolException;
	
	public String selectProjectDeptChk(String fileId) throws ExpertPoolException;
	
	public String selectConsultantDeptChk(String ssn) throws ExpertPoolException;
	
	public String selectFileProjectCode(String fileId) throws ExpertPoolException;
	
	public String selectFileProjectChk(String ssn, String projectCode) throws ExpertPoolException;
	
	public String selectPastFileProjectChk(String fileId, String ssn) throws ExpertPoolException;
}
