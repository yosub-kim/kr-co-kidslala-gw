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
function viewDetail(){
	var url = "/action/ExpertPoolManagerAction.do?mode=getExpertPoolWorkPeriodList_awardingDetail";
	var sFeatures = "top=120,left=120,width=1300,height=900,resizable=no,scrollbars=yes";
	var detailWin = window.open(url, "viewExpertDetail", sFeatures);
	detailWin.focus();
}
</script>
</head>


<body>
<form name="hallOfHonorForm" method="post">
	
		<div class="location">
			<p class="menu_title">Hall Of Honor</p>
			<ul>
				<li class="home">HOME</li>
				<li>게시판</li>
				<li>Hall Of Honor</li>
			</ul>
		</div>
		<!-- // location -->
		
			<!-- contents sub -->
	<div class="contents sub">
		<div class="fixed_box">
			<div class="title" style="background-color: #006699;">
				<div class="h1_area" style="background-color: #006699;">
					<p class="h1" style="background-color: #006699; color: #fff;"><i class="mdi mdi-party-popper"></i>Hall Of Honor</p>
					<date:year afterYears="-1" beforeYears="15"  attribute=" name='year' class='select' style='width:110px' onchange='this.form.submit();'" selectedInfo="${year}" />
				</div>
			</div>
		
			<div class="fixed_contents sc" >
				<c:choose><c:when test="${year eq '2021' }">
					<table id="projectProgressTable" class="tbl-edit td-c"><!-- td 영역이 가운데 정렬 일 경우 td-c -->
					<!-- <img src="../images/award/2021/test.png" style="width:100%; height:100%;"> -->
					<div style="text-align: center;">
						<img src="../images/award/2021/2021.png" style="width:60%;">
					</div>
						<%@ include file="../images/award/2021/award.htm" %>
						<br>
					</table>
				</c:when><c:otherwise>
					<table id="projectProgressTable" class="tbl-edit td-c"><!-- td 영역이 가운데 정렬 일 경우 td-c -->
					<div style="text-align: center;">
						<img src="../images/award/year/<c:out value="${year}" />.jpg" style="width:60%;">
					</div>
					<br>
					</table>
				</c:otherwise></c:choose>
			</div>
		</div>
	</div>
</form>
</body>
</html>