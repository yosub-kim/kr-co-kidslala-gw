<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>

<html>
<head>
<title>주요 기준지침</title>
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

/*popup layout*/
window.onload=function(){
	layer_open(this, 'pop_register');
}
</script>
</head>
<body>
	<form name="myProjectListForm" method="post">
	
		<div style="padding: 20 20 0 20">
		<!-- <div class="popup_bg"></div> -->
		<div class="fixed_contents sc">
		<div class="popup_inner" style="width:900px; ">
				
			<div class="board_box">
				<div class="title_both">
					<div class="h1_area">
						<p class="term">
							<i class="mdi mdi-file-document-outline"></i>
							<c:out value="${result.boardData.subjectNoTag}" />
						</p>
					</div>
				</div>

				<div class="board_contents sc">

					<!-- Table View-->
					<table class="tbl-view">
						<thead>
							<tr>
								<th>작성자</th>
								<td><c:out value="${result.boardData.writer}" /></th>
								<th>작성일</th>
								<td><fmt:parseDate value="${result.boardData.wtime}" type="DATE" dateStyle="SHORT" pattern="yyyy-MM-dd HH:mm:ss.SSS" var="from" /> 
									<fmt:formatDate value="${from}" pattern="yyyy-MM-dd" var="formatted" /> 
									<c:out value="${formatted}" />
								</td>
							</tr>
							<tr class="file">
								<th>첨부파일</th>
								<td colspan="3">
										<jsp:include page="/common/repository/fileUpload.jsp"><jsp:param value="Y" name="log"  /></jsp:include>
								</td>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td colspan="4" class="text_view_box">
									<p class="text_view">
										<c:out value="${result.boardData.content}" escapeXml="false" />
									</p>
								</td>
							</tr>
						</tbody>
					</table>
					<!-- // Table View-->
				</div>
				<form name="frm" method="post">
					<input type="hidden" name="saveMode"	value="DELETE">
					<input type="hidden" name="bbsId"		value="<c:out value="${bbsId}" />">
					<input type="hidden" name="seq"			value="<c:out value="${result.boardData.seq}" />">
					<input type="hidden" name="jobClass"	value='<%=session.getAttribute("jobClass") %>' />
				</form>
			</div>
		</div>
		</div>
	</div>
</form>
</body>
</html>