/*
 * $Id: PopupConfigAction.java,v 1.1 2009/09/19 11:15:45 cvs3 Exp $
 * created by    : jiwoongLee
 * creation-date : 2005. 10. 27
 * =========================================================
 * Copyright (c) 2005 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.system.popup.action;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.kmac.pms.common.ajax.AjaxUtil;
import kr.co.kmac.pms.common.code.exception.CodeException;
import kr.co.kmac.pms.common.util.DateUtil;
import kr.co.kmac.pms.common.util.SessionUtils;
import kr.co.kmac.pms.common.util.StringUtil;
import kr.co.kmac.pms.project.manpower.data.ProjectManpower;
import kr.co.kmac.pms.project.manpower.manager.ProjectManpowerManager;
import kr.co.kmac.pms.project.master.data.ProjectWorkName;
import kr.co.kmac.pms.project.master.manager.ProjectMasterInfoManager;
import kr.co.kmac.pms.system.popup.dao.PopUpConfigDAO;
import kr.co.kmac.pms.system.popup.data.PopUpConfig;
import kr.co.kmac.pms.system.popup.manager.PopUpConfigManager;
import kr.co.kmac.pms.system.processcategory.data.ProcessCategory;
import kr.co.kmac.pms.system.processcategory.manager.ProcessCategoryManager;
import net.mlw.vlh.ValueListHandler;
import net.mlw.vlh.ValueListInfo;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.struts.DispatchActionSupport;
/**
 * 팝업 설정을 위한 Action Class <br>
 * 아래 스크립트를 left.jsp에 활성화 시키면 팝업 컬트롤러를 사용할 수 있다. <br>
 * <code>
 * function popUp() {
 * 	if("<%=PopUpConfig.isPopUpEnable()%>" == "Y") {
 * 	 var width = <%=PopUpConfig.getPopUpWidth()%>;
 * 	 var height = <%=PopUpConfig.getPopUpHeight()%>;
 * 	 var screenHeight = screen.height;
 * 	 var screenWidth = screen.width;
 * 	 var leftpos = screenWidth / 2 - width / 2;
 * 	 var toppos = screenHeight / 2 - height / 2;
 * 	 window.open('/popUp.jsp','','toolbar=no,location=no,status=no,menubar=no,scrollbars=no,resizable=yes,width='+width+',height='+height+',left='+leftpos+',top='+toppos+'')
 *  }
 * }
 * </code>
 * @author jiwoongLee
 * @version $Id: PopupConfigAction.java,v 1.1 2009/09/19 11:15:45 cvs3 Exp $
 */
/**
 * @struts.action name="responseForm" path="/action/PopupConfigAction" parameter="mode" scope="request"
 * @struts.action-forward name="popUp" path="/system/popup/PopUpConfig.jsp" redirect="false"
 */
public class PopupConfigAction extends DispatchActionSupport {
	private PopUpConfigDAO popUpConfigDAO;
	private PopUpConfigManager popUpConfigManager;
	private ProjectManpowerManager projectManpowerManager;
	private ProjectMasterInfoManager projectMasterInfoManager;

	public ProjectManpowerManager getProjectManpowerManager() {
		return projectManpowerManager;
	}

	public void setProjectManpowerManager(
			ProjectManpowerManager projectManpowerManager) {
		this.projectManpowerManager = projectManpowerManager;
	}

	public ProjectMasterInfoManager getProjectMasterInfoManager() {
		return projectMasterInfoManager;
	}

	public void setProjectMasterInfoManager(
			ProjectMasterInfoManager projectMasterInfoManager) {
		this.projectMasterInfoManager = projectMasterInfoManager;
	}

	/**
	 * @return Returns the popUpConfigManager.
	 */
	public PopUpConfigManager getPopUpConfigManager() {
		return this.popUpConfigManager;
	}

	/**
	 * @param popUpConfigManager The popUpConfigManager to set.
	 */
	public void setPopUpConfigManager(PopUpConfigManager popUpConfigManager) {
		this.popUpConfigManager = popUpConfigManager;
	}

	/**
	 * @return Returns the popUpConfigDAO.
	 */
	public PopUpConfigDAO getPopUpConfigDAO() {
		return this.popUpConfigDAO;
	}

	/**
	 * @param popUpConfigDAO The popUpConfigDAO to set.
	 */
	public void setPopUpConfigDAO(PopUpConfigDAO popUpConfigDAO) {
		this.popUpConfigDAO = popUpConfigDAO;
	}

