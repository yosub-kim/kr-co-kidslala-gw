<%@page import="kr.co.kmac.pms.common.util.StringUtil"%>
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
	<form id="parkingViewForm" action="">
		<input type="hidden" name="idx" value="<c:out value="${parkingReservation.idx }"/>">
		<input type="hidden" name="pdate" id="pdate" value="<c:out value="${parkingReservation.pdate }"/>">
		<div data-role="header" data-theme="a"> 
			<h1 onclick="goHOME();">KMAC 인트라넷</h1>
			<a href="javascript:history.back();" data-icon="arrow-l" data-iconpos="notext" data-rel="back" data-direction="reverse">back</a>
		</div><!-- /header -->
	
		<div data-role="content">
			<div class="ui-grid-solo" style="text-align: center; margin-bottom: 30px;">
				<ul data-role="listview" id="expertpoolListView">
					<li data-role="list-divider">주차 예약정보</li>
				</ul>
			</div>			
			<table style="width: 100%; font-size: 12px; border-collapse: collapse; ">
				<colgroup> 
					<col width="30%"> 
					<col width="70%">
				</colgroup>
				<tr>
					<th style="padding: 5px; background-color: #d5d5d5; border-width: 1px; border-color: #bbbbbb; border-style: solid;">일자</th>
					<td style="padding: 5px; border-width: 1px; border-color: #bbbbbb; border-style: solid;"><c:out value="${parkingReservation.pdateStr}"/> </td>
				</tr>		
				<tr>
					<th style="padding: 5px; background-color: #d5d5d5; border-width: 1px; border-color: #bbbbbb; border-style: solid;">주/야간</th>
					<td style="padding: 5px; border-width: 1px; border-color: #bbbbbb; border-style: solid;"><c:choose><c:when test="${'am' eq parkingReservation.rtime}">주간</c:when><c:otherwise>야간</c:otherwise></c:choose></td>
				</tr>		
				<tr>
					<th style="padding: 5px; background-color: #d5d5d5; border-width: 1px; border-color: #bbbbbb; border-style: solid;">소속</th>
					<td style="padding: 5px; border-width: 1px; border-color: #bbbbbb; border-style: solid;"><c:out value="${parkingReservation.company}"/>, <c:out value="${parkingReservation.deptName}"/></td>
				</tr>		
				<tr>
					<th style="padding: 5px; background-color: #d5d5d5; border-width: 1px; border-color: #bbbbbb; border-style: solid;">성명</th>
					<td style="padding: 5px; border-width: 1px; border-color: #bbbbbb; border-style: solid;"><c:out value="${parkingReservation.pname}"/></td>
				</tr>		
				<tr>
					<th style="padding: 5px; background-color: #d5d5d5; border-width: 1px; border-color: #bbbbbb; border-style: solid;">연락처</th>
					<td style="padding: 5px; border-width: 1px; border-color: #bbbbbb; border-style: solid;"><c:out value="${parkingReservation.ptel}"/></td>
				</tr>		
				<tr>
					<th style="padding: 5px; background-color: #d5d5d5; border-width: 1px; border-color: #bbbbbb; border-style: solid;">차량번호</th>
					<td style="padding: 5px; border-width: 1px; border-color: #bbbbbb; border-style: solid;"><c:out value="${parkingReservation.pcarNum}"/></td>
				</tr>		
				<tr>
					<th style="padding: 5px; background-color: #d5d5d5; border-width: 1px; border-color: #bbbbbb; border-style: solid;">방문목적</th>
					<td style="padding: 5px; border-width: 1px; border-color: #bbbbbb; border-style: solid;"><c:out value="${parkingReservation.pmemo}"/></td>
				</tr>		
				<tr>
					<th style="padding: 5px; background-color: #d5d5d5; border-width: 1px; border-color: #bbbbbb; border-style: solid;">등록일</th>
					<td style="padding: 5px; border-width: 1px; border-color: #bbbbbb; border-style: solid;"><c:out value="${parkingReservation.regdate}"/></td>
				</tr>		
				<tr>
					<th style="padding: 5px; background-color: #d5d5d5; border-width: 1px; border-color: #bbbbbb; border-style: solid;">등록자</th>
					<td style="padding: 5px; border-width: 1px; border-color: #bbbbbb; border-style: solid;"><c:out value="${parkingReservation.writerName}"/></td>
				</tr>		
			</table>
		<c:if test="${userId eq 'kmchoo78' or userId eq 'hindongs' or userId eq 'yhyim'}">
			<div class="ui-grid-b">
				<div class="ui-block-a"><a href="javascript:modifyParkingDetail('<c:out value="${parkingReservation.idx}"/>')" data-role="button" data-theme="e" data-corners="false" >수정</a></div>
				<div class="ui-block-b"><a href="javascript:confirmParkingDetail('<c:out value="${parkingReservation.idx}"/>')" data-role="button" data-theme="e" data-corners="false" >확정</a></div>
				<div class="ui-block-c"><a href="javascript:cancleParkingDetail('<c:out value="${parkingReservation.idx}"/>')" data-role="button" data-theme="e" data-corners="false" >취소</a></div>
			</div>		
		</c:if>
		<c:if test="${userId != 'kmchoo78' and userId != 'hindongs' and userId != 'yhyim'}">
			<div class="ui-grid-c" style="font-size: 10pt"><br>주차 취소를 희망하는 경우 <a href="tel:02-3786-0622">02-3786-0622</a> 로 연락바랍니다.</div>
		</c:if>			
		</div><!-- /content -->
	</form>
	<jsp:include page="/m/web/common/footer.jsp" >
		<jsp:param value="" name="data_position" />
	</jsp:include><!-- /footerx	 -->
	
	
</div><!-- /page -->
</body>
</html>
