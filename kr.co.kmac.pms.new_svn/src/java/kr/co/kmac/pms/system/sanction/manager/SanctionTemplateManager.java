package kr.co.kmac.pms.system.sanction.manager;

import java.util.List;

import kr.co.kmac.pms.system.exception.SystemException;
import kr.co.kmac.pms.system.sanction.data.SanctionTemplate;

public interface SanctionTemplateManager {

	public void createSanctionTemplate(SanctionTemplate sanctionTemplate) throws SystemException;

	public void deleteSanctionTemplate(String approvalType) throws SystemException;

	public List<SanctionTemplate> getSanctionTemplate() throws SystemException;

	public List<SanctionTemplate> getSanctionTemplate(boolean useYn) throws SystemException;

	public List<SanctionTemplate> getSanctionTemplate(boolean useYn, boolean useMobile) throws SystemException;

	public List<SanctionTemplate> getSanctionTemplate(boolean useYn, String sanctionType) throws SystemException;

	public SanctionTemplate getSanctionTemplate(String approvalType) throws SystemException;
	
	public SanctionTemplate getSanctionTemplate2(String approvalType, String seq) throws SystemException;
	
	public SanctionTemplate getSanctionTemplate3(String approvalType, String seq) throws SystemException;
	
	public SanctionTemplate getSanctionTemplate(String approvalType, String type) throws SystemException;

	public void updateSanctionTemplate(SanctionTemplate sanctionTemplate) throws SystemException;

}
