package kr.co.kmac.pms.system.accesslog.dao;

import javax.servlet.http.HttpSession;

import org.springframework.dao.DataAccessException;

public interface IMenuLogDao {

	public void insertMenuLog(HttpSession session, String menuId) throws DataAccessException;

}