	/**
	 * 팝업 반환
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getPopUp(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		PopUpConfig popUpConfig = getPopUpConfigDAO().getPopUp();
		popUpConfig.setContent(popUpConfig.getContent());
		request.setAttribute("popUp", popUpConfig);
		return mapping.findForward("popUp");
	}
	
	public void projectActivityList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();

		String projectCode = ServletRequestUtils.getStringParameter(request, "projectCode", "");
		System.out.println(projectCode);
		try {
			List<ProjectManpower> list = this.getProjectManpowerManager().getProjectActivity(projectCode);
			System.out.println(list.size());
			map.put("projectActivityList", list);
			AjaxUtil.successWrite(response, map);
		} catch (Exception e) {
			AjaxUtil.failWrite(response);
		}
	}
	
	/**
	 * schedule activeity 목록
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getPopupActivity(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		String ssn = SessionUtils.getUsername(request);
		
		//ssn project info		
		List<ProjectManpower> projectManpowerList = this.getProjectManpowerManager().getProjectRunningInfo(ssn);

		request.setAttribute("projectInfo", projectManpowerList);
		request.setAttribute("ssn", ssn);
		return mapping.findForward("popUpSchedule");
	}

	/**
	 * 팝업 업데이트
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public void updatePopUp(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HashMap<String, Serializable> map = new HashMap<String, Serializable>();
		try {
		
			String width = request.getParameter("width");
			String height = request.getParameter("height");
			String isEnable = ServletRequestUtils.getStringParameter(request, "isEnable", "N");
			String content = request.getParameter("content");
			
			PopUpConfig popUpConfig = new PopUpConfig();
			popUpConfig.setWidth(width);
			popUpConfig.setHeight(height);
			popUpConfig.setIsEnable(isEnable);
			popUpConfig.setContent(content);
	
			boolean res = getPopUpConfigDAO().updatePopUp(popUpConfig);
			String retMsg = "";
			if (res) {
				retMsg = "작업이 정상적으로 처리되었습니다.";
			} else {
				retMsg = "데이터 저장에 오류가 발생하였습니다.";
			}
			
			//JSONWriter writer = new JSONWriter();
			map.put("result", true);
			map.put("resultMsg", retMsg);
			
			AjaxUtil.successWrite(response, map);
		
		} catch (CodeException e) {
			map.put("result", false);
			map.put("resultMsg", "설정 저장 실패!");
			map.put("ErrMsg", e);
			AjaxUtil.failWrite(response, map);
		}
	}
	
	public void storeProjectManpower(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String projectCode = ServletRequestUtils.getStringParameter(request, "workSeq", "");
		String startDate = StringUtil.replace(ServletRequestUtils.getStringParameter(request, "startDate", ""), "-", "");
		String endDate = StringUtil.replace(ServletRequestUtils.getStringParameter(request, "endDate", ""), "-", "");
		boolean chkWeek1 = ServletRequestUtils.getBooleanParameter(request, "chkWeek1", false);
		boolean chkWeek2 = ServletRequestUtils.getBooleanParameter(request, "chkWeek2", false);
		boolean chkWeek3 = ServletRequestUtils.getBooleanParameter(request, "chkWeek3", false);
		boolean chkWeek4 = ServletRequestUtils.getBooleanParameter(request, "chkWeek4", false);
		boolean chkWeek5 = ServletRequestUtils.getBooleanParameter(request, "chkWeek5", false);
		boolean chkWeek6 = ServletRequestUtils.getBooleanParameter(request, "chkWeek6", false);
		boolean chkWeek7 = ServletRequestUtils.getBooleanParameter(request, "chkWeek7", false);
		String[] ssn = ServletRequestUtils.getStringParameters(request, "ssn");
		String workSeq = ServletRequestUtils.getStringParameter(request, "activitySeq", null);

		try {
			// 지도일정 저장
			List<ProjectManpower> projectManpowerList = new ArrayList<ProjectManpower>();
			Calendar sDate = DateUtil.getCalendarInstance(startDate);
			if (endDate.equals("")) {
				ProjectManpower projectManpower = new ProjectManpower();
				projectManpower.setProjectCode(projectCode);
				projectManpower.setYear(String.valueOf(sDate.get(Calendar.YEAR)));
				projectManpower.setMonth(String.valueOf(sDate.get(Calendar.MONTH) + 1));
				projectManpower.setMonth((sDate.get(Calendar.MONTH) + 1) >= 10 ? String.valueOf((sDate.get(Calendar.MONTH) + 1)) : "0"
						+ String.valueOf((sDate.get(Calendar.MONTH) + 1)));
				projectManpower.setDay(sDate.get(Calendar.DAY_OF_MONTH) >= 10 ? String.valueOf(sDate.get(Calendar.DAY_OF_MONTH)) : "0"
						+ String.valueOf(sDate.get(Calendar.DAY_OF_MONTH)));
				projectManpower.setSsnArray(ssn);

				projectManpower.setWorkSeq(workSeq);
				WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
				ValueListHandler vlh = (ValueListHandler) wc.getBean("projectValueList", ValueListHandler.class);
				ValueListInfo info = new ValueListInfo();
				Map<String, String> filters = new HashMap<String, String>();
				filters.put("projectCode", projectCode);
				filters.put("workSeq", workSeq);
				filters.put(ValueListInfo.PAGING_NUMBER_PER, String.valueOf(Integer.MAX_VALUE - 1));
				info.setFilters(filters);
				ProjectWorkName projectWorkName = (ProjectWorkName) vlh.getValueList("projectWorkNameListSelect", info).getList().get(0);
				projectManpower.setWorkName(projectWorkName.getWorkName());

				this.getProjectManpowerManager().storeProjectManpower(projectManpower);
			} else {
				Calendar eDate = DateUtil.getCalendarInstance(endDate);
				while (DateUtil.getDifferDays(sDate, eDate) >= 0) {
					ProjectManpower projectManpower = new ProjectManpower();
					projectManpower.setProjectCode(projectCode);
					projectManpower.setYear(String.valueOf(sDate.get(Calendar.YEAR)));
					projectManpower.setMonth((sDate.get(Calendar.MONTH) + 1) >= 10 ? String.valueOf((sDate.get(Calendar.MONTH) + 1)) : "0"
							+ String.valueOf((sDate.get(Calendar.MONTH) + 1)));
					projectManpower.setDay(sDate.get(Calendar.DAY_OF_MONTH) >= 10 ? String.valueOf(sDate.get(Calendar.DAY_OF_MONTH)) : "0"
							+ String.valueOf(sDate.get(Calendar.DAY_OF_MONTH)));
					projectManpower.setSsnArray(ssn);

					projectManpower.setWorkSeq(workSeq);
					WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
					ValueListHandler vlh = (ValueListHandler) wc.getBean("projectValueList", ValueListHandler.class);
					ValueListInfo info = new ValueListInfo();
					Map<String, String> filters = new HashMap<String, String>();
					filters.put("projectCode", projectCode);
					filters.put("workSeq", workSeq);
					filters.put(ValueListInfo.PAGING_NUMBER_PER, String.valueOf(Integer.MAX_VALUE - 1));
					info.setFilters(filters);
					ProjectWorkName projectWorkName = (ProjectWorkName) vlh.getValueList("projectWorkNameListSelect", info).getList().get(0);
					projectManpower.setWorkName(projectWorkName.getWorkName());

					int weekInfo = sDate.get(Calendar.DAY_OF_WEEK);
					int monthInfo = sDate.get(Calendar.MONTH);
					int weekInfo2 = sDate.get(Calendar.DATE);

					switch (weekInfo) {
					case Calendar.MONDAY:
						if (chkWeek1) {
							projectManpowerList.add(projectManpower);
						}
						break;
					case Calendar.TUESDAY:
						if (chkWeek2) {
							projectManpowerList.add(projectManpower);
						}
						break;
					case Calendar.WEDNESDAY:
						if (chkWeek3) {
							projectManpowerList.add(projectManpower);
						}
						break;
					case Calendar.THURSDAY:
						if (chkWeek4) {
							System.out.println(Calendar.MONTH + " " + Calendar.DAY_OF_MONTH);
							projectManpowerList.add(projectManpower);
						}
						break;
					case Calendar.FRIDAY:
						if (chkWeek5) {
							projectManpowerList.add(projectManpower);
						}
						break;
					case Calendar.SATURDAY:
						if (chkWeek6) {
							projectManpowerList.add(projectManpower);
						}
						break;
					case Calendar.SUNDAY:
						if (chkWeek7) {
							projectManpowerList.add(projectManpower);
						}
						break;
					}

					sDate.add(Calendar.DATE, 1);
				}
				this.getProjectManpowerManager().storeProjectManpower(projectManpowerList);
			}

			AjaxUtil.successWrite(response);
		} catch (Exception e) {
			AjaxUtil.failWrite(response);
		}
	}
}