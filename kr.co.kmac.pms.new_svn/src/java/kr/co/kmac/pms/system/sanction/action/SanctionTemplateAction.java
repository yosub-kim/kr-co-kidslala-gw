package kr.co.kmac.pms.system.sanction.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.kmac.pms.common.ajax.AjaxUtil;
import kr.co.kmac.pms.common.util.SessionUtils;
import kr.co.kmac.pms.system.sanction.data.SanctionTemplate;
import kr.co.kmac.pms.system.sanction.form.SanctionTemplateForm;
import kr.co.kmac.pms.system.sanction.manager.SanctionTemplateManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.struts.DispatchActionSupport;

/**
 * 시스템 관리 - 전자결재 템플릿 관리
 */
/**
 * @struts.action name="sanctionTemplateAction" path="/action/SanctionTemplateAction" parameter="mode" scope="request"
 * @struts.action-forward name="sanctionTemplateList" path="/system/sanction/sanctionTemplateList.jsp" redirect="false"
 * @struts.action-forward name="sanctionTemplateForm" path="/system/sanction/sanctionTemplateForm.jsp" redirect="false"
 */
public class SanctionTemplateAction extends DispatchActionSupport {

	private static final Log logger = LogFactory.getLog(SanctionTemplateAction.class);
	private SanctionTemplateManager sanctionTemplateManager;

	public SanctionTemplateManager getSanctionTemplateManager() {
		return sanctionTemplateManager;
	}

	public void setSanctionTemplateManager(SanctionTemplateManager sanctionTemplateManager) {
		this.sanctionTemplateManager = sanctionTemplateManager;
	}

	public ActionForward getSanctionTemplateList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		List<SanctionTemplate> list = this.getSanctionTemplateManager().getSanctionTemplate();
		request.setAttribute("list", list);
		request.setAttribute("listSize", list.size());
		return mapping.findForward("sanctionTemplateList");
	}

	public ActionForward getSanctionTemplate(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String approvalType = ServletRequestUtils.getRequiredStringParameter(request, "approvalType");
		SanctionTemplate template = this.getSanctionTemplateManager().getSanctionTemplate(approvalType);
		request.setAttribute("template", template);
		return mapping.findForward("sanctionTemplateForm");
	}

	public ActionForward loadFormSanctionTemplate(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String approvalType = ServletRequestUtils.getStringParameter(request, "approvalType", "");
		SanctionTemplate template = null;
		if (!approvalType.equals("")) {
			template = this.getSanctionTemplateManager().getSanctionTemplate(approvalType, "admin");
		} else {
			template = new SanctionTemplate();
		}
		request.setAttribute("template", template);
		return mapping.findForward("sanctionTemplateForm");
	}

	public void storeSanctionTemplate(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			SanctionTemplate sanctionTemplate = SanctionTemplate.valueOf((SanctionTemplateForm) form);

			if (this.getSanctionTemplateManager().getSanctionTemplate(sanctionTemplate.getApprovalType()) == null) {
				sanctionTemplate.setCreateUser(SessionUtils.getUsername(request));
				this.getSanctionTemplateManager().createSanctionTemplate(sanctionTemplate);
			} else {
				sanctionTemplate.setUpdateUser(SessionUtils.getUsername(request));
				this.getSanctionTemplateManager().updateSanctionTemplate(sanctionTemplate);
			}

			AjaxUtil.successWrite(response);
		} catch (Exception e) {
			AjaxUtil.failWrite(response);
			logger.error(e.getMessage(), e);
		}
	}

	public void deleteSanctionTemplate(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String approvalType = ServletRequestUtils.getRequiredStringParameter(request, "approvalType");
		try {
			this.getSanctionTemplateManager().deleteSanctionTemplate(approvalType);
			AjaxUtil.successWrite(response);
		} catch (Exception e) {
			AjaxUtil.failWrite(response);
			logger.error(e.getMessage(), e);
		}
	}
}
