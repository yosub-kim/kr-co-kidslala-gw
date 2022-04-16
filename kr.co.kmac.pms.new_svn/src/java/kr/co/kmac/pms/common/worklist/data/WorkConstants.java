/*
 * $Id: WorkConstants.java,v 1.1 2009/09/19 11:15:39 cvs3 Exp $
 * created by    : jiwoong
 * creation-date : 2008. 9. 17.
 * =========================================================
 * Copyright (c) 2008 Maninsoft, Inc. All rights reserved.
 */
package kr.co.kmac.pms.common.worklist.data;

/**
 * 결재 업무의 상수 정의
 * 
 * @author jiwoong
 * @version $Id: WorkConstants.java,v 1.1 2009/09/19 11:15:39 cvs3 Exp $
 */
public class WorkConstants {

	// WORK_STATE
	public static final String WORK_STATE_READY 			= "WORK_STATE_READY"; 			// 새업무 상태
	public static final String WORK_STATE_OPENED 			= "WORK_STATE_OPENED"; 			// 읽음 상태
	public static final String WORK_STATE_REJECTED_READY 	= "WORK_STATE_REJECTED_READY"; 	// 반려된 새업무 상태
	public static final String WORK_STATE_REJECTED_OPENED 	= "WORK_STATE_REJECTED_OPENED"; // 반려된 읽음 상태
	public static final String WORK_STATE_EXECUTED 			= "WORK_STATE_EXECUTED";		// 업무 실행으로 처리
	public static final String WORK_STATE_REJECTED 			= "WORK_STATE_REJECTED";		// 업무 반려로 처리
	public static final String WORK_STATE_TERMINATED 		= "WORK_STATE_TERMINATED";		// 업무 종결로 처리

	// WORK_TYPE
	// public static final String WORKTYPE_GENERAL_SANCTION = "WORKTYPE_GENERAL_SANCTION"; // 일반 전자 결재
	// public static final String WORKTYPE_PROJECT_LUANCH_SANCTION = "WORKTYPE_PROJECT_LUANCH_SANCTION"; // 프로젝트 시작 결재
	// public static final String WORKTYPE_PROJECT_RUNNING_SANCTION = "WORKTYPE_PROJECT_RUNNING_SANCTION"; // 프로젝트 운영 결재
	// public static final String WORKTYPE_PROJECT_BUDJET_SANCTION = "WORKTYPE_PROJECT_BUDJET_SANCTION"; // 프로젝트 예산 변경 결재
	// public static final String WORKTYPE_PROJECT_SCHEDULE_SANCTION = "WORKTYPE_PROJECT_SCHEDULE_SANCTION"; // 프로젝트 일정 변경 결재
	// public static final String WORKTYPE_PROJECT_MEMBER_SANCTION = "WORKTYPE_PROJECT_MEMBER_SANCTION"; // 프로젝트 인력 변경 결재
	// public static final String WORKTYPE_PROJECT_SALARY_PU_SANCTION = "WORKTYPE_PROJECT_SALARY_PU_SANCTION"; // 프로젝트 PU 강사료 결재
	// public static final String WORKTYPE_PROJECT_SALARY_BU_SANCTION = "WORKTYPE_PROJECT_SALARY_BU_SANCTION"; // 프로젝트 BU 강사료 결재

}