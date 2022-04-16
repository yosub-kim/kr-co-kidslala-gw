package kr.co.kmac.pms.project.myproject.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.kmac.pms.common.util.SessionUtils;
import kr.co.kmac.pms.common.util.StringUtil;
import kr.co.kmac.pms.expertpool.data.ExpertPool;
import kr.co.kmac.pms.expertpool.manager.ExpertPoolManager;
import net.mlw.vlh.ValueList;
import net.mlw.vlh.ValueListHandler;
import net.mlw.vlh.ValueListInfo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.struts.DispatchActionSupport;

/**
 * @struts.action name="myProjectAction" path="/action/MyProjectAction" parameter="mode" scope="request"
 * @struts.action-forward name="myProjectList" path="/project/myProject/myProjectList.jsp" redirect="false"
 */
public class MyProjectAction extends DispatchActionSupport {

	private static final Log logger = LogFactory.getLog(MyProjectAction.class);
	private ExpertPoolManager expertPoolManager;

	@SuppressWarnings("unchecked")
	public ActionForward getMyProjectList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		WebApplicationContext wc;
		String pageSize = ServletRequestUtils.getStringParameter(request, "pageSize", "10");
		String pageNo = ServletRequestUtils.getStringParameter(request, "pageNo", "1");
		String projectName = ServletRequestUtils.getStringParameter(request, "projectName", "");
		String projectCode = ServletRequestUtils.getStringParameter(request, "projectCode", "");
		String businessTypeCode = ServletRequestUtils.getStringParameter(request, "businessTypeCode", "");
		String realStartDate = ServletRequestUtils.getStringParameter(request, "realStartDate", "");
		String realEndDate = ServletRequestUtils.getStringParameter(request, "realEndDate", "");
		String projectState = ServletRequestUtils.getStringParameter(request, "projectState", "");
		String delayState = ServletRequestUtils.getStringParameter(request, "delayState", "");
		String delayStateAll = ServletRequestUtils.getStringParameter(request, "delayStateAll", ""); 
		String pjtEndYear = ServletRequestUtils.getStringParameter(request, "pjtEndYear", "");
		// String userDept = ServletRequestUtils.getStringParameter(request, "userDept", "");
		// String userDiv = ServletRequestUtils.getStringParameter(request, "userDiv", "");
		String runningDeptCode = ServletRequestUtils.getStringParameter(request, "runningDeptCode", "");
		String userId = SessionUtils.getUsername(request);
		String customerName = ServletRequestUtils.getStringParameter(request, "customerName", "");
		String costOver = ServletRequestUtils.getStringParameter(request, "costOver", "");

		try {
			wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh = (ValueListHandler) wc.getBean("projectValueList", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();
			Map<String, String> filters = new HashMap<String, String>();
			filters.put(ValueListInfo.PAGING_PAGE, pageNo);
			filters.put(ValueListInfo.PAGING_NUMBER_PER, pageSize);			

			if (!projectName.equals(""))
				filters.put("projectName", "%" + projectName + "%");
			if (!customerName.equals(""))
				filters.put("customerName", "%" + customerName + "%");
			if (!projectCode.equals(""))
				filters.put("projectCode", projectCode);
			if (!businessTypeCode.equals(""))
				filters.put("businessTypeCode", businessTypeCode);
			if (!projectState.equals("")) {
				filters.put("projectState", projectState);
			} else {
				filters.put("projectStateAll", "projectStateAll");
			}
			if (!delayState.equals(""))
				filters.put("delayState", delayState);
			if (!delayStateAll.equals(""))
				filters.put("delayStateAll", delayStateAll);
			if (!pjtEndYear.equals(""))
				filters.put("pjtEndYear", pjtEndYear+"%");
			if (!realStartDate.equals("") && !realStartDate.equals("")) {
				filters.put("realStartDate", StringUtil.replace(realStartDate, "-", ""));
				filters.put("realEndDate", StringUtil.replace(realEndDate, "-", ""));
			}
			if (!costOver.equals("")) {					filters.put("costOver", costOver);			}

			ExpertPool expertPool = this.getExpertPoolManager().retrieve(userId);
			ValueList valueList = null;
			if (StringUtil.isNotNull(expertPool.getSsn())) {
				filters.put("userSsn", userId);	// 보안을  위하여 위치를 이동함
				if (expertPool.getCompanyPosition().equals("08CF") || expertPool.getCompanyPosition().equals("09CJ")) {	// COO(본부장, 센터장)
					filters.put("runningDeptCode", expertPool.getDept());
					info.setFilters(filters);
					valueList = vlh.getValueList("myProjectTeamListSelect", info);
				}else if (expertPool.getCompanyPosition().equals("05CC") || expertPool.getCompanyPosition().equals("06CB")) {		// CCO, CBO
					if (runningDeptCode.equals(""))
						filters.put("runningDivCode", expertPool.getDept());
					else 
						filters.put("runningDeptCode", runningDeptCode);
					info.setFilters(filters);
					valueList = vlh.getValueList("myProjectTeamListSelect", info);
				} else {
					info.setFilters(filters);
					valueList = vlh.getValueList("myProjectListSelect", info);
				}
			} else {
				filters.put("userSsn", "");	// 사용자 정보가 없으면 프로젝트 리스트를 볼 수 없게 함(보안을 위한 조치사항) 
				info.setFilters(filters);
				valueList = vlh.getValueList("myProjectListSelect", info); 
			}

			request.setAttribute("list", valueList);

			request.setAttribute("projectName", projectName);
			request.setAttribute("customerName", customerName);
			request.setAttribute("projectState", projectState);
			request.setAttribute("businessTypeCode", businessTypeCode);
			request.setAttribute("pjtEndYear", pjtEndYear);
			request.setAttribute("costOver", costOver);
			request.setAttribute("delayState", delayState);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return mapping.findForward("myProjectList");
	}

	public ExpertPoolManager getExpertPoolManager() {
		return expertPoolManager;
	}

	public void setExpertPoolManager(ExpertPoolManager expertPoolManager) {
		this.expertPoolManager = expertPoolManager;
	}

}
