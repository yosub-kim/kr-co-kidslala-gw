/*
 * $Id: SanctionConstants.java,v 1.5 2013/10/17 13:07:51 cvs Exp $
 * created by    : jiwoong
 * creation-date : 2008. 9. 17.
 * =========================================================
 * Copyright (c) 2008 Maninsoft, Inc. All rights reserved.
 */
package kr.co.kmac.pms.sanction.general.data;

/**
 * 결재 업무의 상수 정의
 * 
 * @author jiwoong
 * @version $Id: SanctionConstants.java,v 1.5 2013/10/17 13:07:51 cvs Exp $
 */
public class SanctionConstants {

	// SANCTION_STATE
	public static final String SANCTION_STATE_DRAFT 			= "SANCTION_STATE_DRAFT"; 			// 작성
	public static final String SANCTION_STATE_REJECT_DRAFT		= "SANCTION_STATE_REJECT_DRAFT"; 	// 반려된 기안상태
	public static final String SANCTION_STATE_REVIEW 			= "SANCTION_STATE_REVIEW"; 			// 검토
	public static final String SANCTION_STATE_CHECK             = "SANCTION_STATE_CHECK"; 			// 확인
	public static final String SANCTION_STATE_APPROVE 			= "SANCTION_STATE_APPROVE"; 		// 승인
	public static final String SANCTION_STATE_ASSIST1 			= "SANCTION_STATE_ASSIST1"; 		// 협의1
	public static final String SANCTION_STATE_ASSIST2 			= "SANCTION_STATE_ASSIST2"; 		// 협의2
	public static final String SANCTION_STATE_ASSIST3 			= "SANCTION_STATE_ASSIST3"; 		// 협의3
	public static final String SANCTION_STATE_ASSIST4 			= "SANCTION_STATE_ASSIST4"; 		// 협의4
	public static final String SANCTION_STATE_SPPORT_DRAFT 		= "SANCTION_STATE_SUPPORT_DRAFT"; 	// 지원실 기안
	public static final String SANCTION_STATE_SPPORT_REVIEW 	= "SANCTION_STATE_SUPPORT_REVIEW"; 	// 지원실 검토
	public static final String SANCTION_STATE_SPPORT_APPROVE 	= "SANCTION_STATE_SUPPORT_APPROVE"; // 지원실 승인
	public static final String SANCTION_STATE_CEO 				= "SANCTION_STATE_CEO"; 			// 대표이사
	public static final String SANCTION_STATE_COMPLETE		 	= "SANCTION_STATE_COMPLETE"; 		// 완료

	public static final String SANCTION_STATE_RATING				= "SANCTION_STATE_RATING"; 					// 평가
	public static final String SANCTION_STATE_ENDRIVIEW_DRAFT		= "SANCTION_STATE_ENDRIVIEW_DRAFT"; 		// 리뷰지
	public static final String SANCTION_STATE_ENDRIVIEW_RIVIEW		= "SANCTION_STATE_ENDRIVIEW_RIVIEW"; 		// 리뷰지
	public static final String SANCTION_STATE_ENDRIVIEW_APPROVE		= "SANCTION_STATE_ENDRIVIEW_APPROVE"; 		// 리뷰지
	public static final String SANCTION_STATE_ENDRIVIEW_VERIFICATE	= "SANCTION_STATE_ENDRIVIEW_VERIFICATE"; 	// 리뷰지
}