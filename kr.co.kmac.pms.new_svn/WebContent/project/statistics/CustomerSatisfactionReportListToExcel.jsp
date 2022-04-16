<%@ page contentType="application/vnd.ms-excel; charset=utf-8"%>
<%@page import="kr.co.kmac.pms.common.util.StringUtil"%>
<%@ include file="/common/include/taglib.jsp"%>
<table width="665" border=1>
	<thead>
		<tr>
			<td align="center" bgcolor="silver">비즈니스유형</td>
			<td align="center" bgcolor="silver">담당COO</td>
			<td align="center" bgcolor="silver">프로젝트명</td>
			<td align="center" bgcolor="silver">고객사명</td>
			<td align="center" bgcolor="silver">고객만족도</td>
			<td align="center" bgcolor="silver">평가일</td>
		</tr>
	</thead>
	<tbody>
	<c:forEach var="result" items="${result.list}">
		<tr>
			<td align="center"><c:out value="${result.businessTypeName}" /></td>
			<td align="center"><c:out value="${result.runningDivName}" /></td>
			<td align="center"><c:out value="${result.projectName}" /></td>
			<td align="center"><c:out value="${result.customerName}" /></td>
			<td align="center"><c:out value="${result.ratio}" /></td>
			<td align="center"><c:out value="${result.writeDate}" /></td>
		</tr>
	</c:forEach>
	</tbody>
</table>

