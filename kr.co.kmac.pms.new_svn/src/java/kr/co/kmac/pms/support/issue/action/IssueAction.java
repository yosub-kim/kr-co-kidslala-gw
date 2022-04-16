package kr.co.kmac.pms.support.issue.action;

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
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.support.WebApplicationContextUtils;

import kr.co.kmac.pms.attach.manager.AttachManager;
import kr.co.kmac.pms.attach.manager.AttachTemplateManager;
import kr.co.kmac.pms.common.ajax.AjaxUtil;
import kr.co.kmac.pms.common.code.manager.CommonCodeManager;
import kr.co.kmac.pms.common.repository.action.RepositoryDispatchActionSupport;
import kr.co.kmac.pms.common.util.SessionUtils;
import kr.co.kmac.pms.support.issue.exception.IssueException;
import kr.co.kmac.pms.support.issue.form.IssueDetailForm;
import kr.co.kmac.pms.support.issue.manager.IssueManager;

public class IssueAction extends RepositoryDispatchActionSupport {

	private static final Log logger = LogFactory.getLog(IssueAction.class);
	private IssueManager issueManager;
	private CommonCodeManager commonCodeManager;
	private AttachManager attachManager;
	private AttachTemplateManager attachTemplateManager;

	public IssueManager getIssueManager() {
		return issueManager;
	}

	public void setIssueManager(IssueManager issueManager) {
		this.issueManager = issueManager;
	}

	public CommonCodeManager getCommonCodeManager() {
		return commonCodeManager;
	}

	public void setCommonCodeManager(CommonCodeManager commonCodeManager) {
		this.commonCodeManager = commonCodeManager;
	}

	public AttachManager getAttachManager() {
		return attachManager;
	}

	public void setAttachManager(AttachManager attachManager) {
		this.attachManager = attachManager;
	}

	public AttachTemplateManager getAttachTemplateManager() {
		return attachTemplateManager;
	}

	public void setAttachTemplateManager(
			AttachTemplateManager attachTemplateManager) {
		this.attachTemplateManager = attachTemplateManager;
	}

	public static Log getLogger() {
		return logger;
	}

	/**
	 * 문서발급 DB 리스트
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward selectList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String pageSize				= ServletRequestUtils.getStringParameter(request, "pageSize"		, "10");
		String pg					= ServletRequestUtils.getStringParameter(request, "pg"				, "1");
		String keyword				= ServletRequestUtils.getStringParameter(request, "keyword"			, "");
		String keyfield				= ServletRequestUtils.getStringParameter(request, "keyfield"		, "");
		String gaksa				= ServletRequestUtils.getStringParameter(request, "gaksa"			, "KMAC");
		String dept 				= this.getIssueManager().select(SessionUtils.getUsername(request));
		
		try {
			WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh = (ValueListHandler) wc.getBean("issueValueList", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();
			Map<String, String> filters = new HashMap<String, String>();
			filters.put(ValueListInfo.PAGING_PAGE, pg);
			filters.put(ValueListInfo.PAGING_NUMBER_PER, pageSize);
			
			if (!keyword.equals("")) {
				if (keyfield.equals("content"))
					filters.put("content", "%" + keyword + "%");
				else if (keyfield.equals("dept_name"))
					filters.put("dept_name", "%" + keyword + "%");
				else if (keyfield.equals("receive"))
					filters.put("receive", "%" + keyword + "%");
				else if (keyfield.equals("year"))
					filters.put("year", "%" + keyword + "%");
				else if (keyfield.equals("gubun"))
					filters.put("gubun", "%" + keyword + "%");
			}
			
			info.setFilters(filters);
			ValueList valueList = vlh.getValueList("issueSelectList", info);
			request.setAttribute("result", valueList);
			
			request.setAttribute("pg", pg);
			request.setAttribute("keyfield", keyfield);
			request.setAttribute("keyword", keyword);
			request.setAttribute("gaksa", gaksa);
			request.setAttribute("dept", dept);
			
			Map<String, String> pageData = new HashMap<String, String>();
			pageData.put("pg", pg);
			pageData.put("keyword", keyword);
			pageData.put("keyfield", keyfield);
			pageData.put("gaksa", gaksa);
			pageData.put("dept", dept);
			
			request.setAttribute("pageData", pageData);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		
		return mapping.findForward("issueList");
	}
	
	/**
	 * 등록 팝업
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward insertForm (ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String gaksa				= ServletRequestUtils.getStringParameter(request, "gaksa"			, "");
		String dept 				= this.getIssueManager().select(SessionUtils.getUsername(request));
			
		request.setAttribute("gaksa", gaksa);
		request.setAttribute("dept", dept);
		
		Map<String, String> pageData = new HashMap<String, String>();
		pageData.put("gaksa", gaksa);
		pageData.put("dept", dept);
		
		return mapping.findForward("insertForm");
	}
	
	/**
	 * 문서발급 DB 데이터 생성
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public void saveIssue(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		IssueDetailForm issueDetailForm = (IssueDetailForm) form;
		String sabun = this.getIssueManager().select2(SessionUtils.getUsername(request));
		String gaksa = request.getParameter("gaksa");
		String dept = request.getParameter("dept");
		
		try {
			issueDetailForm.setSabun(sabun);
			
			getIssueManager().create(issueDetailForm);
			
			map.put("result", true);
			map.put("resultMsg", "save success");
			
			AjaxUtil.successWrite(response, map);
		} catch (IssueException e){
			map.put("result", false);
			map.put("resultMsg", "save fail");
			map.put("errMsg", e);
			AjaxUtil.failWrite(response, map);
		}
	}
	
}
