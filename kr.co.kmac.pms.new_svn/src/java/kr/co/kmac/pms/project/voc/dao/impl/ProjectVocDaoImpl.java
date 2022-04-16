package kr.co.kmac.pms.project.voc.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import javax.sql.DataSource;

import kr.co.kmac.pms.common.util.DateTime;
import kr.co.kmac.pms.common.util.DateUtil;
import kr.co.kmac.pms.common.util.StringUtil;
import kr.co.kmac.pms.project.voc.dao.ProjectVocDao;
import kr.co.kmac.pms.project.voc.data.ProjectVocInfoData;
import kr.co.kmac.pms.project.voc.data.ProjectVocSendingInfoData;
import kr.co.kmac.pms.project.master.data.ProjectSimpleInfo;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.object.BatchSqlUpdate;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.jdbc.object.SqlUpdate;

/**
 * @author hyunseong
 * @jobDate &2008. 6. 17.
 * @description :
 */
public class ProjectVocDaoImpl extends JdbcDaoSupport implements ProjectVocDao {
	private InsertProjectVoc insertProjectVoc;
	private DeleteProjectVoc deleteProjectVoc;
	private UpdateProjectVoc updateProjectVoc;
	private SelectProjectVoc selectProjectVoc;
	private SelectSendingProjectVoc selectSendingProjectVoc;
	private SelectSendingProjectVocForSend selectSendingProjectVocForSend;

	protected void initDao() throws Exception {
		this.insertProjectVoc = new InsertProjectVoc(getDataSource());
		this.deleteProjectVoc = new DeleteProjectVoc(getDataSource());
		this.selectProjectVoc = new SelectProjectVoc(getDataSource());
		this.updateProjectVoc = new UpdateProjectVoc(getDataSource());
		this.selectSendingProjectVoc = new SelectSendingProjectVoc(getDataSource());
		this.selectSendingProjectVocForSend = new SelectSendingProjectVocForSend(getDataSource());
	}

	protected class SelectProjectVoc extends MappingSqlQuery {
		protected SelectProjectVoc(DataSource ds) {
			super(ds, "	SELECT ROW_NUMBER() OVER(ORDER BY requestDate) AS rownum, projectCode, requestDate, "
					+ " 	   sendDate, receiveDate, responseDate, vocType, receiveName, receiveEmail "
					+ "   FROM ProjectVocInfo WHERE projectCode = ? ORDER BY requestDate ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			ProjectVocInfoData projectVocInfoData = new ProjectVocInfoData();
			projectVocInfoData.setRownum(rs.getString("rownum"));
			projectVocInfoData.setProjectCode(rs.getString("projectCode"));
			projectVocInfoData.setRequestDate(rs.getString("requestDate"));
			projectVocInfoData.setSendDate(rs.getString("sendDate"));
			projectVocInfoData.setReceiveDate(rs.getString("receiveDate"));
			projectVocInfoData.setResponseDate(rs.getString("responseDate"));
			projectVocInfoData.setVocType(rs.getString("vocType"));
			projectVocInfoData.setReceiveName(rs.getString("receiveName"));
			projectVocInfoData.setReceiveEmail(rs.getString("receiveEmail"));
			return projectVocInfoData;
		}
	}

	protected class UpdateProjectVoc extends SqlUpdate {
		protected UpdateProjectVoc(DataSource ds) {
			super(ds, "	update ProjectVocInfo set sendDate=?, receiveName=?, receiveEmail=? where projectCode=? and requestDate=? ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected int update(String sendDate, String receiveName, String receiveEmail, String projectCode, String requestDate) {
			return this.update(new Object[] { sendDate, receiveName, receiveEmail, projectCode, requestDate });
		}
	}

	protected class SelectSendingProjectVoc extends MappingSqlQuery {
		protected SelectSendingProjectVoc(DataSource ds) {
			super(ds, " SELECT	ROW_NUMBER() OVER(ORDER BY requestDate, p.runningDeptCode) AS rownum	" + "			,v.projectCode, p.projectName	"
					+ "			,(select description from smGroup where id=p.runningDeptCode) AS runningDivName	"
					+ "			,v.requestDate, v.SendDate, v.receiveDate, v.responseDate	" + "			,v.receiveName, v.receiveEmail			"
					+ "   FROM ProjectVocInfo v, project p				" + "	 WHERE v.projectCode = p.projectCode			"
					+ "	   AND v.requestDate like ? " + "	 ORDER BY requestDate, p.runningDeptCode ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			ProjectVocSendingInfoData projectVocInfoData = new ProjectVocSendingInfoData();
			projectVocInfoData.setRownum(rs.getString("rownum"));
			projectVocInfoData.setProjectCode(rs.getString("projectCode"));
			projectVocInfoData.setProjectName(rs.getString("projectName"));
			projectVocInfoData.setRunningDivName(rs.getString("runningDivName"));
			projectVocInfoData.setRequestDate(rs.getString("requestDate"));
			projectVocInfoData.setSendDate(rs.getString("sendDate"));
			projectVocInfoData.setReceiveDate(rs.getString("receiveDate"));
			projectVocInfoData.setResponseDate(rs.getString("responseDate"));
			projectVocInfoData.setReceiveName(rs.getString("receiveName"));
			projectVocInfoData.setReceiveEmail(rs.getString("receiveEmail"));
			return projectVocInfoData;
		}
	}

