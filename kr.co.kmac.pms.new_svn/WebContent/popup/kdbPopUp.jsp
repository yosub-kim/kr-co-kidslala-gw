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
function goKDB_popup() {
	userwidth = (screen.width - 1); 
	userheight = (screen.height - 1);
	
	window.open('https://intranet.kmac.co.kr/kmac/ks/hub.asp','new_kdb_pop','scrollbars=yes,toolbar=yes,location=yes,status=yes,menubar=yes,resizable=yes,width='+userwidth+',height='+userheight+',left=0,top=0');
	history.back();
}
</script>

</head>

<body onload="goKDB_popup()">
</body>

</html>
 