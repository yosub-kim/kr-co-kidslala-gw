package kr.co.kmac.pms.system.sanction.manager.impl;

import java.io.IOException;
import java.util.List;

import kr.co.kmac.pms.common.util.StringUtil;
import kr.co.kmac.pms.expertpool.dao.ExpertPoolDao;
import kr.co.kmac.pms.expertpool.data.ExpertPool;
import kr.co.kmac.pms.project.master.data.Project;
import kr.co.kmac.pms.schedule.dao.ScheduleDao;
import kr.co.kmac.pms.system.exception.SystemException;
import kr.co.kmac.pms.system.sanction.dao.SanctionTemplateDao;
import kr.co.kmac.pms.system.sanction.data.SanctionTemplate;
import kr.co.kmac.pms.system.sanction.manager.SanctionTemplateManager;
import kr.co.kmac.pms.schedule.data.DailyScheduleInfo;

public class SanctionTemplateManagerImpl implements SanctionTemplateManager {

	private SanctionTemplateDao sanctionTemplateDao;
	private ScheduleDao scheduleDao;
	private ExpertPoolDao expertPoolDao;

	/**
	 * @return the sanctionTemplateDao
	 */
	public SanctionTemplateDao getSanctionTemplateDao() {
		return sanctionTemplateDao;
	}

	/**
	 * @param sanctionTemplateDao the sanctionTemplateDao to set
	 */
	public void setSanctionTemplateDao(SanctionTemplateDao sanctionTemplateDao) {
		this.sanctionTemplateDao = sanctionTemplateDao;
	}
	
	/**
	 * @return the scheduleDao
	 */	
	public ScheduleDao getScheduleDao() {
		return scheduleDao;
	}
	
	/**
	 * @param scheduleDao the scheduleDao to set
	 */	
	public void setScheduleDao(ScheduleDao scheduleDao) {
		this.scheduleDao = scheduleDao;
	}
	
	/**
	 * @return the xpertPoolDao
	 */	
	public ExpertPoolDao getExpertPoolDao() {
		return expertPoolDao;
	}
	
	/**
	 * @param expertPoolDao the scheduleDao to set
	 */	
	public void setExpertPoolDao(ExpertPoolDao expertPoolDao) {
		this.expertPoolDao = expertPoolDao;
	}	

	@Override
	public void createSanctionTemplate(SanctionTemplate sanctionTemplate) throws SystemException {
		this.getSanctionTemplateDao().createSanctionTemplate(sanctionTemplate);
	}

	@Override
	public void deleteSanctionTemplate(String approvalType) throws SystemException {
		this.getSanctionTemplateDao().deleteSanctionTemplate(approvalType);
	}

	@Override
	public List<SanctionTemplate> getSanctionTemplate() throws SystemException {
		return this.getSanctionTemplateDao().getSanctionTemplate();
	}

	@Override
	public List<SanctionTemplate> getSanctionTemplate(boolean useYn) throws SystemException {
		return this.getSanctionTemplateDao().getSanctionTemplate(useYn);
	}

	@Override
	public List<SanctionTemplate> getSanctionTemplate(boolean useYn, String sanctionType) throws SystemException {
		return this.getSanctionTemplateDao().getSanctionTemplate(useYn, sanctionType);
	}

	@Override
	public List<SanctionTemplate> getSanctionTemplate(boolean useYn, boolean useMobile) throws SystemException {
		return this.getSanctionTemplateDao().getSanctionTemplate(useYn, useMobile);
	}

	@Override
	public SanctionTemplate getSanctionTemplate(String approvalType) throws SystemException {
		SanctionTemplate template = this.getSanctionTemplateDao().getSanctionTemplate(approvalType);
		
		// Job Date: 2018-06-04	Author: yhyim	Description: �����ڰ� �濵��ȹ�� �� ��� upday ���θ� üũ�Ͽ� ������ ������ �ε� ���θ� ����  (��� ����)
		/*if(template != null && template.isHasSptTeamMng()) {
			String yyyyMMdd = StringUtil.getCurr("yyyyMMdd");
			// ������ �Ⱓ�̸� �������� �����ڸ� �����Ѵ�.
			List<DailyScheduleInfo> managerUpday = getScheduleDao().getUpdayListByMonth(template.getSptTeamMngSsn(), yyyyMMdd.substring(0, 4), yyyyMMdd.substring(4, 6), yyyyMMdd.substring(6, 8));
			if (!managerUpday.isEmpty()) {
				ExpertPool manager = getExpertPoolDao().retrieve(managerUpday.get(0).getSsn());
				ExpertPool subManager = getExpertPoolDao().getDivManager(manager.getDept().substring(0, 3) + "1");
				template.setSptTeamMngDept(subManager.getDept());
				template.setSptTeamMngName(subManager.getName());
				template.setSptTeamMngSsn(subManager.getSsn());
			}
		}*/
		return template;
	}
	
