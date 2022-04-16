package kr.co.kmac.pms.project.wreport.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import javax.sql.DataSource;

import kr.co.kmac.pms.project.progress.data.ProjectProgressEntity;
import kr.co.kmac.pms.project.wreport.dao.WeeklyReportDao;
import kr.co.kmac.pms.project.wreport.data.WeeklyReport;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.jdbc.object.SqlUpdate;

public class WeeklyReportDaoImpl extends JdbcDaoSupport implements WeeklyReportDao {
	private WeeklyReportInsertQuery weeklyReportInsertQuery;
	private WeeklyReportSelectQuery1 weeklyReportSelectQuery1;
	private WeeklyReportSelectQuery2 weeklyReportSelectQuery2;
	private WeeklyReportUpdateQuery weeklyReportUpdateQuery;
	private WeeklyReportDeleteQuery1 weeklyReportDeleteQuery1;
	private WeeklyReportDeleteQuery2 weeklyReportDeleteQuery2;
	private WeeklyReportActivitySelectQuery1 weeklyReportActivitySelectQuery1;

	@Override
	protected void initDao() throws Exception {
		this.weeklyReportInsertQuery = new WeeklyReportInsertQuery(getDataSource());
		this.weeklyReportUpdateQuery = new WeeklyReportUpdateQuery(getDataSource());
		this.weeklyReportSelectQuery1 = new WeeklyReportSelectQuery1(getDataSource());
		this.weeklyReportSelectQuery2 = new WeeklyReportSelectQuery2(getDataSource());
		this.weeklyReportDeleteQuery1 = new WeeklyReportDeleteQuery1(getDataSource());
		this.weeklyReportDeleteQuery2 = new WeeklyReportDeleteQuery2(getDataSource());
		this.weeklyReportActivitySelectQuery1 = new WeeklyReportActivitySelectQuery1(getDataSource());
	}

