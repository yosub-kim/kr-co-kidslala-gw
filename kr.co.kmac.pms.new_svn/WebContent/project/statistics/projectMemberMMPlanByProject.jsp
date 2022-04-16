<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>프로젝트 투입률 변경</title>
<%-- 공통js,css include --%>
<%@ include file="/common/include/includeCss.jsp"%>
<%@ include file="/common/include/includeJavaScript.jsp"%>
<script type="text/javascript">
<%-- 개별 스크립트 영역 --%>
function storeMMPlan() {
	var budgetTotalMMValue = document.getElementById("totalMMValue").value;
	
	// 입력한 투입량 합산
	var trCount = document.getElementById("mmValueTableBody").getElementsByTagName("tr").length - 2;
	var totalMMValue = 0;
	var tdIndex = 1;
	for ( var i=0; i < trCount; i++ ){
		if (document.getElementById("mmValueTableBody").getElementsByTagName("td")[tdIndex].childNodes[0].value == "") {
			alert((i+1) + "번째 행의 투입값을 입력해주세요.\n(미투입시 0 입력)");
			return;
		}
		totalMMValue += parseFloat(document.getElementById("mmValueTableBody").getElementsByTagName("td")[tdIndex].childNodes[0].value);
		tdIndex += 2;
	}
	// 투입량 합산 완료
	
	// 입력한 총 투입량과 예산서상 총 투입량 비교
	if(totalMMValue > budgetTotalMMValue) {
		alert("예산서상 총 투입값("+ budgetTotalMMValue +")보다 큰 투입값("+ totalMMValue + ")을 입력할 수 없습니다.");
		return;
	}
	
	var ActionURL = "/action/ProjectMemberMMPlanAction.do?mode=updateProjectMemberMMPlanByProject";
	var sFrm = document.forms["projectMemberMMPlanForm"];
	
	var status = AjaxRequest.submit(
			sFrm,
			{	'url':ActionURL,
				'onSuccess':function(obj){
					alert('저장하였습니다.');
					document.projectMemberMMPlanForm.target = "projectMemberMMPlanFrame";
					document.projectMemberMMPlanForm.action = "/action/ProjectMemberMMPlanAction.do"
						+"?mode=getProjectMemberMMPlan&type=B&ssn=" + $('ssn').value + "&year=" + $('parentYear').value + "&month=" + $('parentMonth').value;
					document.projectMemberMMPlanForm.submit();	
					
					//window.parent.Windows.close("modal_window");
				},
				'onLoading':function(obj){},
				'onError':function(obj){
					alert("저장할 수 없습니다.");
				}
			}
	); status = null;
}

function closeMMPlan() {
	window.parent.Windows.close("modal_window");
}

</script> 
</head>

