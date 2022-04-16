package kr.co.kmac.pms.sanction.workcabinet.action;

import java.io.Writer;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.kmac.pms.common.ajax.AjaxUtil;
import kr.co.kmac.pms.common.ajax.JSONWriter;
import kr.co.kmac.pms.common.code.action.CommonCodeRetrieveAction;
import kr.co.kmac.pms.expertpool.manager.ExpertPoolManager;
import kr.co.kmac.pms.schedule.manager.ScheduleManager;
import net.mlw.vlh.ValueList;
import net.mlw.vlh.ValueListHandler;
import net.mlw.vlh.ValueListInfo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.struts.DispatchActionSupport;

/**
 * 전자결재 업무함
 */
/**
 * @struts.action name="workInfoAction" path="/notauth/workInfoAction" parameter="mode" scope="request"
 */
public class WorkInfoAction extends DispatchActionSupport {

	private static final Log logger = LogFactory.getLog(CommonCodeRetrieveAction.class);
	private ScheduleManager scheduleManager;
	private ExpertPoolManager expertPoolManager;

	// 내 업무함
	public void countMyWorkList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String userId = ServletRequestUtils.getRequiredStringParameter(request, "userId");

		response.setContentType("text/html;charset=utf-8");
		Writer w = response.getWriter();
		String res = "";
		WebApplicationContext wc;

		try {
			wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh = (ValueListHandler) wc.getBean("workCabinet", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();
			Map<String, String> filters = new HashMap<String, String>();
			filters.put(ValueListInfo.PAGING_PAGE, "1");
			filters.put(ValueListInfo.PAGING_NUMBER_PER, String.valueOf(Integer.MAX_VALUE - 1));
			filters.put("assigneeId", this.getExpertPoolManager().retrieveUserId(userId).getSsn());
			info.setFilters(filters);

			ValueList valueList = vlh.getValueList("myIngWorkList", info);
			if (valueList.getList().size() > 99) {
				res = "99+";
			} else {
				res = String.valueOf(valueList.getList().size());
			}
			w.write(res);

		} catch (Exception e) {
			w.write(e.getMessage());
			logger.error(e.getMessage(), e);
		}
	}

	// 지도일지
	public void countMyProjectReport(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String userId = ServletRequestUtils.getRequiredStringParameter(request, "userId");

		response.setContentType("text/html;charset=utf-8");
		Writer w = response.getWriter();
		String res = "";
		WebApplicationContext wc;
		try {
			wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh = (ValueListHandler) wc.getBean("workCabinet", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();
			Map<String, String> filters = new HashMap<String, String>();
			filters.put(ValueListInfo.PAGING_PAGE, "1");
			filters.put(ValueListInfo.PAGING_NUMBER_PER, String.valueOf(Integer.MAX_VALUE - 1));
			filters.put("assigneeId", this.getExpertPoolManager().retrieveUserId(userId).getSsn());
			info.setFilters(filters);

			ValueList valueList = vlh.getValueList("projectReportWorkList", info);
			if (valueList.getList().size() > 99) {
				res = "99+";
			} else {
				res = String.valueOf(valueList.getList().size());
			}
			w.write(res);

		} catch (Exception e) {
			w.write(e.getMessage());
			logger.error(e.getMessage(), e);
		}
	}

	// 새 일정
	public void countMySchedule(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String userId = ServletRequestUtils.getRequiredStringParameter(request, "userId");

		response.setContentType("text/html;charset=utf-8");
		Writer w = response.getWriter();
		String res = "";
		try {
			String ssn = this.getExpertPoolManager().retrieveUserId(userId).getSsn();
			String secretYN = "N";
			Calendar c = new GregorianCalendar();

			String nYear = Integer.toString(c.get(Calendar.YEAR));
			String nMonth = "0" + Integer.toString(c.get(Calendar.MONTH) + 1);
			nMonth = nMonth.substring(nMonth.length() - 2, nMonth.length());
			String nDay = "0" + Integer.toString(c.get(Calendar.DAY_OF_MONTH));
			nDay = nDay.substring(nDay.length() - 2, nDay.length());
			int projectCnt = scheduleManager.getProjectScheduleListByMonth(ssn, nYear, nMonth, nDay).size();
			int scheduleCnt = scheduleManager.getScheduleListByMonth(ssn, nYear, nMonth, nDay, secretYN).size();
			// logger.debug("갯수 : " + Integer.toString(scheduleList.size()));
			res = String.valueOf(projectCnt + scheduleCnt);
			w.write(res);
			
		} catch (Exception e) {
			w.write(e.getMessage());
			logger.error(e.getMessage(), e);
		}
	}

	public ScheduleManager getScheduleManager() {
		return scheduleManager;
	}

	public void setScheduleManager(ScheduleManager scheduleManager) {
		this.scheduleManager = scheduleManager;
	}

	public ExpertPoolManager getExpertPoolManager() {
		return expertPoolManager;
	}

	public void setExpertPoolManager(ExpertPoolManager expertPoolManager) {
		this.expertPoolManager = expertPoolManager;
	}

}
