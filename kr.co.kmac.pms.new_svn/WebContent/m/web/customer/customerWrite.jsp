<%@page import="kr.co.kmac.pms.common.util.StringUtil"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="fmt"		uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="authz"	uri="http://acegisecurity.org/authz" %>
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
<div data-role="page" id="customerWritePage">
	<form id="customerInfoForm" action="">
		<div data-role="header" data-theme="a">
			<h1 onclick="goHOME();">KMAC 인트라넷</h1>
			<a href="" data-icon="arrow-l" data-iconpos="notext" data-rel="back" data-direction="reverse">back</a>
			<a href="/m/index.jsp" data-icon="check" >등록</a>
		</div><!-- /header -->
	
		<div data-role="content">
			<div data-role="fieldcontain" class="ui-field-contain ui-body ui-br">
				<label for="subject" class="ui-input-text">고객정보유형:</label>	
				<input type="hidden" name="ip" value="<%=request.getRemoteAddr() %>"/>
				<select name="customerInfoType" id="customerWrite_customerInfoName">
					<option value="CTO">시장/고객동향</option>
					<option value="CTL">상담/프로모션</option>
					<option value="CTM">잠재니즈</option>
					<option value="CTN">표출니즈</option>
					<!-- <option value="CTA">입찰정보</option> --> 
					<option value="CTC">제안/PT</option>
					<option value="CTE">수주/수주실패</option>
					<option value="CTZ">기타</option>
				</select>
			</div>
			<div data-role="fieldcontain" class="ui-field-contain ui-body ui-br">
				<label for="subject" class="ui-input-text">제목:</label>	
				<input type="text" name="subject" id="customerWrite_subject">
			</div>
			<!-- 
			<div data-role="fieldcontain" class="ui-field-contain ui-body ui-br">
				<label for="subject" class="ui-input-text">입찰금액:</label>	
				<input type="text" name="embbs_company" size="15">
			</div>
			 -->
			<div data-role="fieldcontain" class="ui-field-contain ui-body ui-br">
				<label for="subject" class="ui-input-text">산업별구분:</label>
				<select name="industryTypeCode" id="customerWrite_industryTypeCode">
					<option value="ITA">제조업</option>
					<option value="ITB">서비스업</option>
					<option value="ITC">행정자치</option>
					<option value="ITD">사회공공</option>
					<option value="ITE">산업계전반</option>
				</select>
			</div>
			<div data-role="fieldcontain" class="ui-field-contain ui-body ui-br">
				<fieldset data-role="controlgroup" data-type="horizontal" ><!-- horizontal -->
					<legend>비즈니스타입:</legend>	
					<input type="checkbox" name="businessTypeCodes" value="BTA" id="industryTypeCode-1" data-mini="true"><label for="industryTypeCode-1">컨설팅</label>
					<input type="checkbox" name="businessTypeCodes" value="BTB" id="industryTypeCode-2" data-mini="true"><label for="industryTypeCode-2">진흥</label>
					<input type="checkbox" name="businessTypeCodes" value="BTC" id="industryTypeCode-3" data-mini="true"><label for="industryTypeCode-3">인증</label>
					<input type="checkbox" name="businessTypeCodes" value="BTJ" id="industryTypeCode-4" data-mini="true"><label for="industryTypeCode-4">진단</label>
					<input type="checkbox" name="businessTypeCodes" value="BTD" id="industryTypeCode-5" data-mini="true"><label for="industryTypeCode-5">리서치</label>
					<input type="checkbox" name="businessTypeCodes" value="BTE" id="industryTypeCode-6" data-mini="true"><label for="industryTypeCode-6">교육</label>
					<input type="checkbox" name="businessTypeCodes" value="BTO" id="industryTypeCode-7" data-mini="true"><label for="industryTypeCode-7">연수</label>
					<input type="checkbox" name="businessTypeCodes" value="BTG" id="industryTypeCode-8" data-mini="true"><label for="industryTypeCode-8">미디어</label>
					<input type="checkbox" name="businessTypeCodes" value="BTN" id="industryTypeCode-9" data-mini="true"><label for="industryTypeCode-9">기타</label>
					<!-- <input type="checkbox" name="businessTypeCodes" value="BTE1" id="industryTypeCode-6" data-mini="true"><label for="industryTypeCode-6">공개교육</label>
					<input type="checkbox" name="businessTypeCodes" value="BTE2" id="industryTypeCode-7" data-mini="true"><label for="industryTypeCode-7">사내교육</label> -->
					<!-- <input type="checkbox" name="businessTypeCodes" value="BTE3" id="industryTypeCode-8" data-mini="true"><label for="industryTypeCode-8">세미나/컨퍼런스</label> -->
					<!-- <input type="checkbox" name="businessTypeCodes" value="BTF1" id="industryTypeCode-9" data-mini="true"><label for="industryTypeCode-9">사내연수</label>
					<input type="checkbox" name="businessTypeCodes" value="BTF2" id="industryTypeCode-10" data-mini="true"><label for="industryTypeCode-10">공개연수</label> -->
					<!-- <input type="checkbox" name="businessTypeCodes" value="BTG" id="industryTypeCode-11" data-mini="true"><label for="industryTypeCode-11">미디어</label>
					<input type="checkbox" name="businessTypeCodes" value="BTH" id="industryTypeCode-12" data-mini="true"><label for="industryTypeCode-12">경영전반</label>
					<input type="checkbox" name="businessTypeCodes" value="BTI" id="industryTypeCode-13" data-mini="true"><label for="industryTypeCode-13">위원회</label>
					<input type="checkbox" name="businessTypeCodes" value="BTL" id="industryTypeCode-14" data-mini="true"><label for="industryTypeCode-14">국제사업</label>
					<input type="checkbox" name="businessTypeCodes" value="BTM" id="industryTypeCode-15" data-mini="true"><label for="industryTypeCode-15">PCO</label> -->
				</fieldset>
			</div>
			<div data-role="fieldcontain" class="ui-field-contain ui-body ui-br">
				<label for="writerSsn" class="ui-input-text">작성자:</label>	
				<input type="hidden" name="writerSsn" value="<%=session.getAttribute("ssn")%>">
				<input type="hidden" name="companyPosition" value="<%=session.getAttribute("companyPosition")%>"> 
				<input type="hidden" name="jobclass" value="<%=session.getAttribute("jobClass")%>">
				<input type="text"	 name="writer" value="<authz:authentication operation="name"/>" readonly>
				
			</div>
			<div data-role="fieldcontain" class="ui-field-contain ui-body ui-br">
				<fieldset data-role="controlgroup" data-type="" >
					<legend>관련부서:</legend>
					<input type="hidden" name="writerTeam" value="<%=session.getAttribute("dept")%>">
					<select name="writerDept" id="customerWrite_writerDept" >
						<option value="2040" <%=session.getAttribute("div").equals("2040")?"selected":""%>>CSO</option>
						<option value="9200" <%=session.getAttribute("div").equals("9200")?"selected":""%>>가치혁신부문</option>
						<option value="9201" <%=session.getAttribute("div").equals("9201")?"selected":""%>>기업가치진단본부</option>
						<option value="9202" <%=session.getAttribute("div").equals("9202")?"selected":""%>>사업가치진단본부</option>
						<option value="9203" <%=session.getAttribute("div").equals("9203")?"selected":""%>>ESG혁신본부</option>
						<option value="9204" <%=session.getAttribute("div").equals("9204")?"selected":""%>>리서치혁신본부</option>
						<option value="9205" <%=session.getAttribute("div").equals("9205")?"selected":""%>>고객가치컨설팅센터</option>
						<option value="9210" <%=session.getAttribute("div").equals("9210")?"selected":""%>>공공혁신부문</option>
						<option value="9211" <%=session.getAttribute("div").equals("9211")?"selected":""%>>공공컨설팅1본부</option>
						<option value="9212" <%=session.getAttribute("div").equals("9212")?"selected":""%>>공공컨설팅2본부</option>
						<option value="9213" <%=session.getAttribute("div").equals("9213")?"selected":""%>>공공컨설팅3본부</option>
						<option value="9214" <%=session.getAttribute("div").equals("9214")?"selected":""%>>공공컨설팅4본부</option>
						<option value="9215" <%=session.getAttribute("div").equals("9215")?"selected":""%>>공공컨설팅5본부</option>
						<option value="9216" <%=session.getAttribute("div").equals("9216")?"selected":""%>>에너지/환경본부</option>
						<option value="9217" <%=session.getAttribute("div").equals("9217")?"selected":""%>>공공조사1본부</option>
						<option value="9218" <%=session.getAttribute("div").equals("9218")?"selected":""%>>공공조사2본부</option>
						<option value="6201" <%=session.getAttribute("div").equals("6201")?"selected":""%>>리서치센터</option>
						<option value="9230" <%=session.getAttribute("div").equals("9230")?"selected":""%>>스마트혁신부문</option>
						<option value="9231" <%=session.getAttribute("div").equals("9231")?"selected":""%>>스마트/PI본부</option>
						<option value="9232" <%=session.getAttribute("div").equals("9232")?"selected":""%>>정부정책사업본부</option>
						<option value="9233" <%=session.getAttribute("div").equals("9233")?"selected":""%>>데이터사업센터</option>
						<option value="9240" <%=session.getAttribute("div").equals("9240")?"selected":""%>>인재개발부문</option>
						<option value="9241" <%=session.getAttribute("div").equals("9241")?"selected":""%>>스마트교육본부</option>
						<option value="9242" <%=session.getAttribute("div").equals("9242")?"selected":""%>>기업교육센터</option>
						<option value="9243" <%=session.getAttribute("div").equals("9243")?"selected":""%>>공공교육센터</option>
						<option value="9244" <%=session.getAttribute("div").equals("9244")?"selected":""%>>대학사업센터</option>
						<option value="9251" <%=session.getAttribute("div").equals("9251")?"selected":""%>>전략사업센터</option>
						<option value="9281" <%=session.getAttribute("div").equals("9281")?"selected":""%>>미디어센터</option>
		            </select>
				</fieldset>	
			</div>
			<div data-role="fieldcontain" class="ui-field-contain ui-body ui-br">
				<label for="info_date" class="ui-input-text">정보수집일:</label>	
				<table style="width: 100%">
					<tr>
						<td>
							<select name="info_dateYyyy" id="info_dateYyyy">
								<option value='<%= (Integer.parseInt(year)) %>' selected><%= (Integer.parseInt(year)) %></option>
								<option value='<%= (Integer.parseInt(year))+1 %>' ><%= (Integer.parseInt(year))+1 %></option>
							</select> 
						</td>
						<td>
							<select name="info_dateMm" id="info_dateMm">
								<option value='01' <%=month.equals("01")?"selected":""%> > 1월</option>
								<option value='02' <%=month.equals("02")?"selected":""%> > 2월</option>
								<option value='03' <%=month.equals("03")?"selected":""%> > 3월</option>
								<option value='04' <%=month.equals("04")?"selected":""%> > 4월</option>
								<option value='05' <%=month.equals("05")?"selected":""%> > 5월</option>
								<option value='06' <%=month.equals("06")?"selected":""%> > 6월</option>
								<option value='07' <%=month.equals("07")?"selected":""%> > 7월</option>
								<option value='08' <%=month.equals("08")?"selected":""%> > 8월</option>
								<option value='09' <%=month.equals("09")?"selected":""%> > 9월</option>
								<option value='10' <%=month.equals("10")?"selected":""%> >10월</option>
								<option value='11' <%=month.equals("11")?"selected":""%> >11월</option>
								<option value='12' <%=month.equals("12")?"selected":""%> >12월</option>
							</select>					
						</td>
						<td>
							<select name="info_dateDd" id="info_dateDd">
								<option value='01' <%=day.equals("01")?"selected":""%> > 1일</option>   
								<option value='02' <%=day.equals("02")?"selected":""%> > 2일</option>   
								<option value='03' <%=day.equals("03")?"selected":""%> > 3일</option>   
								<option value='04' <%=day.equals("04")?"selected":""%> > 4일</option>   
								<option value='05' <%=day.equals("05")?"selected":""%> > 5일</option>   
								<option value='06' <%=day.equals("06")?"selected":""%> > 6일</option>   
								<option value='07' <%=day.equals("07")?"selected":""%> > 7일</option>   
								<option value='08' <%=day.equals("08")?"selected":""%> > 8일</option>   
								<option value='09' <%=day.equals("09")?"selected":""%> > 9일</option>   
								<option value='10' <%=day.equals("10")?"selected":""%> >10일</option>   
								<option value='11' <%=day.equals("11")?"selected":""%> >11일</option>   
								<option value='12' <%=day.equals("12")?"selected":""%> >12일</option>   
								<option value='13' <%=day.equals("13")?"selected":""%> >13일</option>   
								<option value='14' <%=day.equals("14")?"selected":""%> >14일</option>   
								<option value='15' <%=day.equals("15")?"selected":""%> >15일</option>   
								<option value='16' <%=day.equals("16")?"selected":""%> >16일</option>   
								<option value='17' <%=day.equals("17")?"selected":""%> >17일</option>   
								<option value='18' <%=day.equals("18")?"selected":""%> >18일</option>   
								<option value='19' <%=day.equals("19")?"selected":""%> >19일</option>   
								<option value='20' <%=day.equals("20")?"selected":""%> >20일</option>   
								<option value='21' <%=day.equals("21")?"selected":""%> >21일</option>   
								<option value='22' <%=day.equals("22")?"selected":""%> >22일</option>   
								<option value='23' <%=day.equals("23")?"selected":""%> >23일</option>   
								<option value='24' <%=day.equals("24")?"selected":""%> >24일</option>   
								<option value='25' <%=day.equals("25")?"selected":""%> >25일</option>   
								<option value='26' <%=day.equals("26")?"selected":""%> >26일</option>   
								<option value='27' <%=day.equals("27")?"selected":""%> >27일</option>   
								<option value='28' <%=day.equals("28")?"selected":""%> >28일</option>   
								<option value='29' <%=day.equals("29")?"selected":""%> >29일</option>   
								<option value='30' <%=day.equals("30")?"selected":""%> >30일</option>   
								<option value='31' <%=day.equals("31")?"selected":""%> >31일</option>   
							</select>
						</td>
					</tr>
				</table>
			</div>
			<div data-role="fieldcontain" class="ui-field-contain ui-body ui-br">
				<label for="customerName" class="ui-input-text"><table><tr>
					<td>정보수집자:</td>
					<td><a href="#customerCollectorPopupPage" data-role="button" data-rel="dialog" data-icon="search" data-iconpos="notext" data-transition="pop">검색</a></td>
				</tr></table></label>
				<div id="customerWrite_embbsGather_dev" >
					<span data-role="button" data-icon="delete" style="width: 130px;" data-inline="true" onclick="$(this).remove()">
						<authz:authentication operation="name"/>
						<input type="hidden" name="picker_ssn" value="<%=session.getAttribute("ssn")%>">
					</span>
				
				</div>

				<input type="hidden" name="embbsGather" id="customerWrite_embbsGather" value="" readonly/>
				<input type="hidden" name="embbsGather" id="customerWrite_embbsGather" value="" readonly placeholder="입력시 검색 버튼을 누르세요"/>
			</div>
			<div data-role="fieldcontain" class="ui-field-contain ui-body ui-br">
				<label for="customerName" class="ui-input-text"><table><tr>
					<td>고객사:</td>
					<td><a href="#customerCompanyPopupPage" data-role="button" data-rel="dialog" data-icon="search" data-iconpos="notext" data-transition="pop">검색</a></td>
				</tr></table></label>
				<input type="hidden" name="customerCode" id="customerWrite_customerCode" value="" readonly/>
				<input type="text" name="customerName" id="customerWrite_customerName" value="" readonly placeholder="입력시 검색 버튼을 누르세요"/>
			</div>
			<div data-role="fieldcontain" class="ui-field-contain ui-body ui-br">
				<label for="customerName" class="ui-input-text"><table><tr>
					<td>정보제공자:</td>
					<td><a href="#customerPersonPopupPage" data-role="button" data-rel="dialog" data-icon="search" data-iconpos="notext" data-transition="pop">검색</a></td>
				</tr></table></label>
				<input type="hidden" name="embbs_idx" id="customerWrite_embbs_idx" readonly/>
				<input type="text" name="embbsName" id="customerWrite_embbsName" readonly placeholder="입력시 검색 버튼을 누르세요"/>
			</div>
			<div data-role="fieldcontain" class="ui-field-contain ui-body ui-br">
				<label for="subject" class="ui-input-text">정보제공부서:</label>	
				<input type="text" name="embbsDept" id="customerWrite_embbsDept" readonly/>
			</div>
			<div data-role="fieldcontain" class="ui-field-contain ui-body ui-br">
				<label for="subject" class="ui-input-text">제공자직위:</label>	
				<input type="text" name="embbsStatus" id="customerWrite_embbsStatus" readonly>
			</div>
			<div data-role="fieldcontain" class="ui-field-contain ui-body ui-br">
				<label for="subject" class="ui-input-text">제공자전화번호:</label>	
				<input type="text" name="embbsPhone" id="customerWrite_embbsPhone" readonly>
			</div>
			<div data-role="fieldcontain" class="ui-field-contain ui-body ui-br">
				<label for="subject" class="ui-input-text">제공자Email:</label>	
				<input type="text" name="embbsEmail" id="customerWrite_embbsEmail" readonly>
			</div>
			<div data-role="fieldcontain" class="ui-field-contain ui-body ui-br">
				<label for="subject" class="ui-input-text">정보수집방법:</label>	
				<select name="embbsMethod" id="customerWrite_embbsMethod">
					<option value=''>선택하세요</option>   
					<option value='1'>방문수집</option>   
					<option value='2'>유선수집</option>   
					<option value='3'>기타</option>   
				</select>							
			</div>
			<div data-role="fieldcontain" class="ui-field-contain ui-body ui-br">
				<label for="subject" class="ui-input-text">정보내용:</label>	
				<textarea name="content" style="min-height: 200px;" id="customerWrite_content" ></textarea>
			</div>
			<div data-role="fieldcontain" class="ui-field-contain ui-body ui-br">
				<label for="subject" class="ui-input-text">제출자의견:</label>	
				<textarea name="opinion" style="min-height: 150px;" ></textarea>
			</div>
			<a href="javascript:saveCustomerInfo()" data-role="button" data-corners="false" data-icon="star" data-theme="e">등록</a>
		</div><!-- /content -->
	</form>
		
	
	<jsp:include page="/m/web/common/footer.jsp" > 
		<jsp:param value="data_position" name=""/>
	</jsp:include><!-- /footerx	 -->
