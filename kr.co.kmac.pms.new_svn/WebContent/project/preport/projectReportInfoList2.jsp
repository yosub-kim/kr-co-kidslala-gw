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

<script type="text/javascript">
<%-- 개별 스크립트 영역 --%>
function closeFrame(){
	window.close();
	location.href="/action/ProjectManpowerAction.do?mode=getProjectManpowerList&projectCode=<%= request.getParameter("projectCode") %>&viewMode=<%= request.getParameter("viewMode") %>";
}
function createProjectReport(){
	var ActionURL = "/action/ProjectReportContentAction.do?mode=createProjectReportContent";
	var status = AjaxRequest.post (
			{	'url':ActionURL,
				'parameters' : { "projectCode": $("projectCode").value },
				'onSuccess':function(obj){
					var res = eval('(' + obj.responseText + ')');
					alert('지도일지를 생성했습니다.');
				}, 
				'onLoading':function(obj){},
				'onError':function(obj){
					alert("저장할 수 없습니다.");
				}
			}
	); status = null;	
}
function searchProjectReportInfo() {
	var ActionURL = "/action/ProjectReportInfoAction.do?mode=searchProjectReportInfoList";
	var sFrm = document.forms["projectReportInfoForm"];
	
	var status = AjaxRequest.submit (
			sFrm,
			{	'url':ActionURL,
				'onSuccess':function(obj){
					var res = eval('(' + obj.responseText + ')');
					var rsObj = res.projectReportPlanList;		 
					var table = $('projectReportInfoTable');
					var tbody = $('projectReportInfoTableBody').childElements();
		           	var tdCount = $('projectReportInfoTableHeader').down('tr').childElements().size();

		           	tbody.each( function(preportInfo){
		           		preportInfo.remove();
		    		});
		    		
 		    		rsObj.each(function(preportInfo){
			    	    var newRow=table.insertRow(-1);
			    		var newCellArr = new Array();
			    		for ( var i=0;i<tdCount;i++ ){
			    			newCellArr[i] = newRow.insertCell(-1);
			    		}		
						
		    			newCellArr[0].innerHTML = "<span id='day'>"+preportInfo.day+"</span>";
		    			newCellArr[1].innerHTML = "<span id='week'>"+preportInfo.week+"</span>";
		    			newCellArr[2].innerHTML = "<span id='workSeq' style='display: none;'>"+preportInfo.workSeq+"</span><span id='workName'>"+preportInfo.workName+"</span>";
		    			var tempHtml = "";
		    			for(var i=0; preportInfo.ssnArray.length >i; i++){
			    			if(preportInfo.nameArray[i] != ''){
			    				tempHtml = "<span id='"+preportInfo.year+preportInfo.month+preportInfo.day+preportInfo.ssnArray[i]+"' year='"+preportInfo.year+"' month='"+preportInfo.month+"' day='"+preportInfo.day+"' ssn='"+preportInfo.ssnArray[i]+"'>"
												+preportInfo.nameArray[i]
												+"<img alt='삭제' src='/images/delete.gif' align='absmiddle' style='cursor: hand;' onclick=\"deleteProjectReportSchedule('"+preportInfo.year+preportInfo.month+preportInfo.day+preportInfo.ssnArray[i]+"')\">" 
												+ ",&nbsp;</span>" + tempHtml  ;
			    			}
		    			}
		    			newCellArr[3].innerHTML = tempHtml;
		    			newCellArr[4].innerHTML = "<span id='outputName'>"+preportInfo.outputName+"</span>";

		    			if(preportInfo.week == 'SAT' ){newCellArr[0].bgColor = "#eee";} 
		    			if(preportInfo.week == 'SUN'){newCellArr[0].bgColor = "#eee";} 
		    			if(preportInfo.week == 'SAT' ){newCellArr[1].bgColor = "#eee";} 
		    			if(preportInfo.week == 'SUN'){newCellArr[1].bgColor = "#eee";} 
		    			if(preportInfo.week == 'SAT' ){newCellArr[2].bgColor = "#eee";} 
		    			if(preportInfo.week == 'SUN'){newCellArr[2].bgColor = "#eee";} 
		    			if(preportInfo.week == 'SAT' ){newCellArr[3].bgColor = "#eee";} 
		    			if(preportInfo.week == 'SUN'){newCellArr[3].bgColor = "#eee";} 
		    			if(preportInfo.week == 'SAT' ){newCellArr[4].bgColor = "#eee";} 
		    			if(preportInfo.week == 'SUN'){newCellArr[4].bgColor = "#eee";}  
		    		});	
				}, 
				'onLoading':function(obj){},
				'onError':function(obj){
					alert("저장할 수 없습니다.");
				}
			}
	); status = null;			
}
function openProjecReportScheduleRegistPopup(projectReportElmt) { 
	selectedElmt = $(projectReportElmt);
	var preportPop = 
	window.open("/action/ProjectReportInfoAction.do?mode=loadProjectSchedulePopup&projectCode="+$('projectCode').value,
			"acct", "top=0,left=0,width=469,height=350,scrollbars=no");
}
function deleteProjectReportSchedule(delKey){
	if(confirm("삭제 하시겠습니까?")) {
		var scheduleElement = $(delKey);
		var ActionURL = "/action/ProjectReportInfoAction.do?mode=deleteProjectReportInfo";
		var status = AjaxRequest.post (
				{	'url':ActionURL,
					'parameters' : { "projectCode": $("projectCode").value, "year": scheduleElement.readAttribute("year"), "month": scheduleElement.readAttribute("month"), "day": scheduleElement.readAttribute("day"), "ssn":scheduleElement.readAttribute("ssn") },
					'onSuccess':function(obj){
						var res = eval('(' + obj.responseText + ')');
						alert(res.message);
						if(res.canDelete == true){//담당자가 없을경우 업무명 산출물 삭제
							var siblingsCnt = scheduleElement.siblings().length;
							var targetTd = scheduleElement.up('td');
							scheduleElement.remove();	
							if(siblingsCnt==0){
								targetTd.previous(0).update();
								targetTd.next(0).update();
							}
						}
					},
					'onLoading':function(obj){},
					'onError':function(obj){
						alert("저장할 수 없습니다.");
					}
				}
		); status = null;
	}
}

