<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<html>
<head>
<%@ include file="/common/include/includeJavaScript.jsp"%>
<script>
var UserAgent = navigator.userAgent;

function Chk_Salary(){
	var status4 =  AjaxRequest.post (
			{	'url': "/action/ProjectARExpenseSanctionAction.do?mode=checkARExpenseSanction",
				'onSuccess':function(obj){
					var res = eval('(' + obj.responseText + ')');
					if(res.hasExSanction == "true" || res.hasExRestSanction == "true"){
						window.open("/system/popup/expPopUp.jsp?cnt="+res.cntExSanction+"&restCnt="+res.cntExRestSanction, "expPopup","top=120,left=120,width=500,height=400,scrollbars=no,toolbar=no,location=no,status=no");
					}
				}, 
				'onLoading':function(obj){},
				'onError':function(obj){ 
					//alert("강사료 결재 정보를 읽어올 수 없습니다.");
				}
			}
	); 
		
	if (UserAgent.match(/iPhone|iPod|iPad|Android|Windows CE|BlackBerry|Symbian|Windows Phone|webOS|Opera Mini|Opera Mobi|POLARIS|IEMobile|lgtelecom|nokia|SonyEricsson/i) != null 
			|| UserAgent.match(/LG|SAMSUNG|Samsung/) != null)
	{
		location.href = "/action/AuthorityAction.do?mode=mobileMainPage";
	}
	else
	{
		location.href = "/action/AuthorityAction.do?mode=mainPage";
	}
}	
function Chk_Salary_2(){
	var status5 =  AjaxRequest.post (
			{	'url': "/action/ScheduleAction.do?mode=countMySchedule_time",
				'onSuccess':function(obj){
					var res = eval('(' + obj.responseText + ')');
					if(res.scheduleCount > 0){
						window.open("/system/popup/expPopUp_time.jsp?scheduleCount="+res.scheduleCount, "expPopup_time","top=160,left=160,width=500,height=400,scrollbars=no,toolbar=no,location=no,status=no");
					}
				}, 
				'onLoading':function(obj){},
				'onError':function(obj){ 
					//alert("근무시간 정보를 읽어올 수 없습니다.");
				}
			}
	); 
	if (UserAgent.match(/iPhone|iPod|iPad|Android|Windows CE|BlackBerry|Symbian|Windows Phone|webOS|Opera Mini|Opera Mobi|POLARIS|IEMobile|lgtelecom|nokia|SonyEricsson/i) != null 
			|| UserAgent.match(/LG|SAMSUNG|Samsung/) != null)
	{
		location.href = "/action/AuthorityAction.do?mode=mobileMainPage";
	}
	else
	{
		location.href = "/action/AuthorityAction.do?mode=mainPage";
	}
	
}	

// 프로젝트 스케줄 입력
/* function Chk_Salary_3(){
	var status6 =  AjaxRequest.post (
			{	'url': "/action/ScheduleAction.do?mode=countConSchedule",
				'onSuccess':function(obj){
					var res = eval('(' + obj.responseText + ')');
					if(res.scheduleCount == 0){
						window.open("/action/PopupConfigAction.do?mode=getPopupActivity", "expPopup_time","top=160,left=160,width=535,height=360,scrollbars=no,toolbar=no,location=no,status=no");
					}
				}, 
				'onLoading':function(obj){},
				'onError':function(obj){ 
					alert("스케줄 정보를 읽어올 수 없습니다.");
				}
			}
	); 
	if (UserAgent.match(/iPhone|iPod|iPad|Android|Windows CE|BlackBerry|Symbian|Windows Phone|webOS|Opera Mini|Opera Mobi|POLARIS|IEMobile|lgtelecom|nokia|SonyEricsson/i) != null 
			|| UserAgent.match(/LG|SAMSUNG|Samsung/) != null)
	{
		location.href = "/m";
	}
	else
	{
		location.href = "/action/AuthorityAction.do?mode=mainPage";
	}
} */

function reditectMain(){
	if (UserAgent.match(/iPhone|iPod|iPad|Android|Windows CE|BlackBerry|Symbian|Windows Phone|webOS|Opera Mini|Opera Mobi|POLARIS|IEMobile|lgtelecom|nokia|SonyEricsson/i) != null 
			|| UserAgent.match(/LG|SAMSUNG|Samsung/) != null)
	{
		location.href = "/action/AuthorityAction.do?mode=mobileMainPage";
	}
	else
	{
		location.href = "/action/AuthorityAction.do?mode=mainPage";
	}
}

</script>
</head>
<% if (session.getAttribute("companyPosition").equals("04CI") || session.getAttribute("companyPosition").equals("06CB") 
		|| session.getAttribute("companyPosition").equals("08CF") || session.getAttribute("companyPosition").equals("09CJ") || session.getAttribute("companyPosition").equals("10TM")){ %>
	<body onload="Chk_Salary(); Chk_Salary_2();">
<%} else { %>
	<body onload="reditectMain();">
<%} %>
</body>
</html>
