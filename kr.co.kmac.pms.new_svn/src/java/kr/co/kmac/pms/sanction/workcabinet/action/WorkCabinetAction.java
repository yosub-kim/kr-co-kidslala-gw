package kr.co.kmac.pms.sanction.workcabinet.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.kmac.pms.common.ajax.AjaxUtil;
import kr.co.kmac.pms.common.code.action.CommonCodeRetrieveAction;
import kr.co.kmac.pms.common.util.SessionUtils;
import kr.co.kmac.pms.common.util.StringUtil;
import kr.co.kmac.pms.common.worklist.manager.WorklistManager;
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
 * 전자결재 업무함
 */
/**
 * @struts.action name="workCabinetAction" path="/action/WorkCabinetAction" parameter="mode" scope="request"
 * @struts.action-forward name="myWork" path="/sanction/workCabinet/myWorkList.jsp" redirect="false"
 * @struts.action-forward name="myRefWorkList" path="/sanction/workCabinet/myRefWorkList.jsp" redirect="false"
 * @struts.action-forward name="myWorkMobile" path="/m/web/sanction/sanctionCabinetList.jsp" redirect="false"
 * @struts.action-forward name="sanctionTypeList" path="/m/web/sanction/sanctionTypeList.jsp" redirect="false"
 */
public class WorkCabinetAction extends DispatchActionSupport {

	private static final Log logger = LogFactory.getLog(CommonCodeRetrieveAction.class);
	private WorklistManager worklistManager;
	private SanctionTemplateManager sanctionTemplateManager;

	// 내 업무함
	public void countMyWorkList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
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

