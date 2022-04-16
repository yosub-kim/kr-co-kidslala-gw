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
<!-- 서브페이지에서만 사용 -->
<link rel="stylesheet" href="/resources/css/selectric.css" type="text/css" />
<link rel="stylesheet" href="/resources/css/dev.css" type="text/css" />
<meta name="viewport" content="width=1600">
<script type="text/javascript">
<%-- 개별 스크립트 영역 --%>
function saveToExcel() {
	var surl = '/action/EmergencyConnectionAction.do?mode=saveToExcel';
	//alert(orgTab.ActiveTab);
	if(orgTab.ActiveTab == 'pane1') {
		surl += "&jobClass=A";
	} else if (orgTab.ActiveTab == 'pane2') {
		surl += "&jobClass=J";
	} else if (orgTab.ActiveTab == 'pane3') {
		surl += "&jobClass=H2";
	} else {
		surl += "&jobClass=R";
	}
 
	document.location = surl;
}
function detailview(ssn) {
		location.href="/action/ExpertPoolManagerAction.do?mode=infoview&ssn="+ssn;
}
</script>
</head>
<form name="detailForm" method="post">
	<input name="ssn" value="" type="hidden" >
</form>
<body>
		<!-- location -->
		<div class="location">
			<p class="menu_title">비상연락망</p>
			<ul>
				<li class="home">HOME</li>
				<li>비상 연락망</li>
			</ul>
		</div>
		<!-- // location -->
		
		<div class="contents sub">	
			<div class="fixed_box">
				<div class="title">
					<div class="h1_area">
						<p class="h1"><i class="mdi mdi-file-document-outline"></i>비상연락망</p>									
					</div>
					<button type="button" class="btn line btn_excel" onclick="saveToExcel()"><i class="mdi mdi-microsoft-excel"></i>EXCEL 저장</button>
				</div>	
				
			<div class="fixed_contents sc">
				<div class="tab_menu_area">
					<ul class="tab_ui" id="tab_menu">
						<li class="current"><a href="#" class="active"	id="pane1">상근</a></li>
						<!-- <li><a href="#" 				id="pane2">&nbsp;상근(2)&nbsp;</a></li> -->
						<li><a href="#" 				id="pane2">상임</a></li>
						<!-- <li><a href="#" 				id="pane4">&nbsp;AA&nbsp;</a></li> -->
						<li><a href="#" 				id="pane3">RA</a></li>
						<li><a href="#" 				id="pane4">아르바이트</a></li>
					</ul>
		
				<div id="Process_container">
					<div id="Process_overlay" class="overlay" style="display: none">
						<img alt="" src="/images/loading.gif" align="middle" >	 
					</div>
					<div id="Process" class="pane"></div>
				</div> 
				<script type="text/javascript">
				var orgTab = new TabbedPane('Process', 
					{
						'pane1': '/action/EmergencyConnectionAction.do?mode=retrieveList&jobClass=A',
						'pane2': '/action/EmergencyConnectionAction.do?mode=retrieveList&jobClass=J',
						'pane3': '/action/EmergencyConnectionAction.do?mode=retrieveList&jobClass=H2',
						'pane4': '/action/EmergencyConnectionAction.do?mode=retrieveList&jobClass=R'
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
</div>
</body>
</html>