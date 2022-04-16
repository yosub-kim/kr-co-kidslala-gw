<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<%@page import="kr.co.kmac.pms.common.tag.tablegrid.TableGrid"%>
<%@page import="kr.co.kmac.pms.common.tag.tablegrid.Row"%>
<%@page import="kr.co.kmac.pms.common.tag.tablegrid.Cell"%>
<%@page import="java.util.List"%>
<%@page import="kr.co.kmac.pms.project.statistics.data.ExpenseRealTimeResult"%>
<%@page import="net.mlw.vlh.ValueList"%>

<%@page import="kr.co.kmac.pms.common.util.StringUtil"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>월별 성과급 현황(전체)</title>
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

function goDetailPage(ssn) {
	document.form.target = "";
	document.form.action = "/action/RealTimeProjectExpenseAction.do?mode=retrieveExpenseRealTimeResultDetail&deptCode=&ssn="+ssn;
	document.form.submit();	
}
function goPage(next_page) {
	document.form.pageNo.value = next_page ;
	document.form.target = "";		
	document.form.action = "/action/RealTimeProjectExpenseAction.do?mode=retrieveExpenseRealTimeResult2";
	document.form.submit();
}
function doSearch() {
	document.form.pageNo.value = '1';
	document.form.target = "";		
	document.form.action = "/action/RealTimeProjectExpenseAction.do?mode=retrieveExpenseRealTimeResult2";
	document.form.submit();
}

function changeDiv(obj) {
	if(obj == 'A'){
		$('div0').innerHTML = $('div1').innerHTML; 
	}else if(obj == 'J'){
		$('div0').innerHTML = $('div2').innerHTML; 
	}else if(obj == 'C'){
		$('div0').innerHTML = $('div3').innerHTML;
	}
}

function saveListToExcel() {
	var surl = '/action/RealTimeProjectExpenseAction.do?mode=saveListToExcel';
	surl += "&deptCode=" + document.form.deptCode.value;
	surl += "&jobClass=" + document.form.jobClass.value;
	surl += "&year=" + document.form.year.value;
	surl += "&month=" + document.form.month.value;
	document.location = surl;
}

function saveDetailListToExcel() {
	var surl = '/action/RealTimeProjectExpenseAction.do?mode=saveDetailListToExcel';
	surl += "&year=" + document.form.year.value;
	surl += "&month=" + document.form.month.value;
	document.location = surl;
}

function saveDetailListGroupByPjtToExcel() {
	var surl = '/action/RealTimeProjectExpenseAction.do?mode=saveDetailListGroupByPjtToExcel';
	surl += "&year=" + document.form.year.value;
	surl += "&month=" + document.form.month.value;
	document.location = surl;
}
</script> 
</head>

