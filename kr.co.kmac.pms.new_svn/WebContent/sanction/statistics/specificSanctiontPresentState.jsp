<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>특정 전자결재 현황 보기</title>
<%-- 공통js,css include --%>
<%@ include file="/common/include/includeCss.jsp"%>
<%@ include file="/common/include/includeJavaScript.jsp"%>

<script type="text/javascript">
<%-- 개별 스크립트 영역 --%>
function sanctionItemClick(projectCode, approvalType, seq, workType, woriTypeUrl) {
	document.sanctionPresentStateForm.target = "";		
	document.sanctionPresentStateForm.action = woriTypeUrl+"&readonly=true&projectCode="+projectCode+"&approvalType="+approvalType+"&seq="+seq;
	document.sanctionPresentStateForm.submit();
}
</script>
</head>

<body>
<form name="sanctionPresentStateForm" id="sanctionPresentStateForm" method="post">
<jsp:useBean id="now" class="java.util.Date"/>
<table width="98%">
	<!-- SPACER -->
	<tr>
		<td><jsp:include page="/common/include/includePageMainTitle.jsp" flush="false">
			<jsp:param name="title" value="전자결재 현황" />
		</jsp:include></td>
	</tr>


	<!-- 본문 리스트 시작 -->
	<tr>
		<td>
			<div class="btNbox pb20">
				<div class="btNlefTdiv">				
					<img src="/images/sub/line3Blit.gif" alt="">
					<span class="bold colore74c3a"><c:out value="${list.valueListInfo.totalNumberOfEntries}"/></span>
					<span>Total - Page(<span><c:out value="${list.valueListInfo.pagingPage}"/></span> of <span><c:out value="${list.valueListInfo.totalNumberOfPages}"/></span>)</span>
				</div>
			</div>
		</td>
	</tr>
	<tr>
		<td>
				<table class="listTable">
					<colgroup>
						<col width="130px">
						<col width="*">
						<col width="100px">
						<col width="120px">
						<col width="50px">
					</colgroup>
					<thead>
						<tr>
							<td>결재유형</td>
							<td>제목</td>
							<td>기안자</td>
							<td>기안일</td>
							<td>진행상태</td>
						</tr>
					</thead>
					<tbody id="ListData">
						<c:if test="${!empty list.list}">
							<c:forEach var="rs" items="${list.list}">
								<tr style="cursor: hand;" onmouseover="row_over(this)" onmouseout="row_out(this)" onclick="sanctionItemClick('<c:out value="${rs.projectCode}" />', '<c:out value="${rs.approvalType}" />', '<c:out value="${rs.seq}" />', '<c:out value="${rs.workType}" />', '<c:out value="${rs.workTypeUrl}" />')">
									<fmt:parseDate value="${rs.registerDate}" type="DATE" dateStyle="SHORT" pattern="yyyy-MM-dd HH:mm:ss.SSS" var="var4" />
									<fmt:formatDate value="${var4}" pattern="yyyy-MM-dd" var="registerDate" />
									<fmt:formatDate value="${now}" pattern="yyyy-MM-dd" var="today" />
									<td><c:out value="${rs.approvalTypeName}" /></td>
									<td class="myoverflow"><c:out value="${rs.projectName}" /></td>
									<td><c:out value="${rs.registerName}" /></td>
									<td><c:out value="${registerDate}" /></td>
									<td <c:if test="${rs.present == '반려'}" >bgcolor="#ffdfff"</c:if><c:if test="${rs.present == '종료'}" >bgcolor="#ccccff"</c:if>><c:out value="${rs.present}" /></td>
								</tr>
							</c:forEach>
						</c:if>
						<c:if test="${empty list.list}">
							<td align="center" colspan="5">검색 결과가 없습니다.</td>
						</c:if>
					</tbody>
				</table>
		</td>
	</tr>
	<!-- 본문 리스트 종료 -->

	<%-- 페이징 영역 시작--%>
	<tr>
		<td align="center">
				<table border="0" cellspacing="0" cellpadding="0">
					<tr height='30'>
						<td width="100%" align="center">
							<SCRIPT LANGUAGE="JavaScript"> 
											document.write( makePageHtml( 
											<c:out value="${list.valueListInfo.pagingPage}" default="1"/>, 
											15, 
											<c:out value="${list.valueListInfo.totalNumberOfEntries}" default="0"/> , 
											15)
											) ;
							</SCRIPT>
						</td>
					</tr>
				</table>
		</td>
	</tr>
	<%-- 페이징 영역 끝--%>
</table>
</form>
</body>
</html>