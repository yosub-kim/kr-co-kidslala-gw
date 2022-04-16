package kr.co.kmac.pms.system.gadget.manager;

import java.util.ArrayList;
import java.util.List;

import kr.co.kmac.pms.system.gadget.dao.GadgetDao;
import kr.co.kmac.pms.system.gadget.data.Gadget;
import kr.co.kmac.pms.system.gadget.data.Content;
import kr.co.kmac.pms.system.gadget.data.MyGadget;
import kr.co.kmac.pms.system.exception.SystemException;

public interface GadgetManager {
	public List<Gadget> getMyGadgetList(String userId, String gadgetType ) throws SystemException;
	public List<Gadget> getMyGadgetListByRole(String role, String gadgetType ) throws SystemException;
	public List<Gadget> getMyMobileGadgetListByRole(String role) throws SystemException;
	public List<Gadget> getMyWideGadgetList(String userId) throws SystemException;
	public List<Gadget> getGadgetList(String gadgetId) throws SystemException;
	public List<Gadget> getGadgetList(String gadgetId, String gadgetRole, String gadgetName) throws SystemException;
	
	public List<Gadget> getNotSelectedGadgetList(String userId) throws SystemException;
	
	public void createGadget(Gadget gadget) throws SystemException;
	
	public void updateGadget (Gadget gadget) throws SystemException;

	public void deleteGadget (Gadget gadget) throws SystemException;
	
	public void appendMyGadget(MyGadget myGadget) throws SystemException;
	
	public void updateMyGadget(MyGadget myGadget) throws SystemException;

	public void deleteMyGadget(MyGadget myGadget) throws SystemException;

	public List<Content> getContentList(String sqlText) throws SystemException;
	
	public int getMaxOrdSeq(String userId) throws SystemException;
}