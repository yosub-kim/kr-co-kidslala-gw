<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<%@ include file="/common/include/includeJavaScript.jsp"%>
<%@page import="kr.co.kmac.pms.common.util.DateTime"%>
<%@page import="kr.co.kmac.pms.sanction.general.data.SanctionDoc"%>
<link rel="stylesheet" href="/resources/css/common.css" type="text/css" />
<link rel="stylesheet" href="/resources/css/board.css" type="text/css" />
<link rel="stylesheet" href="/resources/css/selectric.css" type="text/css" />
<link rel="stylesheet" href="/resources/css/dev.css" type="text/css" />
<meta name="viewport" content="width=1600">
<html>
<head>
<script type="text/javascript">

function tempSaveProjectReport() {
	var attachOutputType = $$('select[name="attachOutputType"]');
	var attachOutputName = $$('input[name="attachOutputName"]');
	for (var i = 0; i < attachOutputType.length; i++) {
		if(attachOutputType[i].value == ''){
			alert((i+1)+"번째 첨부파일 타입을 입력하세요.");
			return;
		}
	}
	for (var i = 0; i < attachOutputName.length; i++) {
		if(attachOutputName[i].value == ''){
			alert((i+1)+"번째 첨부파일 문서명을 입력하세요.");
			return;
		}
	}
		
	var ActionURL = "/action/WeeklyReportAction.do?mode=saveProjectReportTask";
	var sFrm = document.forms["weeklyReportForm"];

	var status = AjaxRequest.submit(
			sFrm,
			{	'url':ActionURL,
				'onSuccess':function(obj){
					alert('저장하였습니다.');
					document.location = "/action/WeeklyReportCabinetAction.do?mode=getMyWeeklyReportList";
				},
				'onLoading':function(obj){},
				'onError':function(obj){
					alert("저장할 수 없습니다.");
				}
			}
	); status = null;		
}	

function removeProjectReport() {
	if(confirm("삭제 하시겠습니까?")) {
		var ActionURL = "/action/WeeklyReportAction.do?mode=removeProjectReportTask";
		var sFrm = document.forms["weeklyReportForm"];
	
		var status = AjaxRequest.submit(
				sFrm,
				{	'url':ActionURL,
					'onSuccess':function(obj){
						alert('삭제하였습니다.');
						document.location = "/action/WeeklyReportCabinetAction.do?mode=getMyWeeklyReportList";
					},
					'onLoading':function(obj){},
					'onError':function(obj){
						alert("저장할 수 없습니다.");
					}
				}
		); status = null;		
	}
}	

function completeProjectReport(appValue) {
	<c:if test="${viewMode != 'SIMPLE'}">
		var attachOutputType = $$('select[name="attachOutputType"]');
		var attachOutputName = $$('input[name="attachOutputName"]');
		for (var i = 0; i < attachOutputType.length; i++) {
			if(attachOutputType[i].value == ''){
				alert((i+1)+"번째 첨부파일 타입을 입력하세요.");
				return;
			}
		}
		for (var i = 0; i < attachOutputName.length; i++) {
			if(attachOutputName[i].value == ''){
				alert((i+1)+"번째 첨부파일 문서명을 입력하세요.");
				return;
			}
		}
	</c:if>
	
	weeklyReportForm.appYN.value = appValue;
	
	var ActionURL = "/action/WeeklyReportAction.do?mode=executeProjectReportTask";
	var sFrm = document.forms["weeklyReportForm"];
	
	/* if($("projectMemberStr").value == "") {
		alert("실행 인력을 등록하세요.");
		document.weeklyReportForm.searchEmp.focus();
		return;
	}
	
	if(!isOpenManpowerPopup){
		alert('투입 일정을 입력하세요');
		return;
	}
	
	if($("realProgress").value == ""){
		   alert('실 진척률을 입력하세요.');
		   document.weeklyReportForm.realProgress.focus();
		   return;
		}
	if($('progressRatio').value < 80 && $("delayReason").value == ""){
		alert('계획대비 진척률이 80% 미만일 경우 공정지연사유 및 대책을 입력하세요.');
		return;
	} */
	
	if($("thisWeekContent").value == ""){
	   alert('금주 진행사항을 입력하세요.');
	   document.weeklyReportForm.thisWeekContent.focus();
	   return;
	}
	if($("nextWeekContent").value == ""){
	   alert('차주 진행계획을 입력하세요.');
	   document.weeklyReportForm.nextWeekContent.focus();
	   return;
	}
	
	/* 진행사항 길이 체크 */
	/* <c:if test="${weeklyReport.state == 'writer'}" >
	
		var str = $("thisWeekContent").value;
		var str_len = str.length;
		
		if(str_len < 50){
			alert('프로젝트 진행사항을 구체적으로 작성해주시기 바랍니다.(50자 이상)');
			return;
		}
		
		var attachOutputType_len = attachOutputType.length;
		
		if(attachOutputType_len < 1){
			alert('산출물 및 관련 파일들을 반드시 첨부해 주시기 바랍니다.');
			return;
		}
	</c:if> */
	
	
	var status = AjaxRequest.submit(
			sFrm,
			{	'url':ActionURL,
				'onSuccess':function(obj){
					alert('등록하였습니다.');
					document.location = "/action/WeeklyReportCabinetAction.do?mode=getMyWeeklyReportList";
				},
				'onLoading':function(obj){},
				'onError':function(obj){
					alert("저장할 수 없습니다.");
				}
			}
	); status = null;		
}

