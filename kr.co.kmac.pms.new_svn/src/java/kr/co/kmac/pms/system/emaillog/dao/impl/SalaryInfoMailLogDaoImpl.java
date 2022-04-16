/**
 * @author yhyim
 *
 */
package kr.co.kmac.pms.system.emaillog.dao.impl;

import java.sql.Types;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.object.SqlUpdate;

import kr.co.kmac.pms.system.emaillog.dao.SalaryInfoMailLogDao;
import kr.co.kmac.pms.system.emaillog.data.SalaryInfoMailLogData;

public class SalaryInfoMailLogDaoImpl extends JdbcDaoSupport implements SalaryInfoMailLogDao {

	private SalaryInfoMailLogCreate mailLogCreate;
	private SalaryInfoMailLogDelete mailLogDelete;

	protected class SalaryInfoMailLogCreate extends SqlUpdate {
		protected SalaryInfoMailLogCreate(DataSource ds) {
			super(ds, " INSERT INTO SalaryMailingList( " + "		year, month, ssn, sendDateTime)" + " VALUES(?, ?, ?, GETDATE() ) ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected int insert(SalaryInfoMailLogData mailLogData) throws DataAccessException {
			return this.update(new Object[] { mailLogData.getYear(), mailLogData.getMonth(), mailLogData.getSsn() });
		}
	}
	
	protected class SalaryInfoMailLogDelete extends SqlUpdate {
		protected SalaryInfoMailLogDelete(DataSource ds) {
			super(ds, "DELETE FROM SalaryMailingList WHERE seq = ?");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected int delete(String emailSeq) throws DataAccessException {
			return this.update(new Object[] { emailSeq });
		}
	}	

	protected void initDao() throws Exception {
		mailLogCreate = new SalaryInfoMailLogCreate(getDataSource());
		mailLogDelete = new SalaryInfoMailLogDelete(getDataSource());		
	}

	@Override
	public void insetSalaryInfoMailLog(SalaryInfoMailLogData mailLogData) throws DataAccessException {
		// TODO Auto-generated method stub
		this.mailLogCreate.insert(mailLogData);
	}

	@Override
	public void deleteSalaryInfoMailLog(String emailSeq) throws DataAccessException {
		// TODO Auto-generated method stub
		this.mailLogDelete.delete(emailSeq);
	}
}