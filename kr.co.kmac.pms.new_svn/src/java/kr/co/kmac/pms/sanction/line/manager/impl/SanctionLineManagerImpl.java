package kr.co.kmac.pms.sanction.line.manager.impl;

import java.util.ArrayList;
import java.util.List;

import kr.co.kmac.pms.common.util.StringUtil;
import kr.co.kmac.pms.expertpool.data.ExpertPool;
import kr.co.kmac.pms.sanction.exception.SanctionException;
import kr.co.kmac.pms.sanction.line.dao.SanctionLineDao;
import kr.co.kmac.pms.sanction.line.data.SanctionLine;
import kr.co.kmac.pms.sanction.line.manager.SanctionLineManager;
import kr.co.kmac.pms.schedule.dao.ScheduleDao;
import kr.co.kmac.pms.schedule.data.DailyScheduleInfo;

public class SanctionLineManagerImpl implements SanctionLineManager {

	private SanctionLineDao sanctionLineDao;
	private ScheduleDao scheduleDao;

	public SanctionLineDao getSanctionLineDao() {
		return sanctionLineDao;
	}

	public void setSanctionLineDao(SanctionLineDao sanctionLineDao) {
		this.sanctionLineDao = sanctionLineDao;
	}
	
	public ScheduleDao getScheduleDao() {
		return scheduleDao;
	}
	
	public void setScheduleDao(ScheduleDao scheduleDao) {
		this.scheduleDao = scheduleDao;
	}

	@Override
	public void deleteSanctionLine(String id) throws SanctionException {
		this.getSanctionLineDao().delete(id);
	}

	@Override
	public void insertSanctionLine(SanctionLine sanctionLine) throws SanctionException {
		this.getSanctionLineDao().delete(sanctionLine.getId());
		this.getSanctionLineDao().insert(sanctionLine);
	}

	@Override
	public SanctionLine selectSanctionLine(String id) throws SanctionException {
		SanctionLine myLine = this.getSanctionLineDao().select(id);
		
		// Job Date: 2018-06-04	Author: yhyim	Description: �����ڰ� �濵��ȹ�� �� ��� upday ���θ� üũ�Ͽ� ������ ������ �ε� ���θ� �����Ѵ�.
		/*if(myLine != null) {
			String yyyyMMdd = StringUtil.getCurr("yyyyMMdd");
			// CBO ������ ���� Ȯ��
			List<DailyScheduleInfo> cboUpday = getScheduleDao().getUpdayListByMonth(myLine.getCioSsn(), yyyyMMdd.substring(0, 4), yyyyMMdd.substring(4, 6), yyyyMMdd.substring(6, 8));
			List<DailyScheduleInfo> cooUpday = getScheduleDao().getUpdayListByMonth(myLine.getTeamManagerSsn(), yyyyMMdd.substring(0, 4), yyyyMMdd.substring(4, 6), yyyyMMdd.substring(6, 8));
			if (!cboUpday.isEmpty() && cooUpday.isEmpty()) {
				// COO�� CBO ����������� �̵�
				myLine.setCioDept(myLine.getTeamManagerDept());
				myLine.setCioName(myLine.getTeamManagerName());
				myLine.setCioSsn(myLine.getTeamManagerSsn());
				// COO ������� ����
				myLine.setTeamManagerDept("");
				myLine.setTeamManagerName("");
				myLine.setTeamManagerSsn("");
			}
			// COO ������ ���� Ȯ��
			if (!cooUpday.isEmpty() && cboUpday.isEmpty()) {
				// COO ������� ����
				myLine.setTeamManagerDept("");
				myLine.setTeamManagerName("");
				myLine.setTeamManagerSsn("");
			}
			// CBO, COO ��� ������
			if (!cboUpday.isEmpty() && !cooUpday.isEmpty()) {
				myLine.setTeamManagerDept("");
				myLine.setTeamManagerName("");
				myLine.setTeamManagerSsn("");
				myLine.setCioDept("");
				myLine.setCioName("");
				myLine.setCioSsn("");
			}
		}*/
		return myLine;
	}

	@Override
	public void updateSanctionLine(SanctionLine sanctionLine) throws SanctionException {
		this.getSanctionLineDao().update(sanctionLine);
	}

	@Override
	public List<ExpertPool> selectUpdayOfficer(String id) throws SanctionException {
		SanctionLine myLine = this.getSanctionLineDao().select(id);
		
		String yyyyMMdd = StringUtil.getCurr("yyyyMMdd");
		List<ExpertPool> officers = new ArrayList<ExpertPool>();
		
		// CBO ������ ���� Ȯ��
		List<DailyScheduleInfo> managerUpday = getScheduleDao().getUpdayListByMonth(myLine.getCioSsn(), yyyyMMdd.substring(0, 4), yyyyMMdd.substring(4, 6), yyyyMMdd.substring(6, 8));
		if (!managerUpday.isEmpty()) {
			ExpertPool officer = new ExpertPool();
			officer.setSsn(myLine.getCioSsn());
			officer.setName(myLine.getCioName());
			officer.setDept(myLine.getCioDept());
			officers.add(officer);
		}
		managerUpday = getScheduleDao().getUpdayListByMonth(myLine.getTeamManagerSsn(), yyyyMMdd.substring(0, 4), yyyyMMdd.substring(4, 6), yyyyMMdd.substring(6, 8));
		if (!managerUpday.isEmpty()) {
			ExpertPool officer = new ExpertPool();
			officer.setSsn(myLine.getTeamManagerSsn());
			officer.setName(myLine.getTeamManagerName());
			officer.setDept(myLine.getTeamManagerDept());
			officers.add(officer);
		}
		return officers;
	}
}
