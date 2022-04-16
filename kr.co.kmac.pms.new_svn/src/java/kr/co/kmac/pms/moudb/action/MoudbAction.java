package kr.co.kmac.pms.moudb.action;

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

import kr.co.kmac.pms.common.code.exception.CodeException;
import kr.co.kmac.pms.common.util.SessionUtils;
import kr.co.kmac.pms.common.code.manager.CommonCodeManager;
import kr.co.kmac.pms.attach.form.AttachForm;
import kr.co.kmac.pms.attach.manager.AttachManager;
import kr.co.kmac.pms.attach.manager.AttachTemplateManager;
import kr.co.kmac.pms.board.data.BoardDataForSelect;
import kr.co.kmac.pms.common.ajax.AjaxUtil;
import kr.co.kmac.pms.common.ajax.JSONWriter;
import kr.co.kmac.pms.common.repository.action.RepositoryDispatchActionSupport;
import kr.co.kmac.pms.moudb.data.MoudbDetail;
import kr.co.kmac.pms.moudb.form.MoudbDetailForm;
import kr.co.kmac.pms.moudb.manager.MoudbManager;

/**
* @struts.action-forward name="moudbList"	path="/moudb/moudb_list.jsp"	redirect="false"
* @struts.action-forward name="moudbView"	path="/moudb/moudb_view.jsp"	redirect="false"
* @struts.action-forward name="moudbEdit"	path="/moudb/moudb_form.jsp"	redirect="false"
*/

public class MoudbAction extends RepositoryDispatchActionSupport {
	
	private static final Log logger = LogFactory.getLog(MoudbAction.class);
	private MoudbManager moudbManager;
	private CommonCodeManager commonCodeManager;
	private AttachManager attachManager;
	private AttachTemplateManager attachTemplateManager;
	
	public MoudbManager getMoudbManager() {
		return moudbManager;
	}

