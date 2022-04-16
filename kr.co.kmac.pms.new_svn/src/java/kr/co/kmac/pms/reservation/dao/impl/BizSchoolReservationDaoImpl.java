package kr.co.kmac.pms.reservation.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import javax.sql.DataSource;

import kr.co.kmac.pms.reservation.dao.BizSchoolReservationDao;
import kr.co.kmac.pms.reservation.data.BizSchoolReservation;
import kr.co.kmac.pms.reservation.form.BizSchoolReservationForm;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.jdbc.object.SqlUpdate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

public class BizSchoolReservationDaoImpl extends JdbcDaoSupport implements BizSchoolReservationDao {
	private BizSchoolReservationInsert bizSchoolReservationInsert;
	private BizSchoolReservationStateInsert bizSchoolReservationStateInsert;
	private BizSchoolReservationUpdate bizSchoolReservationUpdate;
	private BizSchoolReservationStateUpdate bizSchoolReservationStateUpdate;
	private BizSchoolReservationDelete bizSchoolReservationDelete;
	private BizSchoolReservationDeleteInsert bizSchoolReservationDeleteInsert;
	private BizSchoolReservationRetrieveQuery1 bizSchoolReservationRetrieveQuery1;
	private BizSchoolReservationRetrieveQuery2 bizSchoolReservationRetrieveQuery2;
	private BizSchoolReservationRetrieveQuery3 bizSchoolReservationRetrieveQuery3;
	private BizSchoolReservationRetrieveQuery4 bizSchoolReservationRetrieveQuery4;

	protected void initDao() {
		this.bizSchoolReservationInsert = new BizSchoolReservationInsert(getDataSource());
		this.bizSchoolReservationStateInsert = new BizSchoolReservationStateInsert(getDataSource());
		this.bizSchoolReservationUpdate = new BizSchoolReservationUpdate(getDataSource());
		this.bizSchoolReservationStateUpdate = new BizSchoolReservationStateUpdate(getDataSource());
		this.bizSchoolReservationDelete = new BizSchoolReservationDelete(getDataSource());
		this.bizSchoolReservationDeleteInsert = new BizSchoolReservationDeleteInsert(getDataSource());
		this.bizSchoolReservationRetrieveQuery1 = new BizSchoolReservationRetrieveQuery1(getDataSource());
		this.bizSchoolReservationRetrieveQuery2 = new BizSchoolReservationRetrieveQuery2(getDataSource());
		this.bizSchoolReservationRetrieveQuery3 = new BizSchoolReservationRetrieveQuery3(getDataSource());
		this.bizSchoolReservationRetrieveQuery4 = new BizSchoolReservationRetrieveQuery4(getDataSource());
	}

	protected class BizSchoolReservationInsert extends SqlUpdate {
		public BizSchoolReservationInsert(DataSource ds) {
			super(ds, " insert into schedule.dbo.biz_schedule (                                                                      "
					+ "		F_userid,			F_yyyy,				F_mm,				F_dd,			F_hh,			F_ss,		F_ehh,		F_ess, 		"
					+ "		f_juya, 			F_jobtype,			F_JobContent,		F_Company,		F_Customer,		F_Place,	F_Jido, 	F_Bigo, 	F_writer,	"
					+ "		start_time1,		end_time1,			start_time2,		end_time2,		start_time3,	end_time3,	company,	dept,		"
					+ "		instructor1,		instructor2,		instructor3,		instructor1_diff,													"
					+ "		instructor2_diff,	instructor3_diff,	F_Customer_tel,		desk_arrange,					course_ready,	regdate) 			"
					+ " values (	?, ?, ?, ?,    ?, ?, ?, ?,          ?, ?, ?, ?,    ?, ?, ?, ?, ?,   "
					+ " 			?, ?, ?, ?,    ?, ?, ?, ?,          ?, ?, ?, ?,    ?, ?, ?, ?,    ?, getDate()    ) ");
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

			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));

