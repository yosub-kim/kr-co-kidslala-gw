package kr.co.kmac.pms.project.statistics.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.kmac.pms.common.util.DateTime;
import kr.co.kmac.pms.common.util.SessionUtils;
import kr.co.kmac.pms.common.util.StringUtil;
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
 * @struts.action name="projectEmergencyAction" path="/action/ProjectEmergencyAction" parameter="mode" scope="request"
 * @struts.action-forward name="projectEmergencyOnCost" path="/project/statistics/projectEmergencyOnCost.jsp" redirect="false"
 * @struts.action-forward name="projectEmergencyOnCIO" path="/project/statistics/projectEmergencyOnCIO.jsp" redirect="false"
 * @struts.action-forward name="projectEmergencyOnCfo" path="/project/statistics/projectEmergencyOnCfo.jsp" redirect="false"
 * @struts.action-forward name="projectEmergencyOnBiz" path="/project/statistics/projectEmergencyOnBiz.jsp" redirect="false"
 * @struts.action-forward name="projectEmergencyGadgetTable" path="/project/statistics/projectEmergencyGadgetTable.jsp" redirect="false"
 * @struts.action-forward name="projectEmergencyGadgetTableForGeneralManager" path="/project/statistics/projectEmergencyGadgetTableForGeneralManager.jsp"
 *                        redirect="false"
 * @struts.action-forward name="projectEmergencyGadgetTableForBoardMember" path="/project/statistics/projectEmergencyGadgetTableForBoardMember.jsp"
 *                        redirect="false"
 * @struts.action-forward name="projectEmergencyGadgetChart" path="/project/statistics/projectEmergencyGadgetChartXml.jsp" redirect="false"
 * @struts.action-forward name="projectEmergencyGadgetChartForBoardMember" path="/project/statistics/projectEmergencyGadgetChartXmlForBoardMember.jsp"
 *                        redirect="false"
 * @struts.action-forward name="projectEmergencyGadgetChartForGeneralManager" path="/project/statistics/projectEmergencyGadgetChartXmlForGeneralManager.jsp"
 *                        redirect="false"
 * @struts.action-forward name="emergencyGadgetChart" path="/project/statistics/projectEmergencyGadgetChart.jsp" redirect="false"
 */
public class ProjectEmergencyAction extends DispatchActionSupport {

	private static final Log logger = LogFactory.getLog(ProjectEmergencyAction.class);
	private ExpertPoolManager expertPoolManager;

