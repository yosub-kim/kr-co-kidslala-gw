<%@ page contentType="text/html; charset=utf-8"
	import="java.util.List"%>
<%@ include file="/common/include/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>전문가 찾기</title>
<%-- 공통js,css include --%>
<%@ include file="/common/include/includeCss.jsp"%>
<%@ include file="/common/include/includeJavaScript.jsp"%>

<script type="text/javascript">
<%-- 개별 스크립트 영역 --%>

</script>
</head>
<body onload="page_load();">
<%@ include file="/common/include/includeBodyTop.jsp"%>
<%-- 작업영역 --%>
<div id="PageFull">
<table align="center" width="100%">
	<tr>
		<td class="Title">전문가 찾기</td>
	</tr>
	<tr>
		<td align="center">
			<table cellSpacing="0" borderColorDark="white" width="100%"
				borderColorLight="black" border="1" class='listTable' >
				<form name="frm" method="post">
				<thead>
					<tr height="25px">
						<td class="detailTableTitle_center" width="100">테스트</td>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>
						
						</td>
					</tr>
				</tbody>
			</table>
			<br>
		</td>
	</tr>
</table>
</div>
<%@ include file="/common/include/includeBodyBottom.jsp"%>
</body>
</html>