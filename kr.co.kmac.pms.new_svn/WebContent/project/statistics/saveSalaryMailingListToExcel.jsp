<%@ page contentType="application/vnd.ms-excel; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<table width="665" border=1>
	<thead>
		<tr>
			<td align="center" bgcolor="silver">직업구분</td>
			<td align="center" bgcolor="silver">이름</td>
			<td align="center" bgcolor="silver">주민번호</td>
			<td align="center" bgcolor="silver">소속</td>
			<td align="center" bgcolor="silver">프로젝트<br>코드</td>
			<td align="center" bgcolor="silver">프로젝트명</td>
			<td align="center" bgcolor="silver">PJT성과급</td>
			<td align="center" bgcolor="silver">총 성과급</td>
			<td align="center" bgcolor="silver">안내메일<br>발송여부</td>
			<td align="center" bgcolor="silver">안내메일<br>발송일</td>
		</tr>
	</thead>
	<tbody>
	<c:forEach var="result" items="${list.list}">
		<tr>
			<td align="center"><c:out value="${result.jobClassDesc}" /></td>
			<td align="center"><c:out value="${result.name}" /></td>
			<td align="center"><c:out value="${result.uid}" /></td>
			<td align="center"><c:out value="${result.company}" /></td>
			<td align="center"><c:out value="${result.projectCode}" /></td>
			<td align="left"><c:out value="${result.projectName}" /></td>
			<td align="right"><fmt:formatNumber value="${result.realTimeSalaryEachProject}" pattern="#,###.##"/></td>
			<td align="right"><fmt:formatNumber value="${result.totalRealTimeSalary}" pattern="#,###.##"/></td>
			<td align="center"><c:out value="${result.emailSendYN}" /></td>
			<td align="center"><fmt:formatDate value="${result.emailSendDate}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
		</tr>
	</c:forEach>
	</tbody>
</table>