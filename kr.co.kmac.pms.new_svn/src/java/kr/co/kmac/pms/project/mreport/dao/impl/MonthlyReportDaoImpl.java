package kr.co.kmac.pms.project.mreport.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import javax.sql.DataSource;

import kr.co.kmac.pms.project.mreport.dao.MonthlyReportDao;
import kr.co.kmac.pms.project.mreport.data.MonthlyReport;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.jdbc.object.SqlUpdate;

public class MonthlyReportDaoImpl extends JdbcDaoSupport implements MonthlyReportDao {
	private MonthlyReportInsertQuery monthlyReportInsertQuery;
	private MonthlyReportSelectQuery1 monthlyReportSelectQuery1;
	private MonthlyReportSelectQuery2 monthlyReportSelectQuery2;
	private MonthlyReportUpdateQuery monthlyReportUpdateQuery;
	private MonthlyReportDeleteQuery1 monthlyReportDeleteQuery1;
	private MonthlyReportDeleteQuery2 monthlyReportDeleteQuery2;

	@Override
	protected void initDao() throws Exception {
		this.monthlyReportInsertQuery = new MonthlyReportInsertQuery(getDataSource());
		this.monthlyReportUpdateQuery = new MonthlyReportUpdateQuery(getDataSource());
		this.monthlyReportSelectQuery1 = new MonthlyReportSelectQuery1(getDataSource());
		this.monthlyReportSelectQuery2 = new MonthlyReportSelectQuery2(getDataSource());
		this.monthlyReportDeleteQuery1 = new MonthlyReportDeleteQuery1(getDataSource());
		this.monthlyReportDeleteQuery2 = new MonthlyReportDeleteQuery2(getDataSource());
	}

