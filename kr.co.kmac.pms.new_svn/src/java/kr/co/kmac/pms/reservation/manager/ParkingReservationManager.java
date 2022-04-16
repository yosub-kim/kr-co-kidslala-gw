package kr.co.kmac.pms.reservation.manager;

import java.util.List;

import org.springframework.dao.DataAccessException;

import kr.co.kmac.pms.reservation.data.ParkingReservation;

public interface ParkingReservationManager {

	public List<ParkingReservation> getParkingReservationMonthlyList(String month) throws Exception;

	public List<ParkingReservation> getParkingReservationList(String yyyyMMdd) throws Exception;

	public ParkingReservation getParkingReservation(String idx) throws Exception;

	public void insertParkingReservation(ParkingReservation parkingReservation) throws Exception;

	public void updateParkingReservation(ParkingReservation parkingReservation) throws Exception;

	public void confirmParkingReservation(String idx) throws Exception;

	public void deleteParkingReservation(String idx) throws Exception;

	public boolean parkingReservationCheck(String pdate, String pname) throws Exception;
}
