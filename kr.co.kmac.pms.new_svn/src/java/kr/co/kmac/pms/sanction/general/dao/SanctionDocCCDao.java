/*
 * $Id: SanctionDocCCDao.java,v 1.2 2009/11/01 09:31:41 cvs1 Exp $
 * created by    : jiwoongLee
 * creation-date : 2006. 3. 25.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.sanction.general.dao;

import java.util.List;

import kr.co.kmac.pms.sanction.general.data.SanctionDocCC;

import org.springframework.dao.DataAccessException;

/**
 * TODO 클래스 설명
 * 
 * @author jiwoongLee
 * @version $Id: SanctionDocCCDao.java,v 1.2 2009/11/01 09:31:41 cvs1 Exp $
 */
public interface SanctionDocCCDao {

	public void insert(String seq, List<SanctionDocCC> sanctionDocCCList) throws DataAccessException;

	public void update(SanctionDocCC sanctionDocCC) throws DataAccessException;

	public void update(String seq, List<SanctionDocCC> sanctionDocCCList) throws DataAccessException;

	public void delete(String seq) throws DataAccessException;

	public void delete(String seq, String refMemberSsn) throws DataAccessException;

	public List<SanctionDocCC> select(String seq) throws DataAccessException;

	public SanctionDocCC select(String seq, String refMemberSsn) throws DataAccessException;

}
