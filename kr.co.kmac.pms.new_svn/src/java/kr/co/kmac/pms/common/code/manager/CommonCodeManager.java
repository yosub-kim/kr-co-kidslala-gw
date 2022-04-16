package kr.co.kmac.pms.common.code.manager;

import java.util.List;

import kr.co.kmac.pms.common.code.data.Code;
import kr.co.kmac.pms.common.code.data.CodeDefinition;
import kr.co.kmac.pms.common.code.data.CodeEntity;
import kr.co.kmac.pms.common.code.exception.CodeException;

public interface CommonCodeManager {

	public void createCodeDefinition(CodeDefinition codeDefinition) throws CodeException;

	public void deleteCodeDefinition(String tableName) throws CodeException;

	public void updateCodeDefinition(CodeDefinition codeDefinition) throws CodeException;

	public CodeDefinition getCodeDefinition(String tableName) throws CodeException;

	public List<CodeDefinition> getCodeDefinition() throws CodeException;
	
	public List<CodeDefinition> getCodeDefinition_eun() throws CodeException;
	
	public void createCodeEntity(CodeEntity codeEntity) throws CodeException;

	public void updateCodeEntity(String tableName, String key1, String key2, String key3, CodeEntity codeEntity) throws CodeException;

	public void deleteCodeEntity(String tableName) throws CodeException;

	public void deleteCodeEntity(String tableName, String key1) throws CodeException;

	public List<CodeEntity> getCodeEntityList(String tableName) throws CodeException;

	/**
	 * 우편번호 검색
	 * 
	 * @param tableName
	 * @param data3
	 * @return
	 * @throws CodeException
	 */
	public List<CodeEntity> getCodeEntityList2(String tableName, String data3) throws CodeException;

	public List getRelationWithKmacList(String companyName) throws CodeException;

	public List<Code> getCodeEntitySimpleList(String tableName) throws CodeException;

	public CodeEntity getCodeEntity(String key1) throws CodeException;

	public CodeEntity getCodeEntity(String tableName, String key1) throws CodeException;

	public CodeEntity getCodeEntity(String tableName, String key1, String key2) throws CodeException;

	public CodeEntity getCodeEntity(String tableName, String key1, String key2, String key3) throws CodeException;

}