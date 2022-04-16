/*
 * $Id: ProjectReportContentDaoImpl.java,v 1.3 2009/11/15 09:53:32 cvs1 Exp $
 * created by    : jiwoongLee
 * creation-date : 2006. 3. 9.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.sanction.preport.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import javax.sql.DataSource;

import kr.co.kmac.pms.sanction.preport.dao.ProjectReportContentDao;
import kr.co.kmac.pms.sanction.preport.data.ProjectReportContent;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.jdbc.object.SqlUpdate;

/**
 * TODO 클래스 설명
 * 
 * @author jiwoongLee
 * @version $Id: ProjectReportContentDaoImpl.java,v 1.3 2009/11/15 09:53:32 cvs1 Exp $
 */
public class ProjectReportContentDaoImpl extends JdbcDaoSupport implements ProjectReportContentDao {

	private ProjectReportInsertQuery projectReportInsertQuery;
	private ProjectReportUpdateQuery projectReportUpdateQuery;
	private ProjectReportDeleteQuery1 projectReportDeleteQuery1;
	private ProjectReportDeleteQuery2 projectReportDeleteQuery2;
	private ProjectReportRetrieveQuery1 projectReportRetrieveQuery1;
	private ProjectReportRetrieveQuery2 projectReportRetrieveQuery2;
	private ProjectReportRetrieveQuery3 projectReportRetrieveQuery3;
	private ProjectReportRetrieveQuery4 projectReportRetrieveQuery4;
	private ProjectReportRetrieveQuery5 projectReportRetrieveQuery5;

