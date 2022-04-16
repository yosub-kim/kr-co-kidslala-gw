<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%-- 공통js,css include --%>
<%-- <%@ include file="/common/include/includeCss.jsp"%> --%>
<%@ include file="/common/include/includeJavaScript.jsp"%>
<link rel="stylesheet" href="/resources/css/common.css" type="text/css" />
<link rel="stylesheet" href="/resources/css/board.css" type="text/css" />
<link rel="stylesheet" href="/resources/css/selectric.css" type="text/css" />
<link rel="stylesheet" href="/resources/css/dev.css" type="text/css" />
<meta name="viewport" content="width=1600">


<script type="text/javascript">
<%-- 개별 스크립트 영역 --%>

function moveNext(){
	var year = document.projectManpowerForm.year.value;
	var month = document.projectManpowerForm.month.value;
	if (month == "12") {
		document.projectManpowerForm.year.value = parseInt(year) + 1;
		document.projectManpowerForm.month.value = 1;
	}
	else {
		document.projectManpowerForm.month.value = parseInt(month) + 1;
	}
	document.projectManpowerForm.submit();
}

function movePrevious(){
	var year = document.projectManpowerForm.year.value;
	var month = document.projectManpowerForm.month.value;
	if (month == "1") {
		document.projectManpowerForm.year.value = parseInt(year) - 1;
		document.projectManpowerForm.month.value = 12;
	}
	else {
		document.projectManpowerForm.month.value = parseInt(month) - 1;
	}
	document.projectManpowerForm.submit();
}
</script> 
</head>


<body leftmargin="15" topmargin="0" style="overflow-x:hidden;overflow-y:hidden">
	<div style='display: none;'>
		<input type="text" name="projectCode" id="projectCode" value="<%= request.getParameter("projectCode") %>" >
		<input type="text" name="viewMode" id="viewMode" value="<%= request.getParameter("viewMode") %>" >
	</div>
   <iframe id="preport" 
   	 src="/action/ProjectManpowerAction.do?mode=getProjectManpowerList&projectCode=<%= request.getParameter("projectCode") %>&viewMode=<%= request.getParameter("viewMode") %>"
     frameborder="0" scrolling="yes" style="width: 100%; height: 100%; margin: 0px; padding: 0px;" ></iframe>
</body>
</html>