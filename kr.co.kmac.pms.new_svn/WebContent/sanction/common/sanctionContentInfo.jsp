<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<%@ include file="/common/include/includeJavaScript.jsp"%>
<link rel="stylesheet" href="/resources/css/common.css" type="text/css" />
<link rel="stylesheet" href="/resources/css/board.css" type="text/css" />
<link rel="stylesheet" href="/resources/css/selectric.css" type="text/css" />
<link rel="stylesheet" href="/resources/css/dev.css" type="text/css" />
<meta name="viewport" content="width=1600">

<div class="board_box">
		<div class="title">
				<div class="h1_area">
					<p class="h1">문서정보</p>
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
							<td><c:out value="${expertPool.deptName }" /></td>
							<th>기안자</th>
							<td><c:out value="${expertPool.name }"/> / <c:out value="${expertPool.companyPositionName }" /></td>
						</tr>
						<tr>
							<th>결재유형</th>
							<td><c:out value="${sanctionTemplate.approvalName}" />
								<input type="hidden" name="approvalType" id="approvalType" value="<c:out value="${sanctionTemplate.approvalType}" />" />
							</td>
							<th>제목</th>
							<td><input type="text" name="subject" id="subject" class="contentInput_left" style="width: 80%" value="<c:out value="${sanctionDoc.subject}"/>" <c:if test="${readonly}">readonly</c:if>></td>
						</tr>	
					</tbody>
				</table>
			</div>
		</div>
		
		<div class="board_box">
			<div class="title">
				<div class="h1_area">
					<p class="h1">상세정보</p>
				</div>
				<c:choose><c:when test="${sanctionDoc.state == 'SANCTION_STATE_DRAFT' }">
					<div class="text-R">
					<c:if test="${!readonly}">
						<c:if test="${sanctionDoc.state == 'SANCTION_STATE_DRAFT'}">
							<button tyle="button" class="btn line btn_aqua" onclick="draftSave()"><i class="mdi mdi-folder-clock-outline"></i>임시저장</button>
						</c:if>						
							<button type="button" class="btn line btn_blue" onclick="draftRequest();"><i class="mdi mdi-square-edit-outline"></i>등록요청</button>
						<c:if test="${sanctionDoc.state != 'SANCTION_STATE_DRAFT'}">
							<button type="button" class="btn line btn_grey" onclick="showEntrustInfo();"><i class="mdi mdi-account-multiple"></i>업무위임</button>
						</c:if> 
						<c:if test="${(!empty sanctionDoc.taskId && sanctionDoc.state == 'SANCTION_STATE_DRAFT') || sanctionDoc.state == 'SANCTION_STATE_REJECT_DRAFT'}">
							<button type="button" class="btn line btn_pink" onclick="deleteWork()"><i class="mdi mdi-trash-can-outline"></i>삭제</button>
						</c:if>	
					</c:if>		
					<c:if test="${readonly && isRefWork}">
						<button type="button" class="btn line btn_blue" onclick="refTaskRequest();"><i class="mdi mdi-square-edit-outline"></i>등록요청</button>
					</c:if>
					</div>
				</c:when><c:otherwise></c:otherwise></c:choose>
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
							<th>프로젝트 명</th>
							<td colspan="3"><span class="d-blue">[<c:out value="${project.projectCode }" />] <c:out value="${project.projectName }" /></span></td>
						</tr>
						<tr>
							<th>프로젝트 기간</th>
							<td><fmt:parseDate value="${project.realStartDate }" var="startDate" pattern="yyyyMMdd" /><fmt:formatDate value="${startDate}" pattern="yyyy.MM.dd" /> 
							~ <fmt:parseDate value="${project.realEndDate }" var="endDate" pattern="yyyyMMdd" /><fmt:formatDate value="${endDate}" pattern="yyyy.MM.dd" /> </td>
							<th>고객사 명</th>
							<td><span class="d-blue"><c:out value="${project.customerName }" /></span></td>
						</tr>
						<tr>
							<th>매출 액</th>
							<td><span class="aqua"><fmt:formatNumber value="${projectBudget.amt1 }" type="number"/></span> 원</td>
							<th>총매출원가</th>
							<td><span class="aqua"><fmt:formatNumber value="${projectBudget.amt2 }" type="number"/></span> 원</td>
						</tr>
						<tr>
							<th>인건비매출원가율</th>
							<td><c:choose><c:when test='${empty projectBudget.amt3 }'>-</c:when><c:otherwise><span class="aqua"><c:out value="${projectBudget.amt3 }" /></span> %</c:otherwise></c:choose></td>
							<th>매출이익(율)</th>
							<td><span class="pink"><fmt:formatNumber value="${projectBudget.amt4 }" type="number"/> 원 (이익률: <span class="pink"><c:out value="${projectBudget.amt5 }" />%</span>)</td>
						</tr>
						<tr>
							<th>인력 정보</th>
							<td colspan="3">
								<c:forEach var="rs" items="${projectMember}">
									<c:out value="${rs.name }"/>
								</c:forEach>
							</td>
						</tr>
						<tr>
							<th>내용</th>
							<td colspan="3"><div style="padding: 25 0 25 0">
							<textarea name="content" id="content" class="textArea" style="width: 100%; height: 300px;"  
							><c:choose><c:when test="${empty sanctionDoc.taskId && sanctionDoc.state == 'SANCTION_STATE_DRAFT'}"
											><c:out value="${sanctionTemplate.templateText}" /></c:when
										><c:otherwise
											><c:out value="${sanctionDoc.content}" /></c:otherwise
							></c:choose></textarea></div>
							</td>
						</tr>
						<c:if test="${sanctionTemplate.hasAttach}">
							<tr class="file">
								<th><label for="file">첨부파일</label></th>
								<td colspan="3" style="padding: 10 0 0 0 ">
									<jsp:include page="/common/repository/fileUpload.jsp"><jsp:param value="Y" name="log"  /></jsp:include>
								</td>
							</tr>
						</c:if>
					</tbody>
				</table>
			</div>
		</div>
	
		<c:choose><c:when test="${sanctionDoc.state == 'SANCTION_STATE_DRAFT' }">
			<div style="display:none;"><div class="board_box">
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
					<c:choose><c:when test="${sanctionDoc.state != 'SANCTION_STATE_DRAFT' }">
						<div class="text-R">
						<c:if test="${!readonly}">
								<c:if test="${sanctionDoc.state == 'SANCTION_STATE_DRAFT'}">
									<button tyle="button" class="btn line btn_aqua" onclick="draftSave()"><i class="mdi mdi-folder-clock-outline"></i>임시저장</button>
								</c:if>						
									<button type="button" class="btn line btn_blue" onclick="draftRequest();"><i class="mdi mdi-square-edit-outline"></i>등록요청</button>
								<c:if test="${sanctionDoc.state != 'SANCTION_STATE_DRAFT'}">
									<button type="button" class="btn line btn_grey" onclick="showEntrustInfo();"><i class="mdi mdi-account-multiple"></i>업무위임</button>
								</c:if> 
								<c:if test="${(!empty sanctionDoc.taskId && sanctionDoc.state == 'SANCTION_STATE_DRAFT') || sanctionDoc.state == 'SANCTION_STATE_REJECT_DRAFT'}">
									<button type="button" class="btn line btn_pink" onclick="deleteWork()"><i class="mdi mdi-trash-can-outline"></i>삭제</button>
								</c:if>	
							</c:if>		
							<c:if test="${readonly && isRefWork}">
								<button type="button" class="btn line btn_blue" onclick="refTaskRequest();"><i class="mdi mdi-square-edit-outline"></i>등록요청</button>
							</c:if>
						</div>
					</c:when><c:otherwise></c:otherwise></c:choose>
				</div>
				<div class="board_contents">
					<!-- sanctionApprove -->
					<%@include file="/sanction/common/sanctionApproveInfo.jsp" %>
					<!-- // sanctionApprove -->
				</div>
			</div></div>
		</c:when><c:otherwise>
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
					<c:choose><c:when test="${sanctionDoc.state != 'SANCTION_STATE_DRAFT' }">
						<div class="text-R">
						<c:if test="${!readonly}">
								<c:if test="${sanctionDoc.state == 'SANCTION_STATE_DRAFT'}">
									<button tyle="button" class="btn line btn_aqua" onclick="draftSave()"><i class="mdi mdi-folder-clock-outline"></i>임시저장</button>
								</c:if>						
									<button type="button" class="btn line btn_blue" onclick="draftRequest();"><i class="mdi mdi-square-edit-outline"></i>등록요청</button>
								<%-- <c:if test="${sanctionDoc.state != 'SANCTION_STATE_DRAFT'}">
									<button type="button" class="btn line btn_grey" onclick="showEntrustInfo();"><i class="mdi mdi-account-multiple"></i>업무위임</button>
								</c:if>  --%>
								<c:if test="${(!empty sanctionDoc.taskId && sanctionDoc.state == 'SANCTION_STATE_DRAFT') || sanctionDoc.state == 'SANCTION_STATE_REJECT_DRAFT'}">
									<button type="button" class="btn line btn_pink" onclick="deleteWork()"><i class="mdi mdi-trash-can-outline"></i>삭제</button>
								</c:if>	
							</c:if>		
							<c:if test="${readonly && isRefWork}">
								<button type="button" class="btn line btn_blue" onclick="refTaskRequest();"><i class="mdi mdi-square-edit-outline"></i>등록요청</button>
							</c:if>
						</div>
					</c:when><c:otherwise></c:otherwise></c:choose>
				</div>
				<div class="board_contents">
					<!-- sanctionApprove -->
					<%@include file="/sanction/common/sanctionApproveInfo.jsp" %>
					<!-- // sanctionApprove -->
				</div>
			</div>
		</c:otherwise></c:choose>
					

