/**
 * @author yhyim
 *
 */
package kr.co.kmac.pms.system.emaillog.manager;

import kr.co.kmac.pms.system.emaillog.data.SalaryInfoMailLogData;
import kr.co.kmac.pms.system.emaillog.exception.EmailLogException;

public interface SalaryInfoMailLogManager {
	public void insertSalaryInfoMailLog(SalaryInfoMailLogData mailLogData) throws EmailLogException;
	public void deleteSalaryInfoMailLog(String emailSeq) throws EmailLogException;  
}