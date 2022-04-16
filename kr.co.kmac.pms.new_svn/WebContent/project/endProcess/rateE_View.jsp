<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%-- 공통js,css include --%>
<%@ include file="/common/include/includeCss.jsp"%>
<%@ include file="/common/include/includeJavaScript.jsp"%>
</head>
<body>
<%-- 작업영역 --%>
<table width="100%"  cellpadding="0" cellspacing="0" style="table-layout:fixed;">

	<!-- sub 타이틀 영역 시작--> 
	<tr>
		<td>
			<h4 class="subTitle mt5 mb5">프로젝트 명</h4>
		</td>
	</tr>
	<!-- sub 타이틀 영역 종료--> 	
	<tr>
		<td>
			<table cellSpacing="0" cellpadding="0" width="100%" >
				<tr height="25px">
					<td class="detailTableTitle_center" width="120px">프로젝트 명</td>
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
			<h4 class="subTitle mt15 mb5">강사 평가 항목 및 결과</h4>
		</td>
	</tr>
	<!-- sub 타이틀 영역 종료--> 	
	<tr>
		<td>
			<table class="tableStyle05">
				<thead>
					<tr height="25px">
						<td width="80px">성명</td>
						<td class="detailTableTitle_center" width="*">교육과목</td>
						<td class="detailTableTitle_center" width="70px">강의수준</td>
						<td class="detailTableTitle_center" width="70px">강의준비</td>
						<td class="detailTableTitle_center" width="70px">강의스킬</td>
						<td class="detailTableTitle_center" width="70px">성의 및 열의</td>
						<td class="detailTableTitle_right" width="50px">평가</td>
					</tr>
				</thead>
				<tbody>
				<c:if test="${!empty result.list}">
					<c:forEach var="result" items="${result.list}">
					<tr height="25px">
						<td class="detailTableField_center"><c:out value="${result.name}" /></td>
						<td class="detailTableField_left"><c:out value="${result.subject}" /></td>
						<!-- 
						<c:forEach begin="1" end="4" varStatus="i">
						<td class="detailTableField_left">
							<c:out value="${i.count}"/>								
						</td>
						</c:forEach>
						-->
						<td class="detailTableField_center"><c:out value="${result.answer1}"/> / 25</td>
						<td class="detailTableField_center"><c:out value="${result.answer2}"/> / 25</td>
						<td class="detailTableField_center"><c:out value="${result.answer3}"/> / 25</td>
						<td class="detailTableField_center"><c:out value="${result.answer4}"/> / 25</td>
						<td class="detailTableField_right"><b><c:out value="${result.estimateC}"/> 점</b></td>
					</tr>
					</c:forEach>
				</c:if>
				<c:if test="${empty result.list}">
					<tr>
						<td class="detailTableField_center" colspan="7">평가 결과가 없습니다.</td>
					</tr>
				</c:if>
				</tbody>
			</table>
		</td>
	</tr>
	<tr>
		<td height="15"></td>
	</tr>
	

</table>
</body>
</html>