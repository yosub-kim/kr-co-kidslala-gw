package kr.co.kmac.pms.common.menuMy.dao;

import java.util.List;

import kr.co.kmac.pms.common.code.data.Code;
import kr.co.kmac.pms.common.code.data.CodeEntity;
import kr.co.kmac.pms.common.code.exception.CodeException;
import kr.co.kmac.pms.common.menuMy.data.MenuMy;

import org.springframework.dao.DataAccessException;

public interface MenuMyDao {

	public void createMenuMy(MenuMy menuMy) throws DataAccessException;

	public int updateMenuMy(MenuMy menuMy) throws DataAccessException;

	public List<MenuMy> findMenuMyList(String creatorSsn) throws DataAccessException;

	public int deleteMenuMy(String menuNum, String creatorSsn) throws DataAccessException;

}
