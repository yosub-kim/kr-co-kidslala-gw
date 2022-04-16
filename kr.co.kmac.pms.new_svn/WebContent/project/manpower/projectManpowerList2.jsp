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
//프로젝트 투입일정 ---------------------------------------------------시작//

function closeFrame(){
	window.close();
	location.href="/action/ProjectManpowerAction.do?mode=getProjectManpowerList&projectCode=<%= request.getParameter("projectCode") %>&viewMode=<%= request.getParameter("viewMode") %>";
}
function searchProjectManpower() {
	var ActionURL = "/action/ProjectManpowerAction.do?mode=searchProjectManpowerList";
	var sFrm = document.forms["projectManpowerForm"];
	
	var status = AjaxRequest.submit (
			sFrm,
			{	'url':ActionURL,
				'onSuccess':function(obj){
					var res = eval('(' + obj.responseText + ')');
					var rsObj = res.projectManpowerList;
					var project = res.project;
					var tbody = $('projectManpowerInfoTableBody');
		           	var tdCount = $('projectManpowerInfoTableBody').down('tr').childElements().size();

		           	tbody.childElements().each( function(manpowerInfo){
		           		manpowerInfo.remove();
		    		});
		    		
 		    		rsObj.each(function(manpowerInfo){
			    	    var newRow=tbody.insertRow(-1);
			    		var newCellArr = new Array();
			    		for ( var i=0;i<tdCount;i++ ){
			    			newCellArr[i] = newRow.insertCell(-1);
			    		}		
						
		    			newCellArr[0].innerHTML = "<span id='day'>"+manpowerInfo.day+"</span>";
		    			newCellArr[1].innerHTML = "<span id='week'>"+manpowerInfo.week+"</span>";
		    			
		    			// 투입일정등록 버튼 이미지 생성(프로젝트가 평가, 리뷰가 아닐 때 My Project 일 때만 표시)
		    			var tempElement = "<span id='workName'> "+manpowerInfo.workName+"</span>";
		    			if (project.projectState < '4' || project.adminOpen == 'Y') {
			    			if('<%=request.getAttribute("viewMode")%>' != 'projectSearch' &&  !res.readonly){
			    				tempElement = "<img src='/images/btn_schedule_regist_mini.jpg' alt='투입일정등록' style='cursor:hand;' align='absmiddle' border='0' "
									+ " onclick='openProjecManpowerScheduleRegistPopup(\""+ manpowerInfo.year +"-"+ manpowerInfo.month +"-"+ manpowerInfo.day +"\")' > "
									+ "<span id='workName'> "+manpowerInfo.workName+"</span>";
			    			}
			    		}
		    			newCellArr[2].innerHTML = tempElement;
		    			newCellArr[2].style.textAlign = "left";
		    			var tempHtml = "";
		    			for(var i=0; manpowerInfo.ssnArray.length >i; i++){
			    			if(manpowerInfo.nameArray[i] != ''){
				    			var bte="";
								var delStr='';
								if('<%=request.getAttribute("viewMode")%>' == 'projectSearch' ||  res.readonly == true){
									delStr=', ';
								}else{
									delStr=" <img alt='삭제' src='/images/delete.gif' align='absmiddle' style='cursor: hand;' onclick=\"deleteProjectManpowerSchedule('"+manpowerInfo.year+manpowerInfo.month+manpowerInfo.day+manpowerInfo.ssnArray[i]+"')\">" 
								}
			    				tempHtml += "<span id='"+manpowerInfo.year+manpowerInfo.month+manpowerInfo.day+manpowerInfo.ssnArray[i]+"' year='"+manpowerInfo.year+"' month='"+manpowerInfo.month+"' day='"+manpowerInfo.day+"' ssn='"+manpowerInfo.ssnArray[i]+"'>"
			    								+ "<a href='#'"+  bte + ">"+ manpowerInfo.nameArray[i] +"</a>"
												+ delStr + "&nbsp;</span>";
			    			}
		    			}
		    			newCellArr[3].innerHTML = tempHtml;
		    			newCellArr[3].style.textAlign = "left";
		    			
		    			if(manpowerInfo.week == 'SAT' ){
		    				newCellArr[0].style.backgroundColor = "#D9ECFF"; 
			    			newCellArr[1].style.backgroundColor = "#D9ECFF"; 
			    			newCellArr[2].style.backgroundColor = "#D9ECFF"; 
			    			newCellArr[3].style.backgroundColor = "#D9ECFF"; 
		    			} else if(manpowerInfo.week == 'SUN'){
		    				newCellArr[0].style.backgroundColor = "#FFDFFF"; 
		    				newCellArr[1].style.backgroundColor = "#FFDFFF"; 
		    				newCellArr[2].style.backgroundColor = "#FFDFFF"; 
		    				newCellArr[3].style.backgroundColor = "#FFDFFF"; 
		    			} else {
		    				newCellArr[0].style.backgroundColor = "#FFFFFF"; 
		    				newCellArr[1].style.backgroundColor = "#FFFFFF"; 
		    				newCellArr[2].style.backgroundColor = "#FFFFFF"; 
		    				newCellArr[3].style.backgroundColor = "#FFFFFF"; 
		    			}
		    		});	
				},
				'onLoading':function(obj){},
				'onError':function(obj){
					alert("화면 새로고침을 실패하였습니다.");
				}
			}
	); status = null;			
}
function openProjecManpowerScheduleRegistPopup(selectedDay) { 
	var preportPop = 
	window.open("/action/ProjectManpowerAction.do"
			+ "?mode=loadProjectManpowerSchedulePopup"
			+ "&projectCode="+$('projectCode').value
			+ "&selectedDay=" + selectedDay,
			"acct", "top=0,left=0,width=490,height=350,scrollbars=no");
}
function deleteProjectManpowerSchedule(delKey){
	var scheduleElement = $(delKey);
	if(confirm("삭제하시겠습니까?")) {
		var ActionURL = "/action/ProjectManpowerAction.do?mode=deleteProjectManpower";
		var status = AjaxRequest.post (
				{	'url':ActionURL,
					'parameters' : { "projectCode": $("projectCode").value, "year": scheduleElement.readAttribute("year"), "month": scheduleElement.readAttribute("month"), "day": scheduleElement.readAttribute("day"), "ssn":scheduleElement.readAttribute("ssn") },
					'onSuccess':function(obj){
						var res = eval('(' + obj.responseText + ')');
						// 삭제 이후 리스트 화면 생성
						var rsObj = res.projectManpowerList;
						var project = res.project;
						var tbody = $('projectManpowerInfoTableBody');
			           	var tdCount = $('projectManpowerInfoTableBody').down('tr').childElements().size();

			           	tbody.childElements().each( function(manpowerInfo){
			           		manpowerInfo.remove();
			    		});
			    		
	 		    		rsObj.each(function(manpowerInfo){
				    	    var newRow=tbody.insertRow(-1);
				    		var newCellArr = new Array();
				    		for ( var i=0;i<tdCount;i++ ){
				    			newCellArr[i] = newRow.insertCell(-1);
				    		}		
							
			    			newCellArr[0].innerHTML = "<span id='day'>"+manpowerInfo.day+"</span>";
			    			newCellArr[1].innerHTML = "<span id='week'>"+manpowerInfo.week+"</span>";
			    			
			    			// 투입일정등록 버튼 이미지 생성(프로젝트가 평가, 리뷰가 아닐 때 My Project 일 때만 표시)
			    			var tempElement = "<span id='workName'> "+manpowerInfo.workName+"</span>";
			    			if (project.projectState < '4' || project.adminOpen == 'Y') {
				    			if('<%=request.getAttribute("viewMode")%>' != 'projectSearch' &&  !res.readonly){
				    				tempElement = "<img src='/images/btn_schedule_regist_mini.jpg' alt='투입일정등록' align='absmiddle' border='0' "
										+ " > "
										+ "<span id='workName'> "+manpowerInfo.workName+"</span>";
				    			}
				    		}
			    			newCellArr[2].innerHTML = tempElement;
			    			newCellArr[2].style.textAlign = "left";
			    			var tempHtml = "";
			    			for(var i=0; manpowerInfo.ssnArray.length >i; i++){
				    			if(manpowerInfo.nameArray[i] != ''){
					    			var bte="";
									var delStr='';
									if('<%=request.getAttribute("viewMode")%>' == 'projectSearch' ||  res.readonly == true){
										delStr=', ';
									}else{
										delStr=" <img alt='삭제' src='/images/delete.gif' align='absmiddle' style='cursor: hand;' onclick=\"deleteProjectManpowerSchedule('"+manpowerInfo.year+manpowerInfo.month+manpowerInfo.day+manpowerInfo.ssnArray[i]+"')\">" 
									}
				    				tempHtml += "<span id='"+manpowerInfo.year+manpowerInfo.month+manpowerInfo.day+manpowerInfo.ssnArray[i]+"' year='"+manpowerInfo.year+"' month='"+manpowerInfo.month+"' day='"+manpowerInfo.day+"' ssn='"+manpowerInfo.ssnArray[i]+"'>"
				    								+ "<a href='#'"+  bte + ">"+ manpowerInfo.nameArray[i] +"</a>"
													+ delStr + "&nbsp;</span>";
				    			}
			    			}
			    			newCellArr[3].innerHTML = tempHtml;
			    			newCellArr[3].style.textAlign = "left";
			    			
			    			if(manpowerInfo.week == 'SAT' ){
			    				newCellArr[0].style.backgroundColor = "#eee"; 
				    			newCellArr[1].style.backgroundColor = "#eee"; 
				    			newCellArr[2].style.backgroundColor = "#eee"; 
				    			newCellArr[3].style.backgroundColor = "#eee"; 
				    			newCellArr[0].style.color="#5A95D7";
			    			} else if(manpowerInfo.week == 'SUN'){
			    				newCellArr[0].style.backgroundColor = "#eee"; 
			    				newCellArr[1].style.backgroundColor = "#eee"; 
			    				newCellArr[2].style.backgroundColor = "#eee"; 
			    				newCellArr[3].style.backgroundColor = "#eee"; 
			    				newCellArr[0].style.color="#E77D7D";
			    			} else {
			    				newCellArr[0].style.backgroundColor = "#FFFFFF"; 
			    				newCellArr[1].style.backgroundColor = "#FFFFFF"; 
			    				newCellArr[2].style.backgroundColor = "#FFFFFF"; 
			    				newCellArr[3].style.backgroundColor = "#FFFFFF"; 
			    			}
			    		});	
						alert(res.message);
					},
					'onLoading':function(obj){},
					'onError':function(obj){
						alert("삭제할 수 없습니다.");
					}
				}
		); status = null;
	}
}

