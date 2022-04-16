package kr.co.kmac.pms.sanction.general.dao;

import kr.co.kmac.pms.sanction.general.data.CcoTarget;
import org.springframework.dao.DataAccessException;

public interface CcoTargetDao {
	public CcoTarget select(String companyCode) throws DataAccessException;

	public boolean isExist(String companyCode) throws DataAccessException;
}