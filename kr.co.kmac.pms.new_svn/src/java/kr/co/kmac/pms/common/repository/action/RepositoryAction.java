package kr.co.kmac.pms.common.repository.action;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.kmac.pms.common.ajax.AjaxUtil;
import kr.co.kmac.pms.common.repository.data.UploadFile;
import kr.co.kmac.pms.common.repository.exception.RepositoryException;
import kr.co.kmac.pms.common.util.RepositoryUtil;
import kr.co.kmac.pms.common.util.SessionUtils;
import kr.co.kmac.pms.common.util.StringUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;

/**
 * @struts.action name="repositoryAction" path="/action/RepositoryAction" parameter="mode" scope="request"
 */
public class RepositoryAction extends RepositoryDispatchActionSupport {
	private static final Log logger = LogFactory.getLog(RepositoryAction.class);

	/**
	 * 파일 첨부 메소드 FIXME 파일 첨부 수정 시 데이터 처리
	 * 
	 * @param request
	 * @param groupId
	 * @param delFileList
	 * @return 파일이 저장된 그룹의 아이디
	 * @throws RepositoryException
	 */
	public void fileUpload(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();

		String groupId = request.getParameter("groupId");
		if (RepositoryUtil.isMultiPart(request)) {

			groupId = (StringUtil.isNull(groupId, "")).equals("") ? RepositoryUtil.getFileId() : groupId;
			List<UploadFile> listFile = new ArrayList<UploadFile>();

			UploadFile uploadFile = null;
			try {
				MultipartFile multiFile = super.getMultipartResolver().resolveMultipart(request).getFile("userfile");
				if (!multiFile.isEmpty()) {
					uploadFile = new UploadFile();
					uploadFile.setGroupId(groupId);
					uploadFile.setFileId(RepositoryUtil.getFileId());
					uploadFile.setFileInputName("userfile");
					uploadFile.setOrginalFileName(multiFile.getOriginalFilename());
					uploadFile.setFilePath(this.getFilePath());
					uploadFile.setFileSize(multiFile.getSize());
					uploadFile.setContentType(multiFile.getContentType());
					uploadFile.setInputStream(multiFile.getInputStream());
					uploadFile.setUserId(SessionUtils.getUserObjext().getId());
					uploadFile.setUserName(SessionUtils.getUserObjext().getName());
					listFile.add(uploadFile);
				}

				List<UploadFile> delList = new ArrayList<UploadFile>();

				String[] delFileList = request.getParameterValues("");
				if (delFileList == null || delFileList.length == 0) {
					delFileList = request.getParameterValues("deleteFileId");
					if (delFileList != null && delFileList.length > 0) {
						for (int i = 0; i < delFileList.length; i++) {
							delList.add(this.getRepositoryManager().getFileByFileId(delFileList[i]));
						}
					}
				}

				// DB에 파일 내용 저장
				if (listFile != null && listFile.size() > 0) {
					this.getRepositoryManager().saveFile(listFile, delFileList);
				}
				// 파일 시스템에 파일 작성
				//RepositoryUtil.writeFile(uploadFile);
				
				File uploadDir = new File(uploadFile.getFilePath());
				if (!uploadDir.exists()) {
					uploadDir.mkdirs();
				}
				multiFile.transferTo(new File(uploadFile.getFilePath() + "/" + uploadFile.getFileId()));

				map.put("file", uploadFile);
				AjaxUtil.successWrite(response, map);
			} catch (MultipartException e) {
				logger.error(e.getMessage(), e);
				map.put("errorLog", e.getMessage());
				AjaxUtil.failWrite(response, map);
				// throw new RepositoryException(e);
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
				map.put("errorLog", e.getMessage());
				AjaxUtil.failWrite(response, map);
				// throw new RepositoryException(e);
			}
		}
	}

