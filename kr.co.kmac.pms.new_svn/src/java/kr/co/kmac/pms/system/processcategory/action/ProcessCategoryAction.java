package kr.co.kmac.pms.system.processcategory.action;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.kmac.pms.common.ajax.AjaxUtil;
import kr.co.kmac.pms.common.code.data.CodeEntity;
import kr.co.kmac.pms.common.code.manager.CommonCodeManager;
import kr.co.kmac.pms.common.org.data.OrgUnit;
import kr.co.kmac.pms.common.org.manager.IOrgUnitManager;
import kr.co.kmac.pms.system.processcategory.data.ProcessCategory;
import kr.co.kmac.pms.system.processcategory.manager.ProcessCategoryManager;
import kr.co.kmac.pms.system.processtemplate.data.ProcessTemplate;
import kr.co.kmac.pms.system.processtemplate.manager.ProcessTemplateManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.struts.DispatchActionSupport;

/**
 * @struts.action name="processCategoryAction" path="/action/ProcessCategoryAction" parameter="mode" scope="request"
 * @struts.action-forward name="processCategoryList" path="/system/process/processCategoryList.jsp" redirect="false"
 * @struts.action-forward name="processCategoryDetail" path="/system/process/processCategoryDetail.jsp" redirect="false"
 */
public class ProcessCategoryAction extends DispatchActionSupport {
	private static final Log logger = LogFactory.getLog(ProcessCategoryAction.class);
	private ProcessCategoryManager processCategoryManager;
	private ProcessTemplateManager processTemplateManager;
	private CommonCodeManager commonCodeManager;
	private IOrgUnitManager orgUnitManager;

	@SuppressWarnings("unchecked")
	public ActionForward getProcessCategoryList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String processCategoryName = ServletRequestUtils.getStringParameter(request, "processCategoryName", "");
		List<ProcessCategory> list = this.getProcessCategoryManager().getProcessCategoryList(processCategoryName);
		request.setAttribute("list", list);
		request.setAttribute("listSize", list.size());

		List<OrgUnit> orgUnitList = this.getOrgUnitManager().find();
		List<CodeEntity> bizCodeList = this.getCommonCodeManager().getCodeEntityList2("BUSINESS_TYPE_CODE", "PMS");
		List<CodeEntity> buzFuncList = this.getCommonCodeManager().getCodeEntityList("BUSINESS_FUNCTION_TYPE");
		List<ProcessTemplate> processTemplateList = this.getProcessTemplateManager().getProcessTemplateList(null);

		request.setAttribute("runningDivCodeList", orgUnitList);
		request.setAttribute("businessTypeCodeList", bizCodeList);
		request.setAttribute("businessFunctionTypeList", buzFuncList);
		request.setAttribute("processTemplateList", processTemplateList);

