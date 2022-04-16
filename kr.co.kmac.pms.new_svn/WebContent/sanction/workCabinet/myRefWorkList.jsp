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
	           		document.myWorkListForm.action = rsObj+"&refWork=true&workId="+workId;
					document.myWorkListForm.target = "";		
					document.myWorkListForm.submit();
				},
				'onLoading':function(obj){},
				'onError':function(obj){
					alert("저장할 수 없습니다.");
				}
			}
	); status = null;	
}
function goPage(next_page) {
	document.myWorkListForm.pageNo.value = next_page ;
	document.myWorkListForm.target = "";		
	document.myWorkListForm.action = "/action/WorkCabinetAction.do?mode=getMyRefWorkList";
	document.myWorkListForm.submit();
}
function doSearch(){
	document.myWorkListForm.target = "";		
	document.myWorkListForm.action = "/action/WorkCabinetAction.do?mode=getMyRefWorkList";
	document.myWorkListForm.submit();	
}
</script>
</head>

<body onload="window.parent.leftFrame.countMyPanel();">
		<form name="myWorkListForm" method="post">
			<input type="hidden" name='pageNo'> 
			<input type="hidden" name='readonly' value="true"> 
			<input type="hidden" name='isRefWork' value="true"> 
		
			<!-- location -->
			<div class="location">
				<p class="menu_title">내 참조함</p>
				<ul>
					<li class="home">HOME</li>
					<li>내 참조함</li>
				</ul>
			</div>
			<!-- // location -->
		 
		 <div class="contents sub">
	
		<!-- 신규 프로젝트 등록 -->
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
			<div class="board_box">
			<div class="title_both">
				<div class="h1_area">
					<p class="h1">내 참조함</p>
				</div>
			</div>
		
			<div class="board_contents">
				<table  id="projectProgressTable" class="tbl-edit td-c"><!-- td 영역이 가운데 정렬 일 경우 td-c -->
							<thead>
								<colgroup>
									<col style="width: 15%" />
									<col style="width: *" />
									<col style="width: 13%" />
									<col style="width: 13%" />
									<col style="width: 13%" />
								</colgroup>
								<tr>
									<th>결재 유형</th>
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
										<td class="subject" style="padding: 0 0 0 15;"><span id="title"><c:out value="${rs.title}" /></span></a></td>
										<td><span id="draftUserDept"><c:out value="${rs.draftUserDeptName}" /></span></td>
										<td><span id="draftUserId"><c:out value="${rs.draftUserName}" /></span></td>
										<td><span id="draftDate"><fmt:formatDate value="${rs.draftDate}" pattern="yyyy-MM-dd" /></span></td>
									</tr>
								</c:forEach>
							</c:if>
							<c:if test="${empty list.list}">
								<td colspan="5">검색된 데이터가 없습니다. </td>
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
</body>
</html>