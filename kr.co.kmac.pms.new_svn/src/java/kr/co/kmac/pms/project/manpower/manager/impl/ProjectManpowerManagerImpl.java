package kr.co.kmac.pms.project.manpower.manager.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import kr.co.kmac.pms.common.util.DateTime;
import kr.co.kmac.pms.project.exception.ProjectException;
import kr.co.kmac.pms.project.master.dao.ProjectDao;
import kr.co.kmac.pms.project.manpower.dao.ProjectManpowerDao;
import kr.co.kmac.pms.project.manpower.dao.ProjectManpowerDetailDao;
import kr.co.kmac.pms.project.manpower.data.ProjectManpower;
import kr.co.kmac.pms.project.manpower.manager.ProjectManpowerManager;
import kr.co.kmac.pms.project.preport.data.ProjectReportPlan;

public class ProjectManpowerManagerImpl implements ProjectManpowerManager {
	
	private ProjectManpowerDao projectManpowerDao;
	private ProjectManpowerDetailDao projectManpowerDetailDao;

	@Override
	public List<ProjectManpower> getProjectManpower(String projectCode, String year, String month) throws ProjectException {
		List<ProjectManpower> projectManpowerList = new ArrayList<ProjectManpower>();

		if (this.getProjectManpowerDao().count(projectCode, year, month) > 0) {
			List<ProjectManpower> list = this.getProjectManpowerDao().select(projectCode, year, month);
			for (ProjectManpower projectManpower : list) {
				List<ProjectManpower> detailList = this.getProjectManpowerDetailDao().select(projectCode, year, month, projectManpower.getDay());
				String[] ssnArray = new String[detailList.size()];
				String[] nameArray = new String[detailList.size()];
				int i = 0;
				for (ProjectManpower detail : detailList) {
					if (detail.getSsn() != null && !detail.getSsn().equals("")) {
						ssnArray[i] = detail.getSsn();
						nameArray[i] = detail.getName();
					}
					i++;
				}
				projectManpower.setSsnArray(ssnArray);
				projectManpower.setNameArray(nameArray);

				projectManpowerList.add(projectManpower);
			}

		} else {
			Calendar calendar = Calendar.getInstance();
			calendar.set(Integer.parseInt(year), Integer.parseInt(month) - 1, 1);
			int lastDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

			ProjectManpower projectManpower = null;
			for (int i = 1; i <= lastDay; i++) {
				projectManpower = new ProjectManpower();
				projectManpower.setProjectCode(projectCode);
				projectManpower.setYear(year);
				projectManpower.setMonth(month);
				projectManpower.setDay((i < 10 ? "0" + String.valueOf(calendar.get(Calendar.DATE)) : String.valueOf(calendar.get(Calendar.DATE))));
				projectManpower.setWeek(DateTime.getWeekOfDayString(calendar.get(Calendar.DAY_OF_WEEK)));
				projectManpower.setWorkSeq("");
				projectManpower.setWorkName("");
				projectManpower.setSsn("");
				projectManpower.setName("");
				projectManpowerList.add(projectManpower);
				calendar.add(Calendar.DATE, 1);
			}
		}
		return projectManpowerList;
	}
	
	@Override
	public List<ProjectManpower> getProjectWorkName(String projectCode, String year, String month) throws ProjectException {
		return this.getProjectManpowerDetailDao().select2(projectCode, year, month);
	}
	
	@Override
	public List<ProjectManpower> getProjecMonthWork(String projectCode, String year, String month) throws ProjectException {
		return this.getProjectManpowerDetailDao().retrieve3(projectCode, year, month);
	}
	
	@Override
	public List<ProjectManpower> getProjectManpowerForWreport(String projectCode, String startDate, String endDate) throws ProjectException {
		return this.getProjectManpowerDetailDao().retrieve(projectCode, startDate, endDate);
	}
	
	@Override
	public List<ProjectManpower> getProjectMember(String projectCode){
		return this.getProjectManpowerDetailDao().retrieve2(projectCode);
	}
	
	@Override
	public List<ProjectManpower> getProjectRunningInfo(String projectCode){
		return this.getProjectManpowerDetailDao().retrieve4(projectCode);
	}

	@Override
	public List<ProjectManpower> getProjectActivity(String projectCode){
		return this.getProjectManpowerDetailDao().retrieve5(projectCode);
	}


