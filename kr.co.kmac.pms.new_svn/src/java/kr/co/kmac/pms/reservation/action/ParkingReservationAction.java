package kr.co.kmac.pms.reservation.action;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.kmac.pms.common.ajax.AjaxUtil;
import kr.co.kmac.pms.common.util.SessionUtils;
import kr.co.kmac.pms.common.util.StringUtil;
import kr.co.kmac.pms.expertpool.data.ExpertPool;
import kr.co.kmac.pms.expertpool.manager.ExpertPoolManager;
import kr.co.kmac.pms.reservation.data.ParkingReservation;
import kr.co.kmac.pms.reservation.manager.ParkingReservationManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.struts.DispatchActionSupport;

/**
 * @struts.action name="ParkingReservationAction" path="/action/ParkingReservationAction" parameter="mode" scope="request"
 * 
 * @struts.action-forward name="getParkingReservation" path="/m/web/reservation/parkingView.jsp" redirect="false"
 * @struts.action-forward name="getParkingReservationList" path="/m/web/reservation/parkingDailyList.jsp" redirect="false"
 * @struts.action-forward name="getParkingReservationMonthlyList" path="/m/web/reservation/parkingMonthlyList.jsp" redirect="false"
 * @struts.action-forward name="getParkingReservationInputForm" path="/m/web/reservation/parkingInsert.jsp" redirect="false"
 */
public class ParkingReservationAction extends DispatchActionSupport {

	private static final Log logger = LogFactory.getLog(ParkingReservationAction.class);
	private ParkingReservationManager parkingReservationManager;
	private ExpertPoolManager expertPoolManager;

	public ExpertPoolManager getExpertPoolManager() {
		return expertPoolManager;
	}

	public void setExpertPoolManager(ExpertPoolManager expertPoolManager) {
		this.expertPoolManager = expertPoolManager;
	}

	public ParkingReservationManager getParkingReservationManager() {
		return parkingReservationManager;
	}

	public void setParkingReservationManager(ParkingReservationManager parkingReservationManager) {
		this.parkingReservationManager = parkingReservationManager;
	}

	public ActionForward getParkingReservationMonthlyList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String month = ServletRequestUtils.getStringParameter(request, "month", StringUtil.getCurr("yyyyMM"));

		List<ParkingReservation> parkingReservationList = this.getParkingReservationManager().getParkingReservationMonthlyList(month);

		request.setAttribute("parkingReservationList", parkingReservationList);
		request.setAttribute("selYear", month.substring(0, 4));
		request.setAttribute("selMonth", month.substring(4, 6));

