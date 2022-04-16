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
		// pmsrenewal 임시 메일 발송
		simpleMailMessage.setTo(getMailReceiver(sanctionDoc));
		//simpleMailMessage.setTo("yskim@kmac.co.kr");
		simpleMailMessage.setSentDate(new Date());
		// 결재 의견을 메일 제목에 표시하기 위한 
		String notice = "";
		if (!sanctionDoc.getRejectReason().equals("")) 
			notice = "(결재 의견 있음)";
		if (sanctionDoc.getIsApproved().equalsIgnoreCase("N")) {
			simpleMailMessage.setSubject("[전자결재 반려] " + sanctionDoc.getSubject() + "-" + assignerUserPos + " " + assignerUserName + " 반려");
		} else {
			simpleMailMessage.setSubject("[전자결재 승인] " + sanctionDoc.getSubject() + "-" + assignerUserPos + " " + assignerUserName + " 승인 " + notice);
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
		simpleMailMessage.setSubject("[전자결재 참조] " + sanctionDoc.getSubject());
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
		msg.append("\n결재일시 : " + DateTime.getYear() + "년 " + DateTime.getMonth() + "월 " + DateTime.getDay() + "일\n");
		msg.append("제목 : " + sanctionDoc.getSubject() + "\n");
		if (sanctionDoc.getApprovalType().equals("A") || sanctionDoc.getApprovalType().equals("PA")){
			ProjectSimpleInfo projectSimpleInfo = this.getProjectMasterInfoManager().getProjectSimpleInfo(sanctionDoc.getProjectCode());
			msg.append("관련프로젝트 : [" + sanctionDoc.getProjectCode() + "]  " + projectSimpleInfo.getProjectName()+"\n");
		}
		if (sanctionDoc.getIsApproved().equalsIgnoreCase("N")) {
			msg.append(assignerUserPos + " : " + assignerUserName).append("님이 반려 하였습니다.");
			msg.append("\n\n");
			msg.append("--- 반려 내용 ---\n");
			msg.append(sanctionDoc.getRejectReasonView());
		} else {
			msg.append(assignerUserPos + " : " + assignerUserName).append("님이 승인 하였습니다.");
			msg.append("\n\n");
			msg.append("--- 승인 내용 ---\n");
			msg.append(sanctionDoc.getRejectReasonView());
		}
		return msg.toString();
	}
	
	private String getRefMailContent(String assignerUserName, String assignerUserPos, SanctionDoc sanctionDoc) {
		StringBuffer msg = new StringBuffer();
		msg.append("\n결재일시 :" + DateTime.getYear() + "년 " + DateTime.getMonth() + "월 " + DateTime.getDay() + "일\n");
		msg.append("제목 :" + sanctionDoc.getSubject() + "\n");
		msg.append(assignerUserPos + ": " + assignerUserName).append("님이 승인 하였습니다.");
		msg.append("\n\n");
		msg.append("--- 승인 내용 ---\n");
		msg.append(sanctionDoc.getRejectReasonView());
		msg.append("\n");
		msg.append("--- 품의 내용 ---\n\n");
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
		if (sanctionDoc.getState().equals(SanctionConstants.SANCTION_STATE_REVIEW)) {// 팀장
			toAddressList.add(getExpertPoolManager().getEmail(register));
		} else if (sanctionDoc.getState().equals(SanctionConstants.SANCTION_STATE_CHECK)) {// 본부장
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
		} else if (sanctionDoc.getState().equals(SanctionConstants.SANCTION_STATE_ASSIST1)) {// 협의1
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
		} else if (sanctionDoc.getState().equals(SanctionConstants.SANCTION_STATE_ASSIST2)) {// 협의2
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
		} else if (sanctionDoc.getState().equals(SanctionConstants.SANCTION_STATE_ASSIST3)) {// 협의3
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
		} else if (sanctionDoc.getState().equals(SanctionConstants.SANCTION_STATE_CEO)) {// ceo 실행
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

	// 다운로드 기준 초과 사용자 알림 메일 발송
	public void sendDownloadInfo(String userId, int downloadCount) throws Exception {
		SimpleMailMessage simpleMailMessage = getSimpleMailMessage();
		simpleMailMessage.setFrom("PMS@kmac.co.kr");
		simpleMailMessage.setTo(getMailReceiver());
		simpleMailMessage.setSentDate(new Date());
		simpleMailMessage.setSubject("[다운로드 알림] 다운로드 확인 알림메일");
		simpleMailMessage.setText(getMailContent(getExpertPoolManager().getName(userId), String.valueOf(downloadCount)));
	
		getMailSender().send(simpleMailMessage);
	}
	
	public void sendSanctonRefInfoOrg(OrgdbDetailForm orgdbDetailForm, String updateYN) throws Exception {
		final SimpleMailMessage simpleMailMessage = getSimpleMailMessage();
		simpleMailMessage.setFrom("PMS@kmac.co.kr");
		simpleMailMessage.setTo("mwji@kmac.co.kr");
		simpleMailMessage.setSentDate(new Date());
		simpleMailMessage.setSubject("[협력사 등록] " + orgdbDetailForm.getOrgName());
		simpleMailMessage.setText(getMailOrgContent(orgdbDetailForm, updateYN));

		new Thread("sendSanctonInfo"){
	        public void run(){
	        	getMailSender().send(simpleMailMessage);
	        }
	      }.start();
	}
	
	private String getMailOrgContent(OrgdbDetailForm orgdbDetailForm, String updateYN) {
		StringBuffer msg = new StringBuffer();
		msg.append("제목 : " + orgdbDetailForm.getOrgName() + "\n");
		msg.append("등록자 : " + orgdbDetailForm.getPmName() + "\n");
		if(updateYN.equals("Y")){
			/*msg.append("일자 : " + orgdbDetailForm.getCreateDate() + "\n");*/
			msg.append("구분 : 신규등록"+"\n");
		}else{
			/*msg.append("일자 : " + orgdbDetailForm.getModifyDate() + "\n");*/
			msg.append("구분 : 수정"+"\n");
		}
		return msg.toString();
	}
	
	// JobDate: 2012-01-10	Author: yhyim	Description: 고객만족도 결과 메일 참조자 리스트 return
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
	
	// JobDate: 2012-01-10	Author: yhyim	Description: 고객만족도 결과 메일 숨은 참조자 리스트 return
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

	// Job Date: 2007-12-20 Author: yhyim Description: 다운로드 확인자 리스트 return
	private String[] getMailReceiver() {
		String toAddress[] = new String[1];
		toAddress[0] = "mailadmin@kmac.co.kr";
		return toAddress;
	}

	// Job Date: 2008-02-19 Author: yhyim Description: 다운로드 확인 내용
	private String getMailContent(String userName, String count) {
		StringBuffer msg = new StringBuffer();
		msg.append("사용자 " + userName + " 님이 다운로드를 ");
		msg.append(count + " 회 하였습니다. " + "\n");
		msg.append("다운로드 현황을 확인해 보시기 바랍니다. \n");
		return msg.toString();
	}

	// Job Date: 2009-04-18 Author: jwlee Description: 리서치 고객만족도 요청 메일 발송
	public void sendRndCSMail(String projectCode) throws Exception {
		this.sendProjectCSMail(projectCode);
	}

	// Job Date: 2009-04-18 Author: jwlee Description: 컨설팅 고객만족도 요청 메일 발송
	public void sendConsultingCSMail(String projectCode) throws Exception {
		this.sendProjectCSMail(projectCode);
	}

	// Job Date: 2009-04-18 Author: jwlee Description: 사내교육 고객만족도 요청 메일 발송
	public void sendCompanyEduCSMail(String projectCode) throws Exception {
		this.sendProjectCSMail(projectCode);
	}
	
	// TODO
	// 공개교육
	public void sendPublicEduCSMail(String projectCode) throws Exception {
		this.sendProjectCSMail(projectCode);
	}
	
	// 리서치센터(KCSMA) 고객만족도 메일 발송
	public void sendKCSMACSMail(String projectCode) throws Exception {
		this.sendKCSMAProjectCSMail(projectCode);
	}

	// Job Date: 2011-05-15 Author: jwlee Description: 컨설팅/리서치 고객만족도 요청 메일 발송
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
					helper.setSubject("KMAC에서 수행한 귀사 프로젝트 만족도 설문에 도움을 주시기 바랍니다.");
					sendString = getMailContents().getContentInfo("projectCSMailContentKOR");
					sendString = StringUtil.replace(sendString, "[$projectName]", projectSimpleInfo.getProjectName());
				} else if (projectSimpleInfo.getLang().equals("KOR")) {
					helper.setSubject("KMAC에서 수행한 귀사 프로젝트 만족도 설문에 도움을 주시기 바랍니다.");
					sendString = getMailContents().getContentInfo("projectCSMailContentKOR");
					sendString = StringUtil.replace(sendString, "[$projectName]", projectSimpleInfo.getProjectName());
				} else if (projectSimpleInfo.getLang().equals("ENG")) {
					helper.setSubject("We deeply appreciate if you complete the project satisfaction survey.");
					sendString = getMailContents().getContentInfo("projectCSMailContentENG");
					sendString = StringUtil.replace(sendString, "[$projectName]", projectSimpleInfo.getProjectSubName());
				/*} else if (projectSimpleInfo.getLang().equals("JPN")) {
					helper.setSubject("チャリティくちこみサイトのご案内オクション");
					sendString = getMailContents().getContentInfo("projectCSMailContentJPN");
					sendString = StringUtil.replace(sendString, "[$projectName]", projectSimpleInfo.getProjectSubName());		*/
				} else if (projectSimpleInfo.getLang().equals("CHN")) {
					helper.setSubject("希望您能协助完成针对由KMAC执行的贵社项目的满意度调查");
					
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
		helper.setFrom(new InternetAddress("cs@kcsma.or.kr", "한국고객만족경영학회", "UTF-8"));
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
					helper.setSubject("한국고객만족경영학회에서 수행한 귀사 프로젝트 만족도 설문에 도움을 주시기 바랍니다.");
					sendString = getMailContents().getContentInfo("KCSMAprojectCSMailContentKOR");
					sendString = StringUtil.replace(sendString, "[$projectName]", projectSimpleInfo.getProjectName());
				} else if (projectSimpleInfo.getLang().equals("KOR")) {
					helper.setSubject("한국고객만족경영학회에서 수행한 귀사 프로젝트 만족도 설문에 도움을 주시기 바랍니다.");
					sendString = getMailContents().getContentInfo("KCSMAprojectCSMailContentKOR");
					sendString = StringUtil.replace(sendString, "[$projectName]", projectSimpleInfo.getProjectName());
				} else if (projectSimpleInfo.getLang().equals("ENG")) {
					helper.setSubject("We deeply appreciate if you complete the project satisfaction survey.");
					sendString = getMailContents().getContentInfo("projectCSMailContentENG");
					sendString = StringUtil.replace(sendString, "[$projectName]", projectSimpleInfo.getProjectSubName());
				} else if (projectSimpleInfo.getLang().equals("CHN")) {
					helper.setSubject("希望您能协助完成针对由KCSMA执行的贵社项目的满意度调查");
					
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

	// Job Date: 2011-05-15 Author: jwlee Description: 컨설팅/리서치 고객만족도 요청 메일 재 발송
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
			helper.setSubject("[재발송] KMAC에서 수행한 귀사 프로젝트 만족도 설문에 도움을 주시기 바랍니다.");
			sendString = getMailContents().getContentInfo("projectCSMailContentKOR");
			sendString = StringUtil.replace(sendString, "[$projectName]", projectSimpleInfo.getProjectName());
		} else if (projectSimpleInfo.getLang().equals("KOR")) {
			helper.setSubject("[재발송] KMAC에서 수행한 귀사 프로젝트 만족도 설문에 도움을 주시기 바랍니다.");
			sendString = getMailContents().getContentInfo("projectCSMailContentKOR");
			sendString = StringUtil.replace(sendString, "[$projectName]", projectSimpleInfo.getProjectName());
		} else if (projectSimpleInfo.getLang().equals("ENG")) {
			helper.setSubject("We deeply appreciate if you complete the project satisfaction survey.");
			sendString = getMailContents().getContentInfo("projectCSMailContentENG");
			sendString = StringUtil.replace(sendString, "[$projectName]", projectSimpleInfo.getProjectSubName());
		/*} else if (projectSimpleInfo.getLang().equals("JPN")) {
			helper.setSubject("チャリティくちこみサイトのご案内オクション");
			sendString = getMailContents().getContentInfo("projectCSMailContentJPN");
			sendString = StringUtil.replace(sendString, "[$projectName]", projectSimpleInfo.getProjectSubName());		*/
		} else if (projectSimpleInfo.getLang().equals("CHN")) {
			helper.setSubject("希望您能协助完成针对由KMAC执行的贵社项目的满意度调查");
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
	
	
	// Job Date: 2011-05-15 Author: jwlee Description: 컨설팅/리서치 고객만족도 요청 메일 재 발송
		public void sendKCSMAProjectCSMailAgain(String projectCode, int customerSeq) throws Exception {
			ProjectSimpleInfo projectSimpleInfo = this.getProjectMasterInfoManager().getProjectSimpleInfo(projectCode);
			ProjectCsrInfo projectCsrInfo = this.getProjectMasterInfoManager().getProjectCsrInfo(projectCode, customerSeq);
			
			JavaMailSenderImpl sender = new JavaMailSenderImpl();
			sender.setHost("webmail.kmac.co.kr");
			MimeMessage message = sender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
			helper.setFrom(new InternetAddress("cs@kcsma.or.kr", "한국고객만족경영학회", "UTF-8"));
			helper.setBcc("mailadmin@kmac.co.kr");
			helper.setSentDate(new Date());
			String sendString = "";
			String procFile = "icsi1_kcsma";
			if(projectSimpleInfo.getProcessTypeCode().equals("N1") || projectSimpleInfo.getProcessTypeCode().equals("N2")){
				procFile = "eduicsi1";
			}
			if (projectSimpleInfo.getLang() == null || projectSimpleInfo.getLang().equals("")) {
				helper.setSubject("[재발송] 한국고객만족경영학회에서 수행한 귀사 프로젝트 만족도 설문에 도움을 주시기 바랍니다.");
				sendString = getMailContents().getContentInfo("KCSMAprojectCSMailContentKOR");
				sendString = StringUtil.replace(sendString, "[$projectName]", projectSimpleInfo.getProjectName());
			} else if (projectSimpleInfo.getLang().equals("KOR")) {
				helper.setSubject("[재발송] 한국고객만족경영학회에서 수행한 귀사 프로젝트 만족도 설문에 도움을 주시기 바랍니다.");
				sendString = getMailContents().getContentInfo("KCSMAprojectCSMailContentKOR");
				sendString = StringUtil.replace(sendString, "[$projectName]", projectSimpleInfo.getProjectName());
			} else if (projectSimpleInfo.getLang().equals("ENG")) {
				helper.setSubject("We deeply appreciate if you complete the project satisfaction survey.");
				sendString = getMailContents().getContentInfo("projectCSMailContentENG");
				sendString = StringUtil.replace(sendString, "[$projectName]", projectSimpleInfo.getProjectSubName());
			/*} else if (projectSimpleInfo.getLang().equals("JPN")) {
				helper.setSubject("チャリティくちこみサイトのご案内オクション");
				sendString = getMailContents().getContentInfo("projectCSMailContentJPN");
				sendString = StringUtil.replace(sendString, "[$projectName]", projectSimpleInfo.getProjectSubName());		*/
			} else if (projectSimpleInfo.getLang().equals("CHN")) {
				helper.setSubject("希望您能协助完成针对由KCSMA执行的贵社项目的满意度调查");
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
	
	// Job Date: 2011-05-15 Author: jwlee Description: 프로젝트 감사 메일
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
				helper.setSubject("프로젝트 고객의견 접수 및 평가에 대한 안내");
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
				helper.setSubject("项目顾客意见接收 及 评价指南");
				sendString = getMailContents().getContentInfo("customerThxCHN");
				sendString = StringUtil.replace(sendString, "[$projectName]", projectSimpleInfo.getProjectSubName());
				sendString = StringUtil.replace(sendString, "[$projectCode]", projectSimpleInfo.getProjectCode());
				sendString = StringUtil.replace(sendString, "[$email]", projectSimpleInfo.getCustomerEmail());
			}
			helper.setText(sendString, true);
	
			// 다수 고객사에 감사 메일 발송
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
						+ StringUtil.longt2Money(str.getRealTimeSalaryEachProject()) + " 원</td></tr>";	
		
				
				totalAmount = StringUtil.longt2Money(str.getTotalRealTimeSalary());
				toAddress = str.getEmail();
				toName = str.getName();
			}
		sendString = StringUtil.replace(sendString, "[$amount]", amountText);
		String salaryGrandTotalText = "<tr><td style='padding: 5px 0px; text-align: center; border-right-color: rgb(204, 204, 204); border-bottom-color: rgb(204, 204, 204); border-right-width: 1px; border-bottom-width: 1px; border-right-style: solid; border-bottom-style: solid; background-color: rgb(255, 246, 247);'><strong>합계</strong></td><td style='text-align: right; margin-right: 10px; border-bottom-color: rgb(204, 204, 204); border-bottom-width: 1px; border-bottom-style: solid; background-color: rgb(255, 246, 247);'><span style='padding-right:10px'>" + totalAmount + " 원</span></td></tr>";
		sendString = StringUtil.replace(sendString, "[$amountGrandTotal]", salaryGrandTotalText);
		
		helper.setSubject(toName + "님의 " + year + "년 " + month + "월 수행 성과급 지급내역 송부" );
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
					helper.setSubject("KMAC에서 수행 중인 귀사 프로젝트에 대한 고객의견 청취");
					sendString = getMailContents().getContentInfo("vocKOR");
					sendString = StringUtil.replace(sendString, "[$projectname]", projectSimpleInfo.getProjectName());
				} else if (projectSimpleInfo.getLang().equals("KOR")) {
					helper.setSubject("KMAC에서 수행 중인 귀사 프로젝트에 대한 고객의견 청취");
					sendString = getMailContents().getContentInfo("vocKOR");
					sendString = StringUtil.replace(sendString, "[$projectname]", projectSimpleInfo.getProjectName());
				} else if (projectSimpleInfo.getLang().equals("ENG")) {
					helper.setSubject("VOC of the ongoing KMAC project");
					sendString = getMailContents().getContentInfo("vocENG");
					sendString = StringUtil.replace(sendString, "[$projectname]", projectSimpleInfo.getProjectSubName());
				/*} else if (projectSimpleInfo.getLang().equals("JPN")) {
					helper.setSubject("チャリティくちこみサイトのご案内オクション");
					sendString = getMailContents().getContentInfo("vocJPN");
					sendString = StringUtil.replace(sendString, "[$projectname]", projectSimpleInfo.getProjectSubName());*/
				} else if (projectSimpleInfo.getLang().equals("CHN")) {
					helper.setSubject("聆听顾客对KMAC进行中的贵社项目的意见");
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
	
	// Job Date:  2017-10-24	Author: yhyim	Description: 지도일지 검토/승인 의견을 작성자에게 메일 발송
	public void sendProjectReportContentOpinion(ProjectReportContent projectReportContent) {
		SimpleMailMessage simpleMailMessage = getSimpleMailMessage();
		simpleMailMessage.setFrom("PMS@kmac.co.kr");
		simpleMailMessage.setTo(getMailReceiver(projectReportContent));
		simpleMailMessage.setSentDate(new Date());
		
		String titleHeader = "[지도일지 승인자 의견] ";
		if (projectReportContent.getState().equals("approver"))
			titleHeader = "[지도일지 검토자 의견] ";
		else if (projectReportContent.getState().equals("reject"))
			titleHeader = "[지도일지 반려 의견] ";
			
		simpleMailMessage.setSubject(titleHeader + projectReportContent.getAssignDate() + " | " + projectReportContent.getTitle());
		simpleMailMessage.setText(getMailContent(projectReportContent));
	
		getMailSender().send(simpleMailMessage);
	}
	
	// Job Date:  2017-10-24	Author: yhyim	Description: 지도일지 검토/승인 의견 수신자
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
	
	// Job Date:  2017-10-24	Author: yhyim	Description: 지도일지 검토/승인 의견 내용
	private String getMailContent(ProjectReportContent projectReportContent) {
		StringBuffer msg = new StringBuffer();
		msg.append("[의견 내용] <br>");
		msg.append("---------------------------------------------------------------<br>");
		if (projectReportContent.getState().equals("approver"))
			msg.append(projectReportContent.getRevieweOpinion());
		else
			msg.append(projectReportContent.getApproveOpinion());
		msg.append("<br><br>");
		msg.append("[프로젝트명] " + projectReportContent.getProjectName() + "<br>");
		msg.append("[지도일] " + projectReportContent.getAssignDate() + "<br>");
		msg.append("---------------------------------------------------------------<br>" + projectReportContent.getWorkContent() + "<br>");

		return msg.toString();
	}
	
	// Job Date: 2007-12-18 Author: yhyim Description: 프로젝트 관련 인원에게 메일 발송
	public void sendBoardInfo(BoardData boardData) throws Exception {
		JavaMailSenderImpl sender = new JavaMailSenderImpl();
		sender.setHost("webmail.kmac.co.kr");
		MimeMessage message = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
		helper.setFrom("PMS@kmac.co.kr");
		helper.setTo(getMailReceiver(boardData.getBbsId()));
		if (boardData.getUpdateTag().equals("Y")) {
			helper.setSubject("[프로젝트 게시판] 게시물 수정 알림메일");
		} else {
			helper.setSubject("[프로젝트 게시판] 새 게시물 등록 알림메일");
		}
		helper.setSentDate(new Date());
		helper.setText(getMailContent(boardData), true);

		sender.send(message);
	}

	// Job Date: 2007-12-20 Author: yhyim Description: 프로젝트 관련 인원 메일 리스트 return
	private String[] getMailReceiver(String projectCode) {
		List<ListOrderedMap> email = getExpertPoolManager().getProjectInvolvedMembers(projectCode);

		String toaddress[] = new String[email.size()];
		for (int i = 0; i < email.size(); i++) {
			toaddress[i] = (String) email.get(i).get("email");
		}

		return toaddress;
	}

	// Job Date: 2007-12-18 Author: yhyim Description: 프로젝트 게시판 내용 편집
	private String getMailContent(BoardData boardData) {
		ProjectSimpleInfo projectSimpleInfo = getProjectMasterInfoManager().getProjectSimpleInfo(boardData.getBbsId());

		StringBuffer msg = new StringBuffer();
		msg.append("[프로젝트명] " + projectSimpleInfo.getProjectName() + "<br>");
		msg.append("[등록자] " + getExpertPoolManager().getName(boardData.getWriterId()) + "<br>");
		msg.append("[제목] " + boardData.getSubject() + "<br>");
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