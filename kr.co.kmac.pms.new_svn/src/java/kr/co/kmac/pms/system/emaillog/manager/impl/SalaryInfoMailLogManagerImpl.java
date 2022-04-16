/**
 * @author yhyim
 * @create_date 2018-11-11
 */
package kr.co.kmac.pms.system.emaillog.manager.impl;

import kr.co.kmac.pms.system.emaillog.manager.SalaryInfoMailLogManager;
import kr.co.kmac.pms.system.emaillog.data.SalaryInfoMailLogData;
import kr.co.kmac.pms.system.emaillog.exception.EmailLogException;
import kr.co.kmac.pms.system.emaillog.dao.SalaryInfoMailLogDao;

public class SalaryInfoMailLogManagerImpl implements SalaryInfoMailLogManager {
	private SalaryInfoMailLogDao salaryInfoMailLogDao;

	public void insertSalaryInfoMailLog(SalaryInfoMailLogData mailLogData) throws EmailLogException {
		salaryInfoMailLogDao.insetSalaryInfoMailLog(mailLogData);
	}
	
	public void deleteSalaryInfoMailLog(String emailSeq) throws EmailLogException {
		salaryInfoMailLogDao.deleteSalaryInfoMailLog(emailSeq);
	}
	
	public SalaryInfoMailLogDao getSalaryInfoMailLogDao() {
		return salaryInfoMailLogDao;
	}

	public void setSalaryInfoMailLogDao(SalaryInfoMailLogDao salaryInfoMailLogDao) {
		this.salaryInfoMailLogDao = salaryInfoMailLogDao;
	}
}