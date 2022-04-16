package kr.co.kmac.pms.reservation.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import kr.co.kmac.pms.reservation.data.Reservation;
import kr.co.kmac.pms.reservation.data.ReservationItem;

public interface ReservationDao {

	public List<ReservationItem> getReservationItemList(String gubun) throws DataAccessException;

	public ReservationItem getReservationItemList(String gubun, String resvCode) throws DataAccessException;

	public Reservation getReservation(String no) throws DataAccessException;

	public List<Reservation> getReservation(String resvCode, String sdate) throws DataAccessException;

	public boolean checkReservation(String resvCode, String stime, String etime) throws DataAccessException;

	public void insertReservation(Reservation reservation) throws DataAccessException;

	public void updateReservation(Reservation reservation) throws DataAccessException;

	public void deleteReservation(String no) throws DataAccessException;

}
