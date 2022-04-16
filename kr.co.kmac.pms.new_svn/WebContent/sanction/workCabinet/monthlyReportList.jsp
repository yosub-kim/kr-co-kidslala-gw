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
function selectWork(workId) {
	document.myWorkListForm.action = "/action/MonthlyReportAction.do?mode=getMonthlyReportByWorkId&workId="+workId;
	document.myWorkListForm.target = "";		
	document.myWorkListForm.submit();
}
function goPage(next_page) {
	document.myWorkListForm.pageNo.value = next_page ;
	document.myWorkListForm.target = "";		
	document.myWorkListForm.action = "/action/MonthlyReportCabinetAction.do?mode=getMyMonthlyReportList";
	document.myWorkListForm.submit();
}
function doSearch() {
	document.myWorkListForm.projectName.value=document.form1.projectName.value;
	document.myWorkListForm.writerName.value=document.form1.writerName.value;
	document.myWorkListForm.target = "";		
	document.myWorkListForm.action = "/action/MonthlyReportCabinetAction.do?mode=getMyMonthlyReportList";
	document.myWorkListForm.submit();
}
function removeProjectReport2() {
		var ActionURL = "/action/MonthlyReportAction.do?mode=removeProjectReportTask2";
		/* var sFrm = document.forms["monthlyReportForm"]; */
		var chkBoxEls = $$('input[name="monthlyReportCheck"]');
		
		var assignProjectCode = $$('input[name="assignProjectCode"]');
		var assignYear = $$('input[name="assignYear"]');
		var assignMonth = $$('input[name="assignMonth"]');
		var assignDraftUserId = $$('input[name="assignDraftUserId"]');
		var assignId = $$('input[name="assignId"]');
	
		if(confirm("삭제 하시겠습니까?")) {
			for(var i=0; i<chkBoxEls.length; i++){
				if(chkBoxEls[i].checked){
					var chkProjectCode = assignProjectCode[i].value;
					var chkAssignYear = assignYear[i].value;
					var chkAssignMonth = assignMonth[i].value;
					var chkAssignDraftUserId = assignDraftUserId[i].value;
					var chkAssignId = assignId[i].value;
					
					var status = AjaxRequest.post(
							{	'url':ActionURL,
								'parameters' : { "assignId": chkAssignId, "projectCode": chkProjectCode, "assignYear": chkAssignYear, "assignMonth": chkAssignMonth, "assignDraftUserId": chkAssignDraftUserId },
								'onSuccess':function(obj){
									var res = eval('(' + obj.responseText + ')');
								},
								'onLoading':function(obj){},
								'onError':function(obj){
									alert("저장할 수 없습니다.");
								}
							}
					); status = null;		
				}
			}
			alert("삭제 완료하였습니다.");
			location.reload();
		}
}	
</script>
</head>

