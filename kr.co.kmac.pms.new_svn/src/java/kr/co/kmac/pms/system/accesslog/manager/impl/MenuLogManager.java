package kr.co.kmac.pms.system.accesslog.manager.impl;

import javax.servlet.http.HttpSession;

import kr.co.kmac.pms.system.accesslog.dao.IMenuLogDao;
import kr.co.kmac.pms.system.accesslog.manager.IMenuLogManager;

import org.springframework.dao.DataAccessException;

public class MenuLogManager implements IMenuLogManager {

	private IMenuLogDao menuLogDao;

	public IMenuLogDao getMenuLogDao() {
		return menuLogDao;
	}

	public void setMenuLogDao(IMenuLogDao menuLogDao) {
		this.menuLogDao = menuLogDao;
	}

	public void insertMenuLog(HttpSession session, String menuId) throws DataAccessException {
		this.menuLogDao.insertMenuLog(session, menuId);
	}

}
