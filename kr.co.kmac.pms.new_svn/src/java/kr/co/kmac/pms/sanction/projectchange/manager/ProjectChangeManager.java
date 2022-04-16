package kr.co.kmac.pms.sanction.projectchange.manager;

import java.util.List;

import kr.co.kmac.pms.sanction.exception.SanctionException;
import kr.co.kmac.pms.sanction.projectchange.data.AddMemberChange;
import kr.co.kmac.pms.sanction.projectchange.data.AddMemberChangeArray;
import kr.co.kmac.pms.sanction.projectchange.data.RunningMemberChange;
import kr.co.kmac.pms.sanction.projectchange.data.RunningMemberChangeArray;
import kr.co.kmac.pms.sanction.projectchange.data.ScheduleChange;

public interface ProjectChangeManager {

	public void insertScheduleChange(ScheduleChange scheduleChange) throws SanctionException;

	public void updateScheduleChange(ScheduleChange scheduleChange) throws SanctionException;

	public void deleteScheduleChange(String projectCode, String scheduleChangeSeq) throws SanctionException;

	public ScheduleChange selectScheduleChange(String projectCode, String scheduleChangeSeq) throws SanctionException;

	public void finishScheduleChange(String projectCode, String scheduleChangeSeq) throws SanctionException;

	public void insertMemberChange(RunningMemberChangeArray runningMemberChangeArray, AddMemberChangeArray addMemberChangeArray);

	public void deleteMemberChange(String projectCode, String memberChangeSeq) throws SanctionException;

	public void updateMemberChange(RunningMemberChangeArray runningMemberChangeArray, AddMemberChangeArray addMemberChangeArray)
			throws SanctionException;

	public List<RunningMemberChange> selectRunningMemberChange(String projectCode, String memberChangeSeq) throws SanctionException;

	public List<AddMemberChange> selectAddMemberChange(String projectCode, String memberChangeSeq) throws SanctionException;

	public void finishMemberChange(String projectCode, String memberChangeSeq) throws SanctionException;
	
	public void updateMemberMMPlan(RunningMemberChangeArray runningMemberChangeArray) throws SanctionException; 
}
