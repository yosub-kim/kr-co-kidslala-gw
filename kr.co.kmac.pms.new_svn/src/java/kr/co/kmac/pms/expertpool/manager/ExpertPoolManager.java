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
 * 전문가 풀을 관리하기 위한 인터페이스
 * 
 * @author jiwoongLee
 * @version $Id: ExpertPoolManager.java,v 1.26 2019/03/20 08:12:04 cvs Exp $
 */
public interface ExpertPoolManager {

	/**
	 * 사용자 고유 식별번호 생성 (20141106 jiwoong)
	 * 
	 * @param jobClass
	 * @return 사용자 고유 식별번호
	 * @throws ExpertPoolException
	 */
	public String getSsn(String jobClass) throws ExpertPoolException;

	/**
	 * 새로운 전문가를 풀에 등록
	 * 
	 * @param expertPool
	 * @throws ExpertPoolException
	 */
	public void create(ExpertPool expertPool) throws ExpertPoolException;

	/**
	 * 새로운 전문가를 이력관리에 등록
	 * 
	 * @param expertPool
	 * @throws ExpertPoolException Job Date: 2008-01-11 Ahthor: yhyim Description: 부서 이동 이력관리
	 */
	public void createHistory(ExpertPool expertPool) throws ExpertPoolException;

	/**
	 * 새로운 전문가를 결재라인에 기안자로 추가
	 * 
	 * @param expertPool
	 * @throws ExpertPoolException Job Date: 2010-07-08 Ahthor: yhyim Description: adding user to sanctionLine
	 */
	public void createSanctionLine(ExpertPool expertPool) throws ExpertPoolException;

	/**
	 * 메뉴 접근 권한 기록 생성
	 * 
	 * @param expertPool
	 * @throws ExpertPoolException Job Date: 2018-04-18 Ahthor: yhyim Description: create menu access authority history to AccountHistory
	 */
	public void createAccountHistory(String accessType, ExpertPool expertPool) throws ExpertPoolException;	
	
	/**
	 * 입력된 전문가 정보 수정
	 * 
	 * @param expertPool
	 * @throws ExpertPoolException
	 */
	public void store(ExpertPool expertPool) throws ExpertPoolException;

	/**
	 * 투입 제한 전문가 정보 수정
	 * 
	 * @param ssn
	 * @throws ExpertPoolException
	 */
	public void updateRestrictUserState(String ssn, String state) throws ExpertPoolException;
	
	/**
	 * 투입 제한 컨텐츠 수정
	 * 
	 * @param ssn
	 * @throws ExpertPoolException
	 */
	public void updateRestrictContents(String ssn, String state, String restrictContents) throws ExpertPoolException;

	/**
	 * 투입 준비 전문가 정보 수정
	 * 
	 * @param ssn
	 * @throws ExpertPoolException
	 */
	public void updateReadyUserState(String ssn, String state) throws ExpertPoolException;

	public void updateMyinfo(ExpertPool expertPool, String withPassword) throws ExpertPoolException;

	public void updateEduSystemAccount(ExpertPool expertPool) throws ExpertPoolException;
	
	/**
	 * 전문가 부서 이력 수정
	 * 
	 * @param expertPool
	 * @throws ExpertPoolException
	 */
	public void storeHistory(ExpertPool expertPool) throws ExpertPoolException;

	/**
	 * 전문가 결재라인 정보 수정
	 * 
	 * @param expertPool
	 * @throws ExpertPoolException
	 */
	public void storeSanctionLine(ExpertPool expertPool) throws ExpertPoolException;
	
	/**
	 * 전문가 정보 조회
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
	 * 전문가 정보 전체 리스트 조회
	 * 
	 * @return
	 * @throws ExpertPoolException
	 */
	public List findAll() throws ExpertPoolException;

	public List<ExpertPool> findAsName(String name) throws ExpertPoolException;

	public List<ExpertPool> findAsUid(String uid) throws ExpertPoolException;

	public List findMenuRole() throws ExpertPoolException;

	/**
	 * 해당 정보가 있는지 조회
	 * 
	 * @param regNo
	 * @return
	 * @throws ExpertPoolException
	 */
	public boolean isExist(String regNo) throws ExpertPoolException;

	public boolean isExistUserId(String userId) throws ExpertPoolException;

	/**
	 * 부재상황을 반환
	 * 
	 * @param regNo
	 * @return
	 * @throws ExpertPoolException
	 */
	public boolean isAbsence(String ssn) throws ExpertPoolException;

	/**
	 * 부서 정보 전체가져오기
	 * 
	 * @return
	 * @throws ExpertPoolException
	 */
	public List findGroup() throws ExpertPoolException;

	/**
	 * BU 정보 가져오기
	 * 
	 * @return
	 * @throws ExpertPoolException
	 * @author yhyim
	 */
	public List findBUGroup() throws ExpertPoolException;

	/**
	 * PU 정보 가져오기
	 * 
	 * @return
	 * @throws ExpertPoolException
	 * @author yhyim
	 */
	public List findPUGroup() throws ExpertPoolException;

	/**
	 * 전문가 부서입력
	 * 
	 * @return
	 * @throws ExpertPoolException
	 */
	public void addUserMember(String parentId, String userId) throws ExpertPoolException;

	/**
	 * 전문가 부서삭제
	 * 
	 * @return
	 * @throws ExpertPoolException
	 */
	public void removeUserMember(String userId) throws ExpertPoolException;

	/**
	 * 직위 정보 전체가져오기
	 * 
	 * @return
	 * @throws ExpertPoolException
	 */
	public List findRole() throws ExpertPoolException;

	/**
	 * BU 직위 정보 가져오기
	 * 
	 * @return
	 * @throws ExpertPoolException
	 * @author yhyim
	 */
	public List findBURole() throws ExpertPoolException;

	/**
	 * PU 직위 정보 가져오기
	 * 
	 * @return
	 * @throws ExpertPoolException
	 * @author yhyim
	 */
	public List findPURole() throws ExpertPoolException;

	/**
	 * 전문가 직위입력
	 * 
	 * @return
	 * @throws ExpertPoolException
	 */
	public void addUserRole(String userId, String roleId) throws ExpertPoolException;

	/**
	 * 전문가 직위삭제
	 * 
	 * @return
	 * @throws ExpertPoolException
	 */
	public void removeUserRole(String userId) throws ExpertPoolException;

	/**
	 * 전문가 등록시 같은 주민번호가 있는지 확인 TODO 메소드 설명
	 * 
	 * @param ssn
	 * @return
	 * @throws ExpertPoolException
	 */
	public int checkSsn(String ssn) throws ExpertPoolException;

	/**
	 * UserId 등록시 같은 UserId가 있는지 확인 TODO 메소드 설명
	 * 
	 * @param ssn
	 * @return
	 * @throws ExpertPoolException
	 */
	public int checkUserId(String userId) throws ExpertPoolException;

	/**
	 * 프로젝트 관련 인력 리스트(BU장, 팀장, PM, PU장, PL, 멤버)
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
