<%@ page contentType="text/html; charset=utf-8"
	import="java.util.List"%>
<%@ include file="/common/include/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>근무시간 확정 안내</title>
<%-- 공통js,css include --%>
<%-- <%@ include file="/common/include/includeCss.jsp"%> --%>
<%@ include file="/common/include/includeJavaScript.jsp"%>
<link rel="stylesheet" href="/resources/css/common.css" type="text/css" />
<link rel="stylesheet" href="/resources/css/board.css" type="text/css" />
<link rel="stylesheet" href="/resources/css/selectric.css" type="text/css" />
<link rel="stylesheet" href="/resources/css/dev.css" type="text/css" />
<script type="text/javascript">
<%-- 개별 스크립트 영역 --%>
// 사위원님 URL 넣고 ssn 받아오기
function goPage(){
	var userid = "<%=session.getAttribute("userId") %>";
	var thisDate = new Date();
	var thisYear = thisDate.getFullYear();
	var thisMonth = thisDate.getMonth()+2;
	if(thisMonth == 13){
		thisMonth = "1";
	}
 	opener.window.parent.contentFrame.location 
	// 실제 적용 코드
	// = "http://intranet.kmac.co.kr/kmac/newschedule/visitinfo/schedule_working.asp?mode=chk&year=<c:out value="{param.year}" />&month=<c:out value="{param.month}" />";
		= "https://intranet.kmac.co.kr/kmac/newschedule/working/schedule_working.asp?_id="+userid+"&year="+thisYear+"&month="+thisMonth;
	self.close();
}
/*popup layout*/
window.onload=function(){
	layer_open(this, 'pop_Auth');
}
</script>
</head>
<body>

	<c:if test="${param.scheduleCount > 0}">
		<div id="pop_Auth" class="popup_layer">
			<div class="popup_inner tbl-sc" style="width:350px; height: 300px;">								
				<button type="button" class="btn-close" onclick="window.close();" title="닫기"><i class="mdi mdi-close"></i></button>
				<div class="popup_contents">
					<p class="h1">근무시간 확정 안내</p>
					<p class="popup_text">확정해야 할 대상자가 <span><c:out value="${param.scheduleCount}"/></span>명 있습니다.</p>
					<div class="form_box">
						<button type="button" class="btn btn_blue" onclick="location.href='javascript:goPage();'">근무시간 확정</button>
					</div>		
				</div>
			</div>
		</div>
	</c:if>
</body>
</html>