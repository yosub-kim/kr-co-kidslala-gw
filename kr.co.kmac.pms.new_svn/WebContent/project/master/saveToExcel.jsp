<%@ page contentType="application/vnd.ms-excel; charset=utf-8"%>
<%@page import="kr.co.kmac.pms.common.util.StringUtil"%>
<%@ include file="/common/include/taglib.jsp"%>
<table width="765" border="1">
	<thead>
		<tr>
			<td align="center" bgcolor="silver">비즈니스 유형</td>
			<td align="center" bgcolor="silver">산업 구분</td>
			<td align="center" bgcolor="silver">프로세스 유형</td>
			<td alian="center" bgcolor="silver">Function 유형</td>
			<td align="center" bgcolor="silver">조직단위</td>
			<td align="center" bgcolor="silver">프로젝트 코드</td>
			<td align="center" bgcolor="silver">프로젝트 명</td>
			<td align="center" bgcolor="silver">시작일</td>
			<td align="center" bgcolor="silver">종료일</td>
			<td align="center" bgcolor="silver">고객사</td>
		</tr>
	</thead>
	<tbody>
		<c:choose>
			<c:when test="${!empty list.list}">
				<c:forEach var="rs" items="${list.list}">
				<tr>
					<td alian="center" style="text-align: center" ><c:out value="${rs.businessTypeCodeName}" /></td>
					<td alian="center" style="text-align: center" ><code:code tableName="INDUSTRY_TYPE_CODE"  code="${rs.industryTypeCode}" /></td>
					<td alian="center" style="text-align: center" ><code:code tableName="PROCESS_TYPE_CODE"  code="${rs.processTypeCode}" /></td>
					<td alian="center" style="text-align: center" >
					<c:choose>
						<c:when test="${rs.businessFunctionType eq 'BAT01'}">
							경영전략
						</c:when>
						<c:when test="${rs.businessFunctionType eq 'BAT02'}">
							인사조직
						</c:when>
						<c:when test="${rs.businessFunctionType eq 'BAT03'}">
							CS경영
						</c:when>
						<c:when test="${rs.businessFunctionType eq 'BAT04'}">
							마케팅
						</c:when>
						<c:when test="${rs.businessFunctionType eq 'BAT05'}">
							경영품질
						</c:when>
						<c:when test="${rs.businessFunctionType eq 'BAT06'}">
							생산혁신
						</c:when>
						<c:otherwise>
							기타/컨버전스
						</c:otherwise>
					</c:choose>
					<td alian="center" style="text-align: center" ><c:out value="${rs.runningDeptCodeName}" /></td>
					<td alian="center" style="text-align: center" ><c:out value="${rs.projectCode}" /></td>
					<td alian="center" style="text-align: center" ><c:out value="${rs.projectName}" /></td>
					<td alian="center" style="text-align: center" ><c:out value="${rs.realStartDate}" /></td>
					<td alian="center" style="text-align: center" ><c:out value="${rs.realEndDate}" /></td>
					<td alian="center" style="text-align: center" ><c:out value="${rs.customerName}" /></td>
				</tr>
				</c:forEach>
			</c:when>
			<c:otherwise>
				<tr><td align="center" colspan="9">검색된 데이터가 없습니다.</td></tr>
			</c:otherwise>
		</c:choose>
	</tbody>
</table>