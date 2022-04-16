package kr.co.kmac.pms.orgdb.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.kmac.pms.attach.form.AttachForm;
import kr.co.kmac.pms.attach.manager.AttachManager;
import kr.co.kmac.pms.attach.manager.AttachTemplateManager;
import kr.co.kmac.pms.board.data.BoardDataForSelect;
import kr.co.kmac.pms.common.ajax.AjaxUtil;
import kr.co.kmac.pms.common.ajax.JSONWriter;
import kr.co.kmac.pms.common.code.data.CodeEntity;
import kr.co.kmac.pms.common.code.exception.CodeException;
import kr.co.kmac.pms.common.code.manager.CommonCodeManager;
import kr.co.kmac.pms.common.repository.action.RepositoryDispatchActionSupport;
import kr.co.kmac.pms.common.util.SessionUtils;
import kr.co.kmac.pms.expertpool.exception.ExpertPoolException;
import kr.co.kmac.pms.orgdb.data.OrgdbDetail;
import kr.co.kmac.pms.orgdb.form.OrgdbDetailForm;
import kr.co.kmac.pms.orgdb.manager.OrgdbManager;
import kr.co.kmac.pms.service.mailsender.manager.PmsInfoMailSender;
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

/**
 * @struts.action name="orgdbActionForm" path="/action/OrgdbAction" parameter="mode" scope="request"
 * 
 * @struts.action-forward name="orgdbList"	path="/orgdb/orgdb_list.jsp"	redirect="false"
 * @struts.action-forward name="orgdbView"	path="/orgdb/orgdb_view.jsp"	redirect="false"
 * @struts.action-forward name="orgdbEdit"	path="/orgdb/orgdb_form.jsp"	redirect="false"
 * @struts.action-forward name="orgdbExcl"	path="/orgdb/orgdb_excel.jsp"	redirect="false"
 * @struts.action-forward name="orgdbHist"	path="/orgdb/orgdb_history.jsp"	redirect="false"
 */

