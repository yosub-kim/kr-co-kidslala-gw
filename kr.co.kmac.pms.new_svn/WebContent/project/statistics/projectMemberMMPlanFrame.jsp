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

<body leftmargin="0" topmargin="0" style="overflow-x:hidden;overflow-y:hidden;padding-left:0px;">
	<div style='display: none;'>
		<input type="text" name="ssn" id="ssn" value="<%= request.getParameter("ssn") %>" >
		<input type="text" name="year" id="year" value="<%= request.getParameter("year") %>" >
		<input type="text" name="month" id="month" value="<%= request.getParameter("month") %>" >
		<input type="text" name="type" id="type" value="<%= request.getParameter("type") %>" >
	</div>
   <iframe name="projectMemberMMPlanFrame" id="projectMemberMMPlanFrame" 
     src="/action/ProjectMemberMMPlanAction.do?mode=getProjectMemberMMPlan&ssn=<%= request.getParameter("ssn") %>&year=<%= request.getParameter("year") %>&month=<%= request.getParameter("month") %>&type=<%= request.getParameter("type") %>"
     frameborder="0" scrolling="yes" style="width: 800px; height: 500px; margin: 0px; padding: 0px;" ></iframe>
</body>
</html>