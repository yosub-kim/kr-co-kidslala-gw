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

function scheduleDelete(){
	var url="/action/ProjectReportInfoAction.do?mode=getProjectReportInfoList2&&projectCode=<%= request.getParameter("projectCode") %>&viewMode=<%= request.getParameter("viewMode") %>";
	window.open(url,'budget_result','top=140,left=140,status=no,toolbar=no,scrollbars=yes,resizable=yes,width=980,height=850,directories=no,menubar=no');
}

function goPage(next_page) {
	document.projectReportInfoForm.pageNo.value = next_page ;
	document.projectReportInfoForm.target = "";		
	document.projectReportInfoForm.action = "/action/ProjectReportInfoAction.do?mode=searchProjectReportInfoList";
	document.projectReportInfoForm.submit();
}

function moveNext(){
	var year = document.projectReportInfoForm.year.value;
	var month = document.projectReportInfoForm.month.value;
	if (month == "12") {
		document.projectReportInfoForm.year.value = parseInt(year) + 1;
		document.projectReportInfoForm.month.value = 1;
	}
	else {
		document.projectReportInfoForm.month.value = parseInt(month) + 1;
	}
	document.projectReportInfoForm.submit();
}

function movePrevious(){
	var year = document.projectReportInfoForm.year.value;
	var month = document.projectReportInfoForm.month.value;
	if (month == "1") {
		document.projectReportInfoForm.year.value = parseInt(year) - 1;
		document.projectReportInfoForm.month.value = 12;
	}
	else {
		document.projectReportInfoForm.month.value = parseInt(month) - 1;
	}
	document.projectReportInfoForm.submit();
}

