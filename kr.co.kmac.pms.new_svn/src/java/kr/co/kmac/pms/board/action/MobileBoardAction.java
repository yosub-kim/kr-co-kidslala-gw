/*
 * $Id: MobileBoardAction.java,v 1.9 2015/05/18 00:53:02 cvs Exp $
 * creation-date : 2006. 3. 7.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.board.action;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.kmac.pms.attach.form.AttachForm;
import kr.co.kmac.pms.attach.manager.AttachManager;
import kr.co.kmac.pms.attach.manager.AttachTemplateManager;
import kr.co.kmac.pms.board.data.BoardCommentData;
import kr.co.kmac.pms.board.data.BoardData;
import kr.co.kmac.pms.board.data.BoardDataForListMobile;
import kr.co.kmac.pms.board.data.BoardDataForSelect;
import kr.co.kmac.pms.board.exception.BoardException;
import kr.co.kmac.pms.board.manager.BoardManager;
import kr.co.kmac.pms.common.ajax.AjaxUtil;
import kr.co.kmac.pms.common.code.manager.CommonCodeManager;
import kr.co.kmac.pms.common.org.manager.IOrgUnitManager;
import kr.co.kmac.pms.common.util.SessionUtils;
import kr.co.kmac.pms.expertpool.exception.ExpertPoolException;
import kr.co.kmac.pms.expertpool.manager.ExpertPoolManager;
import kr.co.kmac.pms.service.mailsender.manager.PmsInfoMailSender;
import net.mlw.vlh.ValueList;
import net.mlw.vlh.ValueListHandler;
import net.mlw.vlh.ValueListInfo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.struts.DispatchActionSupport;

/**
 * Provide description for "BoardAction"
 * @author halberd
 * @version $Id: MobileBoardAction.java,v 1.9 2015/05/18 00:53:02 cvs Exp $
 */

/**
 * @struts.action name="boardAction" path="/action/MobileBoardAction" parameter="mode" scope="request"
 * @struts.action-forward name="mobileBoardIndex" path="/m/web/board/boardIndex.jsp" redirect="false"
 * @struts.action-forward name="mobileBoardList" path="/m/web/board/boardList.jsp" redirect="false"
 * @struts.action-forward name="mobileBoardView" path="/m/web/board/boardView.jsp" redirect="false"
 * @struts.action-forward name="mobileBoardForm" path="/m/web/board/boardWrite.jsp" redirect="false"
 * @struts.action-forward name="mobileThxBoardView" path="/m/web/board/boardThxView.jsp" redirect="false"
 * @struts.action-forward name="mobileThxBoardForm" path="/m/web/board/boardThxWrite.jsp" redirect="false"
 */
public class MobileBoardAction extends DispatchActionSupport {
	private static final Log logger = LogFactory.getLog(MobileBoardAction.class);
	private BoardManager boardManager;
	private IOrgUnitManager orgUnitManager;
	private PmsInfoMailSender sanctionInfoMailSender;
	private AttachManager attachManager;
	private AttachTemplateManager attachTemplateManager;
	private CommonCodeManager commonCodeManager;
	private ExpertPoolManager expertPoolManager;

	public ActionForward getBoardIndex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		WebApplicationContext wc;

		try {
			wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());

			ValueListHandler vlh = (ValueListHandler) wc.getBean("mobileBoardBean", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();
			Map<String, String> filters = new HashMap<String, String>();
			filters.put(ValueListInfo.PAGING_PAGE, "1");
			filters.put(ValueListInfo.PAGING_NUMBER_PER, String.valueOf(Integer.MAX_VALUE - 1));
			info.setFilters(filters);
			ValueList valueList = vlh.getValueList("mobileBoardIndex", info);
			request.setAttribute("result", valueList.getList());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return mapping.findForward("mobileBoardIndex");
	}

	public void selectBoardList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		int pg = ServletRequestUtils.getIntParameter(request, "pg", 1);
		int pgNumPer = ServletRequestUtils.getIntParameter(request, "pgNumPer", 15);
		String bbsId = ServletRequestUtils.getRequiredStringParameter(request, "bbsId");
		String subject = ServletRequestUtils.getStringParameter(request, "subject", "");
		String ssnJobClass = this.getBoardManager().select2(SessionUtils.getUsername(request));
		String ssnDept = this.getBoardManager().select3(SessionUtils.getUsername(request));

