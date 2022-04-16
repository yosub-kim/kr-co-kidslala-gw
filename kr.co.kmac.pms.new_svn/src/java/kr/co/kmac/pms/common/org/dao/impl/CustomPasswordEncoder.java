package kr.co.kmac.pms.common.org.dao.impl;

import kr.co.kmac.pms.expertpool.manager.ExpertPoolManager;

import org.acegisecurity.providers.encoding.PasswordEncoder;
import org.springframework.dao.DataAccessException;

public class CustomPasswordEncoder implements PasswordEncoder {

	private ExpertPoolManager expertPoolManager;

	/**
	 * @return the expertPoolManager
	 */
	public ExpertPoolManager getExpertPoolManager() {
		return expertPoolManager;
	}

	/**
	 * @param expertPoolManager
	 *            the expertPoolManager to set
	 */
	public void setExpertPoolManager(ExpertPoolManager expertPoolManager) {
		this.expertPoolManager = expertPoolManager;
	}

	@Override
	public String encodePassword(String rawPass, Object salt)
			throws DataAccessException {
		return this.getExpertPoolManager().getEncPassword(rawPass);
	}

	@Override
	public boolean isPasswordValid(String encPass, String rawPass, Object salt)
			throws DataAccessException {
		return (encPass.equals(encodePassword(rawPass, salt)));
	}

}
