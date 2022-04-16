package kr.co.kmac.pms.system.accesslog.dao.impl;

import javax.sql.DataSource;
import java.sql.Types;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.object.SqlUpdate;
import org.springframework.jdbc.object.MappingSqlQuery;

import java.sql.ResultSet;
import java.sql.SQLException;

import kr.co.kmac.pms.system.accesslog.dao.AccountHistoryDao;

public class AccountHistoryDaoImpl  extends JdbcDaoSupport implements AccountHistoryDao {

	@Override
	public void updateEtc(String seq, String etc)
			throws DataAccessException {
		getJdbcTemplate().execute(" " 
				+ " UPDATE AccountHistory SET etc='" + etc + "' "
				+ "  WHERE seq='" + seq + "' ");
	}
	
	/*
	private UpdateQuery updateQuery;

	protected void initDao() throws Exception {
		this.updateQuery = new UpdateQuery(getDataSource());
	}
	
	protected class UpdateQuery extends SqlUpdate {
		protected UpdateQuery(DataSource ds) {
			super(ds, "UPDATE AccountHistory SET etc = ? WHERE seq = ? ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
			//setRequiredRowsAffected(1);
		}
	
		protected int update(String seq, String etc) throws DataAccessException {
			return this.update(new Object[] { etc, seq });
		}
	}
	
	
	@Override
	public void updateEtc(String seq, String etc) throws DataAccessException {
		this.updateQuery.update(seq, etc);
	}
	*/
}