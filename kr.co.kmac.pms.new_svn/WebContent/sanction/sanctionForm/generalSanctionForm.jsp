<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>

<%@page import="kr.co.kmac.pms.common.util.DateTime"%>
<%@page import="kr.co.kmac.pms.sanction.general.data.SanctionDoc"%>
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
	function changeApprovalType() {
		if ($("approvalTypeSelect").value == "draft2") {
			alert("진행 중인 프로젝트의 법인카드신청 품의는 \n My Project 메뉴에서 프로젝트 관련 품의 항목 중 \n '법인카드신청' 을 선택하여 진행하시기 바랍니다. ");
		}
		document.generalSanctionForm.approvalType.value = $("approvalTypeSelect").value;
		document.generalSanctionForm.target = "";
		if($('isReSanction').value == 'true'){
			document.generalSanctionForm.action = "/action/GeneralSanctionAction.do?mode=loadFormGeneralForReSanction";
		}else if($('callCenterSanction').value == 'true'){
			document.generalSanctionForm.action = "/action/GeneralSanctionAction.do?mode=loadFormCallCenterSanction";
		}else{
			document.generalSanctionForm.action = "/action/GeneralSanctionAction.do?mode=loadFormGeneralSanction";
		}
		document.generalSanctionForm.submit();
	}

	function checkApprovalType() {
		if($("approvalTypeSelect").value == ""){
			alert('결재 유형을 선택하고 진행하세요');
			return;
		}
	}

	function tempSaveWork(){
		var sFrm = document.forms["generalSanctionForm"];
		var status = AjaxRequest.submit(
				sFrm,
				{	'url': "/action/GeneralSanctionAction.do?mode=saveGeneralSanction",
					'onSuccess':function(obj){
						var res = eval('(' + obj.responseText + ')');
						if(res.result == 'SUCCESS'){alert('임시저장 하였습니다.');}						
					},
					'onLoading':function(obj){},
					'onError':function(obj){
						alert("저장할 수 없습니다.");
					}
				}
		); status = null;				
	}
	
	function draftSave(){
		var ActionURL;
		if($("approvalTypeSelect").value == ""){
			alert('결재 유형을 선택하고 진행하세요');return;
		}
		if($("taskId").value == ""){
			ActionURL = "/action/GeneralSanctionAction.do?mode=draftSaveGeneralSanction";		
		} else {
			ActionURL = "/action/GeneralSanctionAction.do?mode=saveGeneralSanction";		
		}
			
		var sFrm = document.forms["generalSanctionForm"];
		var status = AjaxRequest.submit(
				sFrm, 
				{	'url':ActionURL,
					'onSuccess':function(obj){
						alert('저장하였습니다.');
						if($('state').value == "SANCTION_STATE_SAVE"){
							document.location = "/action/GeneralSanctionAction.do?mode=loadFormGeneralSanction";
						}else{
							document.location = "/action/WorkCabinetAction.do?mode=getMyWorkList";
						}
					},
					'onLoading':function(obj){},
					'onError':function(obj){
						alert("저장할 수 없습니다.");
					}
				}
		); status = null;		
	}
	
	function deleteWork(){
		if(confirm("삭제 하시겠습니까?")) {
			var sFrm = document.forms["generalSanctionForm"];
			var status = AjaxRequest.submit(
					sFrm,
					{	'url': "/action/GeneralSanctionAction.do?mode=deleteGeneralSanction",
						'onSuccess':function(obj){
							var res = eval('(' + obj.responseText + ')');
							if(res.result == 'SUCCESS'){alert('삭제하였습니다.');
								document.generalSanctionForm.target = "";
								document.generalSanctionForm.action = "/action/WorkCabinetAction.do?mode=getMyWorkList";
								document.generalSanctionForm.submit();								
							}		
						},
						'onLoading':function(obj){},
						'onError':function(obj){
							alert("저장할 수 없습니다.");
						}
					}
			);	 
		}
	}
	
	 var doubleSubmitFlag = false;
	 function doubleSubmitCheck(){
		if(doubleSubmitFlag){
			return doubleSubmitFlag;
		}else{
			doubleSubmitFlag = true;
			return false;
	 	}
	}

	function draftRequest(){
		
		var ActionURL;
		if($("subject").value == ""){
			alert('제목을 입력하세요');
			document.generalSanctionForm.subject.focus();
			return;
		}
		if($("content").value == ""){
			alert('내용을 입력하세요');
			document.generalSanctionForm.content.focus();
			return;
		}		  
		if($('state').value == "SANCTION_STATE_DRAFT"){
			if($('registerSsn') == null || $('registerSsn').value == ''){
				alert("기안자 정보가 없습니다.");	return;
			}
			if($('cioSsn') != null && $('cioSsn').value == ''){
				alert("승인자 정보가 없습니다.");	return;
			}
			$('isApproved').value= 'Y';
			if($("taskId").value == ""){
				ActionURL = "/action/GeneralSanctionAction.do?mode=createGeneralSanction";
			}else{
				ActionURL = "/action/GeneralSanctionAction.do?mode=executeGeneralSanction";
			}
		}else{
			ActionURL = "/action/GeneralSanctionAction.do?mode=executeGeneralSanction";
			<c:if test="${sanctionDoc.state != 'SANCTION_STATE_DRAFT' && sanctionDoc.state != 'SANCTION_STATE_REJECT_DRAFT' }">
				if(generalSanctionForm.isApproved[0].checked == false && generalSanctionForm.isApproved[1].checked == false){
					alert('승인여부를 선택하세요');return;
				}
			</c:if>
			
		}
		if(doubleSubmitCheck()) return;
		var sFrm = document.forms["generalSanctionForm"];
		var status = AjaxRequest.submit(
				sFrm, 
				{	'url':ActionURL,
					'onSuccess':function(obj){
						alert('등록하였습니다.');
						if($('state').value == "SANCTION_STATE_DRAFT"){
							document.location = "/action/GeneralSanctionAction.do?mode=loadFormGeneralSanction";
						}else{
							document.location = "/action/WorkCabinetAction.do?mode=getMyWorkList";
						}
					},
					'onLoading':function(obj){},
					'onError':function(obj){
						alert("저장할 수 없습니다.");
					}
				}
		); status = null;		
	}
	
	function print() {
		var printWin;
		var sURL = "/action/GeneralSanctionAction.do?mode=getGeneralSanctionData&isPrint=true";
		sURL += "&seq=" + document.generalSanctionForm.seq.value;
		sURL += "&approvalType=" + document.generalSanctionForm.approvalType.value;
		sURL += "&projectCode=" + document.generalSanctionForm.projectCode.value;
		var sFeather = "top=0,left=0,width=790,height=670,scrollbars=yes,toolbar=no,status=no,directories=no,menubar=no";
		printWin = window.open(sURL, "printWin", sFeather);
		printWin.focus();
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
		}else {
			var n=0;
			var snactionNameList = $$('input[sanctionInfo="name"]');
			var snactionSsnList = $$('input[sanctionInfo="ssn"]');
			var snactionDeptList = $$('input[sanctionInfo="dept"]');
			for (var i = 0; i < snactionNameList.length; i++) {
				try{
					if(levelInfo <= snactionNameList[i].getAttribute("seq")){
						snactionNameList[i].value = expertPoolList[n].name;
						snactionSsnList[i].value = expertPoolList[n].ssn;
						snactionDeptList[i].value = expertPoolList[n].dept;
						n++;
					}
				}catch(e){}
			}
		}
	}

	
	function refTaskRequest(){
		var sFrm = document.forms["generalSanctionForm"];

		var status = AjaxRequest.submit(
				sFrm,
				{	'url': "/action/GeneralSanctionAction.do?mode=executeRefWork",
					'onSuccess':function(obj){
						alert('등록하였습니다.');
						document.location = "/action/WorkCabinetAction.do?mode=getMyRefWorkList";
					},
					'onLoading':function(obj){},
					'onError':function(obj){
						alert("저장할 수 없습니다.");
					}
				}
		); status = null;		
	}
