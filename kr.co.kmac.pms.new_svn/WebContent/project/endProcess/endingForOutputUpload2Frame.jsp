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

</script> 
</head>


<body topmargin="0" style="overflow-x:hidden;overflow-y:hidden">
	<div style='display: none;'>
		<input type="text" name="projectCode" id="projectCode" value="<%= request.getParameter("projectCode") %>" >
		<input type="text" name="viewMode" id="viewMode" value="<%= request.getParameter("viewMode") %>" >
	</div>
   <iframe id="preport" 
     src="/action/ProjectEndingAction.do?mode=selectEndingForOutputUpload2&projectCode=<%= request.getParameter("projectCode") %>"
     frameborder="0" scrolling="no" style="width: 100%; height: 100%; margin: 0px; padding: 0px;" ></iframe>
</body>
</html>