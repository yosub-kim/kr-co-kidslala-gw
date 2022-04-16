/*
 * $Id: RepositoryTestAction.java,v 1.1 2009/09/19 11:15:38 cvs3 Exp $
 * created by    : jiwoongLee
 * creation-date : 2006. 2. 23.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.common.repository.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.kmac.pms.common.repository.data.UploadFile;
import kr.co.kmac.pms.common.util.StringUtil;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 공통 코드를 검색하기 위한 Action Class
 * 
 * @author jiwoongLee
 * @version $Id: RepositoryTestAction.java,v 1.1 2009/09/19 11:15:38 cvs3 Exp $
 */
/**
 * @struts.action name="repositoryTestAction" path="/action/RepositoryTestAction" parameter="mode" scope="request"
 * @struts.action-forward name="uploadTestInit" path="/common/repository/uploadTestInit.jsp" redirect="false"
 * @struts.action-forward name="uploadTestList" path="/common/repository/uploadTestList.jsp" redirect="false"
 * @struts.action-forward name="uploadTestResult" path="/common/repository/uploadTestResult.jsp" redirect="false"
 */
public class RepositoryTestAction extends RepositoryDispatchActionSupport {

	// private static final Log logger = LogFactory.getLog(RepositoryTestAction.class);

	public ActionForward uploadTestInit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("uploadTestInit");
	}

	public ActionForward uploadTestList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("uploadTestList");
	}

	public ActionForward uploadTestResult(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String groupId = StringUtil.isNull(request.getParameter("groupId"), "");
		groupId = super.fileUpload(form, request, groupId, null);
		List<UploadFile> list = super.getFileGroup(groupId);

		if (list != null && list.size() > 0) {
			for (UploadFile file : list) {
				System.out.println("groupid=" + file.getGroupId());
				System.out.println("fileid=" + file.getFileId());

			}
		} 

		request.setAttribute("attachType", "insert");
		request.setAttribute("fileList", list);

		return mapping.findForward("uploadTestResult");
	}

}
