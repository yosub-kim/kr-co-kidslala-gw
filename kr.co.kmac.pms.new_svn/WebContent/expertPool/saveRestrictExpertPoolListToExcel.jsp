<%@ page contentType="application/vnd.ms-excel; charset=utf-8"%>
<%@page import="kr.co.kmac.pms.common.util.StringUtil"%>
<%@ include file="/common/include/taglib.jsp"%>
<table width="665" border=1>
	<thead>
		<tr>
			<td align="center" bgcolor="silver">No</td>
			<td align="center" bgcolor="silver">인트라넷계정</td>
			<td align="center" bgcolor="silver">이름</td>
			<td align="center" bgcolor="silver">주민번호</td>
			<td align="center" bgcolor="silver">직업구분</td>
			<td align="center" bgcolor="silver">소속기관</td>
			<td align="center" bgcolor="silver">소속부서</td>
			<td align="center" bgcolor="silver">직위</td>
			<td align="center" bgcolor="silver">휴대전화</td>
			<td align="center" bgcolor="silver">이메일</td>
			<td align="center" bgcolor="silver">주소</td>
			<td align="center" bgcolor="silver">등록일</td>
			<td align="center" bgcolor="silver">수정일</td>
			<td align="center" bgcolor="silver">등록자</td>
		</tr>
	</thead>
	<tbody>
	<c:forEach var="rs" items="${list.list}">
		<tr>
			<td align="center"><c:out value="${rs.rank}" /></td>
			<td align="center"><c:out value="${rs.userId}" /></td>
			<td align="center"><c:out value="${rs.name}" /></td>
			<td align="center"><c:out value="${rs.uid}" /></td>
			<td align="center"><c:out value="${rs.jobClass}" /></td>
			<td align="center"><c:out value="${rs.company}" /></td>
			<td align="center"><c:out value="${rs.deptName}" /></td>
			<td align="center"><c:out value="${rs.companyPositionName}" /></td>
			<td align="center"><c:out value="${rs.mobileNo}" /></td>
			<td align="center"><c:out value="${rs.email }" /></td>
			<td align="center"><c:out value="${rs.address1}" /></td>
			<td align="center"><c:out value="${rs.createdDate}" /></td>
			<td align="center"><c:out value="${rs.modifiedDate}" /></td>
			<td align="center"><c:out value="${rs.createrId}" /></td>
		</tr>
	</c:forEach>
	</tbody>
</table>