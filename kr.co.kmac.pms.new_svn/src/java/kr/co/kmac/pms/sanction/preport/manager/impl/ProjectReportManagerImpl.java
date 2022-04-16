package kr.co.kmac.pms.sanction.preport.manager.impl;

import java.util.Date;
import java.util.List;

import kr.co.kmac.pms.common.util.DateTime;
import kr.co.kmac.pms.common.util.DateUtil;
import kr.co.kmac.pms.common.util.IdGenerator;
import kr.co.kmac.pms.common.worklist.data.Work;
import kr.co.kmac.pms.common.worklist.data.WorkConstants;
import kr.co.kmac.pms.common.worklist.manager.WorklistManager;
import kr.co.kmac.pms.project.preport.manager.ProjectReportInfoManager;
import kr.co.kmac.pms.sanction.exception.SanctionException;
import kr.co.kmac.pms.sanction.general.action.GeneralSanctionAction;
import kr.co.kmac.pms.sanction.general.data.SanctionConstants;
import kr.co.kmac.pms.sanction.preport.dao.ProjectReportContentDao;
import kr.co.kmac.pms.sanction.preport.data.ProjectReportContent;
import kr.co.kmac.pms.sanction.preport.manager.ProjectReportManager;
import kr.co.kmac.pms.service.mailsender.manager.PmsInfoMailSender;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;

public class ProjectReportManagerImpl implements ProjectReportManager {
	private static final Log logger = LogFactory.getLog(GeneralSanctionAction.class);
	private ProjectReportContentDao projectReportContentDao;
	private WorklistManager worklistManager;
	private ProjectReportInfoManager projectReportInfoManager;
	private PmsInfoMailSender pmsInfoMailSender;

	@Override
	public void createProjectReport(ProjectReportContent projectReportContent) throws SanctionException {
		this.getProjectReportContentDao().create(projectReportContent);
	}
	
	@Override
	public void entrustProjectReport(ProjectReportContent projectReportContent) throws SanctionException {
		Work work = this.getWorklistManager().getWork(projectReportContent.getWorkId());
		
		work.setAssigneeId(projectReportContent.getEntrustUserSsn());
		this.getWorklistManager().entrustWork(work);
		
		// Job Date: 2010-07-20	Author: yhyim	Description: 지도일지 업데이트는 무의미 하다고 여겨 주석처리 함
		//this.updateProjectReport(projectReportContent);
	}

	@Override
	public void executeProjectReport(ProjectReportContent projectReportContent) throws SanctionException {
		try {
			String assigneeId = null;
			String taskTypeId = null;
			String level = null;
			Work work = this.getWorklistManager().getWork(projectReportContent.getWorkId());
			// 반려 여부 체크
			if (projectReportContent.getAppYN() != null && projectReportContent.getAppYN().equals("N")) {
				projectReportContent.setWriteDate("");
				projectReportContent.setRevieweDate("");
				projectReportContent.setApproveDate("");
				assigneeId = projectReportContent.getWriterSsn();
				projectReportContent.setState("reject");
				this.getWorklistManager().rejectWork(work.getId());
			} else {
				if (projectReportContent.getState().equals("writer") || projectReportContent.getState().equals("reject")) {
					projectReportContent.setWriteDate(DateTime.getDateString(""));
					if (projectReportContent.getReviewerSsn() != null && !projectReportContent.getReviewerSsn().equals("")) {
						assigneeId = projectReportContent.getReviewerSsn();
						projectReportContent.setState("reviewer");
					} else {
						assigneeId = projectReportContent.getApproverSsn();
						projectReportContent.setState("approver");
					}
				} else if (projectReportContent.getState().equals("reviewer")) {
					projectReportContent.setRevieweDate(DateTime.getDateString(""));
					assigneeId = projectReportContent.getApproverSsn();
					projectReportContent.setState("approver");
				} else if (projectReportContent.getState().equals("approver")) {
					projectReportContent.setApproveDate(DateTime.getDateString(""));
					assigneeId = projectReportContent.getApproverSsn();
					projectReportContent.setState("complete");
				} else {
					projectReportContent.setApproveDate(DateTime.getDateString(""));
					assigneeId = projectReportContent.getApproverSsn();
					projectReportContent.setState("complete");
				}
				this.getWorklistManager().executeWork(work.getId());
			}
			this.updateProjectReport(projectReportContent);

			// 업무를 할당한다.
			if (projectReportContent.getState().equals("writer")) {
				taskTypeId = "4028809e0a20e689010a2b4be76c0046";
				level = SanctionConstants.SANCTION_STATE_DRAFT;
			} else if (projectReportContent.getState().equals("reject")) {
				taskTypeId = "4028809e0a20e689010a2b4be76c0047";
				level = SanctionConstants.SANCTION_STATE_REJECT_DRAFT;
			} else if (projectReportContent.getState().equals("reviewer")) {
				taskTypeId = "4028809e0a20e689010a2b4be76c0048";
				level = SanctionConstants.SANCTION_STATE_REVIEW;
			} else if (projectReportContent.getState().equals("approver")) {
				taskTypeId = "4028809e0a20e689010a2b4be76c0049";
				level = SanctionConstants.SANCTION_STATE_APPROVE;
			}
			
			// Job Date: 2010-05-11 Author: yhyim Description: 지도일지가 승인되면 업무가 종료되도록 조건절을 수정함(complete 부분 추가)
			if ((assigneeId != null && !assigneeId.trim().equals("")) && !projectReportContent.getState().equals("complete")) {
				Work assigneeWork = this.getWorklistManager().getWorkTemplate(taskTypeId, projectReportContent.getWriterSsn(), assigneeId,
						projectReportContent.getProjectCode(), projectReportContent.getTaskFormTypeId(), projectReportContent.getSeq());
				assigneeWork.setId(IdGenerator.generate(IdGenerator.PREFIX_WORK));
				assigneeWork.setCreateDate(new Date());
				assigneeWork.setDraftDate(DateUtil.getDateTime("yyyyMMdd", projectReportContent.getWriteDate()));
				assigneeWork.setDraftUserId(projectReportContent.getWriterSsn());
				assigneeWork.setLevel(level);
				assigneeWork.setState(WorkConstants.WORK_STATE_READY);
				assigneeWork.setTitle(this.getWorklistManager().getWorkType(taskTypeId).getName());

				this.getWorklistManager().assignWork(assigneeWork);
				System.out.println(assigneeWork.toString());
			}
			
			// Job Date: 2017-10-25	Author: yhyim	Description: 검토자 또는 승인자가 입력한 의견을 지도일지 작성자와 입력자에게 이메일 전송
			try {
				if ( (projectReportContent.getState().equals("approver") && projectReportContent.getRevieweOpinion() != null && !projectReportContent.getRevieweOpinion().equals("")) 
						|| (projectReportContent.getState().equals("complete") && projectReportContent.getApproveOpinion() != null && !projectReportContent.getApproveOpinion().equals(""))
						|| (projectReportContent.getState().equals("reject") && projectReportContent.getApproveOpinion() != null && !projectReportContent.getApproveOpinion().equals(""))
				)
					this.getPmsInfoMailSender().sendProjectReportContentOpinion(projectReportContent);
			} catch (Exception e) {
				logger.error("Send Mail error");
			}
			
			System.out.println(work.toString());
		} catch (SanctionException e) {
			e.printStackTrace();
			throw new SanctionException();
		}
	}