	@Override
	public ProjectManpower getProjectManpower(String projectCode, String year, String month, String day, String ssn) throws ProjectException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void storeProjectManpower(ProjectManpower projectManpower) throws ProjectException {
		if (this.getProjectManpowerDao().count(projectManpower.getProjectCode(), projectManpower.getYear(), projectManpower.getMonth()) <= 0) {
			Calendar calendar = Calendar.getInstance();
			calendar.set(Integer.parseInt(projectManpower.getYear()), Integer.parseInt(projectManpower.getMonth()) - 1, 1);
			int lastDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

			ProjectManpower tempProjectManpower = null;
			List<ProjectManpower> tempProjectManpowerList = new ArrayList<ProjectManpower>();
			for (int i = 1; i <= lastDay; i++) {
				tempProjectManpower = new ProjectManpower();
				tempProjectManpower.setProjectCode(projectManpower.getProjectCode());
				tempProjectManpower.setYear(projectManpower.getYear());
				tempProjectManpower.setMonth(projectManpower.getMonth());
				tempProjectManpower.setDay((i < 10 ? "0" + String.valueOf(calendar.get(Calendar.DATE)) : String
						.valueOf(calendar.get(Calendar.DATE))));
				tempProjectManpower.setWeek(DateTime.getWeekOfDayString(calendar.get(Calendar.DAY_OF_WEEK)));
				tempProjectManpower.setWorkSeq("");
				tempProjectManpower.setWorkName("");
				tempProjectManpower.setSsn("");
				tempProjectManpower.setName("");
				tempProjectManpowerList.add(tempProjectManpower);
				calendar.add(Calendar.DATE, 1);
			}
			this._storeProjectManpower(tempProjectManpowerList);
		}

		this.getProjectManpowerDao().update(projectManpower);
		if (projectManpower.getSsnArray() != null && projectManpower.getSsnArray().length > 0) {
			String ssn[] = projectManpower.getSsnArray();
			this.getProjectManpowerDetailDao().delete(projectManpower.getProjectCode(), projectManpower.getYear(), projectManpower.getMonth(),
					projectManpower.getDay());
			for (int i = 0; i < ssn.length; i++) {
				ProjectManpower prp = projectManpower;
				prp.setSsn(ssn[i].trim());
				try {
					this.getProjectManpowerDetailDao().insert(prp);
				} catch (Exception e) {
					// do nothing
				}
			}
		}
	}
	
	private void _storeProjectManpower(List<ProjectManpower> projectManpowerList) throws ProjectException {
		for (ProjectManpower projectReportPlan : projectManpowerList) {
			this.getProjectManpowerDao().insert(projectReportPlan);
		}
		for (ProjectManpower projectManpower : projectManpowerList) {
			if (projectManpower.getSsn() != null && !projectManpower.getSsn().equals(""))
				this.getProjectManpowerDetailDao().insert(projectManpower);
		}
	}

	@Override
	public void storeProjectManpower(List<ProjectManpower> projectManpowerList) throws ProjectException {
		for (ProjectManpower projectManpower : projectManpowerList) {
			this.storeProjectManpower(projectManpower);
		}
	}

	@Override
	public void deleteProjectManpower(String projectCode, String year, String month, String day, String ssn) throws ProjectException {
		// 해당 인력의 투입정보 삭제
		this.getProjectManpowerDetailDao().delete(projectCode, year, month, day, ssn);
		
		// 투입일정 정보 관리
		List<ProjectManpower> p = this.getProjectManpowerDetailDao().select(projectCode, year, month, day);
		if (p == null || p.size() == 0) {
			ProjectManpower projectManpower = this.getProjectManpowerDao().select(projectCode, year, month, day);
			// 인력이 더 이상 없으면 투입일정의 업무정보 초기화
			if (projectManpower != null) {
				projectManpower.setWorkSeq("");
				projectManpower.setWorkName("");
				this.getProjectManpowerDao().update(projectManpower);
			}
		}
		
	}

	@Override
	public void deleteProjectManpower(String projectCode) throws ProjectException {
		// TODO Auto-generated method stub
		
	}
	
	public ProjectManpowerDao getProjectManpowerDao() {
		return projectManpowerDao;
	}

	public void setProjectManpowerDao(ProjectManpowerDao projectManpowerDao) {
		this.projectManpowerDao = projectManpowerDao;
	}

	public ProjectManpowerDetailDao getProjectManpowerDetailDao() {
		return projectManpowerDetailDao;
	}

	public void setProjectManpowerDetailDao(ProjectManpowerDetailDao projectManpowerDetailDao) {
		this.projectManpowerDetailDao = projectManpowerDetailDao;
	}
}