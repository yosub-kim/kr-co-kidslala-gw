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
function selectWork(workId, type) {
	var ActionURL = "/action/WorkCabinetAction.do?mode=getWorkType";
	var status = AjaxRequest.post (
			{	'url':ActionURL,
				'parameters' : { "workTypeId": type },
				'onSuccess':function(obj){
		           	var res = eval('(' + obj.responseText + ')');
		           	var rsObj = res.workType.formUrl;
	           		//document.myWorkListForm.action = rsObj+"&workId="+workId;
					//document.myWorkListForm.target = "";		
					//document.myWorkListForm.submit();
					document.location.href = rsObj+"&workId="+workId;
				},
				'onLoading':function(obj){},
				'onError':function(obj){
					alert("저장할 수 없습니다.");
				}
			}
	); status = null;	
	/*
	if(type == "WORKTYPE_GENERAL_SANCTION"){//일반 전자결재
		document.myWorkListForm.action = "/action/GeneralSanctionAction.do?mode=getGeneralSanctionData&workId="+workId;
	}else if(type == "WORKTYPE_PROJECT_LUANCH_SANCTION"){ //프로젝트 시작품의
		document.myWorkListForm.action = "/action/ProjectLaunchSanctionAction.do?mode=getProjectLaunchSanctionData&workId="+workId;
	}else if(type == "WORKTYPE_PROJECT_OPER_SANCTION"){ //프로젝트 운영품의
		document.myWorkListForm.action = "/action/WorkCabinetAction.do?mode=getMyWorkList";
	}else if(type == "WORKTYPE_PROJECT_BUDJET_SANCTION"){ //프로젝트 예산변경 품의
		document.myWorkListForm.action = "/action/WorkCabinetAction.do?mode=getMyWorkList";
	}else if(type == "WORKTYPE_PROJECT_SCHEDULE_SANCTION"){ //프로젝트 일정변경 품의
		document.myWorkListForm.action = "/action/WorkCabinetAction.do?mode=getMyWorkList";
	}else if(type == "WORKTYPE_PROJECT_MEMBER_SANCTION"){ //프로젝트 인력 변경 품의
		document.myWorkListForm.action = "/action/WorkCabinetAction.do?mode=getMyWorkList";
	}else if(type == "WORKTYPE_PROJECT_SALARY_PU_SANCTION"){ //프로젝트 PU 강사료 픔의
		document.myWorkListForm.action = "/action/WorkCabinetAction.do?mode=getMyWorkList";
	}else if(type == "WORKTYPE_PROJECT_SALARY_BU_SANCTION"){ //프로젝트 BU 강사료 픔의
		document.myWorkListForm.action = "/action/WorkCabinetAction.do?mode=getMyWorkList";
	}*/
}
function goPage(next_page) {
	document.myWorkListForm.pageNo.value = next_page ;
	document.myWorkListForm.target = "";		
	document.myWorkListForm.action = "/action/WorkCabinetAction.do?mode=getMyWorkList";
	document.myWorkListForm.submit();
}
function doSearch() {
	document.myWorkListForm.target = "";		
	document.myWorkListForm.action = "/action/WorkCabinetAction.do?mode=getMyWorkList";
	document.myWorkListForm.submit();
}

</script>
</head>

