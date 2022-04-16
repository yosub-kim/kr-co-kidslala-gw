<%@ page contentType="application/vnd.ms-excel; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<table width="765" border="1">
	<thead>
		<tr>
			<td style="background:silver; text-align:center; font-weight:bold;">소속</td>
			<td style="background:silver; text-align:center; font-weight:bold;">직위</td>
			<td style="background:silver; text-align:center; font-weight:bold;">성명</td>
			<c:if test="${jobClass != 'J'}">
				<td style="background:silver; text-align:center; font-weight:bold;">전화번호</td>
			</c:if>
			<td style="background:silver; text-align:center; font-weight:bold;">핸드폰</td>
			<td style="background:silver; text-align:center; font-weight:bold;">E-mail</td>
		</tr>
	</thead>
	<tbody>
	<c:set var="chkVal" value=""/>
	<c:forEach var="rs" items="${result.list}" varStatus="i"> 
		<tr>
			<c:if test="${rs.dept != chkVal}">
				<td rowspan="<c:out value="${rs.rowCnt}"/>"><c:out value="${rs.dept}"/></td>
			</c:if>
			<td><c:out value="${rs.companyPosition}"/></td>
			<td><c:out value="${rs.name}"/></td>
			<c:if test="${jobClass != 'J'}">
				<td><c:out value="${rs.companyTelNo}"/></td>
			</c:if>
			<td><c:out value="${rs.mobileNo}"/></td>
			<td><c:out value="${rs.email}"/></td>
		</tr>
		<c:set var="chkVal" value="${rs.dept}"/>
		</c:forEach>
	</tbody>
</table>