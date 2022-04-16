package kr.co.kmac.pms.sanction.projectexpense.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.kmac.pms.common.ajax.AjaxUtil;
import kr.co.kmac.pms.common.code.data.CodeEntity;
import kr.co.kmac.pms.common.code.manager.CommonCodeManager;
import kr.co.kmac.pms.common.util.DateTime;
import kr.co.kmac.pms.common.util.SessionUtils;
import kr.co.kmac.pms.common.worklist.data.Work;
import kr.co.kmac.pms.common.worklist.manager.WorklistManager;
import kr.co.kmac.pms.expertpool.data.ExpertPool;
import kr.co.kmac.pms.expertpool.manager.ExpertPoolManager;
import kr.co.kmac.pms.sanction.general.data.SanctionConstants;
import kr.co.kmac.pms.sanction.general.data.SanctionDoc;
import kr.co.kmac.pms.sanction.general.manager.SanctionDocManager;
import kr.co.kmac.pms.sanction.line.data.SanctionLine;
import kr.co.kmac.pms.sanction.line.manager.SanctionLineManager;
import kr.co.kmac.pms.sanction.projectexpense.data.ExpenseRealTimeResultDetailForSanction;
import kr.co.kmac.pms.sanction.projectexpense.form.ProjectExpenseForm;
import kr.co.kmac.pms.sanction.projectexpense.manager.ProjectExpenseManager;
import kr.co.kmac.pms.system.sanction.data.SanctionTemplate;
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
 * @struts.action name="expenseSanctionAction" path="/action/ProjectARExpenseSanctionAction" parameter="mode" scope="request"
 * @struts.action-forward name="projectARExpenseSanctionForm" path="/sanction/sanctionForm/projectARExpenseSanctionForm.jsp" redirect="false"
 * @struts.action-forward name="projectARExpenseRestSanctionForm" path="/sanction/sanctionForm/projectARExpenseRestSanctionForm.jsp" redirect="false"
 * @struts.action-forward name="projectRefExpenseSanctionForm" path="/sanction/sanctionForm/projectRefExpenseSanctionForm.jsp" redirect="false"
 * @struts.action-forward name="saveToExcel" path="/sanction/sanctionForm/saveToExcel.jsp" redirect="false"
 */
public class ProjectARExpenseSanctionAction extends DispatchActionSupport {

	private static final Log logger = LogFactory.getLog(ProjectARExpenseSanctionAction.class);
	private ProjectExpenseManager projectExpenseManager;
	private SanctionTemplateManager sanctionTemplateManager;
	private SanctionDocManager sanctionDocManager;
	private WorklistManager worklistManager;
	private SanctionLineManager sanctionLineManager;
	private ExpertPoolManager expertPoolManager;
	private CommonCodeManager commonCodeManager;

	/**
	 * 아래 액션은 로그인 시 COO에게 강사료 품의 건수를 알려주는 팝업창에서 호출 함
	 * PU장 강사료 품의(컨설턴트 강사료 금액 리스트) 폼 로드
	 */
	@SuppressWarnings("unchecked")
	public void checkARExpenseSanction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		String year = DateTime.getYear();
		String month = DateTime.getMonth();
		int day = Integer.parseInt(DateTime.getDay());
		
		if (day >= 1 && day <= 5) {
			if (month.equals("01")) {
				year = String.valueOf(Integer.parseInt(year) - 1);
				month = "12";
			} else {
				month = String.valueOf(Integer.parseInt(month) - 1);
				if (month.length() == 1) {
					month = "0" + month;
				}
			}
		}

