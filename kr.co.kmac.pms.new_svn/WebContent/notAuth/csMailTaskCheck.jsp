
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@page import="kr.co.kmac.pms.project.endprocess.manager.ProjectEndProcessManager"%>
<%@page import="kr.co.kmac.pms.common.util.StringUtil"%>
<%@page import="org.springframework.web.bind.ServletRequestUtils"%>
<%@page import="kr.co.kmac.pms.project.endprocess.data.EndProcessCheck"%>
<%@page import="kr.co.kmac.pms.project.endprocess.manager.ProjectEndTaskAssignManager"%>

<%@ page contentType="text/html; charset=euc-kr" %>

<%
	WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(session.getServletContext());
	ProjectEndTaskAssignManager	projectEndTaskAssignManager= (ProjectEndTaskAssignManager)wac.getBean("projectEndTaskAssignManager");
	
	String projectCode = ServletRequestUtils.getRequiredStringParameter(request, "projectCode");
	
	try {
		EndProcessCheck endProcessCheck = new EndProcessCheck();
		endProcessCheck.setProjectCode(projectCode);
		endProcessCheck.setTaskId("VOC");
		endProcessCheck.setIsExecuted("Y");
		projectEndTaskAssignManager.store(endProcessCheck);
		if (projectEndTaskAssignManager.isRollingAndRateingFinished(projectCode)) {
			projectEndTaskAssignManager.assignEndProcessTask(projectCode);
		}
%>
		<script>
			alert("응답해 주셔서 감사합니다.");
			window.close();
		</script>
<%
	} catch (Exception e) {
%>
		<script> 
			alert("error occured.(empty projectCode)");
		</script>
<%
	}
	
%>
