/*
 * $Id: EmergencyConnectionAction.java,v 1.11 2014/11/20 15:44:46 cvs Exp $
 * created by    : 안성호
 * creation-date : 2006. 7. 7.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.emergencyConnection.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.kmac.pms.common.util.SessionUtils;
import kr.co.kmac.pms.emergencyConnection.data.EmergencyConnectionData;
import kr.co.kmac.pms.emergencyConnection.data.EmergencyGroup;
import kr.co.kmac.pms.expertpool.data.ExpertPool;
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
 * @struts.action name="emergencyConnectionAction" path="/action/EmergencyConnectionAction" parameter="mode" scope="request"
 * @struts.action-forward name="emergencyConnectionList"	path="/emergencyConnection/emergencyConnection.jsp" redirect="false"
 * @struts.action-forward name="emergencyConnectionDetail"	path="/emergencyConnection/emergencyDetail.jsp" redirect="false"
 * @struts.action-forward name="saveToExcel"				path="/emergencyConnection/saveToExcel.jsp"			redirect="false"
 */
public class EmergencyConnectionAction extends DispatchActionSupport {
	private static final Log logger = LogFactory.getLog(EmergencyConnectionAction.class);
	private ExpertPoolManager expertPoolManager;
		
	public ExpertPoolManager getExpertPoolManager() {
		return expertPoolManager;
	}

	public void setExpertPoolManager(ExpertPoolManager expertPoolManager) {
		this.expertPoolManager = expertPoolManager;
	}

