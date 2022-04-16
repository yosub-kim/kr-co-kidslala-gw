/*
 * $Id: BoardAction.java,v 1.9 2016/09/29 23:25:47 cvs Exp $
 * creation-date : 2006. 3. 7.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.board.action;

import java.io.PrintWriter;
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
import kr.co.kmac.pms.board.data.BoardDataForSelect;
import kr.co.kmac.pms.board.exception.BoardException;
import kr.co.kmac.pms.board.manager.BoardManager;
import kr.co.kmac.pms.common.ajax.AjaxUtil;
import kr.co.kmac.pms.common.code.manager.CommonCodeManager;
import kr.co.kmac.pms.common.org.manager.IOrgUnitManager;
import kr.co.kmac.pms.common.util.SessionUtils;
import kr.co.kmac.pms.common.util.StringUtil;
import kr.co.kmac.pms.expertpool.data.ExpertPool;
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
 * TODO Provide description for "BoardAction"
 * @author halberd
 * @version $Id: BoardAction.java,v 1.9 2016/09/29 23:25:47 cvs Exp $
 */

/**
 * @struts.action name="boardAction" path="/action/BoardAction" parameter="mode" scope="request"
 * @struts.action-forward name="boardList" path="/board/board_list.jsp" redirect="false"
 * @struts.action-forward name="boardForm" path="/board/board_form.jsp" redirect="false"
 * @struts.action-forward name="boardView" path="/board/board_view.jsp" redirect="false"
 * @struts.action-forward name="boardView_popup" path="/board/board_view_popup.jsp" redirect="false"
 */
