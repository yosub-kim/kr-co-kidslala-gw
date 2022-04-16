package kr.co.kmac.pms.reservation.manager;

import java.util.List;

import kr.co.kmac.pms.reservation.data.Reservation;
import kr.co.kmac.pms.reservation.data.ReservationItem;

public interface ReservationManager {
	public List<ReservationItem> getReservationItemList(String gubun) throws Exception;

	public ReservationItem getReservationItemList(String gubun, String resvCode) throws Exception;

	public Reservation getReservation(String no) throws Exception;

	public List<Reservation> getReservation(String resvCode, String sdate) throws Exception;

	public void insertReservation(Reservation reservation) throws Exception;

	public void deleteReservation(String no) throws Exception;

	public boolean checkReservation(String resvCode, String stime, String etime) throws Exception;
}
