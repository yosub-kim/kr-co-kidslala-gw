<%@page import="kr.co.kmac.pms.common.util.StringUtil"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="fmt"		uri="http://java.sun.com/jstl/fmt" %>
<%
	String year = StringUtil.getCurr("yyyy");	
	String month = StringUtil.getCurr("MM");	
	String day = StringUtil.getCurr("dd");	
%>
<!DOCTYPE html>
<html lang="en">
<head>
	<%@ include file="/m/web/common/includeMeta.jsp"%>
	<%@ include file="/m/web/common/includeCss.jsp"%>
	<%@ include file="/m/web/common/includeJavaScript.jsp"%> 
</head>
<body> 
<div data-role="page" id="reservationDetailPage">
	<div data-role="header" data-theme="a"> 
		<h1 onclick="goHOME();">KMAC 인트라넷</h1>
		<c:choose>
			<c:when test="${gubun eq 'E' }">
				<a href="/m/web/reservation/equipmentList.jsp" data-icon="arrow-l" data-iconpos="notext" data-direction="reverse">back</a>
			</c:when>
			<c:when test="${gubun eq 'C' }">
				<a href="/m/web/reservation/meetingRoomList.jsp" data-icon="arrow-l" data-iconpos="notext" data-direction="reverse">back</a>
			</c:when>
		</c:choose>
	</div><!-- /header -->

	<div data-role="content">
		<div class="ui-grid-solo" style="text-align: center; margin-bottom: 15px;">
			<ul data-role="listview" id="preportListView">
				<li data-role="list-divider"><c:out value="${sdateStr }"/> [ <c:out value="${reservationItem.codeName }"/> ]</li>
				<c:forEach var="rs" items="${reservationList}">
					<c:choose>
				    	<c:when test="${!empty rs.no}">
							<li>
								<a href="/action/ReservationAction.do?mode=getReservationDetail&resvGubun=<c:out value="${gubun}"/>&no=<c:out value="${rs.no}"/>" data-transition="slide">
									<img src="/m/img/board.png" alt="예약불가" class="ui-li-icon" style="middle: .0em; padding-top: 5px;">
									<c:out value="${rs.timeVal }" />시 | <c:out value="${rs.usePer }" />[<c:out value="${rs.useTeam }" />]
								</a>
							</li>
				    	</c:when>
				    	<c:otherwise>
							<li><a href="/m/web/reservation/reservationInsert.jsp?sdate=<c:out value="${sdate }" />&timeVal=<c:out value="${rs.timeVal }" />&resvCode=<c:out value="${reservationItem.resvCode }"/>&gubun=<c:out value="${gubun }"/>" data-transition="slide">
									<img src="/m/img/board.png" alt="예약가능" class="ui-li-icon ui-li-thumb">
									<c:out value="${rs.timeVal }" />시 | <font color="#0080FF">[예약가능]</font>
								</a>
							</li>
				    	</c:otherwise>
					</c:choose>	
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
