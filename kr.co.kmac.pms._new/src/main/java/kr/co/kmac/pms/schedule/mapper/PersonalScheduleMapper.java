package kr.co.kmac.pms.schedule.mapper;

import java.util.List;

import org.springframework.dao.DataAccessException;

import kr.co.kmac.pms.schedule.domain.ScheduleDetail;
import kr.co.kmac.pms.schedule.domain.ScheduleSearchParam;

public interface PersonalScheduleMapper {

	public int createPersonalSchedule(ScheduleDetail scheduleInfo) throws DataAccessException;

	public ScheduleDetail getPersonalSchedule(ScheduleSearchParam searchParam) throws DataAccessException;
	public ScheduleDetail getPersonalScheduleByProject(ScheduleSearchParam searchParam) throws DataAccessException;
	public ScheduleDetail getPersonalScheduleBycustomer_pickers(ScheduleSearchParam searchParam) throws DataAccessException;

	public List<ScheduleDetail> getPersonalScheduleByDate(ScheduleSearchParam searchParam) throws DataAccessException;

	public List<ScheduleDetail> getPersonalScheduleByMonth(ScheduleSearchParam searchParam) throws DataAccessException;
	
	public List<ScheduleDetail> getPersonalScheduleSearchByMonth(ScheduleSearchParam searchParam) throws DataAccessException;

	public int updatePersonalSchedule(ScheduleDetail scheduleInfo) throws DataAccessException;

	public int removePersonalSchedule(ScheduleSearchParam searchParam) throws DataAccessException;

}
