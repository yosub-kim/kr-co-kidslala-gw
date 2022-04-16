package kr.co.kmac.pms.reservation.action;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.kmac.pms.common.ajax.AjaxUtil;
import kr.co.kmac.pms.common.util.SessionUtils;
import kr.co.kmac.pms.expertpool.data.ExpertPool;
import kr.co.kmac.pms.expertpool.manager.ExpertPoolManager;
import kr.co.kmac.pms.reservation.data.Reservation;
import kr.co.kmac.pms.reservation.data.ReservationItem;
import kr.co.kmac.pms.reservation.manager.ReservationManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.struts.DispatchActionSupport;

/**
 * @struts.action name="ReservationAction" path="/action/ReservationAction" parameter="mode" scope="request"
 * 
 * @struts.action-forward name="getReservation" path="/m/web/reservation/reservationDetail.jsp" redirect="false"
 * @struts.action-forward name="getReservationDetail" path="/m/web/reservation/reservationView.jsp" redirect="false"
 */
public class ReservationAction extends DispatchActionSupport {

	private static final Log logger = LogFactory.getLog(ReservationAction.class);
	private ReservationManager reservationManager;
	private ExpertPoolManager expertPoolManager;

	public ExpertPoolManager getExpertPoolManager() {
		return expertPoolManager;
	}

	public void setExpertPoolManager(ExpertPoolManager expertPoolManager) {
		this.expertPoolManager = expertPoolManager;
	}

	/**
	 * @return the reservationManager
	 */
	public ReservationManager getReservationManager() {
		return reservationManager;
	}

	/**
	 * @param reservationManager the reservationManager to set
	 */
	public void setReservationManager(ReservationManager reservationManager) {
		this.reservationManager = reservationManager;
	}

	public void getReservationItemList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String gubun = request.getParameter("gubun");
		HashMap<String, Object> map = new HashMap<String, Object>();
		try {
			List<ReservationItem> reservationItemList = this.getReservationManager().getReservationItemList(gubun);
			map.put("reservationItemList", reservationItemList);
			AjaxUtil.successWrite(response, map);
		} catch (Exception e) {
			AjaxUtil.failWrite(response);
			logger.error(e.getMessage(), e);
		}
	}

	public ActionForward getReservation(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String resvCode = request.getParameter("resvCode");
		String sdate = request.getParameter("sdate");
		String gubun = request.getParameter("gubun");

		ReservationItem reservationItem = this.getReservationManager().getReservationItemList(gubun, resvCode);
		List<Reservation> reservationList = this.getReservationManager().getReservation(resvCode, sdate);

		request.setAttribute("reservationItem", reservationItem);
		request.setAttribute("reservationList", reservationList);
		request.setAttribute("sdateStr", sdate.substring(0, 4) + "년 " + sdate.substring(4, 6) + "월 " + sdate.substring(6, 8) + "일");
		request.setAttribute("sdate", sdate);
		request.setAttribute("gubun", gubun);

		return mapping.findForward("getReservation");
	}

	public ActionForward getReservationDetail(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String no = request.getParameter("no");
		String resvGubun = request.getParameter("resvGubun");
		Reservation reservation = this.getReservationManager().getReservation(no);
		ExpertPool expertPool = this.getExpertPoolManager().retrieve(SessionUtils.getUsername(request));
		//expertPoolManager.retrieve(SessionUtils.getUsername(request));
		if (reservation.getEmpno().equals(expertPool.getEmplNo())) {
			request.setAttribute("isRegister", "Y");
		}
		request.setAttribute("reservation", reservation);
		request.setAttribute("sdateYear", reservation.getStime().substring(0, 4));
		request.setAttribute("sdateMonth", reservation.getStime().substring(4, 6));
		request.setAttribute("sdateDay", reservation.getStime().substring(6, 8));
		request.setAttribute("sdateTime", reservation.getStime().substring(8, 10));
		request.setAttribute("edateYear", reservation.getEtime().substring(0, 4));
		request.setAttribute("edateMonth", reservation.getEtime().substring(4, 6));
		request.setAttribute("edateDay", reservation.getEtime().substring(6, 8));
		request.setAttribute("edateTime", reservation.getEtime().substring(8, 10));
		request.setAttribute("resvGubun", resvGubun);

		return mapping.findForward("getReservationDetail");
	}

	public void insertReservation(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String resvCode = request.getParameter("resvCode");
		String stime = request.getParameter("startYear") + "" + request.getParameter("startMonth") + "" + request.getParameter("startDay") + ""
				+ request.getParameter("startTime");
		String etime = request.getParameter("endYear") + "" + request.getParameter("endMonth") + "" + request.getParameter("endDay") + ""
				+ request.getParameter("endTime");
		String usePurpose = request.getParameter("usePurpose");
		String useLoc = request.getParameter("useLoc");
		ExpertPool expertPool = expertPoolManager.retrieve(SessionUtils.getUsername(request));
		try {
			Reservation reservation = new Reservation();
			reservation.setResvCode(resvCode);
			reservation.setEmpno(expertPool.getEmplNo());
			reservation.setStime(stime);
			reservation.setEtime(etime);
			reservation.setUseTeam(expertPool.getDeptName());
			reservation.setUsePer(expertPool.getName());
			reservation.setUseLoc(useLoc);
			reservation.setUsePurpose(usePurpose);
			
			if (this.getReservationManager().checkReservation(resvCode, stime, etime)) {
				this.getReservationManager().insertReservation(reservation);
				AjaxUtil.successWrite(response);
			} else {
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("resMsg", "해당 항목에 대한 예약 시간이 중복되었습니다.");
				AjaxUtil.failWrite(response, map);
			}
		} catch (Exception e) {
			AjaxUtil.failWrite(response);
			logger.error(e.getMessage(), e);
		}
	}

	public void deleteReservation(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String no = request.getParameter("no");
		try {
			if (no == null || no.length() < 1) {
				throw new Exception("rerv no need");
			} else {
				this.getReservationManager().deleteReservation(no);
			}
			AjaxUtil.successWrite(response);
		} catch (Exception e) {
			AjaxUtil.failWrite(response);
			logger.error(e.getMessage(), e);
		}
	}
}
