/**
 * @author yhyim
 *
 */
package kr.co.kmac.pms.system.emaillog.action;

import java.io.Serializable;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.kmac.pms.common.ajax.AjaxUtil;
import kr.co.kmac.pms.expertpool.exception.ExpertPoolException;
import kr.co.kmac.pms.system.emaillog.manager.SalaryInfoMailLogManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.struts.DispatchActionSupport;

/**
 * @struts.action name="EmailLogAction" path="/action/EmailLogAction" parameter="mode" scope="request"
 */
public class EmailLogAction extends DispatchActionSupport {
	private static final Log logger = LogFactory.getLog(EmailLogAction.class);
	private SalaryInfoMailLogManager salaryInfoMailLogManager;
	
	public void deleteSalaryInfoMailLog(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HashMap<String, Serializable> map = new HashMap<String, Serializable>();
		
		String emailSeq = ServletRequestUtils.getStringParameter(request, "emailSeq", "");
		
		try {
			getSalaryInfoMailLogManager().deleteSalaryInfoMailLog(emailSeq);
			
			map.put("result", true);
			map.put("resultMsg", "처리되었습니다.");
			AjaxUtil.successWrite(response, map);
			
		} catch (ExpertPoolException e) {
			
			logger.info(e.getMessage(), e);
			map.put("result", false);
			map.put("resultMsg", "처리도중 에러 발생");
			map.put("ErrMsg", e);
			AjaxUtil.failWrite(response, map);
		}
	}	
	
	
	public SalaryInfoMailLogManager getSalaryInfoMailLogManager() {
		return salaryInfoMailLogManager;
	}
	
	public void setSalaryInfoMailLogManager(SalaryInfoMailLogManager salaryInfoMailLogManager) {
		this.salaryInfoMailLogManager = salaryInfoMailLogManager;
	}
	
}	
