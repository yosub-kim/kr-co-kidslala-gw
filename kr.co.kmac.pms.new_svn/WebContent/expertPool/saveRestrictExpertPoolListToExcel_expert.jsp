<%@ page contentType="application/vnd.ms-excel; charset=utf-8"%>
<%@page import="kr.co.kmac.pms.common.util.StringUtil"%>
<%@ include file="/common/include/taglib.jsp"%>
<table width="665" border=1>
	<thead>
		<tr>
			<td align="center" bgcolor="silver">성명</td>
			<td align="center" bgcolor="silver">주민번호</td>
			<td align="center" bgcolor="silver">등급</td>
			<td align="center" bgcolor="silver">프로젝트 정보</td>
			<td align="center" bgcolor="silver">실행기간</td>
			<td align="center" bgcolor="silver">PM</td>
		</tr>
	</thead>
	<tbody>
		<c:choose>
			<c:when test="${!empty list.list}">
				<c:set var="NameChk" value=""/>
				<c:set var="name" value=" "/>
				<c:forEach var="rs" items="${list.list}">
					<tr onclick="detail('<c:out value="${rs.ssn}" />')" style="cursor: hand;" onmouseover="row_over(this)" onmouseout="row_out(this)">
					<td style="text-align: center" <c:if test="${rs.projectName == null}">bgcolor="#ffdfff"</c:if>><c:out value="${rs.name}" escapeXml="false" /></td>
					<td rowspan="<c:out value="${rs.ssnn}"/>" style="text-align: center" <c:if test="${rs.projectName == null}">bgcolor="#ffdfff"</c:if>><c:out value="${rs.uid}" escapeXml="false" /></td>
					<td alian="center">
						<c:choose><c:when test="${rs.resultPosition eq '40EE'}"><c:out value="엑스퍼트"/></c:when><c:when test="${rs.resultPosition eq '41EF'}"><c:out value="엑스퍼트Ⅰ"/></c:when>
						<c:when test="${rs.resultPosition eq '42EG'}"><c:out value="엑스퍼트Ⅱ"/></c:when><c:when test="${rs.resultPosition eq '43EH'}"><c:out value="엑스퍼트Ⅲ"/></c:when>
						<c:when test="${rs.resultPosition eq '44EI'}"><c:out value="엑스퍼트Ⅳ"/></c:when><c:when test="${rs.resultPosition eq ''}"><c:out value="엑스퍼트"/></c:when>
						<c:when test="${rs.resultPosition eq '44EE'}"><c:out value="엑스퍼트"/></c:when>
						<c:otherwise><c:out value="${rs.resultPosition}" /></c:otherwise></c:choose>
					</td>
					<td class="subject">&nbsp&nbsp[<c:out value="${rs.projectCode}" />] <c:out value="${rs.projectName}" /></td>
					<td alian="center">
						<c:choose><c:when test="${rs.realStartDate == null}"> </c:when><c:otherwise><c:out value="${rs.realStartDate}" /> ~ </c:otherwise></c:choose>
						<c:choose><c:when test="${rs.realEndDate == null}"> </c:when><c:otherwise><c:out value="${rs.realEndDate}" /></c:otherwise></c:choose>
					</td>
					<td alian="center"><c:out value="${rs.projectManager}" /></td>
					</tr>
				</c:forEach>
			</c:when>
			<c:otherwise>
				<tr><td align="center" colspan="6">검색된 데이터가 없습니다.</td></tr>
			</c:otherwise>
		</c:choose>
	</tbody>
</table>