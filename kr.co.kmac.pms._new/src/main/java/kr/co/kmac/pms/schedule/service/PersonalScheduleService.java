package kr.co.kmac.pms.schedule.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.kmac.pms.schedule.domain.ScheduleDetail;
import kr.co.kmac.pms.schedule.domain.ScheduleSearchParam;
import kr.co.kmac.pms.schedule.mapper.PersonalScheduleMapper;

@Service
@Transactional
public class PersonalScheduleService {

	@Autowired
	private PersonalScheduleMapper personalScheduleMapper;

	public int createPersonalSchedule(ScheduleDetail scheduleDetail) {
		return personalScheduleMapper.createPersonalSchedule(scheduleDetail);
	}

	public int createPersonalSchedule(List<ScheduleDetail> scheduleDetailList) {
		int a = 0;
		for (ScheduleDetail scheduleDetail : scheduleDetailList) {
			a = personalScheduleMapper.createPersonalSchedule(scheduleDetail);
		}
		return a;
	}

	public List<ScheduleDetail> getPersonalScheduleByMonth(ScheduleSearchParam searchParam) {
		return personalScheduleMapper.getPersonalScheduleByMonth(searchParam);
	}
	
	public List<ScheduleDetail> getPersonalScheduleSearchByMonth(ScheduleSearchParam searchParam){
		return personalScheduleMapper.getPersonalScheduleSearchByMonth(searchParam);
	}

	public List<ScheduleDetail> getPersonalScheduleByDate(ScheduleSearchParam searchParam) {
		return personalScheduleMapper.getPersonalScheduleByDate(searchParam);
	}

	public ScheduleDetail getPersonalSchedule(ScheduleSearchParam searchParam) {
		if (searchParam.getType() != null && searchParam.getType().equals("프로젝트")) {
			return personalScheduleMapper.getPersonalScheduleByProject(searchParam);
		} else if (searchParam.getType() != null && searchParam.getType().equals("고객정보")) {
			return personalScheduleMapper.getPersonalScheduleBycustomer_pickers(searchParam);
		} else {
			return personalScheduleMapper.getPersonalSchedule(searchParam);
		}
	}

	public int updatePersonalSchedule(ScheduleDetail scheduleDetail) {
		return personalScheduleMapper.updatePersonalSchedule(scheduleDetail);
	}

	public int removePersonalSchedule(ScheduleSearchParam searchParam) {
		return personalScheduleMapper.removePersonalSchedule(searchParam);
	}

}
