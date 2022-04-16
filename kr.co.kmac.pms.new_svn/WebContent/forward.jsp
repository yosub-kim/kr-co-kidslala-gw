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
<link rel="shortcut icon" href="/images/kmac_favicon.ico">

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
		response.sendRedirect(request.getContextPath() + "/login.jsp");
	} else {
		String clientIP = SessionUtils.getClientIP(request);
		clientIP = clientIP.substring(0, 3);
		if(clientIP.equals("192") || clientIP.equals("210") || (SessionUtils.getClientIP(request)).equals("127.0.0.1")){
			//내부 아이피일 경우에는 pc인증 없이 로그인 진행
		} else {
			String cookieValue = getCookieValue(cookies, "d_kmacPms_auth");//클라이언트 쿠키값
			if(cookieValue == null || cookieValue.equals("")){
				response.sendRedirect("/m/auth_msg.jsp?device=desktop");//인증필요 페이지
			}else{
				UserAuthenticationManager userAuthenticationManager = (UserAuthenticationManager) wac.getBean("userAuthenticationManager");
				if(clientIP.equals("192") || clientIP.equals("210") || (SessionUtils.getClientIP(request)).equals("127.0.0.1")){
					//내부 아이피일 경우에는 pc인증 없이 로그인 진행
				} else {
					String authValue = userAuthenticationManager.getUserAuthentication(SessionUtils.getUsername(request), "desktop",  true).getAuthValue();
					//외부일 경우는 인증 실시
					if( !StringUtil.isNull(authValue, "not_auth").equals(cookieValue)){
						//pc인증 실퍠시 
						response.sendRedirect("/m/auth_msg.jsp?device=desktop");
					} else {
						session.setAttribute("deviceAuth", true);
					}
				}
			}
		}
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
	session.setAttribute("deviceType", "pc");
	
	Calendar lastModifiedDate = Calendar.getInstance();
	lastModifiedDate.setTime(expertPool.getLastModifiedDate());
	lastModifiedDate.add(Calendar.DATE, 90);
	
	if(lastModifiedDate.compareTo(Calendar.getInstance()) < 0){
		session.setAttribute("needToPasswordUpdate", "true");	
		//System.out.println("########needToPasswordUpdate: true");
	} else {
		session.setAttribute("needToPasswordUpdate", "false");	
		//System.out.println("########needToPasswordUpdate: false");
	}
	
	//System.out.println("UserContext: "+SessionUtils.getUserContext());
	// clientIP Log
	loginLogDao.insertLoginLog(session, SessionUtils.getClientIP(request));	

	
%>


<script type="text/javascript" src="/resources/js/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="/resources/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="/resources/js/jquery.selectric.min.js"></script><!-- 셀렉트 박스 UI-->

<script type="text/javascript">
jQuery.noConflict();
var j$ = jQuery;
</script> 

<script language="JavaScript" type="text/JavaScript">
function securityValue(){
	// 신규 서버로 전송
	var sFrm = document.frm;
	/* j$.ajax({
		url: "/_new/login_",
        type: "POST",
        data: JSON.stringify({ "id": sFrm.id.value, "pw": sFrm.pw.value }),
        async: true,
        contentType: "application/json",
        success: function () { 
        	alert("성공");
        },
        error: function(){ alert("실패");}
	});	 */
	
	//asp 전송
	top.document.location.href = 
		"https://intranet.kmac.co.kr/lib1/pre_authority.asp"
		+"?_id=<%=expertPool.getSsn()%>"
		+"&_url=https://<%= request.getServerName()%>:<%= request.getServerPort() %>/pageRedirect2.jsp"
		+"&reqSrc=<%=request.getParameter("reqSrc")%>";
}
</script>

<body onload="securityValue()">
	<form name="frm" method="post">
		<input type="hidden" name="id" id="id" value="<%= expertPool.getUserId() %>" />
		<input type="hidden" name="pw" name="pw" value="<%= expertPool.getPassword() %>" />
	</form>
</body>
