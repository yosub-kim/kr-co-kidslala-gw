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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.kmac.pms.common.repository.data.UploadFile;
import kr.co.kmac.pms.common.repository.exception.RepositoryException;
import kr.co.kmac.pms.common.repository.manager.IRepositoryManager;
import kr.co.kmac.pms.common.util.SessionUtils;
import kr.co.kmac.pms.expertpool.data.ExpertPool;
import kr.co.kmac.pms.expertpool.manager.ExpertPoolManager;
import kr.co.kmac.pms.service.mailsender.manager.PmsInfoMailSender;
import kr.co.kmac.pms.system.downloadlog.manager.DownloadLogManager;

import org.apache.catalina.connector.ClientAbortException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
//import org.apache.poi.hssf.usermodel.HSSFCell;
//import org.apache.poi.hssf.usermodel.HSSFRow;
//import org.apache.poi.hssf.usermodel.HSSFSheet;
//import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.HttpServletBean;

/**
 * 
 * @author jiwoong
 * @version $Id: RepositoryDownLoadServlet.java,v 1.17 2018/01/17 08:57:27 cvs Exp $
 */

public class RepositoryDownLoadServlet2 extends HttpServletBean {
	
	private static final long serialVersionUID = -8076969656288482933L;
	private static final int WEEK_LIMIT = 100;
	private static final int ALLTHETIME_LIMIT = 50;
	private static final int MONTH_LIMIT = 200;
	private Log logger = LogFactory.getLog(RepositoryDownLoadServlet2.class);
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

