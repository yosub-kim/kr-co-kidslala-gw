/*
 * $Id: PhotoDownLoadServlet.java,v 1.4 2017/01/16 05:13:00 cvs Exp $
 * created by    : jiwoong
 * creation-date : 2008. 9. 30.
 * =========================================================
 * Copyright (c) 2008 Maninsoft, Inc. All rights reserved.
 */
package kr.co.kmac.pms.common.repository.servlet;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.kmac.pms.common.repository.data.UploadFile;
import kr.co.kmac.pms.common.repository.exception.RepositoryException;
import kr.co.kmac.pms.common.repository.manager.IRepositoryManager;
import kr.co.kmac.pms.common.util.SessionUtils;
import kr.co.kmac.pms.common.util.StringUtil;
import kr.co.kmac.pms.expertpool.data.ExpertPool;
import kr.co.kmac.pms.expertpool.manager.ExpertPoolManager;
import kr.co.kmac.pms.service.mailsender.manager.PmsInfoMailSender;
import kr.co.kmac.pms.system.downloadlog.manager.DownloadLogManager;

import org.apache.catalina.connector.ClientAbortException;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.HttpServletBean;

/**
 * 
 * @author jiwoong
 * @version $Id: PhotoDownLoadServlet.java,v 1.4 2017/01/16 05:13:00 cvs Exp $
 */

public class PhotoDownLoadServlet extends HttpServletBean {
	private static final long serialVersionUID = -8076969656288482933L;
	private Log logger = LogFactory.getLog(PhotoDownLoadServlet.class);
	private IRepositoryManager repositoryManager;
	private DownloadLogManager downloadLogManager;
	private PmsInfoMailSender pmsInfoMailSender;
	private ExpertPoolManager expertPoolManager;