	public void setMoudbManager(MoudbManager moudbManager) {
		this.moudbManager = moudbManager;
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

	public void setAttachTemplateManager(AttachTemplateManager attachTemplateManager) {
		this.attachTemplateManager = attachTemplateManager;
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
		
		String pageSize				= ServletRequestUtils.getStringParameter(request , "pageSize"			, "10");
		String pg					= ServletRequestUtils.getStringParameter(request , "pg"					, "1");
		String keyword				= ServletRequestUtils.getStringParameter(request , "keyword"			, "");
		String keyfield				= ServletRequestUtils.getStringParameter(request , "keyfield"			, "");
		String com_name				= ServletRequestUtils.getStringParameter(request , "com_name"			, "");
		String country				= ServletRequestUtils.getStringParameter(request , "country"			, "");
		String writer				= ServletRequestUtils.getStringParameter(request , "writer"				, "");
		String com_cooperation	 	= ServletRequestUtils.getStringParameter(request , "com_cooperation"	, "");
		String com_expert			= ServletRequestUtils.getStringParameter(request , "com_expert"			, "");
		
		try {
			WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh = (ValueListHandler) wc.getBean("mouDbValueList", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();
			Map<String, String> filters = new HashMap<String, String>();
			filters.put(ValueListInfo.PAGING_PAGE, pg);
			filters.put(ValueListInfo.PAGING_NUMBER_PER, pageSize);
			
			if (!keyword.equals("")) {
				if (keyfield.equals("country"))
					filters.put("country", "%" + keyword + "%");
				else if (keyfield.equals("com_name"))
					filters.put("com_name", "%" + keyword + "%");
				else if (keyfield.equals("com_expert"))
					filters.put("com_expert", "%" + keyword + "%");
				else if (keyfield.equals("com_cooperation"))
					filters.put("com_cooperation", "%" + keyword + "%");
				else if (keyfield.equals("writer"))
					filters.put("writer", "%" + keyword + "%");
			}
			
			info.setFilters(filters);
			ValueList valueList = vlh.getValueList("mouDbSelectList", info);
			request.setAttribute("result", valueList);
			
			request.setAttribute("pg", pg);
			request.setAttribute("keyfield", keyfield);
			request.setAttribute("keyword", keyword);
			
			/*Map<String, String> pageData = new HashMap<String, String>();
			pageData.put("pg", pg);
			pageData.put("keyword", keyword);
			pageData.put("keyfield", keyfield);
	
			request.setAttribute("pageData"	, pageData)*/;
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return mapping.findForward("moudbList");
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
		
		String idx = ServletRequestUtils.getRequiredStringParameter(request, "idx");
		/*boolean permission = false;*/
		WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
		/*BoardDataForSelect boardDataForSelect = new BoardDataForSelect();*/
		
		try {
			MoudbDetail moudbDetail = getMoudbManager().retrieve(idx);
			/*String mname = SessionUtils.getUserObjext().getName();
			
			if (mname.equals(moudbDetail.getWrtier())) permission = true;
			
			boardDataForSelect = getAttachManager().select2("Moudb", idx);*/
			
			request.setAttribute("fileMode", "READ");
			request.setAttribute("fileList", this.getAttachManager().selectListForTask(wc, "moudb" + idx, null, null).getList());
			request.setAttribute(
					"attachOutputType",
					this.getAttachTemplateManager()	
					.selectOutputTypeCodeList(WebApplicationContextUtils.getWebApplicationContext(super.getServletContext()), null, "BT4")
					.getList());
			request.setAttribute("moudb", moudbDetail.getIdx());
			request.setAttribute("moudbDetail", moudbDetail);
			/*request.setAttribute("permission", permission);*/
			request.setAttribute("viewMode", "SIMPLE");
			/*request.setAttribute("result", boardDataForSelect);*/
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return mapping.findForward("moudbView");
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
	public ActionForward loadForm (ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String idx = ServletRequestUtils.getStringParameter(request, "idx", "");
		
		WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
		try {
			MoudbDetail moudbDetail = new MoudbDetail();
			String titleText = "MOU 등록";
			
			if(!idx.equals("")) {
				moudbDetail = getMoudbManager().retrieve(idx);
				titleText = "MOU 수정";
				
				request.setAttribute("fileList", this.getAttachManager().selectListForTask(wc, "moudb" + idx, null, null).getList());
			}
			
			request.setAttribute(
					"attachOutputTye",
					this.getAttachTemplateManager()
					.selectOutputTypeCodeList(WebApplicationContextUtils.getWebApplicationContext(super.getServletContext()), null, "BT4")
					.getList());
			request.setAttribute("fileMode", "WRITE"); //READ WRITE
			request.setAttribute("viewMode", "SIMPLE");
			request.setAttribute("moudbDetail", moudbDetail);
			request.setAttribute("titleText", titleText);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return mapping.findForward("moudbEdit");
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
	public void saveMoudb(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		MoudbDetailForm moudbDetailForm = (MoudbDetailForm) form;
		
		String idx = "";
		String mname = SessionUtils.getUserObjext().getName();
		
		try {
			if (moudbDetailForm.getIdx().equals("")) {
				moudbDetailForm.setWriter(mname);
				
				idx = getMoudbManager().create(moudbDetailForm);
			} else {
				moudbDetailForm.setWriter(mname);
				
				getMoudbManager().update(moudbDetailForm);
				idx = moudbDetailForm.getIdx();
			}
			
			AttachForm attachForm = (AttachForm) form;
			if (attachForm.getAttachFileId() != null && attachForm.getAttachFileId().length > 0) {
				this.getAttachManager().deleteAllInTask("moudb" + idx);
				attachForm.setTaskId("moudb" + idx);
				attachForm.setProjectCode("");
				attachForm.setTaskFormTypeId("");
				this.getAttachManager().insert(attachForm);
			}
			
			map.put("result", true);
			map.put("resultMsg", "save success");
			
			AjaxUtil.successWrite(response, map);
		} catch (CodeException e) {
			map.put("result", false);
			map.put("resultMsg", "save fail");
			map.put("errMsg", e);
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
	public void removeMoudb (ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		String idx = ServletRequestUtils.getRequiredStringParameter(request, "idx");
		
		try {
			getMoudbManager().remove(idx);
			
			JSONWriter writer = new JSONWriter();
			map.put("result", true);
			map.put("resultMsg", "delete success");
			
			AjaxUtil.successWrite(response, map);
		} catch (CodeException e) {
			map.put("result", false);
			map.put("resultMsg", "delete fail");
			map.put("ErrMsg", e);
			AjaxUtil.failWrite(response, map);
		}
	}
}
