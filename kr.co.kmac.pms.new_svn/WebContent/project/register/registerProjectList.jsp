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
function openErpPopUp() { 
	var win = new Window('modal_window', {
		className : "dialog",
		title : "프로젝트 예산 선택",
		top : 50,
		left : 50,
		width : 694,
		height : 400,
		zIndex : 150,
		opacity : 1, 
		resizable : true,
		showEffectOptions : {duration : 0.1},
		hideEffectOptions : {duration : 0.1},		
		url : "/action/ErpProjectAction.do?mode=getErpProjectList"
	});
	win.show(true);
	win.setDestroyOnClose();
}

function goProjectDetail(projectCode) {
	document.location.href = "/action/ProjectSearchAction.do"
		+"?mode=getProjectInfoTab&viewMode=register&projectCode="+projectCode;
}

function goPage(next_page) {
	document.registerProjectListForm.pageNo.value = next_page ;
	document.registerProjectListForm.target = "";		
	document.registerProjectListForm.action = "/action/RegisterProjectAction.do?mode=getRegisterProjectList";
	document.registerProjectListForm.submit();
}
function doSearch() {
	document.registerProjectListForm.target = "";
	document.registerProjectListForm.action = "/action/RegisterProjectAction.do?mode=getRegisterProjectList";
	document.registerProjectListForm.submit();
}
function selectErpProject(projectCode) {
	var ActionURL = "/action/ErpProjectAction.do?mode=erpProjectCheck";
	var status = AjaxRequest.post (
			{	'url':ActionURL,
				'parameters' : { "projectCode": projectCode },
				'onSuccess':function(obj){
		           	var res = eval('(' + obj.responseText + ')');
		           	var rsObj = res.isRegistered;
		           	if(rsObj == true){
			           	alert("이미 등록된 프로젝트 입니다. ");
		           	}else{
						$('projectCode').value = projectCode;
						document.erpProjectForm.target = "contentFrame";
						//document.erpProjectForm.action = "/project/projectInfoTab.jsp?viewMode=register&projectCode="+projectCode;
						document.erpProjectForm.action = "/action/ProjectSearchAction.do"
							+"?mode=getProjectInfoTab&viewMode=register&projectCode="+projectCode
						document.erpProjectForm.submit();	
		           	}
				},
				'onLoading':function(obj){},
				'onError':function(obj){
					alert("저장할 수 없습니다.");
				}
			}
	); status = null;	
	
}
function draftRequestAll(){

	var ActionURL = "/action/ProjectLaunchSanctionAction.do?mode=executeProjectLaunchSanction_batch";

	if(confirm("일괄 등록하시겠습니까?") == true){
		AjaxRequest.submit(
				registerProjectListForm,
				{	'url':ActionURL,
					'onSuccess':function(obj){
						alert("등록하였습니다.");
						location.reload(true);					
					},
					'onLoading':function(obj){},
					'onError':function(obj){
						alert("저장할 수 없습니다.");
					}
				}
		);
	}else{
		return false;
	}
}
</script> 
</head>


