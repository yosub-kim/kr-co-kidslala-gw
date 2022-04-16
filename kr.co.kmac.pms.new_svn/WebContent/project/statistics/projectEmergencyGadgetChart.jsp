<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>

<%@page import="kr.co.kmac.pms.common.util.SessionUtils"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<%-- 공통js,css include --%>
<%@ include file="/common/include/includeCss.jsp"%>
<%@ include file="/common/include/includeJavaScript.jsp"%>

<script type="text/javascript">
<%-- 개별 스크립트 영역 --%>

</script>
</head>
<%--
<body>
		<table width="210" cellpadding="0" cellspacing="0">
			<tr>
				<td><object id="projectProgress" name="projectProgress" width="100%" height="195"
					 		type="application:x-shockwave-flash" classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000"
					 		codebase="http://fpdownload.macromedia.com/get/flashplayer/current/swflash.cab">
					 		<param name="src" 
					 			value="/project/statistics/<c:out value="${filename}"/>?&serviceUrl=<%= request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/"%>"
					 		<param name="wmode" value="transparent"/>
					 		<param name="quality" value="high"/>
					 		<param name="allowScriptAccess" value="sameDomain"/>
					 		<param name="bgcolor" value="#ffffff"/>
					 		<param name='FlashVars' value=""/>	
						</object>
				</td>
			</tr>
		</table>						
</body>
 --%>
 
<iframe	src="/action/ProjectEmergencyAction.do?mode=emergencyGadgetChart"
	width="210px" height="203px" style="border-style: none; padding: 0px; margin: 0px;" frameborder="0" scrolling="no" ></iframe>
</body> 

</html>					