	@Override
	public void deleteProjectReport1(String projectCode, String taskFormTypeId, String seq) throws SanctionException {
		ProjectReportContent projectReportContent = this.getProjectReportContent1(projectCode, taskFormTypeId, seq);

		Work work = this.getWorklistManager().getWorkList(projectCode, taskFormTypeId, seq);
		this.getWorklistManager().terminatWork(work.getId());
		this.getProjectReportInfoManager().deleteProjectReportInfo(projectReportContent.getProjectCode(),
				projectReportContent.getAssignDate().substring(0, 4), projectReportContent.getAssignDate().substring(4, 6),
				projectReportContent.getAssignDate().substring(6, 8), projectReportContent.getWriterSsn());
		this.getProjectReportContentDao().delete1(projectCode, taskFormTypeId, seq);
	}

	@Override
	public void deleteProjectReport2(String projectCode, String assignDate, String writerSsn) throws SanctionException {
		this.getProjectReportContentDao().delete2(projectCode, assignDate, writerSsn);
	}

	@Override
	public List<ProjectReportContent> getProjectReportContent1(String projectCode, String taskFormTypeId) throws DataAccessException {
		List<ProjectReportContent> list = null;
		if (taskFormTypeId == null || taskFormTypeId.equals(""))
			list = this.getProjectReportContentDao().getProjectReportContent1(projectCode);
		else
			list = this.getProjectReportContentDao().getProjectReportContent1(projectCode, taskFormTypeId);

		return list;
	}

	@Override
	public ProjectReportContent getProjectReportContent1(String projectCode, String taskFormTypeId, String seq) throws DataAccessException {
		return this.getProjectReportContentDao().getProjectReportContent1(projectCode, taskFormTypeId, seq);
	}

	@Override
	public List<ProjectReportContent> getProjectReportContent2(String projectCode, String assignDate) throws DataAccessException {
		List<ProjectReportContent> list = null;
		if (assignDate == null || assignDate.equals(""))
			list = this.getProjectReportContentDao().getProjectReportContent1(projectCode);
		else
			list = this.getProjectReportContentDao().getProjectReportContent2(projectCode, assignDate);

		return list;
	}

	@Override
	public ProjectReportContent getProjectReportContent2(String projectCode, String assignDate, String writerSsn) throws DataAccessException {
		return this.getProjectReportContentDao().getProjectReportContent2(projectCode, assignDate, writerSsn);
	}

	@Override
	public void updateProjectReport(ProjectReportContent projectReportContent) throws SanctionException {
		this.getProjectReportContentDao().update(projectReportContent);
	}

	public ProjectReportContentDao getProjectReportContentDao() {
		return projectReportContentDao;
	}

	public void setProjectReportContentDao(ProjectReportContentDao projectReportContentDao) {
		this.projectReportContentDao = projectReportContentDao;
	}

	public WorklistManager getWorklistManager() {
		return worklistManager;
	}

	public void setWorklistManager(WorklistManager worklistManager) {
		this.worklistManager = worklistManager;
	}

	public ProjectReportInfoManager getProjectReportInfoManager() {
		return projectReportInfoManager;
	}

	public void setProjectReportInfoManager(ProjectReportInfoManager projectReportInfoManager) {
		this.projectReportInfoManager = projectReportInfoManager;
	}
	
	public PmsInfoMailSender getPmsInfoMailSender() {
		return pmsInfoMailSender;
	}

	public void setPmsInfoMailSender(PmsInfoMailSender pmsInfoMailSender) {
		this.pmsInfoMailSender = pmsInfoMailSender;
	}
}
