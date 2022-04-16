<%@page import="kr.co.kmac.pms.common.util.DateUtil"%>
<%@ taglib prefix="c" 			uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="expertPool"	uri="/WEB-INF/expertPool.tld" %>
<%@ taglib prefix="fmt"			uri="http://java.sun.com/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
	<%@ include file="/m/web/common/includeMeta.jsp"%>
	<%@ include file="/m/web/common/includeCss.jsp"%>
	<%@ include file="/m/web/common/includeJavaScript.jsp"%>
	<script src="https://apis.google.com/js/client.js?onload=handleClientLoad"></script>
	<script src="/m/js/moment.js"></script>	
	
</head>
<body> 
<div data-role="page" id="scheduleDateListPage">

	<input type="hidden" id="scheduleSelDate" value="<%=DateUtil.getDateTimeByPattern("yyyy-MM-dd")%>"/>
	
	<div data-role="header" data-theme="a">
		<h1 onclick="goHOME();">KMAC 인트라넷</h1>
		<a href="/m/index.jsp" data-icon="arrow-l" data-iconpos="notext" data-direction="reverse">back</a>
		<a href="/m/web/schedule/scheduleWrite.jsp?saveMode=INSERT" data-icon="plus" data-iconpos="notext" >추가</a>
	</div><!-- /header -->

	<div data-role="content">
		<div class="ui-grid-solo" style="text-align: center; ">
			<ul data-role="listview" id="scheduleListView">
				<li data-role="list-divider" style="font-weight: bold;">[<%=DateUtil.getDateTimeByPattern("yyyy-MM-dd")%>] 오늘의 일정</li>
			</ul>
		</div>
	</div><!-- /content -->
	
	<jsp:include page="/m/web/common/footer.jsp" >
		<jsp:param value="fixed" name="data_position"/>
	</jsp:include><!-- /footerx	 -->	

</div><!-- /page -->
</body>
</html>
