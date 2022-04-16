<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%-- 공통js,css include --%>
<title>개인별 고객 리스트</title>
<%@ include file="/common/include/includeCss.jsp"%>
<%@ include file="/common/include/includeJavaScript.jsp"%>
<script type="text/javascript" src="/js/tab/CustomerTabbedPane.js"></script>
<script type="text/javascript">
<%-- 개별 스크립트 영역 --%>
function customer_detail_move(viewmode,idx){
	location.href='http://intranet.kmac.co.kr/kmac/comlist/part_pop_customer_detail.asp?viewMode='+viewmode+'&idx='+idx;
}
</script>
</head>
<body>
<%@ include file="/common/include/includeBodyTop.jsp"%>
<%-- 작업영역 --%>
<table width="765" cellpadding="0" cellspacing="0" style="table-layout:fixed" border="0">
	<tr>
		<td height='7'></td>
	</tr>
	<tr>
		<td style="padding-left:4px; padding-right:2px;">
			<jsp:include page="/common/include/includePageMainTitle.jsp" flush="true">
				<jsp:param name="title" value="개인별 고객정보 상세 등록현황" />
				<jsp:param name="backButtonYN" value="N"/>
			</jsp:include>
		</td>
	</tr>
	<tr>
		<td height='7'></td>
	</tr>
	<tr>
		<td height="20" style="padding-left:4px; padding-right:2px;"><strong>총 <c:out value="${result.valueListInfo.totalNumberOfEntries}"/> 건</strong></td>
	</tr>
	<tr>
		<td width="765" style="padding-left:4px; padding-right:2px;">
			<table width="100%" class="listTable" style="table-layout:fixed">
				<thead>
					<tr>
						<td width="60px" >정보유형</td>
						<td width="*" >제목</td>
						<td width="70px" >조직단위</td>
						<td width="70px">비즈니스</td>
						<td width="85px">고객사</td>
						<td width="50px">제공자</td>
						<td width="50px">작성자</td>
						<td width="70px">작성일</td>
						<td width="35px">조회</td>
					</tr>
				</thead>
				<tbody>
				<c:forEach var="result" items="${result.list}">
					<tr onclick="customer_detail_move('person','<c:out value="${result.idx}"/>');" onmouseout="row_out(this)" onmouseover="row_over(this)" style="cursor:hand">
						<td align='center'><c:out value="${result.customerInfoName}" escapeXml="false"/></td>
						<td style="white-space:nowrap; text-overflow: ellipsis; overflow: hidden; "><c:out value="${result.subject}" escapeXml="false" /></td>
						<td align='center' style="white-space:nowrap; text-overflow: ellipsis; overflow: hidden; "><c:out value="${result.writerDeptName}" escapeXml="false"/></td>						
						<td align='center' style="white-space:nowrap; text-overflow: ellipsis; overflow: hidden; "><c:out value="${result.businessTypeName}" escapeXml="false"/></td>
						<td align='center' style="white-space:nowrap; text-overflow: ellipsis; overflow: hidden; "><c:out value="${result.customerName}"  escapeXml="false" /></td>
						<td align='center' style="white-space:nowrap; text-overflow: ellipsis; overflow: hidden; "><c:out value="${result.embbsName}" /></td>
						<td align='center'><c:out value="${result.writer}" /></td>
						<fmt:parseDate value="${result.regdate}" type="DATE" dateStyle="SHORT"  pattern="yyyy-MM-dd HH:mm:ss.SSS" var="from"/>
						<fmt:formatDate value="${from}" pattern="yyyy-MM-dd" var="formatted"/>
						<td align='center'><c:out value="${formatted}" /></td>
						<td align='center'><c:out value="${result.readCnt}" /></td>
					</tr>
				</c:forEach>
				<c:if test="${empty result.list}">
					<tr>
						<td colspan='9' align='center' style="white-space:nowrap; text-overflow: ellipsis; overflow: hidden; ">검색 결과가 존재하지 않습니다.</td>
					</tr>
				</c:if>
				</tbody>
			</table>		
		</td>
	</tr>
	<!-- 페이징 -->
	<script>
	function goPage(pageNumber){
		pagingfrm.pg.value = pageNumber;
		pagingfrm.submit();
	}
	</script>
	<form name="pagingfrm">
	<tr>
		<td align="left">
			<table width="765" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="100%" align="center">
						<SCRIPT LANGUAGE="JavaScript">
							document.write( makePageHtml( 
									<c:out value="${result.valueListInfo.pagingPage}" default="1"/>, 
									10, 
									<c:out value="${result.valueListInfo.totalNumberOfEntries}" default="0"/> , 
									10)
							) ;
						</SCRIPT>
					</td>
				</tr>
			</table>									
			
			<input type="hidden" name="mode"     value="selectListForIndividual">
			
			<input type="hidden" name="pg"       			value="<c:out value="${pageData.pg}"/>">
			<input type="hidden" name="writer"       		value="<c:out value="${pageData.writer}"/>">
			<input type="hidden" name="customerInfoType"    value="<c:out value="${pageData.customerInfoType}"/>">
			<input type="hidden" name="from"       			value="<c:out value="${pageData.from}"/>">
			<input type="hidden" name="to"       			value="<c:out value="${pageData.to}"/>">
			<input type="hidden" name="industryTypeCode"	value="<c:out value="${pageData.industryTypeCode}"/>">
			<input type="hidden" name="businessTypeCode"	value="<c:out value="${pageData.businessTypeCode}"/>">
			<input type="hidden" name="valueType"       	value="<c:out value="${pageData.valueType}"/>">
		</td>
	</tr>
	
	</form>
<%-- 페이징 영역 끝--%>
</table>
</body>
</html>
