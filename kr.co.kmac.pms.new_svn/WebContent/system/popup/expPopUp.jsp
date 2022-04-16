<%@ page contentType="text/html; charset=utf-8"
	import="java.util.List"%>
<%@ include file="/common/include/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>성과급 품의 안내</title>
<%-- 공통js,css include --%>
<%-- <%@ include file="/common/include/includeCss.jsp"%> --%>
<%@ include file="/common/include/includeJavaScript.jsp"%>
<link rel="stylesheet" href="/resources/css/common.css" type="text/css" />
<link rel="stylesheet" href="/resources/css/board.css" type="text/css" />
<link rel="stylesheet" href="/resources/css/selectric.css" type="text/css" />
<link rel="stylesheet" href="/resources/css/dev.css" type="text/css" />
<script type="text/javascript">
<%-- 개별 스크립트 영역 --%>
function goPage(){
	opener.window.parent.contentFrame.location 
	= "/action/ProjectARExpenseSanctionAction.do?mode=loadARExpenseSanctionForm&approvalType=expenseB";
	self.close();
}
function goPageRest(){
	opener.window.parent.contentFrame.location 
	= "/action/ProjectARExpenseRestSanctionAction.do?mode=loadARExpenseRestSanctionForm&approvalType=expenseR";
	self.close();
}
/*popup layout*/
window.onload=function(){
	layer_open(this, 'pop_Auth');
}
</script>
</head>
<body>
	<c:if test="${param.cnt != '' && param.cnt > 0}">
		<div id="pop_Auth" class="popup_layer">
			<div class="popup_inner tbl-sc" style="width:350px; height: 300px;">								
				<button type="button" class="btn-close" onclick="window.close();" title="닫기"><i class="mdi mdi-close"></i></button>
				<div class="popup_contents">
					<p class="h1">성과급 품의 안내</p>
					<p class="popup_text">품의해야 할 <span>성과급/강사료</span>가 있습니다.</p>					
					<div class="form_box">
						<button type="button" class="btn btn_blue" onclick="location.href='javascript:goPage();'">성과급 품의</button>
						<c:if test="${param.restCnt != '' && param.restCnt > 0}">
							<button type="button" class="btn btn_aqua" onclick="location.href='javascript:goPageRest();'">인센티브 품의</button>
						</c:if>
					</div>
				</div>
			</div>
		</div>
	</c:if>
</body>
</html>