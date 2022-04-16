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
function goPage(next_page) {
	document.form.pageNo.value = next_page ;
	document.form.target = "";		
	document.form.action = "/action/ProjectReportSearchAction.do?mode=getProjectReportList";
	document.form.submit();
}
function getProjectReportDetail(taskFormTypeId, seq) {

	var url = "/action/ProjectReportSearchAction.do?mode=getProjectReport&projectCode="+$('projectCode').value+"&taskFormTypeId="+taskFormTypeId+"&seq="+seq+"&readonly=true";
	 window.open(url,'projectReport','top=0,left=0,status=no,toolbar=no,scrollbars=yes,width=784,height=510,directories=no,menubar=no');
	 
	//document.form.target = "";		
	//document.form.submit();
}
function doSearch() {
	document.form.target = "";		 
	document.form.action = "/action/ProjectReportSearchAction.do?mode=getProjectReportList";
	document.form.submit();
}
</script>
</head>

<body style="padding-left:2px">
<form name="form" method="post">	
	<input type="hidden" name="pageNo" id="pageNo"> 
	<input type="hidden" name="projectCode" id="projectCode" value="<c:out value="${projectCode}"/>" >

	<table width="740px" cellpadding="0" cellspacing="0">
		<tr>
			<td width="100%" valign="top">
				<table width="100%" cellpadding="0" cellspacing="0"> 

					<!-- sub 타이틀 영역 시작-->  
					<tr>
						<td><h4 class="subTitle mt15 mb5">지도일지</h4></td>
					</tr>
					<!-- sub 타이틀 영역 종료--> 
					<!-- 검색 영역 -->
					<tr>
						<td height="21" align="left" valign="top">
							<table border="0" cellpadding="0" cellspacing="0" width="100%" > 
								<tr>
									<td>						
										<table border="0" width="100%" style="border-collapse: collapse; ">
											<colgroup> 
												<col width="100px">
												<col width="*">
												<col width="100px">
												<col width="201px">
											</colgroup>
											<tr>
												<td class="searchTitle_center">작성기간</td> 
												<td class="searchField_left">
													<script>
														jQuery(function(){jQuery( "#fromDate" ).datepicker({});});
														jQuery(function(){jQuery( "#toDate" ).datepicker({});});
													</script>												
													<input type="text" name="fromDate" id="fromDate" size=10 value="<c:out value="${fromDate}"/>" style="width:90px"> 
													~ <input type="text" name="toDate" id="toDate" size=10 value="<c:out value="${toDate}"/>"  style="width:90px">
												</td>
												<td class="searchTitle_center">작성자</td>
												<td class="searchField_center">
													<input type="text" name="writerName" class="textInput_left" style="width: 100%;" value="<c:out value="${writerName}"/>" onkeypress="if(event.keyCode == 13) doSearch();">
												</td>
											</tr>
										</table>
									</td>
									<td align="center" width="70" class="searchField_center" style="border-left-width: 0px;"
										><a href="#" onclick="doSearch()"><img src="/images/sub/btnSrch.gif"></a></td>
								</tr>
							</table>
						</td>
					
					</tr>					
				 	
				 	
					<tr><td height='10'></td></tr>
					<tr>
						<td width="100%">
							<!-- 페이지 정보, 버튼 -->			
							<div class="btNbox pb15">
								<div class="btNlefTdiv">				
									<img src="/images/sub/line3Blit.gif" alt="">
									<span class="bold colore74c3a"><c:out value="${projectReportList.valueListInfo.totalNumberOfEntries}"/></span>
									<span>Total - Page(<span><c:out value="${projectReportList.valueListInfo.pagingPage}"/></span> of <span><c:out value="${projectReportList.valueListInfo.totalNumberOfPages}"/></span>)</span>
								</div>
							</div>
						</td>
					</tr>
					<tr><td height='5'></td></tr> 					
					
					<tr>
						<td>
							<table id="projectReportTable" class="listTable">
								<thead id="projectReportTableHeader">
									<tr> 
										<td width="390px">업무명</td>
										<td width="100px">상태</td>
										<!-- <td width="25px">순번</td>  -->
										<!-- <td width="64px">할당일자</td> -->
										<td width="100px">작성자</td> 
										<td width="150px">작성일</td> 
										<!-- <td width="50px">검토자</td> 
										<td width="80px">검토일</td> 
										<td width="50px">승인자</td>  
										<td width="80px">승인일</td> --> 
									</tr>  
								</thead>									
								<tbody id="projectReportTableBody"> 
									<c:if test="${!empty projectReportList.list}">
										<c:forEach var="rs" items="${projectReportList.list}">
											<tr onclick="getProjectReportDetail('<c:out value="${rs.taskFormTypeId}"/>','<c:out value="${rs.seq}"/>')" style="cursor: hand;">
												<td class="myoverflowC"><c:out value="${rs.taskFormTypeName}"/></td> 
												<td><c:choose>
													<c:when test="${rs.state == 'writer'}">작성</c:when> 
													<c:when test="${rs.state == 'reviewer'}">검토</c:when> 
													<c:when test="${rs.state == 'approver'}">승인</c:when> 
													<c:when test="${rs.state == 'complete'}">완료</c:when>  
													<c:when test="${rs.state == 'reject'}"><font color="red">반려</font></c:when> <c:otherwise></c:otherwise>
												</c:choose></td>
												<%-- <td><c:out value="${rs.seq}"/></td> --%>

												<%-- <fmt:parseDate value="${rs.assignDate}" type="DATE" dateStyle="SHORT"  pattern="yyyyMMdd" var="v1"/>
												<fmt:formatDate value="${v1}" pattern="yyyy-MM-dd" var="assignDate"/>
												<td><c:out value="${assignDate}"/></td> --%>
		
												<td><c:out value="${rs.writerName}"/></td>
												<fmt:parseDate value="${rs.writeDate}" type="DATE" dateStyle="SHORT"  pattern="yyyyMMdd" var="v2"/>
												<fmt:formatDate value="${v2}" pattern="yyyy-MM-dd" var="writeDate"/>
												<td><c:out value="${writeDate}"/></td>
		
												<%-- <td><c:out value="${rs.reviewerName}"/></td>
												<fmt:parseDate value="${rs.revieweDate}" type="DATE" dateStyle="SHORT"  pattern="yyyyMMdd" var="v3"/>
												<fmt:formatDate value="${v3}" pattern="yyyy-MM-dd" var="revieweDate"/>
												<td><c:out value="${revieweDate}"/></td>
		
												<td><c:out value="${rs.approverName}"/></td>
												<fmt:parseDate value="${rs.approveDate}" type="DATE" dateStyle="SHORT"  pattern="yyyyMMdd" var="v4"/>
												<fmt:formatDate value="${v4}" pattern="yyyy-MM-dd" var="approveDate"/>
												<td><c:out value="${approveDate}"/></td> --%>
											</tr>
										</c:forEach>
									</c:if>
									<c:if test="${empty projectReportList.list}">
										<tr>
											<td colspan="10">지도일지가 없습니다.</td>
										</tr>
									</c:if>
								</tbody>
							</table>
						</td>
					</tr>
					<%-- 페이징 영역 시작 --%>
					<tr>
						<td>
							<table width="100%" border="0" cellpadding="0" cellspacing="0">
								<tr height='30'>
									<td width="100%" align="center">
										<SCRIPT LANGUAGE="JavaScript">
											document.write( makePageHtml( 
													<c:out value="${projectReportList.valueListInfo.pagingPage}" default="1"/>, 
													10, 
													<c:out value="${projectReportList.valueListInfo.totalNumberOfEntries}" default="0"/> , 
													10)
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
</body>

</html>					