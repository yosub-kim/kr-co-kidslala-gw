package kr.co.kmac.pms.reservation.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import javax.sql.DataSource;

import kr.co.kmac.pms.reservation.dao.ReservationDao;
import kr.co.kmac.pms.reservation.data.Reservation;
import kr.co.kmac.pms.reservation.data.ReservationItem;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.jdbc.object.SqlUpdate;

public class ReservationDaoImpl extends JdbcDaoSupport implements ReservationDao {
	private ReservationInsertQuery reservationInsertQuery;
	// private ReservationUpdateQeury reservationUpdateQeury;
	private ReservationRetrieveQuery reservationRetrieveQuery;
	private ReservationRetrieveQuery1 reservationRetrieveQuery1;
	private ReservationItemRetrieveQuery reservationItemRetrieveQuery;
	private ReservationItemRetrieveQuery1 reservationItemRetrieveQuery1;
	private ReservationDeleteQuery reservationDeleteQuery;
	private DataSource hhDevDataSource;

	public DataSource getHhDevDataSource() {
		return hhDevDataSource;
	}

	public void setHhDevDataSource(DataSource hhDevDataSource) {
		this.hhDevDataSource = hhDevDataSource;
	}

	protected void initDao() {
		this.reservationInsertQuery = new ReservationInsertQuery(getHhDevDataSource());
		this.reservationRetrieveQuery = new ReservationRetrieveQuery(getHhDevDataSource());
		this.reservationRetrieveQuery1 = new ReservationRetrieveQuery1(getHhDevDataSource());
		this.reservationItemRetrieveQuery = new ReservationItemRetrieveQuery(getHhDevDataSource());
		this.reservationItemRetrieveQuery1 = new ReservationItemRetrieveQuery1(getHhDevDataSource());
		// this.reservationUpdateQeury = new ReservationUpdateQeury(getHhDevDataSource());
		this.reservationDeleteQuery = new ReservationDeleteQuery(getHhDevDataSource());
	}

