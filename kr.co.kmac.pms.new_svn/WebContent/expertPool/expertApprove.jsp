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
<%@ include file="/common/include/includeBodyTop.jsp"%>
<script type="text/javascript">
	window.open("https://newbudget.kmac.co.kr:8080/com/login_chk_pms_frame.jsp?param=<authz:authentication operation="username" />|DWPM30700_L|?cmd=INS",
			"acct", "top=30,left=30,width=850,height=700,scrollbars=no");
</script>

<%@ include file="/common/include/includeBodyBottom.jsp"%>

</body>

</html>
 