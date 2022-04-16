/*
 * $Id: ExpertPoolManagerAction.java,v 1.55 2019/03/28 04:28:01 cvs Exp $
 * created by : jiwoongLee creation-date : 2006. 1. 17
 * ========================================================= Copyright (c) 2006
 * Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.expertpool.action;

import java.net.URLDecoder;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.kmac.pms.common.ajax.AjaxUtil;
import kr.co.kmac.pms.common.code.exception.CodeException;
import kr.co.kmac.pms.common.util.DateTime;
import kr.co.kmac.pms.common.util.SessionUtils;
import kr.co.kmac.pms.common.util.StringUtil;
import kr.co.kmac.pms.expertpool.data.BankAccount;
import kr.co.kmac.pms.expertpool.data.ExpertPool;
import kr.co.kmac.pms.expertpool.exception.ExpertPoolException;
import kr.co.kmac.pms.expertpool.form.ExpertPoolForm;
import kr.co.kmac.pms.expertpool.manager.ExpertPoolManager;
import kr.co.kmac.pms.project.search.manager.HashTagManager;
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
 * 전문가 관리
 * 
 * @author jiwoongLee
 * @version $Id: ExpertPoolManagerAction.java,v 1.15 2010/08/18 08:49:43 cvs1
 *          Exp $
 */
/**
 * @struts.action name="expertPoolManagerAction" path="/action/ExpertPoolManagerAction" parameter="mode" scope="request"
 * @struts.action-forward name="expertPoolList" path="/expertPool/expertPoolList.jsp" redirect="false"
 * @struts.action-forward name="expertPoolExtList" path="/expertPool/expertPoolExtList.jsp" redirect="false"
 * @struts.action-forward name="expertPoolWorkPeriodList" path="/expertPool/expertPoolWorkPeriodList.jsp" redirect="false"
 * @struts.action-forward name="expertPoolWorkPeriodList_exp" path="/expertPool/expertPoolWorkPeriodList_exp.jsp" redirect="false"
 * @struts.action-forward name="expertPoolWorkPeriodList_vac" path="/expertPool/expertPoolWorkPeriodList_vac.jsp" redirect="false"
 * @struts.action-forward name="expertPoolWorkPeriodList_performance" path="/expertPool/expertPoolWorkPeriodList_performance.jsp" redirect="false"
 * @struts.action-forward name="projectExpertPoolList" path="/expertPool/projectExpertPoolList.jsp" redirect="false"
 * @struts.action-forward name="restrictExpertPoolList" path="/expertPool/restrictExpertPoolList.jsp" redirect="false"
 * @struts.action-forward name="readyExpertPoolList" path="/expertPool/readyExpertPoolList.jsp" redirect="false"
 * @struts.action-forward name="orgTable" path="/common/org/orgTable.jsp" redirect="false"
 * @struts.action-forward name="expertPoolForm" path="/expertPool/expertPoolForm.jsp" redirect="false"
 * @struts.action-forward name="expertPoolView" path="/expertPool/expertPoolView.jsp" redirect="false"
 * @struts.action-forward name="expertPoolViewForMobile" path="/m/web/expertpool/expertpoolView.jsp" redirect="false"
 * @struts.action-forward name="orgFinderPage" path="/common/org/orgFinder.jsp" redirect="false"
 * @struts.action-forward name="projectMemberOrgPopup" path="/sanction/memberChabngeOrgPopup/projectMemberOrgFinder.jsp" redirect="false"
 * @struts.action-forward name="zipCodeSearch" path="/popup/zipCodeSearch.jsp" redirect="false"
 * @struts.action-forward name="relationWithKmacSearch" path="/popup/relationWithKmac.jsp" redirect="false"
 * @struts.action-forward name="MyInfoForm" path="/expertPool/myInfoForm.jsp" redirect="false"
 * @struts.action-forward name="expertPoolList2" path="/expertPool/jsp/expertPoolList2.jsp" redirect="false"
 * @struts.action-forward name="expertPoolDetail" path="/expertPool/jsp/expertPoolDetail.jsp" redirect="false"
 * @struts.action-forward name="saveReadyExpertPoolListToExcel" path="/expertPool/saveReadyExpertPoolListToExcel.jsp" redirect="false"
 * @struts.action-forward name="saveRestrictExpertPoolListToExcel" path="/expertPool/saveRestrictExpertPoolListToExcel.jsp" redirect="false"
 * @struts.action-forward name="saveRestrictExpertPoolListToExcel_schedule" path="/expertPool/saveRestrictExpertPoolListToExcel_schedule.jsp" redirect="false"
 * @struts.action-forward name="mobileExpertPoolList" path="/m/web/expertpool/expertpoolList.jsp" redirect="false"
 */
public class ExpertPoolManagerAction extends DispatchActionSupport {

	private static final Log logger = LogFactory.getLog(ExpertPoolManagerAction.class);
	private ExpertPoolManager expertPoolManager;
	private HashTagManager hashTagManager;

	/**
	 * @param expertPoolManager The expertPoolManager to set.
	 */
	public void setExpertPoolManager(ExpertPoolManager expertPoolManager) {
		this.expertPoolManager = expertPoolManager;
	}

	/**
	 * @return Returns the expertPoolManager.
	 */
	public ExpertPoolManager getExpertPoolManager() {
		return this.expertPoolManager;
	}
	
	public HashTagManager getHashTagManager() {
		return hashTagManager;
	}

	public void setHashTagManager(HashTagManager hashTagManager) {
		this.hashTagManager = hashTagManager;
	}

	public void retrieve(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		HashMap<String, Object> map = new HashMap<String, Object>();

		String ssn = StringUtil.isNull(request.getParameter("ssn"), "");
		if ("".equals(ssn)) {
			ssn = SessionUtils.getUsername(request);
		}
		ExpertPool expertPool = expertPoolManager.retrieve(ssn);

		String drawDownLoadPath1 = "";
		String drawDownLoadPath2 = "";
		if (expertPool.getPhoto() != null && !"".equals(expertPool.getPhoto())) {
			drawDownLoadPath1 = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath()
					+ "/servlet/PhotoDownLoadServlet?fileId=" + expertPool.getPhoto();
		}
		if (expertPool.getResume() != null && !"".equals(expertPool.getResume())) {
			drawDownLoadPath2 = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath()
					+ "/servlet/RepositoryDownLoadServlet?fileId=" + expertPool.getResume();
		}

		request.setAttribute("expertPool", expertPool);
		request.setAttribute("photoDownLoadPath", drawDownLoadPath1);
		request.setAttribute("resumeDownLoadPath", drawDownLoadPath2);

		map.put("expertPool", expertPool);
		map.put("photoDownLoadPath", drawDownLoadPath1);
		map.put("resumeDownLoadPath", drawDownLoadPath2);

		AjaxUtil.successWrite(response, map);

	}

	/**
	 * 전문가관리 상세보기 화면
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward infoview(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
 
		String ssn = ServletRequestUtils.getStringParameter(request, "ssn", "");

		if ("".equals(ssn)) {
			ssn = SessionUtils.getUsername(request);
		}
		ExpertPool expertPool = expertPoolManager.retrieve(ssn);
		BankAccount bankAccount = expertPoolManager.retrieveBankAccount(expertPool);

		// //
		WebApplicationContext wc;
		try {
			String pg = ServletRequestUtils.getStringParameter(request, "pg", "1");
			int pageSize = ServletRequestUtils.getIntParameter(request, "pageSize", 10);
			wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh = (ValueListHandler) wc.getBean("expoertPoolCategory", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();

			Map<String, String> filters = new HashMap<String, String>();
			filters.put(ValueListInfo.PAGING_PAGE, pg);
			filters.put("ssn", ssn);
			filters.put(ValueListInfo.PAGING_NUMBER_PER, String.valueOf(pageSize == -1 ? Integer.MAX_VALUE - 1 : pageSize));
			info.setFilters(filters);

			ValueList result = vlh.getValueList("select", info);
			request.setAttribute("projectInfoList", result);
			/*해시태그 추가*/
			request.setAttribute("hashTagList", this.getHashTagManager().getExpertHashTag(ssn, "SHOW"));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		// //

		request.setAttribute("expertPool", expertPool);
		request.setAttribute("bankAccount", bankAccount);

