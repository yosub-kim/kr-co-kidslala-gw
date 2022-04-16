package kr.co.kmac.pms.common.worklist.manager.impl;

import java.util.Date;
import java.util.List;

import kr.co.kmac.pms.common.worklist.dao.WorkDao;
import kr.co.kmac.pms.common.worklist.dao.WorkTypeDao;
import kr.co.kmac.pms.common.worklist.data.Work;
import kr.co.kmac.pms.common.worklist.data.WorkConstants;
import kr.co.kmac.pms.common.worklist.data.WorkType;
import kr.co.kmac.pms.common.worklist.exception.WorklistException;
import kr.co.kmac.pms.common.worklist.manager.WorklistManager;

import org.springframework.dao.DataAccessException;

public class WorklistManagerImpl implements WorklistManager {

	private WorkDao workDao;
	private WorkTypeDao workTypeDao;

	public WorkTypeDao getWorkTypeDao() {
		return workTypeDao;
	}

	public void setWorkTypeDao(WorkTypeDao workTypeDao) {
		this.workTypeDao = workTypeDao;
	}

	public WorkDao getWorkDao() {
		return workDao;
	}

	public void setWorkDao(WorkDao workDao) {
		this.workDao = workDao;
	}

	@Override
	public boolean updateWork(Work work) throws WorklistException {
		return this.getWorkDao().updateWork(work);
	}

	@Override
	public boolean updateDraftDate(String id) throws WorklistException {
		return this.getWorkDao().updateDraftDate(id);
	}

	@Override
	public boolean assignWork(Work work) throws WorklistException {
		return this.getWorkDao().createWork(work);
	}

	@Override
	public void executeWork(String id) throws WorklistException {
		Work work = this.getWorkDao().getWork(id);
		work.setState(WorkConstants.WORK_STATE_EXECUTED);
		this.getWorkDao().updateWork(work);
	}

	@Override
	public Work getWork(String id) throws WorklistException {
		return this.getWorkDao().getWork(id);
	}

	@Override
	public List<Work> getWorkList(String refWorkId1) throws WorklistException {
		return this.getWorkDao().getWorkList(refWorkId1);
	}

	@Override
	public List<Work> getWorkList(String refWorkId1, String refWorkId2) throws WorklistException {
		return this.getWorkDao().getWorkList(refWorkId1, refWorkId2);
	}

	@Override
	public Work getWorkList(String refWorkId1, String refWorkId2, String refWorkId3) throws WorklistException {
		return this.getWorkDao().getWorkList(refWorkId1, refWorkId2, refWorkId3);
	}

	@Override
	public Work getWorkTemplate(String type, String assignerId, String assigneeId, String refWorkId1, String refWorkId2, String refWorkId3)
			throws WorklistException {
		Work work = new Work();
		work.setAssigneeId(assigneeId);
		work.setAssignerId(assignerId);
		work.setState(WorkConstants.WORK_STATE_READY);
		work.setType(type);
		work.setRefWorkId1(refWorkId1);
		work.setRefWorkId2(refWorkId2);
		work.setRefWorkId3(refWorkId3);
		work.setCreateDate(new Date());
		return work;
	}

	@Override
	public void openWork(String id) throws WorklistException {
		Work work = this.getWorkDao().getWork(id);
		work.setState(WorkConstants.WORK_STATE_OPENED);
		work.setOpenDate(new Date());
		this.getWorkDao().updateWork(work);
	}

	@Override
	public void rejectWork(String id) throws WorklistException {
		Work work = this.getWorkDao().getWork(id);
		work.setState(WorkConstants.WORK_STATE_REJECTED);
		work.setExecuteDate(new Date());
		this.getWorkDao().updateWork(work);
	}

	@Override
	public void terminatWork(String id) throws WorklistException {
		Work work = this.getWorkDao().getWork(id);
		work.setState(WorkConstants.WORK_STATE_TERMINATED);
		work.setExecuteDate(new Date());
		this.getWorkDao().updateWork(work);
	}

	@Override
	public void entrustWork(Work work) throws WorklistException {
		this.getWorkDao().updateWork(work);
	}

	@Override
	public void createWorkType(WorkType workType) throws DataAccessException {
		this.getWorkTypeDao().createWorkType(workType);
	}

	@Override
	public void deleteWorkType(String id) throws DataAccessException {
		this.getWorkTypeDao().deleteWorkType(id);
	}

	@Override
	public WorkType getWorkType(String id) throws DataAccessException {
		return (WorkType) this.getWorkTypeDao().getWorkType(id);
	}

	@Override
	public void udpateWorkType(WorkType workType) throws DataAccessException {
		this.getWorkTypeDao().updateWorkType(workType);
	}

}
