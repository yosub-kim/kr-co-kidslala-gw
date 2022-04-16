/*
 * $Id: MobileExpertPoolManagerAction.java,v 1.4 2018/01/03 05:03:34 cvs Exp $
 * created by : jiwoongLee creation-date : 2006. 1. 17
 * ========================================================= Copyright (c) 2006
 * Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.expertpool.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.kmac.pms.common.ajax.AjaxUtil;
import kr.co.kmac.pms.common.util.SessionUtils;
import kr.co.kmac.pms.common.util.StringUtil;
import kr.co.kmac.pms.expertpool.data.ExpertPool;
import kr.co.kmac.pms.expertpool.exception.ExpertPoolException;
import kr.co.kmac.pms.expertpool.manager.ExpertPoolManager;
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
 * @version $Id: MobileExpertPoolManagerAction.java,v 1.15 2010/08/18 08:49:43 cvs1
 *          Exp $
 */
/**
 * @struts.action name="mobileExpertPoolManagerAction" path="/action/MobileExpertPoolManagerAction" parameter="mode" scope="request"
 * @struts.action-forward name="expertPoolList" path="/m/web/expertpool/expertpoolList.jsp" redirect="false"
 * 
 * @struts.action-forward name="expertPoolExtList" path="/expertPool/expertPoolExtList.jsp" redirect="false"
 * @struts.action-forward name="projectExpertPoolList" path="/expertPool/projectExpertPoolList.jsp" redirect="false"
 * @struts.action-forward name="restrictExpertPoolList" path="/expertPool/restrictExpertPoolList.jsp" redirect="false"
 * @struts.action-forward name="readyExpertPoolList" path="/expertPool/readyExpertPoolList.jsp" redirect="false"
 * @struts.action-forward name="orgTable" path="/common/org/orgTable.jsp" redirect="false"
 * @struts.action-forward name="expertPoolForm" path="/expertPool/expertPoolForm.jsp" redirect="false"
 * @struts.action-forward name="expertPoolView" path="/expertPool/expertPoolView.jsp" redirect="false"
 * @struts.action-forward name="orgFinderPage" path="/common/org/orgFinder.jsp" redirect="false"
 * @struts.action-forward name="projectMemberOrgPopup" path="/sanction/memberChabngeOrgPopup/projectMemberOrgFinder.jsp" redirect="false"
 * @struts.action-forward name="zipCodeSearch" path="/popup/zipCodeSearch.jsp" redirect="false"
 * @struts.action-forward name="relationWithKmacSearch" path="/popup/relationWithKmac.jsp" redirect="false"
 * @struts.action-forward name="MyInfoForm" path="/expertPool/myInfoForm.jsp" redirect="false"
 * @struts.action-forward name="expertPoolList2" path="/expertPool/jsp/expertPoolList2.jsp" redirect="false"
 * @struts.action-forward name="expertPoolDetail" path="/expertPool/jsp/expertPoolDetail.jsp" redirect="false"
 * @struts.action-forward name="saveReadyExpertPoolListToExcel" path="/expertPool/saveReadyExpertPoolListToExcel.jsp" redirect="false"
 * @struts.action-forward name="saveRestrictExpertPoolListToExcel" path="/expertPool/saveRestrictExpertPoolListToExcel.jsp" redirect="false"
 */
public class MobileExpertPoolManagerAction extends DispatchActionSupport {

	private static final Log logger = LogFactory.getLog(MobileExpertPoolManagerAction.class);
	private ExpertPoolManager expertPoolManager;

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
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		// //

		request.setAttribute("expertPool", expertPool);

		return mapping.findForward("expertPoolView");

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
	public ActionForward getExpertPoolExtListForMobile(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
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
		ExpertPool expertPool = this.getExpertPoolManager().retrieve(SessionUtils.getUsername(request));

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
			List<ExpertPool> list = null;
			if(expertPool.getJobClass().equals("A") || expertPool.getJobClass().equals("B")){
				list = this.getExpertPoolManager().getExpertPoolExtList(filters);
			}else{
				list = this.getExpertPoolManager().getExpertPoolExtList2(filters);
			}

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
		return mapping.findForward("expertPoolExtListForMobile");
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
		String pageSize = ServletRequestUtils.getStringParameter(request, "pageSize", "15");
		String pg = ServletRequestUtils.getStringParameter(request, "pg", "1");
		String keyfield = ServletRequestUtils.getStringParameter(request, "keyfield", "");
		String jobClass = ServletRequestUtils.getStringParameter(request, "jobClass", "A");
		String keyword = ServletRequestUtils.getStringParameter(request, "keyword", "");
		String selectedGroup = ServletRequestUtils.getStringParameter(request, "selectedGroup", "");
		String[] specialField = ServletRequestUtils.getStringParameters(request, "specialField");

		try {
			ValueListInfo info = new ValueListInfo();
			HashMap<String, String> filters = new HashMap<String, String>();

			filters.put("pg", pg);
			filters.put("keyfield", keyfield);

			if (keyfield.equals(""))
				filters.put("jobClass", jobClass);
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

			info.setFilters(filters);

			int cnt = this.getExpertPoolManager().getExpertPoolExtCnt(filters);
			List<ExpertPool> list = this.getExpertPoolManager().getExpertPoolExtList(filters);

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
		return mapping.findForward("expertPoolExtList");
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

}
