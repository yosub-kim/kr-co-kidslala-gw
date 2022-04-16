<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>

<%@page import="kr.co.kmac.pms.common.util.DateTime"%><html>
<head>
<title>멤버일정 등록</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<%-- 공통js,css include --%>
<%@ include file="/common/include/includeCss.jsp"%>
<%@ include file="/common/include/includeJavaScript.jsp"%>

<script type="text/javascript">
<%-- 개별 스크립트 영역 --%> 
function registProjectReportSchedule() {
	if($('startDate').value ==""){alert('일정을 선택하세요.'); return;}
	if($('workSeq').value ==""){alert('업무를 선택하세요.'); return;}
	if($('sequencial').checked && $('endDate').value == ""){
		alert('종료일을 입력하세요.'); return;
	}
	if($('sequencial').checked && ($('startDate').value > $('endDate').value)){
		alert('지도일정 연속 등록 시 종료일이 시작일보다 앞설 수 없습니다.');return;
	}
	var chkBoxEls = $$('input[name="ssn"]');
	var cnt=0;
	for ( var i=0;i<chkBoxEls.length;i++){ 
		if ( chkBoxEls[i].checked ) {
			cnt=cnt+1;
		}
	}
	if(cnt == 0){alert('업무 담당자를 선택하세요'); return;}

	/* <c:if test="${businessTypeCode !='BTA' && businessTypeCode !='BTB' && businessTypeCode !='BTC' && businessTypeCode !='BTJ' && businessTypeCode !='BTD' && businessTypeCode !='BTF'}">
		var timeEls = $$('input[name="time"]');
		for ( var i=0;i<timeEls.length;i++){
			if(timeEls[i].value == ""){alert("강의시간을 입력하세요.");return;}
		}
	</c:if> */
	
	var ActionURL = "/action/ProjectReportInfoAction.do?mode=storeProjectReportInfo";
	var sFrm = document.forms["projectReportScheduleForm"];

	var status = AjaxRequest.submit(
			sFrm,
			{	'url':ActionURL,
				'onSuccess':function(obj){
					alert("등록하였습니다.");
					AjaxRequest.post (
							{	'url': "/action/ExpenseManagerAction.do?mode=refreshErpDataSpcProject",
								'parameters' : {projectCode: $("projectCode").value },
								'onSuccess':function(obj){},
								'onLoading':function(obj){},
								'onError':function(obj){
									alert("인건비 예산 초과 정보 업데이트 시 에러가 발생하였습니다.");
								}
							}
					);
					opener.searchProjectReportInfo(); 
					//window.parent.Windows.close("modal_window");
					self.close();
				},
				'onLoading':function(obj){},
				'onError':function(obj){
					alert("저장할 수 없습니다.");
				}
			}
	); status = null;	
}
function timeInputChange(obj, element) {
	if($(obj).checked){
		<c:if test="${businessTypeCode!='BTA' && businessTypeCode!='BTB' && businessTypeCode!='BTC' && businessTypeCode!='BTJ' && businessTypeCode!='BTD' && businessTypeCode!='BTF'}">
			$(element).innerHTML = "<input type='text' name='time' class='textInput_center' style='width: 100%;ime-mode:disabled' onchange='Number_Only(this, 10, 1);'>";
		</c:if>
	}else{
		$(element).innerHTML = "";
	}	
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
</script>
</head>

<body style="width:480px; padding-left:5px; padding-right:5px">
	<form name="projectReportScheduleForm" method="post">
	<div style="display: none;">
		<input type="text" name="projectCode" id="projectCode" value="<c:out value="${projectCode}"/>" >
		<input type="text" name="businessTypeCode" id="businessTypeCode" value="<c:out value="${businessTypeCode}"/>" >
	</div>
	<table width="100%" cellpadding="0" cellspacing="0">
		<tr>
			<td>
				<h4 class="subTitle">멤버일정 등록</h4>
			</td>
		</tr>
					<!-- sub 타이틀 영역 종료--> 
				
		<tr>
			<td>
				<table width='100%' cellpadding="0" cellspacing="0">
					<tr>  
						<td class="detailTableTitle_center" width="90px">지도일</td>
						<td class="detailTableField_left" width="*">
							<script>
								jQuery(function(){jQuery( "#startDate" ).datepicker({});});
								jQuery(function(){jQuery( "#endDate" ).datepicker({});});
							</script>
							<input type="text" id="startDate"  name="startDate" size="12" value="<c:out value="${selectedDay}"/>">
							<span style="display: none;" id="toDate"> ~ <input type="text" name="endDate" id="endDate" size="12"></span> 
						</td>
					</tr>
					<tr>  
						<td class="detailTableTitle_center" width="90px">지도일 옵션</td>
						<td class="detailTableField_left" width="*"> 
							<input type="checkbox" value="true" name="sequencial" id="sequencial" onclick="showToDate()">연속입력 <br/>
							<span style="display: none;" id="weekend">
								<input type="checkbox" value="true" name="chkWeek1" id="chkWeek1" checked>월
								<input type="checkbox" value="true" name="chkWeek2" id="chkWeek2" checked>화
								<input type="checkbox" value="true" name="chkWeek3" id="chkWeek3" checked>수
								<input type="checkbox" value="true" name="chkWeek4" id="chkWeek4" checked>목
								<input type="checkbox" value="true" name="chkWeek5" id="chkWeek5" checked>금
								<input type="checkbox" value="true" name="chkWeek6" id="chkWeek6" >토
								<input type="checkbox" value="true" name="chkWeek7" id="chkWeek7" >일
								<input type="checkbox" value="true" name="chkWeek8" id="chkWeek8" checked>공휴일 제외
							</span>
						</td>
					</tr>
					<tr>  
						<td class="detailTableTitle_center" width="90px">업무 선택</td>
						<td class="detailTableField_left" width="*"> 
							<select id="workSeq" name="workSeq" id="workSeq" class="selectboxPopup" style="width: 100%">
								<option value="">-- 업무를 선택하세요. --</option>
								<c:forEach var="rs" items="${projectWorkNameList.list}">
									<option value="<c:out value="${rs.workSeq}" />"><c:out value="${rs.workName}" /><c:if test="${rs.startDate!=null && rs.startDate!=''}">&nbsp;&nbsp;&nbsp;[시작일:<c:out value="${rs.startDate}" />~ 종료일:<c:out value="${rs.endDate}" />]</c:if>
									</option>
								</c:forEach>
							</select>
						</td>
					</tr> 
					<tr>  
						<td class="detailTableTitle_center" width="90px">작성자 선택</td>
						<td class="detailTableField_left" width="*"> 
							<div style="width: 100%; height: 165px; margin-left: 0px; margin-right: 0px; margin-bottom: 3px; margin-top: 3px; overflow: auto">
								<table class="listTable">
									<colgroup>
										<col align="center"  width="30px">								
										<col align="center" width="*">								
										<col align="center" width="75px">
									<c:if test="${businessTypeCode=='BTE' || businessTypeCode=='BTG' || businessTypeCode=='BTI' || businessTypeCode=='BTK'}">
										<col align="center" width="65px">
									</c:if>								
									</colgroup>
									<thead>
										<tr height="25px">
											<td scope="col">선택</td>
											<td scope="col">이름</td> 
											<td scope="col">Role</td>
										<%-- <c:if test="${businessTypeCode=='BTE' || businessTypeCode=='BTG' || businessTypeCode=='BTI' || businessTypeCode=='BTK'}">
											<td scope="col">강의시간</td>
										</c:if> --%>
										</tr>
									</thead>			
									<tbody id="ListData">
										<c:forEach var="rs" items="${projectMemberList.list}">
											<tr>
												<td><input id="ssn" type="checkbox" name="ssn" value="<c:out value="${rs.ssn}" />" onclick="timeInputChange(this, 'input_<c:out value="${rs.ssn}" />')"></td>
												<td><c:out value="${rs.name}" /></td>
												<td><c:out value="${rs.role}" /></td>
											<%-- <c:if test="${businessTypeCode=='BTE' || businessTypeCode=='BTG' || businessTypeCode=='BTI' || businessTypeCode=='BTK'}">
									 			<td id="input_<c:out value="${rs.ssn}" />"></td>
									 		</c:if> --%>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	
		<tr>
			<td height="7"></td>
		</tr>
		<tr>
			<td style="height:36px; background:#F3F3F3;">
				<div class="btNbox pt10 pb10 txtR">
					<a class="btNe006bc6 txt2btn" href="#" onclick="registProjectReportSchedule()">등록</a>
				</div>
			</td>
		</tr>
	</table>						

	</form>
</body>

</html>					