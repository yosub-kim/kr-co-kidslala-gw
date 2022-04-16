<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>

<html>
<head>
<title>공지사항</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%-- 공통js,css include --%>
<%-- <%@ include file="/common/include/includeCss.jsp"%> --%>
<%@ include file="/common/include/includeJavaScript.jsp"%>
<link rel="stylesheet" href="/resources/css/common.css" type="text/css" />
<link rel="stylesheet" href="/resources/css/board.css" type="text/css" />
<link rel="stylesheet" href="/resources/css/selectric.css" type="text/css" />
<link rel="stylesheet" href="/resources/css/dev.css" type="text/css" />
<meta name="viewport" content="width=1600">
<script type="text/javascript">

<%-- 개별 스크립트 영역 --%>

window.onload=function(){
	layer_open(this, 'pop_register');
} 
function cookie_set(obj){
	if(obj.checked){
		setCookie1("noHomePop", "Y", 7);
	}
}
function setCookie1( name, value, expiredays ){
	var todayDate = new Date();
	todayDate.setDate( todayDate.getDate() + expiredays );
	document.cookie = name + '=' + escape( value ) + '; path=/; expires=' + todayDate.toGMTString() + ';'
}

function setCookie2( name, value ){
	document.cookie = name + '=' + escape( value ) + '; path=/; '
}


function getCookie( name ){
	var nameOfCookie = name + "=";
	var x = 0;
	while ( x <= document.cookie.length ){
		var y = (x+nameOfCookie.length);
		if ( document.cookie.substring( x, y ) == nameOfCookie ) {
			if ( (endOfCookie=document.cookie.indexOf( ";", y )) == -1 )
				endOfCookie = document.cookie.length;
			return unescape( document.cookie.substring( y, endOfCookie ) );
		}
		x = document.cookie.indexOf( " ", x ) + 1;
		if ( x == 0 )
			break;
	}
	return "";
}
</script>
</head>
<body style="padding: 15px;">
	<form name="frm" method="post">
		<div id="pop_register" class="popup_layer pop_register">
			<div class="popup_inner tbl-sc" style="width:550px; ">
				<div class="info_box">
					<p class="info_title" style='color: #f089a5;'>재택근무자 의무 사항</p>
					<p class="info_text">
						- 사전 재택근무 신청 품의 및 스케줄 등록 <br/>
						- 일일 출/퇴근 체크(인트라넷 화면 좌측의 출근/퇴근 버튼 클릭)<br/>
						- 일일 업무일지 등록 및 산출물 첨부 (인트라넷 좌측 재택근무보고서 클릭)<br/>
					</p>
				</div>
				<p style='font-size: 14px; padding: 0 0 5px 30px;'>재택근무 신청 및 업무 보고 전결기준<p>
				<table class="tbl-edit" width="400" border="0" cellspacing="1" cellpadding="0">
					<tr>
						<th rowspan='2'>구분</th>
						<th colspan='2'>전결기준</th>
					</tr>
					<tr>
						<th>재택근무 신청 품의</th>
						<th>재택근무 업무 보고</th>
					</tr>
					<tr>
						<th>부문장 이상</th>
						<td style='text-align:center;'>CEO</td>
						<td style='text-align:center;'>본인 전결</td>
					</tr>
					<tr>
						<th>본부/센터장</th>
						<td style='text-align:center;'>부문장</td>
						<td style='text-align:center;'>부문장</td>
					</tr>
					<tr>
						<th>PM/팀장</th>
						<td style='text-align:center;'>본부/센터장</td>
						<td style='text-align:center;'>본부/센터장</td>
					</tr>
				</table>	
				<div class="info_box">
					<p class="info_text"><span class="c-new">
						※ RA의 경우 본부/센터장 전결을 기본으로 셋팅,필요시 PM/팀장으로 지정 가능
					</span></p>
					<p class="info_text">
						<input type="checkbox" name="popViewChk" id="popViewChk" class="btn_check" onclick="cookie_set(this);"><label for="popViewChk">&nbsp;</label>7일간 보지 않기
					</p>
				</div>
			</div>
		</div>
	</form>
</body>
</html>