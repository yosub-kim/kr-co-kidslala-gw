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

<script type="text/javascript">
<%-- 개별 스크립트 영역 --%>
function doSave(){

	var ActionURL = "/action/ProjectEndingAction.do";	
	ActionURL += "?mode=insertRateP";
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
	<div class="location">
		<p class="menu_title">PL 평가</p>
		<ul>
			<li class="home">HOME</li>
			<li>PL 평가</li>
		</ul>
	</div>
	
<%-- 작업영역 --%>

<div class="contents sub">
		<form name="rateFrm" method="post">
			<div class="board_box">
				<div class="title_both">
					<div class="h1_area">
						<p class="h1">프로젝트 개요</p>
					</div>
					<div class="text-R">
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
					<p class="h1">PL 평가 항목 및 채점</p>
				</div>
			</div>
				
			<div class="board_contents">
				<table class="tbl-edit"><!-- td 영역이 가운데 정렬 일 경우 td-c -->
					<colgroup>
						<col style="width: 20%"/>
						<col style="width: *"/>
					</colgroup>
					<tbody>
					<tr>
						<th>항 목</th>
						<th>항목의 정의</th>
					</tr>
					<tr>
						<td class="a-c">1. 방법론 제시력</td>
						<td>성공적 프로젝트 수행을 위한 효율적이고 효과적인 방법론을 제시하거나 창출할 수 있는가?</td>
					</tr>
					<tr>
						<td class="a-c">2. 문제 해결력</td>
						<td>프로젝트 목적을 잊지 않고 문제의 본질을 잘 이해하며 잠재적 위협 요인을 잘 파악할 수 있는가?</td>
					</tr>
					<tr>
						<td class="a-c">3. 대안 창출력</td>
						<td>문제의 정확한 해결안이나 현재나 미래에 대한 의사결정을 지원할 수 있는 전략 대안 도출이 가능한가?</td>
					</tr>
					<tr>
						<td class="a-c">4. 납기준수</td>
						<td>프로젝트 관리 기준을 잘 준수하고 멤버들의 일정 관리를 세심히 살펴 납기를 잘 지키고 있는가?</td>
					</tr>
					<tr>
						<td class="a-c">5. 원가관리</td>
						<td>프로젝트의 원가와 이익 개념을 잘 파악하고 있으며 비용 상승 요인에 대한 통제력을 지니고 있는가?</td>
					</tr>
					<tr>
						<td class="a-c">6. 품질유지</td>
						<td>프로젝트 결과 산출물 뿐만 아니라 진행 과정 상의 제반 품질 기준을 잘 지키고 있는가?</td>
					</tr>
					<tr>
						<td class="a-c">7. 고객리더십</td>
						<td>문제점을 고객보다 먼저 파악하거나 미래 니즈를 창출하는 등 고객을 선도하는 입장에 서 있는가?</td>
					</tr>
					<tr>
						<td class="a-c">8. 불만 관리</td>
						<td>고객의 불만 요인을 미리 제거하거나 또는 조기에 발견하며, 불만 제기 시 신속한 대응이 가능한가?</td>
					</tr>
					<tr>
						<td class="a-c">9. 책임감</td>
						<td>프로젝트의 총 책임자로서의 역할 인식이 분명하며 잘못이 있을 경우 책임 회피나 전가를 하지 않는가?</td>
					</tr>
					<tr>
						<td class="a-c">10. 후배육성</td>
						<td>멤버들의 육성에 구체적으로 최선을 다하며 항상 시너지를 발휘하기 위한 팀 빌딩을 고민하고 있는가?</td>
					</tr>
					</tbody>
				</table>
			</div>
		</div>
		<div class="board_box">
			<div class="title_both">
				<div class="h1_area">
					<p class="h1">컨설턴트 평가 결과</p>
				</div>
			</div>
			<div class="board_contents">
				<table class="tbl-edit td-c pd-none"><!-- td 영역내 여백 없을 경우 .pd-none -->
					<thead>
					<colgroup>
						<col style="*%" />
						<col style="8%" />
						<col style="8%" />
						<col style="8%" />
						<col style="8%" />
						<col style="8%" />
						<col style="8%" />
						<col style="8%" />
						<col style="8%" />
						<col style="8%" />
						<col style="8%" />									
					</colgroup>	
					<tr>
						<th>성명</th>
						<th>방법론제시력</th>
						<th>문제해결력</th>
						<th>대안창출력</th>
						<th>납기준수</th>
						<th>원가관리</th>
						<th>품질유지</th>
						<th>고객리더십</th>
						<th>불만관리</th>
						<th>책임감</th>
						<th>후배육성</th>
					</tr>
					</thead>			
					<tbody>
						<c:if test="${!empty result.list}">
							<c:forEach var="result" items="${result.list}">
							<tr height="25px">
								<td>
									<c:out value="${result.name}" />
									<input type="hidden" name="ssn" value="<c:out value="${result.ssn}" />">
								</td>
								<c:forEach begin="1" end="10" varStatus="i">
								<td class="detailTableField_left">
									<select name="answer<c:out value="${i.count}"/>Array" class="selectbox">
										<c:set var="k" value="10"/>
										<c:forEach begin="0" end="10" step="1" varStatus="j"><option value="<c:out value="${k}"/>"><c:out value="${k}"/>/10</option><c:set var="k" value="${k-1}"/></c:forEach>
									</select>
								</td>
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
					<p class="h1">컨설턴트 평가 의견 작성</p>
				</div>
			</div>
				
			<div class="board_contents">
				<table  id="projectProgressTable" class="tbl-edit td-c"><!-- td 영역이 가운데 정렬 일 경우 td-c -->
					<thead>
					<colgroup>
						<col style="width: 20%"/>
						<col style="width: *"/>
					</colgroup>
					<tr>
						<th>성명</th>
						<th>의견</th>
					</tr>
					</thead>
					<tbody id="tbody_Comment">
					<c:if test="${!empty result.list}">
						<c:forEach var="result" items="${result.list}">
						<tr height="25px" id="tr_<c:out value="${result.ssn}" />">
							<td><c:out value="${result.name}" /></td>
							<td>
								<input type="text" name="commentArray" class="textInput_left" style="width:100%">
							</td>
						</tr>
						</c:forEach>
					</c:if>
					</tbody>
				</table>
			</div>
		</div>
	</form>
</div>
</body>
</html>