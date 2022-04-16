<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>

<%
	String companyPosition = request.getParameter("companyPosition");
	String titleMsg = "엑스퍼트 현황";
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>엑스퍼트 현황</title>
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
function saveListToExcel() {
	var surl = '/action/ExpertPoolManagerAction.do?mode=saveRestrictExpertPoolListToExcel_expert';
	/* surl += "&name=" + document.frm.name.value; */
	document.location = surl;
}


function viewExpertPoolWorkPeriodList_exp3(){
	self.close();
	var url = "/action/ExpertPoolManagerAction.do?mode=getExpertPoolWorkPeriodList_exp3";
	var sFeatures = "top=140,left=140,width=1020,height=750,resizable=no,scrollbars=yes";
	var detailWin = window.open(url, "ExpertPoolWorkPeriodList_exp", sFeatures);
	detailWin.focus();
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
			<div class="popup_inner tbl-sc" style="width:1000px; ">
			
				<div class="board_box">
					<div class="title_both">
						<div class="h1_area">
							<p class="term">
								<i class="mdi mdi-file-document-outline"></i>
								엑스퍼트 현황
							</p>
						</div>
						<div class="text-R">
							<button type="button" class="btn line btn_excel" onclick="viewExpertPoolWorkPeriodList_exp3();"><i class="mdi mdi-square-edit-outline"></i>90년생 이후</button>
							<button type="button" class="btn line btn_excel" onclick="saveListToExcel();"><i class="mdi mdi-square-edit-outline"></i>EXCEL 다운로드</button>
						</div>
					</div>
					
					<div class="board_contents sc">
						<!-- Table View-->
						<table class="tbl-edit td-c pd-none">
							<colgroup>
								<col style="width: 10%" />
								<col style="width: 15%" />
								<col style="width: 10%" />
								<col style="width: *%" />
								<col style="width: 18%" />
								<col style="width: 10%" />
							</colgroup>
							<tbody>
								<tr>
									<th>성명</th>
									<th>주민등록번호</th>
									<th>등급</th>
									<th>프로젝트 정보</th>
									<th>실행기간</th>
									<th>PM</th>
								</tr>
								<c:choose>
									<c:when test="${!empty list.list}">
										<c:set var="NameChk" value=""/>
										<c:set var="name" value=" "/>
										<c:forEach var="rs" items="${list.list}">
											<tr onclick="detail('<c:out value="${rs.ssn}" />')" style="cursor: hand;" onmouseover="row_over(this)" onmouseout="row_out(this)">
											<td style="text-align: center" <c:if test="${rs.projectName == null}">bgcolor="#ffdfff"</c:if>><c:out value="${rs.name}" escapeXml="false" /></td>
											<td rowspan="<c:out value="${rs.ssnn}"/>" style="text-align: center" <c:if test="${rs.projectName == null}">bgcolor="#ffdfff"</c:if>><c:out value="${rs.uid}" escapeXml="false" /></td>
											<td alian="center">
												<c:choose><c:when test="${rs.resultPosition eq '40EE'}"><c:out value="엑스퍼트"/></c:when><c:when test="${rs.resultPosition eq '41EF'}"><c:out value="엑스퍼트Ⅰ"/></c:when>
												<c:when test="${rs.resultPosition eq '42EG'}"><c:out value="엑스퍼트Ⅱ"/></c:when><c:when test="${rs.resultPosition eq '43EH'}"><c:out value="엑스퍼트Ⅲ"/></c:when>
												<c:when test="${rs.resultPosition eq '44EI'}"><c:out value="엑스퍼트Ⅳ"/></c:when><c:when test="${rs.resultPosition eq ''}"><c:out value="엑스퍼트"/></c:when>
												<c:when test="${rs.resultPosition eq '44EE'}"><c:out value="엑스퍼트"/></c:when>
												<c:otherwise><c:out value="${rs.resultPosition}" /></c:otherwise></c:choose>
											</td>
											<td class="subject">&nbsp&nbsp[<c:out value="${rs.projectCode}" />] <c:out value="${rs.projectName}" /></td>
											<td alian="center">
												<c:choose><c:when test="${rs.realStartDate == null}"> </c:when><c:otherwise><c:out value="${rs.realStartDate}" /> ~ </c:otherwise></c:choose>
												<c:choose><c:when test="${rs.realEndDate == null}"> </c:when><c:otherwise><c:out value="${rs.realEndDate}" /></c:otherwise></c:choose>
											</td>
											<td alian="center"><c:out value="${rs.projectManager}" /></td>
											</tr>
										</c:forEach>
									</c:when>
									<c:otherwise>
										<tr><td align="center" colspan="6">검색된 데이터가 없습니다.</td></tr>
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