	protected void initServletBean() throws ServletException {
		ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
		this.repositoryManager = (IRepositoryManager) (context.getBean("repositoryManager"));
		this.downloadLogManager = (DownloadLogManager) (context.getBean("downloadLogManager"));
		this.pmsInfoMailSender = (PmsInfoMailSender) (context.getBean("pmsInfoMailSender"));
		this.expertPoolManager = (ExpertPoolManager) (context.getBean("expertPoolManager"));
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String fileId = request.getParameter("fileId");
		String isMobile = StringUtil.isNull(request.getParameter("isMobile"), "N");
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		String ssn = SessionUtils.getUsername(request);
		
		try {
			ExpertPool expertPool = this.expertPoolManager.retrieve(ssn);
			ExpertPool downloadExp = this.expertPoolManager.retrieve2(fileId);
			ExpertPool downloadCnt = this.expertPoolManager.retrieve3(fileId);
			String projectChkYN = this.expertPoolManager.selectProjectChk(fileId);
			String projectDeptChk = this.expertPoolManager.selectProjectDeptChk(fileId);
			// jobDate: 2021-05-11	Author : yskim	Description: 첨부파일 보안등급 추가
			if(Integer.parseInt(downloadCnt.getDownloadCnt()) != 0){
				if(("N").equals(downloadExp.getResultYN())){
					if(downloadExp.getAttachCreatorId().equals(ssn))
					{ System.out.println("본인 예외"); }
					else if (expertPool.getDept().equals("2000")||expertPool.getDept().equals("2040")||expertPool.getDept().equals("2050")||expertPool.getDept().equals("9360")||expertPool.getDept().equals("9361")
							||expertPool.getDept().equals("9362")||expertPool.getDept().equals("9370")||expertPool.getDept().equals("9371")||expertPool.getDept().equals("9381"))
					{ System.out.println("관리자 예외"); }
					else if (expertPool.getCompanyPosition().equals("08CF") || expertPool.getCompanyPosition().equals("09CJ") || expertPool.getCompanyPosition().equals("10TM") || expertPool.getCompanyPosition().equals("06CB"))
					{ System.out.println("보직자 예외");}
					else if (projectChkYN.equals(ssn))
					{ System.out.println("PM 예외");}
					else if (expertPool.getJobClass().equals("J")){
						String consultantDeptChk = this.expertPoolManager.selectConsultantDeptChk(ssn);
						if(projectDeptChk.equals(consultantDeptChk)){
							System.out.println("상임 예외");	
						}
					}else{
						response.setHeader("Content-Type", "text/html; charset=euc-kr;");
						response.getWriter().println("<script>alert('해당 파일은 비공개 되어 있어 다운로드 할 수 없습니다. 해당 파일  필요 시 프로젝트 PM 또는 지식혁신센터로 문의하여 주시기 바랍니다.');</script>");
						return;
						}
				}else{
					// 상임 컨설턴트 첨부파일 보안등급
					if (expertPool.getJobClass().equals("J"))
					{
						String consultantDeptChk = this.expertPoolManager.selectConsultantDeptChk(ssn);
						String fileProjectCode = this.expertPoolManager.selectFileProjectCode(fileId);
						String fileProjectChk = this.expertPoolManager.selectFileProjectChk(ssn, fileProjectCode);
						String pastFileProjectChk = this.expertPoolManager.selectPastFileProjectChk(fileId, ssn);
						if(projectDeptChk.equals(consultantDeptChk)){
							System.out.println("소속 부문 예외");
						}else if("Y".equals(fileProjectChk)){
							System.out.println("과거 프로젝트 투입 예외");
						}else if (fileProjectCode.equals("") || fileProjectCode.equals("8888888")){
							System.out.println("공지 예외");
						}else if ("Y".equals(pastFileProjectChk)){
							System.out.println("과거 부서 예외");
						}else{
							response.setHeader("Content-Type", "text/html; charset=euc-kr;");
							response.getWriter().println("<script>alert('타 부문 산출물은 다운로드 할 수 없습니다. 해당 파일 필요 시 프로젝트 PM 또는 지식혁신센터로  문의하여 주시기 바랍니다.');</script>");
							return;
						}
					}else{
						System.out.println("상임 외");
					}
				}
			}
			
			bos = new BufferedOutputStream(response.getOutputStream());
			
			if (fileId != null) {
				UploadFile uploadFile = this.repositoryManager.getFileByFileId(fileId);
				File file = new File(uploadFile.getFilePath() + "/" + uploadFile.getFileId());

				response.setHeader("Content-Type", StringUtil.isNull(uploadFile.getContentType(), "application/octet-stream ") + "; charset=euc-kr;");
				// response.setHeader("Content-Type", "application/octet-stream; charset=euc-kr;");

				if (isMobile.equals("Y")) {
					response.addHeader("Content-disposition", getDisposition(uploadFile.getOrginalFileName(), "Chrome"));
				} else {
					//response.addHeader("Content-disposition", getDisposition(uploadFile.getOrginalFileName(), getBrowser(request)));
					response.setHeader("Content-Disposition", "Attachment; filename=\""
							+ new String(uploadFile.getOrginalFileName().getBytes("EUC-KR"), "8859_1") + "\";");
				}
				response.setHeader("Pragma", "cache;");
				response.setHeader("Cache-Control", "cache;");
				response.setHeader("Content-Length", "" + file.length());
				response.setHeader("Content-Transfer-Encoding", "ISO-8859-1;");
				response.setHeader("Pragma", "no-cache;");
				response.setHeader("Expires", "-1;");

				bis = new BufferedInputStream(new FileInputStream(file));
				byte[] content = new byte[2048]; // buffer size 2K.

				int read = 0;
				while ((read = bis.read(content)) != -1) {
					bos.write(content, 0, read);
				}

				// if (condition) {
				// // 로그 기록 추가
				// DownloadLogData downloadLogData = new DownloadLogData();
				// downloadLogData.setFileId(fileId);
				// downloadLogData.setFileName(uploadFile.getOrginalFileName());
				// downloadLogData.setSsn(SessionUtils.getUsername(request));
				// downloadLogData.setDownloadTime(StringUtil.getCurr("yyyyMMdd HH:mm:ss"));
				// downloadLogData.setIp(request.getRemoteAddr());
				// downloadLogData.setUploadUserId(uploadFile.getUserId());
				// downloadLogManager.create(downloadLogData);
				// }
			} else {
				response.setHeader("Content-Type", "text/html; charset=euc-kr;");
				response.getWriter().println("<script>alert('해당 파일을 찾을 수 없습니다');</script>");
				return;
			}
			bos.flush();
		} catch (UnsupportedEncodingException e) {
			logger.error("Exception occured while download file ... 1: " + fileId);
			response.setHeader("Content-Type", "text/html; charset=euc-kr;");
			response.getWriter().println("<script>alert('해당 파일 다운로드 중 1번 오류가 발생하였습니다.');</script>");
			return;
		} catch (FileNotFoundException e) {
			logger.error("Exception occured while download file ... 2: " + fileId);
			response.setHeader("Content-Type", "text/html; charset=euc-kr;");
			response.getWriter().println("<script>alert('해당 파일 다운로드 중 2번 오류가 발생하였습니다.');</script>");
			return;
		} catch (ClientAbortException e) {
			return;
		} catch (IOException e) {
			logger.error("Exception occured while download file ... 3: " + fileId);
			response.setHeader("Content-Type", "text/html; charset=euc-kr;");
			response.getWriter().println("<script>alert('해당 파일 다운로드 중 3번 오류가 발생하였습니다.');</script>");
			return;
		} catch (RepositoryException e) {
			logger.error("Exception occured while download file ... 4: " + fileId, e);
			response.setHeader("Content-Type", "text/html; charset=euc-kr;");
			response.getWriter().println("<script>alert('해당 파일 다운로드 중 4번 오류가 발생하였습니다.');</script>");
			return;
		} catch (Exception e) {
			logger.error("Exception occured while download file ... 5: " + fileId, e);
			response.setHeader("Content-Type", "text/html; charset=euc-kr;");
			response.getWriter().println("<script>alert('해당 파일 다운로드 중 5번 오류가 발생하였습니다.');</script>");
			return;
		} finally {
			if (bis != null)
				bis.close();
			if (bos != null)
				bos.close();
		}
	}

	private String getBrowser(HttpServletRequest request) {
		String header = request.getHeader("User-Agent");
		if (header.indexOf("MSIE") > -1) {
			return "MSIE";
		} else if (header.indexOf("Chrome") > -1) {
			return "Chrome";
		} else if (header.indexOf("Opera") > -1) {
			return "Opera";
		}
		return "Firefox";
	}

	private String getDisposition(String filename, String browser) throws Exception {
		String dispositionPrefix = "attachment;filename=";
		String encodedFilename = null;
		if (browser.equals("MSIE")) {
			encodedFilename = URLEncoder.encode(filename, "UTF-8").replaceAll("\\+", "%20");
		} else if (browser.equals("Firefox")) {
			encodedFilename = "\"" + new String(filename.getBytes("UTF-8"), "8859_1") + "\"";
		} else if (browser.equals("Opera")) {
			encodedFilename = "\"" + new String(filename.getBytes("UTF-8"), "8859_1") + "\"";
		} else if (browser.equals("Chrome")) {
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < filename.length(); i++) {
				char c = filename.charAt(i);
				if (c > '~') {
					sb.append(URLEncoder.encode("" + c, "UTF-8"));
				} else {
					sb.append(c);
				}
			}
			encodedFilename = sb.toString();
		} else {
			throw new RuntimeException("Not supported browser");
		}
		return dispositionPrefix + encodedFilename;
	}

}
