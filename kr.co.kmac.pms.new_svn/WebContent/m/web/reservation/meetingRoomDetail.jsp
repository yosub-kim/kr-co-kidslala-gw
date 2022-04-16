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
	
	<div data-role="header" data-theme="a"> 
		<h1 onclick="goHOME();">KMAC 인트라넷</h1>
		<a href="/m/index.jsp" data-icon="arrow-l" data-iconpos="notext" data-direction="reverse">back</a>
	</div><!-- /header -->

	<div data-role="content">
		<div class="ui-grid-solo" style="text-align: center; margin-bottom: 15px;">
			<ul data-role="listview" id="preportListView">
				<li data-role="list-divider">2013년 10월 12일 [ 빔1(소) ]</li>
			</ul>
		</div> 
		<div class="ui-grid-solo" style="text-align: center;" data-role="fieldcontain">
		    <fieldset data-role="controlgroup">
				<input type="checkbox" name="checkbox-01" id="checkbox-01" class="custom" /><label for="checkbox-01" style="color: blue;">[예약 가능] 07	~ 08 시</label>   
				<input type="checkbox" name="checkbox-02" id="checkbox-02" class="custom" /><label for="checkbox-02">[예약 가능] 08	~ 09 시</label>   
				<input type="checkbox" name="checkbox-03" id="checkbox-03" class="custom" /><label for="checkbox-03">[예약 가능] 09	~ 10 시</label>  
				<input type="checkbox" name="checkbox-04" id="checkbox-04" class="custom" /><label for="checkbox-04" style="color: red;">[예약 불가] 10	~ 11 시</label>  
				<input type="checkbox" name="checkbox-05" id="checkbox-05" class="custom" /><label for="checkbox-05">[예약 불가] 11	~ 12 시</label>  
				<input type="checkbox" name="checkbox-06" id="checkbox-06" class="custom" /><label for="checkbox-06">[예약 불가] 12	~ 13 시</label>  
				<input type="checkbox" name="checkbox-07" id="checkbox-07" class="custom" /><label for="checkbox-07">[예약 불가] 13	~ 14 시</label>  
				<input type="checkbox" name="checkbox-08" id="checkbox-08" class="custom" /><label for="checkbox-08">[예약 불가] 14	~ 15 시</label>  
				<input type="checkbox" name="checkbox-09" id="checkbox-09" class="custom" /><label for="checkbox-09">[예약 가능] 15	~ 16 시</label>  
				<input type="checkbox" name="checkbox-10" id="checkbox-10" class="custom" /><label for="checkbox-10">[예약 가능] 16	~ 17 시</label>  
				<input type="checkbox" name="checkbox-11" id="checkbox-11" class="custom" /><label for="checkbox-11">[예약 가능] 17	~ 18 시</label>  
				<input type="checkbox" name="checkbox-12" id="checkbox-12" class="custom" /><label for="checkbox-12">[예약 가능] 18	~ 19 시</label>  
				<input type="checkbox" name="checkbox-13" id="checkbox-13" class="custom" /><label for="checkbox-13">[예약 가능] 19	~ 20 시</label>  
				<input type="checkbox" name="checkbox-14" id="checkbox-14" class="custom" /><label for="checkbox-14">[예약 가능] 20	~ 21 시</label>  
				<input type="checkbox" name="checkbox-15" id="checkbox-15" class="custom" /><label for="checkbox-15">[예약 가능] 21	~ 22 시</label>  
				<input type="checkbox" name="checkbox-16" id="checkbox-16" class="custom" /><label for="checkbox-16">[예약 가능] 22	~ 23 시</label>  
				<input type="checkbox" name="checkbox-17" id="checkbox-17" class="custom" /><label for="checkbox-17">[예약 가능] 23	~ 24 시</label>  
		    </fieldset>
		</div> 
	</div><!-- /content -->
	
	
	<jsp:include page="/m/web/common/footer.jsp" >
		<jsp:param value="data_position" name=""/>
	</jsp:include><!-- /footerx	 -->
	
	
</div><!-- /page -->
</body>
</html>
