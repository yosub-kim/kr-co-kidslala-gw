/*
 * $Id: ExpertPoolCareerHstDaoImpl.java,v 1.1 2009/09/19 11:15:34 cvs3 Exp $
 * created by    : jiwoongLee
 * creation-date : 2006. 1. 17
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.expertpool.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import javax.sql.DataSource;

import kr.co.kmac.pms.expertpool.dao.ExpertPoolCareerHstDao;
import kr.co.kmac.pms.expertpool.data.ExpertPoolCareerHst;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.jdbc.object.SqlUpdate;
import org.springframework.orm.ObjectRetrievalFailureException;

/**
 * TODO 클래스 설명
 * 
 * @author jiwoongLee
 * @version $Id: ExpertPoolCareerHstDaoImpl.java,v 1.1 2009/09/19 11:15:34 cvs3 Exp $
 */
public class ExpertPoolCareerHstDaoImpl extends JdbcDaoSupport implements ExpertPoolCareerHstDao {

	private ExpertPoolCareerHstInsertQuery expertPoolCareerHstInsertQuery;
	private ExpertPoolCareerHstUpdateQuery expertPoolCareerHstUpdateQuery;
	private ExpertPoolCareerHstRetrieveQuery expertPoolCareerHstRetrieveQuery;
	private ExpertPoolCareerHstFindQuery expertPoolCareerHstFindQuery;
	private ExpertPoolCareerHstDeleteQuery expertPoolCareerHstDeleteQuery;
	private ExpertPoolCareerHstDeleteQueryMulti expertPoolCareerHstDeleteQueryMulti;

	protected class ExpertPoolCareerHstInsertQuery extends SqlUpdate {
		protected ExpertPoolCareerHstInsertQuery(DataSource ds) {
			super(ds, " INSERT INTO ExpertCareerHst(ssn, seqNo, term, companyName, workingDept,companyPosition, workingDetail, remark)    "
					+ "	VALUES(?, ?, ?, ?, ?, ?, ?, ? )                                                                                   ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected int insert(ExpertPoolCareerHst careerHst) {
			return update(new Object[] { careerHst.getSsn(), careerHst.getSeqNo(), careerHst.getTerm(), careerHst.getCompanyName(),
					careerHst.getWorkingDept(), careerHst.getCompanyPosition(), careerHst.getWorkingDetail(), careerHst.getRemark() });

		}
	}

	protected class ExpertPoolCareerHstUpdateQuery extends SqlUpdate {
		protected ExpertPoolCareerHstUpdateQuery(DataSource ds) {
			super(ds, " UPDATE ExpertCareerHst SET term=?, companyName=?, workingDept=?, companyPosition=?, workingDetail=?, remark=?          "
					+ " WHERE ssn=? And seqNo=?                               ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected int update(ExpertPoolCareerHst careerHst) {
			return update(new Object[] { careerHst.getTerm(), careerHst.getCompanyName(), careerHst.getWorkingDept(), careerHst.getCompanyPosition(),
					careerHst.getWorkingDetail(), careerHst.getRemark(), careerHst.getSsn(), careerHst.getSeqNo() });
		}
	}

	protected class ExpertPoolCareerHstDeleteQuery extends SqlUpdate {
		protected ExpertPoolCareerHstDeleteQuery(DataSource ds) {
			super(ds, " Delete from ExpertCareerHst where ssn = ?, seqNo = ?");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
		}

		protected int delete(String ssn, String seqNo) {
			return update(new Object[] { ssn, seqNo });
		}
	}

	protected class ExpertPoolCareerHstDeleteQueryMulti extends SqlUpdate {
		protected ExpertPoolCareerHstDeleteQueryMulti(DataSource ds) {
			super(ds, " Delete from ExpertCareerHst where ssn = ?");
			declareParameter(new SqlParameter(Types.VARCHAR));
		}

		protected int delete(String ssn) {
			return update(new Object[] { ssn });
		}
	}

	protected class ExpertPoolCareerHstRetrieveQuery extends MappingSqlQuery {
		protected ExpertPoolCareerHstRetrieveQuery(DataSource ds, String query) {
			super(ds, query);
		}

		protected ExpertPoolCareerHstRetrieveQuery(DataSource ds) {
			super(ds, "seelct * from ExpertCareerHst where ssn = ?, seqNo = ? ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			ExpertPoolCareerHst expertPoolCareerHst = new ExpertPoolCareerHst();
			expertPoolCareerHst.setSsn(rs.getString("ssn"));
			expertPoolCareerHst.setSeqNo(rs.getString("seqNo"));
			expertPoolCareerHst.setTerm(rs.getString("term"));
			expertPoolCareerHst.setCompanyName(rs.getString("companyName"));
			expertPoolCareerHst.setWorkingDept(rs.getString("workingDept"));
			expertPoolCareerHst.setCompanyPosition(rs.getString("companyPosition"));
			expertPoolCareerHst.setWorkingDetail(rs.getString("workingDetail"));
			expertPoolCareerHst.setRemark(rs.getString("remark"));
			return expertPoolCareerHst;
		}
	}

	protected class ExpertPoolCareerHstFindQuery extends ExpertPoolCareerHstRetrieveQuery {
		protected ExpertPoolCareerHstFindQuery(DataSource ds) {
			super(ds, "select * from ExpertCareerHst where ssn = ? order by ssn, seqNo ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}
	}

	protected void initDao() throws Exception {
		this.expertPoolCareerHstFindQuery = new ExpertPoolCareerHstFindQuery(getDataSource());
		this.expertPoolCareerHstInsertQuery = new ExpertPoolCareerHstInsertQuery(getDataSource());
		this.expertPoolCareerHstRetrieveQuery = new ExpertPoolCareerHstRetrieveQuery(getDataSource());
		this.expertPoolCareerHstUpdateQuery = new ExpertPoolCareerHstUpdateQuery(getDataSource());
		this.expertPoolCareerHstDeleteQuery = new ExpertPoolCareerHstDeleteQuery(getDataSource());
		this.expertPoolCareerHstDeleteQueryMulti = new ExpertPoolCareerHstDeleteQueryMulti(getDataSource());
	}

	public void create(ExpertPoolCareerHst expertPoolCareerHst) throws DataAccessException {
		this.expertPoolCareerHstInsertQuery.insert(expertPoolCareerHst);
	}

	public boolean isExist(String ssn, String seqNo) throws DataAccessException {
		Object object = this.expertPoolCareerHstRetrieveQuery.findObject(new Object[] { ssn, seqNo });
		return object != null;
	}

	public void remove(String ssn, String seqNo) throws DataAccessException {
		this.expertPoolCareerHstDeleteQuery.delete(ssn, seqNo);
	}

	public void remove(String ssn) throws DataAccessException {
		this.expertPoolCareerHstDeleteQueryMulti.delete(ssn);
	}

	public ExpertPoolCareerHst retrieve(String ssn, String seqNo) throws DataAccessException {
		Object object = this.expertPoolCareerHstRetrieveQuery.findObject(new Object[] { ssn, seqNo });
		if (object == null) {
			throw new ObjectRetrievalFailureException(ExpertPoolCareerHst.class, ssn + ":" + seqNo);
		}
		return (ExpertPoolCareerHst) object;
	}

	@SuppressWarnings("unchecked")
	public List<ExpertPoolCareerHst> findAll(String ssn) throws DataAccessException {
		return this.expertPoolCareerHstFindQuery.execute(ssn);
	}

	public void store(ExpertPoolCareerHst expertPoolCareerHst) throws DataAccessException {
		this.expertPoolCareerHstUpdateQuery.update(expertPoolCareerHst);
	}

}
