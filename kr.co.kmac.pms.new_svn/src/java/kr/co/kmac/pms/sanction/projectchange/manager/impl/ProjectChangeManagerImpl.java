package kr.co.kmac.pms.sanction.projectchange.manager.impl;

import java.util.List;

import org.springframework.dao.DataAccessException;

import kr.co.kmac.pms.project.master.dao.ProjectMemberDao;
import kr.co.kmac.pms.project.master.data.ProjectMember;
import kr.co.kmac.pms.sanction.exception.SanctionException;
import kr.co.kmac.pms.sanction.projectchange.dao.MemberChangeDao;
import kr.co.kmac.pms.sanction.projectchange.dao.ScheduleChangeDao;
import kr.co.kmac.pms.sanction.projectchange.data.AddMemberChange;
import kr.co.kmac.pms.sanction.projectchange.data.AddMemberChangeArray;
import kr.co.kmac.pms.sanction.projectchange.data.RunningMemberChange;
import kr.co.kmac.pms.sanction.projectchange.data.RunningMemberChangeArray;
import kr.co.kmac.pms.sanction.projectchange.data.ScheduleChange;
import kr.co.kmac.pms.sanction.projectchange.manager.ProjectChangeManager;

public class ProjectChangeManagerImpl implements ProjectChangeManager {
	private ScheduleChangeDao scheduleChangeDao;
	private MemberChangeDao memberChangeDao;
	private ProjectMemberDao projectMemberDao;

	@Override
	public void insertScheduleChange(ScheduleChange scheduleChange) throws DataAccessException {
		this.getScheduleChangeDao().insertScheduleChange(scheduleChange);
	}

	@Override
	public void updateScheduleChange(ScheduleChange scheduleChange) throws DataAccessException {
		this.getScheduleChangeDao().updateScheduleChange(scheduleChange);
	}

	@Override
	public void deleteScheduleChange(String projectCode, String scheduleChangeSeq) throws SanctionException {
		this.getScheduleChangeDao().deleteScheduleChange(projectCode, scheduleChangeSeq);
	}

	@Override
	public ScheduleChange selectScheduleChange(String projectCode, String scheduleChangeSeq) throws DataAccessException {
		return this.getScheduleChangeDao().selectScheduleChange(projectCode, scheduleChangeSeq);
	}

	@Override
	public void finishScheduleChange(String projectCode, String scheduleChangeSeq) throws SanctionException {
		this.getScheduleChangeDao().finishScheduleChange(this.getScheduleChangeDao().selectScheduleChange(projectCode, scheduleChangeSeq));
	}

	@Override
	public void insertMemberChange(RunningMemberChangeArray runningMemberChangeArray, AddMemberChangeArray addMemberChangeArray) {
		this.getMemberChangeDao().deleteRunningMember(runningMemberChangeArray.getProjectCode(), String.valueOf(runningMemberChangeArray.getMemberChangeSeq()));
		this.getMemberChangeDao().deleteAddMember(addMemberChangeArray.getProjectCode(), String.valueOf(addMemberChangeArray.getMemberChangeSeq()));
		this.getMemberChangeDao().insertAddMember(addMemberChangeArray);
		this.getMemberChangeDao().insertRunningMember(runningMemberChangeArray);
	}

	@Override
	public void updateMemberChange(RunningMemberChangeArray runningMemberChangeArray, AddMemberChangeArray addMemberChangeArray)
			throws SanctionException {
		this.getMemberChangeDao().deleteRunningMember(runningMemberChangeArray.getProjectCode(), String.valueOf(runningMemberChangeArray.getMemberChangeSeq()));
		this.getMemberChangeDao().deleteAddMember(addMemberChangeArray.getProjectCode(), String.valueOf(addMemberChangeArray.getMemberChangeSeq()));
		this.getMemberChangeDao().insertAddMember(addMemberChangeArray);
		this.getMemberChangeDao().insertRunningMember(runningMemberChangeArray);
	}
	
	@Override
	public void updateMemberMMPlan(RunningMemberChangeArray runningMemberChangeArray) throws SanctionException {
		this.getMemberChangeDao().updateMemberMMPlan(runningMemberChangeArray);
	}

	@Override
	public void deleteMemberChange(String projectCode, String memberChangeSeq) throws SanctionException {
		this.getMemberChangeDao().deleteRunningMember(projectCode, memberChangeSeq);
		this.getMemberChangeDao().deleteAddMember(projectCode, memberChangeSeq);
	}

	@Override
	public void finishMemberChange(String projectCode, String memberChangeSeq) throws SanctionException {
		List<RunningMemberChange> runningMemberList = this.selectRunningMemberChange(projectCode, memberChangeSeq);
		
		this.getProjectMemberDao().delete(projectCode);
		this.getProjectMemberDao().insert(runningMemberList);

		List<AddMemberChange> addMemberList = this.selectAddMemberChange(projectCode, memberChangeSeq);
		if (addMemberList != null) {
			ProjectMember projectMember = null;
			for (AddMemberChange addMemberChange : addMemberList) {
				if (addMemberChange.getAddMemberSsn() != null && !"".equals(addMemberChange.getAddMemberSsn())) {
					projectMember = new ProjectMember();
					projectMember.setContributionCost(addMemberChange.getAddMemberContributionCost());
					projectMember.setCost(addMemberChange.getAddMemberCost());
					projectMember.setName(addMemberChange.getAddMemberName());
					projectMember.setProjectCode(projectCode);
					projectMember.setResRate(addMemberChange.getAddMemberResRate());
					projectMember.setRole(addMemberChange.getAddMemberRole());
					projectMember.setSsn(addMemberChange.getAddMemberSsn());
					projectMember.setTrainingYn(addMemberChange.getAddMemberTrainingYn());
					projectMember.setPosition(addMemberChange.getAddMemberPosition());
					projectMember.setStartDate(addMemberChange.getAddMemberStartDate());
					projectMember.setEndDate(addMemberChange.getAddMemberEndDate());
					try{
						this.getProjectMemberDao().insert(projectMember);						
					}catch(Exception e){
						this.getProjectMemberDao().updateMemberChange(
								projectMember, 
								addMemberChange.getAddMemberRole(),
								addMemberChange.getAddMemberTrainingYn());												
					}
				}
			}
		}
	}

	@Override
	public List<AddMemberChange> selectAddMemberChange(String projectCode, String memberChangeSeq) throws SanctionException {
		return this.getMemberChangeDao().selectAddMemberChange(projectCode, memberChangeSeq);
	}

	@Override
	public List<RunningMemberChange> selectRunningMemberChange(String projectCode, String memberChangeSeq) throws SanctionException {
		return this.getMemberChangeDao().selectRunningMemberChange(projectCode, memberChangeSeq);
	}

	public ScheduleChangeDao getScheduleChangeDao() {
		return scheduleChangeDao;
	}

	public void setScheduleChangeDao(ScheduleChangeDao scheduleChangeDao) {
		this.scheduleChangeDao = scheduleChangeDao;
	}

	public MemberChangeDao getMemberChangeDao() {
		return memberChangeDao;
	}

	public void setMemberChangeDao(MemberChangeDao memberChangeDao) {
		this.memberChangeDao = memberChangeDao;
	}

	public ProjectMemberDao getProjectMemberDao() {
		return projectMemberDao;
	}

	public void setProjectMemberDao(ProjectMemberDao projectMemberDao) {
		this.projectMemberDao = projectMemberDao;
	}

}
