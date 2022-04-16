package kr.co.kmac.pms.system.gadget.dao;


import java.util.ArrayList;
import java.util.List;

import kr.co.kmac.pms.system.gadget.data.Gadget;
import kr.co.kmac.pms.system.gadget.data.Content;
import kr.co.kmac.pms.system.gadget.data.MyGadget;

import org.springframework.dao.DataAccessException;

public interface GadgetDao {
	public List<Gadget> getMyGadgetList(String userId, String gadgetType) throws DataAccessException;
	public List<Gadget> getMyGadgetListByRole(String role, String gadgetType) throws DataAccessException;
	public List<Gadget> getMyMobileGadgetListByRole(String role) throws DataAccessException;
	public List<Gadget> getMyWideGadgetList(String userId) throws DataAccessException;
	public List<Gadget> getGadgetList(String gadgetId) throws DataAccessException;
	public List<Gadget> getGadgetList(String gadgetId, String gadgetRole, String gadgetName) throws DataAccessException;
	public List<Gadget> getNotSelectGadgetList(String userId) throws DataAccessException;
	public void createGadget(Gadget gadget) throws DataAccessException;
	public void updateGadget(Gadget gadget) throws DataAccessException;
	public void deleteGadget(Gadget gadget) throws DataAccessException;
	public void appendMyGadget(MyGadget myGadget) throws DataAccessException;
	public void updateMyGadget(MyGadget myGadget) throws DataAccessException;
	public void deleteMyGadget(MyGadget myGadget) throws DataAccessException;
	public List<Content> getContentList(String sqlText) throws DataAccessException;
	public int getMaxOrdSeq(String userId) throws DataAccessException;
}