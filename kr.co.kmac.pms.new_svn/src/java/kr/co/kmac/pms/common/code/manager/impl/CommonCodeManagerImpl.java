package kr.co.kmac.pms.common.code.manager.impl;

import java.util.List;

import kr.co.kmac.pms.common.code.dao.CodeDefinitionDao;
import kr.co.kmac.pms.common.code.dao.CodeEntityDao;
import kr.co.kmac.pms.common.code.data.Code;
import kr.co.kmac.pms.common.code.data.CodeDefinition;
import kr.co.kmac.pms.common.code.data.CodeEntity;
import kr.co.kmac.pms.common.code.exception.CodeException;
import kr.co.kmac.pms.common.code.manager.CommonCodeManager;

/**
 * 공통 코드 관리 메니저
 * 
 * @author jiwoongLee
 * @version $Id: CommonCodeManagerImpl.java,v 1.3 2010/01/23 12:46:55 cvs3 Exp $
 */
public class CommonCodeManagerImpl implements CommonCodeManager {
	private CodeDefinitionDao codeDefinitionDao;
	private CodeEntityDao codeEntityDao;

	@Override
	public void createCodeDefinition(CodeDefinition codeDefinition) throws CodeException {
		this.getCodeDefinitionDao().createCodeDefinition(codeDefinition);
	}

	@Override
	public void createCodeEntity(CodeEntity codeEntity) throws CodeException {
		this.getCodeEntityDao().createCodeEntity(codeEntity);
	}

	@Override
	public void deleteCodeDefinition(String tableName) throws CodeException {
		this.getCodeDefinitionDao().deleteCodeDefinition(tableName);
	}

	@Override
	public void deleteCodeEntity(String tableName, String key1) throws CodeException {
		this.getCodeEntityDao().deleteCodeEntity(tableName, key1);
	}

	@Override
	public void deleteCodeEntity(String tableName) throws CodeException {
		this.getCodeEntityDao().deleteCodeEntity(tableName);
	}

	@Override
	public List<CodeDefinition> getCodeDefinition() throws CodeException {
		return this.getCodeDefinitionDao().getCodeDefinition();
	}
	
	@Override
	public List<CodeDefinition> getCodeDefinition_eun() throws CodeException {
		return this.getCodeDefinitionDao().getCodeDefinition_eun();
	}
	
	@Override
	public CodeDefinition getCodeDefinition(String tableName) throws CodeException {
		return this.getCodeDefinitionDao().getCodeDefinition(tableName);
	}

	@Override
	public CodeEntity getCodeEntity(String tableName, String key1) throws CodeException {
		return this.getCodeEntityDao().getCodeEntity(tableName, key1);
	}

	@Override
	public CodeEntity getCodeEntity(String tableName, String key1, String key2) throws CodeException {
		return this.getCodeEntityDao().getCodeEntity(tableName, key1, key2);
	}

	@Override
	public CodeEntity getCodeEntity(String tableName, String key1, String key2, String key3) throws CodeException {
		return this.getCodeEntityDao().getCodeEntity(tableName, key1, key2, key3);
	}

	@Override
	public List<CodeEntity> getCodeEntityList(String tableName) throws CodeException {
		return this.getCodeEntityDao().findCodeEntityList(tableName);
	}

	@Override
	public List<CodeEntity> getCodeEntityList2(String tableName, String data3) throws CodeException {
		return this.getCodeEntityDao().findCodeEntityList2(tableName, data3);
	}

	public List getRelationWithKmacList(String companyName) throws CodeException {
		return this.getCodeEntityDao().findRelationWithKmac(companyName);
	}

	@Override
	public List<Code> getCodeEntitySimpleList(String tableName) throws CodeException {
		return this.getCodeEntityDao().findCodeEntitySimpleList(tableName);
	}

	@Override
	public CodeEntity getCodeEntity(String key1) throws CodeException {
		return this.getCodeEntityDao().getCodeEntity(key1);
	}

	@Override
	public void updateCodeDefinition(CodeDefinition codeDefinition) throws CodeException {
		this.getCodeDefinitionDao().updateCodeDefinition(codeDefinition);
	}

	@Override
	public void updateCodeEntity(String tableName, String key1, String key2, String key3, CodeEntity codeEntity) throws CodeException {
		this.getCodeEntityDao().updateCodeEntity(tableName, key1, key2, key3, codeEntity);
	}

	public CodeDefinitionDao getCodeDefinitionDao() {
		return codeDefinitionDao;
	}

	public void setCodeDefinitionDao(CodeDefinitionDao codeDefinitionDao) {
		this.codeDefinitionDao = codeDefinitionDao;
	}

	public CodeEntityDao getCodeEntityDao() {
		return codeEntityDao;
	}

	public void setCodeEntityDao(CodeEntityDao codeEntityDao) {
		this.codeEntityDao = codeEntityDao;
	}

}