package kr.co.kmac.pms.system.gadget.action;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.kmac.pms.common.ajax.AjaxUtil;
import kr.co.kmac.pms.common.util.SessionUtils;
import kr.co.kmac.pms.common.util.StringUtil;
import kr.co.kmac.pms.companySchedule.manager.CompanyScheduleManager;
import kr.co.kmac.pms.expertpool.exception.ExpertPoolException;
import kr.co.kmac.pms.expertpool.manager.ExpertPoolManager;
import kr.co.kmac.pms.schedule.manager.ScheduleManager;
import kr.co.kmac.pms.system.gadget.data.Content;
import kr.co.kmac.pms.system.gadget.data.Gadget;
import kr.co.kmac.pms.system.gadget.data.MyGadget;
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
 * @struts.action name="responseForm" path="/action/GadgetAction" parameter="mode" scope="request"
 * @struts.action-forward name="gadgetListPage" path="/system/gadget/gadgetList.jsp" redirect="false"
 * @struts.action-forward name="gadgetEditPage" path="/system/gadget/gadgetEdit.jsp" redirect="false"
 * @struts.action-forward name="myGadgetPage" path="/system/gadget/myGadget.jsp" redirect="false"
 * @struts.action-forward name="mainPage" path="/right.jsp" redirect="false"
 * @struts.action-forward name="configMyGadget" path="/system/gadget/myGadget.jsp" redirect="false"
 */

public class GadgetAction extends DispatchActionSupport {
	private static final Log logger = LogFactory.getLog(GadgetAction.class);
	private GadgetManager gadgetManager;
	private ScheduleManager scheduleManager;
	private CompanyScheduleManager companyScheduleManager;
	private ExpertPoolManager expertPoolManager;
	
	public GadgetManager getGadgetManager() {
		return gadgetManager;
	}

	public void setGadgetManager(GadgetManager gadgetManager) {
		this.gadgetManager = gadgetManager;
	}

	public ScheduleManager getScheduleManager() {
		return scheduleManager;
	}

	public void setScheduleManager(ScheduleManager scheduleManager) {
		this.scheduleManager = scheduleManager;
	}

	public CompanyScheduleManager getCompanyScheduleManager() {
		return companyScheduleManager;
	}

	public void setCompanyScheduleManager(CompanyScheduleManager companyScheduleManager) {
		this.companyScheduleManager = companyScheduleManager;
	}
	public ExpertPoolManager getExpertPoolManager() {
		return expertPoolManager;
	}

	public void setExpertPoolManager(ExpertPoolManager expertPoolManager) {
		this.expertPoolManager = expertPoolManager;

	}

	// 가젯 리스트 페이지
	public ActionForward gadgetList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String pageTitle = "Gadget 리스트";
		String gadgetId = StringUtil.isNull(request.getParameter("gadgetId"), "");
		String role = ServletRequestUtils.getStringParameter(request, "role", "");
		String keyword = ServletRequestUtils.getStringParameter(request, "keyword", "");
		
		if (gadgetId.equals("")) gadgetId = "%%";
		if (role.equals("")) role = "%%";
		if (keyword.equals("")) { 
			keyword = "%%";
		} else {
			keyword = "%" + keyword + "%";
		}

		List<Gadget> gadgetList = this.getGadgetManager().getGadgetList(gadgetId, role, keyword);

		request.setAttribute("role", request.getParameter("role"));
		request.setAttribute("keyword", request.getParameter("keyword"));
		request.setAttribute("pageTitle", pageTitle);
		request.setAttribute("gadgetList", gadgetList);

