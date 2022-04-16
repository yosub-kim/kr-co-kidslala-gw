package kr.co.kmac.pms.common.code.dao;

import java.util.List;

import kr.co.kmac.pms.common.code.data.CodeDefinition;

import org.springframework.dao.DataAccessException;

public interface CodeDefinitionDao {

	public void createCodeDefinition(CodeDefinition codeDefinition) throws DataAccessException;

	public void updateCodeDefinition(CodeDefinition codeDefinition) throws DataAccessException;

	public CodeDefinition getCodeDefinition(String tableName) throws DataAccessException;

	public List<CodeDefinition> getCodeDefinition() throws DataAccessException;
	
	public List<CodeDefinition> getCodeDefinition_eun() throws DataAccessException;

	public void deleteCodeDefinition(String tableName) throws DataAccessException;

}
