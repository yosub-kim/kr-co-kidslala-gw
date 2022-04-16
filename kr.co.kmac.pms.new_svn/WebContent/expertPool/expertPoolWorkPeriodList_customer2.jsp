<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>

<%
	String companyPosition = request.getParameter("companyPosition");
	String titleMsg = "스케줄 현황";
	
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
		<tr>
				<td height="21" align="left" valign="top">
					<%@ include file="/common/include/searchBox_Header.jsp"%>
					<table border="0" width="100%" style="border-collapse: collapse;">
						<colgroup> 
							<col width="100px">
							<col width="*">
							<col width="100px">
							<col width="125px"> 
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
										<td width="100px">일자</td>
										<td width="80px">업무유형</td>
										<td width="170px">업무내용</td>
										<td width="140px">고객사</td>
										<td width="140px">장소</td>
										<td width="150px">시간</td>
									</tr>
								</thead>			
								<tbody id="ListData">
									<c:choose>
										<c:when test="${!empty list.list}">
											<c:forEach var="rs" items="${list.list}">
											<tr>
												<td><c:out value="${rs.year }" />-<c:out value="${rs.month }" />-<c:out value="${rs.day }" /></td>
												<td><c:out value="${rs.type }" /></td>
												<td><c:out value="${rs.content }" /></td>
												<td><c:out value="${rs.customerName }" /></td>
												<td><c:out value="${rs.place }" /></td>
												<td><c:out value="${rs.startHour }" />시<c:out value="${rs.startMin }" />분 ~ <c:out value="${rs.endHour }" />시<c:out value="${rs.endMin }" />분</td>
											</tr>
											</c:forEach>
										</c:when>
										<c:otherwise>
											<tr><td align="center" colspan="5">검색된 데이터가 없습니다.</td></tr>
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