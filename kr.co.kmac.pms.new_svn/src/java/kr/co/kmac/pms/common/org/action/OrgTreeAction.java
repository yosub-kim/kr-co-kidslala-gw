package kr.co.kmac.pms.common.org.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.kmac.pms.common.ajax.AjaxUtil;
import kr.co.kmac.pms.common.org.data.Group;
import kr.co.kmac.pms.common.org.data.OrgTreeNode;
import kr.co.kmac.pms.common.org.data.User;
import kr.co.kmac.pms.common.org.manager.IOrgUnitManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.struts.DispatchActionSupport;

/**
 * @struts.action name="orgTreeAction" path="/action/OrgTreeAction" parameter="mode" scope="request"
 */
public class OrgTreeAction extends DispatchActionSupport {

	private static final Log logger = LogFactory.getLog(OrgTreeAction.class);

	private IOrgUnitManager orgUnitManager;

	@SuppressWarnings("unchecked")
	public void getOrgNodeList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();

		String parentId = ServletRequestUtils.getRequiredStringParameter(request, "groupId");
		boolean isAll = ServletRequestUtils.getBooleanParameter(request, "isAll", false);

		try {
			List orgUnitList = this.getOrgUnitManager().findMember(parentId, false, isAll);
			List orgNodeList = new ArrayList();
			OrgTreeNode orgTreeNode = null;
			for (Object object : orgUnitList) {
				if (object instanceof User) {
					this.getOrgUnitManager().populateGroup((User) object);
					orgTreeNode = OrgTreeNode.valueOf((User) object);
				} else if (object instanceof Group) {
					orgTreeNode = OrgTreeNode.valueOf((Group) object);
				}
				orgNodeList.add(orgTreeNode);
			}
			map.put("orgNodeList", orgNodeList);
			AjaxUtil.successWrite(response, map);
		} catch (Exception e) {
			AjaxUtil.failWrite(response);
			logger.error(e.getMessage(), e);
		}
	}
	public void getOrgNodeListForMemberSanction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		String parentId = ServletRequestUtils.getRequiredStringParameter(request, "groupId");
		boolean isAll = ServletRequestUtils.getBooleanParameter(request, "isAll", false);
		
		try {
			List orgUnitList = this.getOrgUnitManager().findMember(parentId, false, isAll);
			List orgNodeList = new ArrayList();
			OrgTreeNode orgTreeNode = null;
			for (Object object : orgUnitList) {
				if (object instanceof User) {
					this.getOrgUnitManager().populateGroup((User) object);
					orgTreeNode = OrgTreeNode.valueOf((User) object);
				} else if (object instanceof Group) {
					orgTreeNode = OrgTreeNode.valueOf((Group) object);
				}
				orgNodeList.add(orgTreeNode);
			}
			map.put("orgNodeList", orgNodeList);
			AjaxUtil.successWrite(response, map);
		} catch (Exception e) {
			AjaxUtil.failWrite(response);
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * @return the orgUnitManager
	 */
	public IOrgUnitManager getOrgUnitManager() {
		return orgUnitManager;
	}

	/**
	 * @param orgUnitManager the orgUnitManager to set
	 */
	public void setOrgUnitManager(IOrgUnitManager orgUnitManager) {
		this.orgUnitManager = orgUnitManager;
	}

}
