/*
 * $Id: RepositoryDownLoadServlet.java,v 1.17 2018/01/17 08:57:27 cvs Exp $
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
import kr.co.kmac.pms.system.downloadlog.data.DownloadLogData;
import kr.co.kmac.pms.system.downloadlog.manager.DownloadLogManager;

import org.apache.catalina.connector.ClientAbortException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.HttpServletBean;

/**
 * 
 * @author jiwoong
 * @version $Id: RepositoryDownLoadServlet.java,v 1.17 2018/01/17 08:57:27 cvs Exp $
 */

public class RepositoryDownLoadServlet extends HttpServletBean {
	private static final long serialVersionUID = -8076969656288482933L;
	private static final int WEEK_LIMIT = 100;
	private static final int ALLTHETIME_LIMIT = 50;
	private static final int MONTH_LIMIT = 200;
	private Log logger = LogFactory.getLog(RepositoryDownLoadServlet.class);
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
			
			// jobDate: 2011-03-23	Author: yhyim	Description: blocking download for block user			
			if (expertPool.getBlockDownload().equals("Y")) {
				response.setHeader("Content-Type", "text/html; charset=euc-kr;");
				response.getWriter().println("<script>alert('다운로드 권한이 없습니다. 오영택 위원 (642, lokal07@kmac.co.kr) 에게 문의하시기 바랍니다.');</script>");
				return;
			}
			if (this.expertPoolManager.isDailyDownloadLimitUser(ssn)) {
				response.setHeader("Content-Type", "text/html; charset=euc-kr;");
				response.getWriter().println("<script>alert('다운로드 가능 횟수(주 "+WEEK_LIMIT+"건, 월 "+MONTH_LIMIT+"건)를 초과하였습니다. 추가 다운로드 희망 시 오영택 위원 (642, lokal07@kmac.co.kr)으로 신청 사유를 보내시기 바랍니다.');</script>");
				return;
			}
			
			if (fileId != null) {
				UploadFile uploadFile = this.repositoryManager.getFileByFileId(fileId);
				File file = new File(uploadFile.getFilePath() + "/" + uploadFile.getFileId());

				response.setHeader("Content-Type", StringUtil.isNull(uploadFile.getContentType(), "application/binary") + "; charset=euc-kr;");
				response.setHeader("Content-Disposition", "Attachment; filename=\""
						+ new String(uploadFile.getOrginalFileName().getBytes("EUC-KR"), "8859_1") + "\";");
				response.setHeader("Pragma", "cache;");
				response.setHeader("Cache-Control", "cache;");
				response.setHeader("Content-Length", "" + file.length());
				bos = new BufferedOutputStream(response.getOutputStream());
				bis = new BufferedInputStream(new FileInputStream(file));
				byte[] content = new byte[2048]; // buffer size 2K.

				int read = 0;
				while ((read = bis.read(content)) != -1) {
					bos.write(content, 0, read);
				}

				// 로그 기록 추가
				DownloadLogData downloadLogData = new DownloadLogData();
				downloadLogData.setFileId(fileId);
				downloadLogData.setFileName(uploadFile.getOrginalFileName());
				downloadLogData.setSsn(SessionUtils.getUsername(request));
				downloadLogData.setDownloadTime(StringUtil.getCurr("yyyyMMdd HH:mm:ss"));
				downloadLogData.setIp(request.getRemoteAddr());
				downloadLogData.setUploadUserId(uploadFile.getUserId());
				downloadLogManager.create(downloadLogData);

				bos.flush();
			} else {
				response.setHeader("Content-Type", "text/html; charset=euc-kr;");
				response.getWriter().println("<script>alert('해당 파일을 찾을 수 없습니다');</script>");
				return;
			}

			int weeklyCount = this.downloadLogManager.getWeeklyCount(SessionUtils.getUsername(request));
			int monthlyCount = this.downloadLogManager.getMonthlycount(SessionUtils.getUsername(request), StringUtil.getCurr("yyyyMM") + "01",
					StringUtil.getCurr("yyyyMMdd"));
			/* 다운로드 제한 적용 */
			if (weeklyCount == WEEK_LIMIT) {
				this.pmsInfoMailSender.sendDownloadInfo(SessionUtils.getUsername(request), weeklyCount);
				this.expertPoolManager.storeDailyDownloadLimitInfo(ssn);
			}
			if (monthlyCount == MONTH_LIMIT) {
				this.expertPoolManager.storeDailyDownloadLimitInfo(ssn);
			}
			/* 여기까지 */
			
			/* 다운로드 알림 메일 발송 */
			if (monthlyCount >= MONTH_LIMIT && monthlyCount % MONTH_LIMIT == 0) {
				this.pmsInfoMailSender.sendDownloadInfo(SessionUtils.getUsername(request), monthlyCount);
			}
			// jobDate: 2014-08-14 Description: 50건 단위로 월간 다운로드 발생 시 알림 메일 발송
			if (monthlyCount >= ALLTHETIME_LIMIT && monthlyCount % ALLTHETIME_LIMIT == 0) {
				this.pmsInfoMailSender.sendDownloadInfo(SessionUtils.getUsername(request), monthlyCount);
			}
			/* 여기까지 */
				
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
}
