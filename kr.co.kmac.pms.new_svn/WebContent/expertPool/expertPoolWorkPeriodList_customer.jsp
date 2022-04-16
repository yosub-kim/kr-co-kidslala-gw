<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>

<%
	String companyPosition = request.getParameter("companyPosition");
	String titleMsg = "스케줄/고객정보 현황";
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
	var startDate = document.frm.startDate.value;
	var endDate = document.frm.endDate.value;
	var url = "/action/ExpertPoolManagerAction.do?mode=getExpertPoolWorkPeriodList_customer2&ssn="+ssn+"&startDate="+startDate+"&endDate="+endDate;
	var sFeatures = "top=50,left=50,width=790,height=600,resizable=no,scrollbars=yes";
	var detailWin = window.open(url, "customer1", sFeatures);
	detailWin.focus();
}

function detail2(ssn, start, end) {
	
	var url = "http://intranet.kmac.co.kr/kmac/comlist/customer_methodlist.asp?date1="+start+"&date2="+end+"&search=writer&embbsmethod=1&ssn="+ssn;
	var sFeatures = "top=50,left=50,width=790,height=600,resizable=no,scrollbars=yes";
	var detailWin = window.open(url, "customer2", sFeatures);
	detailWin.focus();
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
		<table width="100%" cellpadding="0" cellspacing="0" style="table-layout:fixed" border="0">
		<style>
			.border-styles > p{
				margin: 1px 0;
				border-radius:5px;
   				line-height:21px;
   				padding: 10px 10px 5px 10px;
   				border:1px solid #ccc;
  				padding-left:15px;
   				font-weight: bold;
				font-size:13px;
				color: gray;
				line-height:150%;
				font-family : 맑은고딕;
				}
				
				#fontweight1 {padding-left:12px; font-size:11pt; font-weight: bold; color: #555; }
				#fontweight2 {padding-right:15px; font-size:10pt; font-weight: bold; color: #555; text-align:right;}
		</style>
			<div class="border-styles">
				<p style="border-style: solid">
				&nbsp&nbsp ※&nbsp&nbsp 스케줄 등록 건수 : 외근 스케줄에서 고객정보 등록대상 스케줄 건수</br>
				&nbsp&nbsp ※&nbsp&nbsp 고객정보 등록 건수 : 고객정보 등록대상 스케줄 날짜에 등록된 고객정보 건수</br>
				</p>
			</div>
			
	</table>			
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
										<!-- <a class="btNa0a0a0 txt2btn" onclick="javascript:self.close()" href="#">닫기</a> -->
									</div>
								</div>								
							</td>
					</tr>
					<tr>
						<td>
							<table class="listTable">
								<thead>
									<tr height="25px">
										<td width="10px">조직단위</td>
										<td width="100px">성명</td>
										<td width="110px">직위/직책</td>
										<!-- <td width="50px">월</td>
										<td width="50px">일</td>
										<td width="90px">스케줄 등록</td>
										<td width="100px">고객정보 등록</td> -->
										<td width="130px">스케줄 등록 건수</td>
										<td width="140px">고객정보 등록 건수</td>
										<td width="80px">등록률</td>
									</tr>
								</thead>			
								<tbody id="ListData">
									<c:choose>
										<c:when test="${!empty list.list}">
											<c:set var="NameChk" value=" "/>
											<c:set var="chk" value=" "/>
											<c:set var="dateChk" value=" "/>
											<c:forEach var="rs" items="${list.list}">
											<%-- <tr onclick="detail('<c:out value="${rs.ssn}" />')" style="cursor: hand;" onmouseover="row_over(this)" onmouseout="row_out(this)"> --%>
											<tr>
												<c:if test="${rs.labelName != NameChk}">
													<td rowspan="<c:out value="${rs.groupcount}"/>" style="text-align: center" ><c:out value="${rs.groupName}" escapeXml="false" /></td>
												</c:if>
												<c:if test="${rs.userName != chk}">
													<td rowspan="<c:out value="${rs.usercount}"/>" style="text-align: center"><c:out value="${rs.userName}" escapeXml="false" /></td>
												</c:if>
												<c:if test="${rs.userName != chk}">
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
												<%-- <td><c:out value="${rs.month }" /></td>
												<td><c:out value="${rs.day }" /></td>
												<td><c:out value="${rs.ssnCount }" /></td>
												<td><c:out value="${rs.cusCount }" /></td> --%>
												<c:if test="${rs.userName != chk}">
													<td rowspan="<c:out value="${rs.usercount}"/>" style="text-align: center; cursor: hand;" onclick="detail('<c:out value="${rs.ssn}" />')" onmouseover="row_over(this)" onmouseout="row_out(this)"><c:out value="${rs.ssnResult}" /></td>
												</c:if>
												<c:if test="${rs.userName != chk}">
													<td rowspan="<c:out value="${rs.usercount}"/>" style="text-align: center; cursor: hand;" onclick="detail2('<c:out value="${rs.ssn}" />','<c:out value="${start}" />','<c:out value="${end}" />')" onmouseover="row_over(this)" onmouseout="row_out(this)"><c:out value="${rs.cusResult}" /></td>
												</c:if>
												<c:if test="${rs.userName != chk}">
													<td rowspan="<c:out value="${rs.usercount}"/>" style="text-align: center"><fmt:formatNumber value="${rs.result }" type="percent" /></td>
												</c:if>
												
											</tr>
											<c:set var="NameChk" value="${rs.labelName}"/>
											<c:set var="chk" value="${rs.userName}"/>
											<c:set var="dateChk" value="${rs.filterdate}"/>
											</c:forEach>
										</c:when>
										<c:otherwise>
											<tr><td align="center" colspan="6">검색된 데이터가 없습니다.</td></tr>
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