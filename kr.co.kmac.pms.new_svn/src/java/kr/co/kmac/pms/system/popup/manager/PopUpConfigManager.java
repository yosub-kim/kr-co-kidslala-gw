/*
 * $Id: PopUpConfigManager.java,v 1.1 2009/09/19 11:15:41 cvs3 Exp $
 * created by    : Administrator
 * creation-date : 2005. 10. 27
 * =========================================================
 * Copyright (c) 2005 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.system.popup.manager;

import kr.co.kmac.pms.system.popup.dao.PopUpConfigDAO;
import kr.co.kmac.pms.system.popup.data.PopUpConfig;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 팝업 설정을 하기 위한 Manager Class
 * @author Administrator
 * @version $Id: PopUpConfigManager.java,v 1.1 2009/09/19 11:15:41 cvs3 Exp $
 */
public class PopUpConfigManager {

	public static Log log = LogFactory.getLog(PopUpConfigManager.class);
	private PopUpConfigDAO popUpConfigDAO;

	/**
	 * @return Returns the popUpConfigDAO.
	 */
	public PopUpConfigDAO getPopUpConfigDAO() {
		return this.popUpConfigDAO;
	}

	/**
	 * @param popUpConfigDAO The popUpConfigDAO to set.
	 */
	public void setPopUpConfigDAO(PopUpConfigDAO popUpConfigDAO) {
		this.popUpConfigDAO = popUpConfigDAO;
	}

	/**
	 * 팝업의 상태를 반환
	 * 
	 * @return
	 */
	public String isPopUpEnable() {
		String resVal = null;

		try {
			PopUpConfig popUpConfig = getPopUpConfigDAO().getPopUp();
			resVal = popUpConfig.getIsEnable();
		} catch (Exception e) {
			log.info(e);
		}
		return resVal;
	}

	/**
	 * 팝업의 넓이 반환
	 * @return
	 */
	public String getPopUpWidth() {
		String resVal = null;

		try {
			PopUpConfig popUpConfig = getPopUpConfigDAO().getPopUp();
			resVal = popUpConfig.getWidth();
		} catch (Exception e) {
			log.info(e);
		}
		return resVal;
	}

	/**
	 * 팝업의 높이 반환
	 * @return
	 */
	public String getPopUpHeight() {
		String resVal = null;

		try {
			PopUpConfig popUpConfig = getPopUpConfigDAO().getPopUp();
			resVal = popUpConfig.getHeight();
		} catch (Exception e) {
			log.info(e);
		}
		return resVal;
	}

	/**
	 * 팝업의 내용 반환
	 * @return
	 */
	public String getPopUpContent() {
		String resVal = null;

		try {
			PopUpConfig popUpConfig = getPopUpConfigDAO().getPopUp();
			resVal = popUpConfig.getContent();
		} catch (Exception e) {
			log.info(e);
		}
		return resVal;
	}
}
