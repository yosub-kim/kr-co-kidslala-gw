<%@ page contentType="application/vnd.ms-excel; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<table width="765" border=1>
	<thead>
		<tr>
			<td rowspan="2" bgcolor="silver">구분</td>
			<td rowspan="2" bgcolor="silver">이름</td>
			<td rowspan="2" bgcolor="silver">고객사</td>
			<td colspan="5" bgcolor="silver">가치창출정보</td>
			<td colspan="4" bgcolor="silver">산업별</td>
			<td colspan="6" bgcolor="silver">비즈니스유형별</td>
			<td rowspan="2" bgcolor="silver">소계</td>			
		</tr>
		<tr>
			<td bgcolor="silver">니즈</td>
			<td bgcolor="silver">상담</td>
			<td bgcolor="silver">제안</td>
			<td bgcolor="silver">프로</td>
			<td bgcolor="silver">관계</td>
			<td bgcolor="silver">제조</td>
			<td bgcolor="silver">서비</td>
			<td bgcolor="silver">공공</td>
			<td bgcolor="silver">행정</td>
			<td bgcolor="silver">진단</td>
			<td bgcolor="silver">서치</td>
			<td bgcolor="silver">컨설</td>
			<td bgcolor="silver">인재</td>
			<td bgcolor="silver">미디</td>
			<td bgcolor="silver">기타</td>			
		</tr>
	</thead>
	<tbody>
	<c:forEach var="result" items="${list.list}">
		<tr>
			<td bgcolor="silver" nowrap><c:out value="${result.name}" escapeXml="false"/></td>
			<td bgcolor="silver" nowrap><c:out value="${result.writerName}" /></td>
			<td align="right"><c:out value="${result.customerCnt}" /></td>
			<td align="right"><c:out value="${result.ctf}" /></td>
			<td align="right"><c:out value="${result.ctb}" /></td>
			<td align="right"><c:out value="${result.ctc}" /></td>
			<td align="right"><c:out value="${result.cth}" /></td>
			<td align="right"><c:out value="${result.cti}" /></td>
			
			<td align="right"><c:out value="${result.ita}" /></td>
			<td align="right"><c:out value="${result.itb}" /></td>
			<td align="right"><c:out value="${result.itd}" /></td>
			<td align="right"><c:out value="${result.itc}" /></td>
			
			<td align="right"><c:out value="${result.a}" /></td>
			<td align="right"><c:out value="${result.b}" /></td>
			<td align="right"><c:out value="${result.c}" /></td>
			<td align="right"><c:out value="${result.d}" /></td>
			<td align="right"><c:out value="${result.e}" /></td>
			<td align="right"><c:out value="${result.f}" /></td>
			
			<td align="right"><c:out value="${result.subTotal}" /></td>
			
		</tr>
	</c:forEach>
	</tbody>
</table>

