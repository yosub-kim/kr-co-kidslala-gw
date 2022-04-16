/*
 * $Id: ProjectEndTaskAssignManagerImpl.java,v 1.26 2018/06/29 04:42:52 cvs Exp $
 * created by    : jiwoongLee
 * creation-date : 2006. 3. 30.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.project.endprocess.manager.impl;

import java.util.Date;
import java.util.List;

import kr.co.kmac.pms.common.util.IdGenerator;
import kr.co.kmac.pms.common.worklist.data.Work;
import kr.co.kmac.pms.common.worklist.data.WorkConstants;
import kr.co.kmac.pms.common.worklist.manager.WorklistManager;
import kr.co.kmac.pms.expertpool.manager.ExpertPoolManager;
import kr.co.kmac.pms.project.endprocess.dao.EndProcessCheckDao;
import kr.co.kmac.pms.project.endprocess.dao.ProjectEndingDao;
import kr.co.kmac.pms.project.endprocess.data.EndProcessCheck;
import kr.co.kmac.pms.project.endprocess.manager.ProjectEndTaskAssignManager;
import kr.co.kmac.pms.project.exception.ProjectException;
import kr.co.kmac.pms.project.master.dao.ProjectDao;
import kr.co.kmac.pms.project.master.dao.ProjectMemberDao;
import kr.co.kmac.pms.project.master.data.ProjectCsrInfo;
import kr.co.kmac.pms.project.master.data.ProjectMember;
import kr.co.kmac.pms.project.master.data.ProjectSimpleInfo;
import kr.co.kmac.pms.project.master.manager.ProjectMasterInfoManager;
import kr.co.kmac.pms.sanction.general.data.SanctionConstants;
import kr.co.kmac.pms.service.mailsender.manager.PmsInfoMailSender;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * ���� ���μ��� ���� �Ҵ�
 * 
 * @author jiwoongLee
 * @version $Id: ProjectEndTaskAssignManagerImpl.java,v 1.26 2018/06/29 04:42:52 cvs Exp $
 */
public class ProjectEndTaskAssignManagerImpl implements ProjectEndTaskAssignManager {
	private ProjectDao projectDao;
	private EndProcessCheckDao endProcessCheckDao;
	private ProjectEndingDao projectEndingDao;
	private WorklistManager worklistManager;
	private PmsInfoMailSender pmsInfoMailSender;
	private ProjectMemberDao projectMemberDao;
	private ExpertPoolManager expertPoolManager;
	private ProjectMasterInfoManager projectMasterInfoManager;
	protected static Log log;

	static {
		log = LogFactory.getLog(ProjectEndTaskAssignManagerImpl.class);
	}

	/**
	 * #������ �� ������ �� - �����忡�� ������ �� ���� ���� ��û ���� �߼� �� PL ���� Ȯ�� ���� �߼�
	 * --> ������ �ٷ� ��û ���� �߼��ϰ� PM���� Ȯ�� ���� �Ҵ��ϴ� ������� ����(2011.05.19)
	 */
	public void assignConsultingCustomerSatisfactionTask(String projectCode) throws ProjectException {
		try {
			ProjectSimpleInfo projectSimpleInfo = getProjectDao().getProjectSimpleInfo(projectCode);
			List<ProjectCsrInfo> projectCsrInfoList = this.getProjectMasterInfoManager().getProjectAllCsrInfo(projectCode);
			
			for (ProjectCsrInfo projectCsrInfo : projectCsrInfoList) {
				Work assigneeWork = this.getWorklistManager().getWorkTemplate(
						"4028809e0a43b9c4010a43c573480003", "PMS", projectSimpleInfo.getPmSsn(),
						projectCode, null, null);
				assigneeWork.setId(IdGenerator.generate(IdGenerator.PREFIX_WORK));
				assigneeWork.setRefWorkId3(String.valueOf(projectCsrInfo.getSeq()));
				assigneeWork.setCreateDate(new Date());
				assigneeWork.setDraftDate(new Date());
				//assigneeWork.setDraftUserId(projectSimpleInfo.getArSsn());
				assigneeWork.setDraftUserId("");
				assigneeWork.setLevel(SanctionConstants.SANCTION_STATE_RATING);
				assigneeWork.setState(WorkConstants.WORK_STATE_READY);
				assigneeWork.setTitle(projectSimpleInfo.getProjectName() + " "
						+ this.getWorklistManager().getWorkType("4028809e0a43b9c4010a43c573480003").getName());
				
				this.getWorklistManager().assignWork(assigneeWork);
				getEndProcessCheckDao().create(projectCode, assigneeWork.getId(), "N");
			}

			getPmsInfoMailSender().sendConsultingCSMail(projectCode);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}// ������ �� ���� ����
		//getEndProcessCheckDao().create(projectCode, "VOC", "N");
		// ���� ���� ��� ������Ʈ(2010-02-08 �߰�)
		//this.getProjectEndingDao().sendInfoConsultingCustomerSatisfactionTask(projectCode);
	}
	