var levelInfo;
function openExpertPoolPopUp(_levelInfo) {
	levelInfo = _levelInfo;
	orgFinder_Open('setSanctionLevelInfo');	
}

function setSanctionLevelInfo(expertPoolList){
	if(levelInfo == "refMember"){
		expertPoolList.each(function(expertPool) {
			$('refMemberSsn').value = $('refMemberSsn').value + ($('refMemberSsn').value.length > 0 ? ", ": "") + expertPool.ssn;
			$('refMemberName').value = $('refMemberName').value + ($('refMemberName').value.length > 0 ? ", ": "") +  expertPool.name; 	
		});
	}else if(levelInfo == "entrust"){
		expertPoolList.each(function(expertPool) {
			$('entrustUserSsn').value = expertPool.ssn;
			$('entrustUserName').value = expertPool.name; 	
		});
	}else{
		var n=0;
		var snactionNameList = $$('input[sanctionInfo="name"]');
		var snactionSsnList = $$('input[sanctionInfo="ssn"]');
		for (var i = 0; i < snactionNameList.length; ++i) {
			try{
				if(levelInfo <= snactionNameList[i].getAttribute("seq")){
					snactionNameList[i].value = expertPoolList[n].name;
					snactionSsnList[i].value = expertPoolList[n].ssn;
					n++;
				}
			}catch(e){}
		}
	}
}

var isOpenManpowerPopup = false;
function openProjecManpowerList(){
	isOpenManpowerPopup = true;
	window.open("/action/ProjectManpowerAction.do?mode=getProjectManpowerList"
					+"&projectCode=<c:out value="${weeklyReport.projectCode}" />"
					+"&viewMode=wreport"
					+"&assignYear=<c:out value="${weeklyReport.assignYear}" />"
					+"&assignMonth=<c:out value="${weeklyReport.assignMonth}" />" 					
					+"&assignWeekOfMonth=<c:out value="${weeklyReport.assignWeekOfMonth}"/>"
					+"&thisWeekFromDate=<c:out value="${weeklyReport.thisWeekFromDate}"/>"
					+"&thisWeekToDate=<c:out value="${weeklyReport.thisWeekToDate}"/>"
					+"&nextWeekFromDate=<c:out value="${weeklyReport.nextWeekFromDate}"/>"
					+"&nextWeekToDate=<c:out value="${weeklyReport.nextWeekToDate}"/>",
			"mpowerlist", "top=100,left=100,width=785,height=450,scrollbars=yes");
}

/*
function openExpertPoolPopUpForProjectMaster(projectMemberElmt) {
	window.open("/action/WeeklyReportAction.do?mode=loadProjectMemberPopup&projectCode=<c:out value="${weeklyReport.projectCode}" />",
				"acct", "top=0,left=0,width=450,height=350,scrollbars=no");	
}
*/

function changeRatio(){
	var res =Math.round($('realProgress').value/$('planProgress').value*100);
	$('progressRatio_txt').innerHTML=res;
	$('progressRatio').value = res;
	if(res < 80){
		$('progressRatio_txt').setStyle({color: 'red'});
	} else {
		$('progressRatio_txt').setStyle({color: 'black'});
	}
}

function resetProjectMemberStr(){
	var spanList = $('projectMemberStr_html').select('span');
	var txt;
	for ( var i=0;i<spanList.length;i++){ 
		txt =+ spanList[i].innerHTML+', ';
	}
	$('projectMemberStr').value = txt.subsring(0, txt.length-1);
}