	public void expertPhotoUpload(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();

		if (RepositoryUtil.isMultiPart(request)) {
			String groupId = RepositoryUtil.getFileId();
			List<UploadFile> listFile = new ArrayList<UploadFile>();

			UploadFile uploadFile = null;
			try {
				MultipartFile multiFile = super.getMultipartResolver().resolveMultipart(request).getFile("photoFile");
				if (!multiFile.isEmpty()) {
					uploadFile = new UploadFile();
					uploadFile.setGroupId(groupId);
					uploadFile.setFileId(RepositoryUtil.getFileId());
					uploadFile.setFileInputName("userfile");
					uploadFile.setOrginalFileName(multiFile.getOriginalFilename());
					uploadFile.setFilePath(this.getFilePath());
					uploadFile.setFileSize(multiFile.getSize());
					uploadFile.setContentType(multiFile.getContentType());
					uploadFile.setInputStream(multiFile.getInputStream());
					uploadFile.setUserId(SessionUtils.getUserObjext().getId());
					uploadFile.setUserName(SessionUtils.getUserObjext().getName());
					listFile.add(uploadFile);
				}

				List<UploadFile> delList = new ArrayList<UploadFile>();

				String[] delFileList = request.getParameterValues("");
				if (delFileList == null || delFileList.length == 0) {
					delFileList = request.getParameterValues("deleteFileId");
					if (delFileList != null && delFileList.length > 0) {
						for (int i = 0; i < delFileList.length; i++) {
							delList.add(this.getRepositoryManager().getFileByFileId(delFileList[i]));
						}
					}
				}

				// DB에 파일 내용 저장
				if (listFile != null && listFile.size() > 0) {
					this.getRepositoryManager().saveFile(listFile, delFileList);
				}
				// 파일 시스템에 파일 작성
				RepositoryUtil.writeFile(uploadFile);

				map.put("file", uploadFile);
				AjaxUtil.successWrite(response, map);
			} catch (MultipartException e) {
				logger.error(e.getMessage(), e);
				map.put("errorLog", e.getMessage());
				AjaxUtil.failWrite(response, map);
				// throw new RepositoryException(e);
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
				map.put("errorLog", e.getMessage());
				AjaxUtil.failWrite(response, map);
				// throw new RepositoryException(e);
			}
		}
	}

	public void deleteFile(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String fileId = request.getParameter("fileId");
		try {
			//System.out.println(fileId);
			UploadFile delFile = this.getRepositoryManager().getFileByFileId(fileId);
			this.getRepositoryManager().deleteFile(fileId);
			this.getRepositoryManager().deleteProjectTaskFormAttachData(fileId);
			//RepositoryUtil.deleteFile(delFile.getFilePath(), delFile.getOrginalFileName());
			AjaxUtil.successWrite(response);
		} catch (Exception e) {
			AjaxUtil.failWrite(response);
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 파일 검색 메소드
	 * 
	 * @param fileId
	 * @return
	 * @throws RepositoryException
	 */
	public void getFileByFileId(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		String fileId = request.getParameter("fileId");
		try {
			UploadFile file = this.getRepositoryManager().getFileByFileId(fileId);
			map.put("file", file);
			AjaxUtil.successWrite(response, map);
		} catch (Exception e) {
			AjaxUtil.failWrite(response);
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 파일 검색 메소드
	 * 
	 * @param fileName
	 * @return
	 * @throws RepositoryException
	 */
	public void getFileByFileNamed(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		String fileName = request.getParameter("fileName");
		try {
			List<UploadFile> file = this.getRepositoryManager().getFileByFileName(fileName);
			map.put("fileList", file);
			AjaxUtil.successWrite(response, map);
		} catch (Exception e) {
			AjaxUtil.failWrite(response);
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 파일 검색 메소드
	 * 
	 * @param groupId
	 * @return
	 * @throws RepositoryException
	 */
	public void getFileGroupd(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		String groupId = request.getParameter("groupId");
		try {
			List<UploadFile> file = this.getRepositoryManager().getFileGroup(groupId);
			map.put("fileList", file);
			AjaxUtil.successWrite(response, map);
		} catch (Exception e) {
			AjaxUtil.failWrite(response);
			logger.error(e.getMessage(), e);
		}
	}

}
