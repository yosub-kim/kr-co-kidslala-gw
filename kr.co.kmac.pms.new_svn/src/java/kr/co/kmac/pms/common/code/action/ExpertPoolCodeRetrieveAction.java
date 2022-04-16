/*
 * $Id: ExpertPoolCodeRetrieveAction.java,v 1.6 2012/09/07 09:08:27 cvs Exp $
 * created by    : jiwoongLee
 * creation-date : 2006. 3. 20.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.common.code.action;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.kmac.pms.common.ajax.AjaxUtil;
import kr.co.kmac.pms.common.ajax.JSONWriter;
import kr.co.kmac.pms.common.code.data.ExpertCategory;
import kr.co.kmac.pms.common.code.data.ExpertCategoryTreeNode;
import kr.co.kmac.pms.expertpool.data.ExpertPoolZipCode;
import kr.co.kmac.pms.expertpool.manager.ExpertPoolManager;
import net.mlw.vlh.ValueListHandler;
import net.mlw.vlh.ValueListInfo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.struts.DispatchActionSupport;

/**
 * 전문가 풀 전문분야 검색 트리
 * @author jiwoongLee
 * @version $Id: ExpertPoolCodeRetrieveAction.java,v 1.6 2012/09/07 09:08:27 cvs Exp $
 */
/**
 * @struts.action name="expertPoolCodeRetrieveAction" path="/action/ExpertPoolCodeRetrieveAction" parameter="mode" scope="request"
 */
public class ExpertPoolCodeRetrieveAction extends DispatchActionSupport {

	private static final Log logger = LogFactory.getLog(ExpertPoolCodeRetrieveAction.class);
	private ExpertPoolManager expertPoolManager;

