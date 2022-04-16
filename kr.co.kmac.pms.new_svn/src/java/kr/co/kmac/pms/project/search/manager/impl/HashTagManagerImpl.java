package kr.co.kmac.pms.project.search.manager.impl;

import java.util.List;

import kr.co.kmac.pms.project.exception.ProjectException;
import kr.co.kmac.pms.project.search.dao.HashTagDao;
import kr.co.kmac.pms.project.search.data.HashTag;
import kr.co.kmac.pms.project.search.manager.HashTagManager;

public class HashTagManagerImpl implements HashTagManager {

	private HashTagDao hashTagDao;

	@Override
	public List<HashTag> getHashTag(String projectCode, String isShow)
			throws ProjectException {
		return this.getHashTagDao().select(projectCode, isShow);
	}
	
	@Override
	public List<HashTag> getExpertHashTag(String expertSsn, String isShow) throws ProjectException {
		return this.getHashTagDao().select3(expertSsn, isShow);
	}
	
	@Override
	public  List<HashTag> getRecommendCnt(String bbsId, String seq, String ssn) throws ProjectException{
		return this.getHashTagDao().select2(bbsId, seq, ssn);
	}

	@Override
	public void addHashTag(HashTag hashTag) throws ProjectException {
		this.getHashTagDao().insert(hashTag);
	}
	
	@Override
	public void addExpertHashTag(HashTag hashTag) throws ProjectException {
		this.getHashTagDao().insert3(hashTag);
	}
	
	@Override
	public void addRecommend(HashTag hashTag) throws ProjectException {
		this.getHashTagDao().insert2(hashTag);
	}
	
	@Override
	public void deleteRecommend(HashTag hashTag) throws ProjectException {
		this.getHashTagDao().delete2(hashTag);
	}

	@Override
	public void deleteAllHashTag(String projectCode) throws ProjectException {
		this.getHashTagDao().delete(projectCode);
	}

	@Override
	public void deleteHashTag(String projectCode, String uuid)
			throws ProjectException {
		this.getHashTagDao().delete(projectCode, uuid);
	}
	
	@Override
	public void deleteExpertHashTag(String expertSsn, String uuid) throws ProjectException{
		this.getHashTagDao().delete3(expertSsn, uuid);
	}

	public HashTagDao getHashTagDao() {
		return hashTagDao;
	}

	public void setHashTagDao(HashTagDao hashTagDao) {
		this.hashTagDao = hashTagDao;
	}

}