	protected class WeeklyReportInsertQuery extends SqlUpdate {
		protected WeeklyReportInsertQuery(DataSource ds) {
			super(ds, "	INSERT INTO ProjectWeeklyReport (projectCode, taskFormTypeId, "
					+ "				assignDate, assignYear, assignMonth, assignWeekOfMonth, weekOfProject, "
					+ "				thisWeekFromDate, thisWeekToDate, nextWeekFromDate, nextWeekToDate, "
					+ "				planProgress, state,"
					+ "				writerSsn, writerName,"
					+ "				reviewerSsn, reviewerName, "
					+ "				approverSsn, approverName  )"
					+ " VALUES (?,?,   ?,?,?,?,?,   ?,?,?,?,   ?,?,   ?,?,?,?,?,?)");
			declareParameter(new SqlParameter(Types.VARCHAR)); declareParameter(new SqlParameter(Types.VARCHAR));
			
			declareParameter(new SqlParameter(Types.VARCHAR)); declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR)); declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR)); 
			
			declareParameter(new SqlParameter(Types.VARCHAR)); declareParameter(new SqlParameter(Types.VARCHAR)); 
			declareParameter(new SqlParameter(Types.VARCHAR)); declareParameter(new SqlParameter(Types.VARCHAR)); 

			declareParameter(new SqlParameter(Types.VARCHAR)); declareParameter(new SqlParameter(Types.VARCHAR));

			declareParameter(new SqlParameter(Types.VARCHAR)); declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR)); declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR)); declareParameter(new SqlParameter(Types.VARCHAR));

			compile();
			setRequiredRowsAffected(1);
		}

		protected int insert(WeeklyReport weeklyReport) throws DataAccessException {
			return update(new Object[] { 
					weeklyReport.getProjectCode(), weeklyReport.getTaskFormTypeId(), 
					weeklyReport.getAssignDate(), weeklyReport.getAssignYear(), weeklyReport.getAssignMonth(), weeklyReport.getAssignWeekOfMonth(), weeklyReport.getWeekOfProject(), 
					weeklyReport.getThisWeekFromDate(), weeklyReport.getThisWeekToDate(), weeklyReport.getNextWeekFromDate(), weeklyReport.getNextWeekToDate(),
					weeklyReport.getPlanProgress(), weeklyReport.getState(),
					weeklyReport.getWriterSsn(), weeklyReport.getWriterName(),
					weeklyReport.getReviewerSsn(), weeklyReport.getReviewerName(),
					weeklyReport.getApproverSsn(), weeklyReport.getApproverName()
			});
		}
	}

	protected class WeeklyReportUpdateQuery extends SqlUpdate {
		protected WeeklyReportUpdateQuery(DataSource ds) {
			super(ds, "	UPDATE ProjectWeeklyReport "
					+ "SET realProgress=?, progressRatio=?,		"
					+ "    projectMemberStr=?,	"
					+ "    thisWeekContent=?, 	thisWeekOutputList=?,		thisWeekPlComment=?, "
					+ "    nextWeekContent=?, 	nextWeekOutputList=?,		nextWeekPlComment=?, "
					+ "    delayReason=?,		"	
					+ "    attach=?, 			state=?, "
					
					+ "    writerSsn=?, 	writerName=?, 		writeDate=?,"
					+ "    reviewerSsn=?, 	reviewerName=?, 	revieweDate=?, 	revieweOpinion=?, "
					+ "    approverSsn=?, 	approverName=?, 	approveDate=?, 	approveOpinion=?,	activity=? "
					
					+ " WHERE projectCode=? and assignYear=? and assignMonth=? and assignWeekOfMonth=?");
			declareParameter(new SqlParameter(Types.VARCHAR)); declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR)); 
			declareParameter(new SqlParameter(Types.VARCHAR)); declareParameter(new SqlParameter(Types.VARCHAR));declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR)); declareParameter(new SqlParameter(Types.VARCHAR));declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));	
			declareParameter(new SqlParameter(Types.VARCHAR)); declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR)); declareParameter(new SqlParameter(Types.VARCHAR)); declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR)); declareParameter(new SqlParameter(Types.VARCHAR)); declareParameter(new SqlParameter(Types.VARCHAR)); declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR)); declareParameter(new SqlParameter(Types.VARCHAR)); declareParameter(new SqlParameter(Types.VARCHAR)); declareParameter(new SqlParameter(Types.VARCHAR)); declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR)); declareParameter(new SqlParameter(Types.VARCHAR)); declareParameter(new SqlParameter(Types.VARCHAR)); declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
			setRequiredRowsAffected(1);
		}

		protected int update(WeeklyReport weeklyReport) throws DataAccessException {
			return update(new Object[] {
					weeklyReport.getRealProgress(), 	weeklyReport.getProgressRatio(),
					weeklyReport.getProjectMemberStr(),
					weeklyReport.getThisWeekContent(), weeklyReport.getThisWeekOutputList(), weeklyReport.getThisWeekPlComment(),
					weeklyReport.getNextWeekContent(), weeklyReport.getNextWeekOutputList(), weeklyReport.getNextWeekPlComment(),
					weeklyReport.getDelayReason(),
					weeklyReport.getAttach(), weeklyReport.getState(),
					weeklyReport.getWriterSsn(), weeklyReport.getWriterName(), weeklyReport.getWriteDate(),
					weeklyReport.getReviewerSsn(), weeklyReport.getReviewerName(), weeklyReport.getRevieweDate(), weeklyReport.getRevieweOpinion(),
					weeklyReport.getApproverSsn(), weeklyReport.getApproverName(), weeklyReport.getApproveDate(), weeklyReport.getApproveOpinion(), weeklyReport.getActivity(),
					weeklyReport.getProjectCode(), weeklyReport.getAssignYear(), weeklyReport.getAssignMonth(), weeklyReport.getAssignWeekOfMonth()
			});
		}
	}

	protected class WeeklyReportDeleteQuery1 extends SqlUpdate {
		protected WeeklyReportDeleteQuery1(DataSource ds) {
			super(ds, " DELETE FROM ProjectWeeklyReport	"
					+ " WHERE projectCode=?			"
					+ " AND assignYear=?			"
					+ " AND assignMonth=?			"
					+ " AND assignWeekOfMonth=?		");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected int delete(String projectCode, String assignYear, String assignMonth, String assignWeekOfMonth) throws DataAccessException {
			return this.update(new Object[] { projectCode, assignYear, assignMonth, assignWeekOfMonth  });
		}
	}
	protected class WeeklyReportDeleteQuery2 extends SqlUpdate {
		protected WeeklyReportDeleteQuery2(DataSource ds) {
			super(ds, " DELETE FROM ProjectWeeklyReport	"
					+ " WHERE projectCode=?			");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected int delete(String projectCode) throws DataAccessException {
			return this.update(new Object[] { projectCode });
		}
	}
	
	protected class WeeklyReportActivitySelectQuery1 extends MappingSqlQuery {
		protected WeeklyReportActivitySelectQuery1(DataSource ds){
			super (ds,"SELECT * FROM projectScheduleM WHERE projectcode=? ORDER BY orderSeq");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			ProjectProgressEntity projectProgressEntity = new ProjectProgressEntity();
			projectProgressEntity.setProjectCode(rs.getString("projectCode"));
			projectProgressEntity.setWorkName(rs.getString("workName"));
			projectProgressEntity.setRealEndDate(rs.getString("realEndDate"));
			projectProgressEntity.setContentId(rs.getString("contentId"));
			
			return projectProgressEntity;
		}
	}

	protected class WeeklyReportSelectQuery1 extends MappingSqlQuery {
		protected WeeklyReportSelectQuery1(DataSource ds) {
			super(ds, " SELECT a.*, b.projectName, b.preStartDate, b.preEndDate "
					+ " FROM ProjectWeeklyReport a "
					+ " inner join project b on a.projectCode = b.projectCode	"
					+ " WHERE a.projectCode=?			"
					+ " AND assignYear=?			"
					+ " AND assignMonth=?			"
					+ " AND assignWeekOfMonth=?		");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected WeeklyReportSelectQuery1(DataSource ds, String query) {
			super(ds, query);
		}

		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			WeeklyReport weeklyReport = new WeeklyReport();
			weeklyReport.setProjectCode(rs.getString("projectCode"));
			weeklyReport.setProjectName(rs.getString("projectName"));
			weeklyReport.setTaskFormTypeId(rs.getString("taskFormTypeId"));
			
			weeklyReport.setProjectStartDate(rs.getString("preStartDate"));
			weeklyReport.setProjectEndDate(rs.getString("preEndDate"));
					
			weeklyReport.setAssignDate(rs.getString("assignDate"));
			weeklyReport.setAssignYear(rs.getString("assignYear"));
			weeklyReport.setAssignMonth(rs.getString("assignMonth"));
			weeklyReport.setAssignWeekOfMonth(rs.getString("assignWeekOfMonth"));
			weeklyReport.setWeekOfProject(rs.getString("weekOfProject"));

			weeklyReport.setRealProgress(rs.getString("realProgress"));
			weeklyReport.setPlanProgress(rs.getString("planProgress"));
			weeklyReport.setProgressRatio(rs.getString("progressRatio"));

			weeklyReport.setThisWeekFromDate(rs.getString("thisWeekFromDate"));
			weeklyReport.setThisWeekToDate(rs.getString("thisWeekToDate"));
			weeklyReport.setThisWeekContent(rs.getString("thisWeekContent"));
			weeklyReport.setThisWeekOutputList(rs.getString("thisWeekOutputList"));
			weeklyReport.setThisWeekPlComment(rs.getString("thisWeekPlComment"));

			weeklyReport.setNextWeekFromDate(rs.getString("nextWeekFromDate"));
			weeklyReport.setNextWeekToDate(rs.getString("nextWeekToDate"));
			weeklyReport.setNextWeekContent(rs.getString("nextWeekContent"));
			weeklyReport.setNextWeekOutputList(rs.getString("nextWeekOutputList"));
			weeklyReport.setNextWeekPlComment(rs.getString("nextWeekPlComment"));

			weeklyReport.setDelayReason(rs.getString("delayReason"));
			weeklyReport.setProjectMemberStr(rs.getString("projectMemberStr"));

			weeklyReport.setAttach(rs.getString("attach"));
			weeklyReport.setState(rs.getString("state"));

			weeklyReport.setWriterSsn(rs.getString("writerSsn"));
			weeklyReport.setWriterName(rs.getString("writerName"));
			weeklyReport.setWriteDate(rs.getString("writeDate"));
			weeklyReport.setReviewerSsn(rs.getString("reviewerSsn"));
			weeklyReport.setReviewerName(rs.getString("reviewerName"));
			weeklyReport.setRevieweDate(rs.getString("revieweDate"));
			weeklyReport.setRevieweOpinion(rs.getString("revieweOpinion"));
			weeklyReport.setApproverSsn(rs.getString("approverSsn"));
			weeklyReport.setApproverName(rs.getString("approverName"));
			weeklyReport.setApproveDate(rs.getString("approveDate"));
			weeklyReport.setApproveOpinion(rs.getString("approveOpinion"));
			weeklyReport.setActivity(rs.getString("activity"));
			
			return weeklyReport;
		}
	}
	
	protected class WeeklyReportSelectQuery2 extends WeeklyReportSelectQuery1 {
		protected WeeklyReportSelectQuery2(DataSource ds) {
			super(ds, " SELECT *  FROM ProjectWeeklyReport	"
					+ " WHERE projectCode=?			");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected WeeklyReportSelectQuery2(DataSource ds, String query) {
			super(ds, query);
		}
	}

	
	@Override
	public int insert(WeeklyReport weeklyReport) throws DataAccessException {
		return this.weeklyReportInsertQuery.insert(weeklyReport);
	}



	@Override
	public List<WeeklyReport> select(String projectCode) throws DataAccessException {
		return this.weeklyReportSelectQuery2.execute(new Object[] { projectCode });
	}
	
	
	@Override
	public WeeklyReport select(String projectCode, String assignYear, String assignMonth, String assignWeekOfMonth) throws DataAccessException {
		Object object = this.weeklyReportSelectQuery1.findObject(new Object[] { projectCode, assignYear, assignMonth, assignWeekOfMonth });
		return (WeeklyReport) object;
	}

	
	@Override
	public int update(WeeklyReport weeklyReport) throws DataAccessException {
		return this.weeklyReportUpdateQuery.update(weeklyReport);
	}
	
	
	@Override
	public int delete(String projectCode) throws DataAccessException {
		return this.weeklyReportDeleteQuery2.delete(projectCode );
	}
	

	@Override
	public int delete(String projectCode, String assignYear, String assignMonth, String assignWeekOfMonth) throws DataAccessException {
		return this.weeklyReportDeleteQuery1.delete(projectCode, assignYear, assignMonth, assignWeekOfMonth );
	}

	
	@Override
	public List<ProjectProgressEntity> selectActivity(String projectCode) throws DataAccessException {
		return this.weeklyReportActivitySelectQuery1.execute(new Object[] { projectCode });
	}

}
