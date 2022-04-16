<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>

<%
	String companyPosition = request.getParameter("companyPosition");
	String titleMsg = "엑스퍼트 현황 (90년생 이후)";
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>엑스퍼트 현황 (90년생 이후)</title>
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

function detail(ssn) {
	document.location.href = "/action/ExpertPoolManagerAction.do?mode=infoview&ssn="+ssn;
}
function saveListToExcel() {
	var surl = '/action/ExpertPoolManagerAction.do?mode=saveRestrictExpertPoolListToExcel_expert';
	/* surl += "&name=" + document.frm.name.value; */
	document.location = surl;
}


function viewExpertPoolWorkPeriodList_exp3(){
	document.location.href = "/action/ExpertPoolManagerAction.do?mode=getExpertPoolWorkPeriodList_exp3";
}

</script>
</head>


<body>
<form name="frm" method="post">
	<div style="display: none">
		<input type="hidden" id="today" name="today" value="<c:out value="${today}" />" />
	</div>
	<div style="padding: 20 20 0 20">
		<!-- <div class="popup_bg"></div> -->
		<div id="pop_register" class="popup_layer pop_register">
		<div class="fixed_contents sc">
			<div class="popup_inner tbl-sc" style="width:880px; ">
			
				<div class="board_box">
					<div class="title_both">
						<div class="h1_area">
							<p class="term">
								<i class="mdi mdi-file-document-outline"></i>
								엑스퍼트 현황 (90년생 이후)
							</p>
						</div>
					</div>
					<div class="board_contents sc">
						<!-- Table View-->
						<table class="tbl-edit td-c pd-none">
							<colgroup>
								<col style="width: 10%" />
								<col style="width: 20%" />
								<col style="width: *%" />
								<col style="width: 15%" />
								<col style="width: 15%" />
							</colgroup>
							<tbody>
								<tr>
									<th>성명</th>
									<th>주민번호</th>
									<th>프로젝트 정보</th>
									<th>부서</th>
									<th>PM</th>
								</tr>
								<c:choose>
										<c:when test="${!empty list.list}">
											<c:set var="NameChk" value=""/>
											<c:set var="name" value=""/>
											<c:forEach var="rs" items="${list.list}">
												<tr<%--  onclick="detail('<c:out value="${rs.ssn}" />')" style="cursor: hand;" onmouseover="row_over(this)" onmouseout="row_out(this)" --%>>
													<c:if test="${rs.name != chk}">
														<td rowspan="<c:out value="${rs.ssnCnt}"/>" alian="center"><c:out value="${rs.name}"/></td>
														<td rowspan="<c:out value="${rs.ssnCnt}"/>" alian="center"><c:out value="${rs.uid }"/></td>
													</c:if>
													<td class="subject"><c:choose><c:when test="${rs.projectCode eq '-' }"></c:when><c:otherwise>&nbsp&nbsp[<c:out value="${rs.projectCode }"/>] <c:out value="${rs.projectName }"/></c:otherwise></c:choose></td>
													<td alian="center"><c:out value="${rs.deptName }"/></td>
													<td alian="center"><c:out value="${rs.pm }"/></td>
												</tr>
												<c:set var="chk" value="${rs.name}"/>
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
</form>
</body>
</html>