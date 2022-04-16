<%@ taglib prefix="c" 		uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="fmt"		uri="http://java.sun.com/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<%@ include file="/m/web/common/includeMeta.jsp"%>
	<%@ include file="/m/web/common/includeCss.jsp"%>
	<%@ include file="/m/web/common/includeJavaScript.jsp"%>
</head>  
<body>  
<div data-role="page">
	<form id="parkingInsertForm" action="">
		<input type="hidden" name="idx" value="<c:out value="${parkingReservation.idx }"/>">
		<div data-role="header" data-theme="a"> 
			<h1 onclick="goHOME();">KMAC 인트라넷</h1>
			<a href="" data-icon="arrow-l" data-iconpos="notext" data-rel="back" data-direction="reverse">back</a>
		</div><!-- /header -->
		<div data-role="content">
			<div class="ui-grid-solo" style="text-align: center; margin-bottom: 30px;">
				<ul data-role="listview" id="parkingListView">
					<li data-role="list-divider">주차 예약</li>
				</ul>
			</div>	
			<div data-role="fieldcontain" class="ui-hide-label ui-body ui-br">
				<label for="basic">일자: <c:out value="${parkingReservation.pdate}"/></label>
				<input type="hidden" name="pdate" id="pdate" value="<c:out value="${parkingReservation.pdate}"/>">
			</div>
			<div data-role="fieldcontain" class="ui-hide-label ui-body ui-br">
				<label for="basic">주/야간:</label>
				<fieldset data-role="controlgroup" data-mini="true">
					<input type="radio" name="rtime" id="radio-mini-1" value="am" <c:if test="${'am' eq parkingReservation.rtime }">checked="checked" </c:if>/>
					<label for="radio-mini-1">주간</label>
					
					<input type="radio" name="rtime" id="radio-mini-2" value="pm" <c:if test="${'pm' eq parkingReservation.rtime }">checked="checked" </c:if>/>
					<label for="radio-mini-2">야간</label>
				</fieldset>
			</div>			
			<div data-role="fieldcontain" class="ui-hide-label ui-body ui-br">
				<label for="basic">회사명:</label>
				<table style="width: 99%">
					<tr>
						<td>
							<select name="company" id="park_companyGubun" onchange="javascript:parkCompanySelect();">								
								<option value="KMAC" <c:if test="${'KMAC' eq parkingReservation.company }">selected</c:if>>KMAC</option>
								<option value="etc" <c:if test="${'etc' eq parkingReservation.company }">selected</c:if>>기타</option>
							</select>
						</td>
						<td>
							<div id="park_dept_sel" <c:if test="${'etc' eq parkingReservation.company }">style="display: none;"</c:if>>
								<select name="dept_sel">
									<c:forEach var="rs" items="${deptList}">
										<option value="<c:out value="${rs.id}"/>"
											<c:if test="${rs.id eq parkingReservation.dept }">selected</c:if>
										><c:out value="${rs.name}"/></option>
									</c:forEach>	
								</select>
							</div>
							<input type="text" name="dept_txt" id="park_dept_txt" value="<c:out value="${parkingReservation.dept}"/>" data-mini="true" <c:if test="${('KMAC' eq parkingReservation.company) or (empty parkingReservation.company) }">style="display: none;"</c:if>/>
						</td>
					</tr>
				</table>
			</div>
			<div data-role="fieldcontain" class="ui-hide-label ui-body ui-br">
				<label for="basic">성명:</label>
				<input type="text" name="pname" id="pname" value="<c:out value="${parkingReservation.pname}"/>" data-mini="true" />	
			</div>
			<div data-role="fieldcontain" class="ui-hide-label ui-body ui-br">
				<label for="basic">연락처(핸드폰):</label>
				<input type="text" name="ptel" id="ptel" value="<c:out value="${parkingReservation.ptel}"/>" data-mini="true" />	
			</div>
			<div data-role="fieldcontain" class="ui-hide-label ui-body ui-br">
				<label for="basic">차량번호(차량번호 4자리):</label>
				<input type="text" name="pcarNum" id="pcarNum" value="<c:out value="${parkingReservation.pcarNum}"/>" data-mini="true" />	
			</div>
			<div data-role="fieldcontain" class="ui-hide-label ui-body ui-br">
				<label for="basic">방문목적:</label>
				<textarea name="pmemo" id="pmemo" style="min-height: 80px;"><c:out value="${parkingReservation.pmemo}" escapeXml="false"/></textarea>
			</div>
			<c:choose>
				<c:when test="${not empty parkingReservation.idx}">
					<a href="javascript:updateParkingReservation();" data-role="button" data-corners="false" data-icon="star" data-theme="e">수정</a>
				</c:when>
				<c:otherwise>
					<a href="javascript:insertParkingReservation();" data-role="button" data-corners="false" data-icon="star" data-theme="e">등록</a>
				</c:otherwise>
			</c:choose>
		</div><!-- /content -->
	</form>
	<jsp:include page="/m/web/common/footer.jsp" >
		<jsp:param value="" name="data_position" />
	</jsp:include><!-- /footerx	 -->
	
	
</div><!-- /page -->
</body>
</html>
