<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>

<%@page import="kr.co.kmac.pms.common.util.DateTime"%>
<%@page import="kr.co.kmac.pms.common.util.StringUtil"%>
<%@page import="kr.co.kmac.pms.common.util.DateUtil"%>
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

</script>
</head> 

<body>
	<div class="location">
		<p class="menu_title">PMS</p>
		<ul>
			<li class="home">HOME</li>
			<li>PMS</li>
			<li>프로젝트 현황</li>
			<li>Emergency 프로젝트 현황</li>
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
						<i class="mdi mdi-file-document-outline"></i>Emergency 프로젝트 현황
					</p>
				</div>
				<div class="btn_area">
					<button type="button" class="btn line btn_grey" onclick="location.href='javascript:history.back();'"><i class="mdi mdi-backburger"></i>목록</button>
				</div>
			</div>
		<!-- 본문시작-->
		<div class="fixed_contents sc">
				<div class="tab_menu_area">
					<ul id="tab_menu" class="tab_ui">	
							<li class="current"><a href="#" class="active" id="pane3">조직단위별 현황</a></li>
							<li><a href="#" 				id="pane1">비즈니스별 현황</a></li>
							<li><a href="#"					id="pane4">산업별 현황</a></li>
							<li><a href="#"					id="pane5">월별 예산 현황</a></li>
					</ul>
					<div id="Emergency_container">
						<div id="Emergency_overlay" class="overlay" style="display: none">
							<img alt="" src="/images/loading.gif" align="middle">
						</div>
						<div id="Emergency" class="pane"></div>
					</div>
			
					<p></p>
				
					<script type="text/javascript">
						var Tab = new TabbedPane('Emergency', 
							{
								'pane1': '/project/statistics/projectEmergencyIFrame.jsp?mode=cboData',
								//'pane2': '/project/statistics/projectEmergencyIFrame.jsp?mode=PUData',
								'pane3': '/project/statistics/projectEmergencyIFrame.jsp?mode=cfoData',
								'pane4': '/project/statistics/projectEmergencyIFrame.jsp?mode=cioData',
								'pane5': '/project/statistics/projectEmergencyIFrame.jsp?mode=costOverData'
							},
							{
								onClick: function(e) {
									$('Emergency_overlay').show();
								},
								onSuccess: function(e) {
									$('Emergency_overlay').hide();
								}
							});
					</script>
				</div>								
			</td>
		</tr>
		<!-- 본문종료-->
</body>
</html>					