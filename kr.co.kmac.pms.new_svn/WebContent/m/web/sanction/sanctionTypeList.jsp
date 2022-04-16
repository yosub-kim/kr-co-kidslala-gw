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

	<div data-role="header" data-theme="a">
		<h1 onclick="goHOME();">KMAC 인트라넷</h1>
		<a href="/m/web/sanction/sanctionIndex.jsp" data-icon="arrow-l" data-iconpos="notext" data-direction="reverse">back</a>
	</div><!-- /header -->

	<div data-role="content">
		<div class="ui-grid-solo" style="text-align: center; ">
			<ul data-role="listview">
				<li data-role="list-divider">결재유형 선택</li>
				<c:forEach var="sanctionTemplate" items="${sanctionTemplateList }">
				<li><a href="/action/GeneralSanctionAction.do?mode=loadFormGeneralSanctionForMobile&approvalType=<c:out value="${sanctionTemplate.approvalType }" />" data-transition="slide">
					<img src="/m/img/kmenu_b.png" alt="doc" class="ui-li-icon ui-li-thumb" style="padding-top: 5px;">
					<h4 class="ui-li-heading">
						<c:out value="${sanctionTemplate.approvalName }" />
					</h4>
				</a></li>
				</c:forEach>
			</ul>			
			</ul>
		</div>

	</div><!-- /content -->
	
	
	<jsp:include page="/m/web/common/footer.jsp" >
		<jsp:param value="nofixed" name="data_position" />
	</jsp:include><!-- /footerx	 -->

	
</div><!-- /page -->
</body>
</html>
