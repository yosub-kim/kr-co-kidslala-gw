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
function btnNew_onclick(){
	document.location = "/action/ExpertPoolManagerAction.do?mode=loadFormExpertPool";
}
function doSearch() {
	var sfrm = document.forms["frm"];
	sfrm.submit();
}
function keyfield_onchange(obj) {
	var sfrm = document.forms["frm"];
	if(obj.selectedIndex != 0) {
		sfrm.keyword.readOnly = false;
	} else {
		sfrm.keyword.value = "";
		sfrm.keyword.readOnly = true;
	}
}
function goPage(pageNumber){
	pagingfrm.pg.value = pageNumber;
	pagingfrm.mode.value = "selectList";
	pagingfrm.submit();
}
function detail(idx) {
	document.location.href = "?mode=select&idx="+idx;
}
</script>
</head>
<body>
	<form name="frm">

		<!-- location -->
		<div class="location">
			<p class="menu_title">업무제휴기관(MOU)</p>
			<ul>
				<li class="home">HOME</li>
				<li>네트워크</li>
				<li>Infra 관리</li>
				<li>업무제휴기관(MOU)</li>
			</ul>
		</div>
		<!-- // location -->

		<!-- contents sub -->
		<div class="contents sub">
			<div class="box">

				<div class="search_box total">
					<div class="select_group">
						<div class="select_box">
							<div class="label_group">
								<p>검색조건</p>
								<select name="keyfield" id="search" class="select">
									<option value="com_name"<c:if test="${keyfield == 'com_name'}">selected</c:if>>기관명</option>
									<option value="country"<c:if test="${keyfield == 'country'}">selected</c:if>>지역</option>
									<option value="com_expert"<c:if test="${keyfield == 'com_expert'}">selected</c:if>>전문분야</option>
									<option value="com_cooperation"<c:if test="${keyfield == 'com_cooperation'}">selected</c:if>>협력분야</option>
									<option value="writer"<c:if test="${keyfield == 'writer'}">selected</c:if>>등록자</option>
								</select> 
							</div>
							<div class="label_group">
								<p>&nbsp;</p>
								<input type="text" name="keyword" id="keyword" class="textInput_left" value="<c:out value="${keyword }"/>" /> 
								<input type="hidden" name="mode" value="selectList">
							</div>
						</div>
					</div>
					<div>
						<button type="button" class="btn btn_blue" onclick="doSearch()">검색 <i class="mdi mdi-magnify"></i></button>
					</div>
				</div>
			</div>
	</form>
	<div class="board_box">
		<div class="title_both">
			<div class="h1_area">
				<p class="h1">협력사 현황</p>
			</div>
			<div class="select_box">
				<button type="button" class="btn line btn_blue" onclick="location.href='/action/MoudbAction.do?mode=loadForm'"><i class="mdi mdi-square-edit-outline"></i>등록</button>
			</div>
		</div>

		<div class="board_contents">
			<table id="projectProgressTable" class="tbl-edit td-c">
				<!-- td 영역이 가운데 정렬 일 경우 td-c -->
				<colgroup>
					<col style="width: 20%" />
					<col style="width: 15%" />
					<col style="width: *%" />
					<col style="width: 12%" />
					<col style="width: 8%" />
					<col style="width: 12%" />
				</colgroup>
				<tr>
					<th>기관명</th>
					<th>전문분야</th>
					<th>협력분야</th>
					<th>전화번호</th>
					<th>등록자</th>
					<th>등록일</th>
				</tr>
				<tbody id="ListData">
					<c:forEach var="obj" items="${result.list}" varStatus="i">
						<tr onclick="detail('<c:out value="${obj.idx}" />')" style="cursor: hand;" onmouseover="row_over(this)" onmouseout="row_out(this)" >
							<td><c:out value="${obj.com_name }" /></td>
							<td><c:out value="${obj.com_expert }" /></td>
							<td><c:out value="${obj.com_cooperation}" /></td>
							<td><c:out value="${obj.com_tel }" /></td>
							<td><c:out value="${obj.writer }" /></td>
							<td><c:out value="${obj.regdate }" /></td>
						</tr>
					</c:forEach>
					<c:if test="${empty result.list}">
						<td colspan='6' style="text-align:center;">해당하는 자료가 없습니다</td>
					</c:if>
				</tbody>
			</table>
			<form name="pagingfrm" style="margin: 0px; padding: 0px;">
				<div class="paging_area">
					<SCRIPT LANGUAGE="JavaScript">
						document
								.write(makePageHtml2(
										<c:out value="${result.valueListInfo.pagingPage}" default="1"/>,
										10,
										<c:out value="${result.valueListInfo.totalNumberOfEntries}" default="0"/>,
										10));
					</SCRIPT>
				</div>
				<div style="display: none">
					<input type="hidden" name="mode" value="selectList"> 
					<input type="hidden" name="keyfield" value="<c:out value="${pageData.keyfield}"/>"> 
					<input type="hidden" name="keyword" value="<c:out value="${pageData.keyword}"/>"> 
					<input type="hidden" name="pg" value="<c:out value="${pageData.pg}"/>">
				</div>
			</form>
		</div>
	</div>
	</div>
</body>
</html>