	/**
	 * 전문가 풀의 인력 분류 트리
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void getExpertPoolTree(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
		List treeList = new ArrayList();

		try {
			ValueListHandler vlh = (ValueListHandler) wc.getBean("expoertPoolCategory", ValueListHandler.class);

			ValueListInfo info_div = new ValueListInfo("key_1", ValueListInfo.ASCENDING);
			Map<String, String> filters = new HashMap<String, String>();
			filters.put(ValueListInfo.PAGING_NUMBER_PER, String.valueOf(Integer.MAX_VALUE));
			info_div.setFilters(filters);

			ValueListInfo info_exp = new ValueListInfo("key_1", ValueListInfo.ASCENDING);
			Map<String, String> filters1 = new HashMap<String, String>();
			filters1.put(ValueListInfo.PAGING_NUMBER_PER, String.valueOf(Integer.MAX_VALUE));

			ValueListInfo info_exp_detail = new ValueListInfo("key_1", ValueListInfo.ASCENDING);
			Map<String, String> filters2 = new HashMap<String, String>();
			filters2.put(ValueListInfo.PAGING_NUMBER_PER, String.valueOf(Integer.MAX_VALUE));

			List<ExpertCategory> allList = vlh.getValueList("runningDivCodeForExpertPool", info_div).getList();// Update for ExpertPool Top Node Element
			for (ExpertCategory item0 : allList) {
				ExpertCategoryTreeNode expertPoolTreeNode = new ExpertCategoryTreeNode();
				expertPoolTreeNode.setId(((ExpertCategory) (item0)).getKey());
				expertPoolTreeNode.setText(((ExpertCategory) (item0)).getData());

				filters1.put("level1Key", ((ExpertCategory) (item0)).getKey());
				info_exp.setFilters(filters1);
				List<ExpertCategory> expList = vlh.getValueList("expertCategoryCode", info_exp).getList();
				for (ExpertCategory item1 : expList) {
					ExpertCategoryTreeNode _expertPoolTreeNode = new ExpertCategoryTreeNode();
					_expertPoolTreeNode.setId(((ExpertCategory) (item1)).getKey());
					_expertPoolTreeNode.setText(((ExpertCategory) (item1)).getData());

					filters2.put("level1Key", ((ExpertCategory) (item0)).getKey());
					filters2.put("level2Key", ((ExpertCategory) (item1)).getKey());
					info_exp_detail.setFilters(filters2);
					List<ExpertCategory> expDetailList = vlh.getValueList("expertCategoryDetailCode", info_exp_detail).getList();
					for (ExpertCategory item2 : expDetailList) {
						ExpertCategoryTreeNode __expertPoolTreeNode = new ExpertCategoryTreeNode();
						__expertPoolTreeNode.setId(((ExpertCategory) (item2)).getKey());
						__expertPoolTreeNode.setText(((ExpertCategory) (item2)).getData());
						__expertPoolTreeNode.setLeaf(true);
						_expertPoolTreeNode.getChildren().add(__expertPoolTreeNode);
					}
					if (_expertPoolTreeNode.getChildren().size() == 0)
						_expertPoolTreeNode.setLeaf(true);
					expertPoolTreeNode.getChildren().add(_expertPoolTreeNode);
				}
				if (expertPoolTreeNode.getChildren().size() == 0)
					expertPoolTreeNode.setLeaf(true);
				treeList.add(expertPoolTreeNode);
			}

			map.put("returnValue", treeList);
			AjaxUtil.successWrite(response, map);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			AjaxUtil.failWrite(response, map);
		}
	}

	public void getGroupCode(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		try {
			map.put("returnValue", expertPoolManager.findGroup());
			AjaxUtil.successWrite(response, map);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			AjaxUtil.failWrite(response, map);
		}
	}

	public void getBuGroupCode(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		try {
			map.put("returnValue", expertPoolManager.findBUGroup());
			AjaxUtil.successWrite(response, map);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			AjaxUtil.failWrite(response, map);
		}
	}

	public void getPuGroupCode(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		try {
			map.put("returnValue", expertPoolManager.findPUGroup());
			AjaxUtil.successWrite(response, map);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			AjaxUtil.failWrite(response, map);
		}
	}

	public void getRoleCode(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		try {
			map.put("returnValue", expertPoolManager.findRole());
			AjaxUtil.successWrite(response, map);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			AjaxUtil.failWrite(response, map);
		}
	}

	public void getBuRoleCode(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		try {
			map.put("returnValue", expertPoolManager.findBURole());
			AjaxUtil.successWrite(response, map);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			AjaxUtil.failWrite(response, map);
		}
	}

	public void getPuRoleCode(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		try {
			map.put("returnValue", expertPoolManager.findPURole());
			AjaxUtil.successWrite(response, map);
		} catch (Exception e) {
			logger.info(e);
			AjaxUtil.failWrite(response, map);
		}
	}

	// Job Date: 2010-07-05 Author: yhyim Description: get menu role list
	public void getMenuRoleCode(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		try {
			map.put("returnValue", expertPoolManager.findMenuRole());
			AjaxUtil.successWrite(response, map);
		} catch (Exception e) {
			logger.info(e);
			AjaxUtil.failWrite(response, map);
		}
	}

	public void getRoadZipCodeSidoList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
		try {
			String sido = URLDecoder.decode(ServletRequestUtils.getRequiredStringParameter(request, "sido"), "UTF-8");

			ValueListHandler vlh = (ValueListHandler) wc.getBean("expoertPoolZipcode", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();
			Map<String, String> filters = new HashMap<String, String>();
			filters.put("sido", sido);
			filters.put(ValueListInfo.PAGING_NUMBER_PER, String.valueOf(Integer.MAX_VALUE - 1));
			info.setFilters(filters);
			List<ExpertPoolZipCode> roadZipCodeSidoList = vlh.getValueList("getRoadZipCodeSidoList", info).getList();

			JSONWriter writer = new JSONWriter();
			map.put("returnValue", roadZipCodeSidoList);
			AjaxUtil.successWrite(response, map);
		} catch (Exception e) {
			logger.info(e.getMessage(), e);
			AjaxUtil.failWrite(response, map);
		}
	}

	public void getRoadZipCodeRoadList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
		try {
			String sido = URLDecoder.decode(ServletRequestUtils.getRequiredStringParameter(request, "sido"), "UTF-8");
			String gugun = URLDecoder.decode(ServletRequestUtils.getRequiredStringParameter(request, "gugun"), "UTF-8");
			String roadName = URLDecoder.decode(ServletRequestUtils.getRequiredStringParameter(request, "roadName"), "UTF-8");

			ValueListHandler vlh = (ValueListHandler) wc.getBean("expoertPoolZipcode", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();
			Map<String, String> filters = new HashMap<String, String>();
			filters.put("sido", sido);
			filters.put("gugun", gugun);
			filters.put("roadName", roadName+"%");
			filters.put(ValueListInfo.PAGING_NUMBER_PER, String.valueOf(Integer.MAX_VALUE - 1));
			info.setFilters(filters);
			List<ExpertPoolZipCode> roadZipCodeRoadList = vlh.getValueList("getRoadZipCodeRoadList", info).getList();

			JSONWriter writer = new JSONWriter();
			map.put("returnValue", roadZipCodeRoadList);
			AjaxUtil.successWrite(response, map);
		} catch (Exception e) {
			logger.info(e.getMessage(), e);
			AjaxUtil.failWrite(response, map);
		}
	}

	/**
	 * @return the expertPoolManager
	 */
	public ExpertPoolManager getExpertPoolManager() {
		return expertPoolManager;
	}

	/**
	 * @param expertPoolManager the expertPoolManager to set
	 */
	public void setExpertPoolManager(ExpertPoolManager expertPoolManager) {
		this.expertPoolManager = expertPoolManager;
	}

}
