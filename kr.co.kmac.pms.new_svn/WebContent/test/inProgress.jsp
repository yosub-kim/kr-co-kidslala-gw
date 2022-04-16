<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>

<%@page import="kr.co.kmac.pms.common.util.DateTime"%>
<%@page import="kr.co.kmac.pms.sanction.general.data.SanctionDoc"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<%-- 공통js,css include --%>
<%@ include file="/common/include/includeCss.jsp"%>
<%@ include file="/common/include/includeJavaScript.jsp"%>
</head>
<script type="text/javascript">
function nameqwer() {

		AjaxRequest.post (
			{	'url': "http://intranet.kmac.co.kr/kmac/business_manage/kmac_35_1_new.asp",
				'parameters' : { "projectCode": '1'},
				'onSuccess':function(obj){
					alert('1');
				},
				'onLoading':function(obj){
				},
				'onError':function(obj){
					alert("저장할 수 없습니다.");
				}
			}
		); 		

}
</script>
<body>

	<form action="">
		<input type="button" onclick="nameqwer()">
	</form>
		<table width='765'  cellpadding="0" cellspacing="0">
			<tr>
				<td width="793" valign="top">
					<table width='765'  cellpadding="0" cellspacing="0">		
		
 
		<!-- sub 타이틀 영역 시작-->
						<tr><td height='5'>
						
						
						</td></tr>
						<tr><td align="center"><br><br><br><br><br>
							<font size="6"><strong>개발 진행중</strong></font> 
						</td></tr>

						
					
					</table>
				</td>
			</tr>
		</table>					
		
				
</body>
</html>