	public ActionForward getEmergencyGadgetChart(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		try {
			String ssn = SessionUtils.getUsername(request);
			ExpertPool expertPool = this.getExpertPoolManager().retrieve(ssn);

			try {
				if (Integer.parseInt(expertPool.getCompanyPosition().substring(0, 2)) < 10 || expertPool.getDept().startsWith("938") || expertPool.getDept().startsWith("9362")) {
					request.setAttribute("filename", "gadgetColumnChart.swf");
					return mapping.findForward("emergencyGadgetChart");
				} else {
					request.setAttribute("filename", "gadgetPieChart.swf");
					return mapping.findForward("emergencyGadgetChart");
				}
			} catch (Exception e) {
				request.setAttribute("filename", "gadgetPieChart.swf");
				return mapping.findForward("emergencyGadgetChart");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	public ActionForward emergencyGadgetChart(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		WebApplicationContext wc;
		try {
			String userId = SessionUtils.getUsername(request);
			String ssn = SessionUtils.getUsername(request);
			ExpertPool expertPool = this.getExpertPoolManager().retrieve(ssn);
			
			wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			
			ValueListHandler vlh1 = (ValueListHandler) wc.getBean("industryTree", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();
			
			Map<String, String> filters = new HashMap<String, String>();
			filters.put("userId", userId);
			filters.put("dept", expertPool.getDept());
			info.setFilters(filters);
			
			ValueList vl = null;

			try {
				if (expertPool.getCompanyPosition().equals("08CF") || expertPool.getCompanyPosition().equals("09CJ")) {
					// 본부장
					vl = vlh1.getValueList("emergencyGadgetForGeneralManager", info);
					request.setAttribute("list", vl);
					request.setAttribute("dept", expertPool.getDept());
					return mapping.findForward("projectEmergencyGadgetChartForGeneralManager");
				} else if (Integer.parseInt(expertPool.getCompanyPosition().substring(0, 2)) < 8 || expertPool.getDept().startsWith("938") || expertPool.getDept().startsWith("9362")) {
					// 임원 및 관리자
					vl = vlh1.getValueList("emergencyGadgetForBoardMember", info);
					request.setAttribute("list", vl);
					return mapping.findForward("projectEmergencyGadgetChartForBoardMember");
				} else {
					// 기타(팀장, 팀원)
					request.setAttribute("filename", "gadgetPieChart.swf");
					vl = vlh1.getValueList("emergencyGadgetForMember", info);
					request.setAttribute("list", vl);
					return mapping.findForward("projectEmergencyGadgetChart");
				}
			} catch (Exception e) {
				vl = vlh1.getValueList("emergencyGadgetForMember", info);
				request.setAttribute("list", vl);
				return mapping.findForward("projectEmergencyGadgetChart");
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	public ActionForward emergencyGadgetTable(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		WebApplicationContext wc;
		try {
			String userId = SessionUtils.getUsername(request);
			String ssn = SessionUtils.getUsername(request);
			ExpertPool expertPool = this.getExpertPoolManager().retrieve(ssn);
			
			wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			
			ValueListHandler vlh1 = (ValueListHandler) wc.getBean("industryTree", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();
			
			Map<String, String> filters = new HashMap<String, String>();
			filters.put("userId", userId);
			filters.put("dept", expertPool.getDept());
			info.setFilters(filters);
			
			ValueList vl = null;

			try {
				if (expertPool.getCompanyPosition().equals("08CF") || expertPool.getCompanyPosition().equals("09CJ")) {
					// 본부장
					vl = vlh1.getValueList("emergencyGadgetForGeneralManager", info);
					request.setAttribute("list", vl);
					request.setAttribute("dept", expertPool.getDept());
					return mapping.findForward("projectEmergencyGadgetTableForGeneralManager");
				} else if (Integer.parseInt(expertPool.getCompanyPosition().substring(0, 2)) < 8 || expertPool.getDept().startsWith("938") || expertPool.getDept().startsWith("9362")) {
					vl = vlh1.getValueList("emergencyGadgetForBoardMember", info);
					request.setAttribute("list", vl);
					return mapping.findForward("projectEmergencyGadgetTableForBoardMember");
				} else {
					vl = vlh1.getValueList("emergencyGadgetForMember", info);
					request.setAttribute("list", vl);
					return mapping.findForward("projectEmergencyGadgetTable");
				}
			} catch (Exception e) {
				vl = vlh1.getValueList("emergencyGadgetForMember", info);
				request.setAttribute("list", vl);
				return mapping.findForward("projectEmergencyGadgetTable");
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	public ActionForward cboData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		WebApplicationContext wc;
		try {
			List<String> dataInfo = setDate(request);
			wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh1 = (ValueListHandler) wc.getBean("industryTree", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();

			Map<String, String> filters = new HashMap<String, String>();
			filters.put("realStartDate", dataInfo.get(0));
			filters.put("realEndDate", dataInfo.get(1));
			filters.put("currDate", StringUtil.getCurr("yyyyMMdd"));
			info.setFilters(filters);

			ValueList vl = vlh1.getValueList("select_CboGraph", info);
			request.setAttribute("list", vl);

			request.setAttribute("startYear", dataInfo.get(2));
			request.setAttribute("startMonth", dataInfo.get(3));
			request.setAttribute("endYear", dataInfo.get(4));
			request.setAttribute("endMonth", dataInfo.get(5));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return mapping.findForward("projectEmergencyOnBiz");
	}

	public ActionForward cfoData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		WebApplicationContext wc;
		try {
			List<String> dataInfo = setDate(request);
			wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh1 = (ValueListHandler) wc.getBean("industryTree", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();

			Map<String, String> filters = new HashMap<String, String>();
			filters.put("realStartDate", dataInfo.get(0));
			filters.put("realEndDate", dataInfo.get(1));
			filters.put("currDate", StringUtil.getCurr("yyyyMMdd"));
			info.setFilters(filters);

			ValueList vl = vlh1.getValueList("select_CfoGraph", info);
			request.setAttribute("list", vl);

			request.setAttribute("startYear", dataInfo.get(2));
			request.setAttribute("startMonth", dataInfo.get(3));
			request.setAttribute("endYear", dataInfo.get(4));
			request.setAttribute("endMonth", dataInfo.get(5));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return mapping.findForward("projectEmergencyOnCfo");
	}
	
	public ActionForward cfoData2(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		WebApplicationContext wc;
		
		String dt = DateTime.getDateString();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Calendar c = Calendar.getInstance();
		c.setTime(sdf.parse(dt));
		c.add(Calendar.DATE,1);
		dt = sdf.format(c.getTime());
	
		String startDate = ServletRequestUtils.getStringParameter(request, "startDate", dt);
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
			List<String> dataInfo = setDate(request);
			wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh1 = (ValueListHandler) wc.getBean("industryTree", ValueListHandler.class);
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
			info.setFilters(filters);

			ValueList vl = vlh1.getValueList("select_CfoGraph2", info);
			request.setAttribute("list", vl);

			request.setAttribute("startDate", startDate);
			request.setAttribute("endDate", endDate);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return mapping.findForward("projectEmergencyOnCfo2");
	}

	public ActionForward cioData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		WebApplicationContext wc;
		try {
			List<String> dataInfo = setDate(request);
			wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh1 = (ValueListHandler) wc.getBean("industryTree", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();

			Map<String, String> filters = new HashMap<String, String>();
			filters.put("realStartDate", dataInfo.get(0));
			filters.put("realEndDate", dataInfo.get(1));
			filters.put("currDate", StringUtil.getCurr("yyyyMMdd"));
			info.setFilters(filters);

			ValueList vl = vlh1.getValueList("select_CioGraph", info);
			request.setAttribute("list", vl);

			request.setAttribute("startYear", dataInfo.get(2));
			request.setAttribute("startMonth", dataInfo.get(3));
			request.setAttribute("endYear", dataInfo.get(4));
			request.setAttribute("endMonth", dataInfo.get(5));

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return mapping.findForward("projectEmergencyOnCIO");
	}

	public ActionForward costOverData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		WebApplicationContext wc;
		try {
			List<String> dataInfo = setDate(request);
			wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh1 = (ValueListHandler) wc.getBean("industryTree", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();

			Map<String, String> filters = new HashMap<String, String>();
			filters.put("realStartDate", dataInfo.get(0));
			filters.put("realEndDate", dataInfo.get(1));
			filters.put("currDate", StringUtil.getCurr("yyyyMMdd"));
			info.setFilters(filters);

			ValueList vl = vlh1.getValueList("select_costOverGraph", info);
			request.setAttribute("list", vl);

			request.setAttribute("startYear", dataInfo.get(2));
			request.setAttribute("startMonth", dataInfo.get(3));
			request.setAttribute("endYear", dataInfo.get(4));
			request.setAttribute("endMonth", dataInfo.get(5));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return mapping.findForward("projectEmergencyOnCost");
	}

	private List<String> setDate(HttpServletRequest request) throws Exception {
		List<String> result = new ArrayList<String>();
		String startYear = ServletRequestUtils.getStringParameter(request, "startYear", DateTime.getYear());
		int startMonth = ServletRequestUtils.getIntParameter(request, "startMonth", 1);
		String endYear = ServletRequestUtils.getStringParameter(request, "endYear", DateTime.getYear());
		int endMonth = ServletRequestUtils.getIntParameter(request, "endMonth", 12);

		String strStartMohth = null;
		String strEndMohth = null;

		if (startMonth < 10) {
			strStartMohth = "0" + startMonth;
		} else {
			strStartMohth = String.valueOf(startMonth);
		}
		if (endMonth < 10) {
			strEndMohth = "0" + endMonth;
		} else {
			strEndMohth = String.valueOf(endMonth);
		}

		int endDay = StringUtil.getMonthlyDayCount(Integer.parseInt(endYear), endMonth);
		result.add(0, startYear + "" + strStartMohth + "" + "01");
		result.add(1, endYear + "" + strEndMohth + "" + endDay);
		result.add(2, startYear);
		result.add(3, startMonth + "");
		result.add(4, endYear);
		result.add(5, endMonth + "");
		return result;
	}

	public ExpertPoolManager getExpertPoolManager() {
		return expertPoolManager;
	}

	public void setExpertPoolManager(ExpertPoolManager expertPoolManager) {
		this.expertPoolManager = expertPoolManager;
	}

}
