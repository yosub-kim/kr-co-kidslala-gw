<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>SITE MAP</title>
<%-- 공통js,css include --%>
<%@ include file="/common/include/includeCss.jsp"%>
<%@ include file="/common/include/includeJavaScript.jsp"%>

<script type="text/javascript">
	document.observe("dom:loaded", function() {
		$('sitemap').innerHTML = $(opener).sitemapDiv.innerHTML;
	});
	function openpage(url){
		opener.openpage(url);
		self.close();
	}
</script>
</head>
 
<body style="margin: 5px;">
<table width="790" cellpadding="0" cellspacing="0" style="table-layout:fixed" border="0">
	<tr>
		<td height="7"></td>
	</tr>
	<tr>
		<td align="left">
			<jsp:include page="/common/include/includePageMainTitle.jsp" flush="true">
				<jsp:param name="title" value="Site Map" />
				<jsp:param name="backButtonYN" value="N"/>
			</jsp:include>
		</td>
	</tr>
	<tr>
		<td height="7"></td>
	</tr>
	<tr>
		<td width="790" style="padding-left:4px; padding-right:4px;">
			<div id="sitemap" >
				
			</div>								
		</td>
	</tr>
</table>					
</body>
</html>	