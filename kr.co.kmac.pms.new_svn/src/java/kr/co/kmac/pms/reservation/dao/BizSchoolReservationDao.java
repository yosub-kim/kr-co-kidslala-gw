package kr.co.kmac.pms.reservation.dao;

import java.util.List;

import kr.co.kmac.pms.reservation.data.BizSchoolReservation;
import kr.co.kmac.pms.reservation.form.BizSchoolReservationForm;

import org.springframework.dao.DataAccessException;

public interface BizSchoolReservationDao {

	public List<BizSchoolReservation> getBizSchoolReservationList(String yyyyMMdd) throws DataAccessException;

	public BizSchoolReservation getBizSchoolReservation(String fId) throws DataAccessException;

	public void insertBizSchoolReservation(BizSchoolReservationForm bizSchoolReservationForm) throws DataAccessException;

	public void updateBizSchoolReservation(BizSchoolReservationForm bizSchoolReservationForm) throws DataAccessException;

	public void confirmBizSchoolReservation(String fId) throws DataAccessException;

	public void deleteBizSchoolReservation(String fId) throws DataAccessException;

	public List<BizSchoolReservation> getBizSchoolList() throws DataAccessException;

	public List<BizSchoolReservation> getBizTypeList() throws DataAccessException;

}