<body>
	<div style="display: none;">
		<form name="erpProjectForm" method="post">
			<input type="text" name="projectCode" id="projectCode" />
			<input type="text" name="pageNo" />
		</form>
	</div>
	<form name="registerProjectListForm" method="post">
		<div style='display: none;'>
			<input type="hidden" name='pageNo'> 
		</div>
		
		<!-- location -->
		<div class="location">
			<p class="menu_title">프로젝트등록</p>
			<ul>
				<li class="home">HOME</li>
				<li>PMS</li>
				<li>프로젝트 관리</li>
				<li>프로젝트 등록</li>
			</ul>
		</div>
	<!-- // location -->
	<!-- contents sub -->
	<div class="contents sub">
	
		<!-- 신규 프로젝트 등록 -->
		<div class="board_box">
			<div class="title_both">
				<div class="h1_area">
					<p class="h1">신규 프로젝트</p>
				</div>
			</div>
		
			<div class="board_contents">
				<table  id="projectProgressTable" class="tbl-edit td-c"><!-- td 영역이 가운데 정렬 일 경우 td-c -->
							<colgroup>
								<col style="width: 15%"/>
								<col style="width: 15%"/>
								<col style="width: 15%" />
								<col style="width: *" />
								<col style="width: 15%" />
							</colgroup>
							<tr>
								<th>산업 구분</th>
								<th>Biz 구분</th>
								<th>프로젝트 코드</th>
								<th>프로젝트 명</th>
								<th>등록일</th>											
							</tr>
							<c:forEach var="rs" items="${list2.list}">
								<tr onmouseover="row_over(this)" onmouseout="row_out(this)" onclick="selectErpProject('<c:out value="${rs.projectCode}" />')" style="cursor: hand;">
									<td><c:out value="${rs.indsTypeName}" /></td>
									<td><c:out value="${rs.projectTypeName}" /></td>
									<td><c:out value="${rs.projectCode}" /></td>
									<td class="subject"><div style="padding : 0 0 0 20 "><c:out value="${rs.projectName}" /></div></td>
									<td><c:out value="${rs.inputDate}" /></td>
								</tr>
							</c:forEach>
							<c:if test="${empty list2.list}">
								<td align="center" colspan="5">등록중인 프로젝트가 없습니다.</td>
							</c:if>
						</table>
						<div class="paging_area">
						<SCRIPT LANGUAGE="JavaScript">
								document.write( makePageHtml2( 
										<c:out value="${list2.valueListInfo.pagingPage}" default="1"/>, 
										10, 
										<c:out value="${list2.valueListInfo.totalNumberOfEntries}" default="0"/> , 
										10)
								) ;
						</SCRIPT>
						</div>
				</div>
			</div>
		<!-- // 신규 프로젝트 등록 -->
		
		<!-- 프로젝트 등록 -->
			<div class="board_box">
				<div class="title_both">
					<div class="h1_area">
						<p class="h1">등록 프로젝트</p>
					</div>
					<div class="text-R">
					<% if(session.getAttribute("dept").equals("9352") || session.getAttribute("ssn").equals("H003949")){ %>
						<button type="button" class="btn line btn_pink" onclick="draftRequestAll()"><i class="mdi mdi-book-check"></i>공개교육 프로젝트 일괄 등록</button>
					<%} %>
				</div>
				</div>
			
			<div class="board_contents">
				<table  id="projectProgressTable" class="tbl-edit td-c"><!-- td 영역이 가운데 정렬 일 경우 td-c -->
					<colgroup>
						<col style="width: 15%"/>
						<col style="width: 15%"/>
						<col style="width: 15%" />
						<col style="width: *" />
						<col style="width: 15%" />
					</colgroup>
					<tr>
						<th>상태</th>
						<th>구분</th>
						<th>프로젝트 코드</th>
						<th>프로젝트 명</th>
						<th>등록일</th>											
					</tr>
					<c:forEach var="rs" items="${list.list}">
						<tr onmouseover="row_over(this)" onmouseout="row_out(this)" onclick="goProjectDetail('<c:out value="${rs.projectCode}" />')"  style="cursor: hand;">
							<td align="center">
								<c:choose>
									<c:when test="${rs.isReject > 0 }">
										<span class="pink">반려<span class="mdi mdi-check-circle"></span></span>
									</c:when>
									<c:when test="${rs.projectState == 1}">등록중</c:when>
									<c:when test="${rs.projectState == 2}">품의중</c:when>
									<c:otherwise>품의완료</c:otherwise>
								</c:choose>
							</td>
							<td align="center"><c:out value="${rs.businessTypeName}" /></td>
							<td align="center"><c:out value="${rs.projectCode}" /></td>
							<td class="subject"><div style="padding : 0 0 0 20 "><c:out value="${rs.projectName}" /></div></td>
							<td align="center"><c:out value="${rs.createDate}" /></td>
						</tr>
					</c:forEach>
					<c:if test="${empty list.list}">
						<td align="center" colspan="5">등록된 프로젝트가 없습니다.</td>
					</c:if>
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