package kr.co.kmac.pms.moudb.manager.impl;

import org.springframework.dao.DataAccessException;

import kr.co.kmac.pms.moudb.dao.MoudbDao;
import kr.co.kmac.pms.moudb.data.MoudbDetail;
import kr.co.kmac.pms.moudb.exception.MoudbException;
import kr.co.kmac.pms.moudb.form.MoudbDetailForm;
import kr.co.kmac.pms.moudb.manager.MoudbManager;

public class MoudbManagerImpl implements MoudbManager {
	
	private MoudbDao moudbDao;

	public MoudbDao getMoudbDao() {
		return moudbDao;
	}

	public void setMoudbDao(MoudbDao moudbDao) {
		this.moudbDao = moudbDao;
	}
	
	public String create(MoudbDetailForm moudbDetailForm) throws MoudbException {
		return getMoudbDao().create(moudbDetailForm);
	}

	@Override
	public void update(MoudbDetailForm moudbDetailForm)throws DataAccessException {
		getMoudbDao().update(moudbDetailForm);
	}

	@Override
	public void remove(String idx) throws DataAccessException {
		getMoudbDao().remove(idx);
	}

	@Override
	public MoudbDetail retrieve(String idx) throws DataAccessException {
		return getMoudbDao().retrieve(idx);
	}

	@Override
	public boolean isExist(String com_name) throws DataAccessException {
		return getMoudbDao().isExist(com_name);
	}
	
	

}