</script>
</head>

<body>
<form name="generalSanctionForm" method="post" enctype="multipart/form-data">
	<div id="hiddneValue" style="display: none;">
		<input type="text" name="taskId" id="taskId" value="<c:out value="${sanctionDoc.taskId}" />" />
		<input type="text" name="approvalType" value="<c:out value="${sanctionDoc.approvalType}" />" />
		<input type="text" name="projectCode" value="<c:out value="${sanctionDoc.projectCode}" />" />
		<input type="text" name="seq" id="seq" value="<c:out value="${sanctionDoc.seq}" />" /> 
		<input type="text" name="state" id="state" value="<c:out value="${sanctionDoc.state}" />" />
		<input type="text" name="isReSanction" id="isReSanction" value="<c:out value="${isReSanction}" />" />
		<input type="text" name="callCenterSanction" id="callCenterSanction" value="<c:out value="${callCenterSanction}" />" />
		<input type="text" name="eduCourseCode" id="eduCourseCode" value="<c:out value="${eduCourseCode}" />" />
	</div>


		<!-- location -->
		<div class="location">
				<p class="menu_title">기안하기</p>
				<ul>
					<li class="home">HOME</li>
					<li>전자결재</li>
					<li>기안하기</li>
			</ul>
		</div>
	<!-- // location -->	
	
		<div class="contents sub"><!-- 서브 페이지 .sub -->
			<div class="fixed_box">
				<div class="title">
					<div class="h1_area">
						<p class="h1"><i class="mdi mdi-file-document-outline"></i>내용 작성</p>	
					</div>
					<div class="btn_area">
						<%-- <c:if test="${readonly && sanctionTemplate.approvalType == 'draft2' && sanctionDoc.state == 'SANCTION_STATE_COMPLETE'}">
							<button type="button" class="btn line btn_grey" onclick="print()"><i class="mdi mdi-printer"></i>인쇄</button>
						</c:if> --%>
						<button type="button" class="btn line btn_grey" onclick="location.href='javascript:history.back();'"><i class="mdi mdi-backburger"></i>목록</button>
					</div>
				</div>	
				
				<div class="fixed_contents sc">
					<!-- sanctionLine area -->
					<%@include file="/sanction/common/sanctionLineInfo.jsp" %>	
					<!-- // sanctionLine area -->
					
					<!-- 기안 내용 -->
					<div class="board_box">
						<div class="title">
							<div class="h1_area">
								<p class="h1">기본 내용</p>
							</div>
						</div>
						<div class="board_contents">
							<table class="tbl-edit">
								<colgroup>
									<col style="width: 13%"/>
									<col style="width: 37%" />
									<col style="width: 13%" />
									<col style="width: 37%" />
								</colgroup>
								<tbody>
									<tr>
										<th>소속</th>
										<td><c:out value="${userDept }" /></td>
										<th>성명 / 직위</th>
										<td><c:out value="${userName }" />  / <c:out value="${companyPositionName }" /></td>
									</tr>
									<tr>
										<th>결재유형</th>
										<td>														
											<select class="select w-100" name="approvalTypeSelect" id="approvalTypeSelect"  onchange="changeApprovalType();">
													<option value="" <c:if test="${sanctionTemplate.approvalType == '' }">selected</c:if>><c:out value="${item.approvalName}"/>결재유형 선택</option>
												<c:forEach var="item" items="${sanctionTemplateList}">
													<option value="<c:out value="${item.approvalType}"/>" <c:if test="${sanctionTemplate.approvalType == item.approvalType}">selected</c:if>><c:out value="${item.approvalName}"/></option>
												</c:forEach>
												<c:if test="${param.readonly eq 'true'}">
													<option value="R" <c:if test="${sanctionTemplate.approvalType == 'R'}">selected</c:if>>기획사업 재품의</option>
													<option value="C" <c:if test="${sanctionTemplate.approvalType == 'C'}">selected</c:if>>기획사업 취소품의</option>
												</c:if>
											</select>
										</td>
										<th>제목</th>
										<td>
											<input type="text" name="subject" id="subject" class="contentInput_left" style="width: 100%" onchange="checkApprovalType()" 
											value="<c:out value="${sanctionDoc.subject}"/>" <c:if test="${readonly}">readonly</c:if>>
										</td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
					<!-- // 기안 내용 -->
					
					<!-- 품의 내용 -->
					<div class="board_box">
						<div class="title_both">
							<div class="h1_area">
								<p class="h1">상세 내용</p>
							</div>
							<c:choose><c:when test="${sanctionDoc.state == 'SANCTION_STATE_DRAFT' }">
								<div class="text-R">
									<c:if test="${sanctionTemplate.approvalType == 'expenseC' && (sanctionDoc.state == 'SANCTION_STATE_DRAFT' || sanctionDoc.state == 'SANCTION_STATE_REJECT_DRAFT') }">
										<button type="button" class="btn line btn_grey" onclick="location.href='https://newpms.kmac.co.kr/servlet/PhotoDownLoadServlet?fileId=c111b683-5299-4daf-b288-dcd27cf318de';"><i class="mdi mdi-square-edit-outline"></i>사업기여 성과급 양식</button>
									</c:if>
									<c:if test="${!readonly}"> 
										<c:if test="${sanctionDoc.state == 'SANCTION_STATE_DRAFT'}">
											<button type="button" class="btn line btn_grey" onclick="draftSave()"><i class="mdi mdi-square-edit-outline"></i>임시 저장</button>
										</c:if>
											<button type="button" class="btn line btn_blue" onclick="draftRequest()"><i class="mdi mdi-book-check-outline"></i>등록 요청</button>
										<c:if test="${sanctionDoc.state != 'SANCTION_STATE_DRAFT'}">
											<!-- <a class="btNaaa txt2btn" href="javascript:showEntrustInfo()">업무위임</a> -->
										</c:if>							
										<c:if test="${(!empty sanctionDoc.taskId && sanctionDoc.state == 'SANCTION_STATE_DRAFT') || sanctionDoc.state == 'SANCTION_STATE_REJECT_DRAFT'}">
											<button type="button" class="btn line btn_pink" onclick="deleteWork()"><i class="mdi mdi-trash-can-outline"></i>업무 삭제</button>
										</c:if>
									</c:if>
									<c:if test="${readonly && isRefWork}"> 
										<button type="button" class="btn line btn_blue" onclick="refTaskRequest()"><i class="mdi mdi-book-check-outline"></i>등록 요청</button>
									</c:if>
									<%-- <c:if test="${readonly && sanctionTemplate.approvalType == 'draft2' && sanctionDoc.state == 'SANCTION_STATE_COMPLETE'}"> 
										<button type="button" class="btn line btn_grey" onclick="print()"><i class="mdi mdi-file-excel-outline"></i>인쇄하기</button>
									</c:if> --%>
								</div>
							</c:when><c:otherwise> </c:otherwise></c:choose>
						</div>
						<div class="board_contents">
							<c:choose>
							<c:when test="${empty sanctionDoc.taskId && sanctionDoc.state == 'SANCTION_STATE_DRAFT'}">
								<textarea name="content" id="content" class="textArea" style="width: 100%; height: 40%;" <c:if test="${readonly}">readonly</c:if>><c:out value="${sanctionTemplate.templateText}" /></textarea>
							</c:when>
							<c:otherwise><textarea name="content" id="content" class="textArea" style="width: 100%; height: 40%;" <c:if test="${readonly}">readonly</c:if>><c:out value="${sanctionDoc.content}" /></textarea></c:otherwise>
							</c:choose>
							</textarea>
							<c:if test="${sanctionTemplate.hasAttach}">
							</br>
							<table class="tbl-edit">
								<colgroup>
									<col style="width: 13%"/>
									<col style="width: 37%" />
									<col style="width: 13%" />
									<col style="width: 37%" />
								</colgroup>
								</br>
								<tr class="file">
									<th><label for="file">첨부파일</label></th>
									<td colspan="3" style="padding: 10 0 0 0 ">
										<jsp:include page="/common/repository/fileUpload.jsp"><jsp:param value="Y" name="log"  /></jsp:include>
									</td>
								</tr>
							</table>
							</c:if>
						</div>
					</div>
					<!-- // 품의 내용 -->
					
					<!-- 결재선택/의견작성 -->
					<c:choose><c:when test="${sanctionDoc.state == 'SANCTION_STATE_DRAFT' }">
						<div style="display:none">
							<div class="board_box">
								<div class="title_both">
									<div class="h1_area">
										<c:choose>
										<c:when test="${!readonly }">
											<p class="h1">결재 선택/의견 작성</p>
										</c:when>
										<c:otherwise>
											<p class="h1">승인/반려 내역</p>
										</c:otherwise>
										</c:choose>
									</div>
									<div class="text-R">
										<c:if test="${sanctionTemplate.approvalType == 'expenseC' && (sanctionDoc.state == 'SANCTION_STATE_DRAFT' || sanctionDoc.state == 'SANCTION_STATE_REJECT_DRAFT') }">
											<button type="button" class="btn line btn_grey" onclick="location.href='https://newpms.kmac.co.kr/servlet/PhotoDownLoadServlet?fileId=c111b683-5299-4daf-b288-dcd27cf318de';"><i class="mdi mdi-square-edit-outline"></i>사업기여 성과급 양식</button>
										</c:if>
										<c:if test="${!readonly}"> 
											<c:if test="${sanctionDoc.state == 'SANCTION_STATE_DRAFT'}">
												<button type="button" class="btn line btn_grey" onclick="draftSave()"><i class="mdi mdi-square-edit-outline"></i>임시 저장</button>
											</c:if>
												<button type="button" class="btn line btn_blue" onclick="draftRequest()"><i class="mdi mdi-book-check-outline"></i>등록 요청</button>
											<c:if test="${sanctionDoc.state != 'SANCTION_STATE_DRAFT'}">
												<!-- <a class="btNaaa txt2btn" href="javascript:showEntrustInfo()">업무위임</a> -->
											</c:if>							
											<c:if test="${(!empty sanctionDoc.taskId && sanctionDoc.state == 'SANCTION_STATE_DRAFT') || sanctionDoc.state == 'SANCTION_STATE_REJECT_DRAFT'}">
												<button type="button" class="btn line btn_pink" onclick="deleteWork()"><i class="mdi mdi-trash-can-outline"></i>업무 삭제</button>
											</c:if>
										</c:if>
										<c:if test="${readonly && isRefWork}"> 
											<button type="button" class="btn line btn_blue" onclick="refTaskRequest()"><i class="mdi mdi-book-check-outline"></i>등록 요청</button>
										</c:if>
										<%-- <c:if test="${readonly && sanctionTemplate.approvalType == 'draft2' && sanctionDoc.state == 'SANCTION_STATE_COMPLETE'}"> 
											<button type="button" class="btn line btn_grey" onclick="print()"><i class="mdi mdi-file-excel-outline"></i>인쇄하기</button>
										</c:if> --%>
									</div>
								</div>
								<div class="board_contents">
									<!-- sanctionApprove -->
									<%@include file="/sanction/common/sanctionApproveInfo.jsp" %>
									<!-- // sanctionApprove -->
								</div>
							</div>
						</div>
					</c:when>
					<c:otherwise>
						<div class="board_box">
								<div class="title_both">
									<div class="h1_area">
										<c:choose>
										<c:when test="${!readonly }">
											<p class="h1">결재 선택/의견 작성</p>
										</c:when>
										<c:otherwise>
											<p class="h1">승인/반려 내역</p>
										</c:otherwise>
										</c:choose>
									</div>
									<div class="text-R">
										<c:if test="${sanctionTemplate.approvalType == 'expenseC' && (sanctionDoc.state == 'SANCTION_STATE_DRAFT' || sanctionDoc.state == 'SANCTION_STATE_REJECT_DRAFT') }">
											<button type="button" class="btn line btn_grey" onclick="location.href='https://newpms.kmac.co.kr/servlet/PhotoDownLoadServlet?fileId=c111b683-5299-4daf-b288-dcd27cf318de';"><i class="mdi mdi-square-edit-outline"></i>사업기여 성과급 양식</button>
										</c:if>
										<c:if test="${!readonly}"> 
											<c:if test="${sanctionDoc.state == 'SANCTION_STATE_DRAFT'}">
												<button type="button" class="btn line btn_grey" onclick="draftSave()"><i class="mdi mdi-square-edit-outline"></i>임시 저장</button>
											</c:if>
												<button type="button" class="btn line btn_blue" onclick="draftRequest()"><i class="mdi mdi-book-check-outline"></i>등록 요청</button>
											<c:if test="${sanctionDoc.state != 'SANCTION_STATE_DRAFT'}">
												<!-- <a class="btNaaa txt2btn" href="javascript:showEntrustInfo()">업무위임</a> -->
											</c:if>							
											<c:if test="${(!empty sanctionDoc.taskId && sanctionDoc.state == 'SANCTION_STATE_DRAFT') || sanctionDoc.state == 'SANCTION_STATE_REJECT_DRAFT'}">
												<button type="button" class="btn line btn_pink" onclick="deleteWork()"><i class="mdi mdi-trash-can-outline"></i>업무 삭제</button>
											</c:if>
										</c:if>
										<c:if test="${readonly && isRefWork}"> 
											<button type="button" class="btn line btn_blue" onclick="refTaskRequest()"><i class="mdi mdi-book-check-outline"></i>등록 요청</button>
										</c:if>
										<%-- <c:if test="${readonly && sanctionTemplate.approvalType == 'draft2' && sanctionDoc.state == 'SANCTION_STATE_COMPLETE'}"> 
											<button type="button" class="btn line btn_grey" onclick="print()"><i class="mdi mdi-file-excel-outline"></i>인쇄하기</button>
										</c:if> --%>
									</div>
								</div>
								<div class="board_contents">
									<!-- sanctionApprove -->
									<%@include file="/sanction/common/sanctionApproveInfo.jsp" %>
									<!-- // sanctionApprove -->
								</div>
							</div>
					</c:otherwise></c:choose>
					<!-- // 결재선택/의견작성 -->					
			</div>
		</div>
	</div>
		
