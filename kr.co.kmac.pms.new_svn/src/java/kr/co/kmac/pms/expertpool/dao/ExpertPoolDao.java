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
 * TODO 클래스 설명
 * 
 * @author jiwoongLee
 * @version $Id: ExpertPoolDao.java,v 1.26 2018/12/06 08:28:51 cvs Exp $
 */
public interface ExpertPoolDao {
	/**
	 * 사용자 고유 식별번호 생성 (20141106 jiwoong)
	 * 
	 * @param jobClass
	 * @return 사용자 고유 식별번호
	 * @throws DataAccessException
	 */
	public String getSsn(String jobClass) throws DataAccessException;
	
	/**
	 * 사용자 고유 식별번호 생성 (20151124 yhyim)
	 * 
	 * @param jobClass
	 * @return 사용자 고유 식별번호 + 1
	 * @throws DataAccessException
	 */
	public String getSsn2(String jobClass) throws DataAccessException;

	/**
	 * 새로운 전문가를 풀에 등록
	 * 
	 * @param expertPool
	 * @throws DataAccessException
	 */
	public void create(ExpertPool expertPool) throws DataAccessException;

	/**
	 * 새로운 전문가를 부서 이력관리에 등록
	 * 
	 * @param expertPool
	 * @throws DataAccessException Job Date: 2008-01-11 Author: yhyim
	 */
	public void createHistory(ExpertPool expertPool) throws DataAccessException;
	
	/**
	 * 메뉴 접근 기록을 생성함
	 * 
	 * @param accessType, expertPool
	 * @throws DataAccessException Job Date: 2018-04-18 Author: yhyim
	 */
	public void createAccountHistory(String accessType, ExpertPool expertPool) throws DataAccessException;
	
	/**
	 * 입력된 전문가 정보 수정
	 * 
	 * @param expertPool
	 * @throws DataAccessException
	 */
	public void store(ExpertPool expertPool) throws DataAccessException;
	
	/**
	 * 입력된 전문가 정보 수정(비밀번호 포함하여 저장)
	 * 
	 * @param expertPool
	 * @throws DataAccessException
	 */
	public void storeWithPwd(ExpertPool expertPool) throws DataAccessException;
	
	/**
	 * 교육관리 시스템 비밀번호 동기화
	 * 
	 * @param expertPool
	 * @throws DataAccessException
	 */
	public void updateEduSystemAccount(ExpertPool expertPool) throws DataAccessException;

	public void updateRestrictUserState(String ssn, String state, String absence) throws DataAccessException;
	
	public void updateRestrictContents(String ssn, String state, String absence, String restrictContents) throws DataAccessException;

	/**
	 * 전문가 부서 이력 수정
	 * 
	 * @param expertPool
	 * @throws DataAccessException
	 */
	public void storeHistory(ExpertPool expertPool) throws DataAccessException;

	public void storeSanctionLine(ExpertPool expertPool) throws DataAccessException;
	
	/**
	 * 전문가 정보 삭제
	 * 
	 * @param ssn
	 * @throws DataAccessException
	 */
	public void remove(String ssn) throws DataAccessException;

	/**
	 * 전문가 조회
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
	 * 전문가 조회
	 * 
	 * @param dept, companyPosition
	 * @return
	 * @throws DataAccessException
	 */
	public ExpertPool retrieve(String dept, String companyPosition) throws DataAccessException;

	/**
	 * 전문가 조회
	 * 
	 * @param userId
	 * @return
	 * @throws DataAccessException
	 */
	public ExpertPool retrieveUserId(String userId) throws DataAccessException;

	/**
	 * CCO, CBO, COO 조회
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
	 * 전체 전문가 리스트 조회
	 * 
	 * @return
	 * @throws DataAccessException
	 */
	public List findAll() throws DataAccessException;

	public List<ExpertPool> findAsName(String name) throws DataAccessException;

	public List<ExpertPool> findAsUid(String uid) throws DataAccessException;

	/**
	 * 전문가 부서입력
	 * 
	 * @return
	 * @throws DataAccessException
	 */
	public void addUserMember(String parentId, String userId) throws DataAccessException;

	/**
	 * 전문가 부서삭제
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
	 * BU 정보 가져오기
	 * 
	 * @return
	 * @throws DataAccessException
	 */
	public List findBUGroup() throws DataAccessException;

	/**
	 * PU 정보 가져오기
	 * 
	 * @return
	 * @throws DataAccessException
	 */
	public List findPUGroup() throws DataAccessException;

	/**
	 * BU Role 정보 가져오기
	 * 
	 * @return
	 * @throws DataAccessException
	 */
	public List findBURole() throws DataAccessException;

	/**
	 * PU Role 정보 가져오기
	 * 
	 * @return
	 * @throws DataAccessException
	 */
	public List findPURole() throws DataAccessException;

	/**
	 * 메뉴 접근 정보 가져오기
	 * 
	 * @return
	 * @throws DataAccessException
	 */
	public List findMenuRole() throws DataAccessException;

	/**
	 * 전문가 직위입력
	 * 
	 * @return
	 * @throws DataAccessException
	 */
	public void addUserRole(String userId, String roleId) throws DataAccessException;

	/**
	 * 전문가 직위삭제
	 * 
	 * @return
	 * @throws DataAccessException
	 */
	public void removeUserRole(String userId) throws DataAccessException;

	/**
	 * 전문가 등록시 같은 주민번호가 있는지 확인 TODO 메소드 설명
	 * 
	 * @param ssn
	 * @return
	 * @throws DataAccessException
	 */
	public int checkSsn(String ssn) throws DataAccessException;

	/**
	 * UserId 등록시 같은 UserId가 있는지 확인 TODO 메소드 설명
	 * 
	 * @param userId
	 * @return
	 * @throws DataAccessException
	 */
	public int checkUserId(String userId) throws DataAccessException;

	public String getEmail(String ssn) throws DataAccessException;

	/**
	 * 전문가 이름 반환
	 * 
	 * @param ssn
	 * @return
	 * @throws DataAccessException Job Date: 2008-02-04 Author: yhyim
	 */
	public String getName(String ssn) throws DataAccessException;
	
	/**
	 * 전문가 menu role 반환
	 * 
	 * @param ssn
	 * @return
	 * @throws DataAccessException Job Date: 2016-06-26 Author: yhyim
	 */
	public String getMenuRole(String ssn) throws DataAccessException;

	/**
	 * 프로젝트 관련 인력 리스트(BU장, 팀장, PM, PU장, PL, 멤버)
	 * 
	 * @param projectCode
	 * @return member List
	 * @throws ExpertPoolException Job Date: 2007-12-18 Author: yhyim
	 */
	public List<ListOrderedMap> getProjectInvolvedMembers(String projectCode);

	/**
	 * 해당 Role을 지정받은 사용자 리스트
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

