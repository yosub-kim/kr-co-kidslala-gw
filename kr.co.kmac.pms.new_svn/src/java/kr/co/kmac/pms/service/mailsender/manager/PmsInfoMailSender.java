/*
 * $Id: PmsInfoMailSender.java,v 1.36 2018/11/08 19:08:44 cvs Exp $
 * created by    : jiwoongLee
 * creation-date : 2006. 10. 8.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.service.mailsender.manager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import kr.co.kmac.pms.board.data.BoardData;
import kr.co.kmac.pms.common.util.DateTime;
import kr.co.kmac.pms.common.util.DateUtil;
import kr.co.kmac.pms.common.util.StringUtil;
import kr.co.kmac.pms.expertpool.manager.ExpertPoolManager;
import kr.co.kmac.pms.orgdb.form.OrgdbDetailForm;
import kr.co.kmac.pms.project.master.data.ProjectCsrInfo;
import kr.co.kmac.pms.project.master.data.ProjectSimpleInfo;
import kr.co.kmac.pms.project.master.manager.ProjectMasterInfoManager;
import kr.co.kmac.pms.project.statistics.data.SalaryMailingList;
import kr.co.kmac.pms.project.voc.data.ProjectVocSendingInfoData;
import kr.co.kmac.pms.project.voc.manager.ProjectVocManager;
import kr.co.kmac.pms.sanction.general.action.GeneralSanctionAction;
import kr.co.kmac.pms.sanction.general.data.SanctionConstants;
import kr.co.kmac.pms.sanction.general.data.SanctionDoc;
import kr.co.kmac.pms.sanction.general.data.SanctionDocCC;
import kr.co.kmac.pms.sanction.preport.data.ProjectReportContent;
import kr.co.kmac.pms.service.mailsender.data.MailContents;

import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
// JobDate: 2012-01-10

public class PmsInfoMailSender {

	private static final Log logger = LogFactory.getLog(GeneralSanctionAction.class);
	private MailSender mailSender;
	private SimpleMailMessage simpleMailMessage;
	private ExpertPoolManager expertPoolManager;
	private ProjectVocManager projectVocManager;
	private ProjectMasterInfoManager projectMasterInfoManager;
	private MailContents mailContents;
	
	private PmsInfoMailSenderManager pmsInfoMailSenderManager;

	public void sendSanctonInfo(String assigneeUserName, String assigneeUserPos, String assignerUserName, String assignerUserPos,
			SanctionDoc sanctionDoc) throws Exception {
		final SimpleMailMessage simpleMailMessage = getSimpleMailMessage();
		simpleMailMessage.setFrom("PMS@kmac.co.kr");
		// pmsrenewal ?????? ?????? ??????
		simpleMailMessage.setTo(getMailReceiver(sanctionDoc));
		//simpleMailMessage.setTo("yskim@kmac.co.kr");
		simpleMailMessage.setSentDate(new Date());
		// ?????? ????????? ?????? ????????? ???????????? ?????? 
		String notice = "";
		if (!sanctionDoc.getRejectReason().equals("")) 
			notice = "(?????? ?????? ??????)";
		if (sanctionDoc.getIsApproved().equalsIgnoreCase("N")) {
			simpleMailMessage.setSubject("[???????????? ??????] " + sanctionDoc.getSubject() + "-" + assignerUserPos + " " + assignerUserName + " ??????");
		} else {
			simpleMailMessage.setSubject("[???????????? ??????] " + sanctionDoc.getSubject() + "-" + assignerUserPos + " " + assignerUserName + " ?????? " + notice);
		}
		simpleMailMessage.setText(getMailContent(assignerUserName, assignerUserPos, sanctionDoc));

		new Thread("sendSanctonInfo"){
	        public void run(){
	        	getMailSender().send(simpleMailMessage);
	        }
	      }.start();
	}

	public void sendSanctonRefInfo(List<SanctionDocCC> sanctionDocCCs, String assignerUserName, String assignerUserPos, SanctionDoc sanctionDoc)
			throws Exception {
		final SimpleMailMessage simpleMailMessage = getSimpleMailMessage();
		simpleMailMessage.setFrom("PMS@kmac.co.kr");
		simpleMailMessage.setTo(getMailReceiver(sanctionDocCCs));
		simpleMailMessage.setSentDate(new Date());
		simpleMailMessage.setSubject("[???????????? ??????] " + sanctionDoc.getSubject());
		simpleMailMessage.setText(getRefMailContent(assignerUserName, assignerUserPos, sanctionDoc));

		new Thread("sendSanctonInfo"){
	        public void run(){
	        	getMailSender().send(simpleMailMessage);
	        }
	      }.start();
	}

	private String[] getMailReceiver(List<SanctionDocCC> sanctionDocCCs) {
		String[] assigneeEmail = new String[sanctionDocCCs.size()];
		for (int i = 0; i < sanctionDocCCs.size(); i++) {
			assigneeEmail[i] = getExpertPoolManager().getEmail(sanctionDocCCs.get(i).getRefMemberSsn());
		}
		return assigneeEmail;
	}

	private String getMailContent(String assignerUserName, String assignerUserPos, SanctionDoc sanctionDoc) {
		StringBuffer msg = new StringBuffer();
		msg.append("\n???????????? : " + DateTime.getYear() + "??? " + DateTime.getMonth() + "??? " + DateTime.getDay() + "???\n");
		msg.append("?????? : " + sanctionDoc.getSubject() + "\n");
		if (sanctionDoc.getApprovalType().equals("A") || sanctionDoc.getApprovalType().equals("PA")){
			ProjectSimpleInfo projectSimpleInfo = this.getProjectMasterInfoManager().getProjectSimpleInfo(sanctionDoc.getProjectCode());
			msg.append("?????????????????? : [" + sanctionDoc.getProjectCode() + "]  " + projectSimpleInfo.getProjectName()+"\n");
		}
		if (sanctionDoc.getIsApproved().equalsIgnoreCase("N")) {
			msg.append(assignerUserPos + " : " + assignerUserName).append("?????? ?????? ???????????????.");
			msg.append("\n\n");
			msg.append("--- ?????? ?????? ---\n");
			msg.append(sanctionDoc.getRejectReasonView());
		} else {
			msg.append(assignerUserPos + " : " + assignerUserName).append("?????? ?????? ???????????????.");
			msg.append("\n\n");
			msg.append("--- ?????? ?????? ---\n");
			msg.append(sanctionDoc.getRejectReasonView());
		}
		return msg.toString();
	}
	
	private String getRefMailContent(String assignerUserName, String assignerUserPos, SanctionDoc sanctionDoc) {
		StringBuffer msg = new StringBuffer();
		msg.append("\n???????????? :" + DateTime.getYear() + "??? " + DateTime.getMonth() + "??? " + DateTime.getDay() + "???\n");
		msg.append("?????? :" + sanctionDoc.getSubject() + "\n");
		msg.append(assignerUserPos + ": " + assignerUserName).append("?????? ?????? ???????????????.");
		msg.append("\n\n");
		msg.append("--- ?????? ?????? ---\n");
		msg.append(sanctionDoc.getRejectReasonView());
		msg.append("\n");
		msg.append("--- ?????? ?????? ---\n\n");
		msg.append(sanctionDoc.getContent());
		return msg.toString();
	}
	
	private String[] getMailReceiver(SanctionDoc sanctionDoc) {
		String register = sanctionDoc.getRegisterSsn();
		String teamManager = sanctionDoc.getTeamManagerSsn();
		String cfo = sanctionDoc.getCfoSsn();
		String cio = sanctionDoc.getCioSsn();
		String assistant1 = StringUtil.isNull(sanctionDoc.getAssistant1Ssn(), null);
		String assistant2 = StringUtil.isNull(sanctionDoc.getAssistant2Ssn(), null);
		String assistant3 = StringUtil.isNull(sanctionDoc.getAssistant3Ssn(), null);

		List<String> toAddressList = new ArrayList<String>();
		String[] toAddress = null;
		if (sanctionDoc.getState().equals(SanctionConstants.SANCTION_STATE_REVIEW)) {// ??????
			toAddressList.add(getExpertPoolManager().getEmail(register));
		} else if (sanctionDoc.getState().equals(SanctionConstants.SANCTION_STATE_CHECK)) {// ?????????
			if (StringUtil.isNotNull(register)) {
				toAddressList.add(getExpertPoolManager().getEmail(register));
			}
			if (StringUtil.isNotNull(teamManager)) {
				toAddressList.add(getExpertPoolManager().getEmail(teamManager));
			}
		} else if (sanctionDoc.getState().equals(SanctionConstants.SANCTION_STATE_APPROVE)) {// CBO
			if (StringUtil.isNotNull(register)) {
				toAddressList.add(getExpertPoolManager().getEmail(register));
			}
			if (StringUtil.isNotNull(teamManager)) {
				toAddressList.add(getExpertPoolManager().getEmail(teamManager));
			}
			if (StringUtil.isNotNull(cfo)) {
				toAddressList.add(getExpertPoolManager().getEmail(cfo));
			}
		} else if (sanctionDoc.getState().equals(SanctionConstants.SANCTION_STATE_ASSIST1)) {// ??????1
			if (StringUtil.isNotNull(register)) {
				toAddressList.add(getExpertPoolManager().getEmail(register));
			}
			if (StringUtil.isNotNull(teamManager)) {
				toAddressList.add(getExpertPoolManager().getEmail(teamManager));
			}
			if (StringUtil.isNotNull(cfo)) {
				toAddressList.add(getExpertPoolManager().getEmail(cfo));
			}
			if (StringUtil.isNotNull(cio)) {
				toAddressList.add(getExpertPoolManager().getEmail(cio));
			}
		} else if (sanctionDoc.getState().equals(SanctionConstants.SANCTION_STATE_ASSIST2)) {// ??????2
			if (StringUtil.isNotNull(register)) {
				toAddressList.add(getExpertPoolManager().getEmail(register));
			}
			if (StringUtil.isNotNull(teamManager)) {
				toAddressList.add(getExpertPoolManager().getEmail(teamManager));
			}
			if (StringUtil.isNotNull(cfo)) {
				toAddressList.add(getExpertPoolManager().getEmail(cfo));
			}
			if (StringUtil.isNotNull(cio)) {
				toAddressList.add(getExpertPoolManager().getEmail(cio));
			}
			if (StringUtil.isNotNull(assistant1)) {
				toAddressList.add(getExpertPoolManager().getEmail(assistant1));
			}
		} else if (sanctionDoc.getState().equals(SanctionConstants.SANCTION_STATE_ASSIST3)) {// ??????3
			if (StringUtil.isNotNull(register)) {
				toAddressList.add(getExpertPoolManager().getEmail(register));
			}
			if (StringUtil.isNotNull(teamManager)) {
				toAddressList.add(getExpertPoolManager().getEmail(teamManager));
			}
			if (StringUtil.isNotNull(cfo)) {
				toAddressList.add(getExpertPoolManager().getEmail(cfo));
			}
			if (StringUtil.isNotNull(cio)) {
				toAddressList.add(getExpertPoolManager().getEmail(cio));
			}
			if (StringUtil.isNotNull(assistant1)) {
				toAddressList.add(getExpertPoolManager().getEmail(assistant1));
			}
			if (StringUtil.isNotNull(assistant2)) {
				toAddressList.add(getExpertPoolManager().getEmail(assistant2));
			}
		} else if (sanctionDoc.getState().equals(SanctionConstants.SANCTION_STATE_CEO)) {// ceo ??????
			if (StringUtil.isNotNull(register)) {
				toAddressList.add(getExpertPoolManager().getEmail(register));
			}
			if (StringUtil.isNotNull(teamManager)) {
				toAddressList.add(getExpertPoolManager().getEmail(teamManager));
			}
			if (StringUtil.isNotNull(cfo)) {
				toAddressList.add(getExpertPoolManager().getEmail(cfo));
			}
			if (StringUtil.isNotNull(cio)) {
				toAddressList.add(getExpertPoolManager().getEmail(cio));
			}
			if (StringUtil.isNotNull(assistant1)) {
				toAddressList.add(getExpertPoolManager().getEmail(assistant1));
			}
			if (StringUtil.isNotNull(assistant2)) {
				toAddressList.add(getExpertPoolManager().getEmail(assistant2));
			}
			if (StringUtil.isNotNull(assistant3)) {
				toAddressList.add(getExpertPoolManager().getEmail(assistant3));
			}
		}
		toAddress = new String[toAddressList.size()];
		for (int i = 0; i < toAddressList.size(); i++) {
			toAddress[i] = toAddressList.get(i);
		}
		return toAddress;
	}

	// ???????????? ?????? ?????? ????????? ?????? ?????? ??????
	public void sendDownloadInfo(String userId, int downloadCount) throws Exception {
		SimpleMailMessage simpleMailMessage = getSimpleMailMessage();
		simpleMailMessage.setFrom("PMS@kmac.co.kr");
		simpleMailMessage.setTo(getMailReceiver());
		simpleMailMessage.setSentDate(new Date());
		simpleMailMessage.setSubject("[???????????? ??????] ???????????? ?????? ????????????");
		simpleMailMessage.setText(getMailContent(getExpertPoolManager().getName(userId), String.valueOf(downloadCount)));
	
		getMailSender().send(simpleMailMessage);
	}
	
	public void sendSanctonRefInfoOrg(OrgdbDetailForm orgdbDetailForm, String updateYN) throws Exception {
		final SimpleMailMessage simpleMailMessage = getSimpleMailMessage();
		simpleMailMessage.setFrom("PMS@kmac.co.kr");
		simpleMailMessage.setTo("mwji@kmac.co.kr");
		simpleMailMessage.setSentDate(new Date());
		simpleMailMessage.setSubject("[????????? ??????] " + orgdbDetailForm.getOrgName());
		simpleMailMessage.setText(getMailOrgContent(orgdbDetailForm, updateYN));

		new Thread("sendSanctonInfo"){
	        public void run(){
	        	getMailSender().send(simpleMailMessage);
	        }
	      }.start();
	}
	
	private String getMailOrgContent(OrgdbDetailForm orgdbDetailForm, String updateYN) {
		StringBuffer msg = new StringBuffer();
		msg.append("?????? : " + orgdbDetailForm.getOrgName() + "\n");
		msg.append("????????? : " + orgdbDetailForm.getPmName() + "\n");
		if(updateYN.equals("Y")){
			/*msg.append("?????? : " + orgdbDetailForm.getCreateDate() + "\n");*/
			msg.append("?????? : ????????????"+"\n");
		}else{
			/*msg.append("?????? : " + orgdbDetailForm.getModifyDate() + "\n");*/
			msg.append("?????? : ??????"+"\n");
		}
		return msg.toString();
	}
	
	// JobDate: 2012-01-10	Author: yhyim	Description: ??????????????? ?????? ?????? ????????? ????????? return
	private String[] getCCMailReceiver(String projectCode) {
		List<String> toAddressList = new ArrayList<String>();
		String[] toAddress = null;
		toAddressList = this.getPmsInfoMailSenderManager().selectCCAddress(projectCode);
		
		toAddress = new String[toAddressList.size()];
		for (int i = 0; i < toAddressList.size(); i++) {
			toAddress[i] = toAddressList.get(i);
		}
		return toAddress;
	}
	
	// JobDate: 2012-01-10	Author: yhyim	Description: ??????????????? ?????? ?????? ?????? ????????? ????????? return
	private String[] getBCCMailReceiver() {
		List<String> toAddressList = new ArrayList<String>();
		String[] toAddress = null;
		
		toAddressList = this.getPmsInfoMailSenderManager().selectBCCAddress();
		
		toAddress = new String[toAddressList.size()];
		for (int i = 0; i < toAddressList.size(); i++) {
			toAddress[i] = toAddressList.get(i);
		}
		return toAddress;
	}

	// Job Date: 2007-12-20 Author: yhyim Description: ???????????? ????????? ????????? return
	private String[] getMailReceiver() {
		String toAddress[] = new String[1];
		toAddress[0] = "mailadmin@kmac.co.kr";
		return toAddress;
	}

	// Job Date: 2008-02-19 Author: yhyim Description: ???????????? ?????? ??????
	private String getMailContent(String userName, String count) {
		StringBuffer msg = new StringBuffer();
		msg.append("????????? " + userName + " ?????? ??????????????? ");
		msg.append(count + " ??? ???????????????. " + "\n");
		msg.append("???????????? ????????? ????????? ????????? ????????????. \n");
		return msg.toString();
	}

	// Job Date: 2009-04-18 Author: jwlee Description: ????????? ??????????????? ?????? ?????? ??????
	public void sendRndCSMail(String projectCode) throws Exception {
		this.sendProjectCSMail(projectCode);
	}

	// Job Date: 2009-04-18 Author: jwlee Description: ????????? ??????????????? ?????? ?????? ??????
	public void sendConsultingCSMail(String projectCode) throws Exception {
		this.sendProjectCSMail(projectCode);
	}

	// Job Date: 2009-04-18 Author: jwlee Description: ???????????? ??????????????? ?????? ?????? ??????
	public void sendCompanyEduCSMail(String projectCode) throws Exception {
		this.sendProjectCSMail(projectCode);
	}
	
	// TODO
	// ????????????
	public void sendPublicEduCSMail(String projectCode) throws Exception {
		this.sendProjectCSMail(projectCode);
	}
	
	// ???????????????(KCSMA) ??????????????? ?????? ??????
	public void sendKCSMACSMail(String projectCode) throws Exception {
		this.sendKCSMAProjectCSMail(projectCode);
	}

	// Job Date: 2011-05-15 Author: jwlee Description: ?????????/????????? ??????????????? ?????? ?????? ??????
	public void sendProjectCSMail(String projectCode) throws Exception {
		ProjectSimpleInfo projectSimpleInfo = this.getProjectMasterInfoManager().getProjectSimpleInfo(projectCode);
		List<ProjectCsrInfo> projectCsrInfoList = this.getProjectMasterInfoManager().getProjectAllCsrInfo(projectCode);

		JavaMailSenderImpl sender = new JavaMailSenderImpl();
		sender.setHost("webmail.kmac.co.kr");
		MimeMessage message = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
		helper.setFrom(new InternetAddress("cs@kmac.co.kr", "KMAC", "UTF-8"));
		helper.setBcc("mailadmin@kmac.co.kr");
		helper.setSentDate(new Date());
		String sendString = "";
		String procFile = "icsi1";
		if(projectSimpleInfo.getProcessTypeCode().equals("N1") || projectSimpleInfo.getProcessTypeCode().equals("N2")){
			procFile = "eduicsi1";
		}
		if (projectCsrInfoList.size() > 0)
			for (ProjectCsrInfo projectCsrInfo : projectCsrInfoList) {
				if (projectSimpleInfo.getLang() == null || projectSimpleInfo.getLang().equals("")) {
					helper.setSubject("KMAC?????? ????????? ?????? ???????????? ????????? ????????? ????????? ????????? ????????????.");
					sendString = getMailContents().getContentInfo("projectCSMailContentKOR");
					sendString = StringUtil.replace(sendString, "[$projectName]", projectSimpleInfo.getProjectName());
				} else if (projectSimpleInfo.getLang().equals("KOR")) {
					helper.setSubject("KMAC?????? ????????? ?????? ???????????? ????????? ????????? ????????? ????????? ????????????.");
					sendString = getMailContents().getContentInfo("projectCSMailContentKOR");
					sendString = StringUtil.replace(sendString, "[$projectName]", projectSimpleInfo.getProjectName());
				} else if (projectSimpleInfo.getLang().equals("ENG")) {
					helper.setSubject("We deeply appreciate if you complete the project satisfaction survey.");
					sendString = getMailContents().getContentInfo("projectCSMailContentENG");
					sendString = StringUtil.replace(sendString, "[$projectName]", projectSimpleInfo.getProjectSubName());
				/*} else if (projectSimpleInfo.getLang().equals("JPN")) {
					helper.setSubject("???????????????????????????????????????????????????????????????");
					sendString = getMailContents().getContentInfo("projectCSMailContentJPN");
					sendString = StringUtil.replace(sendString, "[$projectName]", projectSimpleInfo.getProjectSubName());		*/
				} else if (projectSimpleInfo.getLang().equals("CHN")) {
					helper.setSubject("?????????????????????????????????KMAC???????????????????????????????????????");
					
					sendString = getMailContents().getContentInfo("projectCSMailContentCHN");
					sendString = StringUtil.replace(sendString, "[$projectName]", projectSimpleInfo.getProjectSubName());
				}
				sendString = StringUtil.replace(sendString, "[$receiveEmail]", projectCsrInfo.getCustomerWorkPEmail());
				sendString = StringUtil.replace(sendString, "[$seq]", String.valueOf(projectCsrInfo.getSeq()));
				sendString = StringUtil.replace(sendString, "[$procFile]", procFile);
				sendString = StringUtil.replace(sendString, "[$projectCode]", projectSimpleInfo.getProjectCode());
				sendString = StringUtil.replace(sendString, "[$startDate]", projectSimpleInfo.getStartDate().substring(0, 4) + "-" + projectSimpleInfo.getStartDate().substring(4, 6) + "-" + projectSimpleInfo.getStartDate().substring(6, 8));
				sendString = StringUtil.replace(sendString, "[$endDate]", projectSimpleInfo.getEndDate().substring(0, 4) + "-" + projectSimpleInfo.getEndDate().substring(4, 6) + "-" + projectSimpleInfo.getEndDate().substring(6, 8));
				helper.setTo(projectCsrInfo.getCustomerWorkPEmail());
				helper.setText(sendString, true);
				sender.send(message);
				this.projectMasterInfoManager.updateProjectCsrInfo(projectCsrInfo);
			}
	}
	
	
	public void sendKCSMAProjectCSMail(String projectCode) throws Exception {
		ProjectSimpleInfo projectSimpleInfo = this.getProjectMasterInfoManager().getProjectSimpleInfo(projectCode);
		List<ProjectCsrInfo> projectCsrInfoList = this.getProjectMasterInfoManager().getProjectAllCsrInfo(projectCode);

		JavaMailSenderImpl sender = new JavaMailSenderImpl();
		sender.setHost("webmail.kmac.co.kr");
		MimeMessage message = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
		helper.setFrom(new InternetAddress("cs@kcsma.or.kr", "??????????????????????????????", "UTF-8"));
		helper.setBcc("mailadmin@kmac.co.kr");
		helper.setSentDate(new Date());
		String sendString = "";
		String procFile = "icsi1_kcsma";
		if(projectSimpleInfo.getProcessTypeCode().equals("N1") || projectSimpleInfo.getProcessTypeCode().equals("N2")){
			procFile = "eduicsi1";
		}
		if (projectCsrInfoList.size() > 0)
			for (ProjectCsrInfo projectCsrInfo : projectCsrInfoList) {
				if (projectSimpleInfo.getLang() == null || projectSimpleInfo.getLang().equals("")) {
					helper.setSubject("???????????????????????????????????? ????????? ?????? ???????????? ????????? ????????? ????????? ????????? ????????????.");
					sendString = getMailContents().getContentInfo("KCSMAprojectCSMailContentKOR");
					sendString = StringUtil.replace(sendString, "[$projectName]", projectSimpleInfo.getProjectName());
				} else if (projectSimpleInfo.getLang().equals("KOR")) {
					helper.setSubject("???????????????????????????????????? ????????? ?????? ???????????? ????????? ????????? ????????? ????????? ????????????.");
					sendString = getMailContents().getContentInfo("KCSMAprojectCSMailContentKOR");
					sendString = StringUtil.replace(sendString, "[$projectName]", projectSimpleInfo.getProjectName());
				} else if (projectSimpleInfo.getLang().equals("ENG")) {
					helper.setSubject("We deeply appreciate if you complete the project satisfaction survey.");
					sendString = getMailContents().getContentInfo("projectCSMailContentENG");
					sendString = StringUtil.replace(sendString, "[$projectName]", projectSimpleInfo.getProjectSubName());
				} else if (projectSimpleInfo.getLang().equals("CHN")) {
					helper.setSubject("?????????????????????????????????KCSMA???????????????????????????????????????");
					
					sendString = getMailContents().getContentInfo("projectCSMailContentCHN");
					sendString = StringUtil.replace(sendString, "[$projectName]", projectSimpleInfo.getProjectSubName());
				}
				sendString = StringUtil.replace(sendString, "[$receiveEmail]", projectCsrInfo.getCustomerWorkPEmail());
				sendString = StringUtil.replace(sendString, "[$seq]", String.valueOf(projectCsrInfo.getSeq()));
				sendString = StringUtil.replace(sendString, "[$procFile]", procFile);
				sendString = StringUtil.replace(sendString, "[$projectCode]", projectSimpleInfo.getProjectCode());
				sendString = StringUtil.replace(sendString, "[$startDate]", projectSimpleInfo.getStartDate().substring(0, 4) + "-" + projectSimpleInfo.getStartDate().substring(4, 6) + "-" + projectSimpleInfo.getStartDate().substring(6, 8));
				sendString = StringUtil.replace(sendString, "[$endDate]", projectSimpleInfo.getEndDate().substring(0, 4) + "-" + projectSimpleInfo.getEndDate().substring(4, 6) + "-" + projectSimpleInfo.getEndDate().substring(6, 8));
				helper.setTo(projectCsrInfo.getCustomerWorkPEmail());
				helper.setText(sendString, true);
				sender.send(message);
				this.projectMasterInfoManager.updateProjectCsrInfo(projectCsrInfo);
			}
	}

	// Job Date: 2011-05-15 Author: jwlee Description: ?????????/????????? ??????????????? ?????? ?????? ??? ??????
	public void sendProjectCSMailAgain(String projectCode, int customerSeq) throws Exception {
		ProjectSimpleInfo projectSimpleInfo = this.getProjectMasterInfoManager().getProjectSimpleInfo(projectCode);
		ProjectCsrInfo projectCsrInfo = this.getProjectMasterInfoManager().getProjectCsrInfo(projectCode, customerSeq);
		
		JavaMailSenderImpl sender = new JavaMailSenderImpl();
		sender.setHost("webmail.kmac.co.kr");
		MimeMessage message = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
		helper.setFrom(new InternetAddress("cs@kmac.co.kr", "KMAC", "UTF-8"));
		helper.setBcc("mailadmin@kmac.co.kr");
		helper.setSentDate(new Date());
		String sendString = "";
		String procFile = "icsi1";
		if(projectSimpleInfo.getProcessTypeCode().equals("N1") || projectSimpleInfo.getProcessTypeCode().equals("N2")){
			procFile = "eduicsi1";
		}
		if (projectSimpleInfo.getLang() == null || projectSimpleInfo.getLang().equals("")) {
			helper.setSubject("[?????????] KMAC?????? ????????? ?????? ???????????? ????????? ????????? ????????? ????????? ????????????.");
			sendString = getMailContents().getContentInfo("projectCSMailContentKOR");
			sendString = StringUtil.replace(sendString, "[$projectName]", projectSimpleInfo.getProjectName());
		} else if (projectSimpleInfo.getLang().equals("KOR")) {
			helper.setSubject("[?????????] KMAC?????? ????????? ?????? ???????????? ????????? ????????? ????????? ????????? ????????????.");
			sendString = getMailContents().getContentInfo("projectCSMailContentKOR");
			sendString = StringUtil.replace(sendString, "[$projectName]", projectSimpleInfo.getProjectName());
		} else if (projectSimpleInfo.getLang().equals("ENG")) {
			helper.setSubject("We deeply appreciate if you complete the project satisfaction survey.");
			sendString = getMailContents().getContentInfo("projectCSMailContentENG");
			sendString = StringUtil.replace(sendString, "[$projectName]", projectSimpleInfo.getProjectSubName());
		/*} else if (projectSimpleInfo.getLang().equals("JPN")) {
			helper.setSubject("???????????????????????????????????????????????????????????????");
			sendString = getMailContents().getContentInfo("projectCSMailContentJPN");
			sendString = StringUtil.replace(sendString, "[$projectName]", projectSimpleInfo.getProjectSubName());		*/
		} else if (projectSimpleInfo.getLang().equals("CHN")) {
			helper.setSubject("?????????????????????????????????KMAC???????????????????????????????????????");
			sendString = getMailContents().getContentInfo("projectCSMailContentCHN");
			sendString = StringUtil.replace(sendString, "[$projectName]", projectSimpleInfo.getProjectSubName());
		}
		sendString = StringUtil.replace(sendString, "[$projectCode]", projectSimpleInfo.getProjectCode());
		sendString = StringUtil.replace(sendString, "[$procFile]", procFile);
		sendString = StringUtil.replace(sendString, "[$seq]", String.valueOf(projectCsrInfo.getSeq()));
		sendString = StringUtil.replace(sendString, "[$receiveEmail]", projectCsrInfo.getCustomerWorkPEmail());
		sendString = StringUtil.replace(sendString, "[$startDate]", projectSimpleInfo.getStartDate().substring(0, 4) + "-" + projectSimpleInfo.getStartDate().substring(4, 6) + "-" + projectSimpleInfo.getStartDate().substring(6, 8));
		sendString = StringUtil.replace(sendString, "[$endDate]", projectSimpleInfo.getEndDate().substring(0, 4) + "-" + projectSimpleInfo.getEndDate().substring(4, 6) + "-" + projectSimpleInfo.getEndDate().substring(6, 8));
		helper.setTo(projectCsrInfo.getCustomerWorkPEmail());
		helper.setText(sendString, true);
		sender.send(message);

		this.projectMasterInfoManager.updateProjectCsrInfo(projectCsrInfo);
	}
	
	
	// Job Date: 2011-05-15 Author: jwlee Description: ?????????/????????? ??????????????? ?????? ?????? ??? ??????
		public void sendKCSMAProjectCSMailAgain(String projectCode, int customerSeq) throws Exception {
			ProjectSimpleInfo projectSimpleInfo = this.getProjectMasterInfoManager().getProjectSimpleInfo(projectCode);
			ProjectCsrInfo projectCsrInfo = this.getProjectMasterInfoManager().getProjectCsrInfo(projectCode, customerSeq);
			
			JavaMailSenderImpl sender = new JavaMailSenderImpl();
			sender.setHost("webmail.kmac.co.kr");
			MimeMessage message = sender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
			helper.setFrom(new InternetAddress("cs@kcsma.or.kr", "??????????????????????????????", "UTF-8"));
			helper.setBcc("mailadmin@kmac.co.kr");
			helper.setSentDate(new Date());
			String sendString = "";
			String procFile = "icsi1_kcsma";
			if(projectSimpleInfo.getProcessTypeCode().equals("N1") || projectSimpleInfo.getProcessTypeCode().equals("N2")){
				procFile = "eduicsi1";
			}
			if (projectSimpleInfo.getLang() == null || projectSimpleInfo.getLang().equals("")) {
				helper.setSubject("[?????????] ???????????????????????????????????? ????????? ?????? ???????????? ????????? ????????? ????????? ????????? ????????????.");
				sendString = getMailContents().getContentInfo("KCSMAprojectCSMailContentKOR");
				sendString = StringUtil.replace(sendString, "[$projectName]", projectSimpleInfo.getProjectName());
			} else if (projectSimpleInfo.getLang().equals("KOR")) {
				helper.setSubject("[?????????] ???????????????????????????????????? ????????? ?????? ???????????? ????????? ????????? ????????? ????????? ????????????.");
				sendString = getMailContents().getContentInfo("KCSMAprojectCSMailContentKOR");
				sendString = StringUtil.replace(sendString, "[$projectName]", projectSimpleInfo.getProjectName());
			} else if (projectSimpleInfo.getLang().equals("ENG")) {
				helper.setSubject("We deeply appreciate if you complete the project satisfaction survey.");
				sendString = getMailContents().getContentInfo("projectCSMailContentENG");
				sendString = StringUtil.replace(sendString, "[$projectName]", projectSimpleInfo.getProjectSubName());
			/*} else if (projectSimpleInfo.getLang().equals("JPN")) {
				helper.setSubject("???????????????????????????????????????????????????????????????");
				sendString = getMailContents().getContentInfo("projectCSMailContentJPN");
				sendString = StringUtil.replace(sendString, "[$projectName]", projectSimpleInfo.getProjectSubName());		*/
			} else if (projectSimpleInfo.getLang().equals("CHN")) {
				helper.setSubject("?????????????????????????????????KCSMA???????????????????????????????????????");
				sendString = getMailContents().getContentInfo("projectCSMailContentCHN");
				sendString = StringUtil.replace(sendString, "[$projectName]", projectSimpleInfo.getProjectSubName());
			}
			sendString = StringUtil.replace(sendString, "[$projectCode]", projectSimpleInfo.getProjectCode());
			sendString = StringUtil.replace(sendString, "[$procFile]", procFile);
			sendString = StringUtil.replace(sendString, "[$seq]", String.valueOf(projectCsrInfo.getSeq()));
			sendString = StringUtil.replace(sendString, "[$receiveEmail]", projectCsrInfo.getCustomerWorkPEmail());
			sendString = StringUtil.replace(sendString, "[$startDate]", projectSimpleInfo.getStartDate().substring(0, 4) + "-" + projectSimpleInfo.getStartDate().substring(4, 6) + "-" + projectSimpleInfo.getStartDate().substring(6, 8));
			sendString = StringUtil.replace(sendString, "[$endDate]", projectSimpleInfo.getEndDate().substring(0, 4) + "-" + projectSimpleInfo.getEndDate().substring(4, 6) + "-" + projectSimpleInfo.getEndDate().substring(6, 8));
			helper.setTo(projectCsrInfo.getCustomerWorkPEmail());
			helper.setText(sendString, true);
			sender.send(message);

			this.projectMasterInfoManager.updateProjectCsrInfo(projectCsrInfo);
		}
	
	// Job Date: 2011-05-15 Author: jwlee Description: ???????????? ?????? ??????
	public void sendCustomerThx(String projectCode) throws Exception {
		ProjectSimpleInfo projectSimpleInfo = this.getProjectMasterInfoManager().getProjectSimpleInfo(projectCode);
		List<ProjectCsrInfo> projectCsrInfoList = this.getProjectMasterInfoManager().getProjectCsrInfo(projectCode);
		
		if (projectSimpleInfo.getBusinessTypeCode().equals("BTA") || projectSimpleInfo.getBusinessTypeCode().equals("BTD")) {	
			JavaMailSenderImpl sender = new JavaMailSenderImpl();
			sender.setHost("webmail.kmac.co.kr");
			MimeMessage message = sender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
			helper.setFrom(new InternetAddress("cs@kmac.co.kr", "KMAC", "UTF-8"));
			//helper.setBcc("mailadmin@kmac.co.kr");
			helper.setSentDate(new Date());
			String sendString = "";
			if (projectSimpleInfo.getLang() == null || projectSimpleInfo.getLang().equals("")) {
				helper.setSubject(projectSimpleInfo.getProjectName());
				sendString = getMailContents().getContentInfo("customerThxKOR");
				sendString = StringUtil.replace(sendString, "[$projectName]", projectSimpleInfo.getProjectName());
				sendString = StringUtil.replace(sendString, "[$projectCode]", projectSimpleInfo.getProjectCode());
				sendString = StringUtil.replace(sendString, "[$email]", projectSimpleInfo.getCustomerEmail());
			} else if (projectSimpleInfo.getLang().equals("KOR")) {
				helper.setSubject("???????????? ???????????? ?????? ??? ????????? ?????? ??????");
				sendString = getMailContents().getContentInfo("customerThxKOR");
				sendString = StringUtil.replace(sendString, "[$projectName]", projectSimpleInfo.getProjectName());
				sendString = StringUtil.replace(sendString, "[$projectCode]", projectSimpleInfo.getProjectCode());
				sendString = StringUtil.replace(sendString, "[$email]", projectSimpleInfo.getCustomerEmail());
			} else if (projectSimpleInfo.getLang().equals("ENG")) {
				helper.setSubject("Project VOC & Assessment");
				sendString = getMailContents().getContentInfo("customerThxENG");
				sendString = StringUtil.replace(sendString, "[$projectName]", projectSimpleInfo.getProjectSubName());
			/*} else if (projectSimpleInfo.getLang().equals("JPN")) {
				helper.setSubject(projectSimpleInfo.getProjectSubName());
				sendString = getMailContents().getContentInfo("customerThxJPN");
				sendString = StringUtil.replace(sendString, "[$projectName]", projectSimpleInfo.getProjectSubName());*/
			} else if (projectSimpleInfo.getLang().equals("CHN")) {
				helper.setSubject("???????????????????????? ??? ????????????");
				sendString = getMailContents().getContentInfo("customerThxCHN");
				sendString = StringUtil.replace(sendString, "[$projectName]", projectSimpleInfo.getProjectSubName());
				sendString = StringUtil.replace(sendString, "[$projectCode]", projectSimpleInfo.getProjectCode());
				sendString = StringUtil.replace(sendString, "[$email]", projectSimpleInfo.getCustomerEmail());
			}
			helper.setText(sendString, true);
	
			// ?????? ???????????? ?????? ?????? ??????
			if (projectCsrInfoList.size() > 0)
				for (ProjectCsrInfo projectCsrInfo : projectCsrInfoList) {
					helper.setTo(projectCsrInfo.getCustomerWorkPEmail());
					sender.send(message);
					projectCsrInfo.setProjectName(projectSimpleInfo.getProjectName());
					projectCsrInfo.setRunningDivCode(projectSimpleInfo.getRunningDivCode());
					this.projectMasterInfoManager.insertProjectThxInfo(projectCsrInfo);
				}
		}
	}
	
	public void sendSalaryNoticeEmail(String ssn, String year, String month, List<SalaryMailingList> salaryList) throws Exception {
		JavaMailSenderImpl sender = new JavaMailSenderImpl();
		sender.setHost("webmail.kmac.co.kr");
		MimeMessage message = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
		helper.setFrom(new InternetAddress("cs@kmac.co.kr", "KMAC", "UTF-8"));
		helper.addBcc("mailadmin@kmac.co.kr");
		helper.addBcc("gadi@kmac.co.kr");
		helper.setSentDate(new Date());
		String sendString = "";
		
		sendString = getMailContents().getContentInfo("salaryMailingContent");
		sendString = StringUtil.replace(sendString, "[$year]", year);
		sendString = StringUtil.replace(sendString, "[$month]", month);

		String amountText = "";
		String totalAmount = "";
		String toAddress = "";
		String toName = "";
		if (salaryList.size() > 0)
			for (SalaryMailingList str : salaryList) {
				amountText += "<tr><td style='padding: 5px 0px; text-align: center; border-right-color: rgb(204, 204, 204); "
						+ "border-bottom-color: rgb(204, 204, 204); border-right-width: 1px; border-bottom-width: 1px; border-right-style: solid; "
						+ "border-bottom-style: solid;'>" + str.getProjectName()
						+ "</td><td style='text-align: right; border-bottom-color: rgb(204, 204, 204); "
						+ "border-bottom-width: 1px; border-bottom-style: solid;'><span style='padding-right:10px'>" 
						+ StringUtil.longt2Money(str.getRealTimeSalaryEachProject()) + " ???</td></tr>";	
		
				
				totalAmount = StringUtil.longt2Money(str.getTotalRealTimeSalary());
				toAddress = str.getEmail();
				toName = str.getName();
			}
		sendString = StringUtil.replace(sendString, "[$amount]", amountText);
		String salaryGrandTotalText = "<tr><td style='padding: 5px 0px; text-align: center; border-right-color: rgb(204, 204, 204); border-bottom-color: rgb(204, 204, 204); border-right-width: 1px; border-bottom-width: 1px; border-right-style: solid; border-bottom-style: solid; background-color: rgb(255, 246, 247);'><strong>??????</strong></td><td style='text-align: right; margin-right: 10px; border-bottom-color: rgb(204, 204, 204); border-bottom-width: 1px; border-bottom-style: solid; background-color: rgb(255, 246, 247);'><span style='padding-right:10px'>" + totalAmount + " ???</span></td></tr>";
		sendString = StringUtil.replace(sendString, "[$amountGrandTotal]", salaryGrandTotalText);
		
		helper.setSubject(toName + "?????? " + year + "??? " + month + "??? ?????? ????????? ???????????? ??????" );
		helper.setText(sendString, true);
		if(toAddress != null && !toAddress.equals("")) {			
			helper.setTo(toAddress);
			sender.send(message);
		}
	}	
	
	public void sendProjectVOCMail() throws Exception {
		List<ProjectVocSendingInfoData> vocSendingInfos = projectVocManager.getSendingProjectVoc(DateTime.getYear(), DateTime.getMonth(),
				DateTime.getDay());

		if (vocSendingInfos.size() > 0) {
			JavaMailSenderImpl sender = new JavaMailSenderImpl();
			sender.setHost("webmail.kmac.co.kr");
			MimeMessage message = sender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
			helper.setFrom(new InternetAddress("cs@kmac.co.kr", "KMAC", "UTF-8"));
			//helper.setBcc("mailadmin@kmac.co.kr");
			helper.setSentDate(new Date());
			String sendString = "";

			for (ProjectVocSendingInfoData vocSendingInfo : vocSendingInfos) {
				ProjectSimpleInfo projectSimpleInfo = this.getProjectMasterInfoManager().getProjectSimpleInfo(vocSendingInfo.getProjectCode());
				if (projectSimpleInfo.getLang() == null || projectSimpleInfo.getLang().equals("")) {
					helper.setSubject("KMAC?????? ?????? ?????? ?????? ??????????????? ?????? ???????????? ??????");
					sendString = getMailContents().getContentInfo("vocKOR");
					sendString = StringUtil.replace(sendString, "[$projectname]", projectSimpleInfo.getProjectName());
				} else if (projectSimpleInfo.getLang().equals("KOR")) {
					helper.setSubject("KMAC?????? ?????? ?????? ?????? ??????????????? ?????? ???????????? ??????");
					sendString = getMailContents().getContentInfo("vocKOR");
					sendString = StringUtil.replace(sendString, "[$projectname]", projectSimpleInfo.getProjectName());
				} else if (projectSimpleInfo.getLang().equals("ENG")) {
					helper.setSubject("VOC of the ongoing KMAC project");
					sendString = getMailContents().getContentInfo("vocENG");
					sendString = StringUtil.replace(sendString, "[$projectname]", projectSimpleInfo.getProjectSubName());
				/*} else if (projectSimpleInfo.getLang().equals("JPN")) {
					helper.setSubject("???????????????????????????????????????????????????????????????");
					sendString = getMailContents().getContentInfo("vocJPN");
					sendString = StringUtil.replace(sendString, "[$projectname]", projectSimpleInfo.getProjectSubName());*/
				} else if (projectSimpleInfo.getLang().equals("CHN")) {
					helper.setSubject("???????????????KMAC?????????????????????????????????");
					sendString = getMailContents().getContentInfo("vocCHN");
					sendString = StringUtil.replace(sendString, "[$projectname]", projectSimpleInfo.getProjectSubName());
				}
				sendString = StringUtil.replace(sendString, "[$receiveEmail]", projectSimpleInfo.getCustomerEmail());
				sendString = StringUtil.replace(sendString, "[$projectCode]", projectSimpleInfo.getProjectCode());
				sendString = StringUtil.replace(sendString, "[$requestDate]", vocSendingInfo.getRequestDate());
				sendString = StringUtil.replace(sendString, "[$startDate]", projectSimpleInfo.getStartDate().substring(0, 4) + "-" + projectSimpleInfo.getStartDate().substring(4, 6) + "-" + projectSimpleInfo.getStartDate().substring(6, 8));
				sendString = StringUtil.replace(sendString, "[$endDate]", projectSimpleInfo.getEndDate().substring(0, 4) + "-" + projectSimpleInfo.getEndDate().substring(4, 6) + "-" + projectSimpleInfo.getEndDate().substring(6, 8));
				helper.setText(sendString, true);

				helper.setTo(projectSimpleInfo.getCustomerEmail());
				sender.send(message);

				vocSendingInfo.setSendDate(DateUtil.getCurrentYyyymmdd());
				projectVocManager.updateProjectVoc(vocSendingInfo, projectSimpleInfo);
			}

		}
	}
	
	// Job Date:  2017-10-24	Author: yhyim	Description: ???????????? ??????/?????? ????????? ??????????????? ?????? ??????
	public void sendProjectReportContentOpinion(ProjectReportContent projectReportContent) {
		SimpleMailMessage simpleMailMessage = getSimpleMailMessage();
		simpleMailMessage.setFrom("PMS@kmac.co.kr");
		simpleMailMessage.setTo(getMailReceiver(projectReportContent));
		simpleMailMessage.setSentDate(new Date());
		
		String titleHeader = "[???????????? ????????? ??????] ";
		if (projectReportContent.getState().equals("approver"))
			titleHeader = "[???????????? ????????? ??????] ";
		else if (projectReportContent.getState().equals("reject"))
			titleHeader = "[???????????? ?????? ??????] ";
			
		simpleMailMessage.setSubject(titleHeader + projectReportContent.getAssignDate() + " | " + projectReportContent.getTitle());
		simpleMailMessage.setText(getMailContent(projectReportContent));
	
		getMailSender().send(simpleMailMessage);
	}
	
	// Job Date:  2017-10-24	Author: yhyim	Description: ???????????? ??????/?????? ?????? ?????????
	private String[] getMailReceiver(ProjectReportContent projectReportContent) {
		String toaddress[] = new String[2];
		toaddress[0] = getExpertPoolManager().getEmail(projectReportContent.getWriterSsn());
		try {
			if (projectReportContent.getState().equals("approver") 
					&& projectReportContent.getReviewerSsn() != null 
					&& !projectReportContent.getReviewerSsn().equals("")) {
				toaddress[1] = getExpertPoolManager().getEmail(projectReportContent.getReviewerSsn());
			} else {
				toaddress[1] = getExpertPoolManager().getEmail(projectReportContent.getApproverSsn());
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			toaddress[1] = "PMS@kmac.co.kr";
		}
		return toaddress;
	}
	
	// Job Date:  2017-10-24	Author: yhyim	Description: ???????????? ??????/?????? ?????? ??????
	private String getMailContent(ProjectReportContent projectReportContent) {
		StringBuffer msg = new StringBuffer();
		msg.append("[?????? ??????] <br>");
		msg.append("---------------------------------------------------------------<br>");
		if (projectReportContent.getState().equals("approver"))
			msg.append(projectReportContent.getRevieweOpinion());
		else
			msg.append(projectReportContent.getApproveOpinion());
		msg.append("<br><br>");
		msg.append("[???????????????] " + projectReportContent.getProjectName() + "<br>");
		msg.append("[?????????] " + projectReportContent.getAssignDate() + "<br>");
		msg.append("---------------------------------------------------------------<br>" + projectReportContent.getWorkContent() + "<br>");

		return msg.toString();
	}
	
	// Job Date: 2007-12-18 Author: yhyim Description: ???????????? ?????? ???????????? ?????? ??????
	public void sendBoardInfo(BoardData boardData) throws Exception {
		JavaMailSenderImpl sender = new JavaMailSenderImpl();
		sender.setHost("webmail.kmac.co.kr");
		MimeMessage message = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
		helper.setFrom("PMS@kmac.co.kr");
		helper.setTo(getMailReceiver(boardData.getBbsId()));
		if (boardData.getUpdateTag().equals("Y")) {
			helper.setSubject("[???????????? ?????????] ????????? ?????? ????????????");
		} else {
			helper.setSubject("[???????????? ?????????] ??? ????????? ?????? ????????????");
		}
		helper.setSentDate(new Date());
		helper.setText(getMailContent(boardData), true);

		sender.send(message);
	}

	// Job Date: 2007-12-20 Author: yhyim Description: ???????????? ?????? ?????? ?????? ????????? return
	private String[] getMailReceiver(String projectCode) {
		List<ListOrderedMap> email = getExpertPoolManager().getProjectInvolvedMembers(projectCode);

		String toaddress[] = new String[email.size()];
		for (int i = 0; i < email.size(); i++) {
			toaddress[i] = (String) email.get(i).get("email");
		}

		return toaddress;
	}

	// Job Date: 2007-12-18 Author: yhyim Description: ???????????? ????????? ?????? ??????
	private String getMailContent(BoardData boardData) {
		ProjectSimpleInfo projectSimpleInfo = getProjectMasterInfoManager().getProjectSimpleInfo(boardData.getBbsId());

		StringBuffer msg = new StringBuffer();
		msg.append("[???????????????] " + projectSimpleInfo.getProjectName() + "<br>");
		msg.append("[?????????] " + getExpertPoolManager().getName(boardData.getWriterId()) + "<br>");
		msg.append("[??????] " + boardData.getSubject() + "<br>");
		msg.append("-----------------------------------------------------<br>" + boardData.getContent() + "<br>");

		return msg.toString();
	}

	public MailSender getMailSender() {
		return mailSender;
	}

	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}

	public SimpleMailMessage getSimpleMailMessage() {
		return simpleMailMessage;
	}

	public void setSimpleMailMessage(SimpleMailMessage simpleMailMessage) {
		this.simpleMailMessage = simpleMailMessage;
	}

	public ExpertPoolManager getExpertPoolManager() {
		return expertPoolManager;
	}

	public void setExpertPoolManager(ExpertPoolManager expertPoolManager) {
		this.expertPoolManager = expertPoolManager;
	}

	public ProjectMasterInfoManager getProjectMasterInfoManager() {
		return projectMasterInfoManager;
	}

	public void setProjectMasterInfoManager(ProjectMasterInfoManager projectMasterInfoManager) {
		this.projectMasterInfoManager = projectMasterInfoManager;
	}

	public MailContents getMailContents() {
		return mailContents;
	}

	public void setMailContents(MailContents mailContents) {
		this.mailContents = mailContents;
	}

	public ProjectVocManager getProjectVocManager() {
		return projectVocManager;
	}

	public void setProjectVocManager(ProjectVocManager projectVocManager) {
		this.projectVocManager = projectVocManager;
	}

	public PmsInfoMailSenderManager getPmsInfoMailSenderManager() {
		return pmsInfoMailSenderManager;
	}

	public void setPmsInfoMailSenderManager(PmsInfoMailSenderManager pmsInfoMailSenderManager) {
		this.pmsInfoMailSenderManager = pmsInfoMailSenderManager;
	}
}