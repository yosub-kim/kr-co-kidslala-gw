/*
 * $Id: ProjectEndingDaoImpl.java,v 1.4 2010/06/16 14:29:10 cvs2 Exp $
 * created by    : CHO DAE HYON
 * creation-date : 2006. 3. 28.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.project.endprocess.dao.impl;

import java.sql.Types;

import javax.sql.DataSource;

import kr.co.kmac.pms.project.endprocess.dao.ProjectEndingDao;
import kr.co.kmac.pms.project.endprocess.form.ProjectEndingForm;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.object.SqlUpdate;

/**
 * TODO 클래스 설명
 * 
 * @author CHO DAE HYON
 * @version $Id: ProjectEndingDaoImpl.java,v 1.4 2010/06/16 14:29:10 cvs2 Exp $
 */
public class ProjectEndingDaoImpl extends JdbcDaoSupport implements ProjectEndingDao {

	private ProjectEndingInsertQuery1 projectEndingInsertQuery1;
	private ProjectEndingInsertQuery2 projectEndingInsertQuery2;
	private ProjectEndingInsertQuery3 projectEndingInsertQuery3;
	private ProjectEndingInsertQuery4 projectEndingInsertQuery4;
	private RndCSMailSendInfo rndCSMailSendInfo;
	private ConsultingMailSendInfo consultingMailSendInfo;

	protected void initDao() throws Exception {
		this.projectEndingInsertQuery1 = new ProjectEndingInsertQuery1(getDataSource());
		this.projectEndingInsertQuery2 = new ProjectEndingInsertQuery2(getDataSource());
		this.projectEndingInsertQuery3 = new ProjectEndingInsertQuery3(getDataSource());
		this.projectEndingInsertQuery4 = new ProjectEndingInsertQuery4(getDataSource());
		this.rndCSMailSendInfo = new RndCSMailSendInfo(getDataSource());
		this.consultingMailSendInfo = new ConsultingMailSendInfo(getDataSource());
	}

	@Override
	public void sendInfoResearchCustomerSatisfactionTask(String projectCode) throws DataAccessException {
		this.rndCSMailSendInfo.insert(projectCode);
	}

	protected class RndCSMailSendInfo extends SqlUpdate {
		protected RndCSMailSendInfo(DataSource ds) {
			super(ds, "	INSERT INTO ProjectIcsiManage(projectCode,SurveySendDate) VALUES(?, getDate())");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected int insert(String projectCode) throws DataAccessException {
			return this.update(new Object[] { projectCode });
		}
	}

	@Override
	public void sendInfoConsultingCustomerSatisfactionTask(String projectCode) throws DataAccessException {
		this.consultingMailSendInfo.insert(projectCode);
	}

	protected class ConsultingMailSendInfo extends SqlUpdate {
		protected ConsultingMailSendInfo(DataSource ds) {
			super(ds, "	INSERT INTO ProjectIcsiManage(projectCode) VALUES(?)");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected int insert(String projectCode) throws DataAccessException {
			return this.update(new Object[] { projectCode });
		}
	}

	@Override
	public void insertRateC(ProjectEndingForm projectEndingForm) throws DataAccessException {

		String projectCode = projectEndingForm.getProjectCode();
		String writerSsn = projectEndingForm.getWriterSsn();
		String writeDate = projectEndingForm.getWriteDate();
		String ssn[] = projectEndingForm.getSsn();
		String answer1[] = projectEndingForm.getAnswer1Array();
		String answer2[] = projectEndingForm.getAnswer2Array();
		String answer3[] = projectEndingForm.getAnswer3Array();
		String answer4[] = projectEndingForm.getAnswer4Array();
		String answer5[] = projectEndingForm.getAnswer5Array();
		String answer6[] = projectEndingForm.getAnswer6Array();
		String answer7[] = projectEndingForm.getAnswer7Array();
		String answer8[] = projectEndingForm.getAnswer8Array();
		String answer9[] = projectEndingForm.getAnswer9Array();
		String answer10[] = projectEndingForm.getAnswer10Array();
		String comment[] = projectEndingForm.getCommentArray();

		ProjectEndingForm projectEndingForm1 = null;
		if (projectEndingForm.getSsn() != null) {
			for (int i = 0; i < ssn.length; i++) {
				projectEndingForm1 = new ProjectEndingForm();
				projectEndingForm1.setProjectCode(projectCode);
				projectEndingForm1.setWriterSsn(writerSsn);
				projectEndingForm1.setWriteDate(writeDate);

				projectEndingForm1.setSsn_(ssn[i]);
				projectEndingForm1.setAnswer1(answer1[i]);
				projectEndingForm1.setAnswer2(answer2[i]);
				projectEndingForm1.setAnswer3(answer3[i]);
				projectEndingForm1.setAnswer4(answer4[i]);
				projectEndingForm1.setAnswer5(answer5[i]);
				projectEndingForm1.setAnswer6(answer6[i]);
				projectEndingForm1.setAnswer7(answer7[i]);
				projectEndingForm1.setAnswer8(answer8[i]);
				projectEndingForm1.setAnswer9(answer9[i]);
				projectEndingForm1.setAnswer10(answer10[i]);
				projectEndingForm1.setComment(comment[i]);
				this.projectEndingInsertQuery1.insert(projectEndingForm1);
			}
		}
	}

