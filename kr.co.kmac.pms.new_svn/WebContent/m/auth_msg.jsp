<!DOCTYPE html>
<html lang="en">
<% Cookie[] cookies = request.getCookies(); %> 
<%!    
    private String getCookieValue(Cookie[] cookies, String name) {
        String value = null;
        if (cookies == null) {
            return null;
        }
        
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(name)) {  
            	System.out.println(cookie.getValue());
                return cookie.getValue();
            }
        }
        return null;
    }
%>
<%
	String cookieValue = getCookieValue(cookies, "d_kmacPms_auth");//클라이언트 쿠키값
	if(cookieValue == null || cookieValue.equals("")){
		 
	}else{
		response.sendRedirect(request.getContextPath() + "/pageRedirect.jsp");
	}
%>

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
			layer_open(this,'device_check');
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
				layer_open(this,'device_check');
			}
			</script>
	<%
		}
	%>
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
					<div id="device_check" class="popup_layer">
						<div class="popup_inner">
							<button type="button" class="btn-close" onclick="location.href='/j_acegi_logout'" title="닫기"><i class="mdi mdi-close"></i></button>
							<div class="popup_contents">
								<p class="h1">기기 인증</p>
								<p class="popup_text">해당 기기로 통합 인트라넷에 접속하기 위해 <br><span>사용자 인증</span>이 필요합니다.<br/>
								<div class="btn_area">
									<button type="button" class="btn btn_blue" onclick="location.href='/m/auth_proc.jsp?device=<%=request.getParameter("device") %>'">다음</button>
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
         <div id="device_check" class="popup_layer">
             <div class="popup_inner">								
                 <button type="button" class="btn-close" onclick="javascript:layer_close('pop_pwSearch');" title="닫기"><i class="mdi mdi-close"></i></button>
                 <div class="popup_contents">
                     <p class="h1">기기 인증</p>
                     <p class="popup_text">해당 기기로 모바일 인트라넷에 접속하기 위해 <br><span>사용자 인증</span>이 필요합니다.</p>                            
                    
                     <div class="btn_area">
                         <button type="button" class="btn c-main" onclick="location.href='/m/auth_proc.jsp?device=<%=request.getParameter("device") %>'">다음</button>
                         <button type="button" class="btn c-grey" onclick="location.href='/j_acegi_mobile_logout'">취소</button>
                     </div>
                     <p class="t-line"><a href="javascript:;" class="d-line"></a></p>
                 </div>
             </div>
         </div>
		<%} %>
	</form>
</body>
</html>
