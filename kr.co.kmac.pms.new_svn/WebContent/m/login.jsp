<%@page import="org.acegisecurity.context.HttpSessionContextIntegrationFilter"%>
<%@ page import="org.acegisecurity.context.HttpSessionContextIntegrationFilter"%>
<%@ page import="org.acegisecurity.Authentication"%>
<%@ page import="org.acegisecurity.LockedException"%>
<%@ page import="org.acegisecurity.context.SecurityContext"%>
<%@ page import="org.acegisecurity.ui.AbstractProcessingFilter"%>
<%@ page import="org.acegisecurity.ui.webapp.AuthenticationProcessingFilter"%>
<%@ page import="org.acegisecurity.AuthenticationException"%>
<%@ taglib prefix="c"			uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="code"		uri="/WEB-INF/commonCode.tld" %>
<link rel="shortcut icon" href="/images/kmac_favicon.ico">
<meta name="viewport" content="width=460, user-scalable=yes">
<%
	SecurityContext context = (SecurityContext) request.getSession().getAttribute(HttpSessionContextIntegrationFilter.ACEGI_SECURITY_CONTEXT_KEY);
	if (context != null) {
		Authentication auth = context.getAuthentication();
		if (auth != null && !"guest".equals(auth.getPrincipal())) {
			response.sendRedirect(request.getContextPath() + "/forwardM.jsp");
		}
	}
	response.setHeader("Pragma", "no-cache");
	response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Expires", "0");
%>
<script type="text/javascript">
//로그인
function login(){
	if($("#j_username").val() == ''){alert('아이디를 입력하세요.');return(false);}
	if($("#j_password").val() == ''){alert('비밀번호를 입력하세요.');return(false);}
	document.loginform.submit();
	return(false); 
}
</script>
<!DOCTYPE html>
<html lang="ko">
<head>
	<script type="text/javascript">
		<c:if test="${not empty param.login_error}">
			alert("아이디 또는 패스워드를 잘못 입력 하셨습니다.");
		</c:if>
	</script>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>KMAC 통합 인트라넷</title>

    <script src="/m/resources/js/jquery-3.4.1.min.js"></script>
    <script src="/m/resources/js/jquery-ui-1.12.1.min.js"></script>
    <script src="/m/resources/js/common.js"></script>

    <link href="/m/resources/css/jquery-ui.css" rel="stylesheet" />
    <link href="/m/resources/css/kmac.css" rel="stylesheet" />
</head>
<body>
    
    <div id="wrap" class="login">

        <!-- Header -->
        <header id="header">
            <div class="logo"></div>
        </header>
        <!-- // Header -->

        <!-- Container -->
        <div id="container">

            <div class="login_contents">
                <p class="title">Log In</p>
                
                <form name="loginform" action="/j_acegi_mobile_security_check" method="post" >
	                <div class="form_box">
	                    <div>
	                        <label for="j_username"></label>
	                        <input type="text" name="j_username" id="j_username" placeholder="ID" title="아이디 입력" />
	                        <button type="button"><i class="mdi mdi-account-outline"></i></button>
	                    </div>
	                </div>
	                <div class="form_box">
	                    <div>
	                        <label for="j_password"></label>
	                        <input type="password" name="j_password" id="j_password" placeholder="Password" title="비밀번호 입력" />
	                        <button type="button"><i class="mdi mdi-lock-outline"></i></button>
	                    </div>
	                </div>
	                <div class="pass_check" style="display:none;">
							 	<input type="checkbox" class="btn_check" ch name="_acegi_security_remember_me" id="_acegi_security_remember_me" checked="checked">
							 	<label for="_acegi_security_remember_me"></label><p>&nbsp Don't ask for my password for two weeks</p>
							</div>
	                <div class="btn_area">
	                    <button type="button" class="btn c-main" onclick="login();">로그인</button>
	                </div>
                </form>

                <p class="t-line"><a href="javascript:layer_open(this,'pop_pwSearch');">비밀번호 찾기</a></p>

                <!-- 비밀번호 찾기 팝업 -->
                <div id="pop_pwSearch" class="popup_layer">
                    <div class="popup_inner">								
                        <button type="button" class="btn-close" onclick="javascript:layer_close('pop_pwSearch');" title="닫기"><i class="mdi mdi-close"></i></button>
                        <div class="popup_contents">
                            <p class="h1">비밀번호 찾기</p>
                            <p class="popup_text"><span>ID</span>, <span>휴대 전화 번호</span>를 입력하시면<br />인증코드를 전송해 드립니다.</p>                            
                            <div class="form_box">
	                            <div>											
	                                <input type="text" name="userId" id="userId" placeholder="ID"/>
	                            </div>
                            </div>
                            <div class="phone_box">											
                                <input type="text" name="phone1" id="phone1" title="휴대폰번호 국번 입력" value="010"/><span>-</span>
                                <input type="text" name="phone2" id="phone2" title="휴대폰번호 중간자리 입력" /><span>-</span>
                                <input type="text" name="phone3" id="phone3" title="휴대폰번호 끝자리 입력" />
                            </div>
                            <div class="btn_area">
                                <button type="button" class="btn c-main" onclick="javascript:getAuthToken()">다음</button>
                                <button type="button" class="btn c-grey" onclick="javascript:layer_close('pop_pwSearch')">취소</button>
                            </div>
                            <!-- <p class="t-line"><a href="javascript:;" class="d-line">이메일 문의</a></p> -->
                        </div>
                    </div>
                </div>
                <!-- // 비밀번호 찾기 팝업 -->

                 <!-- 비밀번호 재설정 팝업 -->
                 <div id="pop_newpass" class="popup_layer"><!-- 팝업 창 확인 시  class="popup_layer show" -->
                    <div class="popup_inner">								
                        <button type="button" class="btn-close" onclick="javascript:layer_close('pop_newpass');" title="닫기"><i class="mdi mdi-close"></i></button>
                        <div class="popup_contents">
                            <p class="h1">비밀번호 재설정</p>
                            <p class="text1">사용할 새로운 비밀번호를 입력하세요.</p>                  
                            <p class="text2">
                                - 비밀번호는 8자리 이상, 영문대소문자, 숫자 혼용
                            </p> 
                            <p class="text2">
                                - 3자리 이상 동일 또는 연속된 문자나 숫자는 불가 예) 111, aaa / 123, abc
                            </p>  
                            <div>											
                                <input type="password" name="authToken" id="authToken" title="인증코드 입력" placeholder="인증코드 입력" />
                                <input type="password" name="newpwd" id="newpwd" title="newpwd" placeholder="New Password" />
                                <input type="password" name="newpwd1" id="newpwd1" title="newpwd1 재입력" placeholder="New Password 재입력" />

                            </div>
                            <div class="btn_area">
                                <button type="button" class="btn c-main" onclick="javascript:authRequest_pwFgt()">다음</button>
                                <button type="button" class="btn c-grey" onclick="javascript:layer_close('pop_newpass')">취소</button>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- // 비밀번호 재설정 팝업 -->

                <div class="copyright">
                    <p>copyright © 2022 KMAC All Rights Reserved. <a href="mailto:mailadmin@kmac.co.kr">mailadmin@kmac.co.kr</a></p>
                </div>
            </div>

        </div>
        <!-- // Container -->
    </div>
   
</body>
</html>