<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%-- 공통js,css include --%>
<%@ include file="/common/include/includeCss.jsp"%>
<script type="text/javascript">
	if (navigator.userAgent.match(/iPhone|iPod|iPad|Android|Windows CE|BlackBerry|Symbian|Windows Phone|webOS|Opera Mini|Opera Mobi|POLARIS|IEMobile|lgtelecom|nokia|SonyEricsson/i) != null 
			|| navigator.userAgent.match(/LG|SAMSUNG|Samsung/) != null){
		top.document.location.href = "/<c:url value='j_acegi_mobile_logout'/>";
	}else{
		//alert("KMAC 통합 인트라넷은 회사 홈페이지를 통해서만 접속 가능합니다.1");
		top.document.location.href = "/<c:url value='j_acegi_logout'/>";
	}

</script>
<%
	//response.sendRedirect("/j_acegi_logout");
%>