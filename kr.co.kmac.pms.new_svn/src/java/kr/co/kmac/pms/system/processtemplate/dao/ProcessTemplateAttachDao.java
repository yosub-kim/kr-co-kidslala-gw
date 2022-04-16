package kr.co.kmac.pms.system.processtemplate.dao;

import java.util.List;

import kr.co.kmac.pms.system.processtemplate.data.ProcessTemplateAttach;

import org.springframework.dao.DataAccessException;

public interface ProcessTemplateAttachDao {

	public List<ProcessTemplateAttach> getProcessTemplateAttach(String processTemplateCode, String workSeq) throws DataAccessException;

	public ProcessTemplateAttach getProcessTemplateAttach(String processTemplateCode, String workSeq, String attachSeq) throws DataAccessException;

	public void createProcessTemplateAttach(ProcessTemplateAttach processTemplateAttach) throws DataAccessException;

	public void updateProcessTemplateAttach(ProcessTemplateAttach processTemplateAttach) throws DataAccessException;

	public void deleteProcessTemplateAttach(String processTemplateCode, String workSeq) throws DataAccessException;

	public void deleteProcessTemplateAttach(String processTemplateCode, String workSeq, String attachSeq) throws DataAccessException;
}
