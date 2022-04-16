<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>

<%@page import="kr.co.kmac.pms.common.util.DateTime"%>
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
<script type="text/javascript">
	
<%-- 개별 스크립트 영역 --%>
function doSearch() {
	var sfrm = document.forms["frm"];
	sfrm.submit();
}
function goPage(pageNumber) {
	frm.pg.value = pageNumber;
	frm.submit();
}
</script>
</head>

<body>
	<div id="sub_location">
		<div class="location">
			<p class="menu_title">시스템 개발현황 공유 게시판</p>
			<ul>
				<li class="home">HOME</li>
				<li>업무지원</li>
				<li>시스템 개발현황 공유 게시판</li>
			</ul>
		</div>
	</div>
	<!-- // location -->
	
	<form name="frm" method="post">
	<div class="contents sub"> <!-- 서브 페이지 .sub -->
		<!-- 검색 창 -->
		<div class="box">
			<div class="search_box total">
				<div class="select_group">
					<div class="select_box">
						<div class="label_group" >
							<p>상태</p>
							<select class="select" name="status" id="status" style="width: 100%;">
								<option value="">선택</option>
								<option value="1" <c:if test="${status=='1'}">selected</c:if>>대기</option>
								<option value="2" <c:if test="${status=='2'}">selected</c:if>>진행중</option>
								<option value="3" <c:if test="${status=='3'}">selected</c:if>>지연</option>
								<option value="4" <c:if test="${status=='4'}">selected</c:if>>완료</option>
							</select>
						</div>
						<div class="label_group">
							<p>기간</p>
							<%-- <fmt:parseDate value="${start_Date}" type="DATE" dateStyle="SHORT"  pattern="yyyyMMdd" var="var1"/>
							<fmt:formatDate value="${var1}" pattern="yyyy-MM-dd" var="start"/>
							<fmt:parseDate value="${end_Date}" type="DATE" dateStyle="SHORT"  pattern="yyyyMMdd" var="var2"/>
							<fmt:formatDate value="${var2}" pattern="yyyy-MM-dd" var="end"/>  --%>
							<script>
								jQuery(function(){jQuery( "#start_date" ).datepicker({});});
								jQuery(function(){jQuery( "#end_date" ).datepicker({});});
							</script>
							<input type="text" name="start_date" id="start_date" readonly="readonly" style="width: 41%;" size="15" value="<c:out value="${start_date}" />" />&nbsp~&nbsp
							<input type="text" name="end_date" id="end_date" readonly="readonly" style="width: 41%;" size="15" value="<c:out value="${end_date}" />" />
						</div>
						<div class="label_group">
							<p>과제명</p>
							<input type="text" name="developSubject" id="developSubject" value="<c:out value="${developSubject}"/>" placeholder="과제명을 입력하세요."  style="width:94%" />	
							<input type="hidden" name="mode" value="developSelectList">
							<input type="hidden" name="bbsId" value="<c:out value="${bbsId }"/>">
						</div>
					</div>
				</div>
				<div><button type="button" class="btn btn_blue" onclick="doSearch()">검색<i class="mdi mdi-magnify"></i></button></div>
			</div>
		</div>
		
		<!-- 테이블 리스트 -->
		<div class="box">
			<div class="a-both total">
				<p>총<span><c:out value="${result.valueListInfo.totalNumberOfEntries}" /></span>건</p>
				<div class="btnNrighTdiv">
					<button type="button" class="btn btn_aqua"
						onclick="location.href='/action/BoardAction.do?mode=inputForm&bbsId=<c:out value="${bbsId}"/>'">등록</button>
				</div>
			</div>
			
			<div class="tbl-wrap sc">
				<table class="tbl-list all">
					<thead>
						<tr>
							<th style="width: 15%;">진행상태</th>
							<th style="width: 50%;">과제명</th>
							<th style="width: 35%;">개발기간</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="result" items="${result.list}">
						<tr onmouseout="row_out(this)" <c:choose><c:when test="${result.topArticle == 'Y'}">onmouseover="topArticleRow_over(this);" class="topArticle"</c:when><c:otherwise> onmouseover="row_over(this)"</c:otherwise></c:choose>>
							<td><c:out value="${result.status}" /></td>
							<td style="cursor:pointer" class="subject" onclick= "location.href='/action/BoardAction.do?mode=boardView&bbsId=<c:out value="${bbsId}" />&seq=<c:out value="${result.seq}" />&projectCode=<c:out value="${projectCode}" />&projectName=<c:out value="${projectName}" />'">
								<p><c:out value="${result.subject}" /></a></p>
							</td>
							<td><c:out value="${result.start_date}" /> ~ <c:out value="${result.end_date}" /></td>
						</tr>
						</c:forEach>
						<c:if test="${empty result.list}">
							<td colspan='3' style="text-align:center;">해당하는 자료가 없습니다</td>
						</c:if>
					</tbody>
				</table>
			</div> 
			<!-- //tbl-wrap -->
			
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
				<input type="hidden" name="bbsId"    	value="<c:out value="${bbsId}"/>">
				<input type="hidden" name="start_date" value="<c:out value="${start_date}"/>"> 
				<input type="hidden" name="end_date" value="<c:out value="${end_date}"/>"> 
				<input type="hidden" name="status" value="<c:out value="${status}"/>"> 
				<input type="hidden" name="developSubject" value="<c:out value="${developSubject}"/>"> 
				<input type="hidden" name="pg" value="<c:out value="${pg}"/>">
			</div>
		</div>
	</div>
	</form>
</body>
</html>