<body style="width: 400px;">
	<form name="projectMemberMMPlanForm" method="post">
		<div style='display: none;'>
			<input type="hidden" name="ssn" id="ssn" value="<c:out value="${ssn}"/>" />
			<input type="hidden" name="projectCode" id="projectCode" value="<c:out value="${projectCode}"/>" />
			<input type="hidden" name="projectTypeCode" id="projectTypeCode" value="<c:out value="${mmValueList.list[0].projectTypeCode}"/>" />
			<input type="hidden" name="totalMMValue" id="totalMMValue" value="<c:out value="${mmValueList.list[0].totalMMValue}"/>" />
			<input type="hidden" name="type" id="type" value="<c:out value="${type}" />" />
			<input type="hidden" name="parentYear" id="parentYear" value="<c:out value="${year}" />" />
			<input type="hidden" name="parentMonth" id="parentMonth" value="<c:out value="${month}" />" />
		</div>
		
		<table cellpadding="0" cellspacing="0" style="width: 400px">
			<!-- 타이틀 영역 -->
			<tr>
				<td height="12"></td>
			</tr>
			
			
			<!-- 본문 리스트 시작 -->			
			<tr>
				<td>
					<table border="0" cellspacing="0" cellpadding="0" style="width: 100%">
						<tr>
							<td>
								<h4 class="subTitle">1. 프로젝트 정보</h4>
							</td>
						</tr>
						<tr>
							<td>
								<table cellpadding="0" cellspacing="0" style="width: 100%">
								<colgroup>
									<col width="80px"/>
									<col width="110px"/>
									<col width="80px"/>
									<col width="*"/>
								</colgroup>
								<thead>
									<tr>
										<td class="detailTableTitle_center">프로젝트명</td>
										<td class="detailTableField_left" colspan="3"><c:out value="${mmValueList.list[0].projectName}" /></td>
									</tr>
									<tr>
										<td class="detailTableTitle_center">프로젝트유형</td>
										<td class="detailTableField_left"><code:code tableName="PROJECT_TYPE_CODE" code="${mmValueList.list[0].projectTypeCode}" /></td>
										<td class="detailTableTitle_center">컨설턴트명</td>
										<td class="detailTableField_left"><c:out value="${mmValueList.list[0].name}" /></td>
									</tr>
								</thead>
								</table>
							</td>
						</tr>
					</table>
				</td>			
			</tr>
			<tr>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td>
					<table border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td>
								<h4 class="subTitle">2. 월별 투입정보</h4>
							</td>
							<td style="text-align: right;">
							<span class="bold colore74c3a">
								예산서상 총 투입 
								<c:if test="${mmValueList.list[0].projectTypeCode == 'MM'}">M/M</c:if>
								<c:if test="${mmValueList.list[0].projectTypeCode == 'MD'}">M/D</c:if>
								<c:if test="${mmValueList.list[0].projectTypeCode == 'ED'}">시간</c:if>
								: <c:out value="${mmValueList.list[0].totalMMValue}" />
							</span>
							</td>
						</tr>
						<tr>
							<td colspan="2">
								<table class="listTable" style="table-layout: fixed;" id="mmValueTable">
									<thead id="mmValueTableHeader">
										<tr height="25px">
											<td width="100px">투입월</td>
											<td width="*">투입</td>
										</tr>
									</thead>			
									<tbody id="mmValueTableBody">
										<c:choose>
											<c:when test="${!empty mmValueList.list}">
												<c:set var="realTotalMMValue" value="0"/>
												<c:forEach var="rs" items="${mmValueList.list}">
													<c:set var="realTotalMMValue" value="${realTotalMMValue + rs.mmValue}"/>
													<input type="hidden" id="year" name="year" value="<c:out value="${rs.year}" />" />
													<input type="hidden" id="month" name="month" value="<c:out value="${rs.month}" />" />
													<input type="hidden" id="mmState" name="mmState" value="<c:out value="${rs.mmState}" />" />
													<tr id="mmValueTableTr" onmouseover="row_over(this)" onmouseout="row_out(this)">
														<td><c:out value="${rs.year}" />년 <c:out value="${rs.month}" />월</td>
														<td><input type="text" id="mmValue" name="mmValue" class="textInput_center" <c:if test="${rs.mmState == 'Y'}">readonly=readonly</c:if> value=<c:out value="${rs.mmValue}" /> onchange="Money_Only(this, 1000, -1);" style="width: 100%;" /></td>
													</tr>
												</c:forEach> 
											</c:when>
											<c:otherwise>
												<tr>
													<td align="center" colspan="2">검색된 데이터가 없습니다.</td>
												</tr>
											</c:otherwise>
										</c:choose>
										<tr>
											<td style="background-color:#F2F1EE">합계</td>
											<td style="background-color:#F2F1EE"><fmt:formatNumber value="${realTotalMMValue}" pattern="0.00"/></td>
										</tr>
										<tr>
											<td colspan="2">
											<div class="btNbox mt10 mb10 txtR">
												<a class="btNe006bc6 txt2btn" href="#" onclick="storeMMPlan('<c:out value="${realTotalMMValue}" />');" >저장</a>
												<a class="btNa0a0a0 txt2btn" href="#" onclick="closeMMPlan();">닫기</a>
											</div>
											</td>
										</tr>
									</tbody>
								</table>
							</td>
						</tr>
					</table>
				</td>			
			</tr> 
		</table>
	
	</form>
</body>
</html>