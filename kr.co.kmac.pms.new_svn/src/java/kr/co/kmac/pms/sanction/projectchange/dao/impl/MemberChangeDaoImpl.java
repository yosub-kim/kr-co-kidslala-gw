package kr.co.kmac.pms.sanction.projectchange.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import javax.sql.DataSource;

import kr.co.kmac.pms.common.util.StringUtil;
import kr.co.kmac.pms.sanction.projectchange.dao.MemberChangeDao;
import kr.co.kmac.pms.sanction.projectchange.data.AddMemberChange;
import kr.co.kmac.pms.sanction.projectchange.data.AddMemberChangeArray;
import kr.co.kmac.pms.sanction.projectchange.data.RunningMemberChange;
import kr.co.kmac.pms.sanction.projectchange.data.RunningMemberChangeArray;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.jdbc.object.SqlUpdate;

/**
 * TODO 클래스 설명
 * 
 * @author CHO DAE HYON
 * @version $Id: MemberChangeDaoImpl.java,v 1.4 2019/02/09 05:47:26 cvs Exp $
 */
public class MemberChangeDaoImpl extends JdbcDaoSupport implements MemberChangeDao {

	private AddMemberInsertQuery addMemberInsertQuery;
	private RunningMemberInsertQuery runningMemberInsertQuery;
	private AddMemberDeleteQuery addMemberDeleteQuery;
	private RunningMemberDeleteQuery runningMemberDeleteQuery;
	private AddMemberSelectQuery addMemberSelectQuery;
	private RunningMemberSelectQuery runningMemberSelectQuery;
	private ProjectMemberMMPlanUPdateQuery projectMemberMMPlanUPdateQuery;

	protected void initDao() throws Exception {

		this.addMemberInsertQuery = new AddMemberInsertQuery(getDataSource());
		this.runningMemberInsertQuery = new RunningMemberInsertQuery(getDataSource());
		this.addMemberDeleteQuery = new AddMemberDeleteQuery(getDataSource());
		this.runningMemberDeleteQuery = new RunningMemberDeleteQuery(getDataSource());
		this.addMemberSelectQuery = new AddMemberSelectQuery(getDataSource());
		this.runningMemberSelectQuery = new RunningMemberSelectQuery(getDataSource());
		this.projectMemberMMPlanUPdateQuery = new ProjectMemberMMPlanUPdateQuery(getDataSource());
	}

	protected class AddMemberInsertQuery extends SqlUpdate {
		protected AddMemberInsertQuery(DataSource ds) {
			super(ds, "	INSERT INTO projectMemberAddChange(projectCode, seq, ssn, name,"
					+ "		role, cost, trainingYn, contributionCost,	"
					+ "		resRate, position,  "
					+ "		startDate, endDate)	"
					+ "	VALUES (?,  ?,  ?,  ?,		?,  ?,  ?,  ?,		?,  ?,		?,  ? )");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.INTEGER));
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

