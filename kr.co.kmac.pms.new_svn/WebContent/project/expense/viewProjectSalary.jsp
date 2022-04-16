<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>

<%
		/* java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("MM");
		String today = formatter.format(new java.util.Date()); */
		String titleMsg = "과거 성과급 및 투입률 현황";
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
	document.location.href = "/action/ExpertPoolManagerAction.do?mode=infoview&ssn="+ssn;
}
function doSearch() {
	document.frm.submit();
}
</script>
</head>


<body>
<form name="frm" method="post">
	<input type="hidden" id="today" name="today" value="<c:out value="${today}" />" />
	<table width="500" cellpadding="0" cellspacing="0">
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
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
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
										<tr>
											<td width="100px" rowspan="2">성명</td>
											<td width="80px" rowspan="2">년</td>
											<td width="80px" rowspan="2">월</td>
											<td width="120px" rowspan="2">지급금액</td>
											<td width="120px" rowspan="2">투입률</td>
										</tr>
									</thead>						
								<tbody id="ListData">
									<c:choose>
										<c:when test="${!empty list.list}">
										<c:set var="chk" value=" "/>
											<c:forEach var="rs" items="${list.list}">
											<tr>
												<c:if test="${rs.name != chk}">
													<td rowspan="<c:out value="${rs.allCount}"/>" style="text-align: center" ><c:out value="${rs.name}" escapeXml="false" /></td>
												</c:if>
												<td><c:out value="${rs.year}" escapeXml="false" /></td>
												<td><c:out value="${rs.month}" escapeXml="false" /></td>
												<td><c:out value="${rs.salarySum}" escapeXml="false" /></td>
												<td><c:choose><c:when test="${rs.mmValue == null }">-</c:when><c:otherwise><c:out value="${rs.mmValue}" escapeXml="false" /></c:otherwise></c:choose></td>
											</tr>
											<c:set var="chk" value="${rs.name}"/>
											</c:forEach>
										</c:when>
										<c:otherwise>
											<tr><td align="center" colspan="10">검색된 데이터가 없습니다.</td></tr>
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