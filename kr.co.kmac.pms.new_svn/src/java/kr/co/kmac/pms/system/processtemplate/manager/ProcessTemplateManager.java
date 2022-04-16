package kr.co.kmac.pms.system.processtemplate.manager;

import java.util.List;

import javax.transaction.SystemException;

import kr.co.kmac.pms.system.processtemplate.data.ProcessTemplate;
import kr.co.kmac.pms.system.processtemplate.data.ProcessTemplateAttach;
import kr.co.kmac.pms.system.processtemplate.data.ProcessTemplateDetail;

public interface ProcessTemplateManager {

	public List<ProcessTemplate> getProcessTemplateList(String processTemplateName) throws SystemException;

	public ProcessTemplate getProcessTemplate(String processTemplateCode) throws SystemException;

	public void createProcessTemplate(ProcessTemplate processTemplate) throws SystemException;

	public void updateProcessTemplate(ProcessTemplate processTemplate) throws SystemException;

	public void deleteProcessTemplate(String processTemplateCode) throws SystemException;

	public List<ProcessTemplateDetail> getProcessTemplateDetail(String processTemplateCode) throws SystemException;

	public ProcessTemplateDetail getProcessTemplateDetail(String processTemplateCode, String workSeq) throws SystemException;

	public void createProcessTemplateDetail(ProcessTemplateDetail processTemplateDetail) throws SystemException;

	public void createProcessTemplateDetail(String processTemplateCode, List<ProcessTemplateDetail> processTemplateDetailList) throws SystemException;

	public void updateProcessTemplateDetail(ProcessTemplateDetail processTemplateDetail) throws SystemException;

	public void deleteProcessTemplateDetail(String processTemplateCode, String workSeq) throws SystemException;

	public List<ProcessTemplateAttach> getProcessTemplateAttach(String processTemplateCode, String workSeq) throws SystemException;

	public ProcessTemplateAttach getProcessTemplateAttach(String processTemplateCode, String workSeq, String attachSeq) throws SystemException;

	public void createProcessTemplateAttach(String processTemplateCode, String workSeq, List<ProcessTemplateAttach> processTemplateAttachList)
			throws SystemException;

	public void updateProcessTemplateAttach(ProcessTemplateAttach processTemplateAttach) throws SystemException;

	public void deleteProcessTemplateAttach(String processTemplateCode, String workSeq, String attachSeq) throws SystemException;

}
