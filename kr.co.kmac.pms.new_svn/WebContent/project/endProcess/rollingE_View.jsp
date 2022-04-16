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
<!-- 서브페이지에서만 사용 -->
<link rel="stylesheet" href="/resources/css/selectric.css" type="text/css" />
<link rel="stylesheet" href="/resources/css/dev.css" type="text/css" />
<meta name="viewport" content="width=1600">
<script type="text/javascript">
<%-- 개별 스크립트 영역 --%>

function doSave(){

	var ActionURL = "/action/ProjectEndingAction.do";	
	ActionURL += "?mode=insertRollingE";
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
<!-- 프로젝트 일정 관리 -->
		<div id="tab_menu_content">
			<div class="board_box">
				<div class="title">
					<div class="h1_area">
						<p class="h1">프로젝트 개요</p>
					</div>
				</div>
				
				<div class="board_contents">
					<table  id="projectProgressTable" class="tbl-view"><!-- td 영역이 가운데 정렬 일 경우 td-c -->
						<colgroup>
							<col style="width: 15%"/>
							<col style="width: *"/>
						</colgroup>
						<tr>
							<th class="detailTableField_center">프로젝트 명</th>
							<td>[<c:out value="${project.projectCode }"/>] <c:out value="${project.projectName}"/></td>
							<input type="hidden" name="projectCode" value="<c:out value="${project.projectCode}"/>">
							</tr>
					</table>
				</div>
			</div>
			
			<div class="board_box">
				<div class="title">
					<div class="h1_area">
						<p class="h1">강사 만족도</p>
					</div>
				</div>
				
				<div class="board_contents">
					<table  id="projectProgressTable" class="tbl-edit td-c"><!-- td 영역이 가운데 정렬 일 경우 td-c -->
						<colgroup>
							<col style="width: 15%"/>
							<col style="width: 17%"/>
							<col style="width: 17%"/>
							<col style="width: 17%"/>
							<col style="width: 17%"/>
							<col style="width: 17%"/>
						</colgroup>
						<tr>
							<th>성명</th>
							<th>강의 교재</th>
							<th>강의 준비</th>
							<th>강의 스킬</th>
							<th>성의 및 열의</th>
							<th>평가</th>
						</tr>
						<tbody>
							<c:if test="${!empty result2.list}">
								<c:forEach var="rs1" items="${result2.list}">
								<tr>
									<td><c:out value="${rs1.name}"/></td>
									<td><c:out value="${rs1.answer8}"/> / 25</td>
									<td><c:out value="${rs1.answer9}"/> / 25</td>
									<td><c:out value="${rs1.answer10}"/> / 25</td>
									<td><c:out value="${rs1.answer12}"/> / 25</td>
									<td align="right" style="padding-right:5pt"><b><c:out value="${rs1.estimateC}"/> 점</b></td>
								</tr>
								</c:forEach>
							</c:if>
							<c:if test="${empty result2.list}">
								<td align="center"  colspan="6">평가 대상 강사가 없습니다.</td>
							</c:if>
						</tbody>
					</table>
				</div>
			</div>
			
			<div class="board_box">
				<div class="title">
					<div class="h1_area">
						<p class="h1">교육 만족도</p>
					</div>
				</div>
				
				<div class="board_contents">
					<table  id="projectProgressTable" class="tbl-view"><!-- td 영역이 가운데 정렬 일 경우 td-c -->
						<colgroup>
							<col style="width: *"/>
							<col style="width: 20%"/>
						</colgroup>
						<tr>
							<th>항목</th>
							<th>배점</th>
						</tr>
					<tbody>
					<c:forEach var="rs" items="${result.list}">
						<tr>
							<td class=detailTableField_left>내용의 체계성(배점 20점)</td>
							<td class="detailTableField_right"><c:out value="${rs.answer1}"/> 점&nbsp;</td>
						</tr>
						<tr>
							<td class="detailTableField_left">현업 활용도(배점 20점)</td>
							<td class="detailTableField_right"><c:out value="${rs.answer2}"/> 점&nbsp;</td>
						</tr>
						<tr>
							<td class="detailTableField_left">교육시설(교육장, 교육기자재)(배점 10점)</td>
							<td class="detailTableField_right"><c:out value="${rs.answer3}"/> 점&nbsp;</td>
						</tr>
						<tr>
							<td class="detailTableField_left">교육진행(배점 20점)</td>
							<td class="detailTableField_right"><c:out value="${rs.answer4}"/> 점&nbsp;</td>
						</tr>
						<tr>
							<td class="detailTableField_left">부대시설(휴게실, 화장실 등)(배점 10점)</td>
							<td class="detailTableField_right"><c:out value="${rs.answer5}"/> 점&nbsp;</td>
						</tr>
						<tr>
							<td class="detailTableField_left">전반적 만족도(배점 20점)</td>
							<td class="detailTableField_right"><c:out value="${rs.answer6}"/> 점&nbsp;</td>
						</tr>
						<tr>
							<td class="detailTableTitle_center">평가</td>
							<td class="detailTableField_right"><b><c:out value="${rs.sum}"/> 점</b>&nbsp;</td>
						</tr>
					</c:forEach>
					</tbody>
					</table>
				</div>
			</div>
		</div>
	</table>
</body>
</html>