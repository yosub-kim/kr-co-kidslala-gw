/*
 * $Id: CcoTargetManager.java,v 1.1 2013/03/25 15:19:47 cvs Exp $ * created by    : yhyim
 * creation-date : 2013. 3. 15.
 * =========================================================
 * Copyright KMAC, Inc. All rights reserved.
 */
package kr.co.kmac.pms.sanction.general.manager;

import kr.co.kmac.pms.sanction.exception.SanctionException;
import kr.co.kmac.pms.sanction.general.data.CcoTarget;

/**
 * TODO 클래스 설명
 * 
 * @author yhyim
 * @version $Id: CcoTargetManager.java,v 1.1 2013/03/25 15:19:47 cvs Exp $
 */
public interface CcoTargetManager {

	public CcoTarget getCcoTarget(String companyCode) throws SanctionException;

	public boolean isCcoTargetExist(String companyCode) throws SanctionException;
}