	protected class ProjectEndingInsertQuery1 extends SqlUpdate {
		protected ProjectEndingInsertQuery1(DataSource ds) {
			super(ds, "	INSERT INTO projectRateC (projectCode, ssn,	answer1, answer2, answer3, answer4, answer5,	"
					+ "				answer6, answer7, answer8, answer9, answer10, comment, writerSsn, writeDate	)	"
					+ "	VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?	 )	");
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

		protected int insert(ProjectEndingForm projectEndingForm1) throws DataAccessException {
			return this.update(new Object[] { projectEndingForm1.getProjectCode(), projectEndingForm1.getSsn_(), projectEndingForm1.getAnswer1(),
					projectEndingForm1.getAnswer2(), projectEndingForm1.getAnswer3(), projectEndingForm1.getAnswer4(),
					projectEndingForm1.getAnswer5(), projectEndingForm1.getAnswer6(), projectEndingForm1.getAnswer7(),
					projectEndingForm1.getAnswer8(), projectEndingForm1.getAnswer9(), projectEndingForm1.getAnswer10(),
					projectEndingForm1.getComment(), projectEndingForm1.getWriterSsn(), projectEndingForm1.getWriteDate() });
		}
	}

	@Override
	public void insertRateP(ProjectEndingForm projectEndingForm) throws DataAccessException {

		String projectCode = projectEndingForm.getProjectCode();
		String writerSsn = projectEndingForm.getWriterSsn();
		String writeDate = projectEndingForm.getWriteDate();
		String ssn[] = projectEndingForm.getSsn();
		String answer1[] = projectEndingForm.getAnswer1Array();
		String answer2[] = projectEndingForm.getAnswer2Array();
		String answer3[] = projectEndingForm.getAnswer3Array();
		String answer4[] = projectEndingForm.getAnswer4Array();
		String answer5[] = projectEndingForm.getAnswer5Array();
		String answer6[] = projectEndingForm.getAnswer6Array();
		String answer7[] = projectEndingForm.getAnswer7Array();
		String answer8[] = projectEndingForm.getAnswer8Array();
		String answer9[] = projectEndingForm.getAnswer9Array();
		String answer10[] = projectEndingForm.getAnswer10Array();
		String comment[] = projectEndingForm.getCommentArray();

		ProjectEndingForm projectEndingForm2 = null;
		for (int i = 0; i < ssn.length; i++) {
			projectEndingForm2 = new ProjectEndingForm();
			projectEndingForm2.setProjectCode(projectCode);
			projectEndingForm2.setWriterSsn(writerSsn);
			projectEndingForm2.setWriteDate(writeDate);

			projectEndingForm2.setSsn_(ssn[i]);
			projectEndingForm2.setAnswer1(answer1[i]);
			projectEndingForm2.setAnswer2(answer2[i]);
			projectEndingForm2.setAnswer3(answer3[i]);
			projectEndingForm2.setAnswer4(answer4[i]);
			projectEndingForm2.setAnswer5(answer5[i]);
			projectEndingForm2.setAnswer6(answer6[i]);
			projectEndingForm2.setAnswer7(answer7[i]);
			projectEndingForm2.setAnswer8(answer8[i]);
			projectEndingForm2.setAnswer9(answer9[i]);
			projectEndingForm2.setAnswer10(answer10[i]);
			projectEndingForm2.setComment(comment[i]);
			this.projectEndingInsertQuery2.insert(projectEndingForm2);

		}

	}

