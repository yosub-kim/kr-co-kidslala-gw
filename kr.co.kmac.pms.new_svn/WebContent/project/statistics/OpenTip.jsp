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
</head>
 
<body style="width: 650px">
<table width="650px" cellpadding="0" cellspacing="0" style="table-layout:fixed" border="0">
	<tr>
		<td align="left">
			<jsp:include page="/common/include/includePageMainTitle.jsp" flush="true">
				<jsp:param name="title" value="※ 투입률 표시기준 및 수정방법" />
				<jsp:param name="backButtonYN" value="N"/>
			</jsp:include>
		</td>
	</tr>
	<tr>
		<td width="600" style="padding-left:4px; padding-right:4px;">
			<table class="listTable">				
				<thead>
					<tr height="25px">
						<td width="300px">과거(월)</td>
						<td width="300px">현재/미래(월)</td>
					</tr>
				</thead>
				<tbody id="ListData">
					<td><b>성과급 지급을 위해 입력한 실 투입정보</br>(M/M 투입률, 지도일지)이며 <font color="red">수정 불가능</font></b></td>
  					<td><b>온라인 예산서 작성시 입력한 월별 투입정보이며 </br>투입현황 상세화면에서 PM에 한해 <font color="blue">수정 가능</font></b></td>
				</tbody>			
		</td>
	</tr>
</table>					
</body>
</html>	