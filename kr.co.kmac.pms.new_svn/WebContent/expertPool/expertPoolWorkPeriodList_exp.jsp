<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>

<%
	String companyPosition = request.getParameter("companyPosition");
	String titleMsg = "엑스퍼트 운영현황";
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
	<table width="700" cellpadding="0" cellspacing="0">
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
										<td width="300px">조직단위</td>
										<td width="100px">성명</td>
										<td width="80px">유형</td>
										<td width="580px">실행 프로젝트 명</td>
										<td width="320px">실행 기간</td>
										<td width="100px">담당 PM</td>
									</tr>
								</thead>			
								<tbody id="ListData">
									<c:choose>
										<c:when test="${!empty list.list}">
											<c:set var="NameChk" value=""/>
											<c:set var="name" value=" "/>
											<c:forEach var="rs" items="${list.list}">
												<tr onclick="detail('<c:out value="${rs.ssn}" />')" style="cursor: hand;" onmouseover="row_over(this)" onmouseout="row_out(this)">
												<c:if test="${rs.des != NameChk}">
											
													<td rowspan="<c:out value="${rs.dept}"/>" style="text-align: center" <c:if test="${rs.projectName == null}">bgcolor="#ffdfff"</c:if>><c:out value="${rs.des} </br> (${rs.expertCnt}명)" escapeXml="false" /></td>
												</c:if>
												<c:if test="${rs.name != chk}">
													<td rowspan="<c:out value="${rs.ssnn}"/>" style="text-align: center" <c:if test="${rs.projectName == null}">bgcolor="#ffdfff"</c:if>><c:out value="${rs.name}" escapeXml="false" /></td>
												</c:if>
													<td alian="center"><c:choose><c:when test="${rs.projectTypeCode eq 'ED'}"><c:out value="${'MH'}"/></c:when><c:otherwise><c:out value="${rs.projectTypeCode}" /></c:otherwise></c:choose></td>
													<!--<td alian="center"<c:if test="${rs.projectTypeCode == 'ED'}"><c:out value="MH" /></c:if>><c:out value="${rs.projectTypeCode}" /></td> -->
													<td alian="center"<c:if test="${rs.projectName == null}">bgcolor="#ffdfff"</c:if>><c:out value="${rs.projectName}" /></td>
													<td alian="center"<c:if test="${rs.realStartDate == null && rs.realEndDate == null}">bgcolor="#ffdfff"</c:if>>
														<c:choose><c:when test="${rs.realStartDate == null}"> </c:when><c:otherwise><c:out value="${rs.realStartDate}" /> ~ </c:otherwise></c:choose>
														<c:choose><c:when test="${rs.realEndDate == null}"> </c:when><c:otherwise><c:out value="${rs.realEndDate}" /></c:otherwise></c:choose>
													</td>
													<td alian="center"<c:if test="${rs.projectName == null}">bgcolor="#ffdfff"</c:if>><c:out value="${rs.projectManager}" /></td>
												</tr>
												<c:set var="NameChk" value="${rs.des}"/>
												<c:set var="chk" value="${rs.name}"/>
												
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