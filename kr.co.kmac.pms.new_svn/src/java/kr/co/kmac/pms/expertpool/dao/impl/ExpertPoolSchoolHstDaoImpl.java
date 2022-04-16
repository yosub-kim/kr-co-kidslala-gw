package kr.co.kmac.pms.expertpool.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import javax.sql.DataSource;

import kr.co.kmac.pms.expertpool.dao.ExpertPoolSchoolHstDao;
import kr.co.kmac.pms.expertpool.data.ExpertPoolSchoolHst;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.jdbc.object.SqlUpdate;

public class ExpertPoolSchoolHstDaoImpl extends JdbcDaoSupport implements ExpertPoolSchoolHstDao {

	private ExpertPoolSchoolHstInsertQuery expertPoolSchoolHstInsertQuery;
	private ExpertPoolSchoolHstUpdateQuery expertPoolSchoolHstUpdateQuery;
	private ExpertPoolSchoolHstDeleteQuery expertPoolSchoolHstDeleteQuery;
	private ExpertPoolSchoolHstRetireveQuery expertPoolSchoolHstRetireveQuery;
	private ExpertPoolSchoolHstFindQuery expertPoolSchoolHstFindQuery;
	private ExpertPoolSchoolHstDeleteQueryMulti expertPoolSchoolHstDeleteQueryMulti;

	protected class ExpertPoolSchoolHstInsertQuery extends SqlUpdate {
		protected ExpertPoolSchoolHstInsertQuery(DataSource ds) {
			super(ds, " INSERT INTO ExpertSchoolHst( ssn, seqNo, term, schoolName, major, graduationState, gpa, location, remark )    "
					+ " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)                       ");
			declareParameter(new SqlParameter(Types.VARCHAR));
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

		protected int insert(ExpertPoolSchoolHst expertPoolSchoolHst) throws DataAccessException {
			return update(new Object[] { expertPoolSchoolHst.getSsn(), expertPoolSchoolHst.getSeq(), expertPoolSchoolHst.getTerm(),
					expertPoolSchoolHst.getSchoolName(), expertPoolSchoolHst.getMajor(), expertPoolSchoolHst.getGraduationState(),
					expertPoolSchoolHst.getGpa(), expertPoolSchoolHst.getLocation(), expertPoolSchoolHst.getRemark() });
		}
	}

	protected class ExpertPoolSchoolHstUpdateQuery extends SqlUpdate {
		protected ExpertPoolSchoolHstUpdateQuery(DataSource ds) {
			super(ds, " UPDATE ExpertSchoolHst                                      "
					+ "	   SET term=?, schoolName=?, major=?, graduationState=?, gpa=?, location=?, remark=?                                     "
					+ "  WHERE ssn = ?                                                  "
					+ "    And seqNo = ?                                                  ");
			declareParameter(new SqlParameter(Types.VARCHAR));
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

		protected int update(ExpertPoolSchoolHst expertPoolSchoolHst) throws DataAccessException {
			return update(new Object[] { expertPoolSchoolHst.getTerm(), expertPoolSchoolHst.getSchoolName(), expertPoolSchoolHst.getMajor(),
					expertPoolSchoolHst.getGraduationState(), expertPoolSchoolHst.getGpa(), expertPoolSchoolHst.getLocation(),
					expertPoolSchoolHst.getRemark(), expertPoolSchoolHst.getSsn(), expertPoolSchoolHst.getSeq() });
		}
	}

	protected class ExpertPoolSchoolHstDeleteQuery extends SqlUpdate {
		protected ExpertPoolSchoolHstDeleteQuery(DataSource ds) {
			super(ds, " Delete from ExpertSchoolHst where ssn = ? and seqNo = ? ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected int delete(String ssn, String seq) {
			return update(new Object[] { ssn, seq });
		}
	}

	protected class ExpertPoolSchoolHstDeleteQueryMulti extends SqlUpdate {
		protected ExpertPoolSchoolHstDeleteQueryMulti(DataSource ds) {
			super(ds, " Delete from ExpertSchoolHst where ssn = ? ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected int delete(String ssn) {
			return update(new Object[] { ssn });
		}
	}

