package kr.co.kmac.pms.sanction.projectexpense.action;

import java.util.ArrayList;
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
 * @struts.action name="expenseSanctionAction" path="/action/ProjectKMExpenseSanctionAction" parameter="mode" scope="request"
 * @struts.action-forward name="projectKMExpenseSanctionForm" path="/sanction/sanctionForm/projectKMExpenseSanctionForm.jsp" redirect="false"
 * @struts.action-forward name="saveToExcel" path="/sanction/sanctionForm/saveToExcel.jsp" redirect="false"
 */
public class ProjectKMExpenseSanctionAction extends DispatchActionSupport {

	private static final Log logger = LogFactory.getLog(ProjectKMExpenseSanctionAction.class);
	private ProjectExpenseManager projectExpenseManager;
	private SanctionTemplateManager sanctionTemplateManager;
	private SanctionDocManager sanctionDocManager;
	private WorklistManager worklistManager;
	private SanctionLineManager sanctionLineManager;
	private ExpertPoolManager expertPoolManager;
	private CommonCodeManager commonCodeManager;

	/**
	 * PU장 강사료 품의(컨설턴트 강사료 금액 리스트) 폼 로드
	 */
	@SuppressWarnings("unchecked")
	public void checkKMExpenseSanction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();

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

		try {

			WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh = (ValueListHandler) wc.getBean("projectExpenseSalary", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();
			Map<String, String> filters = new HashMap<String, String>();
			filters.put(ValueListInfo.PAGING_PAGE, "1");
			filters.put(ValueListInfo.PAGING_NUMBER_PER, String.valueOf(Integer.MAX_VALUE));
			filters.put("deptCode", "1111");
			filters.put("assignDate", year + month + "__");
			filters.put("init", "init");
			info.setFilters(filters);
			ValueList result = vlh.getValueList("getKMExpenseResultDetailForSanction", info);

			List<ExpenseRealTimeResultDetailForSanction> res = new ArrayList<ExpenseRealTimeResultDetailForSanction>();
			res.addAll(this.getProjectExpenseManager().addAssignDateInExpenseList(result.getList(), "init"));
			res.addAll(this.getProjectExpenseManager().addSeqNumInExpenseList(result.getList()));

			map.put("hasExSanction", String.valueOf(res.size() > 0));
			AjaxUtil.successWrite(response, map);
		} catch (Exception e) {
			AjaxUtil.failWrite(response);
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * PU장 강사료 품의(컨설턴트 강사료 금액 리스트) 폼 로드
	 */
	@SuppressWarnings("unchecked")
	public ActionForward loadKMExpenseSanctionForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
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
		request.setAttribute("year", year);
		request.setAttribute("month", month);
		request.setAttribute("assignDate", year + month);
		request.setAttribute("deptCode", "1111");

		try {
			SanctionDoc sanctionDoc = new SanctionDoc();
			sanctionDoc.setApprovalType(approvalType);
			// 결재라인 세팅
			SanctionLine sanctionLine = this.getSanctionLineManager().selectSanctionLine(SessionUtils.getUsername(request));
			SanctionTemplate sanctionTemplate = this.getSanctionTemplateManager().getSanctionTemplate(approvalType);
			if (sanctionLine != null) {
				setSanctionList(sanctionDoc, sanctionLine, sanctionTemplate);
			} else {
				ExpertPool expertPool = getExpertPoolManager().retrieve(SessionUtils.getUsername(request));
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
			filters.put("deptCode", "1111");
			filters.put("assignDate", year + month + "__");
			filters.put("init", "init");
			info.setFilters(filters);
			ValueList result = vlh.getValueList("getKMExpenseResultDetailForSanction", info);

			request.setAttribute("readySanction", "ready"); // 품의 전 성과급 상세 내역을 조회 여부 flag
			request.setAttribute("expenselist", this.getProjectExpenseManager().addAssignDateInExpenseList(result.getList(), "init"));

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return mapping.findForward("projectKMExpenseSanctionForm");
	}

	@SuppressWarnings("unchecked")
	public ActionForward refreshKMExpenseSanctionForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
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
		filters.put("refresh", "refresh");
		info.setFilters(filters);
		ValueList result = vlh.getValueList("getKMExpenseResultDetailForSanction", info);

		request.setAttribute("expenselist", this.getProjectExpenseManager().addAssignDateInExpenseList(result.getList(), "refresh"));

		request.setAttribute("sanctionDoc", sanctionDoc);
		request.setAttribute("sanctionTemplate", this.getSanctionTemplateManager().getSanctionTemplate(sanctionDoc.getApprovalType()));

		return mapping.findForward("projectKMExpenseSanctionForm");
	}

	public ActionForward getKMExpenseSanctionData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String workId = ServletRequestUtils.getStringParameter(request, "workId", "");
		boolean readonly = ServletRequestUtils.getBooleanParameter(request, "readonly", false);
		boolean isRefWork = ServletRequestUtils.getBooleanParameter(request, "isRefWork", false);

		if (!workId.equals("")) {
			Work work = this.getWorklistManager().getWork(workId);

			SanctionDoc sanctionDoc = this.getSanctionDocManager().getGeneralSanctionDoc(work.getRefWorkId1(), work.getRefWorkId2(),
					work.getRefWorkId3());
			sanctionDoc.setTaskId(workId);

			if (sanctionDoc.getState().equals("SANCTION_STATE_REJECT_DRAFT")) {
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
				WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
				ValueListHandler vlh = (ValueListHandler) wc.getBean("projectExpenseSalary", ValueListHandler.class);
				ValueListInfo info = new ValueListInfo();
				Map<String, String> filters = new HashMap<String, String>();
				filters.put(ValueListInfo.PAGING_PAGE, "1");
				filters.put(ValueListInfo.PAGING_NUMBER_PER, String.valueOf(Integer.MAX_VALUE));
				filters.put("assignDate", assignDate + "__");
				// filters.put("assignDate1", assignDate);
				filters.put("refresh", "refresh");
				// filters.put("arSsn", SessionUtils.getUsername(request));
				info.setFilters(filters);
				ValueList result = vlh.getValueList("getKMExpenseResultDetailForSanction", info);

				List<ExpenseRealTimeResultDetailForSanction> res = new ArrayList<ExpenseRealTimeResultDetailForSanction>();
				res.addAll(this.getProjectExpenseManager().addAssignDateInExpenseList(result.getList(), "refresh"));
				res.addAll(this.getProjectExpenseManager().addSeqNumInExpenseList(result.getList()));
				request.setAttribute("expenselist", res);
			} else {
				request.setAttribute("expenselist", this.getProjectExpenseManager().getProjectExpenseTempDataList(work.getRefWorkId3()));
			}

			request.setAttribute("sanctionDoc", sanctionDoc);
			request.setAttribute("sanctionTemplate", this.getSanctionTemplateManager().getSanctionTemplate(sanctionDoc.getApprovalType()));
			request.setAttribute("readonly", readonly);
			request.setAttribute("isRefWork", isRefWork);
			return mapping.findForward("projectKMExpenseSanctionForm");
		} else {
			return getKMExpenseSanctionData1(mapping, form, request, response);
		}
	}

	public ActionForward getKMExpenseSanctionData1(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String projectCode = ServletRequestUtils.getStringParameter(request, "projectCode", "");
		String approvalType = ServletRequestUtils.getStringParameter(request, "approvalType", "");
		String seq = ServletRequestUtils.getStringParameter(request, "seq", "");
		boolean readonly = ServletRequestUtils.getBooleanParameter(request, "readonly", true);

		SanctionDoc sanctionDoc = this.getSanctionDocManager().getGeneralSanctionDoc(projectCode, approvalType, seq);

		request.setAttribute("readonly", readonly);

		request.setAttribute("expenselist", this.getProjectExpenseManager().getProjectExpenseTempDataList(seq));

		request.setAttribute("readySanction", "ready"); // 품의 전 성과급 상세 내역을 조회 여부 flag
		request.setAttribute("sanctionDoc", sanctionDoc);
		request.setAttribute("sanctionTemplate", this.getSanctionTemplateManager().getSanctionTemplate(sanctionDoc.getApprovalType()));

		return mapping.findForward("projectKMExpenseSanctionForm");
	}

	public void createKMExpenseSanction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();
		ProjectExpenseForm projectExpenseForm = (ProjectExpenseForm) form;
		try {
			SanctionDoc sanctionDoc = SanctionDoc.valueOf(projectExpenseForm);
			sanctionDoc.setProjectCode("8888888");
			map = this.getSanctionDocManager().createGeneralSanctionWork("STEE8809e0a4c4436010a4ead57e4001f", sanctionDoc);
			projectExpenseForm.setProjectExpenseSeq(map.get("seq"));
			this.getProjectExpenseManager().executeKMExpenseSanction(projectExpenseForm);

			AjaxUtil.successWrite(response, map);
		} catch (Exception e) {
			AjaxUtil.failWrite(response);
			logger.error(e.getMessage(), e);
		}
	}

