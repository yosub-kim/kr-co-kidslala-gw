<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%-- 공통js,css include --%>
<%@ include file="/common/include/includeCss.jsp"%>
<%@ include file="/common/include/includeJavaScript.jsp"%>

<script type="text/javascript">
<%-- 개별 스크립트 영역 --%>
</script>
</head>


<body>
<form name="frm" method="post">
	<input type="hidden" id="today" name="today" value="<c:out value="${today}" />" />
	<table width="750" cellpadding="0" cellspacing="0">
		<!-- 타이틀 영역 -->
		<tr>
			<td height="12">
				<% String back = request.getParameter("backButtonYN"); %>
				<jsp:include page="/common/include/includePageMainTitle.jsp" flush="true" >
					<jsp:param name="title" value="20% 성과급 누적 내역" />
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
						<td>
							<table class="listTable">
								<thead>
									<tr height="25px">
										<td width="100px">이름</td>
										<td width="*">프로젝트 명</td>
										<td width="70px">년</td>
										<td width="70px">월</td>
										<td width="90px">금액</td>
										<td width="90px">총 금액</td>
										<td width="80px">지급 여부</td>
									</tr>
								</thead>			
								<tbody id="ListData">
									<c:choose>
										<c:when test="${!empty list.list}">
											<c:set var="SsnChk" value=""/>
											<c:set var="projectChk" value=""/>
											<c:forEach var="rs" items="${list.list}">
													<c:if test="${rs.ssn != SsnChk}">
														<td rowspan="<c:out value="${rs.ssnCnt}"/>" style="text-align: center"><c:out value="${rs.name}" escapeXml="false" /></td>
													</c:if>
													<c:if test="${rs.projectName != projectChk }">
														<td rowspan="<c:out value="${rs.projectCnt}"/>" style="text-align: center"><c:out value="${rs.projectName}" escapeXml="false" /></td>
													</c:if>
													<td alian="center"><c:out value="${rs.year}" /></td>
													<td alian="center"><c:out value="${rs.month}" /></td>
													<td alian="center"><fmt:formatNumber value="${rs.cost}" type="number" var="cost" /><c:out value="${cost}" /></td>
													<c:if test="${rs.projectName != projectChk }">
														<td rowspan="<c:out value="${rs.projectCnt }"/>" style="text-align: center"><fmt:formatNumber value="${rs.sumCost}" type="number" var="cost" /><c:out value="${cost}" /></td>
													</c:if>
													<c:if test="${rs.projectName != projectChk }">
														<td rowspan="<c:out value="${rs.projectCnt }"/>" style="text-align: center"> <c:out value="${rs.isSanction}" /></td>
													</c:if>
												</tr>
												<c:set var="SsnChk" value="${rs.ssn}"/>
												<c:set var="projectChk" value="${rs.projectName}"/>
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