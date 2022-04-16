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

<script type="text/javascript">
function goBlind_popup() {
	var popup = window.open("https://intranet.kmac.co.kr/kmac/task/blind_board/board_list.asp","","toolbar=no,status=no,width=795,height=600,directories=no,scrollbars=1,location=no,resizable=no,menubar=no,screenX=0,left=0,screenY=0,top=0,right=0");
	
	if(popup == null || popup.screenLeft == 0){
		alert('팝업이 차단되었습니다. 브라우저 우측 상단에 팝업을 허용해주세요.⛔');
	}else{
		popup;
	}
	history.back();
}
</script>

</head>

<body onload="goBlind_popup()">
</body>

</html>
 