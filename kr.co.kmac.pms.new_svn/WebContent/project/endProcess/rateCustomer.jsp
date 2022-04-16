<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<%@page import="net.mlw.vlh.ValueList"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>평가보기</title>
<%-- 공통js,css include --%>
<%-- <%@ include file="/common/include/includeCss.jsp"%> --%>
<%@ include file="/common/include/includeJavaScript.jsp"%>
<link rel="stylesheet" href="/resources/css/common.css" type="text/css" />
<link rel="stylesheet" href="/resources/css/board.css" type="text/css" />
<link rel="stylesheet" href="/resources/css/selectric.css" type="text/css" />
<link rel="stylesheet" href="/resources/css/dev.css" type="text/css" />
<meta name="viewport" content="width=1600">
<script type="text/javascript">
<%-- 개별 스크립트 영역 --%>
function doSave(){
	if(document.rateFrm.receiveCnt.value >= document.rateFrm.sendCnt.value){ 
		AjaxRequest.post (
			{    'url':'/action/ProjectEndingAction.do?mode=insertRateCustomer'
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
}
</script>
</head>
<body>
<c:forEach var="DetailResult" items="${result.list}">
	<form name="rateFrm" method="post">
	<input type="hidden" name="taskId" value="<c:out value="${taskId}" />" />
	<input type="hidden" name="projectCode" value="<c:out value="${projectCode}" />">
	<input type="hidden" name="seq" value="<c:out value="${seq}" />">
	<input type="hidden" name="sendCnt" value="<c:out value="${rollingState.sendCnt}"/>">
	<input type="hidden" name="receiveCnt" value="<c:out value="${rollingState.receiveCnt}"/>">
	
	<!-- location -->
	<div class="location">
		<p class="menu_title">고객만족도 평가</p>
		<ul>
			<li class="home">HOME</li>
			<li>고객만족도 평가</li>
		</ul>
	</div>
	<!-- // location -->
		
				<!-- contents sub -->
	<div class="contents sub">
	
		<!-- 신규 프로젝트 등록 -->
		<div class="board_box">
			<div class="title_both">
				<div class="h1_area">
					<p class="h1">프로젝트 개요</p>
				</div>
				<div class="text-R">
					<p>고객만족도 조사에 응답하면 평가 내역을 확인할 수 있습니다. 그 이후에 등록요청을 누르십시오.</p>
					<button type="button" class="btn btn_blue" onclick="doSave()">등록요청</button>
					
				</div>
			</div>
		
			<div class="board_contents">
				<table  id="projectProgressTable" class="tbl-edit td-c"><!-- td 영역이 가운데 정렬 일 경우 td-c -->
							<colgroup>
								<col style="width: 20%"/>
								<col style="width: *"/>
							</colgroup>
							<tr>
								<th>프로젝트 명</th>
								<td>
									<c:out value="${DetailResult.projectName}"/>
								</td>
							</tr>
							<div style="display:none;">
								<c:if test="${rollingState.sendCnt > 1}">
									<tr>
										<td class="detailTableTitle_center">고객만족도 조사 대상 고객사 수</td>
										<td class="detailTableField_left"><c:out value="${rollingState.sendCnt}"/></td>
									</tr>
									<tr>
										<td class="detailTableTitle_center">고객만족도 평가 완료 고객사 수</td>
										<td class="detailTableField_left"><c:out value="${rollingState.receiveCnt}"/></td>
									</tr>
								</c:if>
							</div>
					</table>
				</div>
			</div>
			
			<div class="board_box">
				<div class="title_both">
					<div class="h1_area">
						<p class="h1"><c:out value="${DetailResult.businessTypeName}"/> 만족도</p>
					</div>
				</div>
		
				<div class="board_contents">
					<table  id="projectProgressTable" class="tbl-edit td-c"><!-- td 영역이 가운데 정렬 일 경우 td-c -->
								<colgroup>
									<col style="width: 20%"/>
									<col style="width: 20%"/>
									<col style="width: 40%"/>
									<col style="width: 20%"/>
								</colgroup>
								<tr>
									<th>고객사</th>
									<th>담당자</th>
									<th>항목</tj>
									<th>평가</th>
								</tr>
								<tr>
									<td rowspan="4">
									<c:if test="${rollingState.sendCnt <= 1}">
				 						<c:out value="${DetailResult.customerName}"/>
				 					</c:if>	
									</td>
									<td rowspan="4">
									<c:if test="${rollingState.sendCnt <= 1}">
				 					<p><c:out value="${DetailResult.jikwee}"/></p>
					 				<c:out value="${DetailResult.customerWorkPName}"/>
					 				</c:if>
									</td>
									<td>전반적 만족도 (배점 25점)</td>
									<td><c:out value="${DetailResult.cs3}"/> 점</td>
								</tr>
								<tr>
									<td>
				 						<c:choose>
				 							<c:when test="${project.processTypeCode eq 'E4'}">골드파이어 활용 만족도 (배점 25점)</c:when>
				 							<c:when test="${project.processTypeCode eq 'DE'}">현업 적용 도움 여부(배점 25점)</c:when>
				 							<c:otherwise>계약내용준수 (배점 25점)</c:otherwise>
				 						</c:choose>
				 					</td>
				 					<td align="right"><c:out value="${DetailResult.cs6}"/> 점</td>
								</tr>
								<tr>
						 			<td>
						 				<c:choose>
						 					<c:when test="${project.processTypeCode eq 'E4'}">산출물 만족도(배점 25점)</c:when>
						 					<c:when test="${project.processTypeCode eq 'N4' or project.processTypeCode eq 'SS'}">교재 만족도(배점 25점)</c:when>
						 					<c:when test="${project.processTypeCode eq 'DE'}">숙박, 교통 및 음식 만족도 (배점 25점)</c:when>
						 					<c:otherwise>산출물 만족도 (배점 25점)</c:otherwise>
						 				</c:choose>
						 			</td>
						 			<td align="right"><c:out value="${DetailResult.cs5}"/> 점</td>
					 			</tr>
					 			<tr>
						 			<td>추천의향 (배점 25점)</td>
						 			<td align="right"><c:out value="${DetailResult.cs4}"/> 점</td>
					 			</tr>	 			
					 			<tr>
					 				<td class='detailTableTitle_center'><b>평가</b></td>
						 			<td colspan="3" align="right" style="padding-right:5pt">
						 			<c:if test="${rollingState.receiveCnt > 0}">
						 				<b><fmt:formatNumber value="${DetailResult.cs3+DetailResult.cs4+DetailResult.cs5+DetailResult.cs6}" pattern=".00"/> 점</b>
						 			</c:if>
						 			</td>
					 			</tr>
						</table>
					</div>
				</div>
				
				<c:if test="${project.processTypeCode != 'DE' }">	
				
				<div class="board_box">
				<div class="title_both">
					<div class="h1_area">
						<p class="h1"><c:choose><c:when test="${project.processTypeCode eq 'N4' or project.processTypeCode eq 'SS'}">강사</c:when><c:otherwise>컨설턴트</c:otherwise></c:choose> 만족도</p>
					</div>
				</div>
		
				<div class="board_contents">
					<table  id="projectProgressTable" class="tbl-edit td-c"><!-- td 영역이 가운데 정렬 일 경우 td-c -->
								<colgroup>
									<col style="width: 10%"/>
									<col style="width: 15%"/>
									<col style="width: 15%"/>
									<col style="width: 15%"/>
									<col style="width: 15%"/>
									<col style="width: 15%"/>
									<col style="width: 15%"/>
								</colgroup>
								<tr>
									<th>성명</th>
					 				<c:choose>
					 					<c:when test="${project.processTypeCode eq 'N4' or project.processTypeCode eq 'SS'}">
											<th>강의수준</th>
											<th>강의준비</th>
											<th>강의스킬</th>
											<th>성의 및 열의</th>
					 					</c:when>
					 					<c:otherwise>
											<th>고객요구대응</th>
											<th>전문성</th>
											<th>의사소통</th>
											<th>일정준수도</th>
					 					</c:otherwise>
					 				</c:choose>						
									<th>종합만족도</th>
									<th>평가</th>
								</tr>
									<c:if test="${!empty result2.list}">
										<c:forEach var="DetailResult2" items="${result2.list}">
										<c:if test="${!empty DetailResult2.cname}">
										<tr>
											<td><c:out value="${DetailResult2.cname}"/></td>
											<td><fmt:formatNumber value="${DetailResult2.rc8}" pattern=""/> / 20</td>
											<td><fmt:formatNumber value="${DetailResult2.rc9}" pattern=""/> / 20</td>
											<td><fmt:formatNumber value="${DetailResult2.rc10}" pattern=""/> / 20</td>
											<td><fmt:formatNumber value="${DetailResult2.rc12}" pattern=""/> / 20</td>
											<td><fmt:formatNumber value="${DetailResult2.rc13}" pattern=""/> / 20</td>
											<td>
											<fmt:formatNumber value="${DetailResult2.rc8+DetailResult2.rc9+DetailResult2.rc10+DetailResult2.rc12+DetailResult2.rc13}" pattern=""/>
										점</td>
										</tr>
										</c:if>
										</c:forEach>
									</c:if>
								<c:if test="${empty result2.list}">
									<td align="center"  colspan="7">평가 대상 컨설턴트가 없습니다.</td>
								</c:if>
						</table>
					</div>
				</div>
				</c:if>
				
				<div class="board_box">
					<div class="title_both">
						<div class="h1_area">
							<p class="h1"><c:out value="${DetailResult.businessTypeName}"/> 의견/불만사항</p>
						</div>
					</div>
		
					<div class="board_contents">
						<table  id="projectProgressTable" class="tbl-edit td-c"><!-- td 영역이 가운데 정렬 일 경우 td-c -->
							<colgroup>
								<col style="width: *"/>
							</colgroup>
							<tr>
								<td>
									<textarea style='width:100%;height:100%' readonly="readonly" ><c:out value="${DetailResult.opinion}" escapeXml="false"/></textarea>
								</td>
							</tr>
					</table>
				</div>
			</div>
	</table>
	</form>
</div>
</c:forEach>
</body>
</html>