public class OrgdbAction extends RepositoryDispatchActionSupport {
	private static final Log logger = LogFactory.getLog(OrgdbAction.class);
	private OrgdbManager orgdbManager;
	private CommonCodeManager commonCodeManager;
	private AttachManager attachManager;
	private AttachTemplateManager attachTemplateManager;
	private PmsInfoMailSender pmsInfoMailSender;
	
	
	public PmsInfoMailSender getPmsInfoMailSender() {
		return pmsInfoMailSender;
	}
	public void setPmsInfoMailSender(PmsInfoMailSender pmsInfoMailSender) {
		this.pmsInfoMailSender = pmsInfoMailSender;
	}
	public AttachTemplateManager getAttachTemplateManager() {
		return attachTemplateManager;
	}
	public void setAttachTemplateManager(AttachTemplateManager attachTemplateManager) {
		this.attachTemplateManager = attachTemplateManager;
	}
	public OrgdbManager getOrgdbManager() {
		return orgdbManager;
	}
	public void setOrgdbManager(OrgdbManager orgdbManager) {
		this.orgdbManager = orgdbManager;
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
	
	/**
	 * 기관 DB 리스트
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward selectList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String pageSize     = ServletRequestUtils.getStringParameter(request , "pageSize"			, "10");
		String pg           = ServletRequestUtils.getStringParameter(request , "pg"					, "1");
		String keyword      = ServletRequestUtils.getStringParameter(request , "keyword"			, "");
		String orgName		= ServletRequestUtils.getStringParameter(request,  "orgName", "");
		String keyfield     = ServletRequestUtils.getStringParameter(request , "keyfield"			, "");
		String businessType = ServletRequestUtils.getStringParameter(request , "businessTypeCode"	, "");
		String relWithKmac  = ServletRequestUtils.getStringParameter(request , "relWithKmacCode"	, "");
		String specialField = ServletRequestUtils.getStringParameter(request , "specialFieldCode"	, "");
		String companyName  = ServletRequestUtils.getStringParameter(request , "companyName"		, "");

		
		try {
			WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh = (ValueListHandler) wc.getBean("orgDbValueList", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();
			Map<String, String> filters = new HashMap<String, String>();
			filters.put(ValueListInfo.PAGING_PAGE, pg);
			filters.put(ValueListInfo.PAGING_NUMBER_PER, pageSize);

			if (!keyword.equals("ALL") && !keyword.equals("")) {
				if (keyfield.equals("businessCatogory"))
					filters.put("businessCatogory", "%" + keyword + "%");
				else if (keyfield.equals("orgName"))
					filters.put("orgName", "%" + keyword + "%");
			}
			if (!businessType.equals("ALL") && !businessType.equals(""))
				filters.put("businessType", "%" + businessType + "%");
			if (!relWithKmac.equals("ALL") && !relWithKmac.equals(""))
				filters.put("relWithKmac", relWithKmac);
			if (!specialField.equals("ALL") && !specialField.equals(""))
				filters.put("specialField", specialField);
			if (companyName != null && !companyName.equals(""))
				filters.put("companyName", "%" + companyName + "%");

			info.setFilters(filters);
			ValueList valueList = vlh.getValueList("orgDbSelectList", info);
			request.setAttribute("result", valueList);
			
			Map<String, String> pageData = new HashMap<String, String>();
			pageData.put("pg"			, pg);
			pageData.put("keyword"		, keyword);
			pageData.put("keyfield"		, keyfield);
			pageData.put("businessType"	, businessType);
			pageData.put("relWithKmac"	, relWithKmac);
			pageData.put("specialField"	, specialField);
			pageData.put("companyName"	, companyName);
			request.setAttribute("pageData"	, pageData);
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return mapping.findForward("orgdbList");
	}
	
	/**
	 * 기관 DB 리스트 Excel로 다운로드
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveToExcel(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String pageSize     = ServletRequestUtils.getStringParameter(request , "pageSize"			, "15");
		String pg           = ServletRequestUtils.getStringParameter(request , "pg"					, "1");
		String keyword      = ServletRequestUtils.getStringParameter(request , "keyword"			, "");
		String keyfield     = ServletRequestUtils.getStringParameter(request , "keyfield"			, "");
		String businessType = ServletRequestUtils.getStringParameter(request , "businessTypeCode"	, "");
		String relWithKmac  = ServletRequestUtils.getStringParameter(request , "relWithKmacCode"	, "");
		String specialField = ServletRequestUtils.getStringParameter(request , "specialFieldCode"	, "");
		String companyName  = ServletRequestUtils.getStringParameter(request , "companyName"		, "");

		String fileName = "협력기관 리스트.xls";
		response.setHeader("Content-Disposition", "attachment; filename=" + new String(fileName.getBytes("EUC-KR"), "8859_1") + ";"); 
	    response.setHeader("Content-Description", "JSP Generated Data");
		
		try {
			WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh = (ValueListHandler) wc.getBean("orgDbValueList", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();
			Map<String, String> filters = new HashMap<String, String>();
			filters.put(ValueListInfo.PAGING_PAGE, pg);
			filters.put(ValueListInfo.PAGING_NUMBER_PER, pageSize);

			if (!keyword.equals("ALL") && !keyword.equals("")) {
				if (keyfield.equals("businessCatogory"))
					filters.put("businessCatogory", "%" + keyword + "%");
				else if (keyfield.equals("orgName"))
					filters.put("orgName", "%" + keyword + "%");
			}
			if (!businessType.equals("ALL") && !businessType.equals(""))
				filters.put("businessType", "%" + businessType + "%");
			if (!relWithKmac.equals("ALL") && !relWithKmac.equals(""))
				filters.put("relWithKmac", relWithKmac);
			if (!specialField.equals("ALL") && !specialField.equals(""))
				filters.put("specialField", specialField);
			if (companyName != null && !companyName.equals(""))
				filters.put("companyName", "%" + companyName + "%");

			info.setFilters(filters);
			ValueList valueList = vlh.getValueList("orgDbSelectList", info);
			request.setAttribute("result", valueList);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return mapping.findForward("orgdbExcl");
	}
	
	/**
	 * 기관DB 한건 반환
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward select(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String orgCode = ServletRequestUtils.getRequiredStringParameter(request, "orgCode");
		boolean permission = false;
		WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
		BoardDataForSelect boardDataForSelect = new BoardDataForSelect();
		
		try {
			OrgdbDetail orgdbDetail = getOrgdbManager().retrieve(orgCode);
			String snName = SessionUtils.getUserObjext().getName();
			//String snDept = SessionUtils.getUserObjext().getUsername();
			
			if(snName.equals(orgdbDetail.getCreator())) permission = true;
			
			boardDataForSelect = getAttachManager().select2("Orgdb", orgCode);

			request.setAttribute("fileMode" , "READ");// READ  WRITE
			request.setAttribute("fileList", this.getAttachManager().selectListForTask(wc, "orgdb" + orgCode, null, null).getList());
			request.setAttribute(
					"attachOutputType",
					this.getAttachTemplateManager()
							.selectOutputTypeCodeList(WebApplicationContextUtils.getWebApplicationContext(super.getServletContext()), null, "BT4")
							.getList());
			
			request.setAttribute("orgdb", orgdbDetail.getOrgCode());
			request.setAttribute("orgdbDetail", orgdbDetail);
			request.setAttribute("permission", permission);
			request.setAttribute("viewMode", "SIMPLE");
			request.setAttribute("result", boardDataForSelect);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return mapping.findForward("orgdbView");
	}
	
	/**
	 * 등록 및 수정 화면
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward loadForm(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String orgCode = ServletRequestUtils.getStringParameter(request, "orgCode", ""); 

		WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
		try {
			OrgdbDetail orgdbDetail = new OrgdbDetail();
			String titleText = "협력기관 등록";
			
			if(!orgCode.equals("")) {
				orgdbDetail = getOrgdbManager().retrieve(orgCode);
				titleText = "협력기관 수정";
				
				request.setAttribute("fileList", this.getAttachManager().selectListForTask(wc, "orgdb" + orgCode, null, null).getList());
			}
			List<CodeEntity> businessTypeList  = this.getCommonCodeManager().getCodeEntityList("BUSINESS_TYPE_CODE");
			
			request.setAttribute(
					"attachOutputType",
					this.getAttachTemplateManager()
							.selectOutputTypeCodeList(WebApplicationContextUtils.getWebApplicationContext(super.getServletContext()), null, "BT4")
							.getList());
			
			request.setAttribute("fileMode" , "WRITE");// READ  WRITE
			request.setAttribute("viewMode" , "SIMPLE");
			request.setAttribute("orgdbDetail", orgdbDetail);
			request.setAttribute("bizTypeList", businessTypeList);
			request.setAttribute("titleText", titleText);
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return mapping.findForward("orgdbEdit");
	}
	
	/**
	 * 기관 dB 데이터 생성
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public void saveOrgdb(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		OrgdbDetailForm orgdbDetailForm = (OrgdbDetailForm) form;
		
		String orgCode = "";
		String snName = SessionUtils.getUserObjext().getName();
		//String snDept = SessionUtils.getUserObjext().getUsername();
		try {
			String[] bizType = request.getParameterValues("businessType");
			String businessType = "";
			if(bizType != null) {
				for(int i = 0; i < bizType.length; i++) {
					businessType += (i == 0)? "" : "|";
					businessType += bizType[i];
				}
			}
			orgdbDetailForm.setBusinessType(businessType);

			
			if(orgdbDetailForm.getOrgCode().equals("")) {
				orgdbDetailForm.setCreator(snName);
				//orgdbDetailForm.setCreatorSsn(creatorSsn)
				orgCode = getOrgdbManager().create(orgdbDetailForm);
				
				/***** 안내 메일 발송 Create*****/
				try{
					this.getPmsInfoMailSender().sendSanctonRefInfoOrg(orgdbDetailForm, "Y");
				}catch(Exception e){
					logger.error("Send Mail error");
				}
				/***** 안내 메일 종료 *****/
			} else {
				orgdbDetailForm.setModifier(snName);
				
				getOrgdbManager().update(orgdbDetailForm);
				orgCode = orgdbDetailForm.getOrgCode();
				
				/***** 안내 메일 발송 Modify*****/
				try{
					this.getPmsInfoMailSender().sendSanctonRefInfoOrg(orgdbDetailForm, "N");
				}catch(Exception e){
					logger.error("Send Mail error");
				}
				/***** 안내 메일 종료 *****/
			}
			
