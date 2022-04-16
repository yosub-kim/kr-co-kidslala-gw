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

	var ActionURL = "/action/ProjectRollingAction.do";	
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
					alert("이미 만족도 평가가 완료되었으므로 업무를 실행할 수 없습니다.\n관리자에게 문의하시기 바랍니다.");
				}
			}
	); status = null;
}
</script>
</head>
<body>
<%-- 작업영역 --%>
	<form name="rateFrm" method="post">
		
		<table width="100%" cellpadding="0" cellspacing="0">
			<tr>
				<td>
					<jsp:include page="/common/include/includePageMainTitle.jsp" flush="true">
						<jsp:param name="title" value="연수 만족도 평가" />
						<jsp:param name="backButtonYN" value="Y" />
					</jsp:include>
				</td>
			</tr>

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
								<input type="hidden" name="taskId" value="<c:out value="${taskId}" />" />
								<input type="hidden" name="projectCode" value="<c:out value="${project.projectCode}"/>">
								<input type="hidden" name="gubun" value="T">
							</td>
						</tr>
					</table>
				</td>
			</tr>



			<!-- sub 타이틀 영역 시작--> 
			<tr>
				<td>
					<h4 class="subTitle mt15 mb5">연수 만족도 평가 항목 및 채점</h4>
				</td>
			</tr>
			<!-- sub 타이틀 영역 종료--> 
			
			<tr>
				<td>
					<table class="listTable" id="listTable">
		 				<tr class="detailTableTitle_center">
							<td width="120px">항 목</td>
							<td width="*">항목의 정의</td>
							<td width="100px">채점</td>
						</tr>
						<tr>
							<td class="detailTableTitle_center">문항 1</td>
							<td class="detailTableField_left">   귀하께서는 본 연수과정에 대해 전반적으로 얼마나 만족하십니까 ?</td>
							<td>
								<select name="answer1" class="selectbox" style="width: 100%">
									<c:set var="k" value="10"/>
									<c:forEach begin="0" end="10" step="1" varStatus="j">
										<option value="<c:out value="${k}"/>"><c:out value="${k}"/>/10</option>
										<c:set var="k" value="${k-1}"/>
									</c:forEach>
								</select>
							</td>
						</tr>
						<tr>
							<td class="detailTableTitle_center">문항 2</td>
							<td class="detailTableField_left">   연수테마에 맞는 벤치마킹 업체가 잘 선택되었습니까 ?</td>
							<td>
								<select name="answer2" class="selectbox" style="width: 100%">
									<c:set var="k" value="10"/>
									<c:forEach begin="0" end="10" step="1" varStatus="j">
										<option value="<c:out value="${k}"/>"><c:out value="${k}"/>/10</option>
										<c:set var="k" value="${k-1}"/>
									</c:forEach>
								</select>
							</td>
						</tr>
						<tr>
							<td class="detailTableTitle_center">문항 3</td>
							<td class="detailTableField_left">   연수를 통해 얻은 정보가 현업적용에 많은 도움이 되신다고 생각하십니까 ?</td>
							<td>
								<select name="answer3" class="selectbox" style="width: 100%">
									<c:set var="k" value="10"/>
									<c:forEach begin="0" end="10" step="1" varStatus="j">
										<option value="<c:out value="${k}"/>"><c:out value="${k}"/>/10</option>
										<c:set var="k" value="${k-1}"/>
									</c:forEach>
								</select>
							</td>
						</tr>
						<tr>
							<td class="detailTableTitle_center">문항 4</td>
							<td class="detailTableField_left">   연수 진행중 방문업체, 숙박, 교통편 등 예정된 일정이 잘 준수 되었습니까 ?</td>
							<td>
								<select name="answer4" class="selectbox" style="width: 100%">
									<c:set var="k" value="10"/>
									<c:forEach begin="0" end="10" step="1" varStatus="j">
										<option value="<c:out value="${k}"/>"><c:out value="${k}"/>/10</option>
										<c:set var="k" value="${k-1}"/>
									</c:forEach>
								</select>
							</td>
						</tr>
						<tr>
							<td class="detailTableTitle_center">문항 5</td>
							<td class="detailTableField_left">   연수 과정 중 제공된 숙박 및 음식에 만족하십니까 ?</td>
							<td>
								<select name="answer5" class="selectbox" style="width: 100%"> 
									<c:set var="k" value="10"/>
									<c:forEach begin="0" end="10" step="1" varStatus="j">
										<option value="<c:out value="${k}"/>"><c:out value="${k}"/>/10</option>
										<c:set var="k" value="${k-1}"/>
									</c:forEach>
								</select>
							</td>
						</tr>
						<tr>
							<td class="detailTableTitle_center">문항 6</td>
							<td class="detailTableField_left">   사전정보제공(현지날씨, 준비물, 방문기업정보)에 만족하십니까 ?</td>
							<td>
								<select name="answer6" class="selectbox" style="width: 100%">
									<c:set var="k" value="10"/>
									<c:forEach begin="0" end="10" step="1" varStatus="j">
										<option value="<c:out value="${k}"/>"><c:out value="${k}"/>/10</option>
										<c:set var="k" value="${k-1}"/>
									</c:forEach>
								</select>
							</td>
						</tr>
						<tr>
							<td class="detailTableTitle_center">문항 7</td>
							<td class="detailTableField_left">   연수 진행시 전반적인 진행 및 전문성에 대한 만족도는 ?</td>
							<td>
								<select name="answer7" class="selectbox" style="width: 100%">
									<c:set var="k" value="10"/>
									<c:forEach begin="0" end="10" step="1" varStatus="j">
										<option value="<c:out value="${k}"/>"><c:out value="${k}"/>/10</option>
										<c:set var="k" value="${k-1}"/>
									</c:forEach>
								</select>
							</td>
						</tr>
						<tr>
							<td class="detailTableTitle_center">문항 8</td>
							<td class="detailTableField_left">   연수 진행시 통역의 언어구사력 및 전문지식에 대한 만족도는 ?</td>
							<td>
								<select name="answer8" class="selectbox" style="width: 100%">
									<c:set var="k" value="10"/>
									<c:forEach begin="0" end="10" step="1" varStatus="j">
										<option value="<c:out value="${k}"/>"><c:out value="${k}"/>/10</option>
										<c:set var="k" value="${k-1}"/>
									</c:forEach>
								</select>
							</td>
						</tr>
						<tr>
							<td class="detailTableTitle_center">문항 9</td>
							<td class="detailTableField_left">   연수 진행시 가이드의 성실성, 친절성, 적극성에 대한 만족도는 ?</td>
							<td>
								<select name="answer9" class="selectbox" style="width: 100%">
									<c:set var="k" value="10"/>
									<c:forEach begin="0" end="10" step="1" varStatus="j">
										<option value="<c:out value="${k}"/>"><c:out value="${k}"/>/10</option>
										<c:set var="k" value="${k-1}"/>
									</c:forEach>
								</select>
							</td>
						</tr>
						<tr>
							<td class="detailTableTitle_center">문항 10</td>
							<td class="detailTableField_left">   귀하께서는 본 연수 과정을 타인에게 추천하실 의향이 얼마나 되십니까? </td>
							<td>
								<select name="answer10" class="selectbox" style="width: 100%">
									<c:set var="k" value="10"/>
									<c:forEach begin="0" end="10" step="1" varStatus="j">
										<option value="<c:out value="${k}"/>"><c:out value="${k}"/>/10</option>
										<c:set var="k" value="${k-1}"/>
									</c:forEach>
								</select>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td height="7"></td>
			</tr>
			<tr>
				<td>
					<table width="100%" height="36" bgcolor="#F3F3F3" cellpadding="0" cellspacing="0">
						<tr>
							<td>
								<div class="btNbox txtR">
									<a class="btNe006bc6 txt2btn" href="#" onclick="doSave()">등록요청</a>
								</div>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>