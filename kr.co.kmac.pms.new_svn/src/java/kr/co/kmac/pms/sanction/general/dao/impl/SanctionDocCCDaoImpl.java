/*
 * $Id: SanctionDocCCDaoImpl.java,v 1.3 2010/01/31 10:46:49 cvs1 Exp $
 * created by    : jiwoongLee
 * creation-date : 2006. 3. 25.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.sanction.general.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import javax.sql.DataSource;

import kr.co.kmac.pms.sanction.exception.SanctionException;
import kr.co.kmac.pms.sanction.general.dao.SanctionDocCCDao;
import kr.co.kmac.pms.sanction.general.data.SanctionDocCC;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.jdbc.object.SqlUpdate;

/**
 * 운영, 시작, 일반 품의 DAO
 * 
 * @author jiwoongLee
 * @version $Id: SanctionDocCCDaoImpl.java,v 1.3 2010/01/31 10:46:49 cvs1 Exp $
 */
public class SanctionDocCCDaoImpl extends JdbcDaoSupport implements SanctionDocCCDao {

	private SanctionDocCCInsertQuery sanctionDocCCInsertQuery;
	private SanctionDocCCUpdateQuery sanctionDocCCUpdateQuery;
	private SanctionDocCCRetrieveQuery1 sanctionDocCCRetrieveQuery1;
	private SanctionDocCCRetrieveQuery2 sanctionDocCCRetrieveQuery2;
	private SanctionDocCCDeleteQuery1 sanctionDocCCDeleteQuery1;
	private SanctionDocCCDeleteQuery2 sanctionDocCCDeleteQuery2;

	protected void initDao() {
		this.sanctionDocCCInsertQuery = new SanctionDocCCInsertQuery(getDataSource());
		this.sanctionDocCCUpdateQuery = new SanctionDocCCUpdateQuery(getDataSource());
		this.sanctionDocCCRetrieveQuery1 = new SanctionDocCCRetrieveQuery1(getDataSource());
		this.sanctionDocCCRetrieveQuery2 = new SanctionDocCCRetrieveQuery2(getDataSource());
		this.sanctionDocCCDeleteQuery1 = new SanctionDocCCDeleteQuery1(getDataSource());
		this.sanctionDocCCDeleteQuery2 = new SanctionDocCCDeleteQuery2(getDataSource());
	}

	protected class SanctionDocCCInsertQuery extends SqlUpdate {
		protected SanctionDocCCInsertQuery(DataSource ds) {
			super(ds, " INSERT INTO SanctionDocCC(seq, refMemberSsn, refMemberName, draftRefMailSended, "
					+ "                            approveRefMailSended, approveCheckedDate           ) "
					+ " VALUES(?, ?, ?, ?,    ?, ?                                                    ) ");
			declareParameter(new SqlParameter(Types.INTEGER));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.BOOLEAN));

