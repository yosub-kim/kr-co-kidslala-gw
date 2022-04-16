package kr.co.kmac.pms.sanction.statistics.action;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.kmac.pms.common.ajax.AjaxUtil;
import kr.co.kmac.pms.common.util.DateTime;
import kr.co.kmac.pms.common.util.SessionUtils;
import kr.co.kmac.pms.common.util.StringUtil;
import kr.co.kmac.pms.common.worklist.manager.WorklistManager;
import kr.co.kmac.pms.sanction.general.manager.SanctionDocManager;
import kr.co.kmac.pms.system.sanction.manager.SanctionTemplateManager;
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
 * @struts.action name="sanctiontPresentStateAction" path="/action/SanctiontPresentStateAction" parameter="mode" scope="request"
 * 
 * @struts.action-forward name="getSanctionCountOnProject" path="/sanction/statistics/sanctionCountOnProject.jsp" redirect="false"
 * @struts.action-forward name="getAllSanctiontPresentState" path="/sanction/statistics/sanctiontPresentState.jsp" redirect="false"
 * @struts.action-forward name="getSpecificSanctiontPresentState" path="/sanction/statistics/specificSanctiontPresentState.jsp" redirect="false"
 * @struts.action-forward name="getAllSanctiontPresentStateForMobile" path="/m/web/sanction/sanctionStateList.jsp" redirect="false"
 */
public class SanctiontPresentStateAction extends DispatchActionSupport {
	private static final Log logger = LogFactory.getLog(SanctiontPresentStateAction.class);
	private SanctionTemplateManager sanctionTemplateManager; 
	private SanctionDocManager sanctionDocManager;
	private WorklistManager worklistManager;

