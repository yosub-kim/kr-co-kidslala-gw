<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>전 직원 스케줄 관리</title>
<%-- 공통js,css include --%>
<%@ include file="/common/include/includeCss.jsp"%>
<%@ include file="/common/include/includeJavaScript.jsp"%>
<script type="text/javascript">
<%-- 개별 스크립트 영역 --%>

function goPage(next_page) {
	document.scheduleManagerForm.pageNo.value = next_page ;
	document.scheduleManagerForm.target = "";		
	document.scheduleManagerForm.action = "/action/ScheduleAction.do?mode=scheduleManagerSelectList_time";
	document.scheduleManagerForm.submit();
}
function doSearch() {
	document.scheduleManagerForm.target = "";		
	document.scheduleManagerForm.action = "/action/ScheduleAction.do?mode=scheduleManagerSelectList_time";
	document.scheduleManagerForm.submit();
}
function schedule_Info(ssn){
	var scheduleInfo;
	var sURL = "/action/ScheduleAction.do?mode=scheduleForm_time";
	var date = new Date().getDate();
	if (date < 10)	date = "0" + date;
	sURL += "&ssn=" + ssn;
	sURL += "&sdate=" + scheduleManagerForm.YY.value + "-" + scheduleManagerForm.MM.value + "-" + date;
	sURL += "&seq=";
	sURL += "&isManager=MANAGER";
	var sFeatures = "width=750, height=400, top=100, left=100,scrollbars=yes";

	scheduleInfo = window.open(sURL, "scheduleInfo", sFeatures);
	scheduleInfo.focus();
}
function goSanctionDetail(ssn, approvalType) {
	var year = document.scheduleManagerForm.YY.value;
	var month = document.scheduleManagerForm.MM.value;
	var url = "/action/SanctiontPresentStateAction.do?mode=getSpecificSanctiontPresentState&year="+year+"&month=" + month+"&ssn="+ssn+"&approvalType="+approvalType;
	window.open(url,'eval','top=0,left=0,status=no,toolbar=no,scrollbars=yes,width=820,height=610,directories=no,menubar=no');
}

</script> 
</head>

<!-- <body leftmargin="15" topmargin="0" style="overflow-x:hidden;overflow-y:hidden">  -->
<body>
<div style="margin: 70 0 0 20 ">
	<form name="scheduleManagerForm" method="post">
		<div style='display: none;'>
			<input type="hidden" name="pageNo" value="<c:out value="${list.valueListInfo.pagingPage}"/>">
			<input type="hidden" name="YY" value="<c:out value="${year}"/>"> 
			<input type="hidden" name="MM" value="<c:out value="${month}"/>"> 
		</div>
		
		<table width="100%" cellpadding="0" cellspacing="0">
			<!-- 타이틀 영역 -->
			<tr>
				<td height="12">
					<jsp:include page="/common/include/includePageMainTitle.jsp" flush="true">
						<jsp:param name="title" value="근무시간(관리자)" />
						<jsp:param name="backButtonYN" value="N" />
					</jsp:include>
				</td>
			</tr>
			
			<!-- 검색 영역 -->
			<tr>
				<td height="21" align="left" valign="top">
					<%@ include file="/common/include/searchBox_Header.jsp"%>
					<table border="0" width="100%" style="border-collapse: collapse;">
						<colgroup> 
							<col width="120px">
							<col width="*">
							<col width="120px">
							<col width="225px"> 
						</colgroup>
						<tr>
							<td class="searchTitle_center">기간</td> 
							<td class="searchField_center">
								<date:year beforeYears="3" afterYears="3" hasAll="Y" attribute=" name='year' class='selectbox' style='width:80px' " selectedInfo="${year}" />년&nbsp;
							 	<date:month hasAll="Y" attribute=" name='month' class='selectbox' style='width:80px' " selectedInfo="${month}" />월								
							</td>
							<td class="searchTitle_center">직군</td> 
							<td class="searchField_center">
								<select name="jobClass" class="selectbox" style="width: 100%;">
									<option value="A" <c:if test="${jobClass == 'A' }" >selected</c:if>>상근(1)</option>
									<option value="B" <c:if test="${jobClass == 'B' }" >selected</c:if>>상근(2)</option>
									<option value="H" <c:if test="${jobClass == 'H' }" >selected</c:if>>AA</option>
									
								</select>
							</td>
						</tr>
						<tr>
							<td class="searchTitle_center">성명</td>
							<td class="searchField_center" colspan="3">
								<input type="text" name="name" value="<c:out value="${name}"/>" class="textInput_left" style="width: 100%;" onkeypress="if(event.keyCode == 13) doSearch();">
							</td>
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
							<td height="35px">
								<!-- 페이지 정보, 버튼 -->			
								<div class="btNbox pb15">
									<div class="btNlefTdiv">				
										<img src="/images/sub/line3Blit.gif" alt="">
										<span class="bold colore74c3a"><c:out value="${list.valueListInfo.totalNumberOfEntries}"/></span>
										<span>Total - Page(<span><c:out value="${list.valueListInfo.pagingPage}"/></span> of <span><c:out value="${list.valueListInfo.totalNumberOfPages}"/></span>)</span>
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td>
								<table class="listTable" style="table-layout: fixed;">
									<thead>
										<tr>
											<td width="160px" rowspan="2">부서</td>
											<td width="120px" rowspan="2">직위</td>
											<td width="150px" rowspan="2">성명</td>
											<td colspan="4"><c:out value="${month}"/>월 스케줄 관리</td>
										</tr>
										<tr>
											<td colspan="2">근무시간 등록</td>
											<td width="70px" colspan="2">근무시간 보기</td>
										</tr>
									</thead>			
									<tbody id="ListData">
										<c:choose>
											<c:when test="${!empty list.list}">
												<c:forEach var="rs" items="${list.list}">
													<tr onmouseover="row_over(this)" onmouseout="row_out(this)">
														<td><c:out value="${rs.deptName}" /></td>
														<td><c:out value="${rs.position}" /></td>
														<td><c:out value="${rs.name}" /></td>

														<td colspan="2">
															<a title="<c:out value="${rs.name}" /> 근무시간 등록" class="btN006bc6" style="padding: 3px 10px" href="javascript:schedule_Info('<c:out value="${rs.ssn}" />')">등록</a>
														</td>
														<td colspan="2">
															<a title="<c:out value="${rs.name}" /> 근무시간 보기" class="btNaaa" style="padding: 3px 10px"  href="/action/ScheduleAction.do?mode=scheduleOfMonth_time&isManager=MANAGER&history=true&chkOption=Y&ssn=<c:out value="${rs.ssn}" />&selectedYear=<c:out value="${year}" />&selectedMonth=<c:out value="${month}" />">보기</a>
														</td>
													</tr>
												</c:forEach>
											</c:when>
											<c:otherwise>
												
											</c:otherwise>
										</c:choose>
										
									</tbody>
								</table>
							</td>
						</tr>
						<%-- 페이징 영역 시작--%>
						<tr>
							<td>
								<table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr height='30'>
										<td width="100%" align="center">
											<SCRIPT LANGUAGE="JavaScript"> 
												document.write( makePageHtml( 
														<c:out value="${list.valueListInfo.pagingPage}" default="1"/>, 
														15, 
														<c:out value="${list.valueListInfo.totalNumberOfEntries}" default="0"/> , 
														15)
												) ;
											</SCRIPT>
										</td>
									</tr>
								</table>									
							</td>
						</tr>
						<%-- 페이징 영역 끝--%>
					</table>
				</td>			
			</tr> 
		</table>
	
	</form>
</div>
</body>
</html>