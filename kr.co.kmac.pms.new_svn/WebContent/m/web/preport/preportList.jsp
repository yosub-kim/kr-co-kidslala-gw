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
<input type="hidden" id="preportListPg" value="0"/>
<div data-role="page" id="preportListPage">
	
	<div data-role="header" data-theme="a"> 
		<h1 onclick="goHOME();">KMAC 인트라넷</h1>
		<a href="/m/index.jsp" data-icon="arrow-l" data-iconpos="notext" data-direction="reverse">back</a>
	</div><!-- /header -->

	<div data-role="content">
		<div class="ui-grid-solo" style="text-align: center; ">
			<ul data-role="listview" id="preportListView">
				<li data-role="list-divider">지도일지</li>
			</ul>
		</div>
		<div class="ui-grid-solo" style="text-align: center; padding-top: 20px;">
			<a href="#" 
				data-role="button" data-icon="plus" data-theme="e" data-corners="false" id="preportListMore">
				<span id="preportListPageInfo"></span>
			</a>
		</div>	
	</div><!-- /content -->
	
	
	<jsp:include page="/m/web/common/footer.jsp" >
		<jsp:param value="data_position" name=""/>
	</jsp:include><!-- /footerx	 -->
	
	
</div><!-- /page -->
</body>
</html>
