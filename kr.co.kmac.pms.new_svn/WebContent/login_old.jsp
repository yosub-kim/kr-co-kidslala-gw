<!doctype html>
<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<%@ page import="org.acegisecurity.context.HttpSessionContextIntegrationFilter"%>
<%@ page import="org.acegisecurity.Authentication"%>
<%@ page import="org.acegisecurity.context.SecurityContext"%>
<%@ page import="org.acegisecurity.ui.AbstractProcessingFilter"%>
<%@ page import="org.acegisecurity.ui.webapp.AuthenticationProcessingFilter"%>
<%@ page import="org.acegisecurity.AuthenticationException"%>
<%
		SecurityContext context = (SecurityContext) request.getSession().getAttribute(HttpSessionContextIntegrationFilter.ACEGI_SECURITY_CONTEXT_KEY);
		if (context != null) {
			Authentication auth = context.getAuthentication();
			if (auth != null && !"guest".equals(auth.getPrincipal())) {
				response.sendRedirect(request.getContextPath() + "/pageRedirect.jsp");
			}
		}

		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Expires", "0");

%>
<html lang="ko">
<head>
<meta charset="utf-8">
<title>KMAC 통합 인트라넷</title>
<style>
	html,body,div, a { font-family: "나눔고딕","맑은 고딕", "돋움" }
	
	a:link { text-decoration: none; color: #fff; }
	a:visited { text-decoration: none; color: #fff;}
	a:hover { text-decoration: underline; color: fff;}
	a:active { text-decoration: none; color: #fff;}
	
	body {
	background: url(images/login/main.png);
	}
	
	#main { 
	position:absolute; 
	top:50%; left:50%; width:440px; 
	margin-left: -207px; margin-top: -200px;
	}
	
	.font01 {
	color:#FFFFFF; 
	font-size:13px;
	}
	
	.button { 
	background-color: #004b8c;
	width:100%; 
	height:50px;
	border-radius:5px 5px 5px 5px;
	border: none;
	color:#FFFFFF;
	border: 1px solid #00315b;
	font-weight: bold;
	text-align: center;
	text-decoration: none;
	display: inline-block;
	font-size: 16px;
	margin: 4px 2px;
	cursor: pointer;
	transition-duration: 0.2s;
	}
	
	.button:hover {
 		box-shadow: 0 10px 10px 0 rgba(0,0,0,0.10),0 10px 15px 0 rgba(0,0,0,0.10);
	}

	input {
	width:100%; 
	height:40px; 
	padding:0;margin-top:10px;
	vertical-align:bottom;
	}
	
	.id_pw {
	font-size: 15px;
	width:100%; 
	height:auto;
	padding:.8em .2em;
	}
	
	.id_pw:hover {
 		box-shadow: 0 10px 10px 0 rgba(0,0,0,0.10),0 10px 15px 0 rgba(0,0,0,0.10);
	}
	
	.id_pw_find {
	font-size:11px;
	color:#FFFFFF;
	border: none;
	}
	
	.footer{
	font-size:11px;
	color:#FFFFFF;
	text-align: center;
	margin-top: 780px;
	
	}
			
</style> 
<script type="text/javascript">
if (navigator.userAgent.match(/iPhone|iPod|iPad|Android|Windows CE|BlackBerry|Symbian|Windows Phone|webOS|Opera Mini|Opera Mobi|POLARIS|IEMobile|lgtelecom|nokia|SonyEricsson/i) != null 
		|| navigator.userAgent.match(/LG|SAMSUNG|Samsung/) != null){
	top.document.location.href = "/m";
}
       function Page_Load() {
       	var ckUserId = getCookie("pms_User_Id");
       	if(ckUserId != "") {
       		document.getElementById("j_username").value = ckUserId;
       	}
       	document.getElementById("j_username").focus();
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
       function login(){
		  	if(document.loginform.j_username.value == ''){
		  		alert('아이디를 입력하세요.');
		  		document.loginform.j_username.focus();
		  		return(false);
		  	}
		  	if(document.loginform.j_password.value == ''){
		  		alert('비밀번호를 입력하세요.');
		  		document.loginform.j_password.focus();
		  		return(false);
		  	}
		  	document.loginform.agentInfo.value = navigator.userAgent;
		  	document.loginform.submit();
		  	return(false); 
		}
       function f_search(Mode){
       	switch(Mode){
       		case "I":
       			window.open('http://intranet.kmac.co.kr/kmac/pwmanage/idsearch.asp','idsearch','width=340,height=215,scrollbars=no');
       		break;
       		case "P":
       			window.open('http://intranet.kmac.co.kr/kmac/pwmanage/pwsearch.asp','pwsearch','width=340,height=237scrollbars=no');
       		break;
       	}
       }        
</script>
</head>   
<body onload="Page_Load();">

<div id="main" align="center">
		<h1><img class="logo" src="images/login/kmac.png"></h1>
        <div class="font01">KMAC 통합 인트라넷은 Value Creation의 원천인 우리의 핵심 자산입니다.<br>
		통합 인트라넷을 통한 구성원 개개인의 지식활동은 KMAC의 경쟁력입니다.
        </div><br>
		<form name="loginform" action="<c:url value='j_acegi_security_check'/>" method="POST" onsubmit="return login();"> 
          	<div>
          		<input type="hidden" name='agentInfo' />
				<input class="id_pw" type="text" name="j_username" id="j_username" placeholder="ID" onkeypress="if(event.keyCode == 13){ login();}">
          	</div>
            <div> 
				<input class="id_pw" type="password" name="j_password" id="j_password" placeholder="Password" onkeypress="if(event.keyCode == 13){ login();}">
            </div><br>
            <button class="button" onClick="login()">LOGIN</button>
		</form>
      <br>
		  <div class="id_pw_find">
		    <a class="id_btn" href="#" onclick="f_search('I');">아이디 찾기</a> | <a class="pw_btn" href="#" onclick="f_search('P');">비밀번호 찾기</a>
        </div>
	</div>
		<div class="footer">copyright &copy; 2016 KMAC All Rights Reserved. <strong><a href="mailto:mailadmin@kmac.co.kr">mailadmin@kmac.co.kr</a></strong></div>
</body>
</html>
<c:if test="${not empty param.login_error}">
	<script>
		alert("아이디 또는 비밀번호를 다시 확인하세요. \n"+
				"등록되지 않은 아이디이거나, 아이디 또는 비밀번호를 잘못 입력하셨습니다."	);
	</script>
</c:if>