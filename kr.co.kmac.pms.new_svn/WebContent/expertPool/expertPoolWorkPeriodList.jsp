<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>

<%
	String companyPosition = request.getParameter("companyPosition");
	String titleMsg = "AA 계약기간";
	if (companyPosition.equals("64GT"))
		titleMsg = "RA 계약기간";
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>RA 현황</title>
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
function saveListToExcel(jobClass,companyPosition) {
	var surl = '/action/ExpertPoolManagerAction.do?mode=saveRestrictExpertPoolListToExcel_ra';
	surl += "&jobClass=" + jobClass;
	surl += "&companyPosition=" + companyPosition;
	document.location = surl;
}
</script>
</head>


<body>
<form name="frm" method="post">
	<div style="display: none">
		<input type="hidden" id="today" name="today" value="<c:out value="${today}" />" />
	</div>
	<div style="padding: 10 10 0 10">
		<!-- <div class="popup_bg"></div> -->
		<div id="pop_register" class="popup_layer pop_register">
		<div class="fixed_contents sc">
			<div class="popup_inner tbl-sc" style="width:900px; ">
			
				<div class="board_box">
					<div class="title_both">
						<div class="h1_area">
							<p class="term">
								<i class="mdi mdi-file-document-outline"></i>
								RA 현황
							</p>
						</div>
						<div class="text-R">
							<button type="button" class="btn line btn_excel" onclick="saveListToExcel('N','64GT');"><i class="mdi mdi-square-edit-outline"></i>EXCEL 다운로드</button>
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
									<th>계약시작</th>
									<th>계약종료</th>
									<th>개인연차</th>
									<th>사용연차</th>
									<th>잔여연차</th>
								</tr>
								<c:choose>
									<c:when test="${!empty list.list}">
										<c:set var="deptNameChk" value=""/>
										<c:forEach var="rs" items="${list.list}">
											<tr onclick="detail('<c:out value="${rs.ssn}" />')" style="cursor: hand;" onmouseover="row_over(this)" onmouseout="row_out(this)">
											<c:if test="${rs.deptName != deptNameChk}">
												<td rowspan="<c:out value="${rs.dept}"/>" style="text-align: center"><c:out value="${rs.deptName}" escapeXml="false" /></td>
											</c:if>
												<td align="center"><c:out value="${rs.name}" /></td>
												<td align="center" <c:if test="${rs.acctExpireDate != '' && rs.acctExpireDate < today}">bgcolor="#ffdfff"</c:if>>
													<c:choose><c:when test="${rs.acctBeginDate == ''}">인사담당자 미입력</c:when><c:otherwise><c:out value="${rs.acctBeginDate}" /></c:otherwise></c:choose>
												</td>
												<td align="center" <c:if test="${rs.acctExpireDate != '' && rs.acctExpireDate < today}">bgcolor="#ffdfff"</c:if>>
													<c:choose><c:when test="${rs.acctExpireDate == ''}">인사담당자 미입력</c:when><c:otherwise><c:out value="${rs.acctExpireDate}" /></c:otherwise></c:choose>
												</td>
														<td alian="center"><c:out value="${rs.restday_1}" /></td>
												<td alian="center"><c:out value="${rs.useday}" /></td>
	
												<td alian="center"><c:out value="${rs.restday}" /></td>
											</tr>
											<c:set var="deptNameChk" value="${rs.deptName}"/>
										</c:forEach>
									</c:when>
									<c:otherwise>
										<tr><td align="center" colspan="8">검색된 데이터가 없습니다.</td></tr>
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
</form>
</body>
</html>