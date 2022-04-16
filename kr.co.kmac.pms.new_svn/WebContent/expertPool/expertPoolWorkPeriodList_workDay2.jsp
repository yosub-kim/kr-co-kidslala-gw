<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>

<%
	String companyPosition = request.getParameter("companyPosition");
	String titleMsg = "재택근무 출/퇴근 현황";
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title><%=titleMsg %></title>
<%-- 공통js,css include --%>
<%-- <%@ include file="/common/include/includeCss.jsp"%> --%>
<%@ include file="/common/include/includeJavaScript.jsp"%>
<link rel="stylesheet" href="/resources/css/common.css" type="text/css" />
<link rel="stylesheet" href="/resources/css/board.css" type="text/css" />
<link rel="stylesheet" href="/resources/css/selectric.css" type="text/css" />
<link rel="stylesheet" href="/resources/css/dev.css" type="text/css" />

<script type="text/javascript">
<%-- 개별 스크립트 영역 --%>
function detail(ssn) {
	document.location.href = "/action/ExpertPoolManagerAction.do?mode=infoview&ssn="+ssn;
}
function doSearch() {
	document.frm.submit();
}
function home_schedule(ssn){
	document.location.href = "/action/BoardAction.do?mode=selectList_home&bbsId=home&writerId="+ssn;
}
</script>
</head>


<body>
<form name="frm" method="post">
	<input type="hidden" id="today" name="today" value="<c:out value="${today}" />" />
	<!-- location -->
		<div class="location">
			<p class="menu_title">재택근무 출/퇴근 현황</p>
			<ul>
				<li class="home">HOME</li>
				<li>스케줄 관리</li>
				<li>재택근무 출/퇴근 현황</li>
			</ul>
		</div>
		<!-- // location -->
		
		<div class="contents sub"><!-- 서브 페이지 .sub -->
			<!-- 검색 창 -->					
			<div class="box">
				<div class="search_box">
					<div class="select_group">
					<div class="search_input_box">
						<select  name="keyfield" class="select">
							<option value=subject>기준일</option>
						</select>
						
						<div class="search_input">
							<label for="search"></label>
							<fmt:parseDate value="${startDate}" type="DATE" dateStyle="SHORT"  pattern="yyyyMMdd" var="var1"/>
							<fmt:formatDate value="${var1}" pattern="yyyy-MM-dd" var="start"/>
							<fmt:parseDate value="${endDate}" type="DATE" dateStyle="SHORT"  pattern="yyyyMMdd" var="var2"/>
							<fmt:formatDate value="${var2}" pattern="yyyy-MM-dd" var="end"/>
							<script>
								jQuery(function(){jQuery( "#startDate" ).datepicker({});});
								jQuery(function(){jQuery( "#endDate" ).datepicker({});});
							</script>
							<input type="text" name="startDate" id="startDate" readonly="readonly" style="width: 45%;" size="15" value="<c:out value="${start}" />" />&nbsp~&nbsp
							<input type="text" name="endDate" id="endDate" readonly="readonly" style="width: 45%;" size="15" value="<c:out value="${end}" />" />
						</div>
					</div>
				</div>
				<div><button type="button" class="btn btn_blue" onclick="location.href='javascript:doSearch();'">검색</button></div>
			</div>
		</div>
		
		<div class="box">
			<div class="a-both total">
				<p>
					총 <span><c:out value="${list.valueListInfo.totalNumberOfEntries}" /></span>건
				</p>
			</div>
		
		<div style="margin-top: 10px; margin-bottom: 20px; margin-left: 20px;margin-right: 20px;">	
		<div class="tbl-wrap sc">
			<table class="tbl-edit td-c">
					<tr>
						<th>조직단위</th>
						<th class="subject">성명</th>
						<th>일자</th>
						<th>출근시간</th>
						<th>퇴근시간</th>
						<th>업무일지</th>
					</tr>
				<tbody>
				<c:choose>
					<c:when test="${!empty list.list}">
						<c:forEach var="rs" items="${list.list}">
							<tr>
								<td><c:out value="${rs.labelName }" /></td>
								<td><c:out value="${rs.userName}" /></td>
								<td><c:out value="${rs.resultDate }" /></td>
								<td><c:out value="${rs.workOn}" /></td>
								<td><c:out value="${rs.workOff}" /></td>
								<td><button type="button" class="btn line btn_blue" onclick="home_schedule('<c:out value="${rs.ssn}"/>')"><i class="mdi mdi-square-edit-outline"></i>상세</button></td>
							</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr><td align="center" colspan="6">검색된 데이터가 없습니다.</td></tr>
					</c:otherwise>
				</c:choose>
				</tbody>
			</table>
		</div>
		</div>
	</div>
</div>
</form>
</body>
</html>