<script type="text/javascript">
		function showEntrustInfo(){
			$('entrustInfo').style.top = document.body.scrollHeight/2;
			$('entrustInfo').style.left = 457;
			$('entrustInfo').show();
		}
		
		function entrustRequest(){
			if($('entrustUserName').value ==""){alert("위임자를 지정하시기 바랍니다.");return;}
			var sFrm = document.forms["projectLaunchSanctionForm"];
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
	style="position: absolute; background: white; display: none; width: 310px; height: 100px; text-align: center; border-style: solid; border-color: gray; border-width: 2px;">
<table style="" cellpadding="3" cellspacing="3">
	<tr>
		<td><h4 class="subTitle">업무위임 요청</h4></td>
	</tr>
	<tr>
		<td>
		<table id="delayInfoTable" width="290px">
			<thead id="delayInfoTableHeader">
				<tr>
					<td class="detailTableTitle_center" width="27%">위임자</td>
					<td class="detailTableField_left" width="65%">
						<input type="hidden" id="entrustUserSsn" name="entrustUserSsn"> 
						<input type="Text" id="entrustUserName" name="entrustUserName" class="textInput_left" readonly>
					</td>
					<td class="detailTableField_left" width="8%">
						<img alt="위임자 선택" src="/images/btn_glass.jpg" style="cursor: hand;" onclick="openExpertPoolPopUp('entrust')">
					</td>
				</tr>
			</thead>
			<tbody id="delayInfoTableBody">
			</tbody>
		</table>
		</td>
	</tr>
	<tr>
		<td colspan="2">
			<div class="btNbox mt5 txtR">
				<a title="위임 실행" class="btNe006bc6 txt2btn" href="#" onclick="entrustRequest()">위임</a>
				<a title="취소" class="btNa0a0a0 txt2btn" href="#" onclick="$('entrustInfo').hide()">닫기</a>
			</div>
		</td>
	</tr>
</table>
</div>
<table border="0">
	<tr>
		<td height="1"></td>
	</tr>
</table>
<div id="displayInfo"
	style="position: absolute; background: white; display: none; width: 310px; text-align: center; border-style: solid; border-color: gray; border-width: 3px;">
</div>
<%--
<script>
//alert(UserAgent);
if (UserAgent.match(/SHW-M380S/) != null){
	var textAreaProp = document.getElementsByTagName('textarea');
	//alert(textAreaProp.length);
	for(var i=0; textAreaProp.length > i; i++){
		textAreaProp[i].readOnly = false;
	}
}
</script>
 --%>