	public ActionForward retrieveList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		WebApplicationContext wc;
		try {
			// 비상연락망.
			String jobClass = ServletRequestUtils.getRequiredStringParameter(request, "jobClass");

			int pageSize = 100000;
			String pg = "1";
			wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh1 = (ValueListHandler) wc.getBean("emergencyConnectionBean",
					ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();

			Map<String, String> filters = new HashMap<String, String>();
			filters.put(ValueListInfo.PAGING_PAGE, pg);
			filters.put(ValueListInfo.PAGING_NUMBER_PER, String.valueOf(pageSize == -1
					? Integer.MAX_VALUE - 1
					: pageSize));
			info.setFilters(filters);
			ValueList result = null;
			if ("A".equals(jobClass)) {
				result = vlh1.getValueList("selectType_A", info);
			} else if ("B".equals(jobClass)) {
				result = vlh1.getValueList("selectType_B", info);
			} else if ("J".equals(jobClass)) {
				result = vlh1.getValueList("selectType_C", info);
			} else if ("H".equals(jobClass)) {
				result = vlh1.getValueList("selectType_H", info);
			} else if ("H2".equals(jobClass)) {
				result = vlh1.getValueList("selectType_H2", info);
			} else if ("R".equals(jobClass)) {
					result = vlh1.getValueList("selectType_R", info);
				}

			//List m_list = getOrderByInfo(result.getList(), request);

			//request.setAttribute("m_list", m_list);
			request.setAttribute("result", result.getList());
			request.setAttribute("jobClass", jobClass);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return mapping.findForward("emergencyConnectionList");
	}
	
	public ActionForward retrieveListForMobile(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		WebApplicationContext wc;
		try {
			// 비상연락망.
			String jobClass = ServletRequestUtils.getStringParameter(request, "jobClass", "A");
			String name = ServletRequestUtils.getStringParameter(request, "name", "");
			System.out.println(name);
			int pageSize = 100000;
			String pg = "1";
			wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh1 = (ValueListHandler) wc.getBean("emergencyConnectionBean",
					ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();

			Map<String, String> filters = new HashMap<String, String>();
			
			if("".equals(name)){
				filters.put("name", "%%");
			}else{
				filters.put("name", "%"+name+"%");
			}
			
			filters.put(ValueListInfo.PAGING_PAGE, pg);
			filters.put(ValueListInfo.PAGING_NUMBER_PER, String.valueOf(pageSize == -1
					? Integer.MAX_VALUE - 1
					: pageSize));
			info.setFilters(filters);
			ValueList result = null;
			if ("A".equals(jobClass)) {
				result = vlh1.getValueList("selectType_A_Mobile", info);
			} else if ("J".equals(jobClass)) {
				result = vlh1.getValueList("selectType_J_Mobile", info);
			} else if ("N".equals(jobClass)) {
				result = vlh1.getValueList("selectType_N_Mobile", info);
			}

			request.setAttribute("result", result.getList());
			request.setAttribute("jobClass", jobClass);
			request.setAttribute("name", name);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return mapping.findForward("emergencyConnectionListForMobile");
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
	public ActionForward detailView(ActionMapping mapping, ActionForm form,	HttpServletRequest request, HttpServletResponse response) throws Exception {
		String ssn = ServletRequestUtils.getRequiredStringParameter(request, "ssn");
		WebApplicationContext wc;
		if ("".equals(ssn)) {
			ssn = SessionUtils.getUsername(request);
		}
		ExpertPool expertPool = expertPoolManager.retrieve(ssn);
		
		try {
			String juminNB = expertPool.getUid();
			juminNB = juminNB.substring(0,6) + "-" + "*******";
			request.setAttribute("juminNB", juminNB);
			request.setAttribute("expertPool", expertPool);	
			
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
		return mapping.findForward("emergencyConnectionDetail");
	}
	
	public ActionForward saveToExcel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		WebApplicationContext wc;
		try {
			// 비상연락망.
			String jobClass = ServletRequestUtils.getRequiredStringParameter(request, "jobClass");
			
			String subTitle = "";
			if(jobClass.equals("A"))
				subTitle = "KMAC";
			else if(jobClass.equals("J"))
				subTitle = "상임";
			else
				subTitle = "RA";
			
			String fileName = "비상연락망("+ subTitle + ").xls";
			response.setHeader("Content-Disposition", "attachment; filename=" + new String(fileName.getBytes("EUC-KR"), "8859_1") + ";"); 
		    response.setHeader("Content-Description", "JSP Generated Data");

			int pageSize = 100000;
			String pg = "1";
			wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh1 = (ValueListHandler) wc.getBean("emergencyConnectionBean",
					ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();

			Map<String, String> filters = new HashMap<String, String>();
			filters.put(ValueListInfo.PAGING_PAGE, pg);
			filters.put(ValueListInfo.PAGING_NUMBER_PER, String.valueOf(pageSize == -1
					? Integer.MAX_VALUE - 1
					: pageSize));
			info.setFilters(filters);
			ValueList result = null;
			if ("A".equals(jobClass)) {
				result = vlh1.getValueList("selectType_A", info);
			} else if ("J".equals(jobClass)) {
				result = vlh1.getValueList("selectType_C", info);
			} else {
				result = vlh1.getValueList("selectType_H2", info);
			}

			//List m_list = getOrderByInfo(result.getList(), request);

			//request.setAttribute("m_list", m_list);
			request.setAttribute("result", result);
			request.setAttribute("jobClass", jobClass);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return mapping.findForward("saveToExcel");
	}

	/**
	 * @deprecated
	 * @param userlist
	 * @return
	 * @throws Exception
	 */
	private List<EmergencyGroup> groupByDept(List<EmergencyConnectionData> userlist) throws Exception {	
		List<EmergencyGroup> groupList = new ArrayList<EmergencyGroup>();
		EmergencyGroup emergencyGroup = null;
		List<EmergencyConnectionData> groupUserList = new ArrayList<EmergencyConnectionData>();
		String tempGroupName = "";
		for(int i = 0; i < userlist.size(); i++) {
			EmergencyConnectionData emergencyConnectionData = userlist.get(i);			
			
			if(((i != 0)&&(!tempGroupName.equals(emergencyConnectionData.getDept())))
					||(i ==(userlist.size()-1) )) {
				
				if((i != 0)&&(!tempGroupName.equals(emergencyConnectionData.getDept()))) {
					emergencyGroup = new EmergencyGroup();
					emergencyGroup.setGroupName(tempGroupName);
					emergencyGroup.setGroupUserList(groupUserList);
					emergencyGroup.setUserCnt(groupUserList.size());
					groupList.add(emergencyGroup);
					
					groupUserList = new ArrayList<EmergencyConnectionData>();
				}
				
				if(i ==(userlist.size()-1)) {
					groupUserList.add(emergencyConnectionData);
					emergencyGroup = new EmergencyGroup();
					emergencyGroup.setGroupName(tempGroupName);
					emergencyGroup.setGroupUserList(groupUserList);
					emergencyGroup.setUserCnt(groupUserList.size());
					groupList.add(emergencyGroup);
					
					groupUserList = new ArrayList<EmergencyConnectionData>();
				}
			}
			groupUserList.add(emergencyConnectionData);
			tempGroupName = emergencyConnectionData.getDept();	
		}
		
		return groupList;
	}
}