		return mapping.findForward("gadgetListPage");
	}

	// 가젯 Form 페이지
	public ActionForward gadgetForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String gadgetId = StringUtil.isNull(request.getParameter("gadgetId"), "");
		String pageTitle = "Gadget 입력";
		String saveMode = "INSERT";
		Gadget gadget = new Gadget();
		if (!gadgetId.equals("")) {
			List<Gadget> gadgetList = this.getGadgetManager().getGadgetList(gadgetId);
			gadget = gadgetList.get(0);
			pageTitle = "Gadget 수정";
			saveMode = "UPDATE";
		}

		request.setAttribute("pageTitle", pageTitle);
		request.setAttribute("gadget", gadget);
		request.setAttribute("saveMode", saveMode);
		request.setAttribute("role", request.getParameter("role"));
		request.setAttribute("keyword", request.getParameter("keyword"));

		return mapping.findForward("gadgetEditPage");
	}

	// My가젯 설정 페이지
	public ActionForward myGadgetConfig(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String pageTitle = "My Gadget 설정";

		String ssn = SessionUtils.getUsername(request);

		List<Gadget> gadgetList = this.getGadgetManager().getNotSelectedGadgetList(ssn);
		List<Gadget> myGadgetList = this.getGadgetManager().getMyGadgetList(ssn, "");
		
		request.setAttribute("pageTitle", pageTitle);
		request.setAttribute("gadgetList", gadgetList);
		request.setAttribute("myGadgetList", myGadgetList);

		return mapping.findForward("configMyGadget");
	}

	// 메인 페이지
	public ActionForward mainPage(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String role = expertPoolManager.getMenuRole(SessionUtils.getUsername(request));
		
		//schedule
		String ssn = SessionUtils.getUsername(request);
		Calendar c = new GregorianCalendar();

		String nYear = Integer.toString(c.get(Calendar.YEAR));
		String nMonth = "0" + Integer.toString(c.get(Calendar.MONTH) + 1);
		nMonth = nMonth.substring(nMonth.length() - 2, nMonth.length());
		String nDay = "0" + Integer.toString(c.get(Calendar.DAY_OF_MONTH));
		nDay = nDay.substring(nDay.length() - 2, nDay.length());
		
		List<Object> todayScheduleList = new ArrayList<Object>();
		todayScheduleList.addAll(scheduleManager.getProjectScheduleListByMonth(ssn, nYear, nMonth, nDay));
		todayScheduleList.addAll(scheduleManager.getScheduleListByMonth(ssn, nYear, nMonth, nDay, "%%"));
		
		if (!role.equals("ROLE2006080118352520784"))	// 엑스퍼트는 회사일정 제외
			todayScheduleList.addAll(companyScheduleManager.scheduleListOfMonth(nYear, nMonth, nDay));

		c.add(Calendar.DATE, 1);
		nYear = Integer.toString(c.get(Calendar.YEAR));
		nMonth = "0" + Integer.toString(c.get(Calendar.MONTH) + 1);
		nMonth = nMonth.substring(nMonth.length() - 2, nMonth.length());
		nDay = "0" + Integer.toString(c.get(Calendar.DAY_OF_MONTH));
		nDay = nDay.substring(nDay.length() - 2, nDay.length());
		List<Object> tomorrowScheduleList = new ArrayList<Object>();
		tomorrowScheduleList.addAll(scheduleManager.getProjectScheduleListByMonth(ssn, nYear, nMonth, nDay));
		tomorrowScheduleList.addAll(scheduleManager.getScheduleListByMonth(ssn, nYear, nMonth, nDay, "%%"));
		
		if (!role.equals("ROLE2006080118352520784"))	// 엑스퍼트는 회사일정 제외
			tomorrowScheduleList.addAll(companyScheduleManager.scheduleListOfMonth(nYear, nMonth, nDay));
		
		
		//center gadget list
		//List<Gadget> myWideGadgetList = this.getGadgetManager().getMyGadgetList(SessionUtils.getUsername(request), "B");
		List<Gadget> myWideGadgetList = this.getGadgetManager().getMyGadgetListByRole(role, "B");
		//right gadget list
		//List<Gadget> myNormalGadgetList = this.getGadgetManager().getMyGadgetList(SessionUtils.getUsername(request), "A");
		List<Gadget> myNormalGadgetList = this.getGadgetManager().getMyGadgetListByRole(role, "A");
		

		for (int i = 0; i < myWideGadgetList.size(); i++) {
			List<Content> contentList = this.getGadgetManager().getContentList(myWideGadgetList.get(i).getSqlText());
			myWideGadgetList.get(i).setContentList(contentList);
		}
		for (int i = 0; i < myNormalGadgetList.size(); i++) {
			List<Content> contentList = this.getGadgetManager().getContentList(myNormalGadgetList.get(i).getSqlText());
			myNormalGadgetList.get(i).setContentList(contentList);
		}
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
		
		
		//주간진척관리
		WebApplicationContext wc2;
		wc2 = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
		ValueListHandler vlh2 = (ValueListHandler) wc2.getBean("workCabinet", ValueListHandler.class);
		ValueListInfo info2 = new ValueListInfo();
		Map<String, String> filters2 = new HashMap<String, String>();
		filters2.put(ValueListInfo.PAGING_PAGE, "1");
		filters2.put(ValueListInfo.PAGING_NUMBER_PER, String.valueOf(Integer.MAX_VALUE - 1));
		filters2.put("assigneeId", SessionUtils.getUsername(request));
		info2.setFilters(filters2);

		ValueList valueList2 = vlh2.getValueList("weeklyReportWorkListCount", info2);
		if (valueList2.getList().size() > 99) {
			request.setAttribute("wreportCount", "99+");
		} else {
			request.setAttribute("wreportCount", String.valueOf(valueList2.getList().size()));
		}
		
		//프로젝트 레포트
		WebApplicationContext wc3;
		wc3 = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
		ValueListHandler vlh3 = (ValueListHandler) wc3.getBean("workCabinet", ValueListHandler.class);
		ValueListInfo info3 = new ValueListInfo();
		Map<String, String> filters3 = new HashMap<String, String>();
		filters3.put(ValueListInfo.PAGING_PAGE, "1");
		filters3.put(ValueListInfo.PAGING_NUMBER_PER, String.valueOf(Integer.MAX_VALUE - 1));
		filters3.put("assigneeId", SessionUtils.getUsername(request));
		info3.setFilters(filters3);

		ValueList valueList3 = vlh3.getValueList("monthlyReportWorkListCount", info3);
		if (valueList3.getList().size() > 99) {
			request.setAttribute("mreportCount", "99+");
		} else {
			request.setAttribute("mreportCount", String.valueOf(valueList3.getList().size()));
		}
		
		request.setAttribute("menuRole", role);
		request.setAttribute("todayScheduleList", todayScheduleList);
		request.setAttribute("tomorrowScheduleList", tomorrowScheduleList);
		request.setAttribute("myWideGadgetList", myWideGadgetList);
		request.setAttribute("myNormalGadgetList", myNormalGadgetList);

		return mapping.findForward("mainPage");
	}
	
	// 메인 페이지(외부사용자)
	public ActionForward externalMainPage(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		List<Gadget> myGadgetList2 = this.getGadgetManager().getMyWideGadgetList(SessionUtils.getUsername(request));
		for (int i = 0; i < myGadgetList2.size(); i++) {
			List<Content> contentList = this.getGadgetManager().getContentList(myGadgetList2.get(i).getSqlText());
			myGadgetList2.get(i).setContentList(contentList);
		}
		
		request.setAttribute("myGadgetList2", myGadgetList2);

		return mapping.findForward("mainPage");
	}	

	// 가젯 저장(INSERT,UPDATE)
	@SuppressWarnings("unchecked")
	public void saveGadget(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		HashMap map = new HashMap();
		try {
			String saveMode		= ServletRequestUtils.getRequiredStringParameter(request, "saveMode");
			String gadgetId		= ServletRequestUtils.getStringParameter(request, "gadgetId", "");
			String gadgetName	= ServletRequestUtils.getStringParameter(request, "gadgetName");
			String linkUrl		= ServletRequestUtils.getStringParameter(request, "linkUrl");
			String fixedYN		= ServletRequestUtils.getStringParameter(request, "fixedYN");
			String useYN		= ServletRequestUtils.getStringParameter(request, "useYN");
			String ordSeq		= ServletRequestUtils.getStringParameter(request, "ordSeq" , "10");
			String gadgetType	= ServletRequestUtils.getStringParameter(request, "gadgetType", "A");
			String sqlText		= ServletRequestUtils.getStringParameter(request, "sqlText");
			String tableHeader	= ServletRequestUtils.getStringParameter(request, "tableHeader");
			String gadgetRole	= ServletRequestUtils.getStringParameter(request, "gadgetRole");
			
			Gadget gadget = new Gadget();
			gadget.setGadgetName(gadgetName);
			gadget.setLinkUrl(linkUrl);
			gadget.setFixedYN(fixedYN);
			gadget.setUseYN(useYN);
			gadget.setOrdSeq(Integer.parseInt(ordSeq));
			gadget.setGadgetType(gadgetType);
			gadget.setSqlText(sqlText);
			gadget.setGadgetRole(gadgetRole);
			gadget.setTableHeader(tableHeader);

			if (saveMode.equals("INSERT")) {
				this.getGadgetManager().createGadget(gadget);
			} else if (saveMode.equals("UPDATE")) {
				gadget.setGadgetId(gadgetId);
				this.getGadgetManager().updateGadget(gadget);
			} else if (saveMode.equals("DELETE")) {
				gadget.setGadgetId(gadgetId);				
				this.getGadgetManager().deleteGadget(gadget);
			}

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

	@SuppressWarnings("unchecked")
	public void configMyGadget(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		HashMap map = new HashMap();
		try {
			String saveMode = ServletRequestUtils.getRequiredStringParameter(request, "saveMode");
			String[] gadgetId = ServletRequestUtils.getRequiredStringParameters(request, "gadgetId");

			String userId = SessionUtils.getUsername(request);
			for (int i = 0; i < gadgetId.length; i++){
				MyGadget myGadget = new MyGadget();
	
				myGadget.setGadgetId(gadgetId[i]);
				myGadget.setUserId(userId);
				
				if (saveMode.equals("INSERT")) {
					myGadget.setOrdSeq(this.getGadgetManager().getMaxOrdSeq(userId)+1);
					
					this.getGadgetManager().appendMyGadget(myGadget);
				} else if (saveMode.equals("DELETE")) {
					this.getGadgetManager().deleteMyGadget(myGadget);
				}
			}
			
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
	
	@SuppressWarnings("unchecked")
	public void ordSeqChange(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		HashMap map = new HashMap();
		try {
			String saveMode = ServletRequestUtils.getRequiredStringParameter(request, "saveMode");
			String[] gadgetId = ServletRequestUtils.getRequiredStringParameters(request, "gadgetId");
			int      ordSeq   = ServletRequestUtils.getIntParameter(request, "ordSeq",50);

			String userId = SessionUtils.getUsername(request);
			for (int i = 0; i < gadgetId.length; i++){
				MyGadget myGadget = new MyGadget();
	
				myGadget.setGadgetId(gadgetId[i]);
				myGadget.setUserId(userId);
				myGadget.setOrdSeq(ordSeq);
	
				if (saveMode.equals("INSERT")) {
					this.getGadgetManager().appendMyGadget(myGadget);
				} else if (saveMode.equals("UPDATE")) {
					this.getGadgetManager().updateMyGadget(myGadget);
				} else if (saveMode.equals("DELETE")) {
					this.getGadgetManager().deleteMyGadget(myGadget);
				}
			}
			
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
}
