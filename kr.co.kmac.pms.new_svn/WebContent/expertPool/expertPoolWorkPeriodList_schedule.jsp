<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>

<%
	String companyPosition = request.getParameter("companyPosition");
	String titleMsg = "개인 일정 현황";
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
function saveListToExcel() {
	var surl = '/action/ExpertPoolManagerAction.do?mode=saveRestrictExpertPoolListToExcel_schedule';
 	surl += "&startDate=" + document.frm.startDate.value;
	surl += "&endDate=" + document.frm.endDate.value;
	/* surl += "&name=" + document.frm.name.value; */
	document.location = surl;
}
</script>
</head>


<body>
<form name="frm" method="post">
	<input type="hidden" id="today" name="today" value="<c:out value="${today}" />" />
	<table width="1000" cellpadding="0" cellspacing="0">
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
					<%@ include file="/common/include/searchBox_Header.jsp"%>
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
										<!-- <a class="btN3fac0c txt2btn" href="javascript:saveListToExcel();">Excel 저장</a> -->
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
										<td width="70px">조직단위</td>
										<td width="60px">성명</td>
										<!-- <td width="60px">사번</td> -->
										<td width="80px">직위</td>
										<td width="100px">날짜</td>
										<td width="150px">시간</td>
										<td width="70px">외/내근여부</td>
										<td width="80px">출/퇴근여부</td>
										<td width="100px">기업명</td>
										<td width="90px">장소</td>
										<td width="180px">내용</td>
									</tr>
								</thead>			
								<tbody id="ListData">
									<c:choose>
										<c:when test="${!empty list.list}">
											<c:set var="NameChk" value=" "/>
											<c:set var="chk" value=" "/>
											<c:set var="dateChk" value=" "/>
											<c:forEach var="rs" items="${list.list}">
												<tr onclick="detail('<c:out value="${rs.ssn}" />')" style="cursor: hand;" onmouseover="row_over(this)" onmouseout="row_out(this)">
												<c:if test="${rs.groupName != NameChk}">
													<td rowspan="<c:out value="${rs.groupcount}"/>" style="text-align: center" ><c:out value="${rs.labelName}" escapeXml="false" /></td>
												</c:if>
												<c:if test="${rs.name != chk}">
													<td rowspan="<c:out value="${rs.usercount}"/>" style="text-align: center"><c:out value="${rs.name}" escapeXml="false" /></td>
												</c:if>
												<%-- <c:if test="${rs.name != chk}">
													<td rowspan="<c:out value="${rs.usercount}"/>" style="text-align: center"><c:out value="${rs.emplNo}" escapeXml="false" /></td>
												</c:if> --%>
												<c:if test="${rs.name != chk}">
													<td rowspan="<c:out value="${rs.usercount}"/>" style="text-align: center">
														<c:choose>
															<c:when test="${rs.posName == '시니어 컨설턴트'}"><c:out value="시니어" /></c:when>
															<c:when test="${rs.posName == '치프 컨설턴트'}"><c:out value="치프" /></c:when>
															<c:when test="${rs.posName == '프린서플 컨설턴트'}"><c:out value="프린서플" /></c:when>
															<c:when test="${rs.posName == '파트너 컨설턴트'}"><c:out value="파트너" /></c:when>
															<c:otherwise><c:out value="${rs.posName}" escapeXml="false" /></c:otherwise>
														</c:choose>
													</td>
												</c:if>
												<%-- <td rowspan="<c:out value="${rs.dateCnt}"/>" style="text-align: center"><c:out value="${rs.year}"/>-<c:out value="${rs.month}"/>-<c:out value="${rs.day}"/></td> --%>
												<td alian="center"><c:out value="${rs.year}" />-<c:out value="${rs.month}" />-<c:out value="${rs.day}" /></td>
												<td alian="center"><c:out value="${rs.startHour}" />시<c:out value="${rs.startMin}" />분 ~ <c:out value="${rs.endHour}" />시<c:out value="${rs.endMin}" />분</td>
												<td alian="center">
												<c:choose>
													<c:when test="${rs.workType == 'I'}"><c:out value="내근" /></c:when>
													<c:when test="${rs.workType == 'E'}"><c:out value="외근" /></c:when>
													<c:otherwise><c:out value="-" /></c:otherwise>
												</c:choose>
												</td>
												<td alian="center">
												<c:choose>
													<c:when test="${rs.relationUser == '1'}"><c:out value="현장 출근" /></c:when>
													<c:when test="${rs.relationUser == '2'}"><c:out value="현장 퇴근" /></c:when>
													<c:when test="${rs.relationUser == '3'}"><c:out value="현장 출/퇴근" /></c:when>
													<c:otherwise><c:out value="-" /></c:otherwise>
												</c:choose>
												</td>
												<td alian="center"><c:out value="${rs.customerName}" /></td>
												<td alian="center">
												<c:choose>
													<c:when test = "${rs.place == '' }" ><c:out value="-" /></c:when>
													<c:when test = "${rs.place == null }" ><c:out value="-" /></c:when>
													<c:otherwise><c:out value="${rs.place}" /></c:otherwise>
												</c:choose>
												</td>
												<td alian="center"><c:out value="${rs.content}" /></td>
												</tr>
												<c:set var="NameChk" value="${rs.groupName}"/>
												<c:set var="chk" value="${rs.name}"/>
												<c:set var="dateChk" value="${rs.filterdate}"/>
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