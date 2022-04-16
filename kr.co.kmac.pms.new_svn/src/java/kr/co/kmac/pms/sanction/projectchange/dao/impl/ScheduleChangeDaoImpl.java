package kr.co.kmac.pms.sanction.projectchange.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import javax.sql.DataSource;

import kr.co.kmac.pms.sanction.projectchange.dao.ScheduleChangeDao;
import kr.co.kmac.pms.sanction.projectchange.data.ScheduleChange;
import kr.co.kmac.pms.sanction.projectchange.form.ScheduleChangeSanctionForm;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.jdbc.object.SqlUpdate;

public class ScheduleChangeDaoImpl extends JdbcDaoSupport implements ScheduleChangeDao {

	private ScheduleInsertQuery scheduleInsertQuery;
	private ScheduleSelectQuery scheduleSelectQuery;
	private ScheduleDeleteQuery scheduleDeleteQuery;
	private ScheduleUpdateQuery1 scheduleUpdatequery1;
	private ScheduleUpdateQuery scheduleUpdatequery;

	protected void initDao() throws Exception {
		this.scheduleInsertQuery = new ScheduleInsertQuery(getDataSource());
		this.scheduleDeleteQuery = new ScheduleDeleteQuery(getDataSource());
		this.scheduleUpdatequery1 = new ScheduleUpdateQuery1(getDataSource());
		this.scheduleUpdatequery = new ScheduleUpdateQuery(getDataSource());
		this.scheduleSelectQuery = new ScheduleSelectQuery(getDataSource());
	}

	protected class ScheduleInsertQuery extends SqlUpdate {
		protected ScheduleInsertQuery(DataSource ds) {
			super(ds, "	INSERT INTO projectScheduleChange (	projectCode, changeScheduleSeq, "
					+ "				preStartDate, preEndDate, realStartDate, realEndDate )	"
					+ "	VALUES	( ?,  ?,  ?,  ?,  ?,  ?)                                    ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.INTEGER));

			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected int insert(ScheduleChange scheduleChange) throws DataAccessException {
			return update(new Object[] { scheduleChange.getProjectCode(), scheduleChange.getScheduleChangeSeq(), scheduleChange.getPreStartDate(),
					scheduleChange.getPreEndDate(), scheduleChange.getRealStartDate(), scheduleChange.getRealEndDate() });
		}
	}

	protected class ScheduleDeleteQuery extends SqlUpdate {
		protected ScheduleDeleteQuery(DataSource ds) {
			super(ds, "	Delete from projectScheduleChange WHERE projectCode=? AND changeScheduleSeq=? ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.INTEGER));
			compile();
		}

		protected int delete(String projectCode, String scheduleChangeSeq) throws DataAccessException {
			return update(new Object[] { projectCode, scheduleChangeSeq });
		}
	}

	protected class ScheduleUpdateQuery extends SqlUpdate {
		protected ScheduleUpdateQuery(DataSource ds) {
			super(ds, "	UPDATE 	projectScheduleChange SET preStartDate=?, preEndDate=?, realStartDate=?, realEndDate=?	"
					+ "	 WHERE 	projectCode =? AND changeScheduleSeq =?									");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));

			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.INTEGER));
			compile();
		}

		protected int update(ScheduleChange scheduleChange) throws DataAccessException {
			return update(new Object[] { scheduleChange.getPreStartDate(), scheduleChange.getPreEndDate(), scheduleChange.getRealStartDate(),
					scheduleChange.getRealEndDate(), scheduleChange.getProjectCode(), scheduleChange.getScheduleChangeSeq() });
		}
	}

	protected class ScheduleSelectQuery extends MappingSqlQuery {
		protected ScheduleSelectQuery(DataSource ds) {
			super(ds, "	SELECT * FROM projectScheduleChange WHERE projectCode =? AND changeScheduleSeq =?	");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			ScheduleChange scheduleChange = new ScheduleChangeSanctionForm();
			scheduleChange.setProjectCode(rs.getString("projectCode"));
			scheduleChange.setScheduleChangeSeq(rs.getInt("changeScheduleSeq"));
			scheduleChange.setPreStartDate(rs.getString("preStartDate"));
			scheduleChange.setPreEndDate(rs.getString("preEndDate"));
			scheduleChange.setRealStartDate(rs.getString("realStartDate"));
			scheduleChange.setRealEndDate(rs.getString("realEndDate"));
			return scheduleChange;
		}
	}

	protected class ScheduleUpdateQuery1 extends SqlUpdate {
		protected ScheduleUpdateQuery1(DataSource ds) {
			super(ds, "	UPDATE 	project SET realStartDate=?, realEndDate=? WHERE projectCode=? ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected int update(ScheduleChange scheduleChange) throws DataAccessException {
			return update(new Object[] { scheduleChange.getRealStartDate(), scheduleChange.getRealEndDate(), scheduleChange.getProjectCode() });
		}
	}

	@Override
	public void finishScheduleChange(ScheduleChange scheduleChange) throws DataAccessException {
		this.scheduleUpdatequery1.update(scheduleChange);
	}

	@Override
	public void insertScheduleChange(ScheduleChange scheduleChange) throws DataAccessException {
		this.scheduleInsertQuery.insert(scheduleChange);
	}

	@Override
	public void deleteScheduleChange(String projectCode, String scheduleChangeSeq) throws DataAccessException {
		this.scheduleDeleteQuery.delete(projectCode, scheduleChangeSeq);
	}

	@Override
	public ScheduleChange selectScheduleChange(String projectCode, String scheduleChangeSeq) throws DataAccessException {
		return (ScheduleChange) this.scheduleSelectQuery.findObject(new Object[] { projectCode, scheduleChangeSeq });
	}

	@Override
	public void updateScheduleChange(ScheduleChange scheduleChange) throws DataAccessException {
		this.scheduleUpdatequery.update(scheduleChange);
	}

}