	public void sendCSMail(String projectCode) throws ProjectException {
		try {
			getPmsInfoMailSender().sendRndCSMail(projectCode);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
	}

	/**
	 * ������ ������Ʈ �� - ����ü PL
	 * PL, partPL, MB -> MB�� ��
	 */
	public void assignConsultingMemberValuationTask(String projectCode) throws ProjectException {
		List<ProjectMember> projectMembers = this.getProjectMemberDao().selectOnlyMemberAll(projectCode);
		ProjectSimpleInfo projectSimpleInfo = getProjectDao().getProjectSimpleInfo(projectCode);
		if (projectMembers.size() > 0) {
			
			// PL -> MB ������ (������)
			Work assigneeWork = this.getWorklistManager().getWorkTemplate("4028809e0a43b9c4010a43c573480005", "PMS", projectSimpleInfo.getPlSsn(),
					projectCode, null, null);
			assigneeWork.setId(IdGenerator.generate(IdGenerator.PREFIX_WORK));
			assigneeWork.setCreateDate(new Date());
			assigneeWork.setDraftDate(new Date());
			assigneeWork.setDraftUserId(projectSimpleInfo.getPlSsn());
			assigneeWork.setLevel(SanctionConstants.SANCTION_STATE_RATING);
			assigneeWork.setState(WorkConstants.WORK_STATE_READY);
			assigneeWork.setTitle(projectSimpleInfo.getProjectName() + " "
					+ this.getWorklistManager().getWorkType("4028809e0a43b9c4010a43c573480005").getName());

			this.getWorklistManager().assignWork(assigneeWork);
			getEndProcessCheckDao().create(projectCode, assigneeWork.getId(), "N");
			
			// PL, partPL, MB -> MB �ٸ���
			// �� ���
			/*List<ProjectSimpleInfo> projectSimpleInfo2 = getProjectDao().getProjectSimpleInfo2(projectCode);
			
			for(int i = 0; i < Integer.valueOf(projectSimpleInfo2.get(0).getMbCnt()); i++){
				Work assigneeWork = null;
				
				// ���ڰ�  MB�� �������� ���� ��
				if(projectSimpleInfo2.get(i).getMbRole().equals("MB")){
					assigneeWork = this.getWorklistManager().getWorkTemplate("4028809e0a43b9c4010a43c573480010", "PMS", projectSimpleInfo2.get(i).getMbSsn(),
							projectCode, null, null);
				}else{
					// ���ڰ�  MB�� �ƴҶ��� ���� ��
					assigneeWork = this.getWorklistManager().getWorkTemplate("4028809e0a43b9c4010a43c573480005", "PMS", projectSimpleInfo2.get(i).getMbSsn(),
							projectCode, null, null);
				}
				assigneeWork.setId(IdGenerator.generate(IdGenerator.PREFIX_WORK));
				assigneeWork.setCreateDate(new Date());
				assigneeWork.setDraftDate(new Date());
				assigneeWork.setDraftUserId(projectSimpleInfo2.get(i).getMbSsn());
				assigneeWork.setLevel(SanctionConstants.SANCTION_STATE_RATING);
				assigneeWork.setState(WorkConstants.WORK_STATE_READY);
				
				// ���ڰ�  MB�� �������� ���� ��
				if(projectSimpleInfo2.get(i).getMbRole().equals("MB")){
					assigneeWork.setTitle(projectSimpleInfo2.get(0).getProjectName() + " "
							+ this.getWorklistManager().getWorkType("4028809e0a43b9c4010a43c573480010").getName());
				}else{
					// ���ڰ�  MB�� �ƴҶ��� ���� ��
					assigneeWork.setTitle(projectSimpleInfo2.get(0).getProjectName() + " "
							+ this.getWorklistManager().getWorkType("4028809e0a43b9c4010a43c573480005").getName());
				}
				this.getWorklistManager().assignWork(assigneeWork);
				getEndProcessCheckDao().create(projectCode, assigneeWork.getId(), "N");
			}*/
		}
	}

	/**
	 * ������ PL �� - ����ü PM
	 * PM, partPL, MB -> PL�� ��
	 */
	public void assignConsultingPLValuationTask(String projectCode) throws ProjectException {
		ProjectSimpleInfo projectSimpleInfo = getProjectDao().getProjectSimpleInfo(projectCode);

		// PM -> ML ������ (������)
		Work assigneeWork = this.getWorklistManager().getWorkTemplate("4028809e0a43b9c4010a43c573480004", "PMS", projectSimpleInfo.getPmSsn(),
				projectCode, null, null);
		assigneeWork.setId(IdGenerator.generate(IdGenerator.PREFIX_WORK));
		assigneeWork.setCreateDate(new Date());
		assigneeWork.setDraftDate(new Date());
		assigneeWork.setDraftUserId(projectSimpleInfo.getPmSsn());
		assigneeWork.setLevel(SanctionConstants.SANCTION_STATE_RATING);
		assigneeWork.setState(WorkConstants.WORK_STATE_READY);
		assigneeWork.setTitle(projectSimpleInfo.getProjectName() + " "
				+ this.getWorklistManager().getWorkType("4028809e0a43b9c4010a43c573480004").getName());

		this.getWorklistManager().assignWork(assigneeWork);
		getEndProcessCheckDao().create(projectCode, assigneeWork.getId(), "N");
		
		// partPL, MB -> PL �ٸ���
		/*List<ProjectSimpleInfo> projectSimpleInfo3 = getProjectDao().getProjectSimpleInfo3(projectCode);
		for(int i = 0; i < Integer.valueOf(projectSimpleInfo3.get(0).getMbCnt()); i++){
			Work assigneeWork = null;
			
			// ���ڰ� PM �϶��� ���� ��
			if(projectSimpleInfo3.get(i).getMbRole().equals("PM")){
				assigneeWork = this.getWorklistManager().getWorkTemplate("4028809e0a43b9c4010a43c573480004", "PMS", projectSimpleInfo3.get(i).getMbSsn(),
						projectCode, null, null);
			}else{
				// ���ڰ� PM �ƴҶ��� ���� ��
				assigneeWork = this.getWorklistManager().getWorkTemplate("4028809e0a43b9c4010a43c573480008", "PMS", projectSimpleInfo3.get(i).getMbSsn(),
						projectCode, null, null);
			}
			assigneeWork.setId(IdGenerator.generate(IdGenerator.PREFIX_WORK));
			assigneeWork.setCreateDate(new Date());
			assigneeWork.setDraftDate(new Date());
			assigneeWork.setDraftUserId(projectSimpleInfo3.get(i).getMbSsn());
			assigneeWork.setLevel(SanctionConstants.SANCTION_STATE_RATING);
			assigneeWork.setState(WorkConstants.WORK_STATE_READY);
			
			// ���ڰ� PM �϶��� ���� ��
			if(projectSimpleInfo3.get(i).getMbRole().equals("PM")){
				assigneeWork.setTitle(projectSimpleInfo3.get(0).getProjectName() + " "
						+ this.getWorklistManager().getWorkType("4028809e0a43b9c4010a43c573480004").getName());
			}else{
				// ���ڰ� PM �ƴҶ��� ���� ��
				assigneeWork.setTitle(projectSimpleInfo3.get(0).getProjectName() + " "
						+ this.getWorklistManager().getWorkType("4028809e0a43b9c4010a43c573480008").getName());
			}
			this.getWorklistManager().assignWork(assigneeWork);
			getEndProcessCheckDao().create(projectCode, assigneeWork.getId(), "N");
		}*/
	}
	
	/**
	 * ������ PL �� - ����ü PM
	 * PL, MB -> partPL�� ��
	 */
	/*public void assignConsultingPartPLValuationTask(String projectCode) throws ProjectException {
		ProjectSimpleInfo projectSimpleInfo = getProjectDao().getProjectSimpleInfo(projectCode);
		
		// PL, MB -> partPL �ٸ���
		List<ProjectSimpleInfo> projectSimpleInfo4 = getProjectDao().getProjectSimpleInfo4(projectCode);
		for(int i = 0; i < Integer.valueOf(projectSimpleInfo4.get(0).getMbCnt()); i++){
		
			// ���ڰ� PL �϶��� ���� ��
			if(projectSimpleInfo4.get(i).getMbRole().equals("PL")){
				Work assigneeWork = this.getWorklistManager().getWorkTemplate("4028809e0a43b9c4010a43c573480007", "PMS", projectSimpleInfo4.get(i).getMbSsn(),
						projectCode, null, null);
				assigneeWork.setId(IdGenerator.generate(IdGenerator.PREFIX_WORK));
				assigneeWork.setCreateDate(new Date());
				assigneeWork.setDraftDate(new Date());
				assigneeWork.setDraftUserId(projectSimpleInfo4.get(i).getMbSsn());
				assigneeWork.setLevel(SanctionConstants.SANCTION_STATE_RATING);
				assigneeWork.setState(WorkConstants.WORK_STATE_READY);
				assigneeWork.setTitle(projectSimpleInfo4.get(0).getProjectName() + " "
						+ this.getWorklistManager().getWorkType("4028809e0a43b9c4010a43c573480007").getName());
				
				this.getWorklistManager().assignWork(assigneeWork);
				getEndProcessCheckDao().create(projectCode, assigneeWork.getId(), "N");
			}else{
				// ���ڰ� PL�� �ƴҶ��� �� ����
				 assigneeWork = this.getWorklistManager().getWorkTemplate("4028809e0a43b9c4010a43c573480010", "PMS", projectSimpleInfo4.get(i).getMbSsn(),
						projectCode, null, null);
						
				   assigneeWork.setTitle(projectSimpleInfo4.get(0).getProjectName() + " "
						+ this.getWorklistManager().getWorkType("4028809e0a43b9c4010a43c573480010").getName()); 
			}
		}
	}*/

	/**
	 * ����ġ �� ������ �� - ����ü PM(Changed 2007-06-18)
	 *  --> ��û���� ���濡 ���� ����ġ �� ������ �� �� ��ü PM ���� ������ ����(Changed 2009-04-18) 
	 *  --> �� ����� PM���� �Է��� �ƴ� ����� ���� �߼�(Changed 2009-04-18)
	 *  --> ���翡�� ���� ���� �߼�(2011.05.19)
	 */
	public void assignResearchCustomerSatisfactionTask(String projectCode) throws ProjectException {
		try {
			ProjectSimpleInfo projectSimpleInfo = getProjectDao().getProjectSimpleInfo(projectCode);
			Work assigneeWork = this.getWorklistManager().getWorkTemplate("4028809e0a43b9c4010a43c573480006", "PMS", projectSimpleInfo.getPmSsn(),
					projectCode, null, null);
			assigneeWork.setId(IdGenerator.generate(IdGenerator.PREFIX_WORK));
			assigneeWork.setCreateDate(new Date());
			assigneeWork.setDraftDate(new Date());
			//assigneeWork.setDraftUserId(projectSimpleInfo.getArSsn());
			assigneeWork.setDraftUserId("");
			assigneeWork.setLevel(SanctionConstants.SANCTION_STATE_RATING);
			assigneeWork.setState(WorkConstants.WORK_STATE_READY);
			assigneeWork.setTitle(projectSimpleInfo.getProjectName() + " "
					+ this.getWorklistManager().getWorkType("4028809e0a43b9c4010a43c573480006").getName());

			this.getWorklistManager().assignWork(assigneeWork);
			getEndProcessCheckDao().create(projectCode, assigneeWork.getId(), "N");
			
			if (projectSimpleInfo.getcoGRP().equals("G")) {
				getPmsInfoMailSender().sendKCSMACSMail(projectCode);
			} else {
				getPmsInfoMailSender().sendRndCSMail(projectCode);
			}			
			
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}// ������ �� ���� ����
		//getEndProcessCheckDao().create(projectCode, "VOC", "N");
		// ���� ���۰�� ������Ʈ
		this.getProjectEndingDao().sendInfoResearchCustomerSatisfactionTask(projectCode);

	}

	/**
	 * ���� ���� ������
	 */
	@Override
	public void assignPublicEduCustomerSatisfactionTask(String projectCode) throws ProjectException {
		try {
			ProjectSimpleInfo projectSimpleInfo = getProjectDao().getProjectSimpleInfo(projectCode);
			List<ProjectCsrInfo> projectCsrInfoList = this.getProjectMasterInfoManager().getProjectAllCsrInfo(projectCode);

			for (ProjectCsrInfo projectCsrInfo : projectCsrInfoList) {
				Work assigneeWork = this.getWorklistManager().getWorkTemplate("4028809e0a43b9c4010a43d61140000a", "PMS", projectSimpleInfo.getPmSsn(),
						projectCode, null, null);
				assigneeWork.setId(IdGenerator.generate(IdGenerator.PREFIX_WORK));
				assigneeWork.setRefWorkId3(String.valueOf(projectCsrInfo.getSeq()));
				assigneeWork.setCreateDate(new Date());
				assigneeWork.setDraftDate(new Date());
				assigneeWork.setDraftUserId(projectSimpleInfo.getPmSsn());
				assigneeWork.setLevel(SanctionConstants.SANCTION_STATE_RATING);
				assigneeWork.setState(WorkConstants.WORK_STATE_READY);
				assigneeWork.setTitle(projectSimpleInfo.getProjectName() + " "
						+ this.getWorklistManager().getWorkType("4028809e0a43b9c4010a43d61140000a").getName());
				
				this.getWorklistManager().assignWork(assigneeWork);
				getEndProcessCheckDao().create(projectCode, assigneeWork.getId(), "N");
			}
			getPmsInfoMailSender().sendPublicEduCSMail(projectCode);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
	}

	/**
	 * ������ �߰� Ȯ��
	 */
	public void assignEndProcessTaskForMReview(String projectCode) throws ProjectException {
		ProjectSimpleInfo projectSimpleInfo = getProjectDao().getProjectSimpleInfo(projectCode);
		Work assigneeWork = null;

		assigneeWork = this.getWorklistManager().getWorkTemplate("4028809e0a43b9c4010a43c573580009", "PMS", projectSimpleInfo.getPmSsn(),
				projectCode, null, null);//���� ����� ar --> pm ���� ���� 20180126 -jiwoong
		assigneeWork.setId(IdGenerator.generate(IdGenerator.PREFIX_WORK));
		assigneeWork.setCreateDate(new Date());
		assigneeWork.setDraftDate(new Date());
		// ������, ���� �� ��� PL�� �������� �ۼ���
		if (projectSimpleInfo.getBusinessTypeCode().equals("BTA") || projectSimpleInfo.getBusinessTypeCode().equals("BTJ")) {
			assigneeWork.setDraftUserId(projectSimpleInfo.getPlSsn());
		} else {
			assigneeWork.setDraftUserId(projectSimpleInfo.getPmSsn());
		}
		assigneeWork.setLevel(SanctionConstants.SANCTION_STATE_ENDRIVIEW_RIVIEW);
		assigneeWork.setState(WorkConstants.WORK_STATE_READY);
		assigneeWork.setTitle(projectSimpleInfo.getProjectName() + " "
				+ this.getWorklistManager().getWorkType("4028809e0a43b9c4010a43c573580009").getName());

		this.getWorklistManager().assignWork(assigneeWork);
		//getEndProcessCheckDao().create(projectCode, assigneeWork.getId(), "N");
	}

	/**
	 * ������ �߰� Ȯ��
	 */
	public void assignEndProcessTaskForM2Review(String projectCode) throws ProjectException {
		ProjectSimpleInfo projectSimpleInfo = getProjectDao().getProjectSimpleInfo(projectCode);

		Work assigneeWork = this.getWorklistManager().getWorkTemplate("4028809e0a43b9c4010a43c573580009", "PMS", projectSimpleInfo.getPuSsn(),
				projectCode, null, null);
		assigneeWork.setId(IdGenerator.generate(IdGenerator.PREFIX_WORK));
		assigneeWork.setCreateDate(new Date());
		assigneeWork.setDraftDate(new Date());
		assigneeWork.setDraftUserId(projectSimpleInfo.getPuSsn());
		assigneeWork.setLevel(SanctionConstants.SANCTION_STATE_ENDRIVIEW_RIVIEW);
		assigneeWork.setState(WorkConstants.WORK_STATE_READY);
		assigneeWork.setTitle(projectSimpleInfo.getProjectName() + " "
				+ this.getWorklistManager().getWorkType("4028809e0a43b9c4010a43c573580009").getName());

		this.getWorklistManager().assignWork(assigneeWork);
		//getEndProcessCheckDao().create(projectCode, assigneeWork.getId(), "N");
	}

	/**
	 * ������ ���� Ȯ��
	 */
	public void assignEndProcessTaskForCOO(String projectCode) throws ProjectException {
		ProjectSimpleInfo projectSimpleInfo = getProjectDao().getProjectSimpleInfo(projectCode);
		String cooSsn = getEndProcessCheckDao().getCooSsn(projectSimpleInfo.getRunningDetpCode());
		Work assigneeWork = null;

		assigneeWork = this.getWorklistManager().getWorkTemplate("4028809e0a43b9c4010a43c573580009", "PMS", cooSsn, projectCode, null, null);
		assigneeWork.setId(IdGenerator.generate(IdGenerator.PREFIX_WORK));
		assigneeWork.setCreateDate(new Date());
		assigneeWork.setDraftDate(new Date());
		assigneeWork.setLevel(SanctionConstants.SANCTION_STATE_ENDRIVIEW_APPROVE);
		assigneeWork.setState(WorkConstants.WORK_STATE_READY);
		assigneeWork.setTitle(projectSimpleInfo.getProjectName() + " "
				+ this.getWorklistManager().getWorkType("4028809e0a43b9c4010a43c573580009").getName());
		
		if (projectSimpleInfo.getBusinessTypeCode().equals("BTA")|| projectSimpleInfo.getBusinessTypeCode().equals("BTJ")) {
			assigneeWork.setDraftUserId(projectSimpleInfo.getPlSsn());
		} else {
			assigneeWork.setDraftUserId(projectSimpleInfo.getPmSsn());
		}
		this.getWorklistManager().assignWork(assigneeWork);
		//getEndProcessCheckDao().create(projectCode, assigneeWork.getId(), "N");
	}

	/**
	 * ������ ���� ���� - KM
	 */
	public void assignEndProcessTaskForKM(String projectCode) throws ProjectException {
		ProjectSimpleInfo projectSimpleInfo = getProjectDao().getProjectSimpleInfo(projectCode);
		String kmSsn = getEndProcessCheckDao().getKMSsn();
		Work assigneeWork = null;
		
		assigneeWork = this.getWorklistManager().getWorkTemplate("4028809e0a43b9c4010a43c573580009", "PMS", kmSsn, projectCode, null,
				null);
		assigneeWork.setId(IdGenerator.generate(IdGenerator.PREFIX_WORK));
		assigneeWork.setCreateDate(new Date());
		assigneeWork.setDraftDate(new Date());
		if (projectSimpleInfo.getBusinessTypeCode().equals("BTA")) {
			assigneeWork.setDraftUserId(projectSimpleInfo.getPlSsn());
		} else {
			assigneeWork.setDraftUserId(projectSimpleInfo.getPmSsn());
		}
		assigneeWork.setLevel(SanctionConstants.SANCTION_STATE_ENDRIVIEW_VERIFICATE);
		assigneeWork.setState(WorkConstants.WORK_STATE_READY);
		assigneeWork.setTitle(projectSimpleInfo.getProjectName() + " "
				+ this.getWorklistManager().getWorkType("4028809e0a43b9c4010a43c573580009").getName());

		this.getWorklistManager().assignWork(assigneeWork);
		//getEndProcessCheckDao().create(projectCode, assigneeWork.getId(), "N");
	}

	/**
	 * ���� ���μ���(������ �ۼ�����) 
	 * ����ü - ��������, ���̳�-����, ����, ����, ����, �系����, ����ġ : PM 
	 * ����ü - ������ : PL
	 * (non-Javadoc)
	 */
	public void assignEndProcessTask(String projectCode) throws ProjectException {
		ProjectSimpleInfo projectSimpleInfo = getProjectDao().getProjectSimpleInfo(projectCode);
		Work assigneeWork = null;

		if (projectSimpleInfo.getBusinessTypeCode().equals("BTA") || projectSimpleInfo.getBusinessTypeCode().equals("BTJ")) {
			assigneeWork = this.getWorklistManager().getWorkTemplate("4028809e0a43b9c4010a43c573580009", "PMS", projectSimpleInfo.getPlSsn(),
					projectCode, null, null);
			assigneeWork.setId(IdGenerator.generate(IdGenerator.PREFIX_WORK));
			assigneeWork.setCreateDate(new Date());
			assigneeWork.setDraftDate(new Date());
			assigneeWork.setDraftUserId(projectSimpleInfo.getPlSsn());
			assigneeWork.setLevel(SanctionConstants.SANCTION_STATE_ENDRIVIEW_DRAFT);
			assigneeWork.setState(WorkConstants.WORK_STATE_READY);
			assigneeWork.setTitle(projectSimpleInfo.getProjectName() + " "
					+ this.getWorklistManager().getWorkType("4028809e0a43b9c4010a43c573580009").getName());
			getProjectDao().updateProjectEndTaskState(projectCode, "next:review-1-init");
		} else {
			assigneeWork = this.getWorklistManager().getWorkTemplate("4028809e0a43b9c4010a43c573580009", "PMS", projectSimpleInfo.getPmSsn(),
					projectCode, null, null);
			assigneeWork.setId(IdGenerator.generate(IdGenerator.PREFIX_WORK));
			assigneeWork.setCreateDate(new Date());
			assigneeWork.setDraftDate(new Date());
			assigneeWork.setDraftUserId(projectSimpleInfo.getPmSsn());
			assigneeWork.setLevel(SanctionConstants.SANCTION_STATE_ENDRIVIEW_DRAFT);
			assigneeWork.setState(WorkConstants.WORK_STATE_READY);
			assigneeWork.setTitle(projectSimpleInfo.getProjectName() + " "
					+ this.getWorklistManager().getWorkType("4028809e0a43b9c4010a43c573580009").getName());
			getProjectDao().updateProjectEndTaskState(projectCode, "next:review-1-init");
		}
		this.getWorklistManager().assignWork(assigneeWork);
		getEndProcessCheckDao().create(projectCode, assigneeWork.getId(), "N");
	}

	/**
	 * ������ ���� ���μ���(����ó�� �ۼ�����) ����ü - ��������, ���̳�-����, ����, ����, ����, �系����, ����ġ : PM ����ü - ������ : PL
	 */
	public void assignAbnormalEndProcessTask(String projectCode) throws ProjectException {
		ProjectSimpleInfo projectSimpleInfo = getProjectDao().getProjectSimpleInfo(projectCode);
		Work assigneeWork = null;

		if (projectSimpleInfo.getBusinessTypeCode().equals("BTA") || projectSimpleInfo.getBusinessTypeCode().equals("BTJ")) {
			assigneeWork = this.getWorklistManager().getWorkTemplate("4028809e0a43b9c4010a43c573580013", "PMS", projectSimpleInfo.getPlSsn(),
					projectCode, null, null);
			assigneeWork.setId(IdGenerator.generate(IdGenerator.PREFIX_WORK));
			assigneeWork.setCreateDate(new Date());
			assigneeWork.setDraftDate(new Date());
			assigneeWork.setDraftUserId(projectSimpleInfo.getPlSsn());
			assigneeWork.setLevel(SanctionConstants.SANCTION_STATE_DRAFT);
			assigneeWork.setState(WorkConstants.WORK_STATE_READY);
			assigneeWork.setTitle(projectSimpleInfo.getProjectName() + " "
					+ this.getWorklistManager().getWorkType("4028809e0a43b9c4010a43c573580013").getName());
		} else {
			assigneeWork = this.getWorklistManager().getWorkTemplate("4028809e0a43b9c4010a43c573580013", "PMS", projectSimpleInfo.getPmSsn(),
					projectCode, null, null);
			assigneeWork.setId(IdGenerator.generate(IdGenerator.PREFIX_WORK));
			assigneeWork.setCreateDate(new Date());
			assigneeWork.setDraftDate(new Date());
			assigneeWork.setDraftUserId(projectSimpleInfo.getPmSsn());
			assigneeWork.setLevel(SanctionConstants.SANCTION_STATE_DRAFT);
			assigneeWork.setState(WorkConstants.WORK_STATE_READY);
			assigneeWork.setTitle(projectSimpleInfo.getProjectName() + " "
					+ this.getWorklistManager().getWorkType("4028809e0a43b9c4010a43c573580013").getName());
		}

		this.getWorklistManager().assignWork(assigneeWork);
		getEndProcessCheckDao().create(projectCode, assigneeWork.getId(), "N");
	}

	/**
	 * ���� ������ �� - ����ü PM
	 */
	public void assignEduSatisfactionTask(String projectCode) throws ProjectException {
		ProjectSimpleInfo projectSimpleInfo = getProjectDao().getProjectSimpleInfo(projectCode);

		Work assigneeWork = this.getWorklistManager().getWorkTemplate("4028809e0a43b9c4010a43d61140000a", "PMS", projectSimpleInfo.getPmSsn(),
				projectCode, null, null);
		assigneeWork.setId(IdGenerator.generate(IdGenerator.PREFIX_WORK));
		assigneeWork.setCreateDate(new Date());
		assigneeWork.setDraftDate(new Date());
		assigneeWork.setDraftUserId(projectSimpleInfo.getPmSsn());
		assigneeWork.setLevel(SanctionConstants.SANCTION_STATE_RATING);
		assigneeWork.setState(WorkConstants.WORK_STATE_READY);
		assigneeWork.setTitle(projectSimpleInfo.getProjectName() + " "
				+ this.getWorklistManager().getWorkType("4028809e0a43b9c4010a43d61140000a").getName());

		this.getWorklistManager().assignWork(assigneeWork);
		getEndProcessCheckDao().create(projectCode, assigneeWork.getId(), "N");
	}

	/**
	 * �系  ���� ������ �� - ����ü PM
	 */
	@Override
	public void assignEduOnlineSatisfactionTask(String projectCode) throws ProjectException {
		try {
			ProjectSimpleInfo projectSimpleInfo = getProjectDao().getProjectSimpleInfo(projectCode);
			List<ProjectCsrInfo> projectCsrInfoList = this.getProjectMasterInfoManager().getProjectAllCsrInfo(projectCode);

			for (ProjectCsrInfo projectCsrInfo : projectCsrInfoList) {

				Work assigneeWork = this.getWorklistManager().getWorkTemplate("4028809e0a43b9c4010a43d61140000d", "PMS",
						projectSimpleInfo.getPmSsn(), projectCode, null, null);
				assigneeWork.setId(IdGenerator.generate(IdGenerator.PREFIX_WORK));
				assigneeWork.setRefWorkId3(String.valueOf(projectCsrInfo.getSeq()));
				assigneeWork.setCreateDate(new Date());
				assigneeWork.setDraftDate(new Date());
				assigneeWork.setDraftUserId(projectSimpleInfo.getPmSsn());
				assigneeWork.setLevel(SanctionConstants.SANCTION_STATE_RATING);
				assigneeWork.setState(WorkConstants.WORK_STATE_READY);
				assigneeWork.setTitle(projectSimpleInfo.getProjectName() + " "
						+ this.getWorklistManager().getWorkType("4028809e0a43b9c4010a43d61140000d").getName());

				this.getWorklistManager().assignWork(assigneeWork);
				getEndProcessCheckDao().create(projectCode, assigneeWork.getId(), "N");
			}

			getPmsInfoMailSender().sendCompanyEduCSMail(projectCode);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
	}
	
	/**
	 * �系  ���� ������ �� - ����ü PM
	 */
	@Override
	public void assignSpLectureOnlineSatisfactionTask(String projectCode) throws ProjectException {
		try {
			ProjectSimpleInfo projectSimpleInfo = getProjectDao().getProjectSimpleInfo(projectCode);
			List<ProjectCsrInfo> projectCsrInfoList = this.getProjectMasterInfoManager().getProjectAllCsrInfo(projectCode);
			
			for (ProjectCsrInfo projectCsrInfo : projectCsrInfoList) {
				
				Work assigneeWork = this.getWorklistManager().getWorkTemplate("4028809e0a43b9c4010a43d61140000f", "PMS",
						projectSimpleInfo.getPmSsn(), projectCode, null, null);
				assigneeWork.setId(IdGenerator.generate(IdGenerator.PREFIX_WORK));
				assigneeWork.setRefWorkId3(String.valueOf(projectCsrInfo.getSeq()));
				assigneeWork.setCreateDate(new Date());
				assigneeWork.setDraftDate(new Date());
				assigneeWork.setDraftUserId(projectSimpleInfo.getPmSsn());
				assigneeWork.setLevel(SanctionConstants.SANCTION_STATE_RATING);
				assigneeWork.setState(WorkConstants.WORK_STATE_READY);
				assigneeWork.setTitle(projectSimpleInfo.getProjectName() + " "
						+ this.getWorklistManager().getWorkType("4028809e0a43b9c4010a43d61140000f").getName());
				
				this.getWorklistManager().assignWork(assigneeWork);
				getEndProcessCheckDao().create(projectCode, assigneeWork.getId(), "N");
			}
			
			getPmsInfoMailSender().sendCompanyEduCSMail(projectCode);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
	}

	/**
	 * ���� ���� �� - ����ü PM
	 */
	public void assignEduLectureTask(String projectCode) throws ProjectException {
		ProjectSimpleInfo projectSimpleInfo = getProjectDao().getProjectSimpleInfo(projectCode);

		Work assigneeWork = this.getWorklistManager().getWorkTemplate("4028809e0a43b9c4010a43d6114f000b", "PMS", projectSimpleInfo.getPmSsn(),
				projectCode, null, null);
		assigneeWork.setId(IdGenerator.generate(IdGenerator.PREFIX_WORK));
		assigneeWork.setCreateDate(new Date());
		assigneeWork.setDraftDate(new Date());
		assigneeWork.setDraftUserId(projectSimpleInfo.getPmSsn());
		assigneeWork.setLevel(SanctionConstants.SANCTION_STATE_RATING);
		assigneeWork.setState(WorkConstants.WORK_STATE_READY);
		assigneeWork.setTitle(projectSimpleInfo.getProjectName() + " "
				+ this.getWorklistManager().getWorkType("4028809e0a43b9c4010a43d6114f000b").getName());

		this.getWorklistManager().assignWork(assigneeWork);
		getEndProcessCheckDao().create(projectCode, assigneeWork.getId(), "N");
	}

	/**
	 * ���� ������ �� - ����ü PM
	 */
	public void assignTrainingOnlineSatisfactionTask(String projectCode) throws ProjectException {
		try {
			ProjectSimpleInfo projectSimpleInfo = getProjectDao().getProjectSimpleInfo(projectCode);
			List<ProjectCsrInfo> projectCsrInfoList = this.getProjectMasterInfoManager().getProjectAllCsrInfo(projectCode);

			for (ProjectCsrInfo projectCsrInfo : projectCsrInfoList) {

				Work assigneeWork = this.getWorklistManager().getWorkTemplate("4028809e0a43b9c4010a43d6114f000e", "PMS",
						projectSimpleInfo.getPmSsn(), projectCode, null, null);
				assigneeWork.setId(IdGenerator.generate(IdGenerator.PREFIX_WORK));
				assigneeWork.setRefWorkId3(String.valueOf(projectCsrInfo.getSeq()));
				assigneeWork.setCreateDate(new Date());
				assigneeWork.setDraftDate(new Date());
				assigneeWork.setDraftUserId(projectSimpleInfo.getPmSsn());
				assigneeWork.setLevel(SanctionConstants.SANCTION_STATE_RATING);
				assigneeWork.setState(WorkConstants.WORK_STATE_READY);
				assigneeWork.setTitle(projectSimpleInfo.getProjectName() + " "
						+ this.getWorklistManager().getWorkType("4028809e0a43b9c4010a43d6114f000e").getName());

				this.getWorklistManager().assignWork(assigneeWork);
				getEndProcessCheckDao().create(projectCode, assigneeWork.getId(), "N");
			}

			getPmsInfoMailSender().sendCompanyEduCSMail(projectCode);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
	}

	/**
	 * ���� ������ �� - ����ü PM
	 */
	public void assignTrainingSatisfactionTask(String projectCode) throws ProjectException {
		ProjectSimpleInfo projectSimpleInfo = getProjectDao().getProjectSimpleInfo(projectCode);
		
		Work assigneeWork = this.getWorklistManager().getWorkTemplate("4028809e0a43b9c4010a43d6114f000c", "PMS", projectSimpleInfo.getPmSsn(),
				projectCode, null, null);
		assigneeWork.setId(IdGenerator.generate(IdGenerator.PREFIX_WORK));
		assigneeWork.setCreateDate(new Date());
		assigneeWork.setDraftDate(new Date());
		assigneeWork.setDraftUserId(projectSimpleInfo.getPmSsn());
		assigneeWork.setLevel(SanctionConstants.SANCTION_STATE_RATING);
		assigneeWork.setState(WorkConstants.WORK_STATE_READY);
		assigneeWork.setTitle(projectSimpleInfo.getProjectName() + " "
				+ this.getWorklistManager().getWorkType("4028809e0a43b9c4010a43d6114f000c").getName());
		
		this.getWorklistManager().assignWork(assigneeWork);
		getEndProcessCheckDao().create(projectCode, assigneeWork.getId(), "N");
	}

	@Override
	public void store(EndProcessCheck endProcessCheck) throws ProjectException {
		getEndProcessCheckDao().store(endProcessCheck);
	}

	@Override
	public boolean isRollingAndRateingFinished(String projectCode) throws ProjectException {
		boolean isFinished = endProcessCheckDao.isFinished(projectCode);
		if (isFinished) {
			this.getProjectDao().updateProjectState(projectCode, "5");
		}
		return isFinished;
	}

	@Override
	public List<String> getEndProcessTaskList(String projectCode) throws ProjectException {
		return getProjectDao().getEndProcessTaskList(projectCode);
	}

	public ProjectEndingDao getProjectEndingDao() {
		return projectEndingDao;
	}

	public WorklistManager getWorklistManager() {
		return worklistManager;
	}

	public void setWorklistManager(WorklistManager worklistManager) {
		this.worklistManager = worklistManager;
	}

	public void setProjectEndingDao(ProjectEndingDao projectEndingDao) {
		this.projectEndingDao = projectEndingDao;
	}

	public ProjectDao getProjectDao() {
		return projectDao;
	}

	public void setProjectDao(ProjectDao projectDao) {
		this.projectDao = projectDao;
	}

	public EndProcessCheckDao getEndProcessCheckDao() {
		return this.endProcessCheckDao;
	}

	public void setEndProcessCheckDao(EndProcessCheckDao endProcessCheckDao) {
		this.endProcessCheckDao = endProcessCheckDao;
	}

	public PmsInfoMailSender getPmsInfoMailSender() {
		return pmsInfoMailSender;
	}

	public void setPmsInfoMailSender(PmsInfoMailSender pmsInfoMailSender) {
		this.pmsInfoMailSender = pmsInfoMailSender;
	}

	public ProjectMemberDao getProjectMemberDao() {
		return projectMemberDao;
	}

	public void setProjectMemberDao(ProjectMemberDao projectMemberDao) {
		this.projectMemberDao = projectMemberDao;
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


}
