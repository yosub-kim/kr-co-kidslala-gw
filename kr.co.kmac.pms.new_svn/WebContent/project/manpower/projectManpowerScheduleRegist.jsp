<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>

<%@page import="kr.co.kmac.pms.common.util.DateTime"%><html>
<head>
<title>투입일정 등록</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<%-- 공통js,css include --%>
<%@ include file="/common/include/includeCss.jsp"%>
<%@ include file="/common/include/includeJavaScript.jsp"%>

<script type="text/javascript">
<%-- 개별 스크립트 영역 --%> 
function registProjectManpowerSchedule() {
	if($('startDate').value ==""){alert('일정을 선택하세요.'); return;}
	if($('workSeq').value ==""){alert('프로젝트 일정을 선택하세요.'); return;}
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
	if(cnt == 0){alert('실행인력을 선택하세요'); return;}

	var ActionURL = "/action/ProjectManpowerAction.do?mode=storeProjectManpower";
	var sFrm = document.forms["projectManpowerInsertForm"];

	var status = AjaxRequest.submit(
			sFrm,
			{	'url':ActionURL,
				'onSuccess':function(obj){
					alert("등록하였습니다.");
					opener.searchProjectManpower(); 
					self.close();
					location.href="/action/ProjectManpowerAction.do?mode=getProjectManpowerList&projectCode=2104230&viewMode=myProject";
				},
				'onLoading':function(obj){},
				'onError':function(obj){
					alert("저장할 수 없습니다.");
				}
			}
	); status = null;	
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
	<form name="projectManpowerInsertForm" method="post">
	<div style="display: none;">
		<input type="text" name="projectCode" id="projectCode" value="<c:out value="${projectCode}"/>" >
		<input type="text" name="businessTypeCode" id="businessTypeCode" value="<c:out value="${businessTypeCode}"/>" >
	</div>
	<table width="100%" cellpadding="0" cellspacing="0">
		<tr>
			<td>
				<h4 class="subTitle">투입일정 등록</h4>
			</td>
		</tr>
		<!-- sub 타이틀 영역 종료--> 
				
		<tr>
			<td>
				<table width='100%' cellpadding="0" cellspacing="0">
					<tr>  
						<td class="detailTableTitle_center" width="90px">투입일</td>
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
						<td class="detailTableTitle_center" width="90px">투입일 옵션</td>
						<td class="detailTableField_left" width="*"> 
							<input type="checkbox" value="true" name="sequencial" id="sequencial" onclick="showToDate()">연속입력 <br/>
							<span style="display: none;" id="weekend">
								<input type="checkbox" value="true" name="chkWeek1" id="chkWeek1" checked>월
								<input type="checkbox" value="true" name="chkWeek2" id="chkWeek2" checked>화
								<input type="checkbox" value="true" name="chkWeek3" id="chkWeek3" checked>수
								<input type="checkbox" value="true" name="chkWeek4" id="chkWeek4" checked>목
								<input type="checkbox" value="true" name="chkWeek5" id="chkWeek5" checked>금
								<input type="checkbox" value="true" name="chkWeek6" id="chkWeek6" checked>토
								<input type="checkbox" value="true" name="chkWeek7" id="chkWeek7" checked>일
								<input type="checkbox" value="true" name="chkWeek8" id="chkWeek8" checked>공휴일 제외
							</span>
						</td>
					</tr>
					<tr>  
						<td class="detailTableTitle_center" width="90px">일정 선택</td>
						<td class="detailTableField_left" width="*"> 
							<select id="workSeq" name="workSeq" id="workSeq" class="selectboxPopup" style="width: 100%">
								<option value="">-- 프로젝트 일정을 선택하세요. --</option>
								<c:forEach var="rs" items="${projectWorkNameList.list}">
									<option value="<c:out value="${rs.workSeq}" />"><c:out value="${rs.workName}" /><c:if test="${rs.startDate!=null && rs.startDate!=''}">&nbsp;&nbsp;&nbsp;[시작일:<c:out value="${rs.startDate}" />~ 종료일:<c:out value="${rs.endDate}" />]</c:if>
									</option>
								</c:forEach>
							</select>
						</td>
					</tr> 
					<tr>  
						<td class="detailTableTitle_center" width="90px">투입인력 선택</td>
						<td class="detailTableField_left" width="*"> 
							<div style="width: 100%; height: 165px; margin-left: 0px; margin-right: 0px; margin-bottom: 3px; margin-top: 3px; overflow: auto">
								<table class="listTable">
									<colgroup>
										<col align="center"  width="30px">								
										<col align="center" width="*">								
										<col align="center" width="75px">
									</colgroup>
									<thead>
										<tr height="25px">
											<td scope="col">선택</td>
											<td scope="col">이름</td> 
											<td scope="col">Role</td>
										</tr>
									</thead>			
									<tbody id="ListData">
										<c:forEach var="rs" items="${projectMemberList.list}">
											<tr>
												<td><input id="ssn" type="checkbox" name="ssn" value="<c:out value="${rs.ssn}" />"></td>
												<td><c:out value="${rs.name}" /></td>
												<td><c:out value="${rs.role}" /></td>
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
					<a class="btNe006bc6 txt2btn" href="#" onclick="registProjectManpowerSchedule()">등록</a>
				</div>
			</td>
		</tr>
	</table>						

	</form>
</body>

</html>					