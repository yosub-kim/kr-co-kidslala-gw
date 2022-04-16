package kr.co.kmac.pms.support.taxWork.manager.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;

import kr.co.kmac.pms.support.exception.SupportException;
import kr.co.kmac.pms.support.taxWork.dao.TaxWorkDao;
import kr.co.kmac.pms.support.taxWork.data.TaxWorkMobileData;
import kr.co.kmac.pms.support.taxWork.manager.TaxWorkManager;

public class TaxWorkManagerImpl implements TaxWorkManager {

	private TaxWorkDao taxWorkDao;
	private static final Log logger = LogFactory.getLog(TaxWorkManagerImpl.class);
	
	public TaxWorkDao getTaxWorkDao() {
		return taxWorkDao;
	}
	public void setTaxWorkDao(TaxWorkDao taxWorkDao) {
		this.taxWorkDao = taxWorkDao;
	}
	
	@Override
	public String comp_code(String empno) throws SupportException {
		// TODO Auto-generated method stub
		return getTaxWorkDao().comp_code(empno);
	}
	
	@Override
	public String jumin(String empno) throws SupportException {
		// TODO Auto-generated method stub
		return getTaxWorkDao().jumin(empno);
	}
	@Override
	public TaxWorkMobileData select(String idx) throws DataAccessException {
		// TODO Auto-generated method stub
		return getTaxWorkDao().select(idx);
	}
	
	
}
