package kr.co.kmac.pms.system.processcategory.manager.impl;

import java.util.List;

import javax.transaction.SystemException;

import kr.co.kmac.pms.common.util.IdGenerator;
import kr.co.kmac.pms.system.processcategory.dao.ProcessCategoryDao;
import kr.co.kmac.pms.system.processcategory.data.ProcessCategory;
import kr.co.kmac.pms.system.processcategory.manager.ProcessCategoryManager;

public class ProcessCategoryManagerImpl implements ProcessCategoryManager {

	private ProcessCategoryDao processCategoryDao;

	@Override
	public void deleteProcessCategory(String processCategoryCode) throws SystemException {
		this.getProcessCategoryDao().deleteProcessCatogory(processCategoryCode);
	}

	@Override
	public ProcessCategory getProcessCategory(String processCategoryCode) throws SystemException {
		return this.getProcessCategoryDao().getProcessCatogory(processCategoryCode);
	}

	@Override
	public List<ProcessCategory> getProcessCategoryList(String processCategoryName) throws SystemException {
		return this.getProcessCategoryDao().getProcessCatogoryList(processCategoryName);
	}

	@Override
	public List<ProcessCategory> getProcessCatogoryList(String processCategoryName, String businessTypeCode, String runningDivCode,
			String businessFunctionType, String processCode) throws SystemException {
		return this.getProcessCategoryDao().getProcessCatogoryList(processCategoryName, businessTypeCode, runningDivCode, businessFunctionType,
				processCode);
	}

	@Override
	public void storeProcessCategory(ProcessCategory processCategory) throws SystemException {
		if (processCategory.getprocessCategoryCode().equals("")) {
			processCategory.setprocessCategoryCode(IdGenerator.generate(IdGenerator.PREFIX_CATEGORY));
			this.getProcessCategoryDao().createProcessCatogory(processCategory);
		} else {
			this.getProcessCategoryDao().updateProcessCatogory(processCategory);
		}
	}

	@Override
	public void updateProcessCategory(ProcessCategory processCategory) throws SystemException {
		this.getProcessCategoryDao().updateProcessCatogory(processCategory);
	}

	public ProcessCategoryDao getProcessCategoryDao() {
		return processCategoryDao;
	}

	public void setProcessCategoryDao(ProcessCategoryDao processCategoryDao) {
		this.processCategoryDao = processCategoryDao;
	}

}
