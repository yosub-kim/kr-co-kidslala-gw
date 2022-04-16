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
	<input type="hidden" id="reservationGubun" value="E">
	<div data-role="header" data-theme="a"> 
		<h1 onclick="goHOME();">KMAC 인트라넷</h1>
		<a href="javascript:history.back();" data-icon="arrow-l" data-iconpos="notext" data-direction="reverse">back</a>
		<a href="javascript:goInsertParkingReservation('<c:out value="${pdate }"/>');" data-icon="gear">등록</a>
	</div><!-- /header -->

	<div data-role="content">
		<div class="ui-grid-solo" style="text-align: center; ">
			<ul data-role="listview" id="equipmentListView">
				<li data-role="list-divider">주차 예약 [ <c:out value="${pdate }"/> ]</li>
				<c:choose>
					<c:when test="${not empty parkingReservationList}">
						<c:forEach var="rs" items="${parkingReservationList}">
							<input type="hidden" id="parkingIdx" value="<c:out value="${rs.idx}"/>">
							<li style='padding: 0px;' id="parkingReservationItem"><a href='/action/ParkingReservationAction.do?mode=getParkingReservation&idx=<c:out value="${rs.idx }"/>' data-transition='slide'>
								<c:choose>
									<c:when test="${ rs.checkResult eq 'Y'}">
										<h4 class='ui-li-heading-green'>
										[확정]
									</c:when>
									<c:otherwise>
										<h4 class='ui-li-heading-red'>
										[신청]
									</c:otherwise>
								</c:choose>
									<c:out value="${rs.pname}"/>
									(<c:out value="${rs.company}"/> <c:out value="${rs.deptName}"/>)</h4>
								<p class='ui-li-desc' style='font-size: 11px;'>
								연락처: <c:out value="${rs.ptel}"/> 
								| 차량번호: <c:out value="${rs.pcarNum}"/> 
								| 등록자: <c:out value="${rs.writerName}"/>
								| 등록일: <c:out value="${rs.regdate}"/> 
								<!-- 
								-->
								</p>
							</a></li>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<li style='padding: 0px;'><a href='javascript:void()' data-transition='slide'>
							<h4 class='ui-li-heading'>검색결과가 없습니다.</h4>
						</a></li>
					</c:otherwise>
				</c:choose>
			</ul>
		</div> 
	</div><!-- /content -->
	
	
	<jsp:include page="/m/web/common/footer.jsp" >
		<jsp:param value="data_position" name=""/>
	</jsp:include><!-- /footerx	 -->
	
	
</div><!-- /page -->
</body>
</html>
