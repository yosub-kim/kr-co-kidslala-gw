<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<%@page import="net.mlw.vlh.ValueList"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>평가보기</title>
<%-- 공통js,css include --%>
<%@ include file="/common/include/includeCss.jsp"%>
<%@ include file="/common/include/includeJavaScript.jsp"%>
<script type="text/javascript">
<%-- 개별 스크립트 영역 --%>
function doSave(){
	AjaxRequest.post (
		{	'url': "/action/ProjectEndingAction.do?mode=isRateFinished",
			'parameters' : { "projectCode": $("projectCode").value},
			'onSuccess':function(obj){
				var res = eval('(' + obj.responseText + ')');
				if(res.isFinished == true ){ 
						//|| $('ignoreCustomerRate').checked){
					AjaxRequest.post (
						{    'url':'/action/ProjectRollingAction.do?mode=insertRollingE'
							,'parameters': {'projectCode':'<c:out value="${projectCode}"/>', 'taskId':'<c:out value="${taskId}" />'}
							,'onSuccess':function(obj){
								document.location = "/action/WorkCabinetAction.do?mode=getMyWorkList";
							}
							,'onLoading':function(obj){}
							,'onError':function(obj){
								alert("진행할 수 없습니다.");
							}
						}
					); 
				}else{
					alert("고객평가가 완료되지 않아 업무를 실행할 수 없습니다.\n고객이 만족도 평가에 응답한 후 다시 시도하십시오.");
				}
			},
			'onLoading':function(obj){},
			'onError':function(obj){
				alert("저장할 수 없습니다.\n관리자에게 문의하시기 바랍니다.");
			}
		}
	);

	
}
</script>
</head>
<body>
<c:forEach var="DetailResult" items="${result.list}">
	<form name="rateFrm" method="post">
	<input type="hidden" name="taskId" id="taskId" value="<c:out value="${taskId}" />" />
	<input type="hidden" name="projectCode" id="projectCode" value="<c:out value="${projectCode}" />">
	<input type="hidden" name="seq" id="seq" value="<c:out value="${seq}" />">
	
	<%-- 작업영역 --%>
	<table width="100%" cellpadding="0" cellspacing="0">
		<tr>
			<td class="Title">
				<jsp:include page="/common/include/includePageMainTitle.jsp" flush="true">
					<jsp:param name="title" value="공개교육 고객만족도 평가" />
					<jsp:param name="backButtonYN" value="Y" />
				</jsp:include>
			</td>
		</tr>
		<tr>	
			<td>
				<h4 class="subTitle mt15 mb5">교육 정보</h4>
			</td>
		</tr>
		<tr>
			<td>
				<table class="listTable" id="listTable">
					<tr>
						<td width="25%" class="detailTableTitle_center">교육명</td>
						<td class="detailTableField_left"><c:out value="${DetailResult.projectName}"/></td>
					</tr>
				</table>
			</td>
		</tr>
		
		<tr>
			<td>
				<h4 class="subTitle mt15 mb5"><c:out value="${DetailResult.businessTypeName}"/> 만족도</h4>
			</td>
		</tr>
		<tr>
			<td>
		      	<table class="listTable" id="listTable">
		 			<tr class="detailTableTitle_center">
			 			<td width="80%"><b>항목</b></td>
			 			<td width="20%"><b>평가</b></td>
		 			</tr>
		 			<tr>
			 			<td>내용의 체계성 (배점 20점)</td>
			 			<td align="right" style="padding-right:5pt"><c:out value="${DetailResult.cs1}"/> 점</td>
		 			</tr>
		 			<tr>
			 			<td>현업 활용도 (배점 20점)</td>
			 			<td align="right" style="padding-right:5pt"><c:out value="${DetailResult.cs2}"/> 점</td>
		 			</tr>
		 			<tr>
			 			<td>교육시설(교육장, 교육기자재) (배점 10점)</td>
			 			<td align="right" style="padding-right:5pt"><c:out value="${DetailResult.cs3}"/> 점</td>
		 			</tr>
		 			<tr>
			 			<td>교육진행 (배점 20점)</td>
			 			<td align="right" style="padding-right:5pt"><c:out value="${DetailResult.cs4}"/> 점</td>
		 			</tr>
		 			<tr>
			 			<td>부대시설(휴게실, 화장실 등)(배점 10점)</td>
			 			<td align="right" style="padding-right:5pt"><c:out value="${DetailResult.cs5}"/> 점</td>
		 			</tr>
		 			<tr>
			 			<td>전반적 만족도 (배점 20점)</td>
			 			<td align="right" style="padding-right:5pt"><c:out value="${DetailResult.cs6}"/> 점</td>
		 			</tr>
		 			<tr>
		 				<td class="detailTableTitle_center"><b>평가</b></td>
			 			<td align="right" style="padding-right:5pt"><b><c:out value="${DetailResult.cs1+DetailResult.cs2+DetailResult.cs3+DetailResult.cs4+DetailResult.cs5+DetailResult.cs6}"/> 점</b></td>
		 			</tr>
		 		</table>
			</td>
		</tr>

		<tr>
			<td>
				<h4 class="subTitle mt15 mb5">강사 만족도</h4>
			</td>
		</tr>
		<tr>
			<td>
				<table class="listTable" id="listTable">
					<tr class="detailTableTitle_center">
						<td width="*"><b>성명</b></td>
						<td width="15%"><b>강의교재</b></td>
						<td width="15%"><b>강의준비</b></td>
						<td width="15%"><b>강의스킬</b></td>
						<td width="15%"><b>성의 및 열의</b></td>
						<td width="15%"><b>평가</b></td>
					</tr>
					<c:if test="${!empty result2.list}">
						<c:forEach var="DetailResult2" items="${result2.list}">
						<tr height="19" align="center">
							<td><c:out value="${DetailResult2.cname}"/></td>
							<td><c:out value="${DetailResult2.rc8}"/> / 25</td>
							<td><c:out value="${DetailResult2.rc9}"/> / 25</td>
							<td><c:out value="${DetailResult2.rc10}"/> / 25</td>
							<td><c:out value="${DetailResult2.rc12}"/> / 25</td>
							<td align="right" style="padding-right:5pt"><b><c:out value="${DetailResult2.rc8+DetailResult2.rc9+DetailResult2.rc10+DetailResult2.rc12}"/> 점</b></td>
						</tr>
						</c:forEach>
					</c:if>
					<c:if test="${empty result2.list}">
						<td align="center"  colspan="6">평가 대상 컨설턴트가 없습니다.</td>
					</c:if>
				</table>
			</td>
		</tr>
		<tr>
			<td>
				<h4 class="subTitle mt15 mb5">교육생 의견 및 건의 사항</h4>
			</td>
		</tr>
		<tr>
			<td>
				<textarea rows="5" style="padding:3px;width:100%;height:100" readonly="readonly"><c:out value="${DetailResult.cs7}"/></textarea>
			</td>
		</tr> 
		<tr><td height="7"></td></tr>	
		<tr>
			<td>
				<table width="100%" height="36" bgcolor="#F3F3F3" cellpadding="0" cellspacing="0">
					<tr>
						<td align="right">비즈니스 스쿨에서 고객만족도 결과를 입력하면 점수를 확인할 수 있습니다. 그 이후에 '등록요청'을 누르십시오.</td>
						<td>
							<div class="btNbox txtR">
								<a class="btNe006bc6 txt2btn" href="javascript:doSave();">등록요청</a>
							</div>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	</form>
</div>
</c:forEach>
</body>
</html>