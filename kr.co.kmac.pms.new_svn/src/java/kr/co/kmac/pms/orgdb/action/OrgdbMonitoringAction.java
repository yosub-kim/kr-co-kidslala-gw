package kr.co.kmac.pms.orgdb.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.kmac.pms.common.ajax.AjaxUtil;
import kr.co.kmac.pms.common.code.exception.CodeException;
import kr.co.kmac.pms.common.form.ResponseForm;
import kr.co.kmac.pms.orgdb.exception.OrgdbException;
import kr.co.kmac.pms.orgdb.manager.OrgdbManager;
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
 * @struts.action name="orgdbMonitoringAction" path="/action/OrgdbMonitoringAction" parameter="mode" scope="request"
 * @struts.action-forward name="orgdbMonitoringList"			path="/orgdb/orgdbMonitoringList.jsp"		redirect="false"
 * @struts.action-forward name="orgdbMonitoringDetailList"	path="/orgdb/orgdbMonitoringDetailList.jsp"	redirect="false"
 */
public class OrgdbMonitoringAction extends DispatchActionSupport {
	private static final Log logger = LogFactory.getLog(OrgdbMonitoringAction.class);
	private static final int CATEGORY_CUSTOMER = 1;
	private static final int CATEGORY_EXPERTPOOL = 2;
	private static final int CATEGORY_PROJECT = 3;
	private OrgdbManager orgdbManager;

	/**
	 * @return Returns the orgdbManager.
	 */
	public OrgdbManager getOrgdbManager() {
		return orgdbManager;
	}

	/**
	 * @param orgdbManager The orgdbManager to set.
	 */
	public void setOrgdbManager(OrgdbManager orgdbManager) {
		this.orgdbManager = orgdbManager;
	}

	public ActionForward selectList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String pg			= ServletRequestUtils.getStringParameter(request, "pg"			, "1");
		String pageSize		= ServletRequestUtils.getStringParameter(request, "pageSize"	, "15");
		String category		= ServletRequestUtils.getStringParameter(request, "category"	, "");
		String writerName	= ServletRequestUtils.getStringParameter(request, "writerName"	, "");
		String fromDate		= ServletRequestUtils.getStringParameter(request, "fromDate"	, "");
		String toDate		= ServletRequestUtils.getStringParameter(request, "toDate"		, "");

		WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
		ValueListHandler vlh = (ValueListHandler) wc.getBean("orgdbMonitoringListBean", ValueListHandler.class);

		Map<String, String> filters = new HashMap<String, String>();
		filters.put(ValueListInfo.PAGING_NUMBER_PER, pageSize);
		filters.put(ValueListInfo.PAGING_PAGE, pg);
		filters.put("category"		, category);
		filters.put("writerName"	, "%" + writerName + "%");
		filters.put("fromDate"		, fromDate);
		filters.put("toDate"		, toDate);

		try {
			ValueListInfo info = new ValueListInfo(filters);
			request.setAttribute("result", vlh.getValueList("getOrgdbMonitoringList", info));
			
			Map<String, String> pageData = new HashMap<String, String>();
			pageData.put("pg"			, pg);
			pageData.put("pageSize"		, pageSize);
			pageData.put("category"		, category);
			pageData.put("writerName"	, writerName);
			pageData.put("fromDate"		, fromDate);
			pageData.put("toDate"		, toDate);
			
			request.setAttribute("pageData", pageData);
			
		} catch (OrgdbException e) {
			logger.error(e.getMessage(), e);
		}
		return mapping.findForward("orgdbMonitoringList");
	}

	public ActionForward selectMonitoringDetailList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String writerSsn	= ServletRequestUtils.getStringParameter(request, "writerSsn"	, "");
		String category		= ServletRequestUtils.getStringParameter(request, "category"	, "");

		WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
		ValueListHandler vlh = (ValueListHandler) wc.getBean("orgdbMonitoringListBean", ValueListHandler.class);

		Map<String, String> filters = new HashMap<String, String>();
		filters.put(ValueListInfo.PAGING_NUMBER_PER, String.valueOf(Integer.MAX_VALUE));
		filters.put(ValueListInfo.PAGING_PAGE, "1");
		filters.put("writerSsn", writerSsn);

		try {
			ValueListInfo info = new ValueListInfo(filters);
			switch (Integer.parseInt(category)) {
				case CATEGORY_CUSTOMER :
					request.setAttribute("result", vlh.getValueList("getOrgdbCustomerInfoList", info));
					request.setAttribute("title", "제목");
					break;
				case CATEGORY_EXPERTPOOL :
					request.setAttribute("result", vlh.getValueList("getOrgdbExpertCompanyList", info));
					request.setAttribute("title", "성명");
					break;
				case CATEGORY_PROJECT :
					request.setAttribute("result", vlh.getValueList("getOrgdbCoCompanyList", info));
					request.setAttribute("title", "프로젝트명");
					break;
			}
			
			request.setAttribute("categoryCode", category);
		} catch (OrgdbException e) {
			logger.error(e.getMessage(), e);
		}
		return mapping.findForward("orgdbMonitoringDetailList");
	}
	
	public void updateOrgdbDetail(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		String key1		= ServletRequestUtils.getStringParameter(request, "key1"		, "");
		String key2		= ServletRequestUtils.getStringParameter(request, "key2"		, "");
		String orgCode	= ServletRequestUtils.getStringParameter(request, "orgCode"		, "");
		String orgName	= ServletRequestUtils.getStringParameter(request, "orgName"		, "");
		String category	= ServletRequestUtils.getStringParameter(request, "category"	, "");

		try {
			switch (Integer.parseInt(category)) {
				case CATEGORY_CUSTOMER :
					getOrgdbManager().customerOrgdbUpdate(orgCode, orgName, key1);
					break;
				case CATEGORY_EXPERTPOOL :
					getOrgdbManager().expertOrgdbUpdate(orgCode, orgName, key1);
					break;
				case CATEGORY_PROJECT :
					getOrgdbManager().projectOrgdbUpdate(orgCode, orgName, key1, key2);
					break;
			}
			//JSONWriter writer = new JSONWriter();
			map.put("result", true);
			map.put("resultMsg"		, "save success");
			
			AjaxUtil.successWrite(response, map);
		} catch (CodeException e) {
			map.put("result", false);
			map.put("resultMsg", "save fail");
			map.put("ErrMsg", e);
			AjaxUtil.failWrite(response, map);
		}
	}
}
