<!doctype html>
<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<%@ page import="org.acegisecurity.context.HttpSessionContextIntegrationFilter"%>
<%@ page import="org.acegisecurity.Authentication"%>
<%@ page import="org.acegisecurity.LockedException"%>
<%@ page import="org.acegisecurity.context.SecurityContext"%>
<%@ page import="org.acegisecurity.ui.AbstractProcessingFilter"%>
<%@ page import="org.acegisecurity.ui.webapp.AuthenticationProcessingFilter"%>
<%@ page import="org.acegisecurity.AuthenticationException"%>
<link rel="shortcut icon" href="/images/kmac_favicon.ico">


<%
	SecurityContext context = (SecurityContext) request.getSession().getAttribute(HttpSessionContextIntegrationFilter.ACEGI_SECURITY_CONTEXT_KEY);
	if (context != null) {
		Authentication auth = context.getAuthentication();
		if (auth != null && !"guest".equals(auth.getPrincipal())) {
			response.sendRedirect(request.getContextPath() + "/forward.jsp");
		}
	}
	
	response.setHeader("Pragma", "no-cache");
	response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Expires", "0");
%>

<html lang="ko">
<head>
<meta charset="utf-8">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="Generator" content="EditPlus" charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport" content="width=1600">
<title>KMAC 통합 인트라넷</title>
<link rel="stylesheet" href="/resources/css/common.css" type="text/css" />
<link rel="stylesheet" href="/resources/css/login.css" type="text/css" />
<link rel="stylesheet" href="/resources/kidzlala/main.css" type="text/css" />
<link rel="stylesheet" href="/resources/kidzlala/base.css" type="text/css" />
<script type="text/javascript" src="/resources/js/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="/resources/js/jquery-ui.js"></script>
<script type="text/javascript" src="/resources/js/common.js"></script>
<style>
	.gnb {
	    width: 100%;
	    height: 45px;
	    background-color: #4f4cce;
	    text-align: center;
	    font-size: 16px;
	    padding-top: 12px;
	    color: white;
	    font-weight: bold;
	}
	
	.button1 { 
	    height: 30px;
	    background-color: #25355c;
	    font-size: 15px;
 		border-radius: 5px 5px 5px 5px;
 		border-style: none;
	}
</style>
<script type="text/javascript">
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
       			window.open('https://intranet.kmac.co.kr/kmac/pwmanage/idsearch.asp','idsearch','width=340,height=215,scrollbars=no');
       		break;
       		case "P":
       			location.href = "/pwalert.jsp";
       		break;
       	}
       }        
</script>
 
<% 
	// homepage 접속
	String kmac = request.getParameter("_ntype"); 
