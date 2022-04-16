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
function row_delete() {
	var chkBoxEls = $$('input[name="chkMember"]');
	for(var i = 0; i < chkBoxEls.length; i++){
		if ( chkBoxEls[i].checked ) {
			$(chkBoxEls[i]).up().up().remove();//성공 시 행삭제
		}
	}
}
function doSave(){

	var ActionURL = "/action/ProjectEndingAction.do";	
	ActionURL += "?mode=insertRateE";
	var sFrm = document.forms["rateFrm"];
	

	var status = AjaxRequest.submit(
			sFrm
			,{ 'url':ActionURL
				,'onSuccess':function(obj){
					document.location = "/action/WorkCabinetAction.do?mode=getMyWorkList";
				}
				,'onLoading':function(obj){}
				,'onError':function(obj){
					alert("이미 강사 평가가 완료되었으므로 업무를 실행할 수 없습니다.\n관리자에게 문의하시기 바랍니다.");
				}
			}
	); status = null;
}
</script>
</head>
<body>
<%-- 작업영역 --%>
	<form name="rateFrm" method="post">
		<table width="100%" cellpadding="0" cellspacing="0" border="0">
			<tr>
				<td>
					<jsp:include page="/common/include/includePageMainTitle.jsp" flush="true">
						<jsp:param name="title" value="교육 강사 평가" />
						<jsp:param name="backButtonYN" value="Y" />
					</jsp:include>
				</td>
			</tr>

			<!-- sub 타이틀 영역 시작--> 
			<tr>
				<td>
					<h4 class="subTitle mt5 mb5">프로젝트 정보</h4>
				</td>
			</tr>
			<!-- sub 타이틀 영역 종료--> 
					
					
			<tr>
				<td>
					<table cellSpacing="0" cellpadding="0" width="100%" >
						<tr height="25px">
							<td class="detailTableTitle_center" width="100px">프로젝트 명</td>
							<td class="detailTableField_left" width="*">
								<c:out value="${project.projectName}"/>
								<input type="hidden" name="taskId" value="<c:out value="${taskId}" />" />
								<input type="hidden" name="projectCode" value="<c:out value="${project.projectCode}"/>">
								<input type="hidden" name="gubun" value="T">
							</td>
							<td class="detailTableTitle_center" width="100px">수행기간</td>
							<td class="detailTableField_left" >
								<fmt:parseDate value="${project.realStartDate}" type="DATE" dateStyle="SHORT"  pattern="yyyyMMdd" var="date"/>
								<fmt:formatDate value="${date}" pattern="yyyy-MM-dd" var="formattedFrom"/>
								<c:out value="${formattedFrom}"/>
								~
								<fmt:parseDate value="${project.realEndDate}" type="DATE" dateStyle="SHORT"  pattern="yyyyMMdd" var="date"/>
								<fmt:formatDate value="${date}" pattern="yyyy-MM-dd" var="formattedFrom"/>
								<c:out value="${formattedFrom}"/>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			

			<!-- sub 타이틀 영역 시작--> 
			<tr>
				<td>
					<h4 class="subTitle mt15 mb5">강사 평가 항목 및 채점</h4>
				</td>
			</tr>
			<!-- sub 타이틀 영역 종료--> 
			
	
			<tr>
				<td>
					<table class="tableStyle05">
						<thead>
							<tr height="25px">
								<td width="30px">선택</td>
								<td width="85px">성명</td>
								<td width="*">교육과목</td>
								<td width="80">강의수준</td>
								<td width="80">강의준비</td>
								<td width="80">강의스킬</td>
								<td width="80">성의 및 열의</td>
							</tr>
						</thead>
						<tbody> 
						<c:if test="${!empty result.list}">
							<c:forEach var="result" items="${result.list}">
								<tr>
									<td align="center">
										<input type="checkbox" name="chkMember">
										<input type="hidden" name="ssn" value="<c:out value="${result.ssn}" />">
									</td>
									<td align="center"><c:out value="${result.name}" /></td>
									<td align="left">
										<input type="text" name="subjectArray" value="" class="textInput_left" style="width: 100%;">
									</td>
									<c:forEach begin="1" end="4" varStatus="i">
										<td align="left">
											<select name="answer<c:out value="${i.count}"/>Array" class="selectbox"  style="width: 100%;">
												<c:set var="k" value="25"/>
												<c:forEach begin="0" end="25" step="1" varStatus="j"><option value="<c:out value="${k}"/>"><c:out value="${k}"/>/25</option><c:set var="k" value="${k-1}"/></c:forEach>
											</select>
										</td>
									</c:forEach>
								</tr>
							</c:forEach>
						</c:if>
						</tbody>
					</table>
				</td>
			</tr>
			<tr>
				<td height="30" align="right"><a class="btNe14f42 txt2btn" href="#" onclick="row_delete();">행삭제</a></td>
			</tr>
		
			<tr>
				<td height='7'></td>
			</tr>
			<tr>
				<td>
					<table width="100%" height="36" bgcolor="#F3F3F3" cellpadding="0" cellspacing="0">
						<tr>
							<td align="right">평가 대상이 아닌 강사를 선택한 뒤 후 행 삭제 버튼을 누르면 평가에서 제외됩니다.</td>
							<td>
								<p align="right">
									<a class="btNe006bc6 txt2btn" href="#" onclick="doSave()">등록요청</a>
								</p>
							</td>
						</tr>
					</table>
				</td>
			</tr>
	</table>
	</form>
</body>
</html>