<%@ page contentType="application/vnd.ms-excel; charset=utf-8"%>
<%@page import="kr.co.kmac.pms.common.util.StringUtil"%>
<%@ include file="/common/include/taglib.jsp"%>
<table width="665" border=1>
	<thead>
		<tr>
			<td align="center" bgcolor="silver">소속</td>
			<td align="center" bgcolor="silver">주민번호</td>
			<td align="center" bgcolor="silver">성명</td>
			<td align="center" bgcolor="silver">직위/직책</td>
			<td align="center" bgcolor="silver">총 성과급</td>
		</tr>
	</thead>
	<tbody>
	<c:forEach var="result" items="${list.list}">
		<tr>
			<td align="center"><c:out value="${result.deptName}" /></td>
			<td align="center"><c:out value="${result.ssn}" /></td>
			<td align="center"><c:out value="${result.name}" /></td>
			<td align="center"><c:out value="${result.role}" /></td>
			<td align="right"><fmt:formatNumber value="${result.realTimeSalary}" pattern="#,###.##"/></td>
		</tr>
	</c:forEach>
	</tbody>
</table>

