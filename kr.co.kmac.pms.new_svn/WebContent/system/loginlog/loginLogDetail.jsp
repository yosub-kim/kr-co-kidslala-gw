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
function doSubmit() {
	frm.submit();
}
</script>
</head>
<body>
<%-- 작업영역 --%>
<div style="margin: 70 0 0 20 ">
<table width="751" cellpadding="0" cellspacing="0">
	<tr>
		<td height="12">
			<% String back = request.getParameter("backButtonYN"); %>
			<jsp:include page="/common/include/includePageMainTitle.jsp" flush="true">
				<jsp:param name="title" value="로그인 상세 현황" />
				<jsp:param name="backButtonYN" value="<%=back %>" />
			</jsp:include>
		</td>
	</tr>
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
					<a class="btN3fac0c txt2btn" href="javascript:document.saveToExcelFrm.submit();">Excel 다운</a>
				</div>
			</div>
		</td>
	</tr>	
	<tr>
		<td align="center">
			<table class="listTable">
				<thead>
					<tr height="25px">
						<td>SEQ</td>
						<td>로그인 시간</td>
						<td>IP</td>
						<td>이름</td>
						<td>접속기기</td>
					</tr>
				</thead>
				<tbody id="ListData">
				<c:forEach var="result" items="${list.list}">
					<tr>
						<td><c:out value="${result.cnt}" /></td>
						<td><c:out value="${result.loginDate}" /></td>
						<td><c:out value="${result.ip}" /></td>
						<td><c:out value="${result.name}" /></td>
						<td><c:out value="${result.deviceType}" /></td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
		</td>
	</tr>
	<%-- 페이징 영역 시작--%>
	<script>
	function goPage(pageNumber){
		pagingfrm.pg.value = pageNumber;
		pagingfrm.submit();
	}
	</script>
	<form name="pagingfrm">
	<tr>
		<td>
			<div>
			<%-- 페이징 영역 시작--%>
				<table width='765'  cellpadding="0" cellspacing="0">
					<tr height='30'>
						<td width="100%" align="center">
							<SCRIPT LANGUAGE="JavaScript">
								document.write( makePageHtml( 
										<c:out value="${list.valueListInfo.pagingPage}" default="1"/>, 
										15, 
										<c:out value="${list.valueListInfo.totalNumberOfEntries}" default="0"/> , 
										15)
								) ;
							</SCRIPT>
						</td>
					</tr>
				</table>									
			
			</div>
			<input type="hidden" name="mode"	value="getLoginLogDetailReport">
			<input type="hidden" name="pg"		value="">
			<input type="hidden" name="from"	value="<c:out value="${search.from}"/>">
			<input type="hidden" name="to"		value="<c:out value="${search.to}"/>">
			<input type="hidden" name="ssn"		value="<c:out value="${search.ssn}"/>">
			<input type="hidden" name="name"	value="<c:out value="${search.name}"/>">
		</td>
	</tr>
	</form>
	<%-- 페이징 영역 끝--%>
	<form name="saveToExcelFrm">
		<input type="hidden" name="mode"	value="saveLoginLogDetailReport">
		<input type="hidden" name="from"	value="<c:out value="${search.from}"/>">
		<input type="hidden" name="to"		value="<c:out value="${search.to}"/>">
		<input type="hidden" name="ssn"		value="<c:out value="${search.ssn}"/>">
		<input type="hidden" name="name"	value="<c:out value="${search.name}"/>">
	</form>
	<tr>
		<td>&nbsp;</td>
	</tr>
</table>
<%@ include file="/common/include/includeBodyBottom.jsp"%>
</body>
</div>
</html>