	protected class ExpertPoolSchoolHstRetireveQuery extends MappingSqlQuery {
		protected ExpertPoolSchoolHstRetireveQuery(DataSource ds, String query) {
			super(ds, query);
		}

		protected ExpertPoolSchoolHstRetireveQuery(DataSource ds) {
			super(ds, "select * from ExpertSchoolHst where ssn = ? and seqNo = ?");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			ExpertPoolSchoolHst expertPoolSchoolHst = new ExpertPoolSchoolHst();
			expertPoolSchoolHst.setSsn(rs.getString("ssn"));
			expertPoolSchoolHst.setSeq(rs.getString("seqNo"));
			expertPoolSchoolHst.setTerm(rs.getString("term"));
			expertPoolSchoolHst.setSchoolName(rs.getString("schoolName"));
			expertPoolSchoolHst.setMajor(rs.getString("major"));
			expertPoolSchoolHst.setGraduationState(rs.getString("graduationState"));
			expertPoolSchoolHst.setGpa(rs.getString("gpa"));
			expertPoolSchoolHst.setLocation(rs.getString("location"));
			expertPoolSchoolHst.setRemark(rs.getString("remark"));
			return expertPoolSchoolHst;
		}
	}

	protected class ExpertPoolSchoolHstFindQuery extends ExpertPoolSchoolHstRetireveQuery {
		protected ExpertPoolSchoolHstFindQuery(DataSource ds, String query) {
			super(ds, query);
		}

		protected ExpertPoolSchoolHstFindQuery(DataSource ds) {
			super(ds, "select * from ExpertSchoolHst where ssn = ? order by seqNo ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}
	}

	protected void initDao() throws Exception {
		this.expertPoolSchoolHstInsertQuery = new ExpertPoolSchoolHstInsertQuery(getDataSource());
		this.expertPoolSchoolHstUpdateQuery = new ExpertPoolSchoolHstUpdateQuery(getDataSource());
		this.expertPoolSchoolHstDeleteQuery = new ExpertPoolSchoolHstDeleteQuery(getDataSource());
		this.expertPoolSchoolHstRetireveQuery = new ExpertPoolSchoolHstRetireveQuery(getDataSource());
		this.expertPoolSchoolHstFindQuery = new ExpertPoolSchoolHstFindQuery(getDataSource());
		this.expertPoolSchoolHstDeleteQueryMulti = new ExpertPoolSchoolHstDeleteQueryMulti(getDataSource());
		super.initDao();
	}

	public void create(ExpertPoolSchoolHst expertPoolSchoolHst) throws DataAccessException {
		this.expertPoolSchoolHstInsertQuery.insert(expertPoolSchoolHst);
	}

	@SuppressWarnings("unchecked")
	public List<ExpertPoolSchoolHst> findAll(String ssn) throws DataAccessException {
		return expertPoolSchoolHstFindQuery.execute(new Object[] { ssn });
	}

	public boolean isExist(String ssn, String seqNo) throws DataAccessException {
		return expertPoolSchoolHstRetireveQuery.findObject(new Object[] { ssn, seqNo }) != null;
	}

	public void remove(String ssn, String seqNo) throws DataAccessException {
		this.expertPoolSchoolHstDeleteQuery.delete(ssn, seqNo);

	}

	public void remove(String ssn) throws DataAccessException {
		this.expertPoolSchoolHstDeleteQueryMulti.delete(ssn);

	}

	public ExpertPoolSchoolHst retrieve(String ssn, String seqNo) throws DataAccessException {
		return (ExpertPoolSchoolHst) expertPoolSchoolHstRetireveQuery.findObject(new Object[] { ssn, seqNo });
	}

	public void store(ExpertPoolSchoolHst expertPoolSchoolHst) throws DataAccessException {
		this.expertPoolSchoolHstUpdateQuery.update(expertPoolSchoolHst);
	}

}
