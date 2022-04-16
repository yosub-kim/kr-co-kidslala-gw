<%@ include file="/m/web/common/includeAuth.jsp"%>
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
<div data-role="page" id="customerListPage">
	<input type="hidden" id="customerListPg" value="0"/>
	<div data-role="header" data-theme="a">
		<h1 onclick="goHOME();">KMAC 인트라넷</h1>
		<a href="" data-icon="arrow-l" data-iconpos="notext" data-rel="back" data-direction="reverse">back</a>
		<!-- <a href="/m/web/customer/customerWrite.jsp" data-icon="plus" rel="external">등록</a> -->
	</div><!-- /header -->

	<div data-role="content">
		<div class="ui-grid-solo" style="text-align: center; ">
			<ul data-role="listview" id="customerListView">
				<li data-role="list-divider">고객정보</li>
			</ul>
		</div>
		<div class="ui-grid-solo" style="text-align: center; padding-top: 20px;">
			<a href="#" 
				data-role="button" data-icon="plus" data-theme="e" data-corners="false" id="customerListMore">
				<span id="customerListPageInfo"></span>
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
						<select name="customerinfo_type" id="customerinfo_type">
							<option value="">정보유형</option>
							<option value="CTL">상담/프로모션</option>
							<option value="CTM">잠재니즈</option>
							<option value="CTN">표출니즈</option>
							<option value="CTA">입찰정보</option>
							<option value="CTC">제안/PT</option>
							<option value="CTE">수주/수주실패</option>
							<option value="CTZ">기타</option>
						</select>
						<select name="function_type" id="function_type">
							<option value="">[::전체::]</option>
							<option value="9200">가치혁신 부문</option>
							<option value="9201">기업가치진단본부</option>
							<option value="9202">사업가치진단본부</option>
							<option value="9203">가치혁신컨설팅본부</option>
							<option value="9204">리서치혁신본부</option>
							<option value="9205">고객가치컨설팅센터</option>
							<option value="9210">공공혁신 부문</option>
							<option value="9211">공공컨설팅1본부</option>
							<option value="9212">공공컨설팅2본부</option>
							<option value="9213">공공컨설팅3본부</option>
							<option value="9214">공공컨설팅4본부</option>
							<option value="9215">공공컨설팅5본부</option>
							<option value="9216">에너지/환경본부</option>
							<option value="9217">공공조사1본부</option>							
							<option value="9218">공공조사2본부</option>
							<option value="9271">리서치센터</option>
							<option value="9230">스마트혁신 부문</option>
							<option value="9231">스마트/PI본부</option>
							<option value="9232">정부정책사업본부</option>
							<option value="9233">데이터사업센터</option>
							<option value="9240">인재개발 부문</option>
							<option value="9241">스마트교육본부</option>
							<option value="9242">기업교육센터</option>
							<option value="9243">공공교육센터</option>
							<option value="9244">대학사업센터</option>
							<option value="9281">미디어센터</option>
							<option value="9251">전략사업센터</option>
						</select>
						<input type="search" name="searchKey" id="searchKey" value="" placeholder="제목 검색" style="width: 100%;" />
					</td> 
					<td><a href="javascript:getCustomerListPageSearch();" data-role="button" data-theme="b" data-corners="false" id="boardListMore"  data-icon="search" >검색</a></td>
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
