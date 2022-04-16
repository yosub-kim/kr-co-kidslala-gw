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
<div data-role="page" id="sanctionIndexPage">

	<div data-role="header" data-theme="a">
		<h1 onclick="goHOME();">KMAC 인트라넷</h1>
		<a href="/m/index.jsp" data-icon="arrow-l" data-iconpos="notext" data-direction="reverse">back</a>
	</div><!-- /header -->

	<div data-role="content">
		<div class="ui-grid-solo" style="text-align: center; ">
			<ul data-role="listview">
				<li data-role="list-divider">전자결재</li>
				<li><a href="/m/web/sanction/sanctionCabinetList.jsp" data-transition="slide">
					<img src="/m/img/kmenu_b.png" alt="doc" class="ui-li-icon ui-li-thumb" style="padding-top: 5px;">
					<h4 class="ui-li-heading"> 
						내 결재함
					</h4>	
					<span class="ui-li-count ui-btn-up-c ui-btn-corner-all" id="sanctionIndexMyWorkCnt"></span>
				</a></li>
				<li><a href="/action/WorkCabinetAction.do?mode=getSanctionTypeForMobile" data-transition="slide">
					<img src="/m/img/kmenu_b.png" alt="doc" class="ui-li-icon ui-li-thumb" style="padding-top: 5px;">
					<h4 class="ui-li-heading">
						기안하기
					</h4>
				</a></li>
				<li><a href="/m/web/sanction/sanctionStateList.jsp" data-transition="slide">
					<img src="/m/img/kmenu_b.png" alt="doc" class="ui-li-icon ui-li-thumb" style="padding-top: 5px;">
					<h4 class="ui-li-heading">
						결재현황 보기
					</h4>
				</a></li>
			</ul>			
			</ul>
		</div>

	</div><!-- /content -->
	
	
	<jsp:include page="/m/web/common/footer.jsp" >
		<jsp:param value="fixed" name="data_position" />
	</jsp:include><!-- /footerx	 -->

	
</div><!-- /page -->
</body>
</html>
