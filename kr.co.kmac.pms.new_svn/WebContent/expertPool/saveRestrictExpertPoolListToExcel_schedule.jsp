<%@ page contentType="application/vnd.ms-excel; charset=utf-8"%>
<%@page import="kr.co.kmac.pms.common.util.StringUtil"%>
<%@ include file="/common/include/taglib.jsp"%>
<table width="665" border=1>
	<thead>
		<tr>
			<td align="center" bgcolor="silver">조직단위</td>
			<td align="center" bgcolor="silver">성명</td>
			<td align="center" bgcolor="silver">사번</td>
			<td align="center" bgcolor="silver">직급</td>
			<td align="center" bgcolor="silver">날짜</td>
			<td align="center" bgcolor="silver">시간</td>
			<td align="center" bgcolor="silver">외/내근여부</td>
			<td align="center" bgcolor="silver">출/퇴근여부</td>
			<td align="center" bgcolor="silver">장소</td>
			<td align="center" bgcolor="silver">내용</td>
		</tr>
	</thead>
	<tbody>
	<c:choose>
			<c:when test="${!empty list.list}">
				<c:set var="NameChk" value=" "/>
				<c:set var="chk" value=" "/>
				<c:set var="dateChk" value=" "/>
				<c:forEach var="rs" items="${list.list}">
					<tr onclick="detail('<c:out value="${rs.ssn}" />')" style="cursor: hand;" onmouseover="row_over(this)" onmouseout="row_out(this)">
						<c:if test="${rs.groupName != NameChk}">
							<td rowspan="<c:out value="${rs.groupcount}"/>" style="text-align: center" ><c:out value="${rs.labelName}" escapeXml="false" /></td>
						</c:if>
						<c:if test="${rs.name != chk}">
							<td rowspan="<c:out value="${rs.usercount}"/>" style="text-align: center"><c:out value="${rs.name}" escapeXml="false" /></td>
						</c:if>
						<c:if test="${rs.name != chk}">
							<td rowspan="<c:out value="${rs.usercount}"/>" style="text-align: center"><c:out value="${rs.emplno}" escapeXml="false" /></td>
						</c:if>
						<c:if test="${rs.name != chk}">
							<td rowspan="<c:out value="${rs.usercount}"/>" style="text-align: center">
						<c:choose>
							<c:when test="${rs.posName == '시니어 컨설턴트'}"><c:out value="시니어" /></c:when>
							<c:when test="${rs.posName == '치프 컨설턴트'}"><c:out value="치프" /></c:when>
							<c:when test="${rs.posName == '프린서플 컨설턴트'}"><c:out value="프린서플" /></c:when>
							<c:when test="${rs.posName == '파트너 컨설턴트'}"><c:out value="파트너" /></c:when>
							<c:otherwise><c:out value="${rs.posName}" escapeXml="false" /></c:otherwise>
						</c:choose>
						</td>
						</c:if>
						<%-- <td rowspan="<c:out value="${rs.dateCnt}"/>" style="text-align: center"><c:out value="${rs.year}"/>-<c:out value="${rs.month}"/>-<c:out value="${rs.day}"/></td> --%>
						<td alian="center" style="text-align: center" ><c:out value="${rs.year}" />-<c:out value="${rs.month}" />-<c:out value="${rs.day}" /></td>
						<td alian="center" style="text-align: center" ><c:out value="${rs.startHour}" />시<c:out value="${rs.startMin}" />분 ~ <c:out value="${rs.endHour}" />시<c:out value="${rs.endMin}" />분</td>
						<td alian="center" style="text-align: center" >
							<c:choose>
								<c:when test="${rs.workType == 'I'}"><c:out value="내근" /></c:when>
								<c:when test="${rs.workType == 'E'}"><c:out value="외근" /></c:when>
								<c:otherwise><c:out value="-" /></c:otherwise>
							</c:choose>
						</td>
						<td alian="center" style="text-align: center" >
							<c:choose>
								<c:when test="${rs.relationUser == '1'}"><c:out value="현장 출근" /></c:when>
								<c:when test="${rs.relationUser == '2'}"><c:out value="현장 퇴근" /></c:when>
								<c:when test="${rs.relationUser == '3'}"><c:out value="현장 출/퇴근" /></c:when>
								<c:otherwise><c:out value="-" /></c:otherwise>
							</c:choose>
						</td>
						<td alian="center" style="text-align: center" >
							<c:choose>
								<c:when test = "${rs.place == '' }" ><c:out value="-" /></c:when>
								<c:when test = "${rs.place == null }" ><c:out value="-" /></c:when>
								<c:otherwise><c:out value="${rs.place}" /></c:otherwise>
							</c:choose>
						</td>
						<td alian="center" style="text-align: center" ><c:out value="${rs.content}" /></td>
					</tr>
					<c:set var="NameChk" value="${rs.groupName}"/>
					<c:set var="chk" value="${rs.name}"/>
					<c:set var="dateChk" value="${rs.filterdate}"/>
				</c:forEach>
			</c:when>
			<c:otherwise>
				<tr><td align="center" colspan="7">검색된 데이터가 없습니다.</td></tr>
			</c:otherwise>
		</c:choose>
	</tbody>
</table>