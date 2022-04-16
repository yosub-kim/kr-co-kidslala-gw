<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>

<%@page import="kr.co.kmac.pms.common.util.DateTime"%>
<%@page import="kr.co.kmac.pms.sanction.general.data.SanctionDoc"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<%-- 공통js,css include --%>
<%@ include file="/common/include/includeCss.jsp"%>
<%@ include file="/common/include/includeJavaScript.jsp"%>

<script type="text/javascript">
	
</script>

</head>

<body>
<iframe id="acct" src="http://210.107.60.13/com/login_chk_pms.jsp?param=<authz:authentication operation="username" />|DWPM20100_L" style="width: 793px; height: 100%;" scrolling="no"
	frameborder="0" onload="DYNIFS.resize('acct');"> </iframe>

</body>
</html>
