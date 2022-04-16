<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%-- 공통js,css include --%>
<%@ include file="/common/include/includeCss.jsp"%>
<%@ include file="/common/include/includeJavaScript.jsp"%>

<script type="text/javascript">

</script>
</head>
<body>
<%-- 작업영역 --%>
<table width="751"  cellpadding="0" cellspacing="0" style="table-layout:fixed;">

	<!-- sub 타이틀 영역 시작--> 
	<tr>
		<td>
			<h4 class="subTitle mt10 mb5">프로젝트 명</h4>
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
			<h4 class="subTitle mt10 mb5">PL 평가 항목 및 결과</h4>
		</td>
	</tr>
	<!-- sub 타이틀 영역 종료--> 		
	<tr>
		<td>
			<table class="tableStyle05">
				<thead>
					<tr>
						<td width="120px">항 목</td>
						<td width="*">항목의 정의</td>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td class="detailTableTitle_left">1. 방법론 제시력</td>
						<td class="detailTableField_left"> 성공적 프로젝트 수행을 위한 효율적이고 효과적인 방법론을 제시하거나 창출할 수 있는가?</td>
					</tr>
					<tr>
						<td class="detailTableTitle_left">2. 문제 해결력</td>
						<td class="detailTableField_left"> 프로젝트 목적을 잊지 않고 문제의 본질을 잘 이해하며 잠재적 위협 요인을 잘 파악할 수 있는가?</td>
					</tr>
					<tr>
						<td class="detailTableTitle_left">3. 대안 창출력</td>
						<td class="detailTableField_left"> 문제의 정확한 해결안이나 현재나 미래에 대한 의사결정을 지원할 수 있는 전략 대안 도출이 가능한가?</td>
					</tr>
					<tr>
						<td class="detailTableTitle_left">4. 납기준수</td>
						<td class="detailTableField_left"> 프로젝트 관리 기준을 잘 준수하고 멤버들의 일정 관리를 세심히 살펴 납기를 잘 지키고 있는가?</td>
					</tr>
					<tr>
						<td class="detailTableTitle_left">5. 원가관리</td>
						<td class="detailTableField_left"> 프로젝트의 원가와 이익 개념을 잘 파악하고 있으며 비용 상승 요인에 대한 통제력을 지니고 있는가?</td>
					</tr>
					<tr>
						<td class="detailTableTitle_left">6. 품질유지</td>
						<td class="detailTableField_left"> 프로젝트 결과 산출물 뿐만 아니라 진행 과정 상의 제반 품질 기준을 잘 지키고 있는가?</td>
					</tr>
					<tr>
						<td class="detailTableTitle_left">7. 고객리더십</td>
						<td class="detailTableField_left"> 문제점을 고객보다 먼저 파악하거나 미래 니즈를 창출하는 등 고객을 선도하는 입장에 서 있는가?</td>
					</tr>
					<tr>
						<td class="detailTableTitle_left">8. 불만 관리</td>
						<td class="detailTableField_left"> 고객의 불만 요인을 미리 제거하거나 또는 조기에 발견하며, 불만 제기 시 신속한 대응이 가능한가?</td>
					</tr>
					<tr>
						<td class="detailTableTitle_left">9. 책임감</td>
						<td class="detailTableField_left"> 프로젝트의 총 책임자로서의 역할 인식이 분명하며 잘못이 있을 경우 책임 회피나 전가를 하지 않는가?</td>
					</tr>
					<tr>
						<td class="detailTableTitle_left">10. 후배육성</td>
						<td class="detailTableField_left"> 멤버들의 육성에 구체적으로 최선을 다하며 항상 시너지를 발휘하기 위한 팀 빌딩을 고민하고 있는가?</td>
					</tr>
				</tbody>
			</table>
		</td>
	</tr>
	<tr>
		<td align="right" height="5"></td>
	</tr>
	<tr>
		<td>
			<table class="tableStyle05">
				<thead>
					<tr>
						<td width="*">성명</td>
						<td width="8%">방법론<br>제시력</td>
						<td width="8%">문제<br>해결력</td>
						<td width="8%">대안<br>창출력</td>
						<td width="8%">납기<br>준수</td>
						<td width="8%">원가<br>관리</td>
						<td width="8%">품질<br>유지</td>
						<td width="8%">고객<br>리더십</td>
						<td width="8%">불만<br>관리</td>
						<td width="8%">책임감</td>
						<td width="8%">후배<br>육성</td>
					</tr>
				</thead>
				<tbody>
				<c:if test="${!empty result.list}">
					<c:forEach var="result" items="${result.list}">
					<tr>
						<td class="detailTableField_center"><c:out value="${result.name}" /></td>
						<td><c:out value="${result.answer1}" />/10</td>
						<td><c:out value="${result.answer2}" />/10</td>
						<td><c:out value="${result.answer3}" />/10</td>
						<td><c:out value="${result.answer4}" />/10</td>
						<td><c:out value="${result.answer5}" />/10</td>
						<td><c:out value="${result.answer6}" />/10</td>
						<td><c:out value="${result.answer7}" />/10</td>
						<td><c:out value="${result.answer8}" />/10</td>
						<td><c:out value="${result.answer9}" />/10</td>
						<td><c:out value="${result.answer10}" />/10</td>
					</tr>
					</c:forEach>
				</c:if>
				<c:if test="${empty result.list}">
					<td align="center"  colspan="11">평가 결과가 없습니다.</td>
				</c:if>
				</tbody>
			</table>
		</td>
	</tr>

	<!-- sub 타이틀 영역 시작--> 
	<tr>
		<td>
			<h4 class="subTitle mt10 mb5">PL 평가 의견</h4>
		</td>
	</tr>
	<!-- sub 타이틀 영역 종료--> 		
	<tr>
		<td>
			<table class="tableStyle05">
				<thead>
					<tr height="25px">
						<td width="100px">성명</td>
						<td width="*">의견</td>
					</tr>
				</thead>
				<tbody>
				<c:if test="${!empty result.list}">
					<c:forEach var="result" items="${result.list}">
					<tr height="25px">
						<td align="center"><c:out value="${result.name}" /></td>
						<td><c:out value="${result.comment}" /></td>
					</tr>
					</c:forEach>
				</c:if>
				<c:if test="${empty result.list}">
					<td align="center" colspan="2">의견이 없습니다.</td>
				</c:if>
				</tbody>
			</table>
		</td>
	</tr>
	<tr>
		<td align="center" height="7px" valign="middle"></td>
	</tr>
</table>
</body>
</html>