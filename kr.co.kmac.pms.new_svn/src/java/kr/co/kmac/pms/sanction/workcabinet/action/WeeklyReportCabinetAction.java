package kr.co.kmac.pms.sanction.workcabinet.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.kmac.pms.common.ajax.AjaxUtil;
import kr.co.kmac.pms.common.code.action.CommonCodeRetrieveAction;
import kr.co.kmac.pms.common.util.SessionUtils;
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
 * @struts.action name="weeklyReportCabinetAction" 				path="/action/WeeklyReportCabinetAction"						parameter="mode"	scope="request"
 * @struts.action-forward name="weeklyReportList" 				path="/sanction/workCabinet/weeklyReportList.jsp"				redirect="false"
 * @struts.action-forward name="mobileProjectReportList"		path="/m/web/wreport/wreportList.jsp"							redirect="false"
 */
public class WeeklyReportCabinetAction extends DispatchActionSupport {

	private static final Log logger = LogFactory.getLog(CommonCodeRetrieveAction.class);

	public void countMyWeeklyReport(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();
		WebApplicationContext wc;
		try {
			wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh = (ValueListHandler) wc.getBean("workCabinet", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();
			Map<String, String> filters = new HashMap<String, String>();
			filters.put(ValueListInfo.PAGING_PAGE, "1");
			filters.put(ValueListInfo.PAGING_NUMBER_PER, String.valueOf(Integer.MAX_VALUE - 1));
			filters.put("assigneeId", SessionUtils.getUsername(request));
			info.setFilters(filters);

			ValueList valueList = vlh.getValueList("weeklyReportWorkListCount", info);
			if (valueList.getList().size() > 99) {
				map.put("wreportCount", "99+");
			} else {
				map.put("wreportCount", String.valueOf(valueList.getList().size()));
			}
			AjaxUtil.successWrite(response, map);

		} catch (Exception e) {
			AjaxUtil.failWrite(response);
			logger.error(e.getMessage(), e);
		}
	}

	// �� ������
		public ActionForward getMyWeeklyReportList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			WebApplicationContext wc;
			String pageSize = ServletRequestUtils.getStringParameter(request, "pageSize", "10");
			String pageNo = ServletRequestUtils.getStringParameter(request, "pageNo", "1");
			String projectName = ServletRequestUtils.getStringParameter(request, "projectName", "");
			String writerName = ServletRequestUtils.getStringParameter(request, "writerName", "");
			String intMonth = ServletRequestUtils.getStringParameter(request, "intMonth", "");
			String intWeekOfMonth = ServletRequestUtils.getStringParameter(request, "intWeekOfMonth", "");

			try {
				wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
				ValueListHandler vlh = (ValueListHandler) wc.getBean("workCabinet", ValueListHandler.class);
				ValueListInfo info = new ValueListInfo();
				Map<String, String> filters = new HashMap<String, String>();
				filters.put(ValueListInfo.PAGING_PAGE, pageNo);
				filters.put(ValueListInfo.PAGING_NUMBER_PER, pageSize.equals("-1") ? String.valueOf(Integer.MAX_VALUE - 1) : pageSize);
				filters.put("assigneeId", SessionUtils.getUsername(request));
				if (!projectName.equals("")) {
					filters.put("projectName", "%" + projectName + "%");
				}
				if (!writerName.equals("")) {
					filters.put("draftUserName", "%" + writerName + "%");
				}
				if(!intMonth.equals("")){
					filters.put("intMonth", "%" + intMonth + "%");
				}
				if(!intWeekOfMonth.equals("")){
					filters.put("intWeekOfMonth", "%" + intWeekOfMonth + "%");
				}
				info.setFilters(filters);

				ValueList valueList = vlh.getValueList("weeklyReportWorkList", info);
				request.setAttribute("list", valueList);
				request.setAttribute("projectName", projectName);
				request.setAttribute("writerName", writerName);
				request.setAttribute("intMonth", intMonth);
				request.setAttribute("intWeekOfMonth", intWeekOfMonth);

			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
			return mapping.findForward("weeklyReportList");
		}
		
		
//	// �� ������
//	public void getMyWeeklyReportListForMobile(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
//			throws Exception {
//		int pg = ServletRequestUtils.getIntParameter(request, "pg", 1);
//		String projectName = ServletRequestUtils.getStringParameter(request, "projectName", "");
//
//		HashMap<String, Object> map = new HashMap<String, Object>();
//		try {
//			WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
//			
//			ValueListHandler vlh = (ValueListHandler) wc.getBean("workCabinet", ValueListHandler.class);
//			ValueListInfo info = new ValueListInfo();
//			Map<String, String> filters = new HashMap<String, String>();
//			filters.put(ValueListInfo.PAGING_PAGE, String.valueOf(pg));
//			filters.put(ValueListInfo.PAGING_NUMBER_PER, String.valueOf(10));
//			filters.put("assigneeId", SessionUtils.getUsername(request));
//			if (!projectName.equals("")) {
//				filters.put("projectName", "%" + projectName + "%");
//			}
//			info.setFilters(filters);
//			ValueList valueList = vlh.getValueList("weeklyReportWorkList", info);
//
//			map.put("pagingPage", String.valueOf(pg));
//			map.put("pagingEntries",
//					pg == valueList.getValueListInfo().getTotalNumberOfPages() ? String.valueOf(valueList.getValueListInfo()
//							.getTotalNumberOfEntries()) : String.valueOf(pg * 10));
//			map.put("totalNumberOfEntries", String.valueOf(valueList.getValueListInfo().getTotalNumberOfEntries()));
//			map.put("totalNumberOfPages", String.valueOf(valueList.getValueListInfo().getTotalNumberOfPages()));
//			map.put("wreportList", valueList.getList());
//			map.put("projectName", projectName);
//			
//			AjaxUtil.successWrite(response, map);
//		} catch (Exception e) {
//			logger.error(e.getMessage(), e);
//			AjaxUtil.failWrite(response, map);
//		}
//	}

}
