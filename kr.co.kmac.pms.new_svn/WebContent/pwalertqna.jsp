<%@page import="org.acegisecurity.BadCredentialsException"%>
<%@page import="org.acegisecurity.LockedException"%>
<script src="http://code.jquery.com/jquery-latest.js"></script> 
<script language="javascript">
/* alert('<c:out value="${param.log}"/>'); */
function getAuthToken(){
	$.getJSON("/noauthaction/UserAuthenticationAction.do?mode=getAuthenticationTokenFromWeb_qna", 
			{
				"userId":"yskim", 
				"phone1":"010", 
				"phone2":"5158", 
				"phone3":"5651"
			}, 
			function(data, status) {
				if(data.resultMsg == "1"){
					//alert("인증번호 발송 ");
					$("#auth").show();
				} else if(data.resultMsg == "2"){
					alert("인증불가!\n등록된 휴대전화 번호가 아닙니다. ");
				} else if(data.resultMsg == "3"){
					alert("시스템 오류\n관리자에게 연락하기 바랍니다.");
				}
	});	
	location.href("http://kidzlala.kmac.co.kr/action/BoardAction.do?mode=selectList&bbsId=qna");
}

function getAuthToken2(){
	$.getJSON("/noauthaction/UserAuthenticationAction.do?mode=getAuthenticationTokenFromWeb_qna", 
			{
				"userId":"lokal07", 
				"phone1":"010", 
				"phone2":"4758", 
				"phone3":"8227"
			}, 
			function(data, status) {
				if(data.resultMsg == "1"){
					//alert("인증번호 발송 ");
					$("#auth").show();
				} else if(data.resultMsg == "2"){
					alert("인증불가!\n등록된 휴대전화 번호가 아닙니다. ");
				} else if(data.resultMsg == "3"){
					alert("시스템 오류\n관리자에게 연락하기 바랍니다.");
				}
	});	/* 
	location.href("http://localhost/action/BoardAction.do?mode=selectList&bbsId=qna"); */
}

</script> 

<!doctype html>
<%@ page contentType="text/html; charset=utf-8"%>
<script>

	getAuthToken(); 
	getAuthToken2();
</script>
</html>

