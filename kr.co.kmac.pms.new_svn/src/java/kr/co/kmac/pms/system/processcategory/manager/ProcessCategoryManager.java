package kr.co.kmac.pms.system.processcategory.manager;

import java.util.List;

import javax.transaction.SystemException;

import kr.co.kmac.pms.system.processcategory.data.ProcessCategory;

public interface ProcessCategoryManager {

	public List<ProcessCategory> getProcessCategoryList(String processCategoryName) throws SystemException;

	public List<ProcessCategory> getProcessCatogoryList(String processCategoryName, String businessTypeCode, String runningDivCode,
			String businessFunctionType, String processCode) throws SystemException;

	public ProcessCategory getProcessCategory(String processCategoryCode) throws SystemException;

	public void storeProcessCategory(ProcessCategory processCategory) throws SystemException;

	public void updateProcessCategory(ProcessCategory processCategory) throws SystemException;

	public void deleteProcessCategory(String processCategoryCode) throws SystemException;

}
