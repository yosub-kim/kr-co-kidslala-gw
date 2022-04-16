<!-- 2019.07.16 전문가 투입 메뉴얼 화면 (김요섭) -->

<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>투입률 표시기준 및 수정방법</title>
<%-- 공통js,css include --%>
<%@ include file="/common/include/includeCss.jsp"%>
<%@ include file="/common/include/includeJavaScript.jsp"%>

<script type="text/javascript">
function saveListToExcel() {
	var surl = '/action/ExpertPoolManagerAction.do?mode=saveExpertPoolWorkPeriodList_taxbill';
	document.location = surl;
}
</script>
</head>
 
<body style="width: 650px">
<table width="650px" cellpadding="0" cellspacing="0" style="table-layout:fixed" border="0">
	<tr>
		<td align="left">
			<jsp:include page="/common/include/includePageMainTitle.jsp" flush="true">
				<jsp:param name="title" value="누락 계산서 결과" />
				<jsp:param name="backButtonYN" value="N"/>
			</jsp:include>
		</td>
	</tr>
	<tr>
		<td width="10" style="padding-left:4px; padding-right:4px;">
			<table >				
				<thead>
					<tr height="0">
					</tr>
				</thead>
				<tbody id="ListData">
					<td height="80px"><b>체크 완료 : </b><a class="btNe006bc6 txt4btn" href="#" onclick="saveListToExcel()">결과 다운로드</a></td>
				</tbody>			
		</td>
	</tr>
</table>					
</body>
</html>	