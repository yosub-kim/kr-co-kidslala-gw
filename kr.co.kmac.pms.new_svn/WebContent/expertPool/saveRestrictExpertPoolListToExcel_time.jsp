<%@ page contentType="application/vnd.ms-excel; charset=utf-8"%>
<%@page import="kr.co.kmac.pms.common.util.StringUtil"%>
<%@ include file="/common/include/taglib.jsp"%>
<table width="665" border=1>
	<thead>
		<tr>
			<td align="center" bgcolor="silver">소속</td>
			<td align="center" bgcolor="silver">성명</td>
			<td align="center" bgcolor="silver">1일</td>
			<td align="center" bgcolor="silver">2일</td>
			<td align="center" bgcolor="silver">3일</td>
			<td align="center" bgcolor="silver">4일</td>
			<td align="center" bgcolor="silver">5일</td>
			<td align="center" bgcolor="silver">6일</td>
			<td align="center" bgcolor="silver">7일</td>
			<td align="center" bgcolor="silver">8일</td>
			<td align="center" bgcolor="silver">9일</td>
			<td align="center" bgcolor="silver">10일</td>
			<td align="center" bgcolor="silver">11일</td>
			<td align="center" bgcolor="silver">12일</td>
			<td align="center" bgcolor="silver">13일</td>
			<td align="center" bgcolor="silver">14일</td>
			<td align="center" bgcolor="silver">15일</td>
			<td align="center" bgcolor="silver">16일</td>
			<td align="center" bgcolor="silver">17일</td>
			<td align="center" bgcolor="silver">18일</td>
			<td align="center" bgcolor="silver">19일</td>
			<td align="center" bgcolor="silver">20일</td>
			<td align="center" bgcolor="silver">21일</td>
			<td align="center" bgcolor="silver">22일</td>
			<td align="center" bgcolor="silver">23일</td>
			<td align="center" bgcolor="silver">24일</td>
			<td align="center" bgcolor="silver">25일</td>
			<td align="center" bgcolor="silver">26일</td>
			<td align="center" bgcolor="silver">27일</td>
			<td align="center" bgcolor="silver">28일</td>
			<td align="center" bgcolor="silver">29일</td>
			<td align="center" bgcolor="silver">30일</td>
			<td align="center" bgcolor="silver">31일</td>
		</tr>
	</thead>
	<tbody>
	<c:choose>
			<c:when test="${!empty list.list}">
				<c:set var="NameChk" value=" "/>
				<c:forEach var="rs" items="${list.list}">
				<tr>
				<c:if test="${rs.labelName != NameChk}">
					<td rowspan="<c:out value="${rs.groupcount}"/>" style="text-align: center" ><c:out value="${rs.labelName}" /></td>
				</c:if>
					<td alian="center" style="text-align: center" ><c:out value="${rs.userName}" /></td>
					<td alian="center" style="text-align: center" ><c:out value="${rs.m01}" /></td>
					<td alian="center" style="text-align: center" ><c:out value="${rs.m02}" /></td>
					<td alian="center" style="text-align: center" ><c:out value="${rs.m03}" /></td>
					<td alian="center" style="text-align: center" ><c:out value="${rs.m04}" /></td>
					<td alian="center" style="text-align: center" ><c:out value="${rs.m05}" /></td>
					<td alian="center" style="text-align: center" ><c:out value="${rs.m06}" /></td>
					<td alian="center" style="text-align: center" ><c:out value="${rs.m07}" /></td>
					<td alian="center" style="text-align: center" ><c:out value="${rs.m08}" /></td>
					<td alian="center" style="text-align: center" ><c:out value="${rs.m09}" /></td>
					<td alian="center" style="text-align: center" ><c:out value="${rs.m10}" /></td>
					<td alian="center" style="text-align: center" ><c:out value="${rs.m11}" /></td>
					<td alian="center" style="text-align: center" ><c:out value="${rs.m12}" /></td>
					<td alian="center" style="text-align: center" ><c:out value="${rs.m13}" /></td>
					<td alian="center" style="text-align: center" ><c:out value="${rs.m14}" /></td>
					<td alian="center" style="text-align: center" ><c:out value="${rs.m15}" /></td>
					<td alian="center" style="text-align: center" ><c:out value="${rs.m16}" /></td>
					<td alian="center" style="text-align: center" ><c:out value="${rs.m17}" /></td>
					<td alian="center" style="text-align: center" ><c:out value="${rs.m18}" /></td>
					<td alian="center" style="text-align: center" ><c:out value="${rs.m19}" /></td>
					<td alian="center" style="text-align: center" ><c:out value="${rs.m20}" /></td>
					<td alian="center" style="text-align: center" ><c:out value="${rs.m21}" /></td>
					<td alian="center" style="text-align: center" ><c:out value="${rs.m22}" /></td>
					<td alian="center" style="text-align: center" ><c:out value="${rs.m23}" /></td>
					<td alian="center" style="text-align: center" ><c:out value="${rs.m24}" /></td>
					<td alian="center" style="text-align: center" ><c:out value="${rs.m25}" /></td>
					<td alian="center" style="text-align: center" ><c:out value="${rs.m26}" /></td>
					<td alian="center" style="text-align: center" ><c:out value="${rs.m27}" /></td>
					<td alian="center" style="text-align: center" ><c:out value="${rs.m28}" /></td>
					<td alian="center" style="text-align: center" ><c:out value="${rs.m29}" /></td>
					<td alian="center" style="text-align: center" ><c:out value="${rs.m30}" /></td>
					<td alian="center" style="text-align: center" ><c:out value="${rs.m31}" /></td>
				</tr>
				<c:set var="NameChk" value="${rs.labelName}"/>
				</c:forEach>
			</c:when>
			<c:otherwise>
				<tr><td align="center" colspan="33">검색된 데이터가 없습니다.</td></tr>
			</c:otherwise>
		</c:choose>
	</tbody>
</table>