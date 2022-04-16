package kr.co.kmac.pms.system.processtemplate.dao;

import java.util.List;

import kr.co.kmac.pms.system.processtemplate.data.ProcessTemplate;

import org.springframework.dao.DataAccessException;

public interface ProcessTemplateDao {

	public List<ProcessTemplate> getProcessTemplateList() throws DataAccessException;

	public List<ProcessTemplate> getProcessTemplateList(String processTemplateName) throws DataAccessException;

	public ProcessTemplate getProcessTemplate(String processTemplateCode) throws DataAccessException;

	public void createProcessTemplate(ProcessTemplate processTemplate) throws DataAccessException;

	public void updateProcessTemplate(ProcessTemplate processTemplate) throws DataAccessException;

	public void deleteProcessTemplate(String processTemplateCode) throws DataAccessException;

}