</form>
</body>
</html>
 			<%-- <%@include file="/sanction/common/sanctionLineInfo.jsp" %>			 
						
							
			<!-- sub 타이틀 영역 시작--> 
			<tr>
				<td>
					<h4 class="subTitle">기안 내용</h4>
				</td>
			</tr>
			<!-- sub 타이틀 영역 종료--> 		
										
				
			<tr>
				<td>		
					<table cellpadding="0" cellspacing="0">
						<tr>
							<td width="100" class="detailTableTitle_center">결재 유형</td>
							<td width="665" class="detailTableField_left">
								<select name="approvalTypeSelect" id="approvalTypeSelect" class="selectBoxPopup" style="width: 100%;" onchange="changeApprovalType();" 
									<c:if test="${(sanctionDoc.state != 'SANCTION_STATE_DRAFT') || ((projectCode != null && projectCode != '') && (eduCourseCode == null || eduCourseCode == '')) }">disabled</c:if>>
									<option value="">결재 유형을 선택하세요.</option>
									<c:forEach var="item" items="${sanctionTemplateList}">
										<option value="<c:out value="${item.approvalType}"/>" <c:if test="${sanctionTemplate.approvalType == item.approvalType}">selected</c:if>><c:out value="${item.approvalName}"/></option>
									</c:forEach>
									<c:if test="${param.readonly eq 'true'}">
										<option value="R" <c:if test="${sanctionTemplate.approvalType == 'R'}">selected</c:if>>기획사업 재품의</option>
										<option value="C" <c:if test="${sanctionTemplate.approvalType == 'C'}">selected</c:if>>기획사업 취소품의</option>
									</c:if>
								</select>
							</td>
						</tr>
						<c:if test="${projectCode != null && projectCode != ''}">
							<tr>
								<td width="100" class="detailTableTitle_center">프로젝트명</td>
								<td width="665" class="detailTableField_left">[<c:out value="${projectCode}"/>] <c:out value="${projectName}"/></td>
							</tr>
						</c:if>
						<tr>
							<td width="100" class="detailTableTitle_center">제목</td>
							<td width="665" class="detailTableField_left"><input type="text" name="subject" id="subject" class="contentInput_left" style="width: 100%" onchange="checkApprovalType()" 
								value="<c:out value="${sanctionDoc.subject}"/>" <c:if test="${readonly}">readonly</c:if>></td>
						</tr>
						<tr>
							<td width="100" class="detailTableTitle_center">품의 내용</td>
							<td width="665" class="detailTableField_left">
								<textarea name="content" id="content" class="textArea" 
								style="width: 100%; height: 260px;" <c:if test="${readonly}">readonly</c:if>
								><c:choose
									><c:when test="${empty sanctionDoc.taskId && sanctionDoc.state == 'SANCTION_STATE_DRAFT'}"
										><c:out value="${sanctionTemplate.templateText}" /></c:when
										><c:otherwise><c:out value="${sanctionDoc.content}" /></c:otherwise
									></c:choose></textarea>
							</td>
						</tr>
					</table>
				</td>
			</tr>				
			<c:if test="${sanctionTemplate.hasAttach}">
				<jsp:include page="/common/repository/fileUpload.jsp"><jsp:param value="N" name="log"/></jsp:include>
			</c:if>								
					
			<c:if test="${sanctionTemplate.approvalType == 'expenseC' && (sanctionDoc.state == 'SANCTION_STATE_DRAFT' || sanctionDoc.state == 'SANCTION_STATE_REJECT_DRAFT') }">
				<tr>
					<td style="text-align: center; padding-top: 10px;">
						사업기여 성과급 양식을 다운로드 후 성과급 내역을 작성하여 첨부하시기 바랍니다. 
						<a href="http://newpms.kmac.co.kr/servlet/PhotoDownLoadServlet?fileId=c111b683-5299-4daf-b288-dcd27cf318de"><b>▶  다운로드 하기</b></a>
					</td>
				</tr>			
			</c:if>
			
			<%@include file="/sanction/common/sanctionApproveInfo.jsp" %>
		<!-- 버튼부분-->
			<tr>
				<td height="7"></td>
			</tr>
			<c:if test="${!readonly}"> 
				<tr>
					<td>
						<table width="100%" height="40" bgcolor="#F3F3F3" cellpadding="0" cellspacing="0">
							<tr>
								<td>
									<div class="btNbox txtR">
										<c:if test="${sanctionDoc.state == 'SANCTION_STATE_DRAFT'}">
											<a class="btNa0a0a0 txt2btn" href="javascript:draftSave()">임시저장</a>
										</c:if>
										<a class="btNe006bc6 txt2btn" href="javascript:draftRequest()">등록요청</a>
										<c:if test="${sanctionDoc.state != 'SANCTION_STATE_DRAFT'}">
											<a class="btNaaa txt2btn" href="javascript:showEntrustInfo()">업무위임</a>
										</c:if>							
										<c:if test="${(!empty sanctionDoc.taskId && sanctionDoc.state == 'SANCTION_STATE_DRAFT') || sanctionDoc.state == 'SANCTION_STATE_REJECT_DRAFT'}">
											<a class="btNe14f42 txt2btn" href="javascript:deleteWork()">업무삭제</a>
										</c:if>
									</div>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</c:if>
			<c:if test="${readonly && isRefWork}"> 
				<tr>
					<td>
						<table width="100%" height="32" bgcolor="#F3F3F3" cellpadding="0" cellspacing="0">
							<tr>
								<td>
									<p align="right">
										<a href="#" onclick="refTaskRequest()"><img alt="등록요청" 	src="/images/btn_regist_request.jpg" border="0"></a>
									</p>
								</td>
							</tr>
						</table>
					</td>
				</tr>			
			</c:if>
			<c:if test="${readonly && sanctionTemplate.approvalType == 'draft2' && sanctionDoc.state == 'SANCTION_STATE_COMPLETE'}"> 
				<tr>
					<td>
						<table width="100%" height="40" bgcolor="#F3F3F3" cellpadding="0" cellspacing="0">
							<tr>
								<td>
									<div class="btNbox txtR"><a class="btNa0a0a0 txt2btn" href="javascript:print()">인쇄하기</a></div>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</c:if>
		<!-- 버튼종료-->								
		</table>					
		<script type="text/javascript">
		function showEntrustInfo(){
			$('entrustInfo').style.top = document.body.scrollHeight-120;
			$('entrustInfo').style.left = 457;
			$('entrustInfo').show();
		}
		
		function entrustRequest(){
			if($F('entrustUserName') ==""){alert("위임자를 지정하시기 바랍니다.");return;}
			var sFrm = document.forms["generalSanctionForm"];
			var status = AjaxRequest.submit(
					sFrm, 
					{	'url': "/action/GeneralSanctionAction.do?mode=entrustGeneralSanction",
						'onSuccess':function(obj){
							alert('업무를 위임하였습니다.');
							document.location = "/action/WorkCabinetAction.do?mode=getMyWorkList";
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
				<tr align="right">
					<td colspan="2">
						<img alt="위임 실행" src="/images/btn_regist.jpg" onclick="entrustRequest()" style="cursor: hand;"/>
						<img alt="취소" src="/images/btn_close.jpg" onclick="$('entrustInfo').hide()" style="cursor: hand;"/>
					</td>
				</tr>
			</table>
		</div> --%>
