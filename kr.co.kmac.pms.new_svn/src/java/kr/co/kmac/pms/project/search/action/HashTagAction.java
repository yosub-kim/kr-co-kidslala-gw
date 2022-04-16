package kr.co.kmac.pms.project.search.action;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.kmac.pms.common.ajax.AjaxUtil;
import kr.co.kmac.pms.common.repository.action.RepositoryDispatchActionSupport;
import kr.co.kmac.pms.common.util.SessionUtils;
import kr.co.kmac.pms.project.search.data.HashTag;
import kr.co.kmac.pms.project.search.manager.HashTagManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.bind.ServletRequestUtils;

/**
 * @struts.action name="hashTagAction" path="/action/HashTagAction"
 *                parameter="mode" scope="request"
 */
public class HashTagAction extends RepositoryDispatchActionSupport {

	private static final Log logger = LogFactory.getLog(HashTagAction.class);
	private HashTagManager hashTagManager;

	public void addHashTag(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String projectCode = ServletRequestUtils.getRequiredStringParameter(request, "projectCode");
		String hashTag = ServletRequestUtils.getRequiredStringParameter( request, "hashTag");
		String tagType = ServletRequestUtils.getRequiredStringParameter( request, "tagType");

		try {
			HashTag hashTagObj = new HashTag();
			hashTagObj.setUuid(UUID.randomUUID().toString());
			hashTagObj.setProjectCode(projectCode);
			hashTagObj.setHashTag(hashTag);
			hashTagObj.setIsShow("SHOW");
			hashTagObj.setTagType(tagType);
			hashTagObj.setCreaterSsn(SessionUtils.getUsername(request));

			this.getHashTagManager().addHashTag(hashTagObj);
			AjaxUtil.successWrite(response);
		} catch (Exception e) {
			AjaxUtil.failWrite(response);
			logger.error(e.getMessage(), e);
		}
	}
	
	public void addExpertHashTag(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String expertSsn = ServletRequestUtils.getRequiredStringParameter(request, "expertSsn");
		String hashTag = ServletRequestUtils.getRequiredStringParameter( request, "hashTag");
		String tagType = ServletRequestUtils.getRequiredStringParameter( request, "tagType");

		try {
			HashTag hashTagObj = new HashTag();
			hashTagObj.setUuid(UUID.randomUUID().toString());
			hashTagObj.setExpertSsn(expertSsn);
			hashTagObj.setHashTag(hashTag);
			hashTagObj.setIsShow("SHOW");
			hashTagObj.setTagType(tagType);
			hashTagObj.setCreaterSsn(SessionUtils.getUsername(request));

			this.getHashTagManager().addExpertHashTag(hashTagObj);
			AjaxUtil.successWrite(response);
		} catch (Exception e) {
			AjaxUtil.failWrite(response);
			logger.error(e.getMessage(), e);
		}
	}

	public void deleteHashTag(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String projectCode = ServletRequestUtils.getRequiredStringParameter(request, "projectCode");
		String uuid = ServletRequestUtils.getRequiredStringParameter(request, "uuid");
		
		try {
			this.getHashTagManager().deleteHashTag(projectCode, uuid);
			AjaxUtil.successWrite(response);
		} catch (Exception e) {
			AjaxUtil.failWrite(response);
			logger.error(e.getMessage(), e);
		}
	}
	
	public void deleteExpertHashTag(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		String expertSsn = ServletRequestUtils.getRequiredStringParameter(request, "expertSsn");
		String uuid = ServletRequestUtils.getRequiredStringParameter(request, "uuid");
		
		try {
			this.getHashTagManager().deleteExpertHashTag(expertSsn, uuid);
			AjaxUtil.successWrite(response);
		} catch (Exception e) {
			AjaxUtil.failWrite(response);
			logger.error(e.getMessage(), e);
		}
	}

	public void updateRecommend(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String bbsId = ServletRequestUtils.getRequiredStringParameter(request, "bbsId");
		String seq = ServletRequestUtils.getRequiredStringParameter( request, "seq");
		String ssn = SessionUtils.getUsername(request);

		// 삭제 추가 할 것
		try {
			HashTag hashTagObj = new HashTag();
			hashTagObj.setCreaterSsn(ssn);
			hashTagObj.setBbsId(bbsId);
			hashTagObj.setSeq(seq);

			int count = this.getHashTagManager().getRecommendCnt(bbsId, seq, ssn).size();
			System.out.println(this.getHashTagManager().getRecommendCnt(bbsId, seq, ssn).size());
			if(count > 0){
				this.getHashTagManager().deleteRecommend(hashTagObj);
			} else{
				this.getHashTagManager().addRecommend(hashTagObj);
			}
			AjaxUtil.successWrite(response);
		} catch (Exception e) {
			AjaxUtil.failWrite(response);
			logger.error(e.getMessage(), e);
		}
	}

	/*public void countRecommend(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String bbsId = ServletRequestUtils.getRequiredStringParameter(request, "bbsId");
		String seq = ServletRequestUtils.getRequiredStringParameter( request, "seq");
		
		try {
			this.getHashTagManager().getRecommendCnt(bbsId, seq);
			System.out.println(this.getHashTagManager().getRecommendCnt(bbsId, seq));
			AjaxUtil.successWrite(response);
		} catch (Exception e) {
			AjaxUtil.failWrite(response);
			logger.error(e.getMessage(), e);
		}
	}*/

	
	public HashTagManager getHashTagManager() {
		return hashTagManager;
	}

	public void setHashTagManager(HashTagManager hashTagManager) {
		this.hashTagManager = hashTagManager;
	}

}
