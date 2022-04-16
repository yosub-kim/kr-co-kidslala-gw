<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<html>
<head>
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
j$(function(){j$("[name='requestDate']" ).datepicker({});});

function addRowVoc() {
	var tableObj = $('vocTable');

	var tdCount = tableObj.down('tr').down('td').nextSiblings().length + 1 ;
    var newRow=tableObj.insertRow(-1);
	var newCellArr = new Array();
	for ( var i=0;i<tdCount;i++ ){
		newCellArr[i] = newRow.insertCell(-1);
	}
	var tableCount = tableObj.rows.length;
	new Effect.Highlight(newCellArr[0], { startcolor: '#FFFF80', endcolor: '#ffffff', restorecolor: '#ffffff' }); 
	new Effect.Highlight(newCellArr[1], { startcolor: '#FFFF80', endcolor: '#ffffff', restorecolor: '#ffffff' }); 
	new Effect.Highlight(newCellArr[2], { startcolor: '#FFFF80', endcolor: '#ffffff', restorecolor: '#ffffff' }); 
	
	newCellArr[0].innerHTML = "<input type='checkbox' name='vocCheck' class='btn_check' id='"+tableCount+"' /><label for='"+tableCount+"'></label>";
	newCellArr[1].innerHTML = "&nbsp;";
	newCellArr[2].innerHTML = "<input type='hidden' name='vocType' value='A'><input type='text' name='requestDate' size='10' class='' value='' style='width:90%'  />";

	//newCellArr[0].className = "BGC";
	//newCellArr[1].className = "BGC";
	newCellArr[2].style = "text-align: left";
	newCellArr[2].align = "left";
	
	j$(function(){j$("[name='requestDate']" ).datepicker({});});
}
function deleteRowVoc() {
	var tableObj = $('vocTable');
	var deleteOverAllIds = "";
	var chkBoxEls = $$('input[name="vocCheck"]');
	for ( var i=0;i<chkBoxEls.length;i++){ 
		if ( chkBoxEls[i].checked ) {
			$(chkBoxEls[i]).up().up().remove();
		}
	}
}
function setMonthData(type) {
	var ActionURL = "/action/ProjectVocAction.do?mode=getVocMonthlySchedule";
	var status = AjaxRequest.post (
			{	'url':ActionURL,
				'parameters' : { "type": type, "fromDate": <c:out value="${project.realStartDate}"/>, "toDate": <c:out value="${project.realEndDate}"/>},
				'onSuccess':function(obj){
		           	var res = eval('(' + obj.responseText + ')');
		           	var dateList = res.date;
					dateList.each(function(date){
						var tableObj = $('vocTable');
						var tdCount = tableObj.down('tr').down('td').nextSiblings().length + 1 ;
					    var newRow=tableObj.insertRow(-1);
						//newRow.innerHTML = "<fmt:parseDate value='${rs.requestDate}' type='DATE' dateStyle='SHORT'  pattern='yyyyMMdd' var='data1'/><fmt:formatDate value='${data1}' pattern='yyyy-MM-dd' var='requestDate'/><script>jQuery(function(){jQuery( '#requestDate' ).datepicker({});});";
						var newCellArr = new Array();
						for ( var i=0;i<tdCount;i++ ){
							newCellArr[i] = newRow.insertCell(-1);
						}
						var tableCount = tableObj.rows.length;
						new Effect.Highlight(newCellArr[0], { startcolor: '#FFFF80', endcolor: '#ffffff', restorecolor: '#ffffff' }); 
						new Effect.Highlight(newCellArr[1], { startcolor: '#FFFF80', endcolor: '#ffffff', restorecolor: '#ffffff' }); 
						new Effect.Highlight(newCellArr[2], { startcolor: '#FFFF80', endcolor: '#ffffff', restorecolor: '#ffffff' }); 
						
						newCellArr[0].innerHTML = "<input type='checkbox' name='vocCheck' class='btn_check' id='"+tableCount+"' /><label for='"+tableCount+"'></label>";
						newCellArr[1].innerHTML = "&nbsp;";
						newCellArr[2].innerHTML = "<input type='hidden' name='vocType' value='A'><input type='text' name='requestDate' size='10' class='calendar' style='width:90%' value='"+date+"' />";

						//newCellArr[0].align = "center";
						//newCellArr[1].align = "center";
						newCellArr[2].style = "text-align: left";
						newCellArr[2].align = "left";
						
						j$(function(){j$("[name='requestDate']" ).datepicker({});});
		    		});
				},
				'onLoading':function(obj){},
				'onError':function(obj){
					alert("저장할 수 없습니다.");
				}
			}
	); status = null;	
}
function saveVocSchedule() {
	var ActionURL = "/action/ProjectVocAction.do?mode=insertProjectVoc";
	var sFrm = document.forms["projectVocForm"];
	
	var status = AjaxRequest.submit(
			sFrm,
			{	'url':ActionURL,
				'onSuccess':function(obj){
					alert('저장하였습니다.');
					window.close();
/* 					window.parent.Windows.close("modal_window"); */
			},
				'onLoading':function(obj){},
				'onError':function(obj){
					alert("저장할 수 없습니다.");
				}
			}
	); status = null;
}

