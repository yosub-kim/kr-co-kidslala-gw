<%@page import="org.acegisecurity.BadCredentialsException"%>
<%@page import="org.acegisecurity.LockedException"%>
<%
String userId = "";
if(request.getSession().getAttribute("ACEGI_SECURITY_LAST_EXCEPTION") != null){
	Exception e = (Exception)(request.getSession().getAttribute("ACEGI_SECURITY_LAST_EXCEPTION"));
	
	if(e instanceof LockedException) {
		userId = ((LockedException)e).getAuthentication().getPrincipal().toString();		
	} else if(e instanceof BadCredentialsException) {
		userId = ((BadCredentialsException)e).getAuthentication().getPrincipal().toString();
	}
}
String pwChgType = "pwFgt";
if(session.getAttribute("needToPasswordUpdate") != null && session.getAttribute("needToPasswordUpdate").equals("true")){
	pwChgType = "pwExprd";
}
%>

<!doctype html>
<%@ page contentType="text/html; charset=utf-8"%>
<html lang="ko">
<head>
<meta charset="utf-8">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title>KMAC 통합 인트라넷 비밀번호 변경안내</title>
<link rel="stylesheet" href="/resources/css/common.css" type="text/css" />
<link rel="stylesheet" href="/resources/css/login.css" type="text/css" />
<script type="text/javascript" src="/resources/js/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="/resources/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="/resources/js/common.js"></script>
<!-- <link rel="stylesheet" href="https://intranet.kmac.co.kr/kmac/common/css/pw.css" type="text/css">  -->
<script type="text/javascript" src="https://code.jquery.com/jquery-latest.min.js"></script>

<script language="javascript">
function getAuthToken(){
	if($("#phone2").val() == ""){alert("휴대폰 번호를 입력하세요.");return;}
	if($("#phone3").val() == ""){alert("휴대폰 번호를 입력하세요.");return;}
	alert("인증번호 발송을 요청하였습나다.");
	$.getJSON("/noauthaction/UserAuthenticationAction.do?mode=getAuthenticationTokenFromWeb", 
			{
				"userId":$("#userId").val(), 
				"phone1":$("#phone1").val(), 
				"phone2":$("#phone2").val(), 
				"phone3":$("#phone3").val()
			}, 
			function(data, status) {
				if(data.resultMsg == "1"){
					//alert("인증번호 발송 ");
					$("#auth").show();
					layer_open(this,'pop_pwSearch2');
				} else if(data.resultMsg == "2"){
					alert("인증불가!\n등록된 휴대전화 번호가 아닙니다. ");
				} else if(data.resultMsg == "3"){
					alert("시스템 오류\n관리자에게 연락하기 바랍니다.");
				}
	});
}

function authRequest_pwExprd(){
	if($("#oldpwd").val() == ""){alert("기존 비밀번호를 입력하세요.");return;}
	if($("#newpwd").val() == ""){alert("새로운 비밀번호를 입력하세요.");return;}
	if($("#newpwd1").val() == ""){alert("새로운 비밀번호 확인을 입력하세요.");return;}
	
	var str = fn_pw_check();
	if(str==true){
		$.getJSON(
				"/noauthaction/UserAuthenticationAction.do?mode=authRequestFromPwExprd", 
				{
					"oldpwd":$("#oldpwd").val(), 
					"userId":$("#userId").val(),
					"newpwd":$("#newpwd").val()
				},
				function(data, status) {
					if(data.resultMsg == "1"){
						webmailForm.user_account.value = data.emailAddr;
	                	webmailForm.new_pass.value = data.encPwd;
	                	webmailForm.is_encrypt.value = 1;
						webmailForm.action="https://webmail.kmac.co.kr/a_biz/api/change_pass.nvd";
						webmailForm.target = "hiddenifr"
	                	webmailForm.submit();
						alert("비밀번호를 변경하였습니다.");
						location.href = "/forward.jsp";
					} else if(data.resultMsg == "2"){
						alert("기존 비밀번호를 정확히 입력하세요.");
						$("#oldpwd").val('');
					} else if(data.resultMsg == "3"){
						alert("시스템 오류\n관리자에게 연락하기 바랍니다.");
					}
		});
	}
	
}

