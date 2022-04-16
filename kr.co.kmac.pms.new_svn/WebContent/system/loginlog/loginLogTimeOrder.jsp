<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%-- 공통js,css include --%>
<%@ include file="/common/include/includeCss.jsp"%>
<%@ include file="/common/include/includeJavaScript.jsp"%>

<script type="text/javascript">
<%-- 개별 스크립트 영역 --%>
function doSearch() {
	if(document.frm.from.value > document.frm.to.value){
		alert("검색 기간 설정 시 시작일이 종료일 이전이어야 합니다.");
		return;
	}
	document.frm.target = "";
	document.frm.action = "/action/AccessLogAction.do?mode=getLoginLogTimeOrderReport";
	document.frm.submit();
}
function goPage(next_page) {
	document.frm.pg.value = next_page;
	document.frm.target = "";
	document.frm.action = "/action/AccessLogAction.do?mode=getLoginLogTimeOrderReport";
	document.frm.submit();
}
function set_date() {
	var myDate = new Date();
	var nowYear = myDate.getYear();
	if (navigator.userAgent.indexOf("MSIE") == -1)
		nowYear = 1900 + nowYear;
	var nowMonth = (myDate.getMonth()+1 + 100 + "").substring(1,3);	
	
	var sfrom;
	var sto;
	var svalue = document.frm.dateType.value;
	switch (svalue) {
	case "1" :

		break;
	case "2" :
		sfrom = nowYear + "-" + nowMonth + "-01";
		sto = addDate("d",-1,addDate("m",1, sfrom,"-"),"-");
		break;
	case "3" :
		sfrom = nowYear + "-01-01";
		sto   = nowYear + "-12-31";
		break;
	case "4" :
		sfrom = nowYear + "-01-01";
		sto   = nowYear + "-03-31";
		break;
	case "5" :
		sfrom = nowYear + "-04-01";
		sto   = nowYear + "-06-30";
		break;
	case "6" :
		sfrom = nowYear + "-07-01";
		sto   = nowYear + "-09-30";
		break;
	case "7" :
		sfrom = nowYear + "-10-01";
		sto   = nowYear + "-12-31";
		break;
	}
	frm.from.value = sfrom;
	frm.to.value   = sto;
}


/* ----------------------------------------------------------------------------
 * 특정 날짜에 대해 지정한 값만큼 가감(+-)한 날짜를 반환
 * 
 * 입력 파라미터 -----
 * pInterval : "yyyy" 는 연도 가감, "m" 은 월 가감, "d" 는 일 가감
 * pAddVal  : 가감 하고자 하는 값 (정수형)
 * pYyyymmdd : 가감의 기준이 되는 날짜
 * pDelimiter : pYyyymmdd 값에 사용된 구분자를 설정 (없으면 "" 입력)
 * 
 * 반환값 ----
 * yyyymmdd 또는 함수 입력시 지정된 구분자를 가지는 yyyy?mm?dd 값
 *
 * 사용예 ---
 * 2008-01-01 에 3 일 더하기 ==> addDate("d", 3, "2008-08-01", "-");
 * 20080301 에 8 개월 더하기 ==> addDate("m", 8, "20080301", "");
 --------------------------------------------------------------------------- */
function addDate(pInterval, pAddVal, pYyyymmdd, pDelimiter){
	 var yyyy;
	 var mm;
	 var dd;
	 var cDate;
	 var oDate;
	 var cYear, cMonth, cDay;

	 if (pDelimiter != "") {
	 	pYyyymmdd = pYyyymmdd.replace(eval("/\\" + pDelimiter + "/g"), "");
	 }

	 yyyy = pYyyymmdd.substr(0, 4);
	 mm  = pYyyymmdd.substr(4, 2);
	 dd  = pYyyymmdd.substr(6, 2);

	 if (pInterval == "yyyy") {
	 	yyyy = (yyyy * 1) + (pAddVal * 1); 
	 } else if (pInterval == "m") {
		 mm  = (mm * 1) + (pAddVal * 1);
	 } else if (pInterval == "d") {
		 dd  = (dd * 1) + (pAddVal * 1);
	 }

	 cDate = new Date(yyyy, mm - 1, dd) // 12월, 31일을 초과하는 입력값에 대해 자동으로 계산된 날짜가 만들어짐.
	 cYear = cDate.getFullYear();
	 cMonth = cDate.getMonth() + 1;
	 cDay = cDate.getDate();

	 cMonth = cMonth < 10 ? "0" + cMonth : cMonth;
	 cDay = cDay < 10 ? "0" + cDay : cDay;



	 if (pDelimiter != "") {
	 	return cYear + pDelimiter + cMonth + pDelimiter + cDay;
	 } else {
		 return cYear + cMonth + cDay;
	 }
}


