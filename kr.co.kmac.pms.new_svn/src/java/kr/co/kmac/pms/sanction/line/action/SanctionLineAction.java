package kr.co.kmac.pms.sanction.line.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.kmac.pms.common.ajax.AjaxUtil;
import kr.co.kmac.pms.common.util.SessionUtils;
import kr.co.kmac.pms.sanction.line.form.SanctionLineForm;
import kr.co.kmac.pms.sanction.line.manager.SanctionLineManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.struts.DispatchActionSupport;

/**
 * @struts.action name="SanctionLineAction" path="/action/SanctionLineAction" parameter="mode" scope="request"
 * 
 * @struts.action-forward name="sanctionLineManagement" path="/sanction/line/sanctionLineManagement.jsp" redirect="false"
 */
public class SanctionLineAction extends DispatchActionSupport {

	private static final Log logger = LogFactory.getLog(SanctionLineAction.class);
	private SanctionLineManager sanctionLineManager;

	public SanctionLineManager getSanctionLineManager() {
		return sanctionLineManager;
	}

	public void setSanctionLineManager(SanctionLineManager sanctionLineManager) {
		this.sanctionLineManager = sanctionLineManager;
	}

	public ActionForward getSanctionLine(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setAttribute("sanctionLine", this.getSanctionLineManager().selectSanctionLine(SessionUtils.getUsername(request)));
		return mapping.findForward("sanctionLineManagement");
	}

	public void storeSanctionList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		SanctionLineForm sanctionLineForm = (SanctionLineForm) form;
		try {
			sanctionLineForm.setId(SessionUtils.getUsername(request));
			this.getSanctionLineManager().insertSanctionLine(sanctionLineForm);
			AjaxUtil.successWrite(response);
		} catch (Exception e) {
			AjaxUtil.failWrite(response);
			logger.error(e.getMessage(), e);
		}
	}
}
