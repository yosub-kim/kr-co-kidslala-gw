<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>

	<div id="tab_menu_content">
		<!-- 비상연락망 데이터 -->
		<div class="board_contents">
			<table id="projectProgressTable" class="tbl-edit td-c">
				<colgroup>
					<col style="width: 15%" />
					<col style="width: 15%" />
					<col style="width: 15%" />
					<c:if test="${jobClass != 'J' and jobClass != 'R'}">
						<col style="width: 15%" />
					</c:if>
					<col style="width: 15%" />
					<col style="width: *%" />
				</colgroup>
				<tr>
					<th>소속</th>
					<th>직책/직위</th>
					<th>성명</th>
				<c:if test="${jobClass != 'J' and jobClass != 'R'}">
					<th>내선전화</th>
				</c:if>
					<th>휴대전화</th>
					<th>E-Mail</th>
				</tr>
				<tbody>
					<c:set var="chkVal" value=""/>
					<c:forEach var="grp" items="${result}">
						<c:choose>
							<c:when test="${grp.ssn eq 'A000006' }"><tr></c:when>
							<c:otherwise><tr style="cursor: hand;" onmouseover="row_over(this)" onmouseout="row_out(this)" onClick="javascript:detailview('<c:out value="${grp.ssn}"/>');"></c:otherwise>
						</c:choose>
						<c:if test="${grp.dept != chkVal}">
							<c:choose><c:when test="${jobClass == 'R' }" >
								<td rowspan="<c:out value="${grp.rowCnt }" />" style="text-align: center"><c:out value="${grp.dept}" escapeXml="false" /></td>
							</c:when>
							<c:otherwise>
								<td rowspan="<c:out value="${grp.rowCnt}"/>" style="text-align: center"><c:out value="${grp.dept}" escapeXml="false" /></td>
							</c:otherwise></c:choose>
						</c:if>
							<c:choose><c:when test="${jobClass == 'R' }" >
								<td style="text-align: center;">아르바이트</td>
							</c:when>
							<c:otherwise>
								<td style="text-align: center;"><c:out value="${grp.companyPosition}" /></td>
							</c:otherwise></c:choose>
							<td style="text-align: center;"><c:out value="${grp.name}" /></td>
						<c:if test="${jobClass != 'J' and jobClass != 'R'}">
							<td style="text-align: center;"><c:out value="${grp.companyTelNo}" /></td>
						</c:if>
							<td style="text-align: center;"><c:out value="${grp.mobileNo}" /></td>
							<td class="a-l">
								<c:out value="${grp.email}" />
							</td>
						</tr>
						<c:set var="chkVal" value="${grp.dept}"/>
					</c:forEach>
				</tbody>
			</table>
		</div>	
	</div>