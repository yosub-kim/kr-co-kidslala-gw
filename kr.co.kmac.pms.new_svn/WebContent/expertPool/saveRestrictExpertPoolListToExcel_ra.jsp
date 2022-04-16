<%@ page contentType="application/vnd.ms-excel; charset=utf-8"%>
<%@page import="kr.co.kmac.pms.common.util.StringUtil"%>
<%@ include file="/common/include/taglib.jsp"%>
<table width="665" border=1>
	<thead>
		<tr>
			<td align="center" bgcolor="silver">부서</td>
			<td align="center" bgcolor="silver">이름</td>
			<td align="center" bgcolor="silver">직위</td>
			<td align="center" bgcolor="silver">계약 시작일</td>
			<td align="center" bgcolor="silver">계약 종료일</td>
			<td align="center" bgcolor="silver">개인연차</td>
			<td align="center" bgcolor="silver">사용연차</td>
			<td align="center" bgcolor="silver">잔여연차</td>
		</tr>
	</thead>
	<tbody>
		<c:choose>
			<c:when test="${!empty list.list}">
				<c:set var="deptNameChk" value=""/>
				<c:forEach var="rs" items="${list.list}">
					<tr onclick="detail('<c:out value="${rs.ssn}" />')" style="cursor: hand;" onmouseover="row_over(this)" onmouseout="row_out(this)">
					<c:if test="${rs.deptName != deptNameChk}">
						<td rowspan="<c:out value="${rs.dept}"/>" style="text-align: center"><c:out value="${rs.deptName}" escapeXml="false" /></td>
					</c:if>
						<td align="center"><c:out value="${rs.name}" /></td>
						<td alian="center"><c:out value="${rs.companyPositionName}" /></td>
						<td align="center" <c:if test="${rs.acctExpireDate != '' && rs.acctExpireDate < today}">bgcolor="#ffdfff"</c:if>>
							<c:choose><c:when test="${rs.acctBeginDate == ''}">인사담당자 미입력</c:when><c:otherwise><c:out value="${rs.acctBeginDate}" /></c:otherwise></c:choose>
						</td>
						<td align="center" <c:if test="${rs.acctExpireDate != '' && rs.acctExpireDate < today}">bgcolor="#ffdfff"</c:if>>
							<c:choose><c:when test="${rs.acctExpireDate == ''}">인사담당자 미입력</c:when><c:otherwise><c:out value="${rs.acctExpireDate}" /></c:otherwise></c:choose>
						</td>
								<td alian="center"><c:out value="${rs.restday_1}" /></td>
						<td alian="center"><c:out value="${rs.useday}" /></td>

						<td alian="center"><c:out value="${rs.restday}" /></td>
					</tr>
					<c:set var="deptNameChk" value="${rs.deptName}"/>
				</c:forEach>
			</c:when>
			<c:otherwise>
				<tr><td align="center" colspan="7">검색된 데이터가 없습니다.</td></tr>
			</c:otherwise>
		</c:choose>
	</tbody>
</table>