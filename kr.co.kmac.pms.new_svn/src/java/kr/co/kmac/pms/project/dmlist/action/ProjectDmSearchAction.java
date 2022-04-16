package kr.co.kmac.pms.project.dmlist.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
 * @struts.action name="ProjectDmSearchAction" path="/action/ProjectDmSearchAction" parameter="mode" scope="request"
 * @struts.action-forward name="ProjectDmSearchList" path="/project/dmlist/ProjectDmSearchList.jsp" redirect="false"
 */
public class ProjectDmSearchAction extends DispatchActionSupport {

	private static final Log logger = LogFactory.getLog(ProjectDmSearchAction.class);

	public ActionForward getProjectDmSearchList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		try {
			// 인자가 없을경우 디폴트 페이지는 1번 건수는 15건
			String pagingPage = ServletRequestUtils.getStringParameter(request, "pg", "1");
			String pagingNumberPer = ServletRequestUtils.getStringParameter(request, "pageSize", "10");

			String search_Gubun = ServletRequestUtils.getStringParameter(request, "search_Gubun", "p_name");
			String searchKey = ServletRequestUtils.getStringParameter(request, "searchKey", "");
			String callbackFunc = ServletRequestUtils.getStringParameter(request, "callbackFunc");

			WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh = (ValueListHandler) wc.getBean("DmList", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();
			Map<String, String> filters = new HashMap<String, String>();

			if (search_Gubun.equals("p_name")) {
				filters.put("search_Gubun1",  "%" + searchKey + "%");
			}else{
				filters.put("search_Gubun2",  "%" + searchKey + "%");
				
			}
			filters.put(ValueListInfo.PAGING_PAGE, pagingPage);
			filters.put(ValueListInfo.PAGING_NUMBER_PER, pagingNumberPer);

			info.setFilters(filters);

			ValueList valueList = vlh.getValueList("getProjectDmSearchList", info);
			request.setAttribute("result", valueList);

			request.setAttribute("search_Gubun", search_Gubun);
			request.setAttribute("searchKey", searchKey);
			request.setAttribute("callbackFunc", callbackFunc);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return mapping.findForward("ProjectDmSearchList");
	}

}
