<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>

<%@page import="kr.co.kmac.pms.common.util.DateTime"%>
<%@page import="kr.co.kmac.pms.sanction.general.data.SanctionDoc"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<%-- 공통js,css include --%>
<%@ include file="/common/include/includeCss.jsp"%>
<%@ include file="/common/include/includeJavaScript.jsp"%>

<script type="text/javascript">
	
	function tempSaveWork(){
		var sFrm = document.forms["projectRunningSanctionForm"];
		var status = AjaxRequest.submit(
				sFrm,
				{	'url': "/action/ProjectRunningSanctionAction.do?mode=saveProjectRunningSanction",
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
			var sFrm = document.forms["projectRunningSanctionForm"];
			var status = AjaxRequest.submit(
					sFrm,
					{	'url': "/action/ProjectRunningSanctionAction.do?mode=deleteProjectRunningSanction",
						'onSuccess':function(obj){
							var res = eval('(' + obj.responseText + ')');
							if(res.result == 'SUCCESS'){alert('삭제하였습니다.');
								document.projectRunningSanctionForm.target = "";
								document.projectRunningSanctionForm.action = "/action/WorkCabinetAction.do?mode=getMyWorkList";
								document.projectRunningSanctionForm.submit();								
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
		   alert('제목을 입력하세요.');
		   document.projectRunningSanctionForm.subject.focus();
		   return;
		  }
		if(doubleSubmitCheck()) return;
		if($('state').value == "SANCTION_STATE_DRAFT"){
			$('isApproved').value= 'Y';
			ActionURL = "/action/ProjectRunningSanctionAction.do?mode=createGeneralSanction";
		}else{
			//if($('isApproved')[0].checked == false && $('isApproved')[1].checked == false ){alert('승인여부를 선택하세요');return;}
			ActionURL = "/action/ProjectRunningSanctionAction.do?mode=executeGeneralSanction";
		}
		var sFrm = document.forms["projectRunningSanctionForm"];
		var ActionURL = "/action/ProjectRunningSanctionAction.do?mode=createProjectRunningSanction";
		var status = AjaxRequest.submit(
				sFrm,
				{	'url':ActionURL,
					'onSuccess':function(obj){
						alert('등록하였습니다.');
						if($('state').value == "SANCTION_STATE_DRAFT"){
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

	function refTaskRequest(){
		var sFrm = document.forms["projectRunningSanctionForm"];

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

<form name="projectRunningSanctionForm" style="display: none;">

	<div style="display: none;">
		<input type="text" name="approvalType" value="<c:out value="${sanctionDoc.approvalType}" />" />
		<input type="text" name="projectCode" value="<c:out value="${sanctionDoc.projectCode}" />" />
		<input type="text" name="seq" value="<c:out value="${sanctionDoc.seq}" />" /> 
		<input type="text" name="state" value="<c:out value="${sanctionDoc.state}" />" />
	</div>

	<table width='765'  cellpadding="0" cellspacing="0">

	<!-- SPACER -->
	<tr>
		<td width="765" height="8">&nbsp;</td>
	</tr>

	<tr>
		<td>
			<jsp:include page="/common/include/includePageMainTitle.jsp" flush="true">
				<jsp:param name="title" value="기안하기 (전자결재)" />
				<jsp:param name="backButtonYN" value="Y" />
			</jsp:include>
		</td>
	</tr>
	<tr>
		<td height="10"></td>
	</tr>	
	
	
	
	<%@include file="/sanction/common/sanctionLineInfo.jsp" %>			 
		
			
	<!-- sub 타이틀 영역 시작-->
		<tr><td height='5'></td></tr>
		<tr>
			<td width="793" height="27" >&nbsp;<img src="/images/icon_5.gif" align="absmiddle"><b><font class='SF'>&nbsp;기안 내용</font></b></td>
		</tr>
		
		<tr><td height='5'></td></tr>
	<!-- sub 타이틀 영역 종료-->		
		<tr>
			<td width="793">		
				<table width='765'  cellpadding="0" cellspacing="0">
					<tr>
						<td width="100px" class="detailTableTitle_center">결재 유형</td>
						<td width="693px" class="detailTableField_left">
							<select name="approvalTypeSelect" class="selectbox" style="width: 100%;"  disabled="disabled">
								<option value="">결재 유형을 선택하세요.</option>
								<c:forEach var="item" items="${sanctionTemplateList}">
									<option value="<c:out value="${item.approvalType}"/>" <c:if test="${sanctionTemplate.approvalType == item.approvalType}">selected</c:if>><c:out value="${item.approvalName}" /></option>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<td width="100px" class="detailTableTitle_center">제목</td>
						<td width="693px" class="detailTableField_left"><input type="text" name="subject" class="textInput_left" style="width: 100%" <c:if test="${readonly}">readonly</c:if> value="<c:out value="${sanctionDoc.subject}"/>"></td>
					</tr>
					<tr>
						<td width="100" class="detailTableTitle_center">품의 내용</td>
						<td width="665" class="detailTableField_left"><textarea name="content" class="textArea" style="width: 100%; height: 250px;"  <c:if test="${readonly}">readonly</c:if>><c:choose><c:when test="${sanctionDoc.state == 'SANCTION_STATE_DRAFT'}"><c:out value="${sanctionTemplate.templateText}" /></c:when><c:otherwise><c:out value="${sanctionDoc.content}" /></c:otherwise></c:choose></textarea></td>
					</tr>
				</table>
			</td>
		</tr>			
		<c:if test="${sanctionTemplate.hasAttach}">
				<jsp:include page="/common/repository/fileUpload.jsp"><jsp:param value="Y" name="log"/></jsp:include>
		</c:if>															
		<%@include file="/sanction/common/sanctionApproveInfo.jsp" %>
				
	
		
	<!-- 버튼부분-->
		<tr>
			<td height='7'></td>
		</tr>
		<c:if test="${!readonly}"> 
			<tr>
				<td>
					<table width='765' height='32' bgcolor='#F3F3F3' cellpadding="0" cellspacing="0">
						<tr>
							<td><p align='right'>
								<img alt="등록요청" src="/images/btn_regist_request.jpg" border="0" onclick="draftRequest()"  style="cursor: hand;">
								<c:if test="${sanctionDoc.state != 'SANCTION_STATE_DRAFT'}">
									<img alt="업무위임" src="/images/btn_mandate.jpg" border="0" onclick="showEntrustInfo();" style="cursor: hand;">
								</c:if>
								<c:if test="${sanctionDoc.state != 'SANCTION_STATE_DRAFT'}">
									<img alt="임시저장" src="/images/btn_tempor_save.jpg" border="0" onclick="tempSaveWork()" style="cursor: hand;">
									<img alt="업무삭제" src="/images/btn_delete.jpg" border="0" onclick="deleteWork()" style="cursor: hand;">
								</c:if>
								</p>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</c:if>
		<c:if test="${readonly && isRefWork}"> 
			<tr>
				<td>
					<table width='765' height='32' bgcolor='#F3F3F3' cellpadding="0" cellspacing="0">
						<tr>
							<td>
								<p align='right'>
									<a href="#" onclick="refTaskRequest()"><img alt="등록요청" 	src="/images/btn_regist_request.jpg" border="0"></a>
								</p>
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