	@Override
	public SanctionTemplate getSanctionTemplate2(String approvalType, String seq) throws SystemException {
		SanctionTemplate template = this.getSanctionTemplateDao().getSanctionTemplate(approvalType);
		
		// ���� ���� �߰�
		String dept = getScheduleDao().getSanctionDept(seq);
		if("Y".equals(dept)) { template.setHasCfo(true); }
		
		//�濵��ȹ���� �ް��� ���� �߰�
		String deptChk = getScheduleDao().getSanctionDept4(seq);
		if("Y".equals(deptChk) && ("draft4".equals(approvalType) || "draft10".equals(approvalType))){ template.setHasTeamManager(true); }
		
		// ����Ʈ�������� ���ڰ��� ǰ�� �Ͻ���
		if("WORK17F1F294A5A".equals(seq) || "WORK17F253DFC80".equals(seq)){
			template.setHasAssistMember(true);
			template.setAssistMemberCnt(1);
		}
		
		
		// Job Date: 2018-06-04	Author: yhyim	Description: �����ڰ� �濵��ȹ�� �� ��� upday ���θ� üũ�Ͽ� ������ ������ �ε� ���θ� ���� (��� ����)
		/*if(template != null && template.isHasSptTeamMng()) {
			String yyyyMMdd = StringUtil.getCurr("yyyyMMdd");
			// ������ �Ⱓ�̸� �������� �����ڸ� �����Ѵ�.
			List<DailyScheduleInfo> managerUpday = getScheduleDao().getUpdayListByMonth(template.getSptTeamMngSsn(), yyyyMMdd.substring(0, 4), yyyyMMdd.substring(4, 6), yyyyMMdd.substring(6, 8));
			if (!managerUpday.isEmpty()) {
				ExpertPool manager = getExpertPoolDao().retrieve(managerUpday.get(0).getSsn());
				ExpertPool subManager = getExpertPoolDao().getDivManager(manager.getDept().substring(0, 3) + "1");
				template.setSptTeamMngDept(subManager.getDept());
				template.setSptTeamMngName(subManager.getName());
				template.setSptTeamMngSsn(subManager.getSsn());
			}
		}*/
		return template;
	}
	
	@Override
	public SanctionTemplate getSanctionTemplate3(String approvalType, String seq) throws SystemException {
		SanctionTemplate template = this.getSanctionTemplateDao().getSanctionTemplate(approvalType);
		
		// ���� ���� �߰�
		String dept = getScheduleDao().getSanctionDept2(seq);
		if("Y".equals(dept)) { template.setHasCfo(true); }
		
		//�濵��ȹ���� �ް��� ���� �߰�
		String deptChk = getScheduleDao().getSanctionDept3(seq);
		if("Y".equals(deptChk) && ("draft4".equals(approvalType) || "draft10".equals(approvalType))){ template.setHasTeamManager(true); }
		
		// ����Ʈ�������� ���ڰ��� ǰ�� �Ͻ���
		if("79794".equals(seq)){
			template.setHasAssistMember(true);
			template.setAssistMemberCnt(1);
		}
		
		// Job Date: 2018-06-04	Author: yhyim	Description: �����ڰ� �濵��ȹ�� �� ��� upday ���θ� üũ�Ͽ� ������ ������ �ε� ���θ� ���� (��� ����)
		/*if(template != null && template.isHasSptTeamMng()) {
			String yyyyMMdd = StringUtil.getCurr("yyyyMMdd");
			// ������ �Ⱓ�̸� �������� �����ڸ� �����Ѵ�.
			List<DailyScheduleInfo> managerUpday = getScheduleDao().getUpdayListByMonth(template.getSptTeamMngSsn(), yyyyMMdd.substring(0, 4), yyyyMMdd.substring(4, 6), yyyyMMdd.substring(6, 8));
			if (!managerUpday.isEmpty()) {
				ExpertPool manager = getExpertPoolDao().retrieve(managerUpday.get(0).getSsn());
				ExpertPool subManager = getExpertPoolDao().getDivManager(manager.getDept().substring(0, 3) + "1");
				template.setSptTeamMngDept(subManager.getDept());
				template.setSptTeamMngName(subManager.getName());
				template.setSptTeamMngSsn(subManager.getSsn());
			}
		}*/
		return template;
	}
	
	@Override
	public SanctionTemplate getSanctionTemplate(String approvalType, String admin) throws SystemException {
		SanctionTemplate template = this.getSanctionTemplateDao().getSanctionTemplate(approvalType);
		return template;
	}

	@Override
	public void updateSanctionTemplate(SanctionTemplate sanctionTemplate) throws SystemException {
		this.getSanctionTemplateDao().updateSanctionTemplate(sanctionTemplate);
	}

}