	protected class SelectSendingProjectVocForSend extends MappingSqlQuery {
		protected SelectSendingProjectVocForSend(DataSource ds) {
			super(ds, " SELECT	ROW_NUMBER() OVER(ORDER BY requestDate, p.runningDeptCode) AS rownum	"
					+ "			,v.projectCode, p.projectName	"
					+ "			,(select description from smGroup where id=p.runningDeptCode) AS runningDivName	"
					+ "			,v.requestDate, v.SendDate, v.receiveDate, v.responseDate	" 
					+ "			,v.receiveName, v.receiveEmail			"
					+ "   FROM ProjectVocInfo v, project p				" 
					+ "	 WHERE v.projectCode = p.projectCode			"
					+ "	   AND v.requestDate = ?	                    " 
					+ "	   AND v.sendDate is NULL	                    " 
					+ "	 ORDER BY requestDate, p.runningDeptCode         ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			ProjectVocSendingInfoData projectVocInfoData = new ProjectVocSendingInfoData();
			projectVocInfoData.setRownum(rs.getString("rownum"));
			projectVocInfoData.setProjectCode(rs.getString("projectCode"));
			projectVocInfoData.setProjectName(rs.getString("projectName"));
			projectVocInfoData.setRunningDivName(rs.getString("runningDivName"));
			projectVocInfoData.setRequestDate(rs.getString("requestDate"));
			projectVocInfoData.setSendDate(rs.getString("sendDate"));
			projectVocInfoData.setReceiveDate(rs.getString("receiveDate"));
			projectVocInfoData.setResponseDate(rs.getString("responseDate"));
			projectVocInfoData.setReceiveName(rs.getString("receiveName"));
			projectVocInfoData.setReceiveEmail(rs.getString("receiveEmail"));
			return projectVocInfoData;
		}
	}

	protected class InsertProjectVoc extends BatchSqlUpdate {
		protected InsertProjectVoc(DataSource ds) {
			super(ds, " INSERT INTO ProjectVocInfo (projectCode, requestDate, vocType) " + "                      VALUES (?, ?, ?)");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected void insert(String projectCode, String[] requestDate, String[] vocType) {
			String today = StringUtil.replace(DateTime.getDateString(), "-", "");
			String historyDate = "";
			for (int i = 0; i < requestDate.length; i++) {
				historyDate = StringUtil.replace(requestDate[i], "-", "");
				if ( today.compareTo(historyDate) < 0 ) {
					this.update(new Object[] { projectCode, historyDate, vocType[i] });
				}
				//this.update(new Object[] { projectCode, StringUtil.replace(requestDate[i], "-", ""), vocType[i] });
			}
			this.flush();
		}
	}

	protected class DeleteProjectVoc extends SqlUpdate {
		protected DeleteProjectVoc(DataSource ds) {
			//super(ds, " DELETE FROM ProjectVocInfo WHERE projectCode = ? ");
			// Job Date: 2011-06-02		Author: yhyim	Description: 오늘 날짜 이전에 발송한 VOC 발송 내역이 삭제되지 않도록 하기 위해 기능 변경 
			super(ds, " DELETE FROM ProjectVocInfo WHERE requestDate > convert(varchar,getDate(),112) AND projectCode = ? ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected int delete(String projectCode) {
			return this.update(new Object[] { projectCode });
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<ProjectVocInfoData> getProjectVoc(String projectCode) throws DataAccessException {
		return this.selectProjectVoc.execute(new Object[] { projectCode });
	}

	@Override
	public List<ProjectVocSendingInfoData> getSendingProjectVoc(String year, String month) throws DataAccessException {
		return this.selectSendingProjectVoc.execute(new Object[] { year + month + "__" });
	}

	@Override
	public List<ProjectVocSendingInfoData> getSendingProjectVoc(String year, String month, String day) throws DataAccessException {
		return this.selectSendingProjectVocForSend.execute(new Object[] { year + month + day });
	}

	@Override
	public void setProjectVoc(String projectCode, String[] requestDate, String[] vocType) {
		this.insertProjectVoc.insert(projectCode, requestDate, vocType);
	}

	@Override
	public void deleteProjectVoc(String projectCode) {
		this.deleteProjectVoc.delete(projectCode);
	}

	@Override
	public void updateProjectVoc(ProjectVocInfoData projectVocInfoData, ProjectSimpleInfo projectSimpleInfo) throws DataAccessException {
		this.updateProjectVoc.update(new Object[] { DateUtil.getCurrentYyyymmdd(), projectSimpleInfo.getCustomerWorkerName(),
				projectSimpleInfo.getCustomerEmail(), projectVocInfoData.getProjectCode(), projectVocInfoData.getRequestDate() });
	}

}