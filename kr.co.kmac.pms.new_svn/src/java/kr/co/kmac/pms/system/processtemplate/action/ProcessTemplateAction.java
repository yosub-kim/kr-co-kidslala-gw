package kr.co.kmac.pms.system.processtemplate.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.kmac.pms.common.ajax.AjaxUtil;
import kr.co.kmac.pms.common.code.data.CodeEntity;
import kr.co.kmac.pms.common.code.manager.CommonCodeManager;
import kr.co.kmac.pms.system.processtemplate.data.ProcessTemplate;
import kr.co.kmac.pms.system.processtemplate.data.ProcessTemplateAttach;
import kr.co.kmac.pms.system.processtemplate.data.ProcessTemplateDetail;
import kr.co.kmac.pms.system.processtemplate.manager.ProcessTemplateManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.struts.DispatchActionSupport;

/**
 * @struts.action name="processTemplateAction" path="/action/ProcessTemplateAction" parameter="mode" scope="request"
 * @struts.action-forward name="processTemplateList" path="/system/process/processTemplateList.jsp" redirect="false"
 * @struts.action-forward name="processTemplateDetail" path="/system/process/processTemplateDetail.jsp" redirect="false"
 * @struts.action-forward name="processTemplateAttach" path="/system/process/processTemplateAttach.jsp" redirect="false"
 */
public class ProcessTemplateAction extends DispatchActionSupport {
	private static final Log logger = LogFactory.getLog(ProcessTemplateAction.class);
	private CommonCodeManager commonCodeManager;
	private ProcessTemplateManager processTemplateManager;

	public ProcessTemplateManager getProcessTemplateManager() {
		return processTemplateManager;
	}

	public void setProcessTemplateManager(ProcessTemplateManager processTemplateManager) {
		this.processTemplateManager = processTemplateManager;
	}

