<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>투입 프로젝트 정보</title>
<%-- 공통js,css include --%>
<%@ include file="/common/include/includeCss.jsp"%>
<%@ include file="/common/include/includeJavaScript.jsp"%>
<script type="text/javascript">
<%-- 개별 스크립트 영역 --%>
function doSearch() {
	var type = document.projectMemberMMPlanForm.type.value;
	document.projectMemberMMPlanForm.target = "";		
	document.projectMemberMMPlanForm.action = "/action/ProjectMemberMMPlanAction.do?mode=getProjectMemberMMPlan&type=" + type;
	document.projectMemberMMPlanForm.submit();
}
function openMMEditPopUp(projectCode) { 
	var ssn = document.getElementById("ssn").value;
	var year = document.getElementById("searchYear").value;
	var month = document.getElementById("searchMonth").value;
	var linkUrl = "/action/ProjectMemberMMPlanAction.do?mode=getProjectMemberMMPlanByProject&ssn=" + ssn + "&projectCode=" + projectCode + "&year=" + year + "&month=" + month;
	var win = new Window('modal_window', {
		className : "dialog",
		title : "프로젝트 투입 정보 변경",
		top : 50,
		left : 150,
		width : 450,
		height : 400,
		zIndex : 150,
		opacity : 1, 
		resizable : true,
		showEffectOptions : {duration : 0.1},
		hideEffectOptions : {duration : 0.1},		
		url : linkUrl
	});
	win.show(true);
	win.setDestroyOnClose();
}
</script> 
</head>