		HashMap<String, Object> map = new HashMap<String, Object>();
		try {
			WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());

			ValueListHandler vlh = (ValueListHandler) wc.getBean("boardListBean", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();
			Map<String, String> filters = new HashMap<String, String>();
			filters.put(ValueListInfo.PAGING_PAGE, String.valueOf(pg));
			// 모바일 페이지 수 (bbsid 조건문 해서 페이지 수 늘리기)
			filters.put(ValueListInfo.PAGING_NUMBER_PER, String.valueOf(pgNumPer));
			filters.put("bbsId", bbsId);
			if (!subject.equals(""))
				filters.put("subject", "%" + subject + "%");
			info.setFilters(filters);
			ValueList valueList = null;
			if (bbsId.equals("managementAll")) {
				valueList = vlh.getValueList("selectExceptionListEntry", info);
				map.put("boardList", BoardDataForListMobile.valueOf(valueList.getList()));
			} else {
				if("2000".equals(ssnDept) || "2040".equals(ssnDept) || "2050".equals(ssnDept) || "9360".equals(ssnDept) || "9362".equals(ssnDept) || "9381".equals(ssnDept)){
					valueList = vlh.getValueList("selectListEntry", info);
					map.put("boardList", BoardDataForListMobile.valueOf(valueList.getList()));
				} else if ("A".equals(ssnJobClass) || "B".equals(ssnJobClass)){
					valueList = vlh.getValueList("selectListEntry3", info);
					map.put("boardList", BoardDataForListMobile.valueOf(valueList.getList()));
				} else if ("J".equals(ssnJobClass)){
					valueList = vlh.getValueList("selectListEntry4", info);
					map.put("boardList", BoardDataForListMobile.valueOf(valueList.getList()));
				} else {
					valueList = vlh.getValueList("selectListEntry5", info);
					map.put("boardList", BoardDataForListMobile.valueOf(valueList.getList()));
				}
			}

			map.put("pagingPage", String.valueOf(pg));
			map.put("pagingEntries",
					pg == valueList.getValueListInfo().getTotalNumberOfPages() ? String.valueOf(valueList.getValueListInfo()
							.getTotalNumberOfEntries()) : String.valueOf(pg * 10));
			map.put("totalNumberOfEntries", String.valueOf(valueList.getValueListInfo().getTotalNumberOfEntries()));
			map.put("totalNumberOfPages", String.valueOf(valueList.getValueListInfo().getTotalNumberOfPages()));
			map.put("codeEntity", commonCodeManager.getCodeEntity("StandardBBS_Master", bbsId));

			AjaxUtil.successWrite(response, map);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			AjaxUtil.failWrite(response);
		}
	}
	
	public ActionForward selectList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		WebApplicationContext wc;

		String bbsId = ServletRequestUtils.getRequiredStringParameter(request, "bbsId");
		String pg = ServletRequestUtils.getStringParameter(request, "pg", "1");
		int pageSize = ServletRequestUtils.getIntParameter(request, "pageSize", 10);
		String keyword = ServletRequestUtils.getStringParameter(request, "keyword", "");
		// Job Date: 2007-06-13 Author: yhyim Description: Reorganizing particular artice using attribute 'ref'
		String ref = ServletRequestUtils.getStringParameter(request, "ref", "0");
		String keyfield = ServletRequestUtils.getStringParameter(request, "keyfield", "");
		// Job Date: 2012-02-24 Author: yhyim Description: 일반 게시판과 프로젝트 게시판 제목을 다르게 보여주기 위한 값 처리
		String projectCode = ServletRequestUtils.getStringParameter(request, "projectCode", "");
		String projectName = ServletRequestUtils.getStringParameter(request, "projectName", "");
		String ssnJobClass = this.getBoardManager().select2(SessionUtils.getUsername(request));
		String ssnDept = this.getBoardManager().select3(SessionUtils.getUsername(request));
		try {
			wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());

			ValueListHandler vlh = (ValueListHandler) wc.getBean("boardListBean", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();
			Map<String, String> filters = new HashMap<String, String>();
			filters.put(ValueListInfo.PAGING_PAGE, pg);
			filters.put("bbsId", bbsId);
			// Job Date: 2007-06-13 Author: yhyim Description: Adding ref to filter
			filters.put("ref", ref);
			filters.put(ValueListInfo.PAGING_NUMBER_PER, String.valueOf(pageSize == -1 ? Integer.MAX_VALUE - 1 : pageSize));

			if (!keyword.equals("")) {
				if (keyfield.equals("subject"))
					filters.put("subject", "%" + keyword + "%");
				else if (keyfield.equals("content"))
					filters.put("content", "%" + keyword + "%");
				else if (keyfield.equals("writer"))
					filters.put("writer", "%" + keyword + "%");
			}

			System.out.println(keyfield);
			System.out.println(keyword);
			info.setFilters(filters);
			// Job Date: 2008-03-24 Author: yhyim Description: 
			if (bbsId.equals("managementAll")) {
				ValueList valueExceptionList = vlh.getValueList("selectExceptionListEntry", info);
				request.setAttribute("result", valueExceptionList);
			} else {
				// admin / 상근 / 상임 게시판 권한 분리
				if("2000".equals(ssnDept) || "2040".equals(ssnDept) || "2050".equals(ssnDept) || "9360".equals(ssnDept) || "9362".equals(ssnDept) || "9381".equals(ssnDept)){
					ValueList valueList = vlh.getValueList("selectListEntry", info);
					request.setAttribute("result", valueList);
				} else if ("A".equals(ssnJobClass) || "B".equals(ssnJobClass)){
					ValueList valueList = vlh.getValueList("selectListEntry3", info);
					request.setAttribute("result", valueList);
				} else if ("J".equals(ssnJobClass)){
					ValueList valueList = vlh.getValueList("selectListEntry4", info);
					request.setAttribute("result", valueList);
				} else {
					ValueList valueList = vlh.getValueList("selectListEntry5", info);
					request.setAttribute("result", valueList);
				}
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		request.setAttribute("bbsId", bbsId);
		request.setAttribute("keyfield", keyfield);
		request.setAttribute("keyword", keyword);
		request.setAttribute("projectCode", projectCode);
		request.setAttribute("projectName", projectName);
		request.setAttribute("dept", ssnDept);
		request.setAttribute("jobClass", ssnJobClass);
		request.setAttribute("codeEntity", commonCodeManager.getCodeEntity("StandardBBS_Master", bbsId));
		return mapping.findForward("mobileBoardList");
	}

	public ActionForward boardView(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String bbsId = request.getParameter("bbsId");
		String seq = request.getParameter("seq");

		BoardDataForSelect boardDataForSelect = new BoardDataForSelect();
		try {
			if (bbsId.equals("managementAll")) {
				bbsId = getBoardManager().select(seq);
			}
			boardDataForSelect = getBoardManager().select(bbsId, seq);
		} catch (BoardException e) {
			logger.error(e.getMessage(), e);
		}
		WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
		request.setAttribute("fileList", this.getAttachManager().selectListForTask(wc, bbsId + "_" + seq, null, null, null, null).getList());
		request.setAttribute("result", boardDataForSelect);
		request.setAttribute("myUserId", SessionUtils.getUsername(request));
		request.setAttribute("bbsId", bbsId);
		request.setAttribute("codeEntity", commonCodeManager.getCodeEntity("StandardBBS_Master", bbsId));
		request.setAttribute("fileList", this.getAttachManager().selectListForTask(wc, bbsId + "_" + seq, null, null, null, null).getList());

		BoardData readLogData = boardDataForSelect.getBoardData();
		readLogData.setReader(SessionUtils.getUsername(request));
		readLogData.setReadIp(request.getRemoteAddr());
		getBoardManager().insertReadLog(readLogData);

		return mapping.findForward("mobileBoardView");
	}

	public ActionForward inputForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String bbsId = ServletRequestUtils.getRequiredStringParameter(request, "bbsId");
		String saveMode = ServletRequestUtils.getStringParameter(request, "saveMode");
		String seq = ServletRequestUtils.getStringParameter(request, "seq", "");
		WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());

		BoardData boardData = new BoardData();
		String modeText = "신규";
		if (!seq.equals("")) {
			boardData = getBoardManager().select(bbsId, seq).getBoardData();
			if (saveMode.equals("UPDATE")) {
				modeText = "수정";
			} else if (saveMode.equals("REPLY")) {
				modeText = "답글";
			}
			request.setAttribute("fileList", this.getAttachManager().selectListForTask(wc, bbsId + "_" + seq, null, null, null, null).getList());
		} else {
			saveMode = "INSERT";
		}

		request.setAttribute("viewMode", "SIMPLE");
		request.setAttribute("fileMode", "WRITE");

		request.setAttribute("bbsId", bbsId);
		request.setAttribute("saveMode", saveMode);
		request.setAttribute("modeText", modeText);
		request.setAttribute("boardData", boardData);
		request.setAttribute("codeEntity", commonCodeManager.getCodeEntity("StandardBBS_Master", bbsId));

		return mapping.findForward("mobileBoardForm");
	}

	public void saveBoard(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		HashMap<String, Serializable> map = new HashMap<String, Serializable>();

		String bbsId = ServletRequestUtils.getRequiredStringParameter(request, "bbsId");
		String saveMode = ServletRequestUtils.getRequiredStringParameter(request, "saveMode");
		String seq = ServletRequestUtils.getStringParameter(request, "seq", "");
		String topArticle = ServletRequestUtils.getStringParameter(request, "topArticle", "N");
		String refSchedule = ServletRequestUtils.getStringParameter(request, "refSchedule", "N");
		String workDate = ServletRequestUtils.getStringParameter(request, "workDate", "");
		String email = ServletRequestUtils.getStringParameter(request, "email","");

		BoardData boardData = (BoardData) form;
		String retSeq = "";
		boardData.setTopArticle(topArticle);
		boardData.setRefSchedule(refSchedule);
		try {
			if (saveMode.equals("INSERT")) {
				boardData.setWriterId(SessionUtils.getUsername(request));
				boardData.setIp(request.getRemoteAddr());
				boardData.setWorkDate(workDate);
				boardData.setEmail(email);
				retSeq = getBoardManager().insert(boardData);
			} else if (saveMode.equals("UPDATE")) {
				boardData = getBoardManager().select(bbsId, seq).getBoardData();
				boardData.setTopArticle(topArticle);
				boardData.setRefSchedule(refSchedule);
				boardData.setWorkDate(workDate);
				boardData.setEmail(email);
				boardData.setSubject(request.getParameter("subject"));
				boardData.setContent(request.getParameter("content"));
				boardData.setIp(request.getRemoteAddr());
				boardData.setWorkDate(workDate);
				retSeq = getBoardManager().update(boardData);
			} else if (saveMode.equals("REPLY")) {
				boardData = getBoardManager().select(bbsId, seq).getBoardData();
				boardData.setSubject(request.getParameter("subject"));
				boardData.setContent(request.getParameter("content"));
				boardData.setEmail(email);
				boardData.setWriterId(SessionUtils.getUsername(request));
				boardData.setIp(request.getRemoteAddr());
				retSeq = getBoardManager().reply(boardData);
			} else if (saveMode.equals("DELETE")) {
				getBoardManager().delete(bbsId, seq);

			}

			/***** 파일 첨부 시작 *****/
			AttachForm attachForm = (AttachForm) form;
			if (attachForm.getAttachFileId() != null && attachForm.getAttachFileId().length > 0) {
				this.getAttachManager().deleteAllInTask(bbsId + "_" + retSeq);
				attachForm.setTaskId(bbsId + "_" + retSeq);
				this.getAttachManager().insert(attachForm);
			}
			/***** 파일 첨부 종료 *****/

			map.put("result", true);
			map.put("resultMsg", "처리되었습니다.");
			AjaxUtil.successWrite(response, map);

		} catch (ExpertPoolException e) {

			logger.info(e.getMessage(), e);
			map.put("result", false);
			map.put("resultMsg", "처리도중 에러 발생");
			map.put("ErrMsg", e);
			AjaxUtil.failWrite(response, map);
		}

	}

	public void saveBoardComment(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		HashMap<String, Serializable> map = new HashMap<String, Serializable>();

		String bbsId = ServletRequestUtils.getRequiredStringParameter(request, "bbsId");
		String seq = ServletRequestUtils.getStringParameter(request, "seq", "");
		String commentContents = ServletRequestUtils.getStringParameter(request, "commentContents", "").replaceAll("\n", "<br/>")
				.replaceAll("\r", "");

		BoardCommentData boardCommentData = new BoardCommentData();
		boardCommentData.setBbsId(bbsId);
		boardCommentData.setSeq(seq);
		boardCommentData.setContent(commentContents);
		boardCommentData.setIp(request.getRemoteAddr());
		boardCommentData.setWriterId(SessionUtils.getUserObjext().getUsername());
		boardCommentData.setWriter(SessionUtils.getUserObjext().getName());
		try {
			getBoardManager().insertBoardCommentData(boardCommentData);

			map.put("result", true);
			map.put("resultMsg", "처리되었습니다.");
			AjaxUtil.successWrite(response, map);

		} catch (ExpertPoolException e) {

			logger.info(e.getMessage(), e);
			map.put("result", false);
			map.put("resultMsg", "처리도중 에러 발생");
			map.put("ErrMsg", e);
			AjaxUtil.failWrite(response, map);
			e.printStackTrace();
		}
	}

	public void deleteBoardComment(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		HashMap<String, Serializable> map = new HashMap<String, Serializable>();

		String bbsId = ServletRequestUtils.getRequiredStringParameter(request, "bbsId");
		String seq = ServletRequestUtils.getRequiredStringParameter(request, "seq");
		String commentSeq = ServletRequestUtils.getRequiredStringParameter(request, "commentSeq");

		try {
			getBoardManager().deleteBoardCommentData(bbsId, seq, commentSeq);

			map.put("result", true);
			map.put("resultMsg", "처리되었습니다.");
			AjaxUtil.successWrite(response, map);

		} catch (ExpertPoolException e) {

			logger.info(e.getMessage(), e);
			map.put("result", false);
			map.put("resultMsg", "처리도중 에러 발생");
			map.put("ErrMsg", e);
			AjaxUtil.failWrite(response, map);
		}
	}

	public AttachTemplateManager getAttachTemplateManager() {
		return attachTemplateManager;
	}

	public void setAttachTemplateManager(AttachTemplateManager attachTemplateManager) {
		this.attachTemplateManager = attachTemplateManager;
	}

	public AttachManager getAttachManager() {
		return attachManager;
	}

	public void setAttachManager(AttachManager attachManager) {
		this.attachManager = attachManager;
	}

	public PmsInfoMailSender getSanctionInfoMailSender() {
		return sanctionInfoMailSender;
	}

	public void setSanctionInfoMailSender(PmsInfoMailSender sanctionInfoMailSender) {
		this.sanctionInfoMailSender = sanctionInfoMailSender;
	}

	public IOrgUnitManager getOrgUnitManager() {
		return this.orgUnitManager;
	}

	public void setOrgUnitManager(IOrgUnitManager orgUnitManager) {
		this.orgUnitManager = orgUnitManager;
	}

	public BoardManager getBoardManager() {
		return this.boardManager;
	}

	public void setBoardManager(BoardManager boardManager) {
		this.boardManager = boardManager;
	}

	public CommonCodeManager getCommonCodeManager() {
		return commonCodeManager;
	}

	public void setCommonCodeManager(CommonCodeManager commonCodeManager) {
		this.commonCodeManager = commonCodeManager;
	}

	public ExpertPoolManager getExpertPoolManager() {
		return expertPoolManager;
	}

	public void setExpertPoolManager(ExpertPoolManager expertPoolManager) {
		this.expertPoolManager = expertPoolManager;
	}

}
