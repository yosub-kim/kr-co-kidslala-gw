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
			<h4 class="subTitle mt10 mb5">컨설턴트 평가 항목 및 결과</h4>
		</td>
	</tr>
	<!-- sub 타이틀 영역 종료--> 	
	<tr>
		<td>
			<table class="tableStyle05">
				<thead>
					<tr height="25px">
						<td width="120px">항 목</td>
						<td width="*">항목의 정의</td>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td class="detailTableTitle_left">1. 직무지식</td>
						<td> 프로젝트 수행 시 담당 직무 수행에 직접적으로 필요한 이론지식, 실무지식이 있는가?</td>
					</tr>
					<tr>
						<td class="detailTableTitle_left">2. 정보수집 분석력</td>
						<td>프로젝트 수행에 직,간접적으로 필요한 정보를 수집, 분석, 유용하게 자료화하는 능력을 지니고 있는가?</td>
					</tr>
					<tr>
						<td class="detailTableTitle_left">3. 응용개선능력</td>
						<td>습득한 업무지식을 응용하여 문제점을 파악하고 보다 효과적인 해결방법을 제시하는 능력이 있는가?</td>
					</tr>
					<tr>
						<td class="detailTableTitle_left">4. 이해력</td>
						<td>문제나 상황을 신속, 정확하게 파악하고 리더의 지시나 업무 범위 등을 잘 이해할 수 있는가?</td>
					</tr>
					<tr>
						<td class="detailTableTitle_left">5. 수행력</td>
						<td>주어진 업무 목표를 일정에 맞게 완수하여 명확한 결과를 도출하고 그 결과물을 산출할 수 있는가?</td>
					</tr>
					<tr>
						<td class="detailTableTitle_left">6. 판단력</td>
						<td>리더 지시에 대한 요점을 잘 파악하여 적절한 판단을 하고 올바른 결론과 행동을 취하고 있는가?</td>
					</tr>
					<tr>
						<td class="detailTableTitle_left">7. 표현력</td>
						<td>전달하려는 생각이나 의도를 문서 또는 구두로 논리적이며 구체적으로 표현하고 납득시킬 수 있는가?</td>
					</tr>
					<tr>
						<td class="detailTableTitle_left">8. 협조성</td>
						<td>조직의 일원으로서 동료 및 리더에 협력하며 프로젝트의 전체적 성공에 부응하려 하는가?</td>
					</tr>
					<tr>
						<td class="detailTableTitle_left">9. 책임감</td>
						<td>자기 역할과 책임을 충분히 인식하고 기대에 부응하려는 노력을 하며 책임회피나 전가를 하지 않는가?</td>
					</tr>
					<tr>
						<td class="detailTableTitle_left">10. 도전의식</td>
						<td>어렵고 힘든 일이라도 실패를 두려워하지 않고 능동적이며 과감하게 도전하여 성취하려고 하는가?</td>
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
						<td width="9%">직무지식</td>
						<td width="9%">정보수집 분석력</td>
						<td width="9%">응용개선능력</td>
						<td width="9%">이해력</td>
						<td width="9%">수행력</td>
						<td width="9%">판단력</td>
						<td width="9%">표현력</td>
						<td width="9%">협조성</td>
						<td width="9%">책임감</td>
						<td width="9%">도전의식</td>
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
					<td class="detailTableField_center" colspan="11">평가 결과가 없습니다.</td>
				</c:if>
				</tbody>
			</table>
		</td>
	</tr>
	<!-- sub 타이틀 영역 시작--> 
	<tr>
		<td>
			<h4 class="subTitle mt10 mb5">컨설턴트 평가 의견</h4>
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
				<tbody id="tbody_Comment">
				<c:if test="${!empty result.list}">
					<c:forEach var="result" items="${result.list}">
					<tr height="25px" id="tr_<c:out value="${result.ssn}" />">
						<td align="center"><c:out value="${result.name}" /></td>
						<td><c:out value="${result.comment}" /></td>
					</tr>
					</c:forEach>
				</c:if>
				<c:if test="${empty result.list}">
					<td align="center" colspan="2">평가 결과가 없습니다.</td>
				</c:if>
				</tbody>
			</table>
		</td>
	</tr>
	</form>
</table>
</body>
</html>