</div><!-- /page -->

<div data-role="page" id="customerCompanyPopupPage">
	<div data-role="header" data-theme="e">
		<h1>고객사 검색</h1>
	</div><!-- /header -->
	
	<div data-role="content" data-theme="d" style="padding-top: 0px;">	
		<div style="padding-bottom: 15px;">
			<table style="width: 100%;"><tr>
				<td><input type="text" name="search_cpmpanyName" id="search_cpmpanyName" placeholder="검색어를 입력하세요"/></td>
				<td><a href="javascript:getCustomerCompanySearcgList();" data-role="button" data-icon="search" data-iconpos="notext" ></a></td>
			</tr></table>
		</div>
		<ul data-role="listview" id="customerCompanyPopupListView">
			<!-- 
			<li><a href="#" data-transition="slide">고객사</a></li>
			-->
		</ul>
	</div><!-- /content -->
	
	<div data-role="footer">
		<h4 onclick="history.go(-1)">닫기</h4>
	</div><!-- /footer -->
</div><!-- /page popup -->

<div data-role="page" id="customerPersonPopupPage">
	<div data-role="header" data-theme="e">
		<h1>정보제공자 검색</h1>
	</div><!-- /header -->
	
	<div data-role="content" data-theme="d">	
		<div style="padding-bottom: 15px;">
			<table style="width: 100%;"><tr>
				<td><input type="text" name="search_customerName" id="search_customerName" placeholder="검색어를 입력하세요"/></td>
				<td><a href="javascript:getCustomerPersonSearcgList();" data-role="button" data-icon="search" data-iconpos="notext" ></a></td>
			</tr></table>
		</div>		
		<ul data-role="listview" id="customerPersonPopupListView">
			<!-- 
			<li style="padding: 0px;"><a href="#" data-transition="slide">
				<h4 class="ui-li-heading">한승화 [AAA컴퍼니]</h4>
				<p class="ui-li-desc" style="font-size: 11px;">	부서: rs.customerName | 등록자:"+rs.writer+" | 작성일:"+rs.regdates+"</p>
			</a></li>
			-->
 		</ul>
	</div><!-- /content -->
	
	<div data-role="footer">
		<h4 onclick="history.go(-1)">닫기</h4>
	</div><!-- /footer -->
