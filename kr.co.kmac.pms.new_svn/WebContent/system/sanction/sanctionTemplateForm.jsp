<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<%-- 공통js,css include --%>
<%@ include file="/common/include/includeCss.jsp"%>
<%@ include file="/common/include/includeJavaScript.jsp"%>

<script type="text/javascript">
<%-- 개별 스크립트 영역 --%>
function btnNew_onclick(){
	document.location = "/action/SanctionTemplateAction.do?mode=loadFormSanctionTemplate";
}

function goList() {
	document.location = "/action/SanctionTemplateAction.do?mode=getSanctionTemplateList";
}

function createApprovalType() {
	var url = "/action/SanctionTemplateAction.do?mode=storeSanctionTemplate";
	var sFrm = document.forms["sanctionTemplateForm"];	

	var status = AjaxRequest.submit(
			sFrm,{
				'url': url,
				'onSuccess':function(obj){
					alert('성공하였습니다.');
					goList();
				},
				'onLoading':function(obj){},
				'onError':function(obj){				
					alert("저장할 수 없습니다.");
				}	
			}
	); status = null;
}

function deleteApprovalType() {
	var url = "/action/SanctionTemplateAction.do?mode=deleteSanctionTemplate";
	var sFrm = document.forms["sanctionTemplateForm"];	

	var status = AjaxRequest.submit(
			sFrm,{
				'url': url,
				'onSuccess':function(obj){
					alert('성공하였습니다.');
					goList();
				},
				'onLoading':function(obj){},
				'onError':function(obj){				
					alert("저장할 수 없습니다.");
				}	
			}
	); status = null;
}

function toggleCeoDetail() {
	if($('hasCeo').checked){
		$('ceoDetail').show();
	}else{
		$('ceoSsn').value = '';
		$('ceoName').value = '';
		$('ceoDept').value = '';
		$('ceoDetail').hide();
	}
}
function toggleSptTeamDetail() {
	if($('hasSptTeamMng').checked){
		$('sptTeamDetail').show();
	}else{
		$('sptTeamMngSsn').value = '';
		$('sptTeamMngName').value = '';
		$('sptTeamMngDept').value = '';
		$('sptTeamDetail').hide();
	}
}

function toggleAssistMemberDetail() {
	if($('hasAssistMember').checked){
		$('assistMemberDetail').show();
	}else{
		$('assistMemberCnt').value = '';
		$('assistMemberDetail').hide();
	}
}
function toggleRefMember() {
	if($('hasRefMember').checked){
		$('refMemberDetail').show();
	}else{
		$('refMemberNames').value = '';
		$('refMemberSsns').value = '';
		$('refMemberDetail').hide();
	}
}
</script>
</head>

