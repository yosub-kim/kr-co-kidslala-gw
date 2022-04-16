package kr.co.kmac.pms.common.org.action;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.kmac.pms.common.ajax.AjaxUtil;
import kr.co.kmac.pms.common.org.data.User;
import kr.co.kmac.pms.common.org.manager.IEntityManager;
import kr.co.kmac.pms.common.org.manager.IOrgUnitManager;
import kr.co.kmac.pms.common.org.manager.impl.OrgUnitManagerImpl;
import kr.co.kmac.pms.expertpool.data.ExpertPool;
import kr.co.kmac.pms.expertpool.manager.ExpertPoolManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.struts.DispatchActionSupport;

/**
 * @struts.action name="orgTableAction" path="/action/OrgTableAction" parameter="mode" scope="request"
 */
public class OrgTableAction extends DispatchActionSupport {

	private static final Log logger = LogFactory.getLog(OrgTableAction.class);
	private ExpertPoolManager expertPoolManager;

	/**
	 * 하위 노드 리스트 가져오기 (그룹, 유저)
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void findByUserName(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String userName = ServletRequestUtils.getRequiredStringParameter(request, "userName");
		HashMap<String, Object> map = new HashMap<String, Object>();

		try {
			
			//List<ExpertPool> expertPoolList = this.getExpertPoolManager().findUserByName("%" + userName + "%");
			
			//map.put("orgNodeList", userList);
			AjaxUtil.successWrite(response, map);
		} catch (Exception e) {
			AjaxUtil.failWrite(response);
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * @return the expertPoolManager
	 */
	public ExpertPoolManager getExpertPoolManager() {
		return expertPoolManager;
	}

	/**
	 * @param expertPoolManager the expertPoolManager to set
	 */
	public void setExpertPoolManager(ExpertPoolManager expertPoolManager) {
		this.expertPoolManager = expertPoolManager;
	}

}