function authRequest_pwFgt(){ 
	if($("#newpwd").val() == ""){alert("새로운 비밀번호를 입력하세요.");return;}
	if($("#newpwd1").val() == ""){alert("새로운 비밀번호 확인을 입력하세요.");return;}
	var str = fn_pw_check();
	if(str==true){
		$.getJSON(
				"/noauthaction/UserAuthenticationAction.do?mode=authRequestFromPwFgt", 
				{
					"authToken":$("#authToken").val(), 
					"userId":$("#userId").val(),
					"newpwd":$("#newpwd").val(),
					"device": ''
				},
				function(data, status) {
					if(data.resultMsg == "1"){
						alert("비밀번호를 변경하였습니다.");
						location.href = "/j_acegi_logout";
					} else if(data.resultMsg == "2"){
						alert("올바른 인증 번호가 아닙니다.");
						$("#authToken").val('');
					} else if(data.resultMsg == "3"){
						alert("시스템 오류\n관리자에게 연락하기 바랍니다.");
					}
		});
	}
	
}

function fn_pw_check(){
	var pw = $("#newpwd").val();
	var pw2 = $("#newpwd1").val();
	var id = $("#userId").val();
	
	pw_passed = true;
	var pattern1 = /[0-9]/;
	var pattern2 = /[a-zA-Z]/;
	var pattern3 = /[~!@\#$%<>^&*/]/;     // 원하는 특수문자 추가 제거
	var pw_msg = "";
	
	if(pw.length ==0){alert("비밀번호를 입력해주세요");return false;}
	if($("#oldpwd").val() == pw) {alert("새 비밀번호는 현재 비밀번호와 다르게 해야 합니다.");return false;}
	if(pw != pw2){alert("비밀번호가 일치하지 않습니다.");return false;}
	
	if(!pattern1.test(pw)||!pattern2.test(pw)||pw.length<8||pw.length>50){
		alert("비밀번호는 영문 + 특수문자(~!@\#$%<>^&* 포함) + 숫자조합 8자리 이상으로 설정을 해주십시오.");
		return false;
	}
	
	if(pw.indexOf(id) > -1) {alert("비밀번호에 아이디를 사용할 수 없습니다.");return false;}
	
	var SamePass_0 = 0; //동일문자 카운트
	var SamePass_1 = 0; //연속성(+) 카운드
	var SamePass_2 = 0; //연속성(-) 카운드
	
	for(var i=0; i < pw.length; i++) {
		var chr_pass_0 = pw.charCodeAt(i-2);
		var chr_pass_1 = pw.charCodeAt(i-1);
		var chr_pass_2 = pw.charCodeAt(i);
		//동일문자 카운트
		if(i > 2) {    
			if((chr_pass_0 == chr_pass_1) && (chr_pass_1 == chr_pass_2)) {
				SamePass_0++;
			} else {
				SamePass_0 = 0;
			}
			//연속성(+) 카운드
			if(chr_pass_0 - chr_pass_1 == 1 && chr_pass_1 - chr_pass_2 == 1) {
				SamePass_1++;
			} else {
				SamePass_1 = 0;
			}
			//연속성(-) 카운드
			if(chr_pass_0 - chr_pass_1 == -1 && chr_pass_1 - chr_pass_2 == -1) {
				SamePass_2++;
			} else {
				SamePass_2 = 0;
			}
		}
		if(SamePass_0 > 0) {
			alert("동일문자를 3번 이상 사용할 수 없습니다.");
			pw_passed=false;
			return false;
		}
		
		if(SamePass_1 > 0 || SamePass_2 > 0 ) {
			alert("연속된 문자열(123 또는 321, abc, cba 등)을 3개이상 올 수 없습니다.");
			pw_passed=false;
			return false;
		}
		if(!pw_passed) {
			break;
		}
 	}
	if( !pw_passed) {
		return false;
	}
	return true;
}
/* password popup layer */
window.onload=function(){
	layer_open(this,'pop_pwSearch');
}

function newPage()  {
	  window.location.href = 'https://newpms.kmac.co.kr'
	}
</script> 	 
</head>

<body>
		
<div class="wrap">
	<div class="login_container">
		<div class="login_bg">
			<div class="logo"><img src="/resources/img/logo_login.png" alt="KMAC" /></div>
		</div>
		<!-- // login_bg -->	
		<%	if(pwChgType.equals("pwFgt")){ %>
		<div class="login_inner">
			<div class="login_contents">
				<div id="pop_pwSearch"  class="popup_layer">
					<div class="popup_inner">								
						<button type="button" class="btn-close" onclick="javascript:newPage();" title="닫기"><i class="mdi mdi-close"></i></button>
						<div class="popup_contents">
							<p class="h1">비밀번호 찾기</p>
							<p class="popup_text">가입 시 입력한 <span>ID, 휴대폰 번호</span>를 입력하시면<br />인증코드를 전송해 드립니다.</p>
							<div class="form_box">
								<div>
									<label for="userId"></label>
									<input type="text" name="userId" id="userId" placeholder="ID" title="휴대전화 번호 입력" />
								</div>
							</div>
							<div class="a-both">											
									<input type="text" name="phone1" id="phone1" placeholder="010" title="010" value="010" /><span class="dash">-</span>
									<input type="text" name="phone2" id="phone2" placeholder="휴대폰번호" title="휴대폰번호 중간자리 입력" /><span class="dash">-</span>
									<input type="text" name="phone2" id="phone3" placeholder="휴대폰번호" title="휴대폰번호 끝자리 입력" />
							</div>
							<div class="btn_area">
								<button type="button" class="btn btn_blue" onclick="javascript:getAuthToken();">다음</button>
								<button type="button" class="btn btn_grey" onclick="javascript:newPage();">취소</button>
							</div>
							<!-- <a href="javascript:;" class="d-line">이메일 문의</a> -->
						</div>
					</div>
				</div>
				<div id="pop_pwSearch2"  class="popup_layer">
					<div class="popup_inner">								
						<button type="button" class="btn-close" onclick="javascript:newPage();" title="닫기"><i class="mdi mdi-close"></i></button>
						<div class="popup_contents">
							<p class="h1">비밀번호 찾기</p>
							<p class="popup_text">인증코드를 입력 후 <span>새 비밀번호, 새 비밀번호 확인</span>을 </br>입력하시기 바랍니다.</p>
							<div class="form_box">
								<div>
									<label for="authToken"></label>
									<input type="text" name="authToken" id="authToken"  placeholder="인증번호" title="인증번호" />
								</div>
							</div>
							<div class="form_box">
								<div>
									<label for="newpwd"></label>
									<input type="password" name="newpwd" id="newpwd"  placeholder="새 비밀번호" title="새 비밀번호" />
								</div>
							</div>
							<div class="form_box">
								<div>
									<label for="newpwd1"></label>
									<input type="password" name="newpwd1" id="newpwd1"  placeholder="새 비밀번호 확인" title="새 비밀번호 확인" />
								</div>
							</div>
							<div class="btn_area">
								<button type="button" class="btn btn_blue" onclick="location.href='javascript:authRequest_pwFgt();'">변경</button>
								<button type="button" class="btn btn_grey" onclick="javascript:newPage();">취소</button>
							</div>
							<!-- <a href="javascript:;" class="d-line">이메일 문의</a> -->
						</div>
					</div>
				</div>	
			</div>
		</div>
		<%	} else if(pwChgType.equals("pwExprd")){ %>		
		<div class="login_inner password"><!-- 비빌번호 변경 페이지 .password -->
			<div class="login_contents">
				<p class="h1">통합 인트라넷 비밀번호 변경안내</p>
		    	<p class="h2">통합 인트라넷의 비밀번호 작성 규칙이 강화 되었습니다.</p>
					<p class="h3"><%=session.getAttribute("name") %>(<%=session.getAttribute("userId") %>)님</p>
					<p class="info_text">
							KMAC는 통합 인트라넷 사용자 정보 보호를 위해<br />
							<span>'최종 비밀번호 변경 후, 90일 경과'</span>시 로그인할 때 비밀번호 변경을 요청드리고 있습니다.<br />
							소중한 정보를 보호하기 위하여 비밀번호를 변경해 주시기 바랍니다.
					</p>
  					<p class="h3">비밀번호 변경 시 유의사항</p>
					<p class="info_text">
						여러 사이트에서 동일한 비밀번호를 사용하시는 경우,<br />
						개인정보 유출로 인한 피해가 연쇄적으로 발생할 수 있으니,<br />
						가급적 사이트별로 비밀번호를 다르게 설정하시기 바랍니니다.
					</p>
					<p class="h3">비밀번호 작성 규칙 안내</p>
					<p class="info_text">
						- 비밀번호는 8자리 이상, 영문대소문자, 숫자를 혼용하셔야 합니다.<br />
						- 3자리 이상 동일한 문자나 숫자를 허용하지 않습니다. 예) 111, aaa<br />
						- 3자리 이상 연속된 문자나 숫자를 허용하지 않습니다. 예) 123, abc
					</p>
	            <!-- 비밀번호 변경 문구 안내 끝-->
	            <!-- 비밀번호 입력 부분 시작 -->
				<form name="fm1" method="post">
					<%-- <%	if(pwChgType.equals("pwFgt")){ %>	
					<%	} else if(pwChgType.equals("pwExprd")){ %>										
					<%	} %> --%>
					<input type="hidden" 	name="userId" 		id="userId"		value="<%=session.getAttribute("userId")%>">
					<div class="form_box">
						<div>
							<label for="nowPassword"></label>
							<input type="password" name="oldpwd" id="oldpwd" placeholder="현재 비밀번호 입력" title="현재 비밀번호 입력" />
						</div>
					</div>
					<div class="form_box">
						<div>
							<label for="newPassword"></label>
							<input type="password" name="newpwd" id="newpwd" placeholder="새 비밀번호 입력" title="새 비밀번호 입력" />
						</div>
					</div>
					<div class="form_box">
						<div>
							<label for="confPassword"></label>
							<input type="password" name="newpwd1" id="newpwd1" placeholder="새 비밀번호 입력 확인" title="새 비밀번호 입력 확인" />
						</div>
					</div>

					<div class="btn_area w-1">
						<button type="button" class="btn btn_blue" onclick="location.href='javascript:authRequest_pwExprd();'">Login</button>
					</div>

					<div class="copyright">
						<p>copyright © 2021 KMAC All Rights Reserved.<a href="mailto:mailadmin@kmac.co.kr">mailadmin@kmac.co.kr</a></p>
					</div>
				</form>
				<!-- 비밀번호 입력 부분 끝 -->
				<form name="webmailForm" method="post">
					<input type="hidden" name="user_account" id="user_account" />
					<input type="hidden" name="is_encrypt" id="is_encrypt" />
					<input type="hidden" name="new_pass" id="new_pass" />
				</form>
				<iframe id="hidden" name="hiddenifr" src="" style="display: none;"></iframe>
			</div>
		</div>
		<!-- // login_inner -->	
		<%	} %>
	</div>
	<!-- // login_container -->				
</div>
	<!-- // wrap -->
</body>
</html>