	@SuppressWarnings("resource")
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String fileId = request.getParameter("fileId");
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		String ssn = SessionUtils.getUsername(request);
		try {
			// jobDate: 2011-03-23	Author: yhyim	Description: blocking download for block user			
			ExpertPool expertPool = this.expertPoolManager.retrieve(ssn);
			/*if (expertPool.getBlockDownload().equals("Y")) {
				response.setHeader("Content-Type", "text/html; charset=euc-kr;");
				response.getWriter().println("<script>alert('�ٿ�ε� ������ �����ϴ�. ����� ������(632, jwjeong@kmac.co.kr), ������ ���� (642, lokal07@kmac.co.kr) ���� �����Ͻñ� �ٶ��ϴ�.');</script>");
				return;
			}
			if (this.expertPoolManager.isDailyDownloadLimitUser(ssn)) {
				response.setHeader("Content-Type", "text/html; charset=euc-kr;");
				response.getWriter().println("<script>alert('�ٿ�ε� ���� Ƚ��(�� "+WEEK_LIMIT+"��, �� "+MONTH_LIMIT+"��)�� �ʰ��Ͽ����ϴ�. �߰� �ٿ�ε� ��� �� ����� ������(jwjeong@kmac.co.kr)���� ��û ������ �����ñ� �ٶ��ϴ�.');</script>");
				return;
			}*/
			
			if (fileId != null) {
				UploadFile uploadFile = this.repositoryManager.getFileByFileId(fileId);
				File file = new File(uploadFile.getFilePath() + "/" + uploadFile.getFileId());
				response.setHeader("Content-Type", "text/html; charset=euc-kr;");
				/*response.setHeader("Content-Type", StringUtil.isNull(uploadFile.getContentType(), "application/binary") + "; charset=euc-kr;");
				response.setHeader("Content-Disposition", "Attachment; filename=\""
						+ new String(uploadFile.getOrginalFileName().getBytes("EUC-KR"), "8859_1") + "\";");
				response.setHeader("Pragma", "cache;");
				response.setHeader("Cache-Control", "cache;");
				response.setHeader("Content-Length", "" + file.length());*/
				/*bos = new BufferedOutputStream(response.getOutputStream());
				bis = new BufferedInputStream(new FileInputStream(file));
				byte[] content = new byte[2048]; // buffer size 2K.
				
				File file2 = new File(uploadFile.getFilePath() + "/" + uploadFile.getFileId()); 
				
				int read = 0;
				while ((read = bis.read(content)) != -1) {
					bos.write(content, 0, read);
				}*/

				/* ���� ��ȯ �� ���� */
				String oriFilePath = uploadFile.getFilePath() + "/" + uploadFile.getFileId();
				String copyFilePath = "Z:/PMS-REPO/TAXFILE/"+uploadFile.getFileId()+".xls";
				File oriFile = new File(oriFilePath);
		        File copyFile = new File(copyFilePath);
		        FileInputStream fiss = new FileInputStream(oriFile); //��������
	            FileOutputStream fos = new FileOutputStream(copyFile); //����������
	            int fileByte = 0; 
	            while((fileByte = fiss.read()) != -1) {
	                fos.write(fileByte);
	            }
	            fiss.close();
	            fos.close();
				
	            /* ���� ���� �о�帮�� */
	            FileInputStream fis=new FileInputStream(copyFilePath);
	            try{
//	            	HSSFWorkbook workbook = new HSSFWorkbook(fis);
//	            	int rowindex=0;
//	            	int columnindex=0;
//	            	//��Ʈ �� (ù��°���� �����ϹǷ� 0�� �ش�)
//	            	//���� �� ��Ʈ�� �б����ؼ��� FOR���� �ѹ��� �����ش�
//	            	HSSFSheet sheet=workbook.getSheetAt(0);
//	            	//���� ��
//	            	int rows=sheet.getPhysicalNumberOfRows();
//	            	for(rowindex=1;rowindex<rows;rowindex++){
//	            	    //���� �д´�
//	            	    HSSFRow row=sheet.getRow(rowindex);
//	            	    if(row !=null){
//	            	        //���� ��
//	            	        int cells=row.getPhysicalNumberOfCells();
//	            	        String[] colValue = new String[6];
//	            	        for(columnindex=0;columnindex<=5;columnindex++){
//	            	            //������ �д´�
//	            	            HSSFCell cell=row.getCell(columnindex);
//	            	            String value="";
//	            	            //���� ���ϰ�츦 ���� ��üũ
//	            	            if(cell==null){
//	            	                continue;
//	            	            }else{
//	            	            	try{
//	            	            		value = cell.getStringCellValue() + "";
//	            	            	}catch(IllegalStateException e){
//	            	            		value = Integer.toString((int)cell.getNumericCellValue());
//	            	            	}
//	                             }
//	            	            /*System.out.println("�� �� ���� :"+value);*/
//	            	            colValue[columnindex] = value;
//	            	           /* System.out.println(columnindex+"��° : "+colValue[columnindex]);*/
//	            	            }
//	            	        this.expertPoolManager.storeHomeTaxValue(colValue[0], colValue[1], colValue[2], colValue[3], colValue[4], colValue[5]);
//	            	        }
//	            	}
	            }catch(Exception e) {
	                e.printStackTrace();
	            }
				// �α� ��� �߰�
				/*DownloadLogData downloadLogData = new DownloadLogData();
				downloadLogData.setFileId(fileId);
				downloadLogData.setFileName(uploadFile.getOrginalFileName());
				downloadLogData.setSsn(SessionUtils.getUsername(request));
				downloadLogData.setDownloadTime(StringUtil.getCurr("yyyyMMdd HH:mm:ss"));
				downloadLogData.setIp(request.getRemoteAddr());
				downloadLogData.setUploadUserId(uploadFile.getUserId());
				downloadLogManager.create(downloadLogData);

				bos.flush();*/
			} else {
				response.setHeader("Content-Type", "text/html; charset=euc-kr;");
				return;
			}
			/*
			int weeklyCount = this.downloadLogManager.getWeeklyCount(SessionUtils.getUsername(request));
			int monthlyCount = this.downloadLogManager.getMonthlycount(SessionUtils.getUsername(request), StringUtil.getCurr("yyyyMM") + "01",
					StringUtil.getCurr("yyyyMMdd"));
			 �ٿ�ε� ���� ���� 
			if (weeklyCount == WEEK_LIMIT) {
				this.pmsInfoMailSender.sendDownloadInfo(SessionUtils.getUsername(request), weeklyCount);
				this.expertPoolManager.storeDailyDownloadLimitInfo(ssn);
			}
			if (monthlyCount == MONTH_LIMIT) {
				this.expertPoolManager.storeDailyDownloadLimitInfo(ssn);
			}
			 ������� 
			
			 �ٿ�ε� �˸� ���� �߼� 
			if (monthlyCount >= MONTH_LIMIT && monthlyCount % MONTH_LIMIT == 0) {
				this.pmsInfoMailSender.sendDownloadInfo(SessionUtils.getUsername(request), monthlyCount);
			}
			// jobDate: 2014-08-14 Description: 50�� ������ ���� �ٿ�ε� �߻� �� �˸� ���� �߼�
			if (monthlyCount >= ALLTHETIME_LIMIT && monthlyCount % ALLTHETIME_LIMIT == 0) {
				this.pmsInfoMailSender.sendDownloadInfo(SessionUtils.getUsername(request), monthlyCount);
			}
			*/
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
