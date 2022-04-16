<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%-- 공통js,css include --%>
<%@ include file="/common/include/includeCss.jsp"%>
<%@ include file="/common/include/includeJavaScript.jsp"%>
<script type="text/javascript" src="/js/contextMenu/contextMenu.js"></script> 
<script type="text/javascript">
<%-- 개별 스크립트 영역 --%>

function goProjectDetail(projectCode) {
	document.location.href = "/action/ProjectSearchAction.do"
		+"?mode=getProjectInfoTab&viewMode=myProject&projectCode="+projectCode;	
}
function goProjectDetail(projectCode, projectRole) {
	document.location.href = "/action/ProjectSearchAction.do"
		+"?mode=getProjectInfoTab&viewMode=myProject&projectCode="+projectCode+"&projectRole="+projectRole;
}
</script> 
</head>


<body onload="goProjectDetail('<%=request.getParameter("projectCode") %>', '<%=request.getParameter("projectRole") %>'); ">
	<form name="myProjectListForm" method="post">
	</form>
</body>
</html>