<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>

<%@page import="kr.co.kmac.pms.common.util.DateTime"%>
<%@page import="kr.co.kmac.pms.common.util.StringUtil"%>
<%@page import="kr.co.kmac.pms.common.util.DateUtil"%>
<%@page import="kr.co.kmac.pms.common.data.TabData" %>
<%@page import="java.util.ArrayList" %>

<%@page import="org.springframework.web.bind.ServletRequestUtils"%><html>
<head>
<title>프로젝트 종료정보</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<%-- 공통js,css include --%>
<%-- <%@ include file="/common/include/includeCss.jsp"%> --%>
<%@ include file="/common/include/includeJavaScript.jsp"%>
<link rel="stylesheet" href="/resources/css/common.css" type="text/css" />
<link rel="stylesheet" href="/resources/css/board.css" type="text/css" />
<!-- 서브페이지에서만 사용 -->
<link rel="stylesheet" href="/resources/css/selectric.css" type="text/css" />
<link rel="stylesheet" href="/resources/css/dev.css" type="text/css" />
<meta name="viewport" content="width=1600">
<%
String projectCode 		= ServletRequestUtils.getRequiredStringParameter(request,"projectCode");
String businessTypeCode = ServletRequestUtils.getStringParameter(request, "businessTypeCode","");
String projectName = ServletRequestUtils.getStringParameter(request, "projectName","");
String reqStr = ServletRequestUtils.getStringParameter(request, "projectName","");
String projectRole = ServletRequestUtils.getStringParameter(request, "projectName","");
String isRefresh = ServletRequestUtils.getStringParameter(request, "projectName","");
String processTypeCode  = ServletRequestUtils.getStringParameter(request, "processTypeCode","");
String viewMode  = ServletRequestUtils.getStringParameter(request, "viewMode","");
String state = ServletRequestUtils.getStringParameter(request, "state","");
String evalRole = ServletRequestUtils.getStringParameter(request, "evalRole","");
%>


<script type="text/javascript">

function getProjectReportDetail(taskFormTypeId, seq) {

	var url = "/action/ProjectReportSearchAction.do?"
		+ "mode=getProjectReport"
		+ "&projectCode=" + $('projectCode').value
		+ "&taskFormTypeId="+taskFormTypeId
		+ "&seq="+seq
		+ "&readonly=true";
	 window.open(url,'projectReport','top=0,left=0,status=no,toolbar=no,scrollbars=yes,width=784,height=510,directories=no,menubar=no');
	 
	//document.form.target = "";		
	//document.form.submit();
}

function fileDownload1(uuid){
	if(uuid != ""){
		if($$("#_targetDownload").length == 0)
			document.body.insertAdjacentHTML('beforeEnd', "<iframe id=\"_targetDownload\" src=\"\" style=\"display:none\"></iframe>");
    	//$("_targetDownload").src = "/servlet/PhotoDownLoadServlet?fileId=" + uuid;
		$("_targetDownload").src = "/servlet/RepositoryDownLoadServlet?fileId=" + uuid;
	}
}
 
function goProjectDetail(projectCode, viewMode){
	<% if(viewMode.equals("myProject")){ %>
		document.location.href = "/action/ProjectSearchAction.do?mode=getProjectInfoTab&viewMode=myProject&projectCode="+projectCode+"&projectRole=PM";
	<%} else if(viewMode.equals("projectSearch")){%>
		document.location.href = "/action/ProjectSearchAction.do?mode=getProjectInfoTab&viewMode=projectSearch&projectCode="+projectCode;
	<%}%>
	
}

</script>
</head>

<body>
<!-- 본문시작-->
<%

ArrayList<TabData> tabList = new ArrayList<TabData>();
if(!viewMode.equals("ending")){
	tabList.add(new TabData("Project Summary"	, "/action/ProjectViewAction.do?mode=summary&projectCode=" + projectCode));
}

