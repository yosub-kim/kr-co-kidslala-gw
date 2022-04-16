package kr.co.kmac.pms.common.menuMy.manager;

import java.util.List;

import kr.co.kmac.pms.common.menuMy.data.MenuMy;
import kr.co.kmac.pms.common.menuMy.exception.MenuMyException;

public interface MenuMyManager {

	public void createMenuMy(MenuMy menuMy) throws MenuMyException;

	public int updateMenuMy(MenuMy menuMy) throws MenuMyException;

	public List<MenuMy> findMenuMyList(String creatorSsn) throws MenuMyException;

	public int deleteMenuMy(String menuNum, String creatorSsn) throws MenuMyException;

}
