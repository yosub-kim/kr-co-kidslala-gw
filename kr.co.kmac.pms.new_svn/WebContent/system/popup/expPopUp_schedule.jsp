<%@ page contentType="text/html; charset=utf-8"
	import="java.util.List"%>
<%@ include file="/common/include/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>프로젝트 스케줄 등록</title>
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
function showToDate() {
	$('chkWeek1').checked = true;
	$('chkWeek2').checked = true;
	$('chkWeek3').checked = true;
	$('chkWeek4').checked = true;
	$('chkWeek5').checked = true;
	$('chkWeek6').checked = false;
	$('chkWeek7').checked = false;

	if($('sequencial').checked){
		$('toDate').show();
		$('weekend').show();
	}else{
		$('endDate').value = '';
		$('toDate').hide();
		$('weekend').hide();
	}
}
function registProjectSchedule() {
	if($('startDate').value ==""){alert('일정을 선택하세요.'); return;}
	if($('workSeq').value ==""){alert('액티비티를 선택하세요.'); return;}
	if($('sequencial').checked && $('endDate').value == ""){
		alert('종료일을 입력하세요.'); return;
	}
	if($('sequencial').checked && ($('startDate').value > $('endDate').value)){
		alert('일정 연속 등록 시 종료일이 시작일보다 앞설 수 없습니다.');return;
	}
	var chkBoxEls = $$('input[name="ssn"]');
	var cnt=0;
	for ( var i=0;i<chkBoxEls.length;i++){ 
		if ( chkBoxEls[i].checked ) {
			cnt=cnt+1;
		}
	}

	var ActionURL = "/action/PopupConfigAction.do?mode=storeProjectManpower";
	var sFrm = document.forms["projectExpScheduleInsert"];

	var status = AjaxRequest.submit(
			sFrm,
			{	'url':ActionURL,
				'onSuccess':function(obj){
					alert("등록하였습니다.");
					window.close();
				},
				'onLoading':function(obj){},
				'onError':function(obj){
					alert("저장할 수 없습니다.");
				}
			}
	); status = null;	
}
function workSeqChange(projectCode){
	var ActionURL = "/action/PopupConfigAction.do?mode=projectActivityList"
			+"&projectCode="+projectCode;
	 AjaxRequest.post (
			{	'url':ActionURL,
				'onSuccess':function(obj){
					var res = eval('(' + obj.responseText + ')');
					if(res.result == 'SUCCESS'){
						$('activitySeq').innerHTML = "";
						$('activitySeq').add(new Option("진행 액티비티를 선택하세요.", ""));
						for(var n=0; res.projectActivityList.length>n; n++){
							$('activitySeq').add(new Option(res.projectActivityList[n].workName, res.projectActivityList[n].workSeq));
						}
					}
				},
				'onLoading':function(obj){},
				'onError':function(obj){
					alert("삭제할 수 없습니다.");
				}
			}
	);
}
/*popup layout*/
window.onload=function(){
	layer_open(this, 'pop_register');
}
</script>
</head>
<body>
	<%-- 작업영역 --%>
	<form name="projectExpScheduleInsert" method="post">
	<input type="hidden" name="ssn" id="ssn" value="<c:out value="${ssn }" />" />
	<div id="pop_register" class="popup_layer pop_register">
		<!-- <div class="popup_bg"></div> -->
		<div class="popup_inner tbl-sc" style="width:400px; ">
			<div class="a-both">
				<p class="h1">프로젝트 스케줄 등록<span style="display: none;" id="toDate"></p>
			</div>
			<div class="popup_contents">
			<div class="month_check">
				<div class="input_date">
					<fmt:parseDate value="${startDate}" type="DATE" dateStyle="SHORT"  pattern="yyyyMMdd" var="var1"/>
					<fmt:formatDate value="${var1}" pattern="yyyy-MM-dd" var="start"/>
						<input type="text" id="startDate"  name="startDate" size="12" value="<c:out value="${selectedDay}"/>">
				</div>
				<span>~</span>
				<div class="input_date">
					<fmt:parseDate value="${endDate}" type="DATE" dateStyle="SHORT"  pattern="yyyyMMdd" var="var2"/>
					<fmt:formatDate value="${var2}" pattern="yyyy-MM-dd" var="end"/>
					<input type="text" name="endDate" id="endDate" size="12">
				</div>
				<script>
					jQuery(function(){jQuery( "#startDate" ).datepicker({});});
					jQuery(function(){jQuery( "#endDate" ).datepicker({});});
				</script>
			</div>
			<div class="check_all">
				<input type="checkbox" value="true" name="sequencial" id="sequencial" onclick="showToDate()" class="btn_check" ><label for="sequencial"><p>연속입력</p></label> 
				<span style="display: none;" id="weekend">
					<p>월</p><input type="checkbox" value="true" name="chkWeek1" id="chkWeek1"  class="btn_check" checked><label for="chkWeek1"></label>
					<p>화</p><input type="checkbox" value="true" name="chkWeek2" id="chkWeek2"  class="btn_check" checked><label for="chkWeek2"></label>
					<p>수</p><input type="checkbox" value="true" name="chkWeek3" id="chkWeek3" class="btn_check" checked><label for="chkWeek3"></label>
					<p>목</p><input type="checkbox" value="true" name="chkWeek4" id="chkWeek4" class="btn_check" checked><label for="chkWeek4"></label>
					<p>금</p><input type="checkbox" value="true" name="chkWeek5" id="chkWeek5" class="btn_check" checked><label for="chkWeek5"></label>
					<p>토</p><input type="checkbox" value="true" name="chkWeek6" id="chkWeek6" class="btn_check"><label for="chkWeek6"></label>
					<p>일</p><input type="checkbox" value="true" name="chkWeek7" id="chkWeek7" class="btn_check"><label for="chkWeek7"></label>
				</span>
			</div>
			<select id="workSeq" name="workSeq"  class="select" onchange="workSeqChange(this.value);">
					<option value="">진행 프로젝트를 선택하세요.</option>
					<c:forEach var="rs" items="${projectInfo}">
						<option value="<c:out value="${rs.projectCode}" />">[<c:out value="${rs.projectCode }" />] <c:out value="${rs.projectName }" /> / <c:out value="${rs.role }" /></option>
					</c:forEach>
			</select>
			<select id="activitySeq" name="activitySeq"  class="select">
					<option value="">진행 액티비티를 선택하세요.</option>
			</select>
			<div class="btn_area">
				<button type="button" class="btn btn_d_blue" onclick="registProjectSchedule()">등록</button>
				<button type="button" class="btn btn_d_grey" onclick="window.close();">닫기</button>
			</div>
			</div>
		</div>
	</div>
	</form>
</body>
</html>