</script>
</head>
 
<body>
	<form name="projectReportInfoForm" method="post">
	<div style="display: none;">
		<input type="text" name="projectCode" id="projectCode" value="<c:out value="${projectCode}"/>" >
		<input type="text" name="readonly" id="readonly" value="<c:out value="${readonly}"/>" >
	</div>
	
	<div style="padding: 20 20 0 20">
		<!-- <div class="popup_bg"></div> -->
		<div class="fixed_contents sc">
		<div class="popup_inner" style="width:900px; ">
		<div class="board_box">
			<div class="title_both">
				<div class="h1_area">
					<p class="term"><i class="mdi mdi-file-document-outline"></i>일정 삭제</p>
				</div>
				<div class="select_box">
					<date:year beforeYears="4" afterYears="4" attribute=" id='preportSelYear' name='year' class='selectboxPopup' onchange='searchProjectReportInfo()' " selectedInfo="${year}" />년&nbsp;
					<date:month attribute=" id='preportSelMonth' name='month' class='selectboxPopup' onchange='searchProjectReportInfo()' " selectedInfo="${month}" />월&nbsp;
				</div>
			</div>
		</div>
		
		<div class="board_contents sc">
			<!-- Table View-->
			<table class="tbl-view">
				<colgroup>
					<col style="width: 10%">
					<col style="width: 10%">
					<col style="width: 40%">
					<col style="width: 40%">
				</colgroup>
				<thead id="projectReportInfoTableHeader">
				<tr>
					<th>일</th>
					<th>요일</th>
					<th>액티비티</th>
					<th>실행인력</th>
				</tr>
				</thead>
				<tbody id="projectReportInfoTableBody">
					<c:if test="${!empty projectReportPlanList}">
						<c:forEach var="rs" items="${projectReportPlanList}">
							<tr onmouseover="row_over(this)" onmouseout="row_out(this)">
								<td style="<c:choose><c:when test="${rs.week == 'SAT'}"> background-color: #eee; </c:when><c:when test="${rs.week == 'SUN' }">background-color: #eee; </c:when></c:choose>">
									<span id="day" style="<c:choose><c:when test="${rs.week == 'SAT'}"> color: #5A95D7 </c:when><c:when test="${rs.week == 'SUN' }">color: #E77D7D </c:when></c:choose>"><c:out value="${rs.day}"/></span></td>
								<td style="<c:choose><c:when test="${rs.week == 'SAT'}"> background-color: #eee; </c:when><c:when test="${rs.week == 'SUN' }">background-color: #eee; </c:when></c:choose>">
									<span id="week" style="<c:choose><c:when test="${rs.week == 'SAT'}"> color: #5A95D7 </c:when><c:when test="${rs.week == 'SUN' }">color: #E77D7D </c:when></c:choose>"><c:out value="${rs.week}"/></span></td>
								<td class="left" <c:choose><c:when test="${rs.week == 'SAT'}"> style="background-color: #eee;" </c:when><c:when test="${rs.week == 'SUN' }"> style="background-color: #eee;" </c:when></c:choose>>
									<c:if test="${viewMode != 'projectSearch' && !readonly}">
									<c:if test="${project.projectState < '4' || project.adminOpen == 'Y'}">
											<img src="/images/btn_schedule_regist_mini.jpg" alt="지도일정등록" <%-- onclick="openProjecReportScheduleRegistPopup('<c:out value="${rs.year}"/>-<c:out value="${rs.month}"/>-<c:out value="${rs.day}"/>')" style="cursor:hand;"  --%>align="absmiddle" border="0">
										</c:if>
									</c:if>
									<span id="workName"><c:out value="${rs.workName}"/></span></td>
								<td class="left" <c:choose><c:when test="${rs.week == 'SAT'}"> style="background-color: #eee;" </c:when><c:when test="${rs.week == 'SUN' }"> style="background-color: #eee;" </c:when></c:choose>>
									<c:forEach var="item" items="${rs.nameArray}" varStatus="cnt"> 
										<c:if test="${item != ''}">
											<span id="<c:out value="${rs.year}"/><c:out value="${rs.month}"/><c:out value="${rs.day}"/><c:out value="${rs.ssnArray[cnt.count-1]}" />" 
													year="<c:out value="${rs.year}"/>" month="<c:out value="${rs.month}"/>" day="<c:out value="${rs.day}"/>" ssn="<c:out value="${rs.ssnArray[cnt.count-1]}" />" 
											><a href="#" 
													<c:if test="${project.businessTypeCode == 'BTE' || project.businessTypeCode == 'BTG' || project.businessTypeCode == 'BTI'}">
														onclick="changeEduWorkTime('<c:out value="${rs.year}"/>','<c:out value="${rs.month}"/>','<c:out value="${rs.day}"/>','<c:out value="${rs.ssnArray[cnt.count-1]}"/>')" 
													</c:if>
												><c:out value="${item}"/></a>
												<c:choose>
													<c:when test="${viewMode == 'projectSearch' || readonly}">
									    				<!-- , -->
									    			</c:when>
													<c:otherwise>
														<c:if test="${project.projectState == '1' || project.projectState == '2' || project.projectState == '3' || project.adminOpen == 'Y'}">
														<img alt="삭제" src="/images/delete.gif" style="cursor: hand;" 
															onclick="deleteProjectReportSchedule('<c:out value="${rs.year}"/><c:out value="${rs.month}"/><c:out value="${rs.day}"/><c:out value="${rs.ssnArray[cnt.count-1]}" />')">
														</c:if>
													</c:otherwise>
												</c:choose>
											</span>
										</c:if> 
									</c:forEach> 
								</td>				
							</tr>
						</c:forEach>
					</c:if>
					<c:if test="${empty projectReportPlanList}">
						<td colspan="4">데이터가 없습니다.</td>
					</c:if>
				</tbody>	
			</table>
		</div>
	</div>
