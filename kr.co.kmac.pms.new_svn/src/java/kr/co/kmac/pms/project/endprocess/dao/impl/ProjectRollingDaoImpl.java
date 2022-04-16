/*
 * $Id: ProjectRollingDaoImpl.java,v 1.6 2017/10/11 03:49:01 cvs Exp $
 * created by    : CHO DAE HYON
 * creation-date : 2006. 3. 23.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.project.endprocess.dao.impl;

import java.sql.Types;
import java.util.Map;

import javax.sql.DataSource;

import kr.co.kmac.pms.project.endprocess.dao.ProjectRollingDao;
import kr.co.kmac.pms.project.endprocess.form.ProjectRollingForm;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.object.SqlUpdate;

/**
 * TODO 클래스 설명
 * 
 * @author CHO DAE HYON
 * @version $Id: ProjectRollingDaoImpl.java,v 1.6 2017/10/11 03:49:01 cvs Exp $
 */
public class ProjectRollingDaoImpl extends JdbcDaoSupport implements ProjectRollingDao {

	private ProjectRollingInsertQuery1 projectRollingInsertQuery1;
	private ProjectRollingInsertQuery2 projectRollingInsertQuery2;

	// private ProjectRollingInsertQuery3 projectRollingInsertQuery3;

	protected void initDao() throws Exception {
		this.projectRollingInsertQuery1 = new ProjectRollingInsertQuery1(getDataSource());
		this.projectRollingInsertQuery2 = new ProjectRollingInsertQuery2(getDataSource());
		// this.projectRollingInsertQuery3 = new ProjectRollingInsertQuery3(getDataSource());
	}

	public void insertRollingC(ProjectRollingForm projectRollingForm) throws DataAccessException {
		this.projectRollingInsertQuery1.insert(projectRollingForm);

		String ssn[] = projectRollingForm.getSsn();
		String answer8[] = projectRollingForm.getAnswer8Array();
		String answer9[] = projectRollingForm.getAnswer9Array();
		String answer10[] = projectRollingForm.getAnswer10Array();
		String answer11[] = projectRollingForm.getAnswer11Array();
		String answer12[] = projectRollingForm.getAnswer12Array();
		String answer13[] = projectRollingForm.getAnswer13Array();

		ProjectRollingForm projectRollingForm1 = null;

		for (int i = 0; i < ssn.length; i++) {
			projectRollingForm1 = new ProjectRollingForm();

			projectRollingForm1.setProjectCode(projectRollingForm.getProjectCode());
			projectRollingForm1.setGubun(projectRollingForm.getGubun());

			projectRollingForm1.setSsn_(ssn[i]);
			projectRollingForm1.setAnswer8(answer8[i]);
			projectRollingForm1.setAnswer9(answer9[i]);
			projectRollingForm1.setAnswer10(answer10[i]);
			projectRollingForm1.setAnswer11(answer11[i]);
			projectRollingForm1.setAnswer12(answer12[i]);
			projectRollingForm1.setAnswer13(answer13[i]);

			projectRollingForm1.setWriterSsn(projectRollingForm.getWriterSsn());
			projectRollingForm1.setWriteDate(projectRollingForm.getWriteDate());

			this.projectRollingInsertQuery2.insert(projectRollingForm1);
		}

	}

	protected class ProjectRollingInsertQuery1 extends SqlUpdate {
		protected ProjectRollingInsertQuery1(DataSource ds) {
			super(ds, "	INSERT INTO	ProjectRolling (projectCode, gubun, answer1, answer2, answer3, answer4, answer5,	"
					+ "	                            answer6, answer7, answer8, answer9, answer10,	    "
					+ "                             dept, duty, name, comment, writerSsn, writeDate)	"
					+ "	VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )	");
			declareParameter(new SqlParameter(Types.VARCHAR));// projectCode
			declareParameter(new SqlParameter(Types.VARCHAR));// gubun
			declareParameter(new SqlParameter(Types.VARCHAR));// answer1
			declareParameter(new SqlParameter(Types.VARCHAR));// answer2
			declareParameter(new SqlParameter(Types.VARCHAR));// answer3
			declareParameter(new SqlParameter(Types.VARCHAR));// answer4
			declareParameter(new SqlParameter(Types.VARCHAR));// answer5
			declareParameter(new SqlParameter(Types.VARCHAR));// answer6
			declareParameter(new SqlParameter(Types.VARCHAR));// answer7
			declareParameter(new SqlParameter(Types.VARCHAR));// answer8
			declareParameter(new SqlParameter(Types.VARCHAR));// answer9
			declareParameter(new SqlParameter(Types.VARCHAR));// answer10
			declareParameter(new SqlParameter(Types.VARCHAR));// dept
			declareParameter(new SqlParameter(Types.VARCHAR));// duty
			declareParameter(new SqlParameter(Types.VARCHAR));// name
			declareParameter(new SqlParameter(Types.VARCHAR));// comment
			declareParameter(new SqlParameter(Types.VARCHAR));// writerSsn
			declareParameter(new SqlParameter(Types.VARCHAR));// writeDate
			compile();
		}

