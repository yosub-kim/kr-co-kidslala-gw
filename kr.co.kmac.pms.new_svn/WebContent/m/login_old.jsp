<%@page import="org.acegisecurity.context.HttpSessionContextIntegrationFilter"%>
<%@page import="org.acegisecurity.Authentication"%>
<%@page import="org.acegisecurity.context.SecurityContext"%>
<%@ taglib prefix="c"			uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="code"		uri="/WEB-INF/commonCode.tld" %>
<%
	SecurityContext context = (SecurityContext) request.getSession().getAttribute(HttpSessionContextIntegrationFilter.ACEGI_SECURITY_CONTEXT_KEY);
	if (context != null) {
		Authentication auth = context.getAuthentication();
		if (auth != null && !"guest".equals(auth.getPrincipal())) {
			response.sendRedirect(request.getContextPath() + "/m/index.jsp");
		}
	}
%>	
 
<!DOCTYPE html>
<html lang="en">
<head>
	<%@ include file="/m/web/common/includeMeta.jsp"%>
	<%@ include file="/m/web/common/includeCss.jsp"%>
	<%@ include file="/m/web/common/includeJavaScript.jsp"%>
	<script type="text/javascript">
		<c:if test="${not empty param.login_error}">
			alert("아이디 또는 패스워드를 잘못 입력 하셨습니다.");
		</c:if>
	</script>
</head> 
<body>
	<form name="loginform" action="/j_acegi_mobile_security_check" method="post" >

		<div data-role="page">
			<%--
			<div data-role="header" data-position="fixed" data-theme="c">
				<h1>KMAC 인트라넷</h1>
			</div><!-- /header -->
			 --%>

			
			<div data-role="content">	
				<div class="ui-grid-solo" style="text-align: left; color: #1155cc; margin-bottom: 20px; margin-top: 20px;">
					<img alt="top_logo" src="/m/img/kmac_new.png">
				</div>
				<div class="ui-grid-a" style="margin-bottom: 5px;">
					<div class="ui-block-a" style="padding: 0px; font-weight: bold;">
						Login 
					</div>
					<div class="ui-block-b" style="vertical-align:middle; text-align: right;">
						<img alt="top_logo" src="/m/img/top_logo.gif" height="12px;">
					</div>	    
				</div>		
				<div style="border-style: solid; padding: 8px 5px 8px 5px; border-width: 1px; border-color: #d1d1d4;">
					<div data-role="fieldcontain" class="ui-body ui-br" style="border-style: none; padding-top: 0.2em; padding-bottom: 0.8em;">
						<label for="j_username" class="ui-input-text" style="font-size: 12px;">사용자 아이디</label>
						<input type="text" name="j_username" id="j_username" value="" placeholder=""/>
					</div>
					<div data-role="fieldcontain" class="ui-body ui-br" style="border-style: none; padding-top: 0.2em; padding-bottom: 0.8em;">
						<label for="j_password" class="ui-input-text" style="font-size: 12px;">비밀번호</label>
						<input type="password" name="j_password" id="j_password" value="" placeholder="" />
					</div>

					<div class="ui-grid-b">
						<div class="ui-block-a" style="padding: 0px;">
							<a href="#" onclick="login();" data-role="button" data-theme="b" data-corners="false">로그인</a> 
						</div>
						<div class="ui-block-b" style="vertical-align:middle;">
						</div>	    
						<div class="ui-block-c" style="vertical-align:middle;">
						</div>	    
					</div>							
				</div>				
			
				<div class="ui-grid-solo" style="text-align: left; margin-top: 20px;">
					<a href="/m/recover.jsp" data-transition="slide" style="color: #1155cc; font-size: 12px;">로그인을 할 수 없습니까?</a>
				</div>				
				<div class="ui-grid-solo" style="height: 500px;">
				</div>							
			</div><!-- /content -->
			
			<div data-role="footer" class="footer-docs" data-theme="c" data-position="fixed">
				<p>&copy; 2013 KMAC All Rights Reserved.</p>
			</div><!-- /footerx	 -->

		</div><!-- /page -->

	
	</form>
</body>
</html>
