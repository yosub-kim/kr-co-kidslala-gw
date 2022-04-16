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
 * ����ó���� ���� �۾��Ҵ� �޴���
 * 
 * @author jiwoongLee
 * @version $Id: ProjectEndTaskAssignManager.java,v 1.8 2018/01/29 02:25:47 cvs Exp $
 */
public interface ProjectEndTaskAssignManager {

	// /**
	// * ������ �߰� ������ ��
	// *
	// * @param projectCode
	// * @return
	// * @throws ProjectException
	// * @deprecated
	// */
	// public void assignConsultingMiddleCustomerSatisfactionTask(String projectCode) throws ProjectException;

	/**
	 * ������ ������ ��
	 * 
	 * @param projectCode
	 * @return
	 * @throws ProjectException
	 */
	public void assignConsultingCustomerSatisfactionTask(String projectCode) throws ProjectException;

	/**
	 * ������ pl ��
	 * 
	 * @param projectCode
	 * @return
	 * @throws ProjectException
	 */
	public void assignConsultingPLValuationTask(String projectCode) throws ProjectException;

	/**
	 * ������ ������Ʈ ��
	 * 
	 * @param projectCode
	 * @return
	 * @throws ProjectException
	 */
	public void assignConsultingMemberValuationTask(String projectCode) throws ProjectException;

	/**
	 * ������ ������Ʈ ��
	 * 
	 * @param projectCode
	 * @return
	 * @throws ProjectException
	 */
	/*public void assignConsultingPartPLValuationTask(String projectCode) throws ProjectException;*/
	
	/**
	 * R&C ������ ��
	 * 
	 * @param projectCode
	 * @return
	 * @throws ProjectException
	 */
	public void assignResearchCustomerSatisfactionTask(String projectCode) throws ProjectException;

	/**
	 * �������� ������ ��
	 * 
	 * @param projectCode
	 * @throws ProjectException
	 */
	public void assignPublicEduCustomerSatisfactionTask(String projectCode) throws ProjectException;

	public void sendCSMail(String projectCode) throws ProjectException;

	/**
	 * ���� ����ó��
	 * 
	 * @param projectCode
	 * @return
	 * @throws ProjectException
	 */
	public void assignEndProcessTask(String projectCode) throws ProjectException;

	/**
	 * ���� ������ ����ó��
	 * 
	 * @param projectCode
	 * @return
	 * @throws ProjectException
	 */
	public void assignAbnormalEndProcessTask(String projectCode) throws ProjectException;

	/**
	 * CBO ����ó��
	 * 
	 * @param projectCode
	 * @return
	 * @throws ProjectException
	 */
	public void assignEndProcessTaskForCOO(String projectCode) throws ProjectException;

	/**
	 * PU��, CFO ������ �߰�����
	 * 
	 * @param projectCode
	 * @return
	 * @throws ProjectException
	 */
	public void assignEndProcessTaskForMReview(String projectCode) throws ProjectException;

	public void assignEndProcessTaskForM2Review(String projectCode) throws ProjectException;

	public void assignEndProcessTaskForKM(String projectCode) throws ProjectException;

	/**
	 * ���� ������ ��
	 * 
	 * @param projectCode
	 * @return
	 * @throws ProjectException
	 */
	public void assignEduSatisfactionTask(String projectCode) throws ProjectException;

	/**
	 * �¶���(�系����) ���� ������ ��
	 * 
	 * @param projectCode
	 * @return
	 * @throws ProjectException
	 */
	public void assignEduOnlineSatisfactionTask(String projectCode) throws ProjectException;
	
	/**
	 * Ư�� ���� ������ ��
	 * 
	 * @param projectCode
	 * @return
	 * @throws ProjectException
	 */
	public void assignSpLectureOnlineSatisfactionTask(String projectCode) throws ProjectException;


	/**
	 * ���� ���� ��
	 * 
	 * @param projectCode
	 * @return
	 * @throws ProjectException
	 */
	public void assignEduLectureTask(String projectCode) throws ProjectException;

	/**
	 * �¶��� ���� ������ ��
	 * 
	 * @param projectCode
	 * @return
	 * @throws ProjectException
	 */
	public void assignTrainingOnlineSatisfactionTask(String projectCode) throws ProjectException;

	/**
	 * ���� ������ ��
	 * 
	 * @param projectCode
	 * @return
	 * @throws ProjectException
	 */
	public void assignTrainingSatisfactionTask(String projectCode) throws ProjectException;

	/**
	 * ���� ������� ����
	 * 
	 * @param endProcessCheck
	 * @throws ProjectException
	 */
	public void store(EndProcessCheck endProcessCheck) throws ProjectException;

	/**
	 * ���Ῡ��
	 * 
	 * @param projectCode
	 * @return
	 * @throws ProjectException
	 */
	public boolean isRollingAndRateingFinished(String projectCode) throws ProjectException;

	/**
	 * ���� ���� ���� ����Ʈ ��ȯ
	 * 
	 * @param projectCode
	 * @return
	 * @throws ProjectException
	 */
	public List<String> getEndProcessTaskList(String projectCode) throws ProjectException;
}
