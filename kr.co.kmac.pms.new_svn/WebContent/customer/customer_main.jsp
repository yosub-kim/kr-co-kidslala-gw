<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%-- 공통js,css include --%>
<%@ include file="/common/include/includeCss.jsp"%>
<%@ include file="/common/include/includeJavaScript.jsp"%>
<script type="text/javascript" src="/js/tab/CustomerTabbedPane.js"></script>
<script type="text/javascript">
<%-- 개별 스크립트 영역 --%>
var myDate = new Date();
var nowYear = myDate.getYear();
var nowMonth = (myDate.getMonth()+1 + 100 + "").substring(1,3);
function set_date() {
	var sfrom;
	var sto;
	var svalue = document.frm.bungi.value;
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

function saveToExcel() {
	var surl = '/action/CustomerPresentReportAction.do?mode=saveToExcel';
	if(orgTab.ActiveTabPage == 'pane2') {
		surl += "&reportType=expert";
	} else {
		surl += "&reportType=BU";
	}
	surl += "&from=" + frm.from.value;
	surl += "&to=" + frm.to.value;
	document.location = surl;
}

function detail_view(customerInfoType,industryTypeCode,businessTypeCode,valueType,writerSsn) {
	var detailWin;
	var surl = "/action/CustomerPresentReportAction.do?mode=selectListForIndividual";
	surl += "&customerInfoType=" + customerInfoType;
	surl += "&industryTypeCode=" + industryTypeCode;
	surl += "&businessTypeCode=" + businessTypeCode;
	surl += "&valueType=" + valueType;
	surl += "&writer=" + writerSsn;
	surl += "&from=" + frm.from.value;
	surl += "&to=" + frm.to.value;

	var sFeatures = "top=100,left=100,width=800,height=450,scrollbars=yes";

	detailWin = window.open(surl,"detailWin", sFeatures);
	detailWin.focus();
}

function prjDetail_view(customerInfoType,industryTypeCode,businessTypeCode,valueType,writer) {
	var detailWin;
	var surl = "/action/BoardAction.do?mode=selectPrjList";
	//surl += "&customerInfoType=" + customerInfoType;
	//surl += "&industryTypeCode=" + industryTypeCode;
	//surl += "&businessTypeCode=" + businessTypeCode;
	//surl += "&valueType=" + valueType;
	surl += "&writer=" + writer;
	surl += "&from=" + frm.from.value;
	surl += "&to=" + frm.to.value;

	var sFeatures = "top=100,left=100,width=800,height=450,scrollbars=yes";

	detailWin = window.open(surl,"detailWin", sFeatures);
	detailWin.focus();
}

function doSearch(){
	orgTab.loadPage(orgTab.ActveTabPage);
}
</script>
</head>
<body style="overflow-x:hidden;overflow-y:hidden">
<%@ include file="/common/include/includeBodyTop.jsp"%>
<%-- 작업영역 --%>
<table width="765" cellpadding="0" cellspacing="0" style="table-layout:fixed" border="0">
	<tr>
		<td height='7'></td>
	</tr>
	<tr>
		<td>
			<jsp:include page="/common/include/includePageMainTitle.jsp" flush="true">
				<jsp:param name="title" value="개인별 고객정보 등록현황" />
				<jsp:param name="backButtonYN" value="N"/>
			</jsp:include>
		</td>
	</tr>
	<tr>
		<td height='7'></td>
	</tr>
	<tr>
		<td>
			<%@ include file="/common/include/searchBox_Header.jsp"%>
				<table width="100%" height="26" cellpadding="0" cellspacing="0">
					<colgroup> 
						<col style="padding-left: 3px; padding-right: 3px; text-align: right;"   width="80px">
						<col style="padding-left: 3px; padding-right: 3px; text-align: left;"    width="130px">
						<col style="padding-left: 3px; padding-right: 3px; text-align: right;"   width="*">
					</colgroup>				
				   	<form name="frm" method="post">
				   	<input type="hidden" name="mode" value="selectList">
				   	<input type="hidden" name="searchOK" value="1">
					<tr>
						<td class="searchTitle_center">기간</td>
						<td class="searchField_left">
								<select name="bungi" class='selectbox' style="width:130px"  onchange="set_date();">
									<option value="2">당월</option>
									<option value="3">당해년도</option>
									<option value="4">당해1분기</option>
									<option value="5">당해2분기</option>
									<option value="6">당해3분기</option>
									<option value="7">당해4분기</option>
								</select>
						</td>
						<td class="searchField_left">
							<input type="text" name="from" size="10" class="calendar" value="">
							~
							<input type="text" name="to" size="10" class="calendar" value="">
						</td>
					</tr>
					</form>
				</table>
			<%@ include file="/common/include/searchBox_Footer.jsp"%>
		</td>
	</tr>	
	<tr>
		<td height='7'></td>
	</tr>
	<script>
	frm.from.value = nowYear + "-" + nowMonth + "-01";
	frm.to.value = addDate("d",-1,addDate("m",1, frm.from.value,"-"),"-");
	</script>
	<tr>
		<td width="765" style="padding-left:0px; padding-right:0px;">
			<div class="tabbed-pane" >
				<table border="0" width="100%" cellpadding="0" cellspacing="0">
					<tr>
						<td>
							<ol class="tabs">
								<li><a href="#" class="active"	id="pane1">&nbsp;상근&nbsp;</a></li>
								<li><a href="#" 				id="pane2">&nbsp;전문가그룹&nbsp;</a></li>
							</ol>
						</td>
						<td align="right">
							<a href="javascript:saveToExcel();"><img src="/images/btn_excel.jpg"></a>
						</td>
					</tr>
				</table>
				<div id="Process_container" class="tabbed-container">
					<div id="Process_overlay" class="overlay" style="display: none">
						<img alt="" src="/images/loading.gif" align="middle" >	 
					</div>
					<div id="Process" class="pane">
					</div>
				</div> 
				<p></p>
				<script type="text/javascript">

				var orgTab = new CustomerTabbedPane('Process', 
						{
							'pane1': '/action/CustomerPresentReportAction.do?mode=getCustomerProjectPresentReport',
							'pane2': '/action/CustomerPresentReportAction.do?mode=getCustomerProjectPresentReport'
						},
						{
							onClick: function(e) {
								$('Process_overlay').show();
							},
							
							onSuccess: function(e) {
								$('Process_overlay').hide();
							}
						}); 
				</script>
			</div>								
		</td>
	</tr>
</table>
</body>
</html>
			