			declareParameter(new SqlParameter(Types.VARCHAR));
			setReturnGeneratedKeys(true);
			compile();
		}

		public int insert(BizSchoolReservationForm bizSchoolReservationForm) {
			KeyHolder keyHolder = new GeneratedKeyHolder();
			update(new Object[] {
					bizSchoolReservationForm.getfUserid(),
					bizSchoolReservationForm.getfYyyy(),
					bizSchoolReservationForm.getfMm(),
					bizSchoolReservationForm.getfDd(),
					bizSchoolReservationForm.getfHh(),
					bizSchoolReservationForm.getfSs(),
					bizSchoolReservationForm.getfEhh(),
					bizSchoolReservationForm.getfEss(),

					bizSchoolReservationForm.getfJuya(),
					bizSchoolReservationForm.getfJobtype(),
					bizSchoolReservationForm.getfJobContent(),
					bizSchoolReservationForm.getfCompany(),
					bizSchoolReservationForm.getfCustomer(),
					bizSchoolReservationForm.getfPlace(),
					bizSchoolReservationForm.getfJido(),
					bizSchoolReservationForm.getfBigo(),
					bizSchoolReservationForm.getfWriter(),

					bizSchoolReservationForm.getStartTime1_1() + bizSchoolReservationForm.getStartTime1_2(),
					bizSchoolReservationForm.getEndTime1_1() + bizSchoolReservationForm.getEndTime1_2(),
					bizSchoolReservationForm.getStartTime2_1() + bizSchoolReservationForm.getStartTime2_2(),
					bizSchoolReservationForm.getEndTime2_1() + bizSchoolReservationForm.getEndTime2_2(),
					bizSchoolReservationForm.getStartTime3_1() + bizSchoolReservationForm.getStartTime3_2(),
					bizSchoolReservationForm.getEndTime3_1() + bizSchoolReservationForm.getEndTime3_2(),
					bizSchoolReservationForm.getCompany(),
					bizSchoolReservationForm.getCompany().equalsIgnoreCase("kmac") ? bizSchoolReservationForm.getDept_sel()
							: bizSchoolReservationForm.getDept_txt(),

					bizSchoolReservationForm.getInstructor1(), bizSchoolReservationForm.getInstructor2(), bizSchoolReservationForm.getInstructor3(),
					bizSchoolReservationForm.getInstructor1Diff(),

					bizSchoolReservationForm.getInstructor2Diff(), bizSchoolReservationForm.getInstructor3Diff(),
					bizSchoolReservationForm.getfCustomerTel(), bizSchoolReservationForm.getDeskArrange(),

					bizSchoolReservationForm.getCourseReady() }, keyHolder);
			return keyHolder.getKey().intValue();
		}
	}

	protected class BizSchoolReservationStateInsert extends SqlUpdate {
		public BizSchoolReservationStateInsert(DataSource ds) {
			super(ds, " insert into schedule.dbo.biz_fontcheck ( f_id, fontCheck ) values (	?, ?) ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		public int insert(String fUsreId, String fontCheck) {
			return update(new Object[] { fUsreId, fontCheck });
		}
	}

	protected class BizSchoolReservationUpdate extends SqlUpdate {
		public BizSchoolReservationUpdate(DataSource ds) {
			super(ds, " update schedule.dbo.biz_schedule set "
					+ "		F_userid=?,			F_yyyy=?,			F_mm=?,				F_dd=?,				F_hh=?,			F_ss=?,		F_ehh=?,	F_ess=?, 		"
					+ "		f_juya=?, 			F_jobtype=?,		F_JobContent=?,		F_Company=?,		F_Customer=?,	F_Place=?,	F_Jido=?, 	F_Bigo=?, 	F_writer=?,	"
					+ "		start_time1=?,		end_time1=?,		start_time2=?,		end_time2=?,		start_time3=?,	end_time3=?,company=?,	dept=?,		"
					+ "		instructor1=?,		instructor2=?,		instructor3=?,		instructor1_diff=?,													"
					+ "		instructor2_diff=?,	instructor3_diff=?,	F_Customer_tel=?,	desk_arrange=?,		course_ready=?	 			"
					+ " where f_id = ?                                                                                     ");
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
			declareParameter(new SqlParameter(Types.VARCHAR));

			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));

			declareParameter(new SqlParameter(Types.INTEGER));
		}

		public int update(BizSchoolReservationForm bizSchoolReservationForm) {
			return update(new Object[] {
					bizSchoolReservationForm.getfUserid(),
					bizSchoolReservationForm.getfYyyy(),
					bizSchoolReservationForm.getfMm(),
					bizSchoolReservationForm.getfDd(),
					bizSchoolReservationForm.getfHh(),
					bizSchoolReservationForm.getfSs(),
					bizSchoolReservationForm.getfEhh(),
					bizSchoolReservationForm.getfEss(),

					bizSchoolReservationForm.getfJuya(),
					bizSchoolReservationForm.getfJobtype(),
					bizSchoolReservationForm.getfJobContent(),
					bizSchoolReservationForm.getfCompany(),
					bizSchoolReservationForm.getfCustomer(),
					bizSchoolReservationForm.getfPlace(),
					bizSchoolReservationForm.getfJido(),
					bizSchoolReservationForm.getfBigo(),
					bizSchoolReservationForm.getfWriter(),

					bizSchoolReservationForm.getStartTime1_1() + bizSchoolReservationForm.getStartTime1_2(),
					bizSchoolReservationForm.getEndTime1_1() + bizSchoolReservationForm.getEndTime1_2(),
					bizSchoolReservationForm.getStartTime2_1() + bizSchoolReservationForm.getStartTime2_2(),
					bizSchoolReservationForm.getEndTime2_1() + bizSchoolReservationForm.getEndTime2_2(),
					bizSchoolReservationForm.getStartTime3_1() + bizSchoolReservationForm.getStartTime3_2(),
					bizSchoolReservationForm.getEndTime3_1() + bizSchoolReservationForm.getEndTime3_2(),
					bizSchoolReservationForm.getCompany(),
					bizSchoolReservationForm.getCompany().equalsIgnoreCase("kmac") ? bizSchoolReservationForm.getDept_sel()
							: bizSchoolReservationForm.getDept_txt(),

					bizSchoolReservationForm.getInstructor1(), bizSchoolReservationForm.getInstructor2(), bizSchoolReservationForm.getInstructor3(),
					bizSchoolReservationForm.getInstructor1Diff(),

					bizSchoolReservationForm.getInstructor2Diff(), bizSchoolReservationForm.getInstructor3Diff(),
					bizSchoolReservationForm.getfCustomerTel(), bizSchoolReservationForm.getDeskArrange(),

					bizSchoolReservationForm.getCourseReady(),

					bizSchoolReservationForm.getfId() });
		}
	}

	protected class BizSchoolReservationStateUpdate extends SqlUpdate {
		public BizSchoolReservationStateUpdate(DataSource ds) {
			super(ds, " update schedule.dbo.biz_fontcheck set fontCheck =? where f_id = ? ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		public int update(String fUsreId, String fontCheck) {
			return update(new Object[] { fontCheck, fUsreId });
		}
	}

	protected class BizSchoolReservationDelete extends SqlUpdate {
		public BizSchoolReservationDelete(DataSource ds) {
			super(ds, " delete from schedule.dbo.biz_schedule  where f_id = ?");

			declareParameter(new SqlParameter(Types.VARCHAR));
		}

		public int delete(String fId) {
			return update(new Object[] { fId });
		}
	}

	protected class BizSchoolReservationDeleteInsert extends SqlUpdate {
		public BizSchoolReservationDeleteInsert(DataSource ds) {
			super(ds, " insert into schedule.dbo.biz_schedule_Del_list (                                                                      "
					+ "		F_id, 				F_userid,			F_yyyy,				F_mm,			F_dd,			F_hh,		F_ss,		F_ehh,		F_ess, 		"
					+ "		f_juya, 			F_jobtype,			F_JobContent,		F_Company,		F_Customer,		F_Place,	F_Jido, 	F_Bigo, 	F_writer,	"
					+ "		start_time1,		end_time1,			start_time2,		end_time2,		start_time3,	end_time3,	company,	dept,		"
					+ "		instructor1,		instructor2,		instructor3,		instructor1_diff,													"
					+ "		instructor2_diff,	instructor3_diff,	F_Customer_tel,		desk_arrange,					course_ready,	regdate) 			"
					+ " values (	?, ?, ?, ?,    ?, ?, ?, ?,          ?, ?, ?, ?,    ?, ?, ?, ?, ?,   "
					+ " 			?, ?, ?, ?,    ?, ?, ?, ?,          ?, ?, ?, ?,    ?, ?, ?, ?,    ?,?, getDate()    ) ");
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

		public int insert(BizSchoolReservation bizSchoolReservation) {
			return update(new Object[] { bizSchoolReservation.getfId(), bizSchoolReservation.getfUserid(), bizSchoolReservation.getfYyyy(),
					bizSchoolReservation.getfMm(), bizSchoolReservation.getfDd(), bizSchoolReservation.getfHh(), bizSchoolReservation.getfSs(),
					bizSchoolReservation.getfEhh(), bizSchoolReservation.getfEss(),

					bizSchoolReservation.getfJuya(), bizSchoolReservation.getfJobtype(), bizSchoolReservation.getfJobContent(),
					bizSchoolReservation.getfCompany(), bizSchoolReservation.getfCustomer(), bizSchoolReservation.getfPlace(),
					bizSchoolReservation.getfJido(), bizSchoolReservation.getfBigo(), bizSchoolReservation.getfWriter(),

					bizSchoolReservation.getStartTime1(), bizSchoolReservation.getEndTime1(), bizSchoolReservation.getStartTime2(),
					bizSchoolReservation.getEndTime2(), bizSchoolReservation.getStartTime3(), bizSchoolReservation.getEndTime3(),
					bizSchoolReservation.getCompany(), bizSchoolReservation.getDept(),

					bizSchoolReservation.getInstructor1(), bizSchoolReservation.getInstructor2(), bizSchoolReservation.getInstructor3(),
					bizSchoolReservation.getInstructor1Diff(),

					bizSchoolReservation.getInstructor2Diff(), bizSchoolReservation.getInstructor3Diff(), bizSchoolReservation.getfCustomerTel(),
					bizSchoolReservation.getDeskArrange(),

					bizSchoolReservation.getCourseReady() });
		}
	}

	/*
	 * 강의실 코드 , Job type join 필요
	 */
	protected class BizSchoolReservationRetrieveQuery1 extends MappingSqlQuery {
		protected BizSchoolReservationRetrieveQuery1(DataSource ds, String query) {
			super(ds, query);
		}

		protected BizSchoolReservationRetrieveQuery1(DataSource ds) {
			super(ds, "	select a.*, b.f_name as f_userName, c.f_des as f_jobTypeDesc,                            "
					+ " 	(case when a.company <> 'kmac' then '3' else										 "
					+ "			(select top 1 f.fontcheck from schedule.dbo.biz_fontcheck f where f.f_id = a.f_id)	 " 
					+ " 	end) fontcheck																		 "
					+ " from schedule.dbo.biz_schedule a                                                         "
					+ " inner join schedule.dbo.biz_member b on a.f_userId = b.f_userId                          "
					+ " inner join schedule.dbo.biz_jobtype c on a.f_jobType = c.f_code                          "
					+ " where f_yyyy = ? and f_mm = ? and f_dd = ?                                               "
					+ " order by b.F_Seq																		 ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
		}

		@Override
		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			BizSchoolReservation bizSchoolReservation = new BizSchoolReservation();
			bizSchoolReservation.setfId(rs.getString("F_id"));
			bizSchoolReservation.setfUserid(rs.getString("F_userid"));
			bizSchoolReservation.setfUsername(rs.getString("F_username"));
			bizSchoolReservation.setfYyyy(rs.getString("F_yyyy"));
			bizSchoolReservation.setfMm(rs.getString("F_mm"));
			bizSchoolReservation.setfDd(rs.getString("F_dd"));
			bizSchoolReservation.setfHh(rs.getString("F_hh"));
			bizSchoolReservation.setfSs(rs.getString("F_ss"));
			bizSchoolReservation.setfEhh(rs.getString("F_ehh"));
			bizSchoolReservation.setfEss(rs.getString("F_ess"));
			bizSchoolReservation.setfJuya(rs.getString("F_juya"));
			bizSchoolReservation.setfJobtype(rs.getString("F_jobtype"));
			bizSchoolReservation.setfJobtypeDesc(rs.getString("f_jobTypeDesc"));
			bizSchoolReservation.setfJobContent(rs.getString("F_jobContent"));
			bizSchoolReservation.setfCompany(rs.getString("F_company"));
			bizSchoolReservation.setfPlace(rs.getString("F_place"));
			bizSchoolReservation.setfProject(rs.getString("F_project"));
			bizSchoolReservation.setfJido(rs.getString("F_jido"));
			bizSchoolReservation.setfBigo(rs.getString("F_bigo"));
			bizSchoolReservation.setfWriter(rs.getString("F_writer"));
			bizSchoolReservation.setStartTime1(rs.getString("start_time1"));
			bizSchoolReservation.setEndTime1(rs.getString("end_time1"));
			bizSchoolReservation.setStartTime2(rs.getString("start_time2"));
			bizSchoolReservation.setEndTime2(rs.getString("end_time2"));
			bizSchoolReservation.setStartTime3(rs.getString("start_time3"));
			bizSchoolReservation.setEndTime3(rs.getString("end_time3"));
			bizSchoolReservation.setCompany(rs.getString("company"));
			bizSchoolReservation.setDept(rs.getString("dept"));
			bizSchoolReservation.setInstructor1(rs.getString("instructor1"));
			bizSchoolReservation.setInstructor2(rs.getString("instructor2"));
			bizSchoolReservation.setInstructor3(rs.getString("instructor3"));
			bizSchoolReservation.setInstructor1Diff(rs.getString("instructor1_diff"));
			bizSchoolReservation.setInstructor2Diff(rs.getString("instructor2_diff"));
			bizSchoolReservation.setInstructor3Diff(rs.getString("instructor3_diff"));
			bizSchoolReservation.setfCustomer(rs.getString("F_customer"));
			bizSchoolReservation.setfCustomerTel(rs.getString("F_customer_tel"));
			bizSchoolReservation.setDeskArrange(rs.getString("desk_Arrange"));
			bizSchoolReservation.setCourseReady(rs.getString("course_Ready"));
			bizSchoolReservation.setRegdate(rs.getString("regdate"));
			bizSchoolReservation.setOcCode(rs.getString("oc_Code"));
			bizSchoolReservation.setOriginFCustomer(rs.getString("origin_F_Customer"));
			bizSchoolReservation.setOriginFJobContent(rs.getString("origin_F_JobContent"));
			bizSchoolReservation.setOriginFPlace(rs.getString("origin_F_Place"));
			bizSchoolReservation.setDeleter(rs.getString("deleter"));
			bizSchoolReservation.setDeleteTime(rs.getString("delete_Time"));
			bizSchoolReservation.setFontCheck(rs.getString("fontcheck"));

			return bizSchoolReservation;
		}

	}

	protected class BizSchoolReservationRetrieveQuery2 extends BizSchoolReservationRetrieveQuery1 {
		protected BizSchoolReservationRetrieveQuery2(DataSource ds) {
			super(ds, "	select a.*, b.f_name as f_userName, c.f_des as f_jobTypeDesc,                            "
					+ " (select top 1 f.fontcheck from schedule.dbo.biz_fontcheck f where f.f_id = a.f_id) fontcheck   "
					+ " from schedule.dbo.biz_schedule a                                                         "
					+ " inner join schedule.dbo.biz_member b on a.f_userId = b.f_userId                          "
					+ " inner join schedule.dbo.biz_jobtype c on a.f_jobType = c.f_code                          "
					+ " where f_id = ?                                                                           ");
			declareParameter(new SqlParameter(Types.VARCHAR));
		}
	}

	/*
	 * 강의실 코드 , Job type join 필요
	 */
	protected class BizSchoolReservationRetrieveQuery3 extends MappingSqlQuery {
		protected BizSchoolReservationRetrieveQuery3(DataSource ds, String query) {
			super(ds, query);
		}

		protected BizSchoolReservationRetrieveQuery3(DataSource ds) {
			super(ds, "	select * from schedule.dbo.biz_member order by f_seq ");
		}

		@Override
		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			BizSchoolReservation bizSchoolReservation = new BizSchoolReservation();
			bizSchoolReservation.setfUserid(rs.getString("F_userid"));
			bizSchoolReservation.setfUsername(rs.getString("F_name"));
			return bizSchoolReservation;
		}

	}

	protected class BizSchoolReservationRetrieveQuery4 extends MappingSqlQuery {
		protected BizSchoolReservationRetrieveQuery4(DataSource ds, String query) {
			super(ds, query);
		}

		protected BizSchoolReservationRetrieveQuery4(DataSource ds) {
			super(ds, "	select * from schedule.dbo.biz_jobtype ");
		}

		@Override
		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			BizSchoolReservation bizSchoolReservation = new BizSchoolReservation();
			bizSchoolReservation.setfJobtype(rs.getString("F_code"));
			bizSchoolReservation.setfJobtypeDesc(rs.getString("F_des"));
			return bizSchoolReservation;
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BizSchoolReservation> getBizSchoolReservationList(String yyyyMMdd) throws DataAccessException {
		return this.bizSchoolReservationRetrieveQuery1.execute(new Object[] { yyyyMMdd.substring(0, 4), yyyyMMdd.substring(4, 6),
				yyyyMMdd.substring(6, 8) });
	}

	@Override
	public BizSchoolReservation getBizSchoolReservation(String fId) throws DataAccessException {
		return (BizSchoolReservation) this.bizSchoolReservationRetrieveQuery2.findObject(new Object[] { fId });
	}

	@Override
	public void insertBizSchoolReservation(BizSchoolReservationForm bizSchoolReservationForm) throws DataAccessException {
		int res = this.bizSchoolReservationInsert.insert(bizSchoolReservationForm);
		this.bizSchoolReservationStateInsert.insert(String.valueOf(res), "2");
	}

	@Override
	public void updateBizSchoolReservation(BizSchoolReservationForm bizSchoolReservationForm) throws DataAccessException {
		this.bizSchoolReservationUpdate.update(bizSchoolReservationForm);
	}

	@Override
	public void confirmBizSchoolReservation(String fId) throws DataAccessException {
		this.bizSchoolReservationStateUpdate.update(fId, "1");
	}

	@Override
	public void deleteBizSchoolReservation(String fId) throws DataAccessException {
		this.bizSchoolReservationDeleteInsert.insert((BizSchoolReservation) this.bizSchoolReservationRetrieveQuery2.findObject(new Object[] { fId }));
		this.bizSchoolReservationDelete.delete(fId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BizSchoolReservation> getBizSchoolList() throws DataAccessException {
		return this.bizSchoolReservationRetrieveQuery3.execute();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BizSchoolReservation> getBizTypeList() throws DataAccessException {
		return this.bizSchoolReservationRetrieveQuery4.execute();
	}

}
