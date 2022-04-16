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

			// jobDate: 2021-05-11	Author : yskim	Description: ÷������ ���ȵ�� �߰�
			if(Integer.parseInt(downloadCnt.getDownloadCnt()) != 0){
				if(("N").equals(downloadExp.getResultYN())){
					if(downloadExp.getAttachCreatorId().equals(ssn))
					{ System.out.println("���� ����"); }
					else if (expertPool.getDept().equals("2000")||expertPool.getDept().equals("2040")||expertPool.getDept().equals("2050")||expertPool.getDept().equals("9360")||expertPool.getDept().equals("9361")
							||expertPool.getDept().equals("9362")||expertPool.getDept().equals("9370")||expertPool.getDept().equals("9371")||expertPool.getDept().equals("9381"))
					{ System.out.println("������ ����"); }
					else if (expertPool.getCompanyPosition().equals("08CF") || expertPool.getCompanyPosition().equals("09CJ") || expertPool.getCompanyPosition().equals("10TM") || expertPool.getCompanyPosition().equals("06CB"))
					{ System.out.println("������ ����");}
					else if (projectChkYN.equals(ssn))
					{ System.out.println("PM ����");}
					else if (expertPool.getJobClass().equals("J")){
						String consultantDeptChk = this.expertPoolManager.selectConsultantDeptChk(ssn);
						if(projectDeptChk.equals(consultantDeptChk)){
							System.out.println("���� ����");	
						}
					}else{
						response.setHeader("Content-Type", "text/html; charset=euc-kr;");
						response.getWriter().println("<script>alert('�ش� ������ ����� �Ǿ� �־� �ٿ�ε� �� �� �����ϴ�. �ش� ����  �ʿ� �� ������Ʈ PM �Ǵ� �������ż��ͷ� �����Ͽ� �ֽñ� �ٶ��ϴ�.');</script>");
						return;
						}
				}else{
					// ���� ������Ʈ ÷������ ���ȵ��
					if (expertPool.getJobClass().equals("J"))
					{
						String consultantDeptChk = this.expertPoolManager.selectConsultantDeptChk(ssn);
						String fileProjectCode = this.expertPoolManager.selectFileProjectCode(fileId);
						String fileProjectChk = this.expertPoolManager.selectFileProjectChk(ssn, fileProjectCode);
						String pastFileProjectChk = this.expertPoolManager.selectPastFileProjectChk(fileId, ssn);
						if(projectDeptChk.equals(consultantDeptChk)){
							System.out.println("�Ҽ� �ι� ����");
						}else if("Y".equals(fileProjectChk)){
							System.out.println("���� ������Ʈ ���� ����");
						}else if (fileProjectCode.equals("") || fileProjectCode.equals("8888888")){
							System.out.println("���� ����");
						}else if ("Y".equals(pastFileProjectChk)){
							System.out.println("���� �μ� ����");
						}else{
							response.setHeader("Content-Type", "text/html; charset=euc-kr;");
							response.getWriter().println("<script>alert('Ÿ �ι� ���⹰�� �ٿ�ε� �� �� �����ϴ�. �ش� ���� �ʿ� �� ������Ʈ PM �Ǵ� �������ż��ͷ�  �����Ͽ� �ֽñ� �ٶ��ϴ�.');</script>");
							return;
						}
					}else{
						System.out.println("���� ��");
					}
				}
			}
			
			// jobDate: 2011-03-23	Author: yhyim	Description: blocking download for block user			
			if (expertPool.getBlockDownload().equals("Y")) {
				response.setHeader("Content-Type", "text/html; charset=euc-kr;");
				response.getWriter().println("<script>alert('�ٿ�ε� ������ �����ϴ�. ������ ���� (642, lokal07@kmac.co.kr) ���� �����Ͻñ� �ٶ��ϴ�.');</script>");
				return;
			}
			if (this.expertPoolManager.isDailyDownloadLimitUser(ssn)) {
				response.setHeader("Content-Type", "text/html; charset=euc-kr;");
				response.getWriter().println("<script>alert('�ٿ�ε� ���� Ƚ��(�� "+WEEK_LIMIT+"��, �� "+MONTH_LIMIT+"��)�� �ʰ��Ͽ����ϴ�. �߰� �ٿ�ε� ��� �� ������ ���� (642, lokal07@kmac.co.kr)���� ��û ������ �����ñ� �ٶ��ϴ�.');</script>");
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

				// �α� ��� �߰�
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
				response.getWriter().println("<script>alert('�ش� ������ ã�� �� �����ϴ�');</script>");
				return;
			}

			int weeklyCount = this.downloadLogManager.getWeeklyCount(SessionUtils.getUsername(request));
			int monthlyCount = this.downloadLogManager.getMonthlycount(SessionUtils.getUsername(request), StringUtil.getCurr("yyyyMM") + "01",
					StringUtil.getCurr("yyyyMMdd"));
			/* �ٿ�ε� ���� ���� */
			if (weeklyCount == WEEK_LIMIT) {
				this.pmsInfoMailSender.sendDownloadInfo(SessionUtils.getUsername(request), weeklyCount);
				this.expertPoolManager.storeDailyDownloadLimitInfo(ssn);
			}
			if (monthlyCount == MONTH_LIMIT) {
				this.expertPoolManager.storeDailyDownloadLimitInfo(ssn);
			}
			/* ������� */
			
			/* �ٿ�ε� �˸� ���� �߼� */
			if (monthlyCount >= MONTH_LIMIT && monthlyCount % MONTH_LIMIT == 0) {
				this.pmsInfoMailSender.sendDownloadInfo(SessionUtils.getUsername(request), monthlyCount);
			}
			// jobDate: 2014-08-14 Description: 50�� ������ ���� �ٿ�ε� �߻� �� �˸� ���� �߼�
			if (monthlyCount >= ALLTHETIME_LIMIT && monthlyCount % ALLTHETIME_LIMIT == 0) {
				this.pmsInfoMailSender.sendDownloadInfo(SessionUtils.getUsername(request), monthlyCount);
			}
			/* ������� */
				
		} catch (UnsupportedEncodingException e) {
			logger.error("Exception occured while download file ... 1: " + fileId);
			response.setHeader("Content-Type", "text/html; charset=euc-kr;");
			response.getWriter().println("<script>alert('�ش� ���� �ٿ�ε� �� 1�� ������ �߻��Ͽ����ϴ�.');</script>");
			return;
		} catch (FileNotFoundException e) {
			logger.error("Exception occured while download file ... 2: " + fileId);
			response.setHeader("Content-Type", "text/html; charset=euc-kr;");
			response.getWriter().println("<script>alert('�ش� ���� �ٿ�ε� �� 2�� ������ �߻��Ͽ����ϴ�.');</script>");
			return;
		} catch (ClientAbortException e) {
			return;
		} catch (IOException e) {
			logger.error("Exception occured while download file ... 3: " + fileId);
			response.setHeader("Content-Type", "text/html; charset=euc-kr;");
			response.getWriter().println("<script>alert('�ش� ���� �ٿ�ε� �� 3�� ������ �߻��Ͽ����ϴ�.');</script>");
			return;
		} catch (RepositoryException e) {
			logger.error("Exception occured while download file ... 4: " + fileId, e);
			response.setHeader("Content-Type", "text/html; charset=euc-kr;");
			response.getWriter().println("<script>alert('�ش� ���� �ٿ�ε� �� 4�� ������ �߻��Ͽ����ϴ�.');</script>");
			return;
		} catch (Exception e) {
			logger.error("Exception occured while download file ... 5: " + fileId, e);
			response.setHeader("Content-Type", "text/html; charset=euc-kr;");
			response.getWriter().println("<script>alert('�ش� ���� �ٿ�ε� �� 5�� ������ �߻��Ͽ����ϴ�.');</script>");
			return;
		} finally {
			if (bis != null)
				bis.close();
			if (bos != null)
				bos.close();
		}
	}
}
