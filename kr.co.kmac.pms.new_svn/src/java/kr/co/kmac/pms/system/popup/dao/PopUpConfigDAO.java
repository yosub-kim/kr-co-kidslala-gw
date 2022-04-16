/*
 * $Id: PopUpConfigDAO.java,v 1.1 2009/09/19 11:15:38 cvs3 Exp $
 * created by    : Administrator
 * creation-date : 2005. 10. 27
 * =========================================================
 * Copyright (c) 2005 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.system.popup.dao;

import kr.co.kmac.pms.system.popup.data.PopUpConfig;

/**
 * 팝업 설정을 위한 DAO
 * @author Administrator
 * @version $Id: PopUpConfigDAO.java,v 1.1 2009/09/19 11:15:38 cvs3 Exp $
 */
public interface PopUpConfigDAO {

	public PopUpConfig getPopUp() throws Exception;

	public boolean updatePopUp(PopUpConfig hccPopUpConfig) throws Exception;

}
