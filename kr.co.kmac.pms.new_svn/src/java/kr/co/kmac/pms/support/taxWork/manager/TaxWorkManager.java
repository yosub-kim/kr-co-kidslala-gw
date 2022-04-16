package kr.co.kmac.pms.support.taxWork.manager;

import org.springframework.dao.DataAccessException;

import kr.co.kmac.pms.support.exception.SupportException;
import kr.co.kmac.pms.support.taxWork.data.TaxWorkMobileData;

public interface TaxWorkManager {
	
	public String comp_code(String empno) throws SupportException;
	
	public String jumin(String empno) throws SupportException;
	
	public TaxWorkMobileData select(String idx) throws DataAccessException;

}
