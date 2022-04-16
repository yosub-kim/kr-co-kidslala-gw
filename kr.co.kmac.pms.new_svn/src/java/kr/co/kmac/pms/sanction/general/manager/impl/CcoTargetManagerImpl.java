package kr.co.kmac.pms.sanction.general.manager.impl;

import kr.co.kmac.pms.sanction.exception.SanctionException;
import kr.co.kmac.pms.sanction.general.data.CcoTarget;
import kr.co.kmac.pms.sanction.general.manager.CcoTargetManager;
import kr.co.kmac.pms.sanction.general.dao.CcoTargetDao;

//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;

public class CcoTargetManagerImpl implements CcoTargetManager {
	
	private CcoTargetDao ccoTargetDao;
	
	public CcoTargetDao getCcoTargetDao() {
		return ccoTargetDao;
	}
	
	public void setCcoTargetDao(CcoTargetDao ccoTargetDao) {
		this.ccoTargetDao = ccoTargetDao; 
	}

	@Override
	public CcoTarget getCcoTarget(String companyCode) throws SanctionException {
		return this.getCcoTargetDao().select(companyCode);
	}

	@Override
	public boolean isCcoTargetExist(String companyCode) throws SanctionException {
		return this.getCcoTargetDao().isExist(companyCode);		
	}
	
}