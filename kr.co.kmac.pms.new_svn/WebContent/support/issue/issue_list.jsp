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
function doSearch() {
	var sfrm = document.forms["SearchForm"];
	sfrm.submit();
}

function goPage(pageNumber){
	SearchForm.pg.value = pageNumber;
	SearchForm.mode.value = "selectList";
	SearchForm.submit();
} 

function insertForm(gaksa) {
	var url = "/action/IssueAction.do?mode=insertForm&gaksa=" + gaksa;
	var sFeatures = "toolbar=0,location=0,directories=0,status=0,menubar=0,scrollbars=0,resizable=0,top=50,left=50,width=620,height=400"
	var insertForm = window.open(url, 'insertForm', sFeatures);
	insertForm.focus();
}
</script>
</head>
<body>
	<form name="SearchForm" method="post">
	
	<input type="hidden" name="page" value="">
		<!-- location -->
		<div class="wrap">
			<div class="location">
				<p class="menu_title">문서발급</p>
				<ul>
					<li class="home">HOME</li>
					<li>업무지원</li>
					<li>신청하기</li>
					<li>문서발급</li>
				</ul>
			</div>
			
			<div class="contents sub"> <!-- 서브 페이지 .sub -->
				
				<!-- 검색 창  -->
				<div class="board_box">
					<div class="search_box">
						<div class="select_group">
							<div class="search_input_box">
								<SELECT class="select" NAME="keyfield" style="width:200px;">
									<option value="content" <c:if test="${keyfield=='content'}">selected</c:if>>내용</option>
									<option value="dept_name" <c:if test="${keyfield=='dept_name'}">selected</c:if>>발신부서명</option>
									<option value="receive" <c:if test="${keyfield=='receive'}">selected</c:if>>수신처</option>
									<option value="year" <c:if test="${keyfield=='year'}">selected</c:if>>연도</option>
									<option value="gubun" <c:if test="${keyfield=='gubun'}">selected</c:if>>구분</option>
								</SELECT>
								<div class="search_input">
									<input type="text" name="keyword" id="keyword" class="textInput_left" value="<c:out value="${keyword }"/>" /> 
									<input type="hidden" name="mode" value="selectList">
								</div>
							</div>
						</div>
						<div><button type="button" class="btn btn_blue" onclick="doSearch()">검색<i class="mdi mdi-magnify"></i></button></div>
					</div>
				</div>
				
				<div class="board_box">
					<div class="title_both">
						<div class="h1_area">
							<p class="h1">문서발급 리스트</p>
						</div>
						
						<div class="btNrighTdiv">
							<select name="gaksa" class="select" onChange="SearchForm.submit();" STYLE="width:150px" >
								<option value="KMAC" <c:if test="${gaksa == 'KMAC'}">selected</c:if>>KMAC</option>
								<option value="KMAMEDIA" <c:if test="${gaksa == 'KMAMEDIA'}">selected</c:if>>KMA MEDIA</option>
							</select>
							<button type="button" class="btn btn_pink" onclick="javascript:insertForm('<c:out value="${gaksa}"/>');">등록</button>
						</div>
					</div>
					
					<div class="board_contents">
						<table class="tbl-list">
							<thead>
								<tr>
									<th width="15%">발급번호</th>
									<th width="*">내용</th>
									<th width="15%">발신부서명</th>
									<th width="20%">수신처</th>
									<th width="10%">매수</th>
									<th width="10%">구분</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="obj" items="${result.list}" varStatus="i">
									<tr>
										<td><c:out value="${obj.year }" /> - <c:out value="${obj.seqno }" /></td>
										<td><c:out value="${obj.content }" /></td>
										<td><c:out value="${obj.dept_name}" /></td>
										<td>(<c:out value="${obj.wtime }" />)<br/><c:out value="${obj.receive }" /></td>
										<td><c:out value="${obj.count_t }" /></td>
										<td><c:out value="${obj.gubun }" /></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
						<div class="paging_area">
							<SCRIPT LANGUAGE="JavaScript">
								document
										.write(makePageHtml2(
												<c:out value="${result.valueListInfo.pagingPage}" default="1"/>,
												10,
												<c:out value="${result.valueListInfo.totalNumberOfEntries}" default="0"/>,													10));
							</SCRIPT>
						</div>
						<div style="display: none">
							<input type="hidden" name="mode" value="selectList">  
							<input type="hidden" name="keyfield" value="<c:out value="${pageData.keyfield}"/>"> 
							<input type="hidden" name="keyword" value="<c:out value="${pageData.keyword}"/>"> 
							<input type="hidden" name="pg" value="<c:out value="${pageData.pg}"/>">
							<input type="hidden" name="gaksa" value="<c:out value="${pageData.gaksa}"/>">
							<input type="hidden" name="dept" value="<c:out value="${pageData.dept}"/>">
						</div>
					</div>
				</div>
			</div>
		</div>
	</form>
</body>
</html>