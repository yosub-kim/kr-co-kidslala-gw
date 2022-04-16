/*
 * $Id: SanctionDocManager.java,v 1.6 2013/10/17 13:07:51 cvs Exp $
 * created by    : jiwoongLee
 * creation-date : 2006. 3. 25.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.sanction.general.manager;

import java.util.HashMap;

import kr.co.kmac.pms.sanction.exception.SanctionException;
import kr.co.kmac.pms.sanction.general.data.SanctionDoc;

/**
 * TODO 클래스 설명
 * 
 * @author jiwoongLee
 * @version $Id: SanctionDocManager.java,v 1.6 2013/10/17 13:07:51 cvs Exp $
 */
public interface SanctionDocManager {

	public HashMap<String, String> draftSaveGeneralSanction(String workType, SanctionDoc sanctionDoc) throws SanctionException;

	public HashMap<String, String> createGeneralSanctionWork(String workType, SanctionDoc sanctionDoc) throws SanctionException;

	public HashMap<String, String> executeGeneralSanctionWork(SanctionDoc sanctionDoc) throws SanctionException;

	public HashMap<String, String> executeRefWork(SanctionDoc sanctionDoc, String assigneeSsn) throws SanctionException;

	public SanctionDoc getGeneralSanctionDoc(String projectCode, String approvalType, String seq) throws SanctionException;

	public SanctionDoc getGeneralSanctionDoc(String projectCode) throws SanctionException;

	public void deleteGeneralSanctionDoc(SanctionDoc sanctionDoc) throws SanctionException;
	
	public void insertGeneralSanctionDocLog(SanctionDoc sanctionDoc) throws SanctionException;

	public void saveGeneralSanctionDoc(SanctionDoc sanctionDoc) throws SanctionException;

	public void entrustGeneralSanctionDoc(SanctionDoc sanctionDoc) throws SanctionException;

	public boolean isIngSanctionExist(String projectCode, String approvalType) throws SanctionException;
}