function registProjectMember() {
	if(confirm("입력된 인력을 투입일정으로 입력 하시겠습니까 ? ")){
		var ActionURL = '';
		if('<%=request.getAttribute("viewMode")%>' == 'wreport'){
			ActionURL = "/action/ProjectManpowerAction.do?mode=searchProjectManpowerListForWreport&projectCode="+$("projectCode").value+"&startDate="+ $("thisWeekFromDate").value +"&endDate="+ $("thisWeekToDate").value;
		} else if('<%=request.getAttribute("viewMode")%>' == 'mreport'){
			ActionURL = "/action/ProjectManpowerAction.do?mode=searchProjectManpowerListForMreport&projectCode="+$("projectCode").value+"&year="+ $("year").value +"&month="+ $("month").value;
		}
		
		var status = AjaxRequest.post (
				{	'url':ActionURL,
					'onSuccess':function(obj){
						var res = eval('(' + obj.responseText + ')');
						// 삭제 이후 리스트 화면 생성
						if(res.projectManpowerNameList == "undefined" || res.projectManpowerNameList.length <= 0){
							if(confirm("투입일정에 등록된 인력이 없습니다. 계속 진행하시겠습니까 ?")){
								opener.document.getElementById("projectMemberStr").value = "";
								self.close();
							}
						} else {
							var rsObj = res.projectManpowerNameList;
							var str='';
							for ( var i=0;i<rsObj.length;i++){ 
								str = str+rsObj[i]+", ";	
							}
							opener.document.getElementById("projectMemberStr").value = str.substring(0, str.length-2);
							self.close();
						}
					},
					'onLoading':function(obj){},
					'onError':function(obj){
						alert("실행 중 오류가 발행했습니다.008");
					}
				}
		); status = null;
	
		
	///////////////////////////////////////////////	
	///////////////////////////////////////////////	
	}
}

