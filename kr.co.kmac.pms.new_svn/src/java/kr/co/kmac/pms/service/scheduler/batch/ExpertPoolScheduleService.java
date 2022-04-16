package kr.co.kmac.pms.service.scheduler.batch;

import java.util.List;

import kr.co.kmac.pms.expertpool.dao.ExpertPoolDao;
import kr.co.kmac.pms.expertpool.data.ExpertPool;
import kr.co.kmac.pms.service.scheduler.dao.ExpertPoolScheduleServiceDao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * TODO 클래스 설명
 * 
 * @author yhyim
 * @version $Id: ExpertPoolScheduleService.java,v 1.0 2019/05/20 11:15:37 cvs3 Exp $
 */
public class ExpertPoolScheduleService {
	private static final Log log = LogFactory.getLog(ExpertPoolScheduleService.class);
	private ExpertPoolScheduleServiceDao expertPoolScheduleServiceDao;
	private ExpertPoolDao expertPoolDao;

	public void updateExpertPoolToStandby() {
		// 업데이트 대상 엑스퍼트 산출
		List<ExpertPool> expertPoolList = getExpertPoolScheduleServiceDao().getNotWorkingExpertList();
		
		// 업데이트 대상 산업계강사 추출
		List<ExpertPool> expertPoolList_D = getExpertPoolScheduleServiceDao().getNotWorkingExpertList_D();
		
		// 업데이트 대상 대학교수 추출
		List<ExpertPool> expertPoolList_E = getExpertPoolScheduleServiceDao().getNotWorkingExpertList_E();
		if (expertPoolList != null && expertPoolList.size() > 0) {
			for (ExpertPool expertPool : expertPoolList) {
				// (대기)엑스퍼트로 업데이트
				this.getExpertPoolScheduleServiceDao().update(expertPool);		
				// deptHistory 처리
				this.getExpertPoolDao().storeHistory(expertPool);
			}
		}
		
		if (expertPoolList_D != null && expertPoolList_D.size() > 0) {
			for (ExpertPool expertPool : expertPoolList_D) {
				// (대기)산업계강사 업데이트
				this.getExpertPoolScheduleServiceDao().update_D(expertPool);		
				// deptHistory 처리
				this.getExpertPoolDao().storeHistory(expertPool);
			}
		}
		
		if (expertPoolList_E != null && expertPoolList_E.size() > 0) {
			for (ExpertPool expertPool : expertPoolList_E) {
				// (대기)대학교수로 업데이트
				this.getExpertPoolScheduleServiceDao().update_E(expertPool);		
				// deptHistory 처리
				this.getExpertPoolDao().storeHistory(expertPool);
			}
		}
		
		log.info("## ExpertPoolScheduleService was executed...");
	}	
	
	public ExpertPoolScheduleServiceDao getExpertPoolScheduleServiceDao() {
		return this.expertPoolScheduleServiceDao;
	}

	public void setExpertPoolScheduleServiceDao(ExpertPoolScheduleServiceDao expertPoolScheduleServiceDao) {
		this.expertPoolScheduleServiceDao = expertPoolScheduleServiceDao;
	}
	
	public ExpertPoolDao getExpertPoolDao() {
		return expertPoolDao;
	}

	public void setExpertPoolDao(ExpertPoolDao expertPoolDao) {
		this.expertPoolDao = expertPoolDao;
	}
}