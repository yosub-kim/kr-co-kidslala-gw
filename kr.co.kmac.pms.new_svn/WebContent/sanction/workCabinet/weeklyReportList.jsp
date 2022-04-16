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
<style>
input[type="checkbox"]:disabled {
  background: #dddddd;
}
</style>
<script type="text/javascript">
<%-- 개별 스크립트 영역 --%>
function selectWork(workId) {
	document.myWorkListForm.action = "/action/WeeklyReportAction.do?mode=getWeeklyReportByWorkId&workId="+workId;
	document.myWorkListForm.target = "";		
	document.myWorkListForm.submit();
}
function goPage(next_page) {
	document.myWorkListForm.pageNo.value = next_page ;
	document.myWorkListForm.target = "";		
	document.myWorkListForm.action = "/action/WeeklyReportCabinetAction.do?mode=getMyWeeklyReportList";
	document.myWorkListForm.submit();
}
function doSearch() {
	document.myWorkListForm.projectName.value=document.form1.projectName.value;
	document.myWorkListForm.writerName.value=document.form1.writerName.value;
	document.myWorkListForm.intMonth.value=document.form1.selectedMonth.value;
	document.myWorkListForm.intWeekOfMonth.value=document.form1.selectedWeekOfMonth.value;
	document.myWorkListForm.target = "";		
	document.myWorkListForm.action = "/action/WeeklyReportCabinetAction.do?mode=getMyWeeklyReportList";
	document.myWorkListForm.submit();
}
function removeProjectReport2() {
		var ActionURL = "/action/WeeklyReportAction.do?mode=removeProjectReportTask2";
		/* var sFrm = document.forms["weeklyReportForm"]; */
		var chkBoxEls = $$('input[name="weeklyReportCheck"]');
		
		var assignProjectCode = $$('input[name="assignProjectCode"]');
		var assignYear = $$('input[name="assignYear"]');
		var assignMonth = $$('input[name="assignMonth"]');
		var assignWeekOfMonth = $$('input[name="assignWeekOfMonth"]');
		var assignId = $$('input[name="assignId"]');
		
		if(confirm("삭제 하시겠습니까?")) {
			for(var i=0; i<chkBoxEls.length; i++){
				if(chkBoxEls[i].checked){
					var chkProjectCode = assignProjectCode[i].value;
					var chkAssignYear = assignYear[i].value;
					var chkAssignMonth = assignMonth[i].value;
					var chkAssignWeekOfMonth = assignWeekOfMonth[i].value;
					var chkAssignId = assignId[i].value;
					var status = AjaxRequest.post(
							{	'url': "/action/WeeklyReportAction.do?mode=removeProjectReportTask2",
								'parameters' : { "assignId": chkAssignId, "projectCode": chkProjectCode, "assignYear": chkAssignYear, "assignMonth": chkAssignMonth, "assignWeekOfMonth": chkAssignWeekOfMonth },
								'onSuccess':function(obj){
									var res = eval('(' + obj.responseText + ')');
								},
								'onLoading':function(obj){},
								'onError':function(obj){
									alert("삭제 오류 발생");
								}
							}
					);
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
				<input type="hidden" name="intMonth" id="intMonth" value="<c:out value="${intMonth}"/>">
				<input type="hidden" name="intWeekOfMonth" id="intWeekOfMonth" value="<c:out value="${intWeekOfMonth}"/>">
			</div>
		</form>
		
		<!-- location -->
		<div class="location">
			<p class="menu_title">내 주간진척관리</p>
			<ul>
				<li class="home">HOME</li>
				<li>내 주간진척관리</li>
			</ul>
		</div>
		<!-- // location -->
	<div class="contents sub"><!-- 서브 페이지 .sub -->
	<!-- 검색 영역 -->
	<form name="form1" method="post">
		<!-- 검색 창 -->					
		<div class="box">
			<div class="search_box total">
				<div class="select_group">
					<div class="select_box">
						<div class="label_group">
							<p>작성 주차</p>
							<div class="select_area">
								<select name="selectedMonth" class="select" style="width: 48%;">
									<option value="" <c:if test="${'' == intMonth }">selected</c:if>>월</option>
									<option value="1" <c:if test="${'1' == intMonth }">selected</c:if>>1</option>
									<option value="2" <c:if test="${'2' == intMonth }">selected</c:if>>2</option>
									<option value="3" <c:if test="${'3' == intMonth }">selected</c:if>>3</option>
									<option value="4" <c:if test="${'4' == intMonth }">selected</c:if>>4</option>
									<option value="5" <c:if test="${'5' == intMonth }">selected</c:if>>5</option>
									<option value="6" <c:if test="${'6' == intMonth }">selected</c:if>>6</option>
									<option value="7" <c:if test="${'7' == intMonth }">selected</c:if>>7</option>
									<option value="8" <c:if test="${'8' == intMonth }">selected</c:if>>8</option>
									<option value="9" <c:if test="${'9' == intMonth }">selected</c:if>>9</option>
									<option value="10" <c:if test="${'10' == intMonth }">selected</c:if>>10</option>
									<option value="11" <c:if test="${'11' == intMonth }">selected</c:if>>11</option>
									<option value="12" <c:if test="${'12' == intMonth }">selected</c:if>>12</option>
								</select>
								<select name="selectedWeekOfMonth" class="select" style="width: 48%;" >
									<option value="" <c:if test="${'' == intWeekOfMonth }">selected</c:if>>주차</option>
									<option value="1" <c:if test="${'1' == intWeekOfMonth }">selected</c:if>>1</option>
									<option value="2" <c:if test="${'2' == intWeekOfMonth }">selected</c:if>>2</option>
									<option value="3" <c:if test="${'3' == intWeekOfMonth }">selected</c:if>>3</option>
									<option value="4" <c:if test="${'4' == intWeekOfMonth }">selected</c:if>>4</option>
									<option value="5" <c:if test="${'5' == intWeekOfMonth }">selected</c:if>>5</option>
								</select>				
							</div>
						</div>
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
			
	<form name="weeklyReportForm" method="post" enctype="multipart/form-data">
	<div class="board_box">
		<div class="title_both">
			<div class="h1_area">
				<p class="h1">주간진척관리 현황 <%-- <p>총<span><c:out value="${list.valueListInfo.totalNumberOfEntries}"/></span>건</p> --%></p>
			</div>
			<div class="text-R">
				<p>미 작성 상태인 주간진척관리는 삭제가 가능합니다</p>
				<button type="button" class="btn line btn_pink" onclick="removeProjectReport2()"><i class="mdi mdi-trash-can-outline"></i>삭제</button>					
			</div>
		</div>

		<div class="board_contents">
			<table  id="projectProgressTable" class="tbl-edit td-c"><!-- td 영역이 가운데 정렬 일 경우 td-c -->
					<thead>
						<colgroup>
							<col style="width: 10%">
							<col style="width: 13%">
							<col style="width: *%">
							<col style="width: 13%">
							<col style="width: 13%">
							<col style="width: 13%">
							<col style="width: 13%">
							<!-- <col style="width: 13%"> -->
						</colgroup>
						<tr>
							<th>선택</th>
							<th>상태</th>
							<th>프로젝트 명</th>
							<th>작성 월</th>
							<th>작성 주차</th>
							<th>작성자</th>
							<th>실 작성일</th>
						</tr>
					</thead>
					<tbody id="ListData">
						<c:if test="${!empty list.list}">
							<c:forEach var="rs" items="${list.list}">
								<tr>  
									<td><input type="checkbox" class="btn_check" name="weeklyReportCheck" id="<c:out value="${rs.projectCode}" /><c:out value="${rs.assignYear}" /><c:out value="${rs.assignMonth}" /><c:out value="${rs.assignWeekOfMonth}" />" 
										<c:if test="${rs.level != 'SANCTION_STATE_DRAFT' && rs.level != 'SANCTION_STATE_REJECT_DRAFT'}">disabled</c:if> />
										<label <c:if test="${rs.level != 'SANCTION_STATE_DRAFT' && rs.level != 'SANCTION_STATE_REJECT_DRAFT'}">style="display:none;"</c:if> for="<c:out value="${rs.projectCode}" /><c:out value="${rs.assignYear}" /><c:out value="${rs.assignMonth}" /><c:out value="${rs.assignWeekOfMonth}" />"></label><c:if test="${rs.level != 'SANCTION_STATE_DRAFT' && rs.level != 'SANCTION_STATE_REJECT_DRAFT'}">-</c:if></td>									
									<td><c:choose>
											<c:when test="${rs.level == 'SANCTION_STATE_DRAFT'}">작성</c:when>
											<c:when test="${rs.level == 'SANCTION_STATE_REJECT_DRAFT'}"><span class="bold colore74c3a">반려</span></c:when>
											<c:when test="${rs.level == 'SANCTION_STATE_REVIEW'}">검토</c:when>
											<c:when test="${rs.level == 'SANCTION_STATE_APPROVE'}">승인</c:when>
											<c:when test="${rs.level == 'SANCTION_STATE_COMPLETE'}">완료</c:when>
										</c:choose>
									</td>
									<td class="subject" onclick="selectWork('<c:out value="${rs.id}" />')" style="cursor: pointer;"><p><c:out value="${rs.projectName}" /></p></td>
									<td><c:out value="${rs.assignYear}" />년 <c:out value="${rs.assignMonth}" />월</td>
									<td><c:out value="${rs.assignWeekOfMonth}" /></td>
									<td><c:out value="${rs.draftUserName}" /></td>
									<td><c:if test="${rs.level != 'SANCTION_STATE_DRAFT' && rs.level != 'SANCTION_STATE_REJECT_DRAFT'}"><fmt:formatDate value="${rs.draftDate}" pattern="yyyy-MM-dd" /></c:if></td>
								</tr>
								 <input type="hidden" name="assignId" value="<c:out value="${rs.id }" />" />
								 <input type="hidden" name="assignProjectCode" value="<c:out value="${rs.projectCode }" />" />
								 <input type="hidden" name="assignYear" value="<c:out value="${rs.assignYear}" />" />
								 <input type="hidden" name="assignMonth" value="<c:out value="${rs.assignMonth}" />" />
								 <input type="hidden" name="assignWeekOfMonth" value="<c:out value="${rs.assignWeekOfMonth}" />" />
							</c:forEach>
						</c:if>
						<c:if test="${empty list.list}">
							<td colspan="7">검색된 데이터가 없습니다. </td>

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
	</form>
</body>
</html>

