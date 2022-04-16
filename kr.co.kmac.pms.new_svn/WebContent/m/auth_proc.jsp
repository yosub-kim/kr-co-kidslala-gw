<!DOCTYPE html>
<html lang="en">
<head>
	<%
		if(request.getParameter("device").equals("desktop")) {
	%>
		<link rel="stylesheet" href="/resources/css/common.css" type="text/css" />
		<link rel="stylesheet" href="/resources/css/login.css" type="text/css" />
		<script type="text/javascript" src="/resources/js/jquery-3.4.1.min.js"></script>
		<script type="text/javascript" src="/resources/js/jquery-ui.min.js"></script>
		<script type="text/javascript" src="/resources/js/common.js"></script>
		<script type="text/javascript">
		/* password popup layer */
		window.onload=function(){
			layer_open(this,'device_check_cookie');
		}
		</script>
	<%
		}else{
	%>
		<script src="/m/resources/js/jquery-3.4.1.min.js"></script>
	    <script src="/m/resources/js/jquery-ui-1.12.1.min.js"></script>
	    <script src="/m/resources/js/common.js"></script>
	
	    <link href="/m/resources/css/jquery-ui.css" rel="stylesheet" />
	    <link href="/m/resources/css/kmac.css" rel="stylesheet" />
	    <meta name="viewport" content="width=480, user-scalable=yes">
	    <script type="text/javascript">
		/* password popup layer */
		window.onload=function(){
			layer_open(this,'device_check_mobile_cookie');
		}
		</script>
	<%
		}
	%>
		<script type="text/javascript">
		//##########################################################################################
		//인증 관련 함수
		//##########################################################################################
		
		// 쿠키 set
		function setCookie( name, value, expiredays )
		{
			var todayDate = new Date();
			todayDate.setDate( todayDate.getDate() + expiredays );
			document.cookie = name + "=" + escape( value ) + "; path=/; expires=" + todayDate.toGMTString() + ";" ;
		}

		// 쿠키 가져오기
		function getCookie(cName) {
		     cName = cName + '=';
		     var cookieData = document.cookie;
		     var start = cookieData.indexOf(cName);
		     var cValue = '';
		     if(start != -1){
		          start += cName.length;
		          var end = cookieData.indexOf(';', start);
		          if(end == -1)end = cookieData.length;
		          cValue = cookieData.substring(start, end);
		     }
		     return unescape(cValue);
		}
		
		function getAuthToken(device){
			$.getJSON(
				"/action/UserAuthenticationAction.do?mode=getAuthenticationToken", 
				{"phone1":$("#phone1").val(), "phone2":$("#phone2").val(), "phone3":$("#phone3").val(), "device": device}, 
				function(data, status) {
					if(data.resultMsg == "1"){
						alert("인증번호를 발송하였습니다.");
						$("#device_check_cookie").hide();
						$("#authTokenInput").show();
						$("#authToken").focus();
						layer_open(this,'device_check_cookie2');
					} else if(data.resultMsg == "2"){
						alert("인증불가!\n등록된 휴대전화 번호가 아닙니다. ");
					} else if(data.resultMsg == "3"){
						alert("시스템 오류\n관리자에게 연락하기 바랍니다.");
					}
				}
			);	
		}
		
		function authRequest(device){
			$.getJSON(
				"/action/UserAuthenticationAction.do?mode=authRequest", 
				{"authToken":$("#authToken").val(), "device": device}, 
				function(data, status) {
					if(data.resultMsg == "1"){
						alert("인증되었습니다.");
						
						if(device =="mobile"){
							setCookie("m_kmacPms_auth", data.authKey, 90);
							location.href = "/forwardM.jsp";					
						} else {
							setCookie("d_kmacPms_auth", data.authKey, 90);
							location.href = "/forward.jsp";
						}
						//location.href = "/m/auth_c.jsp";
					} else if(data.resultMsg == "2"){
						alert("올바른 인증 번호가 아닙니다.");
						if(device =="mobile"){
							location.href = "/j_acegi_mobile_logout";
						} else {
							location.href = "/j_acegi_logout";
						}
					} else if(data.resultMsg == "3"){
						alert("시스템 오류\n관리자에게 연락하기 바랍니다.");
						if(device =="mobile"){
							location.href = "/j_acegi_mobile_logout";
						} else {
							location.href = "/j_acegi_logout";
						}
					}
			});		
		}
		</script>
