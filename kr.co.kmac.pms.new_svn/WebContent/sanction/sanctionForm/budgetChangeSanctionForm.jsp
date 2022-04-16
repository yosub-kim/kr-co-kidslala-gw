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
		//if($F('oldEntNo') == '' || $F('newEntNo') == ''){
		//	alert('변경 예산 정보가 없습니다.\n예산관리에서 변경 예산서를 작성한 후 품의하시기 바랍니다.');
		//	return;
		//}		
		if($("subject").value == ""){
		   alert('제목을 입력하세요.');
		   document.budgetChangeSanctionForm.subject.focus();
		   return;
		  }
		if($("content").value == ""){
		   alert('내용을 입력하세요.');
		   document.budgetChangeSanctionForm.content.focus();
		   return;
		  }
		if($('seq').value == '0'){
			ActionURL = "/action/BudgetChangeSanctionAction.do?mode=createBudgetChangeSanction";
		}else{
			ActionURL = "/action/BudgetChangeSanctionAction.do?mode=executeBudgetChangeSanction";
			<c:if test="${sanctionDoc.state != 'SANCTION_STATE_DRAFT' && sanctionDoc.state != 'SANCTION_STATE_REJECT_DRAFT' }">
				if(budgetChangeSanctionForm.isApproved[0].checked == false && budgetChangeSanctionForm.isApproved[1].checked == false){
					alert('승인여부를 선택하세요.');return;
				}
			</c:if>
		}
		if(doubleSubmitCheck()) return;
		var sFrm = document.forms["budgetChangeSanctionForm"];
	
		var status = AjaxRequest.submit(
				sFrm,
				{	'url':ActionURL,
					'onSuccess':function(obj){  //예산 초과 정보 업데이트 
		        	   	var res = eval('(' + obj.responseText + ')');
						if(res.state == "SANCTION_STATE_COMPLETE"){
							AjaxRequest.post (
									{	'url': "/action/ExpenseManagerAction.do?mode=refreshErpDataSpcProject",
										'parameters' : {projectCode: $("projectCode").value },
										'onSuccess':function(obj){},
										'onLoading':function(obj){},
										'onError':function(obj){
											alert("예산 정보 업데이트 중에 에러가 발생하였습니다.");
										}
									}
							);							
						}					
						alert('등록하였습니다.');
						if($('seq').value == '0'){
							document.location = "/action/MyProjectAction.do?mode=getMyProjectList";
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
	
	function tempSaveWork(){
		var sFrm = document.forms["budgetChangeSanctionForm"];
		var status = AjaxRequest.submit(
				sFrm,
				{	'url': "/action/BudgetChangeSanctionAction.do?mode=saveBudgetChangeSanction",
					'onSuccess':function(obj){
						var res = eval('(' + obj.responseText + ')');
						if(res.result == 'SUCCESS'){alert('저장하였습니다.');}						
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
			var sFrm = document.forms["budgetChangeSanctionForm"];
			var status = AjaxRequest.submit(
					sFrm,
					{	'url': "/action/BudgetChangeSanctionAction.do?mode=deleteBudgetChangeSanction",
						'onSuccess':function(obj){
							var res = eval('(' + obj.responseText + ')');
							if(res.result == 'SUCCESS'){alert('삭제하였습니다.');
								document.budgetChangeSanctionForm.target = "";
								document.budgetChangeSanctionForm.action = "/action/WorkCabinetAction.do?mode=getMyWorkList";
								document.budgetChangeSanctionForm.submit();								
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

	function viewBudget(type){
		var budgetUrl = "https://newbudget.kmac.co.kr:8080/DWPM3/DWPM30100_U_LINK.jsp"
			+ "?empno=<authz:authentication operation="username" />"
			+ "&cmd=MOD&&srch_workgb=1";
		if(type == 'oldEntNo'){
			if($F('oldEntNo') != ''){
				budgetUrl  += ("&gubun=O&entno="+$F('oldEntNo'));
				var sFeatures = "top=100,left=100,width=800,height=600,resizable=yes,scrollbars=yes";
				var detailWin = window.open(budgetUrl,"budgetOld", sFeatures);
				detailWin.focus();
			}else{
				//alert('등록된 기존 예산이 없습니다.');
				alert('기존 예산 정보가 없습니다.');
			}
		}else if(type == 'newEntNo'){
			if($F('newEntNo') != ''){
				budgetUrl  += ("&gubun=N&entno="+$F('newEntNo'));
				var sFeatures = "top=100,left=100,width=800,height=600,resizable=yes,scrollbars=yes";
				var detailWin = window.open(budgetUrl,"budgetNew", sFeatures);
				detailWin.focus();
			}else{
				//alert('변경 예산 정보가 없습니다.\n예산관리에서 기존 예산을 복사하여 변경 예산을 작성하십시오.');
				alert('변경 예산 정보가 없습니다.');
			}
		}
	}


	function refTaskRequest(){
		AjaxRequest.post (
				{	'url': "/action/ProjectBudjetInfoAction.do?mode=goProjectEntNos",
					'parameters' : { "projectCode": $("projectCode").value},
					'onSuccess':function(obj){
						var res = eval('(' + obj.responseText + ')');
						if(res.entNoNew != "" && res.entNoNew != 'undefined'){
							AjaxRequest.submit(
								document.forms["budgetChangeSanctionForm"],
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
							);
						}else{
							alert('등록된 신규 예산이 없습니다.');
						}
					},
					'onLoading':function(obj){},
					'onError':function(obj){
						alert("저장할 수 없습니다.");
					}
				}
		);		
	}	

</script>
</head>

<body>


<form name="budgetChangeSanctionForm" method="post" enctype="multipart/form-data">
	<div id="hiddneValue" style="display: none;">
		<input type="text" id="taskId" name="taskId" value="<c:out value="${sanctionDoc.taskId}" />" />
		<input type="text" id="approvalType" name="approvalType" value="<c:out value="${sanctionDoc.approvalType}" />" />
		<input type="text" id="projectCode" name="projectCode" value="<c:out value="${sanctionDoc.projectCode}" />" />
		<input type="text" id="seq" name="seq" value="<c:out value="${sanctionDoc.seq}" />" /> 
		<input type="text" id="state" name="state" value="<c:out value="${sanctionDoc.state}" />" />
		<input type="text" name="oldEntNo" id="oldEntNo" value="<c:out value="${projectRelatedentNo.oldEntNo}" />" />
		<input type="text" name="newEntNo" id="newEntNo" value="<c:out value="${projectRelatedentNo.newEntNo}" />" />
	</div>
	
	<!-- location -->
	<div class="location">
			<p class="menu_title">예산변경품의</p>
			<ul>
				<li class="home">HOME</li>
				<li>MY PROJECT</li>
				<li>예산변경품의</li>
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
										<td><c:out value="${userName }" /> / <c:out value="${companyPositionName }" /></td>
									</tr>
									<tr>
										<th>결재유형</th>
										<td><c:out value="${sanctionTemplate.approvalName}" /></td>
										<th>제목</th>
										<td><input type="text" id="subject" name="subject" class="textInput_left" style="width: 100%"  <c:if test="${readonly}">readonly</c:if> value="<c:choose><c:when test="${sanctionDoc.state == 'SANCTION_STATE_DRAFT'}"><c:out value="${project.projectName}"/> 예산변경품의 </c:when><c:otherwise><c:out value="${sanctionDoc.subject}"/></c:otherwise></c:choose>" ></td>
									</tr>
									<tr>
										<th>프로젝트 정보</th>
										<td colspan="3">[<c:out value="${project.projectCode}"/>] <c:out value="${project.projectName}"/></td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
					
					<div class="board_box">
						<div class="title">
							<div class="h1_area">
								<p class="h1">상세 내용</p>
								<button type="button" class="btn line btn_black"><a title="예산서정보" href="#" onclick="viewBudget('oldEntNo')"><i class="mdi mdi-currency-krw"></i>기존 예산 정보</a></button>
								<button type="button" class="btn line btn_black"><a title="예산서정보" href="#" onclick="viewBudget('newEntNo')"><i class="mdi mdi-currency-krw"></i>신규 예산 정보</a></button>
							</div>	
							<c:choose><c:when test="${sanctionDoc.state == 'SANCTION_STATE_DRAFT' }">
							<div class="text-R">
								<c:if test="${!readonly}">
									<button type="button" class="btn line btn_blue" onclick="draftRequest()"><i class="mdi mdi-book-check-outline"></i>등록 요청</button>
								</c:if>
								<c:if test="${sanctionDoc.state != 'SANCTION_STATE_DRAFT'}">
									<!-- <a class="btNa0a0a0 txt4btn" href="#" onclick="showEntrustInfo();">업무위임</a> -->
								</c:if>
								<c:if test="${sanctionDoc.state == 'SANCTION_STATE_REJECT_DRAFT'}">
									<button type="button" class="btn line btn_pink" onclick="deleteWork()"><i class="mdi mdi-trash-can-outline"></i>업무 삭제</button>
									<button type="button" class="btn line btn_grey" onclick="tempSaveWork()"><i class="mdi mdi-square-edit-outline"></i>임시 저장</button>
								</c:if>
								<c:if test="${readonly && isRefWork}"> 
									<button type="button" class="btn line btn_blue" onclick="refTaskRequest()"><i class="mdi mdi-book-check-outline"></i>등록 요청</button>
								</c:if>
							</div>
							</c:when><c:otherwise> </c:otherwise></c:choose>
							</div>
							
							<div class="board_contents">
								<c:choose>
									<c:when test="${sanctionDoc.state == 'SANCTION_STATE_DRAFT'}">
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
										<br>
									</td>
								</tr>
							</table>
							</c:if>
							</div>
							
						</div>
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
										<c:if test="${!readonly}">
										<button type="button" class="btn line btn_blue" onclick="draftRequest()"><i class="mdi mdi-book-check-outline"></i>등록 요청</button>
										</c:if>
										<c:if test="${sanctionDoc.state != 'SANCTION_STATE_DRAFT'}">
											<!-- <a class="btNa0a0a0 txt4btn" href="#" onclick="showEntrustInfo();">업무위임</a> -->
										</c:if>
										<c:if test="${sanctionDoc.state == 'SANCTION_STATE_REJECT_DRAFT'}">
											<button type="button" class="btn line btn_pink" onclick="deleteWork()"><i class="mdi mdi-trash-can-outline"></i>업무 삭제</button>
											<button type="button" class="btn line btn_grey" onclick="tempSaveWork()"><i class="mdi mdi-square-edit-outline"></i>임시 저장</button>
										</c:if>
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
										<c:if test="${!readonly}">
											<button type="button" class="btn line btn_blue" onclick="draftRequest()"><i class="mdi mdi-book-check-outline"></i>등록 요청</button>
										</c:if>
										<c:if test="${sanctionDoc.state != 'SANCTION_STATE_DRAFT'}">
											<!-- <a class="btNa0a0a0 txt4btn" href="#" onclick="showEntrustInfo();">업무위임</a> -->
										</c:if>
										<c:if test="${sanctionDoc.state == 'SANCTION_STATE_REJECT_DRAFT'}">
											<button type="button" class="btn line btn_pink" onclick="deleteWork()"><i class="mdi mdi-trash-can-outline"></i>업무 삭제</button>
											<button type="button" class="btn line btn_grey" onclick="tempSaveWork()"><i class="mdi mdi-square-edit-outline"></i>임시 저장</button>
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
					</div>
				</div>
			</div>
	 </div>	
		<script type="text/javascript">
		function showEntrustInfo(){
			$('entrustInfo').style.top = document.body.scrollHeight-120;
			$('entrustInfo').style.left = 457;
			$('entrustInfo').show();
		}
		
		function entrustRequest(){
			if($F('entrustUserName') ==""){alert("위임자를 지정하시기 바랍니다.");return;}
			var sFrm = document.forms["budgetChangeSanctionForm"];
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
			style="position: absolute; background: white; display: none; width: 310px; text-align: center; border-style: solid; border-color: gray; border-width: 3px;">
			<table style="" cellpadding="3" cellspacing="3">
				<tr>
					<td><img alt="" src="/images/title_de02.jpg" align="top"><span style="subTitle"> <b>업무위임 요청</b></span></td>
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
		</div>
	</form>						
		
</body>
</html>