		return mapping.findForward("expertPoolView");

	}
	
	/**
	 * 전문가관리 상세보기 화면
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveToExcel(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		String ssn = ServletRequestUtils.getStringParameter(request, "ssn", "");
		ExpertPool expertPool = expertPoolManager.retrieve(ssn);
		
		String fileName = "프로젝트 참여 이력("+expertPool.getName()+").xls";
		response.setHeader("Content-Disposition", "attachment; filename=" + new String(fileName.getBytes("EUC-KR"), "8859_1") + ";"); 
	    response.setHeader("Content-Description", "JSP Generated Data");
		BankAccount bankAccount = expertPoolManager.retrieveBankAccount(expertPool);

		// //
		WebApplicationContext wc;
		try {
			wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh = (ValueListHandler) wc.getBean("expoertPoolCategory", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();

			Map<String, String> filters = new HashMap<String, String>();
			filters.put("ssn", ssn);
			info.setFilters(filters);

			ValueList result = vlh.getValueList("select2", info);
			request.setAttribute("projectInfoList", result);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		// //

		request.setAttribute("expertPool", expertPool);
		request.setAttribute("bankAccount", bankAccount);

		return mapping.findForward("saveToExcel");

	}

	/**
	 * 전문가관리 상세보기 화면
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward infoviewForMobile(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String ssn = ServletRequestUtils.getStringParameter(request, "ssn", "");

		if ("".equals(ssn)) {
			ssn = SessionUtils.getUsername(request);
		}
		ExpertPool expertPool = expertPoolManager.retrieve(ssn);
		BankAccount bankAccount = expertPoolManager.retrieveBankAccount(expertPool);

		// //
		WebApplicationContext wc;
		try {
			String pg = ServletRequestUtils.getStringParameter(request, "pg", "1");
			int pageSize = ServletRequestUtils.getIntParameter(request, "pageSize", 10);
			wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh = (ValueListHandler) wc.getBean("expoertPoolCategory", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();

			Map<String, String> filters = new HashMap<String, String>();
			filters.put(ValueListInfo.PAGING_PAGE, pg);
			filters.put("ssn", ssn);
			filters.put(ValueListInfo.PAGING_NUMBER_PER, String.valueOf(pageSize == -1 ? Integer.MAX_VALUE - 1 : pageSize));
			info.setFilters(filters);

			ValueList result = vlh.getValueList("select", info);
			request.setAttribute("projectInfoList", result);
			/*해시태그 추가*/
			request.setAttribute("hashTagList", this.getHashTagManager().getExpertHashTag(ssn, "SHOW"));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		// //

		request.setAttribute("expertPool", expertPool);
		request.setAttribute("bankAccount", bankAccount);

		return mapping.findForward("expertPoolViewForMobile");

	}

	/**
	 * 전문가관리 등록저장 프로세스
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void createExpertPool(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		HashMap<String, Object> map = new HashMap<String, Object>();

		ExpertPoolForm expertPoolForm = (ExpertPoolForm) form;

		ExpertPool expertPool = new ExpertPool();
		try {
			expertPool = ExpertPoolForm.valueOf(expertPool, expertPoolForm);
			
			if (expertPoolForm.getUid().equals("*************")) {
				expertPool.setUid("00" + StringUtil.getCurr("MMddHHmmsss"));
			} else {
				expertPool.setUid(expertPoolForm.getUid());
			}
			expertPool.setSsn(getExpertPoolManager().getSsn(expertPoolForm.getJobClass()));
			expertPool.setName(expertPoolForm.getName());
			String identify = StringUtil.isNull(expertPoolForm.getIdentify(), "");
			if (!"".equals(identify)) {
				expertPool.setUserId(identify);
			}
			String passWord = StringUtil.isNull(expertPoolForm.getPassword(), "");
			if (!"".equals(passWord)) {
				expertPool.setPassword(passWord);
				expertPool.setLastModifiedDate(new Date());
			}
			expertPool.setEnable("0");
			expertPool.setCreaterId(SessionUtils.getUsername(request));

			getExpertPoolManager().create(expertPool);

			if ((expertPool.getJobClass().equals("A") || expertPool.getJobClass().equals("J") || expertPool.getJobClass().equals("H"))
					&& !expertPool.getRate().equals("1") && !expertPool.getRate().equals("")
					|| (expertPool.getJobClass().equals("C") && expertPool.getEnable().equals("1")))
				getExpertPoolManager().createHistory(expertPool);

			map.put("result", true);
			map.put("resultMsg", "전문가 정보가 입력되었습니다.");
			AjaxUtil.successWrite(response, map);
		} catch (ExpertPoolException e) {
			logger.info(e.getMessage(), e);
			map.put("result", false);
			map.put("resultMsg", "전문가 정보 입력 중 오류가 발생했습니다.");
			map.put("ErrMsg", e);
			AjaxUtil.failWrite(response, map);

		}
	}

	/**
	 * 전문가관리 수정저장 프로세스
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void updateExpertPool(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		HashMap<String, Object> map = new HashMap<String, Object>();

		ExpertPoolForm expertPoolForm = (ExpertPoolForm) form;

		ExpertPool expertPool = expertPoolManager.retrieve(expertPoolForm.getSsn());
		try {
			expertPool = ExpertPoolForm.valueOf(expertPool, expertPoolForm);

			expertPool.setSsn(expertPoolForm.getSsn());
			expertPool.setName(expertPoolForm.getName());
			String identify = StringUtil.isNull(expertPoolForm.getIdentify(), "");
			if (!"".equals(identify)) {
				expertPool.setUserId(identify);
			}
			String passWord = StringUtil.isNull(expertPoolForm.getPassword(), "");
			if (!"".equals(passWord)) {
				expertPool.setPassword(passWord);
				expertPool.setLastModifiedDate(new Date());
			}
			expertPool.setModifierId(SessionUtils.getUsername(request));
			getExpertPoolManager().store(expertPool);

			if ((expertPool.getJobClass().equals("A") || expertPool.getJobClass().equals("J") || expertPool.getJobClass().equals("H"))
					&& !expertPool.getRate().equals("1") && !expertPool.getRate().equals("")
					|| (expertPool.getJobClass().equals("C") && expertPool.getEnable().equals("1"))) {
				getExpertPoolManager().storeHistory(expertPool);
			}

			// JSONWriter writer = new JSONWriter();
			map.put("result", true);
			map.put("resultMsg", "전문가 정보가 수정 되었습니다.");
			AjaxUtil.successWrite(response, map);
		} catch (ExpertPoolException e) {
			logger.info(e.getMessage(), e);
			map.put("result", false);
			map.put("resultMsg", "전문가 정보 수정 중 오류가 발생했습니다.");
			map.put("ErrMsg", e);
			AjaxUtil.failWrite(response, map);
		}
	}

	/**
	 * 전문가관리 등록 및 수정 화면
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward loadFormExpertPool(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String ssn = ServletRequestUtils.getStringParameter(request, "ssn", "");

		ExpertPool expertPool = new ExpertPool();
		if (!ssn.equals(""))
			expertPool = this.getExpertPoolManager().retrieve(ssn);

		request.setAttribute("ssn", ssn);
		request.setAttribute("expertPool", expertPool);
		request.setAttribute("hashTagList", this.getHashTagManager().getExpertHashTag(ssn, "SHOW"));
		
		return mapping.findForward("expertPoolForm");
	}

	/**
	 * 개인정보 수정 화면
	 */
	public ActionForward loadMyInfoForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		try {
			String ssn = SessionUtils.getUsername(request);
			ExpertPool expertPool = this.getExpertPoolManager().retrieve(ssn);
			request.setAttribute("expertPool", expertPool);
			request.setAttribute("hashTagList", this.getHashTagManager().getExpertHashTag(ssn, "SHOW"));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return mapping.findForward("MyInfoForm");
	}
	
	/**
	 * 개인정보 수정 화면
	 */
	public ActionForward loadMyInfoAddForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String ssn = ServletRequestUtils.getStringParameter(request, "ssn", "");

		if ("".equals(ssn)) {
			ssn = SessionUtils.getUsername(request);
		}
		ExpertPool expertPool = expertPoolManager.retrieve(ssn);

		WebApplicationContext wc;
		try {
			String pg = ServletRequestUtils.getStringParameter(request, "pg", "1");
			int pageSize = ServletRequestUtils.getIntParameter(request, "pageSize", 10);
			wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh = (ValueListHandler) wc.getBean("expoertPoolCategory", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();

			Map<String, String> filters = new HashMap<String, String>();
			filters.put(ValueListInfo.PAGING_PAGE, pg);
			filters.put("ssn", ssn);
			filters.put(ValueListInfo.PAGING_NUMBER_PER, String.valueOf(pageSize == -1 ? Integer.MAX_VALUE - 1 : pageSize));
			info.setFilters(filters);

			ValueList result = vlh.getValueList("select", info);
			request.setAttribute("projectInfoList", result);
			request.setAttribute("hashTagList", this.getHashTagManager().getExpertHashTag(ssn, "SHOW"));
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		// //

		request.setAttribute("expertPool", expertPool);

		return mapping.findForward("MyInfoAddForm");
	}

	public void saveMyInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		HashMap<String, Object> map = new HashMap<String, Object>();
		ExpertPoolForm expertPoolForm = (ExpertPoolForm) form;

		String photoId = ServletRequestUtils.getStringParameter(request, "photoId", "");
		String chkPassword = ServletRequestUtils.getStringParameter(request, "chkPassword", "N");
		String password = ServletRequestUtils.getStringParameter(request, "password2", "");
		try {
			ExpertPool expertPool = expertPoolManager.retrieve(expertPoolForm.getSsn());

			expertPool.setChnName(expertPoolForm.getChnName());
			expertPool.setEngName(expertPoolForm.getEngName());

			expertPool.setTelNo(expertPoolForm.getTelNo());
			expertPool.setMobileNo(expertPoolForm.getMobileNo());
			expertPool.setCompanyTelNo(expertPoolForm.getCompanyTelNo());
			expertPool.setCompanyFaxNo(expertPoolForm.getCompanyFaxNo());

			expertPool.setZipCode(expertPoolForm.getZipCode());
			expertPool.setAddress1(expertPoolForm.getAddress1());
			expertPool.setAddress2(expertPoolForm.getAddress2());

			expertPool.setCompanyZipcode(expertPoolForm.getCompanyZipcode());
			expertPool.setCompanyAddress1(expertPoolForm.getCompanyAddress1());
			expertPool.setCompanyAddress2(expertPoolForm.getCompanyAddress2());

			expertPool.setRemark(expertPoolForm.getRemark());
			expertPool.setModifierId(SessionUtils.getUsername(request));

			if (!photoId.equals(""))
				expertPool.setPhoto(photoId);

			boolean errYN = true;
			if (chkPassword.equals("Y")) {
				if (!this.getExpertPoolManager().getEncPassword(expertPoolForm.getPassword()).equals(expertPool.getPassword())) { // 기존비밀번호가 틀릴때
					errYN = false;
				} else {
					expertPool.setPassword(password);
					expertPool.setLastModifiedDate(new Date());
					request.getSession().setAttribute("needToPasswordUpdate", "false");
				}
			}
			map.put("result", true);
			if (errYN) {
				this.getExpertPoolManager().updateMyinfo(expertPool, chkPassword);
				// JobDate: 2016-09-09	Author: yhyim	Description: 교육관리 시스템과 비밀번호 동기화 
				// JobDate: 2018-08-15	Author: yhyim	Description: 비활성화
				//this.getExpertPoolManager().updateEduSystemAccount(expertPool);
				map.put("resultMsg", "개인 정보가 수정되었습니다.");
				map.put("emailAddr", expertPool.getEmail());
				map.put("encPwd", getExpertPoolManager().getEncPassword(password));
				map.put("chkPassword", chkPassword);
			} else {
				map.put("resultMsg", "현 비밀번호가 맞지 않습니다.");
			}
			AjaxUtil.successWrite(response, map);
		} catch (ExpertPoolException e) {
			logger.info(e.getMessage(), e);
			map.put("result", false);
			map.put("resultMsg", "개인정보 수정 중 오류가 발생했습니다.");
			map.put("ErrMsg", e);
			AjaxUtil.failWrite(response, map);
		}
	}

	public void setRestrictUserList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String ssn = ServletRequestUtils.getRequiredStringParameter(request, "ssn");
		String restrictYn = ServletRequestUtils.getRequiredStringParameter(request, "restrictYn");
		String restrictContents = ServletRequestUtils.getRequiredStringParameter(request, "restrictContents");

		try {
			/*this.getExpertPoolManager().updateRestrictUserState(ssn, restrictYn);*/
			this.getExpertPoolManager().updateRestrictContents(ssn, restrictYn, restrictContents);
			AjaxUtil.successWrite(response);
		} catch (Exception e) {
			AjaxUtil.failWrite(response);
			logger.error(e.getMessage(), e);
		}
	}

	public void setReadyUserList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String ssn = ServletRequestUtils.getRequiredStringParameter(request, "ssn");
		String restrictYn = ServletRequestUtils.getRequiredStringParameter(request, "restrictYn");

		try {
			this.getExpertPoolManager().updateReadyUserState(ssn, restrictYn);
			AjaxUtil.successWrite(response);
		} catch (Exception e) {
			AjaxUtil.failWrite(response);
			logger.error(e.getMessage(), e);
		}
	}
	
	public void setPasswordReset(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String ssn = ServletRequestUtils.getRequiredStringParameter(request, "ssn");
		String newPwd = ServletRequestUtils.getRequiredStringParameter(request, "newPwd");
		
		HashMap<String, Object> map = new HashMap<String, Object>();

		try {
			this.getExpertPoolManager().passwordReset(ssn, newPwd);
			map.put("encPwd", getExpertPoolManager().getEncPassword(newPwd));
			AjaxUtil.successWrite(response, map);
		} catch (Exception e) {
			AjaxUtil.failWrite(response);
			logger.error(e.getMessage(), e);
		}
	}	

	/**
	 * 인력 POOL 관리 리스트
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getExpertPoolList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String pageSize = ServletRequestUtils.getStringParameter(request, "pageSize", "15");
		String pg = ServletRequestUtils.getStringParameter(request, "pg", "1");
		String keyfield = ServletRequestUtils.getStringParameter(request, "keyfield", "");
		String jobClass = ServletRequestUtils.getStringParameter(request, "jobClass", "");
		String keyword = ServletRequestUtils.getStringParameter(request, "keyword", "");
		String selectedGroup = ServletRequestUtils.getStringParameter(request, "selectedGroup", "");
		String[] specialField = ServletRequestUtils.getStringParameters(request, "specialField");

		try {
			WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());

			ValueListHandler vlh = (ValueListHandler) wc.getBean("expertPoolList", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();
			HashMap<String, String> filters = new HashMap<String, String>();

			filters.put("pg", pg);
			filters.put("keyfield", keyfield);
			if (keyfield.equals("1"))
				filters.put("name", "%" + keyword + "%");
			if (keyfield.equals("2"))
				filters.put("company", "%" + keyword + "%");
			if (keyfield.equals("3"))
				filters.put("jobClass", jobClass);
			if (keyfield.equals("4"))
				filters.put("createrid", "%" + keyword + "%");
			if (specialField.length > 0) {
				String string = "";
				for (int i = 0; i < specialField.length; i++) {
					string += "'" + specialField[i] + "',";
				}
				string = string.substring(0, string.length() - 1);
				filters.put("specialField", string);
				request.setAttribute("specialFields", string);
			}
			ExpertPool expertPool = this.getExpertPoolManager().retrieve(SessionUtils.getUsername(request));
			if (expertPool.getDept().startsWith("938") || expertPool.getDept().startsWith("9362"))
				filters.put("restrict", "restrict");
			else
				filters.put("restrict", "");

			info.setFilters(filters);

			int cnt = this.getExpertPoolManager().getExpertPoolCnt(filters);
			List<ExpertPool> list = this.getExpertPoolManager().getExpertPoolList(filters);

			request.setAttribute("selectedGroup", selectedGroup);
			request.setAttribute("list", list);
			request.setAttribute("totalNumberOfEntries", cnt);
			request.setAttribute("pagingPage", pg);
			request.setAttribute("totalNumberOfPages", (int) (Math.ceil((double) cnt / 15)));

			request.setAttribute("keyfield", keyfield);
			request.setAttribute("keyword", keyword);
			request.setAttribute("jobClass", jobClass);
			request.setAttribute("groupList", this.getExpertPoolManager().getDeptLIst());

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return mapping.findForward("expertPoolList");
	}

	/**
	 * 인력 POOL 관리 리스트
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getExpertPoolExtList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String pageSize = ServletRequestUtils.getStringParameter(request, "pageSize", "10");
		String pg = ServletRequestUtils.getStringParameter(request, "pg", "1");
		String keyfield = ServletRequestUtils.getStringParameter(request, "keyfield", "");
		String jobClass = ServletRequestUtils.getStringParameter(request, "jobClass", "");
		String keyword = ServletRequestUtils.getStringParameter(request, "keyword", "");
		String selectedGroup = ServletRequestUtils.getStringParameter(request, "selectedGroup", "");
		String[] specialField = ServletRequestUtils.getStringParameters(request, "specialField");
		String dept = ServletRequestUtils.getStringParameter(request, "dept", "");
		String deptName = ServletRequestUtils.getStringParameter(request, "deptName", "");
		String name = ServletRequestUtils.getStringParameter(request, "name", "");
		String company = ServletRequestUtils.getStringParameter(request, "company", "");

		try {
			ValueListInfo info = new ValueListInfo();
			HashMap<String, String> filters = new HashMap<String, String>();

			filters.put("pg", pg);
			filters.put("keyfield", keyfield);
			filters.put("name", "%" + name + "%");
			filters.put("dept", "%" + dept + "%");
			filters.put("jobClass",  "%" + jobClass + "%");
			filters.put("deptName", "%" + deptName + "%");
			filters.put("company", "%" + company + "%");
			/*if (name.equals(""))
				filters.put("name", "%" + name + "%");
			if (!name.equals(""))
				filters.put("name", "%" + name + "%");
			if (!deptCode.equals(""))
				filters.put("deptCode", "%" + deptCode + "%");
			if (!deptName.equals(""))
				filters.put("deptName", "%" + deptName + "%");
			if (!"".equals(company)){
				filters.put("company", "%" + company + "%");}
			if (keyfield.equals("4"))
				filters.put("createrid", "%" + keyword + "%");
			if (specialField.length > 0) {
				String string = "";
				for (int i = 0; i < specialField.length; i++) {
					string += "'" + specialField[i] + "',";
				}
				string = string.substring(0, string.length() - 1);
				filters.put("specialField", string);
				request.setAttribute("specialFields", string);
			}*/

			info.setFilters(filters);

			int cnt = this.getExpertPoolManager().getExpertPoolExtCnt(filters);
			List<ExpertPool> list = this.getExpertPoolManager().getExpertPoolExtList(filters);

			request.setAttribute("selectedGroup", selectedGroup);
			request.setAttribute("list", list);
			request.setAttribute("totalNumberOfEntries", cnt);
			request.setAttribute("pagingPage", pg);
			request.setAttribute("totalNumberOfPages", (int) (Math.ceil((double) cnt / 10)));

			request.setAttribute("keyfield", keyfield);
			request.setAttribute("keyword", keyword);
			request.setAttribute("jobClass", jobClass);
			request.setAttribute("groupList", this.getExpertPoolManager().getExpertPoolFunctionLIst());
			request.setAttribute("dept", dept);
			request.setAttribute("name", name);
			request.setAttribute("company", company);
			request.setAttribute("deptName", deptName);
			

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return mapping.findForward("expertPoolExtList");
	}
	
	/**
	 * 인력 POOL 관리 리스트
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getExpertPoolExtList2(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String pageSize = ServletRequestUtils.getStringParameter(request, "pageSize", "10");
		String pg = ServletRequestUtils.getStringParameter(request, "pg", "1");
		String keyfield = ServletRequestUtils.getStringParameter(request, "keyfield", "");
		String jobClass = ServletRequestUtils.getStringParameter(request, "jobClass", "");
		String keyword = ServletRequestUtils.getStringParameter(request, "keyword", "");
		String selectedGroup = ServletRequestUtils.getStringParameter(request, "selectedGroup", "");
		String[] specialField = ServletRequestUtils.getStringParameters(request, "specialField");
		String runningDeptCode = ServletRequestUtils.getStringParameter(request, "runningDeptCode", "");
		String dept = ServletRequestUtils.getStringParameter(request, "dept", "");
		String deptName = ServletRequestUtils.getStringParameter(request, "deptName", "");
		String name = ServletRequestUtils.getStringParameter(request, "name", "");
		String company = ServletRequestUtils.getStringParameter(request, "company", "");

		try {
			ValueListInfo info = new ValueListInfo();
			HashMap<String, String> filters = new HashMap<String, String>();

			filters.put("pg", pg);
			filters.put("keyfield", keyfield);
			filters.put("name", "%" + name + "%");
			filters.put("dept", "%" + dept + "%");
			filters.put("jobClass",  "%" + jobClass + "%");
			filters.put("deptName", "%" + deptName + "%");
			filters.put("company", "%" + company + "%");
			
			/*if (keyfield.equals(""))
				filters.put("name", "%" + keyword + "%");
			if (keyfield.equals("1"))
				filters.put("name", "%" + keyword + "%");
			if (keyfield.equals("2"))
				filters.put("company", "%" + keyword + "%");
			if (keyfield.equals("3"))
				filters.put("jobClass", jobClass);
			if (keyfield.equals("4"))
				filters.put("createrid", "%" + keyword + "%");
			if (keyfield.equals("5"))
				filters.put("runningDeptCode", "%" + runningDeptCode + "%");
			if (specialField.length > 0) {
				String string = "";
				for (int i = 0; i < specialField.length; i++) {
					string += "'" + specialField[i] + "',";
				}
				string = string.substring(0, string.length() - 1);
				filters.put("specialField", string);
				request.setAttribute("specialFields", string);
			}*/

			info.setFilters(filters);

			int cnt = this.getExpertPoolManager().getExpertPoolExtCnt2(filters);
			List<ExpertPool> list = this.getExpertPoolManager().getExpertPoolExtList2(filters);

			request.setAttribute("selectedGroup", selectedGroup);
			request.setAttribute("list", list);
			request.setAttribute("totalNumberOfEntries", cnt);
			request.setAttribute("pagingPage", pg);
			request.setAttribute("totalNumberOfPages", (int) (Math.ceil((double) cnt / 10)));

			request.setAttribute("keyfield", keyfield);
			request.setAttribute("keyword", keyword);
			request.setAttribute("jobClass", jobClass);
			request.setAttribute("groupList", this.getExpertPoolManager().getExpertPoolFunctionLIst());
			request.setAttribute("runningDeptCode", runningDeptCode);
			request.setAttribute("dept", dept);
			request.setAttribute("name", name);
			request.setAttribute("company", company);
			request.setAttribute("deptName", deptName);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return mapping.findForward("expertPoolExtList2");
	}

	/**
	 * 인력 POOL 관리 리스트
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public void getExpertPoolExtListForMobile(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pg = ServletRequestUtils.getIntParameter(request, "pg", 1);
		String jobClass = ServletRequestUtils.getStringParameter(request, "jobClass", "A");
		String keyfield = ServletRequestUtils.getStringParameter(request, "keyfield", "");
		String keyword = ServletRequestUtils.getStringParameter(request, "keyword", "");

		HashMap<String, Object> map = new HashMap<String, Object>();
		try {
			WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());

			ValueListHandler vlh = (ValueListHandler) wc.getBean("expertPoolList", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();
			Map<String, String> filters = new HashMap<String, String>();
			filters.put(ValueListInfo.PAGING_PAGE, String.valueOf(pg));
			filters.put(ValueListInfo.PAGING_NUMBER_PER, String.valueOf(10));
			filters.put("jobClass", jobClass);
			if (!keyword.equals(""))
				filters.put("name", "%" + keyword + "%");
			// if (keyfield.equals("1"))
			// filters.put("name", "%" + keyword + "%");
			// if (keyfield.equals("2"))
			// filters.put("company", "%" + keyword + "%");
			// if (keyfield.equals("3"))
			// filters.put("jobClass", jobClass);
			// if (keyfield.equals("4"))
			// filters.put("createrid", "%" + keyword + "%");
			filters.put("miblie", "1");
			info.setFilters(filters);
			ValueList valueList = vlh.getValueList("getExpertPoolList", info);

			map.put("pagingPage", String.valueOf(pg));
			map.put("pagingEntries",
					pg == valueList.getValueListInfo().getTotalNumberOfPages() ? String.valueOf(valueList.getValueListInfo()
							.getTotalNumberOfEntries()) : String.valueOf(pg * 10));
			map.put("totalNumberOfEntries", String.valueOf(valueList.getValueListInfo().getTotalNumberOfEntries()));
			map.put("totalNumberOfPages", String.valueOf(valueList.getValueListInfo().getTotalNumberOfPages()));
			map.put("expertpoolList", valueList.getList());
			map.put("keyfield", keyfield);
			map.put("keyword", keyword);
			map.put("jobClass", jobClass);
			// map.put("groupList", this.getExpertPoolManager().getDeptLIst());
			AjaxUtil.successWrite(response, map);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			AjaxUtil.failWrite(response);
		}
	}

	/**
	 * 전문가 POOL(전문가그룹, 엑스퍼트, 프로젝트 RA) 관리 리스트
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getProjectExpertPoolList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String pg = ServletRequestUtils.getStringParameter(request, "pg", "1");
		String name = ServletRequestUtils.getStringParameter(request, "name", "");
		String jobClass = ServletRequestUtils.getStringParameter(request, "jobClass", "J");
		String rank = ServletRequestUtils.getStringParameter(request, "rank", "");
		String selectedGroup = ServletRequestUtils.getStringParameter(request, "selectedGroup", "");
		String[] specialField = ServletRequestUtils.getStringParameters(request, "specialField");

		try {
			WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());

			// ValueListHandler vlh = (ValueListHandler) wc.getBean("expertPoolList", ValueListHandler.class);
			// ValueListInfo info = new ValueListInfo();
			HashMap<String, String> filters = new HashMap<String, String>();

			filters.put("pg", pg);
			if (!name.equals(""))
				filters.put("name", "%" + name + "%");
			filters.put("jobClass", jobClass);
			if (!rank.equals(""))
				filters.put("rank", rank);
			if (specialField.length > 0) {
				String string = "";
				for (int i = 0; i < specialField.length; i++) {
					string += "'" + specialField[i] + "',";
				}
				string = string.substring(0, string.length() - 1);
				filters.put("specialField", string);
				request.setAttribute("specialFields", string);
			}
			ExpertPool expertPool = this.getExpertPoolManager().retrieve(SessionUtils.getUsername(request));
			if (expertPool.getDept().startsWith("938") || expertPool.getDept().startsWith("9362"))
				filters.put("restrict", "restrict");
			else
				filters.put("restrict", "");

			// info.setFilters(filters);

			int cnt = this.getExpertPoolManager().getProjectExpertPoolCnt(filters);
			List<ExpertPool> list = this.getExpertPoolManager().getProjectExpertPoolList(filters);

			request.setAttribute("selectedGroup", selectedGroup);
			request.setAttribute("list", list);
			request.setAttribute("totalNumberOfEntries", cnt);
			request.setAttribute("pagingPage", pg);
			request.setAttribute("totalNumberOfPages", (int) (Math.ceil((double) cnt / 15)));

			request.setAttribute("jobClass", jobClass);
			request.setAttribute("rank", rank);
			request.setAttribute("name", name);
			request.setAttribute("groupList", this.getExpertPoolManager().getDeptLIst());

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return mapping.findForward("projectExpertPoolList");
	}

	// JobDate: 2012-04-05 Author: yhyim Description: 투입 제한된 인력 리스트 보기
	public ActionForward getRestrictExpertPoolList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String pageSize = ServletRequestUtils.getStringParameter(request, "pageSize", "15");
		String pageNo = ServletRequestUtils.getStringParameter(request, "pageNo", "1");
		String name = ServletRequestUtils.getStringParameter(request, "name", "");
		String restrictUser = ServletRequestUtils.getStringParameter(request, "restrictUser", "Y");
		String jobClass = ServletRequestUtils.getStringParameter(request, "jobClass", "");
		
		try {
			WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh = (ValueListHandler) wc.getBean("expertPoolList", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();
			Map<String, String> filters = new HashMap<String, String>();
			filters.put(ValueListInfo.PAGING_PAGE, pageNo);
			filters.put(ValueListInfo.PAGING_NUMBER_PER, pageSize);

			if (!name.equals("")) {
				filters.put("name", "%" + name + "%");
			}
			if (!restrictUser.equals("")) {
				filters.put("restrictUser", restrictUser);
			}
			if (!jobClass.equals("")) {
				filters.put("jobClass", jobClass);
			}
			if (restrictUser.equals("Y")) {
				filters.put("absence", "Y"); // 투입 제한 인력 설정
			}
			info.setFilters(filters);
			ValueList valueList = vlh.getValueList("getRestrictExpertPoolList", info);

			request.setAttribute("list", valueList);
			request.setAttribute("name", name);
			request.setAttribute("jobClass", jobClass);
			request.setAttribute("restrictUser", restrictUser);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return mapping.findForward("restrictExpertPoolList");
	}

	// JobDate: 2012-07-18 Author: yhyim Description: 인력POOL외 인력 리스트 보기
	public ActionForward getReadyExpertPoolList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String pageSize = ServletRequestUtils.getStringParameter(request, "pageSize", "15");
		String pageNo = ServletRequestUtils.getStringParameter(request, "pageNo", "1");
		String name = ServletRequestUtils.getStringParameter(request, "name", "");
		String restrictUser = ServletRequestUtils.getStringParameter(request, "restrictUser", "Y");
		String jobClass = ServletRequestUtils.getStringParameter(request, "jobClass", "");

		try {
			WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh = (ValueListHandler) wc.getBean("expertPoolList", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();
			Map<String, String> filters = new HashMap<String, String>();
			filters.put(ValueListInfo.PAGING_PAGE, pageNo);
			filters.put(ValueListInfo.PAGING_NUMBER_PER, pageSize);

			if (!name.equals("")) {
				filters.put("name", "%" + name + "%");
			}
			if (!restrictUser.equals("")) {
				filters.put("restrictUser", restrictUser);
			}
			if (!jobClass.equals("")) {
				filters.put("jobClass", jobClass);
			}
			if (restrictUser.equals("Y")) {
				filters.put("absence", "N"); // 투입 대기 인력 설정
			}
			info.setFilters(filters);
			ValueList valueList = vlh.getValueList("getRestrictExpertPoolList", info);

			request.setAttribute("list", valueList);
			request.setAttribute("name", name);
			request.setAttribute("jobClass", jobClass);
			request.setAttribute("restrictUser", restrictUser);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return mapping.findForward("readyExpertPoolList");
	}
	
	// JobDate: 2019-03-03 Author: yhyim Description: 인력POOL 근무기간  리스트 보기
	public ActionForward getExpertPoolWorkPeriodList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String pageSize = ServletRequestUtils.getStringParameter(request, "pageSize", "1000");
		String pageNo = ServletRequestUtils.getStringParameter(request, "pageNo", "1");
		String jobClass = ServletRequestUtils.getStringParameter(request, "jobClass", "");
		String companyPosition = ServletRequestUtils.getStringParameter(request, "companyPosition", "");

		try {
			WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh = (ValueListHandler) wc.getBean("expertPoolList", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();
			Map<String, String> filters = new HashMap<String, String>();
			filters.put(ValueListInfo.PAGING_PAGE, pageNo);
			filters.put(ValueListInfo.PAGING_NUMBER_PER, pageSize);

			if (!jobClass.equals("")) {
				filters.put("jobClass", jobClass);
			}
			if (!companyPosition.equals("") && !companyPosition.equals("!64GT")) {
				filters.put("companyPosition", companyPosition);
			}
			if (!companyPosition.equals("") && companyPosition.equals("!64GT")) {
				filters.put("notInclude", companyPosition);
			}
			info.setFilters(filters);
			ValueList valueList = vlh.getValueList("getExpertPoolWorkPeriodList", info);

			request.setAttribute("list", valueList);
			request.setAttribute("jobClass", jobClass);
			request.setAttribute("companyPosition", companyPosition);
			//request.setAttribute("today", StringUtil.getCurr("yyyy") + "-" + StringUtil.getCurr("MM") + "-" + StringUtil.getCurr("dd"));

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return mapping.findForward("expertPoolWorkPeriodList");
	}
	
	public ActionForward getExpertPoolWorkPeriodList_ra(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String pageSize = ServletRequestUtils.getStringParameter(request, "pageSize", "1000");
		String pageNo = ServletRequestUtils.getStringParameter(request, "pageNo", "1");
		String ssn = SessionUtils.getUsername(request);
		System.out.println(ssn);

		try {
			WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh = (ValueListHandler) wc.getBean("expertPoolList", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();
			Map<String, String> filters = new HashMap<String, String>();
			filters.put(ValueListInfo.PAGING_PAGE, pageNo);
			filters.put(ValueListInfo.PAGING_NUMBER_PER, pageSize);
			filters.put("ssn", ssn);
			
			info.setFilters(filters);
			ValueList valueList = vlh.getValueList("getExpertPoolWorkPeriodList_ra", info);

			request.setAttribute("list", valueList);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return mapping.findForward("expertPoolWorkPeriodList_ra");
	}
	
	public ActionForward getExpertPoolWorkPeriodList_exp(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String pageSize = ServletRequestUtils.getStringParameter(request, "pageSize", "1000");
		String pageNo = ServletRequestUtils.getStringParameter(request, "pageNo", "1");
		String jobClass = ServletRequestUtils.getStringParameter(request, "jobClass", "");
		String companyPosition = ServletRequestUtils.getStringParameter(request, "companyPosition", "");

		try {
			WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh = (ValueListHandler) wc.getBean("expertPoolList", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();
			Map<String, String> filters = new HashMap<String, String>();
			filters.put(ValueListInfo.PAGING_PAGE, pageNo);
			filters.put(ValueListInfo.PAGING_NUMBER_PER, pageSize);

			if (!jobClass.equals("")) {
				filters.put("jobClass", jobClass);
			}
			if (!companyPosition.equals("") && !companyPosition.equals("!64GT")) {
				filters.put("companyPosition", companyPosition);
			}
			if (!companyPosition.equals("") && companyPosition.equals("!64GT")) {
				filters.put("notInclude", companyPosition);
			}
			info.setFilters(filters);
			ValueList valueList = vlh.getValueList("getExpertPoolWorkPeriodList_exp", info);

			request.setAttribute("list", valueList);
			request.setAttribute("jobClass", jobClass);
			request.setAttribute("companyPosition", companyPosition);
			request.setAttribute("today", StringUtil.getCurr("yyyy") + "-" + StringUtil.getCurr("MM") + "-" + StringUtil.getCurr("dd"));

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return mapping.findForward("expertPoolWorkPeriodList_exp");
	}
	
	public ActionForward getExpertPoolWorkPeriodList_vac(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String pageSize = ServletRequestUtils.getStringParameter(request, "pageSize", "1000");
		String pageNo = ServletRequestUtils.getStringParameter(request, "pageNo", "1");
		String jobClass = ServletRequestUtils.getStringParameter(request, "jobClass", "");
		String companyPosition = ServletRequestUtils.getStringParameter(request, "companyPosition", "");
		String dept = ServletRequestUtils.getStringParameter(request, "dept", "");

		try {
			WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh = (ValueListHandler) wc.getBean("expertPoolList", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();
			Map<String, String> filters = new HashMap<String, String>();
			filters.put(ValueListInfo.PAGING_PAGE, pageNo);
			filters.put(ValueListInfo.PAGING_NUMBER_PER, pageSize);
			
			if (!jobClass.equals("")) {
				filters.put("jobClass", jobClass);
			}
			if (!companyPosition.equals("") && !companyPosition.equals("!64GT")) {
				filters.put("companyPosition", companyPosition);
			}
			if (!companyPosition.equals("") && companyPosition.equals("!64GT")) {
				filters.put("notInclude", companyPosition);
			}
			filters.put("dept", dept);
			info.setFilters(filters);
			ValueList valueList = vlh.getValueList("getExpertPoolWorkPeriodList_vac", info);

			request.setAttribute("list", valueList);
			request.setAttribute("jobClass", jobClass);
			request.setAttribute("companyPosition", companyPosition);
			request.setAttribute("dept", dept);
			//request.setAttribute("today", StringUtil.getCurr("yyyy") + "-" + StringUtil.getCurr("MM") + "-" + StringUtil.getCurr("dd"));

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return mapping.findForward("expertPoolWorkPeriodList_vac");
	}

	public ActionForward getExpertPoolWorkPeriodList_exp2(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String pageSize = ServletRequestUtils.getStringParameter(request, "pageSize", "1000");
		String pageNo = ServletRequestUtils.getStringParameter(request, "pageNo", "1");
		String jobClass = ServletRequestUtils.getStringParameter(request, "jobClass", "");
		String companyPosition = ServletRequestUtils.getStringParameter(request, "companyPosition", "");

		try {
			WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh = (ValueListHandler) wc.getBean("expertPoolList", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();
			Map<String, String> filters = new HashMap<String, String>();
			filters.put(ValueListInfo.PAGING_PAGE, pageNo);
			filters.put(ValueListInfo.PAGING_NUMBER_PER, pageSize);

			if (!jobClass.equals("")) {
				filters.put("jobClass", jobClass);
			}
			if (!companyPosition.equals("") && !companyPosition.equals("!64GT")) {
				filters.put("companyPosition", companyPosition);
			}
			if (!companyPosition.equals("") && companyPosition.equals("!64GT")) {
				filters.put("notInclude", companyPosition);
			}
			info.setFilters(filters);
			ValueList valueList = vlh.getValueList("getExpertPoolWorkPeriodList_exp2", info);

			request.setAttribute("list", valueList);
			request.setAttribute("jobClass", jobClass);
			request.setAttribute("companyPosition", companyPosition);
			request.setAttribute("today", StringUtil.getCurr("yyyy") + "-" + StringUtil.getCurr("MM") + "-" + StringUtil.getCurr("dd"));

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return mapping.findForward("expertPoolWorkPeriodList_exp2");
	}
	
	public ActionForward getExpertPoolWorkPeriodList_schedule(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String pageSize = ServletRequestUtils.getStringParameter(request, "pageSize", "1000");
		String pageNo = ServletRequestUtils.getStringParameter(request, "pageNo", "1");
		String jobClass = ServletRequestUtils.getStringParameter(request, "jobClass", "");
		String companyPosition = ServletRequestUtils.getStringParameter(request, "companyPosition", "");
		String startDate = ServletRequestUtils.getStringParameter(request, "startDate", DateTime.getDateString());
		String endDate = ServletRequestUtils.getStringParameter(request, "endDate", DateTime.getDateString());
		System.out.println(startDate);
		System.out.println(endDate);

		try {
			WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh = (ValueListHandler) wc.getBean("expertPoolList", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();
			Map<String, String> filters = new HashMap<String, String>();
			filters.put(ValueListInfo.PAGING_PAGE, pageNo);
			filters.put(ValueListInfo.PAGING_NUMBER_PER, pageSize);

			if (!startDate.equals("")) {
				startDate = StringUtil.replace(startDate, "-", "");
				if (startDate.length() < 8) {
					startDate = startDate;
				}
				filters.put("startDate", startDate);
				System.out.println(startDate);
			}
			if (!endDate.equals("")) {
				endDate = StringUtil.replace(endDate, "-", "");
				if (endDate.length() < 8) {
					endDate = endDate;
				}
				filters.put("endDate", endDate);
			}
			info.setFilters(filters);
			ValueList valueList = vlh.getValueList("getExpertPoolWorkPeriodList_schedule", info);

			request.setAttribute("list", valueList);
			request.setAttribute("jobClass", jobClass);
			request.setAttribute("companyPosition", companyPosition);
			request.setAttribute("startDate", startDate);
			request.setAttribute("endDate", endDate);
			request.setAttribute("today", StringUtil.getCurr("yyyy") + "-" + StringUtil.getCurr("MM") + "-" + StringUtil.getCurr("dd"));

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return mapping.findForward("expertPoolWorkPeriodList_schedule");
	}
	
	public ActionForward getExpertPoolWorkPeriodList_customer(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String pageSize = ServletRequestUtils.getStringParameter(request, "pageSize", "1000");
		String pageNo = ServletRequestUtils.getStringParameter(request, "pageNo", "1");
		String jobClass = ServletRequestUtils.getStringParameter(request, "jobClass", "");
		String companyPosition = ServletRequestUtils.getStringParameter(request, "companyPosition", "");
		String startDate = ServletRequestUtils.getStringParameter(request, "startDate",  DateTime.getYear() + "-" + DateTime.getMonth() + "-01");
		String endDate = ServletRequestUtils.getStringParameter(request, "endDate", DateTime.getDateString());
		endDate = StringUtil.replace(endDate, "-", "");
		
		try {
			WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh = (ValueListHandler) wc.getBean("expertPoolList", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();
			Map<String, String> filters = new HashMap<String, String>();
			filters.put(ValueListInfo.PAGING_PAGE, pageNo);
			filters.put(ValueListInfo.PAGING_NUMBER_PER, pageSize);

			if (!startDate.equals("")) {
				startDate = StringUtil.replace(startDate, "-", "");
				if (startDate.length() < 8) {
					startDate = startDate;
				}
				filters.put("startDate", startDate);
			}
			if (!endDate.equals("")) {
				endDate = StringUtil.replace(endDate, "-", "");
				if (endDate.length() < 8) {
					endDate = endDate;
				}
				filters.put("endDate", endDate);
			}
			System.out.println(startDate);
			System.out.println(endDate);

			info.setFilters(filters);
			ValueList valueList = vlh.getValueList("getExpertPoolWorkPeriodList_customer", info);

			request.setAttribute("list", valueList);
			request.setAttribute("jobClass", jobClass);
			request.setAttribute("companyPosition", companyPosition);
			request.setAttribute("startDate", startDate);
			request.setAttribute("endDate", endDate);
			request.setAttribute("today", StringUtil.getCurr("yyyy") + "-" + StringUtil.getCurr("MM") + "-" + StringUtil.getCurr("dd"));

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return mapping.findForward("expertPoolWorkPeriodList_customer");
	}
	
	public ActionForward getExpertPoolWorkPeriodList_customer2(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String pageSize = ServletRequestUtils.getStringParameter(request, "pageSize", "1000");
		String pageNo = ServletRequestUtils.getStringParameter(request, "pageNo", "1");
		String jobClass = ServletRequestUtils.getStringParameter(request, "jobClass", "");
		String companyPosition = ServletRequestUtils.getStringParameter(request, "companyPosition", "");
		String ssn = ServletRequestUtils.getStringParameter(request, "ssn", "");
		String startDate = ServletRequestUtils.getStringParameter(request, "startDate",  "");
		String endDate = ServletRequestUtils.getStringParameter(request, "endDate", "");
		endDate = StringUtil.replace(endDate, "-", "");
		
		try {
			WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh = (ValueListHandler) wc.getBean("expertPoolList", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();
			Map<String, String> filters = new HashMap<String, String>();
			filters.put(ValueListInfo.PAGING_PAGE, pageNo);
			filters.put(ValueListInfo.PAGING_NUMBER_PER, pageSize);
			
			filters.put("ssn", ssn);

			if (!startDate.equals("")) {
				startDate = StringUtil.replace(startDate, "-", "");
				if (startDate.length() < 8) {
					startDate = startDate;
				}
				filters.put("startDate", startDate);
			}
			if (!endDate.equals("")) {
				endDate = StringUtil.replace(endDate, "-", "");
				if (endDate.length() < 8) {
					endDate = endDate;
				}
				filters.put("endDate", endDate);
			}
			System.out.println(startDate);
			System.out.println(endDate);

			info.setFilters(filters);
			ValueList valueList = vlh.getValueList("getExpertPoolWorkPeriodList_customer2", info);

			request.setAttribute("list", valueList);
			request.setAttribute("jobClass", jobClass);
			request.setAttribute("companyPosition", companyPosition);
			request.setAttribute("startDate", startDate);
			request.setAttribute("endDate", endDate);
			request.setAttribute("today", StringUtil.getCurr("yyyy") + "-" + StringUtil.getCurr("MM") + "-" + StringUtil.getCurr("dd"));

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return mapping.findForward("expertPoolWorkPeriodList_customer2");
	}
	
	public ActionForward getExpertPoolWorkPeriodList_time(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String pageSize = ServletRequestUtils.getStringParameter(request, "pageSize", "1000");
		String pageNo = ServletRequestUtils.getStringParameter(request, "pageNo", "1");
		String jobClass = ServletRequestUtils.getStringParameter(request, "jobClass", "");
		String companyPosition = ServletRequestUtils.getStringParameter(request, "companyPosition", "");
		String startDate = ServletRequestUtils.getStringParameter(request, "startDate",  DateTime.getYear() + "-" + DateTime.getMonth() + "-01");
		String endDate = null;
		
		if(DateTime.getMonth().equals("04") || DateTime.getMonth().equals("06") || DateTime.getMonth().equals("09") || DateTime.getMonth().equals("11"))
		{
			endDate = ServletRequestUtils.getStringParameter(request, "endDate",  DateTime.getYear() + "-" + DateTime.getMonth() + "-30");
		}else if(DateTime.getMonth().equals("02")){
			endDate = ServletRequestUtils.getStringParameter(request, "endDate",  DateTime.getYear() + "-" + DateTime.getMonth() + "-28");
		}else{
			endDate = ServletRequestUtils.getStringParameter(request, "endDate",  DateTime.getYear() + "-" + DateTime.getMonth() + "-31");
		}

		try {
			WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh = (ValueListHandler) wc.getBean("expertPoolList", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();
			Map<String, String> filters = new HashMap<String, String>();
			filters.put(ValueListInfo.PAGING_PAGE, pageNo);
			filters.put(ValueListInfo.PAGING_NUMBER_PER, pageSize);

			if (!startDate.equals("")) {
				startDate = StringUtil.replace(startDate, "-", "");
				if (startDate.length() < 8) {
					startDate = startDate;
				}
				filters.put("startDate", startDate);
			}
			if (!endDate.equals("")) {
				endDate = StringUtil.replace(endDate, "-", "");
				if (endDate.length() < 8) {
					endDate = endDate;
				}
				filters.put("endDate", endDate);
			}
			info.setFilters(filters);
			ValueList valueList = vlh.getValueList("getExpertPoolWorkPeriodList_time", info);

			request.setAttribute("list", valueList);
			request.setAttribute("jobClass", jobClass);
			request.setAttribute("companyPosition", companyPosition);
			request.setAttribute("startDate", startDate);
			request.setAttribute("endDate", endDate);
			request.setAttribute("today", StringUtil.getCurr("yyyy") + "-" + StringUtil.getCurr("MM") + "-" + StringUtil.getCurr("dd"));

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return mapping.findForward("expertPoolWorkPeriodList_time");
	}
	
	public ActionForward getExpertPoolWorkPeriodList_HR(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String pageSize = ServletRequestUtils.getStringParameter(request, "pageSize", "1000");
		String pageNo = ServletRequestUtils.getStringParameter(request, "pageNo", "1");
		
		try {
			WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh = (ValueListHandler) wc.getBean("expertPoolList", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();
			Map<String, String> filters = new HashMap<String, String>();
			filters.put(ValueListInfo.PAGING_PAGE, pageNo);
			filters.put(ValueListInfo.PAGING_NUMBER_PER, pageSize);

			info.setFilters(filters);
			ValueList valueList = vlh.getValueList("getExpertPoolWorkPeriodList_HR", info);

			request.setAttribute("list", valueList);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return mapping.findForward("expertPoolWorkPeriodList_hr");
	}
	
	/* 출근 시간 확인 */
	public ActionForward getExpertPoolWorkPeriodList_workDay(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String pageSize = ServletRequestUtils.getStringParameter(request, "pageSize", "1000");
		String pageNo = ServletRequestUtils.getStringParameter(request, "pageNo", "1");
		String jobClass = ServletRequestUtils.getStringParameter(request, "jobClass", "A");
		String startDate = ServletRequestUtils.getStringParameter(request, "startDate",  DateTime.getDateString());
		String endDate = ServletRequestUtils.getStringParameter(request, "endDate",  DateTime.getDateString());
		String runningDeptCode = ServletRequestUtils.getStringParameter(request,  "runningDeptCode", "");

		try {
			WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh = (ValueListHandler) wc.getBean("expertPoolList", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();
			Map<String, String> filters = new HashMap<String, String>();
			filters.put(ValueListInfo.PAGING_PAGE, pageNo);
			filters.put(ValueListInfo.PAGING_NUMBER_PER, pageSize);
			
			if (!startDate.equals("")) {
				startDate = StringUtil.replace(startDate, "-", "");
				if (startDate.length() < 8) {
					startDate = startDate;
				}
				filters.put("startDate", startDate);
			}
			
			if (!endDate.equals("")) {
				endDate = StringUtil.replace(endDate, "-", "");
				if (endDate.length() < 8) {
					endDate = endDate;
				}
				filters.put("endDate", endDate);
			}
			
			if(!runningDeptCode.equals("")){filters.put("runningDeptCode", "%" + runningDeptCode + "%");}				
			filters.put("jobClass", jobClass);
			
			info.setFilters(filters);
			ValueList valueList = vlh.getValueList("getExpertPoolWorkPeriodList_workDay", info);
			 
			request.setAttribute("list", valueList);
			request.setAttribute("jobClass", jobClass);
			request.setAttribute("startDate", startDate);
			request.setAttribute("endDate", endDate);
			request.setAttribute("runningDeptCode", runningDeptCode);
			request.setAttribute("today", StringUtil.getCurr("yyyy") + "-" + StringUtil.getCurr("MM") + "-" + StringUtil.getCurr("dd"));
 
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return mapping.findForward("expertPoolWorkPeriodList_workday");
	}
	
	public ActionForward getExpertPoolWorkPeriodList_workDay2(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String pageSize = ServletRequestUtils.getStringParameter(request, "pageSize", "1000");
		String pageNo = ServletRequestUtils.getStringParameter(request, "pageNo", "1");
		String jobClass = ServletRequestUtils.getStringParameter(request, "jobClass", "A");
		String startDate = ServletRequestUtils.getStringParameter(request, "startDate",  DateTime.getDateString());
		String endDate = ServletRequestUtils.getStringParameter(request, "endDate",  DateTime.getDateString());
		ExpertPool expertPool = this.getExpertPoolManager().retrieve(SessionUtils.getUsername(request));
		
		try {
			WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh = (ValueListHandler) wc.getBean("expertPoolList", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();
			Map<String, String> filters = new HashMap<String, String>();
			filters.put(ValueListInfo.PAGING_PAGE, pageNo);
			filters.put(ValueListInfo.PAGING_NUMBER_PER, pageSize);
			
			if(expertPool.getCompanyPosition().equals("06CB")){
				filters.put("div", expertPool.getDept());
			}else{
				filters.put("dept", expertPool.getDept());
			}
			
			if (!startDate.equals("")) {
				startDate = StringUtil.replace(startDate, "-", "");
				if (startDate.length() < 8) {
					startDate = startDate;
				}
				filters.put("startDate", startDate);
			}
			
			if (!endDate.equals("")) {
				endDate = StringUtil.replace(endDate, "-", "");
				if (endDate.length() < 8) {
					endDate = endDate; 
				}
				filters.put("endDate", endDate);
			}

			info.setFilters(filters);
			ValueList valueList = vlh.getValueList("getExpertPoolWorkPeriodList_workDay2", info);

			request.setAttribute("list", valueList);
			request.setAttribute("jobClass", jobClass);
			request.setAttribute("startDate", startDate);
			request.setAttribute("endDate", endDate);
			request.setAttribute("today", StringUtil.getCurr("yyyy") + "-" + StringUtil.getCurr("MM") + "-" + StringUtil.getCurr("dd"));

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return mapping.findForward("expertPoolWorkPeriodList_workday2");
	}
	
	//예산 현황 확인
	public ActionForward getExpertPoolWorkPeriodList_budget(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String pageSize = ServletRequestUtils.getStringParameter(request, "pageSize", "1000");
		String pageNo = ServletRequestUtils.getStringParameter(request, "pageNo", "1");
		String jobClass = ServletRequestUtils.getStringParameter(request, "jobClass", "");
		String companyPosition = ServletRequestUtils.getStringParameter(request, "companyPosition", "");
		String selectCheck = ServletRequestUtils.getStringParameter(request, "selectCheck", "");
		String runningDeptCode = ServletRequestUtils.getStringParameter(request, "runningDeptCode", "");
		String startDate = ServletRequestUtils.getStringParameter(request, "startDate",  DateTime.getYear() + "-" + DateTime.getMonth() + "-01");
		String endDate = ServletRequestUtils.getStringParameter(request, "endDate", DateTime.getDateString());
		endDate = StringUtil.replace(endDate, "-", "");

		try {
			WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh = (ValueListHandler) wc.getBean("expertPoolList", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();
			Map<String, String> filters = new HashMap<String, String>();
			filters.put(ValueListInfo.PAGING_PAGE, pageNo);
			filters.put(ValueListInfo.PAGING_NUMBER_PER, pageSize);

			if (!startDate.equals("")) {
				startDate = StringUtil.replace(startDate, "-", "");
				if (startDate.length() < 8) {
					startDate = startDate;
				}
				filters.put("startDate", startDate);
			}
			if (!endDate.equals("")) {
				endDate = StringUtil.replace(endDate, "-", "");
				if (endDate.length() < 8) {
					endDate = endDate;
				}
				filters.put("endDate", endDate);
			}
			if (!runningDeptCode.equals("")) {			filters.put("runningDeptCode", runningDeptCode);			}
			filters.put("checkYN",  selectCheck);
			info.setFilters(filters);
			ValueList valueList = vlh.getValueList("getExpertPoolWorkPeriodList_budget", info);

			request.setAttribute("list", valueList);
			request.setAttribute("jobClass", jobClass);
			request.setAttribute("companyPosition", companyPosition);
			request.setAttribute("startDate", startDate);
			request.setAttribute("endDate", endDate);
			request.setAttribute("selectCheck", selectCheck);
			request.setAttribute("today", StringUtil.getCurr("yyyy") + "-" + StringUtil.getCurr("MM") + "-" + StringUtil.getCurr("dd"));
			request.setAttribute("runningDeptCode", runningDeptCode);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return mapping.findForward("expertPoolWorkPeriodList_budget");
	}
	
	/* 디렉터 투입현황 검색 */
	public ActionForward getExpertPoolWorkPeriodList_project(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String pageSize = ServletRequestUtils.getStringParameter(request, "pageSize", "1000");
		String pageNo = ServletRequestUtils.getStringParameter(request, "pageNo", "1");
		String userName = ServletRequestUtils.getStringParameter(request, "userName", "%%");
		String projectName = ServletRequestUtils.getStringParameter(request, "projectName", "%%");
		String keyword = ServletRequestUtils.getStringParameter(request, "keyword", "");
		String keywordType = ServletRequestUtils.getStringParameter(request, "keywordType", "");

		//System.out.println(userName + ":username " + projectName + ":projectName " + keyword + ":keyword " + keywordType + ":keyWordType");
		try {
			WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh = (ValueListHandler) wc.getBean("expertPoolList", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();
			Map<String, String> filters = new HashMap<String, String>();
			filters.put(ValueListInfo.PAGING_PAGE, pageNo);
			filters.put(ValueListInfo.PAGING_NUMBER_PER, pageSize);

			if(keywordType.equals("userName")){
				filters.put("userName", '%'+keyword+'%');
			}else if(keywordType.equals("projectName")){
				filters.put("projectName", '%'+keyword+'%');
			}
			
			info.setFilters(filters);
			ValueList valueList = vlh.getValueList("getExpertPoolWorkPeriodList_project", info);

			request.setAttribute("list", valueList);
			request.setAttribute("keyword", keyword);
			request.setAttribute("keywordType", keywordType);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return mapping.findForward("expertPoolWorkPeriodList_project"); 
	}
	
	/* 디렉터 투입현황 검색 */
	public ActionForward getExpertPoolWorkPeriodList_project2(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		return mapping.findForward("expertPoolWorkPeriodList_project2");
	}
	
	/* 90년생 엑스퍼트 검색 */
	public ActionForward getExpertPoolWorkPeriodList_exp3(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String pageSize = ServletRequestUtils.getStringParameter(request, "pageSize", "1000");
		String pageNo = ServletRequestUtils.getStringParameter(request, "pageNo", "1");

		try {
			WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh = (ValueListHandler) wc.getBean("expertPoolList", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();
			Map<String, String> filters = new HashMap<String, String>();
			filters.put(ValueListInfo.PAGING_PAGE, pageNo);
			filters.put(ValueListInfo.PAGING_NUMBER_PER, pageSize);

			info.setFilters(filters);
			ValueList valueList = vlh.getValueList("getExpertPoolWorkPeriodList_exp3", info);

			request.setAttribute("list", valueList);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return mapping.findForward("expertPoolWorkPeriodList_exp3");
	}
	
	/* 인사평가 결과 list */
	public ActionForward getExpertPoolWorkPeriodList_performance(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String ssn = ServletRequestUtils.getStringParameter(request, "ssn", "");
		
		if("".equals(ssn)){
			ssn = SessionUtils.getUsername(request);
		}
		
		String pageSize = ServletRequestUtils.getStringParameter(request, "pageSize", "10");
		String pageNo = ServletRequestUtils.getStringParameter(request, "pageNo", "1");

		try{
			WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh = (ValueListHandler) wc.getBean("expertPoolList", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();
			Map<String, String> filters = new HashMap<String, String>();
			filters.put(ValueListInfo.PAGING_PAGE, pageNo);
			filters.put(ValueListInfo.PAGING_NUMBER_PER, pageSize);
			filters.put("ssn", ssn);
			
			info.setFilters(filters);
			ValueList valueList = vlh.getValueList("getExpertPoolWorkPeriodList_performance", info);
			ValueList valueList2 = vlh.getValueList("getExpertPoolWorkPeriodList_score", info);
			//Collections.reverse((List<?>) valueList2);
			request.setAttribute("list", valueList);
			request.setAttribute("list2", valueList2);
		} catch (Exception e){
			logger.error(e.getMessage(), e);
		}
		
		return mapping.findForward("expertPoolWorkPeriodList_performance");
	}
	
	/* 인사평가 전체 list */
	public ActionForward getExpertPoolWorkPeriodList_performance_admin(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String pageSize = ServletRequestUtils.getStringParameter(request, "pageSize", "10");
		String pageNo = ServletRequestUtils.getStringParameter(request, "pageNo", "1");
		String name = ServletRequestUtils.getStringParameter(request, "name", "");
		String runningDeptCode = ServletRequestUtils.getStringParameter(request, "runningDeptCode", "");

		try{
			WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh = (ValueListHandler) wc.getBean("expertPoolList", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();
			Map<String, String> filters = new HashMap<String, String>();
			filters.put(ValueListInfo.PAGING_PAGE, pageNo);
			filters.put(ValueListInfo.PAGING_NUMBER_PER, pageSize);
			
			if (!name.equals("")){
				filters.put("name", "%" + name + "%");
			}
			if (!runningDeptCode.equals("")){
				filters.put("runningDeptCode", "%" + runningDeptCode + "%");
			}
			
			info.setFilters(filters);
			ValueList valueList = vlh.getValueList("getExpertPoolWorkPeriodList_performance_admin", info);
			request.setAttribute("list", valueList);
			request.setAttribute("name", name);
			request.setAttribute("runningDeptCode", runningDeptCode);
		} catch (Exception e){
			logger.error(e.getMessage(), e);
		}
		
		return mapping.findForward("expertPoolWorkPeriodList_performance_admin");
	}
	
	/* awarding 결과 list */
	public ActionForward getExpertPoolWorkPeriodList_awarding(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		String year = ServletRequestUtils.getStringParameter(request, "year", "2021");
		String pageSize = ServletRequestUtils.getStringParameter(request, "pageSize", "10");
		String pageNo = ServletRequestUtils.getStringParameter(request, "pageNo", "1");

		try{
			WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh = (ValueListHandler) wc.getBean("expertPoolList", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();
			Map<String, String> filters = new HashMap<String, String>();
			filters.put(ValueListInfo.PAGING_PAGE, pageNo);
			filters.put(ValueListInfo.PAGING_NUMBER_PER, pageSize);
			filters.put("year", year);
			
			info.setFilters(filters);
			ValueList valueList = vlh.getValueList("getExpertPoolWorkPeriodList_awarding", info);
			request.setAttribute("list", valueList);
			request.setAttribute("year", year);
		} catch (Exception e){
			logger.error(e.getMessage(), e);
		}
		
		return mapping.findForward("expertPoolWorkPeriodList_awarding");
	}
	
	/* awarding detail list */
	public ActionForward getExpertPoolWorkPeriodList_awardingDetail(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		String year = ServletRequestUtils.getStringParameter(request, "year", "2021");
		String pageSize = ServletRequestUtils.getStringParameter(request, "pageSize", "1000");
		String pageNo = ServletRequestUtils.getStringParameter(request, "pageNo", "1");

		try{
			WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh = (ValueListHandler) wc.getBean("expertPoolList", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();
			Map<String, String> filters = new HashMap<String, String>();
			filters.put(ValueListInfo.PAGING_PAGE, pageNo);
			filters.put(ValueListInfo.PAGING_NUMBER_PER, pageSize);
			filters.put("year", year);
			
			info.setFilters(filters);
			ValueList valueList = vlh.getValueList("getExpertPoolWorkPeriodList_awardingDetail", info);
			request.setAttribute("list", valueList);
			request.setAttribute("year", year);
		} catch (Exception e){
			logger.error(e.getMessage(), e);
		}
		
		return mapping.findForward("expertPoolWorkPeriodList_awardingDetail");
	}
	
	
	/* 조직도에서 검색 */
	public ActionForward searchExpert(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String searchName = StringUtil.isNull(request.getParameter("searchName"), "");
		searchName = URLDecoder.decode(searchName, "UTF-8");

		if (searchName.equals(""))
			searchName = "-------";
		// System.out.println("결과 : " + searchName);
		try {
			WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());

			ValueListHandler vlh = (ValueListHandler) wc.getBean("expertPoolList", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();
			Map<String, String> filters = new HashMap<String, String>();

			filters.put(ValueListInfo.PAGING_PAGE, "1");
			filters.put(ValueListInfo.PAGING_NUMBER_PER, String.valueOf(Integer.MAX_VALUE - 1));
			filters.put("name", "%" + searchName + "%");
			info.setFilters(filters);

			ValueList valueList = vlh.getValueList("getSearchExpertPoolList", info);

			request.setAttribute("list", valueList);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return mapping.findForward("orgTable");
	}

	public ActionForward orgFinderForProjectMember(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String callbackFunc = request.getParameter("callbackFunc");
		String init = request.getParameter("init");
		String projectCode = request.getParameter("projectCode");
		String searchName = StringUtil.isNull(request.getParameter("searchName"), "");
		searchName = URLDecoder.decode(searchName, "UTF-8");

		if (searchName.equals(""))
			searchName = "-------";
		// System.out.println("결과 : " + searchName);
		try {
			WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());

			ValueListHandler vlh = (ValueListHandler) wc.getBean("expertPoolList", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();
			Map<String, String> filters = new HashMap<String, String>();

			filters.put(ValueListInfo.PAGING_PAGE, "1");
			filters.put(ValueListInfo.PAGING_NUMBER_PER, String.valueOf(Integer.MAX_VALUE - 1));
			filters.put("name", "%" + searchName + "%");
			filters.put("projectCode", projectCode);
			info.setFilters(filters);

			ValueList valueList = vlh.getValueList("getSearchExpertPoolListForProjectMember", info);

			request.setAttribute("list", valueList);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		request.setAttribute("init", init);
		request.setAttribute("projectCode", projectCode);
		request.setAttribute("callbackFunc", callbackFunc);
		request.setAttribute("searchName", searchName.equals("-------") ? "" : searchName);
		return mapping.findForward("projectMemberOrgPopup");
	}

	public void expertSelect(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		HashMap<String, Object> map = new HashMap<String, Object>();
		String ssn = ServletRequestUtils.getRequiredStringParameter(request, "ssn");

		try {
			map.put("result", true);
			map.put("resultMsg", "전문가 정보 Load");
			map.put("expertPool", this.getExpertPoolManager().retrieve(ssn));
			AjaxUtil.successWrite(response, map);
		} catch (ExpertPoolException e) {
			logger.info(e.getMessage(), e);
			map.put("result", false);
			map.put("resultMsg", "정보를 찾는데 실패 하였습니다.");
			map.put("ErrMsg", e);
			AjaxUtil.failWrite(response, map);
		}
	}

	public void expertSelectAsName(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		HashMap<String, Object> map = new HashMap<String, Object>();
		String name = ServletRequestUtils.getRequiredStringParameter(request, "name");

		try {
			map.put("result", true);
			map.put("resultMsg", "전문가 정보 Load");
			map.put("expertPoolList", this.getExpertPoolManager().findAsName(name));
			AjaxUtil.successWrite(response, map);
		} catch (ExpertPoolException e) {
			logger.info(e.getMessage(), e);
			map.put("result", false);
			map.put("resultMsg", "정보를 찾는데 실패 하였습니다.");
			map.put("ErrMsg", e);
			AjaxUtil.failWrite(response, map);
		}
	}
	
	public void expertSelectAsUid(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		String uid = ServletRequestUtils.getStringParameter(request, "name", "");
		
		try {
			map.put("result", true);
			map.put("resultMsg", "전문가 정보 Load");
			map.put("expertPoolList", this.getExpertPoolManager().findAsUid(uid));
			AjaxUtil.successWrite(response, map);
		} catch (ExpertPoolException e) {
			logger.info(e.getMessage(), e);
			map.put("result", false);
			map.put("resultMsg", "정보를 찾는데 실패 하였습니다.");
			map.put("ErrMsg", e);
			AjaxUtil.failWrite(response, map);
		}
	}
	
	public ActionForward saveExpertPoolListToExcel(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		//String jobClass = ServletRequestUtils.getStringParameter(request, "jobClass", "");
		String name = ServletRequestUtils.getStringParameter(request, "name", "");

		String fileName = "인력POOL리스트.xls";
		response.setHeader("Content-Disposition", "attachment; filename=" + new String(fileName.getBytes("EUC-KR"), "8859_1") + ";");
		response.setHeader("Content-Description", "JSP Generated Data");
		
		try {
			WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh = (ValueListHandler) wc.getBean("expertPoolList", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();
			Map<String, String> filters = new HashMap<String, String>();

			if (!name.equals("")) {
				filters.put("name", "%" + name + "%");
			}
			/* 모든 직군의 인력을 검색할 수 있도록 검색 조건 해지
			if (!jobClass.equals("")) {
				filters.put("jobClass", jobClass);
			}*/
			filters.put("restrictUser", "N");
			info.setFilters(filters);

			ValueList result = vlh.getValueList("getExpertPoolListToExcel", info);
			request.setAttribute("list", result);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		
		return mapping.findForward("saveRestrictExpertPoolListToExcel");
	}
	public ActionForward saveRestrictExpertPoolListToExcel(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String restrictUser = ServletRequestUtils.getStringParameter(request, "restrictUser", "");
		String jobClass = ServletRequestUtils.getStringParameter(request, "jobClass", "");
		String name = ServletRequestUtils.getStringParameter(request, "name", "");

		String fileName = "투입제한인력리스트.xls";
		response.setHeader("Content-Disposition", "attachment; filename=" + new String(fileName.getBytes("EUC-KR"), "8859_1") + ";");
		response.setHeader("Content-Description", "JSP Generated Data");

		try {
			WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh = (ValueListHandler) wc.getBean("expertPoolList", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();
			Map<String, String> filters = new HashMap<String, String>();

			if (!name.equals("")) {
				filters.put("name", "%" + name + "%");
			}
			if (!restrictUser.equals("")) {
				filters.put("restrictUser", restrictUser);
			}
			if (!jobClass.equals("")) {
				filters.put("jobClass", jobClass);
			}
			filters.put("absence", "Y");
			info.setFilters(filters);

			ValueList result = vlh.getValueList("getRestrictExpertPoolListToExcel", info);
			request.setAttribute("list", result);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return mapping.findForward("saveRestrictExpertPoolListToExcel");
	}
	
	// 스케줄 내역 다운로드 Action
	public ActionForward saveRestrictExpertPoolListToExcel_schedule(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		/*String restrictUser = ServletRequestUtils.getStringParameter(request, "restrictUser", "");
		String jobClass = ServletRequestUtils.getStringParameter(request, "jobClass", "");
		String name = ServletRequestUtils.getStringParameter(request, "name", "");*/
		String startDate = ServletRequestUtils.getStringParameter(request, "startDate","");
		String endDate = ServletRequestUtils.getStringParameter(request, "endDate", "");
		System.out.println(startDate);
		System.out.println(endDate);
		
		String fileName = "스케줄현황.xls";
		response.setHeader("Content-Disposition", "attachment; filename=" + new String(fileName.getBytes("EUC-KR"), "8859_1") + ";");
		response.setHeader("Content-Description", "JSP Generated Data");

		try {
			WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh = (ValueListHandler) wc.getBean("expertPoolList", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();
			Map<String, String> filters = new HashMap<String, String>();

			/*if (!name.equals("")) {
				filters.put("name", "%" + name + "%");
			}
			if (!restrictUser.equals("")) {
				filters.put("restrictUser", restrictUser);
			}
			if (!jobClass.equals("")) {
				filters.put("jobClass", jobClass);
			}
			filters.put("absence", "Y");*/
			if (!startDate.equals("")) {
				startDate = StringUtil.replace(startDate, "-", "");
				if (startDate.length() < 8) {
					startDate = startDate;
				}
				filters.put("startDate", startDate);
			}
			if (!endDate.equals("")) {
				endDate = StringUtil.replace(endDate, "-", "");
				if (endDate.length() < 8) {
					endDate = endDate;
				}
				filters.put("endDate", endDate);
			}
			info.setFilters(filters);
			ValueList result = vlh.getValueList("getExpertPoolWorkPeriodList_schedule", info);
			request.setAttribute("list", result);
			request.setAttribute("startDate", startDate);
			request.setAttribute("endDate", endDate);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return mapping.findForward("saveRestrictExpertPoolListToExcel_schedule");
	}

	public ActionForward saveRestrictExpertPoolListToExcel_expert(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		/*String restrictUser = ServletRequestUtils.getStringParameter(request, "restrictUser", "");
		String jobClass = ServletRequestUtils.getStringParameter(request, "jobClass", "");
		String name = ServletRequestUtils.getStringParameter(request, "name", "");*/
		
		String fileName = "엑스퍼트 운영현황.xls";
		response.setHeader("Content-Disposition", "attachment; filename=" + new String(fileName.getBytes("EUC-KR"), "8859_1") + ";");
		response.setHeader("Content-Description", "JSP Generated Data");

		try {
			WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh = (ValueListHandler) wc.getBean("expertPoolList", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();
			Map<String, String> filters = new HashMap<String, String>();

			/*if (!name.equals("")) {
				filters.put("name", "%" + name + "%");
			}
			if (!restrictUser.equals("")) {
				filters.put("restrictUser", restrictUser);
			}
			if (!jobClass.equals("")) {
				filters.put("jobClass", jobClass);
			}
			filters.put("absence", "Y");*/
			info.setFilters(filters);
			ValueList result = vlh.getValueList("getExpertPoolWorkPeriodList_exp2", info);
			request.setAttribute("list", result);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return mapping.findForward("saveRestrictExpertPoolListToExcel_expert");
	}
	
	public ActionForward saveRestrictExpertPoolListToExcel_ra(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String jobClass = ServletRequestUtils.getStringParameter(request, "jobClass", "");
		String companyPosition = ServletRequestUtils.getStringParameter(request, "companyPosition", "");
		String fileName = "RA 운영현황.xls";
		response.setHeader("Content-Disposition", "attachment; filename=" + new String(fileName.getBytes("EUC-KR"), "8859_1") + ";");
		response.setHeader("Content-Description", "JSP Generated Data");

		try {
			WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh = (ValueListHandler) wc.getBean("expertPoolList", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();
			Map<String, String> filters = new HashMap<String, String>();

			if (!jobClass.equals("")) {
				filters.put("jobClass", jobClass);
			}
			if (!companyPosition.equals("") && !companyPosition.equals("!64GT")) {
				filters.put("companyPosition", companyPosition);
			}
			if (!companyPosition.equals("") && companyPosition.equals("!64GT")) {
				filters.put("notInclude", companyPosition);
			}
			info.setFilters(filters);
			ValueList valueList = vlh.getValueList("getExpertPoolWorkPeriodList", info);

			request.setAttribute("list", valueList);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return mapping.findForward("saveRestrictExpertPoolListToExcel_ra");
	}
	
	public ActionForward saveRestrictExpertPoolListToExcel_time(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		/*String restrictUser = ServletRequestUtils.getStringParameter(request, "restrictUser", "");
		String jobClass = ServletRequestUtils.getStringParameter(request, "jobClass", "");
		String name = ServletRequestUtils.getStringParameter(request, "name", "");*/
		String startDate = ServletRequestUtils.getStringParameter(request, "startDate","");
		String endDate = ServletRequestUtils.getStringParameter(request, "endDate", "");
		System.out.println(startDate);
		System.out.println(endDate);
		
		String fileName = "개인일정현황.xls";
		response.setHeader("Content-Disposition", "attachment; filename=" + new String(fileName.getBytes("EUC-KR"), "8859_1") + ";");
		response.setHeader("Content-Description", "JSP Generated Data");

		try {
			WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh = (ValueListHandler) wc.getBean("expertPoolList", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();
			Map<String, String> filters = new HashMap<String, String>();

			/*if (!name.equals("")) {
				filters.put("name", "%" + name + "%");
			}
			if (!restrictUser.equals("")) {
				filters.put("restrictUser", restrictUser);
			}
			if (!jobClass.equals("")) {
				filters.put("jobClass", jobClass);
			}
			filters.put("absence", "Y");*/
			if (!startDate.equals("")) {
				startDate = StringUtil.replace(startDate, "-", "");
				if (startDate.length() < 8) {
					startDate = startDate;
				}
				filters.put("startDate", startDate);
			}
			if (!endDate.equals("")) {
				endDate = StringUtil.replace(endDate, "-", "");
				if (endDate.length() < 8) {
					endDate = endDate;
				}
				filters.put("endDate", endDate);
			}
			info.setFilters(filters);
			ValueList result = vlh.getValueList("getExpertPoolWorkPeriodList_time", info);
			request.setAttribute("list", result);
			request.setAttribute("startDate", startDate);
			request.setAttribute("endDate", endDate);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return mapping.findForward("saveRestrictExpertPoolListToExcel_time");
	}

	public ActionForward saveReadyExpertPoolListToExcel(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String restrictUser = ServletRequestUtils.getStringParameter(request, "restrictUser", "");
		String jobClass = ServletRequestUtils.getStringParameter(request, "jobClass", "");
		String name = ServletRequestUtils.getStringParameter(request, "name", "");

		String fileName = "인력POOL외_인력_리스트.xls";
		response.setHeader("Content-Disposition", "attachment; filename=" + new String(fileName.getBytes("EUC-KR"), "8859_1") + ";");
		response.setHeader("Content-Description", "JSP Generated Data");

		try {
			WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh = (ValueListHandler) wc.getBean("expertPoolList", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();
			Map<String, String> filters = new HashMap<String, String>();

			if (!name.equals("")) {
				filters.put("name", "%" + name + "%");
			}
			if (!restrictUser.equals("")) {
				filters.put("restrictUser", restrictUser);
			}
			if (!jobClass.equals("")) {
				filters.put("jobClass", jobClass);
			}
			filters.put("absence", "N");
			info.setFilters(filters);

			ValueList result = vlh.getValueList("getRestrictExpertPoolListToExcel", info);
			request.setAttribute("list", result);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return mapping.findForward("saveReadyExpertPoolListToExcel");
	}
	
	/*예산현황 엑셀 다운로드*/
	public ActionForward saveExpertPoolWorkPeriodList_budget(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String selectCheck = ServletRequestUtils.getStringParameter(request, "selectCheck", "");
		String startDate = ServletRequestUtils.getStringParameter(request, "startDate",  DateTime.getYear() + "-" + DateTime.getMonth() + "-01");
		String endDate = ServletRequestUtils.getStringParameter(request, "endDate", DateTime.getDateString());
		String runningDeptCode = ServletRequestUtils.getStringParameter(request, "runningDeptCode", "");
		endDate = StringUtil.replace(endDate, "-", "");
		String fileName = "프로젝트 예산현황.xls";
		response.setHeader("Content-Disposition", "attachment; filename=" + new String(fileName.getBytes("EUC-KR"), "8859_1") + ";");
		response.setHeader("Content-Description", "JSP Generated Data");

		try {
				WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
				ValueListHandler vlh = (ValueListHandler) wc.getBean("expertPoolList", ValueListHandler.class);
				ValueListInfo info = new ValueListInfo();
				Map<String, String> filters = new HashMap<String, String>();

				if (!startDate.equals("")) {
					startDate = StringUtil.replace(startDate, "-", "");
					if (startDate.length() < 8) {
						startDate = startDate;
					}
					filters.put("startDate", startDate);
				}
				if (!endDate.equals("")) {
					endDate = StringUtil.replace(endDate, "-", "");
					if (endDate.length() < 8) {
						endDate = endDate;
					}
					filters.put("endDate", endDate);
				}
				filters.put("runningDeptCode", runningDeptCode);
				filters.put("checkYN",  selectCheck);
				info.setFilters(filters);
				ValueList valueList = vlh.getValueList("getExpertPoolWorkPeriodList_budget", info);

				request.setAttribute("list", valueList);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return mapping.findForward("saveRestrictExpertPoolListToExcel_budget");
	}
	
	/*예산현황 엑셀 다운로드*/
	public ActionForward saveExpertPoolWorkPeriodList_taxbill(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	
		String fileName = "미등록 세금계산서.xls";
		response.setHeader("Content-Disposition", "attachment; filename=" + new String(fileName.getBytes("EUC-KR"), "8859_1") + ";");
		response.setHeader("Content-Description", "JSP Generated Data");

		try {
				WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
				ValueListHandler vlh = (ValueListHandler) wc.getBean("expertPoolList", ValueListHandler.class);
				ValueListInfo info = new ValueListInfo();
				Map<String, String> filters = new HashMap<String, String>();
				info.setFilters(filters);
				
				ValueList valueList = vlh.getValueList("getExpertPoolWorkPeriodList_taxbill", info);

				request.setAttribute("list", valueList);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return mapping.findForward("saveRestrictExpertPoolListToExcel_taxbill");
	}

	/* 조직도 팝업 */
	public ActionForward orgFinder(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String callbackFunc = request.getParameter("callbackFunc");
		request.setAttribute("callbackFunc", callbackFunc);

		String searchValue = request.getParameter("searchValue");
		request.setAttribute("searchValue", searchValue);

		return mapping.findForward("orgFinderPage");
	}

	public ActionForward zipCodeSearch(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String callbackFunc = request.getParameter("callbackFunc");
		request.setAttribute("callbackFunc", callbackFunc);
		return mapping.findForward("zipCodeSearch");
	}

	public ActionForward relationWithKmacSearch(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String callbackFunc = request.getParameter("callbackFunc");
		request.setAttribute("callbackFunc", callbackFunc);
		return mapping.findForward("relationWithKmacSearch");
	}

	public void setDailyDownloadLimit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String ssn = ServletRequestUtils.getRequiredStringParameter(request, "limitTargetSsn");
		String isLimit = ServletRequestUtils.getRequiredStringParameter(request, "limitOpenYn");

		try {
			this.expertPoolManager.updateDailyDownloadLimitState(ssn, isLimit);
			AjaxUtil.successWrite(response);
		} catch (Exception e) {
			AjaxUtil.failWrite(response);
			logger.error(e.getMessage(), e);
		}
	}
	public void setBudgetCheck(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap map = new HashMap();
		try {
			String[] chkBudget = request.getParameterValues("chkRole");
			
			for (int i = 0; i < chkBudget.length; i++) {
				System.out.println(chkBudget[i]);
				this.expertPoolManager.insertBudgetCheck(chkBudget[i]);
			}
			AjaxUtil.successWrite(response);

		} catch (CodeException e) {
			AjaxUtil.failWrite(response);
			logger.error(e.getMessage(), e);
		}
	}
}
