<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%-- 공통js,css include --%>
<%@ include file="/common/include/includeCss.jsp"%>
<script language="JavaScript" type="text/JavaScript">
	alert("다른 장치에서 로그인 되어, 현재 장치에서 자동 로그아웃 되었습니다.");
	top.document.location.href = "<c:url value='j_acegi_logout'/>";
</script>
<%
	//response.sendRedirect("/j_acegi_logout");
%>