package kr.co.kmac.pms.reservation.manager.impl;

import java.util.Calendar;
import java.util.List;

import kr.co.kmac.pms.common.util.DateUtil;
import kr.co.kmac.pms.common.util.StringUtil;
import kr.co.kmac.pms.reservation.dao.BizSchoolReservationDao;
import kr.co.kmac.pms.reservation.data.BizSchoolReservation;
import kr.co.kmac.pms.reservation.form.BizSchoolReservationForm;
import kr.co.kmac.pms.reservation.manager.BizSchoolReservationManager;

public class BizSchoolReservationManagerImpl implements BizSchoolReservationManager {

	private BizSchoolReservationDao bizSchoolReservationDao;

	public BizSchoolReservationDao getBizSchoolReservationDao() {
		return bizSchoolReservationDao;
	}

	public void setBizSchoolReservationDao(BizSchoolReservationDao bizSchoolReservationDao) {
		this.bizSchoolReservationDao = bizSchoolReservationDao;
	}

	@Override
	public List<BizSchoolReservation> getBizSchoolReservationList(String yyyyMMdd) throws Exception {
		return this.getBizSchoolReservationDao().getBizSchoolReservationList(yyyyMMdd);
	}

	@Override
	public BizSchoolReservation getBizSchoolReservation(String fId) throws Exception {
		return this.getBizSchoolReservationDao().getBizSchoolReservation(fId);
	}

	@Override
	public void insertBizSchoolReservation(BizSchoolReservationForm bizSchoolReservationForm) throws Exception {
		int term = Integer.parseInt(StringUtil.isNull(bizSchoolReservationForm.getTerm(), "1"));
		Calendar cal = DateUtil.getCalendarInstance(bizSchoolReservationForm.getfYyyy() + bizSchoolReservationForm.getfMm()
				+ bizSchoolReservationForm.getfDd());
		for (int i = 0; i < term; i++) {
			bizSchoolReservationForm.setfYyyy(String.valueOf(cal.get(Calendar.YEAR)));
			bizSchoolReservationForm.setfMm(String.valueOf(cal.get(Calendar.MONTH) + 1).length() == 1 ? "0" + String.valueOf(cal.get(Calendar.MONTH) + 1) : String.valueOf(cal.get(Calendar.MONTH) + 1));
			bizSchoolReservationForm.setfDd(String.valueOf(cal.get(Calendar.DATE)).length() == 1 ? "0" + String.valueOf(cal.get(Calendar.DATE)) : String.valueOf(cal.get(Calendar.DATE)));
			this.getBizSchoolReservationDao().insertBizSchoolReservation(bizSchoolReservationForm);
			cal.add(Calendar.DATE, 1);
		}
	}

	@Override
	public void updateBizSchoolReservation(BizSchoolReservationForm bizSchoolReservationForm) throws Exception {
		this.getBizSchoolReservationDao().updateBizSchoolReservation(bizSchoolReservationForm);
	}

	@Override
	public void confirmBizSchoolReservation(String fId) throws Exception {
		this.getBizSchoolReservationDao().confirmBizSchoolReservation(fId);

	}

	@Override
	public void deleteBizSchoolReservation(String fId) throws Exception {
		this.getBizSchoolReservationDao().deleteBizSchoolReservation(fId);
	}

	@Override
	public List<BizSchoolReservation> getBizSchoolList() throws Exception {
		return this.getBizSchoolReservationDao().getBizSchoolList();
	}

	@Override
	public List<BizSchoolReservation> getBizTypeList() throws Exception {
		return this.getBizSchoolReservationDao().getBizTypeList();
	}

}
