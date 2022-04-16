package kr.co.kmac.pms.system.accesslog.manager;

import javax.servlet.http.HttpSession;

import org.springframework.dao.DataAccessException;

public interface IMenuLogManager {

	public void insertMenuLog(HttpSession session, String menuId) throws DataAccessException;

}
