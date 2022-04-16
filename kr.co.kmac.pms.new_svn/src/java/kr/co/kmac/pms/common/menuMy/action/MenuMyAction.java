/*
 * $Id: CommonCodeRetrieveAction.java,v 1.1 2009/09/19 11:15:36 cvs3 Exp $
 * created by    : jiwoongLee
 * creation-date : 2006. 2. 23.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.common.menuMy.action;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.kmac.pms.common.ajax.AjaxUtil;
import kr.co.kmac.pms.common.menuMy.data.MenuMy;
import kr.co.kmac.pms.common.menuMy.manager.MenuMyManager;
import kr.co.kmac.pms.common.util.SessionUtils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.struts.DispatchActionSupport;

/**
 * 메뉴 즐겨찾기 Action Class
 * 
 * @author jiwoongLee
 * @version $Id: MenuMyAction.java,v 1.1 2009/09/19 11:15:36 cvs3 Exp $
 */
/**
 * @struts.action name="menuMyAction" path="/action/MenuMyAction" parameter="mode" scope="request"
 */
public class MenuMyAction extends DispatchActionSupport {

	private static final Log logger = LogFactory.getLog(MenuMyAction.class);
	private MenuMyManager menuMyManager;

	public MenuMyManager getMenuMyManager() {
		return menuMyManager;
	}

	public void setMenuMyManager(MenuMyManager menuMyManager) {
		this.menuMyManager = menuMyManager;
	}

	public void insertMenuMy(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			String menuNum = ServletRequestUtils.getRequiredStringParameter(request, "menuNum");
			int orderSeq = ServletRequestUtils.getIntParameter(request, "orderSeq", 1);
			MenuMy menuMy = new MenuMy(menuNum, SessionUtils.getUsername(request), orderSeq);

			this.getMenuMyManager().createMenuMy(menuMy);

			AjaxUtil.successWrite(response, map);
		} catch (Exception e) {
			AjaxUtil.failWrite(response);
			logger.error(e.getMessage(), e);
		}
	}

	public void updateMenuMy(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			String menuNum = ServletRequestUtils.getRequiredStringParameter(request, "menuNum");
			int orderSeq = ServletRequestUtils.getIntParameter(request, "orderSeq", 1);
			MenuMy menuMy = new MenuMy(menuNum, SessionUtils.getUsername(request), orderSeq);

			this.getMenuMyManager().updateMenuMy(menuMy);

			AjaxUtil.successWrite(response, map);
		} catch (Exception e) {
			AjaxUtil.failWrite(response);
			logger.error(e.getMessage(), e);
		}
	}

	public void deleteMenuMy(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			String menuNum = ServletRequestUtils.getRequiredStringParameter(request, "menuNum");
			this.getMenuMyManager().deleteMenuMy(menuNum, SessionUtils.getUsername(request));

			AjaxUtil.successWrite(response, map);
		} catch (Exception e) {
			AjaxUtil.failWrite(response);
			logger.error(e.getMessage(), e);
		}
	}

	public void getMenuMyList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		try {
			List<MenuMy> menuMyList = this.getMenuMyManager().findMenuMyList(SessionUtils.getUsername(request));

			map.put("returnValue", menuMyList);
			AjaxUtil.successWrite(response, map);
		} catch (Exception e) {
			logger.info(e.getMessage(), e);
			AjaxUtil.failWrite(response, map);
		}
	}

}