	// http://localhost:8080/action/SanctiontPresentStateAction.do?mode=getSanctionCountOnProject
	public ActionForward getSanctionCountOnProject(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String pageSize = ServletRequestUtils.getStringParameter(request, "pageSize", "1000");
		String pageNo = ServletRequestUtils.getStringParameter(request, "pageNo", "1");		
		String approvalType = ServletRequestUtils.getStringParameter(request, "approvalType", "");
		String searchYear = ServletRequestUtils.getStringParameter(request, "searchYear", DateTime.getYear());
		String divCode = ServletRequestUtils.getStringParameter(request, "divCode", "");

		try {
			WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh = (ValueListHandler) wc.getBean("workCabinet", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();
			Map<String, String> filters = new HashMap<String, String>();
			
			filters.put(ValueListInfo.PAGING_PAGE, pageNo);
			filters.put(ValueListInfo.PAGING_NUMBER_PER, pageSize);
			
			if (!approvalType.equals("")) {
				filters.put("approvalType", approvalType);
			}
			if (!searchYear.equals("")) {
				filters.put("searchYear", searchYear);
			}
			if (!divCode.equals("")) {
				filters.put("divCode", divCode);
			}
			info.setFilters(filters);
			ValueList valueList = vlh.getValueList("getSanctionCountOnProject", info);
			
			request.setAttribute("list", valueList);
			request.setAttribute("approvalType", approvalType);
			request.setAttribute("searchYear", searchYear);
			request.setAttribute("divCode", divCode);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return mapping.findForward("getSanctionCountOnProject");
	}

	public ActionForward getAllSanctiontPresentState(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String pageNo = ServletRequestUtils.getStringParameter(request, "pageNo", "1");
		String pageSize = ServletRequestUtils.getStringParameter(request, "pageSize", "10");
		String approvalType = ServletRequestUtils.getStringParameter(request, "approvalType", "");
		String ing = ServletRequestUtils.getStringParameter(request, "ing", "");
		String divCode = ServletRequestUtils.getStringParameter(request, "divCode", "");
		String prevDivCode = ServletRequestUtils.getStringParameter(request, "prevDivCode", "");
		String prevDivCode2 = ServletRequestUtils.getStringParameter(request, "prevDivCode2", "");
		String prevDivCode3 = ServletRequestUtils.getStringParameter(request, "prevDivCode3", "");
		/*String searchField = ServletRequestUtils.getStringParameter(request, "searchField", "");*/
		/*String keyword = ServletRequestUtils.getStringParameter(request, "keyword", "");*/
		String isPrevDiv = ServletRequestUtils.getStringParameter(request, "isPrevDiv", "N");
		String startDate = ServletRequestUtils.getStringParameter(request, "startDate", "");
		String endDate = ServletRequestUtils.getStringParameter(request, "endDate", DateTime.getDateString());
		String projectName = ServletRequestUtils.getStringParameter(request, "projectName", "");
		String name = ServletRequestUtils.getStringParameter(request, "name", "");
		WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
		
		Map<String, String> filters = new HashMap<String, String>();
		filters.put("userId", SessionUtils.getUsername(request));
		filters.put("userIdDraft3", SessionUtils.getUsername(request));
		filters.put("userIdDraftS", SessionUtils.getUsername(request));
		
		if (!ing.equals("")) {filters.put("ing", ing);}
		
		if (isPrevDiv.equals("N")){
			if (!divCode.equals("")) {filters.put("divCode", divCode);}
		}else{
			if(isPrevDiv.equals("A")){
				if (!prevDivCode2.equals("")) {filters.put("prevDivCode", prevDivCode2);}
			}else if(isPrevDiv.equals("B")){
				if (!prevDivCode3.equals("")) {filters.put("prevDivCode", prevDivCode3);}
			}else{
				if (!prevDivCode.equals("")) {filters.put("prevDivCode", prevDivCode);}
			}
		}
		
		/*if (!keyword.equals("")) {filters.put(searchField, "%" + keyword + "%");}*/
		if (!name.equals("")) {filters.put("name", "%" + name + "%");}
		if (!projectName.equals("")) {filters.put("projectName", "%" + projectName + "%");}
		if (!approvalType.equals("")) {
			if (approvalType.startsWith("draft") && approvalType.length() == 6)
				filters.put("approvalType", approvalType);
			else
				filters.put("approvalType", approvalType + "%");
		}
		if (SessionUtils.getUsername(request).equals("A000006")) {// 사장님이 볼 경우 전사항은 제외
			filters.put("isWholeApproval", "Y");
		}
		if (startDate.equals("")) {
			
			// 기본 시작일자를 현재 기준 1년 전
			int year = Integer.parseInt(DateTime.getDateString().substring(0,4));
			int month = Integer.parseInt(DateTime.getDateString().substring(4,6));
			int date = Integer.parseInt(DateTime.getDateString().substring(6,8));
			
			Calendar cal = Calendar.getInstance();
			cal.set(year,  month - 1, date);
			
			cal.add(Calendar.YEAR, -1);
			SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyyMMdd");
			int lastYear = Integer.parseInt(dateFormatter.format(cal.getTime()));
			startDate = Integer.toString(lastYear);
			// 기본 시작일자를 이전 년도 1월 1일로 변환
			//int lastYear = Integer.parseInt(DateTime.getYear()) - 1;
			//startDate = Integer.toString(lastYear) + "0101";
		}
		if (!startDate.equals("")) {
			startDate = StringUtil.replace(startDate, "-", "");
			if (startDate.length() < 8) {
				startDate = startDate + "01";
			}
			filters.put("startDate", startDate);
		}
		if (!endDate.equals("")) {
			endDate = StringUtil.replace(endDate, "-", "");
			if (endDate.length() < 8) {
				endDate = endDate + "31";
			}
			filters.put("endDate", endDate);
		}
		try {
			ValueListHandler vlh = (ValueListHandler) wc.getBean("workCabinet", ValueListHandler.class);
			
			ValueListInfo info1 = new ValueListInfo();
			Map<String, String> filters1 = new HashMap<String, String>();
			filters1.put("userId", SessionUtils.getUsername(request));
			info1.setFilters(filters1);
			// 전체 권한(인사정보 제외)
			if((vlh.getValueList("getSearchRule_GSM_BM_SA", info1).getList().size() > 0)){
				filters.remove("userId");
			}
			// 전체 권한(인사정보 포함)
			if((vlh.getValueList("getSearchRule_GSM_SA", info1).getList().size() > 0)){
				filters.remove("userIdDraft3");
			}
			// 급여 관리자 권한
			if(vlh.getValueList("getSearchRule_GSM_SALARY_SA", info1).getList().size() > 0){
				filters.remove("userIdDraftS");
			}


			ValueListInfo info = new ValueListInfo();
			filters.put(ValueListInfo.PAGING_PAGE, pageNo);
			filters.put(ValueListInfo.PAGING_NUMBER_PER, pageSize);
			info.setFilters(filters);
			ValueList valueList = vlh.getValueList("getSanctiontPresentState", info);

			request.setAttribute("approvalType", approvalType);
			request.setAttribute("ing", ing);
			request.setAttribute("divCode", divCode);
			request.setAttribute("prevDivCode", prevDivCode);
			request.setAttribute("prevDivCode2", prevDivCode2);
			request.setAttribute("prevDivCode3", prevDivCode3);
			/*request.setAttribute("searchField", searchField);*/
			request.setAttribute("projectName", projectName);
			request.setAttribute("name", name);
			request.setAttribute("list", valueList);
			request.setAttribute("isPrevDiv", isPrevDiv);
			request.setAttribute("startDate", startDate);
			request.setAttribute("endDate", endDate);
			request.setAttribute("sanctionTemplateList", this.getSanctionTemplateManager().getSanctionTemplate());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return mapping.findForward("getAllSanctiontPresentState");
	}
	
	public ActionForward getSpecificSanctiontPresentState(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String pageNo = ServletRequestUtils.getStringParameter(request, "pageNo", "1");
		String pageSize = ServletRequestUtils.getStringParameter(request, "pageSize", "15");
		String approvalType = ServletRequestUtils.getStringParameter(request, "approvalType", "");
		String ssn = ServletRequestUtils.getStringParameter(request, "ssn", "");
		String year = ServletRequestUtils.getStringParameter(request, "year", DateTime.getYear());
		String month = ServletRequestUtils.getStringParameter(request, "month", DateTime.getMonth());
		
		WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
		
		Map<String, String> filters = new HashMap<String, String>();
		
		if (!approvalType.equals("")) {filters.put("approvalType", approvalType);}
		if (!ssn.equals("")) {filters.put("ssn", ssn);}
		
		// 검색 기간 설정: 검색 월 1일 부터 검색 다음월 1일까지
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd"); 
		Calendar cal = Calendar.getInstance();
		
		String from = year + "-" + month + "-01"; 
		cal.setTime(Date.valueOf(from));
		cal.add(Calendar.MONTH, 1);
	    String to = df.format(cal.getTime());
				
		filters.put("from", from);
		filters.put("to", to);
		
		try {
			ValueListHandler vlh = (ValueListHandler) wc.getBean("workCabinet", ValueListHandler.class);
			
			ValueListInfo info = new ValueListInfo();
			filters.put(ValueListInfo.PAGING_PAGE, pageNo);
			filters.put(ValueListInfo.PAGING_NUMBER_PER, pageSize);
			info.setFilters(filters);
			ValueList valueList = vlh.getValueList("getSpecificSanctiontPresentState", info);
			
			request.setAttribute("approvalType", approvalType);
			request.setAttribute("list", valueList);
			request.setAttribute("sanctionTemplateList", this.getSanctionTemplateManager().getSanctionTemplate());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return mapping.findForward("getSpecificSanctiontPresentState");
	}
	
	public ActionForward getAllSanctiontPresentStateForMobile(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String pageNo = ServletRequestUtils.getStringParameter(request, "pageNo", "1");
		String pageSize = ServletRequestUtils.getStringParameter(request, "pageSize", "10");
		String approvalType = ServletRequestUtils.getStringParameter(request, "approvalType", "");
		String ing = ServletRequestUtils.getStringParameter(request, "ing", "");
		String divCode = ServletRequestUtils.getStringParameter(request, "divCode", "");
		String prevDivCode = ServletRequestUtils.getStringParameter(request, "prevDivCode", "");
		String prevDivCode2 = ServletRequestUtils.getStringParameter(request, "prevDivCode2", "");
		String prevDivCode3 = ServletRequestUtils.getStringParameter(request, "prevDivCode3", "");
		/*String searchField = ServletRequestUtils.getStringParameter(request, "searchField", "");*/
		/*String keyword = ServletRequestUtils.getStringParameter(request, "keyword", "");*/
		String isPrevDiv = ServletRequestUtils.getStringParameter(request, "isPrevDiv", "N");
		String startDate = ServletRequestUtils.getStringParameter(request, "startDate", "");
		String endDate = ServletRequestUtils.getStringParameter(request, "endDate", DateTime.getDateString());
		String projectName = ServletRequestUtils.getStringParameter(request, "projectName", "");
		String name = ServletRequestUtils.getStringParameter(request, "name", "");
		WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
		
		Map<String, String> filters = new HashMap<String, String>();
		filters.put("userId", SessionUtils.getUsername(request));
		filters.put("userIdDraft3", SessionUtils.getUsername(request));
		filters.put("userIdDraftS", SessionUtils.getUsername(request));
		
		if (!ing.equals("")) {filters.put("ing", ing);}
		
		if (isPrevDiv.equals("N")){
			if (!divCode.equals("")) {filters.put("divCode", divCode);}
		}else{
			if(isPrevDiv.equals("A")){
				if (!prevDivCode2.equals("")) {filters.put("prevDivCode", prevDivCode2);}
			}else if(isPrevDiv.equals("B")){
				if (!prevDivCode3.equals("")) {filters.put("prevDivCode", prevDivCode3);}
			}else{
				if (!prevDivCode.equals("")) {filters.put("prevDivCode", prevDivCode);}
			}
		}
		
		/*if (!keyword.equals("")) {filters.put(searchField, "%" + keyword + "%");}*/
		if (!name.equals("")) {filters.put("name", "%" + name + "%");}
		if (!projectName.equals("")) {filters.put("projectName", "%" + projectName + "%");}
		if (!approvalType.equals("")) {
			if (approvalType.startsWith("draft") && approvalType.length() == 6)
				filters.put("approvalType", approvalType);
			else
				filters.put("approvalType", approvalType + "%");
		}
		if (SessionUtils.getUsername(request).equals("A000006")) {// 사장님이 볼 경우 전사항은 제외
			filters.put("isWholeApproval", "Y");
		}
		if (startDate.equals("")) {
			
			// 기본 시작일자를 현재 기준 1년 전
			int year = Integer.parseInt(DateTime.getDateString().substring(0,4));
			int month = Integer.parseInt(DateTime.getDateString().substring(4,6));
			int date = Integer.parseInt(DateTime.getDateString().substring(6,8));
			
			Calendar cal = Calendar.getInstance();
			cal.set(year,  month - 1, date);
			
			cal.add(Calendar.YEAR, -1);
			SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyyMMdd");
			int lastYear = Integer.parseInt(dateFormatter.format(cal.getTime()));
			startDate = Integer.toString(lastYear);
			// 기본 시작일자를 이전 년도 1월 1일로 변환
			//int lastYear = Integer.parseInt(DateTime.getYear()) - 1;
			//startDate = Integer.toString(lastYear) + "0101";
		}
		if (!startDate.equals("")) {
			startDate = StringUtil.replace(startDate, "-", "");
			if (startDate.length() < 8) {
				startDate = startDate + "01";
			}
			filters.put("startDate", startDate);
		}
		if (!endDate.equals("")) {
			endDate = StringUtil.replace(endDate, "-", "");
			if (endDate.length() < 8) {
				endDate = endDate + "31";
			}
			filters.put("endDate", endDate);
		}
		try {
			ValueListHandler vlh = (ValueListHandler) wc.getBean("workCabinet", ValueListHandler.class);
			
			ValueListInfo info1 = new ValueListInfo();
			Map<String, String> filters1 = new HashMap<String, String>();
			filters1.put("userId", SessionUtils.getUsername(request));
			info1.setFilters(filters1);
			// 전체 권한(인사정보 제외)
			if((vlh.getValueList("getSearchRule_GSM_BM_SA", info1).getList().size() > 0)){
				filters.remove("userId");
			}
			// 전체 권한(인사정보 포함)
			if((vlh.getValueList("getSearchRule_GSM_SA", info1).getList().size() > 0)){
				filters.remove("userIdDraft3");
			}
			// 급여 관리자 권한
			if(vlh.getValueList("getSearchRule_GSM_SALARY_SA", info1).getList().size() > 0){
				filters.remove("userIdDraftS");
			}


			ValueListInfo info = new ValueListInfo();
			filters.put(ValueListInfo.PAGING_PAGE, pageNo);
			filters.put(ValueListInfo.PAGING_NUMBER_PER, pageSize);
			info.setFilters(filters);
			ValueList valueList = vlh.getValueList("getSanctiontPresentState", info);

			request.setAttribute("approvalType", approvalType);
			request.setAttribute("ing", ing);
			request.setAttribute("divCode", divCode);
			request.setAttribute("prevDivCode", prevDivCode);
			request.setAttribute("prevDivCode2", prevDivCode2);
			request.setAttribute("prevDivCode3", prevDivCode3);
			/*request.setAttribute("searchField", searchField);*/
			request.setAttribute("projectName", projectName);
			request.setAttribute("name", name);
			request.setAttribute("list", valueList);
			request.setAttribute("isPrevDiv", isPrevDiv);
			request.setAttribute("startDate", startDate);
			request.setAttribute("endDate", endDate);
			request.setAttribute("sanctionTemplateList", this.getSanctionTemplateManager().getSanctionTemplate());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return mapping.findForward("getAllSanctiontPresentStateForMobile");
	}
	
	/*public void getAllSanctiontPresentStateForMobile(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pg = ServletRequestUtils.getIntParameter(request, "pg", 1);
		String approvalType = ServletRequestUtils.getStringParameter(request, "approvalType", "");
		String ing = ServletRequestUtils.getStringParameter(request, "ing", "");
		String divCode = ServletRequestUtils.getStringParameter(request, "divCode", "");
		String searchField = ServletRequestUtils.getStringParameter(request, "searchField", "");
		String keyword = ServletRequestUtils.getStringParameter(request, "keyword", "");
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
		
		Map<String, String> filters = new HashMap<String, String>();
		filters.put("userId", SessionUtils.getUsername(request));
		filters.put("userIdDraft3", SessionUtils.getUsername(request));
		filters.put("userIdDraftS", SessionUtils.getUsername(request));
		
		if (!ing.equals("")) {filters.put("ing", ing);}
		if (!divCode.equals("")) {filters.put("divCode", divCode);}
		if (!keyword.equals("")) {filters.put(searchField, "%" + keyword + "%");}
		if (!approvalType.equals("")) {
			if (approvalType.startsWith("draft") && approvalType.length() == 6)
				filters.put("approvalType", approvalType);
			else
				filters.put("approvalType", approvalType + "%");
		}
		if (SessionUtils.getUsername(request).equals("A000006")) {// 사장님이 볼 경우 전사항은 제외
			filters.put("isWholeApproval", "Y");
		}
		
		try {
			ValueListHandler vlh = (ValueListHandler) wc.getBean("workCabinet", ValueListHandler.class);
			
			ValueListInfo info1 = new ValueListInfo();
			Map<String, String> filters1 = new HashMap<String, String>();
			filters1.put("userId", SessionUtils.getUsername(request));
			info1.setFilters(filters1);
			// 전체 권한(인사정보 제외)
			if(vlh.getValueList("getSearchRule_GSM_BM_SA", info1).getList().size() > 0){
				filters.remove("userId");
			}
			// 전체 권한(인사정보 포함)
			if(vlh.getValueList("getSearchRule_GSM_SA", info1).getList().size() > 0){
				filters.remove("userIdDraft3");
			}
			// 급여 관리자 권한
			if(vlh.getValueList("getSearchRule_GSM_SALARY_SA", info1).getList().size() > 0){
				filters.remove("userIdDraftS");
			}
			
			
			ValueListInfo info = new ValueListInfo();
			filters.put(ValueListInfo.PAGING_PAGE, String.valueOf(pg));
			filters.put(ValueListInfo.PAGING_NUMBER_PER, String.valueOf(10)); 
			info.setFilters(filters);
			ValueList valueList = vlh.getValueList("getSanctiontPresentState", info);
			
			map.put("pagingPage", String.valueOf(pg));
			map.put("pagingEntries",
					pg == valueList.getValueListInfo().getTotalNumberOfPages() ? String.valueOf(valueList.getValueListInfo()
							.getTotalNumberOfEntries()) : String.valueOf(pg * 10));
			map.put("totalNumberOfEntries", String.valueOf(valueList.getValueListInfo().getTotalNumberOfEntries()));
			map.put("totalNumberOfPages", String.valueOf(valueList.getValueListInfo().getTotalNumberOfPages()));			request.setAttribute("approvalType", approvalType);
			map.put("ing", ing);
			map.put("divCode", divCode);
			map.put("searchField", searchField);
			map.put("keyword", keyword);
			map.put("sanctionTemplateList", this.getSanctionTemplateManager().getSanctionTemplate(true, true));
			map.put("sanctionStateList", valueList.getList());

			AjaxUtil.successWrite(response, map);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			AjaxUtil.failWrite(response);
		}
	}*/

	public void getWorkType(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		String projectCode = ServletRequestUtils.getRequiredStringParameter(request, "projectCode");
		String approvalType = ServletRequestUtils.getRequiredStringParameter(request, "approvalType");
		String seq = ServletRequestUtils.getRequiredStringParameter(request, "seq");
		try {
			map.put("workType", this.getWorklistManager()
					.getWorkType(this.getWorklistManager().getWorkList(projectCode, approvalType, seq).getType()));
			AjaxUtil.successWrite(response, map);
		} catch (Exception e) {
			AjaxUtil.failWrite(response);
			logger.error(e.getMessage(), e);
		}
	}

	public SanctionTemplateManager getSanctionTemplateManager() {
		return sanctionTemplateManager;
	}

	public void setSanctionTemplateManager(SanctionTemplateManager sanctionTemplateManager) {
		this.sanctionTemplateManager = sanctionTemplateManager;
	}

	public SanctionDocManager getSanctionDocManager() {
		return sanctionDocManager;
	}

	public void setSanctionDocManager(SanctionDocManager sanctionDocManager) {
		this.sanctionDocManager = sanctionDocManager;
	}

	public WorklistManager getWorklistManager() {
		return worklistManager;
	}

	public void setWorklistManager(WorklistManager worklistManager) {
		this.worklistManager = worklistManager;
	}

}
