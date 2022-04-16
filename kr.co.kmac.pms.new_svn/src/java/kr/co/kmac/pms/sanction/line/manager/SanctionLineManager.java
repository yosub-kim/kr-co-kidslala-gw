package kr.co.kmac.pms.sanction.line.manager;

import java.util.List;

import kr.co.kmac.pms.sanction.exception.SanctionException;
import kr.co.kmac.pms.sanction.line.data.SanctionLine;
import kr.co.kmac.pms.expertpool.data.ExpertPool;

public interface SanctionLineManager {

	public void insertSanctionLine(SanctionLine sanctionLine) throws SanctionException;

	public void updateSanctionLine(SanctionLine sanctionLine) throws SanctionException;

	public void deleteSanctionLine(String id) throws SanctionException;

	public SanctionLine selectSanctionLine(String id) throws SanctionException;
	
	public List<ExpertPool> selectUpdayOfficer(String id) throws SanctionException;

}