			/***** 파일 첨부 시작 *****/
			AttachForm attachForm = (AttachForm) form;
			if (attachForm.getAttachFileId() != null && attachForm.getAttachFileId().length > 0) {
				this.getAttachManager().deleteAllInTask("orgdb" + orgCode);
				attachForm.setTaskId("orgdb" + orgCode);
				attachForm.setProjectCode("");
				attachForm.setTaskFormTypeId("");			
				this.getAttachManager().insert(attachForm);
			}
			/***** 파일 첨부 종료 *****/
			
			
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

	/**
	 * 기관 dB 데이터 삭제
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public void removeOrgdb(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		String orgCode = ServletRequestUtils.getRequiredStringParameter(request, "orgCode");

		try {
			getOrgdbManager().remove(orgCode);

			
			JSONWriter writer = new JSONWriter();
			map.put("result", true);
			map.put("resultMsg"		, "delete success");
			
			AjaxUtil.successWrite(response, map);
		} catch (CodeException e) {
			map.put("result", false);
			map.put("resultMsg", "delete fail");
			map.put("ErrMsg", e);
			AjaxUtil.failWrite(response, map);
		}
	}
	
	/**
	 * 기관 dB 승인
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public void orgdbCheck(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap map = new HashMap();

		String orgCode = ServletRequestUtils.getRequiredStringParameter(request, "orgCode");
		String ssn = SessionUtils.getUsername(request);
		
		try {
			getOrgdbManager().orgdbCheck(orgCode, ssn);
		} catch (ExpertPoolException e) {
			logger.info(e.getMessage(), e);
			map.put("result", false);
			map.put("resultMsg", "처리 중 오류가 발생했습니다.");
			map.put("ErrMsg", e);
			AjaxUtil.failWrite(response, map);
		}
	}
	
	/**
	 * 기관 dB 취소
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public void orgdbCheck2(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap map = new HashMap();

		String orgCode = ServletRequestUtils.getRequiredStringParameter(request, "orgCode");
		String ssn = SessionUtils.getUsername(request);
		
		try {
			getOrgdbManager().orgdbCheck2(orgCode);
		} catch (ExpertPoolException e) {
			logger.info(e.getMessage(), e);
			map.put("result", false);
			map.put("resultMsg", "처리 중 오류가 발생했습니다.");
			map.put("ErrMsg", e);
			AjaxUtil.failWrite(response, map);
		}
	}

	/**
	 * 기관 DB 진행정보 리스트
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward selectHistoryList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String orgCode = request.getParameter("orgCode");

		try {
			WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh = (ValueListHandler) wc.getBean("orgDbValueList", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();
			Map<String, String> filters = new HashMap<String, String>();
			filters.put(ValueListInfo.PAGING_PAGE, "1");
			filters.put(ValueListInfo.PAGING_NUMBER_PER, String.valueOf(Integer.MAX_VALUE));

			if (orgCode != null && !orgCode.equals("")) {
				filters.put("orgCode", orgCode);
			}
			info.setFilters(filters);
			ValueList valueList1 = vlh.getValueList("orgDbCustomerSelectList", info);
			ValueList valueList2 = vlh.getValueList("orgDbProjectSelectList", info);
			ValueList valueList3 = vlh.getValueList("orgDbExpertSelectList", info);

			request.setAttribute("result1", valueList1);
			request.setAttribute("result2", valueList2);
			request.setAttribute("result3", valueList3);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return mapping.findForward("orgdbHist");
	}
}
