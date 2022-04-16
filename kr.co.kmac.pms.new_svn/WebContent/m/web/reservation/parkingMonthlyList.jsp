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
<div data-role="page" id="parkingReservationMonthlyList">
	<div data-role="header" data-theme="a"> 
		<h1 onclick="goHOME();">KMAC 인트라넷</h1>
		<a href="/m/web/reservation/bizSchoolIndex.jsp" data-icon="arrow-l" data-iconpos="notext" data-direction="reverse">back</a>
	</div><!-- /header -->

	<div data-role="content">
		<div class="ui-grid-solo" style="text-align: center; ">
			<ul data-role="listview" id="equipmentListView">
				<li data-role="list-divider">예약일자 선택</li>
				<li style="height: 60px; padding: 0px; margin-bottom: 0px;">
					<table style="width: 100%;">
						<tr>
							<td>
								<select name="selYear" id="selYear" onchange="getParkingListPageSearch()">
									<option value='<c:out value="${selYear-1}" />'><c:out value="${selYear-1}" /></option>
									<option value='<c:out value="${selYear}" />' selected><c:out value="${selYear}" /></option>
									<option value='<c:out value="${selYear+1}" />'><c:out value="${selYear+1}" /></option>
								</select> 
							</td> 
							<td>
								<select name="selMonth" id="selMonth" onchange="getParkingListPageSearch()">
									<option value='01'<c:if test="${selMonth=='01'}">selected</c:if>>1월</option>
									<option value='02'<c:if test="${selMonth=='02'}">selected</c:if>>2월</option>
									<option value='03'<c:if test="${selMonth=='03'}">selected</c:if>>3월</option>
									<option value='04'<c:if test="${selMonth=='04'}">selected</c:if>>4월</option>
									<option value='05'<c:if test="${selMonth=='05'}">selected</c:if>>5월</option>
									<option value='06'<c:if test="${selMonth=='06'}">selected</c:if>>6월</option>
									<option value='07'<c:if test="${selMonth=='07'}">selected</c:if>>7월</option>
									<option value='08'<c:if test="${selMonth=='08'}">selected</c:if>>8월</option>
									<option value='09'<c:if test="${selMonth=='09'}">selected</c:if>>9월</option>
									<option value='10'<c:if test="${selMonth=='10'}">selected</c:if>>10월</option>
									<option value='11'<c:if test="${selMonth=='11'}">selected</c:if>>11월</option>
									<option value='12'<c:if test="${selMonth=='12'}">selected</c:if>>12월</option>
								</select>					
							</td>
						</tr>
					</table>
				</li>
				<li data-role="list-divider">주차 예약</li>
				<c:forEach var="rs" items="${parkingReservationList}">
					<li><a href="/action/ParkingReservationAction.do?mode=getParkingReservationList&yyyyMMdd=<c:out value="${rs.dt}"/>" rel="external" >
					<h4 class='ui-li-heading'>
					<c:choose>
						<c:when test="${rs.weekdayStr == '토요일'}"><font color='#116AF6'><c:out value="${rs.dateStr }"/> <c:out value="${rs.weekdayStr }"/></font></c:when> 
						<c:when test="${rs.weekdayStr == '일요일'}"><font color='#FF3000'><c:out value="${rs.dateStr }"/> <c:out value="${rs.weekdayStr }"/></font></c:when>
						<c:otherwise><c:out value="${rs.dateStr }"/> <c:out value="${rs.weekdayStr }"/></c:otherwise> 
					</c:choose>
					</h4>
					<c:if test="${rs.confirmCnt > 0 || rs.noConfirmCnt > 0}">
					<span class='ui-li-count ui-btn-up-c ui-btn-corner-all'>신청: <c:out value="${rs.noConfirmCnt}"/> | <font color='#008000'>확정: <c:out value="${rs.confirmCnt}"/></font></span>
					</c:if>
					</a></li>
				</c:forEach>
			</ul>
		</div> 
	</div><!-- /content -->
	
	
	<jsp:include page="/m/web/common/footer.jsp" >
		<jsp:param value="data_position" name=""/>
	</jsp:include><!-- /footerx	 -->
	
	
</div><!-- /page -->
</body>
</html>
