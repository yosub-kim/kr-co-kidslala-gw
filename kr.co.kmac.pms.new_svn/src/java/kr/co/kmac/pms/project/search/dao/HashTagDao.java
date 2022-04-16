package kr.co.kmac.pms.project.search.dao;

import java.util.List;

import kr.co.kmac.pms.project.search.data.HashTag;

import org.springframework.dao.DataAccessException;

public interface HashTagDao {

	public List<HashTag> select(String projectCode, String isShow) throws DataAccessException;

	public List<HashTag> select(String projectCode) throws DataAccessException;
	
	public List<HashTag> select3 (String expertSsn, String isShow) throws DataAccessException;

	public int insert(HashTag hashTag) throws DataAccessException;
	
	public int insert2(HashTag hashTag) throws DataAccessException;
	
	public int insert3(HashTag hashTag) throws DataAccessException;
	
	public int delete2(HashTag hashTag) throws DataAccessException;

	public int delete(String projectCode) throws DataAccessException;

	public int delete(String projectCode, String uuid) throws DataAccessException;
	
	public int delete3(String expertSsn, String uuid) throws DataAccessException;

	public  List<HashTag> select2(String bbsId, String seq, String ssn) throws DataAccessException;
}
