<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>

<%@page import="kr.co.kmac.pms.common.util.DateTime"%>
<%@page import="kr.co.kmac.pms.sanction.general.data.SanctionDoc"%>
<html>
<head>
<title>지도일지</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<%-- 공통js,css include --%>
<%@ include file="/common/include/includeCss.jsp"%>
<%@ include file="/common/include/includeJavaScript.jsp"%>

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
		
	var ActionURL = "/action/ProjectReportContentAction.do?mode=saveProjectReportTask";
	var sFrm = document.forms["projectReportForm"];

	var status = AjaxRequest.submit(
			sFrm,
			{	'url':ActionURL,
				'onSuccess':function(obj){
					alert('저장하였습니다.');
					document.location = "/action/ProjectReportCabinetAction.do?mode=getMyProjectReportList";
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
		var ActionURL = "/action/ProjectReportContentAction.do?mode=removeProjectReportTask";
		var sFrm = document.forms["projectReportForm"];
	
		var status = AjaxRequest.submit(
				sFrm,
				{	'url':ActionURL,
					'onSuccess':function(obj){
						alert('삭제하였습니다.');
						document.location = "/action/ProjectReportCabinetAction.do?mode=getMyProjectReportList";
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
	
	projectReportForm.appYN.value = appValue;
	
	var ActionURL = "/action/ProjectReportContentAction.do?mode=executeProjectReportTask";
	var sFrm = document.forms["projectReportForm"];

	if($("workContent").value == ""){
	   alert('금번진행사항을 입력하세요');
	   document.projectReportForm.workContent.focus();
	   return;
	  }
	if($("title").value == ""){
	   alert('제목을 입력하세요');
	   document.projectReportForm.title.focus();
	   return;
	  }
	if($('state').value != 'writer' && $('state').value != 'reject'){
		if(projectReportForm.payAmount[0].checked == false 
				&& projectReportForm.payAmount[1].checked == false
				&& projectReportForm.payAmount[2].checked == false
				&& projectReportForm.payAmount[3].checked == false
				&& projectReportForm.payAmount[4].checked == false){
				alert('Rate를 선택하세요');return;
		}
	}  
	var status = AjaxRequest.submit(
			sFrm,
			{	'url':ActionURL,
				'onSuccess':function(obj){
					alert('등록하였습니다.');
					document.location = "/action/ProjectReportCabinetAction.do?mode=getMyProjectReportList";
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
</script>
</head>

<body>

<form name="projectReportForm" method="post" enctype="multipart/form-data">
	<div id="hiddneValue" style="display: none;">
		<input type="text" name="workId" id="workId" value="<c:out value="${projectReportContent.workId}" />" />
		<input type="text" name="projectCode" id="projectCode" value="<c:out value="${projectReportContent.projectCode}" />" />
		<input type="text" name="projectName" id="projectName" value="<c:out value="${projectReportContent.projectName}" />" />
		<input type="text" name="taskFormTypeId" id="taskFormTypeId" value="<c:out value="${projectReportContent.taskFormTypeId}" />" />
		<input type="text" name="taskFormTypeName" id="taskFormTypeName" value="<c:out value="${projectReportContent.taskFormTypeName}" />" />
		<input type="text" name="seq" id="seq" value="<c:out value="${projectReportContent.seq}" />" />
		<input type="text" name="workStartDate" id="workStartDate" value="<c:out value="${projectReportContent.workStartDate}" />" />
		<input type="text" name="workEndDate" id="workEndDate" value="<c:out value="${projectReportContent.workEndDate}" />" />
		<input type="text" name="assignDate" id="assignDate" value="<c:out value="${projectReportContent.assignDate}" />" />
		<input type="text" name="dueDate" id="dueDate" value="<c:out value="${projectReportContent.dueDate}" />" />
		<input type="text" name="attach" id="attach" value="<c:out value="${projectReportContent.attach}" />" />
		<input type="text" name="state" id="state" value="<c:out value="${projectReportContent.state}" />" />
		<input type="text" name="payYN" id="payYN" value="<c:out value="${projectReportContent.payYN}" />" />
		<input type="text" name="isExceed" id="isExceed" value="<c:out value="${projectReportContent.isExceed}" />" />
		<input type="text" name="appYN" id="payYN" value="" />
	</div>


		<table width="751" cellpadding="0" cellspacing="0">
			<tr>
				<td>
					<jsp:include page="/common/include/includePageMainTitle.jsp" flush="true">
						<jsp:param name="title" value="지도일지" />
						<jsp:param name="backButtonYN" value="Y" />
					</jsp:include>
				</td>
			</tr>
			<tr>
				<td height="10"></td>
			</tr>			
			
			
			<tr>
				<td align="right">		
					<table class="TB"> 
						<tr>
							<td>
								<table class="s258" cellpadding="0" cellspacing="0">
									<tr>
										<td width="84" class='detailTableTitle_center'>작성</td>
										<td width="84" class='detailTableTitle_center'>검토</td>
										<td width="84" class='detailTableTitle_center'>승인</td>
									</tr>
									<tr>
										<td class='detailTableField_center'><expertPool:exportname ssn="${projectReportContent.writerSsn}"/><input type="hidden"" sanctionInfo="ssn" seq="1" name="writerSsn" value="<c:out value="${projectReportContent.writerSsn}"/>"></td>
										<td class='detailTableField_center'><expertPool:exportname ssn="${projectReportContent.reviewerSsn}"/><input type="hidden" sanctionInfo="ssn" seq="2" name="reviewerSsn" value="<c:out value="${projectReportContent.reviewerSsn}"/>"></td>
										<td class='detailTableField_center'><expertPool:exportname ssn="${projectReportContent.approverSsn}"/><input type="hidden" sanctionInfo="ssn" seq="3" name="approverSsn" value="<c:out value="${projectReportContent.approverSsn}"/>"></td>
									</tr>
									<tr>
										<td class='detailTableField_center'>
											<fmt:parseDate value="${projectReportContent.writeDate}" type="DATE" dateStyle="SHORT"  pattern="yyyyMMdd" var="v1"/>
											<fmt:formatDate value="${v1}" pattern="yyyy-MM-dd" />
											<input type="hidden" name="writeDate" value="<c:out value="${projectReportContent.writeDate}"/>"/></td>
										<td class='detailTableField_center'>
											<fmt:parseDate value="${projectReportContent.revieweDate}" type="DATE" dateStyle="SHORT"   pattern="yyyyMMdd" var="v2"/>
											<fmt:formatDate value="${v2}" pattern="yyyy-MM-dd" />
											<input type="hidden" name="revieweDate" value="<c:out value="${projectReportContent.revieweDate}"/>"/></td>
										<td class='detailTableField_center'>
											<fmt:parseDate value="${projectReportContent.approveDate}" type="DATE" dateStyle="SHORT"   pattern="yyyyMMdd" var="v3"/>
											<fmt:formatDate value="${v3}" pattern="yyyy-MM-dd" />
											<input type="hidden" name="approveDate" value="<c:out value="${projectReportContent.approveDate}"/>"/></td>
									</tr>
								</table>
							</td>
						</tr>
					</table>							
				</td>
			</tr>				
	
							
			<!-- sub 타이틀 영역 시작--> 
			<tr>
				<td>
					<h4 class="subTitle mb5">지도일지 내용</h4>
				</td>
			</tr>
			<!-- sub 타이틀 영역 종료--> 	
			<tr>
				<td>		
					<table width="100%" cellpadding="0" cellspacing="0">
						<tr>
							<td width="100" class="detailTableTitle_center">프로젝트명</td>
							<td width="*" class="detailTableField_left">[<c:out value="${projectReportContent.projectName}"/>] <c:out value="${projectReportContent.taskFormTypeName}" /></td>
						</tr>
						<tr>
							<td width="100" class="detailTableTitle_center">지도일</td>
							<td width="*" class="detailTableField_left">	<fmt:parseDate value="${projectReportContent.assignDate}" type="DATE" dateStyle="SHORT"  pattern="yyyyMMdd" var="ass"/>
											<fmt:formatDate value="${ass}" pattern="yyyy-MM-dd" /></td>
						</tr>
						<tr>
							<td width="100" class="detailTableTitle_center">제목</td>
							<td width="*" class="detailTableField_left"><input type="text" name="title" id="title" class="contentInput_left" style="width: 100%" value="<c:out value="${projectReportContent.title}"/>" <c:if test="${readonly}">readonly</c:if>></td>
						</tr>
						<tr>
							<td width="100" class="detailTableTitle_center">금번진행사항</td>
							<td width="*" class="detailTableField_left" style="height:200px"><textarea name="workContent" id="workContent" style="width: 100%; height: 98%; class="textArea" <c:if test="${readonly}">readonly</c:if>><c:out value="${projectReportContent.workContent}" /></textarea></td>
						</tr>
						<tr>
							<td width="100" class="detailTableTitle_center">중요조치사항</td>
							<td width="*" class="detailTableField_left" style="height:60px"><textarea name="impoInstContent" id="impoInstContent" style="width: 100%; height: 96%; " class="textArea" <c:if test="${readonly}">readonly</c:if>><c:out value="${projectReportContent.impoInstContent}" /></textarea></td>
						</tr>
						<tr>
							<td width="100" class="detailTableTitle_center">향후일정</td>
							<td width="*" class="detailTableField_left" style="height:60px"><textarea name="nextPlan" style="width: 100%; height: 96%; " class="textArea" <c:if test="${readonly}">readonly</c:if>><c:out value="${projectReportContent.nextPlan}" /></textarea></td>
						</tr>
						<tr>
							<td width="100" class="detailTableTitle_center">컨설턴트의견</td>
							<td width="*" class="detailTableField_left" style="height:60px"><textarea name="consultantOpinion" style="width: 100%; height: 96%; " class="textArea" <c:if test="${readonly}">readonly</c:if>><c:out value="${projectReportContent.consultantOpinion}" /></textarea></td>
						</tr>
						<tr>
							<td width="100" class="detailTableTitle_center">지원요구사항</td>
							<td width="*" class="detailTableField_left" style="height:60px"><textarea name="requestContent" style="width: 100%; height: 96%; " class="textArea" <c:if test="${readonly}">readonly</c:if>><c:out value="${projectReportContent.requestContent}" /></textarea></td>
						</tr>
					</table>
				</td>
			</tr>				
			<jsp:include page="/common/repository/fileUpload.jsp"><jsp:param value="Y" name="log"/></jsp:include>


			<c:if test="${projectReportContent.state != 'writer'}" >
				<!-- sub 타이틀 영역 시작--> 
				<tr>
					<td>
						<h4 class="subTitle mt15 mb5">검토/승인 의견</h4>
					</td>
				</tr>
				<!-- sub 타이틀 영역 종료--> 	
	
				<tr>
					<td>		
						<table width="100%" cellpadding="0" cellspacing="0">
							<tr>
								<td width="100" class="detailTableTitle_center">검토자 의견</td>
								<td width="*" class="detailTableField_left" style="height:60px"><textarea name="revieweOpinion" style="width: 100%; height: 96%; " class="textArea" <c:if test="${readonly || projectReportContent.state != 'reviewer'}">readonly</c:if>><c:out value="${projectReportContent.revieweOpinion}" /></textarea></td>
							</tr>
							<tr>
								<td width="100" class="detailTableTitle_center">승인자 의견</td>
								<td width="*" class="detailTableField_left" style="height:60px"><textarea name="approveOpinion" style="width: 100%; height: 96%; " class="textArea" <c:if test="${readonly || projectReportContent.state != 'approver'}">readonly</c:if>><c:out value="${projectReportContent.approveOpinion}" /></textarea></td>
							</tr>
						</table>
					</td>
				</tr>												
			</c:if>


			<c:if test="${!readonly && (projectReportContent.state == 'approver' || projectReportContent.state == 'reviewer')}" >
				<!-- sub 타이틀 영역 시작--> 
				<tr>
					<td>
						<h4 class="subTitle mt15 mb5">지도일지 Rate</h4>
					</td>
				</tr>
				<!-- sub 타이틀 영역 종료--> 	

				<tr>
					<td>		
						<table width="100%" cellpadding="0" cellspacing="0">
							<tr>
								<td width="100" class="detailTableTitle_center">Rate 조정</td>
								<td width="*" class="detailTableField_left">
									<input type="radio" name="payAmount" value="0" <c:if test="${projectReportContent.payAmount == '0'}">checked="checked"</c:if>>&nbsp;0&nbsp;&nbsp;
									<input type="radio" name="payAmount" value="0.5" <c:if test="${projectReportContent.payAmount == '0.5'}">checked="checked"</c:if>>&nbsp;0.5&nbsp;&nbsp;
									<input type="radio" name="payAmount" value="1" <c:if test="${projectReportContent.payAmount == '1'}">checked="checked"</c:if>>&nbsp;1.0&nbsp;&nbsp;
									<input type="radio" name="payAmount" value="1.5" <c:if test="${projectReportContent.payAmount == '1.5'}">checked="checked"</c:if>>&nbsp;1.5&nbsp;&nbsp;
									<input type="radio" name="payAmount" value="2" <c:if test="${projectReportContent.payAmount == '2'}">checked="checked"</c:if>>&nbsp;2.0&nbsp;&nbsp;
								</td>
							</tr>
						</table>
					</td>
				</tr>												
			</c:if>

			<c:if test="${!readonly}">			
				<!-- 버튼부분-->
				<tr>
					<td height="15"></td>
				</tr>
				<tr>
					<td>
						<table width="100%" height="36" bgcolor="#F3F3F3" cellpadding="0" cellspacing="0">
							<tr>
								<td>
									<div class="btNbox txtR">
										<c:if test="${projectReportContent.state != 'approver' && projectReportContent.state != 'reviewer'}" >
											<a title="등록요청" class="btNe006bc6 txt4btn" href="#" onclick="completeProjectReport('Y');">등록요청</a>
										</c:if>
										<c:if test="${projectReportContent.state == 'approver' || projectReportContent.state == 'reviewer'}" >
											<a title="승인" class="btNe006bc6 txt4btn" href="#" onclick="completeProjectReport('Y');">승인</a>
										</c:if>
										<c:if test="${projectReportContent.state != 'approver' && projectReportContent.state != 'reviewer'}" >
											<a title="임시저장" class="btN006bc6 txt4btn" href="#" onclick="tempSaveProjectReport()">임시저장</a>
										</c:if>
										<c:if test="${projectReportContent.state == 'approver' || projectReportContent.state == 'reviewer'}" >
											<a title="업무위임" class="btNa0a0a0 txt4btn" href="#" onclick="showEntrustInfo();">업무위임</a>
										</c:if>
										<c:if test="${projectReportContent.state != 'approver' && projectReportContent.state != 'reviewer'}" >
											<a title="업무삭제" class="btNe14f42 txt4btn" href="#" onclick="removeProjectReport();">삭제</a>
										</c:if>
										<c:if test="${projectReportContent.state == 'approver' || projectReportContent.state == 'reviewer'}" >
											<a title="반려" class="btNe14f42 txt4btn" href="#" onclick="javascript:completeProjectReport('N');">반려</a>
										</c:if>
									</div>
								</td>
							</tr>
						</table>								
					</td>
				</tr>
				<!-- 버튼종료-->
			</c:if>						
		</table>					
		<script type="text/javascript">
		function showEntrustInfo(){
			$('entrustInfo').style.top = document.body.scrollHeight/2;
			$('entrustInfo').style.left = 457;
			$('entrustInfo').show();
		}
		
		function entrustRequest(){
			if($F('entrustUserName') ==""){alert("위임자를 지정하시기 바랍니다.");return;}
			var sFrm = document.forms["projectReportForm"];
			var status = AjaxRequest.submit(
					sFrm, 
					{	'url': "/action/ProjectReportContentAction.do?mode=entrustProjectReportTask",
						'onSuccess':function(obj){
							alert('업무를 위임하였습니다.');
							document.location = "/action/ProjectReportCabinetAction.do?mode=getMyProjectReportList";
						},
						'onLoading':function(obj){},
						'onError':function(obj){
							alert("execution Fail");
						}
					}
			); status = null;			
		}	
		</script>
		<div id="entrustInfo" 
			style="position: absolute; background: white; display: none; width: 310px; text-align: center; border-style: solid; border-color: gray; border-width: 2px;">
			<table style="" cellpadding="3" cellspacing="3">
				<tr>
					<td><h4 class="subTitle">업무위임 요청</h4></td>
				</tr>
				<tr>
					<td><table id="delayInfoTable" width="290px">
						<thead id="delayInfoTableHeader">
							<tr>
								<td class="detailTableTitle_center" width="27%">위임자</td>
								<td class="detailTableField_left" width="65%">
								<input type="hidden" id="entrustUserSsn" name="entrustUserSsn">
								<input type="Text" id="entrustUserName" name="entrustUserName" class="textInput_left" readonly></td>
								<td class="detailTableField_left" width="8%">
								<img alt="위임자 선택" src="/images/btn_glass.jpg"  style="cursor: hand;" onclick="openExpertPoolPopUp('entrust')"></td>
							</tr>
						</thead>
						<tbody id="delayInfoTableBody">
						</tbody>
					</table></td>
				</tr>
				<tr>
					<td height="36">
						<div class="btNbox txtR">
							<a title="위임 실행" class="btNe006bc6 txt2btn" href="#" onclick="entrustRequest()">위임</a>
							<a title="위임 취소" class="btNa0a0a0 txt2btn" href="#" onclick="$('entrustInfo').hide()">닫기</a>
						</div>
					</td>
				</tr>
			</table>
		</div>		
	</form>						
		
</body>
</html>