	protected class ProjectEndingInsertQuery2 extends SqlUpdate {
		protected ProjectEndingInsertQuery2(DataSource ds) {
			super(ds, "	INSERT INTO	projectRateP ( projectCode, ssn, answer1, answer2, answer3, answer4, answer5, answer6, "
					+ "                            answer7, answer8, answer9, answer10,comment, writerSsn, writeDate )	"
					+ "	VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )	");
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

		protected int insert(ProjectEndingForm projectEndingForm2) throws DataAccessException {
			return this.update(new Object[] { projectEndingForm2.getProjectCode(), projectEndingForm2.getSsn_(), projectEndingForm2.getAnswer1(),
					projectEndingForm2.getAnswer2(), projectEndingForm2.getAnswer3(), projectEndingForm2.getAnswer4(),
					projectEndingForm2.getAnswer5(), projectEndingForm2.getAnswer6(), projectEndingForm2.getAnswer7(),
					projectEndingForm2.getAnswer8(), projectEndingForm2.getAnswer9(), projectEndingForm2.getAnswer10(),
					projectEndingForm2.getComment(), projectEndingForm2.getWriterSsn(), projectEndingForm2.getWriteDate() });
		}
	}

	@Override
	public void insertRateE(ProjectEndingForm projectEndingForm) throws DataAccessException {

		String projectCode = projectEndingForm.getProjectCode();
		String writerSsn = projectEndingForm.getWriterSsn();
		String writeDate = projectEndingForm.getWriteDate();
		String ssn[] = projectEndingForm.getSsn();
		String subject[] = projectEndingForm.getSubjectArray();
		String answer1[] = projectEndingForm.getAnswer1Array();
		String answer2[] = projectEndingForm.getAnswer2Array();
		String answer3[] = projectEndingForm.getAnswer3Array();
		String answer4[] = projectEndingForm.getAnswer4Array();

		ProjectEndingForm projectEndingForm3 = null;
		if (ssn != null) {
			for (int i = 0; i < ssn.length; i++) {
				projectEndingForm3 = new ProjectEndingForm();
				projectEndingForm3.setProjectCode(projectCode);
				projectEndingForm3.setWriterSsn(writerSsn);
				projectEndingForm3.setWriteDate(writeDate);

				projectEndingForm3.setSsn_(ssn[i]);
				projectEndingForm3.setSubject(subject[i]);
				projectEndingForm3.setAnswer1(answer1[i]);
				projectEndingForm3.setAnswer2(answer2[i]);
				projectEndingForm3.setAnswer3(answer3[i]);
				projectEndingForm3.setAnswer4(answer4[i]);

				this.projectEndingInsertQuery3.insert(projectEndingForm3);

			}
		}

	}

	protected class ProjectEndingInsertQuery3 extends SqlUpdate {
		protected ProjectEndingInsertQuery3(DataSource ds) {
			super(ds, "	INSERT INTO	projectRateE ( projectCode, ssn, subject, answer1, answer2, answer3, answer4, writerSsn, writeDate )	"
					+ "	VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ? )	");
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

		protected int insert(ProjectEndingForm projectEndingForm3) throws DataAccessException {
			return this.update(new Object[] { projectEndingForm3.getProjectCode(), projectEndingForm3.getSsn_(), projectEndingForm3.getSubject(),
					projectEndingForm3.getAnswer1(), projectEndingForm3.getAnswer2(), projectEndingForm3.getAnswer3(),
					projectEndingForm3.getAnswer4(), projectEndingForm3.getWriterSsn(), projectEndingForm3.getWriteDate() });
		}
	}

	@Override
	public void insertEnding(ProjectEndingForm projectEndingForm) throws DataAccessException {
		this.projectEndingInsertQuery4.insert(projectEndingForm);
	}

	protected class ProjectEndingInsertQuery4 extends SqlUpdate {
		protected ProjectEndingInsertQuery4(DataSource ds) {
			super(ds, "	UPDATE project SET endGubun = ?, endReason = ?, endBackground=?, endResult=?, endReview=?, "
					+ "                    endSuggestion=?, endRate = ?, groupComment=?, cfoComment=?, cboComment=?, endTaskState=?"
					+ "	 WHERE 	projectCode = ?										                ");
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

		protected int insert(ProjectEndingForm projectEndingForm4) throws DataAccessException {
			return this.update(new Object[] { projectEndingForm4.getEndGubun(), projectEndingForm4.getEndReason(),
					projectEndingForm4.getEndBackground(), projectEndingForm4.getEndResult(), projectEndingForm4.getEndReview(),
					projectEndingForm4.getEndSuggestion(), projectEndingForm4.getEndRate(), projectEndingForm4.getGroupComment(),
					projectEndingForm4.getCfoComment(), projectEndingForm4.getCboComment(), projectEndingForm4.getEndTaskState(),
					projectEndingForm4.getProjectCode() });
		}
	}

	@Override
	public int updateProjectState(String projectCode) throws DataAccessException {
		String query = " Update project set projectState = '6' where projectCode = '" + projectCode + "'";
		return getJdbcTemplate().update(query);
	}
}