			declareParameter(new SqlParameter(Types.BOOLEAN));
			declareParameter(new SqlParameter(Types.DATE));
			compile();
			setRequiredRowsAffected(1);
		}

		protected int insert(SanctionDocCC sanctionDocCC) {
			return this.update(new Object[] { sanctionDocCC.getSeq(), sanctionDocCC.getRefMemberSsn(), sanctionDocCC.getRefMemberName(),
					sanctionDocCC.isDraftRefMailSended(), sanctionDocCC.isApproveRefMailSended(), sanctionDocCC.getApproveCheckedDate() });
		}
	}

	protected class SanctionDocCCUpdateQuery extends SqlUpdate {
		protected SanctionDocCCUpdateQuery(DataSource ds) {
			super(ds, " update SanctionDocCC set draftRefMailSended=?, approveRefMailSended=?, approveCheckedDate=? "
					+ " where seq=? and refMemberSsn=?                                                     ");
			declareParameter(new SqlParameter(Types.BOOLEAN));
			declareParameter(new SqlParameter(Types.BOOLEAN));
			declareParameter(new SqlParameter(Types.DATE));

			declareParameter(new SqlParameter(Types.INTEGER));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
			//setRequiredRowsAffected(1);
		}
		
		protected int update(SanctionDocCC sanctionDocCC) {
			return this.update(new Object[] { 
					sanctionDocCC.isDraftRefMailSended(), 
					sanctionDocCC.isApproveRefMailSended(), 
					sanctionDocCC.getApproveCheckedDate(),
					sanctionDocCC.getSeq(), 
					sanctionDocCC.getRefMemberSsn()
			});
		}
	}

	protected class SanctionDocCCRetrieveQuery1 extends MappingSqlQuery {
		protected SanctionDocCCRetrieveQuery1(DataSource ds, String query) {
			super(ds, query);
		}

		protected SanctionDocCCRetrieveQuery1(DataSource ds) {
			super(ds, " SELECT (select name from expertpool e where e.ssn = c.refMemberSsn) as refMemberName, * FROM SanctionDocCC c WHERE c.seq = ? ");
			declareParameter(new SqlParameter(Types.INTEGER));
			compile();
		}

		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			SanctionDocCC sanctionDocCC = new SanctionDocCC();
			sanctionDocCC.setSeq(rs.getInt("seq"));
			sanctionDocCC.setRefMemberName(rs.getString("refMemberName"));
			sanctionDocCC.setRefMemberSsn(rs.getString("refMemberSsn"));
			sanctionDocCC.setDraftRefMailSended(rs.getBoolean("draftRefMailSended"));
			sanctionDocCC.setApproveRefMailSended(rs.getBoolean("approveRefMailSended"));
			sanctionDocCC.setApproveCheckedDate(rs.getDate("approveCheckedDate"));
			return sanctionDocCC;
		}

	}

	protected class SanctionDocCCRetrieveQuery2 extends SanctionDocCCRetrieveQuery1 {
		protected SanctionDocCCRetrieveQuery2(DataSource ds, String query) {
			super(ds, query);
		}

		protected SanctionDocCCRetrieveQuery2(DataSource ds) {
			super(ds, " SELECT * FROM SanctionDocCC WHERE seq = ? AND refMemberSsn = ? ");
			declareParameter(new SqlParameter(Types.INTEGER));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}
	}

	protected class SanctionDocCCDeleteQuery1 extends SqlUpdate {
		protected SanctionDocCCDeleteQuery1(DataSource ds) {
			super(ds, " DELETE FROM SanctionDocCC WHERE  seq=? ");
			declareParameter(new SqlParameter(Types.INTEGER));
			compile();
		}

		protected int delete(String seq) {
			return this.update(new Object[] { seq });
		}
	}

	protected class SanctionDocCCDeleteQuery2 extends SqlUpdate {
		protected SanctionDocCCDeleteQuery2(DataSource ds) {
			super(ds, " DELETE FROM SanctionDocCC WHERE  seq=? and refMemberSsn=? ");
			declareParameter(new SqlParameter(Types.INTEGER));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected int delete(String seq, String refMemberSsn) {
			return this.update(new Object[] { seq, refMemberSsn });
		}
	}

	@Override
	public void insert(String seq, List<SanctionDocCC> sanctionDocCCList) throws SanctionException {
		if (sanctionDocCCList != null) {
			for (SanctionDocCC sanctionDocCC : sanctionDocCCList) {
				sanctionDocCC.setSeq(Integer.parseInt(seq));
				this.sanctionDocCCInsertQuery.insert(sanctionDocCC);
			}
		}
	}

	@Override
	public void update(SanctionDocCC sanctionDocCC) throws DataAccessException {
		this.sanctionDocCCUpdateQuery.update(sanctionDocCC);
	}

	@Override
	public void update(String seq, List<SanctionDocCC> sanctionDocCCList) throws SanctionException {
		this.delete(seq);
		this.insert(seq, sanctionDocCCList);
	}

	@Override
	public SanctionDocCC select(String seq, String refMemberSsn) throws SanctionException {
		return (SanctionDocCC) this.sanctionDocCCRetrieveQuery2.findObject(new Object[] { seq, refMemberSsn });
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SanctionDocCC> select(String seq) throws SanctionException {
		return this.sanctionDocCCRetrieveQuery1.execute(new Object[] { seq });
	}

	@Override
	public void delete(String seq, String refMemberSsn) throws SanctionException {
		this.sanctionDocCCDeleteQuery2.delete(seq, refMemberSsn);
	}

	@Override
	public void delete(String seq) throws SanctionException {
		this.sanctionDocCCDeleteQuery1.delete(seq);
	}

}
