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
<div data-role="page">
	<form id="bizSchoolViewForm" action="">
		<input type="hidden" name="fId" value="<c:out value="${bizSchoolReservation.fId }"/>">
		<div data-role="header" data-theme="a"> 
			<h1 onclick="goHOME();">KMAC 인트라넷</h1>
			<a href="" data-icon="arrow-l" data-iconpos="notext" data-rel="back" data-direction="reverse">back</a>
		</div><!-- /header -->
	
		<div data-role="content">
			<div class="ui-grid-solo" style="text-align: center; margin-bottom: 30px;">
				<ul data-role="listview" id="expertpoolListView">
					<li data-role="list-divider">강의실 예약정보</li>
				</ul>
			</div>			
			<table style="width: 100%; font-size: 12px; border-collapse: collapse; ">
				<colgroup> 
					<col width="30%"> 
					<col width="70%">
				</colgroup>
				<tr>
					<th style="padding: 5px; background-color: #d5d5d5; border-width: 1px; border-color: #bbbbbb; border-style: solid;">연수실</th>
					<td style="padding: 5px; border-width: 1px; border-color: #bbbbbb; border-style: solid;"><c:out value="${bizSchoolReservation.fUsername}"/> </td>
				</tr>		
				<tr>
					<th style="padding: 5px; background-color: #d5d5d5; border-width: 1px; border-color: #bbbbbb; border-style: solid;">일자</th>
					<td style="padding: 5px; border-width: 1px; border-color: #bbbbbb; border-style: solid;"><c:out value="${bizSchoolReservation.fYyyy}"/>-<c:out value="${bizSchoolReservation.fMm}"/>-<c:out value="${bizSchoolReservation.fDd}"/> (<c:out value="${bizSchoolReservation.fHh}"/>:<c:out value="${bizSchoolReservation.fSs}"/> ~ <c:out value="${bizSchoolReservation.fEhh}"/>:<c:out value="${bizSchoolReservation.fEss}"/>)</td>
				</tr>		
				<tr>
					<th style="padding: 5px; background-color: #d5d5d5; border-width: 1px; border-color: #bbbbbb; border-style: solid;">주/야간</th>
					<td style="padding: 5px; border-width: 1px; border-color: #bbbbbb; border-style: solid;"><c:choose><c:when test="${'1' eq bizSchoolReservation.fJuya }">주간</c:when><c:otherwise>야간</c:otherwise></c:choose></td>
				</tr>		
				<tr>
					<th style="padding: 5px; background-color: #d5d5d5; border-width: 1px; border-color: #bbbbbb; border-style: solid;">업무 유형</th>
					<td style="padding: 5px; border-width: 1px; border-color: #bbbbbb; border-style: solid;"><c:out value="${bizSchoolReservation.fJobtypeDesc}"/></td>
				</tr>
			</table>
			<p></p>
			<table style="width: 100%; font-size: 12px; border-collapse: collapse; ">
				<colgroup> 
					<col width="30%"> 
					<col width="70%">
				</colgroup>		
				<tr>
					<th style="padding: 5px; background-color: #d5d5d5; border-width: 1px; border-color: #bbbbbb; border-style: solid;">과정명</th>
					<td style="padding: 5px; border-width: 1px; border-color: #bbbbbb; border-style: solid;"><c:out value="${bizSchoolReservation.fJobContent}"/></td>
				</tr>		
				<tr>
					<th style="padding: 5px; background-color: #d5d5d5; border-width: 1px; border-color: #bbbbbb; border-style: solid;">노동부환급과정</th>
					<td style="padding: 5px; border-width: 1px; border-color: #bbbbbb; border-style: solid;"><c:choose><c:when test="${'유' eq bizSchoolReservation.fJido }">예</c:when><c:otherwise>아니오</c:otherwise></c:choose></td>
				</tr>		
				<tr>
					<th style="padding: 5px; background-color: #d5d5d5; border-width: 1px; border-color: #bbbbbb; border-style: solid;">관련회사</th>
					<td style="padding: 5px; border-width: 1px; border-color: #bbbbbb; border-style: solid;"><c:out value="${bizSchoolReservation.company}"/> <c:out value="${bizSchoolReservation.dept}"/></td>
				</tr>		
				<tr>
					<th style="padding: 5px; background-color: #d5d5d5; border-width: 1px; border-color: #bbbbbb; border-style: solid;">담당자</th>
					<td style="padding: 5px; border-width: 1px; border-color: #bbbbbb; border-style: solid;"><c:out value="${bizSchoolReservation.fCustomer}"/></td>
				</tr>		
				<tr>
					<th style="padding: 5px; background-color: #d5d5d5; border-width: 1px; border-color: #bbbbbb; border-style: solid;">담당자 연락처</th>
					<td style="padding: 5px; border-width: 1px; border-color: #bbbbbb; border-style: solid;"><c:out value="${bizSchoolReservation.fCustomerTel}"/></td>
				</tr>		
				<tr>
					<th style="padding: 5px; background-color: #d5d5d5; border-width: 1px; border-color: #bbbbbb; border-style: solid;">교육인원</th>
					<td style="padding: 5px; border-width: 1px; border-color: #bbbbbb; border-style: solid;"><c:out value="${bizSchoolReservation.fPlace}"/></td>
				</tr>		
				<tr>
					<th style="padding: 5px; background-color: #d5d5d5; border-width: 1px; border-color: #bbbbbb; border-style: solid;">강사1</th>
					<td style="padding: 5px; border-width: 1px; border-color: #bbbbbb; border-style: solid;">
						[<c:choose><c:when test="${'1' eq bizSchoolReservation.instructor1Diff }">상임</c:when><c:otherwise>엑스퍼트</c:otherwise></c:choose>]<c:out value="${bizSchoolReservation.instructor1}"/> 
						<c:out value="${bizSchoolReservation.startTime1_1}"/>:<c:out value="${bizSchoolReservation.startTime1_2}"/> ~ <c:out value="${bizSchoolReservation.endTime1_1}"/>:<c:out value="${bizSchoolReservation.endTime1_2}"/>
					</td>
				</tr>		
				<tr>
					<th style="padding: 5px; background-color: #d5d5d5; border-width: 1px; border-color: #bbbbbb; border-style: solid;">강사2</th>
					<td style="padding: 5px; border-width: 1px; border-color: #bbbbbb; border-style: solid;">
						[<c:choose><c:when test="${'1' eq bizSchoolReservation.instructor2Diff }">상임</c:when><c:otherwise>엑스퍼트</c:otherwise></c:choose>]<c:out value="${bizSchoolReservation.instructor2}"/> 
						<c:out value="${bizSchoolReservation.startTime2_1}"/>:<c:out value="${bizSchoolReservation.startTime2_2}"/> ~ <c:out value="${bizSchoolReservation.endTime2_1}"/>:<c:out value="${bizSchoolReservation.endTime2_2}"/>
					</td>
				</tr>		
				<tr>
					<th style="padding: 5px; background-color: #d5d5d5; border-width: 1px; border-color: #bbbbbb; border-style: solid;">강사3</th>
					<td style="padding: 5px; border-width: 1px; border-color: #bbbbbb; border-style: solid;">
						[<c:choose><c:when test="${'1' eq bizSchoolReservation.instructor3Diff }">상임</c:when><c:otherwise>엑스퍼트</c:otherwise></c:choose>]<c:out value="${bizSchoolReservation.instructor3}"/> 
						<c:out value="${bizSchoolReservation.startTime3_1}"/>:<c:out value="${bizSchoolReservation.startTime3_2}"/> ~ <c:out value="${bizSchoolReservation.endTime3_1}"/>:<c:out value="${bizSchoolReservation.endTime3_2}"/>
					</td>
				</tr>		
				<tr>
					<th style="padding: 5px; background-color: #d5d5d5; border-width: 1px; border-color: #bbbbbb; border-style: solid;">책상배치</th>
					<td style="padding: 5px; border-width: 1px; border-color: #bbbbbb; border-style: solid;"><c:out value="${bizSchoolReservation.deskArrange}"/></td>
				</tr>		
				<tr>
					<th style="padding: 5px; background-color: #d5d5d5; border-width: 1px; border-color: #bbbbbb; border-style: solid;">교육준비물</th>
					<td style="padding: 5px; border-width: 1px; border-color: #bbbbbb; border-style: solid;"><c:out value="${bizSchoolReservation.courseReady}"/></td>
				</tr>		
				<tr>
					<th style="padding: 5px; background-color: #d5d5d5; border-width: 1px; border-color: #bbbbbb; border-style: solid;">기타요구사항</th>
					<td style="padding: 5px; border-width: 1px; border-color: #bbbbbb; border-style: solid;"><c:out value="${bizSchoolReservation.fBigo}"/></td>
				</tr>		
			</table>			
			<div class="ui-grid-b">
				<div class="ui-block-a"><a href="javascript:modifyBizSchoolDetail('<c:out value="${bizSchoolReservation.fId }"/>')" data-role="button" data-theme="e" data-corners="false" >수정</a></div>
				<c:if test="${userId == 'kmchoo78' or userId == 'yhyim'}">
				<div class="ui-block-b"><a href="javascript:confirmBizSchoolDetail('<c:out value="${bizSchoolReservation.fId }"/>')" data-role="button" data-theme="e" data-corners="false" >확정</a></div>
				</c:if>
				<div class="ui-block-c"><a href="javascript:cancleBizSchoolDetail('<c:out value="${bizSchoolReservation.fId }"/>')" data-role="button" data-theme="e" data-corners="false" >취소</a></div>
			</div>		
		</div><!-- /content -->
	</form>
	<jsp:include page="/m/web/common/footer.jsp" >
		<jsp:param value="" name="data_position" />
	</jsp:include><!-- /footerx	 -->
	
	
</div><!-- /page -->
</body>
</html>
