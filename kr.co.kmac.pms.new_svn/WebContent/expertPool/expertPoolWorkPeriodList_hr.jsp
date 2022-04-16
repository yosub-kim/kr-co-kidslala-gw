<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>

<%
	String companyPosition = request.getParameter("companyPosition");
	String titleMsg = "채용관리 권한목록";
	
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<title><%=titleMsg %></title>
<%-- 공통js,css include --%>
<%@ include file="/common/include/includeCss.jsp"%>
<%@ include file="/common/include/includeJavaScript.jsp"%>

<script type="text/javascript">
<%-- 개별 스크립트 영역 --%>
function detail(ssn) {
	document.location.href = "/action/ExpertPoolManagerAction.do?mode=getExpertPoolWorkPeriodList_customer2";
}

function detail2(name, start, end) {
	document.location.href = "http://intranet.kmac.co.kr/kmac/comlist/customer_methodlist.asp?date1="+start+"&date2="+end+"&search=writer&embbsmethod=1&searchkey="+name;
}

function doSearch() {
	document.frm.submit();
}
</script>
</head>


<body>
<form name="frm" method="post">
	<input type="hidden" id="today" name="today" value="<c:out value="${today}" />" />
	<table width="100%" cellpadding="0" cellspacing="0">
		<!-- 타이틀 영역 -->
		<tr>
			<td height="12">
				<% String back = request.getParameter("backButtonYN"); %>
				<jsp:include page="/common/include/includePageMainTitle.jsp" flush="true" >
					<jsp:param name="title" value="<%=titleMsg %>" />
					<jsp:param name="backButtonYN" value="<%=back %>" />
				</jsp:include>
			</td>
		</tr>
		
		<!-- 검색 영역 -->
		
		<!-- 본문 리스트 시작 -->	
				
		<tr>
			<td align="left" valign="top">
				<table width="460px" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td height="35px">
								<!-- 페이지 정보, 버튼 -->			
								<div class="btNbox pb15">
									<div class="btNlefTdiv">				
										<img src="/images/sub/line3Blit.gif" alt="">
										<span class="bold colore74c3a"><c:out value="${list.valueListInfo.totalNumberOfEntries}"/></span>
										<span>Total - Page(<span><c:out value="${list.valueListInfo.pagingPage}"/></span> of <span><c:out value="${list.valueListInfo.totalNumberOfPages}"/></span>)</span>
									</div>
									<div class="btNrighTdiv">
										<a class="btNa0a0a0 txt2btn" onclick="javascript:self.close()" href="#">닫기</a>
									</div>
								</div>								
							</td>
					</tr>
					<tr>
						<td>
							<table class="listTable">
								<thead>
									<tr height="25px">
										<td width="30%">부서</td>
										<td width="30%">직위</td>
										<td width="40%">이름</td>
									</tr>
								</thead>			
								<tbody id="ListData">
									<c:choose>
										<c:when test="${!empty list.list}">
											<c:forEach var="rs" items="${list.list}">
											<tr>
												<td><c:out value="${rs.labelName }" /></td>
												<td><c:out value="${rs.posName }" /></td>
												<td><c:out value="${rs.userName }" /></td>
												</tr>
											</c:forEach>
										</c:when>
										<c:otherwise>
											<tr><td align="center" colspan="3">검색된 데이터가 없습니다.</td></tr>
										</c:otherwise>
									</c:choose>
								</tbody>
							</table>
						</td>
					</tr>
					<tr>
						<td height="30"></td>
					</tr>
					<%-- 페이징 영역 시작--%>
					<%-- 페이징 영역 끝--%>
				</table>
			</td>			
		</tr> 
	</table>
</form>
</body>
</html>