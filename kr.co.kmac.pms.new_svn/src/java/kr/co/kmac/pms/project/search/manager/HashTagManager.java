package kr.co.kmac.pms.project.search.manager;

import java.util.List;

import kr.co.kmac.pms.project.exception.ProjectException;
import kr.co.kmac.pms.project.search.data.HashTag;

/**
 * @author jiwoong
 * @jobDate &2008. 6. 10.
 * @description : PMS 프로젝트 기본정보에 협력기관 등록을 위한 Manager
 */
public interface HashTagManager {

	public List<HashTag> getHashTag(String projectCode, String isShow)
			throws ProjectException;

	public List<HashTag> getRecommendCnt(String bbsId, String seq, String ssn) throws ProjectException;
	
	public List<HashTag> getExpertHashTag(String ssn, String isShow) throws ProjectException;

	public void addHashTag(HashTag hashTag) throws ProjectException;
	
	public void addExpertHashTag(HashTag hashTag) throws ProjectException;
	
	public void addRecommend(HashTag hashTag) throws ProjectException;
	
	public void deleteRecommend(HashTag hashTag) throws ProjectException;

	public void deleteAllHashTag(String projectCode) throws ProjectException;

	public void deleteHashTag(String projectCode, String uuid)
			throws ProjectException;
	
	public void deleteExpertHashTag(String expertSsn, String uuid) throws ProjectException;
}