		protected int insert(ProjectRollingForm projectRollingForm) throws DataAccessException {
			return this.update(new Object[] { projectRollingForm.getProjectCode(), projectRollingForm.getGubun(), projectRollingForm.getAnswer1(),
					projectRollingForm.getAnswer2(), projectRollingForm.getAnswer3(), projectRollingForm.getAnswer4(),
					projectRollingForm.getAnswer5(), projectRollingForm.getAnswer6(),
					projectRollingForm.getAnswer7() == null ? "" : projectRollingForm.getAnswer7(),
					projectRollingForm.getAnswer8() == null ? "" : projectRollingForm.getAnswer8(),
					projectRollingForm.getAnswer9() == null ? "" : projectRollingForm.getAnswer9(),
					projectRollingForm.getAnswer10() == null ? "" : projectRollingForm.getAnswer10(),
					projectRollingForm.getDept() == null ? "" : projectRollingForm.getDept(),
					projectRollingForm.getDuty() == null ? "" : projectRollingForm.getDuty(),
					projectRollingForm.getName() == null ? "" : projectRollingForm.getName(),
					projectRollingForm.getComment() == null ? "" : projectRollingForm.getComment(), projectRollingForm.getWriterSsn(),
					projectRollingForm.getWriteDate()

			});
		}
	}

	protected class ProjectRollingInsertQuery2 extends SqlUpdate {
		protected ProjectRollingInsertQuery2(DataSource ds) {
			super(ds, "	INSERT INTO	ProjectRollingC (projectCode, gubun, ssn,                "
					+ "				answer8, answer9, answer10, answer11, answer12, answer13, writerSsn, writeDate)	"
					+ "	VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?	)	");
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

		protected int insert(ProjectRollingForm projectRollingForm) throws DataAccessException {
			return this.update(new Object[] { projectRollingForm.getProjectCode(), projectRollingForm.getGubun(), projectRollingForm.getSsn_(),
					projectRollingForm.getAnswer8(), projectRollingForm.getAnswer9(), projectRollingForm.getAnswer10(),
					projectRollingForm.getAnswer11(), projectRollingForm.getAnswer12(), projectRollingForm.getAnswer13(),
					projectRollingForm.getWriterSsn(), projectRollingForm.getWriteDate() });
		}
	}

	@Override
	public void insertRollingR(ProjectRollingForm projectRollingForm) throws DataAccessException {
		this.projectRollingInsertQuery1.insert(projectRollingForm);
	}

	@Override
	public void insertRollingE(ProjectRollingForm projectRollingForm) throws DataAccessException {
		this.projectRollingInsertQuery1.insert(projectRollingForm);
	}

	@Override
	public void insertRollingF(ProjectRollingForm projectRollingForm) throws DataAccessException {
		this.projectRollingInsertQuery1.insert(projectRollingForm);
	}

	@Override
	public void insertRollingT(ProjectRollingForm projectRollingForm) throws DataAccessException {
		this.projectRollingInsertQuery1.insert(projectRollingForm);
	}


	@Override
	public Map isFinishedRolling(String projectCode, String seq) throws DataAccessException {
		try {
			return getJdbcTemplate().queryForMap(
					  "				select	count(a.projectCode) sendCnt,  isNull(sum(tmp),0) receiveCnt" 
					+ "				from project a"
					+ "				inner join projectCsrInfo i"
					+ "					on a.projectCode= i.projectCode"
					+ "				left outer join (select projectCode, seq, 1 tmp from projectRolling) D" 
					+ "					on a.projectCode = d.projectCode"
					+ "					and i.seq = d.seq"
					+ "				where 1=1"
					+ "					and a.projectcode = "+projectCode+""	
					+ "				group by a.projectCode");
		} catch (Exception e) {
			return null;
		}
	}
	
	@Override
	public Map isFinishedRolling(String projectCode) throws DataAccessException {
		try {
			return getJdbcTemplate().queryForMap(
					"				select	count(a.projectCode) sendCnt,  isNull(sum(tmp),0) receiveCnt" 
							+ "				from project a"
							+ "				inner join projectCsrInfo i"
							+ "					on a.projectCode= i.projectCode"
							+ "				left outer join (select projectCode, seq, 1 tmp from projectRolling) D" 
							+ "					on a.projectCode = d.projectCode"
							+ "				where 1=1"
							+ "					and a.projectcode = "+projectCode+""	
							+ "				group by a.projectCode");
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public int numOfCustomer(String projectCode) throws DataAccessException {
		try {
			return getJdbcTemplate().queryForInt(
					  "		SELECT COUNT(seq) cnt "
					+ "		FROM projectCsrInfo "
					+ "		WHERE projectcode ="+projectCode+"");
		} catch (Exception e) {
			return 0;
		}
	}
}
