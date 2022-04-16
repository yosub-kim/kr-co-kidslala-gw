<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<html>
<head>
<title>프로젝트 고객만족도 정보</title>
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

</script>
</head>
<body>
<%-- 작업영역 --%>

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
						<p class="h1">프로젝트 만족도</p>
					</div>
				</div>
				
				<div class="board_contents">
					<table  id="projectProgressTable" class="tbl-edit td-c"><!-- td 영역이 가운데 정렬 일 경우 td-c -->
						<thead>
						<colgroup>
							<col style="width: 25%"/>
							<col style="width: 25%"/>
							<col style="width: 25%"/>
							<col style="width: 25%"/>
						</colgroup>
						<tr>
							<th>고객사</th>
							<th>담당자</th>
							<th>항목</th>
							<th>배점</th>
						</tr>
						</thead>
						<c:set var="rolling" value="${result.list[0]}"/>
						<tbody>
								<tr>
									<td rowspan="4"><c:out value="${rolling.customerName}"/></td>
									<td rowspan="4"><c:out value="${rolling.dept}"/><p><c:out value="${rolling.answer2}"/><p><c:out value="${rolling.name}"/></td>
									<td>전반적 만족도 (배점 25점)</td>
									<td><c:choose><c:when test="${empty result.list}">-</c:when><c:otherwise><c:out value="${rolling.answer3}"/> 점&nbsp;</c:otherwise></c:choose></td>
								</tr>
								<tr>
									<td><c:choose><c:when test="${project.processTypeCode=='E4'}">골드파이어 활용 만족도</c:when><c:when test="${project.processTypeCode=='DE'}">현업 적용 도움 여부</c:when><c:otherwise>계약내용준수</c:otherwise></c:choose> (배점 25점)</td>
									<td><c:choose><c:when test="${empty result.list}">-</c:when><c:otherwise><c:out value="${rolling.answer5}"/> 점&nbsp;</c:otherwise></c:choose></td>
								</tr>
								<tr>	
									<td><c:choose><c:when test="${project.processTypeCode=='N4'}">현업 활용도</c:when><c:when test="${project.processTypeCode=='DE'}">숙박, 교통 및 음식 만족도</c:when><c:otherwise>산출물 만족도</c:otherwise></c:choose> (배점 25점)</td>
									<td><c:choose><c:when test="${empty result.list}">-</c:when><c:otherwise><c:out value="${rolling.answer4}"/> 점&nbsp;</c:otherwise></c:choose></td>
								</tr>
								<tr>	
									<td>추천의향 (배점 25점)</td>
									<td><c:choose><c:when test="${empty result.list}">-</c:when><c:otherwise><c:out value="${rolling.answer4}"/> 점&nbsp;</c:otherwise></c:choose></td>
								</tr>
								<tr>
									<td colspan="3">평가</td>
									<td><c:choose><c:when test="${empty result.list}">평가 결과 없음</c:when><c:otherwise><b><c:out value="${rolling.sum}"/> 점</b>&nbsp;</c:otherwise></c:choose></td>
								</tr>
						</tbody>
					</table>
				</div>
			</div>
			
			<div class="board_box">
				<div class="title">
					<div class="h1_area">
						<p class="h1">컨설턴트 만족도</p>
					</div>
				</div>
				
				<div class="board_contents">
					<table  id="projectProgressTable" class="tbl-edit td-c"><!-- td 영역이 가운데 정렬 일 경우 td-c -->
						<textarea class="textArea" style="width:100%; height:200px;" readonly><c:out value="${rolling.answer7}"/></textarea>
					</table>
				</div>
			</div>
	</div>
</body>
</html>