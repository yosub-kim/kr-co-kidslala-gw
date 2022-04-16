<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<%@page import="kr.co.kmac.pms.system.popup.manager.PopUpConfigManager" %>
<%@page import="kr.co.kmac.pms.system.popup.data.PopUpConfig" %>
<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%
WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(session.getServletContext());
PopUpConfigManager popUpConfigManager = null;
if (wac != null) {
	popUpConfigManager = (PopUpConfigManager) wac.getBean("popUpConfigManager");
}
PopUpConfig popUpConfig = popUpConfigManager.getPopUpConfigDAO().getPopUp();
%>
<%=popUpConfig.getContent()%>
<table>
	<tr>
		<td><font size="-1">이 창을 하루 동안 열지 않음</font></td>
		<td><input type="checkbox" name="popCheck" onclick="cookie_set(this);"></td>
	</tr>
</table>
<script>
function cookie_set(obj){
	if(obj.checked){
		setCookie1("noPopUp", "Y", 1);
	}
}


function setCookie1( name, value, expiredays ){
	var todayDate = new Date();
	todayDate.setDate( todayDate.getDate() + expiredays );
	document.cookie = name + '=' + escape( value ) + '; path=/; expires=' + todayDate.toGMTString() + ';'
}

function setCookie2( name, value ){
	document.cookie = name + '=' + escape( value ) + '; path=/; '
}


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

</script>