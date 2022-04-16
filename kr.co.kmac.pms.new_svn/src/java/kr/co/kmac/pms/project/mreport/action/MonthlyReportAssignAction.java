package kr.co.kmac.pms.project.mreport.action;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.kmac.pms.common.ajax.AjaxUtil;
import kr.co.kmac.pms.common.util.StringUtil;
import kr.co.kmac.pms.service.scheduler.batch.MonthlyReportScheduleService;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.struts.DispatchActionSupport;

/**
 * @struts.action name="monthlyReportAssignAction" path="/action/MonthlyReportAssignAction" parameter="mode" scope="request"
 */
public class MonthlyReportAssignAction extends DispatchActionSupport {

	private MonthlyReportScheduleService monthlyReportScheduleService;

	public MonthlyReportScheduleService getMonthlyReportScheduleService() {
		return monthlyReportScheduleService;
	}

	public void setMonthlyReportScheduleService(MonthlyReportScheduleService monthlyReportScheduleService) {
		this.monthlyReportScheduleService = monthlyReportScheduleService;
	}

	public void assigneMonthlyReport(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();
		String yyyymmdd = ServletRequestUtils.getRequiredStringParameter(request, "yyyymmdd");
		try {
			String resString = this.getMonthlyReportScheduleService().assignMonthlyReportUntilGivenDated(StringUtil.replace(yyyymmdd, "-", ""));
			map.put("resString", resString);

			AjaxUtil.successWrite(response, map);
		} catch (Exception e) {
			e.printStackTrace();
			AjaxUtil.failWrite(response);
		}
	}
}