<c:if test="${viewMode eq 'wreport' }">
	window.onload = function() {
		document.location.href = "#<c:out value="${thisWeekFromDate}"/>";
	}
</c:if>
//프로젝트 투입일정 ---------------------------------------------------끝//
</script>
</head>
 
<body>
	<form name="projectManpowerForm" method="post">
	<div style="display: none;">
		<c:if test="${viewMode eq 'wreport' }">
			<input type="text" name="assignWeekOfMonth" id="assignWeekOfMonth" value="<c:out value="${assignWeekOfMonth}"/>" >
			<input type="text" name="thisWeekFromDate" id="thisWeekFromDate" value="<c:out value="${thisWeekFromDate}"/>" >
			<input type="text" name="thisWeekToDate" id="thisWeekToDate" value="<c:out value="${thisWeekToDate}"/>" >
			<input type="text" name="nextWeekFromDate" id="nextWeekFromDate" value="<c:out value="${nextWeekFromDate}"/>" >
			<input type="text" name="nextWeekToDate" id="nextWeekToDate" value="<c:out value="${nextWeekToDate}"/>" >
		</c:if>
		<c:if test="${viewMode eq 'mreport' }">
			<input type="text" name="year" id="year" value="<c:out value="${year}"/>" >
			<input type="text" name="month" id="month" value="<c:out value="${month}"/>" >
		</c:if>

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
					<p class="term"><i class="mdi mdi-file-document-outline"></i>일정삭제</p>
				</div>
				<div class="select_box">
					<date:year beforeYears="4" afterYears="4" attribute=" id='preportSelYear' name='year' class='selectboxPopup' onchange='searchProjectManpower()' " selectedInfo="${year}" />년&nbsp;
					<date:month attribute=" id='preportSelMonth' name='month' class='selectboxPopup'onchange='searchProjectManpower()' " selectedInfo="${month}" />월&nbsp;
					<button type="button" class="btn line btn_grey" onclick="closeFrame()"><i class="mdi mdi-close-box"></i>닫기</button>
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
						<tr>
							<th>일</th>
							<th>요일</th>
							<th>액티비티</th>
							<th>실행인력</th>
						</tr>
						<tbody id="projectManpowerInfoTableBody"> 
							<c:if test="${!empty projectManpowerList}">
								<c:forEach var="rs" items="${projectManpowerList}">
									<tr onmouseover="row_over(this)" onmouseout="row_out(this)">
										<td style="<c:choose><c:when test="${rs.week == 'SAT'}"> background-color: #eee; </c:when><c:when test="${rs.week == 'SUN' }">background-color: #eee; </c:when></c:choose>">
											<a id="#<c:out value="${rs.year}"/><c:out value="${rs.month}"/><c:out value="${rs.day}"/>"></a><span id="day" style="<c:choose><c:when test="${rs.week == 'SAT'}"> color: #5A95D7 </c:when><c:when test="${rs.week == 'SUN' }">color: #E77D7D </c:when></c:choose>"><c:out value="${rs.day}"/></span></td>
										<td style="<c:choose><c:when test="${rs.week == 'SAT'}"> background-color: #eee; </c:when><c:when test="${rs.week == 'SUN' }">background-color: #eee; </c:when></c:choose>">
											<span id="week" style="<c:choose><c:when test="${rs.week == 'SAT'}"> color: #5A95D7 </c:when><c:when test="${rs.week == 'SUN' }">color: #E77D7D </c:when></c:choose>"><c:out value="${rs.week}"/></span></td>
										<td class="left" <c:choose><c:when test="${rs.week == 'SAT'}"> style="background-color: #eee" </c:when><c:when test="${rs.week == 'SUN' }"> style="background-color: #eee" </c:when></c:choose>>
											<c:if test="${viewMode != 'projectSearch' && !readonly}">
											<c:if test="${project.projectState < '4' || project.adminOpen == 'Y'}">
													<img src='/images/btn_schedule_regist_mini.jpg' alt='투입일정등록' <%-- onclick="openProjecManpowerScheduleRegistPopup('<c:out value="${rs.year}"/>-<c:out value="${rs.month}"/>-<c:out value="${rs.day}"/>')" --%> align="absmiddle" border="0">
												</c:if>
											</c:if>
											<span id="workName"><c:out value="${rs.workName}"/></span></td>
										<td class="left" <c:choose><c:when test="${rs.week == 'SAT'}"> style="background-color: #eee" </c:when><c:when test="${rs.week == 'SUN' }"> style="background-color: #eee" </c:when></c:choose>>
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
																	onclick="deleteProjectManpowerSchedule('<c:out value="${rs.year}"/><c:out value="${rs.month}"/><c:out value="${rs.day}"/><c:out value="${rs.ssnArray[cnt.count-1]}" />')">
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
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
</div>
</form>
</body>
</html>					