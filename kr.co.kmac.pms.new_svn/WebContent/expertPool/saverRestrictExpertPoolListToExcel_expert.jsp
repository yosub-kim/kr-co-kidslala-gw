<%@ page contentType="application/vnd.ms-excel; charset=utf-8"%>
<%@page import="kr.co.kmac.pms.common.util.StringUtil"%>
<%@ include file="/common/include/taglib.jsp"%>
<table width="665" border=1>
	<thead>
		<tr>
			<td align="center" bgcolor="silver">조직단위</td>
			<td align="center" bgcolor="silver">성명</td>
			<td align="center" bgcolor="silver">주민번호</td>
			<td align="center" bgcolor="silver">등급</td>
			<td align="center" bgcolor="silver">유형</td>
			<td align="center" bgcolor="silver">코드</td>
			<td align="center" bgcolor="silver">프로젝트</td>
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
												<c:if test="${rs.des != NameChk}">
											
													<td rowspan="<c:out value="${rs.dept}"/>" style="text-align: center" <c:if test="${rs.projectName == null}">bgcolor="#ffdfff"</c:if>><c:out value="${rs.des} </br> (${rs.expertCnt}명)" escapeXml="false" /></td>
												</c:if>
												<c:if test="${rs.name != chk}">
													<td rowspan="<c:out value="${rs.ssnn}"/>" style="text-align: center" <c:if test="${rs.projectName == null}">bgcolor="#ffdfff"</c:if>><c:out value="${rs.name}" escapeXml="false" /></td>
												</c:if>
												<c:if test="${rs.name != chk}">
													<td rowspan="<c:out value="${rs.ssnn}"/>" style="text-align: center" <c:if test="${rs.projectName == null}">bgcolor="#ffdfff"</c:if>><c:out value="${rs.uid}" escapeXml="false" /></td>
												</c:if>
													<td alian="center"><c:choose><c:when test="${rs.position eq '40EE'}"><c:out value="엑스퍼트"/></c:when><c:when test="${rs.position eq '41EF'}"><c:out value="엑스퍼트Ⅰ"/></c:when>
													<c:when test="${rs.position eq '42EG'}"><c:out value="엑스퍼트Ⅱ"/></c:when><c:when test="${rs.position eq '43EH'}"><c:out value="엑스퍼트Ⅲ"/></c:when>
													<c:when test="${rs.position eq '44EI'}"><c:out value="엑스퍼트Ⅳ"/></c:when><c:when test="${rs.position eq ''}"><c:out value="엑스퍼트"/></c:when>
													<c:when test="${rs.position eq '44EE'}"><c:out value="엑스퍼트"/></c:when>
													<c:otherwise><c:out value="${rs.position}" /></c:otherwise></c:choose></td>
													<td alian="center"><c:choose><c:when test="${rs.projectTypeCode eq 'ED'}"><c:out value="${'MH'}"/></c:when><c:otherwise><c:out value="${rs.projectTypeCode}" /></c:otherwise></c:choose></td>
													<!--<td alian="center"<c:if test="${rs.projectTypeCode == 'ED'}"><c:out value="MH" /></c:if>><c:out value="${rs.projectTypeCode}" /></td> -->
													<td alian="center"<c:if test="${rs.projectName == null}">bgcolor="#ffdfff"</c:if>><c:out value="${rs.projectCode}" /></td>
													<td alian="center"<c:if test="${rs.projectName == null}">bgcolor="#ffdfff"</c:if>><c:out value="${rs.projectName}" /></td>
													<td alian="center"<c:if test="${rs.realStartDate == null && rs.realEndDate == null}">bgcolor="#ffdfff"</c:if>>
														<c:choose><c:when test="${rs.realStartDate == null}"> </c:when><c:otherwise><c:out value="${rs.realStartDate}" /> ~ </c:otherwise></c:choose>
														<c:choose><c:when test="${rs.realEndDate == null}"> </c:when><c:otherwise><c:out value="${rs.realEndDate}" /></c:otherwise></c:choose>
													</td>
													<td alian="center"<c:if test="${rs.projectName == null}">bgcolor="#ffdfff"</c:if>><c:out value="${rs.projectManager}" /></td>
												</tr>
												<c:set var="NameChk" value="${rs.des}"/>
												<c:set var="chk" value="${rs.name}"/>
												
											</c:forEach>
										</c:when>
										<c:otherwise>
											<tr><td align="center" colspan="7">검색된 데이터가 없습니다.</td></tr>
										</c:otherwise>
									</c:choose>
	</tbody>
</table>