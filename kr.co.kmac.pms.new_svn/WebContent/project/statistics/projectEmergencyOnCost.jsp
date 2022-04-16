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
function resizeFrame() {
	if(parent && parent.DYNIFS){
		parent.DYNIFS.resize('emergency');
	}	
}
function doSearch() {
	document.emergencyForm.target = "";
	document.emergencyForm.action = "/action/ProjectEmergencyAction.do?mode=costOverData";
	document.emergencyForm.submit();	
}
function goProjectSearchPage(runningDivCode, projectState, delayState, costOver, endProcess, hasDate, payCostOver, etcCostOver)  {
	var params="";
	if(runningDivCode != '' && runningDivCode != undefined){
		params = params + "&runningDeptCode="+runningDivCode;
	}
	if(projectState != '' && projectState != undefined){
		params = params + "&projectState="+projectState;
	}
	if(delayState != '' && delayState != undefined){
		params = params + "&delayState="+delayState;
	}
	if(costOver != '' && costOver != undefined){
		params = params + "&costOver="+costOver;
	}
	if(endProcess != '' && endProcess != undefined){
		params = params + "&endProcess="+endProcess;
	}
	if(payCostOver != '' && payCostOver != undefined){
		params = params + "&payCostOver=Y";
	}
	if(etcCostOver != '' && etcCostOver != undefined){
		params = params + "&etcCostOver=Y";
	}
	if(hasDate == 'yes'){
		params = params + "&realStartDate="+$('startYear').value+$('startMonth').value;
		params = params + "&realEndDate="+$('endYear').value+$('endMonth').value;
	}
	params = params + "&businessTypeCode=BTA";
	
	parent.document.location.href = "/action/ProjectSearchAction.do?mode=getProjectSearchList&backButtonYN=Y"+params;
}
</script>
</head>
 
<body onload="resizeFrame()">
	<form name="emergencyForm" method="post">
	<div style="display: none;">
	</div>
<div style="margin:70px 15px 0 15px;">
					<div class="board_box">
						<div class="title_both">
							<div class="h1_area">
								<p class="h1">월별 예산 현황</p>
							</div>
							<div class="select_box">
<!-- 							<img src="/images/calendar/leftArrow.gif" border="0" title="이전 달 보기" align="absmiddle" style="cursor:hand;" onclick="movePrevious()"/> -->
								<date:year beforeYears="4" afterYears="4" attribute=" name=startYear id=startYear class=selectbox  " selectedInfo="${startYear}" />&nbsp;
								<date:month attribute=" name=startMonth id=startMonth class=selectbox " selectedInfo="${startMonth}" />&nbsp;~&nbsp;
								<date:year beforeYears="4" afterYears="4" attribute=" name=endYear id=endYear class=selectbox  " selectedInfo="${endYear}" />&nbsp;
								<date:month attribute=" name=endMonth id=endMonth class=selectbox  " selectedInfo="${endMonth}" />
								<button type="button" class="btn line btn_blue" onclick="doSearch()"><i class="mdi mdi-square-edit-outline"></i>검색</button>
							</div>
						</div>
									<div class="board_contents">
					<div class="tbl-wrap sc">
						<table id="projectProgressTable" class="tbl-edit td-c"><!-- td 영역이 가운데 정렬 일 경우 td-c -->
							<colgroup>
								<col style="width: *" />
								<col style="width: 23%" />
								<col style="width: 23%" />
								<col style="width: 23%" />
							</colgroup>
							<tbody>
							<tr style="position:sticky; top: 0px;">
								<th rowspan='2'>구분</th>
								<th colspan='3'>진행 프로젝트</tdh
							</tr>
							<tr style="position:sticky; top: 38px;">
								<th>프로젝트 수</th>
								<th>성과급 초과</th>
								<th>제경비 초과</th>
							</tr>
							<c:if test="${!empty list}">
										<c:forEach var="rs" items="${list.list}">
											<tr onmouseover="row_over(this)" onmouseout="row_out(this)">
												<td style="text-align: center;"><c:out value="${rs.data_1}"/></td>
												<td style="text-align: center;"><a href="#" onclick="goProjectSearchPage('<c:out value="${rs.key_1}"/>', '3')" ><c:out value="${rs.ingTotal}"/></a></td>
												<td style="text-align: center;"><a href="#" onclick="goProjectSearchPage('<c:out value="${rs.key_1}"/>', '3', '' , '', '', '', 'Y')"><c:if test="${rs.payCostOver > 0}"><span class="bold colore74c3a"></c:if><c:out value="${rs.payCostOver}"/><c:if test="${rs.payCostOver > 0}"></span></c:if></a></td>
												<td style="text-align: center;"><a href="#" onclick="goProjectSearchPage('<c:out value="${rs.key_1}"/>', '3', '' , '', '', '', '', 'Y')"><c:if test="${rs.etcCostOver > 0}"><span class="bold colore74c3a"></c:if><c:out value="${rs.etcCostOver}"/><c:if test="${rs.etcCostOver > 0}"></span></c:if></a></td>
											</tr>
										</c:forEach>
									</c:if> 
</tbody>
	</form>
</body>

</html>					