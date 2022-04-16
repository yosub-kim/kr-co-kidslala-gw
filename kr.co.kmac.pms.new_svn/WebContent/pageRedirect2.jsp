<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>

<%@page import="kr.co.kmac.pms.system.accesslog.dao.ILoginLogDao"%>
<%@page import="kr.co.kmac.pms.common.util.DateTime"%>
<%@page import="kr.co.kmac.pms.expertpool.manager.ExpertPoolManager"%>
<%@page import="kr.co.kmac.pms.expertpool.data.ExpertPool"%>
<%@page import="kr.co.kmac.pms.common.util.SessionUtils"%>
<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<html>
<head>
<%@ include file="/common/include/includeJavaScript.jsp"%>
<script>
var UserAgent = navigator.userAgent;

</script>
<%
	WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(session.getServletContext());
	ExpertPoolManager expertPoolManager = (ExpertPoolManager) wac.getBean("expertPoolManager");

	ExpertPool expertPool = expertPoolManager.retrieve(SessionUtils.getUsername(request));
	expertPoolManager.accountReset(expertPool.getSsn());

	session.setAttribute("ssn", expertPool.getSsn());
	session.setAttribute("name", expertPool.getName());
	session.setAttribute("userId", expertPool.getUserId());
	session.setAttribute("password", expertPool.getPassword());
	session.setAttribute("dept", expertPool.getDept());
	try{
		session.setAttribute("div", expertPool.getDept().substring(0, 3) + "0");
	}catch(Exception e){}
	session.setAttribute("deptName", expertPool.getDeptName());
	session.setAttribute("companyPosition", expertPool.getCompanyPosition());
	session.setAttribute("jobClass", expertPool.getJobClass());
	session.setAttribute("deviceType", "pc");


%>

<script language="JavaScript" type="text/JavaScript">
// 신규 서버로 전송
top.document.location.href = 
   "/_new/login_"
   +"?id=<%=expertPool.getUserId()%>";

</script>