function registProjectReportSchedule() {
	if($('startDate').value ==""){alert('일정을 선택하세요.'); return;}
	if($('workSeq').value ==""){alert('업무를 선택하세요.'); return;}
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
	
	var ActionURL = "/action/ProjectReportInfoAction.do?mode=storeProjectReportInfo";
	var sFrm = document.forms["projectReportScheduleForm"];

	var status = AjaxRequest.submit(
			sFrm,
			{	'url':ActionURL,
				'onSuccess':function(obj){
					alert("등록하였습니다.");
					layer_close('pop_register');
					location.href="/action/ProjectReportInfoAction.do?mode=getProjectReportInfoList&projectCode=<%= request.getParameter("projectCode") %>&viewMode=<%= request.getParameter("viewMode") %>";
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
 
<body class="load sc">
	<form name="projectReportInfoForm" method="post">
	<div style="display: none;">
		<input type="text" name="projectCode" id="projectCode" value="<c:out value="${projectCode}"/>" >
		<input type="text" name="readonly" id="readonly" value="<c:out value="${readonly}"/>" >
		<input type="text" name="selectedDay" id="selectedDay" value="<c:out value="${selectedDay}"/>" >
	</div>
	
	<div style="margin:70px 15px 0 15px;">
		<div class="board_box">
			<div class="title_both">
				<div class="h1_area">
					<p class="h1">프로젝트 참여정보</p>
				</div>
				<div class="select_box">
						<c:if test="${viewMode != 'projectSearch' && !readonly}">
							<c:if test="${project.projectState < '6' || project.adminOpen == 'Y'}">
								<%-- <button type="button" class="btn line btn_blue" onclick="openProjecManpowerScheduleRegistPopup(document.getElementById('preportSelYear').value + '-' + document.getElementById('preportSelMonth').value + '-<c:out value="${today}"/>');"><i class="mdi mdi-square-edit-outline"></i>일정등록</button> --%>
								<button type="button" class="btn line btn_blue" onclick="javascript:layer_open(this,'pop_register');"><i class="mdi mdi-square-edit-outline"></i>일정등록</button>
								<button type="button" class="btn line btn_pink" onclick="javascript:scheduleDelete();"><i class="mdi mdi-trash-can-outline"></i>일정삭제</button>
							</c:if>
						</c:if>
				</div>
			</div>
			
			<div class="board_contents sc">
				<!-- month_select -->
				<div class="month_select">
					<button type="button" style="cursor:hand;" onclick="movePrevious()"><i class="mdi mdi-chevron-left"></i></button>
					<div class="select_area">
						<date:year beforeYears="2" afterYears="2" attribute=" id='preportSelYear' name='year' class='selectboxPopup' onchange='document.projectReportInfoForm.submit();' " selectedInfo="${year}" />														
						<%-- <date:month attribute=" id='preportSelMonth' name='month' class='selectboxPopup' onchange='document.projectManpowerForm.submit();' " selectedInfo="${month}" />
						 --%><select name="month" class="selectboxPopup" onchange="document.projectReportInfoForm.submit();">
							<c:forEach var="i" begin="1" end="12">
								<option value="<c:out value="${i}"/>" <c:if test="${month == i}">selected</c:if>><c:out value="${i}"/></option>
							</c:forEach>
						</select>
						<%-- <select name="month" id="preportSelMonth" class="selectboxPopup" onchange="document.projectManpowerForm.submit();">
							<c:forEach var="i" begin="1" end="12"><option value="<c:out value="${i}"/>" <c:if test="${month == i}">selected</c:if>><c:out value="${i}"/></option></c:forEach>
						</select> --%>
					</div>
					<button type="button" style="cursor:hand;" onclick="moveNext()"><i class="mdi mdi-chevron-right"></i></button>
				</div>
				<!-- // month_select -->
				
				<div class="gantt_box member">
					<div class="list">
						<div class="gantt_title"><p>[역할] 컨설턴트 명(직위/직책)</p></div>
						<div class="dropdown_list_wrap">
							<ul class="dropdown_list color">
								<!-- 프로젝트 멤버 추가 -->
								<c:if test="${!empty projectMemberList}">
									<c:forEach var="rs" items="${projectMemberList}">
										<li>
										<div class="drop_title_list"><a href="javascript:;" title="메뉴 보이기/숨기기"><span class="profile"><img src="../resources/img/photo_none.png" alt="프로필 이미지"></span><p>[<c:out value="${rs.role }" />] <c:out value="${rs.name }" /><span>(<c:out value="${rs.companyPositionName }" />)</span></p></a></div>
										<div class="drop_data_list">
											<ul>
												<c:forEach var="rs2" items="${projectWorkNameList }">
													<c:if test="${rs.ssn eq rs2.ssn }" >
														<li><p><c:out value="${rs2.workName }" /></p></li>
													</c:if>
												</c:forEach>
											</ul>
										</div>
										</li>
									</c:forEach>
								</c:if>
								<!-- // 프로젝트 멤버 추가 -->
							</ul>
						</div>
					</div>
					
					<div class="view">
						<div class="week">
							<c:if test="${!empty projectReportPlanList}">
								<c:forEach var="rs" items="${projectReportPlanList}">
									<div class='<c:out value="${rs.week }" />'>
										<c:choose>
											<c:when test="${rs.week eq 'SUN' }">일</c:when>
											<c:when test="${rs.week eq 'MON' }">월</c:when>
											<c:when test="${rs.week eq 'TUE' }">화</c:when>
											<c:when test="${rs.week eq 'WED' }">수</c:when>
											<c:when test="${rs.week eq 'THU' }">목</c:when>
											<c:when test="${rs.week eq 'FRI' }">금</c:when>
											<c:when test="${rs.week eq 'SAT' }">토</c:when>
										</c:choose>
									</div>
								</c:forEach>
							</c:if>
						</div>	
						<div class="week">
							<c:if test="${!empty projectReportPlanList}">
								<c:forEach var="rs" items="${projectReportPlanList}">
									<div class='<c:out value="${rs.week }" />'>
										<c:out value="${rs.day}"/>
									</div>
								</c:forEach>
							</c:if>
						</div>
						<tbody id="projectManpowerInfoTableBody"> 
						<div class="dropdown_list_wrap">
							<ul class="dropdown_list color">
									<c:forEach var="rs" items="${projectMemberList}">
										<li>
										<div class="drop_title_list">
											<div class="task">
												<c:if test="${!empty projectMonthWorkList}">
													<c:forEach var="item" items="${projectMonthWorkList}">
														<c:choose>
															<c:when test="${item.ssn eq rs.ssn && item.workName ne ''}">
																<c:choose><c:when test="${item.rn == '1' && item.rn2 == '1' }">
																	<div class="column select"></div>	
																</c:when><c:when test="${item.rn == '1' && item.rn2 != '1' }">
																	<div class="first_day select"></div>
																</c:when><c:when test="${item.rn == item.rn2 }">
																	<div class="last_day select"></div>
																</c:when>
																<c:otherwise><div class="select"></div></c:otherwise></c:choose>
															</c:when>
															<c:when test="${item.ssn eq rs.ssn && item.workName eq '' }">
																<div></div>
															</c:when>
															<c:otherwise>
															</c:otherwise>
														</c:choose>
													</c:forEach>
												</c:if>
											</div>
										</div>
										<div class="drop_data_list">												
												<c:forEach var="rs2" items="${projectWorkNameList }">
													<c:if test="${rs.ssn eq rs2.ssn }" >
														<c:if test="${!empty projectMonthWorkList}">
															<div class="task task_sub">
															<c:forEach var="item" items="${projectMonthWorkList}">
																<c:choose><c:when test="${rs2.ssn eq item.ssn && rs2.workName eq item.workName }">
																	<c:choose><c:when test="${item.rn == '1' && item.rn2 == '1' }">
																	<div class="column select"></div>	
																	</c:when><c:when test="${item.rn == '1' && item.rn2 != '1' }">
																		<div class="first_day select"></div>
																	</c:when><c:when test="${item.rn == item.rn2 }">
																		<div class="last_day select"></div>
																	</c:when>
																	<c:otherwise><div class="select"></div></c:otherwise></c:choose>
																</c:when>
																<c:when test="${rs2.ssn eq item.ssn && item.workName eq '' }">
																	<div></div>
																</c:when>
																<c:when test="${rs2.ssn eq item.ssn && rs2.workName ne item.workName }">
																	<div></div>
																</c:when>
																<c:otherwise>
																</c:otherwise>
																</c:choose>
															</c:forEach>
															</div>
														</c:if>
													</c:if>
												</c:forEach>
											</div>
											</li>
									</c:forEach>
								</ul>
							</div>
							</tbody>
						</div>				
					</div>
				</div>
			</div>
		</div>
	</form>
	
		<!-- 일정등록 팝업 -->
	<form name="projectReportScheduleForm" method="post">
		<div style="display: none;">
			<input type="text" name="projectCode" id="projectCode" value="<c:out value="${projectCode}"/>" >
			<input type="text" name="businessTypeCode" id="businessTypeCode" value="<c:out value="${businessTypeCode}"/>" >
		</div>
			<div id="pop_register" class="popup_layer pop_register">
				<div class="popup_bg"></div>
				<div class="popup_inner">
					<div class="a-both">
						<p class="h1">일정등록<span style="display: none;" id="toDate"></p>						
						<button type="button" class="btn-close" onclick="javascript:layer_close('pop_register');" title="닫기"><i class="mdi mdi-close"></i></button>
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
						<input type="checkbox" value="true" name="sequencial" id="sequencial" onclick="showToDate()" class="btn_check" checked><label for="sequencial"><p>연속입력</p></label> 
						<span id="weekend">
							<p>월</p><input type="checkbox" value="true" name="chkWeek1" id="chkWeek1"  class="btn_check" checked><label for="chkWeek1"></label>
							<p>화</p><input type="checkbox" value="true" name="chkWeek2" id="chkWeek2"  class="btn_check" checked><label for="chkWeek2"></label>
							<p>수</p><input type="checkbox" value="true" name="chkWeek3" id="chkWeek3" class="btn_check" checked><label for="chkWeek3"></label>
							<p>목</p><input type="checkbox" value="true" name="chkWeek4" id="chkWeek4" class="btn_check" checked><label for="chkWeek4"></label>
							<br><p>금</p><input type="checkbox" value="true" name="chkWeek5" id="chkWeek5" class="btn_check" checked><label for="chkWeek5"></label>
							<p>토</p><input type="checkbox" value="true" name="chkWeek6" id="chkWeek6" class="btn_check"><label for="chkWeek6"></label>
							<p>일</p><input type="checkbox" value="true" name="chkWeek7" id="chkWeek7" class="btn_check"><label for="chkWeek7"></label>
							<!-- <input type="checkbox" value="true" name="chkWeek8" id="chkWeek8" class="btn_check" checked><label for="check_all"><p>공휴일 제외</p></label> -->
								<!-- <input type="checkbox" name="check_all" id="check_all" class="btn_check" />
								<label for="check_all"><p>투입</p></label> -->
						</span>
						</div>
						<select id="workSeq" name="workSeq"  class="select">
								<option value="">액티비티를 선택하세요.</option>
								<c:forEach var="rs" items="${projectWorkNameList2.list}">
									<option value="<c:out value="${rs.workSeq}" />"><c:out value="${rs.workName}" /></option>
								</c:forEach>
						</select>
						<div class="board_contents pop_tbl sc">
							<table  id="projectProgressTable" class="tbl-edit td-c"><!-- td 영역이 가운데 정렬 일 경우 td-c -->
								<colgroup>
									<col style="width: 20%"/>
									<col style="width: 40%"/>
									<col style="width: 40%"/>
								</colgroup>
								<tr>
									<th>선택</th>
									<th>이름</th>
									<th>Role</th>	
								</tr>
								<tbody id="ListData">
									<c:forEach var="rs" items="${projectMemberList2.list}">
										<tr>
											<td><input id="<c:out value="${rs.ssn}"/>" type="checkbox" name="ssn" value="<c:out value="${rs.ssn}"/>" class="btn_check"  /><label for="<c:out value="${rs.ssn}" />"></label></td>
											<td><c:out value="${rs.name}" /></td>
											<td><c:out value="${rs.role}" /></td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
						<div class="btn_area">
							<button type="button" class="btn btn_blue" onclick="registProjectReportSchedule()">등록</button>
							<button type="button" class="btn btn_grey" onclick="javascript:layer_close('pop_register');">취소</button>
						</div>
					</div>
				</div>
			</div>
			</div>
		</form>
		<!-- // 일정등록 팝업 -->						
						
<!-- <div id="tchange" style="display: none; position: absolute;">
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
</div>	 -->
</body>
</html>					