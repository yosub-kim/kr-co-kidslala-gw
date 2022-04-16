<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />

<%-- 공통js,css include --%>
<%@ include file="/common/include/includeJavaScript.jsp"%>
<link rel="stylesheet" href="/resources/css/common.css" type="text/css" />
<link rel="stylesheet" href="/resources/css/board.css" type="text/css" />
<link rel="stylesheet" href="/resources/css/selectric.css" type="text/css" />
<link rel="stylesheet" href="/resources/css/dev.css" type="text/css" />
<meta name="viewport" content="width=1600">
<%--<script type="text/javascript" src='<c:url value="/js/project.js"/>'></script>
include 해서 사용 할 경우 ie6에서 에러발생하므로 주석처리
--%>

<script type="text/javascript">
<%-- 개별 스크립트 영역 --%>

function goPage(next_page) {
	document.form.pageNo.value = next_page ;
	document.form.target = "";		
	document.form.action = "/action/ProjectExpenseAction.do?mode=projectExpenseHistoryManagement2";
	document.form.submit();
}

function moveNext(){
	var year = document.form.year.value;
	var month = document.form.month.value;
	if (month == "12") {
		document.form.year.value = parseInt(year) + 1;
		document.form.month.value = 1;
	}
	else {
		document.form.month.value = parseInt(month) + 1;
	}
	document.form.submit();
}

function movePrevious(){
	var year = document.form.year.value;
	var month = document.form.month.value;
	if (month == "1") {
		document.form.year.value = parseInt(year) - 1;
		document.form.month.value = 12;
	}
	else {
		document.form.month.value = parseInt(month) - 1;
	}
	document.form.submit();
}
</script>
</head>
 <%
	int year = Integer.parseInt(request.getAttribute("year").toString());
	int month = Integer.parseInt(request.getAttribute("month").toString()); 
%>
<body class="load sc">
	<form name="form" method="post">
		<div style='display: none;'>
			<input type="hidden" name="pageNo">
			<input type="hidden" name="role">
		</div>
		
		<!-- // location -->
		<!-- 신규 프로젝트 등록 -->
			<!-- contents sub -->
		<div style="margin:70px 15px 0 15px;">
		<div class="board_box">
			<div class="title_both">
				<div class="h1_area">
					<p class="h1"><c:out value="${year}" />년  <c:out value="${month}" />월 프로젝트별 성과급 내역</p>
				</div>
				<div class="select_box">
					<button type="button" style="cursor:hand;" onclick="movePrevious()"><i class="mdi mdi-chevron-left"></i></button>
						<date:year beforeYears="4" afterYears="0" attribute=" name='year' class='selectboxPopup' onchange='this.form.submit();'" selectedInfo="${year}" />&nbsp;
							<select name="month" class="selectboxPopup" onchange="this.form.submit();">
								<c:forEach var="i" begin="1" end="12">
									<option value="<c:out value="${i}"/>" <c:if test="${month == i}">selected</c:if>><c:out value="${i}"/></option>
								</c:forEach>
							</select>
					<button type="button" style="cursor:hand;" onclick="moveNext()"><i class="mdi mdi-chevron-right"></i></button>
				</div>
			</div>
				
			<div class="board_contents">
				<table id="projectProgressTable" class="tbl-edit td-c pd-none"><!-- td 영역이 가운데 정렬 일 경우 td-c -->
					<colgroup>
						<col style="width: 30%" />
						<col style="width: 10%" />
						<col style="width: 15%" />
						<col style="width: 15%" />
						<col style="width: 13%" />
						<col style="width: 13%" />
						<col style="width: 13%" />
						<col style="width: 13%" />
					</colgroup>
						<tbody>
							<tr>
								<th rowspan="2">프로젝트</th>
								<th rowspan="2">이름</th>
								<th rowspan="2">직위</th>
								<th rowspan="2">합계</th>
								<th colspan="2">성과급</th>
													<%if(year <= 2021 && month <=04){%>
							<th colspan="2">20% 성과급</th>
						<%}else{%>
							<th colspan="2">인센티브</th>
						<%} %>
							</tr>
							<tr>
								<th>금액</th>
								<th>진행 상태</th>
								<th>금액</th>
								<th>진행 상태</th>
							</tr>
							<c:set var="projectChk" value=""/>
							<c:forEach var="ex" items="${list.list}">
								<tr>
									<td><c:out value="${ex.projectName }" /></td>
									<td><c:out value="${ex.name }"/></td>
									<td><c:out value="${ex.role }"/></td>
									<td style="text-align: right;"><fmt:formatNumber value="${ex.salarySum2 }" type="number"/>&nbsp&nbsp</td>
									<td style="text-align: right;"><fmt:formatNumber value="${ex.realTimeSalary }" type="number"/>&nbsp&nbsp</td>
									<td><c:out value="${ex.isSanction }"/></td>
									<td style="text-align: right;"><fmt:formatNumber value="${ex.realTimeSalary2 }" type="number"/>&nbsp&nbsp</td>
									<td><c:out value="${ex.isSanction2 }"/></td>
								</tr>
							<c:set var="projectChk" value="${ex.projectCode }" />
							</c:forEach>
							<c:if test="${empty list.list}">
								<td colspan="8">성과급 내역이 없습니다.</td>
							</c:if>
						</tbody>
					</table>
				<!-- <div class="paging_area">
					<SCRIPT LANGUAGE="JavaScript">
							document.write( makePageHtml2( 
									<c:out value="${list.valueListInfo.pagingPage}" default="1"/>, 
									10, 
									<c:out value="${list.valueListInfo.totalNumberOfEntries}" default="0"/> , 
									10)
							) ;
					</SCRIPT>
				</div> -->
				</div>
			</div>
		</div>
		</div>
	</form>
</body>
</html>					