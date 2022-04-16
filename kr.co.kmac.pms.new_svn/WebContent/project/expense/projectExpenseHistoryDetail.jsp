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

function goPage(next_page) {
	document.form.pageNo.value = next_page ;
	document.form.target = "";
	document.form.action = "/action/ProjectExpenseAction.do?mode=projectExpenseHistoryDetail";
	document.form.submit();
}
function doSearch() {
	document.form.submit();
}
</script>
</head>

<body>
	<form name="form" method="post">
	<div style="display: none;">
		<input type="text" name="projectCode" id="projectCode" value='<%=request.getParameter("projectCode") %>'  />
		<input type="hidden" name="pageNo"> 
	</div>
	<div style="margin:70px 15px 0 15px;">
			<!-- 검색 창 -->
			<div class="board_box">
				<div class="title_both">
					<div class="h1_area">
						<p class="h1">프로젝트 결산세부정보</p>
					</div>
					<div class="select_box">
					<button type="button" class="btn line btn_excel" onclick="location.href='https://intranet.kmac.co.kr/kmac/expense/projectexpense_excel.asp?projectcode=<%= request.getParameter("projectCode") %>&startdate=&enddate=&deptcode='"><i class="mdi mdi-microsoft-excel"></i>EXCEL 저장</button>
					</div>
				</div>
				
				<div class="board_contents tbl-sc ">
					<table  id="projectProgressTable" class="tbl-edit td-c pd-none"><!-- td 영역이 가운데 정렬 일 경우 td-c -->
						<colgroup>
							<col style="width: 13%"/>
							<col style="width: 10%"/>
							<col style="width: 10%" />
							<col style="width: 10%" />
							<col style="width: *" />
							<col style="width: 13%" />
							<col style="width: 13%" />
						</colgroup>
						<tr>
							<th>전표번호</th>
							<th>승인일자</th>
							<th>번호</th>
							<th>거래처</th>
							<th>적요</th>
							<th>매출액</th>
							<th>매출원가</th>												
						</tr>
						<c:if test="${!empty list.list}">
							<c:forEach var="rs" items="${list.list}">
								<tr> 
									<td><c:out value="${rs.DRAW_NO }" /></td>
									<td><c:out value="${rs.ACCT_DATE }" /></td>
									<td><c:out value="${rs.ACCT_NBR }" /></td>
									<td><c:out value="${rs.CUST_NAME }" /></td>
									<td><c:out value="${rs.REMARK }" /></td>
									<td style="text-align: right;"><fmt:formatNumber value="${rs.CREDIT_AMT }" type="number"/></td>
									<td style="text-align: right;"><fmt:formatNumber value="${rs.DEBIT_AMT }" type="number"/></td>
								</tr>
							</c:forEach>
						</c:if>
						<c:if test="${empty list.list}">
							<td colspan="7">등록된 정보가 없습니다.</td>
						</c:if>
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
</body>
</html>