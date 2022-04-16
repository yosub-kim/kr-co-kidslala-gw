<%@ page contentType="text/html; charset=utf-8"
	import="java.util.List"%>
<%@ include file="/common/include/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>스케줄 관리</title>
<%-- 공통js,css include --%>
<%@ include file="/common/include/includeCss.jsp"%>
<%@ include file="/common/include/includeJavaScript.jsp"%>
<script type="text/javascript">
<%-- 개별 스크립트 영역 --%>
function btnClear_onclick(){
	document.frm.reset();
}

function multiYN_onclick(chkObj){
	if(chkObj.checked){
		frm.edate.disabled = false;
		frm.inWeekYN.disabled = false;
	} else {
		frm.edate.disabled = true;
		frm.inWeekYN.disabled = true;
	}
}

function btnSave_onclick() {
	var sFrm = document.frm;
	if((sFrm.multiYN.checked)&&(sFrm.sdate.value >= sFrm.edate.value)){
		alert("종료일자는 기준일자 이후이어야 합니다.");
		return false;
	}
	if(sFrm.content.value == ""){
		alert("업무내용을 입력하세요.")
		return false;
	}

	var ActionURL = "/action/CompanyScheduleAction.do";	
	ActionURL += "?mode=saveSchedule";
		
	var status = AjaxRequest.submit(
		sFrm
		,{ 'url':ActionURL
			,'onSuccess':function(obj){
				var res = eval('(' + obj.responseText + ')');
				opener.document.location.reload();
				location.href = "/action/CompanyScheduleAction.do?mode=companyScheduleForm&sdate=<c:out value="${sdate}"/>";				
			}
			,'onLoading':function(obj){}
			,'onError':function(obj){				
				alert("저장할 수 없습니다.");
			}
		}
	); status = null;	
}

function deleteSchedule(sdate,startHour,startMin) {
	if(confirm("선택된 스케쥴을 삭제 하시겠습니까?")){
		var ActionURL = "/action/CompanyScheduleAction.do";	
		ActionURL += "?mode=removeSchedule";
		ActionURL += "&sdate=" + sdate;
		ActionURL += "&startHour=" + startHour;
		ActionURL += "&startMin=" + startMin;
			
		var status = AjaxRequest.get({ 
				'url':ActionURL
				,'onSuccess':function(obj){
					var res = eval('(' + obj.responseText + ')');
					//alert(res.resultMsg);
					location.href = "/action/CompanyScheduleAction.do?mode=companyScheduleForm&sdate=<c:out value="${sdate}"/>";
				}
				,'onLoading':function(obj){}
				,'onError':function(obj){				
					alert("저장할 수 없습니다.");
				}
			}
		); status = null;
	}
}
</script>
</head>
<body style="width: 650px">
<%-- 작업영역 --%>

