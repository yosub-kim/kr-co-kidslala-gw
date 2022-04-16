<%@ taglib prefix="c" 		uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="fmt"		uri="http://java.sun.com/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
	<%@ include file="/m/web/common/includeMeta.jsp"%>
	<%@ include file="/m/web/common/includeCss.jsp"%>
	<%@ include file="/m/web/common/includeJavaScript.jsp"%>
</head>
<body>

	<div data-role="page" id="first">
		<div data-role="header" data-position="fixed" data-theme="a">
			<a href="/m/login.jsp" data-icon="arrow-l" data-iconpos="notext" data-direction="reverse">LOGIN</a>
			<h1>KMAC 인트라넷</h1>
		</div><!-- /header -->
		
		<div data-role="content">	
			<h3>로그인을 할 수 없습니까?</h3>
			<div style="height: 20px;"></div>
			<fieldset data-role="controlgroup">
		     	<input type="radio" name="radio-choice" id="radio-choice-1" value="choice-1" onclick="javascript:search_id();"/>
		     	<label for="radio-choice-1">아이디를 잊어버렸습니다.</label>
			</fieldset>		
			<div style="height: 20px;"></div>
			<fieldset data-role="controlgroup">
		     	<input type="radio" name="radio-choice" id="radio-choice-2" value="choice-2" onclick="javascript:search_pass();"/>
		     	<label for="radio-choice-2">비밀번호를 잊어버렸습니다.</label>
			</fieldset>		
						
		</div><!-- /content -->
		
		<div data-role="footer" class="footer-docs" data-theme="c" data-position="fixed">
			<p>&copy; 2013 KMAC All Rights Reserved.</p>
		</div><!-- /footerx	 -->

	</div><!-- /page -->


</body>
</html>
<%--

			<fieldset data-role="controlgroup">
				<legend>로그인을 할 수 없습니까?</legend>
			     	<input type="radio" name="radio-choice" id="radio-choice-1" value="choice-1" />
			     	<label for="radio-choice-1">아이디를 잊어버렸습니다.</label>
			
			     	<input type="radio" name="radio-choice" id="radio-choice-2" value="choice-2" />
			     	<label for="radio-choice-2">비밀번호를 잊어버렸습니다.</label>
			</fieldset>			
			
--%>			