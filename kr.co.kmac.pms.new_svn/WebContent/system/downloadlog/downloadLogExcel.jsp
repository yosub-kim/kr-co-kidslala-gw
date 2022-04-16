<%@ page contentType="application/vnd.ms-excel; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<table border="1">
	<thead>
		<tr>
			<td style="text-align:center; background:#dddddd;"><b>다운로드 시간</b></td>
			<td style="text-align:center; background:#dddddd;"><b>파일명</b></td>
			<td style="text-align:center; background:#dddddd;"><b>IP</b></td>
			<td style="text-align:center; background:#dddddd;"><b>등록자명</b></td>
		</tr>
	</thead>
	<tBody>
	<c:forEach var="result" items="${list.list}">
		<tr>
			<td><c:out value="${result.downloadTime}" /></td>
			<td><c:out value="${result.fileName}" escapeXml="false" /></td>
			<td><c:out value="${result.ip}" /></td>
			<td><c:out value="${result.uploadUserName}" /></td>
		</tr>
	</c:forEach>
	</tBody>
</table>