</div><!-- /page popup -->

<div data-role="page" id="customerCollectorPopupPage">
	<div data-role="header" data-theme="e">
		<h1>정보수집자 검색</h1>
	</div><!-- /header -->
	
	<div data-role="content" data-theme="d">	
		<div style="padding-bottom: 15px;">
			<table style="width: 100%;"><tr>
				<td><input type="text" name="search_collectorName" id="search_collectorName" placeholder="검색어를 입력하세요"/></td>
				<td><a href="javascript:getCustomerCollectorSearchList();" data-role="button" data-icon="search" data-iconpos="notext" ></a></td>
			</tr></table>
		</div>		
		<ul data-role="listview" id="customerCollectorPopupListView">
			<!-- 
			<li style="padding: 0px;"><a href="#" data-transition="slide">
				<h4 class="ui-li-heading">한승화 [AAA컴퍼니]</h4>
				<p class="ui-li-desc" style="font-size: 11px;">	부서: rs.customerName | 등록자:"+rs.writer+" | 작성일:"+rs.regdates+"</p>
			</a></li>
			-->
 		</ul>
	</div><!-- /content -->
	
	<div data-role="footer">
		<h4 onclick="history.go(-1)">닫기</h4>
	</div><!-- /footer -->
</div><!-- /page popup -->

</body>
</html>
