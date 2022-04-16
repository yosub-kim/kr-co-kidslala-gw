package kr.co.kmac.pms.project.master.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.kmac.pms.common.ajax.AjaxUtil;
import kr.co.kmac.pms.common.code.data.CodeEntity;
import kr.co.kmac.pms.common.code.manager.CommonCodeManager;
import kr.co.kmac.pms.common.util.SessionUtils;
import kr.co.kmac.pms.common.util.StringUtil;
import kr.co.kmac.pms.common.worklist.data.Work;
import kr.co.kmac.pms.expertpool.data.ExpertPool;
import kr.co.kmac.pms.expertpool.manager.ExpertPoolManager;
import kr.co.kmac.pms.project.master.data.Project;
import kr.co.kmac.pms.project.master.data.ProjectDelayInfo;
import kr.co.kmac.pms.project.master.data.ProjectMember;
import kr.co.kmac.pms.project.master.manager.ProjectMasterInfoManager;
import kr.co.kmac.pms.sanction.general.manager.SanctionDocManager;
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
 * @struts.action name="projectSearchAction" path="/action/ProjectSearchAction" parameter="mode" scope="request"
 * @struts.action-forward name="projectSearchList" path="/project/master/projectSearchList.jsp" redirect="false"
 * @struts.action-forward name="delayProjectSearchList" path="/project/master/delayProjectSearchList.jsp" redirect="false"
 * @struts.action-forward name="projectCustomerRelationList" path="/project/master/projectCustomerRelationList.jsp" redirect="false"
 * @struts.action-forward name="projectOutputUploadList" path="/project/master/projectOutputUploadList.jsp" redirect="false"
 * @struts.action-forward name="projectAdminOpenList" path="/project/master/projectAdminOpenList.jsp" redirect="false"
 * @struts.action-forward name="projectInfoTab" path="/project/projectInfoTab.jsp" redirect="false"
 */
public class ProjectSearchAction extends DispatchActionSupport {

	private static final Log logger = LogFactory.getLog(ProjectSearchAction.class);
	private ProjectMasterInfoManager projectMasterInfoManager;
	private ExpertPoolManager expertPoolManager;
	private SanctionDocManager sanctionDocManager;
	private CommonCodeManager commonCodeManager;

	public ActionForward getProjectSearchList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String pageSize = ServletRequestUtils.getStringParameter(request, "pageSize", "10");
		String pageNo = ServletRequestUtils.getStringParameter(request, "pageNo", "1");
		//String projectName = ServletRequestUtils.getStringParameter(request, "projectName", "");
		//String projectCode = ServletRequestUtils.getStringParameter(request, "projectCode", "");
		String delayState = ServletRequestUtils.getStringParameter(request, "delayState", "");
		String delayStateAll = ServletRequestUtils.getStringParameter(request, "delayStateAll", ""); 
		String chartSsn = ServletRequestUtils.getStringParameter(request, "chartSsn", "");
		String ssn = SessionUtils.getUsername(request);
		String businessTypeCode = ServletRequestUtils.getStringParameter(request, "businessTypeCode", "");
		String projectTypeCode = ServletRequestUtils.getStringParameter(request, "projectTypeCode", "");
		String runningDivCode = ServletRequestUtils.getStringParameter(request, "runningDivCode", "");
		String runningDivCode1 = ServletRequestUtils.getStringParameter(request, "runningDivCode1", "");
		String runningDivCode2 = ServletRequestUtils.getStringParameter(request, "runningDivCode2", "");
		String runningDeptCode = ServletRequestUtils.getStringParameter(request, "runningDeptCode", "");
		String runningPUCode = ServletRequestUtils.getStringParameter(request, "runningPUCode", "");
		String industryTypeCode = ServletRequestUtils.getStringParameter(request, "industryTypeCode", "");
		String realStartDate =  ServletRequestUtils.getStringParameter(request, "realStartDate", "");
		String realEndDate =  ServletRequestUtils.getStringParameter(request, "realEndDate","");
		String pjtEndYear = ServletRequestUtils.getStringParameter(request, "pjtEndYear", "");
		String projectState = ServletRequestUtils.getStringParameter(request, "projectState", "3");
		String endGubun = ServletRequestUtils.getStringParameter(request, "endGubun", "");
		String costOver = ServletRequestUtils.getStringParameter(request, "costOver", "");
		String endProcess = ServletRequestUtils.getStringParameter(request, "endProcess", "");
		String etcCostOver = ServletRequestUtils.getStringParameter(request, "etcCostOver", "");
		String payCostOver = ServletRequestUtils.getStringParameter(request, "payCostOver", "");
		String keywordType = ServletRequestUtils.getStringParameter(request, "keywordType", "");
		String keyword = ServletRequestUtils.getStringParameter(request, "keyword", "");
		String isPrevDiv = ServletRequestUtils.getStringParameter(request, "isPrevDiv", "N");
		
		if (!realStartDate.equals("")) {
			realStartDate = StringUtil.replace(realStartDate, "-", "");
			if (realStartDate.length() < 8) {
				realStartDate = realStartDate;
			}
		}
		if (!realEndDate.equals("")) {
			realEndDate = StringUtil.replace(realEndDate, "-", "");
			if (realEndDate.length() < 8) {
				realEndDate = realEndDate;
			}
		}

