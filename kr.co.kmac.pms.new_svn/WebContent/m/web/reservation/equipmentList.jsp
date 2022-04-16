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
<div data-role="page" id="equipmentListPage">
	<input type="hidden" id="reservationGubun" value="E">
	<div data-role="header" data-theme="a"> 
		<h1 onclick="goHOME();">KMAC 인트라넷</h1>
		<a href="/m/index.jsp" data-icon="arrow-l" data-iconpos="notext" data-direction="reverse">back</a>
	</div><!-- /header -->

	<div data-role="content">
		<div class="ui-grid-solo" style="text-align: center; ">
			<ul data-role="listview" id="equipmentListSelectorView">
				<li data-role="list-divider">예약일자 선택</li>
				<li style="height: 60px; padding: 0px; margin-bottom: 10px;">
					<table style="width: 100%;">
						<tr>
							<td>
								<select name="selYear" id="selYear">
									<option value='<%= (Integer.parseInt(year))-1 %>' ><%= (Integer.parseInt(year))-1 %></option>
									<option value='<%= (Integer.parseInt(year)) %>' selected><%= (Integer.parseInt(year)) %></option>
									<option value='<%= (Integer.parseInt(year))+1 %>' ><%= (Integer.parseInt(year))+1 %></option>
								</select> 
							</td> 
							<td>
								<select name="selMonth" id="selMonth">
									<option value='01' <%=month.equals("01") ? "selected" : ""%>>1월</option>
									<option value='02' <%=month.equals("02") ? "selected" : ""%>>2월</option>
									<option value='03' <%=month.equals("03") ? "selected" : ""%>>3월</option>
									<option value='04' <%=month.equals("04") ? "selected" : ""%>>4월</option>
									<option value='05' <%=month.equals("05") ? "selected" : ""%>>5월</option>
									<option value='06' <%=month.equals("06") ? "selected" : ""%>>6월</option>
									<option value='07' <%=month.equals("07") ? "selected" : ""%>>7월</option>
									<option value='08' <%=month.equals("08") ? "selected" : ""%>>8월</option>
									<option value='09' <%=month.equals("09") ? "selected" : ""%>>9월</option>
									<option value='10' <%=month.equals("10") ? "selected" : ""%>>10월</option>
									<option value='11' <%=month.equals("11") ? "selected" : ""%>>11월</option>
									<option value='12' <%=month.equals("12") ? "selected" : ""%>>12월</option>
								</select>					
							</td>
							<td>
								<select name="selDay" id="selDay">
									<option value='01' <%=day.equals("01") ? "selected" : ""%>>1일</option>   
									<option value='02' <%=day.equals("02") ? "selected" : ""%>>2일</option>   
									<option value='03' <%=day.equals("03") ? "selected" : ""%>>3일</option>   
									<option value='04' <%=day.equals("04") ? "selected" : ""%>>4일</option>   
									<option value='05' <%=day.equals("05") ? "selected" : ""%>>5일</option>   
									<option value='06' <%=day.equals("06") ? "selected" : ""%>>6일</option>   
									<option value='07' <%=day.equals("07") ? "selected" : ""%>>7일</option>   
									<option value='08' <%=day.equals("08") ? "selected" : ""%>>8일</option>   
									<option value='09' <%=day.equals("09") ? "selected" : ""%>>9일</option>   
									<option value='10' <%=day.equals("10") ? "selected" : ""%>>10일</option>   
									<option value='11' <%=day.equals("11") ? "selected" : ""%>>11일</option>   
									<option value='12' <%=day.equals("12") ? "selected" : ""%>>12일</option>   
									<option value='13' <%=day.equals("13") ? "selected" : ""%>>13일</option>   
									<option value='14' <%=day.equals("14") ? "selected" : ""%>>14일</option>   
									<option value='15' <%=day.equals("15") ? "selected" : ""%>>15일</option>   
									<option value='16' <%=day.equals("16") ? "selected" : ""%>>16일</option>   
									<option value='17' <%=day.equals("17") ? "selected" : ""%>>17일</option>   
									<option value='18' <%=day.equals("18") ? "selected" : ""%>>18일</option>   
									<option value='19' <%=day.equals("19") ? "selected" : ""%>>19일</option>   
									<option value='20' <%=day.equals("20") ? "selected" : ""%>>20일</option>   
									<option value='21' <%=day.equals("21") ? "selected" : ""%>>21일</option>   
									<option value='22' <%=day.equals("22") ? "selected" : ""%>>22일</option>   
									<option value='23' <%=day.equals("23") ? "selected" : ""%>>23일</option>   
									<option value='24' <%=day.equals("24") ? "selected" : ""%>>24일</option>   
									<option value='25' <%=day.equals("25") ? "selected" : ""%>>25일</option>   
									<option value='26' <%=day.equals("26") ? "selected" : ""%>>26일</option>   
									<option value='27' <%=day.equals("27") ? "selected" : ""%>>27일</option>   
									<option value='28' <%=day.equals("28") ? "selected" : ""%>>28일</option>   
									<option value='29' <%=day.equals("29") ? "selected" : ""%>>29일</option>   
									<option value='30' <%=day.equals("30") ? "selected" : ""%>>30일</option>   
									<option value='31' <%=day.equals("31") ? "selected" : ""%>>31일</option>
								</select>					
							</td>
						</tr>
					</table>
				</li>
			</ul>
		</div>
		<div class="ui-grid-solo" style="text-align: center; ">
			<ul data-role="listview" id="equipmentListView">
				<li data-role="list-divider">장비 선택</li>
			</ul>
		</div> 
	</div><!-- /content -->
	
	
	<jsp:include page="/m/web/common/footer.jsp" >
		<jsp:param value="data_position" name=""/>
	</jsp:include><!-- /footerx	 -->
	
	
</div><!-- /page -->
</body>
</html>
