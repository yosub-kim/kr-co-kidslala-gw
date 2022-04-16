/*
 * $Id: AuthorityAction.java,v 1.4 2016/07/05 07:04:29 cvs Exp $
 * created by    : 안성호
 * creation-date : 2006. 5. 1.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.system.authority.action;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.kmac.pms.common.ajax.AjaxUtil;
import kr.co.kmac.pms.common.ajax.JSONWriter;
import kr.co.kmac.pms.common.form.ResponseForm;
import kr.co.kmac.pms.common.menuMy.data.MenuMy;
import kr.co.kmac.pms.common.menuMy.manager.MenuMyManager;
import kr.co.kmac.pms.common.util.IdGenerator;
import kr.co.kmac.pms.common.util.SessionUtils;
import kr.co.kmac.pms.common.util.StringUtil;
import kr.co.kmac.pms.expertpool.data.ExpertPool;
import kr.co.kmac.pms.expertpool.exception.ExpertPoolException;
import kr.co.kmac.pms.expertpool.manager.ExpertPoolManager;
import kr.co.kmac.pms.schedule.manager.ScheduleManager;
import kr.co.kmac.pms.system.authority.data.MenuData;
import kr.co.kmac.pms.system.authority.data.RoleData;
import kr.co.kmac.pms.system.authority.manager.AuthorityManager;
import kr.co.kmac.pms.system.gadget.data.Content;
import kr.co.kmac.pms.system.gadget.data.Gadget;
import kr.co.kmac.pms.system.gadget.manager.GadgetManager;
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
 * @struts.action name="responseForm" path="/action/AuthorityAction" parameter="mode" scope="request"
 * @struts.action-forward name="menuListPage" path="/system/authority/menuList.jsp" redirect="false"
 * @struts.action-forward name="menuFormPage" path="/system/authority/menuForm.jsp" redirect="false"
 * @struts.action-forward name="roleListPage" path="/system/authority/roleList.jsp" redirect="false"
 * @struts.action-forward name="roleFormPage" path="/system/authority/roleForm.jsp" redirect="false"
 * @struts.action-forward name="roleEditPage" path="/system/authority/roleEdit.jsp" redirect="false"
 * @struts.action-forward name="topPage" path="/top.jsp" redirect="false"
 * @struts.action-forward name="main" path="/main.jsp" redirect="false"
 * @struts.action-forward name="leftPage" path="/left.jsp" redirect="false"
 * @struts.action-forward name="roleRsrvPage" path="/system/authority/roleRsrv.jsp" redirect="false"
 * @struts.action-forward name="roleUserSet" path="/system/authority/roleUserSet.jsp" redirect="false"
 * @struts.action-forward name="roleExtUserSet" path="/system/authority/roleExtUserSet.jsp" redirect="false"
 * @struts.action-forward name="authorityInit" path="/authority/jsp/authorityInit.jsp" redirect="false"
 */

public class AuthorityAction extends DispatchActionSupport {
	private static final Log logger = LogFactory.getLog(AuthorityAction.class);
	private AuthorityManager authorityManager;
	private ExpertPoolManager expertPoolManager;
	private ScheduleManager scheduleManager;
	private MenuMyManager menuMyManager;
	private GadgetManager gadgetManager;

	public GadgetManager getGadgetManager() {
		return gadgetManager;
	}

	public void setGadgetManager(GadgetManager gadgetManager) {
		this.gadgetManager = gadgetManager;
	}

	public AuthorityManager getAuthorityManager() {
		return authorityManager;
	}

	public void setAuthorityManager(AuthorityManager authorityManager) {
		this.authorityManager = authorityManager;
	}

	public ExpertPoolManager getExpertPoolManager() {
		return expertPoolManager;
	}

	public void setExpertPoolManager(ExpertPoolManager expertPoolManager) {
		this.expertPoolManager = expertPoolManager;
	}
	
	public ScheduleManager getScheduleManager() {
		return scheduleManager;
	}

	public void setScheduleManager(ScheduleManager scheduleManager) {
		this.scheduleManager = scheduleManager;
	}
	
	public MenuMyManager getMenuMyManager() {
		return menuMyManager;
	}

	public void setMenuMyManager(MenuMyManager menuMyManager) {
		this.menuMyManager = menuMyManager;
	}

