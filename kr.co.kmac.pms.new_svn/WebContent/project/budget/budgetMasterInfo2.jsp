<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<html>
<head>
<title>프로젝트 예산 조회</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%-- 공통js,css include --%>
<%@ include file="/common/include/includeCss.jsp"%>
<%@ include file="/common/include/includeJavaScript.jsp"%>

</head>


<body leftmargin="15" topmargin="0" style="overflow-x:hidden;overflow-y:hidden" onload="openpopup();">
	<div style='height: 350px;'>
		<input type="hidden" id="projectCode" value="<%= request.getParameter("projectCode") %>" >
		<input type="hidden" id="viewMode" value="<%= request.getParameter("viewMode") %>" >
		<input type="hidden" id="projectState" value="${project.projectState}" >
	</div>
	<script type="text/javascript">
		var budget;
		var url;
		
		//if($F('viewMode') == 'lsanction' || $F('projectState') == '1' || $F('projectState') == '2'){
		if($F('viewMode') == 'lsanction' || $F('viewMode') == 'register' || $F('projectState') == '1' || $F('projectState') == '2'){
			url = "https://newbudget.kmac.co.kr:8080/DWPM3/DWPM30900_U_LINK.jsp"
 				+ "?empno=<authz:authentication operation="username" />"
				+ "&cmd=MOD&&srch_workgb=1"
				+ "&entno=<c:out value="${project.entNo}"/>";
		}else{
			url = "https://newbudget.kmac.co.kr:8080/com/login_chk_pms.jsp"
				+ "?param=<authz:authentication operation="username" />|DWPM30600_LINK|"
				+ "?projid=<c:out value="${project.projectCode}"/>";
		}
		var sFeatures = "top=100,left=100,width=800,height=600,resizable=yes,scrollbars=yes";

		detailWin = window.open(url,"budget", sFeatures);
		detailWin.focus();
	</script> 
</body>
</html>