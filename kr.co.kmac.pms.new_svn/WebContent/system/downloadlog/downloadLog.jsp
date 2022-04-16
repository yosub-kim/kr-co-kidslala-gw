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
	document.frm.action = "/action/DownloadLogReportAction.do?mode=getDownloadLogReport";
	document.frm.submit();
}
function limitOpenShow(ssn, limitVal) {
	document.limitOpenInfoForm.limitTargetSsn.value=ssn;
	document.limitOpenInfoForm.isLimit.value=limitVal;

	$('limitInfo').style.top = window.event.clientY;
	$('limitInfo').style.left = 455;
	$('limitInfo').show();
}
function limitOpenClose() {
	$('limitInfo').hide();
}
function limitOpenRequest() {
	var sFrm = document.forms["limitOpenInfoForm"];

	for(var i=0; i < sFrm.limitOpenYn.length; i++) {
		if (sFrm.limitOpenYn[i].checked == true) break;
		if (i==sFrm.limitOpenYn.length-1) {
			alert("예, 아니오 둘 중에 하나를 선택하세요.");
			sFrm.limitOpenYn[0].focus();
			return;
		}
	}

	var status = AjaxRequest.submit(
			sFrm, 
			{	'url': "/action/ExpertPoolManagerAction.do?mode=setDailyDownloadLimit",
				'onSuccess':function(obj){
					alert('일일 다운로드 제한 여부를 설정하였습니다.');
					doSearch();
				},
				'onLoading':function(obj){},
				'onError':function(obj){
					alert("execution Fail");
				}
			}
	); status = null;	
}
function go_detail(pSsn, pName) {
	detail.ssn.value = pSsn;
	detail.name.value = pName;
	detail.submit();
}
function goPage(next_page) {
	document.frm.pg.value = next_page;
	document.frm.target = "";
	document.frm.action = "/action/DownloadLogReportAction.do?mode=getDownloadLogReport";
	document.frm.submit();
}
function set_date() {
	var myDate = new Date();
	var nowYear = myDate.getFullYear();
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
				<jsp:param name="title" value="다운로드 현황" />
				<jsp:param name="backButtonYN" value="<%=back %>" />
			</jsp:include>
		</td>
	</tr>
	<!--  검색 영역  -->
	<tr>
		<td height="21" align="left" valign="top">
			<form name="frm" id="frm" method="post">
			<div style='display:none;'>
				<input type="hidden" name="pg" value="<c:out value="${list.valueListInfo.pagingPage}"/>">
			</div>
			<%@ include file="/common/include/searchBox_Header.jsp"%>
				<table width="100%" height="26" cellpadding="0" cellspacing="0" style="border-collapse: collapse; ">
					<colgroup> 
						<col width="60px">
						<col width="*">
						<col width="60px">
						<col width="70px">
						<col width="60px">
						<col width="70px">
					</colgroup>				    
					<tr>
						<td class="searchTitle_center">기간 설정</td>
						<td class="searchField_left">
							<select name="dateType" class="selectbox" onchange="set_date();" style="width: 80px">
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
							<input type="text" name="from" id="from" size="9" style="width:80px;"	value="<c:out value="${search.from}"/>">
							~
							<input type="text" name="to" id="to" size="9" style="width:80px;" value="<c:out value="${search.to}"/>">
						</td>
						<td class="searchTitle_center">제한 여부</td>
						<td class="searchField_left">
							<select name=limitYN class="selectbox" style="100%">
								<option value="">전체</option>
								<option value='Y' <c:if test="${search.limitYN=='Y'}">selected</c:if>>예</option>
								<option value='N' <c:if test="${search.limitYN=='N'}">selected</c:if>>아니오</option>
							</select>
						<td class="searchTitle_center">이름</td>
						<td class="searchField_left">
							<input type="text" name="name"  class="textInput_left" style="width: 80px;" value="<c:out value="${search.name}"/>" onkeypress="if(event.keyCode == 13) doSearch();">
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
		<td height="7"></td>
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
			</div>
		</td>
	</tr>
	<tr>
		<td>
			<table class="listTable">
				<thead>
					<tr height="25px">
						<td width="130px">구분</td>
						<td width="130px">부서</td>
						<td width="130px">직위/직책</td>
						<td width="*">이름</td>							
						<td width="100px">다운로드 수</td>
						<td width="90px">금월 다운로드<br>제한 여부</td>
					</tr>
				</thead>
				<tbody id="ListData">
					<c:choose>
					<c:when test="${!empty list.list}">
						<c:forEach var="result" items="${list.list}">
						<tr>
							<td><c:out value="${result.jobclass}" escapeXml="false" /></td>
							<td><c:out value="${result.dept}" escapeXml="false" /></td>
							<td><c:out value="${result.position}" escapeXml="false" /></td>
							<td style="cursor: hand;" onclick="go_detail('<c:out value="${result.ssn}"/>','<c:out value="${result.name}"/>');"><c:out value="${result.name}" escapeXml="false" /></td>
							<td>
								<c:choose>
								<c:when test="${result.cnt >= 100}">
									<b><c:out value="${result.cnt}" /></b>
								</c:when>
								<c:otherwise>
									<c:out value="${result.cnt}" />
								</c:otherwise>
								</c:choose>
							</td>
							<td style="cursor: hand;" onclick="limitOpenShow('<c:out value="${result.ssn}" />','<c:out value="${result.isDailyDownloadLimit}" />');">
								<c:choose>
								<c:when test="${result.isDailyDownloadLimit > 0}"><font color="red">예</font></c:when>
								<c:otherwise>아니오</c:otherwise>
								</c:choose>
							</td>
						</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr>
							<td colspan="6">검색 결과가 없습니다.</td>
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
			<table width="100%" cellpadding="0" cellspacing="0">
				<tr height="30">
					<td>
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
	</form>
	<%-- 페이징 영역 끝--%>
	<form name="detail">
		<input type="hidden" name="mode"	value="getDownloadLogDetailReport">
		<input type="hidden" name="from"	value="<c:out value="${search.from}"/>">
		<input type="hidden" name="to"		value="<c:out value="${search.to}"/>">
		<input type="hidden" name="ssn"		value="">
		<input type="hidden" name="name"	value="">
		<input type="hidden" name="backButtonYN"	value="Y">
	</form>
	<tr>
		<td>&nbsp;</td>
	</tr>
</table>
</div>
<form name="limitOpenInfoForm" mothod="post">
	<input type="hidden" name="limitTargetSsn" id="limitTargetSsn" value="" />
	<input type="hidden" name="isLimit" id="isLimit" value="" />
	<div id="limitInfo" 
		style="position: absolute; background: white; display: none; width: 310px; text-align: center; border-style: solid; border-color: gray; border-width: 3px;">
		<table style="" cellpadding="3" cellspacing="3">
			<tr>
				<td><img alt="" src="/images/title_de02.jpg" align="top"><span style="subTitle"> <b>일일 다운로드 제한 여부 설정</b></span></td>
			</tr>
			<tr>
				<td><table id="delayInfoTable" width="290px">
					<thead id="delayInfoTableHeader">
						<tr>
							<td class="detailTableTitle_center" width="30%">제한 하기</td>
							<td class="detailTableField_left" width="*">
								<input type="radio" name="limitOpenYn" value="Y" <c:if test="${isLimit==0}">checked</c:if>>예&nbsp;&nbsp;&nbsp;
								<input type="radio" name="limitOpenYn" value="N" <c:if test="${isLimit>=1}">checked</c:if>>아니오
							</td>
						</tr>
					</thead>
					<tbody id="delayInfoTableBody">
					</tbody>
				</table></td>
			</tr>
			<tr align="right">
				<td><img alt="" src="/images/btn_regist.jpg" alt="설정" onclick="limitOpenRequest()" style="cursor: hand;"/>
				<img alt="" src="/images/btn_close.jpg" alt="닫기" onclick="limitOpenClose()" style="cursor: hand;"/></td>
			</tr>
		</table>
	</div>
</form>
</body>
</html>