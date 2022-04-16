<%@ page contentType="text/html; charset=utf-8"
	import="java.util.List"%>
<%@ include file="/common/include/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>스케쥴 관리</title>
<%-- 공통js,css include --%>
<%@ include file="/common/include/includeCss.jsp"%>
<%@ include file="/common/include/includeJavaScript.jsp"%>
<script type="text/javascript">
<%-- 개별 스크립트 영역 --%>
function page_load(){
	
}

</script>
</head>
<body onload="page_load();">
<%@ include file="/common/include/includeBodyTop.jsp"%>
<%-- 작업영역 --%>
<div id="PageFull">
<table align="center" width="100%">
	<tr>
		<td class="Title">스케쥴 관리</td>		
	</tr>
	<tr>
		<td align="center">
			<table cellSpacing="0" width="100%" border="1" class='listTable' >
				<thead>
					<tr>
						<td style="width:70px">일자</td>
						<td style="width:70">업무유형</td>
						<td class="col_Header">업무내용</td>
						<td style="width:100">관련회사</td>
						<td style="width:70">관련자</td>
						<td style="width:70">장소</td>						
						<td style="width:100">시간</td>
					</tr>				
				</thead>
				<tbody>
					<c:forEach var="project" items="${projectList}">
					<tr>
						<td><c:out value="${project.year}"/>-<c:out value="${project.month}"/>-<c:out value="${project.day}"/></td>
						<td>지도일지</td>
						<td><c:out value="${project.projectName}"/></td>
						<td><c:out value="${project.customerName}"/></td>
						<td><c:out value="${project.relationUser}"/></td>
						<td></td>
						<td><c:out value="${project.time}"/>:<c:out value="${schedule.startMin}"/>~<c:out value="${schedule.endHour}"/>:<c:out value="${schedule.endMin}"/></td>
					</tr>
					</c:forEach>
					<c:forEach var="schedule" items="${scheduleList}">
					<tr>
						<td><c:out value="${schedule.year}"/>-<c:out value="${schedule.month}"/>-<c:out value="${schedule.day}"/></td>
						<td><c:out value="${schedule.type}"/></td>
						<td><c:out value="${schedule.content}"/></td>
						<td><c:out value="${schedule.customerName}"/></td>
						<td><c:out value="${schedule.relationUser}"/></td>
						<td><c:out value="${schedule.place}"/></td>
						<td><c:out value="${schedule.startHour}"/>:<c:out value="${schedule.startMin}"/>~<c:out value="${schedule.endHour}"/>:<c:out value="${schedule.endMin}"/></td>
					</tr>
					</c:forEach>
				</tbody>
			</table>
		</td>
	</tr>
</table>
</div>
<%@ include file="/common/include/includeBodyBottom.jsp"%>
</body>
</html>