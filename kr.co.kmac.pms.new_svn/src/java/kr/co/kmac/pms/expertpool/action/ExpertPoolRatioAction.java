/*
 * $Id: ExpertPoolRatioAction.java,v 1.2 2011/01/17 13:25:59 cvs Exp $ created
 * by : ¾È¼ºÈ£ creation-date : 2006. 7. 6.
 * ========================================================= Copyright (c) 2006
 * Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.expertpool.action;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.kmac.pms.common.util.DateUtil;
import kr.co.kmac.pms.common.util.StringUtil;

import net.mlw.vlh.ValueList;
import net.mlw.vlh.ValueListHandler;
import net.mlw.vlh.ValueListInfo;

import oracle.sql.DATE;

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
 * @struts.action name="expertPoolRatioAction" path="/action/ExpertPoolRatioAction" parameter="mode" scope="request"
 * 
 * @struts.action-forward name="expertPoolRatio" path="/expertPool/expertPoolRatio.jsp" redirect="false"
 */
public class ExpertPoolRatioAction extends DispatchActionSupport {

	private static final Log logger = LogFactory.getLog(ExpertPoolRatioAction.class);

	public ActionForward getExpertPoolRatio(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String ratioType = ServletRequestUtils.getStringParameter(request, "ratioType", "");
		String dateFrom = StringUtil.replace(ServletRequestUtils.getStringParameter(request, "dateFrom", DateUtil.getCurrentYyyymmdd()), "-", "");
		String dateTo = StringUtil.replace(ServletRequestUtils.getStringParameter(request, "dateTo", DateUtil.getCurrentYyyymmdd()), "-", "");

		try {
			WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());

			ValueListHandler vlh = (ValueListHandler) wc.getBean("expertPoolList", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();
			Map<String, String> filters = new HashMap<String, String>();

			filters.put(ValueListInfo.PAGING_PAGE, "1");
			filters.put(ValueListInfo.PAGING_NUMBER_PER, String.valueOf(Integer.MAX_VALUE));

			if (ratioType.equals("ratioType1")) {
				filters.put("ratioType1", "ratioType1");
			} else if (ratioType.equals("ratioType2")) {
				filters.put("ratioType2", "ratioType2");
			} else if (ratioType.equals("ratioType3")) {
				filters.put("ratioType3", "ratioType3");
			}

			if (!dateFrom.equals("")) {
				filters.put("dateFrom", dateFrom.substring(0, 6));
			}
			if (!dateTo.equals("")) {
				filters.put("dateTo", dateTo.substring(0, 6));
			}

			info.setFilters(filters);

			ValueList valueList = vlh.getValueList("getExpertPoolRatio", info);

			request.setAttribute("ratioType", ratioType);
			request.setAttribute("dateTo", dateTo);
			request.setAttribute("dateFrom", dateFrom);
			request.setAttribute("result", valueList);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return mapping.findForward("expertPoolRatio");
	}
}