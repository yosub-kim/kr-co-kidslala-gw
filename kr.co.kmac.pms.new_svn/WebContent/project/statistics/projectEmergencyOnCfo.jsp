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
	document.emergencyForm.action = "/action/ProjectEmergencyAction.do?mode=cfoData";
	document.emergencyForm.submit();	
}
function doDelaySearch(){
	var deplayListWin;
	var sURL = "/action/ProjectEmergencyAction.do?mode=cfoData2";
	var sFeather = "top=100,left=100,width=800,height=600,scrollbars=yes";
	deplayListWin = window.open(sURL, "deplayListWin", sFeather);
	deplayListWin.focus();
	
}
function goProjectSearchPage(runningDivCode, projectState, delayState, costOver, endProcess, hasDate)  {
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
	if(hasDate == 'yes'){
		params = params + "&realStartDate="+$('startYear').value+$('startMonth').value;
		params = params + "&realEndDate="+$('endYear').value+$('endMonth').value;
	}
	// 비즈니스 유형 변수에 값이 없으면 BTA로 처리되므로 빈값을 넘긴다
	var businessTypeCode="";
	params = params + "&businessTypeCode="+businessTypeCode;
	
	//parent.document.location.href = "/action/ProjectSearchAction.do?mode=getProjectSearchList&backButtonYN=Y"+params;
	//var url="/action/ProjectSearchAction.do?mode=getProjectSearchList"+params;
 	//window.open(url, 'pjList', 'top=120,left=120,status=no,toolbar=no,scrollbars=yes,width=1585,height=835,directories=no,menubar=no'); 
	parent.document.location.href = "/action/ProjectSearchAction.do?mode=getProjectSearchList"+params;
}
function openDelayProjectList(projectState, delayState) {
	var deplayListWin;
	var sURL = "/action/ProjectSearchAction.do?mode=getDelayProjectSearchList";
	sURL += "&projectState=" + projectState + "&delayState=" + delayState;
	var sFeather = "top=120,left=120,width=1585,height=835,scrollbars=yes";
	deplayListWin = window.open(sURL, "deplayListWin", sFeather);
	deplayListWin.focus();
	alert(sURL)
	location.href=sURL;
}
</script>
</head>
 
<body onload="resizeFrame()">
	<form name="emergencyForm" method="post">
	<div style="display: none;">
	</div>
		
			<!-- 신규 프로젝트 등록 -->
			<div style="margin:70px 15px 0 15px;">
					<div class="board_box">
						<div class="title_both">
							<div class="h1_area">
								<p class="h1">조직단위별 프로젝트 현황</p>
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
						<table id="projectProgressTable" class="tbl-edit td-c pd-none"><!-- td 영역이 가운데 정렬 일 경우 td-c -->
								<tr style="position:sticky; top: 0px;">
									<th rowspan="2">구분</th>
									<th>진행 프로젝트</th>
									<th colspan="3">평가 프로젝트</th>
									<th colspan="3">리뷰 프로젝트</th>
									<th rowspan="2">종료 프로젝트</th>
									<th rowspan="2">전체 프로젝트</th>
								</tr>
								<tr style="position:sticky; top: 38px;">
									<th>전체</th>
									<th><a href="#" onclick="openDelayProjectList('3', 'R')" >일정지연</a></th>
									<th>예산초과</th>
									<th>전체</th>
									<th><a href="#" onclick="openDelayProjectList('4', 'ER')" >평가지연</a></th>
									<th>전체</th>
									<th><a href="#" onclick="openDelayProjectList('5', 'RR')" >리뷰지연</a></th>
								</tr>
								
								<tbody>
								<c:if test="${!empty list}">
									<c:forEach var="rs" items="${list.list}">
										<tr onmouseover="row_over(this)" onmouseout="row_out(this)">
											<td style="text-align: center;"><a href="#" onclick="goProjectSearchPage('<c:out value="${rs.key_1}"/>')" ><c:out value="${rs.data_1}"/></a></td>
											<td style="text-align: center;"><a href="#" onclick="goProjectSearchPage('<c:out value="${rs.key_1}"/>', '3')" ><c:out value="${rs.ingTotal}"/></a></td>
											<td style="text-align: center;"><a href="#" onclick="goProjectSearchPage('<c:out value="${rs.key_1}"/>', '3', 'R')" ><c:out value="${rs.scheduleDelayCnt}"/></a></td>
											<td style="text-align: center;"><a href="#" onclick="goProjectSearchPage('<c:out value="${rs.key_1}"/>', '3', '', 'Y')" ><c:if test="${rs.costOver > 0}"><span class="bold colore74c3a"></c:if><c:out value="${rs.costOver}"/><c:if test="${rs.costOver > 0}"></span></c:if></a></td>
											<td style="text-align: center;"><a href="#" onclick="goProjectSearchPage('<c:out value="${rs.key_1}"/>', '4', '',  '', 'endProcess')" ><c:out value="${rs.evalTotal}"/></a></td>
											<td style="text-align: center;"><a href="#" onclick="goProjectSearchPage('<c:out value="${rs.key_1}"/>', '4',  'ER' )" ><c:out value="${rs.evalDelayCnt}"/></a></td>
											<td style="text-align: center;"><a href="#" onclick="goProjectSearchPage('<c:out value="${rs.key_1}"/>', '5', '',  '', 'endProcess')" ><c:out value="${rs.reviewTotal}"/></a></td>
											<td style="text-align: center;"><a href="#" onclick="goProjectSearchPage('<c:out value="${rs.key_1}"/>', '5',  'RR' )" ><c:out value="${rs.reviewDelayCnt}"/></a></td>
											<td style="text-align: center;"><a href="#" onclick="goProjectSearchPage('<c:out value="${rs.key_1}"/>', '6', '', '', '','yes')" ><c:out value="${rs.doneTotal}"/></a></td>
											<td style="text-align: center;"><c:out value="${rs.total}"/></td>												
										</tr>
									</c:forEach>
								</c:if>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
		</div>
	</form>
</body>

</html>					