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
function doSave(){

	var ActionURL = "/action/ProjectEndingAction.do";	
	ActionURL += "?mode=insertRollingT";
	var sFrm = document.forms["rateFrm"];
	

	var status = AjaxRequest.submit(
			sFrm
			,{ 'url':ActionURL
				,'onSuccess':function(obj){
				document.location = "/action/WorkCabinetAction.do?mode=getMyWorkList";
				}
				,'onLoading':function(obj){}
				,'onError':function(obj){
					alert("삭제 할 수 없습니다.");
				}
			}
	); status = null;
}
</script>
</head>
<body>
<%-- 작업영역 --%>
<table width="100%"  cellpadding="0" cellspacing="0" style="table-layout:fixed;">
	<!-- sub 타이틀 영역 시작--> 
	<tr>
		<td>
			<h4 class="subTitle mt15 mb5">프로젝트 명</h4>
		</td>
	</tr>
	<!-- sub 타이틀 영역 종료--> 
	<tr>
		<td>
			<table class="listTable" id="listTable">
				<tr>
					<td width="25%" class="detailTableTitle_center">프로젝트 명</td>
					<td class="detailTableField_left" width="*">
						<c:out value="${project.projectName}"/>
						<input type="hidden" name="projectCode" value="<c:out value="${project.projectCode}"/>">
					</td>
				</tr>
			</table>
		</td>
	</tr>

	<!-- sub 타이틀 영역 시작--> 
	<tr>
		<td>
			<h4 class="subTitle mt15 mb5">연수 만족도 평가 항목 및 결과</h4>
		</td>
	</tr>
	<!-- sub 타이틀 영역 종료--> 	
	<tr>
		<td>
			<table class="listTable" id="listTable">
		 		<tr class="detailTableTitle_center">
					<td width="200px">고객사</td>
					<td width="*">항 목</td>
					<td width="100px">배점</td>
				</tr>
			<c:if test="${!empty result.list}">
			<c:forEach var="rs" items="${result.list}">	
				<tr>
					<td class="detailTableField_center" rowspan="10"><c:out value="${rs.customerName}"/><br><c:out value="${rs.duty}"/><br><c:out value="${rs.name}"/></td>
					<td class="detailTableField_left">전반적 만족도(배점 10점)</td>
					<td class="detailTableField_right"><c:out value="${rs.answer1}"/> 점&nbsp;</td>
				</tr>
				<tr>
					<td class="detailTableField_left">벤치마킹 업체 선정(배점 10점)</td>
					<td class="detailTableField_right"><c:out value="${rs.answer2}"/> 점&nbsp;</td>
				</tr>
				<tr>
					<td class="detailTableField_left">현업 적용 도움 여부(배점 10점)</td>
					<td class="detailTableField_right"><c:out value="${rs.answer3}"/> 점&nbsp;</td>
				</tr>
				<tr>
					<td class="detailTableField_left">일정 준수도(배점 10점)</td>
					<td class="detailTableField_right"><c:out value="${rs.answer4}"/> 점&nbsp;</td>
				</tr>
				<tr>
					<td class="detailTableField_left">숙박 및 음식(배점 10점)</td>
					<td class="detailTableField_right"><c:out value="${rs.answer5}"/> 점&nbsp;</td>
				</tr>
				<tr>
					<td class="detailTableField_left">사전정보제공(현지날씨, 준비물, 방문기업정보)(배점 10점)</td>
					<td class="detailTableField_right"><c:out value="${rs.answer6}"/> 점&nbsp;</td>
				</tr>
				<tr>
					<td class="detailTableField_left">진행 및 전문성(배점 10점)</td>
					<td class="detailTableField_right"><c:out value="${rs.answer7}"/> 점&nbsp;</td>
				</tr>
				<tr>
					<td class="detailTableField_left">통역의 언어구사력 및 전문지식(배점 10점)</td>
					<td class="detailTableField_right"><c:out value="${rs.answer8}"/> 점&nbsp;</td>
				</tr>
				<tr>
					<td class="detailTableField_left">가이드의 성실성, 친절성, 적극성(배점 10점)</td>
					<td class="detailTableField_right"><c:out value="${rs.answer9}"/> 점&nbsp;</td>
				</tr>
				<tr>
					<td class="detailTableField_left">추천의향(배점 10점)</td>
					<td class="detailTableField_right"><c:out value="${rs.answer10}"/> 점&nbsp;</td>
				</tr>
				<tr>
					<td class="detailTableTitle_center">평가</td>
					<td class="detailTableField_right" colspan="2"><b><c:out value="${rs.sum}"/> 점</b>&nbsp;</td>
				</tr>
			</c:forEach>
			</c:if>
			<c:if test="${empty result.list}">
				<tr>
					<td class="detailTableField_center" rowspan="10">-</td>
					<td class="detailTableField_left">전반적 만족도(배점 10점)</td>
					<td class="detailTableField_right">-&nbsp;</td>
				</tr>
				<tr>
					<td class="detailTableField_left">벤치마킹 업체 선정(배점 10점)</td>
					<td class="detailTableField_right">-&nbsp;</td>
				</tr>
				<tr>
					<td class="detailTableField_left">현업 적용 도움 여부(배점 10점)</td>
					<td class="detailTableField_right">-&nbsp;</td>
				</tr>
				<tr>
					<td class="detailTableField_left">일정 준수도(배점 10점)</td>
					<td class="detailTableField_right">-&nbsp;</td>
				</tr>
				<tr>
					<td class="detailTableField_left">숙박 및 음식(배점 10점)</td>
					<td class="detailTableField_right">-&nbsp;</td>
				</tr>
				<tr>
					<td class="detailTableField_left">사전정보제공(현지날씨, 준비물, 방문기업정보)(배점 10점)</td>
					<td class="detailTableField_right">-&nbsp;</td>
				</tr>
				<tr>
					<td class="detailTableField_left">진행 및 전문성(배점 10점)</td>
					<td class="detailTableField_right">-&nbsp;</td>
				</tr>
				<tr>
					<td class="detailTableField_left">통역의 언어구사력 및 전문지식(배점 10점)</td>
					<td class="detailTableField_right">-&nbsp;</td>
				</tr>
				<tr>
					<td class="detailTableField_left">가이드의 성실성, 친절성, 적극성(배점 10점)</td>
					<td class="detailTableField_right">-&nbsp;</td>
				</tr>
				<tr>
					<td class="detailTableField_left">추천의향(배점 10점)</td>
					<td class="detailTableField_right">-&nbsp;</td>
				</tr>
				<tr>
					<td class="detailTableTitle_center">평가</td>
					<td class="detailTableField_right" colspan="2">평가 결과 없음&nbsp;</td>
				</tr>
			</c:if>
			</table>
		</td>
	</tr>
	<tr>
		<td align="right" height="15"></td>
	</tr>

</table>
</body>
</html>