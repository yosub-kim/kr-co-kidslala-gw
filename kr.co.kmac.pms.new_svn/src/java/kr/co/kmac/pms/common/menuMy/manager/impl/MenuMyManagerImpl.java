package kr.co.kmac.pms.common.menuMy.manager.impl;

import java.util.List;

import kr.co.kmac.pms.common.menuMy.dao.MenuMyDao;
import kr.co.kmac.pms.common.menuMy.data.MenuMy;
import kr.co.kmac.pms.common.menuMy.exception.MenuMyException;
import kr.co.kmac.pms.common.menuMy.manager.MenuMyManager;

public class MenuMyManagerImpl implements MenuMyManager {

	private MenuMyDao menuMyDao;

	public MenuMyDao getMenuMyDao() {
		return menuMyDao;
	}

	public void setMenuMyDao(MenuMyDao menuMyDao) {
		this.menuMyDao = menuMyDao;
	}

	@Override
	public void createMenuMy(MenuMy menuMy) throws MenuMyException {
		this.getMenuMyDao().createMenuMy(menuMy);
	}

	@Override
	public int updateMenuMy(MenuMy menuMy) throws MenuMyException {
		return this.getMenuMyDao().updateMenuMy(menuMy);
	}

	@Override
	public List<MenuMy> findMenuMyList(String creatorSsn) throws MenuMyException {
		return this.getMenuMyDao().findMenuMyList(creatorSsn);
	}

	@Override
	public int deleteMenuMy(String menuNum, String creatorSsn) throws MenuMyException {
		return this.getMenuMyDao().deleteMenuMy(menuNum, creatorSsn);
	}

}
