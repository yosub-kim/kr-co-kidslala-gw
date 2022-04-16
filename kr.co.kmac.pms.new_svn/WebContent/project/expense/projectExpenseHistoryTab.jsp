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
<%--<script type="text/javascript" src='<c:url value="/js/project.js"/>'></script>
include 해서 사용 할 경우 ie6에서 에러발생하므로 주석처리
--%>
</head>

<body>
	<div class="location">
		<p class="menu_title">성과급 관리</p>
		<ul>
			<li class="home">HOME</li>
			<li>PMS</li>
			<li>프로젝트 관리</li>
			<li>성과급 관리</li>
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
						<i class="mdi mdi-file-document-outline"></i>성과급 관리
					</p>
				</div>
				<div class="btn_area">
					<button type="button" class="btn line btn_grey" onclick="location.href='javascript:history.back();'"><i class="mdi mdi-backburger"></i>목록</button>
				</div>
			</div>
			<!-- <div class="tabbed-pane"> -->
			<div class="fixed_contents sc">
				<div class="tab_menu_area">
					<ul id="tab_menu" class="tab_ui">		
						<li class="current"><a href="#" class="active"	id="pane1">컨설턴트별 성과급</a></li>
						<li><a href="#" 				id="pane2">프로젝트별 성과급</a></li>
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
							'pane1': '/project/expense/projectExpenseHistoryFrame.jsp'
							,'pane2': '/project/expense/projectExpenseHistoryFrame2.jsp'
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
