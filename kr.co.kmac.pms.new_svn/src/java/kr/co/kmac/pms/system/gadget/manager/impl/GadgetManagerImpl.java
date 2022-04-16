package kr.co.kmac.pms.system.gadget.manager.impl;

import java.util.List;

import kr.co.kmac.pms.common.util.SessionUtils;
import kr.co.kmac.pms.expertpool.dao.ExpertPoolDao;
import kr.co.kmac.pms.system.exception.SystemException;
import kr.co.kmac.pms.system.gadget.dao.GadgetDao;
import kr.co.kmac.pms.system.gadget.data.Content;
import kr.co.kmac.pms.system.gadget.data.Gadget;
import kr.co.kmac.pms.system.gadget.data.MyGadget;
import kr.co.kmac.pms.system.gadget.manager.GadgetManager;

public class GadgetManagerImpl implements GadgetManager {
	private GadgetDao gadgetDao;
	private ExpertPoolDao expertPoolDao;
	public GadgetDao getGadgetDao() {
		return gadgetDao;
	}

	public void setGadgetDao(GadgetDao gadgetDao) {
		this.gadgetDao = gadgetDao;
	}
	
	public ExpertPoolDao getExpertPoolDao() {
		return expertPoolDao;
	}

	public void setExpertPoolDao(ExpertPoolDao expertPoolDao) {
		this.expertPoolDao = expertPoolDao;
	}

	public List<Gadget> getMyGadgetList(String userId, String gadgetType) throws SystemException {
		if(gadgetType.equals("")) gadgetType = "%%";
		return this.getGadgetDao().getMyGadgetList(userId, gadgetType);
	}
	
	public List<Gadget> getMyGadgetListByRole(String role, String gadgetType) throws SystemException {
		if(gadgetType.equals("")) gadgetType = "%%";
		return this.getGadgetDao().getMyGadgetListByRole(role, gadgetType);
	}
	
	public List<Gadget> getMyMobileGadgetListByRole(String role) throws SystemException {
		return this.getGadgetDao().getMyMobileGadgetListByRole(role);
	}
	
	public List<Gadget> getMyWideGadgetList(String userId) throws SystemException {
		return this.getGadgetDao().getMyWideGadgetList(userId);
	}
	
	public List<Gadget> getGadgetList(String gadgetId) throws SystemException {
		return this.getGadgetDao().getGadgetList(gadgetId);
	}
	
	public List<Gadget> getGadgetList(String gadgetId, String gadgetRole, String gadgetName) throws SystemException {
		return this.getGadgetDao().getGadgetList(gadgetId, gadgetRole, gadgetName);
	}
	
	public List<Gadget> getNotSelectedGadgetList(String userId) throws SystemException {
		return this.getGadgetDao().getNotSelectGadgetList(userId);
	}
	
	public void createGadget(Gadget gadget) throws SystemException {
		this.getGadgetDao().createGadget(gadget);
	}
	
	public void updateGadget (Gadget gadget) throws SystemException {
		this.getGadgetDao().updateGadget(gadget);
	}
	
	public void deleteGadget (Gadget gadget) throws SystemException {
		this.getGadgetDao().deleteGadget(gadget);
	}
	
	public void appendMyGadget(MyGadget myGadget) throws SystemException {
		this.getGadgetDao().appendMyGadget(myGadget);
	}
	
	public void updateMyGadget(MyGadget myGadget) throws SystemException {
		this.getGadgetDao().updateMyGadget(myGadget);
	}

	public void deleteMyGadget(MyGadget myGadget) throws SystemException {
		this.getGadgetDao().deleteMyGadget(myGadget);
	}

	public List<Content> getContentList(String sqlText) throws SystemException {
		if(sqlText.indexOf("#DEPT#") > 0)			
			sqlText = sqlText.replaceAll("#DEPT#", getExpertPoolDao().retrieve(SessionUtils.getUserObjext().getId()).getDept());
		if(sqlText.indexOf("#USER#") > 0)
			sqlText = sqlText.replaceAll("#USER#", SessionUtils.getUserObjext().getId());
		if(sqlText.indexOf("#UID#") > 0){
			String userId = this.getExpertPoolDao().retrieve(SessionUtils.getUserObjext().getId()).getUserId();
			sqlText = sqlText.replaceAll("#UID#", userId);
		}
		//System.out.print(sqlText);
		return this.getGadgetDao().getContentList(sqlText);
	}

	public int getMaxOrdSeq(String userId) throws SystemException {
		return this.getGadgetDao().getMaxOrdSeq(userId);
	}
}