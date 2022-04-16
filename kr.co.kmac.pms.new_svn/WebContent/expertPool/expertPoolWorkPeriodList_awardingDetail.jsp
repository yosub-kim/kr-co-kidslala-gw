<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Awarding</title>
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
			<div class="popup_inner tbl-sc" style="width:1200px; ">
			
				<div class="board_box">
					<div class="title_both">
						<div class="h1_area">
							<p class="term">
								<i class="mdi mdi-file-document-outline"></i>
								<c:out value="${year }" />년 최우수 프로젝트
							</p>
						</div>
					</div>
				
					<div class="board_contents sc">
						<!-- Table View-->
						<table class="tbl-edit td-c pd-none">
							<colgroup>
								<!-- <col style="width: 8%" /> -->
								<col style="width: 6%" />
								<col style="width: 8%" />
								<col style="width: 18%" />
								<col style="width: 12%" />
								<col style="width: *%" />
								<col style="width: 12%" />
								<col style="width: 12%" />
							</colgroup>
							<tbody>
								<tr style="background-color: #006699;">
									<!-- <th>no.</th> -->
									<th>월</th>
									<th>훈격</th>
									<th>본부명</th>
									<th>담당PM</th>
									<th>프로젝트명</th>
									<th>수주금액</th>
									<th>비고</th>
								</tr>
								<c:choose>
									<c:when test="${!empty list.list}">
										<c:set var="monthChk" value=""/>
										<c:set var="typeChk" value=""/>
										<c:forEach var="rs" items="${list.list}">
											<tr>
											<c:if test="${rs.month != monthChk}">
												<td rowspan="<c:out value="${rs.monthCnt}"/>" style="text-align: center"><c:out value="${rs.month}" escapeXml="false" /></td>
											</c:if>
											<%-- <c:if test="${ rs.type != typeChk}">
												<td rowspan="<c:out value="${rs.typeCnt }" />"><c:out value="${rs.type }" /></td>
											</c:if> --%>
												<td><c:out value="${rs.type }" /></td>
												<td><c:out value="${rs.deptName }" /></td>
												<td><c:out value="${rs.pmName }" /></td>
												<td style="text-align: left; padding: 0 0 0 10;"><c:out value="${rs.pjtName }" /></td>
												<td style="text-align: right; padding: 0 10 0 0;"><fmt:formatNumber value="${rs.amount }" type="number"/></td>
												<td><c:out value="${rs.contents }" /></td>
											</tr>
											<c:set var="monthChk" value="${rs.month}" />
											<c:set var="typeChk" value="${rs.type}" />
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