<table width="100%" cellpadding="0" cellspacing="0" style="table-layout: fixed">
	<tr>
		<td>
			<h4 class="mainTitle">주요행사 관리</h4>
		</td>
	</tr>
	<c:if test="${checkWriter != 'N'}">
	<tr>
		<td align="center">
			<table cellpadding="0" cellspacing="0" width="100%" border="0">
				<form name="frm" method="post">
				<div style="display: none;">
					<input type="hidden" name="odate" value="<c:out value="${sdate}"/>">
					<input type="hidden" name="saveMode" value="<c:out value="${saveMode}"/>">
					<input type="hidden" name="oStartHour" value="<c:out value="${scheduleInfoData.startHour}"/>">
					<input type="hidden" name="oStartMin"	value="<c:out value="${scheduleInfoData.startMin}"/>">
				</div>
				<tbody>
					<tr>
						<td class="detailTableTitle_center" width="15%">일자</td>
						<td class="detailTableField_left" width="35%">
							<script>
								jQuery(function(){jQuery( "#sdate" ).datepicker({});});
							</script>
							<input type="text" name="sdate" id="sdate" size="14" value="<c:out value="${sdate}"/>">
						</td>
						<td class="detailTableTitle_center" width="15%">
							<input type="checkbox" name="multiYN" value="Y" <c:if test="${saveMode=='UPDATE'}">disabled</c:if> onclick="multiYN_onclick(this);">
							연속입력
						</td>
						<td class="detailTableField_left" width="35%">
							<script>
								jQuery(function(){jQuery( "#edate" ).datepicker({});});
							</script>
							<input type="text" name="edate" id="edate" size="14" value="" disabled>
							<input type="checkbox" name="inWeekYN" value="Y" disabled>토,일 제외
						</td>
					</tr>
					<tr>
						<td class="detailTableTitle_center" class="textInput_noLine_center" >업무내용</td>
						<td class="detailTableField_left" colspan="3">
							<input type="text" name="content" value="<c:out value="${scheduleInfoData.content}"/>" class="textInput_left"  style="width: 100%;" >
						</td>
					</tr>
					<tr>
						<td class="detailTableTitle_center">장소</td>
						<td class="detailTableField_left">
							<input type="text" name="place" value="<c:out value="${scheduleInfoData.place}"/>" class="textInput_left"  style="width: 100%;">
						</td>
						<td class="detailTableTitle_center">시간</td>
						<td class="detailTableField_left">
							<select name="startHour" class="selectBox" style="width: 17%">
								<option value=""></option>
								<c:forEach var="hour" items="${hourList}">
								<option value="<c:out value="${hour}"/>"<c:if test="${hour==scheduleInfoData.startHour}"> selected</c:if>><c:out value="${hour}"/></option>
								</c:forEach>
							</select>시
							<select name="startMin" class="selectBox" style="width: 17%">
								<option value=""></option>
								<c:forEach var="minute" items="${minList}">
								<option value="<c:out value="${minute}"/>"<c:if test="${minute==scheduleInfoData.startMin}"> selected</c:if>><c:out value="${minute}"/></option>
								</c:forEach>
							</select>분~
							<select name="endHour" class="selectBox" style="width: 17%">
								<option value=""></option>
								<c:forEach var="hour" items="${hourList}">
								<option value="<c:out value="${hour}"/>"<c:if test="${hour==scheduleInfoData.endHour}"> selected</c:if>><c:out value="${hour}"/></option>
								</c:forEach>
							</select>시
							<select name="endMin" class="selectBox" style="width: 17%">
								<option value=""></option>
								<c:forEach var="minute" items="${minList}">
								<option value="<c:out value="${minute}"/>"<c:if test="${minute==scheduleInfoData.endMin}"> selected</c:if>><c:out value="${minute}"/></option>
								</c:forEach>
							</select>분
						</td>
					</tr>
				</tbody>
				</form>
			</table>
		</td>
	</tr>
	<tr>
		<td height="1"></td>
	</tr>		
	<tr>
		<td>
			<table class="listTable">
				<tr>
					<td>(*)는 필수입력, 관련회사는 방문회사 입력, 회사 내부 관련 스케쥴은 'KMAC'로 입력</td>
					<td align="right">
						<a class="btN006bc6 txt2btn" href="?mode=companyScheduleForm&sdate=<c:out value="${sdate}"/>">새일정</a>
						<a class="btNe006bc6 txt2btn" href="#" onclick="btnSave_onclick();">저장</a>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td>
		
		</td>
	</tr>

	<tr>
		<td height="6"></td>
	</tr>
	</c:if>				
	<tr>
		<td align="center">
			<div style="height: 190px; overflow: auto;">
				<table class="tableStyle05">
					<thead>
						<tr>
							<td width="80px">일자</td>
							<td width="*">업무내용</td>
							<td width="180px">장소</td>
							<td width="100">시간</td>
							<c:if test="${checkWriter != 'N'}">
								<td width="50">삭제</td>
							</c:if>
						</tr>				
					</thead> 
					<form name="frmLst" method="post">
						<input type="hidden" name="ssn" value="<c:out value="${schedule.ssn}"/>">
						<input type="hidden" name="sdate" value="<c:out value="${sdate}"/>">
					<tbody>
						<c:forEach var="schedule" items="${scheduleList}">
						<tr height="30">
							<td><c:out value="${schedule.year}"/>-<c:out value="${schedule.month}"/>-<c:out value="${schedule.day}"/></td>
							<td><a href="?mode=companyScheduleForm&sdate=<c:out value="${sdate}"/>&startHour=<c:out value="${schedule.startHour}"/>&startMin=<c:out value="${schedule.startMin}"/>"><c:out value="${schedule.content}"/></a></td>
							<td><c:out value="${schedule.place}"/></td>
							<td><c:out value="${schedule.startHour}"/>:<c:out value="${schedule.startMin}"/>~<c:out value="${schedule.endHour}"/>:<c:out value="${schedule.endMin}"/></td>
							<c:if test="${checkWriter != 'N'}">
								<td><a href="#" onclick="deleteSchedule('<c:out value="${sdate}"/>','<c:out value="${schedule.startHour}"/>','<c:out value="${schedule.startMin}"/>');"><img alt="삭제" border="0" src="/images/delete.jpg"></a></td>
							</c:if>
						</tr>
						</c:forEach>
					</tbody> 
					</form>
				</table>
			</div>
		</td>
	</tr>
</table>
</body>
</html>