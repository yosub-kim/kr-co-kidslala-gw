package kr.co.kmac.pms.reservation.manager;

import java.util.List;

import kr.co.kmac.pms.reservation.data.BizSchoolReservation;
import kr.co.kmac.pms.reservation.form.BizSchoolReservationForm;

public interface BizSchoolReservationManager {

	public List<BizSchoolReservation> getBizSchoolReservationList(String yyyyMMdd) throws Exception;

	public BizSchoolReservation getBizSchoolReservation(String fId) throws Exception;

	public void insertBizSchoolReservation(BizSchoolReservationForm bizSchoolReservationForm) throws Exception;

	public void updateBizSchoolReservation(BizSchoolReservationForm bizSchoolReservationForm) throws Exception;

	public void confirmBizSchoolReservation(String fId) throws Exception;

	public void deleteBizSchoolReservation(String fId) throws Exception;

	public List<BizSchoolReservation> getBizSchoolList() throws Exception;

	public List<BizSchoolReservation> getBizTypeList() throws Exception;
}
