<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>

<%
	String companyPosition = request.getParameter("companyPosition");
	String titleMsg = "AA 계약기간";
	
	if (companyPosition.equals("64GT"))
		titleMsg = "RA 계약기간";
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
</script>
</head>


<body>
<form name="frm" method="post">
	<input type="hidden" id="today" name="today" value="<c:out value="${today}" />" />
	<table width="735" cellpadding="0" cellspacing="0">
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
									<tr height="25px">
										<td width="100px">부서</td>
										<td width="80px">이름</td>
										<td width="80px">직위</td>
										<td width="*">계약기간</td>
										<td width="75px">개인연차</td>
										<td width="75px">사용연차</td>
										<td width="75px">잔여연차</td>
									</tr>
								</thead>			
								<tbody id="ListData">
									<c:choose>
										<c:when test="${!empty list.list}">
											<c:set var="deptNameChk" value=""/>
											<c:forEach var="rs" items="${list.list}">
												<tr onclick="detail('<c:out value="${rs.ssn}" />')" style="cursor: hand;" onmouseover="row_over(this)" onmouseout="row_out(this)">
													<td style="text-align: center"><c:out value="${rs.deptName}" escapeXml="false" /></td>
													<td align="center"><c:out value="${rs.name}" /></td>
													<td alian="center"><c:out value="${rs.companyPositionName}" /></td>
													<td align="center" <c:if test="${rs.acctExpireDate != '' && rs.acctExpireDate < today}">bgcolor="#ffdfff"</c:if>>
														<c:choose><c:when test="${rs.acctBeginDate == ''}">인사담당자 미입력</c:when><c:otherwise><c:out value="${rs.acctBeginDate}" /></c:otherwise></c:choose>
														~
														<c:choose><c:when test="${rs.acctExpireDate == ''}">인사담당자 미입력</c:when><c:otherwise><c:out value="${rs.acctExpireDate}" /></c:otherwise></c:choose>
													</td>
													<td alian="center"><c:out value="${rs.restday_1}" /></td>
													<td alian="center"><c:out value="${rs.useday}" /></td>
													<td alian="center"><c:out value="${rs.restday}" /></td>
												</tr>
												<c:set var="deptNameChk" value="${rs.deptName}"/>
											</c:forEach>
										</c:when>
										<c:otherwise>
											<tr><td align="center" colspan="7">검색된 데이터가 없습니다.</td></tr>
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