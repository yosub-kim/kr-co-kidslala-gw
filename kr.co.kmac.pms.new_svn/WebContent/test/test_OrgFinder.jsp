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

function callBackFuncName(expertPoolList){
	for(var i = 0; i < expertPoolList.length; i++){
		alert(expertPoolList[i].name);
	}
}
</script>
</head>
<body>
<%@ include file="/common/include/includeBodyTop.jsp"%>
<%-- 작업영역 --%>
<div id="PageFull">
<table align="center" width="100%">
	<tr>
		<td class="Title">orgFinder 테스트</td>
	</tr>
	<tr>
		<td>
			<a href="javascript:orgFinder_Open('callBackFuncName')">[찾기]</a>
		</td>
	</tr>
</table>
</div>
<%@ include file="/common/include/includeBodyBottom.jsp"%>
</body>
</html>