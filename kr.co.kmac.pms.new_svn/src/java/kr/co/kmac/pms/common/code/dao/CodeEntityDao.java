package kr.co.kmac.pms.common.code.dao;

import java.util.List;

import kr.co.kmac.pms.common.code.data.Code;
import kr.co.kmac.pms.common.code.data.CodeEntity;
import kr.co.kmac.pms.common.code.exception.CodeException;

import org.springframework.dao.DataAccessException;

public interface CodeEntityDao {

	public void createCodeEntity(CodeEntity codeEntity) throws DataAccessException;

	public void updateCodeEntity(String tableName, String key1, String key2, String key3, CodeEntity codeEntity) throws DataAccessException;

	public CodeEntity getCodeEntity(String key1) throws DataAccessException;

	public CodeEntity getCodeEntity(String tableName, String key1) throws DataAccessException;

	public CodeEntity getCodeEntity(String tableName, String key1, String key2) throws DataAccessException;

	public CodeEntity getCodeEntity(String tableName, String key1, String key2, String key3) throws DataAccessException;

	public List<CodeEntity> findCodeEntityList(String tableName) throws DataAccessException;

	public List<CodeEntity> findCodeEntityList2(String tableName, String data3) throws DataAccessException;

	public List findRelationWithKmac(String companyName) throws DataAccessException;

	public List<Code> findCodeEntitySimpleList(String tableName) throws CodeException;

	public void deleteCodeEntity(String tableName) throws DataAccessException;

	public void deleteCodeEntity(String tableName, String key1) throws DataAccessException;

}