		try {
			WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh = (ValueListHandler) wc.getBean("projectValueList", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();
			Map<String, String> filters = new HashMap<String, String>();
			filters.put(ValueListInfo.PAGING_PAGE, pageNo);
			filters.put(ValueListInfo.PAGING_NUMBER_PER, pageSize);
			//if (!projectName.equals("")) {				filters.put("projectName", "%" + projectName + "%");			}
			//if (!projectCode.equals("")) {				filters.put("projectCode", projectCode);			}
			if (keywordType.equals("PCODE")) {
				filters.put("projectCode", keyword);	
			} else if (keywordType.equals("CUST")) {
				filters.put("customerName", "%" + keyword + "%");	
			} else {
				filters.put("projectName", "%" + keyword + "%");	
			}
			if (!businessTypeCode.equals("")) {			filters.put("businessTypeCode", businessTypeCode);			}
			if (!projectTypeCode.equals("")) {			filters.put("projectTypeCode", projectTypeCode);			}
			//if (!ssn.equals("")) {					filters.put("ssn", ssn);}
			if (!etcCostOver.equals("")) {				filters.put("etcCostOver", etcCostOver);			}
			if (!payCostOver.equals("")) {				filters.put("payCostOver", payCostOver);			}
			if (!delayState.equals("")) {				filters.put("delayState", delayState);			}
			if (!delayStateAll.equals("")) {			filters.put("delayStateAll", delayStateAll);			}
			if (!endProcess.equals("")) {				filters.put("endProcess", endProcess);			}
			if (isPrevDiv.equals("N")) {
				if (!runningDeptCode.equals("")) {			filters.put("runningDeptCode", runningDeptCode);			}
			} else {
				if(isPrevDiv.equals("E")){
					if (!runningDivCode1.equals("")) {			filters.put("runningDeptCode", runningDivCode1);			}
				}else if(isPrevDiv.equals("F")){
					if (!runningDivCode2.equals("")) {			filters.put("runningDivCode", runningDivCode2);			}
				}
			}
			if (!runningDivCode.equals("")) {			filters.put("runningDivCode", runningDivCode);			}
			if (!runningPUCode.equals("")) {			filters.put("runningPUCode", runningPUCode);			}
			if (!industryTypeCode.equals("")) {			filters.put("industryTypeCode", industryTypeCode);			}
			if (!endGubun.equals("")) {					filters.put("endGubun", endGubun);			}
			if (!costOver.equals("")) {					filters.put("costOver", costOver);			}
			if (!chartSsn.equals("")) {					filters.put("chartSsn", chartSsn);			}
			if (!pjtEndYear.equals("")) {				filters.put("pjtEndYear", pjtEndYear+"%");	}
			if (!realStartDate.equals("")) {
				realStartDate = StringUtil.replace(realStartDate, "-", "");
				if (realStartDate.length() < 8) {
					realStartDate = realStartDate + "01";
				}
				filters.put("realStartDate", realStartDate);
			}
			if (!realEndDate.equals("")) {
				realEndDate = StringUtil.replace(realEndDate, "-", "");
				if (realEndDate.length() < 8) {
					realEndDate = realEndDate + "31";
				}
				filters.put("realEndDate", realEndDate);
			}
			if (!projectState.equals("")) {
				if (projectState.equals("EV"))
					filters.put("endProcess", projectState);
				else
					filters.put("projectState", projectState);
			}
			info.setFilters(filters);
			
			// 상근/상임 별 pjt check
			//ExpertPool expertPool_chk = this.getExpertPoolManager().retrieve(ssn);
			//String dept_chk = this.getExpertPoolManager().selectDeptChk(expertPool_chk.getDept());
			/*if("J".equals(expertPool_chk.getJobClass()))
			{
				filters.put("runningDeptCode", dept_chk);
			}*/
			
			ValueList valueList = vlh.getValueList("projectSearchList", info);
			request.setAttribute("list", valueList);				
			// Job Date: 2011-02-09	Author: yhyim	Description: 프로젝트 종료 정보 중 평가 정보 조회 권한 지정(팀장 이상 관리자만 컨설턴트 평가, PL 평가 조회 가능) 
			ExpertPool expertPool = this.getExpertPoolManager().retrieve(ssn);
			if( (!expertPool.getCompanyPosition().equals("10CM") && expertPool.getCompanyPosition().substring(0, 2).compareTo("10") < 0 ) 
				|| expertPool.getDept().startsWith("938") || expertPool.getDept().startsWith("9362") ) {
				request.setAttribute("evalRole", "Y");
			}

			request.setAttribute("keyword", keyword);
			request.setAttribute("keywordType", keywordType);
			request.setAttribute("delayState", delayState);
			request.setAttribute("ssn", ssn);
			request.setAttribute("businessTypeCode", businessTypeCode);
			request.setAttribute("projectTypeCode", projectTypeCode);
			request.setAttribute("runningDivCode", runningDivCode);
			request.setAttribute("runningDivCode1", runningDivCode1);
			request.setAttribute("runningDivCode2", runningDivCode2);
			request.setAttribute("runningDeptCode", runningDeptCode);
			request.setAttribute("runningPUCode", runningPUCode);
			request.setAttribute("industryTypeCode", industryTypeCode);
			request.setAttribute("realStartDate", realStartDate);
			request.setAttribute("realEndDate", realEndDate);
			request.setAttribute("projectState", projectState);
			request.setAttribute("endGubun", endGubun);
			request.setAttribute("costOver", costOver);
			request.setAttribute("endProcess", endProcess);
			request.setAttribute("etcCostOver", etcCostOver);
			request.setAttribute("payCostOver", payCostOver);
			request.setAttribute("chartSsn", chartSsn);
			request.setAttribute("isPrevDiv", isPrevDiv);
			request.setAttribute("realStartDate", realStartDate);
			request.setAttribute("realEndDate", realEndDate);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return mapping.findForward("projectSearchList");
	}
	
	public ActionForward saveToExcel(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String pageSize = ServletRequestUtils.getStringParameter(request, "pageSize", "1000");
		String pageNo = ServletRequestUtils.getStringParameter(request, "pageNo", "1");
		//String projectName = ServletRequestUtils.getStringParameter(request, "projectName", "");
		//String projectCode = ServletRequestUtils.getStringParameter(request, "projectCode", "");
		String delayState = ServletRequestUtils.getStringParameter(request, "delayState", "");
		String delayStateAll = ServletRequestUtils.getStringParameter(request, "delayStateAll", ""); 
		String chartSsn = ServletRequestUtils.getStringParameter(request, "chartSsn", "");
		String ssn = SessionUtils.getUsername(request);
		String businessTypeCode = ServletRequestUtils.getStringParameter(request, "businessTypeCode", "");
		String projectTypeCode = ServletRequestUtils.getStringParameter(request, "projectTypeCode", "");
		String runningDivCode = ServletRequestUtils.getStringParameter(request, "runningDivCode", "");
		String runningDivCode2 = ServletRequestUtils.getStringParameter(request, "runningDivCode2", "");
		String runningDeptCode = ServletRequestUtils.getStringParameter(request, "runningDeptCode", "");
		String runningPUCode = ServletRequestUtils.getStringParameter(request, "runningPUCode", "");
		String industryTypeCode = ServletRequestUtils.getStringParameter(request, "industryTypeCode", "");
		String realStartDate =  ServletRequestUtils.getStringParameter(request, "realStartDate", "");
		String realEndDate =  ServletRequestUtils.getStringParameter(request, "realEndDate","");
		String pjtEndYear = ServletRequestUtils.getStringParameter(request, "pjtEndYear", "");
		String projectState = ServletRequestUtils.getStringParameter(request, "projectState", "3");
		String endGubun = ServletRequestUtils.getStringParameter(request, "endGubun", "");
		String costOver = ServletRequestUtils.getStringParameter(request, "costOver", "");
		String endProcess = ServletRequestUtils.getStringParameter(request, "endProcess", "");
		String etcCostOver = ServletRequestUtils.getStringParameter(request, "etcCostOver", "");
		String payCostOver = ServletRequestUtils.getStringParameter(request, "payCostOver", "");
		String keywordType = ServletRequestUtils.getStringParameter(request, "keywordType", "");
		String keyword = ServletRequestUtils.getStringParameter(request, "keyword", "");
		String isPrevDiv = ServletRequestUtils.getStringParameter(request, "isPrevDiv", "N");
		
		System.out.println(realEndDate);
		System.out.println(realEndDate + "-"+realStartDate);
		if (!realStartDate.equals("")) {
			realStartDate = StringUtil.replace(realStartDate, "-", "");
			if (realStartDate.length() < 8) {
				realStartDate = realStartDate;
			}
		}
		if (!realEndDate.equals("")) {
			realEndDate = StringUtil.replace(realEndDate, "-", "");
			if (realEndDate.length() < 8) {
				realEndDate = realEndDate;
			}
		}
		String fileName = "프로젝트 현황.xls";
		response.setHeader("Content-Disposition", "attachment; filename=" + new String(fileName.getBytes("EUC-KR"), "8859_1") + ";"); 
	    response.setHeader("Content-Description", "JSP Generated Data");
	    
		try {
			WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh = (ValueListHandler) wc.getBean("projectValueList", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();
			Map<String, String> filters = new HashMap<String, String>();
			filters.put(ValueListInfo.PAGING_PAGE, pageNo);
			filters.put(ValueListInfo.PAGING_NUMBER_PER, pageSize);
			//if (!projectName.equals("")) {				filters.put("projectName", "%" + projectName + "%");			}
			//if (!projectCode.equals("")) {				filters.put("projectCode", projectCode);			}
			if (keywordType.equals("PCODE")) {
				filters.put("projectCode", keyword);	
			} else if (keywordType.equals("CUST")) {
				filters.put("customerName", "%" + keyword + "%");	
			} else {
				filters.put("projectName", "%" + keyword + "%");	
			}
			if (!businessTypeCode.equals("")) {			filters.put("businessTypeCode", businessTypeCode);			}
			if (!projectTypeCode.equals("")) {			filters.put("projectTypeCode", projectTypeCode);			}
			//if (!ssn.equals("")) {					filters.put("ssn", ssn);}
			if (!etcCostOver.equals("")) {				filters.put("etcCostOver", etcCostOver);			}
			if (!payCostOver.equals("")) {				filters.put("payCostOver", payCostOver);			}
			if (!delayState.equals("")) {				filters.put("delayState", delayState);			}
			if (!delayStateAll.equals("")) {			filters.put("delayStateAll", delayStateAll);			}
			if (!endProcess.equals("")) {				filters.put("endProcess", endProcess);			}
			if (isPrevDiv.equals("N")) {
				if (!runningDeptCode.equals("")) {			filters.put("runningDeptCode", runningDeptCode);			}
			} else {
				if(isPrevDiv.equals("E")){
					if (!runningDivCode.equals("")) {			filters.put("runningDeptCode", runningDivCode);			}
				}else if(isPrevDiv.equals("F")){
					if (!runningDivCode2.equals("")) {			filters.put("runningDivCode", runningDivCode2);			}
				}
			}
			if (!runningPUCode.equals("")) {			filters.put("runningPUCode", runningPUCode);			}
			if (!industryTypeCode.equals("")) {			filters.put("industryTypeCode", industryTypeCode);			}
			if (!endGubun.equals("")) {					filters.put("endGubun", endGubun);			}
			if (!costOver.equals("")) {					filters.put("costOver", costOver);			}
			if (!chartSsn.equals("")) {					filters.put("chartSsn", chartSsn);			}
			if (!pjtEndYear.equals("")) {				filters.put("pjtEndYear", pjtEndYear+"%");	}
			if (!realStartDate.equals("")) {
				realStartDate = StringUtil.replace(realStartDate, "-", "");
				if (realStartDate.length() < 8) {
					realStartDate = realStartDate + "01";
				}
				filters.put("realStartDate", realStartDate);
			}
			if (!realEndDate.equals("")) {
				realEndDate = StringUtil.replace(realEndDate, "-", "");
				if (realEndDate.length() < 8) {
					realEndDate = realEndDate + "31";
				}
				filters.put("realEndDate", realEndDate);
			}
			if (!projectState.equals("")) {
				if (projectState.equals("EV"))
					filters.put("endProcess", projectState);
				else
					filters.put("projectState", projectState);
			}
			info.setFilters(filters);
			ValueList valueList = vlh.getValueList("projectSearchList", info);

			request.setAttribute("list", valueList);
			
			// Job Date: 2011-02-09	Author: yhyim	Description: 프로젝트 종료 정보 중 평가 정보 조회 권한 지정(팀장 이상 관리자만 컨설턴트 평가, PL 평가 조회 가능) 
			ExpertPool expertPool = this.getExpertPoolManager().retrieve(ssn);
			if( (!expertPool.getCompanyPosition().equals("10CM") && expertPool.getCompanyPosition().substring(0, 2).compareTo("10") < 0 ) 
				||expertPool.getDept().startsWith("938") || expertPool.getDept().startsWith("9362") ) {
				request.setAttribute("evalRole", "Y");
			}

			//request.setAttribute("projectName", projectName);
			//request.setAttribute("projectCode", projectCode);
			request.setAttribute("keyword", keyword);
			request.setAttribute("keywordType", keywordType);
			request.setAttribute("delayState", delayState);
			request.setAttribute("ssn", ssn);
			request.setAttribute("businessTypeCode", businessTypeCode);
			request.setAttribute("projectTypeCode", projectTypeCode);
			request.setAttribute("runningDivCode", runningDivCode);
			request.setAttribute("runningDivCode2", runningDivCode2);
			request.setAttribute("runningDeptCode", runningDeptCode);
			request.setAttribute("runningPUCode", runningPUCode);
			request.setAttribute("industryTypeCode", industryTypeCode);
			request.setAttribute("realStartDate", realStartDate);
			request.setAttribute("realEndDate", realEndDate);
			request.setAttribute("projectState", projectState);
			request.setAttribute("endGubun", endGubun);
			request.setAttribute("costOver", costOver);
			request.setAttribute("endProcess", endProcess);
			request.setAttribute("etcCostOver", etcCostOver);
			request.setAttribute("payCostOver", payCostOver);
			request.setAttribute("chartSsn", chartSsn);
			request.setAttribute("isPrevDiv", isPrevDiv);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return mapping.findForward("saveToExcel");
	}
	
	public ActionForward getProjectSearchList2(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String pageSize = ServletRequestUtils.getStringParameter(request, "pageSize", "1000");
		String pageNo = ServletRequestUtils.getStringParameter(request, "pageNo", "1");
		//String projectName = ServletRequestUtils.getStringParameter(request, "projectName", "");
		//String projectCode = ServletRequestUtils.getStringParameter(request, "projectCode", "");
		String delayState = ServletRequestUtils.getStringParameter(request, "delayState", "");
		String delayStateAll = ServletRequestUtils.getStringParameter(request, "delayStateAll", ""); 
		String chartSsn = ServletRequestUtils.getStringParameter(request, "chartSsn", "");
		String ssn = SessionUtils.getUsername(request);
		String businessTypeCode = ServletRequestUtils.getStringParameter(request, "businessTypeCode", "");
		String projectTypeCode = ServletRequestUtils.getStringParameter(request, "projectTypeCode", "");
		String runningDivCode = ServletRequestUtils.getStringParameter(request, "runningDivCode", "");
		String runningDeptCode = ServletRequestUtils.getStringParameter(request, "runningDeptCode", "");
		String runningPUCode = ServletRequestUtils.getStringParameter(request, "runningPUCode", "");
		String industryTypeCode = ServletRequestUtils.getStringParameter(request, "industryTypeCode", "");
		String realStartDate =  ServletRequestUtils.getStringParameter(request, "startDate", "");
		String realEndDate =  ServletRequestUtils.getStringParameter(request, "endDate","");
		String pjtEndYear = ServletRequestUtils.getStringParameter(request, "pjtEndYear", "");
		String projectState = ServletRequestUtils.getStringParameter(request, "projectState", "3");
		String endGubun = ServletRequestUtils.getStringParameter(request, "endGubun", "");
		String costOver = ServletRequestUtils.getStringParameter(request, "costOver", "");
		String endProcess = ServletRequestUtils.getStringParameter(request, "endProcess", "");
		String etcCostOver = ServletRequestUtils.getStringParameter(request, "etcCostOver", "");
		String payCostOver = ServletRequestUtils.getStringParameter(request, "payCostOver", "");
		String keywordType = ServletRequestUtils.getStringParameter(request, "keywordType", "");
		String keyword = ServletRequestUtils.getStringParameter(request, "keyword", "");
		String isPrevDiv = ServletRequestUtils.getStringParameter(request, "isPrevDiv", "N");
		
		if (!realStartDate.equals("")) {
			realStartDate = StringUtil.replace(realStartDate, "-", "");
			if (realStartDate.length() < 8) {
				realStartDate = realStartDate;
			}
		}
		if (!realEndDate.equals("")) {
			realEndDate = StringUtil.replace(realEndDate, "-", "");
			if (realEndDate.length() < 8) {
				realEndDate = realEndDate;
			}
		}
		System.out.println(realStartDate);
		System.out.println(realEndDate);

		try {
			WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh = (ValueListHandler) wc.getBean("projectValueList", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();
			Map<String, String> filters = new HashMap<String, String>();
			filters.put(ValueListInfo.PAGING_PAGE, pageNo);
			filters.put(ValueListInfo.PAGING_NUMBER_PER, pageSize);
			//if (!projectName.equals("")) {				filters.put("projectName", "%" + projectName + "%");			}
			//if (!projectCode.equals("")) {				filters.put("projectCode", projectCode);			}
			if (keywordType.equals("PCODE")) {
				filters.put("projectCode", keyword);	
			} else if (keywordType.equals("CUST")) {
				filters.put("customerName", "%" + keyword + "%");	
			} else {
				filters.put("projectName", "%" + keyword + "%");	
			}
			if (!businessTypeCode.equals("")) {			filters.put("businessTypeCode", businessTypeCode);			}
			if (!projectTypeCode.equals("")) {			filters.put("projectTypeCode", projectTypeCode);			}
			//if (!ssn.equals("")) {					filters.put("ssn", ssn);}
			if (!etcCostOver.equals("")) {				filters.put("etcCostOver", etcCostOver);			}
			if (!payCostOver.equals("")) {				filters.put("payCostOver", payCostOver);			}
			if (!delayState.equals("")) {				filters.put("delayState", delayState);			}
			if (!delayStateAll.equals("")) {			filters.put("delayStateAll", delayStateAll);			}
			if (!endProcess.equals("")) {				filters.put("endProcess", endProcess);			}
			if (isPrevDiv.equals("N")) {
				if (!runningDeptCode.equals("")) {			filters.put("runningDeptCode", runningDeptCode);			}
			} else {
				if (!runningDivCode.equals("")) {			filters.put("runningDivCode", runningDivCode);			}
			}
			if (!runningPUCode.equals("")) {			filters.put("runningPUCode", runningPUCode);			}
			if (!industryTypeCode.equals("")) {			filters.put("industryTypeCode", industryTypeCode);			}
			if (!endGubun.equals("")) {					filters.put("endGubun", endGubun);			}
			if (!costOver.equals("")) {					filters.put("costOver", costOver);			}
			if (!chartSsn.equals("")) {					filters.put("chartSsn", chartSsn);			}
			if (!pjtEndYear.equals("")) {				filters.put("pjtEndYear", pjtEndYear+"%");	}
			if (!realStartDate.equals("")) {
				realStartDate = StringUtil.replace(realStartDate, "-", "");
				if (realStartDate.length() < 8) {
					realStartDate = realStartDate + "01";
				}
				filters.put("realStartDate", realStartDate);
			}
			if (!realEndDate.equals("")) {
				realEndDate = StringUtil.replace(realEndDate, "-", "");
				if (realEndDate.length() < 8) {
					realEndDate = realEndDate + "31";
				}
				filters.put("realEndDate", realEndDate);
			}
			if (!projectState.equals("")) {
				if (projectState.equals("EV"))
					filters.put("endProcess", projectState);
				else
					filters.put("projectState", projectState);
			}
			info.setFilters(filters);
			ValueList valueList = vlh.getValueList("projectSearchList2", info);

			request.setAttribute("list", valueList);
			
			// Job Date: 2011-02-09	Author: yhyim	Description: 프로젝트 종료 정보 중 평가 정보 조회 권한 지정(팀장 이상 관리자만 컨설턴트 평가, PL 평가 조회 가능) 
			ExpertPool expertPool = this.getExpertPoolManager().retrieve(ssn);
			if( (!expertPool.getCompanyPosition().equals("10CM") && expertPool.getCompanyPosition().substring(0, 2).compareTo("10") < 0 ) 
				|| expertPool.getDept().startsWith("938") || expertPool.getDept().startsWith("9362") ) {
				request.setAttribute("evalRole", "Y");
			}

			//request.setAttribute("projectName", projectName);
			//request.setAttribute("projectCode", projectCode);
			request.setAttribute("keyword", keyword);
			request.setAttribute("keywordType", keywordType);
			request.setAttribute("delayState", delayState);
			request.setAttribute("ssn", ssn);
			request.setAttribute("businessTypeCode", businessTypeCode);
			request.setAttribute("projectTypeCode", projectTypeCode);
			request.setAttribute("runningDivCode", runningDivCode);
			request.setAttribute("runningDeptCode", runningDeptCode);
			request.setAttribute("runningPUCode", runningPUCode);
			request.setAttribute("industryTypeCode", industryTypeCode);
			request.setAttribute("realStartDate", realStartDate);
			request.setAttribute("realEndDate", realEndDate);
			request.setAttribute("projectState", projectState);
			request.setAttribute("endGubun", endGubun);
			request.setAttribute("costOver", costOver);
			request.setAttribute("endProcess", endProcess);
			request.setAttribute("etcCostOver", etcCostOver);
			request.setAttribute("payCostOver", payCostOver);
			request.setAttribute("chartSsn", chartSsn);
			request.setAttribute("isPrevDiv", isPrevDiv);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return mapping.findForward("projectSearchList2");
	}

	public ActionForward getDelayProjectSearchList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String pageSize = ServletRequestUtils.getStringParameter(request, "pageSize", "150");
		String pageNo = ServletRequestUtils.getStringParameter(request, "pageNo", "1");
		String delayState = ServletRequestUtils.getStringParameter(request, "delayState", "");
		String chartSsn = ServletRequestUtils.getStringParameter(request, "chartSsn", "");
		String ssn = SessionUtils.getUsername(request);
		String businessTypeCode = ServletRequestUtils.getStringParameter(request, "businessTypeCode", "");
		String runningDivCode = ServletRequestUtils.getStringParameter(request, "runningDivCode", "");
		String runningDeptCode = ServletRequestUtils.getStringParameter(request, "runningDeptCode", "");
		String industryTypeCode = ServletRequestUtils.getStringParameter(request, "industryTypeCode", "");
		String projectState = ServletRequestUtils.getStringParameter(request, "projectState", "3");
		String endGubun = ServletRequestUtils.getStringParameter(request, "endGubun", "");
		String costOver = ServletRequestUtils.getStringParameter(request, "costOver", "");
		String endProcess = ServletRequestUtils.getStringParameter(request, "endProcess", "");
		String etcCostOver = ServletRequestUtils.getStringParameter(request, "etcCostOver", "");
		String payCostOver = ServletRequestUtils.getStringParameter(request, "payCostOver", "");
		String keywordType = ServletRequestUtils.getStringParameter(request, "keywordType", "");
		String keyword = ServletRequestUtils.getStringParameter(request, "keyword", "");

		try {
			WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh = (ValueListHandler) wc.getBean("projectValueList", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();
			Map<String, String> filters = new HashMap<String, String>();
			filters.put(ValueListInfo.PAGING_PAGE, pageNo);
			filters.put(ValueListInfo.PAGING_NUMBER_PER, pageSize);
			if (keywordType.equals("PCODE")) {
				filters.put("projectCode", keyword);	
			} else {
				filters.put("projectName", "%" + keyword + "%");	
			}
			if (!businessTypeCode.equals("")) {
				if (businessTypeCode.equals("BGA")) {
					filters.put("BGA", businessTypeCode);
				} else if (businessTypeCode.equals("BGB")) {
					filters.put("BGB", businessTypeCode);
				} else if (businessTypeCode.equals("BGC")) {
					filters.put("BGC", businessTypeCode);
				} else if (businessTypeCode.equals("BGD")) {
					filters.put("BGD", businessTypeCode);
				} else if (businessTypeCode.equals("BGE")) {
					filters.put("BGE", businessTypeCode);
				} else {
					filters.put("businessTypeCode", businessTypeCode);
				}
			}
			if (!etcCostOver.equals("")) {				filters.put("etcCostOver", etcCostOver);			}
			if (!payCostOver.equals("")) {				filters.put("payCostOver", payCostOver);			}
			if (!delayState.equals("")) {				filters.put("delayState", delayState);			}
			if (!endProcess.equals("")) {				filters.put("endProcess", endProcess);			}
			if (!runningDivCode.equals("")) {			filters.put("runningDivCode", runningDivCode);			}
			if (!runningDeptCode.equals("")) {			filters.put("runningDeptCode", runningDeptCode);			}
			if (!industryTypeCode.equals("")) {			filters.put("industryTypeCode", industryTypeCode);			}
			if (!endGubun.equals("")) {					filters.put("endGubun", endGubun);			}
			if (!costOver.equals("")) {					filters.put("costOver", costOver);			}
			if (!chartSsn.equals("")) {					filters.put("chartSsn", chartSsn);			}
			if (!projectState.equals("")) {
				if (projectState.equals("EV"))
					filters.put("endProcess", projectState);
				else
					filters.put("projectState", projectState);
			}
			info.setFilters(filters);
			ValueList valueList = vlh.getValueList("delayProjectSearchList", info);

			request.setAttribute("list", valueList);
			
			// Job Date: 2011-02-09	Author: yhyim	Description: 프로젝트 종료 정보 중 평가 정보 조회 권한 지정(팀장 이상 관리자만 컨설턴트 평가, PL 평가 조회 가능) 
			ExpertPool expertPool = this.getExpertPoolManager().retrieve(ssn);
			if( (!expertPool.getCompanyPosition().equals("10CM") && expertPool.getCompanyPosition().substring(0, 2).compareTo("10") < 0 ) 
				|| expertPool.getDept().startsWith("938") || expertPool.getDept().startsWith("9362") ) {
				request.setAttribute("evalRole", "Y");
			}

			request.setAttribute("keyword", keyword);
			request.setAttribute("keywordType", keywordType);
			request.setAttribute("delayState", delayState);
			request.setAttribute("ssn", ssn);
			request.setAttribute("businessTypeCode", businessTypeCode);
			request.setAttribute("runningDivCode", runningDivCode);
			request.setAttribute("industryTypeCode", industryTypeCode);
			request.setAttribute("projectState", projectState);
			request.setAttribute("endGubun", endGubun);
			request.setAttribute("costOver", costOver);
			request.setAttribute("endProcess", endProcess);
			request.setAttribute("etcCostOver", etcCostOver);
			request.setAttribute("payCostOver", payCostOver);
			request.setAttribute("chartSsn", chartSsn);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return mapping.findForward("delayProjectSearchList");
	}
	
	public ActionForward getDelayProjectSearchList2(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String pageSize = ServletRequestUtils.getStringParameter(request, "pageSize", "150");
		String pageNo = ServletRequestUtils.getStringParameter(request, "pageNo", "1");
		String delayState = ServletRequestUtils.getStringParameter(request, "delayState", "");
		String chartSsn = ServletRequestUtils.getStringParameter(request, "chartSsn", "");
		String ssn = SessionUtils.getUsername(request);
		String businessTypeCode = ServletRequestUtils.getStringParameter(request, "businessTypeCode", "");
		String runningDivCode = ServletRequestUtils.getStringParameter(request, "runningDivCode", "");
		String runningDeptCode = ServletRequestUtils.getStringParameter(request, "runningDeptCode", "");
		String industryTypeCode = ServletRequestUtils.getStringParameter(request, "industryTypeCode", "");
		String projectState = ServletRequestUtils.getStringParameter(request, "projectState", "3");
		String endGubun = ServletRequestUtils.getStringParameter(request, "endGubun", "");
		String costOver = ServletRequestUtils.getStringParameter(request, "costOver", "");
		String endProcess = ServletRequestUtils.getStringParameter(request, "endProcess", "");
		String etcCostOver = ServletRequestUtils.getStringParameter(request, "etcCostOver", "");
		String payCostOver = ServletRequestUtils.getStringParameter(request, "payCostOver", "");
		String keywordType = ServletRequestUtils.getStringParameter(request, "keywordType", "");
		String keyword = ServletRequestUtils.getStringParameter(request, "keyword", "");
		String realStartDate =  ServletRequestUtils.getStringParameter(request, "startDate", "");
		String realEndDate =  ServletRequestUtils.getStringParameter(request, "endDate","");

		System.out.println(realStartDate);
		System.out.println(realEndDate);
		
		try {
			WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh = (ValueListHandler) wc.getBean("projectValueList", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();
			Map<String, String> filters = new HashMap<String, String>();
			filters.put(ValueListInfo.PAGING_PAGE, pageNo);
			filters.put(ValueListInfo.PAGING_NUMBER_PER, pageSize);
			if (keywordType.equals("PCODE")) {
				filters.put("projectCode", keyword);	
			} else {
				filters.put("projectName", "%" + keyword + "%");	
			}
			if (!businessTypeCode.equals("")) {
				if (businessTypeCode.equals("BGA")) {
					filters.put("BGA", businessTypeCode);
				} else if (businessTypeCode.equals("BGB")) {
					filters.put("BGB", businessTypeCode);
				} else if (businessTypeCode.equals("BGC")) {
					filters.put("BGC", businessTypeCode);
				} else if (businessTypeCode.equals("BGD")) {
					filters.put("BGD", businessTypeCode);
				} else if (businessTypeCode.equals("BGE")) {
					filters.put("BGE", businessTypeCode);
				} else {
					filters.put("businessTypeCode", businessTypeCode);
				}
			}
			if (!etcCostOver.equals("")) {				filters.put("etcCostOver", etcCostOver);			}
			if (!payCostOver.equals("")) {				filters.put("payCostOver", payCostOver);			}
			if (!delayState.equals("")) {				filters.put("delayState", delayState);			}
			if (!endProcess.equals("")) {				filters.put("endProcess", endProcess);			}
			if (!runningDivCode.equals("")) {			filters.put("runningDivCode", runningDivCode);			}
			if (!runningDeptCode.equals("")) {			filters.put("runningDeptCode", runningDeptCode);			}
			if (!industryTypeCode.equals("")) {			filters.put("industryTypeCode", industryTypeCode);			}
			if (!endGubun.equals("")) {					filters.put("endGubun", endGubun);			}
			if (!costOver.equals("")) {					filters.put("costOver", costOver);			}
			if (!chartSsn.equals("")) {					filters.put("chartSsn", chartSsn);			}
			if (!projectState.equals("")) {
				if (projectState.equals("EV"))
					filters.put("endProcess", projectState);
				else
					filters.put("projectState", projectState);
			}
			if (!realStartDate.equals("")) {
				realStartDate = StringUtil.replace(realStartDate, "-", "");
				if (realStartDate.length() < 8) {
					realStartDate = realStartDate;
				}
				filters.put("realStartDate", realStartDate);
			}
			if (!realEndDate.equals("")) {
				realEndDate = StringUtil.replace(realEndDate, "-", "");
				if (realEndDate.length() < 8) {
					realEndDate = realEndDate;
				}
				filters.put("realEndDate", realEndDate);
			}
			info.setFilters(filters);
			ValueList valueList = vlh.getValueList("delayProjectSearchList2", info);

			request.setAttribute("list", valueList);
			
			// Job Date: 2011-02-09	Author: yhyim	Description: 프로젝트 종료 정보 중 평가 정보 조회 권한 지정(팀장 이상 관리자만 컨설턴트 평가, PL 평가 조회 가능) 
			ExpertPool expertPool = this.getExpertPoolManager().retrieve(ssn);
			if( (!expertPool.getCompanyPosition().equals("10CM") && expertPool.getCompanyPosition().substring(0, 2).compareTo("10") < 0 ) 
				|| expertPool.getDept().startsWith("938") || expertPool.getDept().startsWith("9362") ) {
				request.setAttribute("evalRole", "Y");
			}

			request.setAttribute("keyword", keyword);
			request.setAttribute("keywordType", keywordType);
			request.setAttribute("delayState", delayState);
			request.setAttribute("ssn", ssn);
			request.setAttribute("businessTypeCode", businessTypeCode);
			request.setAttribute("runningDivCode", runningDivCode);
			request.setAttribute("industryTypeCode", industryTypeCode);
			request.setAttribute("projectState", projectState);
			request.setAttribute("endGubun", endGubun);
			request.setAttribute("costOver", costOver);
			request.setAttribute("endProcess", endProcess);
			request.setAttribute("etcCostOver", etcCostOver);
			request.setAttribute("payCostOver", payCostOver);
			request.setAttribute("chartSsn", chartSsn);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return mapping.findForward("delayProjectSearchList2");
	}
	
	public ActionForward getProjectCustomerRelationList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		String pageSize = ServletRequestUtils.getStringParameter(request, "pageSize", "15");
		String pageNo = ServletRequestUtils.getStringParameter(request, "pageNo", "1");
		String projectName = ServletRequestUtils.getStringParameter(request, "projectName", "");
		String projectCode = ServletRequestUtils.getStringParameter(request, "projectCode", "");

		String delayState = ServletRequestUtils.getStringParameter(request, "delayState", "");
		//String ssn = SessionUtils.getUsername(request);
		String businessTypeCode = ServletRequestUtils.getStringParameter(request, "businessTypeCode", "");
		String runningDivCode = ServletRequestUtils.getStringParameter(request, "runningDivCode", "");
		String industryTypeCode = ServletRequestUtils.getStringParameter(request, "industryTypeCode", "");
		String realStartDate = ServletRequestUtils.getStringParameter(request, "realStartDate", "");
		String realEndDate = ServletRequestUtils.getStringParameter(request, "realEndDate", "");
		String projectState = ServletRequestUtils.getStringParameter(request, "projectState", "");
		String cnt = ServletRequestUtils.getStringParameter(request, "cnt", "");
		String pjtYear = ServletRequestUtils.getStringParameter(request, "pjtYear", "");

		try {
			WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh = (ValueListHandler) wc.getBean("projectValueList", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();
			Map<String, String> filters = new HashMap<String, String>();
			filters.put(ValueListInfo.PAGING_PAGE, pageNo);
			filters.put(ValueListInfo.PAGING_NUMBER_PER, pageSize);
			if (!projectName.equals("")) {
				filters.put("projectName", "%" + projectName + "%");
			}
			if (!projectCode.equals("")) {
				filters.put("projectCode", projectCode);
			}
			if (!businessTypeCode.equals("")) {
				filters.put("businessTypeCode", businessTypeCode);
			}
			if (!delayState.equals("")) {
				filters.put("delayState", delayState);
			}
			if (!runningDivCode.equals("")) {
				filters.put("runningDivCode", runningDivCode);
			}
			if (!industryTypeCode.equals("")) {
				filters.put("industryTypeCode", industryTypeCode);
			}
			if (!realStartDate.equals("")) {
				realStartDate = StringUtil.replace(realStartDate, "-", "");
				if (realStartDate.length() < 8) {
					realStartDate = realStartDate + "01";
				}
				filters.put("realStartDate", realStartDate);
			}
			if (!realEndDate.equals("")) {
				realEndDate = StringUtil.replace(realEndDate, "-", "");
				if (realEndDate.length() < 8) {
					realEndDate = realEndDate + "31";
				}
				filters.put("realEndDate", realEndDate);
			}
			if (!projectState.equals("")) {
				if (projectState.equals("EV"))
					filters.put("endProcess", projectState);
				else
					filters.put("projectState", projectState);
			}
			if (!cnt.equals("")) {
				if (cnt.equals("Y"))
					filters.put("notZero", "notZero");
				else
					filters.put("zero", "zero");
			}
			if (!pjtYear.equals("")) {
				filters.put("pjtYear", pjtYear);
			}
			info.setFilters(filters);
			ValueList valueList = vlh.getValueList("projectCustomerRelationList", info);

			request.setAttribute("list", valueList);
			request.setAttribute("projectName", projectName);
			request.setAttribute("projectCode", projectCode);
			request.setAttribute("delayState", delayState);
			request.setAttribute("businessTypeCode", businessTypeCode);
			request.setAttribute("runningDivCode", runningDivCode);
			request.setAttribute("industryTypeCode", industryTypeCode);
			request.setAttribute("realStartDate", realStartDate);
			request.setAttribute("realEndDate", realEndDate);
			request.setAttribute("projectState", projectState);
			request.setAttribute("cnt", cnt);
			request.setAttribute("pjtYear", pjtYear);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return mapping.findForward("projectCustomerRelationList");
	}
	
	
	public ActionForward getProjectOutputUploadList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
		throws Exception {
		String pageSize = ServletRequestUtils.getStringParameter(request, "pageSize", "10");
		String pageNo = ServletRequestUtils.getStringParameter(request, "pageNo", "1");
		String projectName = ServletRequestUtils.getStringParameter(request, "projectName", "");
		String projectCode = ServletRequestUtils.getStringParameter(request, "projectCode", "");
		
		String delayState = ServletRequestUtils.getStringParameter(request, "delayState", "");
		String businessTypeCode = ServletRequestUtils.getStringParameter(request, "businessTypeCode", "BTA");
		String runningDivCode = ServletRequestUtils.getStringParameter(request, "runningDivCode", "");
		String industryTypeCode = ServletRequestUtils.getStringParameter(request, "industryTypeCode", "");
		String realStartDate = ServletRequestUtils.getStringParameter(request, "realStartDate", "");
		String realEndDate = ServletRequestUtils.getStringParameter(request, "realEndDate", "");
		String projectState = ServletRequestUtils.getStringParameter(request, "projectState", "6");
		String endGubun = ServletRequestUtils.getStringParameter(request, "endGubun", "");
		String costOver = ServletRequestUtils.getStringParameter(request, "costOver", "");
		String endProcess = ServletRequestUtils.getStringParameter(request, "endProcess", "");
		String etcCostOver = ServletRequestUtils.getStringParameter(request, "etcCostOver", "");
		String payCostOver = ServletRequestUtils.getStringParameter(request, "payCostOver", "");
		
		try {
			WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh = (ValueListHandler) wc.getBean("projectValueList", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();
			Map<String, String> filters = new HashMap<String, String>();
			filters.put(ValueListInfo.PAGING_PAGE, pageNo);
			filters.put(ValueListInfo.PAGING_NUMBER_PER, pageSize);
			if (!projectName.equals("")) {
				filters.put("projectName", "%" + projectName + "%");
			}
			if (!projectCode.equals("")) {
				filters.put("projectCode", projectCode);
			}
			if (!businessTypeCode.equals("")) {
				filters.put("businessTypeCode", businessTypeCode);
			}
			if (!etcCostOver.equals("")) {
				filters.put("etcCostOver", etcCostOver);
			}
			if (!payCostOver.equals("")) {
				filters.put("payCostOver", payCostOver);
			}
			if (!delayState.equals("")) {
				filters.put("delayState", delayState);
			}
			if (!endProcess.equals("")) {
				filters.put("endProcess", endProcess);
			}
			if (!runningDivCode.equals("")) {
				filters.put("runningDivCode", runningDivCode);
			}
			if (!industryTypeCode.equals("")) {
				filters.put("industryTypeCode", industryTypeCode);
			}
			if (!realStartDate.equals("")) {
				realStartDate = StringUtil.replace(realStartDate, "-", "");
				if (realStartDate.length() < 8) {
					realStartDate = realStartDate + "01";
				}
				filters.put("realStartDate", realStartDate);
			}
			if (!realEndDate.equals("")) {
				realEndDate = StringUtil.replace(realEndDate, "-", "");
				if (realEndDate.length() < 8) {
					realEndDate = realEndDate + "31";
				}
				filters.put("realEndDate", realEndDate);
			}
			if (!projectState.equals("")) {
				if (projectState.equals("EV"))
					filters.put("endProcess", projectState);
				else
					filters.put("projectState", projectState);
			}
			if (!endGubun.equals("")) {
				filters.put("endGubun", endGubun);
			}
			if (!costOver.equals("")) {
				filters.put("costOver", costOver);
			}
			info.setFilters(filters);
			ValueList valueList = vlh.getValueList("projectSearchList", info);
		
			request.setAttribute("list", valueList);
		
			request.setAttribute("projectName", projectName);
			request.setAttribute("projectCode", projectCode);
			request.setAttribute("delayState", delayState);
			request.setAttribute("businessTypeCode", businessTypeCode);
			request.setAttribute("runningDivCode", runningDivCode);
			request.setAttribute("industryTypeCode", industryTypeCode);
			request.setAttribute("realStartDate", realStartDate);
			request.setAttribute("realEndDate", realEndDate);
			request.setAttribute("projectState", projectState);
			request.setAttribute("endGubun", endGubun);
			request.setAttribute("costOver", costOver);
			request.setAttribute("endProcess", endProcess);
			request.setAttribute("etcCostOver", etcCostOver);
			request.setAttribute("payCostOver", payCostOver);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return mapping.findForward("projectOutputUploadList");
	}

	public ActionForward getProjectAdminOpenList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
		throws Exception {
		String pageSize = ServletRequestUtils.getStringParameter(request, "pageSize", "15");
		String pageNo = ServletRequestUtils.getStringParameter(request, "pageNo", "1");
		String projectName = ServletRequestUtils.getStringParameter(request, "projectName", "");
		String projectCode = ServletRequestUtils.getStringParameter(request, "projectCode", "");		
		String delayState = ServletRequestUtils.getStringParameter(request, "delayState", "");
		String businessTypeCode = ServletRequestUtils.getStringParameter(request, "businessTypeCode", "");
		String runningDivCode = ServletRequestUtils.getStringParameter(request, "runningDivCode", "");
		String projectState = ServletRequestUtils.getStringParameter(request, "projectState", "");
		String adminOpen = ServletRequestUtils.getStringParameter(request, "adminOpen", "Y");
		
		try {
			WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh = (ValueListHandler) wc.getBean("projectValueList", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();
			Map<String, String> filters = new HashMap<String, String>();
			filters.put(ValueListInfo.PAGING_PAGE, pageNo);
			filters.put(ValueListInfo.PAGING_NUMBER_PER, pageSize);
			if (!projectName.equals("")) {
				filters.put("projectName", "%" + projectName + "%");
			}
			if (!projectCode.equals("")) {
				filters.put("projectCode", projectCode);
			}
			if (!businessTypeCode.equals("")) {
				filters.put("businessTypeCode", businessTypeCode);
			}
			if (!delayState.equals("")) {
				filters.put("delayState", delayState);
			}
			if (!runningDivCode.equals("")) {
				filters.put("runningDivCode", runningDivCode);
			}
			if (!projectState.equals("")) {
				if (projectState.equals("EV"))
					filters.put("endProcess", projectState);
				else
					filters.put("projectState", projectState);
			}
			if (!adminOpen.equals("")) {
				filters.put("adminOpen", adminOpen);
			}
			info.setFilters(filters);
			ValueList valueList = vlh.getValueList("projectSearchList", info);
		
			request.setAttribute("list", valueList);
		
			request.setAttribute("projectName", projectName);
			request.setAttribute("projectCode", projectCode);
			request.setAttribute("delayState", delayState);
			request.setAttribute("businessTypeCode", businessTypeCode);
			request.setAttribute("runningDivCode", runningDivCode);
			request.setAttribute("projectState", projectState);
			request.setAttribute("adminOpen", adminOpen);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return mapping.findForward("projectAdminOpenList");
	}
	
	public ActionForward getProjectInfoTab(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String projectCode = ServletRequestUtils.getRequiredStringParameter(request, "projectCode");
		String viewMode = ServletRequestUtils.getRequiredStringParameter(request, "viewMode");
		String projectRole = ServletRequestUtils.getStringParameter(request, "projectRole", "NOMB");
		String ssn = SessionUtils.getUsername(request);
		boolean isRefresh = ServletRequestUtils.getBooleanParameter(request, "isRefresh", false);

		ExpertPool expertPool = this.getExpertPoolManager().retrieve(ssn);

		// Job Date: 2010-06-16	Author: yhyim	Description: 경영기획실에 PM권한 부여
		if (expertPool.getDept().startsWith("938") || expertPool.getDept().startsWith("9362")) {
			request.setAttribute("projectRole", "PM");
		} else {
			// Job Date: 2010-06-16	Author: yhyim	Description: My Project와 프로젝트 현황을 분리(My Project의 다중 Role 처리를 위하여)
			if (viewMode.equals("myProject"))
				request.setAttribute("projectRole", projectRole);
			else {
				ProjectMember projectMember = this.getProjectMasterInfoManager().getProjectMember(projectCode, ssn);
				request.setAttribute("projectRole", projectMember == null ? "NOMB" : projectMember.getRole());
			}
				
		}

		Project project = null;
		if (!isRefresh && this.getProjectMasterInfoManager().isRegisteredProject(projectCode)) { 
			project = this.getProjectMasterInfoManager().getProject(projectCode);
		} else {
			project = this.getProjectMasterInfoManager().getErpProject(projectCode);
			//예산 동기화 시 프로젝트 상태 1로 변경
			//Project oldProject = this.getProjectMasterInfoManager().getProject(projectCode);
			//if (oldProject != null) {
			//	project.setProjectState(oldProject.getProjectState());
			//}
		}
		
		try {
			request.setAttribute("sanctionDoc", sanctionDocManager.getGeneralSanctionDoc(projectCode));
		} catch (Exception e) {
		}
		
		// Job Date: 2013-05-15	Author: yhyim	Description: 해당 프로젝트가 소속 본부의 프로젝트 인지 여부 체크
		if (expertPool.getDept().substring(0, 3).equals(project.getRunningDivCode().substring(0, 3))
				&& expertPool.getJobClass().equals("A")) {
			request.setAttribute("divRole", "divRole");
		} else {
			request.setAttribute("divRole", "");			
		}
		
		// Job Date: 2019-09-19 Author: yskim	Description: SALARY_CHANGE_DATE 요율,금액 변경 제어 추가 
		CodeEntity codeEntity = this.getCommonCodeManager().getCodeEntity("SALARY_CHANGE_DATE", "date");
		request.setAttribute("canSanction", codeEntity);
		
		request.setAttribute("projectCode", projectCode);
		// request.setAttribute("project", this.getProjectMasterInfoManager().getProject(projectCode));
		request.setAttribute("project", project);
		request.setAttribute("position", expertPool.getCompanyPosition());
		request.setAttribute("jobclass", expertPool.getJobClass());
		request.setAttribute("viewMode", viewMode);
		request.setAttribute("isRefresh", Boolean.toString(isRefresh));

		return mapping.findForward("projectInfoTab");
	}

	public void getProjectAuthInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		String projectCode = ServletRequestUtils.getRequiredStringParameter(request, "projectCode");
		String ssn = SessionUtils.getUsername(request);

		try {
			ProjectMember projectMember = this.getProjectMasterInfoManager().getProjectMember(projectCode, ssn);
			ExpertPool expertPool = this.getExpertPoolManager().retrieve(ssn);
			
			// 경영기획실에 PM권한 부여
			if (expertPool.getDept().startsWith("938") || expertPool.getDept().startsWith("9362"))
				map.put("projectRole", "PM");
			else
				map.put("projectRole", projectMember == null ? "NOMB" : projectMember.getRole());
			map.put("position", expertPool.getCompanyPosition());
			map.put("jobclass", expertPool.getJobClass());

			AjaxUtil.successWrite(response, map);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			AjaxUtil.failWrite(response, map);
		}
	}

	public void getProjectDelayInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		String projectCode = ServletRequestUtils.getRequiredStringParameter(request, "projectCode");

		try {
			List<ProjectDelayInfo> projectDelayInfoList = this.getProjectMasterInfoManager().getProjectDelayInfo(projectCode, null);

			map.put("projectDelayInfoList", projectDelayInfoList);

			AjaxUtil.successWrite(response, map);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			AjaxUtil.failWrite(response, map);
		}
	}
	
	public void getProjectTaskDelayInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		String projectCode = ServletRequestUtils.getRequiredStringParameter(request, "projectCode");
		
		try {
			List<ProjectDelayInfo> projectDelayInfoList = this.getProjectMasterInfoManager().getProjectTaskDelayInfo(projectCode, null);
			
			map.put("projectTaskDelayInfo", projectDelayInfoList);
			
			AjaxUtil.successWrite(response, map);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			AjaxUtil.failWrite(response, map);
		}
	}
	
	public void setProjectAdminOpen(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String projectCode = ServletRequestUtils.getRequiredStringParameter(request, "adminOpenProject");
		String adminOpenYn = ServletRequestUtils.getRequiredStringParameter(request, "adminOpenYn");
		
		try {
			this.getProjectMasterInfoManager().updateAdminOpenState(projectCode, adminOpenYn);
			AjaxUtil.successWrite(response);
		} catch (Exception e) {
			AjaxUtil.failWrite(response);
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * @return the projectMasterInfoManager
	 */
	public ProjectMasterInfoManager getProjectMasterInfoManager() {
		return projectMasterInfoManager;
	}

	/**
	 * @param projectMasterInfoManager the projectMasterInfoManager to set
	 */
	public void setProjectMasterInfoManager(ProjectMasterInfoManager projectMasterInfoManager) {
		this.projectMasterInfoManager = projectMasterInfoManager;
	}

	public ExpertPoolManager getExpertPoolManager() {
		return expertPoolManager;
	}

	public void setExpertPoolManager(ExpertPoolManager expertPoolManager) {
		this.expertPoolManager = expertPoolManager;
	}

	public SanctionDocManager getSanctionDocManager() {
		return sanctionDocManager;
	}

	public void setSanctionDocManager(SanctionDocManager sanctionDocManager) {
		this.sanctionDocManager = sanctionDocManager;
	}
	public CommonCodeManager getCommonCodeManager() {
		return commonCodeManager;
	}
	public void setCommonCodeManager(CommonCodeManager commonCodeManager) {
		this.commonCodeManager = commonCodeManager;
	}
	
}
