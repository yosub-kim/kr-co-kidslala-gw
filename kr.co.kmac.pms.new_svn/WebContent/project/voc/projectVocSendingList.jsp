<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%-- 공통js,css include --%>
<%@ include file="/common/include/includeCss.jsp"%>
<%@ include file="/common/include/includeJavaScript.jsp"%>
<% int totalCnt = 0; %>

<script type="text/javascript">
<%-- 개별 스크립트 영역 --%>
function doSearch() {
	frm.submit();
}
function openVocContent(requestDate, projectCode){
	var url = 'http://intranet.kmac.co.kr/kmac/vocinfo/VocFeedbackView.asp?projectCode='+projectCode+'&RequestDate='+requestDate;
	window.open(url,'VOC','top=0,left=0,status=no,toolbar=no,scrollbars=yes,location=no,width=700,height=850,directories=no,menubar=no');
}
</script>
</head>

<body>
<form name="frm" method="post">
	<div style="display: none;">
		<input type="text" name="projectCode" value="<c:out value="${projectCode}" />"  />
	</div>
	<div id="totalCnt" style="display: none;"></div>

	<table width="100%" cellpadding="0" cellspacing="0">
		<tr>
			<td height="12">
				<jsp:include page="/common/include/includePageMainTitle.jsp" flush="true">
					<jsp:param name="title" value="VOC 발송 현황" />
					<jsp:param name="backButtonYN" value="N" />
				</jsp:include>
			</td>
		</tr>
		
		<!-- 검색 영역 -->
		<tr>
			<td>
				<%@ include file="/common/include/searchBox_Header.jsp"%>
				<table border="0" height="26" width="100%" cellpadding="0" cellspacing="0" style="border-collapse: collapse; ">
					<colgroup> 
						<col width="85px">
						<col width="*"> 
					</colgroup>
					<tr> 
						<td class="searchTitle_center">검색 조건</td>
						<td class="searchField_left">
							<date:year beforeYears="4" afterYears="4" attribute=" name='year' class='selectbox' style='width:100pt' " selectedInfo="${year}" />
							<select name="month" class='selectbox' style="width:100pt">
								<c:forEach var="i" begin="1" end="12">
									<option value="<c:out value="${i}"/>" <c:if test="${month == i}">selected</c:if>><c:out value="${i}"/>월</option>
								</c:forEach>
							</select>							
						</td>
					</tr>
				</table>
				<jsp:include page="/common/include/searchBox_Footer.jsp" flush="true">
					<jsp:param name="hasbutton" value="Y" />
				</jsp:include>
			</td>
		</tr>			 
		<!-- 본문 리스트 시작 -->
		<tr>
			<td height="30">
				<div class="btNbox pb15">
					<div class="btNlefTdiv">				
						<img src="/images/sub/line3Blit.gif" alt="">
						<span class="bold colore74c3a" id="totalcount"></span>
						<span>Total - Page (1 of 1)</span>
					</div>
				</div>
			</td>
		</tr>
							
		<tr>
			<td>
				<table id="vocTable" class="listTable" style="table-layout: fixed">
					<thead id="vocTableHeader">
						<tr>
							<td width="80">조직단위</td>
							<td width="*">프로젝트명</td>
							<td width="80">요청일</td>
							<td width="80">발송일</td>
							<td width="80">수신일</td>
							<td width="80">응답일</td>
							<td width="150">고객사 담당자</td>
						</tr>
					</thead>									
					<tbody id="vocTableBody">
						<c:if test="${!empty vocList}">
							<c:forEach var="rs" items="${vocList}">
								<tr title="응답일이 표시된 VOC는 돋보기를 클릭하여 내용을 볼 수 있습니다.">
									<fmt:parseDate value="${rs.requestDate}" type="DATE" dateStyle="SHORT"  pattern="yyyyMMdd" var="data1"/>
									<fmt:formatDate value="${data1}" pattern="yyyy-MM-dd" var="requestDate"/>
							
									<fmt:parseDate value="${rs.sendDate}" type="DATE" dateStyle="SHORT"  pattern="yyyyMMdd" var="data2"/>
									<fmt:formatDate value="${data2}" pattern="yyyy-MM-dd" var="sendDate"/>
								
									<fmt:parseDate value="${rs.receiveDate}" type="DATE" dateStyle="SHORT"  pattern="yyyyMMdd" var="data3"/>
									<fmt:formatDate value="${data3}" pattern="yyyy-MM-dd" var="receiveDate"/>
								
									<fmt:parseDate value="${rs.responseDate}" type="DATE" dateStyle="SHORT"  pattern="yyyyMMdd" var="data4"/>
									<fmt:formatDate value="${data4}" pattern="yyyy-MM-dd" var="responseDate"/>
								
									<td class="myoverflowC"><c:out value="${rs.runningDivName}"/></td>
									<td class="myoverflow" title="<c:out value="${rs.projectName}"/>"><c:out value="${rs.projectName}"/></td>
									<td><c:out value="${requestDate}"/></td>
									<td><c:out value="${sendDate}"/></td>
									<td><c:out value="${receiveDate}"/></td>
									<td><c:out value="${responseDate}"/></td>
									<td><c:if test="${rs.receiveName != null}">
										<c:out value="${rs.receiveName}"/>(<c:out value="${rs.receiveEmail}"/>)
										</c:if>
										<c:if test="${rs.responseDate != null}">
											<img alt="VOC내용보기" src="/images/btn_glass.jpg" border="0" style="cursor: hand;" onclick="openVocContent('<c:out value="${rs.requestDate}"/>', '<c:out value="${rs.projectCode}"/>')">
										</c:if>
									</td>
								</tr>
								<% totalCnt = totalCnt + 1; %>
							</c:forEach>
						</c:if>
						<c:if test="${empty vocList}">
							<td colspan="7">검색 결과가 없습니다.</td>
						</c:if>
					</tbody>
				</table>
			</td>
		</tr>
		<tr>
			<td height="10"></td>
		</tr>					

		<!-- 본문종료-->
</form>
</body>
</html>
<script type="text/javascript">
	document.all.totalcount.innerText=<%=totalCnt%>;
</script>