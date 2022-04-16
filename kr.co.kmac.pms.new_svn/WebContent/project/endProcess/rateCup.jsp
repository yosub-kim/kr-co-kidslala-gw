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
	<form name="rateFrm" method="post">
				<!-- 상향,하향평가 -->
			<div style="display:none;">
				<input type="hidden" name="evalChk" value="up">
			</div>
			<!-- location -->
			<div class="location">
				<p class="menu_title">Member 평가</p>
				<ul>
					<li class="home">HOME</li>
					<li>Member 평가</li>
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
						<p class="h1">Member 평가 항목</p>
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
								<td class="a-c">1. 협조성</td>
								<td> 조직의 일원으로서 동료 및 리더/파트리더에 협력하며 프로젝트의 전체적 성공에 부응하려 하는가?</td>
							</tr>
							<tr>
								<td class="a-c">2. 책임감</td>
								<td> 자기 역할과 책임을 충분히 인식하고 기대에 부응하려는 노력을 하며 R&R, 책임회피나 전가를 하지 않는가?</td>
							</tr>
							<tr>
								<td class="a-c">3. 시너지</td>
								<td> 팀 구성원 간 개선방향을 함께 논의하고 보다 나은 대안을 도출하기 위해 적극적으로 협조하였는가?</td>
							</tr>
							</tbody>
					</table>
				</div>
			</div>
			
			<div class="board_box">
				<div class="title_both">
					<div class="h1_area">
						<p class="h1">Member 평가 채점</p>
					</div>
					<div class="text-R">
						<button type="button" class="btn line btn_pink" onclick="row_delete();"><i class="mdi mdi-trash-can-outline">행 삭제</i></button>
					</div>
				</div>
				<div class="board_contents">
					<table  id="projectProgressTable" class="tbl-edit td-c pd-none"><!-- td 영역이 가운데 정렬 일 경우 td-c -->
						<thead>
							<colgroup>
								<col style="width: 10%" />
								<col style="width: 15%" />
								<col style="width: *" />
								<col style="width: *" />
								<col style="width: *" />
							</colgroup>
							<tr>
								<th>선 택</th>
								<th>성 명</th>
								<th>협조성</th>
								<th>책임감</th>
								<th>시너지</th>
							</tr>
						</thead>
						<tbody>
							<c:if test="${!empty result.list}">
								<c:forEach var="result" items="${result.list}">
									<tr height="25px">
										<td>
											<input type="checkbox" name="chkMember" id="<c:out value="${result.ssn }" />" value="<c:out value="${result.ssn}" />" class="btn_check">
											<label for ="<c:out value="${result.ssn }" />"></label>
											<input type="hidden" name="ssn" value="<c:out value="${result.ssn}" />">
										</td>
										<td><c:out value="${result.name}" /></td>
										<c:forEach begin="1" end="3" varStatus="i">
										<td>
											<select name="answer<c:out value="${i.count}"/>Array" class="selectbox">
												<c:set var="k" value="10"/>
												<c:forEach begin="0" end="5" step="1" varStatus="j"><option value="<c:out value="${k}"/>"><c:out value="${k}"/>/10</option><c:set var="k" value="${k-2}"/></c:forEach>
											</select>
										</td>
										</c:forEach>
									<div style="display:none;">
										<input type="hidden" name="answer<c:out value="${4}"/>Array" value="">
										<input type="hidden" name="answer<c:out value="${5}"/>Array" value="">
										<input type="hidden" name="answer<c:out value="${6}"/>Array" value="">
										<input type="hidden" name="answer<c:out value="${7}"/>Array" value="">
										<input type="hidden" name="answer<c:out value="${8}"/>Array" value="">
										<input type="hidden" name="answer<c:out value="${9}"/>Array" value="">
										<input type="hidden" name="answer<c:out value="${10}"/>Array" value="">
									</div>
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
						<p class="h1">Member 평가 의견</p>
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
						<tbody id="tbody_Comment">
							<c:if test="${!empty result.list}">
								<c:forEach var="result" items="${result.list}">
								<tr height="25px" id="tr_<c:out value="${result.ssn}" />">
									<td><c:out value="${result.name}" /></td>
									<td colspan="3">
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
	</table>
</body>
</html>