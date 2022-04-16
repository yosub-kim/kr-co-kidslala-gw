<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>

<%@page import="kr.co.kmac.pms.common.util.DateTime"%>
<%@page import="kr.co.kmac.pms.expertpool.manager.ExpertPoolManager"%>
<%@page import="kr.co.kmac.pms.expertpool.data.ExpertPool"%>
<%@page import="kr.co.kmac.pms.system.popup.manager.PopUpConfigManager" %>
<%@page import="kr.co.kmac.pms.system.popup.data.PopUpConfig" %>
<%@page import="kr.co.kmac.pms.common.util.SessionUtils"%>
<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<html>
<head>
<%
	WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(session.getServletContext());
	ExpertPoolManager expertPoolManager = null;
	if (wac != null) {
		expertPoolManager = (ExpertPoolManager) wac.getBean("expertPoolManager");
	}
	ExpertPool expertPool = expertPoolManager.retrieve(SessionUtils.getUsername(request));
	
	PopUpConfigManager popUpConfigManager = null;
	if (wac != null) {
		popUpConfigManager = (PopUpConfigManager) wac.getBean("popUpConfigManager");
	}
	PopUpConfig popUpConfig = popUpConfigManager.getPopUpConfigDAO().getPopUp();
	
	String userId = expertPool.getUserId();
	String userPw = expertPool.getPassword();

%>
<%@ include file="/common/include/includeJavaScript.jsp"%>
<script>
if(isMobile){
	top.location.href="/m";
}

var isPopUpEnable = "<%=popUpConfig.getIsEnable()%>";
var wnWidth		= "<%=popUpConfig.getWidth()%>";
var wnHeight	= "<%=popUpConfig.getHeight()%>";

function getCookie( name ){
	var nameOfCookie = name + "=";
	var x = 0;
	while ( x <= document.cookie.length ){
		var y = (x+nameOfCookie.length);
		if ( document.cookie.substring( x, y ) == nameOfCookie ) {
			if ( (endOfCookie=document.cookie.indexOf( ";", y )) == -1 )
				endOfCookie = document.cookie.length;
			return unescape( document.cookie.substring( y, endOfCookie ) );
		}
		x = document.cookie.indexOf( " ", x ) + 1;
		if ( x == 0 )
			break;
	}
	return "";
}

//alert(isPopUpEnable);
if((isPopUpEnable == "Y")&&(getCookie("noPopUp") != "Y")) {
	var screenHeight = screen.height;
	var screenWidth = screen.width;
	var leftpos = screenWidth / 2 - wnWidth / 2;
	var toppos = screenHeight / 2 - wnHeight / 2;
	var popupWin = window.open("/PopUp.jsp","popupWin","toolbar=no,location=no,status=no,menubar=no,scrollbars=no,resizable=yes,width="+wnWidth+",height="+wnHeight+",left="+leftpos+",top="+toppos+"");
	popupWin.focus();
}
function setCookie1( name, value, expiredays ){
	var todayDate = new Date();
	todayDate.setDate( todayDate.getDate() + expiredays );
	document.cookie = name + '=' + escape( value ) + '; path=/; expires=' + todayDate.toGMTString() + ';'
}
setCookie1("pms_User_Id",'<%=userId%>',3);

//document.domain = "kmac.co.kr";

function resizePmsFrame(){
	var pf = document.getElementById("parentFrame");
	if(pf){
		pf.rows = '*,0,0';
	}
}
function resizeIntranetFrame(){
	var pf = document.getElementById("parentFrame");
	if(pf){
		pf.rows = '112,*,0';
		document.getElementById("intranetArea").src="http://intranet.kmac.co.kr/kmac/ver2/index.asp";
	}
}
</script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>KMAC 통합 인트라넷</title>
</head>
<frameset id="parentFrame" rows="*,0,0" cols="*" framespacing="0" frameborder="no" border="0">

	<frameset rows="112,*" cols="*" frameborder="no" border="0" framespacing="0">
	
		<frame src="/action/AuthorityAction.do?mode=topMenuRetrieve" name="topFrame" scrolling="no" noresize>

		<frameset cols="209,*" frameborder="no" border="0" framespacing="0">
		
				<frame src="/action/AuthorityAction.do?mode=leftMenuRetrieve" name="leftFrame" scrolling="no" noresize>
		
			<%if(request.getParameter("redirectUrl") != null && !request.getParameter("redirectUrl").equals("")){%>
				<frame src="<%=request.getParameter("redirectUrl")%>" name="rightFrame" scrolling="yes">
		
			<%}else if(session.getAttribute("jobClass").equals("A") || session.getAttribute("jobClass").equals("J")){%>
				<frame src="/action/GadgetAction.do?mode=mainPage" name="rightFrame" scrolling="yes">
		
			<%}else{%>
				<frame src="/action/ScheduleAction.do?mode=scheduleOfMonth" name="rightFrame" scrolling="yes">				
			<%}%>
		</frameset>
		
		
	</frameset>

	<frameset  rows="*" cols="*" frameborder="yes" border="1" framespacing="0">
		<frame id="intranetArea" src="">
	</frameset>

	<frameset rows="*" cols="489,500" framespacing="0" frameborder="no" border="0">
		<%--
		<frame name="hidFrame1" src="pms" scrolling="No" noresize="noresize">
		 --%>
		<frame name="hidFrame2" src="http://intranet.kmac.co.kr/lib1/authority.asp?_id=<%=userId%>&_pass=<%=userPw%>&_url=http://<%= request.getServerName()%>:<%= request.getServerPort() %>/session_remains2.jsp"	scrolling="No" noresize="noresize">
	</frameset>
</frameset>
<noframes>
<body>
</body>
</noframes>
</html>