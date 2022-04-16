package kr.co.kmac.pms.reservation.action;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.kmac.pms.common.ajax.AjaxUtil;
import kr.co.kmac.pms.common.util.SessionUtils;
import kr.co.kmac.pms.expertpool.data.ExpertPool;
import kr.co.kmac.pms.expertpool.manager.ExpertPoolManager;
import kr.co.kmac.pms.reservation.data.BizSchoolReservation;
import kr.co.kmac.pms.reservation.form.BizSchoolReservationForm;
import kr.co.kmac.pms.reservation.manager.BizSchoolReservationManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.struts.DispatchActionSupport;

/**
 * @struts.action name="bizSchoolReservationAction" path="/action/BizSchoolReservationAction" parameter="mode" scope="request"
 * 
 * @struts.action-forward name="getBizSchoolReservationInputForm" path="/m/web/reservation/bizSchoolInsert.jsp" redirect="false"
 * @struts.action-forward name="getBizSchoolReservationDetail" path="/m/web/reservation/bizSchoolView.jsp" redirect="false"
 */
public class BizSchoolReservationAction extends DispatchActionSupport {

	private static final Log logger = LogFactory.getLog(BizSchoolReservationAction.class);
	private BizSchoolReservationManager bizSchoolReservationManager;
	private ExpertPoolManager expertPoolManager;

	public BizSchoolReservationManager getBizSchoolReservationManager() {
		return bizSchoolReservationManager;
	}

	public void setBizSchoolReservationManager(BizSchoolReservationManager bizSchoolReservationManager) {
		this.bizSchoolReservationManager = bizSchoolReservationManager;
	}

	public ExpertPoolManager getExpertPoolManager() {
		return expertPoolManager;
	}

	public void setExpertPoolManager(ExpertPoolManager expertPoolManager) {
		this.expertPoolManager = expertPoolManager;
	}

	public void getBizSchoolReservationList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String yyyy = request.getParameter("yyyy");
		String mm = request.getParameter("mm");
		String dd = request.getParameter("dd");

		HashMap<String, Object> map = new HashMap<String, Object>();
		try {
			List<BizSchoolReservation> reservationItemList = this.getBizSchoolReservationManager().getBizSchoolReservationList(yyyy + mm + dd);
			map.put("bizSchoolReservationList", reservationItemList);
			AjaxUtil.successWrite(response, map);
		} catch (Exception e) {
			AjaxUtil.failWrite(response);
			logger.error(e.getMessage(), e);
		}
	}

	public ActionForward getBizSchoolReservationDetail(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String fId = request.getParameter("fId");
		BizSchoolReservation bizSchoolReservation = this.getBizSchoolReservationManager().getBizSchoolReservation(fId);
		ExpertPool expertPool = expertPoolManager.retrieve(SessionUtils.getUsername(request));

		request.setAttribute("expertPool", expertPoolManager.retrieve(SessionUtils.getUsername(request)));
		request.setAttribute("bizSchoolReservation", bizSchoolReservation);
		request.setAttribute("userId", expertPool.getUserId());

		return mapping.findForward("getBizSchoolReservationDetail");
	}

	public void insertBizSchoolReservation(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		BizSchoolReservationForm bizSchoolReservationForm = (BizSchoolReservationForm) form;
		ExpertPool expertPool = expertPoolManager.retrieve(SessionUtils.getUsername(request));
		bizSchoolReservationForm.setfWriter(expertPool.getUserId());
		try {
			this.getBizSchoolReservationManager().insertBizSchoolReservation(bizSchoolReservationForm);
			AjaxUtil.successWrite(response);
		} catch (Exception e) {
			AjaxUtil.failWrite(response);
			logger.error(e.getMessage(), e);
		}
	}

	public void updateBizSchoolReservation(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		BizSchoolReservationForm bizSchoolReservationForm = (BizSchoolReservationForm) form;
		ExpertPool expertPool = expertPoolManager.retrieve(SessionUtils.getUsername(request));
		bizSchoolReservationForm.setfWriter(expertPool.getUserId());
		try {
			this.getBizSchoolReservationManager().updateBizSchoolReservation(bizSchoolReservationForm);
			AjaxUtil.successWrite(response);
		} catch (Exception e) {
			AjaxUtil.failWrite(response);
			logger.error(e.getMessage(), e);
		}
	}

	public void confirmBizSchoolReservation(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String fId = request.getParameter("fId");
		try {
			if (fId == null || fId.length() < 1) {
				throw new Exception("fId No need");
			} else {
				this.getBizSchoolReservationManager().confirmBizSchoolReservation(fId);
			}
			AjaxUtil.successWrite(response);
		} catch (Exception e) {
			AjaxUtil.failWrite(response);
			logger.error(e.getMessage(), e);
		}
	}

	public void cancleBizSchoolReservation(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String fId = request.getParameter("fId");
		try {
			if (fId == null || fId.length() < 1) {
				throw new Exception("fId No need");
			} else {
				this.getBizSchoolReservationManager().deleteBizSchoolReservation(fId);
			}
			AjaxUtil.successWrite(response);
		} catch (Exception e) {
			AjaxUtil.failWrite(response);
			logger.error(e.getMessage(), e);
		}
	}

	public ActionForward inputForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		//String selYear = request.getParameter("selYear");
		String selMonth = request.getParameter("month");
		String selDay = request.getParameter("day");
		
		BizSchoolReservation bizSchoolReservation = new BizSchoolReservation();
		bizSchoolReservation.setfMm(selMonth);
		bizSchoolReservation.setfDd(selDay);
		request.setAttribute("bizSchoolList", this.getBizSchoolReservationManager().getBizSchoolList());
		request.setAttribute("bizTypeList", this.getBizSchoolReservationManager().getBizTypeList());
		request.setAttribute("deptList", this.getExpertPoolManager().getDeptLIst());
		request.setAttribute("bizSchoolReservation", bizSchoolReservation);

		return mapping.findForward("getBizSchoolReservationInputForm");
	}

	public ActionForward updateForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String fId = request.getParameter("fId");
		BizSchoolReservation bizSchoolReservation = this.getBizSchoolReservationManager().getBizSchoolReservation(fId);

		request.setAttribute("bizSchoolReservation", bizSchoolReservation);
		request.setAttribute("bizSchoolList", this.getBizSchoolReservationManager().getBizSchoolList());
		request.setAttribute("bizTypeList", this.getBizSchoolReservationManager().getBizTypeList());
		request.setAttribute("deptList", this.getExpertPoolManager().getDeptLIst());

		return mapping.findForward("getBizSchoolReservationInputForm");
	}
}
