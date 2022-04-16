package kr.co.kmac.pms.system.accesslog.manager;

import org.springframework.dao.DataAccessException;

public interface AccountHistoryManager {

	public void updateEtc(String seq, String etc) throws DataAccessException;

}