	public void executeKMExpenseSanction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
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
					this.getProjectExpenseManager().rejectKMExpenseSanction(projectExpenseForm);
				} else {
					this.getProjectExpenseManager().executeKMExpenseSanction(projectExpenseForm);
				}
			}
			AjaxUtil.successWrite(response, map);
		} catch (Exception e) {
			AjaxUtil.failWrite(response);
			logger.error(e.getMessage(), e);
		}
	}

	public void deleteKMExpenseSanction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ProjectExpenseForm projectExpenseForm = (ProjectExpenseForm) form;
		try {
			SanctionDoc sanctionDoc = SanctionDoc.valueOf(projectExpenseForm);
			this.getSanctionDocManager().deleteGeneralSanctionDoc(sanctionDoc);
			this.getProjectExpenseManager().deleteKMExpenseSanction(projectExpenseForm);
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
			this.getProjectExpenseManager().completeKMExpenseSanction(projectExpenseForm);
			AjaxUtil.successWrite(response, map);
		} catch (Exception e) {
			AjaxUtil.failWrite(response);
			logger.error(e.getMessage(), e);
		}
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
			String fileName = "전문가품의내역.xls";
			response.setHeader("Content-Disposition", "attachment; filename=" + new String(fileName.getBytes("EUC-KR"), "8859_1") + ";"); 
		    response.setHeader("Content-Description", "JSP Generated Data");

			Work work = this.getWorklistManager().getWork(workId);

			request.setAttribute("result", this.getProjectExpenseManager().getProjectExpenseTempDataList(work.getRefWorkId3()));

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
