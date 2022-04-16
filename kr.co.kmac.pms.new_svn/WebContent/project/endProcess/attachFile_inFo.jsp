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
<body onload="page_Load();">
<%@ include file="/common/include/includeBodyTop.jsp"%>
<%-- 작업영역 --%>
<table width='765'  cellpadding="0" cellspacing="0" style="table-layout:fixed;">
	<colgroup>
		<col width="793px"/>
	</colgroup>
	<tr>
		<td height='7'></td>
	</tr>
	<tr>
		<td class="Title">
			&nbsp;<img src="/images/icon_2.gif" align="absmiddle">
			<b><font class='TF1'>&nbsp;프로젝트 산출물 조회</font></b>
		</td>
	</tr>
	<tr>
		<td height='7'></td>
	</tr>
	<tr>
		<td height=>
			<table cellSpacing="0" cellpadding="0" width="100%" class='listTable' >
				<tr>
					<td>
						&nbsp;<img src="/images/icon_5.gif" align="absmiddle">
						<b><font class='SF'>&nbsp;고객정보</font></b>
					</td>
					<td align="right">
					</td>
				</tr>			
			</table>
		</td>
	</tr>
	
	<tr>
		<td height='3'></td>
	</tr>
	<tr>
		<td>
			<table cellSpacing="0" cellpadding="0" width="100%" class='listTable' >
				<thead>
					<tr height="25px">
						<td class="col_Header">문서종류</td>
						<td class="col_Header">첨부타입</td>
						<td class="col_Header">첨부문서명</td>
						<td class="col_Header">파일명</td>
						<td class="col_Header">등록일</td>
						<td class="col_Header">등록자</td>
						<td class="col_Header">다운</td>
					</tr>
				</thead>
				<tbody>
				<c:if test="${!empty reqFile}">
					<c:forEach var="file" items="${reqFile}">
					<tr height="25px">
						<td ><c:out value="${file.attachOutputType}"/></td>
						<td ><c:out value="${file.attachIsEssential}"/></td>
						<td ><c:out value="${file.attachOutputName}"/></td>
						<td ><c:out value="${file.attachFileName}"/></td>
						<td ><c:out value="${file.attachCreateDate}"/></td>
						<td ><c:out value="${file.attachCreatorName}"/></td>
						<td ></td>
					</tr>
					</c:forEach>
				</c:if>
				</tbody>
			</table>
		</td>
	</tr>
	<tr>
		<td height='7'></td>
	</tr>
	<tr>
		<td height=>
			<table cellSpacing="0" cellpadding="0" width="100%" class='listTable' >
				<tr>
					<td>
						&nbsp;<img src="/images/icon_5.gif" align="absmiddle">
						<b><font class='SF'>&nbsp;지도일지</font></b>
					</td>
					<td align="right">
					</td>
				</tr>			
			</table>
		</td>
	</tr>
	
	<tr>
		<td height='3'></td>
	</tr>
	<tr>
		<td>
			<table cellSpacing="0" cellpadding="0" width="100%" class='listTable' >
				<thead>
					<tr height="25px">
						<td class="col_Header">문서종류</td>
						<td class="col_Header">첨부타입</td>
						<td class="col_Header">첨부문서명</td>
						<td class="col_Header">파일명</td>
						<td class="col_Header">등록일</td>
						<td class="col_Header">등록자</td>
						<td class="col_Header">다운</td>
					</tr>
				</thead>
				<tbody>
				<c:if test="${!empty reqFile}">
					<c:forEach var="file" items="${reqFile}">
					<tr height="25px">
						<td ><c:out value="${file.attachOutputType}"/></td>
						<td ><c:out value="${file.attachIsEssential}"/></td>
						<td ><c:out value="${file.attachOutputName}"/></td>
						<td ><c:out value="${file.attachFileName}"/></td>
						<td ><c:out value="${file.attachCreateDate}"/></td>
						<td ><c:out value="${file.attachCreatorName}"/></td>
						<td ></td>
					</tr>
					</c:forEach>
				</c:if>
				</tbody>
			</table>
		</td>
	</tr>
	<tr>
		<td height='7'></td>
	</tr>
	<tr>
		<td height=>
			<table cellSpacing="0" cellpadding="0" width="100%" class='listTable' >
				<tr>
					<td>
						&nbsp;<img src="/images/icon_5.gif" align="absmiddle">
						<b><font class='SF'>&nbsp;산출물</font></b>
					</td>
					<td align="right">
					</td>
				</tr>			
			</table>
		</td>
	</tr>
	
	<tr>
		<td height='3'></td>
	</tr>
	<tr>
		<td>
			<table cellSpacing="0" cellpadding="0" width="100%" class='listTable' >
				<thead>
					<tr height="25px">
						<td class="col_Header">문서종류</td>
						<td class="col_Header">첨부타입</td>
						<td class="col_Header">첨부문서명</td>
						<td class="col_Header">파일명</td>
						<td class="col_Header">등록일</td>
						<td class="col_Header">등록자</td>
						<td class="col_Header">다운</td>
					</tr>
				</thead>
				<tbody>
				<c:if test="${!empty reqFile}">
					<c:forEach var="file" items="${reqFile}">
					<tr height="25px">
						<td ><c:out value="${file.attachOutputType}"/></td>
						<td ><c:out value="${file.attachIsEssential}"/></td>
						<td ><c:out value="${file.attachOutputName}"/></td>
						<td ><c:out value="${file.attachFileName}"/></td>
						<td ><c:out value="${file.attachCreateDate}"/></td>
						<td ><c:out value="${file.attachCreatorName}"/></td>
						<td ></td>
					</tr>
					</c:forEach>
				</c:if>
				</tbody>
			</table>
		</td>
	</tr>
	<tr>
		<td align="right" height="7px" valign="middle">
		</td>
	</tr>
</table>
<%@ include file="/common/include/includeBodyBottom.jsp"%>
</body>
</html>