package kr.co.kmac.pms.system.processtemplate.dao;

import java.util.List;

import kr.co.kmac.pms.system.processtemplate.data.ProcessTemplateDetail;

import org.springframework.dao.DataAccessException;

public interface ProcessTemplateDetailDao {

	public List<ProcessTemplateDetail> getProcessTemplateDetail(String processTemplateCode) throws DataAccessException;

	public ProcessTemplateDetail getProcessTemplateDetail(String processTemplateCode, String workSeq) throws DataAccessException;

	public void createProcessTemplateDetail(ProcessTemplateDetail processTemplateDetail) throws DataAccessException;

	public void updateProcessTemplateDetail(ProcessTemplateDetail processTemplateDetail) throws DataAccessException;

	public void deleteProcessTemplateDetail(String processTemplateCode) throws DataAccessException;

	public void deleteProcessTemplateDetail(String processTemplateCode, String workSeq) throws DataAccessException;

}