		return mapping.findForward("processCategoryList");
	}

	public void getProcessCategoryObj(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		String processCategoryCode = ServletRequestUtils.getRequiredStringParameter(request, "processCategoryCode");
		map.put("processCategory", this.getProcessCategoryManager().getProcessCategory(processCategoryCode));
		AjaxUtil.successWrite(response, map);
	}

	public void searchProcessCategoryList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();

		String runningDivCode = ServletRequestUtils.getStringParameter(request, "runningDivCode", "");
		String businessTypeCode = ServletRequestUtils.getStringParameter(request, "businessTypeCode", "");
		String processTemplateCode = ServletRequestUtils.getStringParameter(request, "processTemplateCode", "");
		String businessFunctionType = ServletRequestUtils.getStringParameter(request, "businessFunctionType", "");
		String processCategoryName = ServletRequestUtils.getStringParameter(request, "processCategoryName", "");
		try {
			List<ProcessCategory> list = this.getProcessCategoryManager().getProcessCatogoryList(processCategoryName, businessTypeCode,
					runningDivCode, businessFunctionType, processTemplateCode);
			map.put("processCategoryList", list);
			AjaxUtil.successWrite(response, map);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			AjaxUtil.failWrite(response);
		}
	}

	@SuppressWarnings("unchecked")
	public ActionForward getProcessCategory(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String processCategoryCode = ServletRequestUtils.getStringParameter(request, "processCategoryCode", "");

		ProcessCategory processCategory = null;

		if (processCategoryCode.equals("")) {
			processCategory = new ProcessCategory();
		} else {
			processCategory = this.getProcessCategoryManager().getProcessCategory(processCategoryCode);
		}

		List<OrgUnit> orgUnitList = this.getOrgUnitManager().find();
		List<CodeEntity> bizCodeList = this.getCommonCodeManager().getCodeEntityList2("BUSINESS_TYPE_CODE","PMS");
		List<CodeEntity> buzFuncList = this.getCommonCodeManager().getCodeEntityList("BUSINESS_FUNCTION_TYPE");
		List<ProcessTemplate> processTemplateList = this.getProcessTemplateManager().getProcessTemplateList(null);

		request.setAttribute("runningDivCodeList", orgUnitList);
		request.setAttribute("businessTypeCodeList", bizCodeList);
		request.setAttribute("businessFunctionTypeList", buzFuncList);
		request.setAttribute("processTemplateList", processTemplateList);
		request.setAttribute("processCategory", processCategory);
		return mapping.findForward("processCategoryDetail");
	}

	public void storeProcessCategory(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			String processCategoryCode = ServletRequestUtils.getStringParameter(request, "processCategoryCode", "");
			String processCategoryName = ServletRequestUtils.getRequiredStringParameter(request, "processCategoryName");
			String runningDivCode = ServletRequestUtils.getRequiredStringParameter(request, "runningDivCode");
			String businessTypeCode = ServletRequestUtils.getRequiredStringParameter(request, "businessTypeCode");
			String businessFunctionType = ServletRequestUtils.getRequiredStringParameter(request, "businessFunctionType");
			String processTemplateCode = ServletRequestUtils.getRequiredStringParameter(request, "processTemplateCode");

			ProcessCategory processCategory = new ProcessCategory();
			processCategory.setprocessCategoryCode(processCategoryCode);
			processCategory.setProcessCategoryName(processCategoryName);
			processCategory.setBusinessTypeCode(businessTypeCode);
			processCategory.setBusinessFunctionType(businessFunctionType);
			processCategory.setRunningDivCode(runningDivCode);
			processCategory.setProcessTemplateCode(processTemplateCode);
			this.getProcessCategoryManager().storeProcessCategory(processCategory);

			AjaxUtil.successWrite(response);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			AjaxUtil.failWrite(response);
		}
	}

	public void deleteProcessCategory(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			String processCategoryCode = ServletRequestUtils.getStringParameter(request, "processCategoryCode", "");

			this.getProcessCategoryManager().deleteProcessCategory(processCategoryCode);
			AjaxUtil.successWrite(response);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			AjaxUtil.failWrite(response);
		}
	}

	/**
	 * @return the processCategoryManager
	 */
	public ProcessCategoryManager getProcessCategoryManager() {
		return processCategoryManager;
	}

	/**
	 * @param processCategoryManager the processCategoryManager to set
	 */
	public void setProcessCategoryManager(ProcessCategoryManager processCategoryManager) {
		this.processCategoryManager = processCategoryManager;
	}

	/**
	 * @return the commonCodeManager
	 */
	public CommonCodeManager getCommonCodeManager() {
		return commonCodeManager;
	}

	/**
	 * @param commonCodeManager the commonCodeManager to set
	 */
	public void setCommonCodeManager(CommonCodeManager commonCodeManager) {
		this.commonCodeManager = commonCodeManager;
	}

	/**
	 * @return the orgUnitManager
	 */
	public IOrgUnitManager getOrgUnitManager() {
		return orgUnitManager;
	}

	/**
	 * @param orgUnitManager the orgUnitManager to set
	 */
	public void setOrgUnitManager(IOrgUnitManager orgUnitManager) {
		this.orgUnitManager = orgUnitManager;
	}

	/**
	 * @return the processTemplateManager
	 */
	public ProcessTemplateManager getProcessTemplateManager() {
		return processTemplateManager;
	}

	/**
	 * @param processTemplateManager the processTemplateManager to set
	 */
	public void setProcessTemplateManager(ProcessTemplateManager processTemplateManager) {
		this.processTemplateManager = processTemplateManager;
	}

}