<body onload="window.parent.leftFrame.countMyPanel();">
		<form name="myWorkListForm" method="post">
			<div style='display: none;'>
				<input type="hidden" name='pageNo'> 
			</div>
		
			<!-- location -->
		<div class="location">
			<p class="menu_title">내 결재함</p>
			<ul>
				<li class="home">HOME</li>
				<li>내 결재함</li>
			</ul>
		</div>
		<!-- // location -->
		 
		<div class="contents sub">
	
		<!--search_box -->
				<div class="box">
					<div class="search_box">
						<div class="select_group">
							<div class="search_input_box">
								<select  name="keyfield" class="select">
									<option value=subject>결재명</option>
								</select>
								<div class="search_input">
									<label for="search"></label> 
									<input type="text" name="keyword" class="textInput_left" value="<c:out value="${keyword}"/>" >
								</div>
							</div>
						</div>
						<div><button type="button" class="btn btn_blue" onclick="location.href='javascript:doSearch();'">검색</button></div>
					</div>
				</div>
					<!-- // search_box -->
			
			<div class="board_box">
				<div class="title_both">
					<div class="h1_area">
						<p class="h1">내 결재함</p>
					</div>
				</div>
		
				<div class="board_contents">
				<table  id="projectProgressTable" class="tbl-edit td-c"><!-- td 영역이 가운데 정렬 일 경우 td-c -->
							<thead>
								<colgroup>
									<col style="width: 15%" />
									<col style="width: 10%" />
									<col style="width: *" />
									<col style="width: 13%" />
									<col style="width: 13%" />
									<col style="width: 13%" />
								</colgroup>
								<tr>
									<th>결재 유형</th>
									<th>결재 상태</th>
									<th>제목</th>
									<th>기안 부서</th>
									<th>기안자</th>
									<th>기안일</th>		
								</tr>
							</thead>
							<tbody id="ListData">
							<c:if test="${!empty list.list}">
								<c:forEach var="rs" items="${list}">
									<tr onmouseover="row_over(this)" onmouseout="row_out(this)" style="cursor: hand;" onclick="selectWork('<c:out value="${rs.id}" />', '<c:out value="${rs.type}" />')">
										<td><span id="approvalName"><c:out value="${rs.approvalName}" /></span></td>
										<td><span id="state"><c:choose>
												<c:when test="${rs.level == 'SANCTION_STATE_DRAFT'}">작성</c:when>
												<c:when test="${rs.level == 'SANCTION_STATE_REJECT_DRAFT'}"><font color="#F290A5">반려&nbsp<span class="ico-progress"></span></font></c:when>
												<c:when test="${rs.level == 'SANCTION_STATE_REVIEW'}">검토</c:when>
												<c:when test="${rs.level == 'SANCTION_STATE_APPROVE'}">승인</c:when>
												<c:when test="${rs.level == 'SANCTION_STATE_CHECK'}">확인</c:when>
												<c:when test="${rs.level == 'SANCTION_STATE_ASSIST1'}">협의1</c:when>
												<c:when test="${rs.level == 'SANCTION_STATE_ASSIST2'}">협의2</c:when>
												<c:when test="${rs.level == 'SANCTION_STATE_ASSIST3'}">협의3</c:when>
												<c:when test="${rs.level == 'SANCTION_STATE_SUPPORT_DRAFT'}">지원실 기안</c:when>
												<c:when test="${rs.level == 'SANCTION_STATE_SUPPORT_REVIEW'}">지원실 검토</c:when>
												<c:when test="${rs.level == 'SANCTION_STATE_SUPPORT_APPROVE'}">지원실 승인</c:when>
												<c:when test="${rs.level == 'SANCTION_STATE_CEO'}">대표이사</c:when>
												<c:when test="${rs.level == 'SANCTION_STATE_COMPLETE'}">완료</c:when>
												<c:when test="${rs.level == 'SANCTION_STATE_RATING'}">작성</c:when>
												<c:when test="${rs.level == 'SANCTION_STATE_ENDRIVIEW_DRAFT'}">작성</c:when>
												<c:when test="${rs.level == 'SANCTION_STATE_ENDRIVIEW_RIVIEW'}">검토</c:when>
												<c:when test="${rs.level == 'SANCTION_STATE_ENDRIVIEW_APPROVE'}">승인</c:when>
												<c:when test="${rs.level == 'SANCTION_STATE_ENDRIVIEW_VERIFICATE'}">검증</c:when>
											</c:choose></span>
										</td>
										<td class="subject" style="padding: 0 0 0 15;">
											<span id="state">
												<c:choose>
													<c:when test="${rs.refWorkId2 == 'A' || rs.refWorkId2 == 'PA' }"><c:out value="${rs.projectName}" /></c:when>
													<c:otherwise><c:out value="${rs.title}" /></c:otherwise>
												</c:choose>
											</span>
										</td>
										<td><span id="draftUserDept"><c:out value="${rs.draftUserDeptName}" /></span></td>
										<td><span id="draftUserId"><c:out value="${rs.draftUserName}" /></span></td>
										<td><span id="draftDate"><fmt:formatDate value="${rs.draftDate}" pattern="yyyy-MM-dd" /></span></td>
									</tr>
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
		</form>
</div>	
</body>
</html>