<body onload="toggleCeoDetail();toggleSptTeamDetail();toggleAssistMemberDetail();toggleRefMember();">
<div style="margin: 70 0 0 20 ">
	<form name="sanctionTemplateForm" method="post">
		<table width="751" cellpadding="0" cellspacing="0">
			<tr>
				<td>
					<table width="100%" cellpadding="0" cellspacing="0">
		<!-- 타이틀 영역 시작-->
						<tr>
							<td height="12">
								<jsp:include page="/common/include/includePageMainTitle.jsp" flush="true">
									<jsp:param name="title" value="전자결재 템플릿" />
									<jsp:param name="backButtonYN" value="Y" />
								</jsp:include>
							</td>
						</tr>
						<tr>
							<td height="10"></td>
						</tr>
		<!-- 타이틀 영역 종료-->
		
		<!-- sub 타이틀 영역 시작-->
						<tr>
							<td>
								<h4 class="subTitle">상세 내용</h4>
							</td>
						</tr>
		<!-- sub 타이틀 영역 종료-->

		<!-- 본문 영역 시작-->
						<tr>
							<td>
								<table width="100%" cellpadding="0" cellspacing="0">
									<tr>
										<td width="150" class="detailTableTitle_center">결재유형코드</td>
										<td width="*" class="detailTableField_left"><input type="text" name="approvalType" id="approvalType" size="90" style="ime-mode:inactive" value="<c:out value="${template.approvalType}"/>" class="contentInput_left"></td>
									</tr>
									<tr>
										<td class="detailTableTitle_center">결재유형</td>
										<td class="detailTableField_left"><input type="text" name="approvalName" id="approvalName" size="90" value="<c:out value="${template.approvalName}"/>" class="contentInput_left"></td>
									</tr>
									<tr>
										<td class="detailTableTitle_center">검토자 필요여부</td>
										<td class="detailTableField_left"><input type="checkbox" name="hasTeamManager"  id="hasTeamManager"  <c:if test="${template.hasTeamManager}">checked</c:if>>필요</td>
									</tr>
									<tr>
										<td class="detailTableTitle_center">확인자 필요여부</td>
										<td class="detailTableField_left"><input type="checkbox" name="hasCfo"  id="hasCfo"  <c:if test="${template.hasCfo}">checked</c:if>>필요</td>
									</tr>
									<tr>
										<td class="detailTableTitle_center">승인자 필요여부</td>
										<td class="detailTableField_left"><input type="checkbox" name="hasCio"  id="hasCio"  <c:if test="${template.hasCio}">checked</c:if>>필요</td>
									</tr>
									<tr>
										<td class="detailTableTitle_center">참조자 필요여부</td>
										<td class="detailTableField_left"><input type="checkbox" name="hasRefMember"  id="hasRefMember"  <c:if test="${template.hasRefMember}">checked</c:if> onclick="toggleRefMember()">필요
											<div id="refMemberDetail">
												참조자 UID: <input type="text" name="refMemberSsns" id="refMemberSsns" style="width: 200px;" value="<c:out value="${template.refMemberSsns}"/>" class="contentInput_left"><br>
												참조자 이름: <input type="text" name="refMemberNames" id="refMemberNames" style="width: 200px;" value="<c:out value="${template.refMemberNames}"/>" class="contentInput_left">
											</div>										
										</td>
									</tr>
									<tr>
										<td class="detailTableTitle_center">협의라인 필요여부</td>
										<td class="detailTableField_left"><input type="checkbox" name="hasAssistMember" id="hasAssistMember"  <c:if test="${template.hasAssistMember}">checked</c:if> onclick="toggleAssistMemberDetail()">필요
											<div id="assistMemberDetail">
												협의라인수(MAX 4): <input type="text" name="assistMemberCnt" id="assistMemberCnt" style="width: 50px;" value="<c:out value="${template.assistMemberCnt}"/>" class="contentInput_left"><br>
											</div></td>
									</tr>
									<tr>
										<td class="detailTableTitle_center">협의라인<br>기획실장 필요여부</td>
										<td class="detailTableField_left"><input type="checkbox" name="hasSptTeamMng"  id="hasSptTeamMng"  <c:if test="${template.hasSptTeamMng}">checked</c:if> onclick="toggleSptTeamDetail()">필요
												<div id="sptTeamDetail">
													기획실장 UID: <input type="text" name="sptTeamMngSsn" id="sptTeamMngSsn" style="width: 200px;" value="<c:out value="${template.sptTeamMngSsn}"/>" class="contentInput_left"><br>
													기획실장 이름: <input type="text" name="sptTeamMngName" id="sptTeamMngName" style="width: 200px;" value="<c:out value="${template.sptTeamMngName}"/>" class="contentInput_left"><br>
													기획실장 부서: <input type="text" name="sptTeamMngDept" id="sptTeamMngDept" style="width: 200px;" value="<c:out value="${template.sptTeamMngDept}"/>" class="contentInput_left">
												</div>
										</td>
									</tr>
									<tr>
										<td class="detailTableTitle_center">CEO 필요여부</td>
										<td class="detailTableField_left"><input type="checkbox" name="hasCeo" id="hasCeo"  <c:if test="${template.hasCeo}">checked</c:if> onclick="toggleCeoDetail()">필요
												<div id="ceoDetail">
													CEO UID: <input type="text" name="ceoSsn" id="ceoSsn" style="width: 200px;" value="<c:out value="${template.ceoSsn}"/>" class="contentInput_left"><br>
													CEO 이름: <input type="text" name="ceoName" id="ceoName" style="width: 200px;" value="<c:out value="${template.ceoName}"/>" class="contentInput_left"><br>
													CEO 부서: <input type="text" name="ceoDept" id="ceoDept" style="width: 200px;" value="<c:out value="${template.ceoDept}"/>" class="contentInput_left">
												</div>
										</td>
									</tr>
									<tr>
										<td class="detailTableTitle_center">전결 필요여부</td>
										<td class="detailTableField_left"><input type="checkbox" name="hasWholeApprove"  id="hasWholeApprove"  <c:if test="${template.hasWholeApprove}">checked</c:if>>필요</td>
									</tr>
									<tr>
										<td class="detailTableTitle_center">협의1 전결 필요여부</td>
										<td class="detailTableField_left"><input type="checkbox" name="hasSptTeamMngWholeApprove"  id="hasSptTeamMngWholeApprove"  <c:if test="${template.hasSptTeamMngWholeApprove}">checked</c:if>>필요</td>
									</tr>
									<tr>
										<td class="detailTableTitle_center">첨부 필요여부</td>
										<td class="detailTableField_left"><input type="checkbox" name="hasAttach"  id="hasAttach"  <c:if test="${template.hasAttach}">checked</c:if>>필요</td>
									</tr> 
									<tr>
										<td class="detailTableTitle_center">목록에 보이기</td> 
										<td class="detailTableField_left"><input type="checkbox" name="useYn"  id="useYn"  <c:if test="${template.useYn}">checked</c:if>>보이기</td>
									</tr>
									<tr>
										<td class="detailTableTitle_center">모바일결재</td> 
										<td class="detailTableField_left"><input type="checkbox" name="useMobile" id="useMobile"  <c:if test="${template.useMobile}">checked</c:if>>보이기</td>
									</tr>
									<tr>
										<td class="detailTableTitle_center">결재타입</td> 
										<td class="detailTableField_left">
											<select name="sanctionType" id="sanctionType" class="selectbox" style="width: 150px;">
												<option value="KMAC" <c:if test="${template.sanctionType == 'KMAC'}">selected</c:if>>KMAC</option>
												<option value="CC"   <c:if test="${template.sanctionType == 'CC'}"  >selected</c:if>>callCenter</option>												
											</select>
										</td>
									</tr>
									<tr>
										<td class="detailTableTitle_center">결재 문서 내용</td>
										<td class="detailTableField_left" style="height: 250px"><textarea name="templateText"  id="templateText"  style="height:98%; border-width: 1px; border-style: solid; border-color: #D8D8D8;"><c:out value="${template.templateText}"/></textarea></td>
									</tr>
								</table>
							</td>
						</tr>						
		<!-- 본문 영역 종료-->
		
		
		<!-- 버튼부분-->
						<tr>
							<td height="7"></td>
						</tr>
						<tr>
							<td>
								<table width="751" height="36" bgcolor="#F3F3F3" cellpadding="0" cellspacing="0">
									<tr>
										<td>										
											<div class="btNbox txtR">
												<a title="저장" class="btNe006bc6 txt2btn" href="#" onClick="createApprovalType();">저장</a>
												<a title="삭제" class="btNe14f42 txt2btn" href="#" onClick="deleteApprovalType();">삭제</a>
												<a title="목록으로 돌아가기" class="btNa0a0a0 txt2btn" href="#" onClick="goList();">목록</a>
											</div>
										<td>
									</tr>
								</table>
							</td>
						</tr>
		<!-- 버튼종료-->
					</table>
				</td>
			</tr>
		</table>						
	</form>					
	
<%@ include file="/common/include/includeBodyBottom.jsp"%>
</div>
</body>

</html>					