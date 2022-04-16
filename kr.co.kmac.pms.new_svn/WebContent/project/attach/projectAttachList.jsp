<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<%-- 공통js,css include --%>
<%-- <%@ include file="/common/include/includeCss.jsp"%> --%>
<%@ include file="/common/include/includeJavaScript.jsp"%>
<link rel="stylesheet" href="/resources/css/common.css" type="text/css" />
<link rel="stylesheet" href="/resources/css/board.css" type="text/css" />
<link rel="stylesheet" href="/resources/css/selectric.css" type="text/css" />
<link rel="stylesheet" href="/resources/css/dev.css" type="text/css" />
<meta name="viewport" content="width=1600">

<script type="text/javascript">
<%-- 개별 스크립트 영역 --%>
function goPage(next_page) {
	document.form.pageNo.value = next_page ;
	document.form.target = "";		
	document.form.action = "/action/AttachAction.do?mode=selectList";
	document.form.submit();
}
function doSearch() {
	document.form.submit();
}

</script>
</head>

<body>
<form name="form" method="post">
	<div style="display: none;">
		<input type="hidden" name="pageNo"> 
		<input type="text" name="projectCode" value="<%=request.getParameter("projectCode") %>" >
		<input type="text" name="projectCode" id="projectCode" value="<c:out value="${projectCode}"/>" >
		<input type="hidden" name="outputType" id="outputType" value="<c:out value="${outputType}"/>">
		<input type="hidden" name="fileName" id="fileName" value="<c:out value="${fileName}"/>">
	</div>
	<div style="margin:70px 15px 0 15px;">
		<div class="board_box">
			<div class="title_both">
				<div class="h1_area">
					<p class="h1">프로젝트 산출물 현황</p>
				</div>
			</div>
			
			<div class="board_contents">
				<table id="projectProgressTable" class="tbl-edit td-c"><!-- td 영역이 가운데 정렬 일 경우 td-c -->
					<colgroup>
						<col style="width: 15%"/>
						<col style="*" />
						<col style="width: 15%" />
						<col style="width: 15%" />
					</colgroup>
					<tr>
						<th class="detailTableField_center">구분</th>
						<th class="detailTableField_center">파일명</th>
						<th class="detailTableField_center">등록일</th>
						<th class="detailTableField_center">등록자</th>											
					</tr>
					<c:if test="${!empty list.list}">
						<c:forEach var="rs" items="${list.list}">
							<tr onmouseover="row_over(this)" onmouseout="row_out(this)"> 
								<td><c:out value="${rs.attachOutputTypeName}"/></td>
								<td class="subject" style="cursor:hand; padding: 0 0 0 15;" onclick="fileDownload('<c:out value="${rs.attachFileId}"/>', 'Y')"><c:out value="${rs.attachFileName}"/></td>
								<fmt:parseDate value="${rs.attachCreateDate}" type="DATE" dateStyle="SHORT"  pattern="yyyyMMdd" var="v3"/>
								<fmt:formatDate value="${v3}" pattern="yyyy-MM-dd" var="attachCreateDate"/>
								<td><c:out value="${attachCreateDate}"/></td>
								<td><c:out value="${rs.attachCreatorName}"/></td>
							</tr>
						</c:forEach>
					</c:if>
					<c:if test="${empty list.list}">
						<td colspan="4">등록된 정보가 없습니다.</td>
					</c:if>
				</table>
			</div>
			<div class="paging_area">
						<SCRIPT LANGUAGE="JavaScript">
								document.write( makePageHtml2( 
										<c:out value="${list.valueListInfo.pagingPage}" default="1"/>, 
										10, 
										<c:out value="${list.valueListInfo.totalNumberOfEntries}" default="0"/> , 
										10)
								) ;
						</SCRIPT>
					</div>
		</div>
	</div>
</form>
</body>
</html>	