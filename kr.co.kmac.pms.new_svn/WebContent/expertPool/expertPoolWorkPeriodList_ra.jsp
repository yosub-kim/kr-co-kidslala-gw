<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>

<%
	String titleMsg = "연차현황";
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>개인 연차 현황</title>
<%-- 공통js,css include --%>
<%-- <%@ include file="/common/include/includeCss.jsp"%> --%>
<%@ include file="/common/include/includeJavaScript.jsp"%>
<link rel="stylesheet" href="/resources/css/common.css" type="text/css" />
<link rel="stylesheet" href="/resources/css/board.css" type="text/css" />
<link rel="stylesheet" href="/resources/css/selectric.css" type="text/css" />
<link rel="stylesheet" href="/resources/css/dev.css" type="text/css" />
<meta name="viewport" content="width=1600">

<script type="text/javascript">

/*popup layout*/
window.onload=function(){
	layer_open(this, 'pop_register');
}


<%-- 개별 스크립트 영역 --%>
function detail(ssn) {
	document.location.href = "/action/ExpertPoolManagerAction.do?mode=infoview&ssn="+ssn;
}
</script>
</head>


<body>
<form name="frm" method="post">
	<input type="hidden" id="today" name="today" value="<c:out value="${today}" />" />
		<!-- 본문 리스트 시작 -->			
		<div style="padding: 20 20 0 20">
		<!-- <div class="popup_bg"></div> -->
		<div id="pop_register" class="popup_layer pop_register">
		<div class="fixed_contents sc">
			<div class="popup_inner tbl-sc" style="width:900px; ">
			
				<div class="board_box">
					<div class="title_both">
						<div class="h1_area">
							<p class="term">
								<i class="mdi mdi-file-document-outline"></i>
								개인 연차 현황
							</p>
						</div>
					</div>
					
					<div class="board_contents sc">
						<!-- Table View-->
						<table class="tbl-edit td-c pd-none">
							<colgroup>
								<col style="width: *%" />
								<col style="width: 10%" />
								<col style="width: 14%" />
								<col style="width: 14%" />
								<col style="width: 14%" />
								<col style="width: 14%" />
								<col style="width: 14%" />
							</colgroup>
							<tbody>
								<tr>
									<th>부서</th>
									<th>이름</th>
									<th>보유연차</th>
									<th>개인사용연차</th>
									<th>공동사용연차</th>
									<th>사용합계</th>
									<th>잔여연차</th>
								</tr>
								<c:choose>
									<c:when test="${!empty list.list}">
										<c:set var="deptNameChk" value=""/>
										<c:forEach var="rs" items="${list.list}">
											<tr>
												<td align="center"><c:out value="${rs.deptName}" escapeXml="false" /></td>
												<td align="center"><c:out value="${rs.name}" /></td>
										 		<td alian="center"><c:out value="${rs.restday_1}" /></td>
												<td alian="center"><c:out value="${rs.mm}" /></td>
												<td alian="center"><c:out value="${rs.jointUse}" /></td>
												<td alian="center"><c:out value="${rs.useday}" /></td>
												<td alian="center"><c:out value="${rs.restday}" /></td>
										</c:forEach>
									</c:when>
									<c:otherwise>
										<tr><td align="center" colspan="7">검색된 데이터가 없습니다.</td></tr>
									</c:otherwise>
								</c:choose>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
							
	</table>
</form>
</body>
</html>