			ValueList valueList = vlh.getValueList("myIngWorkListCount", info);
			if (valueList.getList().size() > 99) {
				map.put("workCount", "99+");
			} else {
				map.put("workCount", String.valueOf(valueList.getList().size()));
			}
			AjaxUtil.successWrite(response, map);

		} catch (Exception e) {
			AjaxUtil.failWrite(response);
			logger.error(e.getMessage(), e);
		}
	}

	// 내 업무함
	public ActionForward getMyWorkList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		WebApplicationContext wc;
		String pageSize = StringUtil.isNull(request.getParameter("pageSize"), "10");
		String pageNo = StringUtil.isNull(request.getParameter("pageNo"), "1");
		String keyword = ServletRequestUtils.getStringParameter(request, "keyword", null);

		try {
			wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh = (ValueListHandler) wc.getBean("workCabinet", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();
			Map<String, String> filters = new HashMap<String, String>();
			if (keyword != null) {
				filters.put("title", "%" + keyword + "%");
			}
			filters.put(ValueListInfo.PAGING_PAGE, pageNo);
			filters.put(ValueListInfo.PAGING_NUMBER_PER, pageSize);
			filters.put("assigneeId", SessionUtils.getUsername(request));
			info.setFilters(filters);

			ValueList valueList = vlh.getValueList("myIngWorkList", info);
			request.setAttribute("keyword", keyword);
			request.setAttribute("list", valueList);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return mapping.findForward("myWork");
	}
	
	public ActionForward getMyWorkListForMobile(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		WebApplicationContext wc;
		int pageSize = ServletRequestUtils.getIntParameter(request, "pageSize", 10);
		String pageNo = StringUtil.isNull(request.getParameter("pageNo"), "1");
		String keyword = ServletRequestUtils.getStringParameter(request, "keyword", null);

		try {
			wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh = (ValueListHandler) wc.getBean("workCabinet", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();
			Map<String, String> filters = new HashMap<String, String>();
			if (keyword != null) {
				filters.put("title", "%" + keyword + "%");
			}
			filters.put(ValueListInfo.PAGING_PAGE, pageNo);
			filters.put(ValueListInfo.PAGING_NUMBER_PER, String.valueOf(pageSize == -1 ? Integer.MAX_VALUE - 1 : pageSize));
			filters.put("assigneeId", SessionUtils.getUsername(request));
			info.setFilters(filters);

			ValueList valueList = vlh.getValueList("myIngWorkListForMobile", info);
			request.setAttribute("keyword", keyword);
			request.setAttribute("list", valueList);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return mapping.findForward("myWorkMobile");
	}

	// 내 업무함
	/*public void getMyWorkListForMobile(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pg = ServletRequestUtils.getIntParameter(request, "pg", 1);
		String title = ServletRequestUtils.getStringParameter(request, "title", "");

		HashMap<String, Object> map = new HashMap<String, Object>();
		try {
			WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh = (ValueListHandler) wc.getBean("workCabinet", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();
			Map<String, String> filters = new HashMap<String, String>();
			if (!title.equals("")) {
				filters.put("title", "%" + title + "%");
			}
			filters.put(ValueListInfo.PAGING_PAGE, String.valueOf(pg));
			filters.put(ValueListInfo.PAGING_NUMBER_PER, String.valueOf(10));
			filters.put("assigneeId", SessionUtils.getUsername(request));
			info.setFilters(filters);

			ValueList valueList = vlh.getValueList("myIngWorkListForMobile", info);

			map.put("pagingPage", String.valueOf(pg));
			map.put("pagingEntries",
					pg == valueList.getValueListInfo().getTotalNumberOfPages() ? String.valueOf(valueList.getValueListInfo()
							.getTotalNumberOfEntries()) : String.valueOf(pg * 10));
			map.put("totalNumberOfEntries", String.valueOf(valueList.getValueListInfo().getTotalNumberOfEntries()));
			map.put("totalNumberOfPages", String.valueOf(valueList.getValueListInfo().getTotalNumberOfPages()));
			map.put("sanctionCabinetList", valueList.getList());
			map.put("title", title);

			AjaxUtil.successWrite(response, map);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			AjaxUtil.failWrite(response);
		}
	}*/

	public ActionForward getSanctionTypeForMobile(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setAttribute("sanctionTemplateList", this.getSanctionTemplateManager().getSanctionTemplate(true, true));

		return mapping.findForward("sanctionTypeList");
	}

	// 내 참조 업무함
	public ActionForward getMyRefWorkList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		WebApplicationContext wc;
		String pageSize = StringUtil.isNull(request.getParameter("pageSize"), "10");
		String pageNo = StringUtil.isNull(request.getParameter("pageNo"), "1");
		String keyword = ServletRequestUtils.getStringParameter(request, "keyword", null);

		try {
			wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh = (ValueListHandler) wc.getBean("workCabinet", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();
			Map<String, String> filters = new HashMap<String, String>();
			if (keyword != null) {
				filters.put("title", "%" + keyword + "%");
			}
			filters.put(ValueListInfo.PAGING_PAGE, pageNo);
			filters.put(ValueListInfo.PAGING_NUMBER_PER, pageSize);
			filters.put("assigneeId", SessionUtils.getUsername(request));
			info.setFilters(filters);

			ValueList valueList = vlh.getValueList("myRefWorkList", info);
			request.setAttribute("keyword", keyword);
			request.setAttribute("list", valueList);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return mapping.findForward("myRefWorkList");
	}
	
	// 내 참조 업무함
		public ActionForward getMyRefWorkListForMobile(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			WebApplicationContext wc;
			String pageSize = StringUtil.isNull(request.getParameter("pageSize"), "10");
			String pageNo = StringUtil.isNull(request.getParameter("pageNo"), "1");
			String keyword = ServletRequestUtils.getStringParameter(request, "keyword", null);

			try {
				wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
				ValueListHandler vlh = (ValueListHandler) wc.getBean("workCabinet", ValueListHandler.class);
				ValueListInfo info = new ValueListInfo();
				Map<String, String> filters = new HashMap<String, String>();
				if (keyword != null) {
					filters.put("title", "%" + keyword + "%");
				}
				filters.put(ValueListInfo.PAGING_PAGE, pageNo);
				filters.put(ValueListInfo.PAGING_NUMBER_PER, pageSize);
				filters.put("assigneeId", SessionUtils.getUsername(request));
				info.setFilters(filters);

				ValueList valueList = vlh.getValueList("myRefWorkListForMobile", info);
				request.setAttribute("keyword", keyword);
				request.setAttribute("list", valueList);

			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}

			return mapping.findForward("myRefWorkListForMobile");
		}

	public void getWorkType(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		String workTypeId = ServletRequestUtils.getRequiredStringParameter(request, "workTypeId");
		System.out.println(workTypeId);
		try {
			if (workTypeId.startsWith("ref_")) {
				workTypeId = workTypeId.substring(4);
			}
			map.put("workType", this.getWorklistManager().getWorkType(workTypeId));
			AjaxUtil.successWrite(response, map);
		} catch (Exception e) {
			AjaxUtil.failWrite(response);
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * @return the worklistManager
	 */
	public WorklistManager getWorklistManager() {
		return worklistManager;
	}

	/**
	 * @param worklistManager the worklistManager to set
	 */
	public void setWorklistManager(WorklistManager worklistManager) {
		this.worklistManager = worklistManager;
	}

	public SanctionTemplateManager getSanctionTemplateManager() {
		return sanctionTemplateManager;
	}

	public void setSanctionTemplateManager(SanctionTemplateManager sanctionTemplateManager) {
		this.sanctionTemplateManager = sanctionTemplateManager;
	}

}
