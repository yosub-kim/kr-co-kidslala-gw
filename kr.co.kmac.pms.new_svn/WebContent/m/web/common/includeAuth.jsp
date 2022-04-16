<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@page import="kr.co.kmac.pms.common.util.SessionUtils"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="kr.co.kmac.pms.system.authentication.manager.UserAuthenticationManager"%>
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
<%--
Cookie[] cookies = request.getCookies();  
WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(session.getServletContext());
UserAuthenticationManager userAuthenticationManager = (UserAuthenticationManager) wac.getBean("userAuthenticationManager");
String authValue = getCookieValue(cookies, "m_kmacPms_auth");
if(userAuthenticationManager.getUserAuthentication(SessionUtils.getUsername(request), true).getAuthValue() == null 
		|| !userAuthenticationManager.getUserAuthentication(SessionUtils.getUsername(request), true).getAuthValue().equals(authValue)){
	response.sendRedirect("/m/auth_msg.jsp");
}
--%>

