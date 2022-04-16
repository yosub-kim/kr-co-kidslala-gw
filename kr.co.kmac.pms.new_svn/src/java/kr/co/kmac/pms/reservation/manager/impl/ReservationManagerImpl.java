package kr.co.kmac.pms.reservation.manager.impl;

import java.util.List;

import kr.co.kmac.pms.reservation.dao.ReservationDao;
import kr.co.kmac.pms.reservation.data.Reservation;
import kr.co.kmac.pms.reservation.data.ReservationItem;
import kr.co.kmac.pms.reservation.manager.ReservationManager;

public class ReservationManagerImpl implements ReservationManager {
	private ReservationDao reservationDao;

	public ReservationDao getReservationDao() {
		return reservationDao;
	}

	public void setReservationDao(ReservationDao reservationDao) {
		this.reservationDao = reservationDao;
	}

	@Override
	public List<ReservationItem> getReservationItemList(String gubun) throws Exception {
		return this.getReservationDao().getReservationItemList(gubun);
	}

	@Override
	public ReservationItem getReservationItemList(String gubun, String resvCode) throws Exception {
		return this.getReservationDao().getReservationItemList(gubun, resvCode);
	}

	public Reservation getReservation(String no) throws Exception {
		return this.getReservationDao().getReservation(no);
	}

	@Override
	public List<Reservation> getReservation(String resvCode, String sdate) throws Exception {
		return this.getReservationDao().getReservation(resvCode, sdate);
	}

	@Override
	public void insertReservation(Reservation reservation) throws Exception {
		this.getReservationDao().insertReservation(reservation);
	}

	@Override
	public void deleteReservation(String no) throws Exception {
		this.getReservationDao().deleteReservation(no);
	}

	@Override
	public boolean checkReservation(String resvCode, String stime, String etime) throws Exception {
		return this.getReservationDao().checkReservation(resvCode, stime, etime);
	}

}