	protected class ReservationInsertQuery extends SqlUpdate {
		protected ReservationInsertQuery(DataSource ds) {
			super(ds, " INSERT INTO ResvTable(	resv_code, empno, stime, etime,											"
					+ "							use_team, use_per, use_loc, use_purpose, resv_stat)						"
					+ " VALUES(?, ?, ?, ?,  					 ?, ?, ?, ?, '1') 																");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));

			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));

			compile();
			setRequiredRowsAffected(1);
		}

		protected int insert(Reservation reservation) {
			return this.update(new Object[] { reservation.getResvCode(), reservation.getEmpno(), reservation.getStime(), reservation.getEtime(),
					reservation.getUseTeam(), reservation.getUsePer(), reservation.getUseLoc(), reservation.getUsePurpose() });
		}
	}

	protected class ReservationDeleteQuery extends SqlUpdate {
		protected ReservationDeleteQuery(DataSource ds) {
			super(ds, " DELETE FROM ResvTable WHERE no = ? ");
			declareParameter(new SqlParameter(Types.VARCHAR));

			compile();
		}

		protected int delete(String no) {
			return this.update(new Object[] { no });
		}
	}

	protected class ReservationRetrieveQuery extends MappingSqlQuery {
		protected ReservationRetrieveQuery(DataSource ds, String query) {
			super(ds, query);
		}

		protected ReservationRetrieveQuery(DataSource ds) {
			super(
					ds,
					"	select	a.timeVal, b.no, b.resv_code, b.empno, b.stime, b.etime,													"
							+ "			b.use_team, b.use_per, b.use_loc, b.use_purpose, b.out_per, b.resv_stat										"
							+ "	from ResvTableTimeSet a	left outer join (																			"
							+ "	SELECT b.*, a.* FROM ResvTable a inner join ResvTableTimeSet b														"
							+ "	on (?+''+b.timeVal) >= stime and (?+''+b.timeVal) < etime															"
							+ "	WHERE resv_code=? and ( ? between substring(stime, 1, 8) and substring(etime, 1, 8))) b on a. timeVal = b.timeVal order by 1	");

			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			Reservation reservation = new Reservation();
			reservation.setNo(rs.getString("no"));
			reservation.setResvCode(rs.getString("resv_code"));
			reservation.setEmpno(rs.getString("empno"));
			reservation.setStime(rs.getString("stime"));
			reservation.setEtime(rs.getString("etime"));
			reservation.setUseTeam(rs.getString("use_team"));
			reservation.setUsePer(rs.getString("use_per"));
			reservation.setUseLoc(rs.getString("use_loc"));
			reservation.setUsePurpose(rs.getString("use_purpose"));
			try {
				reservation.setTimeVal(rs.getString("timeVal"));
			} catch (Exception e) {
			}
			return reservation;
		}
	}

	protected class ReservationRetrieveQuery1 extends ReservationRetrieveQuery {
		protected ReservationRetrieveQuery1(DataSource ds) {
			super(ds, "SELECT * FROM ResvTable where no = ?");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}
	}

	protected class ReservationItemRetrieveQuery extends MappingSqlQuery {
		protected ReservationItemRetrieveQuery(DataSource ds, String query) {
			super(ds, query);
		}

		protected ReservationItemRetrieveQuery(DataSource ds) {
			super(ds, "select * from ResvCode where useyn='Y' and gubun = ? order by ord");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			ReservationItem reservationItem = new ReservationItem();
			reservationItem.setNo(rs.getString("no"));
			reservationItem.setResvCode(rs.getString("resv_code"));
			reservationItem.setCodeName(rs.getString("code_name").replace("<br>", ""));	// 회의실 명칭에서 줄바꿈 태그 제거
			reservationItem.setUseyn(rs.getString("useyn"));
			reservationItem.setGubun(rs.getString("gubun"));
			reservationItem.setOrd(rs.getString("ord"));
			return reservationItem;
		}
	}

	protected class ReservationItemRetrieveQuery1 extends ReservationItemRetrieveQuery {
		protected ReservationItemRetrieveQuery1(DataSource ds) {
			super(ds, "select * from ResvCode where useyn='Y' and gubun = ? and resv_code = ?");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ReservationItem> getReservationItemList(String gubun) throws DataAccessException {
		return this.reservationItemRetrieveQuery.execute(new Object[] { gubun });
	}

	@Override
	public ReservationItem getReservationItemList(String gubun, String resvCode) throws DataAccessException {
		return (ReservationItem) this.reservationItemRetrieveQuery1.findObject(new Object[] { gubun, resvCode });
	}

	@Override
	public Reservation getReservation(String no) throws DataAccessException {
		return (Reservation) this.reservationRetrieveQuery1.findObject(new Object[] { no });
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Reservation> getReservation(String resvCode, String sdate) throws DataAccessException {
		return this.reservationRetrieveQuery.execute(new Object[] { sdate, sdate, resvCode, sdate });
	}

	@Override
	public void insertReservation(Reservation reservation) throws DataAccessException {
		this.reservationInsertQuery.insert(reservation);
	}

	@Override
	public void updateReservation(Reservation reservation) throws DataAccessException {

	}

	@Override
	public void deleteReservation(String no) throws DataAccessException {
		this.reservationDeleteQuery.delete(no);
	}

	@Override
	public boolean checkReservation(String resvCode, String stime, String etime) throws DataAccessException {
		 setDataSource(getHhDevDataSource());
		return !(this.getJdbcTemplate().queryForInt(
				"SELECT count(*) FROM ResvTable a where  resv_code = '" + resvCode + "' "
						+ "and (('" + stime + "' >= stime and '" + stime + "' < etime) or ('" 
									+ etime + "' >  stime and '" + etime + "' <= etime))") > 0);
	}

}