		return mapping.findForward("getParkingReservationMonthlyList");
	}

	public ActionForward getParkingReservationList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String yyyyMMdd = ServletRequestUtils.getStringParameter(request, "yyyyMMdd", StringUtil.getCurr("yyyyMMdd"));

		List<ParkingReservation> parkingReservationList = this.getParkingReservationManager().getParkingReservationList(yyyyMMdd);

		request.setAttribute("parkingReservationList", parkingReservationList);
		request.setAttribute("pdate", yyyyMMdd.substring(0, 4) + "-" + yyyyMMdd.substring(4, 6) + "-" + yyyyMMdd.substring(6, 8));

		return mapping.findForward("getParkingReservationList");
	}

	public ActionForward getParkingReservation(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String idx = ServletRequestUtils.getRequiredStringParameter(request, "idx");
		ParkingReservation parkingReservation = this.getParkingReservationManager().getParkingReservation(idx);
		ExpertPool expertPool = expertPoolManager.retrieve(SessionUtils.getUsername(request));

		request.setAttribute("parkingReservation", parkingReservation);
		request.setAttribute("userId", expertPool.getUserId());

		return mapping.findForward("getParkingReservation");
	}

	public void insertParkingReservation(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String pdate = StringUtil.replace(request.getParameter("pdate"), "-", "");
		String rtime = request.getParameter("rtime");
		String company = request.getParameter("company");
		String dept = "";
		if (company.equals("KMAC")) {
			dept = request.getParameter("dept_sel");
		} else {
			dept = request.getParameter("dept_txt");
		}

		String pname = request.getParameter("pname");
		String ptel = request.getParameter("ptel");
		String pcarNum = request.getParameter("pcarNum");
		String pmemo = request.getParameter("pmemo");
		ExpertPool expertPool = expertPoolManager.retrieve(SessionUtils.getUsername(request));
		try {
			ParkingReservation parkingReservation = new ParkingReservation();
			parkingReservation.setPdate(pdate);
			parkingReservation.setRtime(rtime);
			parkingReservation.setAddDay("1");
			parkingReservation.setCompany(company);
			parkingReservation.setDept(dept);
			parkingReservation.setPname(pname);
			parkingReservation.setPtel(ptel);
			parkingReservation.setPcarNum(pcarNum);
			parkingReservation.setPmemo(pmemo);
			parkingReservation.setRegdate(StringUtil.getCurr("yyyyMMdd"));
			parkingReservation.setWriter(expertPool.getUserId());

			if (this.getParkingReservationManager().parkingReservationCheck(pdate, pname)) {
				this.getParkingReservationManager().insertParkingReservation(parkingReservation);
			} else {
				throw new Exception("이미 예약된 정보가 있습니다.");
			}
			AjaxUtil.successWrite(response);
		} catch (Exception e) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("msg", e.getMessage());
			AjaxUtil.failWrite(response, map);
			logger.error(e.getMessage(), e);
		}
	}

	public void updateParkingReservation(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String idx = request.getParameter("idx");
		String pdate = StringUtil.replace(request.getParameter("pdate"), "-", "");
		String rtime = request.getParameter("rtime");
		String company = request.getParameter("company");
		String dept = "";
		if (company.equals("KMAC")) {
			dept = request.getParameter("dept_sel");
		} else {
			dept = request.getParameter("dept_txt");
		}

		String pname = request.getParameter("pname");
		String ptel = request.getParameter("ptel");
		String pcarNum = request.getParameter("pcarNum");
		String pmemo = request.getParameter("pmemo");
		ExpertPool expertPool = expertPoolManager.retrieve(SessionUtils.getUsername(request));
		try {
			ParkingReservation parkingReservation = new ParkingReservation();
			parkingReservation.setIdx(idx);
			parkingReservation.setPdate(pdate);
			parkingReservation.setRtime(rtime);
			parkingReservation.setAddDay("1");
			parkingReservation.setCompany(company);
			parkingReservation.setDept(dept);
			parkingReservation.setPname(pname);
			parkingReservation.setPtel(ptel);
			parkingReservation.setPcarNum(pcarNum);
			parkingReservation.setPmemo(pmemo);
			parkingReservation.setRegdate(StringUtil.getCurr("yyyyMMdd"));
			parkingReservation.setWriter(expertPool.getUserId());

			this.getParkingReservationManager().updateParkingReservation(parkingReservation);
			AjaxUtil.successWrite(response);
		} catch (Exception e) {
			AjaxUtil.failWrite(response);
			logger.error(e.getMessage(), e);
		}
	}

	public void confirmParkingReservation(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String idx = request.getParameter("idx");
		try {
			this.getParkingReservationManager().confirmParkingReservation(idx);
			AjaxUtil.successWrite(response);
		} catch (Exception e) {
			AjaxUtil.failWrite(response);
			logger.error(e.getMessage(), e);
		}
	}

	public void deleteParkingReservation(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String idx = ServletRequestUtils.getRequiredStringParameter(request, "idx");
		try {
			if (idx == null || idx.length() < 1) {
				throw new Exception("rerv no need");
			} else {
				this.getParkingReservationManager().deleteParkingReservation(idx);
			}
			AjaxUtil.successWrite(response);
		} catch (Exception e) {
			AjaxUtil.failWrite(response);
			logger.error(e.getMessage(), e);
		}
	}

	public ActionForward inputForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParkingReservation parkingReservation = new ParkingReservation();
		parkingReservation.setPdate(request.getParameter("date"));
		request.setAttribute("parkingReservation", parkingReservation);
		request.setAttribute("deptList", this.getExpertPoolManager().getDeptLIst());

		return mapping.findForward("getParkingReservationInputForm");
	}

	public ActionForward updateForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String idx = ServletRequestUtils.getRequiredStringParameter(request, "idx");

		request.setAttribute("parkingReservation", this.getParkingReservationManager().getParkingReservation(idx));
		request.setAttribute("deptList", this.getExpertPoolManager().getDeptLIst());

		return mapping.findForward("getParkingReservationInputForm");
	}
}
