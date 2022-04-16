<%@ page contentType="application/vnd.ms-excel; charset=utf-8"%>
<%@page import="kr.co.kmac.pms.common.util.StringUtil"%>
<%@ include file="/common/include/taglib.jsp"%>
<table width="665" border=1>
	<thead>
		<tr>
			<td align="center" bgcolor="silver">작성일자</td>
			<td align="center" bgcolor="silver">발급일자</td>
			<td align="center" bgcolor="silver">승인번호</td>
			<td align="center" bgcolor="silver">사업자등록번호</td>
			<td align="center" bgcolor="silver">상호</td>
			<td align="center" bgcolor="silver">합계금액</td>
		</tr>
	</thead>
	<tbody>
	<c:choose>
			<c:when test="${!empty list.list}">
				<c:forEach var="rs" items="${list.list}">
				<tr>
					<td alian="center"><c:out value="${rs.writeDate}" /></td>
					<td alian="center"><c:out value="${rs.readDate}" /></td>
					<td alian="center"><c:out value="${rs.checkValue}" /></td>
					<td alian="center"><c:out value="${rs.companyNum}" /></td>
					<td alian="center"><c:out value="${rs.businessName}" /></td>
					<td alian="center"><c:out value="${rs.sumMoney}" /></td>
				</tr>
				</c:forEach>
			</c:when>
			<c:otherwise>
				<tr><td align="center" colspan="6">검색된 데이터가 없습니다.</td></tr>
			</c:otherwise>
	</c:choose>
	</tbody>
</table>