<%@page import="kr.co.kmac.pms.common.util.StringUtil"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="fmt"		uri="http://java.sun.com/jstl/fmt" %>
<%
	String yyyymm = StringUtil.getCurr("yyyyMM");
%>
<!DOCTYPE html>
<html lang="en">
<head>
	<%@ include file="/m/web/common/includeMeta.jsp"%>
	<%@ include file="/m/web/common/includeCss.jsp"%>
	<%@ include file="/m/web/common/includeJavaScript.jsp"%>
</head> 
<body> 
<div data-role="page">

	<div data-role="header" data-theme="a">
		<h1 onclick="goHOME();">KMAC 인트라넷</h1>
		<a href="/m/index.jsp" data-icon="arrow-l" data-iconpos="notext" data-direction="reverse">back</a>
	</div><!-- /header -->

	<div data-role="content">
		<div class="ui-grid-solo" style="text-align: center; ">
			<ul data-role="listview">
				<li data-role="list-divider">비즈니스 스쿨 예약</li>
				<li>		
					<a href="/m/web/reservation/bizSchoolList.jsp" data-transition="slide">
					<img src="/m/img/board.png" alt="게시판" class="ui-li-icon" style="middle: .0em; padding-top: 5px;">
					<h4 class="ui-li-heading">강의실 예약</h4>
					</a>
				</li>
				<li>
					<a href="/action/ParkingReservationAction.do?mode=getParkingReservationMonthlyList&month=<%=yyyymm %>" data-transition="slide">
					<img src="/m/img/board.png" alt="게시판" class="ui-li-icon" style="middle: .0em; padding-top: 5px;">
					<h4 class="ui-li-heading">주차 예약</h4>
					</a>
				</li>
			</ul>
		</div>		
		
	</div><!-- /content -->
	
	<jsp:include page="/m/web/common/footer.jsp" >
		<jsp:param value="fixed" name="data_position" />
	</jsp:include><!-- /footerx	 -->
	
	
</div><!-- /page -->
</body>
</html>