public class BoardAction extends DispatchActionSupport {
	private static final Log logger = LogFactory.getLog(BoardAction.class);
	private BoardManager boardManager;
	private IOrgUnitManager orgUnitManager;
	private PmsInfoMailSender sanctionInfoMailSender;
	private AttachManager attachManager;
	private AttachTemplateManager attachTemplateManager;
	private CommonCodeManager commonCodeManager;
 
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
		return mapping.findForward("boardList");
	}
	
	public ActionForward selectList2(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		WebApplicationContext wc;

		String pg = ServletRequestUtils.getStringParameter(request, "pg", "1");
		int pageSize = ServletRequestUtils.getIntParameter(request, "pageSize", 10);
		String bbsId = ServletRequestUtils.getRequiredStringParameter(request, "bbsId");
		String keyword = ServletRequestUtils.getStringParameter(request, "keyword", "");
		// Job Date: 2007-06-13 Author: yhyim Description: Reorganizing particular artice using attribute 'ref'
		String ref = ServletRequestUtils.getStringParameter(request, "ref", "0");
		String keyfield = ServletRequestUtils.getStringParameter(request, "keyfield", "");
		// Job Date: 2012-02-24 Author: yhyim Description: 일반 게시판과 프로젝트 게시판 제목을 다르게 보여주기 위한 값 처리
		String projectCode = ServletRequestUtils.getStringParameter(request, "projectCode", "");
		String projectName = ServletRequestUtils.getStringParameter(request, "projectName", "");

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

			info.setFilters(filters);
			// Job Date: 2008-03-24 Author: yhyim Description: 占썸영占쏙옙 占심쇽옙 占쌩곤옙
			if (bbsId.equals("managementAll")) {
				ValueList valueExceptionList = vlh.getValueList("selectExceptionListEntry", info);
				request.setAttribute("result", valueExceptionList);
			} else {
				ValueList valueList = vlh.getValueList("selectListEntry2", info);

				request.setAttribute("result", valueList);
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		request.setAttribute("bbsId", bbsId);
		request.setAttribute("keyfield", keyfield);
		request.setAttribute("keyword", keyword);
		request.setAttribute("projectCode", projectCode);
		request.setAttribute("projectName", projectName);
		return mapping.findForward("boardList2");
	}
	
	public ActionForward selectList_pjt(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		WebApplicationContext wc;

		String pg = ServletRequestUtils.getStringParameter(request, "pg", "1");
		int pageSize = ServletRequestUtils.getIntParameter(request, "pageSize", 15);
		String bbsId = ServletRequestUtils.getRequiredStringParameter(request, "bbsId");
		String keyword = ServletRequestUtils.getStringParameter(request, "keyword", "");
		// Job Date: 2007-06-13 Author: yhyim Description: Reorganizing particular artice using attribute 'ref'
		String ref = ServletRequestUtils.getStringParameter(request, "ref", "0");
		String keyfield = ServletRequestUtils.getStringParameter(request, "keyfield", "");
		// Job Date: 2012-02-24 Author: yhyim Description: 일반 게시판과 프로젝트 게시판 제목을 다르게 보여주기 위한 값 처리
		String projectCode = ServletRequestUtils.getStringParameter(request, "projectCode", "");
		String projectName = ServletRequestUtils.getStringParameter(request, "projectName", "");

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

			info.setFilters(filters);
			// Job Date: 2008-03-24 Author: yhyim Description: 占썸영占쏙옙 占심쇽옙 占쌩곤옙
			if (bbsId.equals("managementAll")) {
				ValueList valueExceptionList = vlh.getValueList("selectExceptionListEntry", info);
				request.setAttribute("result", valueExceptionList);
			} else {
				ValueList valueList = vlh.getValueList("selectListEntry_pjt", info);

				request.setAttribute("result", valueList);
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		request.setAttribute("bbsId", bbsId);
		request.setAttribute("keyfield", keyfield);
		request.setAttribute("keyword", keyword);
		request.setAttribute("projectCode", projectCode);
		request.setAttribute("projectName", projectName);
		return mapping.findForward("boardList_pjt");
	}
	
	//qm 모니터링 화면
	/*public ActionForward selectList_qm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		WebApplicationContext wc;

		String pg = ServletRequestUtils.getStringParameter(request, "pg", "1");
		int pageSize = ServletRequestUtils.getIntParameter(request, "pageSize", 10);
		String bbsId = ServletRequestUtils.getRequiredStringParameter(request, "bbsId");
		String keyword = ServletRequestUtils.getStringParameter(request, "keyword", "");
		// Job Date: 2007-06-13 Author: yhyim Description: Reorganizing particular artice using attribute 'ref'
		String ref = ServletRequestUtils.getStringParameter(request, "ref", "0");
		String keyfield = ServletRequestUtils.getStringParameter(request, "keyfield", "");
		// Job Date: 2012-02-24 Author: yhyim Description: 일반 게시판과 프로젝트 게시판 제목을 다르게 보여주기 위한 값 처리
		String projectCode = ServletRequestUtils.getStringParameter(request, "projectCode", "");
		String projectName = ServletRequestUtils.getStringParameter(request, "projectName", "");
		String email = ServletRequestUtils.getStringParameter(request, "email", "");

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

			info.setFilters(filters);
			// Job Date: 2008-03-24 Author: yhyim Description: 
			if (bbsId.equals("managementAll")) {
				ValueList valueExceptionList = vlh.getValueList("selectExceptionListEntry", info);
				request.setAttribute("result", valueExceptionList);
			} else {
				ValueList valueList = vlh.getValueList("selectListEntryQM", info);

				request.setAttribute("result", valueList);
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		request.setAttribute("bbsId", bbsId);
		request.setAttribute("keyfield", keyfield);
		request.setAttribute("keyword", keyword);
		request.setAttribute("projectCode", projectCode);
		request.setAttribute("projectName", projectName);
		return mapping.findForward("boardList2");
	}*/
	
	public ActionForward selectList_home(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		WebApplicationContext wc;

		String pg = ServletRequestUtils.getStringParameter(request, "pg", "1");
		int pageSize = ServletRequestUtils.getIntParameter(request, "pageSize", 10);
		String bbsId = ServletRequestUtils.getRequiredStringParameter(request, "bbsId");
		String keyword = ServletRequestUtils.getStringParameter(request, "keyword", "");
		// Job Date: 2007-06-13 Author: yhyim Description: Reorganizing particular artice using attribute 'ref'
		String ref = ServletRequestUtils.getStringParameter(request, "ref", "0");
		String keyfield = ServletRequestUtils.getStringParameter(request, "keyfield", "");
		// Job Date: 2012-02-24 Author: yhyim Description: 일반 게시판과 프로젝트 게시판 제목을 다르게 보여주기 위한 값 처리
		String projectCode = ServletRequestUtils.getStringParameter(request, "projectCode", "");
		String projectName = ServletRequestUtils.getStringParameter(request, "projectName", "");
		String writerId = ServletRequestUtils.getRequiredStringParameter(request, "writerId");
		String ssn = ServletRequestUtils.getStringParameter(request, "writerId", "");

		try {
			wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());

			ValueListHandler vlh = (ValueListHandler) wc.getBean("boardListBean", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();
			Map<String, String> filters = new HashMap<String, String>();
			filters.put(ValueListInfo.PAGING_PAGE, pg);
			filters.put("bbsId", bbsId);
			// Job Date: 2007-06-13 Author: yhyim Description: Adding ref to filter
			filters.put("ref", ref);
			filters.put("writerId", writerId);
			filters.put(ValueListInfo.PAGING_NUMBER_PER, String.valueOf(pageSize == -1 ? Integer.MAX_VALUE - 1 : pageSize));

			if (!keyword.equals("")) {
				if (keyfield.equals("subject"))
					filters.put("subject", "%" + keyword + "%");
			}
			filters.put("ssn", ssn);
			info.setFilters(filters);
			// Job Date: 2008-03-24 Author: yhyim Description:
			ValueList valueList = vlh.getValueList("selectListEntryHome", info);
			request.setAttribute("result", valueList);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		request.setAttribute("bbsId", bbsId);
		request.setAttribute("keyfield", keyfield);
		request.setAttribute("keyword", keyword);
		request.setAttribute("projectCode", projectCode);
		request.setAttribute("projectName", projectName);
		request.setAttribute("writerId", writerId);
		return mapping.findForward("boardList_home");
	}

	public ActionForward boardView(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String bbsId = request.getParameter("bbsId");
		String seq = request.getParameter("seq");
		// Job Date: 2012-02-24 Author: yhyim Description: 일반 게시판과 프로젝트 게시판 제목을 다르게 보여주기 위한 값 처리
		String projectCode = ServletRequestUtils.getStringParameter(request, "projectCode", "");
		String projectName = ServletRequestUtils.getStringParameter(request, "projectName", "");
		String ssn = SessionUtils.getUsername(request);
		String ssnJobClass = this.getBoardManager().select2(SessionUtils.getUsername(request));

		BoardDataForSelect boardDataForSelect = new BoardDataForSelect();
		try {
			if(bbsId.equals("managementAll")){
				bbsId = getBoardManager().select(seq);
			}
			if("A".equals(ssnJobClass)){
				boardDataForSelect = getBoardManager().select(bbsId, seq);				
			}else if("B".equals(ssnJobClass)){
				boardDataForSelect = getBoardManager().select(bbsId, seq);
			}else if("J".equals(ssnJobClass)){
				boardDataForSelect = getBoardManager().select2(bbsId, seq);
			}else{
				boardDataForSelect = getBoardManager().select3(bbsId, seq);
			}
		} catch (BoardException e) {
			logger.error(e.getMessage(), e);
		}
		/*
		 * String divCode = null; try { divCode = ((IGroup) (this.getOrgUnitManager().findGroupOfUser(SessionUtils.getUsername(request),
		 * IGroupConstants.GROUP_ORG_UNIT)).get(0)).getId().substring(0, 3)+ "0"; } catch (Exception e) { divCode = "non mapping:" +
		 * SessionUtils.getUsername(request); } IFileResource[] attFile = getFileReouces(boardDataForSelect.getBoardData().getMaskName());
		 */
		WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
		request.setAttribute("viewMode", "SIMPLE");
		request.setAttribute("fileMode", "READ");
		request.setAttribute("attachOutputType", this.getAttachTemplateManager().selectOutputTypeCodeList(wc, null, "BT4").getList());
		request.setAttribute("fileList", this.getAttachManager().selectListForTask(wc, bbsId + "_" + seq, null, null, null, null).getList());

		request.setAttribute("result", boardDataForSelect);
		request.setAttribute("myUserId", SessionUtils.getUsername(request));
		request.setAttribute("bbsId", bbsId);
		request.setAttribute("projectCode", projectCode);
		request.setAttribute("projectName", projectName);
		request.setAttribute("codeEntity", commonCodeManager.getCodeEntity("StandardBBS_Master", bbsId));
		request.setAttribute("buttonState", this.getBoardManager().selectRecommendCnt2(bbsId, seq, ssn));
		BoardData readLogData = boardDataForSelect.getBoardData();
		readLogData.setReader(SessionUtils.getUsername(request));
		readLogData.setReadIp(request.getRemoteAddr());
		getBoardManager().insertReadLog(readLogData);
		
		return mapping.findForward("boardView");
	}
	
	// 좋아요 버튼 비동기식 처리 (210404 김요섭)
	public ActionForward boardViewRecCnt(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String bbsId = request.getParameter("bbsId");
		String seq = request.getParameter("seq");
		String count = this.getBoardManager().selectRecommendCnt(bbsId, seq);
		PrintWriter out = response.getWriter();
		out.println(count);
		out.close();
		
		return null;
	}
	
	// 좋아요 버튼 이미지 url 처리 (2104013 김요섭)
	public ActionForward boardViewRecResult(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");	
		
		String bbsId = request.getParameter("bbsId");
			String seq = request.getParameter("seq");
			String ssn = SessionUtils.getUsername(request);
			String count = this.getBoardManager().selectRecommendCnt2(bbsId, seq, ssn);
			PrintWriter out = response.getWriter();
			if(Integer.parseInt(count) > 0){
				String result = "<i class='mdi mdi-heart'></i>좋아요";
				out.println(result);
			}else{
				String result = "<i class='mdi mdi-heart'></i>좋아요";
				out.println(result);
			}
			out.close();
			
			return null;
	}
	
	// 좋아요 버튼 이미지 url 처리 (2104013 김요섭)
	public ActionForward boardViewRecResultForMobile(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");	
		
		String bbsId = request.getParameter("bbsId");
			String seq = request.getParameter("seq");
			String ssn = SessionUtils.getUsername(request);
			String count = this.getBoardManager().selectRecommendCnt2(bbsId, seq, ssn);
			PrintWriter out = response.getWriter();
			if(Integer.parseInt(count) > 0){
				String result = "<i class='mdi mdi-heart'></i>";
				out.println(result);
			}else{
				String result = "<i class='mdi mdi-heart'></i>";
				out.println(result);
			}
			out.close();
			
			return null;
	}
	
	// 좋아요 옆 좋아합니다 랜덤처리 (210416 김요섭)
	public ActionForward boardViewContentResult(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		String bbsId = request.getParameter("bbsId");
		String seq = request.getParameter("seq");
		String content = this.getBoardManager().selectRecommendContent(bbsId, seq);
		PrintWriter out = response.getWriter();
		out.println(content);
		out.close();
		
		return null;
	}
	
	public ActionForward boardView_qm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String bbsId = request.getParameter("bbsId");
		String seq = request.getParameter("seq");
		// Job Date: 2012-02-24 Author: yhyim Description: 일반 게시판과 프로젝트 게시판 제목을 다르게 보여주기 위한 값 처리
		String projectCode = ServletRequestUtils.getStringParameter(request, "projectCode", "");
		String projectName = ServletRequestUtils.getStringParameter(request, "projectName", "");
		String email = ServletRequestUtils.getStringParameter(request, "email", "");

		BoardDataForSelect boardDataForSelect = new BoardDataForSelect();
		try {
			if(bbsId.equals("managementAll")){
				bbsId = getBoardManager().select(seq);
			}
			boardDataForSelect = getBoardManager().select(bbsId, seq);
		} catch (BoardException e) {
			logger.error(e.getMessage(), e);
		}
		/*
		 * String divCode = null; try { divCode = ((IGroup) (this.getOrgUnitManager().findGroupOfUser(SessionUtils.getUsername(request),
		 * IGroupConstants.GROUP_ORG_UNIT)).get(0)).getId().substring(0, 3)+ "0"; } catch (Exception e) { divCode = "non mapping:" +
		 * SessionUtils.getUsername(request); } IFileResource[] attFile = getFileReouces(boardDataForSelect.getBoardData().getMaskName());
		 */
		WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
		request.setAttribute("viewMode", "SIMPLE");
		request.setAttribute("fileMode", "READ");
		request.setAttribute("attachOutputType", this.getAttachTemplateManager().selectOutputTypeCodeList(wc, null, "BT4").getList());
		request.setAttribute("fileList", this.getAttachManager().selectListForTask(wc, bbsId + "_" + seq, null, null, null, null).getList());

		request.setAttribute("result", boardDataForSelect);
		request.setAttribute("myUserId", SessionUtils.getUsername(request));
		request.setAttribute("bbsId", bbsId);
		request.setAttribute("projectCode", projectCode);
		request.setAttribute("projectName", projectName);
		request.setAttribute("email", email);
		request.setAttribute("codeEntity", commonCodeManager.getCodeEntity("StandardBBS_Master", bbsId));
		
		BoardData readLogData = boardDataForSelect.getBoardData();
		readLogData.setReader(SessionUtils.getUsername(request));
		readLogData.setReadIp(request.getRemoteAddr());
		getBoardManager().insertReadLog(readLogData);
		
		return mapping.findForward("boardView_qm");
	}
	
	public ActionForward boardView_home(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String bbsId = request.getParameter("bbsId");
		String seq = request.getParameter("seq");
		// Job Date: 2012-02-24 Author: yhyim Description: 일반 게시판과 프로젝트 게시판 제목을 다르게 보여주기 위한 값 처리
		String projectCode = ServletRequestUtils.getStringParameter(request, "projectCode", "");
		String projectName = ServletRequestUtils.getStringParameter(request, "projectName", "");
		String email = ServletRequestUtils.getStringParameter(request, "email", "");

		BoardDataForSelect boardDataForSelect = new BoardDataForSelect();
		try {
			if(bbsId.equals("managementAll")){
				bbsId = getBoardManager().select(seq);
			}
			boardDataForSelect = getBoardManager().select(bbsId, seq);
		} catch (BoardException e) {
			logger.error(e.getMessage(), e);
		}
		/*
		 * String divCode = null; try { divCode = ((IGroup) (this.getOrgUnitManager().findGroupOfUser(SessionUtils.getUsername(request),
		 * IGroupConstants.GROUP_ORG_UNIT)).get(0)).getId().substring(0, 3)+ "0"; } catch (Exception e) { divCode = "non mapping:" +
		 * SessionUtils.getUsername(request); } IFileResource[] attFile = getFileReouces(boardDataForSelect.getBoardData().getMaskName());
		 */
		WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
		request.setAttribute("viewMode", "SIMPLE");
		request.setAttribute("fileMode", "READ");
		request.setAttribute("attachOutputType", this.getAttachTemplateManager().selectOutputTypeCodeList(wc, null, "BT4").getList());
		request.setAttribute("fileList", this.getAttachManager().selectListForTask(wc, bbsId + "_" + seq, null, null, null, null).getList());

		request.setAttribute("result", boardDataForSelect);
		request.setAttribute("myUserId", SessionUtils.getUsername(request));
		request.setAttribute("bbsId", bbsId);
		request.setAttribute("projectCode", projectCode);
		request.setAttribute("projectName", projectName);
		request.setAttribute("email", email);
		request.setAttribute("codeEntity", commonCodeManager.getCodeEntity("StandardBBS_Master", bbsId));
		
		BoardData readLogData = boardDataForSelect.getBoardData();
		readLogData.setReader(SessionUtils.getUsername(request));
		readLogData.setReadIp(request.getRemoteAddr());
		getBoardManager().insertReadLog(readLogData);
		
		return mapping.findForward("boardView_home");
	}
	
	public ActionForward boardView_popup(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String bbsId = request.getParameter("bbsId");
		String seq = request.getParameter("seq");

		BoardDataForSelect boardDataForSelect = new BoardDataForSelect();
		try {
			if(bbsId.equals("managementAll")){
				bbsId = getBoardManager().select(seq);
			}
			boardDataForSelect = getBoardManager().select(bbsId, seq);
		} catch (BoardException e) {
			logger.error(e.getMessage(), e);
		}
		/*
		 * String divCode = null; try { divCode = ((IGroup) (this.getOrgUnitManager().findGroupOfUser(SessionUtils.getUsername(request),
		 * IGroupConstants.GROUP_ORG_UNIT)).get(0)).getId().substring(0, 3)+ "0"; } catch (Exception e) { divCode = "non mapping:" +
		 * SessionUtils.getUsername(request); } IFileResource[] attFile = getFileReouces(boardDataForSelect.getBoardData().getMaskName());
		 */
		WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
		request.setAttribute("viewMode", "SIMPLE");
		request.setAttribute("fileMode", "READ");
		request.setAttribute("attachOutputType", this.getAttachTemplateManager().selectOutputTypeCodeList(wc, null, "BT4").getList());
		request.setAttribute("fileList", this.getAttachManager().selectListForTask(wc, bbsId + "_" + seq, null, null, null, null).getList());

		request.setAttribute("result", boardDataForSelect);
		request.setAttribute("myUserId", SessionUtils.getUsername(request));
		request.setAttribute("bbsId", bbsId);
		request.setAttribute("codeEntity", commonCodeManager.getCodeEntity("StandardBBS_Master", bbsId));
		
		BoardData readLogData = boardDataForSelect.getBoardData();
		readLogData.setReader(SessionUtils.getUsername(request));
		readLogData.setReadIp(request.getRemoteAddr());
		getBoardManager().insertReadLog(readLogData); 
		
		return mapping.findForward("boardView_popup");
	}
	

	public ActionForward inputForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String bbsId = ServletRequestUtils.getRequiredStringParameter(request, "bbsId");
		String saveMode = ServletRequestUtils.getStringParameter(request, "saveMode");
		String seq = ServletRequestUtils.getStringParameter(request, "seq", "");
		String projectCode = ServletRequestUtils.getStringParameter(request, "projectCode", "");
		String projectName = ServletRequestUtils.getStringParameter(request, "projectName", "");
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
		request.setAttribute("attachOutputType", this.getAttachTemplateManager().selectOutputTypeCodeList(wc, null, "BT4").getList());

		request.setAttribute("bbsId", bbsId);
		request.setAttribute("saveMode", saveMode);
		request.setAttribute("modeText", modeText);
		request.setAttribute("projectCode", projectCode);
		request.setAttribute("projectName", projectName);
		request.setAttribute("boardData", boardData);

		return mapping.findForward("boardForm");
	}
	
	public ActionForward inputForm_qm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String bbsId = ServletRequestUtils.getRequiredStringParameter(request, "bbsId");
		String saveMode = ServletRequestUtils.getStringParameter(request, "saveMode");
		String seq = ServletRequestUtils.getStringParameter(request, "seq", "");
		String projectCode = ServletRequestUtils.getStringParameter(request, "projectCode", "");
		String projectName = ServletRequestUtils.getStringParameter(request, "projectName", "");
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
		request.setAttribute("attachOutputType", this.getAttachTemplateManager().selectOutputTypeCodeList(wc, null, "BT4").getList());

		request.setAttribute("bbsId", bbsId);
		request.setAttribute("saveMode", saveMode);
		request.setAttribute("modeText", modeText);
		request.setAttribute("projectCode", projectCode);
		request.setAttribute("projectName", projectName);
		request.setAttribute("boardData", boardData);

		return mapping.findForward("boardForm_qm");
	}
	
	public ActionForward inputForm_home(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String bbsId = ServletRequestUtils.getRequiredStringParameter(request, "bbsId");
		String saveMode = ServletRequestUtils.getStringParameter(request, "saveMode");
		String seq = ServletRequestUtils.getStringParameter(request, "seq", "");
		String projectCode = ServletRequestUtils.getStringParameter(request, "projectCode", "");
		String projectName = ServletRequestUtils.getStringParameter(request, "projectName", "");
		String writerId = (SessionUtils.getUsername(request));
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
		request.setAttribute("attachOutputType", this.getAttachTemplateManager().selectOutputTypeCodeList(wc, null, "BT4").getList());

		request.setAttribute("bbsId", bbsId);
		request.setAttribute("saveMode", saveMode);
		request.setAttribute("modeText", modeText);
		request.setAttribute("projectCode", projectCode);
		request.setAttribute("projectName", projectName);
		request.setAttribute("boardData", boardData);
		request.setAttribute("writerId", writerId);
		return mapping.findForward("boardForm_home");
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

				/* jobDate: 2016-09-20	Description: disable sending result mail
				try {
					if (Integer.parseInt(request.getParameter("bbsId")) > 1 && !boardData.getPrjType().equals("prj")) {
						boardData.setUpdateTag("N");
						getSanctionInfoMailSender().sendBoardInfo(boardData);
					}
				} catch (Exception e) {
					logger.info("Exception occured during send result mail.");
				}
				*/
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
		String commentContents = ServletRequestUtils.getStringParameter(request, "commentContents", "").replaceAll("\n", "<br/>").replaceAll("\r", "");
		
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
	
public void saveBoardComment2(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HashMap<String, Serializable> map = new HashMap<String, Serializable>();
		
		String bbsId = "Orgdb";
		String seq = ServletRequestUtils.getStringParameter(request, "seq", "");
		String commentContents = ServletRequestUtils.getStringParameter(request, "commentContents", "").replaceAll("\n", "<br/>").replaceAll("\r", "");
		
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
	
	// Job Date: 2008-09-23 Author: yhyim Description:
	public ActionForward selectPrjList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		WebApplicationContext wc;

		int pageSize = Integer.parseInt(StringUtil.isNull(request.getParameter("pageSize"), "15"));
		String pg = ServletRequestUtils.getStringParameter(request, "pg", "1");
		String keyword = ServletRequestUtils.getStringParameter(request, "keyword", "");
		String writerDept = ServletRequestUtils.getStringParameter(request, "writerDept", "");
		String writerDivType = ServletRequestUtils.getStringParameter(request, "writerDivType", "");
		String writer = ServletRequestUtils.getRequiredStringParameter(request, "writer");

		String from = ServletRequestUtils.getRequiredStringParameter(request, "from");
		String to = ServletRequestUtils.getRequiredStringParameter(request, "to");
		String keyfield = ServletRequestUtils.getStringParameter(request, "keyfield", "");

		try {
			wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());

			ValueListHandler vlh = (ValueListHandler) wc.getBean("boardListBean", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();
			Map<String, String> filters = new HashMap<String, String>();
			filters.put(ValueListInfo.PAGING_PAGE, pg);
			filters.put(ValueListInfo.PAGING_NUMBER_PER, String.valueOf(pageSize == -1 ? Integer.MAX_VALUE - 1 : pageSize));
			filters.put("from", from);
			filters.put("to", to);
			filters.put("writer", writer);

			System.out.println("이름:" + writer);
			if (!writerDept.equals("")) {
				if (!writerDivType.equals("") && writerDivType.equals("div"))
					filters.put("writerDept", writerDept.substring(0, 3) + "_");
				else
					filters.put("writerDept", writerDept);
			}

			if (!keyword.equals("")) {
				if (keyfield.equals("subject"))
					filters.put("subject", "%" + keyword + "%");
				else if (keyfield.equals("content"))
					filters.put("content", "%" + keyword + "%");
			}

			info.setFilters(filters);

			ValueList valueList = vlh.getValueList("selectPrjListEntry", info);
			request.setAttribute("result", valueList);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return mapping.findForward("boardList");
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

	
}
