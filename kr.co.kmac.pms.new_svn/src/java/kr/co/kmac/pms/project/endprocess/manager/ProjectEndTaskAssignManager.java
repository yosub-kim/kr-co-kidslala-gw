/*
 * $Id: ProjectEndTaskAssignManager.java,v 1.8 2018/01/29 02:25:47 cvs Exp $
 * created by    : jiwoongLee
 * creation-date : 2006. 3. 30.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.project.endprocess.manager;

import java.util.List;

import kr.co.kmac.pms.project.endprocess.data.EndProcessCheck;
import kr.co.kmac.pms.project.exception.ProjectException;

/**
 * 종료처리를 위한 작업할당 메니져
 * 
 * @author jiwoongLee
 * @version $Id: ProjectEndTaskAssignManager.java,v 1.8 2018/01/29 02:25:47 cvs Exp $
 */
public interface ProjectEndTaskAssignManager {

	// /**
	// * 컨설팅 중간 만족도 평가
	// *
	// * @param projectCode
	// * @return
	// * @throws ProjectException
	// * @deprecated
	// */
	// public void assignConsultingMiddleCustomerSatisfactionTask(String projectCode) throws ProjectException;

	/**
	 * 컨설팅 만족도 평가
	 * 
	 * @param projectCode
	 * @return
	 * @throws ProjectException
	 */
	public void assignConsultingCustomerSatisfactionTask(String projectCode) throws ProjectException;

	/**
	 * 컨설팅 pl 평가
	 * 
	 * @param projectCode
	 * @return
	 * @throws ProjectException
	 */
	public void assignConsultingPLValuationTask(String projectCode) throws ProjectException;

	/**
	 * 컨설팅 컨설턴트 평가
	 * 
	 * @param projectCode
	 * @return
	 * @throws ProjectException
	 */
	public void assignConsultingMemberValuationTask(String projectCode) throws ProjectException;

	/**
	 * 컨설팅 컨설턴트 평가
	 * 
	 * @param projectCode
	 * @return
	 * @throws ProjectException
	 */
	/*public void assignConsultingPartPLValuationTask(String projectCode) throws ProjectException;*/
	
	/**
	 * R&C 만족도 평가
	 * 
	 * @param projectCode
	 * @return
	 * @throws ProjectException
	 */
	public void assignResearchCustomerSatisfactionTask(String projectCode) throws ProjectException;

	/**
	 * 공개교육 만족도 평가
	 * 
	 * @param projectCode
	 * @throws ProjectException
	 */
	public void assignPublicEduCustomerSatisfactionTask(String projectCode) throws ProjectException;

	public void sendCSMail(String projectCode) throws ProjectException;

	/**
	 * 공통 종료처리
	 * 
	 * @param projectCode
	 * @return
	 * @throws ProjectException
	 */
	public void assignEndProcessTask(String projectCode) throws ProjectException;

	/**
	 * 공통 비정상 종료처리
	 * 
	 * @param projectCode
	 * @return
	 * @throws ProjectException
	 */
	public void assignAbnormalEndProcessTask(String projectCode) throws ProjectException;

	/**
	 * CBO 종료처리
	 * 
	 * @param projectCode
	 * @return
	 * @throws ProjectException
	 */
	public void assignEndProcessTaskForCOO(String projectCode) throws ProjectException;

	/**
	 * PU장, CFO 리뷰지 중간점검
	 * 
	 * @param projectCode
	 * @return
	 * @throws ProjectException
	 */
	public void assignEndProcessTaskForMReview(String projectCode) throws ProjectException;

	public void assignEndProcessTaskForM2Review(String projectCode) throws ProjectException;

	public void assignEndProcessTaskForKM(String projectCode) throws ProjectException;

	/**
	 * 교육 만족도 평가
	 * 
	 * @param projectCode
	 * @return
	 * @throws ProjectException
	 */
	public void assignEduSatisfactionTask(String projectCode) throws ProjectException;

	/**
	 * 온라인(사내교육) 교육 만족도 평가
	 * 
	 * @param projectCode
	 * @return
	 * @throws ProjectException
	 */
	public void assignEduOnlineSatisfactionTask(String projectCode) throws ProjectException;
	
	/**
	 * 특강 교육 만족도 평가
	 * 
	 * @param projectCode
	 * @return
	 * @throws ProjectException
	 */
	public void assignSpLectureOnlineSatisfactionTask(String projectCode) throws ProjectException;


	/**
	 * 교육 강사 평가
	 * 
	 * @param projectCode
	 * @return
	 * @throws ProjectException
	 */
	public void assignEduLectureTask(String projectCode) throws ProjectException;

	/**
	 * 온라인 연수 만족도 평가
	 * 
	 * @param projectCode
	 * @return
	 * @throws ProjectException
	 */
	public void assignTrainingOnlineSatisfactionTask(String projectCode) throws ProjectException;

	/**
	 * 연수 만족도 평가
	 * 
	 * @param projectCode
	 * @return
	 * @throws ProjectException
	 */
	public void assignTrainingSatisfactionTask(String projectCode) throws ProjectException;

	/**
	 * 업무 실행상태 변경
	 * 
	 * @param endProcessCheck
	 * @throws ProjectException
	 */
	public void store(EndProcessCheck endProcessCheck) throws ProjectException;

	/**
	 * 종료여부
	 * 
	 * @param projectCode
	 * @return
	 * @throws ProjectException
	 */
	public boolean isRollingAndRateingFinished(String projectCode) throws ProjectException;

	/**
	 * 종료 관련 업무 리스트 반환
	 * 
	 * @param projectCode
	 * @return
	 * @throws ProjectException
	 */
	public List<String> getEndProcessTaskList(String projectCode) throws ProjectException;
}
