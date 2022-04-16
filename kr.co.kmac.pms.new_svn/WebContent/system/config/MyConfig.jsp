<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>정보수정</title>
<%-- 공통js,css include --%>
<%-- <%@ include file="/common/include/includeCss.jsp"%> --%>
<%@ include file="/common/include/includeJavaScript.jsp"%>
<link rel="stylesheet" href="/resources/css/common.css" type="text/css" />
<link rel="stylesheet" href="/resources/css/board.css" type="text/css" />
<!-- 서브페이지에서만 사용 -->
<link rel="stylesheet" href="/resources/css/selectric.css" type="text/css" />
<link rel="stylesheet" href="/resources/css/dev.css" type="text/css" />
<meta name="viewport" content="width=1600">
</head>
 
<body>
	<!-- location -->
	<div class="location">
		<p class="menu_title">정보수정</p>
		<ul>
			<li class="home">HOME</li>
			<li>정보수정</li>
		</ul>
	</div>
	<!-- // location -->


	<div class="contents sub">	
		<div class="fixed_box">
			<div class="title">
				<div class="h1_area">
					<p class="h1"><i class="mdi mdi-file-document-outline"></i>정보수정</p>									
				</div>
			</div>	
			
		<div class="fixed_contents sc">
			<div class="tab_menu_area">
				<ul class="tab_ui" id="tab_menu">
					<li class="current"><a href="#"	class="active"	id="pane1">개인정보</a></li>
					<li><a href="#"					id="pane2">학력/경력/전문분야</a></li>
					<li><a href="#" 				id="pane3">결재선 관리</a></li>
					<!-- <li><a href="#"					id="pane3">&nbsp;가젯 설정&nbsp;</a></li> 
					<li><a href="#"					id="pane4">&nbsp;전문분야 설정&nbsp;</a></li> -->
				</ul>
	
			<div style="margin:72px 20px 0 20px;">
			<div id="Process_container">
				<div id="Process_overlay" class="overlay" style="display: none">
					<img alt="" src="/images/loading.gif" align="middle" >	 
				</div>
				<div id="Process" class="pane"></div>
			</div>
			</div> 
			<script type="text/javascript">
			var orgTab = new TabbedPane('Process', 
				{
					'pane1': '/system/config/myInfo_iframe.jsp?type=loadMyInfoForm'
					,'pane2': '/system/config/myInfo_iframe.jsp?type=loadMyInfoAddForm'
					,'pane3': '/system/config/myInfo_iframe.jsp?type=sanctionLine'
					//,'pane2': '/action/SanctionLineAction.do?mode=getSanctionLine'
					//,'pane3': '/system/config/myInfo_iframe.jsp?type=myGadgetConfig'
					//,'pane3': '/action/GadgetAction.do?mode=myGadgetConfig'
					//,'pane4': '/system/config/myInfo_iframe.jsp?type=specialField'
					//,'pane4': '/action/ExpertPoolSpecialFieldAction.do?mode=loadSpecialField&ssn=<authz:authentication operation="username" />'	 							
				},
				{
				onClick: function(e) {
				$('Process_overlay').show();
				},							
				onSuccess: function(e) {
					$('Process_overlay').hide();
				},
				/* onComplete: function(e) {
					parent.document.getElementById("_contentFrame").height = $("Process").getHeight() + 200;
				} */
			}); 
		</script>
	</div>
	</div>
</div>

</body>
</html>	