	protected class ProjectReportUpdateQuery extends SqlUpdate {
		protected ProjectReportUpdateQuery(DataSource ds) {
			super(ds, " UPDATE ProjectReportContent                                  "
					+ " SET workStartDate=?, workEndDate=?, assignDate=?, dueDate=?, "
					+ "     title=?, workContent=?, impoInstContent=?, nextPlan=?,   "
					+ "     consultantOpinion=?, requestContent=?, attach=?, state=?,"
					+ "     writerSsn=?, writeDate=?, reviewerSsn=?, revieweDate=?,  "
					+ "     revieweOpinion=?, approverSsn=?, approveDate=?, approveOpinion=?, payYN=?, payAmount=?"
					+ " WHERE  projectCode = ?                                       "
					+ " And    taskFormTypeId = ?                                    "
					+ " And    seq = ?                                               ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));

			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));

			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));

			declareParameter(new SqlParameter(Types.VARCHAR));
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

		protected int update(ProjectReportContent projectReportContent) {
			return this.update(new Object[] { 
					projectReportContent.getWorkStartDate(), projectReportContent.getWorkEndDate(), projectReportContent.getAssignDate(), projectReportContent.getDueDate(), 
					projectReportContent.getTitle(), projectReportContent.getWorkContent(), projectReportContent.getImpoInstContent(), projectReportContent.getNextPlan(),
					projectReportContent.getConsultantOpinion(), projectReportContent.getRequestContent(), projectReportContent.getAttach(),
					projectReportContent.getState(), projectReportContent.getWriterSsn(), projectReportContent.getWriteDate(),
					projectReportContent.getReviewerSsn(), projectReportContent.getRevieweDate(), projectReportContent.getRevieweOpinion(),
					projectReportContent.getApproverSsn(), projectReportContent.getApproveDate(), projectReportContent.getApproveOpinion(),
					projectReportContent.getPayAmount() == null || projectReportContent.getPayAmount().equals("") ? "N" : "Y",
					projectReportContent.getPayAmount() == null ? "1" : projectReportContent.getPayAmount(),
					projectReportContent.getProjectCode(), projectReportContent.getTaskFormTypeId(), projectReportContent.getSeq() });
		}
	}

	protected class ProjectReportInsertQuery extends SqlUpdate {
		protected ProjectReportInsertQuery(DataSource ds) {
			super(ds, "INSERT INTO ProjectReportContent( projectCode, taskFormTypeId, seq, workStartDate, "
					+ "   workEndDate, assignDate, dueDate, title, workContent, impoInstContent, nextPlan, consultantOpinion, "
					+ "   requestContent, attach, state, writerSsn, writeDate, reviewerSsn, revieweDate, revieweOpinion, "
					+ "   approverSsn, approveDate, approveOpinion, isExceed)     " // Job Date: 2007-12-05 Add isExceed
					+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));

			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));

			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));

			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
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

		protected int insert(ProjectReportContent projectReportContent) {
			return this.update(new Object[] { projectReportContent.getProjectCode(), projectReportContent.getTaskFormTypeId(),
					projectReportContent.getSeq(), projectReportContent.getWorkStartDate(), projectReportContent.getWorkEndDate(),
					projectReportContent.getAssignDate(), projectReportContent.getDueDate(), projectReportContent.getTitle(),
					projectReportContent.getWorkContent(), projectReportContent.getImpoInstContent(), projectReportContent.getNextPlan(),
					projectReportContent.getConsultantOpinion(), projectReportContent.getRequestContent(), projectReportContent.getAttach(),
					projectReportContent.getState(), projectReportContent.getWriterSsn(), projectReportContent.getWriteDate(),
					projectReportContent.getReviewerSsn(), projectReportContent.getRevieweDate(), projectReportContent.getRevieweOpinion(),
					projectReportContent.getApproverSsn(), projectReportContent.getApproveDate(), projectReportContent.getApproveOpinion(),
					projectReportContent.getIsExceed() });
		}
	}

	protected class ProjectReportDeleteQuery1 extends SqlUpdate {
		protected ProjectReportDeleteQuery1(DataSource ds) {
			super(ds, " Delete from ProjectReportContent Where projectCode=? And taskFormTypeId=? And seq=? ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected int delete(String projectCode, String taskFormTypeId, String seq) {
			return this.update(new Object[] { projectCode, taskFormTypeId, seq });
		}
	}

	protected class ProjectReportDeleteQuery2 extends SqlUpdate {
		protected ProjectReportDeleteQuery2(DataSource ds) {
			super(ds, " Delete from ProjectReportContent Where projectCode=? And assignDate=? And writerSsn=? ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected int delete(String projectCode, String assignDate, String writerSsn) {
			return this.update(new Object[] { projectCode, assignDate, writerSsn });
		}
	}

	protected class ProjectReportRetrieveQuery1 extends MappingSqlQuery {
		protected ProjectReportRetrieveQuery1(DataSource ds, String query) {
			super(ds, query);
		}

		protected ProjectReportRetrieveQuery1(DataSource ds) {
			super(ds, " select (select p.projectName from project p where p.projectCode=m.projectcode) as projectName,               "
					+ "        (select name from workType w where w.id=m.taskFormTypeId) as taskFormTypeName, m.*                    "
					+ " from ProjectReportContent m where m.projectCode = ? ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			ProjectReportContent projectReport = new ProjectReportContent();
			projectReport.setProjectCode(rs.getString("projectCode"));
			projectReport.setProjectName(rs.getString("projectName"));
			projectReport.setTaskFormTypeId(rs.getString("taskFormTypeId"));
			projectReport.setTaskFormTypeName(rs.getString("taskFormTypeName"));
			projectReport.setSeq(rs.getString("seq"));
			projectReport.setWorkStartDate(rs.getString("workStartDate"));
			projectReport.setWorkEndDate(rs.getString("workEndDate"));
			projectReport.setAssignDate(rs.getString("assignDate"));
			projectReport.setDueDate(rs.getString("dueDate"));
			projectReport.setTitle(rs.getString("title"));
			projectReport.setWorkContent(rs.getString("workContent"));
			projectReport.setImpoInstContent(rs.getString("impoInstContent"));
			projectReport.setNextPlan(rs.getString("nextPlan"));
			projectReport.setConsultantOpinion(rs.getString("consultantOpinion"));
			projectReport.setRequestContent(rs.getString("requestContent"));
			projectReport.setAttach(rs.getString("attach"));
			projectReport.setState(rs.getString("state"));
			projectReport.setWriterSsn(rs.getString("writerSsn"));
			projectReport.setWriteDate(rs.getString("writeDate"));
			projectReport.setReviewerSsn(rs.getString("reviewerSsn"));
			projectReport.setRevieweDate(rs.getString("revieweDate"));
			projectReport.setRevieweOpinion(rs.getString("revieweOpinion"));
			projectReport.setApproverSsn(rs.getString("approverSsn"));
			projectReport.setApproveDate(rs.getString("approveDate"));
			projectReport.setApproveOpinion(rs.getString("approveOpinion"));
			projectReport.setPayYN(rs.getString("payYN"));
			projectReport.setPayAmount(rs.getString("payAmount"));
			return projectReport;
		}
	}

	protected class ProjectReportRetrieveQuery2 extends ProjectReportRetrieveQuery1 {
		protected ProjectReportRetrieveQuery2(DataSource ds) {
			super(ds, " select (select p.projectName from project p where p.projectCode=m.projectcode) as projectName,               "
					+ "        (select name from workType w where w.id=m.taskFormTypeId) as taskFormTypeName, m.*                    "
					+ " from ProjectReportContent m where m.projectCode = ? And taskFormTypeId =? ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

	}

	protected class ProjectReportRetrieveQuery3 extends ProjectReportRetrieveQuery1 {
		protected ProjectReportRetrieveQuery3(DataSource ds) {
			super(ds, " select (select p.projectName from project p where p.projectCode=m.projectcode) as projectName,               "
					+ "        (select name from workType w where w.id=m.taskFormTypeId) as taskFormTypeName, m.*                    "
					+ " from ProjectReportContent m where m.projectCode = ? And taskFormTypeId =? And seq=? ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}
	}

	protected class ProjectReportRetrieveQuery4 extends ProjectReportRetrieveQuery1 {
		protected ProjectReportRetrieveQuery4(DataSource ds) {
			super(ds, " select (select p.projectName from project p where p.projectCode=m.projectcode) as projectName,               "
					+ "        (select name from workType w where w.id=m.taskFormTypeId) as taskFormTypeName, m.*                    "
					+ " from ProjectReportContent m where m.projectCode = ? And assignDate =? ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}
	}

	protected class ProjectReportRetrieveQuery5 extends ProjectReportRetrieveQuery1 {
		protected ProjectReportRetrieveQuery5(DataSource ds) {
			super(ds, " select (select p.projectName from project p where p.projectCode=m.projectcode) as projectName,               "
					+ "        (select name from workType w where w.id=m.taskFormTypeId) as taskFormTypeName, m.*                    "
					+ " from ProjectReportContent m where m.projectCode = ? And assignDate =? And writerSsn=?");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}
	}

	protected void initDao() throws Exception {
		this.projectReportInsertQuery = new ProjectReportInsertQuery(getDataSource());
		this.projectReportUpdateQuery = new ProjectReportUpdateQuery(getDataSource());
		this.projectReportDeleteQuery1 = new ProjectReportDeleteQuery1(getDataSource());
		this.projectReportDeleteQuery2 = new ProjectReportDeleteQuery2(getDataSource());
		this.projectReportRetrieveQuery1 = new ProjectReportRetrieveQuery1(getDataSource());
		this.projectReportRetrieveQuery2 = new ProjectReportRetrieveQuery2(getDataSource());
		this.projectReportRetrieveQuery3 = new ProjectReportRetrieveQuery3(getDataSource());
		this.projectReportRetrieveQuery4 = new ProjectReportRetrieveQuery4(getDataSource());
		this.projectReportRetrieveQuery5 = new ProjectReportRetrieveQuery5(getDataSource());
	}

	@Override
	public void create(ProjectReportContent projectReportContent) throws DataAccessException {
		this.projectReportInsertQuery.insert(projectReportContent);
	}

	@Override
	public void delete1(String projectCode, String taskFormTypeId, String seq) throws DataAccessException {
		this.projectReportDeleteQuery1.delete(projectCode, taskFormTypeId, seq);
	}

	@Override
	public void delete2(String projectCode, String assignDate, String writerSsn) throws DataAccessException {
		this.projectReportDeleteQuery2.delete(projectCode, assignDate, writerSsn);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProjectReportContent> getProjectReportContent1(String projectCode) throws DataAccessException {
		return this.projectReportRetrieveQuery1.execute(new Object[] { projectCode });
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProjectReportContent> getProjectReportContent1(String projectCode, String taskFormTypeId) throws DataAccessException {
		return this.projectReportRetrieveQuery2.execute(new Object[] { projectCode, taskFormTypeId });
	}

	@Override
	public ProjectReportContent getProjectReportContent1(String projectCode, String taskFormTypeId, String seq) throws DataAccessException {
		return (ProjectReportContent) this.projectReportRetrieveQuery3.findObject(new Object[] { projectCode, taskFormTypeId, seq });
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProjectReportContent> getProjectReportContent2(String projectCode, String assignDate) throws DataAccessException {
		return this.projectReportRetrieveQuery4.execute(new Object[] { projectCode, assignDate });
	}

	@Override
	public ProjectReportContent getProjectReportContent2(String projectCode, String assignDate, String writerSsn) throws DataAccessException {
		return (ProjectReportContent) this.projectReportRetrieveQuery5.findObject(new Object[] { projectCode, assignDate, writerSsn });
	}

	@Override
	public void update(ProjectReportContent projectReportContent) throws DataAccessException {
		this.projectReportUpdateQuery.update(projectReportContent);
	}

}
