package kr.co.kmac.pms.sanction.general.dao.impl;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.object.MappingSqlQuery;

import kr.co.kmac.pms.sanction.general.dao.CcoTargetDao;
import kr.co.kmac.pms.sanction.general.data.CcoTarget;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import javax.sql.DataSource;

public class CcoTargetDaoImpl extends JdbcDaoSupport implements CcoTargetDao {
	
	private CcoTargetRetrieveQuery ccoTargetRetrieveQuery;
	private CcoTargetSelectQuery ccoTargetSelectQuery;

	protected void initDao() throws Exception {
		this.ccoTargetRetrieveQuery = new CcoTargetRetrieveQuery(getDataSource());
		this.ccoTargetSelectQuery = new CcoTargetSelectQuery(getDataSource());
	}
	
	protected class CcoTargetRetrieveQuery extends MappingSqlQuery{
		protected CcoTargetRetrieveQuery(DataSource ds) {
			super(ds, "	SELECT count(*) ccoCnt FROM cco_target WHERE com_code = ? AND year = left(convert(varchar,getDate(),112),4)	");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}
		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new Integer(rs.getInt("ccoCnt"));
		}
	}
	
	protected class CcoTargetSelectQuery extends MappingSqlQuery{
		protected CcoTargetSelectQuery(DataSource ds) {
			super(ds, "	SELECT * FROM cco_target WHERE com_code = ?	AND year = left(convert(varchar,getDate(),112),4) ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}
		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			CcoTarget ccoTarget = new CcoTarget();
			ccoTarget.setCcoCode(rs.getString("cco_code"));
			ccoTarget.setCcoGubun(rs.getString("cco_gubun"));
			ccoTarget.setCompanyName(rs.getString("com_name"));
			ccoTarget.setGroupName(rs.getString("group_name"));
			ccoTarget.setYear(rs.getString("year"));
			return ccoTarget;
		}
	}
	
	@Override
	public CcoTarget select(String companyCode) throws DataAccessException {
		return (CcoTarget) this.ccoTargetSelectQuery.findObject(new Object[] { companyCode });
	}

	@Override
	public boolean isExist(String companyCode) throws DataAccessException {		
		return (Integer)this.ccoTargetRetrieveQuery.findObject(new Object[] { companyCode }) > 0 ? true : false;
	}
	
}