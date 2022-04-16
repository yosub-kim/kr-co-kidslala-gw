<%@ include file="/m/web/common/includeAuth.jsp"%>
<%@ taglib prefix="c"			uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="authz"		uri="http://acegisecurity.org/authz" %>

<!DOCTYPE html>
<html lang="en">
<head>
	<%@ include file="/m/web/common/includeMeta.jsp"%>
	<%@ include file="/m/web/common/includeCss.jsp"%>
	<%@ include file="/m/web/common/includeJavaScript.jsp"%>
</head> 
<body> 
<input type="hidden" value="<authz:authentication operation="email" />" id="useremail">
<input type="hidden" value="<authz:authentication operation="username" />" id="username">
<form name="webmailForm" method="post">
	<input name="user_account" value="" type="hidden" />
	<input name="pass" value="" type="hidden" />
	<input name="cmd" value="" type="hidden" />
</form>
<div data-role="page" id="indexPage" style="background: white; background-color: white;">

	<div data-role="header" data-theme="a">
		<h1>KMAC 인트라넷</h1>
		<%--
		<a href="../../" data-icon="home" data-iconpos="notext" data-direction="reverse">Home</a>
		<a href="../nav.html" data-icon="search" data-iconpos="notext" data-rel="dialog" data-transition="fade">Search</a>
		 --%>
	</div><!-- /header -->

	<ul data-role="listview" data-divider-theme="d" style="height: 23px;">
		<li data-role="list-divider" class="sv_tit">주요 알림</li>
	</ul>
	<div data-role="content">
		<div class="ui-grid-b" style="text-align: center;">
			<div class="ui-block-a"><a href="" data-transition="slide" id="goEmail" rel="external">
				<span class="sv_icn">
					<img alt="새메일" src="/m/img/index-1-1.png" width="59px" height="50px">
					<span class="sv_nn" id="mailCount"></span>
				</span>
				<span class="sv_txt">새 메일</span>
			</a></div>
			<div class="ui-block-b"><a href="/m/web/sanction/sanctionCabinetList.jsp" data-transition="slide" rel="external">
				<span class="sv_icn">
					<img alt="새업무" src="/m/img/index-1-2.png" width="59px" height="50px">
					<span class="sv_nn" id="workCount"></span>
				</span>
				<span class="sv_txt">새 업무</span>
			</a></div>
			<div class="ui-block-c"><a href="/m/web/schedule/scheduleDateList.jsp" data-transition="slide" rel="external">
				<span class="sv_icn">
					<img alt="나의일정" src="/m/img/index-1-3.png" width="59px" height="50px">
					<span class="sv_nn" id="scheduleCount"></span>
				</span>
				<span class="sv_txt">나의일정</span>
			</a></div>
		</div>
	</div>
	<ul data-role="listview" data-divider-theme="d" style="height: 23px;">
		<li data-role="list-divider" class="sv_tit">주요 메뉴</li>
	</ul>
	<div data-role="content">
		<div class="ui-grid-b" style="text-align: center; margin-bottom: 80px;" > 
			<c:choose>
				<c:when test="${sessionScope.jobClass eq 'C'}">
					<div class="ui-block-a"><a href="javascript:alert('접근 권한이 없습니다.');" data-transition="slide">
				</c:when>
				<c:otherwise>
					<div class="ui-block-a"><a href="/action/MobileBoardAction.do?mode=getBoardIndex" data-transition="slide" rel="external">
				</c:otherwise>
			</c:choose>
				<span class="sv_icn">
					<img alt="게시판" src="/m/img/index-2-1.png" width="59px" height="50px">
				</span>
				<span class="sv_txt">게시판</span> 
			</a></div>
			<c:choose>
				<c:when test="${sessionScope.jobClass eq 'A' || sessionScope.jobClass eq 'J' || sessionScope.jobClass eq 'H' || sessionScope.jobClass eq 'B' || sessionScope.jobClass eq 'N'}">
					<div class="ui-block-b"><a href="/m/web/customer/customerList.jsp" data-transition="slide" rel="external">
				</c:when>
				<c:otherwise>
					<div class="ui-block-b"><a href="javascript:alert('접근 권한이 없습니다.');" data-transition="slide">
				</c:otherwise>
			</c:choose>
				<span class="sv_icn">
					<img alt="고객정보" src="/m/img/index-2-2.png" width="59px" height="50px">
				</span>
				<span class="sv_txt">고객정보</span>
			</a></div>
			<div class="ui-block-c"><a href="/action/ScheduleMobileAction.do?mode=scheduleOfMonth" data-transition="slide" rel="external" >
				<span class="sv_icn">
					<img alt="스케줄" src="/m/img/index-2-3.png" width="59px" height="50px">
				</span>
				<span class="sv_txt">스케줄</span>
			</a></div>
			<c:choose>
				<c:when test="${sessionScope.jobClass eq 'C'}">
					<div class="ui-block-a"><a href="javascript:alert('접근 권한이 없습니다.');" data-transition="slide">
				</c:when>
				<c:otherwise>
					<div class="ui-block-a" style="padding-top: 15px;"><a href="/m/web/sanction/sanctionIndex.jsp" data-transition="slide" rel="external">
				</c:otherwise>
			</c:choose>
				<span class="sv_icn">
					<img alt="전자결재" src="/m/img/index-3-1.png" width="59px" height="50px">
				</span>
				<span class="sv_txt">전자결재</span>
			</a></div>
			<!-- <div class="ui-block-b" style="padding-top: 15px;"><a href="/m/web/preport/preportList.jsp" data-transition="slide" rel="external">
				<span class="sv_icn">
					<img alt="지도일지" src="/m/img/index-3-2.png" width="59px" height="50px">
					<span class="sv_nn" id="preportCount"></span>
				</span>
				<span class="sv_txt">지도일지</span>
			</a></div> -->
			<c:choose>
				<c:when test="${sessionScope.jobClass eq 'C'}">
					<div class="ui-block-c"><a href="javascript:alert('접근 권한이 없습니다.');" data-transition="slide">
				</c:when>
				<c:otherwise>
					<div class="ui-block-c" style="padding-top: 15px;"><a href="/m/web/expertpool/expertpoolList.jsp" data-transition="slide" rel="external">
				</c:otherwise>
			</c:choose>
				<span class="sv_icn">
					<img alt="인력관리" src="/m/img/index-3-3.png" width="59px" height="50px">
				</span>
				<span class="sv_txt">인력관리</span>
			</a></div>
			<c:choose>
				<c:when test="${sessionScope.jobClass eq 'C'}">
					<div class="ui-block-c"><a href="javascript:alert('접근 권한이 없습니다.');" data-transition="slide">
				</c:when>
				<c:otherwise>
					<div class="ui-block-c" style="padding-top: 15px;"><a href="/m/web/reservation/equipmentList.jsp" data-transition="slide" rel="external">
				</c:otherwise>
			</c:choose>
				<span class="sv_icn">
					<img alt="장비예약" src="/m/img/index-4-1.png" width="59px" height="50px">
				</span>
				<span class="sv_txt">장비예약</span>
			</a></div>
			<c:choose>
				<c:when test="${sessionScope.jobClass eq 'C'}">
					<div class="ui-block-c"><a href="javascript:alert('접근 권한이 없습니다.');" data-transition="slide">
				</c:when>
				<c:otherwise>
					<div class="ui-block-c"  style="padding-top: 15px;"><a href="javascript:alert('모바일 기능 점검으로 인해 PC인트라넷을 통해서 예약하시기 바랍니다.');" data-transition="slide" >
					<!-- <div class="ui-block-c" style="padding-top: 15px;"><a href="/m/web/reservation/meetingRoomList.jsp" data-transition="slide" rel="external" > -->
				</c:otherwise>
			</c:choose>
				<span class="sv_icn">
					<img alt="회의실예약" src="/m/img/index-4-2.png" width="59px" height="50px">
				</span>
				<span class="sv_txt">회의실예약</span>
			</a></div>
			<c:choose>
				<c:when test="${sessionScope.jobClass eq 'C'}">
					<div class="ui-block-c"><a href="javascript:alert('접근 권한이 없습니다.');" data-transition="slide" >
				</c:when>
				<c:otherwise>
					<div class="ui-block-c" style="padding-top: 15px;"><a href="/m/web/reservation/bizSchoolIndex.jsp" data-transition="slide" rel="external" >
				</c:otherwise>
			</c:choose>
				<span class="sv_icn">
					<img alt="비즈니스스쿨예약" src="/m/img/index-4-3.png" width="59px" height="50px">
				</span>
				<span class="sv_txt">비즈니스스쿨예약</span>
			</a></div>
		</div>
	</div><!-- /content -->
	
	<jsp:include page="/m/web/common/footer.jsp" >
		<jsp:param value="fixed" name="data_position" />
	</jsp:include><!-- /footerx	 -->
	
	
</div><!-- /page -->
</body>
</html>
