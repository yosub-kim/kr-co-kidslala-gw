<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<%@ page import="org.acegisecurity.ui.AbstractProcessingFilter"%>
<%@ page import="org.acegisecurity.ui.webapp.AuthenticationProcessingFilter"%>
<%@ page import="org.acegisecurity.AuthenticationException"%>

<html>
<head>
<title>::: KMAC 통합 인트라넷 :::</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%-- 공통js,css include --%>
<%@ include file="/common/include/includeCss.jsp"%>
<script language="JavaScript" type="text/JavaScript">
<!--
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
function setPng24(psImgObj) {
	psImgObj.width = psImgObj.height = 1;
	psImgObj.className = psImgObj.className.replace(/\bpng24\b/i,'');
	psImgObj.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(src='"+ psImgObj.src +"',sizingMethod='image');";
	psImgObj.src = "";
	return "";
}
function login(){
	if(document.loginform.j_username.value == ''){
		alert('ID를 입력하세요.');
		return(false);
	}
	if(document.loginform.j_password.value == ''){
		alert('비밀번호를 입력하세요.');
		return(false);
	}
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

//-->
</script>
</head>
<link href="css/style.css" rel="stylesheet" type="text/css">

<body onload="Page_Load();">
<c:if test="${not empty param.login_error}">
	<script>
		alert("아이디 또는 패스워드를 잘못 입력 하셨습니다. \n"+
				"Reason: <%=((AuthenticationException) session.getAttribute(AbstractProcessingFilter.ACEGI_SECURITY_LAST_EXCEPTION_KEY)).getMessage()%>"	);
	</script>
</c:if>
<form name="loginform" action="<c:url value='j_acegi_security_check'/>" method="POST" onsubmit="return login();">
<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td height="50" align="center">&nbsp;</td>
	</tr>
	<tr>
		<td align="center">
		<table width="700" border="0" align="center" cellpadding="0" cellspacing="0">
			<tr>
				<td style="position: relative;"><!--이미지 플래시 레이어-->
				<div id="Layer2" style="position: absolute; left: 0px; top: 0px; width: 703px; height: 378px; z-index: 1;"><object
					classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,29,0" width="703"
					height="378">
					<param name="movie" value="/images/login/login_001.swf">
					<param name="quality" value="high">
					<param name="wmode" value="transparent">
					<embed src="/images/login/login_001.swf" quality="high" pluginspage="http://www.macromedia.com/go/getflashplayer" type="application/x-shockwave-flash" width="703"
						height="378"></embed> </object></div>
				<!-- 로그인 테이블 레이어-->
				<div id="Layer1" style="position: absolute; left: 323px; top: 330px; width: 380px; height: 78px; z-index: 9;">
				<table width="370" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td height="9" colspan="2"></td>
						<td width="80" rowspan="4" align="right" valign="bottom"><a href="#" onclick="login()"><img class="png24" src="/images/login/login_btn01.png" width="74" height="67" ></img></a></td>
					</tr>
					<tr>
						<td width="132" height="23" align="left" valign="middle"><img class="png24" src="/images/login/login_id.png" width="19" height="9">
							<input type='text' name='j_username' style="ime-mode: inactive" class="input_login" id="j_username" onkeypress="if(event.keyCode == 13){ login();}"
							<c:if test="${not empty param.login_error}">value='<%= session.getAttribute(AuthenticationProcessingFilter.ACEGI_SECURITY_LAST_USERNAME_KEY) %>'</c:if>
							<c:if test="${empty param.login_error}">value=''</c:if>></td>
						<td width="158" align="right" valign="middle"><img class="png24" src="/images/login/login_pass.png" width="34" height="9">
							<input type='password' name='j_password' class="input_login" id="j_password" onkeypress="if(event.keyCode == 13) {login();}"></td>
					</tr>
					<tr>
						<td height="9" colspan="2" align="right"><img src="/images/login/login_line.jpg" width="289" height="1"></td>
					</tr>
					<tr>
						<td height="10" colspan="2" align="right" valign="top">
						<table width="220" height="15" border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td width="62" align="right">
									<table width="54" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<td width="20" align="right"><input type="checkbox" name="_acegi_security_remember_me" id="checkbox"></td>
											<td width="34" align="left" valign="middle"><span class="login_txt">ID저장</span></td>
										</tr>
									</table>
								</td>
								<td width="68" align="right"><a href="#" onclick="f_search('I');"><img src="/images/login/btn_idsearch.jpg" width="63" height="19" border="0"></a></td>
								<td width="90" align="right"><a href="#" onclick="f_search('P');"><img src="/images/login/btn_passearch.jpg" width="85" height="19" border="0"></a></td>
							</tr>
						</table>
						</td>
					</tr>

				</table>
				</div>

				</td>
			</tr>
			<tr>
				<td height="378" align="right" valign="top">&nbsp;</td>
			</tr>
			<tr>
				<td height="125" align="right" valign="top">&nbsp;</td>
			</tr>
			<tr>
				<td height="75" align="right" valign="top">&nbsp;</td>
			</tr>
			<tr>
				<td align="center"><img src="/images/login/login_im03.jpg" width="205" height="9"><a href="mailto:yhyim@kmac.co.kr"><img
					src="/images/login/btn_mail.jpg" width="84" height="9"></a></td>
			</tr>
		</table>
		</td>
	</tr>
	<tr>
		<td align="center">&nbsp;</td>
	</tr>
</table>
</form>


</body>
</html>
