/*
 * $Id: MailCommonAction.java,v 1.7 2018/11/11 17:27:00 cvs Exp $
 * created by    : CHO DAE HYON
 * creation-date : 2006. 2. 24.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.service.mailsender.action;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.kmac.pms.common.ajax.AjaxUtil;
import kr.co.kmac.pms.common.util.SessionUtils;
import kr.co.kmac.pms.expertpool.manager.ExpertPoolManager;
import kr.co.kmac.pms.system.emaillog.data.SalaryInfoMailLogData;
import kr.co.kmac.pms.system.emaillog.manager.SalaryInfoMailLogManager;
import kr.co.kmac.pms.service.mailsender.manager.PmsInfoMailSender;
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
 * @struts.action name="mailCommonAction" path="/action/MailCommonAction" parameter="mode" scope="request"
 */
public class MailCommonAction extends DispatchActionSupport {

	private static final Log logger = LogFactory.getLog(MailCommonAction.class);
	private ExpertPoolManager expertPoolManager;
	private SalaryInfoMailLogManager salaryInfoMailLogManager;
	private PmsInfoMailSender pmsInfoMailSender;

	public void countMyEmail(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();

		try {
			URL url = new URL("https://webmail.kmac.co.kr/a_biz/api/new_mail_count.nvd?user_account="
					+ this.getExpertPoolManager().retrieve(SessionUtils.getUsername(request)).getEmail());
			URLConnection conn = url.openConnection();
			InputStream is = conn.getInputStream();
			BufferedReader ir = new BufferedReader(new InputStreamReader(is, "utf-8"));
			String read = null;
			int count = 0;
			while ((read = ir.readLine()) != null) {
				count = count + Integer.valueOf(read);
			}
			map.put("mailCount", String.valueOf(count > 99 ? "99+" : count));
			AjaxUtil.successWrite(response, map);

		} catch (Exception e) {
			AjaxUtil.failWrite(response);
			logger.error(e.getMessage(), e);
		}
	}
	
	public void sendSalaryNoticeEmail(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();
		String ssnStr = ServletRequestUtils.getRequiredStringParameter(request, "ssnStr");
		String year = ServletRequestUtils.getRequiredStringParameter(request, "year");
		String month = ServletRequestUtils.getRequiredStringParameter(request, "month");
		
		try {
			WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh = (ValueListHandler) wc.getBean("projectExpense", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();
			Map<String, String> filters = new HashMap<String, String>();
			filters.put(ValueListInfo.PAGING_PAGE, "1");
			filters.put(ValueListInfo.PAGING_NUMBER_PER, String.valueOf(Integer.MAX_VALUE));
			filters.put("year", year);
			filters.put("month", month);

			String ssnArray[] = ssnStr.split(";");
			if(ssnArray != null && ssnArray.length > 0){
				for (String ssn : ssnArray) {
					filters.put("ssn", ssn);
					info.setFilters(filters);
					List list = vlh.getValueList("getSalaryMailingList", info).getList();
					
					// 성과급 안내 이메일 발송
					this.getPmsInfoMailSender().sendSalaryNoticeEmail(ssn, year, month, list);
					
					// 성과급 안내 이메일 발송 정보 기록
					SalaryInfoMailLogData mailLogData = new SalaryInfoMailLogData();
					mailLogData.setSsn(ssn);
					mailLogData.setYear(year);
					mailLogData.setMonth(month);
					this.getSalaryInfoMailLogManager().insertSalaryInfoMailLog(mailLogData);
				}
			} 			
			AjaxUtil.successWrite(response, map);

		} catch (Exception e) {
			AjaxUtil.failWrite(response);
			logger.error(e.getMessage(), e);
		}
	}

	public ExpertPoolManager getExpertPoolManager() {
		return expertPoolManager;
	}

	public void setExpertPoolManager(ExpertPoolManager expertPoolManager) {
		this.expertPoolManager = expertPoolManager;
	}
	
	public SalaryInfoMailLogManager getSalaryInfoMailLogManager() {
		return salaryInfoMailLogManager;
	}

	public void setSalaryInfoMailLogManager(SalaryInfoMailLogManager salaryInfoMailLogManager) {
		this.salaryInfoMailLogManager = salaryInfoMailLogManager;
	}	
	
	public PmsInfoMailSender getPmsInfoMailSender() {
		return pmsInfoMailSender;
	}

	public void setPmsInfoMailSender(PmsInfoMailSender pmsInfoMailSender) {
		this.pmsInfoMailSender = pmsInfoMailSender;
	}
}
