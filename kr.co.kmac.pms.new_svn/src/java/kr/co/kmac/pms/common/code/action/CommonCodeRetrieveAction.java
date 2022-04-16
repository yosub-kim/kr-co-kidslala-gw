/*
 * $Id: CommonCodeRetrieveAction.java,v 1.1 2009/09/19 11:15:36 cvs3 Exp $
 * created by    : jiwoongLee
 * creation-date : 2006. 2. 23.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.common.code.action;

import java.util.HashMap;
import java.util.List;
import java.net.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.kmac.pms.common.ajax.AjaxUtil;
import kr.co.kmac.pms.common.ajax.JSONWriter;
import kr.co.kmac.pms.common.code.data.CodeEntity;
import kr.co.kmac.pms.common.code.manager.CommonCodeManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.struts.DispatchActionSupport;

/**
 * 공통 코드를 검색하기 위한 Action Class
 * 
 * @author jiwoongLee
 * @version $Id: CommonCodeRetrieveAction.java,v 1.1 2009/09/19 11:15:36 cvs3 Exp $
 */
/**
 * @struts.action name="commonCodeRetrieveAction" path="/action/CommonCodeRetrieveAction" parameter="mode" scope="request"
 */
public class CommonCodeRetrieveAction extends DispatchActionSupport {

	private static final Log logger = LogFactory.getLog(CommonCodeRetrieveAction.class);
	private CommonCodeManager commonCodeManager;

	/**
	 * @return the commonCodeManager
	 */
	public CommonCodeManager getCommonCodeManager() {
		return commonCodeManager;
	}

	/**
	 * @param commonCodeManager the commonCodeManager to set
	 */
	public void setCommonCodeManager(CommonCodeManager commonCodeManager) {
		this.commonCodeManager = commonCodeManager;
	}

	public void getCodeEntityList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HashMap map = new HashMap();
		try {
			String tableName = ServletRequestUtils.getRequiredStringParameter(request, "tableName");
			List<CodeEntity> codeList = this.getCommonCodeManager().getCodeEntityList(tableName);

			JSONWriter writer = new JSONWriter();
			map.put("returnValue", codeList);
			AjaxUtil.successWrite(response, map);
		} catch (Exception e) {
			logger.info(e.getMessage(), e);
			AjaxUtil.failWrite(response, map);
		}

	}

	public void getZipCodeEntityList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HashMap map = new HashMap();
		try {
			String tableName = ServletRequestUtils.getRequiredStringParameter(request, "tableName");
			String data3 = ServletRequestUtils.getRequiredStringParameter(request, "data3");
			data3 = URLDecoder.decode(data3, "UTF-8");
			data3 += "%";
			List<CodeEntity> codeList = this.getCommonCodeManager().getCodeEntityList2(tableName, data3);

			JSONWriter writer = new JSONWriter();
			map.put("returnValue", codeList);
			AjaxUtil.successWrite(response, map);
		} catch (Exception e) {
			logger.info(e.getMessage(), e);
			AjaxUtil.failWrite(response, map);
		}
	}
	
	public void getRelationWithKmacList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		HashMap map = new HashMap();
		try {
			String companyName = ServletRequestUtils.getRequiredStringParameter(request, "searchKey");
			companyName = URLDecoder.decode(companyName, "UTF-8");
			
			List codeList = this.getCommonCodeManager().getRelationWithKmacList(companyName);
		
			JSONWriter writer = new JSONWriter();
			map.put("returnValue", codeList);
			AjaxUtil.successWrite(response, map);
		} catch (Exception e) {
			logger.info(e.getMessage(), e);
			AjaxUtil.failWrite(response, map);
		}

	}

	
	

}
