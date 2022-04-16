package kr.co.kmac.pms.project.summary.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import kr.co.kmac.pms.project.summary.dao.ProjectSummaryDao;
import kr.co.kmac.pms.project.summary.data.ProjectSummaryData;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.object.MappingSqlQuery;

public class ProjectSummaryDaoImpl extends JdbcDaoSupport implements ProjectSummaryDao {

	private ProjectMainDataRetrieveQuery projectMainDataRetrieveQuery;

	protected class ProjectMainDataRetrieveQuery extends MappingSqlQuery {
		protected ProjectMainDataRetrieveQuery(DataSource ds, String query) {
			super(ds, query);
		}

		protected ProjectMainDataRetrieveQuery(DataSource ds) {
			super(ds, " SELECT	projectCode, projectName, runningDivCode, customerName, customerContPName, processTypeCode, "
					+ "         ltrim((select name as name from smGroup where id = runningDivCode)) runningDivName, "
					+ " 		industryTypeCode, realStartDate, realEndDate, projectState, businessTypeCode,			"
					+ "			projectDetailCode, endBackground, endResult, endReview, endSuggestion, endTaskState, "
					+ "			(SELECT pm.ssn FROM ProjectMember pm									"
					+ "          WHERE pm.role='PM' AND pm.trainingYN='Y' AND pm.projectCode = p.projectCode) pmName, "
					+ "			(SELECT pm.ssn FROM ProjectMember pm									"
					+ "			 WHERE pm.role='PL' AND pm.trainingYN='Y' AND pm.projectCode = p.projectCode) plName  "
					+ "	FROM Project p																	" + " WHERE p.projectCode = ?		                                                    ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			ProjectSummaryData projectSummaryData = new ProjectSummaryData();
			projectSummaryData.setProjectCode(rs.getString("projectCode"));
			projectSummaryData.setProjectName(rs.getString("projectName"));
			projectSummaryData.setRunningDivCode(rs.getString("runningDivCode"));
			projectSummaryData.setRunningDivName(rs.getString("runningDivName"));
			projectSummaryData.setProcessTypeCode(rs.getString("processTypeCode"));
			projectSummaryData.setCustomerName(rs.getString("customerName"));
			projectSummaryData.setCustomerContPName(rs.getString("customerContPName"));
			projectSummaryData.setIndustryTypeCode(rs.getString("industryTypeCode"));
			projectSummaryData.setRealStartDate(rs.getString("realStartDate"));
			projectSummaryData.setRealEndDate(rs.getString("realEndDate"));
			projectSummaryData.setProjectState(rs.getString("projectState"));
			projectSummaryData.setBusinessTypeCode(rs.getString("businessTypeCode"));
			projectSummaryData.setProjectDetailCode(rs.getString("projectDetailCode"));
			projectSummaryData.setEndBackground(rs.getString("endBackground"));
			projectSummaryData.setEndResult(rs.getString("endResult"));
			projectSummaryData.setEndReview(rs.getString("endReview"));
			projectSummaryData.setEndSuggestion(rs.getString("endSuggestion"));
			projectSummaryData.setPmName(rs.getString("pmName"));
			projectSummaryData.setPlName(rs.getString("plName"));
			projectSummaryData.setEndTaskState(rs.getString("endTaskState"));
			return projectSummaryData;
		}
	}

	protected void initDao() throws Exception {
		projectMainDataRetrieveQuery = new ProjectMainDataRetrieveQuery(getDataSource());
	}

	public ProjectSummaryData retrieve(String projectCode) throws DataAccessException {
		ProjectSummaryData projectSummaryData = (ProjectSummaryData) this.projectMainDataRetrieveQuery.findObject(new Object[] { projectCode });
		String query = " SELECT e.name FROM ProjectMember p, ExpertPool e "
				+ " WHERE p.ssn = e.ssn AND p.role = 'MB' AND trainingYN = 'Y' AND p.projectCode = '" + projectCode + "' ";
		List memberList = getJdbcTemplate().queryForList(query);

		String memberName = "";
		String memberNameGubun = ", ";
		for (Iterator iter = memberList.iterator(); iter.hasNext();) {
			memberName = memberName + (String) ((Map) iter.next()).get("name") + memberNameGubun;
		}
		if (memberName.length() != 0 && memberName != "") {
			int gubunEmptyInt = 2;
			int memberNameGubunEmpty = memberName.length() - gubunEmptyInt;
			memberName = memberName.substring(0, memberNameGubunEmpty);
		}
		projectSummaryData.setMemberName(memberName);
		return projectSummaryData;
	}
}