package kr.co.kmac.pms.system.processtemplate.manager.impl;

import java.util.List;

import javax.transaction.SystemException;

import kr.co.kmac.pms.system.processtemplate.dao.ProcessTemplateAttachDao;
import kr.co.kmac.pms.system.processtemplate.dao.ProcessTemplateDao;
import kr.co.kmac.pms.system.processtemplate.dao.ProcessTemplateDetailDao;
import kr.co.kmac.pms.system.processtemplate.data.ProcessTemplate;
import kr.co.kmac.pms.system.processtemplate.data.ProcessTemplateAttach;
import kr.co.kmac.pms.system.processtemplate.data.ProcessTemplateDetail;
import kr.co.kmac.pms.system.processtemplate.manager.ProcessTemplateManager;

public class ProcessTemplateManagerImpl implements ProcessTemplateManager {

	private ProcessTemplateDao processTemplateDao;
	private ProcessTemplateDetailDao processTemplateDetailDao;
	private ProcessTemplateAttachDao processTemplateAttachDao;

	@Override
	public void deleteProcessTemplate(String processTemplateCode) throws SystemException {
		this.getProcessTemplateDao().deleteProcessTemplate(processTemplateCode);
	}

	@Override
	public void deleteProcessTemplateAttach(String processTemplateCode, String workSeq, String attachSeq) throws SystemException {
		if (attachSeq != null)
			this.getProcessTemplateAttachDao().deleteProcessTemplateAttach(processTemplateCode, workSeq, attachSeq);
		else
			this.getProcessTemplateAttachDao().deleteProcessTemplateAttach(processTemplateCode, workSeq);

	}

	@Override
	public void deleteProcessTemplateDetail(String processTemplateCode, String workSeq) throws SystemException {
		if (workSeq != null)
			this.getProcessTemplateDetailDao().deleteProcessTemplateDetail(processTemplateCode, workSeq);
		else
			this.getProcessTemplateDetailDao().deleteProcessTemplateDetail(processTemplateCode);
	}

	@Override
	public ProcessTemplate getProcessTemplate(String processTemplateCode) throws SystemException {
		return this.getProcessTemplateDao().getProcessTemplate(processTemplateCode);
	}

	@Override
	public ProcessTemplateAttach getProcessTemplateAttach(String processTemplateCode, String workSeq, String attachSeq) throws SystemException {
		return this.getProcessTemplateAttachDao().getProcessTemplateAttach(processTemplateCode, workSeq, attachSeq);
	}

	@Override
	public List<ProcessTemplateAttach> getProcessTemplateAttach(String processTemplateCode, String workSeq) throws SystemException {
		return this.getProcessTemplateAttachDao().getProcessTemplateAttach(processTemplateCode, workSeq);
	}

	@Override
	public ProcessTemplateDetail getProcessTemplateDetail(String processTemplateCode, String workSeq) throws SystemException {
		return this.getProcessTemplateDetailDao().getProcessTemplateDetail(processTemplateCode, workSeq);
	}

	@Override
	public List<ProcessTemplateDetail> getProcessTemplateDetail(String processTemplateCode) throws SystemException {
		return this.getProcessTemplateDetailDao().getProcessTemplateDetail(processTemplateCode);
	}

	@Override
	public List<ProcessTemplate> getProcessTemplateList(String processTemplateName) throws SystemException {
		if (processTemplateName != null)
			return this.getProcessTemplateDao().getProcessTemplateList(processTemplateName);
		else
			return this.getProcessTemplateDao().getProcessTemplateList();
	}

	@Override
	public void createProcessTemplate(ProcessTemplate processTemplate) throws SystemException {
		this.getProcessTemplateDao().createProcessTemplate(processTemplate);
	}

	@Override
	public void createProcessTemplateAttach(String processTemplateCode, String workSeq, List<ProcessTemplateAttach> processTemplateAttachList)
			throws SystemException {
		this.deleteProcessTemplateAttach(processTemplateCode, workSeq, null);
		if (processTemplateAttachList != null) {
			for (ProcessTemplateAttach processTemplateAttach : processTemplateAttachList) {
				this.getProcessTemplateAttachDao().createProcessTemplateAttach(processTemplateAttach);
			}
		}
	}

	@Override
	public void createProcessTemplateDetail(ProcessTemplateDetail processTemplateDetail) throws SystemException {
		this.getProcessTemplateDetailDao().createProcessTemplateDetail(processTemplateDetail);
	}

	@Override
	public void createProcessTemplateDetail(String processTemplateCode, List<ProcessTemplateDetail> processTemplateDetailList) throws SystemException {
		this.deleteProcessTemplateDetail(processTemplateCode, null);
		if (processTemplateDetailList != null) {
			for (ProcessTemplateDetail processTemplateDetail : processTemplateDetailList) {
				this.createProcessTemplateDetail(processTemplateDetail);
			}
		}
	}

	@Override
	public void updateProcessTemplate(ProcessTemplate processTemplate) throws SystemException {
		this.getProcessTemplateDao().updateProcessTemplate(processTemplate);
	}

	@Override
	public void updateProcessTemplateAttach(ProcessTemplateAttach processTemplateAttach) throws SystemException {
		this.getProcessTemplateAttachDao().updateProcessTemplateAttach(processTemplateAttach);
	}

	@Override
	public void updateProcessTemplateDetail(ProcessTemplateDetail processTemplateDetail) throws SystemException {
		this.getProcessTemplateDetailDao().updateProcessTemplateDetail(processTemplateDetail);
	}

	public ProcessTemplateDao getProcessTemplateDao() {
		return processTemplateDao;
	}

	public void setProcessTemplateDao(ProcessTemplateDao processTemplateDao) {
		this.processTemplateDao = processTemplateDao;
	}

	public ProcessTemplateDetailDao getProcessTemplateDetailDao() {
		return processTemplateDetailDao;
	}

	public void setProcessTemplateDetailDao(ProcessTemplateDetailDao processTemplateDetailDao) {
		this.processTemplateDetailDao = processTemplateDetailDao;
	}

	public ProcessTemplateAttachDao getProcessTemplateAttachDao() {
		return processTemplateAttachDao;
	}

	public void setProcessTemplateAttachDao(ProcessTemplateAttachDao processTemplateAttachDao) {
		this.processTemplateAttachDao = processTemplateAttachDao;
	}

}
