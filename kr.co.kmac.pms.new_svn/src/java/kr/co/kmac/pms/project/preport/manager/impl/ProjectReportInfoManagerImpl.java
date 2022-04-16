package kr.co.kmac.pms.project.preport.manager.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import kr.co.kmac.pms.common.util.DateTime;
import kr.co.kmac.pms.project.exception.ProjectException;
import kr.co.kmac.pms.project.manpower.data.ProjectManpower;
import kr.co.kmac.pms.project.master.dao.ProjectDao;
import kr.co.kmac.pms.project.preport.dao.ProjectReportDao;
import kr.co.kmac.pms.project.preport.dao.ProjectReportDetailDao;
import kr.co.kmac.pms.project.preport.data.ProjectReportPlan;
import kr.co.kmac.pms.project.preport.manager.ProjectReportInfoManager;

public class ProjectReportInfoManagerImpl implements ProjectReportInfoManager {

	private ProjectReportDao projectReportDao;
	private ProjectReportDetailDao projectReportDetailDao;
	private ProjectDao projectDao;

	@Override
	public ProjectReportPlan getProjectReportInfo(String projectCode, String year, String month, String day, String ssn) throws ProjectException {
		return this.getProjectReportDetailDao().select(projectCode, year, month, day, ssn);
	}

	@Override
	public List<ProjectReportPlan> getProjectReportInfo(String projectCode, String year, String month) throws ProjectException {
		List<ProjectReportPlan> projectReportPlanList = new ArrayList<ProjectReportPlan>();
		String projectBusinessTypeCode = this.getProjectDao().getProjectSimpleInfo(projectCode).getBusinessTypeCode();

		if (this.getProjectReportDao().count(projectCode, year, month) > 0) {
			List<ProjectReportPlan> list = this.getProjectReportDao().select(projectCode, year, month);
			for (ProjectReportPlan projectReportPlan : list) {
				List<ProjectReportPlan> detailList = this.getProjectReportDetailDao().select(projectCode, year, month, projectReportPlan.getDay());
				String[] ssnArray = new String[detailList.size()];
				String[] nameArray = new String[detailList.size()];
				String[] timeArray = new String[detailList.size()];
				int i = 0;
				for (ProjectReportPlan detail : detailList) {
					if (detail.getSsn() != null && !detail.getSsn().equals("")) {
						ssnArray[i] = detail.getSsn();
						if (projectBusinessTypeCode.equals("BTA") 
								|| projectBusinessTypeCode.equals("BTB")
								|| projectBusinessTypeCode.equals("BTC") 
								|| projectBusinessTypeCode.equals("BTD")
								|| projectBusinessTypeCode.equals("BTF")) {// 컨설팅, 진흥, 인증, 리서치, 해외연수
							nameArray[i] = detail.getName();
						} else {
							nameArray[i] = detail.getName();
							/*nameArray[i] = detail.getName() + "(" + detail.getTime() + ")";*/
							timeArray[i] = detail.getTime();
						}
					}
					i++;
				}
				projectReportPlan.setSsnArray(ssnArray);
				projectReportPlan.setNameArray(nameArray);
				projectReportPlan.setTimeArray(timeArray);

				projectReportPlanList.add(projectReportPlan);
			}

		} else {
			Calendar calendar = Calendar.getInstance();
			calendar.set(Integer.parseInt(year), Integer.parseInt(month) - 1, 1);
			int lastDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

			ProjectReportPlan projectReportPlan = null;
			for (int i = 1; i <= lastDay; i++) {
				projectReportPlan = new ProjectReportPlan();
				projectReportPlan.setProjectCode(projectCode);
				projectReportPlan.setYear(year);
				projectReportPlan.setMonth(month);
				projectReportPlan.setDay((i < 10 ? "0" + String.valueOf(calendar.get(Calendar.DATE)) : String.valueOf(calendar.get(Calendar.DATE))));
				projectReportPlan.setWeek(DateTime.getWeekOfDayString(calendar.get(Calendar.DAY_OF_WEEK)));
				projectReportPlan.setWorkSeq("");
				projectReportPlan.setWorkName("");
				projectReportPlan.setSsn("");
				projectReportPlan.setName("");
				projectReportPlan.setOutputName("");
				projectReportPlanList.add(projectReportPlan);
				calendar.add(Calendar.DATE, 1);
			}
		}
		return projectReportPlanList;
	}
	
	@Override
	public List<ProjectReportPlan> getProjectMember(String projectCode){
		return this.getProjectReportDetailDao().selectProjectMember(projectCode);
	}
	
	@Override
	public List<ProjectReportPlan> getProjectWorkName(String projectCode, String year, String month){
		return this.getProjectReportDetailDao().selectProjectWorkName(projectCode, year, month);
	}

	@Override
	public List<ProjectReportPlan> getProjecMonthWork(String projectCode, String year, String month){
		return this.getProjectReportDetailDao().selectProjectMonthWork(projectCode, year, month);
	}

	@Override
	public void storeProjectReportInfo(List<ProjectReportPlan> projectReportPlanList) throws ProjectException {
		for (ProjectReportPlan projectReportPlan : projectReportPlanList) {
			this.storeProjectReportInfo(projectReportPlan);
		}
	}

