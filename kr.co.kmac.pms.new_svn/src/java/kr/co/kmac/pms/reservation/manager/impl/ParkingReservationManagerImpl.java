package kr.co.kmac.pms.reservation.manager.impl;

import java.util.List;

import kr.co.kmac.pms.reservation.dao.ParkingReservationDao;
import kr.co.kmac.pms.reservation.data.ParkingReservation;
import kr.co.kmac.pms.reservation.manager.ParkingReservationManager;

public class ParkingReservationManagerImpl implements ParkingReservationManager {

	private ParkingReservationDao parkingReservationDao;

	public ParkingReservationDao getParkingReservationDao() {
		return parkingReservationDao;
	}

	public void setParkingReservationDao(ParkingReservationDao parkingReservationDao) {
		this.parkingReservationDao = parkingReservationDao;
	}

	@Override
	public List<ParkingReservation> getParkingReservationMonthlyList(String month) throws Exception {
		return this.getParkingReservationDao().getParkingReservationMonthlyList(month);
	}

	@Override
	public List<ParkingReservation> getParkingReservationList(String yyyyMMdd) throws Exception {
		return this.getParkingReservationDao().getParkingReservationList(yyyyMMdd);
	}

	@Override
	public ParkingReservation getParkingReservation(String idx) throws Exception {
		return this.getParkingReservationDao().getParkingReservation(idx);
	}

	@Override
	public void insertParkingReservation(ParkingReservation parkingReservation) throws Exception {
		this.getParkingReservationDao().insertParkingReservation(parkingReservation);
	}

	@Override
	public void updateParkingReservation(ParkingReservation parkingReservation) throws Exception {
		this.getParkingReservationDao().updateParkingReservation(parkingReservation);
	}

	@Override
	public void confirmParkingReservation(String idx) throws Exception {
		this.getParkingReservationDao().confirmParkingReservation(idx);
	}

	@Override
	public void deleteParkingReservation(String idx) throws Exception {
		this.getParkingReservationDao().deleteParkingReservation(idx);
	}

	@Override
	public boolean parkingReservationCheck(String pdate, String pname) throws Exception {
		return this.getParkingReservationDao().parkingReservationCheck(pdate, pname);
	}

}
