<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>

<%@page import="kr.co.kmac.pms.common.util.DateTime"%>
<%@page import="kr.co.kmac.pms.common.util.DateUtil"%>
<html>
<head>
<title>프로젝트 레포트 할당</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<%-- 공통js,css include --%>
<%@ include file="/common/include/includeCss.jsp"%>
<%@ include file="/common/include/includeJavaScript.jsp"%>

<script type="text/javascript">
<%-- 개별 스크립트 영역 --%> 
function projectReportAssign(){
	var ActionURL = "/action/MonthlyReportAssignAction.do?mode=assigneMonthlyReport";
	var status = AjaxRequest.post (
			{	'url':ActionURL,
				'parameters' : {"yyyymmdd": $F('yyyymmdd') },
				'onSuccess':function(obj){
					var res = eval('(' + obj.responseText + ')');
					if(res.result == 'SUCCESS'){
						$('result').innerHTML = res.resString + "\n\n프로젝트 레포트를 할당했습니다."; 
					}
				},
				'onLoading':function(obj){},
				'onError':function(obj){
					alert("System Fail");
				}
			}
	); status = null;		
}
function doSearch() {
	projectReportAssign();
}
</script>
</head>

<body>
<div style="margin: 70 0 0 20 ">
<table width="751" cellpadding="0" cellspacing="0">
	<tr>
		<td>
			<h4 class="mainTitle">프로젝트 레포트 할당</h4>
		</td>
	</tr>
	<tr>
		<td height="10"></td>
	</tr>
	
	<!--  검색 영역  -->
	<tr>
		<td height="21" align="left" valign="top">
			<%@ include file="/common/include/searchBox_Header.jsp"%>
				<table width="100%" height="26" cellpadding="0" cellspacing="0" style="border-collapse: collapse; ">
					<colgroup> 
						<col width="100px">
						<col width="*">
					</colgroup>				    
					<tr>
						<td class="searchTitle_center">기간 지정</td>
						<td class="searchField_left">
							<script>jQuery(function(){jQuery( "#yyyymmdd" ).datepicker({});});
							</script><input type="text" name="yyyymmdd" id="yyyymmdd" style="width: 80px;" value="<%=DateTime.getDateString("-") %>"> (선택월 기준 할당)</td>
					</tr>
				</table>
			<%@ include file="/common/include/searchBox_Footer_etc.jsp"%>	
		</td>
	</tr>
	<tr>
		<td height='7'></td>
	</tr>
	<tr>
		<td>
		<div id="result" style="width: 100%; height: 500px; border-style: solid; border-width: 1px; overflow: scroll;"></div>
		</td>
	</tr>
</table>
</div>
</body>
</html>					