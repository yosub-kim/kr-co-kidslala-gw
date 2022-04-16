<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%-- 공통js,css include --%>
<%-- <%@ include file="/common/include/includeCss.jsp"%> --%>
<%@ include file="/common/include/includeJavaScript.jsp"%>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<meta name="viewport" content="width=1600" />

	<link rel="stylesheet" href="/resources2/css/style.css" type="text/css" />
	<link rel="stylesheet" href="/resources/css/common.css" type="text/css" />
	<link rel="stylesheet" href="/resources/css/board.css" type="text/css" />
	<meta name="viewport" content="width=1600">
	<!--[if lt IE 9]>
	<script type="text/javascript" src="../resources/js/html5shiv.js"></script>
	<![endif]-->
</head>

<script type="text/javascript">
	function userChk(){
		var sFrm = document.frm;
		var ActionURL = "/action/AuthorityAction.do";
		ActionURL += "?mode=userChkPage";
		ActionURL += "&jumin="+sFrm.jumin.value;
		
		// delay time
		fnSleep = function (delay){
	        
	        var start = new Date().getTime();
	        while (start + delay > new Date().getTime());
		}
		
		// uid chk
		if(sFrm.uid.value != sFrm.jumin.value){
			alert("주민등록번호가 일치하지 않습니다.");
			return false;
		}
		
		// execute
		var status = AjaxRequest.post (
			{	'url': ActionURL,
				'onSuccess':function(obj){
					alert("인사평가 결과 페이지로 이동됩니다.");
					fnSleep(500);
					resultLink();
				}, 
				'onLoading':function(obj){},
				'onError':function(obj){
					alert("데이터를 가져오지 못했습니다.(문의:670)");
				}
			}
		);
	}
	
	function resultLink(){
		// result page
		document.location.href="/action/ExpertPoolManagerAction.do?mode=getExpertPoolWorkPeriodList_performance";
	}
</script>

<body>	
	<div class="location">
		<p class="menu_title">인사평가 결과</p>
		<ul>
			<li class="home">HOME</li>
			<li>인사평가 결과</li>
		</ul>
	</div>
	<!-- // location -->

	<div id="login">
	<form id="frm" name="frm" method="post">	
		<div style="hidden">
			<input type="hidden" name="uid" id="uid" value="<c:out value="${uid }" />"  onkeypress="if(event.keyCode == 13) userChk();"/>
		</div>
		<div class="login_wrap">
			<section>
				<h2>인사평가 결과 확인</h2>
				<h3>귀하의 주민등록번호 뒷자리를 정확하게 입력해 주세요.</h3>
				<br><br>
				<div class="login_form">						
					<div class="form_box">
						<div>
							<i class="mdi mdi-account-outline"></i>
							<input type="password" name="jumin" id="jumin" placeholder="주민등록번호 입력" title="주민등록번호 입력" />
						</div>
					</div>
				</div>
				<button class="btn btn_blue" style="background-color: #006699;" onclick="userChk();">확인</button>
			</section>
		</div>
	</form>
	</div>
	<!-- // login -->
</body>
</html>
