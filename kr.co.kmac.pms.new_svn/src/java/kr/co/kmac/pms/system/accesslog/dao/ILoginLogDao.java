package kr.co.kmac.pms.system.accesslog.dao;

import javax.servlet.http.HttpSession;

import org.springframework.dao.DataAccessException;

public interface ILoginLogDao {

	public void insertLoginLog(HttpSession session, String clientIp) throws DataAccessException;

}
