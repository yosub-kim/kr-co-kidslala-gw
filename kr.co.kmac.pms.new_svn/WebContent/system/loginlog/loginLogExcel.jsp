<%@ page contentType="application/vnd.ms-excel; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<table border="1">
	<thead>
		<tr>
			<td style="text-align:center; background:#dddddd;"><b>SEQ</b></td>
			<td style="text-align:center; background:#dddddd;"><b>로그인 시간</b></td>
			<td style="text-align:center; background:#dddddd;"><b>IP</b></td>
			<td style="text-align:center; background:#dddddd;"><b>이름</b></td>
			<td style="text-align:center; background:#dddddd;"><b>접속기기</b></td>
		</tr>
	</thead>
	<tBody>
	<c:forEach var="result" items="${list.list}">
		<tr>
			<td><c:out value="${result.cnt}" /></td>
			<td><c:out value="${result.loginDate}" /></td>
			<td><c:out value="${result.ip}" /></td>
			<td><c:out value="${result.name}" /></td>
			<td><c:out value="${result.deviceType}" /></td>
		</tr>
	</c:forEach>
	</tBody>
</table>