%>
<%-- <%
	// IE 접속 차단
	String browser = ""; 
	String userAgent = request.getHeader("User-Agent");
	if(userAgent.indexOf("Trident") > 0 || userAgent.indexOf("MSIE") > 0){ %>
		<script type="text/javascript">
			alert("이 브라우저는 더이상 지원되지 않습니다. Chrome, Edge 등 최신 브라우저를 사용하여 주시기 바랍니다.");
			location = 'https://www.kmac.co.kr/main/index.asp';
		</script>
<% }else{ %>
	<script type="text/javascript">
	var t = "<%=kmac%>";
	if (navigator.userAgent.match(/iPhone|iPod|iPad|Android|Windows CE|BlackBerry|Symbian|Windows Phone|webOS|Opera Mini|Opera Mobi|POLARIS|IEMobile|lgtelecom|nokia|SonyEricsson/i) != null 
			|| navigator.userAgent.match(/LG|SAMSUNG|Samsung/) != null){
		top.document.location.href = "/m/login.jsp";
	} else {	
		if(t != 'kmac'){
			var ref = document.referrer;
			//alert(ref);
			if(String(ref).indexOf('mode=mainPage') != -1){
				document.write(" ");
			} else if(String(ref).indexOf('login.jsp') != -1){
				document.write(" ");
			} else if(String(ref).indexOf('?_ntype=kmac') != -1){
				out.println(" ");
			} else if(String(ref).indexOf('pwalert.jsp') != -1){ 
				document.write(" ");
			} else if(String(ref).indexOf('/m/auth_msg.jsp?device=desktop') != -1){
				document.write(" ");
			} else if (String(ref).indexOf('/accessDenied.jsp') != -1){
				document.write(" ");
			} else {
				alert("KMAC 통합 인트라넷은 회사 홈페이지를 통해서만 접속 가능합니다.");
				location='https://www.kmac.co.kr/main/index.asp';
			}
		} else {
			
		}
	}
	</script>
<%} %> --%>
<script type="text/javascript">
var t = "<%=kmac%>";
if (navigator.userAgent.match(/iPhone|iPod|iPad|Android|Windows CE|BlackBerry|Symbian|Windows Phone|webOS|Opera Mini|Opera Mobi|POLARIS|IEMobile|lgtelecom|nokia|SonyEricsson/i) != null 
		|| navigator.userAgent.match(/LG|SAMSUNG|Samsung/) != null){
	top.document.location.href = "/m/login.jsp";
} else {
	if(t != 'kmac'){
		var ref = document.referrer;
		//alert(ref);
		if(String(ref).indexOf('mode=mainPage') != -1){
			document.write(" ");
		} else if(String(ref).indexOf('login.jsp') != -1){
			document.write(" ");
		} else if(String(ref).indexOf('?_ntype=kmac') != -1){
			out.println(" ");
		} else if(String(ref).indexOf('pwalert.jsp') != -1){ 
			document.write(" ");
		} else if(String(ref).indexOf('/m/auth_msg.jsp?device=desktop') != -1){
			document.write(" ");
		} else if (String(ref).indexOf('/accessDenied.jsp') != -1){
			document.write(" ");
		} else {
			//alert("KMAC 통합 인트라넷은 회사 홈페이지를 통해서만 접속 가능합니다.");
			//location='https://www.kmac.co.kr/main/index.asp';
		}
	} else {
		
	}
}
</script>
</head> 

<% String browser = ""; 
   String userAgent = request.getHeader("User-Agent");
   if(userAgent.indexOf("Trident") > 0 || userAgent.indexOf("MSIE") > 0){ %>
	<div class="gnb">
		<b>Internet Explorer 서비스 지원이 곧 종료됩니다. <ins>Chrome, Edge 등 최신 브라우저를 사용해 주시기 바랍니다.</ins>&nbsp&nbsp&nbsp
		<button class="button1" onclick="window.open('https://www.google.com/intl/ko/chrome/')"><font color="white"><b>&nbspChrome 다운로드&nbsp</b></font></button>
		<button class="button1" onclick="window.open('https://www.microsoft.com/ko-kr/edge')"><font color="white"><b>&nbspEdge 다운로드&nbsp</b></font></button></b>
	</div>
<%} %>  

