<%@page import="kr.co.kmac.pms.system.authentication.data.UserAuthentication"%>
<%@page import="kr.co.kmac.pms.system.authentication.manager.UserAuthenticationManager"%>
<%@page import="kr.co.kmac.pms.common.util.SessionUtils"%>
<%@page import="kr.co.kmac.pms.expertpool.manager.ExpertPoolManager"%>
<%@page import="kr.co.kmac.pms.expertpool.data.ExpertPool"%>
<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<%
	WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(session.getServletContext());
	ExpertPoolManager expertPoolManager = (ExpertPoolManager) wac.getBean("expertPoolManager");
	ExpertPool expertPool = expertPoolManager.retrieve(SessionUtils.getUsername(request));
	UserAuthenticationManager userAuthenticationManager = (UserAuthenticationManager) wac.getBean("userAuthenticationManager");
	UserAuthentication userAuthentication = userAuthenticationManager.getUserAuthentication(expertPool.getSsn(),"empty", true);

	Cookie cookie = new Cookie("m_kmacPms_auth", userAuthentication.getAuthValue());
	cookie.setMaxAge(60 * 60 * 24 * 90); // 유효시간 설정 : 60*60*24*90(초) = 90일
	response.addCookie(cookie); // 쿠키전송 */

	response.sendRedirect("/action/AuthorityAction.do?mode=mobileMainPage");
	//RequestDispatcher dis = getServletContext(). getRequestDispatcher("/m/index.jsp"); 
	//dis.forward(request, response); 

%>