	@Override
	public void storeProjectReportInfo(ProjectReportPlan projectReportPlan) throws ProjectException {
		if (this.getProjectReportDao().count(projectReportPlan.getProjectCode(), projectReportPlan.getYear(), projectReportPlan.getMonth()) <= 0) {
			Calendar calendar = Calendar.getInstance();
			calendar.set(Integer.parseInt(projectReportPlan.getYear()), Integer.parseInt(projectReportPlan.getMonth()) - 1, 1);
			int lastDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

			ProjectReportPlan tempProjectReportPlan = null;
			List<ProjectReportPlan> tempProjectReportPlanList = new ArrayList<ProjectReportPlan>();
			for (int i = 1; i <= lastDay; i++) {
				tempProjectReportPlan = new ProjectReportPlan();
				tempProjectReportPlan.setProjectCode(projectReportPlan.getProjectCode());
				tempProjectReportPlan.setYear(projectReportPlan.getYear());
				tempProjectReportPlan.setMonth(projectReportPlan.getMonth());
				tempProjectReportPlan.setDay((i < 10 ? "0" + String.valueOf(calendar.get(Calendar.DATE)) : String
						.valueOf(calendar.get(Calendar.DATE))));
				tempProjectReportPlan.setWeek(DateTime.getWeekOfDayString(calendar.get(Calendar.DAY_OF_WEEK)));
				tempProjectReportPlan.setWorkSeq("");
				tempProjectReportPlan.setWorkName("");
				tempProjectReportPlan.setSsn("");
				tempProjectReportPlan.setName("");
				tempProjectReportPlan.setOutputName("");
				tempProjectReportPlanList.add(tempProjectReportPlan);
				calendar.add(Calendar.DATE, 1);
			}
			this._storeProjectReportInfo(tempProjectReportPlanList);
		}

		this.getProjectReportDao().update(projectReportPlan);
		if (projectReportPlan.getSsnArray() != null && projectReportPlan.getSsnArray().length > 0) {
			String ssn[] = projectReportPlan.getSsnArray();
			String time[] = projectReportPlan.getTimeArray();
			this.getProjectReportDetailDao().delete(projectReportPlan.getProjectCode(), projectReportPlan.getYear(), projectReportPlan.getMonth(),
					projectReportPlan.getDay());
			for (int i = 0; i < ssn.length; i++) {
				ProjectReportPlan prp = projectReportPlan;
				prp.setSsn(ssn[i].trim());
				try {
					prp.setTime(time[i].trim());
				} catch (Exception e) {
					prp.setTime("");
				}
				try {
					this.getProjectReportDetailDao().insert(prp);
				} catch (Exception e) {
					this.getProjectReportDetailDao().update(prp);
				}
			}
		}
	}

	@Override
	public void storeProjectReportInfoForTeachingTime(ProjectReportPlan projectReportPlan) throws ProjectException {
		this.getProjectReportDetailDao().update(projectReportPlan);
	}

	@Override
	public void deleteProjectReportInfo(String projectCode) throws ProjectException {
		this.getProjectReportDao().delete(projectCode);
		this.getProjectReportDetailDao().delete(projectCode);
	}

	@Override
	public void deleteProjectReportInfo(String projectCode, String year, String month, String day, String ssn) throws ProjectException {
		this.getProjectReportDetailDao().delete(projectCode, year, month, day, ssn);
		List<ProjectReportPlan> p = this.getProjectReportDetailDao().select(projectCode, year, month, day);
		if (p == null || p.size() == 0) {
			ProjectReportPlan projectReportPlan = this.getProjectReportDao().select(projectCode, year, month, day);
			if (projectReportPlan != null) {
				projectReportPlan.setWorkSeq("");
				projectReportPlan.setWorkName("");
				projectReportPlan.setOutputName("");
				this.getProjectReportDao().update(projectReportPlan);
			}
		}
	}

	private void _storeProjectReportInfo(List<ProjectReportPlan> projectReportPlanList) throws ProjectException {
		for (ProjectReportPlan projectReportPlan : projectReportPlanList) {
			this.getProjectReportDao().insert(projectReportPlan);
		}
		for (ProjectReportPlan projectReportPlan : projectReportPlanList) {
			if (projectReportPlan.getSsn() != null && !projectReportPlan.getSsn().equals(""))
				this.getProjectReportDetailDao().insert(projectReportPlan);
		}
	}

	@Override
	public void updateProjectReportPayAmount(String projectCode, String assignDate, String ssn, String payAmount)
			throws ProjectException {
		this.getProjectReportDao().updateProjectReportPayAmount(projectCode, assignDate, ssn, payAmount);
	}

	public ProjectReportDao getProjectReportDao() {
		return projectReportDao;
	}

	public void setProjectReportDao(ProjectReportDao projectReportDao) {
		this.projectReportDao = projectReportDao;
	}

	public ProjectReportDetailDao getProjectReportDetailDao() {
		return projectReportDetailDao;
	}

	public void setProjectReportDetailDao(ProjectReportDetailDao projectReportDetailDao) {
		this.projectReportDetailDao = projectReportDetailDao;
	}

	public ProjectDao getProjectDao() {
		return projectDao;
	}

	public void setProjectDao(ProjectDao projectDao) {
		this.projectDao = projectDao;
	}

}