if(businessTypeCode.equals("BTA")){
	tabList.add(new TabData("고객만족도"			, "/action/ProjectViewAction.do?mode=rollingC&projectCode=" + projectCode));
	if((!state.equals("") && !state.equals("next:review-1-init")) 
		|| evalRole.equals("Y")) {	// 리뷰지 작성 단계에서는 PL평가를 보이지 않게 함, COO 이상, 관리자만 PL 평가를 볼 수 있게 함 
		tabList.add(new TabData("PL 평가"			, "/action/ProjectViewAction.do?mode=rateP&projectCode=" + projectCode));
	}
	if(!state.equals("") || evalRole.equals("Y")) {	// COO 이상, 관리자만 멤버 평가를 볼 수 있게 함
		tabList.add(new TabData("멤버 평가"			, "/action/ProjectViewAction.do?mode=rateC&projectCode=" + projectCode));
	}
}else if(businessTypeCode.equals("BTD")){
	tabList.add(new TabData("고객만족도"			, "/action/ProjectViewAction.do?mode=rollingC&projectCode=" + projectCode));
}else if( businessTypeCode.equals("BTE") && !processTypeCode.equals("EE")){// 교육
	if (processTypeCode.equals("N4") || processTypeCode.equals("DE")) {
		tabList.add(new TabData("고객만족도"			, "/action/ProjectViewAction.do?mode=rollingC&projectCode=" + projectCode));
	} else {
		tabList.add(new TabData("고객만족도"			, "/action/ProjectViewAction.do?mode=rollingE&projectCode=" + projectCode));
		//tabList.add(new TabData("강사 평가"			, "/action/ProjectViewAction.do?mode=rateE&projectCode=" + projectCode));
	}
}else if( businessTypeCode.equals("BTF")){// 국제사업
	if (processTypeCode.equals("DE")) {
		tabList.add(new TabData("고객만족도"			, "/action/ProjectViewAction.do?mode=rollingC&projectCode=" + projectCode));
	} else {
		tabList.add(new TabData("고객만족도"			, "/action/ProjectViewAction.do?mode=rollingT&projectCode=" + projectCode));
	}
}

request.setAttribute("tabList" , tabList) ;
%>
	<!-- location -->
	<div class="location">
		<p class="menu_title">프로젝트 종료정보</p>
		<ul>
			<li class="home">HOME</li>
			<li>PMS</li>
			<li>프로젝트 관리</li>
			<li>MY PROJECT</li>
			<li>프로젝트 종료정보</li>
		</ul>
	</div>
	<!-- // location -->
	
	<div class="contents sub">
		<!-- 서브 페이지 .sub -->
		<!-- 본문시작-->
		<div class="fixed_box">
			<div class="title">
				<div class="h1_area">
					<p class="h1">
						<i class="mdi mdi-file-document-outline"></i>프로젝트 종료정보 [<%= projectName %>]
					</p>
					<% if(viewMode.equals("myProject") || viewMode.equals("projectSearch")){ %>
					<button type="button" class="btn line btn_black" onclick="goProjectDetail('<%=projectCode%>','<%=viewMode%>')"><i class="mdi mdi-calendar-check"></i>기본정보</button>
					<%} %>
				</div>
				<div class="btn_area">
					<button type="button" class="btn line btn_grey" onclick="location.href='javascript:history.back();'"><i class="mdi mdi-backburger"></i>목록</button>
				</div>
			</div>
			<!-- <div class="tabbed-pane"> -->
			<div class="fixed_contents sc">
				<div class="tab_menu_area">
					<ul id="tab_menu" class="tab_ui">		
						<c:forEach var="tab" items="${tabList}" varStatus="i">
							<c:choose><c:when test="${i.index == 0 }">
								<li class="current"><a href="#" id="pane<c:out value="${i.count}"/>" <c:if test="${i.first}">class="active"</c:if>><c:out value="${tab.tabName}"/></a></li>							
							</c:when>
							<c:otherwise>
								<li><a href="#" id="pane<c:out value="${i.count}"/>" <c:if test="${i.first}">class="active"</c:if>><c:out value="${tab.tabName}"/></a></li>							
							</c:otherwise></c:choose>
						</c:forEach>
					</ul>
					
					<div id="Process_container">
						<div id="Process_overlay" class="overlay" style="display: none">
							<img alt="" src="/images/loading.gif" align="middle">
						</div>
						<div id="Process" class="pane"></div>
					</div>
					<script type="text/javascript">
						new TabbedPane('Process', 
						{
						<c:forEach var="tab" items="${tabList}" varStatus="i">
							<c:if test="${!i.first}">,</c:if>'pane<c:out value="${i.count}"/>': '<c:out value="${tab.tabURL}" escapeXml="false" />'
						</c:forEach>
						},
						{
							onClick: function(e) {
								$('Process_overlay').show();
							},
							onSuccess: function(e) {
								$('Process_overlay').hide();
							}
						});
					</script>
		</div>
	</div>
</div>	
</body>

</html>