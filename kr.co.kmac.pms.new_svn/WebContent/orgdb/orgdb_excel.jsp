<%@ page contentType="application/vnd.ms-excel; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<table border="1">
	<tHeader>
		<tr>
			<td width="180px"><b>기관명</b></td>
			<td width="90px"><b>KMAC 와 관계</b></td>
			<td width="90px"><b>전문분야</b></td>
			<td width="100px"><b>전화번호</b></td>
			<td width="70px"><b>등록자</b></td>
			<td width="70px"><b>등록일</b></td>
			<td width="55px"><b>고객정보</b></td>
			<td width="55px"><b>프로젝트</b></td>
			<td width="55px"><b>인력현황</b></td>
		</tr>
	</tHeader>
	<tBody>
		<c:forEach var="obj" items="${result.list}"> 
			<tr>
				<td nowrap><c:out value="${obj.orgName}" escapeXml="false"/></td>
				<td><code:code tableName="RELATION_WITH_KMAC_CODE"  code="${obj.relWithkmac}"/></td>
				<td><code:code tableName="SPECIAL_FIELD_CODE"  code="${obj.specialField}"/></td>
				<td><c:out value="${obj.telNo}" /></td>
				<td ><c:out value="${obj.creator}"/></td>
				<td >
					<fmt:parseDate value="${obj.createDate}" var="dateFmt" pattern="yyyy-mm-dd"/>
					<fmt:formatDate value="${dateFmt}" pattern="yyyy-mm-dd"/>
				</td>
				<td ><c:out value="${obj.customerCnt}" /></td>
				<td ><c:out value="${obj.projectCnt}" /></td>
				<td ><c:out value="${obj.expertCnt}" /></td>
			</tr>
		</c:forEach>
	</tBody>
</table>