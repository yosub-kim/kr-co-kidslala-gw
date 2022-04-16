<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%-- 공통js,css include --%>
<%@ include file="/common/include/includeCss.jsp"%>
<%@ include file="/common/include/includeJavaScript.jsp"%>

<script type="text/javascript">
<%-- 개별 스크립트 영역 --%>

</script> 
</head>


<body leftmargin="15" topmargin="0" style="overflow-x:hidden;overflow-y:hidden">
	<div style='display: none;'>
		<input type="text" name="projectCode" id="projectCode" value="<%= request.getParameter("projectCode") %>" >
		<input type="text" name="viewMode" id="viewMode" value="<%= request.getParameter("viewMode") %>" >
	</div>
   <iframe id="budgetDetail" 
     src="https://intranet.kmac.co.kr/kmac/expense/projectexpense.asp?projectCode=<c:out value="${project.projectCode}"/>&deptCode=<c:out value="${project.runningDeptCode}"/>"
     frameborder="0" style="width: 745px; height: 420px; margin: 0px; padding: 0px;" ></iframe>
</body>
</html>