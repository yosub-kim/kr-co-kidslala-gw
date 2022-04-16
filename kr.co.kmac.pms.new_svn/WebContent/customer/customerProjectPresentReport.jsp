<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<table width="765" class="listTable" style="table-layout: fixed;">
	<thead>
		<tr>
			<td rowspan="2" style="width: 100px;">구분</td>
			<td rowspan="2" style="width: 100px;">이름</td>
			<td rowspan="2" style="width: 40px;">고객사</td>
			<td colspan="5" style="width: 160px;">가치창출정보</td>
			<td colspan="4" style="width: 128px;">산업별</td>
			<td colspan="6" style="width: 192px;">비즈니스유형별</td>
			<td rowspan="2" style="width: 35px;">소계</td>
		</tr>
		<tr>
			<td style="width: 32px;">니즈</td>
			<td style="width: 32px;">상담</td>
			<td style="width: 32px;">제안</td>
			<td style="width: 32px;">프로</td>
			<td style="width: 32px;">관계</td>
			
			<td style="width: 32px;">제조</td>
			<td style="width: 32px;">서비</td>
			<td style="width: 32px;">공공</td>
			<td style="width: 32px;">행정</td>
			
			<td style="width: 32px;">진단</td>
			<td style="width: 32px;">서치</td>
			<td style="width: 32px;">컨설</td>
			<td style="width: 32px;">인재</td>
			<td style="width: 32px;">미디</td>
			<td style="width: 32px;">기타</td>
		</tr>
	</thead>
</table>	
<div style="height: 336px; overflow-y: scroll; overflow-x: none;">
<table width="765" class="listTable" style="table-layout: fixed;">
	<tbody>
	<c:forEach var="result" items="${list.list}">
		<tr <c:if test="${result.name == '소계'}">bgcolor="#f2f8fa" </c:if> onmouseover="row_over(this)" onmouseout="row_out(this)">
			<td align="center" style="width: 100px;"><c:out value="${result.name}" escapeXml="false"/></td>
			<td align="center" style="width: 100px;"><c:out value="${result.writerName}" /></td>
			<td align="right" style="width: 40px;"><c:out value="${result.customerCnt}" /></td>
			<td align="right" style="width: 32px;"><c:if test="${result.name != '소계'}"><a href="javascript:detail_view('CTF','','','','<c:out value="${result.writerSsn}"/>');"></c:if><c:out value="${result.ctf}" /></a></td>
			<td align="right" style="width: 32px;"><c:if test="${result.name != '소계'}"><a href="javascript:detail_view('CTB','','','','<c:out value="${result.writerSsn}"/>');"></c:if><c:out value="${result.ctb}" /></a></td>
			<td align="right" style="width: 32px;"><c:if test="${result.name != '소계'}"><a href="javascript:detail_view('CTC','','','','<c:out value="${result.writerSsn}"/>');"></c:if><c:out value="${result.ctc}" /></a></td>
			<td align="right" style="width: 32px;"><c:if test="${result.name != '소계'}"><a href="javascript:detail_view('CTH','','','','<c:out value="${result.writerSsn}"/>');"></c:if><c:out value="${result.cth}" /></a></td>
			<td align="right" style="width: 32px;"><c:if test="${result.name != '소계'}"><a href="javascript:detail_view('CTI','','','','<c:out value="${result.writerSsn}"/>');"></c:if><c:out value="${result.cti}" /></a></td>
			
			<td align="right" style="width: 32px;"><c:if test="${result.name != '소계'}"><a href="javascript:detail_view('','ITA','','1','<c:out value="${result.writerSsn}"/>');"></c:if><c:out value="${result.ita}" /></a></td>
			<td align="right" style="width: 32px;"><c:if test="${result.name != '소계'}"><a href="javascript:detail_view('','ITB','','1','<c:out value="${result.writerSsn}"/>');"></c:if><c:out value="${result.itb}" /></a></td>
			<td align="right" style="width: 32px;"><c:if test="${result.name != '소계'}"><a href="javascript:detail_view('','ITD','','1','<c:out value="${result.writerSsn}"/>');"></c:if><c:out value="${result.itd}" /></a></td>
			<td align="right" style="width: 32px;"><c:if test="${result.name != '소계'}"><a href="javascript:detail_view('','ITC','','1','<c:out value="${result.writerSsn}"/>');"></c:if><c:out value="${result.itc}" /></a></td>
			
			<td align="right" style="width: 32px;"><c:if test="${result.name != '소계'}"><a href="javascript:detail_view('','','BTBC','1','<c:out value="${result.writerSsn}"/>');"></c:if><c:out value="${result.a}" /></a></td>
			<td align="right" style="width: 32px;"><c:if test="${result.name != '소계'}"><a href="javascript:detail_view('','','BTD','1','<c:out value="${result.writerSsn}"/>');"></c:if><c:out value="${result.b}" /></a></td>
			<td align="right" style="width: 32px;"><c:if test="${result.name != '소계'}"><a href="javascript:detail_view('','','BTA','1','<c:out value="${result.writerSsn}"/>');"></c:if><c:out value="${result.c}" /></a></td>
			<td align="right" style="width: 32px;"><c:if test="${result.name != '소계'}"><a href="javascript:detail_view('','','BTEF','1','<c:out value="${result.writerSsn}"/>');"></c:if><c:out value="${result.d}" /></a></td>
			<td align="right" style="width: 32px;"><c:if test="${result.name != '소계'}"><a href="javascript:detail_view('','','BTG','1','<c:out value="${result.writerSsn}"/>');"></c:if><c:out value="${result.e}" /></a></td>
			<td align="right" style="width: 32px;"><c:if test="${result.name != '소계'}"><a href="javascript:detail_view('','','BTHI','1','<c:out value="${result.writerSsn}"/>');"></c:if><c:out value="${result.f}" /></a></td>
			
			<td align="right" style="width: 35px;"><c:out value="${result.subTotal}" /></td>
		</tr>
	</c:forEach>
	</tbody>
</table>
</div>

<!--
writerSsn : result.writerSsn
position = result.position
id = result.id
memberCnt = result.memberCnt
-->
