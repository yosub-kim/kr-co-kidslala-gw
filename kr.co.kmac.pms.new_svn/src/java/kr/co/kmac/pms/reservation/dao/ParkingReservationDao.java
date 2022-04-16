package kr.co.kmac.pms.reservation.dao;

import java.util.List;

import kr.co.kmac.pms.reservation.data.ParkingReservation;

import org.springframework.dao.DataAccessException;

public interface ParkingReservationDao {

	public List<ParkingReservation> getParkingReservationMonthlyList(String month) throws DataAccessException;

	public List<ParkingReservation> getParkingReservationList(String yyyyMMdd) throws DataAccessException;

	public ParkingReservation getParkingReservation(String idx) throws DataAccessException;

	public void insertParkingReservation(ParkingReservation parkingReservation) throws DataAccessException;

	public void updateParkingReservation(ParkingReservation parkingReservation) throws DataAccessException;

	public void confirmParkingReservation(String idx) throws DataAccessException;

	public void deleteParkingReservation(String idx) throws DataAccessException;

	public boolean parkingReservationCheck(String pdate, String pname) throws DataAccessException;
}