	protected class MonthlyReportInsertQuery extends SqlUpdate {
		protected MonthlyReportInsertQuery(DataSource ds) {
			super(ds, "	INSERT INTO ProjectMonthlyReport (projectCode, taskFormTypeId, "
					+ "				assignYear, assignMonth, assignDate,  "
					+ "				state,"
					+ "				writerSsn, writerName,"
					+ "				reviewerSsn, reviewerName, "
					+ "				approverSsn, approverName  )"
					+ " VALUES (?,?,  ?,?,?,     ?,    ?,?, ?,?, ?,?)");
			declareParameter(new SqlParameter(Types.VARCHAR)); declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR)); declareParameter(new SqlParameter(Types.VARCHAR)); declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR)); declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR)); declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR)); declareParameter(new SqlParameter(Types.VARCHAR));

			compile();
			setRequiredRowsAffected(1);
		}

		protected int insert(MonthlyReport monthlyReport) throws DataAccessException {
			return update(new Object[] { 
					monthlyReport.getProjectCode(), monthlyReport.getTaskFormTypeId(), 
					monthlyReport.getAssignYear(), monthlyReport.getAssignMonth(), monthlyReport.getAssignDate(), 
					monthlyReport.getState(),
					monthlyReport.getWriterSsn(), monthlyReport.getWriterName(),
					monthlyReport.getReviewerSsn(), monthlyReport.getReviewerName(),
					monthlyReport.getApproverSsn(), monthlyReport.getApproverName()
			});
		}
	}

	protected class MonthlyReportUpdateQuery extends SqlUpdate {
		protected MonthlyReportUpdateQuery(DataSource ds) {
			super(ds, "	UPDATE ProjectMonthlyReport "
					+ "SET "
					+ "    reportTitle=?, 	reportContent=?,		etcContent=?, "
					+ "    projectMemberStr=?,	"
					+ "    attach=?, 			state=?, "					
					+ "    writerSsn=?, 	writerName=?, 		writeDate=?,"
					+ "    reviewerSsn=?, 	reviewerName=?, 	revieweDate=?, 	revieweOpinion=?, "
					+ "    approverSsn=?, 	approverName=?, 	approveDate=?, 	approveOpinion=? "
					
					+ " WHERE projectCode=? and assignYear=? and assignMonth=? and writerSsn=?");
			declareParameter(new SqlParameter(Types.VARCHAR));  declareParameter(new SqlParameter(Types.VARCHAR)); declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR)); 
			declareParameter(new SqlParameter(Types.VARCHAR));  declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR)); declareParameter(new SqlParameter(Types.VARCHAR)); declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR)); declareParameter(new SqlParameter(Types.VARCHAR)); declareParameter(new SqlParameter(Types.VARCHAR)); declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR)); declareParameter(new SqlParameter(Types.VARCHAR)); declareParameter(new SqlParameter(Types.VARCHAR)); declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR)); declareParameter(new SqlParameter(Types.VARCHAR)); declareParameter(new SqlParameter(Types.VARCHAR)); declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
			setRequiredRowsAffected(1);
		}

		protected int update(MonthlyReport monthlyReport) throws DataAccessException {
			return update(new Object[] {
					monthlyReport.getReportTitle(), 	monthlyReport.getReportContent(), monthlyReport.getEtcContent(),
					monthlyReport.getProjectMemberStr(),
					monthlyReport.getAttach(), monthlyReport.getState(),
					monthlyReport.getWriterSsn(), monthlyReport.getWriterName(), monthlyReport.getWriteDate(),
					monthlyReport.getReviewerSsn(), monthlyReport.getReviewerName(), monthlyReport.getRevieweDate(), monthlyReport.getRevieweOpinion(),
					monthlyReport.getApproverSsn(), monthlyReport.getApproverName(), monthlyReport.getApproveDate(), monthlyReport.getApproveOpinion(),
					monthlyReport.getProjectCode(), monthlyReport.getAssignYear(), monthlyReport.getAssignMonth(), monthlyReport.getWriterSsn()
			});
		}
	}

	protected class MonthlyReportDeleteQuery1 extends SqlUpdate {
		protected MonthlyReportDeleteQuery1(DataSource ds) {
			super(ds, " DELETE FROM ProjectMonthlyReport	"
					+ " WHERE projectCode=?			"
					+ " AND assignYear=?			"
					+ " AND assignMonth=?			"
					+ " and writerSsn=?				");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
			setRequiredRowsAffected(1);
		}

		protected int delete(String projectCode, String assignYear, String assignMonth, String writerSsn) throws DataAccessException {
			return this.update(new Object[] { projectCode, assignYear, assignMonth, writerSsn});
		}
	}
	protected class MonthlyReportDeleteQuery2 extends SqlUpdate {
		protected MonthlyReportDeleteQuery2(DataSource ds) {
			super(ds, " DELETE FROM ProjectMonthlyReport	"
					+ " WHERE projectCode=?			");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected int delete(String projectCode) throws DataAccessException {
			return this.update(new Object[] { projectCode });
		}
	}

	protected class MonthlyReportSelectQuery1 extends MappingSqlQuery {
		protected MonthlyReportSelectQuery1(DataSource ds) {
			super(ds, " SELECT a.*, b.projectName, b.preStartDate, b.preEndDate "
					+ " FROM ProjectMonthlyReport a "
					+ " inner join project b on a.projectCode = b.projectCode	"
					+ " WHERE a.projectCode=?			"
					+ " AND assignYear=?			"
					+ " AND assignMonth=?			"
					+ " AND writerSsn=?				"
					);
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected MonthlyReportSelectQuery1(DataSource ds, String query) {
			super(ds, query);
		}

		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			MonthlyReport monthlyReport = new MonthlyReport();
			monthlyReport.setProjectCode(rs.getString("projectCode"));
			monthlyReport.setProjectName(rs.getString("projectName"));
			monthlyReport.setTaskFormTypeId(rs.getString("taskFormTypeId"));
			
			monthlyReport.setProjectStartDate(rs.getString("preStartDate"));
			monthlyReport.setProjectEndDate(rs.getString("preEndDate"));
					
			monthlyReport.setAssignDate(rs.getString("assignDate"));
			monthlyReport.setAssignYear(rs.getString("assignYear"));
			monthlyReport.setAssignMonth(rs.getString("assignMonth"));

			monthlyReport.setReportTitle(rs.getString("reportTitle"));
			monthlyReport.setReportContent(rs.getString("reportContent"));
			monthlyReport.setEtcContent(rs.getString("etcContent"));

			monthlyReport.setProjectMemberStr(rs.getString("projectMemberStr"));

			monthlyReport.setAttach(rs.getString("attach"));
			monthlyReport.setState(rs.getString("state"));

			monthlyReport.setWriterSsn(rs.getString("writerSsn"));
			monthlyReport.setWriterName(rs.getString("writerName"));
			monthlyReport.setWriteDate(rs.getString("writeDate"));
			monthlyReport.setReviewerSsn(rs.getString("reviewerSsn"));
			monthlyReport.setReviewerName(rs.getString("reviewerName"));
			monthlyReport.setRevieweDate(rs.getString("revieweDate"));
			monthlyReport.setRevieweOpinion(rs.getString("revieweOpinion"));
			monthlyReport.setApproverSsn(rs.getString("approverSsn"));
			monthlyReport.setApproverName(rs.getString("approverName"));
			monthlyReport.setApproveDate(rs.getString("approveDate"));
			monthlyReport.setApproveOpinion(rs.getString("approveOpinion"));
			
			return monthlyReport;
		}
	}
	
	protected class MonthlyReportSelectQuery2 extends MonthlyReportSelectQuery1 {
		protected MonthlyReportSelectQuery2(DataSource ds) {
			super(ds, " SELECT *  FROM ProjectMonthlyReport	"
					+ " WHERE projectCode=?			");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected MonthlyReportSelectQuery2(DataSource ds, String query) {
			super(ds, query);
		}
	}

	
	@Override
	public int insert(MonthlyReport monthlyReport) throws DataAccessException {
		return this.monthlyReportInsertQuery.insert(monthlyReport);
	}



	@Override
	public List<MonthlyReport> select(String projectCode) throws DataAccessException {
		return this.monthlyReportSelectQuery2.execute(new Object[] { projectCode });
	}

	@Override
	public MonthlyReport select(String projectCode, String assignYear, String assignMonth, String writerSsn) throws DataAccessException {
		Object object = this.monthlyReportSelectQuery1.findObject(new Object[] { projectCode, assignYear, assignMonth, writerSsn });
		return (MonthlyReport) object;
	}

	
	@Override
	public int update(MonthlyReport monthlyReport) throws DataAccessException {
		return this.monthlyReportUpdateQuery.update(monthlyReport);
	}
	
	
	@Override
	public int delete(String projectCode) throws DataAccessException {
		return this.monthlyReportDeleteQuery2.delete(projectCode );
	}
	

	@Override
	public int delete(String projectCode, String assignYear, String assignMonth, String writerSsn) throws DataAccessException {
		return this.monthlyReportDeleteQuery1.delete(projectCode, assignYear, assignMonth, writerSsn);
	}



}
