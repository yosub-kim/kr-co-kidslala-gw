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
		<a href="/m/index.jsp" data-icon="arrow-l" data-iconpos="notext" data-direction="reverse">back</a>
	</div><!-- /header -->

	<div data-role="content">
		<div class="ui-grid-solo" style="text-align: center; ">
			<ul data-role="listview">
				<li data-role="list-divider">게시판</li>
				<c:choose>
					<c:when test="${not empty result}">
						<c:forEach var="item" items="${result}">
							<c:choose><c:when test="${item.bbsName ne '뉴스 스크랩' and item.bbsName ne 'KMAC-League'}">
							<li>
								<a href="/m/web/board/boardList.jsp?bbsId=<c:out value="${item.bbsId}"/>" data-transition="slide"  rel="external">
								<img src="/m/img/board.png" alt="게시판" class="ui-li-icon" style="middle: .0em; padding-top: 5px;">
								<h4 class="ui-li-heading"><c:out value="${item.bbsName}"/></h4>
								<%-- <span class="ui-li-count ui-btn-up-c ui-btn-corner-all">최근: <span style="color: red;"><c:out value="${item.recentCnt}"/></span>&nbsp;/&nbsp;전체: <c:out value="${item.totalCnt}"/></span> --%>
								
								</a>
							</li>
							</c:when><c:otherwise></c:otherwise></c:choose>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<li><a href="#" data-transition="slide">
							<img src="/m/img/board.png" alt="게시판" class="ui-li-icon ui-li-thumb">등록된 게시판 없음 
							<span class="ui-li-count ui-btn-up-c ui-btn-corner-all">0</span>
							</a>
						</li>
					</c:otherwise>
				</c:choose>
			</ul>
		</div>		
		
	</div><!-- /content -->
	
	<jsp:include page="/m/web/common/footer.jsp" >
		<jsp:param value="fixed" name="data_position" />
	</jsp:include><!-- /footerx	 -->
	
	
</div><!-- /page -->
</body>
</html>
