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
function row_delete() {
	var sfrm = document.rateFrm;
	var pComment = document.getElementById("tbody_Comment");
	for(var i = 0; i < sfrm.elements.length; i++){
		var ele = sfrm.elements[i];
		if(ele.name == "chkMember"){
			if(ele.checked){
				var ssn = ele.value;
				ele.parentNode.parentNode.parentNode.removeChild(ele.parentNode.parentNode);
				for(var j = 0; j < pComment.childNodes.length;j++){
					var child = pComment.childNodes[j];
					if(child.id == "tr_" + ssn) {
						pComment.removeChild(child);
					}
				}
			}
		} 
	}
}
function doSave(){

	var ActionURL = "/action/ProjectEndingAction.do";	
	ActionURL += "?mode=insertRateC";
	var sFrm = document.forms["rateFrm"];
	

	var status = AjaxRequest.submit(
			sFrm
			,{ 'url':ActionURL
				,'onSuccess':function(obj){
					document.location = "/action/WorkCabinetAction.do?mode=getMyWorkList";
					alert("등록하였습니다.");
				}
				,'onLoading':function(obj){}
				,'onError':function(obj){
					alert("이미 컨설턴트 평가가 완료되었으므로 업무를 실행할 수 없습니다.\n관리자에게 문의하시기 바랍니다.");
				}
			}
	); status = null;
}
</script>
</head>
<body>

<!-- location -->
	<div class="location">
		<p class="menu_title">컨설턴트 평가</p>
		<ul>
			<li class="home">HOME</li>
			<li>컨설턴트 평가</li>
		</ul>
	</div>
	
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
					<p class="h1">컨설턴트 평가 항목 및 채점</p>
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
						<td class="a-c">1. 직무지식</td>
						<td> 프로젝트 수행 시 담당 직무 수행에 직접적으로 필요한 이론지식, 실무지식이 있는가?</td>
					</tr>
					<tr>
						<td class="a-c">2. 정보수집 분석력</td>
						<td>프로젝트 수행에 직,간접적으로 필요한 정보를 수집, 분석, 유용하게 자료화하는 능력을 지니고 있는가?</td>
					</tr>
					<tr>
						<td class="a-c">3. 응용개선능력</td>
						<td>습득한 업무지식을 응용하여 문제점을 파악하고 보다 효과적인 해결방법을 제시하는 능력이 있는가?</td>
					</tr>
					<tr>
						<td class="a-c">4. 이해력</td>
						<td>문제나 상황을 신속, 정확하게 파악하고 리더의 지시나 업무 범위 등을 잘 이해할 수 있는가?</td>
					</tr>
					<tr>
						<td class="a-c">5. 수행력</td>
						<td>주어진 업무 목표를 일정에 맞게 완수하여 명확한 결과를 도출하고 그 결과물을 산출할 수 있는가?</td>
					</tr>
					<tr>
						<td class="a-c">6. 판단력</td>
						<td>리더 지시에 대한 요점을 잘 파악하여 적절한 판단을 하고 올바른 결론과 행동을 취하고 있는가?</td>
					</tr>
					<tr>
						<td class="a-c">7. 표현력</td>
						<td>전달하려는 생각이나 의도를 문서 또는 구두로 논리적이며 구체적으로 표현하고 납득시킬 수 있는가?</td>
					</tr>
					<tr>
						<td class="a-c">8. 협조성</td>
						<td>조직의 일원으로서 동료 및 리더에 협력하며 프로젝트의 전체적 성공에 부응하려 하는가?</td>
					</tr>
					<tr>
						<td class="a-c">9. 책임감</td>
						<td>자기 역할과 책임을 충분히 인식하고 기대에 부응하려는 노력을 하며 책임회피나 전가를 하지 않는가?</td>
					</tr>
					<tr>
						<td class="a-c">10. 도전의식</td>
						<td>어렵고 힘든 일이라도 실패를 두려워하지 않고 능동적이며 과감하게 도전하여 성취하려고 하는가?</td>
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
				<div class="text-R">
					<button type="button" class="btn btn_pink" onclick="row_delete()">행 삭제</button>
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
						<th>선택</th>
						<th>성명</th>
						<th>직무지식</th>
						<th>정보수집 분석력</th>
						<th>응용개선능력</th>
						<th>이해력</th>
						<th>수행력</th>
						<th>판단력</th>
						<th>표현력</th>
						<th>협조성</th>
						<th>책임감</th>
						<th>도전의식</th>
					</tr>
					</thead>			
					<tbody>
						<c:if test="${!empty result.list}">
							<c:forEach var="result" items="${result.list}">
								<tr>
									<td>
										<input type="checkbox" name="chkMember" value="<c:out value="${result.ssn}" />" id="<c:out value="${result.ssn}" />"class="btn_check"><label for="<c:out value="${result.ssn }" />" />
										<input type="hidden" name="ssn" value="<c:out value="${result.ssn}" />">
									</td>
									<td><c:out value="${result.name}" /></td>
									<c:forEach begin="1" end="10" varStatus="i">
									<td>
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