<body>
	<form name="form" method="post">
		<div style='display: none;'>
			<input type="hidden" name="pageNo"> 
		</div>
		
		<!-- location -->
		<div class="location">
			<p class="menu_title">월별 성과급 현황</p>
			<ul>
				<li class="home">HOME</li>
				<li>경영관리</li>
				<li>인력관리</li>
				<li>월별 성과급 현황</li>
			</ul>
		</div>
		<!-- // location -->
		
		<!-- contents sub -->
		<div class="contents sub">
	
		<!-- 신규 프로젝트 등록 -->
		<div class="box">
			<div class="search_box total">
				<div class="select_group">
					<div class="select_box">
						<div class="label_group">
							<p>구분</p>
							<select name="jobClass" class="select" onchange="changeDiv(this.value);" <c:if test="${readOnly=='ON'}">disabled</c:if>>
								<!-- <option value="">전체</option> -->
								<option value="B" <c:if test="${jobClass=='B'}">selected</c:if>>상근</option>
								<option value="J" <c:if test="${jobClass=='J'}">selected</c:if>>상임</option>
								<option value="C" <c:if test="${jobClass=='C'}">selected</c:if>>엑스퍼트</option>
							</select>
						</div>
						<div class="label_group">
							<p>소속</p>
								<div id="div0">
									<c:choose>
										<c:when test="${jobClass=='B'}">
											<org:divList enabled="1" divType="A" depth="2" isSelectBox="Y" selectedInfo="${deptCode}" attribute=" name='deptCode' id='deptCode' class='select' " all="Y"></org:divList>
										</c:when>
										<c:when test="${jobClass=='J'}">
											<org:divList enabled="1" divType="J" depth="2" isSelectBox="Y" selectedInfo="${deptCode}" attribute=" name='deptCode' id='deptCode' class='select' " all="Y"></org:divList>
										</c:when>
										<c:when test="${jobClass=='C'}">
											<org:divList enabled="1" divType="C" depth="1" isSelectBox="Y" selectedInfo="${deptCode}" attribute=" name='deptCode' id='deptCode' class='select' " all="Y"></org:divList>
										</c:when>
										<c:otherwise>
											<org:divList enabled="1" divType="J" depth="2" isSelectBox="Y" selectedInfo="${deptCode}" attribute=" name='deptCode' id='deptCode' class='select' " all="Y"></org:divList>
										</c:otherwise>
									</c:choose>
								</div>
						</div>
						<div class="label_group">
							<p>기간</p>
							<date:year beforeYears="2" afterYears="2" attribute=" name=year class=select style='width: 48%' " selectedInfo="${year}" />&nbsp;
							<date:month attribute=" name=month class=select style='width: 48%'  " selectedInfo="${month}" />
						</div>
					</div>
				</div>
				<c:if test="${readOnly!='ON'}">
				<div><button type="button" class="btn btn_blue" onclick="doSearch();">검색 <i class="mdi mdi-magnify"></i></button></div>
				</c:if>
			</div>
		</div>
		
		<div class="board_box">
			<div class="title_both">
				<div class="h1_area">
					<p class="h1">성과급 상세 현황</p>
				</div>
				<%if(session.getAttribute("userId").equals("eun") || session.getAttribute("userId").equals("yskim") || session.getAttribute("userId").equals("harry2080")){%>
				<div class="text-R">
					<button type="button" class="btn line btn_excel" onclick="saveDetailListGroupByPjtToExcel();"><i class="mdi mdi-microsoft-excel"></i>성과급 리스트 (프로젝트)</button>
					<button type="button" class="btn line btn_excel" onclick="saveDetailListToExcel();"><i class="mdi mdi-microsoft-excel"></i>성과급 리스트 (합산)</button>
				</div>
				<%} %>
			</div>
		
			<div class="board_contents">
				<table  id="projectProgressTable" class="tbl-edit td-c"><!-- td 영역이 가운데 정렬 일 경우 td-c -->
							<colgroup>
								<col style="width: 20%" />
								<col style="width: 20%" />
								<col style="width: 20%" />
								<col style="width: 20%" />
								<col style="width: 20%" />
							</colgroup>
							<tr>
								<th>소속</th>
								<th>이름</th>
								<th>직위/직책</th>
								<th>프로젝트 수</th>
								<th>성과급 합계(원)</th>		
							</tr>
							<c:if test="${!empty list.list}">
								<c:forEach var="rs" items="${list.list}">
								<tr onmouseover="row_over(this)" onmouseout="row_out(this)" style="cursor: hand;" onclick="goDetailPage('<c:out value="${rs.ssn }" />')">
									<td><c:out value="${rs.labelName }" /></td>
									<td><c:out value="${rs.name }" /></td>
									<td><c:out value="${rs.companyPositionName }" /></td>
									<td><c:out value="${rs.involvedPrjCnt }" /></td>
									<td><c:out value="${rs.realTimeSalary }" /></td>
								</tr>
								</c:forEach>
							</c:if>
							<c:if test="${empty list.list}"><tr><td colspan="6"><br>검색 결과가 존재하지 않습니다 .<br><br></td></tr></c:if>
					</table>
					<div class="paging_area">
						<SCRIPT LANGUAGE="JavaScript">
								document.write( makePageHtml2( 
										<c:out value="${list.valueListInfo.pagingPage}" default="1"/>, 
										10, 
										<c:out value="${list.valueListInfo.totalNumberOfEntries}" default="0"/> , 
										10)
								) ;
						</SCRIPT>
					</div>
				</div>
			</div>		
		</div>	
	</form>
	<div style="display: none;">
		<div id="div1">
			<org:divList enabled="1" divType="A" depth="2" isSelectBox="Y" selectedInfo="${deptCode}" attribute=" name='deptCode' id='deptCode' class='select' " all="Y"></org:divList>
		</div>
		<div id="div2">
			<org:divList enabled="1" divType="J" depth="2" isSelectBox="Y" selectedInfo="${deptCode}" attribute=" name='deptCode' id='deptCode' class='select' " all="Y"></org:divList>
		</div>
		<div id="div3">
			<org:divList enabled="1" divType="C" depth="1" isSelectBox="Y" selectedInfo="${deptCode}" attribute=" name='deptCode' id='deptCode' class='select' " all="Y"></org:divList>	
		</div>
	</div>
</body>
</html>