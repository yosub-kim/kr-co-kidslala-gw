/**
 * @author yhyim
 *
 */
package kr.co.kmac.pms.system.emaillog.dao;

import org.springframework.dao.DataAccessException;
import kr.co.kmac.pms.system.emaillog.data.SalaryInfoMailLogData;

public interface SalaryInfoMailLogDao {
	public void insetSalaryInfoMailLog(SalaryInfoMailLogData mailLogData)  throws DataAccessException; 
	public void deleteSalaryInfoMailLog(String emailSeq)  throws DataAccessException;
}