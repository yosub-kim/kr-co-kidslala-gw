<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>

<%
		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("MM");
		String today = formatter.format(new java.util.Date());
		String titleMsg = "상태 별 예상 성과급 (" + today+"월)";
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
	<table width="800" cellpadding="0" cellspacing="0">
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
		<tr>
				<td height="21" align="left" valign="top">
					<%-- <%@ include file="/common/include/searchBox_Header.jsp"%>
					 <table border="0" width="100%" style="border-collapse: collapse;">
						<colgroup> 
							<col width="200px">
							<col width="*">
							<col width="200px">
							<col width="225px"> 
						</colgroup>
						<tr>
							<td class="searchTitle_center">기간</td> 
							<td class="searchField_center">
								<fmt:parseDate value="${startDate}" type="DATE" dateStyle="SHORT"  pattern="yyyyMMdd" var="var1"/>
								<fmt:formatDate value="${var1}" pattern="yyyy-MM-dd" var="start"/>
								<fmt:parseDate value="${endDate}" type="DATE" dateStyle="SHORT"  pattern="yyyyMMdd" var="var2"/>
								<fmt:formatDate value="${var2}" pattern="yyyy-MM-dd" var="end"/>
							<script>
								jQuery(function(){jQuery( "#startDate" ).datepicker({});});
								jQuery(function(){jQuery( "#endDate" ).datepicker({});});
							</script>
							<input type="text" name="startDate" id="startDate" readonly="readonly" size="13" value="<c:out value="${start}" />" />&nbsp~&nbsp     
							<input type="text" name="endDate" id="endDate" readonly="readonly" size="13" value="<c:out value="${end}" />" />									
							</td>
						</tr>
					</table> 
					<jsp:include page="/common/include/searchBox_Footer.jsp" flush="true">
				<jsp:param name="hasButton" value="Y" />
			</jsp:include> --%>
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
											<td width="80px" rowspan="2">성명</td>
											<td width="80px" rowspan="2">기준금액</td>
											<td width="80px" rowspan="2">투입률</td>
											<td width="90px" rowspan="2">총 지도일정</td>
											<td width="100px" rowspan="2">미 할당 일지 수</br>(수/금액)</td>
											<td colspan="5">할당 지도일지 (수/금액)</td>
										</tr>
										<tr>
											<td width="90px">할당</td>
											<td width="90px">미작성</td>
											<td width="90px">작성</td>
											<td width="90px">검토</td>
											<td width="90ox">승인 (성과급)</td>
										</tr>
									</thead>						
								<tbody id="ListData">
									<c:choose>
										<c:when test="${!empty list.list}">
											<c:forEach var="rs" items="${list.list}">
											<tr>
											<td width="60px" rowspan="2"><c:out value="${rs.name}" escapeXml="false" /></td>
											<td width="60px" rowspan="2"><fmt:formatNumber value="${rs.realcost }" pattern=""/></td>
											<td width="60px" rowspan="2"><fmt:formatNumber value="${rs.resRate }" pattern=""/></td>
											<td width="60px" rowspan="2"><fmt:formatNumber value="${rs.allcount }" pattern=""/></td>
											<td width="60px" rowspan="2"><fmt:formatNumber value="${rs.allcount2 }" pattern=""/></td>											
											<td><fmt:formatNumber value="${rs.allcount1 }" pattern=""/></td>
											<td><fmt:formatNumber value="${rs.write_cnt }" pattern=""/></td>
											<td><fmt:formatNumber value="${rs.approver_cnt }" pattern=""/></td>
											<td><fmt:formatNumber value="${rs.reviewer_cnt }" pattern=""/></td>
											<td><fmt:formatNumber value="${rs.complete_cnt }" pattern=""/></td>
											</tr>
											<tr>
												<td><c:choose><c:when test="${rs.allmoney == null}"> 0 </c:when><c:otherwise><fmt:formatNumber value="${rs.allmoney }" pattern=""/></c:otherwise></c:choose></td>
												<td><c:choose><c:when test="${rs.write == null}"> 0 </c:when><c:otherwise><fmt:formatNumber value="${rs.write }" pattern=""/></c:otherwise></c:choose></td>
												<td><c:choose><c:when test="${rs.approver == null}"> 0 </c:when><c:otherwise><fmt:formatNumber value="${rs.approver }" pattern=""/></c:otherwise></c:choose></td>
												<td><c:choose><c:when test="${rs.reviewer == null}"> 0 </c:when><c:otherwise><fmt:formatNumber value="${rs.reviewer }" pattern=""/></c:otherwise></c:choose></td>
												<td><c:choose><c:when test="${rs.complete == null}"> 0 </c:when><c:otherwise><fmt:formatNumber value="${rs.complete }" pattern=""/></c:otherwise></c:choose></td>
											</tr>
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