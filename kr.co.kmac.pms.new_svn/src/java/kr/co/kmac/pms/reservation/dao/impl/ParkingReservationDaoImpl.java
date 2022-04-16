package kr.co.kmac.pms.reservation.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import javax.sql.DataSource;

import kr.co.kmac.pms.reservation.dao.ParkingReservationDao;
import kr.co.kmac.pms.reservation.data.ParkingReservation;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.jdbc.object.SqlUpdate;

public class ParkingReservationDaoImpl extends JdbcDaoSupport implements ParkingReservationDao {

	private ParkingReservationInsert parkingReservationInsert;
	private ParkingReservationUpdate parkingReservationUpdate;
	private ParkingReservationConfirm parkingReservationConfirm;
	private ParkingReservationDelete parkingReservationDelete;
	private ParkingReservationRetrieveQuery1 parkingReservationRetrieveQuery1;
	private ParkingReservationRetrieveQuery2 parkingReservationRetrieveQuery2;
	private ParkingReservationRetrieveQuery3 parkingReservationRetrieveQuery3;

	protected void initDao() {
		this.parkingReservationInsert = new ParkingReservationInsert(getDataSource());
		this.parkingReservationUpdate = new ParkingReservationUpdate(getDataSource());
		this.parkingReservationDelete = new ParkingReservationDelete(getDataSource());
		this.parkingReservationConfirm = new ParkingReservationConfirm(getDataSource());
		this.parkingReservationRetrieveQuery1 = new ParkingReservationRetrieveQuery1(getDataSource());
		this.parkingReservationRetrieveQuery2 = new ParkingReservationRetrieveQuery2(getDataSource());
		this.parkingReservationRetrieveQuery3 = new ParkingReservationRetrieveQuery3(getDataSource());
	}

	protected class ParkingReservationInsert extends SqlUpdate {
		public ParkingReservationInsert(DataSource ds) {
			super(ds, "insert into schedule.dbo.parking_reservation (                                                     "
					+ "		pdate,		start_time,	end_time,	Rtime,			add_day,	pname,		ptel,             "
					+ "		pcar_num,	pmemo,		regdate,	check_result,	company,	dept, 		writer,		class "
					+ ") values ( ?,  ?,  ?,  ?,  ?,  ?,  ?,           ?,  ?,  ?,  ?,  ?,  ?,  ?,  ?)");
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

		public int insert(ParkingReservation parkingReservation) {
			return update(new Object[] { parkingReservation.getPdate(), parkingReservation.getStartTime(), parkingReservation.getEndTime(),
					parkingReservation.getRtime(), parkingReservation.getAddDay(), parkingReservation.getPname(), parkingReservation.getPtel(),
					parkingReservation.getPcarNum(), parkingReservation.getPmemo(), parkingReservation.getRegdate(),
					parkingReservation.getCheckResult(), parkingReservation.getCompany(), parkingReservation.getDept(),
					parkingReservation.getWriter(), parkingReservation.getpClass() });
		}
	}

	protected class ParkingReservationUpdate extends SqlUpdate {
		public ParkingReservationUpdate(DataSource ds) {
			super(ds, " update schedule.dbo.parking_reservation set                                                            "
					+ "		pdate=?,	start_time=?,	end_time=?,	Rtime=?,		add_day=?,	pname=?,	ptel=?,            "
					+ "		pcar_num=?,	pmemo=?,		regdate=?,	check_result=?,	company=?,	dept=?, 	writer=?,	class=?"
					+ " where idx = ?                                                                                          ");
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
		}

		public int update(ParkingReservation parkingReservation) {
			return update(new Object[] { parkingReservation.getPdate(), parkingReservation.getStartTime(), parkingReservation.getEndTime(),
					parkingReservation.getRtime(), parkingReservation.getAddDay(), parkingReservation.getPname(), parkingReservation.getPtel(),
					parkingReservation.getPcarNum(), parkingReservation.getPmemo(), parkingReservation.getRegdate(),
					parkingReservation.getCheckResult(), parkingReservation.getCompany(), parkingReservation.getDept(),
					parkingReservation.getWriter(), parkingReservation.getpClass(), parkingReservation.getIdx() });
		}
	}

	protected class ParkingReservationConfirm extends SqlUpdate {
		public ParkingReservationConfirm(DataSource ds) {
			super(ds, " update schedule.dbo.parking_reservation set check_result='Y' where idx = ? ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		public int update(String idx) {
			return update(new Object[] { idx });
		}
	}

	protected class ParkingReservationDelete extends SqlUpdate {
		public ParkingReservationDelete(DataSource ds) {
			super(ds, "delete from schedule.dbo.parking_reservation where idx = ?");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		public int delete(String fId) {
			return update(new Object[] { fId });
		}
	}

	protected class ParkingReservationRetrieveQuery1 extends MappingSqlQuery {
		protected ParkingReservationRetrieveQuery1(DataSource ds, String query) {
			super(ds, query);
		}

