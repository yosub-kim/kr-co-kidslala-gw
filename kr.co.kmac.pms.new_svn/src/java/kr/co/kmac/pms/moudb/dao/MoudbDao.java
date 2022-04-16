package kr.co.kmac.pms.moudb.dao;

import kr.co.kmac.pms.moudb.data.MoudbDetail;
import kr.co.kmac.pms.moudb.form.MoudbDetailForm;

import org.springframework.dao.DataAccessException;

public interface MoudbDao {
	
	public String create(MoudbDetailForm moudbDetailForm) throws DataAccessException;
	
	public void  update(MoudbDetailForm moudbDetailForm) throws DataAccessException;
	
	public void remove(String idx) throws DataAccessException;
	
	public MoudbDetail retrieve(String idx) throws DataAccessException;
	
	public boolean isExist(String com_name) throws DataAccessException;

}
