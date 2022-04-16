package kr.co.kmac.pms.system.sanction.dao;

import java.util.List;

import kr.co.kmac.pms.system.exception.SystemException;
import kr.co.kmac.pms.system.sanction.data.SanctionTemplate;

import org.springframework.dao.DataAccessException;

public interface SanctionTemplateDao {

	public void createSanctionTemplate(SanctionTemplate sanctionTemplate) throws DataAccessException;

	public void updateSanctionTemplate(SanctionTemplate sanctionTemplate) throws DataAccessException;

	public SanctionTemplate getSanctionTemplate(String approvalType) throws DataAccessException;

	public List<SanctionTemplate> getSanctionTemplate() throws DataAccessException;

	public List<SanctionTemplate> getSanctionTemplate(boolean useYn) throws DataAccessException;

	public List<SanctionTemplate> getSanctionTemplate(boolean useYn, boolean useMobile) throws SystemException;

	public List<SanctionTemplate> getSanctionTemplate(boolean useYn, String sanctionType) throws DataAccessException;

	public void deleteSanctionTemplate(String approvalType) throws DataAccessException;

}
