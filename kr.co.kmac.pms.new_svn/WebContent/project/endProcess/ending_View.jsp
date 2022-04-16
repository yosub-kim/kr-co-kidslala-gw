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
</script>
</head>
<body>
<%-- 작업영역 --%>

		<form name="endingFrm" method="post">
		<!-- Hidden 필드 시작 -->
		<input type="hidden" name="projectCode" id="projectCode" value="<c:out value="${projectSummaryData.projectCode}" />">
		<input type="hidden" name="endGubun" value="">
		<!-- Hidden 필드 종료 -->
		
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
							<col style="width: 9%"/>
							<col style="width: 24%"/>
							<col style="width: 9%"/>
							<col style="width: 24%"/>
							<col style="width: 9%"/>
							<col style="width: 24%"/>
						</colgroup>
						<tr>
							<th class="detailTableField_center">프로젝트 명</th>
							<td>[<c:out value="${projectSummaryData.projectCode}" />] <c:out value="${projectSummaryData.projectName}" /></td>
							<th class="detailTableField_center">부서</th>
							<td><c:out value="${projectSummaryData.runningDivName}" escapeXml="false" /></td>
							<th class="detailTableField_center">추진기간</th>
							<td>
								<fmt:parseDate value="${projectSummaryData.realStartDate}" pattern="yyyyMMdd" var="realStartDate" />
								<fmt:formatDate value="${realStartDate}" pattern="yyyy-MM-dd" dateStyle="short" var="formatDate1" />
								<fmt:parseDate value="${projectSummaryData.realEndDate}" pattern="yyyyMMdd" var="realEndDate" />
								<fmt:formatDate value="${realEndDate}" pattern="yyyy-MM-dd" dateStyle="short" var="formatDate2" />
								<c:out value="${formatDate1}" /> ~ <c:out value="${formatDate2}" />
							</td>													
						</tr>
						<tr>
							<th class="detailTableField_center">고객사</th>
							<td><c:out value="${projectSummaryData.customerName}" /></td>
							<th class="detailTableField_center">산업구분</th>
							<td><code:code code="${projectSummaryData.industryTypeCode}" /></td>
							<th class="detailTableField_center">비즈니스 타입</th>
							<td><code:code tableName="BUSINESS_TYPE_CODE" code="${projectSummaryData.businessTypeCode}" />(<code:code code="${projectSummaryData.projectDetailCode}" />)</td>												
						</tr>
						<tr>
							<th>PM / PL</th>
							<td><expertPool:exportname ssn="${projectSummaryData.pmName}" /><c:if test="${projectSummaryData.plName != ''}"> / </c:if><expertPool:exportname ssn="${projectSummaryData.plName}" /></td>
							<th>컨설턴트</th>
							<td colspan="3"><c:out value="${projectSummaryData.memberName}" /></td>
						</tr>
					</table>
				</div>
			</div>
				
				<div class="board_box">
				<div class="title">
					<div class="h1_area">
						<p class="h1">프로젝트 리뷰 내용</p>
					</div>
				</div>
				
				<div class="board_contents">
					<table  id="projectProgressTable" class="tbl-view"><!-- td 영역이 가운데 정렬 일 경우 td-c -->
						<colgroup>
							<col style="width: 15%"/>
							<col style="width: *"/>
						</colgroup>
						<tr>
							<th><c:out value="${objTitle.strA}" escapeXml="false"/></th>
							<td>
								<textarea name="endBackground" style="width:100%; height: 98%;" class="textArea" readonly><c:out value="${ending.endBackground}" escapeXml="false"/></textarea>
							</td>
						</tr>
						<tr>
							<th><c:out value="${objTitle.strB}" escapeXml="false"/></th>
							<td>
								<textarea name="endResult" style="width:100%; height: 98%;" class="textArea" readonly><c:out value="${ending.endResult}" escapeXml="false"/></textarea>
							</td>
						</tr>
						<tr>
							<th><c:out value="${objTitle.strC}" escapeXml="false"/></th>
							<td>
								<textarea name="endReview" style="width:100%; height: 98%;" class="textArea" readonly><c:out value="${ending.endReview}" escapeXml="false"/></textarea>
							</td>
						</tr>
						<tr>
							<th><c:out value="${objTitle.strD}" escapeXml="false"/></th>
							<td>
								<textarea name="endSuggestion" style="width:100%; height: 98%;" class="textArea" readonly><c:out value="${ending.endSuggestion}" escapeXml="false"/></textarea>
							</td>
						</tr>
					</table>
				</div>
				</div>
				
			<div class="board_box">
				<div class="title">
					<div class="h1_area">
						<p class="h1">프로젝트 산출물</p>
					</div>
				</div>
				
				<div class="board_contents">
					<table id="projectProgressTable" class="tbl-edit td-c sc"><!-- td 영역이 가운데 정렬 일 경우 td-c -->
						<thead>
						<colgroup>
							<col style="width: 13%"/>
							<col style="width: 13%"/>
							<col style="width: 48%"/>
							<col style="width: 13%"/>
							<col style="width: 13%"/>
						</colgroup>
						<tr>
							<th>첨부타입</th>
							<th>공개여부</th>
							<th>파일명</th>
							<th>등록자</th>
							<th>등록일</th>
						</tr>
						</thead>
						<tbody>
						<c:if test="${!empty reqFile1}">
							<c:forEach var="file" items="${reqFile1}">
							<c:if test="${file.attachIsEssential eq '1'}">
								<tr height="25px" onmouseover="row_over(this)" onmouseout="row_out(this)" title="파일명을 클릭하면 다운로드 할 수 있습니다.">
									<td><c:out value="${file.attachOutputTypeName}"/></td>
									<td class="myoverflowC"><c:out value="${file.attachOutputName}"/></td>
									<td class="myoverflow" onclick="fileDownload('<c:out value="${file.attachFileId}"/>', 'Y')" style="cursor:hand;"><c:out value="${file.attachFileName}" escapeXml="false"/></td>
									<td><c:out value="${file.attachCreatorName}"/></td>
										<fmt:parseDate value="${file.attachCreateDate}" type="DATE" dateStyle="SHORT"  pattern="yyyyMMdd" var="qqq"/>
										<fmt:formatDate value="${qqq}" pattern="yyyy-MM-dd" var="sswws"/>
									<td><c:out value="${sswws}"/></td>			
								</tr>
							</c:if>
							</c:forEach>
						</c:if>
						<c:if test="${empty reqFile1}">
							<tr>
								<td colspan="5">등록된 필수 산출물이 없습니다.</td>
							</tr>
						</c:if>
						</tbody>
					</table>
				</div>		
			
			</div>
		</form>
</body>
</html>