package kr.co.kmac.pms.system.accesslog.manager.impl;

import org.springframework.dao.DataAccessException;

import kr.co.kmac.pms.system.accesslog.dao.AccountHistoryDao;
import kr.co.kmac.pms.system.accesslog.manager.AccountHistoryManager;

public class AccountHistoryManagerImpl implements AccountHistoryManager {
	
	private AccountHistoryDao accountHistoryDao;

	public void updateEtc(String seq, String etc) throws DataAccessException {
		this.getAccountHistoryDao().updateEtc(seq, etc);
	}
	
	public AccountHistoryDao getAccountHistoryDao() {
		return this.accountHistoryDao;
	}
	
	public void setAccountHistoryDao(AccountHistoryDao accountHistoryDao) {
		this.accountHistoryDao = accountHistoryDao;
	}
}