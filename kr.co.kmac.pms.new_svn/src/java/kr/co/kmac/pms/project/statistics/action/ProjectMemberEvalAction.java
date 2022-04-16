package kr.co.kmac.pms.project.statistics.action;

import java.net.URLDecoder;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.kmac.pms.common.ajax.AjaxUtil;
import kr.co.kmac.pms.common.code.exception.CodeException;
import kr.co.kmac.pms.common.util.DateTime;
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
 * @struts.action name="ProjectMemberEvalAction" path="/action/ProjectMemberEvalAction" parameter="mode" scope="request"
 * @struts.action-forward name="ProjectMemberEvalList" path="/project/statistics/ProjectMemberEvalList.jsp" redirect="false"
 * @struts.action-forward name="ProjectMemberEvalListDetail" path="/project/statistics/ProjectMemberEvalListDetail.jsp" redirect="false"
 */
public class ProjectMemberEvalAction extends DispatchActionSupport {

	private static final Log logger = LogFactory.getLog(ProjectMemberEvalAction.class);

	public ActionForward getValueProjectMemberEvalList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		try {
			// 인자가 없을경우 디폴트 페이지는 1번 건수는 15건
			String pagingPage = ServletRequestUtils.getStringParameter(request, "pg", "1");
			String pagingNumberPer = ServletRequestUtils.getStringParameter(request, "pageSize", "15");
			String projectName = ServletRequestUtils.getStringParameter(request, "projectName", "");
			String year = ServletRequestUtils.getStringParameter(request, "year", DateTime.getYear());
			String deptCode = ServletRequestUtils.getStringParameter(request, "deptCode", "");
			String jobClass = ServletRequestUtils.getStringParameter(request, "jobClass", "J");
			String item = ServletRequestUtils.getStringParameter(request, "item", "");
			String consultantName = ServletRequestUtils.getStringParameter(request, "consultantName", "");

			WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh = (ValueListHandler) wc.getBean("ProjectMemberEvalList", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();
			Map<String, String> filters = new HashMap<String, String>();

			if (projectName != null && !projectName.equals("")) {
				filters.put("projectName", "%" + projectName + "%");
			}
			if (consultantName != null && !consultantName.equals("")) {
				filters.put("consultantName", "%" + consultantName + "%");
			}
			if (deptCode != null && !deptCode.equals("")) {
				if (jobClass.equals("A"))
					filters.put("deptCode", deptCode.substring(0, 3) + "_");
				else
					filters.put("deptCode", deptCode);
			}
			if (jobClass != null && !jobClass.equals("")) {
				filters.put("jobClass", jobClass);
			}
			if (item != null && !item.equals("")) {
				filters.put("item", item);
			}
			if (year != null && !year.equals("")) {
				filters.put("year", year + "%");
			}
			else {
				Calendar c = new GregorianCalendar();
				String strEyear = Integer.toString(c.get(Calendar.YEAR));
				filters.put("year", strEyear);
			}

			filters.put(ValueListInfo.PAGING_PAGE, pagingPage);
			filters.put(ValueListInfo.PAGING_NUMBER_PER, pagingNumberPer);

			info.setFilters(filters);

			ValueList valueList = vlh.getValueList("getValueProjectMemberEvalList", info);
			request.setAttribute("result", valueList);
			valueList.getValueListInfo().getPagingNumberPer();
			request.setAttribute("projectName", projectName);
			request.setAttribute("consultantName", consultantName);
			request.setAttribute("deptCode", deptCode);
			request.setAttribute("jobClass", jobClass);
			request.setAttribute("item", item);
			request.setAttribute("year", year);

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return mapping.findForward("ProjectMemberEvalList");
	}

	public ActionForward getConsultantEvalListDetail(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {

			// 인자가 없을경우 디폴트 페이지는 1번 건수는 10건
			String pagingPage = ServletRequestUtils.getStringParameter(request, "pg", "1");
			String pagingNumberPer = ServletRequestUtils.getStringParameter(request, "pageSize", "15");
			String item = URLDecoder.decode(request.getParameter("item"), "UTF-8");
			String ssn = request.getParameter("ssn") == null || request.getParameter("ssn").equals("") ? "" : request.getParameter("ssn");
			String year = request.getParameter("year") == null || request.getParameter("year").equals("") ? "" : request.getParameter("year");

			WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh = (ValueListHandler) wc.getBean("ProjectMemberEvalList", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();
			Map<String, String> filters = new HashMap<String, String>();
			filters.put(ValueListInfo.PAGING_PAGE, pagingPage);
			filters.put(ValueListInfo.PAGING_NUMBER_PER, pagingNumberPer);
			if (!item.equals("")) {
				filters.put("item", item);
			}
			if (!ssn.equals("")) {
				filters.put("ssn", ssn);
			}
			if (year != null && !year.equals("")) {
				filters.put("year", year + "%");
			}
			info.setFilters(filters);

			ValueList valueList = vlh.getValueList("getConsultantEvalListDetail", info);
			request.setAttribute("result", valueList);
			request.setAttribute("item", item);
			request.setAttribute("ssn", ssn);
			request.setAttribute("year", year);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return mapping.findForward("ProjectMemberEvalListDetail");
	}


	public void getConsultantEvalListDetailView(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		String projectCode = ServletRequestUtils.getRequiredStringParameter(request, "projectCode");
		String ssn = ServletRequestUtils.getStringParameter(request, "ssn", "");
		String item = URLDecoder.decode(ServletRequestUtils.getRequiredStringParameter(request, "item"), "UTF-8");

		try {
			WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());

			ValueListHandler vlh1 = (ValueListHandler) wc.getBean("estimateValueListBean", ValueListHandler.class);
			ValueListInfo info1 = new ValueListInfo();
			Map<String, String> filters1 = new HashMap<String, String>();
			filters1.put(ValueListInfo.PAGING_PAGE, "1");
			filters1.put(ValueListInfo.PAGING_NUMBER_PER, String.valueOf(Integer.MAX_VALUE));
			filters1.put("projectCode", projectCode);
			filters1.put("ssn", ssn);
			filters1.put("item", item);
			info1.setFilters(filters1);

			ValueListHandler vlh2 = (ValueListHandler) wc.getBean("ProjectMemberEvalList", ValueListHandler.class);
			ValueListInfo info2 = new ValueListInfo();
			Map<String, String> filters2 = new HashMap<String, String>();
			filters2.put(ValueListInfo.PAGING_PAGE, "1");
			filters2.put(ValueListInfo.PAGING_NUMBER_PER, String.valueOf(Integer.MAX_VALUE));
			filters2.put("item", item);
			filters2.put("ssn", ssn);
			info2.setFilters(filters2);

			ValueList projectEstimateList = vlh1.getValueList("getProjectEstimate", info1);
			ValueList publicEduProjectEstimateList = vlh1.getValueList("getPublicEduProjectEstimate", info1);
			ValueList consultantEstimateList = vlh1.getValueList("getConsultantEstimate", info1);
			ValueList consultantEvalListDetailViewList = vlh2.getValueList("getConsultantEvalListDetailView", info1);

			map.put("detailView", consultantEvalListDetailViewList.getList());
			map.put("detailViewProEs", projectEstimateList.getList());
			map.put("detailViewPubEduProEs", publicEduProjectEstimateList.getList());
			map.put("detailViewConEs", consultantEstimateList.getList());
			AjaxUtil.successWrite(response, map);

		} catch (CodeException e) {
			logger.error(e.getMessage(), e);
			AjaxUtil.failWrite(response, map);
		}
	}

}