<body onLoad="Page_Load();">	

		<div class="wrap">

			<div class="login_container">

			<div class="main01">
                <div class="vis_t">
                    <i class="Stit" lang="en">KIDS THEME PARK</i>
                    <p class="Stitm">어린이 테마파크</p>
                    <h2 class="Tyg">키즈라라</h2>
                    <p class="scRoll"></p>
                </div>
                <div class="vis_m"><img src="/resources/kidzlala/m_star.png" alt="별 일러스트" class="rela">
                    <img src="/resources/kidzlala/m_kids.png" alt="아이들 일러스트" class="absl"></div>
                    <div id="container">
            <div class="main02" style="padding: 600px 0 0 0">
                <div class="conWr" style="height:100%;">
                    <!-- <article>
                        <div class="subTit">
                            <i class="Stit" lang="en">KIDS THEME PARK</i>
                            <h2>키즈라라 소개</h2>
                        </div>
                    </article>

                    <section>
                    <div class="subTit">
                        <i class="Stit" lang="en">KIDSLALA STORY</i>
                        <h2>키즈라라 소식</h2>
                    </div>
                    <div class="clearfix">
                       
                    </div>
                    </section> -->
                      
                </div>
            </div>
        </div>
            </div>
				<!-- // login_bg -->		

				<div class="login_inner">
					<div class="login_contents">
						<p class="h1">Log In</p>
						<!-- <p class="login_info_text">KIDSLALA INTRANET</p> -->
						<form name="loginform" action="<c:url value='j_acegi_security_check'/>" method="POST" onSubmit="return login();"> 
							<div class="form_box">
								<div>
									<label for="userId"></label>
									<input type="hidden" name='agentInfo' />
									<input type="text" title="아이디 입력" name="j_username" id="j_username" placeholder="ID" onKeyPress="if(event.keyCode == 13){ login(); }" />
									<button type="button"><i class="mdi mdi-account-outline"></i></button>
								</div>
							</div>
							<div class="form_box">
								<div>
									<label for="userPassword"></label>
									<input type="password" type="password" name="j_password" id="j_password" placeholder="Password" onKeyPress="if(event.keyCode == 13){ login(); }" />
									<button type="button"><i class="mdi mdi-lock-outline"></i></button>
								</div>
							</div>
							<div class="pass_check">
							 	<input type="checkbox" class="btn_check" ch name="_acegi_security_remember_me" id="_acegi_security_remember_me" checked="checked">
							 	<label for="_acegi_security_remember_me"></label><p>&nbsp Don't ask for my password for two weeks</p>
							</div>
						</form>
						
						</br>
						<div class="a-both">
							<button type="button" class="btn btn_blue" onclick="login();">Login</button>
							<a href="javascript:f_search('P');" class="d-line">비밀번호 찾기</a>
						</div>

						<!-- 비밀번호 찾기 팝업 -->
						<div id="pop_pwSearch" class="popup_layer">
							<div class="popup_inner">								
								<button type="button" class="btn-close" onclick="javascript:layer_close('pop_pwSearch');" title="닫기"><i class="mdi mdi-close"></i></button>
								<div class="popup_contents">
									<p class="h1">비밀번호 찾기</p>
									<p class="popup_text">가입 시 입력한 <span>휴대 전화 번호</span>를 입력하시면<br />인증코드를 전송해 드립니다.</p>
									<div class="form_box">
										<div>
											<label for="userPhone"></label>
											<input type="text" name="userPhone" id="userPhone" title="휴대전화 번호 입력" />
										</div>
									</div>
									<div class="btn_area">
										<button type="button" class="btn btn_blue" onclick="location.href='javascript:;'">다음</button>
										<button type="button" class="btn btn_grey" onclick="location.href='javascript:;'">취소</button>
									</div>
									<a href="javascript:;" class="d-line">이메일 문의</a>
								</div>
							</div>
						</div>
						<!-- // 비밀번호 찾기 팝업 -->

						<div class="copyright">
							<p>copyright © 2021 KIZLALA All Rights Reserved.<!-- <a href="mailto:mailadmin@kmac.co.kr">mailadmin@kmac.co.kr</a> --></p>
						</div>
					</div>
				</div>
				<!-- // login_inner -->	

			</div>
			<!-- // login_container -->				

		</div>
		<!-- // wrap -->

	</body>
</html>

<c:if test="${not empty param.login_error}">
	<%	
	Exception e = (Exception)request.getSession().getAttribute("ACEGI_SECURITY_LAST_EXCEPTION"); 
	if(e instanceof LockedException ) {
	%>
		<script>
			alert("5회 이상 로그인 실패로 계정이 잠겼습니다. \n본인 인증 후 새 비밀번호를 등록하세요."	);
			location.href = "pwalert.jsp";
		</script>
	<%			
	} else {
	%>
		<script>
			alert("아이디 또는 비밀번호를 다시 확인하세요. \n"+ "등록되지 않은 아이디이거나, 아이디 또는 비밀번호를 잘못 입력하셨습니다."	);
		</script>
	<%
	}
	%>
</c:if>
