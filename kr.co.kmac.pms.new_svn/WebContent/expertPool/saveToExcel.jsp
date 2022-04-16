<%@ page contentType="application/vnd.ms-excel; charset=utf-8"%>
<%@page import="kr.co.kmac.pms.common.util.StringUtil"%>
<%@ include file="/common/include/taglib.jsp"%>
<table width="665" border=1>
	<thead>
		<tr>
			<td align="center" bgcolor="silver">프로젝트명</td>
			<td align="center" bgcolor="silver">고객사명</td>
			<td align="center" bgcolor="silver">비즈니스타입</td>
			<td align="center" bgcolor="silver">조직단위</td>
			<td align="center" bgcolor="silver">시작일자</td>
			<td align="center" bgcolor="silver">종료일자</td>
		</tr>
	</thead>
	<tbody>
	<c:forEach var="rs" items="${projectInfoList.list}">
		<tr>
			<td align="center"><c:out value="${rs.projectName}" /></td>
			<td align="center"><c:out value="${rs.customerName}" /></td>
			<td align="center"><c:out value="${rs.bussinessTypeName}" /></td>
			<td align="center"><c:out value="${rs.runningDivName}" /></td>
			<td align="center"><c:out value="${rs.realStartDate}" /></td>
			<td align="center"><c:out value="${rs.realEndDate}" /></td>
		</tr>
	</c:forEach>
	</tbody>
</table>