		try {
			CodeEntity codeEntity = this.getCommonCodeManager().getCodeEntity("SALARY_CAN_DATE", "date");

			int toDay = Calendar.getInstance().get(Calendar.DATE);
			
			// 성과급 품의 기간 여부 체크
			// 성과급 품의 기간의 시작일이 1일 이전 인 경우와 1일 이후 인 경우에 따라 계산하는 방식이 달라짐
			// 1일 이전 인 경우(복잡): 오늘이 1일 이후 인 경우와 1일 이전 인 경우로 구분
			// 1일 이후 인 경우(단순): 5일 이전이면 품의 기간 
			boolean dueDate = false;
			if (Integer.parseInt(codeEntity.getData1()) > 5) {
				if (toDay < 5) {
					if (Integer.parseInt(codeEntity.getData2()) >= toDay)
						dueDate = true;
				} else {
					if (Integer.parseInt(codeEntity.getData1()) < toDay && Integer.parseInt(codeEntity.getData2()) < toDay)
						dueDate = true;
				}
			} else {
				if (Integer.parseInt(codeEntity.getData2()) >= toDay)
					dueDate = true;
			}
			
			if (dueDate) {
				List<ExpenseRealTimeResultDetailForSanction> res = new ArrayList<ExpenseRealTimeResultDetailForSanction>();
				WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
				ValueListHandler vlh = (ValueListHandler) wc.getBean("projectExpenseSalary", ValueListHandler.class);
				ValueListInfo info = new ValueListInfo();
				Map<String, String> filters = new HashMap<String, String>();
				filters.put(ValueListInfo.PAGING_PAGE, "1");
				filters.put(ValueListInfo.PAGING_NUMBER_PER, String.valueOf(Integer.MAX_VALUE));
				filters.put("assignDate", year + month + "__");
				filters.put("year", year);
				filters.put("month", month);
				filters.put("init", "init");
				filters.put("arSsn", SessionUtils.getUsername(request));
				info.setFilters(filters);
				// MD, MH, MM(80%) 성과급 품의 내역 존재 여부 확인
				ValueList result = vlh.getValueList("getExpenseRealTimeResultDetailForSanction", info);
				
				//res.addAll(this.getProjectExpenseManager().addAssignDateInExpenseList(result.getList(), "init"));
				//res.addAll(this.getProjectExpenseManager().addSeqNumInExpenseList(result.getList()));
				
				map.put("cntExSanction", String.valueOf(result.getList().size()));
				map.put("hasExSanction", String.valueOf(result.getList().size() > 0));
				
				// MM(20%) 성과급 품의 내역 존재 여부 확인
				ValueList restResult = vlh.getValueList("getExpenseRealTimeRestResultDetailForSanction", info);
				map.put("cntExRestSanction", String.valueOf(restResult.getList().size()));
				map.put("hasExRestSanction", String.valueOf(restResult.getList().size() > 0));
				
			} else {
				map.put("cntExSanction", "0");
				map.put("hasExSanction", false);
				map.put("cntExRestSanction", "0");
				map.put("hasExRestSanction", false);
			}
			
						
			AjaxUtil.successWrite(response, map);
		} catch (Exception e) {
			AjaxUtil.failWrite(response);
			logger.error(e.getMessage(), e);
		}
	}
	
	/**
	 * 프로젝트 팀장 강사료 품의(컨설턴트 강사료 금액 리스트) 폼 로드
	 */
	@SuppressWarnings("unchecked")
	public ActionForward loadARExpenseSanctionForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		String approvalType = ServletRequestUtils.getRequiredStringParameter(request, "approvalType");
		String year = DateTime.getYear();
		String month = DateTime.getMonth();
		
		if (Integer.parseInt(DateTime.getDay()) >= 1 && Integer.parseInt(DateTime.getDay()) <= 5) {
			if (month.equals("01")) {
				year = String.valueOf(Integer.parseInt(year) - 1);
				month = "12";
			} else {
				month = String.valueOf(Integer.parseInt(month) - 1);
				if (month.length() == 1) {
					month = "0" + month;
				}
			}
		}
		String assignDate = year + month;
		request.setAttribute("year", year);
		request.setAttribute("month", month);
		request.setAttribute("assignDate", assignDate);
		request.setAttribute("arSsn", SessionUtils.getUsername(request));
		
		try {
			SanctionDoc sanctionDoc = new SanctionDoc();
			sanctionDoc.setApprovalType(approvalType);
			// 결재라인 세팅
			SanctionLine sanctionLine = this.getSanctionLineManager().selectSanctionLine(SessionUtils.getUsername(request));
			SanctionTemplate sanctionTemplate = this.getSanctionTemplateManager().getSanctionTemplate(approvalType);
			ExpertPool expertPool = getExpertPoolManager().retrieve(SessionUtils.getUsername(request));
			if (sanctionLine != null) {
				setSanctionList(sanctionDoc, sanctionLine, sanctionTemplate);
			} else {
				sanctionDoc.setRegisterSsn(expertPool.getSsn());
				sanctionDoc.setRegisterDept(expertPool.getDept());
			}
			
			//부문장님은 본인이 승인
			if("0".equals(expertPool.getDept().substring(3))){
				System.out.println(expertPool.getDept().substring(3));
				sanctionDoc.setCioSsn(expertPool.getSsn());
				sanctionDoc.setCioDept(expertPool.getDept());
				sanctionDoc.setCioName(expertPool.getName());
			}
			
			sanctionDoc.setProjectCode("8888888");
			sanctionDoc.setRegisterDate(new Date());
			sanctionDoc.setReject("N");
			sanctionDoc.setState(SanctionConstants.SANCTION_STATE_DRAFT);
			
			request.setAttribute("sanctionDoc", sanctionDoc);
			request.setAttribute("sanctionTemplate", sanctionTemplate);
			
			WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh = (ValueListHandler) wc.getBean("projectExpenseSalary", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();
			Map<String, String> filters = new HashMap<String, String>();
			filters.put(ValueListInfo.PAGING_PAGE, "1");
			filters.put(ValueListInfo.PAGING_NUMBER_PER, String.valueOf(Integer.MAX_VALUE));
			filters.put("assignDate", assignDate + "__");
			filters.put("year", year);
			filters.put("month", month);
			filters.put("init", "init");
			filters.put("arSsn", SessionUtils.getUsername(request));
			info.setFilters(filters);
			ValueList result = vlh.getValueList("getExpenseRealTimeResultDetailForSanction", info);
			
			List<ExpenseRealTimeResultDetailForSanction> resTemp = new ArrayList<ExpenseRealTimeResultDetailForSanction>();
			List<ExpenseRealTimeResultDetailForSanction> res = new ArrayList<ExpenseRealTimeResultDetailForSanction>();
			resTemp.addAll(this.getProjectExpenseManager().addAssignDateInExpenseList(result.getList(), "init"));
			res.addAll(this.getProjectExpenseManager().addSeqNumInExpenseList(resTemp));
			
			request.setAttribute("expenselist", res);
			
			CodeEntity codeEntity = this.getCommonCodeManager().getCodeEntity("SALARY_CAN_DATE", "date");
			request.setAttribute("canSanction", codeEntity);
			request.setAttribute("deptCode", expertPool.getDept().substring(0, 3) + "0");
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return mapping.findForward("projectARExpenseSanctionForm");
	}
	
	/**
	 * 프로젝트 팀장 강사료 품의(컨설턴트 강사료 금액 리스트) 폼 로드
	 */
	@SuppressWarnings("unchecked")
	public ActionForward loadARExpenseSanctionFormForMobile(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		String approvalType = ServletRequestUtils.getRequiredStringParameter(request, "approvalType");
		approvalType = approvalType.replace(";", "");
		String year = DateTime.getYear();
		String month = DateTime.getMonth();
		
		if (Integer.parseInt(DateTime.getDay()) >= 1 && Integer.parseInt(DateTime.getDay()) <= 5) {
			if (month.equals("01")) {
				year = String.valueOf(Integer.parseInt(year) - 1);
				month = "12";
			} else {
				month = String.valueOf(Integer.parseInt(month) - 1);
				if (month.length() == 1) {
					month = "0" + month;
				}
			}
		}
		String assignDate = year + month;
		request.setAttribute("year", year);
		request.setAttribute("month", month);
		request.setAttribute("assignDate", assignDate);
		request.setAttribute("arSsn", SessionUtils.getUsername(request));
		
		try {
			SanctionDoc sanctionDoc = new SanctionDoc();
			sanctionDoc.setApprovalType(approvalType);
			// 결재라인 세팅
			SanctionLine sanctionLine = this.getSanctionLineManager().selectSanctionLine(SessionUtils.getUsername(request));
			SanctionTemplate sanctionTemplate = this.getSanctionTemplateManager().getSanctionTemplate(approvalType);
			ExpertPool expertPool = getExpertPoolManager().retrieve(SessionUtils.getUsername(request));
			if (sanctionLine != null) {
				setSanctionList(sanctionDoc, sanctionLine, sanctionTemplate);
			} else {
				sanctionDoc.setRegisterSsn(expertPool.getSsn());
				sanctionDoc.setRegisterDept(expertPool.getDept());
			}
			
			//부문장님은 본인이 승인
			if("0".equals(expertPool.getDept().substring(3))){
				System.out.println(expertPool.getDept().substring(3));
				sanctionDoc.setCioSsn(expertPool.getSsn());
				sanctionDoc.setCioDept(expertPool.getDept());
				sanctionDoc.setCioName(expertPool.getName());
			}
			
			sanctionDoc.setProjectCode("8888888");
			sanctionDoc.setRegisterDate(new Date());
			sanctionDoc.setReject("N");
			sanctionDoc.setState(SanctionConstants.SANCTION_STATE_DRAFT);
			
			request.setAttribute("sanctionDoc", sanctionDoc);
			request.setAttribute("sanctionTemplate", sanctionTemplate);
			
			WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh = (ValueListHandler) wc.getBean("projectExpenseSalary", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();
			Map<String, String> filters = new HashMap<String, String>();
			filters.put(ValueListInfo.PAGING_PAGE, "1");
			filters.put(ValueListInfo.PAGING_NUMBER_PER, String.valueOf(Integer.MAX_VALUE));
			filters.put("assignDate", assignDate + "__");
			filters.put("year", year);
			filters.put("month", month);
			filters.put("init", "init");
			filters.put("arSsn", SessionUtils.getUsername(request));
			info.setFilters(filters);
			ValueList result = vlh.getValueList("getExpenseRealTimeResultDetailForSanction", info);
			
			List<ExpenseRealTimeResultDetailForSanction> resTemp = new ArrayList<ExpenseRealTimeResultDetailForSanction>();
			List<ExpenseRealTimeResultDetailForSanction> res = new ArrayList<ExpenseRealTimeResultDetailForSanction>();
			resTemp.addAll(this.getProjectExpenseManager().addAssignDateInExpenseList(result.getList(), "init"));
			res.addAll(this.getProjectExpenseManager().addSeqNumInExpenseList(resTemp));
			
			request.setAttribute("expenselist", res);
			
			CodeEntity codeEntity = this.getCommonCodeManager().getCodeEntity("SALARY_CAN_DATE", "date");
			request.setAttribute("canSanction", codeEntity);
			request.setAttribute("deptCode", expertPool.getDept().substring(0, 3) + "0");
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return mapping.findForward("projectARExpenseSanctionFormForMobile");
	}
	
	/**
	 * 프로젝트 팀장 20% 강사료 품의(컨설턴트 강사료 금액 리스트) 폼 로드
	 */
	@SuppressWarnings("unchecked")
	public ActionForward loadARExpenseRestSanctionForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		String approvalType = ServletRequestUtils.getRequiredStringParameter(request, "approvalType");
		String year = DateTime.getYear();
		String month = DateTime.getMonth();
		
		if (Integer.parseInt(DateTime.getDay()) >= 1 && Integer.parseInt(DateTime.getDay()) <= 5) {
			if (month.equals("01")) {
				year = String.valueOf(Integer.parseInt(year) - 1);
				month = "12";
			} else {
				month = String.valueOf(Integer.parseInt(month) - 1);
				if (month.length() == 1) {
					month = "0" + month;
				}
			}
		}
		String assignDate = year + month;
		request.setAttribute("year", year);
		request.setAttribute("month", month);
		request.setAttribute("assignDate", assignDate);
		request.setAttribute("arSsn", SessionUtils.getUsername(request));
		
		try {
			SanctionDoc sanctionDoc = new SanctionDoc();
			sanctionDoc.setApprovalType(approvalType);
			// 결재라인 세팅
			SanctionLine sanctionLine = this.getSanctionLineManager().selectSanctionLine(SessionUtils.getUsername(request));
			SanctionTemplate sanctionTemplate = this.getSanctionTemplateManager().getSanctionTemplate(approvalType);
			ExpertPool expertPool = getExpertPoolManager().retrieve(SessionUtils.getUsername(request));
			if (sanctionLine != null) {
				setSanctionList(sanctionDoc, sanctionLine, sanctionTemplate);
			} else {
				sanctionDoc.setRegisterSsn(expertPool.getSsn());
				sanctionDoc.setRegisterDept(expertPool.getDept());
			}
			
			sanctionDoc.setProjectCode("8888888");
			sanctionDoc.setRegisterDate(new Date());
			sanctionDoc.setReject("N");
			sanctionDoc.setState(SanctionConstants.SANCTION_STATE_DRAFT);
			
			request.setAttribute("sanctionDoc", sanctionDoc);
			request.setAttribute("sanctionTemplate", sanctionTemplate);
			
			WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh = (ValueListHandler) wc.getBean("projectExpenseSalary", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();
			Map<String, String> filters = new HashMap<String, String>();
			filters.put(ValueListInfo.PAGING_PAGE, "1");
			filters.put(ValueListInfo.PAGING_NUMBER_PER, String.valueOf(Integer.MAX_VALUE));
			filters.put("assignDate", assignDate + "__");
			filters.put("year", year);
			filters.put("month", month);
			filters.put("init", "init");
			filters.put("arSsn", SessionUtils.getUsername(request));
			info.setFilters(filters);
			ValueList result = vlh.getValueList("getExpenseRealTimeRestResultDetailForSanction", info);
			
			List<ExpenseRealTimeResultDetailForSanction> res = new ArrayList<ExpenseRealTimeResultDetailForSanction>();
			res.addAll(this.getProjectExpenseManager().addSeqNumInExpenseList(result.getList()));
			
			request.setAttribute("expenselist", res);
			
			CodeEntity codeEntity = this.getCommonCodeManager().getCodeEntity("SALARY_CAN_DATE", "date");
			request.setAttribute("canSanction", codeEntity);
			request.setAttribute("deptCode", expertPool.getDept().substring(0, 3) + "0");
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return mapping.findForward("projectARExpenseRestSanctionForm");
	}

	@SuppressWarnings("unchecked")
	public ActionForward refreshARExpenseSanctionForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String workId = ServletRequestUtils.getRequiredStringParameter(request, "workId");
		String assignDate = ServletRequestUtils.getRequiredStringParameter(request, "assignDate");
		String deptCode = ServletRequestUtils.getRequiredStringParameter(request, "deptCode");

		Work work = this.getWorklistManager().getWork(workId);

		SanctionDoc sanctionDoc = this.getSanctionDocManager()
				.getGeneralSanctionDoc(work.getRefWorkId1(), work.getRefWorkId2(), work.getRefWorkId3());
		sanctionDoc.setTaskId(workId);

		WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
		ValueListHandler vlh = (ValueListHandler) wc.getBean("projectExpenseSalary", ValueListHandler.class);
		ValueListInfo info = new ValueListInfo();
		Map<String, String> filters = new HashMap<String, String>();
		filters.put(ValueListInfo.PAGING_PAGE, "1");
		filters.put(ValueListInfo.PAGING_NUMBER_PER, String.valueOf(Integer.MAX_VALUE));
		filters.put("deptCode", deptCode);
		filters.put("assignDate", assignDate + "__");
		filters.put("year", assignDate.substring(0, 4));
		filters.put("month", assignDate.substring(4,6));
		filters.put("refresh", "refresh");
		info.setFilters(filters);
		ValueList result = vlh.getValueList("getExpenseRealTimeResultDetailForSanction", info);

		List<ExpenseRealTimeResultDetailForSanction> res = new ArrayList<ExpenseRealTimeResultDetailForSanction>();
		res.addAll(this.getProjectExpenseManager().addAssignDateInExpenseList(result.getList(), "refresh"));
		res.addAll(this.getProjectExpenseManager().addSeqNumInExpenseList(result.getList()));
		request.setAttribute("expenselist", res);

		request.setAttribute("sanctionDoc", sanctionDoc);
		request.setAttribute("sanctionTemplate", this.getSanctionTemplateManager().getSanctionTemplate(sanctionDoc.getApprovalType()));

		return mapping.findForward("projectARExpenseSanctionForm");
	}

	public ActionForward getARExpenseSanctionData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String workId = ServletRequestUtils.getStringParameter(request, "workId", "");
		boolean readonly = ServletRequestUtils.getBooleanParameter(request, "readonly", false);
		boolean isRefWork = ServletRequestUtils.getBooleanParameter(request, "isRefWork", false);
		
		if (!workId.equals("")) {
			String year = DateTime.getYear();
			String month = DateTime.getMonth();
			if (Integer.parseInt(DateTime.getDay()) >= 1 && Integer.parseInt(DateTime.getDay()) <= 5) {
				if (month.equals("01")) {
					year = String.valueOf(Integer.parseInt(year) - 1);
					month = "12";
				} else {
					month = String.valueOf(Integer.parseInt(month) - 1);
					if (month.length() == 1) {
						month = "0" + month;
					}
				}
			}
			
			Work work = this.getWorklistManager().getWork(workId);
			SanctionDoc sanctionDoc = this.getSanctionDocManager().getGeneralSanctionDoc(work.getRefWorkId1(), work.getRefWorkId2(),
					work.getRefWorkId3());
			sanctionDoc.setTaskId(workId);
			
			/* 전자결재 로그 내용 추가 */
			String ssn = SessionUtils.getUsername(request); //읽은 사람
			SimpleDateFormat date = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss");
			String viewDate = date.format(System.currentTimeMillis());
			SanctionDoc sanctionDoc2 = new SanctionDoc();
			
			sanctionDoc2.setViewSsn(ssn);
			sanctionDoc2.setViewDate(viewDate);
			sanctionDoc2.setSanctionDocSsn(sanctionDoc.getRegisterSsn());
			if(sanctionDoc.getRegisterDate() == null){
				sanctionDoc2.setSanctionDocDate(viewDate);
			}else{
				sanctionDoc2.setSanctionDocDate(String.valueOf(sanctionDoc.getRegisterDate()));
			}
			sanctionDoc2.setSeq(sanctionDoc.getSeq());
			
			this.getSanctionDocManager().insertGeneralSanctionDocLog(sanctionDoc2);
			
			if(sanctionDoc.getState().equals("SANCTION_STATE_REJECT_DRAFT")){
				String assignDate = year + month;
				WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
				ValueListHandler vlh = (ValueListHandler) wc.getBean("projectExpenseSalary", ValueListHandler.class);
				ValueListInfo info = new ValueListInfo();
				Map<String, String> filters = new HashMap<String, String>();
				filters.put(ValueListInfo.PAGING_PAGE, "1");
				filters.put(ValueListInfo.PAGING_NUMBER_PER, String.valueOf(Integer.MAX_VALUE));
				filters.put("assignDate", assignDate + "__");
				filters.put("assignDate1", assignDate);
				filters.put("year", assignDate.substring(0, 4));
				filters.put("month", assignDate.substring(4, 6));
				filters.put("refresh", "refresh");
				filters.put("arSsn", SessionUtils.getUsername(request));
				info.setFilters(filters);
				ValueList result = vlh.getValueList("getExpenseRealTimeResultDetailForSanction", info);

				List<ExpenseRealTimeResultDetailForSanction> resTemp = new ArrayList<ExpenseRealTimeResultDetailForSanction>();
				List<ExpenseRealTimeResultDetailForSanction> res = new ArrayList<ExpenseRealTimeResultDetailForSanction>();
				resTemp.addAll(this.getProjectExpenseManager().addAssignDateInExpenseList(result.getList(), "refresh"));
				res.addAll(this.getProjectExpenseManager().addSeqNumInExpenseList(resTemp));
				
				request.setAttribute("expenselist", res);
			}else{
				request.setAttribute("expenselist", this.getProjectExpenseManager().getProjectExpenseTempDataList(work.getRefWorkId3()));
			}
			CodeEntity codeEntity = this.getCommonCodeManager().getCodeEntity("SALARY_CAN_DATE", "date");
			ExpertPool expertPool = getExpertPoolManager().retrieve(SessionUtils.getUsername(request));
			request.setAttribute("canSanction", codeEntity);
			
			request.setAttribute("sanctionDoc", sanctionDoc);
			request.setAttribute("sanctionTemplate", this.getSanctionTemplateManager().getSanctionTemplate(sanctionDoc.getApprovalType()));
			request.setAttribute("readonly", readonly);
			request.setAttribute("isRefWork", isRefWork);
			request.setAttribute("year", year);
			request.setAttribute("month", month);
			request.setAttribute("deptCode", expertPool.getDept().substring(0, 3) + "0");
			return mapping.findForward("projectARExpenseSanctionForm");
		} else {
			return getARExpenseSanctionData1(mapping, form, request, response);
		}
	}

	public ActionForward getARExpenseSanctionData1(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String projectCode = ServletRequestUtils.getStringParameter(request, "projectCode", "");
		String approvalType = ServletRequestUtils.getStringParameter(request, "approvalType", "");
		String seq = ServletRequestUtils.getStringParameter(request, "seq", "");
		boolean readonly = ServletRequestUtils.getBooleanParameter(request, "readonly", true);

		SanctionDoc sanctionDoc = this.getSanctionDocManager().getGeneralSanctionDoc(projectCode, approvalType, seq);
		
		/* 전자결재 로그 내용 추가 */
		String ssn = SessionUtils.getUsername(request); //읽은 사람
		SimpleDateFormat date = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss");
		String viewDate = date.format(System.currentTimeMillis());
		SanctionDoc sanctionDoc2 = new SanctionDoc();
		
		sanctionDoc2.setViewSsn(ssn);
		sanctionDoc2.setViewDate(viewDate);
		sanctionDoc2.setSanctionDocSsn(sanctionDoc.getRegisterSsn());
		if(sanctionDoc.getRegisterDate() == null){
			sanctionDoc2.setSanctionDocDate(viewDate);
		}else{
			sanctionDoc2.setSanctionDocDate(String.valueOf(sanctionDoc.getRegisterDate()));
		}
		sanctionDoc2.setSeq(sanctionDoc.getSeq());
		
		this.getSanctionDocManager().insertGeneralSanctionDocLog(sanctionDoc2);
		
		CodeEntity codeEntity = this.getCommonCodeManager().getCodeEntity("SALARY_CAN_DATE", "date");
		request.setAttribute("canSanction", codeEntity);
		
		request.setAttribute("readonly", readonly);

		request.setAttribute("expenselist", this.getProjectExpenseManager().getProjectExpenseTempDataList(seq));

		request.setAttribute("sanctionDoc", sanctionDoc);
		request.setAttribute("sanctionTemplate", this.getSanctionTemplateManager().getSanctionTemplate(sanctionDoc.getApprovalType()));

		return mapping.findForward("projectARExpenseSanctionForm");
	}
	
	public ActionForward getARExpenseSanctionDataForMobile(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String workId = ServletRequestUtils.getStringParameter(request, "workId", "");
		boolean readonly = ServletRequestUtils.getBooleanParameter(request, "readonly", false);
		boolean isRefWork = ServletRequestUtils.getBooleanParameter(request, "isRefWork", false);
		
		if (!workId.equals("")) {
			String year = DateTime.getYear();
			String month = DateTime.getMonth();
			if (Integer.parseInt(DateTime.getDay()) >= 1 && Integer.parseInt(DateTime.getDay()) <= 5) {
				if (month.equals("01")) {
					year = String.valueOf(Integer.parseInt(year) - 1);
					month = "12";
				} else {
					month = String.valueOf(Integer.parseInt(month) - 1);
					if (month.length() == 1) {
						month = "0" + month;
					}
				}
			}
			
			Work work = this.getWorklistManager().getWork(workId);
			SanctionDoc sanctionDoc = this.getSanctionDocManager().getGeneralSanctionDoc(work.getRefWorkId1(), work.getRefWorkId2(),
					work.getRefWorkId3());
			sanctionDoc.setTaskId(workId);
			
			/* 전자결재 로그 내용 추가 */
			String ssn = SessionUtils.getUsername(request); //읽은 사람
			SimpleDateFormat date = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss");
			String viewDate = date.format(System.currentTimeMillis());
			SanctionDoc sanctionDoc2 = new SanctionDoc();
			
			sanctionDoc2.setViewSsn(ssn);
			sanctionDoc2.setViewDate(viewDate);
			sanctionDoc2.setSanctionDocSsn(sanctionDoc.getRegisterSsn());
			if(sanctionDoc.getRegisterDate() == null){
				sanctionDoc2.setSanctionDocDate(viewDate);
			}else{
				sanctionDoc2.setSanctionDocDate(String.valueOf(sanctionDoc.getRegisterDate()));
			}
			sanctionDoc2.setSeq(sanctionDoc.getSeq());
			
			this.getSanctionDocManager().insertGeneralSanctionDocLog(sanctionDoc2);
			
			if(sanctionDoc.getState().equals("SANCTION_STATE_REJECT_DRAFT")){
				String assignDate = year + month;
				WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
				ValueListHandler vlh = (ValueListHandler) wc.getBean("projectExpenseSalary", ValueListHandler.class);
				ValueListInfo info = new ValueListInfo();
				Map<String, String> filters = new HashMap<String, String>();
				filters.put(ValueListInfo.PAGING_PAGE, "1");
				filters.put(ValueListInfo.PAGING_NUMBER_PER, String.valueOf(Integer.MAX_VALUE));
				filters.put("assignDate", assignDate + "__");
				filters.put("assignDate1", assignDate);
				filters.put("year", assignDate.substring(0, 4));
				filters.put("month", assignDate.substring(4, 6));
				filters.put("refresh", "refresh");
				filters.put("arSsn", SessionUtils.getUsername(request));
				info.setFilters(filters);
				ValueList result = vlh.getValueList("getExpenseRealTimeResultDetailForSanction", info);

				List<ExpenseRealTimeResultDetailForSanction> resTemp = new ArrayList<ExpenseRealTimeResultDetailForSanction>();
				List<ExpenseRealTimeResultDetailForSanction> res = new ArrayList<ExpenseRealTimeResultDetailForSanction>();
				resTemp.addAll(this.getProjectExpenseManager().addAssignDateInExpenseList(result.getList(), "refresh"));
				res.addAll(this.getProjectExpenseManager().addSeqNumInExpenseList(resTemp));
				
				request.setAttribute("expenselist", res);
			}else{
				request.setAttribute("expenselist", this.getProjectExpenseManager().getProjectExpenseTempDataList(work.getRefWorkId3()));
			}
			CodeEntity codeEntity = this.getCommonCodeManager().getCodeEntity("SALARY_CAN_DATE", "date");
			ExpertPool expertPool = getExpertPoolManager().retrieve(SessionUtils.getUsername(request));
			request.setAttribute("canSanction", codeEntity);
			
			request.setAttribute("sanctionDoc", sanctionDoc);
			request.setAttribute("sanctionTemplate", this.getSanctionTemplateManager().getSanctionTemplate(sanctionDoc.getApprovalType()));
			request.setAttribute("readonly", readonly);
			request.setAttribute("isRefWork", isRefWork);
			request.setAttribute("year", year);
			request.setAttribute("month", month);
			request.setAttribute("deptCode", expertPool.getDept().substring(0, 3) + "0");
			return mapping.findForward("projectARExpenseSanctionFormForMobile");
		} else {
			return getARExpenseSanctionData1ForMobile(mapping, form, request, response);
		}
	}

	public ActionForward getARExpenseSanctionData1ForMobile(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String projectCode = ServletRequestUtils.getStringParameter(request, "projectCode", "");
		String approvalType = ServletRequestUtils.getStringParameter(request, "approvalType", "");
		String seq = ServletRequestUtils.getStringParameter(request, "seq", "");
		boolean readonly = ServletRequestUtils.getBooleanParameter(request, "readonly", true);

		SanctionDoc sanctionDoc = this.getSanctionDocManager().getGeneralSanctionDoc(projectCode, approvalType, seq);
		
		/* 전자결재 로그 내용 추가 */
		String ssn = SessionUtils.getUsername(request); //읽은 사람
		SimpleDateFormat date = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss");
		String viewDate = date.format(System.currentTimeMillis());
		SanctionDoc sanctionDoc2 = new SanctionDoc();
		
		sanctionDoc2.setViewSsn(ssn);
		sanctionDoc2.setViewDate(viewDate);
		sanctionDoc2.setSanctionDocSsn(sanctionDoc.getRegisterSsn());
		if(sanctionDoc.getRegisterDate() == null){
			sanctionDoc2.setSanctionDocDate(viewDate);
		}else{
			sanctionDoc2.setSanctionDocDate(String.valueOf(sanctionDoc.getRegisterDate()));
		}
		sanctionDoc2.setSeq(sanctionDoc.getSeq());
		
		this.getSanctionDocManager().insertGeneralSanctionDocLog(sanctionDoc2);
		
		CodeEntity codeEntity = this.getCommonCodeManager().getCodeEntity("SALARY_CAN_DATE", "date");
		request.setAttribute("canSanction", codeEntity);
		
		request.setAttribute("readonly", readonly);

		request.setAttribute("expenselist", this.getProjectExpenseManager().getProjectExpenseTempDataList(seq));

		request.setAttribute("sanctionDoc", sanctionDoc);
		request.setAttribute("sanctionTemplate", this.getSanctionTemplateManager().getSanctionTemplate(sanctionDoc.getApprovalType()));

		return mapping.findForward("projectARExpenseSanctionFormForMobile");
	}

	public void createARExpenseSanction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();
		ProjectExpenseForm projectExpenseForm = (ProjectExpenseForm) form;
		try {
			SanctionDoc sanctionDoc = SanctionDoc.valueOf(projectExpenseForm);
			sanctionDoc.setProjectCode("8888888");
			map = this.getSanctionDocManager().createGeneralSanctionWork("SPEE8809e0a4c4436010a4ead57e4001f", sanctionDoc);
			projectExpenseForm.setProjectExpenseSeq(map.get("seq"));
			this.getProjectExpenseManager().executeARExpenseSanction(projectExpenseForm);

			AjaxUtil.successWrite(response, map);
		} catch (Exception e) {
			AjaxUtil.failWrite(response);
			logger.error(e.getMessage(), e);
		}
	}

	public void executeARExpenseSanction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();
		ProjectExpenseForm projectExpenseForm = (ProjectExpenseForm) form;
		try {
			SanctionDoc sanctionDoc = SanctionDoc.valueOf(projectExpenseForm);
			sanctionDoc.setSeq(Integer.parseInt(projectExpenseForm.getProjectExpenseSeq()));
			sanctionDoc.setProjectCode("8888888");
			map = this.getSanctionDocManager().executeGeneralSanctionWork(sanctionDoc);

			String state = map.get("state");
			if (state != null && state.equals(SanctionConstants.SANCTION_STATE_COMPLETE)) {
				this.getProjectExpenseManager().assignFinishTask(projectExpenseForm);
			} else {
				if (sanctionDoc.getIsApproved().equals("N")) {
					this.getProjectExpenseManager().rejectARExpenseSanction(projectExpenseForm);
				} else {
					this.getProjectExpenseManager().executeARExpenseSanction(projectExpenseForm);
				}
			}
			AjaxUtil.successWrite(response, map);
		} catch (Exception e) {
			AjaxUtil.failWrite(response);
			logger.error(e.getMessage(), e);
		}
	}

	public void deleteARExpenseSanction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ProjectExpenseForm projectExpenseForm = (ProjectExpenseForm) form;
		try {
			SanctionDoc sanctionDoc = SanctionDoc.valueOf(projectExpenseForm);
			this.getSanctionDocManager().deleteGeneralSanctionDoc(sanctionDoc);
			this.getProjectExpenseManager().deleteARExpenseSanction(projectExpenseForm);
			AjaxUtil.successWrite(response);
		} catch (Exception e) {
			AjaxUtil.failWrite(response);
			logger.error(e.getMessage(), e);
		}
	}

	public void executeRefWork(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();
		ProjectExpenseForm projectExpenseForm = (ProjectExpenseForm) form;

		try {
			SanctionDoc sanctionDoc = SanctionDoc.valueOf(projectExpenseForm);
			sanctionDoc.setSeq(Integer.parseInt(projectExpenseForm.getProjectExpenseSeq()));
			map = this.getSanctionDocManager().executeRefWork(sanctionDoc, SessionUtils.getUsername(request));
			projectExpenseForm.setAssistant1Ssn(SessionUtils.getUsername(request));
			this.getProjectExpenseManager().completeARExpenseSanction(projectExpenseForm);
			AjaxUtil.successWrite(response, map);
		} catch (Exception e) {
			e.printStackTrace();
			AjaxUtil.failWrite(response);
			logger.error(e.getMessage(), e);
		}
	}
	
	public ActionForward getRefExpenseSanctionData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String year = DateTime.getYear();
		String month = DateTime.getMonth();
		if (Integer.parseInt(DateTime.getDay()) >= 1 && Integer.parseInt(DateTime.getDay()) <= 5) {
			if (month.equals("01")) {
				year = String.valueOf(Integer.parseInt(year) - 1);
				month = "12";
			} else {
				month = String.valueOf(Integer.parseInt(month) - 1);
				if (month.length() == 1) {
					month = "0" + month;
				}
			}
		}
	
		request.setAttribute("expenselist", this.getProjectExpenseManager().getProjectExpenseTempDataList(year, month));		
		request.setAttribute("year", year);
		request.setAttribute("month", month);
		return mapping.findForward("projectRefExpenseSanctionForm");

	}	

	private void setSanctionList(SanctionDoc sanctionDoc, SanctionLine sanctionLine, SanctionTemplate sanctionTemplate) {
		CodeEntity codeEntity = this.getCommonCodeManager().getCodeEntity("PROJECT_EXPENSE_GS_LINE", "sanctionLineForExpense");

		sanctionDoc.setRegisterSsn(sanctionLine.getRegisterSsn());
		sanctionDoc.setRegisterName(sanctionLine.getRegisterName());
		sanctionDoc.setRegisterDept(sanctionLine.getRegisterDept());
		sanctionDoc.setTeamManagerSsn(sanctionLine.getTeamManagerSsn());
		sanctionDoc.setTeamManagerName(sanctionLine.getTeamManagerName());
		sanctionDoc.setTeamManagerDept(sanctionLine.getTeamManagerDept());
		sanctionDoc.setCfoSsn(sanctionLine.getCfoSsn());
		sanctionDoc.setCfoName(sanctionLine.getCfoName());
		sanctionDoc.setCfoDept(sanctionLine.getCfoDept());
		sanctionDoc.setCioSsn(sanctionLine.getCioSsn());
		sanctionDoc.setCioName(sanctionLine.getCioName());
		sanctionDoc.setCioDept(sanctionLine.getCioDept());

		sanctionDoc.setAssistant1Ssn(codeEntity.getData1());
		sanctionDoc.setAssistant1Name(codeEntity.getData2());
		sanctionDoc.setAssistant1Dept("2160");

		sanctionDoc.setAssistant2Ssn(codeEntity.getData3());
		sanctionDoc.setAssistant2Name(codeEntity.getData4());
		sanctionDoc.setAssistant2Dept("2160");

		sanctionDoc.setAssistant3Ssn(codeEntity.getData5());
		sanctionDoc.setAssistant3Name(codeEntity.getData6());
		sanctionDoc.setAssistant3Dept("2160");
	}
	
	public ActionForward saveToExcel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		String workId = ServletRequestUtils.getStringParameter(request, "workId", "");
		try {
			String fileName = "COO성과급품의내역.xls";
			response.setHeader("Content-Disposition", "attachment; filename=" + new String(fileName.getBytes("EUC-KR"), "8859_1") + ";"); 
		    response.setHeader("Content-Description", "JSP Generated Data");

			Work work = this.getWorklistManager().getWork(workId);

			request.setAttribute("result", this.getProjectExpenseManager().getProjectExpenseTempDataList(work.getRefWorkId3()));

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return mapping.findForward("saveToExcel");
	}
	
	public ActionForward saveToExcelAll(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		String year = ServletRequestUtils.getStringParameter(request, "year", DateTime.getYear());
		String month = ServletRequestUtils.getStringParameter(request, "month", DateTime.getMonth());
		try {
			String fileName = "성과급품의내역(전체).xls";
			response.setHeader("Content-Disposition", "attachment; filename=" + new String(fileName.getBytes("EUC-KR"), "8859_1") + ";"); 
			response.setHeader("Content-Description", "JSP Generated Data");
			
			request.setAttribute("result", this.getProjectExpenseManager().getProjectExpenseTempDataList(year, month));
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return mapping.findForward("saveToExcel");
	}

	// //////////////////////////////////////////////////////////
	public ProjectExpenseManager getProjectExpenseManager() {
		return projectExpenseManager;
	}

	public void setProjectExpenseManager(ProjectExpenseManager projectExpenseManager) {
		this.projectExpenseManager = projectExpenseManager;
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

	public SanctionLineManager getSanctionLineManager() {
		return sanctionLineManager;
	}

	public void setSanctionLineManager(SanctionLineManager sanctionLineManager) {
		this.sanctionLineManager = sanctionLineManager;
	}

	public ExpertPoolManager getExpertPoolManager() {
		return expertPoolManager;
	}

	public void setExpertPoolManager(ExpertPoolManager expertPoolManager) {
		this.expertPoolManager = expertPoolManager;
	}

	public CommonCodeManager getCommonCodeManager() {
		return commonCodeManager;
	}

	public void setCommonCodeManager(CommonCodeManager commonCodeManager) {
		this.commonCodeManager = commonCodeManager;
	}

}