<body>
	<form name="projectMemberMMPlanForm" method="post">
		<div style='display: none;'>
			<input type="hidden" name="ssn" id="ssn" value="<c:out value="${ssn}"/>" />
			<c:set var="myList" value="${wholeMM.list}" />
			<c:set var="mmList" value="${monthlyMM.list}" />
			<input type="hidden" name="type" id="type" value="<c:out value="${type}"/>" />
			<input type="hidden" name="thisYear" id="thisYear" value="<c:out value="${thisYear}"/>" />
			<input type="hidden" name="thisMonth" id="thisMonth" value="<c:out value="${thisMonth}"/>" />
			<input type="hidden" name="userSsn" id="userSsn" value="<c:out value="${userSsn}"/>" />
			<input type="hidden" name="searchYear" id="searchYear" value="<c:out value="${year}"/>" />
			<input type="hidden" name="searchMonth" id="searchMonth" value="<c:out value="${month}"/>" />
		</div>
		<table width="100%" cellpadding="0" cellspacing="0">
			<!-- 타이틀 영역 -->
			<tr>
				<td height="12">
					<% String back = request.getParameter("backButtonYN"); %>
					<jsp:include page="/common/include/includePageMainTitle.jsp" flush="true">
						<jsp:param name="title" value="투입 프로젝트 정보 " />
						<jsp:param name="backButtonYN" value="<%=back %>" />
					</jsp:include>
				</td>
			</tr>
			
			<!-- 검색 영역 -->
			<tr>
				<td height="21" align="left" valign="top">
					<%@ include file="/common/include/searchBox_Header.jsp"%>
					<table border="0" width="100%" style="border-collapse: collapse;">
						<colgroup> 
							<col width="85px" />
							<col width="100px" />
							<col width="85px" />
							<col width="85px" />
							<col width="85px" />
							<col width="95px" />
							<col width="*" />
						</colgroup>
						<tr>
							<td class="searchTitle_center">연도</td>
							<td class="searchField_center">
								<date:year beforeYears="2" afterYears="2" attribute=" name='year' class='selectbox' style='width:100%' " selectedInfo="${year}" />
							</td>
							<td class="searchTitle_center">월</td>
							<td class="searchField_left">
								<date:month hasAll="Y" attribute=" name='month' class='selectbox' style='width:100%' " selectedInfo="${month}" />
							</td>
							<td class="searchTitle_center">상태</td>
							<td class="searchField_left">
								<select id="state" name="state" class="selectbox">
									<option value="" <c:if test="${state == ''}">selected</c:if>>전체</option>
									<option value="3" <c:if test="${state == '3'}">selected</c:if>>진행</option>
									<option value="4" <c:if test="${state == '4'}">selected</c:if>>평가</option>
									<option value="5" <c:if test="${state == '5'}">selected</c:if>>리뷰</option>
									<option value="6"<c:if test="${state == '6'}">selected</c:if>>종료</option>
								</select>
							</td>
							<td class="searchField_left"><input type="text" class="textInput_left" value="<c:out value="${myList[0].name}"/>" readonly="readonly" /></td>
						</tr>
					</table>
					<%@ include file="/common/include/searchBox_Footer.jsp"%>
				</td>
			</tr>
			
			<!-- 본문 리스트 시작 -->			
			<tr>
				<td>
					<table border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td>
								<h4 class="subTitle">
								1.
								<c:choose>
									<c:when test="${state == '3'}">진행</c:when>
									<c:when test="${state == '4'}">평가</c:when>
									<c:when test="${state == '5'}">리뷰</c:when>
									<c:when test="${state == '6'}">종료</c:when>
									<c:otherwise>전체</c:otherwise>
								</c:choose>
								 프로젝트 현황
								</h4>
							</td>
						</tr>
						<tr>
							<td>
								<table class="listTable" style="table-layout: fixed;">
									<thead>
										<tr height="25px">
											<td width="*">프로젝트명</td>
											<td width="60px">Biz 유형</td>
											<td width="80px">품의일자</td>
											<td width="180px">프로젝트 기간</td>
											<td width="90px">조직단위</td>
											<td width="50px">총 투입M/M</td>
										</tr>
									</thead>			
									<tbody id="ListData">
										<c:choose>
											<c:when test="${!empty wholeMM.list}">
												<c:forEach var="rs" items="${wholeMM.list}">
													<tr onmouseover="row_over(this)" onmouseout="row_out(this)">
														<td><c:out value="${rs.projectName}" /></td>
														<td><c:out value="${rs.businessTypeName}" /></td>
														<td>
															<fmt:parseDate value="${rs.sanctionDate}" type="DATE" dateStyle="SHORT"  pattern="yyyyMMdd" var="date"/>
															<fmt:formatDate value="${date}" pattern="yyyy-MM-dd" var="sanctionDate"/>
															<c:out value="${sanctionDate}" />
														</td>
														<td>
															<fmt:parseDate value="${rs.realStartDate}" type="DATE" dateStyle="SHORT"  pattern="yyyyMMdd" var="date"/>
															<fmt:formatDate value="${date}" pattern="yyyy-MM-dd" var="realStartDate"/>
															<c:out value="${realStartDate}" /> ~ 
															<fmt:parseDate value="${rs.realEndDate}" type="DATE" dateStyle="SHORT"  pattern="yyyyMMdd" var="date"/>
															<fmt:formatDate value="${date}" pattern="yyyy-MM-dd" var="realEndDate"/>
															<c:out value="${realEndDate}" />
														</td>
														<td><c:out value="${rs.runningDeptName}" /></td>
														<td><fmt:formatNumber value="${rs.totalMM}" pattern="0.00"/></td>
													</tr>
												</c:forEach>
											</c:when>
											<c:otherwise>
												<tr><td align="center" colspan="6">검색된 데이터가 없습니다.</td></tr>
											</c:otherwise>
										</c:choose>
									</tbody>
								</table>
							</td>
						</tr>
					</table>
				</td>			
			</tr>
			<tr>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td>
					<table border="0" cellspacing="0" cellpadding="0" style="width: 610px">
						<tr>
							<td>
								<h4 class="subTitle">
									2. 투입<c:if test="${type == 'A'}"> M/M</c:if> 정보 (<c:out value="${year}" />년 <c:out value="${month}" />월)
								</h4>
							</td>
						</tr>
						<tr>
							<td>
								<table class="listTable" style="table-layout: fixed;">
									<thead>
										<tr height="25px">
											<td width="*">프로젝트명</td>
											<td width="100px">Biz 유형</td>
											<td width="110x">프로젝트유형</td>
											<td width="75px">투입<c:if test="${type == 'A'}">M/M</c:if></td>
											<td width="40px">유형</td>
										</tr>
									</thead>			
									<tbody id="ListData">
										<c:choose>
											<c:when test="${!empty monthlyMM.list}">
												<c:forEach var="rs" items="${monthlyMM.list}">
													<tr onmouseover="row_over(this)" onmouseout="row_out(this)">
														<td><c:out value="${rs.projectName}" /></td>
														<td><code:code tableName="BUSINESS_TYPE_CODE" code="${rs.businessTypeCode}" /></td>
														<td><code:code tableName="PROJECT_TYPE_CODE" code="${rs.projectTypeCode}" /></td>
														<td>
															<fmt:formatNumber value="${rs.mmValue}" pattern="0.00"/>
															<!-- PM, 경영기획3센터에 투입률 수정권한 부여 -->
															<c:if test="${year eq thisYear && month >= thisMonth && (rs.pmSsn eq userSsn || userDept eq '9262')}"> <a class="btN3fac0c txtbtn" onclick="openMMEditPopUp('<c:out value="${rs.projectCode}" />');" href="#">수정</a></c:if>
														</td>
														<td><c:out value="${rs.mmState}" /></td>
													</tr>
												</c:forEach>
											</c:when>
											<c:otherwise>
												<tr>
													<td align="center" colspan="5">검색된 데이터가 없습니다.</td>
												</tr>
											</c:otherwise>
										</c:choose>
										<tr>
											<td colspan="3" style="background-color:#F2F1EE">합계<c:if test="${type == 'B'}">(M/M)</c:if></td>
											<td style="background-color:#F2F1EE"><fmt:formatNumber value="${mmList[0].totalMMValue}" pattern="0.00"/></td>
											<td style="background-color:#F2F1EE">-</td>
										</tr>
									</tbody>
								</table>
							</td>
						</tr>
					</table>
				</td>			
			</tr> 
		</table>
	
	</form>
</body>
</html>