		protected ParkingReservationRetrieveQuery1(DataSource ds) {
			super(ds, "	select a.dt, a.dateStr, a.weekdayStr, isNull(b.confirmcnt, 0) confirmcnt, isNull(b.noConfirmcnt, 0) noConfirmcnt "
					+ "	from (                                                                                                   "
					+ "		select	convert(char(10), dateadd(d,number, ?),112) dt,                                              "
					+ "				convert(char(10), dateadd(d,number, ?),120) as dateStr,                                      "
					+ "				datename(weekday, dateadd(d,number, ?)) as weekdayStr                                        "
					+ "		from master..spt_values                                                                              "
					+ "		where type = 'P' and number <= datediff(d, ?, ?)                                                     "
					+ "	) a left outer join (                                                                                    "
					+ "		select	isNull(b.pdate, a.pdate) pdate, isnull(a.cnt, 0) confirmCnt, isnull(b.cnt, 0) noConfirmcnt   "
					+ "		from (                                                                                               "
					+ "			select pdate, count(idx) cnt from schedule.dbo.parking_reservation where pdate like ?            "
					+ "			and check_result = 'Y' group by pdate                                                            "
					+ "		) a full outer join (                                                                                "
					+ "			select pdate, count(idx) cnt from schedule.dbo.parking_reservation where pdate like ?            "
					+ "			and check_result is null group by pdate                                                          "
					+ "		) b on a.pdate = b.pdate                                                                             "
					+ "	) b on a.dt = b.pdate where dt like ? order by 1 asc ");
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

		@Override
		protected Object mapRow(ResultSet rs, int arg1) throws SQLException {
			ParkingReservation parkingReservation = new ParkingReservation();
			parkingReservation.setDt(rs.getString("dt"));
			parkingReservation.setDateStr(rs.getString("dateStr"));
			parkingReservation.setWeekdayStr(rs.getString("weekdayStr"));
			parkingReservation.setConfirmCnt(rs.getString("confirmCnt"));
			parkingReservation.setNoConfirmCnt(rs.getString("noConfirmCnt"));
			return parkingReservation;
		}

	}

	protected class ParkingReservationRetrieveQuery2 extends MappingSqlQuery {
		protected ParkingReservationRetrieveQuery2(DataSource ds, String query) {
			super(ds, query);
		}

		protected ParkingReservationRetrieveQuery2(DataSource ds) {
			super(ds, " SELECT a.*, (CASE WHEN g.name IS NULL THEN a.dept ELSE g.name END) as deptName, e.name as writerName "
					+ " FROM schedule.dbo.parking_reservation a LEFT OUTER JOIN smGroup g ON g.id = a.dept "
					+ " LEFT OUTER JOIN expertpool e ON e.userId = a.writer WHERE pdate = ? ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		@Override
		protected Object mapRow(ResultSet rs, int arg1) throws SQLException {
			ParkingReservation parkingReservation = new ParkingReservation();
			parkingReservation.setIdx(rs.getString("idx"));
			parkingReservation.setPdate(rs.getString("pdate"));
			parkingReservation.setStartTime(rs.getString("start_Time"));
			parkingReservation.setEndTime(rs.getString("end_Time"));
			parkingReservation.setRtime(rs.getString("rtime"));
			parkingReservation.setAddDay(rs.getString("add_Day"));
			parkingReservation.setPname(rs.getString("pname"));
			parkingReservation.setPtel(rs.getString("ptel"));
			parkingReservation.setPcarNum(rs.getString("pcar_Num"));
			parkingReservation.setPmemo(rs.getString("pmemo"));
			parkingReservation.setRegdate(rs.getString("regdate"));
			parkingReservation.setCheckResult(rs.getString("check_Result"));
			parkingReservation.setCompany(rs.getString("company"));
			parkingReservation.setDept(rs.getString("dept"));
			parkingReservation.setDeptName(rs.getString("deptName"));
			parkingReservation.setWriter(rs.getString("writer"));
			parkingReservation.setWriterName(rs.getString("writerName"));
			parkingReservation.setpClass(rs.getString("class"));
			return parkingReservation;
		}
	}

	protected class ParkingReservationRetrieveQuery3 extends ParkingReservationRetrieveQuery2 {
		protected ParkingReservationRetrieveQuery3(DataSource ds) {
			super(ds, " select a.*, g.name as deptName, e.name as writerName "
					+ " from schedule.dbo.parking_reservation a left outer join smGroup g on g.id = a.dept "
					+ " left outer join expertpool e on e.userId = a.writer where idx = ? ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ParkingReservation> getParkingReservationMonthlyList(String month) throws DataAccessException {
		return this.parkingReservationRetrieveQuery1.execute(new Object[] { month.substring(0, 4) + "-01-01", month.substring(0, 4) + "-01-01",
				month.substring(0, 4) + "-01-01", month.substring(0, 4) + "-01-01", month.substring(0, 4) + "-12-31", month + "%", month + "%",
				month + "%", });
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ParkingReservation> getParkingReservationList(String yyyyMMdd) throws DataAccessException {
		return this.parkingReservationRetrieveQuery2.execute(new Object[] { yyyyMMdd });
	}

	@Override
	public ParkingReservation getParkingReservation(String idx) throws DataAccessException {
		return (ParkingReservation) this.parkingReservationRetrieveQuery3.findObject(new Object[] { idx });
	}

	@Override
	public void insertParkingReservation(ParkingReservation parkingReservation) throws DataAccessException {
		this.parkingReservationInsert.insert(parkingReservation);
	}

	@Override
	public void updateParkingReservation(ParkingReservation parkingReservation) throws DataAccessException {
		this.parkingReservationUpdate.update(parkingReservation);
	}

	@Override
	public void confirmParkingReservation(String idx) throws DataAccessException {
		this.parkingReservationConfirm.update(idx);
	}

	@Override
	public void deleteParkingReservation(String idx) throws DataAccessException {
		this.parkingReservationDelete.delete(idx);
	}

	public boolean parkingReservationCheck(String pdate, String pname) throws DataAccessException {
		return getJdbcTemplate().queryForInt(
				"select count(*) from  schedule.dbo.parking_reservation where pdate = '" + pdate + "' and pname = '" + pname + "'") <= 0;
	}
}
