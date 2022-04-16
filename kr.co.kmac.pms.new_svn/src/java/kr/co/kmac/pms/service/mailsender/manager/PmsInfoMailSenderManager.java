package kr.co.kmac.pms.service.mailsender.manager;

import java.util.List;
import org.springframework.dao.DataAccessException;


public interface PmsInfoMailSenderManager {
	public List<String> selectCCAddress(String projectCode) throws DataAccessException;
	public List<String> selectBCCAddress() throws DataAccessException;
	public List<String> selectMobilePhoneNo(String[] projectCode) throws DataAccessException;
}