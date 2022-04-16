<%@ taglib prefix="c" 		uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="fmt"		uri="http://java.sun.com/jstl/fmt" %>
<%
	String sdateYear= (String)request.getAttribute("sdateYear");
	String sdateMonth = (String)request.getAttribute("sdateMonth");	
	String sdateDay = (String)request.getAttribute("sdateDay");
	String sdateTime = (String)request.getAttribute("sdateTime");	
	String edateYear= (String)request.getAttribute("edateYear");
	String edateMonth= (String)request.getAttribute("edateMonth");
	String edateDay= (String)request.getAttribute("edateDay");
	String edateTime= (String)request.getAttribute("edateTime");
%>
<!DOCTYPE html>
<html lang="en">
<head>
	<%@ include file="/m/web/common/includeMeta.jsp"%>
	<%@ include file="/m/web/common/includeCss.jsp"%>
	<%@ include file="/m/web/common/includeJavaScript.jsp"%> 
</head>
<body> 
<div data-role="page" id="reservationViewPage">
	<form id="reservationViewForm">
		<input type="hidden" name="no" value='<c:out value="${reservation.no }"/>'>
		<input type="hidden" name="resvGubun" id="resvGubun" value='<c:out value="${resvGubun }"/>'>
		<input type="hidden" name="isRegister" id="isRegister" value='<c:out value="${isRegister }"/>'>
		<div data-role="header" data-theme="a"> 
			<h1 onclick="goHOME();">KMAC 인트라넷</h1>
			<a href="javascript:history.back();" data-icon="arrow-l" data-iconpos="notext" data-direction="reverse">back</a>
		<c:if test="${isRegister=='Y'}">
			<a href="javascript:removeReservation()" data-icon="check" >삭제</a>
		</c:if>
		</div><!-- /header -->
	
		<div data-role="content">
			<div class="ui-grid-solo" style="text-align: center; ">
				<ul data-role="listview" id="equipmentInsertSelectorView">
					<li data-role="list-divider">예약 시작일자</li>
					<li style="height: 40px; padding: 0px; margin-bottom: 0px;">
						<table style="width: 100%;">
							<tr>
								<td>
									<%=sdateYear %>년 <%=sdateMonth%>월 <%=sdateDay%>일 <%=sdateTime%>시 
								</td>
							</tr>
						</table>
					</li>
					<li data-role="list-divider">예약 종료일자</li>
					<li style="height: 40px; padding: 0px; margin-bottom: 10px;">
						<table style="width: 100%;">
							<tr>
								<td>
									<%=edateYear %>년 <%=edateMonth%>월 <%=edateDay%>일 <%=edateTime%>시 
								</td>
							</tr>
						</table>
					</li>
				</ul>
			</div>
			<div class="ui-grid-solo" style="text-align: center; ">
				<ul data-role="listview" id="equipmentInsertView" style="margin-bottom: 10px;">
					<li data-role="list-divider">상세 정보</li>
				</ul>
				<input type="text" name="usePer" id="usePer" value="<c:out value="${reservation.usePer}"/>" data-mini="true" placeholder="신청인"/>
				<input type="text" name="useTeam" id="useTeam" value="<c:out value="${reservation.useTeam}"/>" data-mini="true" placeholder="사용부서"/>
				<input type="text" name="usePurpose" id="usePurpose" value="<c:out value="${reservation.usePurpose}"/>" data-mini="true" placeholder="용도"/>
				<input type="text" name="useLoc" id="useLoc" value="<c:out value="${reservation.useLoc}"/>" data-mini="true" placeholder="사용 장소"/>
			</div> 
		</div><!-- /content -->
		
		
		<jsp:include page="/m/web/common/footer.jsp" >
			<jsp:param value="data_position" name=""/>
		</jsp:include><!-- /footerx	 -->
	</form>
</div><!-- /page -->
</body>
</html>
