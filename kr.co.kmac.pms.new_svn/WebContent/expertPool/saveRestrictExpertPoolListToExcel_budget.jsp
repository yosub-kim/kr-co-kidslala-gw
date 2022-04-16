<%@ page contentType="application/vnd.ms-excel; charset=utf-8"%>
<%@page import="kr.co.kmac.pms.common.util.StringUtil"%>
<%@ include file="/common/include/taglib.jsp"%>
<table width="665" border=1>
	<thead>
		<tr>
			<td align="center" bgcolor="silver">P-Code</td>
			<td align="center" bgcolor="silver">P-Name</td>
			<td align="center" bgcolor="silver">수주월</td>
			<td align="center" bgcolor="silver">수주액</td>
			<td align="center" bgcolor="silver">예산원가</td>
			<td align="center" bgcolor="silver">예산이익</td>
			<td align="center" bgcolor="silver">시작일</td>
			<td align="center" bgcolor="silver">종료일</td>
			<td align="center" bgcolor="silver">부서명</td>
		</tr>
	</thead>
	<tbody>
		<c:choose>
			<c:when test="${!empty list.list}">
					<c:forEach var="rs" items="${list.list}">
						<tr>
							<td alian="center"><c:out value="${rs.projectCode}" /></td>
							<td alian="center"><c:out value="${rs.projectName}" /></td>
							<td alian="center"><c:out value="${rs.registerDate}" /></td>
							<td alian="right"><fmt:formatNumber value="${rs.sum}" pattern="#,###" /></td>
							<td alian="right"><fmt:formatNumber value="${rs.detailSum}" pattern="#,###" /></td>
							<td alian="right"><fmt:formatNumber value="${rs.detailBen}" pattern="#,###" /></td>
							<td alian="center"><c:out value="${rs.realStartDate}"/></td>
							<td alian="center"><c:out value="${rs.realEndDate}" /></td>
							<td alian="center"><c:out value="${rs.aliasName}" /></td>
						</tr>
					</c:forEach>
			</c:when>
			<c:otherwise>
				<tr><td align="center" colspan="9">검색된 데이터가 없습니다.</td></tr>
			</c:otherwise>
		</c:choose>							
	</tbody>
</table>