	public ActionForward menuRetrieve(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String searchMenuSort = request.getParameter("searchMenuSort");
		String searchMenuName = request.getParameter("searchMenuName");
		String searchMenuPath = request.getParameter("searchMenuPath");
		String searchMenuType = request.getParameter("searchMenuType");
		String searchUseYN = request.getParameter("searchUseYN");

		List menuList = authorityManager.menuRetrieve("", searchMenuName, searchMenuPath, searchMenuType, searchUseYN, searchMenuSort);

		request.setAttribute("searchMenuSort", searchMenuSort);
		request.setAttribute("searchMenuName", searchMenuName);
		request.setAttribute("searchMenuPath", searchMenuPath);
		request.setAttribute("searchMenuType", searchMenuType);
		request.setAttribute("searchUseYN", searchUseYN);

		request.setAttribute("menuList", menuList);

		return mapping.findForward("menuListPage");
	}

	public ActionForward menuForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		String menuNum = request.getParameter("menuNum");

		List menuInfo = authorityManager.menuSelect(menuNum);
		if (menuInfo.size() > 0) {
			request.setAttribute("ttlGbn", "수정");
		} else {
			request.setAttribute("ttlGbn", "입력");
		}
		request.setAttribute("menuInfo", menuInfo);

		return mapping.findForward("menuFormPage");
	}

	public void saveMenu(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ResponseForm result = (ResponseForm) form;
		HashMap map = new HashMap();
		try {

			String menuSaveMode = request.getParameter("menuSaveMode");
			String menuNum = request.getParameter("menuNum");
			String menuName = request.getParameter("menuName");
			String menuPath = request.getParameter("menuPath");
			String menuType = request.getParameter("menuType");
			String menuUseInfo = request.getParameter("menuUseInfo");
			String menuSort = request.getParameter("menuSort");

			String[] chkMenu = request.getParameterValues("chkMenu");

			if (menuSaveMode.equals("DELETE")) {
				for (int i = 0; i < chkMenu.length; i++) {
					map.put("sMenu" + i, chkMenu[i]);
					authorityManager.menuRemove(chkMenu[i]);
				}
			} else if (menuSaveMode.equals("INSERT")) {

				MenuData menuData = new MenuData();
				menuData.setMenuNum(menuNum);
				menuData.setMenuName(menuName);
				menuData.setMenuPath(menuPath);
				menuData.setMenuType(menuType);
				menuData.setMenuUseInfo(menuUseInfo);
				menuData.setMenuSort(menuSort);

				menuData.setMenuNum(IdGenerator.generate(IdGenerator.PREFIX_MENU));
				authorityManager.menuCreate(menuData);
			} else if (menuSaveMode.equals("UPDATE")) {

				MenuData menuData = new MenuData();
				menuData.setMenuNum(menuNum);
				menuData.setMenuName(menuName);
				menuData.setMenuPath(menuPath);
				menuData.setMenuType(menuType);
				menuData.setMenuUseInfo(menuUseInfo);
				menuData.setMenuSort(menuSort);

				authorityManager.menuStore(menuData);

			}

			JSONWriter writer = new JSONWriter();
			map.put("result", true);
			map.put("resultMsg", "처리 되었습니다.");
			AjaxUtil.successWrite(response, map);

		} catch (ExpertPoolException e) {

			logger.info(e.getMessage(), e);
			map.put("result", false);
			map.put("resultMsg", "처리 중 오류가 발생했습니다.");
			map.put("ErrMsg", e);
			AjaxUtil.failWrite(response, map);
		}
	}

	public ActionForward roleForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		String roleNum = request.getParameter("roleNum");
		if (roleNum.equals(""))
			roleNum = "Nothing";
		// List menuInfo = authorityManager.menuSelect(menuNum);
		List roleInfo = authorityManager.roleRetrieve(roleNum, "", "");

		if (roleInfo.size() > 0) {
			request.setAttribute("ttlGbn", "수정");
		} else {
			request.setAttribute("ttlGbn", "입력");
		}
		request.setAttribute("roleInfo", roleInfo);

		return mapping.findForward("roleFormPage");
	}

	public ActionForward roleDetailForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String roleNum = request.getParameter("roleNum");
		if (roleNum.equals(""))
			roleNum = "Nothing";

		List roleInfo = authorityManager.roleRetrieve(roleNum, "", "");

		if (roleInfo.size() > 0) {
			request.setAttribute("ttlGbn", "수정");
		} else {
			request.setAttribute("ttlGbn", "입력");
		}

		List menuList = authorityManager.menuRetrieve("", "", "", "", "Y", "");

		request.setAttribute("menuList", menuList);
		request.setAttribute("roleNum", roleNum);

		return mapping.findForward("roleEditPage");
	}

	public ActionForward roleRetrieve(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String roleNum = request.getParameter("roleNum");
		String roleName = request.getParameter("roleName");
		// String roleXml = request.getParameter("roleXml");
		String roleUseInfo = request.getParameter("roleUseInfo");

		List roleList = authorityManager.roleRetrieve(roleNum, roleName, roleUseInfo);
		request.setAttribute("roleList", roleList);
		return mapping.findForward("roleListPage");
	}

	public ActionForward setStoreMenu(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ResponseForm result = (ResponseForm) form;
		try {
			String[] menuNum = request.getParameterValues("menuNum");
			String[] menuName = request.getParameterValues("menuName");
			String[] menuPath = request.getParameterValues("menuPath");
			String[] menuType = request.getParameterValues("menuType");
			String[] menuUseInfo = request.getParameterValues("menuUseInfo");
			String[] menuSaveMode = request.getParameterValues("menuSaveMode");
			String[] menuSort = request.getParameterValues("menuSort");

			for (int i = 0; i < menuName.length; i++) {
				MenuData menuData = new MenuData();
				menuData.setMenuNum(menuNum[i]);
				menuData.setMenuName(menuName[i]);
				menuData.setMenuPath(menuPath[i]);
				menuData.setMenuType(menuType[i]);
				menuData.setMenuUseInfo(menuUseInfo[i]);
				menuData.setMenuSort(menuSort[i]);

				if ("del".equals(menuSaveMode[i])) {
					authorityManager.menuRemove(menuNum[i]);
				} else if ("modi".equals(menuSaveMode[i])) {
					authorityManager.menuStore(menuData);
				} else if ("new".equals(menuSaveMode[i])) {
					menuData.setMenuNum(IdGenerator.generate(IdGenerator.PREFIX_MENU));
					authorityManager.menuCreate(menuData);
				}
			}
			result.setMessage("처리 되었습니다.");
			result.setSuccess(true);
			request.setAttribute("result", result);

		} catch (Throwable e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			result.setSuccess(false);
			result.setMessage("처리 실패되었습니다.");
			result.setThrowable(e);
			request.setAttribute("result", result);
		}
		return mapping.findForward("result");
	}

	public void saveRole(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ResponseForm result = (ResponseForm) form;

		HashMap map = new HashMap();

		try {
			String roleNum = request.getParameter("roleNum");
			String roleName = request.getParameter("roleName");
			String roleUseInfo = request.getParameter("roleUseInfo");
			String roleSaveMode = request.getParameter("roleSaveMode");
			String roleSeq = request.getParameter("roleSeq");

			String[] chkRole = request.getParameterValues("chkRole");

			if (roleSaveMode.equals("DELETE")) {
				for (int i = 0; i < chkRole.length; i++) {
					map.put("sRole" + i, chkRole[i]);
					authorityManager.nodeDeleteAll(chkRole[i]);
					authorityManager.roleRemove(chkRole[i]);
				}
			} else if (roleSaveMode.equals("INSERT")) {

				RoleData roleData = new RoleData();

				roleData.setRoleNum(roleNum);
				roleData.setRoleName(roleName);
				roleData.setRoleSeq(Integer.parseInt(roleSeq));
				roleData.setRoleUseInfo(roleUseInfo);

				roleData.setRoleNum(IdGenerator.generate(IdGenerator.PREFIX_ROLE));
				authorityManager.roleCreate(roleData);

			} else if (roleSaveMode.equals("UPDATE")) {

				RoleData roleData = new RoleData();

				roleData.setRoleNum(roleNum);
				roleData.setRoleName(roleName);
				roleData.setRoleSeq(Integer.parseInt(roleSeq));
				roleData.setRoleUseInfo(roleUseInfo);

				authorityManager.roleStore(roleData);

			} else if (roleSaveMode.equals("COPY")) {
				for (int i = 0; i < chkRole.length; i++) {
					RoleData roleData = null;
					List roleList = authorityManager.roleRetrieve(chkRole[i], null, null);
					if (roleList != null) {
						if (roleList.size() > 0) {
							roleData = (RoleData) roleList.get(0);
							String newRoleNum = IdGenerator.generate(IdGenerator.PREFIX_ROLE);
							String orgRoleNum = roleData.getRoleNum();
							roleData.setRoleName(roleData.getRoleName() + "_사본");
							roleData.setRoleNum(newRoleNum);
							authorityManager.roleCreate(roleData);
							authorityManager.nodeCopy(newRoleNum, orgRoleNum);
						}
					}
				}
			}

			JSONWriter writer = new JSONWriter();
			map.put("result", true);
			map.put("resultMsg", "처리 되었습니다.");
			AjaxUtil.successWrite(response, map);

		} catch (ExpertPoolException e) {

			logger.info(e.getMessage(), e);
			map.put("result", false);
			map.put("resultMsg", "처리 중 오류가 발생했습니다.");
			map.put("ErrMsg", e);
			AjaxUtil.failWrite(response, map);
		}

	}

	public ActionForward setStoreRole(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ResponseForm result = (ResponseForm) form;
		try {
			String[] roleNum = request.getParameterValues("roleNum");
			String[] roleName = request.getParameterValues("roleName");
			// String[] roleXml = request.getParameterValues("roleXml");
			String[] roleUseInfo = request.getParameterValues("roleUseInfo");
			String[] roleSaveMode = request.getParameterValues("roleSaveMode");

			for (int i = 0; i < roleName.length; i++) {
				RoleData roleData = new RoleData();
				roleData.setRoleNum(roleNum[i]);
				roleData.setRoleName(roleName[i]);
				// roleData.setRoleXml(roleXml[i]);
				roleData.setRoleUseInfo(roleUseInfo[i]);

				if ("del".equals(roleSaveMode[i])) {
					authorityManager.roleRemove(roleNum[i]);
				} else if ("modi".equals(roleSaveMode[i])) {
					authorityManager.roleStore(roleData);
				} else if ("new".equals(roleSaveMode[i])) {
					roleData.setRoleNum(IdGenerator.generate(IdGenerator.PREFIX_ROLE));
					authorityManager.roleCreate(roleData);
				}
			}
			result.setMessage("처리 되었습니다.");
			result.setSuccess(true);
			request.setAttribute("result", result);

		} catch (Throwable e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			result.setSuccess(false);
			result.setMessage("처리 실패되었습니다.");
			result.setThrowable(e);
			request.setAttribute("result", result);
		}
		return mapping.findForward("result");
	}

	public ActionForward setCopyRole(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ResponseForm result = (ResponseForm) form;
		try {
			String[] roleNum = request.getParameterValues("roleNum");

			for (int i = 0; i < roleNum.length; i++) {
				RoleData roleData = null;
				List roleList = authorityManager.roleRetrieve(roleNum[i], null, null);
				if (roleList != null) {
					if (roleList.size() > 0) {
						roleData = (RoleData) roleList.get(0);
					}
				}
				roleData.setRoleName(roleData.getRoleName() + "_사본");
				roleData.setRoleNum(IdGenerator.generate(IdGenerator.PREFIX_ROLE));
				authorityManager.roleCreate(roleData);

			}
			result.setMessage("처리 되었습니다.");
			result.setSuccess(true);
			request.setAttribute("result", result);

		} catch (Throwable e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			result.setSuccess(false);
			result.setMessage("처리 실패되었습니다.");
			result.setThrowable(e);
			request.setAttribute("result", result);
		}
		return mapping.findForward("result");
	}

	public void treeDataRetrieve(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap map = new HashMap();

		try {
			String roleNum = request.getParameter("roleNum");
			String pNodeKey = "ROOT";
			int depth = 0;

			List children = authorityManager.roleDetailRetrieve(depth, roleNum, pNodeKey);

			JSONWriter writer = new JSONWriter();
			map.put("result", true);
			map.put("resultMsg", "처리 되었습니다.");
			map.put("treeMap", children);
			AjaxUtil.successWrite(response, map);

		} catch (ExpertPoolException e) {

			logger.info(e.getMessage(), e);
			map.put("result", false);
			map.put("resultMsg", "처리 중 오류가 발생했습니다.");
			map.put("ErrMsg", e);
			AjaxUtil.failWrite(response, map);
		}

	}
	
	public ActionForward mainPage(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
			String ssn = SessionUtils.getUsername(request);
			SimpleDateFormat format1 = new SimpleDateFormat ("yyyy-MM-dd");
			Date time = new Date();
			String resultTime = format1.format(time);
			String resultTime2 = "";
			
			if (!resultTime.equals("")) {
				resultTime2 = StringUtil.replace(resultTime, "-", "");
			}
			
		try {
			ExpertPool expertPool = this.getExpertPoolManager().retrieve(SessionUtils.getUsername(request));

			List children = authorityManager.roleDetailRetrieve(0, expertPool.getRole(), "ROOT", ssn);
			// System.out.println("자식 : " + Integer.toString(children.size()));
			request.setAttribute("menuList", children);
			
			/* 재택근무 기능 추가*/
			String workDayOn = this.getExpertPoolManager().selectWorkDayOn(ssn, resultTime);
			String workDayOff = this.getExpertPoolManager().selectWorkDayOff(ssn, resultTime);
			
			int workDayOnCnt = this.getExpertPoolManager().selectWorkDayOn(ssn, resultTime).length();
			int workDayOffCnt = this.getExpertPoolManager().selectWorkDayOff(ssn, resultTime).length();
			int workDayCnt = this.getExpertPoolManager().selectWorkDayCnt(ssn, resultTime2).length();
			
			/* 스케줄  */
			Calendar c = new GregorianCalendar();

			String nYear = Integer.toString(c.get(Calendar.YEAR));
			String nMonth = "0" + Integer.toString(c.get(Calendar.MONTH) + 1);
			nMonth = nMonth.substring(nMonth.length() - 2, nMonth.length());
			String nDay = "0" + Integer.toString(c.get(Calendar.DAY_OF_MONTH));
			nDay = nDay.substring(nDay.length() - 2, nDay.length());
			
			List<Object> todayScheduleList = new ArrayList<Object>();
			todayScheduleList.addAll(scheduleManager.getProjectScheduleListByMonth(ssn, nYear, nMonth, nDay));
			todayScheduleList.addAll(scheduleManager.getScheduleListByMonth(ssn, nYear, nMonth, nDay, "%%"));

			c.add(Calendar.DATE, 1);
			nYear = Integer.toString(c.get(Calendar.YEAR));
			nMonth = "0" + Integer.toString(c.get(Calendar.MONTH) + 1);
			nMonth = nMonth.substring(nMonth.length() - 2, nMonth.length());
			nDay = "0" + Integer.toString(c.get(Calendar.DAY_OF_MONTH));
			nDay = nDay.substring(nDay.length() - 2, nDay.length());
			List<Object> tomorrowScheduleList = new ArrayList<Object>();
			tomorrowScheduleList.addAll(scheduleManager.getProjectScheduleListByMonth(ssn, nYear, nMonth, nDay));
			tomorrowScheduleList.addAll(scheduleManager.getScheduleListByMonth(ssn, nYear, nMonth, nDay, "%%"));
			
			// mail
			URL url = new URL("https://webmail.kmac.co.kr/a_biz/api/new_mail_count.nvd?user_account="
					+ this.getExpertPoolManager().retrieve(SessionUtils.getUsername(request)).getEmail());
			URLConnection conn = url.openConnection();
			InputStream is = conn.getInputStream();
			BufferedReader ir = new BufferedReader(new InputStreamReader(is, "utf-8"));
			String read = null;
			int count = 0;
			while ((read = ir.readLine()) != null) {
				count = count + Integer.valueOf(read);
			}
			if(count > 99)
				request.setAttribute("mailCount", "99+");
			else
				request.setAttribute("mailCount", count);
			
			// work
			WebApplicationContext wc;
			wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh = (ValueListHandler) wc.getBean("workCabinet", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();
			Map<String, String> filters = new HashMap<String, String>();
			filters.put(ValueListInfo.PAGING_PAGE, "1");
			filters.put(ValueListInfo.PAGING_NUMBER_PER, String.valueOf(Integer.MAX_VALUE - 1));
			filters.put("assigneeId", SessionUtils.getUsername(request));
			info.setFilters(filters);
			ValueList valueList = vlh.getValueList("myIngWorkListCount", info);
			
			if (valueList.getList().size() > 99) {
				request.setAttribute("workCount", "99+");
			} else {
				request.setAttribute("workCount", valueList.getList().size());
			}

			// menu My
			List<MenuMy> menuMyList = this.getMenuMyManager().findMenuMyList(ssn);
			
			request.setAttribute("workDayOn", workDayOn);
			request.setAttribute("workDayOnChk", workDayOn);
			request.setAttribute("workDayOff", workDayOff);
			request.setAttribute("workDayOffChk", workDayOff);
			
			request.setAttribute("workDayOnCnt", workDayOnCnt);
			request.setAttribute("workDayOffCnt", workDayOffCnt);
			request.setAttribute("workDayCnt", workDayCnt);
			request.setAttribute("todayScheduleList", todayScheduleList);
			request.setAttribute("tomorrowScheduleList", tomorrowScheduleList);
			request.setAttribute("todayScheduleListSize", todayScheduleList.size());
			request.setAttribute("tomorrowScheduleListSize", tomorrowScheduleList.size());
			
			request.setAttribute("menuMyList", menuMyList);
		} catch (ExpertPoolException e) {
			logger.info(e.getMessage(), e);
		}
	
		return mapping.findForward("main");
	}
	
	public ActionForward mobileMainPage(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	
			ExpertPool expertPool = this.getExpertPoolManager().retrieve(SessionUtils.getUsername(request));
			String ssn = SessionUtils.getUsername(request);
			
		try {
			
			//공지사항, 열린세상
			List<Gadget> myNormalGadgetList = null;
			if("A".equals(expertPool.getJobClass()) || "B".equals(expertPool.getJobClass())){
				myNormalGadgetList = this.getGadgetManager().getMyMobileGadgetListByRole("mobileA");
			}else if("J".equals(expertPool.getJobClass())){
				myNormalGadgetList = this.getGadgetManager().getMyMobileGadgetListByRole("mobileJ");
			}else {
				myNormalGadgetList = this.getGadgetManager().getMyMobileGadgetListByRole("mobileN");
			}
			
			for (int i = 0; i < myNormalGadgetList.size(); i++) {
				List<Content> contentList = this.getGadgetManager().getContentList(myNormalGadgetList.get(i).getSqlText());
				myNormalGadgetList.get(i).setContentList(contentList);
			}
			
			/* 스케줄  */
			Calendar c = new GregorianCalendar();

			String nYear = Integer.toString(c.get(Calendar.YEAR));
			String nMonth = "0" + Integer.toString(c.get(Calendar.MONTH) + 1);
			nMonth = nMonth.substring(nMonth.length() - 2, nMonth.length());
			String nDay = "0" + Integer.toString(c.get(Calendar.DAY_OF_MONTH));
			nDay = nDay.substring(nDay.length() - 2, nDay.length());
			
			List<Object> todayScheduleList = new ArrayList<Object>();
			todayScheduleList.addAll(scheduleManager.getProjectScheduleListByMonth(ssn, nYear, nMonth, nDay));
			todayScheduleList.addAll(scheduleManager.getScheduleListByMonth(ssn, nYear, nMonth, nDay, "%%"));

			c.add(Calendar.DATE, 1);
			nYear = Integer.toString(c.get(Calendar.YEAR));
			nMonth = "0" + Integer.toString(c.get(Calendar.MONTH) + 1);
			nMonth = nMonth.substring(nMonth.length() - 2, nMonth.length());
			nDay = "0" + Integer.toString(c.get(Calendar.DAY_OF_MONTH));
			nDay = nDay.substring(nDay.length() - 2, nDay.length());
			List<Object> tomorrowScheduleList = new ArrayList<Object>();
			tomorrowScheduleList.addAll(scheduleManager.getProjectScheduleListByMonth(ssn, nYear, nMonth, nDay));
			tomorrowScheduleList.addAll(scheduleManager.getScheduleListByMonth(ssn, nYear, nMonth, nDay, "%%"));
			
			request.setAttribute("myNormalGadgetList", myNormalGadgetList);
			request.setAttribute("todayScheduleList", todayScheduleList);
			request.setAttribute("tomorrowScheduleList", tomorrowScheduleList);
			request.setAttribute("todayScheduleListSize", todayScheduleList.size());
			request.setAttribute("tomorrowScheduleListSize", tomorrowScheduleList.size());
		} catch (ExpertPoolException e) {
			logger.info(e.getMessage(), e);
		}
	
		return mapping.findForward("mobileIndex");
	}
	
	public ActionForward userChkPage(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
			String jumin = ServletRequestUtils.getStringParameter(request, "jumin");
			
			ExpertPool expertPool = this.getExpertPoolManager().retrieve(SessionUtils.getUsername(request));
			String userUid = expertPool.getUid().substring(6, 13);
			
			request.setAttribute("uid", userUid);
			return mapping.findForward("userChk");
	}

	/**
	 * @deprecated 
	 */	
	public ActionForward topMenuRetrieve(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		try {
			ExpertPool expertPool = this.getExpertPoolManager().retrieve(SessionUtils.getUsername(request));

			String[] remoteIp = request.getRemoteAddr().split("[.]");
			// System.out.println("IP : " + Integer.toString(remoteIp.length));
			/*
			 * for (int i = 0; i < remoteIp.length; i++) { System.out.println(Integer.toString(i) + ":" + expertPool.getSsn()); }
			 */
			String roleNum = "";
			if (((remoteIp[0].equals("192")) && (remoteIp[1].equals("168"))) || ((remoteIp[0].equals("127")) && (remoteIp[1].equals("0"))))
				roleNum = expertPool.getRole();
			else
				roleNum = expertPool.getExtRole();

			// System.out.println("roleNum : " + roleNum);
			String pNodeKey = "ROOT";
			int depth = 0;

			List children = authorityManager.roleDetailRetrieve(depth, roleNum, pNodeKey);
			// System.out.println("자식 : " + Integer.toString(children.size()));
			request.setAttribute("menuList", children);

		} catch (ExpertPoolException e) {
			logger.info(e.getMessage(), e);
		}
		return mapping.findForward("topPage");
	}

	/**
	 * @deprecated
	 */
	public ActionForward leftMenuRetrieve(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		try {
			ExpertPool expertPool = this.getExpertPoolManager().retrieve(SessionUtils.getUsername(request));

			// System.out.println("Id:" + SessionUtils.getUserObjext().getHtmlName());
			// System.out.println("Id:" + SessionUtils.getUsername(request));

			request.setAttribute("userId", expertPool.getUserId());

		} catch (ExpertPoolException e) {
			logger.info(e.getMessage(), e);
		}
		return mapping.findForward("leftPage");
	}

	public void saveTree(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ResponseForm result = (ResponseForm) form;

		HashMap map = new HashMap();

		try {
			String roleNum = request.getParameter("roleNum");
			String pNodeKey = request.getParameter("pNodeKey");
			String treeSaveMode = request.getParameter("treeSaveMode");

			String[] chkMenu = request.getParameterValues("chkMenu");
			String[] chkTree = request.getParameter("chkNodes").split(",");

			if (treeSaveMode.equals("CREATE")) {
				for (int i = 0; i < chkMenu.length; i++) {
					authorityManager.nodeCreate(roleNum, pNodeKey, chkMenu[i]);
				}
			} else if (treeSaveMode.equals("REMOVE")) {
				for (int i = 0; i < chkTree.length; i++) {
					authorityManager.nodeDelete(roleNum, chkTree[i]);
				}
			} else if (treeSaveMode.equals("UPDATE")) {

			}

			JSONWriter writer = new JSONWriter();
			map.put("result", true);
			map.put("resultMsg", "처리 되었습니다.");
			AjaxUtil.successWrite(response, map);

		} catch (ExpertPoolException e) {

			logger.info(e.getMessage(), e);
			map.put("result", false);
			map.put("resultMsg", "처리 중 오류가 발생했습니다.");
			map.put("ErrMsg", e);
			AjaxUtil.failWrite(response, map);
		}

	}

	/*************************************
	 * 기능 사용자지정 화면
	 *************************************/
	public ActionForward userSet(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		String roleNum = request.getParameter("roleNum");
		String indField = request.getParameter("indField");
		String keyWordCon = request.getParameter("keyWordCon");
		String keyWord = request.getParameter("keyWord");

		if ((indField != null) || (keyWordCon != null)) {
			List tobeExperts = expertPoolManager.getExpertsBySearch(indField, keyWordCon, keyWord);
			request.setAttribute("tobeExperts", tobeExperts);

		}
		List roleInfo = authorityManager.roleRetrieve(roleNum, "", "");

		List asisExperts = expertPoolManager.getExpertListByRole(roleNum, "%%");

		request.setAttribute("asisExperts", asisExperts);
		request.setAttribute("roleInfo", roleInfo);
		request.setAttribute("roleNum", roleNum);

		request.setAttribute("frmSearchIndField", indField);
		request.setAttribute("frmSearchKeyWordCon", keyWordCon);
		request.setAttribute("frmSearchKeyWord", keyWord);

		return mapping.findForward("roleUserSet");
	}

	public void Assign_Role(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		HashMap map = new HashMap();

		try {
			String roleNum = request.getParameter("roleNum");
			String[] chkExpert = request.getParameterValues("chkExpert");
			for (int i = 0; i < chkExpert.length; i++) {
				ExpertPool expertPool = expertPoolManager.retrieve(chkExpert[i]);
				expertPool.setRole(roleNum);
				expertPoolManager.store(expertPool);
			}
			JSONWriter writer = new JSONWriter();
			map.put("result", true);
			map.put("resultMsg", "처리 되었습니다.");
			AjaxUtil.successWrite(response, map);

		} catch (ExpertPoolException e) {

			logger.info(e.getMessage(), e);
			map.put("result", false);
			map.put("resultMsg", "처리 중 오류가 발생했습니다.");
			map.put("ErrMsg", e);
			AjaxUtil.failWrite(response, map);
		}
	}

	/*************************************
	 * 기능 외부사용자지정 화면
	 *************************************/
	public ActionForward extUserSet(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String roleNum = request.getParameter("roleNum");
		String indField = request.getParameter("indField");
		String keyWordCon = request.getParameter("keyWordCon");
		String keyWord = request.getParameter("keyWord");

		if ((indField != null) || (keyWordCon != null)) {
			List tobeExperts = expertPoolManager.getExpertsBySearch(indField, keyWordCon, keyWord);
			request.setAttribute("tobeExperts", tobeExperts);
		}
		List roleInfo = authorityManager.roleRetrieve(roleNum, "", "");

		List asisExperts = expertPoolManager.getExpertListByRole("%%", roleNum);

		request.setAttribute("asisExperts", asisExperts);
		request.setAttribute("roleInfo", roleInfo);
		request.setAttribute("roleNum", roleNum);

		request.setAttribute("frmSearchIndField", indField);
		request.setAttribute("frmSearchKeyWordCon", keyWordCon);
		request.setAttribute("frmSearchKeyWord", keyWord);

		return mapping.findForward("roleExtUserSet");
	}

	public void Assign_ExtRole(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		HashMap map = new HashMap();

		try {
			String roleNum = request.getParameter("roleNum");
			String[] chkExpert = request.getParameterValues("chkExpert");
			for (int i = 0; i < chkExpert.length; i++) {
				ExpertPool expertPool = expertPoolManager.retrieve(chkExpert[i]);
				expertPool.setExtRole(roleNum);
				expertPoolManager.store(expertPool);
			}
			JSONWriter writer = new JSONWriter();
			map.put("result", true);
			map.put("resultMsg", "처리 되었습니다.");
			AjaxUtil.successWrite(response, map);

		} catch (ExpertPoolException e) {

			logger.info(e.getMessage(), e);
			map.put("result", false);
			map.put("resultMsg", "처리 중 오류가 발생했습니다.");
			map.put("ErrMsg", e);
			AjaxUtil.failWrite(response, map);
		}
	}
	
	public void workOn(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap map = new HashMap();

		String work = ServletRequestUtils.getRequiredStringParameter(request, "workOn");
		String ssn = SessionUtils.getUsername(request);
		String ip = request.getRemoteAddr();
		
		try {
			expertPoolManager.workTime(ssn, work, ip);
		} catch (ExpertPoolException e) {
			logger.info(e.getMessage(), e);
			map.put("result", false);
			map.put("resultMsg", "처리 중 오류가 발생했습니다.");
			map.put("ErrMsg", e);
			AjaxUtil.failWrite(response, map);
		}
	}
	
	public void workOff(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap map = new HashMap();

		String work = ServletRequestUtils.getRequiredStringParameter(request, "workOff");
		String ssn = SessionUtils.getUsername(request);
		String ip = request.getRemoteAddr();
		
		try {
			expertPoolManager.workTime(ssn, work, ip);
		} catch (ExpertPoolException e) {
			logger.info(e.getMessage(), e);
			map.put("result", false);
			map.put("resultMsg", "처리 중 오류가 발생했습니다.");
			map.put("ErrMsg", e);
			AjaxUtil.failWrite(response, map);
		}
	}
}
