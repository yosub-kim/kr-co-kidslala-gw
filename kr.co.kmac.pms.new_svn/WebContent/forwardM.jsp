<%@page import="kr.co.kmac.pms.common.util.StringUtil"%>
<%@page import="kr.co.kmac.pms.system.authentication.manager.UserAuthenticationManager"%>
<%@page import="org.acegisecurity.Authentication"%>
<%@page import="org.acegisecurity.context.HttpSessionContextIntegrationFilter"%>
<%@page import="org.acegisecurity.context.SecurityContext"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.Date"%>
<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>

<%@page import="kr.co.kmac.pms.system.accesslog.dao.ILoginLogDao"%>
<%@page import="kr.co.kmac.pms.common.util.DateTime"%>
<%@page import="kr.co.kmac.pms.expertpool.manager.ExpertPoolManager"%>
<%@page import="kr.co.kmac.pms.expertpool.data.ExpertPool"%>
<%@page import="kr.co.kmac.pms.common.util.SessionUtils"%>
<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>

<% Cookie[] cookies = request.getCookies(); %> 
<%!    
    private String getCookieValue(Cookie[] cookies, String name) {
        String value = null;
        if (cookies == null) {
            return null;
        }
        
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(name)) {      
                return cookie.getValue();
            }
        }
        return null;
    }
%>
<%
	SecurityContext context = (SecurityContext) request.getSession().getAttribute(HttpSessionContextIntegrationFilter.ACEGI_SECURITY_CONTEXT_KEY);
	WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(session.getServletContext());

	if (context == null) {
		response.sendRedirect(request.getContextPath() + "/m/login.jsp");
	} else {
		/* String clientIP = SessionUtils.getClientIP(request);
		clientIP = clientIP.substring(0, 3);
		if(clientIP.equals("192") || clientIP.equals("210") || (SessionUtils.getClientIP(request)).equals("127.0.0.1")){
			//?????? ???????????? ???????????? pc?????? ?????? ????????? ??????
			response.sendRedirect("/pageRedirect2.jsp");
		} else {
			String cookieValue = getCookieValue(cookies, "m_kmacPms_auth");//??????????????? ?????????
			if(cookieValue == null || cookieValue.equals("")){
				response.sendRedirect("/m/auth_msg.jsp?device=mobile");//???????????? ?????????
			}else{
				UserAuthenticationManager userAuthenticationManager = (UserAuthenticationManager) wac.getBean("userAuthenticationManager");
				if(clientIP.equals("192") || clientIP.equals("210") || (SessionUtils.getClientIP(request)).equals("127.0.0.1")){
					//?????? ???????????? ???????????? pc?????? ?????? ????????? ??????
					response.sendRedirect("/pageRedirect2.jsp");
				} else {
					String authValue = userAuthenticationManager.getUserAuthentication(SessionUtils.getUsername(request), "mobile",  true).getAuthValue();
					//????????? ????????? ?????? ??????
					if( !StringUtil.isNull(authValue, "not_auth").equals(cookieValue)){
						//pc?????? ????????? 
						response.sendRedirect("/m/auth_msg.jsp?device=mobile");
					} else {
						response.sendRedirect("/pageRedirect2.jsp");
					}
				}
			}
		} */
		// pageRedirect ??????
		response.sendRedirect("/pageRedirect2.jsp");
	}	
	
	ExpertPoolManager expertPoolManager = (ExpertPoolManager) wac.getBean("expertPoolManager");
	ILoginLogDao loginLogDao = (ILoginLogDao ) wac.getBean("loginLogDao");
	
	ExpertPool expertPool = expertPoolManager.retrieve(SessionUtils.getUsername(request));
	expertPoolManager.accountReset(expertPool.getSsn());
	
	session.setAttribute("ssn", expertPool.getSsn());
	session.setAttribute("name", expertPool.getName());
	session.setAttribute("userId", expertPool.getUserId());
	session.setAttribute("password", expertPool.getPassword());
	session.setAttribute("dept", expertPool.getDept());
	try{
		session.setAttribute("div", expertPool.getDept().substring(0, 3) + "0");
		session.setAttribute("empno", expertPool.getEmplNo());
	}catch(Exception e){}
	session.setAttribute("deptName", expertPool.getDeptName());
	session.setAttribute("companyPosition", expertPool.getCompanyPosition());
	session.setAttribute("jobClass", expertPool.getJobClass());
	session.setAttribute("deviceType", "mobile");
	
	// clientIP Log
	loginLogDao.insertLoginLog(session, SessionUtils.getClientIP(request));
	
%>

	