</script>
</head>
<body>
<%-- 작업영역 --%>
<div style="margin: 70 0 0 20 ">
<table width="751" cellpadding="0" cellspacing="0">
	<tr>
		<td height="12">
			<% String back = request.getParameter("backButtonYN"); %>
			<jsp:include page="/common/include/includePageMainTitle.jsp" flush="true">
				<jsp:param name="title" value="로그인 현황(접속 시간 순)" />
				<jsp:param name="backButtonYN" value="<%=back %>" />
			</jsp:include>
		</td>
	</tr>
	
	<!--  검색 영역  -->
	<tr>
		<td height="21" align="left" valign="top">
			<form name="frm" method="post">
			<div style='display:none;'>
				<input type="hidden" name="pg" value="<c:out value="${list.valueListInfo.pagingPage}"/>">
			</div>
			<%@ include file="/common/include/searchBox_Header.jsp"%>
				<table width="100%" height="26" cellpadding="0" cellspacing="0" style="border-collapse: collapse; ">
					<colgroup> 
						<col width="85px">
						<col width="*">
						<col width="85px">
						<col width="110px">
					</colgroup>				    
					<tr>
						<td class="searchTitle_center">기간 설정</td>
						<td class="searchField_left">
							<select name="dateType" class="selectbox" onchange="set_date();" style="width: 110">
								<option value="1" <c:if test="${search.dateType == '1'  }">selected</c:if>>기간설정</option>
								<option value="2" <c:if test="${search.dateType == '2'  }">selected</c:if>>당월</option>
								<option value="3" <c:if test="${search.dateType == '3'  }">selected</c:if>>당해년도</option>
								<option value="4" <c:if test="${search.dateType == '4'  }">selected</c:if>>당해1분기</option>
								<option value="5" <c:if test="${search.dateType == '5'  }">selected</c:if>>당해2분기</option>
								<option value="6" <c:if test="${search.dateType == '6'  }">selected</c:if>>당해3분기</option>
								<option value="7" <c:if test="${search.dateType == '7'  }">selected</c:if>>당해4분기</option>
							</select>
							<script>
								jQuery(function(){jQuery( "#from" ).datepicker({});});
								jQuery(function(){jQuery( "#to" ).datepicker({});});
							</script>
							<input type="text" name="from" id="from" size="8" value="<c:out value="${search.from}" />" /> 
							~ <input type="text" name="to" id="to" size="8" value="<c:out value="${search.to}" />" />
						</td>
						<td class="searchTitle_center">이름</td>
						<td class="searchField_left">
							<input type="text" name="name"  class="textInput_left" style="width: 100%;" value="<c:out value="${search.name}"/>" onkeypress="if(event.keyCode == 13) doSearch();">
							<!-- <img src='/images/search_bt.gif' align="absmiddle" style="cursor:hand;" onclick="doSubmit();">  -->
						</td>
					</tr>
				</table>
			<jsp:include page="/common/include/searchBox_Footer.jsp" flush="true">
				<jsp:param name="hasbutton" value="Y" />
			</jsp:include>
			</form>
		</td>
	</tr>
	<tr>
		<td height='7'></td>
	</tr>
	<tr>
		<td height="35px">
			<!-- 페이지 정보, 버튼 -->			
			<div class="btNbox pb15">
				<div class="btNlefTdiv">				
					<img src="/images/sub/line3Blit.gif" alt="">
					<span class="bold colore74c3a"><c:out value="${list.valueListInfo.totalNumberOfEntries}"/></span>
					<span>Total - Page(<span><c:out value="${list.valueListInfo.pagingPage}"/></span> of <span><c:out value="${list.valueListInfo.totalNumberOfPages}"/></span>)</span>
				</div>
				<div class="btNrighTdiv">
					<a class="btNa0a0a0 txt2btn" href="/action/AccessLogAction.do?mode=getLoginLogReport">이름 순으로 현황 보기</a>
				</div>
			</div>
		</td>
	</tr>
	<tr>
		<td align="center">
			<table class="listTable">
				<thead>
					<tr height="25px">
						<td width="90px">구분</td>
						<td width="100px">부서</td>
						<td width="100px">직위</td>
						<td width="100px">이름</td>
						<td width="*">로그인 시간</td>
						<td width="100px">IP Address</td>
						<td width="60px">접속유형</td>
					</tr>
				</thead>
				<tbody id="ListData">
					<c:choose>
						<c:when test="${!empty list.list}">
							<c:forEach var="result" items="${list.list}">
							<tr onmouseover="row_over(this);" onmouseout="row_out(this);">
								<td><c:out value="${result.jobClass}" escapeXml="false" /></td>
								<td><c:out value="${result.deptName}" escapeXml="false" /></td>
								<td><c:out value="${result.companyPositionName}" escapeXml="false" /></td>
								<td><c:out value="${result.name}" escapeXml="false" /></td>
								<td><c:out value="${result.loginDate}" /></td>
								<td class="left"><c:out value="${result.ip}" /></td>
								<td><c:out value="${result.deviceType}" /></td>
							</tr>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<tr onmouseover="row_over(this);" onmouseout="row_out(this);" style="cursor: hand;">
								<td align="center" colspan="7">검색 결과가 없습니다.</td>
							</tr>
						</c:otherwise>
					</c:choose>
				</tbody>
			</table>
		</td>
	</tr>
	<%-- 페이징 영역 시작--%>
	<tr>
		<td>
			<table width="100%"  cellpadding="0" cellspacing="0">
				<tr height="30">
					<td width="100%" align="center">
						<SCRIPT LANGUAGE="JavaScript">
							document.write( makePageHtml( 
									<c:out value="${list.valueListInfo.pagingPage}" default="1"/>, 
									15, 
									<c:out value="${list.valueListInfo.totalNumberOfEntries}" default="0"/> , 
									15)
							) ;
						</SCRIPT>
					</td>
				</tr>
			</table>									
		</td>
	</tr>
	<%-- 페이징 영역 끝--%>
	<tr>
		<td>&nbsp;</td>
	</tr>
</table>
</div>
</div>
</body>
</html>