<body>
		<form name="myWorkListForm" method="post" style="display: none">
			<div style='display: none;'>
				<input type="hidden" name="pageNo">
				<input type="hidden" name="pageSize" id="pageSize" value="<c:out value="${list.valueListInfo.pagingNumberPer}" default="15"/>">
				<input type="hidden" name="projectName" id="projectName" value="<c:out value="${projectName}"/>">
				<input type="hidden" name="writerName" id="writerName" value="<c:out value="${writerName}"/>">
			</div>
		</form>
		
		<!-- location -->
		<div class="location">
			<p class="menu_title">내 프로젝트 레포트</p>
			<ul>
				<li class="home">HOME</li>
				<li>내 프로젝트 레포트</li>
			</ul>
		</div>
		<!-- // location -->
		<div class="contents sub"><!-- 서브 페이지 .sub -->
		<form name="form1" method="post">
		<div class="box">
			<div class="search_box total">
				<div class="select_group">
					<div class="select_box">
						<div class="label_group">
							<p>프로젝트 명</p>
							<input type="text" name="projectName" placeholder="프로젝트 검색" id="projectName" value="<c:out value="${projectName}"/>" onkeypress="if(event.keyCode == 13) doSearch();">
						</div>
						<div class="label_group">
							<p>작성자 명</p>
							<input type="text" name="writerName" placeholder="작성자 검색" value="<c:out value="${writerName}"/>" onkeypress="if(event.keyCode == 13) doSearch();">
						</div>
					</div>
				</div>
				<div><button type="button" class="btn btn_blue" onclick="doSearch();">검색<i class="mdi mdi-magnify"></i></button></div>
			</div>
		</div>
		</form>


	<div class="board_box">
		<div class="title_both">
			<div class="h1_area">
				<p class="h1">프로젝트 레포트 현황<%-- <p>총<span><c:out value="${list.valueListInfo.totalNumberOfEntries}"/></span>건</p> --%></p>
			</div>
			<div class="text-R">
				<p>미 작성 상태인 프로젝트 레포트는 삭제가 가능합니다</p>
				<button type="button" class="btn line btn_pink" onclick="removeProjectReport2()"><i class="mdi mdi-trash-can-outline"></i>삭제</button>					
			</div>
		</div>

		<div class="board_contents">
			<table  id="projectProgressTable" class="tbl-edit td-c"><!-- td 영역이 가운데 정렬 일 경우 td-c -->
					<thead>
						<colgroup>
							<col style="width: 10%">
							<col style="width: 15%">
							<col style="width: *%">
							<col style="width: 15%">
							<col style="width: 15%">
							<col style="width: 15%">
						</colgroup>
						<tr>
							<th>선택</th>
							<th>상태</th>
							<th>프로젝트 명</th>
							<th>작성 월</th>
							<th>작성자</th>
							<th>실 작성일</th>
						</tr>
					</thead>
					<tbody id="ListData">
						<c:if test="${!empty list.list}">
								<c:forEach var="rs" items="${list.list}">
									<tr>  
										<td><input type="checkbox" class="btn_check" name="monthlyReportCheck" id="<c:out value="${rs.projectCode}" /><c:out value="${rs.assignYear}" /><c:out value="${rs.assignMonth}" /><c:out value="${rs.draftUserId }" />" 
										<c:if test="${rs.level != 'SANCTION_STATE_DRAFT' && rs.level != 'SANCTION_STATE_REJECT_DRAFT'}">disabled</c:if> />
										<label <c:if test="${rs.level != 'SANCTION_STATE_DRAFT' && rs.level != 'SANCTION_STATE_REJECT_DRAFT'}">style="display:none;"</c:if> for="<c:out value="${rs.projectCode}" /><c:out value="${rs.assignYear}" /><c:out value="${rs.assignMonth}" /><c:out value="${rs.draftUserId }" />"></label><c:if test="${rs.level != 'SANCTION_STATE_DRAFT' && rs.level != 'SANCTION_STATE_REJECT_DRAFT'}">-</c:if></td>
										<td><c:choose>
												<c:when test="${rs.level == 'SANCTION_STATE_DRAFT'}">작성</c:when>
												<c:when test="${rs.level == 'SANCTION_STATE_REJECT_DRAFT'}"><span class="bold colore74c3a">반려</span></c:when>
												<c:when test="${rs.level == 'SANCTION_STATE_REVIEW'}">검토</c:when>
												<c:when test="${rs.level == 'SANCTION_STATE_APPROVE'}">승인</c:when>
												<c:when test="${rs.level == 'SANCTION_STATE_COMPLETE'}">완료</c:when>
											</c:choose>
										</td>
										<td class="subject" onclick="selectWork('<c:out value="${rs.id}" />')" style="cursor: pointer;" ><p><c:out value="${rs.projectName}" /></p></td>
										<td><c:out value="${rs.assignYear}" />년 <c:out value="${rs.assignMonth}" />월</td>
										<td><c:out value="${rs.draftUserName}" /></td>
										<td><c:if test="${rs.level != 'SANCTION_STATE_DRAFT' && rs.level != 'SANCTION_STATE_REJECT_DRAFT'}"><span id="draftDate"><fmt:formatDate value="${rs.draftDate}" pattern="yyyy-MM-dd" /></span></c:if></td>
									</tr>
									<input type="hidden" name="assignId" value="<c:out value="${rs.id }" />" />
									<input type="hidden" name="assignProjectCode" value="<c:out value="${rs.projectCode }" />" />
									<input type="hidden" name="assignYear" value="<c:out value="${rs.assignYear}" />" />
									<input type="hidden" name="assignMonth" value="<c:out value="${rs.assignMonth}" />" />
									<input type="hidden" name="assignDraftUserId" value="<c:out value="${rs.draftUserId}" />" />
								</c:forEach>
							</c:if>
							<c:if test="${empty list.list}">
								<td colspan="6">검색된 데이터가 없습니다. </td>

							</c:if>										
						</tbody>
					</table>
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
		</div>
</body>
</html>

