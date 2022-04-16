<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>
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
<%-- 개별 스크립트 영역 --%>
function doSave(){

	var ActionURL = "/action/ProjectEndingAction.do";	
	ActionURL += "?mode=insertRatePartP";
	var sFrm = document.forms["rateFrm"];
	

	var status = AjaxRequest.submit(
			sFrm
			,{ 'url':ActionURL
				,'onSuccess':function(obj){
					alert("등록하였습니다.");
					document.location = "/action/WorkCabinetAction.do?mode=getMyWorkList";
				}
				,'onLoading':function(obj){}
				,'onError':function(obj){
					alert("이미 PL 평가가 완료되었으므로 업무를 실행할 수 없습니다.\n관리자에게 문의하시기 바랍니다.");
				}
			}
	); status = null;
}
</script>
</head>
<body>
<%-- 작업영역 --%>
	<form name="rateFrm" method="post">
			<!-- location -->
			<div class="location">
				<p class="menu_title">PL 평가</p>
				<ul>
					<li class="home">HOME</li>
					<li>PL 평가</li>
				</ul>
			</div>
			<!-- // location -->
	
	
	 <div class="contents sub">
	
		<!-- 신규 프로젝트 등록 -->
		<div class="board_box">
			<div class="title_both">
				<div class="h1_area">
					<p class="h1">프로젝트 정보</p>
				</div>
				<div class="text-R">
					<button type="button" class="btn line btn_blue" onclick="doSave()"><i class="mdi mdi-square-edit-outline"></i>등록요청</button>
				</div>
			</div>
			
			<div class="board_contents">
				<table  id="projectProgressTable" class="tbl-edit td-c"><!-- td 영역이 가운데 정렬 일 경우 td-c -->
							<colgroup>
								<col style="width: 15%"/>
								<col style="width: *" />
							</colgroup>
							<tr>
								<th>프로젝트 명</th>
								<td>
								<c:out value="${project.projectName}"/>
								<input type="hidden" name="taskId" value="<c:out value="${taskId}" />" />
								<input type="hidden" name="projectCode" value="<c:out value="${project.projectCode}"/>">
								<input type="hidden" name="gubun" value="T">
								</td>
							</tr>
					</table>
				</div>
			</div>
				
			<div class="board_box">
				<div class="title_both">
					<div class="h1_area">
						<p class="h1">PL 평가 항목</p>
					</div>
				</div>
			
				<div class="board_contents">
					<table  id="projectProgressTable" class="tbl-edit"><!-- td 영역이 가운데 정렬 일 경우 td-c -->
								<thead>
									<colgroup>
										<col style="width: 25%" />
										<col style="width: *" />
									</colgroup>
									<tr>
										<th>항 목</th>
										<th>정 의</th>	
									</tr>
								</thead>
								<tbody id="ListData">
							<tr>
								<td class="a-c">1. 직무지식</td>
								<td> 프로젝트 수행 시 담당 직무 수행에 직접적으로 필요한 이론지식, 실무지식이 있는가?</td>
							</tr>
							<tr>
								<td class="a-c">2. 이해력</td>
								<td> 문제나 상황을 신속, 정확하게 파악하고 리더의 지시나 업무 범위 등을 잘 이해할 수 있는가?</td>
							</tr>
							<tr>
								<td class="a-c">3. 수행력</td>
								<td> 주어진 자원을 통해 업무 목표를 일정에 맞게 완수하며, 명확한 결과를 도출하고 그 결과물을 산출할 수 있는가?</td>
							</tr>
							<tr>
								<td class="a-c">4. 표현력</td>
								<td> 전달하려는 생각이나 의도를 문서 또는 구두로 논리적이며 구체적으로 표현하고 납득시킬 수 있는가?</td>
							</tr>
							<tr>
								<td class="a-c">5. 책임감</td>
								<td> 자기 역할과 책임을 충분히 인식하고 기대에 부응하려는 노력을 하며 책임회피나 전가를 하지 않는가?</td>
							</tr>
							</tbody>
					</table>
				</div>
			</div>
			
			<div class="board_box">
				<div class="title_both">
					<div class="h1_area">
						<p class="h1">PL 평가 채점</p>
					</div>
				</div>
			
				<div class="board_contents">
					<table  id="projectProgressTable" class="tbl-edit td-c pd-none"><!-- td 영역이 가운데 정렬 일 경우 td-c -->
						<thead>
							<colgroup>
								<col style="width: 15%" />
								<col style="width: *" />
								<col style="width: *" />
								<col style="width: *" />
								<col style="width: *" />
								<col style="width: *" />
							</colgroup>
							<tr>
								<th>성명</th>
								<th>납기 준수</th>
								<th>원가 관리</th>
								<th>품질 유지</th>
								<th>고객 관리</th>
								<th>후배 육성</th>
							</tr>
						</thead>
						<tbody>
							<c:if test="${!empty result.list}">
								<c:forEach var="result" items="${result.list}">
								<tr>
									<td>
										<c:out value="${result.name}" />
										<input type="hidden" name="ssn" value="<c:out value="${result.ssn}" />">
									</td>
									<c:forEach begin="1" end="5" varStatus="i">
										<td>
											<select name="answer<c:out value="${i.count}"/>Array" class="selectbox">
												<c:set var="k" value="10"/>
												<c:forEach begin="0" end="5" step="1" varStatus="j"><option value="<c:out value="${k}"/>"><c:out value="${k}"/>/10</option><c:set var="k" value="${k-2}"/></c:forEach>
											</select>
										</td>
									</c:forEach>
									<c:forEach begin="6" end="10" varStatus="i">
										<input type="hidden" name="answer<c:out value="${6}"/>Array" value="">
										<input type="hidden" name="answer<c:out value="${7}"/>Array" value="">
										<input type="hidden" name="answer<c:out value="${8}"/>Array" value="">
										<input type="hidden" name="answer<c:out value="${9}"/>Array" value="">
										<input type="hidden" name="answer<c:out value="${10}"/>Array" value="">
										
									</c:forEach>
								</tr>
								</c:forEach>
							</c:if>
						</tbody>
					</table>
				</div>
			</div>
				
			<div class="board_box">
				<div class="title_both">
					<div class="h1_area">
						<p class="h1">PL 평가 의견</p>
					</div>
				</div>
			
				<div class="board_contents">
					<table  id="projectProgressTable" class="tbl-edit td-c"><!-- td 영역이 가운데 정렬 일 경우 td-c -->
						<thead>
							<colgroup>
								<col style="width: 15%" />
								<col style="width: *" />
								<col style="width: *" />
								<col style="width: *" />
								<col style="width: *" />
								<col style="width: *" />
							</colgroup>
							<tr>
								<th>성 명</th>
								<th colspan="3">의 견</th>
								<th colspan="2">이 컨설턴트를 추천하고 다음에도 함께 프로젝트를 수행할 의사가 있는가?</th>
							</tr>
						</thead>
						<tbody>
							<c:if test="${!empty result.list}">
								<c:forEach var="result" items="${result.list}">
								<tr>
									<td><c:out value="${result.name}" /></td>
									<td align="left" colspan="3">
										<input type="text" name="commentArray" class="textInput_left" style="width:100%">
									</td>
									<td colspan="2">
										<select name="memberChk" class="selectbox">
											<option value="Y">예</option>
											<option value="N">아니요</option>
										</select>
									</td>
								</tr>
								</c:forEach>
							</c:if>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</form>
</body>
</html>