package kr.co.kmac.pms.system.accesslog.dao;

import org.springframework.dao.DataAccessException;

public interface AccountHistoryDao {
	public void updateEtc(String seq, String etc) throws DataAccessException;
}