</script>
</head>

<body>
<form name="weeklyReportForm" method="post" enctype="multipart/form-data">

	<div id="hiddneValue" style="display: none;">
		<input type="text" name="workId" id="workId" value="<c:out value="${weeklyReport.workId}" />" />
		<input type="text" name="appYN" id="appYN" value="" />
		<input type="text" name="projectCode" id="projectCode" value="<c:out value="${weeklyReport.projectCode}" />" />
		<input type="text" name="projectName" id="projectName" value="<c:out value="${weeklyReport.projectName}" />" />
		<input type="text" name="taskFormTypeId" id="taskFormTypeId" value="<c:out value="${weeklyReport.taskFormTypeId}" />" />
		<input type="text" name="taskFormTypeName" id="taskFormTypeName" value="<c:out value="${weeklyReport.taskFormTypeName}" />" />
		
		<input type="text" name="seq" id="seq" value="<c:out value="${weeklyReport.seq}" />" />
		<input type="text" name="workStartDate" id="workStartDate" value="<c:out value="${weeklyReport.projectStartDate}" />" />
		<input type="text" name="workEndDate" id="workEndDate" value="<c:out value="${weeklyReport.projectEndDate}" />" />
		
		<input type="text" name="assignYear" id="assignYear" value="<c:out value="${weeklyReport.assignYear}" />" />
		<input type="text" name="assignMonth" id="assignMonth" value="<c:out value="${weeklyReport.assignMonth}" />" />
		<input type="text" name="assignWeekOfMonth" id="assignWeekOfMonth" value="<c:out value="${weeklyReport.assignWeekOfMonth}" />" />
		<input type="text" name="assignDate" id="assignDate" value="<c:out value="${weeklyReport.assignDate}" />" />
		<input type="text" name="weekOfProject" id="weekOfProject" value="<c:out value="${weeklyReport.weekOfProject}" />" />

		<input type="text" name="attach" id="attach" value="<c:out value="${weeklyReport.attach}" />" />
		<input type="text" name="state" id="state" value="<c:out value="${weeklyReport.state}" />" />
	</div>
	
	
	<c:if test="${!readonly}">
		<div class="location">
			<p class="menu_title">주간진척 작성</p>
			<ul>
				<li class="home">HOME</li>
				<li>주간진척 작성</li>
			</ul>
		</div>
			<!-- // location -->
	
		<div class="contents sub">
			<div class="fixed_box">
				<div class="title">
					<div class="h1_area">
						<p class="h1"><i class="mdi mdi-file-document-outline"></i><c:out value="${weeklyReport.assignMonth}" />월<c:out value="${weeklyReport.assignWeekOfMonth}" />주차 주간진척관리</p>	
					</div>
					<div class="btn_area">
						<button type="button" class="btn line btn_grey" onclick="location.href='javascript:history.back();'"><i class="mdi mdi-backburger"></i>목록</button>
					</div>
				</div>
					
				<div class="fixed_contents sc">
					<div class="board_box">
	</c:if>
				<div class="sign_area">
					<c:if test="${readonly }">
						<div class="board_contents">
						<p class="sign_title"><c:out value="${weeklyReport.assignMonth}" />월<c:out value="${weeklyReport.assignWeekOfMonth}" />주차 주간진척관리</p>
						</div>
					</c:if>
					<c:if test="${!readonly }">
						<p class="sign_title"></p>
					</c:if>

					<div style="display:none">
						<input type="hidden"" sanctionInfo="ssn" seq="1" name="writerName" value="<c:out value="${weeklyReport.writerName}"/>">
						<input type="hidden"" sanctionInfo="ssn" seq="1" name="writerSsn" value="<c:out value="${weeklyReport.writerSsn}"/>">
						<input type="hidden" sanctionInfo="ssn" seq="2" name="reviewerName" value="<c:out value="${weeklyReport.reviewerName}"/>">
						<input type="hidden" sanctionInfo="ssn" seq="2" name="reviewerSsn" value="<c:out value="${weeklyReport.reviewerSsn}"/>">
						<input type="hidden" sanctionInfo="ssn" seq="3" name="approverName" value="<c:out value="${weeklyReport.approverName}"/>">
						<input type="hidden" sanctionInfo="ssn" seq="3" name="approverSsn" value="<c:out value="${weeklyReport.approverSsn}"/>">
						<input type="hidden" name="writeDate" value="<c:out value="${weeklyReport.writeDate}"/>"/>
						<input type="hidden" name="revieweDate" value="<c:out value="${weeklyReport.revieweDate}"/>"/>
						<input type="hidden" name="approveDate" value="<c:out value="${weeklyReport.approveDate}"/>"/>
						<input type="hidden" name="activity" value="<c:out value="${weeklyReport.activity }" />"/>
					</div>
					
					<ul class="sign_box">
						<li class="draft">
							<p class="subject">작성</p>
							<div class="name_group">
							<p class="name"><expertPool:exportname ssn="${weeklyReport.writerSsn}"/></p>
							</div>
							<p class="date"><fmt:parseDate value="${weeklyReport.writeDate}" type="DATE" dateStyle="SHORT"  pattern="yyyyMMdd" var="v1"/><fmt:formatDate value="${v1}" pattern="yyyy-MM-dd" /></p>
						</li>
						<li class="review">
							<p class="subject">검토</p>
							<div class="name_group">
							<p class="name"><expertPool:exportname ssn="${weeklyReport.reviewerSsn}"/></p>
							</div>
							<p class="date"><fmt:parseDate value="${weeklyReport.revieweDate}" type="DATE" dateStyle="SHORT"   pattern="yyyyMMdd" var="v2"/><fmt:formatDate value="${v2}" pattern="yyyy-MM-dd" /></p>
						</li>
						<li class="approved">
							<p class="subject">승인</p>
							<div class="name_group">
							<p class="name"><expertPool:exportname ssn="${weeklyReport.approverSsn}"/></p>
							</div>
							<p class="date"><fmt:parseDate value="${weeklyReport.approveDate}" type="DATE" dateStyle="SHORT"   pattern="yyyyMMdd" var="v3"/><fmt:formatDate value="${v3}" pattern="yyyy-MM-dd" /></p>
						</li>
					</ul>
					
				</div>
				<!-- // sign_area -->
				
				<div class="board_contents">
					<table class="tbl-edit weekly">
						<colgroup>
							<col style="width: 16%"/>
							<col style="width: 42%" />
							<col style="width: 42%" />
						</colgroup>
						<tbody>
							<tr>
								<th>프로젝트</th>
								<td colspan="2">
									<ul class="chek_ui">
										<c:out value="${weeklyReport.projectName}" />
									</ul>
								</td>													
							</tr>
							<!-- activity 선택 -->
							<tr>
								<th>진행 액티비티</th>
								<td colspan="2">
									 <ul class="chek_ui">
										<c:forEach var="rs" items="${activity}">
											<li>
												<input type="radio" name="activity" id="<c:out value="${rs.contentId}" />" class="btn_radio" <c:if test="${readonly}">disabled</c:if>
												value="<c:out value="${rs.contentId }" />" <c:if test="${rs.contentId eq weeklyReport.activity }">checked = "checked"</c:if>>
												<label for="<c:out value="${rs.contentId }"/>"></label><c:out value="${rs.workName }"/>
											</li>
										</c:forEach>
									</ul>
								</td>
							</tr>
							<!-- 투입일정 등록 주석 -->
							<%-- <tr>
								<th>투입일정 입력</th>
								<td colspan="2">
								<div id="projectMemberStr_html"></div>
									<input type="text" value="<c:out value="${weeklyReport.projectMemberStr}"/>" name="projectMemberStr" id="projectMemberStr" style="width: 90%" class="contentInput_left" readonly="readonly" placeholder="각 주차별 투입인력을 넣어주세요.">
								</td>													
							</tr> --%>
						</tbody>
					</table>
				</div>
			
				<div class="board_contents">
					<table class="tbl-edit weekly">
						<colgroup>
							<col style="width: 16%"/>
							<col style="width: 42%" />
							<col style="width: 42%" />
						</colgroup>
						<tbody>
							<tr>
								<th>구분</th>
								<div style="display:none">
									<input type="hidden" value="<c:out value="${weeklyReport.thisWeekFromDate}"/>" name="thisWeekFromDate">
									<input type="hidden" value="<c:out value="${weeklyReport.thisWeekToDate}"/>" name="thisWeekToDate">
									<input type="hidden" value="<c:out value="${weeklyReport.nextWeekFromDate}"/>" name="nextWeekFromDate">
									<input type="hidden" value="<c:out value="${weeklyReport.nextWeekToDate}"/>" name="nextWeekToDate">
								</div>
								<th class="weekly_tit"><p class="blue"><c:out value="${weeklyReport.assignWeekOfMonth}"/>주차 진척현황<span>(<fmt:parseDate value="${weeklyReport.thisWeekFromDate}" type="DATE" dateStyle="SHORT"  pattern="yyyyMMdd" var="p11"/><fmt:formatDate value="${p11}" pattern="yyyy-MM-dd" /> ~ <fmt:parseDate value="${weeklyReport.thisWeekToDate}" type="DATE" dateStyle="SHORT"  pattern="yyyyMMdd" var="p12"/><fmt:formatDate value="${p12}" pattern="yyyy-MM-dd" />)</span></p></th>
								<th class="weekly_tit"><p class="pink"><c:out value="${weeklyReport.assignWeekOfMonth+1}"/>주차 진척계획<span>(<fmt:parseDate value="${weeklyReport.nextWeekFromDate}" type="DATE" dateStyle="SHORT"  pattern="yyyyMMdd" var="p21"/><fmt:formatDate value="${p21}" pattern="yyyy-MM-dd" /> ~ <fmt:parseDate value="${weeklyReport.nextWeekToDate}" type="DATE" dateStyle="SHORT"  pattern="yyyyMMdd" var="p22"/><fmt:formatDate value="${p22}" pattern="yyyy-MM-dd" />)</span></p></th>	
							</tr>
							<tr>
								<th>추진 내용</th>
								<td class="text_area">
									<textarea name="thisWeekContent" id="thisWeekContent" style="width: 100%; height: 300px; overflow: auto;" class="textArea" placeholder="※ 프로젝트 진행사항을 명확히 파악할 수 있도록 구체적으로 작성해주시기 바랍니다." class="sc" <c:if test="${readonly}">readonly</c:if>><c:out value="${weeklyReport.thisWeekContent}" /></textarea>
								</td>
								<td class="text_area">
									<textarea name="nextWeekContent" id="nextWeekContent" style="width: 100%; height: 300px; overflow: auto;" class="textArea" placeholder="※ 프로젝트 진행 예정사항을 구체적으로 작성해주시기 바랍니다." class="sc" <c:if test="${readonly}">readonly</c:if>><c:out value="${weeklyReport.nextWeekContent}" /></textarea>
								</td>
							</tr>
							<tr>
								<th>비고 (기타사항)</th>
								<td class="text_area etc" colspan="2">
									<textarea name="delayReason" id="delayReason" style="width: 100%; height: 100px; overflow: auto;" class="sc" <c:if test="${readonly}">readonly</c:if>><c:out value="${weeklyReport.delayReason}" /></textarea>
								</td>
							</tr>
							<tr class="file">
								<th>첨부파일</th>
								<td colspan="2">
									<jsp:include page="/common/repository/fileUpload.jsp"><jsp:param value="Y" name="log"/></jsp:include>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
				
				<c:if test="${!readonly}">			
					<!-- 버튼부분-->
					<div class="btn_area">
						<c:if test="${weeklyReport.state != 'approver' && weeklyReport.state != 'reviewer'}" >
							<button type="button" class="btn btn_blue" onclick="completeProjectReport('Y');">등록요청</button>
						</c:if>
						<c:if test="${weeklyReport.state == 'approver' || weeklyReport.state == 'reviewer'}" >
							<button type="button" class="btn btn_d_blue" onclick="completeProjectReport('Y');">승인</button>
						</c:if>
						<c:if test="${weeklyReport.state != 'approver' && weeklyReport.state != 'reviewer'}" >
							<button type="button" class="btn btn_aqua" onclick="tempSaveProjectReport();">임시저장</button>
						</c:if>
						<%-- <c:if test="${weeklyReport.state == 'approver' || weeklyReport.state == 'reviewer'}" >
							<button type="button" class="btn btn_purple" onclick="showEntrustInfo();">업무위임</button>
						</c:if> --%>
						<c:if test="${weeklyReport.state != 'approver' && weeklyReport.state != 'reviewer'}" >
							<button type="button" class="btn btn_pink" onclick="removeProjectReport();">삭제</button>
						</c:if>
						<c:if test="${weeklyReport.state == 'approver' || weeklyReport.state == 'reviewer'}" >
							<button type="button" class="btn btn_w_grey" onclick="completeProjectReport('N');">반려</button>
						</c:if>
					</div>
					<br>
				<!-- 버튼종료-->
				</c:if>	
				
				<c:if test="${!readonly}">	
					</div>
				</div>
			</div>
		</div>	
	</c:if>			
</form>						
</body>
</html>
