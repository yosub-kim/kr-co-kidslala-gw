package kr.co.kmac.pms.common.worklist.manager;

import java.util.List;

import org.springframework.dao.DataAccessException;

import kr.co.kmac.pms.common.worklist.data.Work;
import kr.co.kmac.pms.common.worklist.data.WorkType;
import kr.co.kmac.pms.common.worklist.exception.WorklistException;

public interface WorklistManager {

	public Work getWork(String id) throws WorklistException;

	public List<Work> getWorkList(String refWorkId1) throws WorklistException;

	public List<Work> getWorkList(String refWorkId1, String refWorkId2) throws WorklistException;

	public Work getWorkList(String refWorkId1, String refWorkId2, String refWorkId3) throws WorklistException;

	public Work getWorkTemplate(String type, String assignerId, String assigneeId, String refWorkId1, String refWorkId2, String refWorkId3)
			throws WorklistException;

	public boolean updateWork(Work work) throws WorklistException;

	public boolean updateDraftDate(String id) throws WorklistException;

	public boolean assignWork(Work work) throws WorklistException;

	public void openWork(String id) throws WorklistException;

	public void executeWork(String id) throws WorklistException;

	public void terminatWork(String id) throws WorklistException;

	public void rejectWork(String id) throws WorklistException;

	public void entrustWork(Work work) throws WorklistException;

	public void createWorkType(WorkType workType) throws DataAccessException;

	public void deleteWorkType(String id) throws DataAccessException;

	public void udpateWorkType(WorkType workType) throws DataAccessException;

	public WorkType getWorkType(String id) throws DataAccessException;

}