</div>
</div>					
</form>
<div id="tchange" style="display: none; position: absolute;">
	<table width="330" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td><img src='/images/box_TL.gif'></td>
		<td background='/images/box_T.gif'></td>
		<td><img src='/images/box_TR.gif'></td>
	</tr>
	<tr>
		<td background='/images/box_L.gif'></td>
		<td bgcolor="#ffffff" width="100%">
			<table width="100%" border="0" cellpadding='0' cellspacing='0' bgcolor='#ffffff'>
				<tr>
					<td align="center" width="90">강의시간변경</td>
					<td align="center" width="80">
						<input name='teachingTime' id='teachingTime' style="width:95%;ime-mode:disabled" class="textInput_center" onchange="Number_Only(this, -1, -1);">
					</td>
					<td>
						<a class="btNe006bc6 txt2btn" href="#" onclick="changeEduWorkTimeSave();">저장</a><a class="btNa0a0a0 txt2btn" href="#" onclick="$('tchange').hide();">닫기</a>
					</td>									
				</tr>
			</table>
		</td> 
		<td background='/images/box_R.gif'></td>
	</tr>
	<tr>
		<td><img src='/images/box_BL.gif'></td>
		<td background='/images/box_B.gif'></td>
		<td><img src='/images/box_BR.gif'></td>
	</tr>
	</table>
</div>	
</body>
</html>					