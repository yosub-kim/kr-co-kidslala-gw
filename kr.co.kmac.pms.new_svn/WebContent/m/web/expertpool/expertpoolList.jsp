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
<div data-role="page" id="expertpoolListPage">
	<input type="hidden" id="expertpoolListPg" value="0"/>
	<div data-role="header" data-theme="a">
		<h1 onclick="goHOME();">KMAC 인트라넷</h1>
		<a href="" data-icon="arrow-l" data-iconpos="notext" data-rel="back" data-direction="reverse">back</a>
	</div><!-- /header -->
 
	<div data-role="content">
		<div class="ui-grid-solo" style="text-align: center; ">
			<ul data-role="listview" id="expertpoolListView">
				<li data-role="list-divider">전문가 정보</li>
			</ul>
		</div>
		<div class="ui-grid-solo" style="text-align: center; padding-top: 20px;">
			<a href="#" 
				data-role="button" data-icon="plus" data-theme="e" data-corners="false" id="expertpoolListMore">
				<span id="expertpoolListPageInfo"></span>
			</a>
		</div>	
		<div class="ui-grid-solo" style="text-align: center; ">
			<table style="width: 100%">
				<colgroup>
					<col width="80%"/>
					<col width="20%"/>
				</colgroup>
				<tr>
					<td>
						<select name="jobClass" id="expertpoolSearchJobClass">
							<option value='A' selected>상근(1)</option>
							<option value='B' >상근(2)</option>
							<option value='J' >상임</option>
							<!-- <option value='H' >AA</option> -->
							<option value='N' >RA</option>
							<!-- <option value='C' >엑스퍼트</option>
							<option value='D' >산업계강사</option>
							<option value='E' >대학교수</option>
							<option value='G' >기타</option> -->
						</select>
						<input type="search" name="keyword" id="expertpoolSearchSubject" value="" placeholder="이름 검색" style="width: 100%;" /> 
					</td> 
					<td><a href="javascript:getExpertpoolListPageSearch();" data-role="button" data-theme="b" data-corners="false" id="boardListMore"  data-icon="search" >검색</a></td>
				</tr>
			</table>
		</div>
	</div><!-- /content -->

	<jsp:include page="/m/web/common/footer.jsp" >
		<jsp:param value="data_position" name=""/>
	</jsp:include><!-- /footerx	 -->


</div><!-- /page -->
</body>
</html>
