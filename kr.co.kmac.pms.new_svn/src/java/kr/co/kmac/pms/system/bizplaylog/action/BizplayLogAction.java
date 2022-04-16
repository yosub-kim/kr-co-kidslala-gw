package kr.co.kmac.pms.system.bizplaylog.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.kmac.pms.common.ajax.AjaxUtil;
import kr.co.kmac.pms.common.ajax.JSONWriter;
import kr.co.kmac.pms.common.form.ResponseForm;
import kr.co.kmac.pms.common.util.DateUtil;
import kr.co.kmac.pms.common.util.StringUtil;
import kr.co.kmac.pms.expertpool.exception.ExpertPoolException;
import kr.co.kmac.pms.service.scheduler.batch.BizplayInfoSendService;
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
 * @struts.action name="bizplayLogAction" path="/action/BizplayLogAction" parameter="mode" scope="request"
 * @struts.action-forward name="bizplayLog" path="/system/bizplaylog/bizplayLog.jsp" redirect="false"
 */
public class BizplayLogAction extends DispatchActionSupport {

	private static final Log logger = LogFactory.getLog(BizplayLogAction.class);

	private BizplayInfoSendService bizplayInfoSendService;

	public ActionForward getBizplayLogList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String pageSize = ServletRequestUtils.getStringParameter(request, "pageSize", "15");
		String pg = ServletRequestUtils.getStringParameter(request, "pg", "1");
		String endPoint = ServletRequestUtils.getStringParameter(request, "endPoint", "");
		String from = ServletRequestUtils.getStringParameter(request, "from", DateUtil.getDateTimeByPattern("yyyy-MM-dd"));
		String to = ServletRequestUtils.getStringParameter(request, "to", DateUtil.getDateTimeByPattern("yyyy-MM-dd"));

		WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());

		Map<String, String> filters = new HashMap<String, String>();
		filters.put("from", StringUtil.replace(from, "-", ""));
		filters.put("to", StringUtil.replace(to, "-", ""));

		if (endPoint != null && !endPoint.equals("")) {
			filters.put("endPoint", endPoint);
		}
		if (pg != null && !pg.equals("")) {
			filters.put(ValueListInfo.PAGING_PAGE, String.valueOf(pg));
		}
		if (pageSize != null && !pageSize.equals("")) {
			filters.put(ValueListInfo.PAGING_NUMBER_PER, String.valueOf(pageSize));
		}

		try {
			ValueListHandler vlh = (ValueListHandler) wc.getBean("bizplayLog", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo("createdTime", ValueListInfo.DESCENDING);
			info.setFilters(filters);
			ValueList valueList = vlh.getValueList("bizplayLogList", info);
			request.setAttribute("list", valueList);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		request.setAttribute("from", from);
		request.setAttribute("to", to);
		request.setAttribute("endPoint", endPoint);

		return mapping.findForward("bizplayLog");
	}

	public void resendBizplayExpertpoolInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap map = new HashMap();

		try {
			bizplayInfoSendService.sendExpertPoolInfoToBizplay();
			
			JSONWriter writer = new JSONWriter();
			map.put("result", true);
			map.put("resultMsg", "처리 되었습니다.");
			AjaxUtil.successWrite(response, map);

		} catch (ExpertPoolException e) {
			logger.info(e.getMessage(), e);
			map.put("result", false);
			map.put("resultMsg", "처리 중 오류가 발생했습니다.");
			map.put("ErrMsg", e);
			AjaxUtil.failWrite(response, map);
		}
	}

	public void resendBizplayDeptInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap map = new HashMap();

		try {
			bizplayInfoSendService.sendDeptInfoToBizplay();
			
			JSONWriter writer = new JSONWriter();
			map.put("result", true);
			map.put("resultMsg", "처리 되었습니다.");
			AjaxUtil.successWrite(response, map);

		} catch (ExpertPoolException e) {

			logger.info(e.getMessage(), e);
			map.put("result", false);
			map.put("resultMsg", "처리 중 오류가 발생했습니다.");
			map.put("ErrMsg", e);
			AjaxUtil.failWrite(response, map);
		}
	}

	public void resendBizplayProjectInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap map = new HashMap();

		try {
			bizplayInfoSendService.sendAllProjectInfoToBizplay();
			JSONWriter writer = new JSONWriter();
			map.put("result", true);
			map.put("resultMsg", "처리 되었습니다.");
			AjaxUtil.successWrite(response, map);

		} catch (ExpertPoolException e) {

			logger.info(e.getMessage(), e);
			map.put("result", false);
			map.put("resultMsg", "처리 중 오류가 발생했습니다.");
			map.put("ErrMsg", e);
			AjaxUtil.failWrite(response, map);
		}
	}

	public BizplayInfoSendService getBizplayInfoSendService() {
		return bizplayInfoSendService;
	}

	public void setBizplayInfoSendService(BizplayInfoSendService bizplayInfoSendService) {
		this.bizplayInfoSendService = bizplayInfoSendService;
	}

}