function closeVocSchedule() {
	window.parent.Windows.close("modal_window");
}

/*popup layout*/
window.onload=function(){
	layer_open(this, 'pop_register');
}
</script> 
</head>

<body style="padding:5px; width:395px">
	<form name="projectVocForm" method="post">
	<input type="hidden" name="projectCode" value="<c:out value="${project.projectCode}"/>"/>
	<div id="pop_register" class="popup_layer pop_register">
		<!-- <div class="popup_bg"></div> -->
		<div class="popup_inner tbl-sc" style="width:550px; ">
			<div class="a-both">
				<fmt:parseDate value="${project.realStartDate}" type="DATE" dateStyle="SHORT"  pattern="yyyyMMdd" var="fromdate"/>
				<fmt:formatDate value="${fromdate}" pattern="yyyy.MM.dd" var="formattedFrom"/>
				<fmt:parseDate value="${project.realEndDate}" type="DATE" dateStyle="SHORT"  pattern="yyyyMMdd" var="todate"/>
				<fmt:formatDate value="${todate}" pattern="yyyy.MM.dd" var="formattedTo"/>
				<p class="h1">VOC 실시</p>			
			</div>
			<div class="board_box">
				<div class="title_both">
					<div class="h1_area">
						<p class="term">일정등록 [<span><c:out value="${formattedFrom}"/> ~ <c:out value="${formattedTo}"/></span>]</p>
					</div>
					<div class="select_box">
					<button type="button" class="btn line btn_blue" onclick="saveVocSchedule()"><i class="mdi mdi-square-edit-outline"></i>저장</button>
					<button type="button" class="btn line btn_pink" onclick="window.close()"><i class="mdi mdi-trash-can-outline"></i>취소</button>
					</div>
				</div>
				<div class="board_contents">
					<table id="vocTable" class="tbl-edit td-c pd-none"><!-- td 영역이 가운데 정렬 일 경우 td-c -->
						<thead id="vocTableHead">
							<colgroup>
								<col style="width: 15%">
								<col style="width: 15%">
								<col style="width: *">
							</colgroup>
							<tr>
								<td>선택</td>
								<td>순번</td>
								<td>발송 예정일</td>
							</tr>
						</thead>
						<tbody id="vocTableBody">
							<c:if test="${!empty vocList}">
								<c:forEach var="rs" items="${vocList}"> 
									<tr> 
										<fmt:parseDate value="${rs.requestDate}" type="DATE" dateStyle="SHORT"  pattern="yyyyMMdd" var="data1"/>
										<fmt:formatDate value="${data1}" pattern="yyyy-MM-dd" var="requestDate"/>
										<td><c:if test="${rs.requestDate > today}"><input type="checkbox" name="vocCheck" class="btn_check" id='<c:out value="${rs.rownum }" />' /><label for='<c:out value="${rs.rownum }" />'></label></c:if></td>
										<td><c:out value="${rs.rownum}"/></td>
										<td><input type="hidden" name="vocType" value="A"><input type="text" style="width:90%;" name="requestDate" value="<c:out value="${requestDate}" />" /></td>
									</tr>
								</c:forEach>
							</c:if>
						</tbody>		
					</table>
				</div>
				<div class="btn_area">
					<button type="button" class="btn btn_blue" onclick="setMonthData('everyMonth')">매월</button>
					<button type="button" class="btn btn_d_blue" onclick="setMonthData('everyOtherMonth')">격월</button>
					<button type="button" class="btn btn_aqua" onclick="addRowVoc()">행추가</button>
					<button type="button" class="btn btn_purple" onclick="deleteRowVoc()">행삭제</button>
				</div>
				<br>
			</div>
		<%-- <div class="popup_contents">
			<div class="title">
					<div class="h1_area">
						<p class="h1">프로젝트 인력 정보</p>
					</div>
				</div>
			<div class="sign_area">	
				<table id="projectProgressTable" class="tbl-edit"><!-- td 영역이 가운데 정렬 일 경우 td-c -->
					<colgroup>
						<col style="width: 15%">
						<col style="width: 15%">
						<col style="width: *">
					</colgroup>
					<tr>
						<th>선택</th>
						<th>순서</th>
						<th>발송일</th>
					</tr>
					<tbody id="vocTableBody">
							<c:if test="${!empty vocList}">
								<c:forEach var="rs" items="${vocList}"> 
									<tr> 
										<fmt:parseDate value="${rs.requestDate}" type="DATE" dateStyle="SHORT"  pattern="yyyyMMdd" var="data1"/>
										<fmt:formatDate value="${data1}" pattern="yyyy-MM-dd" var="requestDate"/>
										<td><c:if test="${rs.requestDate > today}"><input type="checkbox" name="vocCheck"/></c:if></td>
										<td><c:out value="${rs.rownum}"/></td>
										<td><input type="hidden" name="vocType" value="A"><input type="text" style="width:90%;" name="requestDate" value="<c:out value="${requestDate}" />" /></td>
									</tr>
								</c:forEach>
							</c:if>
						</tbody>		
				
				</table>
			</div>
					</div> --%>
		</div>
	</div>
	
	
	<%-- 	<table width="100%" cellpadding="0" cellspacing="0">
			<tr>
				<td>
					<h4 class="subTitle">VOC 발송</h4>
				</td>
			</tr>
			<tr>
				<td class="bold">
					프로젝트 기간:
					<fmt:parseDate value="${project.realStartDate}" type="DATE" dateStyle="SHORT"  pattern="yyyyMMdd" var="fromdate"/>
					<fmt:formatDate value="${fromdate}" pattern="yyyy.MM.dd" var="formattedFrom"/>
					<fmt:parseDate value="${project.realEndDate}" type="DATE" dateStyle="SHORT"  pattern="yyyyMMdd" var="todate"/>
					<fmt:formatDate value="${todate}" pattern="yyyy.MM.dd" var="formattedTo"/>
					<c:out value="${formattedFrom}"/> ~ <c:out value="${formattedTo}"/>
				<td>
				
			</tr>
			<tr>
				<td>
					<table id="vocTable" class="listTable">
						<thead id="vocTableHead">
							<tr>
								<td width="45px">선택</td>
								<td width="45px">순번</td>
								<td width="*">발송 예정일</td>
							</tr>
						</thead>			
						<tbody id="vocTableBody">
							<c:if test="${!empty vocList}">
								<c:forEach var="rs" items="${vocList}"> 
									<tr> 
										<fmt:parseDate value="${rs.requestDate}" type="DATE" dateStyle="SHORT"  pattern="yyyyMMdd" var="data1"/>
										<fmt:formatDate value="${data1}" pattern="yyyy-MM-dd" var="requestDate"/>
										<td><c:if test="${rs.requestDate > today}"><input type="checkbox" name="vocCheck"/></c:if></td>
										<td><c:out value="${rs.rownum}"/></td>
										<td><input type="hidden" name="vocType" value="A"><input type="text" style="width:90%;" name="requestDate" value="<c:out value="${requestDate}" />" /></td>
									</tr>
								</c:forEach>
							</c:if>
						</tbody>											
					</table>
				</td>
			</tr>
			<tr>
				<td>
					<div class="btNbox mt10 mb10 txtC">
						<a class="btNaaa" style="padding: 5px 7px" href="#" onclick="setMonthData('everyMonth')">매월</a>
						<a class="btNaaa" style="padding: 5px 7px" href="#" onclick="setMonthData('everyOtherMonth')">격월</a>
						<a class="btN3fac0c" style="padding: 5px 7px" href="#" onclick="addRowVoc()">행추가</a> 
						<a class="btNe14f42" style="padding: 5px 7px" href="#" onclick="deleteRowVoc()">행삭제</a> 
					</div>
				</td>
			</tr>	
			<tr>
				<td>
					<table width="100%" height="36" bgcolor="#F3F3F3" cellpadding="0" cellspacing="0">
						<tr>
							<td>
								<div class="btNbox mt10 mb10 txtR">
									<a class="btNe006bc6 txt2btn" href="#" onclick="saveVocSchedule();" >저장</a>
									<a class="btNa0a0a0 txt2btn" href="#" onclick="window.close();">닫기</a>
								</div>
							</td>
						</tr>
					</table>
				</td> 
			</tr>
		</table>	 --%>
	</form>
</body>

</html>					