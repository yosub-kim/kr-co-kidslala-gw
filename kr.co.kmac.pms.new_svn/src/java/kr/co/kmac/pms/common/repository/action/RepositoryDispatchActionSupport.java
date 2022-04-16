package kr.co.kmac.pms.common.repository.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import kr.co.kmac.pms.common.repository.data.UploadFile;
import kr.co.kmac.pms.common.repository.exception.RepositoryException;
import kr.co.kmac.pms.common.repository.manager.IRepositoryManager;
import kr.co.kmac.pms.common.util.DateTime;
import kr.co.kmac.pms.common.util.RepositoryUtil;
import kr.co.kmac.pms.common.util.SessionUtils;
import kr.co.kmac.pms.common.util.StringUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;
import org.apache.struts.upload.MultipartRequestHandler;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.struts.DispatchActionSupport;

public abstract class RepositoryDispatchActionSupport extends DispatchActionSupport {
	private static final Log logger = LogFactory.getLog(RepositoryDispatchActionSupport.class);
	private IRepositoryManager repositoryManager;
	private CommonsMultipartResolver multipartResolver;
	private String filePath;
	private String photoPath;

	/**
	 * 파일 첨부 메소드
	 * 
	 * @param request
	 * @return 파일이 저장된 그룹의 아이디
	 * @throws RepositoryException
	 */
	public String fileUpload(ActionForm form, HttpServletRequest request) throws RepositoryException {
		return this.fileUpload(form, request, null);
	}

	/**
	 * 파일 첨부 메소드
	 * 
	 * @param request
	 * @param groupId
	 * @return 파일이 저장된 그룹의 아이디
	 * @throws RepositoryException
	 */
	public String fileUpload(ActionForm form, HttpServletRequest request, String groupId) throws RepositoryException {
		return this.fileUpload(form, request, groupId, null);
	}

	/**
	 * 파일 첨부 메소드 FIXME 파일 첨부 수정 시 데이터 처리
	 * 
	 * @param request
	 * @param groupId
	 * @param delFileList
	 * @return 파일이 저장된 그룹의 아이디
	 * @throws RepositoryException
	 */
	@SuppressWarnings("unchecked")
	public String fileUpload(ActionForm form, HttpServletRequest request, String groupId, String[] delFileList) throws RepositoryException {
		groupId = (StringUtil.isNull(groupId, "")).equals("") ? RepositoryUtil.getFileId() : groupId;
		List<UploadFile> listFile = new ArrayList<UploadFile>();
		if (!RepositoryUtil.isMultiPart(request))
			return null;

		try {
			MultipartRequestHandler handler = form.getMultipartRequestHandler();
			Hashtable fileMap = handler.getFileElements();

			Iterator<String> it = fileMap.keySet().iterator();
			while (it.hasNext()) {
				String fieldName = it.next();
				FormFile formFile = (FormFile) fileMap.get(fieldName);
				if (!formFile.getFileName().equals("")) {
					UploadFile uploadFile = new UploadFile();
					uploadFile.setGroupId(groupId);
					uploadFile.setFileId(RepositoryUtil.getFileId());
					uploadFile.setFileInputName(fieldName);
					uploadFile.setOrginalFileName(formFile.getFileName());
					uploadFile.setFilePath(this.getFilePath());
					uploadFile.setFileSize(formFile.getFileSize());
					uploadFile.setContentType(formFile.getContentType());
					uploadFile.setInputStream(formFile.getInputStream());
					uploadFile.setUserId(SessionUtils.getUserObjext().getId());
					uploadFile.setUserName(SessionUtils.getUserObjext().getName());
					listFile.add(uploadFile);
				}
			}

			List<UploadFile> delList = new ArrayList<UploadFile>();

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
			for (UploadFile uploadFile : listFile) {
				RepositoryUtil.writeFile(uploadFile);
			}
			for (UploadFile uploadFile : delList) {
				RepositoryUtil.deleteFile(uploadFile.getFilePath(), uploadFile.getOrginalFileName());
			}
		} catch (MultipartException e) {
			logger.error(e.getMessage(), e);
			throw new RepositoryException(e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			throw new RepositoryException(e);
		}

		return groupId;

	}

	public List<UploadFile> fileUploadObj(ActionForm form, HttpServletRequest request) throws RepositoryException {
		List<UploadFile> listFile = new ArrayList<UploadFile>();
		if (!RepositoryUtil.isMultiPart(request))
			return null;

		try {
			MultipartRequestHandler handler = form.getMultipartRequestHandler();
			Hashtable fileMap = handler.getFileElements();

			Iterator<String> it = fileMap.keySet().iterator();
			while (it.hasNext()) {
				String fieldName = it.next();
				FormFile formFile = (FormFile) fileMap.get(fieldName);
				if (!formFile.getFileName().equals("")) {
					UploadFile uploadFile = new UploadFile();
					uploadFile.setFileId(RepositoryUtil.getFileId());
					uploadFile.setFileInputName(fieldName);
					uploadFile.setOrginalFileName(formFile.getFileName());
					uploadFile.setFilePath(this.getFilePath());
					uploadFile.setFileSize(formFile.getFileSize());
					uploadFile.setContentType(formFile.getContentType());
					uploadFile.setInputStream(formFile.getInputStream());
					uploadFile.setUserId(SessionUtils.getUserObjext().getId());
					uploadFile.setUserName(SessionUtils.getUserObjext().getName());
					listFile.add(uploadFile);
				}
			}

		} catch (MultipartException e) {
			logger.error(e.getMessage(), e);
			throw new RepositoryException(e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			throw new RepositoryException(e);
		}

		return listFile;

	}

	/**
	 * 파일 검색 메소드
	 * 
	 * @param fileId
	 * @return
	 * @throws RepositoryException
	 */
	public UploadFile getFileByFileId(String fileId) throws RepositoryException {
		return this.getRepositoryManager().getFileByFileId(fileId);
	}

	/**
	 * 파일 검색 메소드
	 * 
	 * @param fileName
	 * @return
	 * @throws RepositoryException
	 */
	public List<UploadFile> getFileByFileName(String fileName) throws RepositoryException {
		return this.getRepositoryManager().getFileByFileName(fileName);
	}

	/**
	 * 파일 검색 메소드
	 * 
	 * @param groupId
	 * @return
	 * @throws RepositoryException
	 */
	public List<UploadFile> getFileGroup(String groupId) throws RepositoryException {
		return this.getRepositoryManager().getFileGroup(groupId);
	}

	/**
	 * @return Returns the filePath.
	 */
	public String getFilePath() {
		return filePath + DateTime.getYear() + "/" + DateTime.getMonth();
	}

	/**
	 * @param filePath The filePath to set.
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	/**
	 * @return Returns the repositoryManager.
	 */
	public IRepositoryManager getRepositoryManager() {
		return repositoryManager;
	}

	/**
	 * @param repositoryManager The repositoryManager to set.
	 */
	public void setRepositoryManager(IRepositoryManager repositoryManager) {
		this.repositoryManager = repositoryManager;
	}

	/**
	 * @return the multipartResolver
	 */
	public CommonsMultipartResolver getMultipartResolver() {
		return multipartResolver;
	}

	/**
	 * @param multipartResolver the multipartResolver to set
	 */
	public void setMultipartResolver(CommonsMultipartResolver multipartResolver) {
		this.multipartResolver = multipartResolver;
	}

	/**
	 * @return the photoPath
	 */
	public String getPhotoPath() {
		return photoPath;
	}

	/**
	 * @param photoPath the photoPath to set
	 */
	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}

}