	public ActionForward getProcessTemplateList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		List<ProcessTemplate> list = this.getProcessTemplateManager().getProcessTemplateList(null);
		request.setAttribute("list", list);
		request.setAttribute("listSize", list.size());
		return mapping.findForward("processTemplateList");
	}

	public void searchProcessTemplateList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();

		String processTemplateName = ServletRequestUtils.getStringParameter(request, "processTemplateName", null);
		try {
			List<ProcessTemplate> list = this.getProcessTemplateManager().getProcessTemplateList(processTemplateName);
			map.put("processTemplateList", list);
			AjaxUtil.successWrite(response, map);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			AjaxUtil.failWrite(response);
		}
	}

	public ActionForward getProcessTemplateDetail(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String processTemplateCode = ServletRequestUtils.getRequiredStringParameter(request, "processTemplateCode");

		ProcessTemplate processTemplate = this.getProcessTemplateManager().getProcessTemplate(processTemplateCode);
		List<ProcessTemplateDetail> processTemplateDetailList = this.getProcessTemplateManager().getProcessTemplateDetail(processTemplateCode);

		request.setAttribute("processTemplate", processTemplate);
		request.setAttribute("processTemplateDetailList", processTemplateDetailList);
		return mapping.findForward("processTemplateDetail");
	}

	public ActionForward getProcessTemplateAttach(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String processTemplateCode = ServletRequestUtils.getRequiredStringParameter(request, "processTemplateCode");
		String workSeq = ServletRequestUtils.getRequiredStringParameter(request, "workSeq");

		ProcessTemplateDetail processTemplateDetail = this.getProcessTemplateManager().getProcessTemplateDetail(processTemplateCode, workSeq);
		List<ProcessTemplateAttach> processTemplateAttachList = this.getProcessTemplateManager().getProcessTemplateAttach(processTemplateCode,
				workSeq);

		List<CodeEntity> bizTypeCodeList = this.getCommonCodeManager().getCodeEntityList("BUSINESS_TYPE_CODE");
		List<CodeEntity> attachTypeCodeList = this.getCommonCodeManager().getCodeEntityList("OUTPUT_TYPE_CODE");

		request.setAttribute("bizTypeCodeList", bizTypeCodeList);
		request.setAttribute("attachTypeCodeList", attachTypeCodeList);
		request.setAttribute("processTemplateDetail", processTemplateDetail);
		request.setAttribute("processTemplateAttachList", processTemplateAttachList);
		return mapping.findForward("processTemplateAttach");
	}

	public void createProcessTemplate(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			String processTemplateCode = ServletRequestUtils.getRequiredStringParameter(request, "processTemplateCode");
			String processTemplateName = ServletRequestUtils.getRequiredStringParameter(request, "processTemplateName");

			ProcessTemplate processTemplate = new ProcessTemplate();
			processTemplate.setProcessTemplateCode(processTemplateCode);
			processTemplate.setProcessTemplateName(processTemplateName);
			this.getProcessTemplateManager().createProcessTemplate(processTemplate);

			AjaxUtil.successWrite(response);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			AjaxUtil.failWrite(response);
		}
	}

	public void storeProcessTemplateDetail(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			String processTemplateCode = ServletRequestUtils.getRequiredStringParameter(request, "processTemplateCode");
			String processTemplateName = ServletRequestUtils.getRequiredStringParameter(request, "processTemplateName");

			String[] workSeq = ServletRequestUtils.getStringParameters(request, "workSeq");
			String[] level = ServletRequestUtils.getStringParameters(request, "level");
			String[] parentWorkSeq = ServletRequestUtils.getStringParameters(request, "parentWorkSeq");
			String[] activityName = ServletRequestUtils.getStringParameters(request, "activityName");
			String[] workType = ServletRequestUtils.getStringParameters(request, "workType");
			String[] editable = ServletRequestUtils.getStringParameters(request, "editable");

			String[] necessary = ServletRequestUtils.getStringParameters(request, "necessary");
			List<ProcessTemplateDetail> processTemplateDetailList = new ArrayList<ProcessTemplateDetail>();
			for (int i = 0; i < workSeq.length; i++) {
				processTemplateDetailList.add(new ProcessTemplateDetail(processTemplateCode, processTemplateName, Integer.parseInt(workSeq[i]),
						activityName[i], Integer.parseInt(level[i]), Integer.parseInt(parentWorkSeq[i]), Integer.parseInt(workType[i]), Boolean
								.parseBoolean(editable[i]), Boolean.parseBoolean(necessary[i])));
			}
			this.getProcessTemplateManager().createProcessTemplateDetail(processTemplateCode, processTemplateDetailList);

			AjaxUtil.successWrite(response);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			AjaxUtil.failWrite(response);
		}
	}

	public void storeProcessTemplateAttach(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			String processTemplateCode = ServletRequestUtils.getRequiredStringParameter(request, "processTemplateCode");
			int workSeq = ServletRequestUtils.getRequiredIntParameter(request, "workSeq");

			int[] attachSeq = ServletRequestUtils.getIntParameters(request, "attachSeq");
			String[] bizType = ServletRequestUtils.getStringParameters(request, "bizType");
			String[] outputType = ServletRequestUtils.getStringParameters(request, "outputType");
			String[] outputName = ServletRequestUtils.getStringParameters(request, "outputName");
			boolean[] necessary = ServletRequestUtils.getBooleanParameters(request, "necessary");

			List<ProcessTemplateAttach> processTemplateAttachList = new ArrayList<ProcessTemplateAttach>();
			for (int i = 0; i < attachSeq.length; i++) {
				processTemplateAttachList.add(new ProcessTemplateAttach(processTemplateCode, workSeq, attachSeq[i], outputName[i], outputType[i],
						bizType[i], necessary[i]));
			}
			this.getProcessTemplateManager().createProcessTemplateAttach(processTemplateCode, String.valueOf(workSeq), processTemplateAttachList);

			AjaxUtil.successWrite(response);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			AjaxUtil.failWrite(response);
		}
	}

	public void deleteProcessTemplateDetail(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			String processTemplateCode = ServletRequestUtils.getRequiredStringParameter(request, "processTemplateCode");
			this.getProcessTemplateManager().deleteProcessTemplateDetail(processTemplateCode, null);
			AjaxUtil.successWrite(response);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			AjaxUtil.failWrite(response);
		}
	}

	public void deleteProcessTemplateDetailEntity(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			String processTemplateCode = ServletRequestUtils.getRequiredStringParameter(request, "processTemplateCode");
			String workSeq = ServletRequestUtils.getRequiredStringParameter(request, "workSeq");
			this.getProcessTemplateManager().deleteProcessTemplateDetail(processTemplateCode, workSeq);
			AjaxUtil.successWrite(response);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			AjaxUtil.failWrite(response);
		}
	}

	public void deleteProcessTemplateAttach(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			String processTemplateCode = ServletRequestUtils.getRequiredStringParameter(request, "processTemplateCode");
			String workSeq = ServletRequestUtils.getRequiredStringParameter(request, "workSeq");
			this.getProcessTemplateManager().deleteProcessTemplateAttach(processTemplateCode, workSeq, null);
			AjaxUtil.successWrite(response);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			AjaxUtil.failWrite(response);
		}
	}

	public void deleteProcessTemplateDetailAttach(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			String processTemplateCode = ServletRequestUtils.getRequiredStringParameter(request, "processTemplateCode");
			String workSeq = ServletRequestUtils.getRequiredStringParameter(request, "workSeq");
			String attachSeq = ServletRequestUtils.getRequiredStringParameter(request, "attachSeq");
			this.getProcessTemplateManager().deleteProcessTemplateAttach(processTemplateCode, workSeq, attachSeq);
			AjaxUtil.successWrite(response);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			AjaxUtil.failWrite(response);
		}
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

}