</head> 
<body>
	<form name="loginform" action="/j_acegi_mobile_security_check" method="post" >
		<%
			if(request.getParameter("device").equals("desktop")) {
		%>
		<div class="wrap">
			<div class="login_container">
			<div class="login_bg">
				<div class="logo"><img src="/resources/img/logo_login.png" alt="KMAC" /></div>
			</div>
				<div class="login_inner">
					<div class="login_contents">
						<div id="device_check_cookie"  class="popup_layer">
							<div class="popup_inner">
							<button type="button" class="btn-close" onclick="location.href='/j_acegi_logout'" title="닫기"><i class="mdi mdi-close"></i></button>
								<div class="popup_contents">
									<p class="h1">휴대전화 인증</p>
									<p class="popup_text">KMAC 구성원 별 <span>PC 1대, 휴대전화 1대</span> 인증이 가능합니다.</br><span>기기 변경 및 다른 기기</span>로 접속하려면 재 인증을 받아야 합니다.</p>
									<div class="a-both">											
											<input type="text" name="phone1" id="phone1" placeholder="010" title="010" value="010" /><span class="dash">-</span>
											<input type="text" name="phone2" id="phone2" pattern=”[0-9]*” placeholder="휴대폰번호" title="휴대폰번호 중간자리 입력" /><span class="dash">-</span>
											<input type="text" name="phone2" id="phone3" pattern=”[0-9]*” placeholder="휴대폰번호" title="휴대폰번호 끝자리 입력" />
									</div>
									<div class="btn_area">
										<button type="button" class="btn btn_blue" onclick="getAuthToken('<%=request.getParameter("device") %>');" data-role="button" data-theme="b" data-corners="false">인증</button>
										<button type="button" class="btn btn_grey" onclick="location.href='/j_acegi_logout'" title="닫기">취소</button>
									</div>
								</div>
							</div>
						</div>
					
						<div id="device_check_cookie2"  class="popup_layer">
							<div class="popup_inner">								
								<button type="button" class="btn-close" onclick="location.href='/j_acegi_logout'" title="닫기"><i class="mdi mdi-close"></i></button>
								<div class="popup_contents">
									<p class="h1">인증코드 입력</p>
									<p class="popup_text">인증코드를 입력 후 <span>확인</span>을 누르시기 바랍니다.</p>
									<div class="form_box">
										<div>
											<label for="authToken"></label>
											<input type="text" name="authToken" id="authToken" placeholder="인증코드 입력" />
										</div>
									</div>
									<div class="btn_area">
										<button type="button" class="btn btn_blue" onclick="authRequest('<%=request.getParameter("device") %>')" data-role="button" data-theme="b" data-corners="false">확인</button>
										<button type="button" class="btn btn_grey" onclick="location.href='/j_acegi_logout'" title="닫기">취소</button>
									</div>
									<!-- <a href="javascript:;" class="d-line">이메일 문의</a> -->
								</div>
							</div>
						</div>	
					</div>
				</div>
			</div>
		</div>
		
		<%
			}else{
		%>
	  		<div id="device_check_mobile_cookie" class="popup_layer">
                    <div class="popup_inner">								
                        <button type="button" class="btn-close" onclick="location.href='/j_acegi_mobile_logout'" title="닫기"><i class="mdi mdi-close"></i></button>
                        <div class="popup_contents">
                            <p class="h1">휴대전화 인증</p>
                            <p class="popup_text">KMAC 구성원 별 <span>휴대전화 1대</span>인증이 가능합니다.<br />다른 기기로 접속하려면 재 인증을 받아야 합니다.</p>                            
                            <div class="phone_box">											
                              	<input type="text" name="phone1" id="phone1" placeholder="010" title="010" value="010" /><span class="dash">-</span>
								<input type="text" name="phone2" id="phone2" pattern=”[0-9]*” placeholder="휴대폰번호" title="휴대폰번호 중간자리 입력" /><span class="dash">-</span>
								<input type="text" name="phone2" id="phone3" pattern=”[0-9]*” placeholder="휴대폰번호" title="휴대폰번호 끝자리 입력" />
							</div>
                            <div class="btn_area">
                                <button type="button" class="btn c-main" onclick="getAuthToken('<%=request.getParameter("device") %>')">다음</button>
                                <button type="button" class="btn c-grey" onclick="location.href='/j_acegi_mobile_logout'">취소</button>		
                            </div>
                            <!-- <p class="t-line"><a href="javascript:;" class="d-line">이메일 문의</a></p> -->
                        </div>
                    </div>
                </div>
                
                <div id="device_check_cookie2" class="popup_layer"><!-- 팝업 창 확인 시  class="popup_layer show" -->
                    <div class="popup_inner">								
                        <button type="button" class="btn-close" onclick="location.href='/j_acegi_mobile_logout'" title="닫기"><i class="mdi mdi-close"></i></button>
                        <div class="popup_contents">
                            <p class="h1">인증코드 입력</p>
                            <p class="popup_text">인증코드를 입력 후 <span>확인</span>을 누르시기 바랍니다.</p>                            
                            <div>							
                            	<label for="authToken"></label>				
                                <input type="text" name="authToken" id="authToken" title="인증코드 입력" />
                            </div>
                            <div class="btn_area">
                                <button type="button" class="btn c-main" onclick="authRequest('<%=request.getParameter("device") %>')">확인</button>
                                <button type="button" class="btn c-grey" onclick="location.href='/j_acegi_mobile_logout'">닫기</button>
                            </div>
                            <!-- <p class="t-line"><a href="javascript:;" class="d-line">인증번호 재전송</a></p> -->
                        </div>
                    </div>
                </div>
		<%} %>
	
	</form>
</body>
</html>