		protected void insert(AddMemberChangeArray addMemberChange) throws DataAccessException {
			if (addMemberChange.getAddMemberSsn() != null) {
				for (int i = 0; i < addMemberChange.getAddMemberSsn().length; i++) {
					update(new Object[] { addMemberChange.getProjectCode(), addMemberChange.getMemberChangeSeq(), addMemberChange.getAddMemberSsn()[i], addMemberChange.getAddMemberName()[i], 
							addMemberChange.getAddMemberRole()[i], addMemberChange.getAddMemberCost()[i].replace(",", "").replace(" ", ""), "Y", "1",
							addMemberChange.getAddMemberResRate()[i], addMemberChange.getAddMemberPosition()[i],
							addMemberChange.getAddMemberStartDate()[i], addMemberChange.getAddMemberEndDate()[i]									
					});
				}
			}
		}
	}

	protected class RunningMemberInsertQuery extends SqlUpdate {
		protected RunningMemberInsertQuery(DataSource ds) {
			super(ds, "	INSERT INTO projectMemberRunningChange(projectCode, seq, ssn, name, role, cost, " 
					+ "										   trainingYn, contributionCost, teachingDay, position, resRate )	"
					+ "	VALUES (?,  ?,  ?,  ?,  ?,  ?,      ?,  ?,  ?,  ?,  ?                       )");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.INTEGER));
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

		protected void insert(RunningMemberChangeArray runningMemberChange) throws DataAccessException {
			if(runningMemberChange.getRunningMemberSsn() != null){
				for (int i = 0; i < runningMemberChange.getRunningMemberSsn().length; i++) {
					update(new Object[] { 
							runningMemberChange.getProjectCode(), 
							runningMemberChange.getMemberChangeSeq(),
							runningMemberChange.getRunningMemberSsn()[i], 
							runningMemberChange.getRunningMemberName()[i],
							runningMemberChange.getRunningMemberRole()[i], 
							runningMemberChange.getRunningMemberCost()[i].replace(",", "").replace(" ", ""),
							
							runningMemberChange.getRunningMemberTrainingYn()[i], 
							runningMemberChange.getRunningMemberContributionCost()[i], 
							runningMemberChange.getRunningMemberTeachingDay()[i], 
							runningMemberChange.getRunningMemberPosition()[i], 
							runningMemberChange.getRunningMemberResRate()[i] 
					});
				}
			}
		}
	}

	protected class AddMemberDeleteQuery extends SqlUpdate {
		protected AddMemberDeleteQuery(DataSource ds) {
			super(ds, "	DELETE FROM projectMemberAddChange WHERE projectCode=? AND seq=? ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.INTEGER));
			compile();
		}

		protected int delete(String projectCode, String memberChangeSeq) throws DataAccessException {
			return update(new Object[] { projectCode, memberChangeSeq });
		}
	}

	protected class RunningMemberDeleteQuery extends SqlUpdate {
		protected RunningMemberDeleteQuery(DataSource ds) {
			super(ds, "	DELETE FROM projectMemberRunningChange WHERE projectCode=? AND seq=? ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.INTEGER));
			compile();
		}

		protected int delete(String projectCode, String memberChangeSeq) throws DataAccessException {
			return update(new Object[] { projectCode, memberChangeSeq });
		}
	}

	protected class AddMemberSelectQuery extends MappingSqlQuery {
		protected AddMemberSelectQuery(DataSource ds) {
			super(ds, "	SELECT * FROM projectMemberAddChange WHERE projectCode=? AND seq=? ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			AddMemberChange addMemberChange = new AddMemberChange();
			addMemberChange.setProjectCode(rs.getString("projectCode"));
			addMemberChange.setMemberChangeSeq(rs.getInt("seq"));
			addMemberChange.setAddMemberSsn(rs.getString("ssn"));
			addMemberChange.setAddMemberName(rs.getString("name"));
			addMemberChange.setAddMemberRole(rs.getString("role"));
			addMemberChange.setAddMemberCost(rs.getString("cost"));
			addMemberChange.setAddMemberTrainingYn(rs.getString("trainingYn"));
			addMemberChange.setAddMemberContributionCost(rs.getString("contributionCost"));
			addMemberChange.setAddMemberResRate(rs.getString("resRate"));
			addMemberChange.setAddMemberPosition(rs.getString("position"));
			addMemberChange.setAddMemberStartDate(rs.getString("startDate"));
			addMemberChange.setAddMemberEndDate(rs.getString("endDate"));
			return addMemberChange;
		}
	}

	protected class RunningMemberSelectQuery extends MappingSqlQuery {
		protected RunningMemberSelectQuery(DataSource ds) {
			super(ds, "	SELECT pm.projectCode, pm.ssn, pm.role, pm.cost, isnull(pmc.trainingYN, 'Y') as trainingYN, pm.contributionCost, pm.teachingDay, pm.position, pm.resRate, pmc.seq, "
					+ " pm.startDate, pm.endDate, dbo.getExpertPoolName(pm.ssn) AS name, '' AS teachingDay FROM projectmember pm "
					+ " LEFT JOIN "
					+ " ( "
					+ " 	SELECT * FROM projectmemberrunningchange WHERE projectcode=? and seq=? "
					+ " )pmc "
					+ " ON pm.projectcode = pmc.projectcode and pm.ssn = pmc.ssn and pm.role = pmc.role "
					+ " WHERE pm.projectcode= ? "
					+ " ORDER BY (case when pm.role='AR' then 1 when pm.role='PM' then 2 when pm.role='PL' then 3 when pm.role='MB' then 4 end) asc ");
			/*super(ds, "	SELECT *, '1' AS seq, dbo.getExpertPoolName(ssn) AS name, '' AS teachingDay FROM projectmember where projectcode= ? "
					+ " order by (case when role='AR' then 1 when role='PM' then 2 when role='PL' then 3 when role='MB' then 4 end) asc" );*/
			declareParameter(new SqlParameter(Types.VARCHAR));
			/*인력변경 품의 시 멤버 미 추가 문제 조치*/
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			RunningMemberChange runningMemberChange = new RunningMemberChange();
			runningMemberChange.setProjectCode(rs.getString("projectCode"));
			runningMemberChange.setMemberChangeSeq(rs.getInt("seq"));
			runningMemberChange.setRunningMemberSsn(rs.getString("ssn"));
			runningMemberChange.setRunningMemberName(rs.getString("name"));
			runningMemberChange.setRunningMemberRole(rs.getString("role"));
			runningMemberChange.setRunningMemberCost(rs.getString("cost"));
			runningMemberChange.setRunningMemberTrainingYn(rs.getString("trainingYn"));
			runningMemberChange.setRunningMemberContributionCost(rs.getString("contributionCost"));
			runningMemberChange.setRunningMemberTeachingDay(rs.getString("teachingDay"));
			runningMemberChange.setRunningMemberPosition(rs.getString("position"));
			runningMemberChange.setRunningMemberResRate(rs.getString("resRate"));
			return runningMemberChange;
		}
	}
	
	protected class ProjectMemberMMPlanUPdateQuery extends SqlUpdate {
		protected ProjectMemberMMPlanUPdateQuery(DataSource ds) {
			super(ds, "	UPDATE ProjectMemberMMPlan SET trainingYN = 'N'	"
					+ "	WHERE projectCode = ? AND year >= ? AND month >=  ? AND ssn in (	"
					+ "		SELECT ssn FROM projectMemberRunningChange 						"
					+ "		WHERE trainingYn = 'N' AND projectCode = ?  AND seq = ? )		");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected int update(RunningMemberChangeArray runningMemberChange, String year, String month) throws DataAccessException {
			return this.update(new Object[] { runningMemberChange.getProjectCode(), year, month, 
											runningMemberChange.getProjectCode(), runningMemberChange.getMemberChangeSeq() });
		}
	}	

	@Override
	public void deleteAddMember(String projectCode, String memberChangeSeq) throws DataAccessException {
		this.addMemberDeleteQuery.delete(projectCode, memberChangeSeq);
	}

	@Override
	public void deleteRunningMember(String projectCode, String memberChangeSeq) throws DataAccessException {
		this.runningMemberDeleteQuery.delete(projectCode, memberChangeSeq);
	}

	@Override
	public void insertAddMember(AddMemberChangeArray addMemberChange) throws DataAccessException {
		this.addMemberInsertQuery.insert(addMemberChange);
	}

	@Override
	public void insertRunningMember(RunningMemberChangeArray runningMemberChange) throws DataAccessException {
		this.runningMemberInsertQuery.insert(runningMemberChange);
	}

	@Override
	public void updateMemberMMPlan(RunningMemberChangeArray runningMemberChange) throws DataAccessException {
		String year = StringUtil.getCurr("yyyy");
		String month = StringUtil.getCurr("MM");
		this.projectMemberMMPlanUPdateQuery.update(runningMemberChange, year, month);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AddMemberChange> selectAddMemberChange(String projectCode, String memberChangeSeq) throws DataAccessException {
		return this.addMemberSelectQuery.execute(new Object[] { projectCode, memberChangeSeq });
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RunningMemberChange> selectRunningMemberChange(String projectCode, String memberChangeSeq) throws DataAccessException {
		return this.runningMemberSelectQuery.execute(new Object[] { projectCode, memberChangeSeq, projectCode });
	}

	@Override
	public void finishMemberChangeDao(RunningMemberChangeArray runningMemberChange, AddMemberChangeArray addMemberChange) throws DataAccessException {
		// TODO
	}

}