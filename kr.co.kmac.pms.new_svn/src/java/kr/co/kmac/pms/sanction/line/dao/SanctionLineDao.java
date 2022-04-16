package kr.co.kmac.pms.sanction.line.dao;

import org.springframework.dao.DataAccessException;

import kr.co.kmac.pms.sanction.line.data.SanctionLine;

public interface SanctionLineDao {

	public void insert(SanctionLine sanctionLine) throws DataAccessException;

	public void update(SanctionLine sanctionLine) throws DataAccessException;

	public void delete(String id) throws DataAccessException;

	public SanctionLine select(String id) throws DataAccessException;

}
