package kr.co.kmac.pms.project.statistics.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
 * @struts.action name="JudgeEvalAction" path="/action/JudgeEvalAction" parameter="mode" scope="request"
 * @struts.action-forward name="JudgeEvalList" path="/project/statistics/JudgeEvalList.jsp" redirect="false"
 */
public class JudgeEvalAction extends DispatchActionSupport {

	private static final Log logger = LogFactory.getLog(JudgeEvalAction.class);

	public ActionForward getValueJudgeEvalList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		try {
			// 인자가 없을경우 디폴트 페이지는 1번 건수는 15건
			String pagingPage = ServletRequestUtils.getStringParameter(request, "pg", "1");
			String pagingNumberPer = ServletRequestUtils.getStringParameter(request, "pageSize", "15");
			
			String promotionType = ServletRequestUtils.getStringParameter(request, "promotionType", "");
			String year = ServletRequestUtils.getStringParameter(request, "year", DateTime.getYear());
			String judgeName = ServletRequestUtils.getStringParameter(request, "judgeName", "");

			WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh = (ValueListHandler) wc.getBean("JudgeEvalList", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();
			Map<String, String> filters = new HashMap<String, String>();

			if (!promotionType.equals("")) {
				filters.put("promotionType", promotionType);
			}
			if (!year.equals("")) {
				filters.put("year", year);
			}
			if (!judgeName.equals("")) {
				filters.put("name", "%" + judgeName + "%");
			}
			info.setFilters(filters);

			filters.put(ValueListInfo.PAGING_PAGE, pagingPage);
			filters.put(ValueListInfo.PAGING_NUMBER_PER, pagingNumberPer);

			info.setFilters(filters);

			ValueList valueList = vlh.getValueList("getValueJudgeEvalList", info);
			request.setAttribute("result", valueList);

			request.setAttribute("promotionType", promotionType);
			request.setAttribute("year", year);
			request.setAttribute("judgeName", judgeName);

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return mapping.findForward("JudgeEvalList");
	}

}
